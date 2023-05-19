package com.mes.aone.repository;

import com.mes.aone.constant.StockManageState;
import com.mes.aone.dto.MaterialStorageDTO;
import com.mes.aone.entity.Material;
import com.mes.aone.entity.MaterialStorage;
import com.mes.aone.entity.StockManage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaterialStorageRepository extends JpaRepository<MaterialStorage, Long> {

/*    List<MaterialStorage> findAll();

    List<MaterialStorage> findByMaterialNameAndMStorageState(String materialName, StockManageState mStorageState);

    List<MaterialStorage> findByMaterialName(String materialName);
    List<MaterialStorage> findByMStorageState(StockManageState mStorageState);*/
}
