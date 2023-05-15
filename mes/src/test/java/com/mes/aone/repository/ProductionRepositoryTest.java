package com.mes.aone.repository;

import com.mes.aone.entity.Production;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class ProductionRepositoryTest {

    @Autowired
    ProductionRepository productionRepository;

    @Test
    @DisplayName("생산 정보 테스트")
    public void createProductionTest(){
        Production production = new Production();
        production.setProductionName("생산1");
        production.setProductionQty(100);
        production.setProcessStage(3);
        production.setLotNumber("1234");
    }

}