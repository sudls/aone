package com.mes.aone.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class inventoryController {
    @GetMapping(value="/inventory1")
    public String inventoryPage1(){
        return"pages/inventoryPage1";
    }
    @GetMapping(value="/inventory2")
    public String inventoryPage2(){
        return"pages/inventoryPage2";
    }

    @GetMapping(value="/inventory3")
    public String inventoryPage3(){
        return"pages/inventoryPage3";
    }
}
