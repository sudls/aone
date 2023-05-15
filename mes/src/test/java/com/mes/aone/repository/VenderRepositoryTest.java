package com.mes.aone.repository;

import com.mes.aone.entity.Vendor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class VenderRepositoryTest {
    @Autowired
    VenderRepository venderRepository;

    @Test
    @DisplayName("거래처 저장테스트")
    public void createVenderTest(){
        Vendor vender = new Vendor();
        vender.setVendorName("테스트 거래처");
        vender.setVendorTel("010-0000-0000");
        vender.setVendorAddr("테스트 주소");
        vender.setVendorNumber("xxx-xx-xxxxx");
        vender.setVendorMemo("테스트비고");
        Vendor savedVendor = venderRepository.save(vender);
        System.out.println(savedVendor.toString());
    }
}
