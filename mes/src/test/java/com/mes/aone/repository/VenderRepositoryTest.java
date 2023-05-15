package com.mes.aone.repository;

import com.mes.aone.entity.Vender;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestPropertySource;

@TestPropertySource(locations = "classpath:application-test.properties")
class VenderRepositoryTest {

    VenderRepository venderRepository;

    @Test
    @DisplayName("거래처 저장테스트")
    public void createVenderTest(){
        Vender vender = new Vender();
        vender.setVendorName("테스트 거래처");
        vender.setVendorTel("010-0000-0000");
        vender.setVendorAddr("테스트 주소");
        vender.setVendorNumber("xxx-xx-xxxxx");
        vender.setVendorMemo("테스트비고");
        Vender savedVender = venderRepository.save(vender);
        System.out.println(savedVender.toString());
    }
}
