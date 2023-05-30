package com.mes.aone.repository;


import com.mes.aone.constant.MaterialState;
import com.mes.aone.constant.StockManageState;
import com.mes.aone.dto.MaterialDTO;
import com.mes.aone.entity.Material;
import com.mes.aone.entity.MaterialStorage;
import com.mes.aone.entity.StockManage;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MaterialStorageRepository extends JpaRepository<MaterialStorage, Long> {

    @Query("SELECT new com.mes.aone.dto.MaterialDTO(m.materialName.materialName, SUM(CASE WHEN m.materialStorageState = 'I' THEN m.materialQty ELSE -m.materialQty END))" +
                "FROM MaterialStorage m " +
                "WHERE m.materialName.materialName IN ('양배추', '흑마늘', '석류농축액', '매실농축액', '콜라겐', '파우치', '스틱파우치', '박스') " +
                "GROUP BY m.materialName.materialName")
    List<MaterialDTO> getCurrentQuantitiesByMaterialNames();


    List<MaterialStorage> findAll();

    List<MaterialStorage> findByMaterialNameAndMaterialStorageStateAndMaterialStorageDateBetween(Material material, MaterialState materialState, LocalDateTime matStartDateTime, LocalDateTime matEndDate, Sort sort);

    List<MaterialStorage> findByMaterialNameAndMaterialStorageState(Material material, MaterialState materialState, Sort sort);

    List<MaterialStorage> findByMaterialNameAndMaterialStorageDateBetween(Material material, LocalDateTime matStartDateTime, LocalDateTime matEndDate, Sort sort);

    List<MaterialStorage> findByMaterialStorageStateAndMaterialStorageDateBetween(MaterialState materialState, LocalDateTime matStartDateTime, LocalDateTime matEndDate, Sort sort);

    List<MaterialStorage> findByMaterialName(Material material, Sort sort);

    List<MaterialStorage> findByMaterialStorageState (MaterialState materialState, Sort sort);

    List<MaterialStorage> findByMaterialStorageDateBetween (LocalDateTime matStartDateTime, LocalDateTime matEndDate, Sort sort);


}
