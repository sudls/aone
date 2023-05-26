package com.mes.aone.repository;

import com.mes.aone.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

    Stock findByStockName(String stockName);

    //제품명 불러오는 쿼리(초기값이 null일때)
    @Query("SELECT s.stockName FROM Stock s")
    List<String> getAllStockNames();
}

