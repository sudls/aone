package com.mes.aone.repository;

import com.mes.aone.dto.ProductionDTO;
import com.mes.aone.entity.Production;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductionRepository extends JpaRepository<Production, Long>, QuerydslPredicateExecutor<Production> {

    //생산현황조회
    @Query("SELECT New com.mes.aone.dto.ProductionDTO(p.productionName, p.productionQty, pp.processStage, pp.endTime, p.lotNumber)"+
    "FROM Production p JOIN p.processPlan pp" + " ORDER BY p.productionId DESC")
    List<ProductionDTO> findProductionDetials();





}
