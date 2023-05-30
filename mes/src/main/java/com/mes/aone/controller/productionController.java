package com.mes.aone.controller;

import com.mes.aone.constant.Status;
import com.mes.aone.dto.ProductionDTO;
import com.mes.aone.dto.WorkOrderDTO;
import com.mes.aone.dto.WorkResultDTO;
import com.mes.aone.entity.Production;
import com.mes.aone.entity.SalesOrder;
import com.mes.aone.entity.WorkOrder;
import com.mes.aone.entity.WorkResult;
import com.mes.aone.repository.ProductionRepository;
import com.mes.aone.repository.WorkOrderRepository;
import com.mes.aone.repository.WorkResultRepository;
import com.mes.aone.service.ProductionService;
import com.mes.aone.service.WorkOrderService;
import com.mes.aone.service.WorkResultService;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class productionController {

    private final WorkOrderRepository workOrderRepository;
    private final WorkOrderService workOrderService;
    private final WorkResultRepository workResultRepository;
    private final ProductionRepository productionRepository;
    private final ProductionService productionService;
    private final WorkResultService workResultService;


    //작업지시 조회
//    @GetMapping(value="/production1")
//    public String workOrderPage1(Model model) {
//
//        List<WorkOrderDTO> workOrderDTOList = workOrderRepository.findWorkOrderDetails();
////        Collections.sort(workOrderDTOList, Comparator.comparingLong(WorkOrderDTO::getWorkOrderId).reversed());
//
//        model.addAttribute("workOrders", workOrderDTOList);
//        return "pages/productionPage1";
//    }

    //작업지시 조회
    @GetMapping(value="/production1")
    public String workOrderPage1(Model model) {

        List<WorkOrderDTO> workOrderDTOList = workOrderRepository.findWorkOrderDetails();
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

    // 작업지시현황 search
    @GetMapping("/production1/search")
    public String workOrderSearch(
            @RequestParam(required = false) String workOrderId,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
            @RequestParam(required = false) String productName,
            @RequestParam(required = false) Status workStatus,
            Model model
    ) {

        Long convertedWorkOrderId = null;
        if (workOrderId != null && !workOrderId.isEmpty()) {
                convertedWorkOrderId = Long.parseLong(workOrderId);
        }
        // 날짜 변환
        LocalDateTime startDateTime = null;
        LocalDateTime endDateTime = null;
        if(startDate != null && endDate != null){
            startDateTime =  LocalDateTime.of(startDate, LocalTime.MIN);
            endDateTime =  LocalDateTime.of(endDate, LocalTime.MAX);
        }

        List<WorkOrder> workOrderList = workOrderService.searchWorkOrders(convertedWorkOrderId, startDateTime, endDateTime, productName, workStatus);
        List<WorkOrderDTO> workOrderDTOList = WorkOrderDTO.of(workOrderList);
        model.addAttribute("workOrders", workOrderDTOList);
        return "pages/productionPage1";
    }




    //작업실적조회
    @GetMapping(value="/production2")
    public String workResultPage(Model model){
        List<WorkResultDTO> workResultDTOList = workResultRepository.findWorkResultDetails();
        model.addAttribute("workResults", workResultDTOList);
        return"pages/productionPage2";
    }

    // 작업실적 search
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

        List<WorkResult> workResultList = workResultService.searchBetweenDate(startDateTime, endDateTime);
        List<WorkResultDTO> workResultDTOList = WorkResultDTO.of(workResultList);
        model.addAttribute("workResults", workResultDTOList);
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