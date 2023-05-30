package com.mes.aone.repository;


import com.mes.aone.constant.MaterialState;
import com.mes.aone.dto.StockDTO;
import com.mes.aone.entity.Material;
import com.mes.aone.entity.MaterialStorage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaterialRepository extends JpaRepository<Material, Long> {
    Material findByMaterialName(String materialName); //원자재 입출고 테이블이 참조

    @Query("SELECT m.materialName FROM Material m")
    List<String> getAllMaterialNames();


    @Query("SELECT v.vendorId FROM Material m JOIN m.vendor v WHERE m.materialName = :materialName")
    String findVendorIdByMaterialName(@Param("materialName") String materialName);

}
