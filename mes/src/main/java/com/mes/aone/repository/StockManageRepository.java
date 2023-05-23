package com.mes.aone.repository;

import com.mes.aone.constant.StockManageState;
import com.mes.aone.dto.StockDTO;
import com.mes.aone.entity.Stock;
import com.mes.aone.entity.StockManage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockManageRepository extends JpaRepository<StockManage, Long> {

    List<StockManage> findAll();

    List<StockManage> findByStockManageName(String stockManageName);

    List<StockManage> findByStockManageNameContaining(String stockManageName);

    List<StockManage> findByStockManageState(StockManageState stockManageState);

    List<StockManage> findByStockManageNameContainingAndStockManageState(String stockManageName, StockManageState stockManageState);


    @Query("SELECT new com.mes.aone.dto.StockDTO(s.stockName, SUM(CASE WHEN sm.stockManageState = 'I' THEN sm.stockManageQty ELSE -sm.stockManageQty END)) " +
            "FROM StockManage sm " +
            "JOIN sm.stock s " +
            "GROUP BY s.stockName")
    List<StockDTO> getCurrentQuantitiesByStockName();

    @Modifying
    @Query("UPDATE Stock st " +
            "SET st.stockQty = :stockQty " +
            "WHERE st.stockName = :stockName " +
            "AND :stockQty IS NOT NULL")
    void updateStock(@Param("stockName") String stockName, @Param("stockQty") Integer stockQty);


}

