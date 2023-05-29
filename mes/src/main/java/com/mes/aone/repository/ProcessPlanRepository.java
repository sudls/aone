package com.mes.aone.repository;



import com.mes.aone.entity.Facility;
import com.mes.aone.entity.ProcessPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface ProcessPlanRepository extends JpaRepository<ProcessPlan, Long> {

    List<ProcessPlan>  findAll();

    @Query("SELECT p FROM ProcessPlan p " +
            "WHERE (:currentTime BETWEEN p.startTime AND p.endTime " +
            "OR :currentTime > p.endTime AND :currentTime < p.startTime) " +
            "ORDER BY p.endTime DESC, p.startTime ASC")
    List<ProcessPlan> findProcessPlansByTimeCondition(Date currentTime);


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






}
