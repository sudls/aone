package com.mes.aone.repository;


import com.mes.aone.entity.Material;
import com.mes.aone.entity.Vendor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MaterialTest {

    @Autowired
    MaterialRepository materialRepository;
    @Autowired
    VendorRepository vendorRepository;


/*    @Test // 자재 기준정보 더미(인설트)
    public void insertMaterial(){
        Material material = new Material();

        material.setMaterialName("양배추");
        material.setMaterialLeadtime(2);

        Vendor vendor = vendorRepository.findByVendorId("ven-a");
        material.setVendorId(vendor);

        materialRepository.save(material);
        //----------------------------------------------------
        Material material1 = new Material();

        material1.setMaterialName("콜라겐");
        material1.setMaterialLeadtime(3);

        vendor = vendorRepository.findByVendorId("ven-nh");
        material1.setVendorId(vendor);

        materialRepository.save(material1);
        //----------------------------------------------------
        Material material2 = new Material();

        material2.setMaterialName("흑마늘");
        material2.setMaterialLeadtime(2);

        vendor = vendorRepository.findByVendorId("ven-a");
        material2.setVendorId(vendor);

        materialRepository.save(material2);
        //----------------------------------------------------
        Material material3 = new Material();

        material3.setMaterialName("파우치");
        material3.setMaterialLeadtime(2);

        vendor = vendorRepository.findByVendorId("ven-po");
        material3.setVendorId(vendor);

        materialRepository.save(material3);
        //----------------------------------------------------
        Material material4 = new Material();

        material4.setMaterialName("석류액기스");
        material4.setMaterialLeadtime(3);

        vendor = vendorRepository.findByVendorId("ven-nh");
        material4.setVendorId(vendor);

        materialRepository.save(material4);
        //----------------------------------------------------
        Material material5 = new Material();

        material5.setMaterialName("스틱파우치");
        material5.setMaterialLeadtime(2);

        vendor = vendorRepository.findByVendorId("ven-po");
        material5.setVendorId(vendor);

        materialRepository.save(material5);
        //----------------------------------------------------
        Material material6 = new Material();

        material6.setMaterialName("매실액기스");
        material6.setMaterialLeadtime(3);

        vendor = vendorRepository.findByVendorId("nh");
        material6.setVendorId(vendor);

        materialRepository.save(material6);
        //----------------------------------------------------
        Material material7 = new Material();

        material7.setMaterialName("박스");
        material7.setMaterialLeadtime(2);

        vendor = vendorRepository.findByVendorId("ven-po");
        material7.setVendorId(vendor);

        materialRepository.save(material7);


    }*/

}

