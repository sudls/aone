package com.mes.aone.repository;



import com.mes.aone.entity.ProcessPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface ProcessPlanRepository extends JpaRepository<ProcessPlan, Long> {

    List<ProcessPlan>  findAll();

//    @Query("SELECT p FROM ProcessPlan p " +
//            "WHERE (:currentTime BETWEEN p.startTime AND p.endTime " +
//            "OR :currentTime > p.endTime AND :currentTime < p.startTime) " +
//            "ORDER BY p.endTime DESC, p.startTime ASC")
//    List<ProcessPlan> findProcessPlansByTimeCondition(LocalDateTime currentTime);

    @Query("SELECT p FROM ProcessPlan p " +
            "WHERE :currentTime BETWEEN p.startTime AND p.endTime " +
            "ORDER BY p.endTime DESC, p.startTime ASC")
    List<ProcessPlan> findProcessPlansByTimeCondition(LocalDateTime currentTime);

    List<ProcessPlan> findByStartTimeLessThanEqualAndEndTimeGreaterThanEqualAndWorkOrder_WorkOrderDate(
            LocalDateTime startTime, LocalDateTime endTime, LocalDateTime orderDate);

//    @Query("SELECT pp FROM ProcessPlan pp " +
//            "JOIN pp.workOrder wo " +
//            "JOIN wo.salesOrder so " +
//            "WHERE :currentTime BETWEEN pp.startTime AND pp.endTime " +
//            "AND DATE(:orderDate) = DATE(so.salesDate)")
@Query("SELECT pp FROM ProcessPlan pp " +
        "JOIN pp.workOrder wo " +
        "JOIN wo.salesOrder so " +
        "WHERE (:currentTime BETWEEN pp.startTime AND pp.endTime " +
        "OR (pp.processStage = '원료계량' AND pp.startTime > :currentTime AND DATE(:orderDate) = DATE(so.salesDate)) " +
        "OR (pp.processStage = '포장' AND pp.endTime < :currentTime AND DATE(:orderDate) = DATE(so.salesDate))) " +
        "AND DATE(:orderDate) = DATE(so.salesDate)")
    List<ProcessPlan> findByCurrentTimeAndSalesDate(@Param("currentTime") LocalDateTime currentTime, @Param("orderDate") LocalDateTime orderDate);



}
