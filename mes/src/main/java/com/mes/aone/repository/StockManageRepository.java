package com.mes.aone.repository;

import com.mes.aone.entity.StockManage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockManageRepository extends JpaRepository<StockManage, Long> {
}
