package com.mes.aone.controller;


import com.mes.aone.service.MaterialService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/material")
@Log4j2
@AllArgsConstructor
public class MaterialController {

/*    private final MaterialService materialService;

    @GetMapping("/test")
    public String test(Model model){
        model.addAttribute("MaterialStorage", materialService.getMaterialStorage());

        model.addAttribute("material", materialService.getMaterial());

        model.addAttribute("PurchaseOrder", materialService.getPurchaseOrder());

        return "/material/test";

    }*/

}
