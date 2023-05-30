package com.mes.aone.controller;

import com.mes.aone.entity.ProcessPlan;
import com.mes.aone.repository.ProcessPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class statusController {


    private final ProcessPlanRepository processPlanRepository;

    @Autowired
    public statusController(ProcessPlanRepository processPlanRepository) {
        this.processPlanRepository = processPlanRepository;
    }


    //현황 관리 페이지
    @GetMapping(value="/status")
    public String statusPage(Model model){
        LocalDateTime currentTime = LocalDateTime.now();
//        currentTime=LocalDateTime.of(2023,06,02,10,20); //임시;

        List<ProcessPlan> processPlanList = processPlanRepository.findByCurrentTimeAndSalesDate2(currentTime);
        System.out.println(processPlanList);

        // processStage에 따라 번호를 매기고 결과를 리스트에 담기
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

        model.addAttribute("processPlanList",processPlanList);
        model.addAttribute("processStageNumbers", processStageNumbers);

        return"pages/statusPage";
    }



//    @GetMapping(value = "/status/facility-info")
//    public @ResponseBody List<ProcessPlanDTO> getCurrentProcessPlans (Model model){
//        LocalDateTime currentTime = LocalDateTime.now();
////        currentTime=LocalDateTime.of(2023,06,02,13,20); //임시;
//
//        System.out.println(currentTime);
//        List<ProcessPlan> currentPlans = processPlanRepository.findByStartTimeBeforeAndEndTimeAfter(currentTime, currentTime);
//        List<ProcessPlanDTO> result = new ArrayList<>();
//
//        for (ProcessPlan plan : currentPlans) {
//            if(plan.getFacilityId()!=null){
//                result.add(new ProcessPlanDTO(plan.getStartTime(), plan.getEndTime(), plan.getFacilityId().getFacilityId(),
//                        plan.getWorkOrder().getSalesOrder().getProductName(), plan.getWorkOrder().getWorkOrderId()));
//            }
//
//        }
//
//        return result;
//    }
}