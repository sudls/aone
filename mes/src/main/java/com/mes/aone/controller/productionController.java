package com.mes.aone.controller;

import com.mes.aone.dto.ProductionDTO;
import com.mes.aone.dto.WorkOrderDTO;
import com.mes.aone.entity.Production;
import com.mes.aone.entity.SalesOrder;
import com.mes.aone.entity.WorkResult;
import com.mes.aone.repository.ProductionRepository;
import com.mes.aone.repository.WorkOrderRepository;
import com.mes.aone.repository.WorkResultRepository;
import com.mes.aone.service.ProductionService;
import com.mes.aone.service.WorkOrderService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Controller
public class productionController {


    private final WorkOrderRepository workOrderRepository;
    private final WorkOrderService workOrderService;

    private final WorkResultRepository workResultRepository;

    private final ProductionRepository productionRepository;
    private final ProductionService productionService;

    public productionController(WorkOrderRepository workOrderRepository, WorkOrderService workOrderService, WorkResultRepository workResultRepository, ProductionRepository productionRepository, ProductionService productionService) {
        this.workOrderRepository = workOrderRepository;
        this.workOrderService = workOrderService;
        this.workResultRepository = workResultRepository;
        this.productionRepository = productionRepository;
        this.productionService = productionService;
    }

    //작업지시 조회
    @GetMapping(value="/production1")
    public String workOrderPage1(Model model) {

        List<WorkOrderDTO> workOrderDTOList = workOrderRepository.findWorkOrderDetails();
//        Collections.sort(workOrderDTOList, Comparator.comparingLong(WorkOrderDTO::getWorkOrderId).reversed());

        model.addAttribute("workOrders", workOrderDTOList);
        return "pages/productionPage1";
    }

    //작업지시에 따른 수주 정보 조회
    @GetMapping(value = "/production1/check")
    public String workOrderPage1(@RequestParam("workOrderId")Long workOrderId, Model model){
        List<WorkOrderDTO> workOrderDTOList = workOrderRepository.findWorkOrderDetails();
        List<SalesOrder> salesOrders = workOrderRepository.findSalesOrdersByWorkOrderId(workOrderId);
        model.addAttribute("salesOrders", salesOrders);
        model.addAttribute("workOrderId", workOrderId);
        model.addAttribute("workOrders", workOrderDTOList);


        return "pages/productionPage1";
    }


    //작업실적조회
    @GetMapping(value="/production2")
    public String workResultPage(Model model){

//        List<WorkResultDTO> workResultDTOList = workResultRepository.findWorkResultDetails();
//        model.addAttribute("workResults", workResultDTOList);

        return"pages/productionPage2";
    }


//    @GetMapping(value="/production2/search")
//    public String workResultPage(@RequestParam(required = false)  @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
//                                 @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
//                                 Model model) {
//        // 날짜 변환
//        LocalDateTime startDateTime = null;
//        LocalDateTime endDateTime = null;
//        if (startDate != null && endDate != null) {
//            startDateTime = LocalDateTime.of(startDate, LocalTime.MIN);
//            endDateTime = LocalDateTime.of(endDate, LocalTime.MAX);
//        }
//
////        Sort sort = Sort.by(Sort.Direction.DESC, "workOrderId");
//
//        List<WorkResultDTO> workResultSearchList = workResultRepository.findByWorkFinishDateBetween(startDateTime, endDateTime);
//        System.out.println("111111111111111111111");
//        for (WorkResultDTO workResult : workResultSearchList) {
//            System.out.println("Work Order ID: " + workResult.getWorkOrderId());
//            System.out.println("Product Name: " + workResult.getProductName());
//            // 추가 필드 및 속성에 대한 정보를 출력할 수 있습니다.
//            System.out.println("Work Finish Quantity: " + workResult.getWorkFinishQty());
//            System.out.println("Work Finish Date: " + workResult.getWorkFinishDate());
//            // ...
//        }
//
//        model.addAttribute("workResults", workResultSearchList);
//        return"pages/productionPage2";
//    }


    @GetMapping(value="/production2/search")
    public String workResultPage(@RequestParam(required = false)  @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                                 @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
                                 Model model) {
        // 날짜 변환
        LocalDateTime startDateTime = null;
        LocalDateTime endDateTime = null;
        if (startDate != null && endDate != null) {
            startDateTime = LocalDateTime.of(startDate, LocalTime.MIN);
            endDateTime = LocalDateTime.of(endDate, LocalTime.MAX);
        }

//        Sort sort = Sort.by(Sort.Direction.DESC, "workOrderId");

        List<WorkResult> workResultList = workResultRepository.findByWorkFinishDateBetween(startDateTime, endDateTime);
        System.out.println("111111111111111111111");
        for (WorkResult workResult : workResultList) {
            System.out.println("Work Order ID: " + workResult.getWorkOrder().getWorkOrderId());
            System.out.println("Product Name: " + workResult.getWorkOrder().getSalesOrder().getProductName());
            // 추가 필드 및 속성에 대한 정보를 출력할 수 있습니다.
            System.out.println("Work Finish Quantity: " + workResult.getWorkFinishQty());
            System.out.println("Work Finish Date: " + workResult.getWorkFinishDate());
            // ...
        }

        model.addAttribute("workResults", workResultList);
        return"pages/productionPage2";
    }





    //생산현황
    @GetMapping(value="production3")
    public String productionPage(Model model){
        List<ProductionDTO> productionDTOList = productionRepository.findProductionDetials();
        model.addAttribute("productions", productionDTOList);
        return "pages/productionPage3";
    }


    // 생산현황 search
    @GetMapping(value="/production3/search")
    public String productionPage( @RequestParam(required = false) String productName,
                                  @RequestParam(required = false) String processStage,
                                  Model model){
        List<Production> productionList = productionService.searchProduction(productName, processStage);
        List<ProductionDTO> productionDtoList = ProductionDTO.of(productionList);

        model.addAttribute("productions", productionDtoList);
        return "pages/productionPage3";
    }
}