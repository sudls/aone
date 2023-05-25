package com.mes.aone.controller;

import com.mes.aone.dto.WorkOrderDTO;
import com.mes.aone.entity.SalesOrder;
import com.mes.aone.repository.WorkOrderRepository;
import com.mes.aone.service.WorkOrderService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class productionController {


    private final WorkOrderRepository workOrderRepository;
    private final WorkOrderService workOrderService;

    public productionController(WorkOrderRepository workOrderRepository, WorkOrderService workOrderService) {
        this.workOrderRepository = workOrderRepository;
        this.workOrderService = workOrderService;
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



    @GetMapping(value="/production2")
    public String inventoryPage2(){
        return"pages/productionPage2";
    }

    @GetMapping(value="/production3")
    public String inventoryPage3(){
        return"pages/productionPage3";
    }
}
