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
        stock.setStockName("양배추박스");
        stock.setStockQty(0);

        Stock savedStock = stockRepository.save(stock);
        System.out.println(savedStock.toString());
    }


//    @Test
//    @DisplayName("재고 수량조회테스트")
//    public void findByStockIdTest(){              // 상품명, 수량
//        Stock findStock = stockRepository.findByStockId(1);
//        System.out.println(findStock.toString());
//    }
}
