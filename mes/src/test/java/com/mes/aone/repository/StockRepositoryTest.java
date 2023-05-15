package com.mes.aone.repository;

import com.mes.aone.entity.Stock;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class StockRepositoryTest{
    @Autowired
    StockRepository stockRepository;

    @Test
    @DisplayName("재고 저장테스트")
    public void createStockTest(){
        Stock stock = new Stock();
        stock.setStockName("테스트 재고");
        stock.setStockQty(6);

        Stock savedStock = stockRepository.save(stock);
        System.out.println(savedStock.toString());
    }

}
