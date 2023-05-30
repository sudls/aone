package com.mes.aone.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class lotController {
    @GetMapping(value="/lot")
    public String inventoryPage1(){
        return "pages/lotPage";
    }
}
