package com.mes.aone.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class orderController {
    @GetMapping(value="/order")
    public String orderPage(){
        return"layout/orderPage";
//        return "pages/content1";
    }
}
