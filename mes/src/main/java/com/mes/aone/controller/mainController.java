package com.mes.aone.controller;
import com.mes.aone.entity.ProcessPlan;
import com.mes.aone.repository.ProcessPlanRepository;
import com.mes.aone.repository.SalesOrderRepository;
import com.mes.aone.service.SalesOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class mainController {

    private final SalesOrderRepository salesOrderRepository;
    private final ProcessPlanRepository processPlanRepository;
    private final SalesOrderService salesOrderService;

    @Autowired
    public mainController(SalesOrderService salesOrderService, ProcessPlanRepository processPlanRepository,SalesOrderRepository salesOrderRepository) {
        this.processPlanRepository = processPlanRepository;
        this.salesOrderRepository = salesOrderRepository;
        this.salesOrderService = salesOrderService;
    }

    @GetMapping(value="/")
    public String mainPage(Model model){

        return"index";
    }

    @GetMapping(value ="/getEvent")
    public @ResponseBody List<Map<String, Object>> getSalesOrderInfo(){

        return salesOrderService.getEventList();
    }

    //작업지시에 따른 수주 정보 조회
    @GetMapping(value = "/check")
    public String mainPageCheck(@RequestParam("selectDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate selectDate, Model model){
        LocalDateTime selectDateTime = null;
        if(selectDate != null){
            selectDateTime =  LocalDateTime.of(selectDate, LocalTime.MIN);
        }


        LocalDateTime currentTime = LocalDateTime.now();
        currentTime=LocalDateTime.of(2023,06,02,13,20); //임시;
        System.out.println("currentTime="+currentTime);

        List<ProcessPlan> processPlanList = processPlanRepository.findByCurrentTimeAndSalesDate(currentTime,selectDateTime);
        System.out.println("here" + processPlanList);
        model.addAttribute("processPlanList", processPlanList);

        List<Integer> processStageNumbers = new ArrayList<>();
        for (ProcessPlan processPlan : processPlanList) {
            String processStage = processPlan.getProcessStage();
            int stageNum =0;

            switch (processStage) {
                case "원료계량":
                    stageNum=1;
                    if(processPlan.getStartTime().isAfter(currentTime)){
                        stageNum=0;
                    }
                    break;
                case "전처리" :
                    stageNum=2;
                    break;
                case "추출 및 혼합" :
                    stageNum=3;
                    break;
                case "충진" :
                    stageNum=4;
                    break;
                case "검사" :
                    stageNum=5;
                    break;
                case "포장":
                    stageNum=6;
                    if(processPlan.getEndTime().isBefore(currentTime)){
                        stageNum=7;
                    }
                    break;
            }

            processStageNumbers.add(stageNum);
        }
        System.out.println(processStageNumbers);

        model.addAttribute("processStageNumbers", processStageNumbers);


        return "index";
    }



}
