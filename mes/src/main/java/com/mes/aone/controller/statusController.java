package com.mes.aone.controller;

import com.mes.aone.entity.ProcessPlan;
import com.mes.aone.repository.ProcessPlanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.time.ZoneId;
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

        LocalDateTime currentTime = LocalDateTime.now();
        Date currentDate = Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant());


        List<ProcessPlan> productPlanList =  processPlanRepository.findProcessPlansByTimeCondition(currentDate);
        System.out.println(productPlanList);
        model.addAttribute("productPlanList",productPlanList);

        return"pages/statusPage";
    }
}
