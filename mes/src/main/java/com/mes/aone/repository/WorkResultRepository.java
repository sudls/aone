package com.mes.aone.repository;

import com.mes.aone.entity.WorkResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface WorkResultRepository extends JpaRepository<WorkResult, Long> {



}
