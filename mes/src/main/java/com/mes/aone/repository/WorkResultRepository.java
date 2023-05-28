package com.mes.aone.repository;

import com.mes.aone.dto.WorkResultDTO;
import com.mes.aone.entity.WorkResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface WorkResultRepository extends JpaRepository<WorkResult, Long> {

    //작업실적 조회
    @Query("SELECT NEW com.mes.aone.dto.WorkResultDTO(wr.workOrder.workOrderId, w.salesOrder.productName, wr.workFinishDate, wr.workFinishQty) " +
            "FROM WorkResult wr JOIN wr.workOrder w")
    List<WorkResultDTO> findWorkResultDetails();


    // 기간 검색
    List<WorkResult> findByWorkFinishDateBetween(LocalDateTime startDate, LocalDateTime endDate);


}
