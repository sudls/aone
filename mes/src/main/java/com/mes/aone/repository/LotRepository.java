package com.mes.aone.repository;

import com.mes.aone.entity.Lot;
import com.mes.aone.entity.Production;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LotRepository extends JpaRepository<Lot, Long> {
}
