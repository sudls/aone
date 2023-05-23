package com.mes.aone.repository;

import com.mes.aone.constant.StockManageState;
import com.mes.aone.entity.StockManage;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StockManageRepository extends JpaRepository<StockManage, Long> {

    List<StockManage> findByStockManageName(String stockManageName);

//    List<StockManage> findByStockManageNameContaining(String stockManageName);
//
//    List<StockManage> findByStockManageState(StockManageState stockManageState);
//
//    List<StockManage> findByStockManageNameContainingAndStockManageState(String stockManageName, StockManageState stockManageState);



    // 제품명, 입출고상태, 기간
    List<StockManage> findByStockManageNameAndStockManageStateAndStockDateBetween(String stockManageName, StockManageState stockManageState, LocalDateTime stockStartDateTime, LocalDateTime stockEndDateTime, Sort sort);
    // 제품명, 입출고상태
    List<StockManage> findByStockManageNameAndStockManageState(String stockManageName, StockManageState stockManageState, Sort sort);
    // 제품명, 기간
    List<StockManage> findByStockManageNameAndStockDateBetween(String stockManageName, LocalDateTime stockStartDateTime, LocalDateTime stockEndDateTim, Sort sort);
    // 입출고상태, 기간
    List<StockManage> findByStockManageStateAndStockDateBetween(StockManageState stockManageState, LocalDateTime stockStartDateTime, LocalDateTime stockEndDateTime, Sort sort);
    // 제품명
    List<StockManage> findByStockManageName(String stockManageName, Sort sort);
    // 입출고상태
    List<StockManage> findByStockManageState(StockManageState stockManageState, Sort sort);
    // 기간
    List<StockManage> findByStockDateBetween(LocalDateTime stockStartDateTime, LocalDateTime stockEndDateTime, Sort sort);
    // 전체
    List<StockManage> findAll();




}

