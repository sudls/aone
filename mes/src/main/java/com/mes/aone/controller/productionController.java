package com.mes.aone.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class productionController {
    @GetMapping(value="/production1")
    public String inventoryPage1(){
        return"pages/productionPage1";
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
