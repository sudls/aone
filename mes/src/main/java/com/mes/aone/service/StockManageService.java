package com.mes.aone.service;

import com.mes.aone.constant.StockManageState;
import com.mes.aone.entity.Stock;
import com.mes.aone.entity.StockManage;
import com.mes.aone.repository.StockManageRepository;
import com.mes.aone.repository.StockRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class StockManageService {
    private final StockManageRepository stockManageRepository;
    private final StockRepository stockRepository;

    public StockManageService(StockManageRepository stockManageRepository, StockRepository stockRepository) {
        this.stockManageRepository = stockManageRepository;
        this.stockRepository = stockRepository;
    }

    //입출고내역 조회
    public List<StockManage> getAllStockManage() {
        return stockManageRepository.findAll();
    }


    //입출고수량 변경 -> 완제품 수량 업데이트
    @Transactional
    public void updateStockQuantity(StockManage stockManage) {
        Stock stock = stockManage.getStock();
        StockManageState stockManageState = stockManage.getStockManageState();
        int stockManageQty = stockManage.getStockManageQty();

        // stockManageState가 'I'인 경우, stockQty를 증가시킴
        if (stockManageState == StockManageState.I) {
            int updatedStockQty = stock.getStockQty() + stockManageQty;
            stock.setStockQty(updatedStockQty);
        }
        // stockManageState가 'O'인 경우, stockQty를 감소시킴
        else if (stockManageState == StockManageState.O) {
            int updatedStockQty = stock.getStockQty() - stockManageQty;
            // stockQty가 음수가 되지 않도록 처리
            if (updatedStockQty >= 0) {
                stock.setStockQty(updatedStockQty);
            } else {
                // 재고가 부족하여 출고할 수 없는 경우에 대한 예외 처리를 해줘야 함
                throw new IllegalStateException("재고가 부족하여 출고할 수 없습니다.");
            }
        }

        stock.getStockManageList().add(stockManage); // StockManage 엔티티를 Stock 엔티티의 stockManageList에 추가

        stockRepository.save(stock); // 변경된 Stock 엔티티 저장
    }

    public List<StockManage> getStockByProductName(String stockManageName) {
        // productName에 해당하는 stockmanageList를 조회하는 로직을 구현합니다.
        return stockManageRepository.findByStockManageName(stockManageName);
    }
}
