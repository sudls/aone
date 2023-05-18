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
    VendorRepository venderRepository;

    @Test
    @DisplayName("거래처 저장테스트")
    public void createVenderTest(){
        Vendor vendor = new Vendor();
        vendor.setVendorId("ven-po");
        vendor.setVendorName("포장");
        vendor.setVendorTel("010-0000-0004");
        vendor.setVendorAddr("포장 주소");
        vendor.setVendorNumber("xxx-xx-xxxxx");
        vendor.setVendorMemo("포장 비고");
        venderRepository.save(vendor);
    }
}
