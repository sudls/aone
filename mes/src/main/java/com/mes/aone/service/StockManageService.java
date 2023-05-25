package com.mes.aone.service;

import com.mes.aone.dto.StockDTO;
import com.mes.aone.entity.StockManage;
import com.mes.aone.repository.StockManageRepository;
import com.mes.aone.repository.StockRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class StockManageService {
    private final StockManageRepository stockManageRepository;
    private final StockRepository stockRepository;


    public StockManageService(StockManageRepository stockManageRepository, StockRepository stockRepository) {
        this.stockManageRepository = stockManageRepository;
        this.stockRepository = stockRepository;
    }
    
}