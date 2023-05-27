package com.mes.aone.controller;

import com.mes.aone.entity.ProcessPlan;
import com.mes.aone.repository.ProcessPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
//        List<ProcessPlan> productPlanList = processPlanRepository.findAll();
//        LocalDateTime currentTime = LocalDateTime.now();

//        LocalDateTime currentTime = LocalDateTime.now();

        LocalDateTime currentTime = LocalDateTime.of(2023,6,2,10,35);

        List<ProcessPlan> productPlanList =  processPlanRepository.findProcessPlansByTimeCondition(currentTime);
        System.out.println(productPlanList);

        // processStage에 따라 번호를 매기고 결과를 리스트에 담기
        List<Integer> processStageNumbers = new ArrayList<>();
        for (ProcessPlan processPlan : productPlanList) {
            String processStage = processPlan.getProcessStage();
            int stageNum;

            switch (processStage) {
                case "원료계량":
                    stageNum=1;
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
                    break;
                default:
                    stageNum = 0; // 다른 값일 경우 0으로 처리
                    break;
            }

            processStageNumbers.add(stageNum);
        }
        System.out.println(processStageNumbers);

        model.addAttribute("productPlanList",productPlanList);
        model.addAttribute("processStageNumbers", processStageNumbers);

        return"pages/statusPage";
    }
}
