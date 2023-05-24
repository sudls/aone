package com.mes.aone;

import com.mes.aone.entity.BOM;
import com.mes.aone.entity.Material;
import com.mes.aone.entity.Vendor;
import com.mes.aone.repository.BOMRepository;
import com.mes.aone.repository.MaterialRepository;
import com.mes.aone.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;

@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    private VendorRepository vendorRepository;
    @Autowired
    private MaterialRepository materialRepository;
    @Autowired
    private BOMRepository bomRepository;


    @Override
    public void run(String... args) throws Exception {
        // 초기 데이터 로드 로직 작성

        // Vendor 생성
        Vendor vendor1 = new Vendor("ven-11", "123456789-1234", "11번가 주소", "Vendor Memo 1", "11○○", "123-456-7890");
        Vendor vendor2 = new Vendor("ven-cou", "123456789-4567", "쿠팡 주소", "Vendor Memo 2", "Cou○○", "987-654-3210");
        Vendor vendor3 = new Vendor("ven-nh", "123456789-7894", "농협 주소", "Vendor Memo 3", "○○농협", "652-486-7890");
        Vendor vendor4 = new Vendor("ven-a", "123456789-6512", "a 주소", "Vendor Memo 4", "에이농장", "302-965-8545");
        Vendor vendor5 = new Vendor("ven-po", "123456789-3245", "po 주소", "Vendor Memo 5", "○○포장", "230-256-4856");

        vendorRepository.saveAll(Arrays.asList(vendor1, vendor2, vendor3, vendor4, vendor5));

        // Material 생성
        Material material1 = new Material("양배추", 2, vendor4);
        Material material2 = new Material("흑마늘", 2, vendor4);
        Material material3 = new Material("석류농축액", 3, vendor3);
        Material material4 = new Material("매실농축액", 3, vendor3);
        Material material5 = new Material("콜라겐", 3, vendor3);
        Material material6 = new Material("파우치", 2, vendor5);
        Material material7 = new Material("스틱파우치", 2, vendor5);
        Material material8 = new Material("포장BOX", 2, vendor5);

        materialRepository.saveAll(Arrays.asList(material1, material2, material3, material4, material5, material6, material7, material8));

        //BOM 생성
        BOM bom1 = new BOM("양배추즙", "양배추",40,"정제수",40,"",0);
        BOM bom2 = new BOM("흑마늘즙", "흑마늘추출액",20,"정제수",60,"",0);
        BOM bom3 = new BOM("석류젤리스틱", "석류농축액",5,"콜라겐",2,"정제수",8);
        BOM bom4 = new BOM("매실젤리스틱", "매실농축액",5,"콜라겐",2,"정제수",8);

        bomRepository.saveAll(Arrays.asList(bom1,bom2,bom3,bom4));




    }



}
