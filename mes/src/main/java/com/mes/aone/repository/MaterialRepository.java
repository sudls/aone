package com.mes.aone.repository;


import com.mes.aone.entity.Material;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Long> {
    Material findByMaterialName(String materialName); //원자재 입출고 테이블이 참조

}
