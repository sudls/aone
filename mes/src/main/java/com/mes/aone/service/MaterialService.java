package com.mes.aone.service;


import com.mes.aone.dto.MaterialDTO;
import com.mes.aone.entity.MaterialStorage;
import com.mes.aone.entity.PurchaseOrder;
import com.mes.aone.repository.MaterialRepository;
import com.mes.aone.repository.MaterialStorageRepository;
import com.mes.aone.repository.PurchaseOrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class MaterialService {

    private final MaterialStorageRepository materialStorageRepository;
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final MaterialRepository materialRepository;

    public List<MaterialStorage> getMaterialStorage(){
        return materialStorageRepository.findAll();
    }

    // 원자재 초기값 없을때 0으로 세팅
    public List<MaterialDTO> getMaterial(){
        List<MaterialDTO> materialDTOList = materialStorageRepository.getCurrentQuantitiesByMaterialNames();
        List<MaterialDTO> dummyMaterialDTOList = new ArrayList<>();

        List<String> materialNames = materialRepository.getAllMaterialNames();

        for (String materialName : materialNames){
            Long currentQuantity = null;
            for (MaterialDTO materialDTO : materialDTOList){
                if (materialDTO.getMaterialName().equals(materialName)){
                    currentQuantity = materialDTO.getCurrentQuantity();
                    break;
                }
            }
            if (currentQuantity == null){
                currentQuantity = 0L;
            }
            dummyMaterialDTOList.add(new MaterialDTO(materialName, currentQuantity));
        }
        return dummyMaterialDTOList;
    }

    public List<PurchaseOrder> getPurchaseOrder(){
        return purchaseOrderRepository.findAll();
    }

    // materialDTO 값 material 테이블에 저장
    public void getCurrentQuantitiesAndUpdate() {
        List<MaterialDTO> materialDTOList = materialStorageRepository.getCurrentQuantitiesByMaterialNames();

        for (MaterialDTO materialDTO : materialDTOList) {
            String materialName = materialDTO.getMaterialName();
            int currentQuantity = materialDTO.getCurrentQuantity().intValue();

            System.out.println("이름: " + materialName);
            System.out.println("수량: " + currentQuantity);

            materialRepository.updateMaterialQuantityByName(currentQuantity, materialName);


        }
    }

    public List<Integer> getMaterialStockQuantities() {
        System.out.println("1111111111111111111111");
        List<Integer> materialQuantities = new ArrayList<>();
        // 각 원자재의 재고량을 조회하여 stockQuantities 리스트에 추가
        materialQuantities.add(materialRepository.findMaterialQuantityByMaterialName("양배추"));
        materialQuantities.add(materialRepository.findMaterialQuantityByMaterialName("흑마늘"));
        materialQuantities.add(materialRepository.findMaterialQuantityByMaterialName("석류농축액"));
        materialQuantities.add(materialRepository.findMaterialQuantityByMaterialName("매실농축액"));
        materialQuantities.add(materialRepository.findMaterialQuantityByMaterialName("콜라겐"));
        materialQuantities.add(materialRepository.findMaterialQuantityByMaterialName("박스"));
        materialQuantities.add(materialRepository.findMaterialQuantityByMaterialName("파우치"));
        materialQuantities.add(materialRepository.findMaterialQuantityByMaterialName("스틱파우치"));
        System.out.println("222222222222222222222");
        for(Integer quantities : materialQuantities)
            System.out.println(quantities);

        return materialQuantities;
    }

}
