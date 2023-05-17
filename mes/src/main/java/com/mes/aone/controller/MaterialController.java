package com.mes.aone.controller;


import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/material")
@Log4j2
public class MaterialController {

    @GetMapping("/test")
    public String test(){

        return "/material/test";

    }

}
