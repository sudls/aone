package com.mes.aone.repository;


import com.mes.aone.entity.Facility;
import com.mes.aone.entity.ProcessPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ProcessPlanRepository extends JpaRepository<ProcessPlan, Long> {

    List<ProcessPlan>  findAll();

    //main페이지의 수주 진척도 조회
    @Query("SELECT pp FROM ProcessPlan pp " +
            "JOIN pp.workOrder wo " +
            "JOIN wo.salesOrder so " +
            "WHERE DATE(so.salesDate) = DATE(:orderDate) " +
            "AND (" +
            "(pp.processStage = '원료계량' AND pp.startTime > :currentTime) " +
            "OR (pp.processStage = '포장' AND pp.endTime < :currentTime) " +
            "OR (:currentTime BETWEEN pp.startTime AND pp.endTime)" +
            ")")
    List<ProcessPlan> findByCurrentTimeAndSalesDate(@Param("currentTime") LocalDateTime currentTime, @Param("orderDate") LocalDateTime orderDate);

    //현황관리페이지의 수주 진척도 조회
    @Query("SELECT pp FROM ProcessPlan pp " +
            "JOIN pp.workOrder wo " +
            "JOIN wo.salesOrder so " +
            "WHERE (" +
            "(pp.processStage = '원료계량' AND pp.startTime > :currentTime) " +
            "OR (pp.processStage = '포장' AND pp.endTime < :currentTime) " +
            "OR (:currentTime BETWEEN pp.startTime AND pp.endTime)" +
            ")")
    List<ProcessPlan> findByCurrentTimeAndSalesDate2(@Param("currentTime") LocalDateTime currentTime);


//    @Query("SELECT p FROM ProcessPlan p " +
//            "WHERE :currentTime BETWEEN p.startTime AND p.endTime " +
//            "ORDER BY p.endTime DESC, p.startTime ASC")
//    List<ProcessPlan> findProcessPlansByTimeCondition(LocalDateTime currentTime);

    List<ProcessPlan> findByStartTimeBeforeAndEndTimeAfter(LocalDateTime startTime, LocalDateTime endTime);

    // 공정별 마지막 공정시간
    @Query("SELECT p FROM ProcessPlan p " +
            "WHERE p.processStage = :processStage " +
            "ORDER BY p.endTime DESC")
    List<ProcessPlan> findProcessPlanByProcessStage(@Param("processStage") String processStage);

    // 설비별 마지막 공정시간
    @Query("SELECT p FROM ProcessPlan p " +
            "WHERE p.facilityId = :facilityId " +
            "ORDER BY p.endTime DESC")
    List<ProcessPlan> findProcessPlanByFacilityId(@Param("facilityId") Facility facilityId);



    @Query("SELECT MAX(pp.endTime) FROM ProcessPlan pp " +
            "JOIN pp.workOrder wo JOIN wo.salesOrder so " +
            "WHERE so.salesOrderId = :salesOrderId AND pp.processStage = '포장'")
    LocalDateTime getShipmentDate(@Param("salesOrderId") Long salesOrderId);

}