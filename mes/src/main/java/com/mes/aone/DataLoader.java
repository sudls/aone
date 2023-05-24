package com.mes.aone;

import com.mes.aone.entity.Vendor;
import com.mes.aone.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    private VendorRepository vendorRepository;

    @Override
    public void run(String... args) throws Exception {
        // 초기 데이터 로드 로직 작성

        //vendor 테이블 초기데이터
        Vendor[] vendors = {
                new Vendor("ven-11", "123456789-1234", "11번가 주소", "Vendor Memo 1", "11○○", "123-456-7890"),
                new Vendor("ven-cou", "123456789-4567", "쿠팡 주소", "Vendor Memo 2", "Cou○○", "987-654-3210"),
                new Vendor("ven-nh", "123456789-7894", "농협 주소", "Vendor Memo 3", "○○농협", "652-486-7890"),
                new Vendor("ven-a", "123456789-6512", "a 주소", "Vendor Memo 4", "에이농장", "302-965-8545"),
                new Vendor("ven-po", "123456789-3245", "po 주소", "Vendor Memo 5", "○○포장", "230-256-4856")
        };
        //insert
        for (Vendor vendor : vendors) {
            vendorRepository.save(vendor);
        }


    }

    @Autowired
    public DataLoader(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }


}
