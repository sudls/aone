package com.mes.aone.repository;


import com.mes.aone.contant.State;
import com.mes.aone.dto.MaterialDTO;
import com.mes.aone.entity.Material;
import com.mes.aone.entity.MaterialStorage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaterialStorageRepository extends JpaRepository<MaterialStorage, Long> {

    @Query("SELECT new com.mes.aone.dto.MaterialDTO(m.materialName.materialName, SUM(CASE WHEN m.materialStorageState = 'I' THEN m.materialQty ELSE -m.materialQty END))" +
                "FROM MaterialStorage m " +
                "WHERE m.materialName.materialName IN ('양배추', '흑마늘', '석류액기스', '매실액기스', '콜라겐', '파우치', '스틱파우치', '박스') " +
                "GROUP BY m.materialName.materialName")
    List<MaterialDTO> getCurrentQuantitiesByMaterialNames();



}
