package com.mes.aone.service;

import com.mes.aone.dto.StockDTO;
import com.mes.aone.dto.StockManageDTO;
import com.mes.aone.entity.Stock;
import com.mes.aone.repository.StockManageRepository;
import com.mes.aone.repository.StockRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class StockService {
    private final StockRepository stockRepository;
    private final StockManageRepository stockManageRepository;

    public StockService(StockRepository stockRepository, StockManageRepository stockManageRepository) {
        this.stockRepository = stockRepository;
        this.stockManageRepository = stockManageRepository;
    }


    public List<StockDTO> getStock(){
        return stockManageRepository.getCurrentQuantitiesByStockName();
    }


    @Transactional
    public List<StockDTO> getSumStock(){
        List<StockDTO>  stockDTOList = stockManageRepository.getCurrentQuantitiesByStockName();
        for (StockDTO stockDTO : stockDTOList) {
            Integer q = stockDTO.getCurrentStockQty().intValue();
            stockManageRepository.updateStock(stockDTO.getStockName(), q);

        }

        return stockDTOList;
    }



}
