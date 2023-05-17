package com.mes.aone.repository;


import com.mes.aone.contant.State;
import com.mes.aone.entity.Material;
import com.mes.aone.entity.MaterialStorage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
public class MaterialStorageTest {

    @Autowired
    MaterialStorageRepository materialStorageRepository;
    @Autowired
    MaterialRepository materialRepository;

    @Test
    public void insertMaterialStorage(){
        MaterialStorage materialStorage = new MaterialStorage();

        Material material = materialRepository.findByMaterialName("석류액기스");
        materialStorage.setMaterialName(material);

        materialStorage.setMaterialQty(50);
        materialStorage.setMStorageState(State.I);
        materialStorage.setMStorageDate(new Date());

        materialStorageRepository.save(materialStorage);

    }

}
