package com.mes.aone.repository;


import com.mes.aone.service.MaterialService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MaterialStorageTest {

    @Autowired
    MaterialStorageRepository materialStorageRepository;
    @Autowired
    MaterialRepository materialRepository;
    @Autowired
    MaterialService materialService;

/*    @Test // 원자재 입출고 관리
    public void insertMaterialStorage(){
        MaterialStorage materialStorage = new MaterialStorage();

        Material material = materialRepository.findByMaterialName("석류액기스");
        materialStorage.setMaterialName(material);

        materialStorage.setMaterialQty(55);
        materialStorage.setMaterialStorageState(State.I);
        materialStorage.setMStorageDate(new Date());
        materialStorage.setUnit("kg");

        materialStorageRepository.save(materialStorage);

    }*/

    @Test // 원자재 입출고 현황
    public void selectMaterialStorage(){
        System.out.println("자재입출고" + materialService.getMaterialStorage().toString());
    }
}
