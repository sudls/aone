package com.mes.aone.service;

import com.mes.aone.entity.Stock;
import com.mes.aone.repository.StockRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class StockService {
    private final StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public List<Stock> getStockByName(String stockName) {
        return stockRepository.findByStockName(stockName);
    }



}
