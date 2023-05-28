package com.mes.aone.repository;

import com.mes.aone.dto.ProductionDTO;
import com.mes.aone.entity.Production;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductionRepository extends JpaRepository<Production, Long> {

    //생산현황조회
    @Query("SELECT New com.mes.aone.dto.ProductionDTO(p.productionName, p.productionQty, pp.processStage, pp.endTime, p.lotNumber)"+
    "FROM Production p JOIN p.processPlan pp")
    List<ProductionDTO> findProductionDetials();


//    List<Production> findAll();


}
