package com.mes.aone.repository;


import com.mes.aone.entity.MaterialStorage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialStorageRepository extends JpaRepository<MaterialStorage, Long> {



}
