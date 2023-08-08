package com.mes.aone.repository;

import com.mes.aone.entity.Lot;
import com.mes.aone.entity.Production;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LotRepository extends JpaRepository<Lot, Long> {

    // 해당 로트
    @Query("SELECT l FROM Lot l WHERE l.lotNum = :lotNum")
    Lot getBackwardLot(@Param("lotNum") String lotNum);

    @Query("SELECT l FROM Lot l WHERE l.parentLotNum = :lotNum")
    List<Lot> getForwardLot(@Param("lotNum") String lotNum);



}
