package com.mes.aone.repository;



import com.mes.aone.entity.ProcessPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

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




}
