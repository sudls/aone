package com.mes.aone.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class shippingController {
    @GetMapping(value="/shipping")
    public String inventoryPage1(){
        return"pages/shippingPage";
    }
}
