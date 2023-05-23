package com.mes.aone.repository;

import com.mes.aone.constant.StockManageState;
import com.mes.aone.dto.StockDTO;
import com.mes.aone.entity.Stock;
import com.mes.aone.entity.StockManage;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StockManageRepository extends JpaRepository<StockManage, Long> {
  
     List<StockManage> findAll();
    
    // 제품명, 입출고상태, 기간
    List<StockManage> findByStockManageNameAndStockManageStateAndStockDateBetween(String stockManageName, StockManageState stockManageState, LocalDateTime stockStartDateTime, LocalDateTime stockEndDateTime, Sort sort);
    // 제품명, 입출고상태
    List<StockManage> findByStockManageNameAndStockManageState(String stockManageName, StockManageState stockManageState, Sort sort);
    // 제품명, 기간
    List<StockManage> findByStockManageNameAndStockDateBetween(String stockManageName, LocalDateTime stockStartDateTime, LocalDateTime stockEndDateTime, Sort sort);
    // 입출고상태, 기간
    List<StockManage> findByStockManageStateAndStockDateBetween(StockManageState stockManageState, LocalDateTime stockStartDateTime, LocalDateTime stockEndDateTime, Sort sort);
    // 제품명
    List<StockManage> findByStockManageName(String stockManageName, Sort sort);
    // 입출고상태
    List<StockManage> findByStockManageState(StockManageState stockManageState, Sort sort);
    // 기간
    List<StockManage> findByStockDateBetween(LocalDateTime stockStartDateTime, LocalDateTime stockEndDateTime, Sort sort);
  

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

