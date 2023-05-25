package com.mes.aone.repository;

import com.mes.aone.entity.Vendor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
//@TestPropertySource(locations = "classpath:application-test.properties")
class VendorRepositoryTest {
    @Autowired
    VendorRepository vendorRepository;

//    @Test
//    @DisplayName("거래처 저장테스트")
//    public void createVendorTest(){
//        Vendor vendor = new Vendor();
//        vendor.setVendorId("거래처아이디");
//        vendor.setVendorName("테스트 거래처");
//        vendor.setVendorTel("010-0000-0000");
//        vendor.setVendorAddr("테스트 주소");
//        vendor.setVendorNumber("xxx-xx-xxxxx");
//        vendor.setVendorMemo("테스트비고");
//        Vendor savedVendor = vendorRepository.save(vendor);
//        System.out.println(savedVendor.toString());
//    }

//    @Test
//    @DisplayName("거래처 아이디 조회테스트")
//    public void findByVendorIdTest(){
//
//    }


}
