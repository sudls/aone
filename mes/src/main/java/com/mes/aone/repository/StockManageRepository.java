package com.mes.aone.repository;

import com.mes.aone.constant.StockManageState;
import com.mes.aone.entity.StockManage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockManageRepository extends JpaRepository<StockManage, Long> {

    List<StockManage> findByStockManageName(String stockManageName);

    List<StockManage> findByStockManageNameContaining(String stockManageName);

    List<StockManage> findByStockManageState(StockManageState stockManageState);

    List<StockManage> findByStockManageNameContainingAndStockManageState(String stockManageName, StockManageState stockManageState);
}

