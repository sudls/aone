package com.mes.aone.service;

import com.mes.aone.constant.StockManageState;
import com.mes.aone.entity.Stock;
import com.mes.aone.entity.StockManage;
import com.mes.aone.repository.StockManageRepository;
import com.mes.aone.repository.StockRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    // 완제품 입출고 다중 검색
    public List<StockManage> searchStockManage(String searchProduct, StockManageState searchState, LocalDateTime stockStartDateTime, LocalDateTime stockEndDateTime, Sort sort) {

        if (searchProduct != null && !searchProduct.isEmpty() && searchState != null && stockStartDateTime != null && stockEndDateTime != null) {
            // 제품명, 입출고상태, 기간
            return stockManageRepository.findByStockManageNameAndStockManageStateAndStockDateBetween(searchProduct, searchState, stockStartDateTime, stockEndDateTime, sort);
        } else if (searchProduct != null && !searchProduct.isEmpty() && searchState != null) {
            // 제품명, 입출고상태
            return stockManageRepository.findByStockManageNameAndStockManageState(searchProduct, searchState, sort);
        } else if (searchProduct != null && !searchProduct.isEmpty() && stockStartDateTime != null && stockEndDateTime != null) {
            // 제품명, 기간
            return stockManageRepository.findByStockManageNameAndStockDateBetween(searchProduct, stockStartDateTime, stockEndDateTime, sort);
        } else if (searchState != null && stockStartDateTime != null && stockEndDateTime != null) {
            // 입출고상태, 기간
            return stockManageRepository.findByStockManageStateAndStockDateBetween(searchState, stockStartDateTime, stockEndDateTime, sort);
        } else if (searchProduct != null && !searchProduct.isEmpty()) {
            // 제품명
            return stockManageRepository.findByStockManageName(searchProduct, sort);
        } else if (searchState != null) {
            // 입출고상태
            return stockManageRepository.findByStockManageState(searchState, sort);
        } else if (stockStartDateTime != null && stockEndDateTime != null) {
            // 기간
            return stockManageRepository.findByStockDateBetween(stockStartDateTime, stockEndDateTime, sort);
        } else {
            // 모든 검색 조건이 제공되지 않은 경우
            return stockManageRepository.findAll(Sort.by(Sort.Direction.DESC, "purchaseOrderId"));
        }

    }
}
