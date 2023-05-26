package com.mes.aone.repository;

import com.mes.aone.entity.ProcessPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProcessPlanRepository extends JpaRepository<ProcessPlan, Long> {




}
