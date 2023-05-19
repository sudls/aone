package com.mes.aone.controller;

import com.mes.aone.constant.SalesStatus;
import com.mes.aone.contant.Status;
import com.mes.aone.dto.OrderDTO;
import com.mes.aone.dto.SalesOrderFormDTO;
import com.mes.aone.entity.SalesOrder;
import com.mes.aone.repository.SalesOrderRepository;
import com.mes.aone.service.SalesOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class orderController {

    private final SalesOrderService salesOrderService;
    private final SalesOrderRepository salesOrderRepository;

//    @GetMapping(value="/order")
//    public String orderPage(Model model){
//
//        List<OrderDTO> orderDTOList = new ArrayList<>();
//
//        for(int i=1; i<=30; i++ ){
//            OrderDTO orderDTO = new OrderDTO();
//            orderDTO.setSalesOrderId(Long.valueOf(i));
//            orderDTO.setProductName("양배추즙");
//            orderDTO.setVendorId("ven-nh");
//            orderDTO.setSalesDate(LocalDateTime.now());
//            orderDTO.setSalesQty(342);
//            orderDTO.setSalesStatus(SalesStatus.A);
//
//            orderDTOList.add(orderDTO);
//        }
//
//        model.addAttribute("orderDTOList",orderDTOList);
//
//        return"pages/orderPage";
//    }

    //기본 조회 리스트
    @GetMapping(value="/order")
    public String orderPage(Model model){

        List<SalesOrder> salesOrderList = salesOrderRepository.findAll();
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
            Model model){

        List<SalesOrder> salesOrderList;

        if (searchProduct != null && !searchProduct.isEmpty() && searchVendor != null && !searchVendor.isEmpty() && searchState != null) {
            // 모든 검색 조건이 제공된 경우
            salesOrderList = salesOrderRepository.findByProductNameAndVendorIdAndSalesStatus(searchProduct, searchVendor, searchState);
        } else if (searchProduct != null && !searchProduct.isEmpty() && searchVendor != null && !searchVendor.isEmpty()) {
            // 제품명과 거래처로 검색한 경우
            salesOrderList = salesOrderRepository.findByProductNameAndVendorId(searchProduct, searchVendor);
        } else if (searchProduct != null && !searchProduct.isEmpty() && searchState != null) {
            // 제품명과 수주상태로 검색한 경우
            salesOrderList = salesOrderRepository.findByProductNameAndSalesStatus(searchProduct, searchState);
        } else if (searchVendor != null && !searchVendor.isEmpty() && searchState != null) {
            // 거래처와 수주상태로 검색한 경우
            salesOrderList = salesOrderRepository.findByVendorIdAndSalesStatus(searchVendor, searchState);
        } else if (searchProduct != null && !searchProduct.isEmpty()) {
            // 제품명으로 검색한 경우
            salesOrderList = salesOrderRepository.findByProductName(searchProduct);
        } else if (searchVendor != null && !searchVendor.isEmpty()) {
            // 거래처로 검색한 경우
            salesOrderList = salesOrderRepository.findByVendorId(searchVendor);
        } else if (searchState != null) {
            // 수주상태로 검색한 경우
            salesOrderList = salesOrderRepository.findBySalesStatus(searchState);
        } else {
            // 모든 검색 조건이 제공되지 않은 경우
            salesOrderList = salesOrderRepository.findAll();
        }

        model.addAttribute("orderDTOList", salesOrderList);
        model.addAttribute("orderDTO", new OrderDTO());
        model.addAttribute("salesOrderFromDTO", new SalesOrderFormDTO());

        return "pages/orderPage";
    }

    // 수주 등록
    @PostMapping(value="/order")
    public String salesOrderWrite(@Valid OrderDTO orderDTO, BindingResult bindingResult,  Model model) {

            if (bindingResult.hasErrors()) {
                return "/pages/orderPage";
            }

            try {
                salesOrderService.createSalesOrder(orderDTO);
                } catch (Exception e) {
                    model.addAttribute("errorMessage", "수주 등록 중 에러가 발생하였습니다");
                }
        return "redirect:/order";
    }



    // 수주 확정
    @PostMapping(value="/order/confirm")
    public String confirmOrder(@Valid SalesOrderFormDTO salesOrderFromDTO, Model model) {
        String[] selectedIds = salesOrderFromDTO.getSelectedOrderIdList().split(",");


        try {
            salesOrderService.confirmSalesOrderState(selectedIds);
        } catch (Exception e) {
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

    //수주 정보 검색

}
