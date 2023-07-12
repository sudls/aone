package com.mes.aone.service;

import com.mes.aone.dto.StockDTO;
import com.mes.aone.dto.StockManageDTO;
import com.mes.aone.entity.Stock;
import com.mes.aone.repository.StockManageRepository;
import com.mes.aone.repository.StockRepository;
import org.springframework.boot.autoconfigure.gson.GsonProperties;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class StockService {
    private final StockRepository stockRepository;
    private final StockManageRepository stockManageRepository;

    public StockService(StockRepository stockRepository, StockManageRepository stockManageRepository) {
        this.stockRepository = stockRepository;
        this.stockManageRepository = stockManageRepository;
    }


/*    public List<StockDTO> getStock(){
        return stockManageRepository.getCurrentQuantitiesByStockName();
    }*/

    //초기값이 null인 경우 0을 반환
    public List<StockDTO> getStock() {
        List<StockDTO> stockDTOList = stockManageRepository.getCurrentQuantitiesByStockName();
        List<StockDTO> dummyStockDTOList = new ArrayList<>();

        List<String> stockNames = stockRepository.getAllStockNames(); // stock 테이블의 stockName 값들을 가져옴

        for (String stockName : stockNames) {
            Long currentStockQty = null;
            for (StockDTO stockDTO : stockDTOList) {
                if (stockDTO.getStockName().equals(stockName)) {
                    currentStockQty = stockDTO.getCurrentStockQty();
                    break;
                }
            }
            if (currentStockQty == null) {
                currentStockQty = 0L;
            }
            dummyStockDTOList.add(new StockDTO(stockName, currentStockQty));
        }

        return dummyStockDTOList;
    }



    @Transactional
    public List<StockDTO> getSumStock(){
        List<StockDTO> stockList;
        List<StockDTO>  stockDTOList = stockManageRepository.getCurrentQuantitiesByStockName();
        for (StockDTO stockDTO : stockDTOList) {
            Integer q = stockDTO.getCurrentStockQty().intValue();
            stockManageRepository.updateStock(stockDTO.getStockName(), q);
        }

        return stockDTOList;
    }

    // ----------여기 하는 중
    // 각 완제품 재고량을 조회하여 stockQuantities 리스트에 추가
    public List<Integer> getStockQuantities(){
        List<Integer> stockQuantities = new ArrayList<>();
        stockQuantities.add(stockRepository.findStockByStockName("양배추즙").getStockQty());
        stockQuantities.add(stockRepository.findStockByStockName("흑마늘즙").getStockQty());
        stockQuantities.add(stockRepository.findStockByStockName("석류젤리스틱").getStockQty());
        stockQuantities.add(stockRepository.findStockByStockName("매실젤리스틱").getStockQty());
        for(Integer quantities : stockQuantities)
            System.out.println(quantities);
        return stockQuantities;
    }



}
