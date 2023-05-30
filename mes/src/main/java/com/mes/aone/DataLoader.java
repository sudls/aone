package com.mes.aone;

import com.mes.aone.constant.Status;
import com.mes.aone.entity.*;
import com.mes.aone.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@ConditionalOnProperty(name = "spring.jpa.hibernate.ddl-auto", havingValue = "create")

public class DataLoader implements CommandLineRunner {
    @Autowired
    private VendorRepository vendorRepository;
    @Autowired
    private MaterialRepository materialRepository;
    @Autowired
    private BOMRepository bomRepository;
    @Autowired
    private FacilityRepository facilityRepository;

    @Autowired
    private StockRepository stockRepository;

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
        Material material8 = new Material("박스", 2, vendor5);

        materialRepository.saveAll(Arrays.asList(material1, material2, material3, material4, material5, material6, material7, material8));

        //BOM 생성
        BOM bom1 = new BOM("양배추즙", "양배추",40,"정제수",40,"",0);
        BOM bom2 = new BOM("흑마늘즙", "흑마늘추출액",20,"정제수",60,"",0);
        BOM bom3 = new BOM("석류젤리스틱", "석류농축액",5,"콜라겐",2,"정제수",8);
        BOM bom4 = new BOM("매실젤리스틱", "매실농축액",5,"콜라겐",2,"정제수",8);

        bomRepository.saveAll(Arrays.asList(bom1,bom2,bom3,bom4));

        //Facility생성
        Facility facility1 = new Facility("extraction_1","액상추출기1",2000,60, Status.N,"추출");
        Facility facility2 = new Facility("extraction_2","액상추출기2",2000,60, Status.N,"추출");
        //혼합저장탱크의 경우 용량상관없이 즙 24h, 스틱 8h으로 고정되어있음. 또한, 리드타임은 즙 0min, 스틱 20min
        Facility facility3 = new Facility("mix_1","혼합저장탱크1(즙)",24,0, Status.N,"혼합");
        Facility facility4 = new Facility("mix_2","혼합저장탱크2(즙)",24,0, Status.N,"혼합");
        Facility facility5 = new Facility("mix_3","혼합저장탱크1(스틱)",8,20, Status.N,"혼합");
        Facility facility6 = new Facility("mix_4","혼합저장탱크2(스틱)",8,20, Status.N,"혼합");

        Facility facility7 = new Facility("pouch_1","파우치포장1",1750,20, Status.N,"충진");
        Facility facility8 = new Facility("pouch_2","파우치포장2",1750,20, Status.N,"충진");
        Facility facility9 = new Facility("liquid_stick_1","액상스틱포장1",1500,20, Status.N,"충진");
        Facility facility10 = new Facility("liquid_stick_2","액상스틱포장2",1500,20, Status.N,"충진");
        //열교환기는 생산능력, 리드타임이 딱히 없음
        Facility facility11 = new Facility("cooling","",0,0, Status.N,"열교환");
        Facility facility12 = new Facility("inspection","금속검출기",5000,10, Status.N,"검사");

        Facility facility14 = new Facility("measurement","",0,20, Status.N,"원료계량");
        Facility facility13 = new Facility("preprocess","세척절단기",1000,20, Status.N,"전처리");
        Facility facility15 = new Facility("packaging","",200,20, Status.N,"박스포장");

        facilityRepository.saveAll(Arrays.asList(facility1,facility2,facility3,facility4,facility5,facility6,facility7,facility8,facility9,facility10,facility11,facility12));


        //완제품 재고 생성
        Stock stock1 = new Stock("양배추즙", 0);
        Stock stock2 = new Stock("흑마늘즙", 0);
        Stock stock3 = new Stock("석류젤리스틱", 0);
        Stock stock4 = new Stock("매실젤리스틱", 0);

        stockRepository.saveAll(Arrays.asList(stock1, stock2, stock3, stock4));


    }



}
