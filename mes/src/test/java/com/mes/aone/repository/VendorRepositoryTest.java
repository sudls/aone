package com.mes.aone.repository;

import com.mes.aone.entity.Vendor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class VendorRepositoryTest {
    @Autowired
    VendorRepository vendorRepository;

    @Test
    @DisplayName("거래처 저장테스트")
    public void createVenderTest(){
        Vendor vender = new Vendor();
        vender.setVendorName("테스트 거래처");
        vender.setVendorTel("010-0000-0000");
        vender.setVendorAddr("테스트 주소");
        vender.setVendorNumber("xxx-xx-xxxxx");
        vender.setVendorMemo("테스트비고");
        Vendor savedVendor = vendorRepository.save(vender);
        System.out.println(savedVendor.toString());
    }

//    @Test
//    @DisplayName("거래처 아이디 조회테스트")
//    public void findByVendorIdTest(){
//
//    }


}
