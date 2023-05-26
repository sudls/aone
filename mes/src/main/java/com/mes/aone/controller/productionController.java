package com.mes.aone.controller;

import com.mes.aone.dto.ProductionDTO;
import com.mes.aone.dto.WorkOrderDTO;
import com.mes.aone.dto.WorkResultDTO;
import com.mes.aone.entity.SalesOrder;
import com.mes.aone.repository.ProductionRepository;
import com.mes.aone.repository.WorkOrderRepository;
import com.mes.aone.repository.WorkResultRepository;
import com.mes.aone.service.WorkOrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class productionController {


    private final WorkOrderRepository workOrderRepository;
    private final WorkOrderService workOrderService;

    private final WorkResultRepository workResultRepository;

    private final ProductionRepository productionRepository;

    public productionController(WorkOrderRepository workOrderRepository, WorkOrderService workOrderService, WorkResultRepository workResultRepository, ProductionRepository productionRepository) {
        this.workOrderRepository = workOrderRepository;
        this.workOrderService = workOrderService;
        this.workResultRepository = workResultRepository;
        this.productionRepository = productionRepository;
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


    //작업실적조회
    @GetMapping(value="/production2")
    public String WorkResultPage(Model model){

        List<WorkResultDTO> workResultDTOList = workResultRepository.findWorkResultDetails();
        model.addAttribute("workResults", workResultDTOList);

        return"pages/productionPage2";
    }

    //생산현황
    @GetMapping(value="/production3")
    public String productionPage(Model model){

        List<ProductionDTO> productionDTOList = productionRepository.findProductionDetials();
        model.addAttribute("productions", productionDTOList);

        return"pages/productionPage3";
    }
}