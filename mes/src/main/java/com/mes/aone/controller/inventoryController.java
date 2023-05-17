package com.mes.aone.controller;

import com.mes.aone.constant.SalesStatus;
import com.mes.aone.constant.StockManageState;
import com.mes.aone.dto.MaterialStorageDTO;
import com.mes.aone.dto.OrderDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class inventoryController {
    @GetMapping(value="/inventory1")
    public String inventoryPage1(Model model){
        List<MaterialStorageDTO> materialStorageDTOList = new ArrayList<>();

        for(int i=1; i<=30; i++ ){
            MaterialStorageDTO materialStorageDTO = new MaterialStorageDTO();
//            materialStorageDTO.setMaterialStorageId(Long.valueOf(i));
            materialStorageDTO.setMaterialName("양배추"); // 자재명
            materialStorageDTO.setMStorageState(StockManageState.I);
            materialStorageDTO.setMaterialQty(1000);
            materialStorageDTO.setMaterialUnit("kg");
            materialStorageDTO.setMStorageDate(LocalDateTime.now());

            materialStorageDTOList.add(materialStorageDTO);
        }

        model.addAttribute("materialStorageDTOList",materialStorageDTOList);

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
