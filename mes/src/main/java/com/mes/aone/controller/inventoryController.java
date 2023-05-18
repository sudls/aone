package com.mes.aone.controller;

import com.mes.aone.service.MaterialService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
public class inventoryController {

    private final MaterialService materialService;

    @GetMapping(value="/inventory1")
    public String inventoryPage1(Model model){
        model.addAttribute("MaterialStorage", materialService.getMaterialStorage());

        model.addAttribute("Material", materialService.getMaterial());

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
