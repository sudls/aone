package com.mes.aone.controller;

import com.mes.aone.entity.ProcessPlan;
import com.mes.aone.entity.SalesOrder;
import com.mes.aone.repository.ProcessPlanRepository;
import com.mes.aone.repository.SalesOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class mainController {

    private final SalesOrderRepository salesOrderRepository;
    private final ProcessPlanRepository processPlanRepository;


    @Autowired
    public mainController(ProcessPlanRepository processPlanRepository,SalesOrderRepository salesOrderRepository) {
        this.processPlanRepository = processPlanRepository;
        this.salesOrderRepository = salesOrderRepository;
    }



    @GetMapping(value="/")
    public String mainPage(Model model){


        //특정날짜 수주 현황 정보

        //LocalDateTime currentTime = LocalDateTime.now();

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

        return"index";
    }
}
