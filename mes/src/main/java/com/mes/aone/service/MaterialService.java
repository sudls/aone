package com.mes.aone.service;


import com.mes.aone.dto.MaterialDTO;
import com.mes.aone.entity.MaterialStorage;
import com.mes.aone.entity.PurchaseOrder;
import com.mes.aone.repository.MaterialRepository;
import com.mes.aone.repository.MaterialStorageRepository;
import com.mes.aone.repository.PurchaseOrderRepository;
import com.mes.aone.repository.VendorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MaterialService {

    private final MaterialRepository materialRepository;
    private final MaterialStorageRepository materialStorageRepository;
    private final VendorRepository vendorRepository;
    private final PurchaseOrderRepository purchaseOrderRepository;

    public List<MaterialStorage> getMaterialStorage(){
        return materialStorageRepository.findAll();
    }

    public List<MaterialDTO> getMaterial(){
        return materialStorageRepository.getCurrentQuantitiesByMaterialNames();
    }

    public List<PurchaseOrder> getPurchaseOrder(){
        return purchaseOrderRepository.findAll();
    }



}
