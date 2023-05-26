package com.mes.aone.controller;

import com.mes.aone.constant.Status;
import com.mes.aone.dto.WorkOrderDTO;
import com.mes.aone.dto.WorkResultDTO;
import com.mes.aone.entity.SalesOrder;
import com.mes.aone.entity.WorkOrder;
import com.mes.aone.entity.WorkResult;
import com.mes.aone.repository.ProcessPlanRepository;
import com.mes.aone.repository.WorkOrderRepository;
import com.mes.aone.repository.WorkResultRepository;
import com.mes.aone.service.WorkOrderService;
import org.springframework.data.domain.Sort;
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
//    private final ProductionQueryRepository productionQueryRepository;
    private final ProcessPlanRepository processPlanRepository;
    private final WorkResultRepository workResultRepository;

    public productionController(WorkOrderRepository workOrderRepository, WorkOrderService workOrderService, ProcessPlanRepository processPlanRepository, WorkResultRepository workResultRepository) {
        this.workOrderRepository = workOrderRepository;
        this.workOrderService = workOrderService;
        this.processPlanRepository = processPlanRepository;
        this.workResultRepository = workResultRepository;
    }

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


    @GetMapping("/production1/search")
    public String workOrderSearch(
            @RequestParam(required = false) String workOrderId,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
            @RequestParam(required = false) String processStage,
            @RequestParam(required = false) String productName,
            @RequestParam(required = false) Status workStatus,
            Model model
    ) {

        Long convertedWorkOrderId = null;
        if (workOrderId != null && !workOrderId.isEmpty()) {
            try {
                convertedWorkOrderId = Long.parseLong(workOrderId);
            } catch (NumberFormatException e) {
                // workOrderId가 숫자로 변환되지 않을 경우 처리할 내용을 추가합니다.
            }
        }

        // 날짜 변환
        LocalDateTime startDateTime = null;
        LocalDateTime endDateTime = null;
        if(startDate != null && endDate != null){
            startDateTime =  LocalDateTime.of(startDate, LocalTime.MIN);
            endDateTime =  LocalDateTime.of(endDate, LocalTime.MAX);
        }


        // 정렬 설정
        Sort sort = Sort.by(Sort.Direction.DESC, "workOrderId");

        List<WorkOrder> workOrderList = workOrderService.searchWorkOrders(convertedWorkOrderId, startDateTime, endDateTime, processStage, productName, workStatus, sort);
        List<WorkOrderDTO> workOrderDTOList = WorkOrderDTO.of(workOrderList);

        model.addAttribute("workOrders", workOrderDTOList);

        return "pages/productionPage1";
    }






    //작업실적조회
    @GetMapping(value="/production2")
    public String WorkResultPage(Model model){

        List<WorkResultDTO> workResultDTOList = workResultRepository.findWorkResultDetails();
        model.addAttribute("workResults", workResultDTOList);

        return"pages/productionPage2";
    }

    @GetMapping(value="/production2/search")
    public String orderPage(@RequestParam(required = false)  @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
                            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate,
                            Model model){
        // 날짜 변환
        LocalDateTime startDateTime = null;
        LocalDateTime endDateTime = null;
        if(startDate != null && endDate != null){
            startDateTime =  LocalDateTime.of(startDate, LocalTime.MIN);
            endDateTime =  LocalDateTime.of(endDate, LocalTime.MAX);
        }
        Sort sort = Sort.by(Sort.Direction.DESC, "workResultId");

        List<WorkResult> workResultList = workResultRepository.findByWorkFinishDateBetween(startDateTime, endDateTime, sort);
        model.addAttribute("workResults", workResultList);
        return "pages/productionPage2";
    }



    //생산현황
//    @GetMapping(value="/production3")
//    public String productionPage(Model model){
//
//        List<ProductionDTO> productionDTOList = productionRepository.findProductionDetials();
//        model.addAttribute("productions", productionDTOList);
//
//        return"pages/productionPage3";
//    }
}