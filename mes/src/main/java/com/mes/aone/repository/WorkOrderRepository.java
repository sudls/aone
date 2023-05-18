package com.mes.aone.repository;

import com.mes.aone.entity.WorkOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface WorkOrderRepository extends JpaRepository<WorkOrder, Long> {
}
