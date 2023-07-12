package com.mes.aone.controller;

import com.mes.aone.constant.Status;
import com.mes.aone.dto.OrderDTO;
import com.mes.aone.dto.SalesOrderFormDTO;
import com.mes.aone.entity.SalesOrder;
import com.mes.aone.repository.SalesOrderRepository;
import com.mes.aone.service.MaterialService;
import com.mes.aone.service.SalesOrderService;
import com.mes.aone.service.StockService;
import com.mes.aone.service.WorkOrderService;
import com.mes.aone.util.Calculator;
import com.mes.aone.util.MESInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class orderController {

    private final SalesOrderService salesOrderService;
    private final SalesOrderRepository salesOrderRepository;
    private final WorkOrderService workOrderService;
    private final MaterialService materialService;
    private final StockService stockService;


    //기본 조회 리스트
    @GetMapping(value="/order")
    public String orderPage(Model model){
//        salesOrderService.standByState(salesOrderRepository.findSalesStatusA());      // 상태가 '대기'인 id들 찾아서 넣어줌 : 예상납품일 업데이트

        List<SalesOrder> salesOrderList = salesOrderRepository.findAll(Sort.by(Sort.Direction.DESC, "salesOrderId"));       // 내림차순
        model.addAttribute("orderDTOList",salesOrderList);
        model.addAttribute("orderDTO", new OrderDTO());
        model.addAttribute("salesOrderFromDTO", new SalesOrderFormDTO());
        return"pages/orderPage";
    }


    //조건 검색
    @GetMapping(value="/order/search")
    public String orderPage(
            @RequestParam(value = "searchProduct", required = false) String searchProduct,
            @RequestParam(value = "searchVendor", required = false) String searchVendor,
            @RequestParam(value = "searchState", required = false) Status searchState,
            @RequestParam(required = false)  @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
            Model model){

        // 날짜 변환
        LocalDateTime startDateTime = null;
        LocalDateTime endDateTime = null;
        if(startDate != null && endDate != null){
            startDateTime =  LocalDateTime.of(startDate, LocalTime.MIN);
            endDateTime =  LocalDateTime.of(endDate, LocalTime.MAX);
        }

        // 정렬 설정
        Sort sort = Sort.by(Sort.Direction.DESC, "salesOrderId");

        // 검색결과
        List<SalesOrder> salesOrderList = salesOrderService.searchSalesOrder(searchProduct, searchVendor, searchState, startDateTime, endDateTime, sort);
        model.addAttribute("orderDTOList", salesOrderList);
        model.addAttribute("orderDTO", new OrderDTO());
        model.addAttribute("salesOrderFromDTO", new SalesOrderFormDTO());

        return "pages/orderPage";
    }



    // 수주 등록
    @PostMapping(value="/order")
    public String salesOrderWrite(@Valid OrderDTO orderDTO, BindingResult bindingResult) {
            if (bindingResult.hasErrors()) {
                return "/pages/orderPage";
            }
            try {
                System.out.println("수주등록: " + orderDTO);
                MESInfo mesInfo = new MESInfo();
                materialService.getMaterialStockQuantities();
                materialService.setMaterialStockQuantities(mesInfo);
                stockService.getStockQuantities();   // 여기 하는중 -------------------------------

                Calculator calculator = new Calculator(mesInfo);

                mesInfo.setProductName(orderDTO.getProductName()); //수주 제품명
                mesInfo.setSalesQty(orderDTO.getSalesQty()); // 수주량
                mesInfo.setSalesDay(LocalDateTime.now()); // 수주일


                mesInfo.setPastPreProcessingMachine(salesOrderService.getProcessFinishTime("전처리"));
                mesInfo.setPastExtractionMachine1(salesOrderService.getFacilityFinishTime("extraction_1"));
                mesInfo.setPastExtractionMachine2(salesOrderService.getFacilityFinishTime("extraction_2"));
                mesInfo.setPastFillingLiquidMachine(salesOrderService.getFacilityFinishTime("pouch_1"));
                mesInfo.setPastFillingJellyMachine(salesOrderService.getFacilityFinishTime("liquid_stick_1"));
                mesInfo.setPastExaminationMachine(salesOrderService.getFacilityFinishTime("inspection"));
                mesInfo.setPastPackagingTime(salesOrderService.getProcessFinishTime("포장"));

                // 예상납품일 계산기 실행
                String purchaseCheck = calculator.purChaseAmount(); // 발주량 계산 메서드 실행
                if (purchaseCheck.equals("enough")){ // 완제품 재고가 충분하면
                    mesInfo.setEstDelivery(LocalDateTime.now()); // 당일 출고
                } else {                                             // 완제품 재고 불충분 시
                    calculator.materialArrived(); // 발주 원자재 도착시간 메서드 실행
                    calculator.measurement(); // 원료계량 메서드 실행
                    if (mesInfo.getProductName().equals("양배추즙") || mesInfo.getProductName().equals("흑마늘즙"))  // 즙 공정일 경우 전처리
                        calculator.preProcessing(); // 전처리 메서드 실행
                    calculator.extraction(); // 추출 메서드 실행
                    calculator.fill();//충진 메서드 실행
                    calculator.examination();//검사 메서드 실행
                    calculator.cooling();//열교환 메서드 실행
                    calculator.packaging(); // 포장 메서드 실행
                }

                // 예상납품일 세팅
                orderDTO.setEstDelivery(mesInfo.getEstDelivery());
                Long salesOrderId = salesOrderService.createSalesOrder(orderDTO); // 수주등록

                // 작업지시 세팅
                workOrderService.createWorkOrder(mesInfo, salesOrderId, purchaseCheck);
                } catch (Exception e) {
                    e.printStackTrace();
            }
        return "redirect:/order";
    }



    // 수주 확정
    @PostMapping(value="/order/confirm")
    public String confirmOrder(@Valid SalesOrderFormDTO salesOrderFromDTO, Model model) {
        String[] selectedIds = salesOrderFromDTO.getSelectedOrderIdList().split(",");
        System.out.println("수주확정: " + selectedIds);
        try {
            salesOrderService.confirmSalesOrderState(selectedIds);
            salesOrderRepository.findSalesStatusA();
            salesOrderService.standByState(salesOrderRepository.findSalesStatusA());      // 상태가 '대기'인 id들 찾아서 넣어줌 : 예상납품일 업데이트
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMessage", "수주 등록 중 에러가 발생하였습니다");
        }
        return "redirect:/order";
    }

    // 수주 취소
    @PostMapping(value="/order/cancel")
    public String cancelOrder(@Valid SalesOrderFormDTO salesOrderFromDTO, Model model) {
        String[] selectedIds = salesOrderFromDTO.getSelectedOrderIdList().split(",");

        try {
            salesOrderService.cancelSalesOrderState(selectedIds);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "수주 등록 중 에러가 발생하였습니다");
        }
        return "redirect:/order";
    }

    // 수주 삭제
    @PostMapping(value="/order/delete")
    public String deleteOrder(@Valid SalesOrderFormDTO salesOrderFromDTO, Model model) {
        String[] selectedIds = salesOrderFromDTO.getSelectedOrderIdList().split(",");

        try {
            salesOrderService.deleteSalesOrderState(selectedIds);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "수주 등록 중 에러가 발생하였습니다");
        }
        return "redirect:/order";
    }


}
