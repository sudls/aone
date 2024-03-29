package com.mes.aone.controller;

import com.mes.aone.dto.ProductionDTO;
import com.mes.aone.entity.Lot;
import com.mes.aone.entity.Production;
import com.mes.aone.service.LotService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class lotController {
    private final LotService lotService;

    @GetMapping(value="/lot")
    public String inventoryPage1(Model model){
        model.addAttribute("finalLotList", lotService.getFinalLotList());
        return "pages/lotPage";
    }

    @GetMapping(value="/lot/search")
    public String productionPage( @RequestParam(required = false) String lotNum,
                                  Model model){
        model.addAttribute("lotList", lotService.getLot(lotNum));
        return "pages/lotPage";
    }

    @PostMapping(value = "/lot")
    @ResponseBody
    public List<Lot> lotList(@RequestParam(required = false) String lotNum) {
        return lotService.getLot(lotNum);
    }
}
