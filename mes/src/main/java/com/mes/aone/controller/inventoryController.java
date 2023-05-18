package com.mes.aone.controller;

import com.mes.aone.constant.SalesStatus;
import com.mes.aone.constant.StockManageState;
import com.mes.aone.dto.MaterialStorageDTO;
import com.mes.aone.dto.MaterialStorageNumDTO;
import com.mes.aone.dto.OrderDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Controller
public class inventoryController {
    @GetMapping(value="/inventory1")
    public String inventoryPage1(Model model){
        //원자재 입출고 현황 (더미)
        List<MaterialStorageDTO> materialStorageDTOList = new ArrayList<>();

        String[] materialNames = {"양배추","콜라겐", "흑마늘", "벌꿀", "석류액기스", "매실액기스", "스틱파우치", "파우치","박스"};

        Random random = new Random();

        for (int i = 1; i <= 30; i++) {
            MaterialStorageDTO materialStorageDTO = new MaterialStorageDTO();
            materialStorageDTO.setMaterialName(materialNames[random.nextInt(materialNames.length)]);
            materialStorageDTO.setMStorageState(StockManageState.I);
            materialStorageDTO.setMaterialQty(random.nextInt(1000));
            materialStorageDTO.setMaterialUnit("kg");
            materialStorageDTO.setMStorageDate(LocalDateTime.now());
            materialStorageDTOList.add(materialStorageDTO);
        }
        model.addAttribute("materialStorageDTOList",materialStorageDTOList);

        //원자재 총 수량(더미)
        List<MaterialStorageNumDTO> materialStorageNumDTOList = new ArrayList<>();
        for (int i=0; i<9; i++){
            MaterialStorageNumDTO materialStorageNumDTO = new MaterialStorageNumDTO();
            materialStorageNumDTO.setMaterialName(materialNames[i]);
            materialStorageNumDTO.setMaterialQty(random.nextInt(1000));
            materialStorageNumDTOList.add(materialStorageNumDTO);
        }
        model.addAttribute("materialStorageNumDTOList",materialStorageNumDTOList);

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
