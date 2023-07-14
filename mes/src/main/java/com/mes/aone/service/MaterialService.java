package com.mes.aone.service;


import com.mes.aone.dto.MaterialDTO;
import com.mes.aone.entity.MaterialStorage;
import com.mes.aone.entity.PurchaseOrder;
import com.mes.aone.repository.MaterialRepository;
import com.mes.aone.repository.MaterialStorageRepository;
import com.mes.aone.repository.PurchaseOrderRepository;
import com.mes.aone.util.MESInfo;
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


    // 각 원자재의 재고량을 조회하여 materialQuantities 리스트에 추가
    public List<Integer> getMaterialStockQuantities() {
        List<Integer> materialQuantities = new ArrayList<>();
        materialQuantities.add(materialRepository.findMaterialByMaterialName("양배추").getMaterialQuantity());
        materialQuantities.add(materialRepository.findMaterialByMaterialName("흑마늘").getMaterialQuantity());
        materialQuantities.add(materialRepository.findMaterialByMaterialName("석류농축액").getMaterialQuantity());
        materialQuantities.add(materialRepository.findMaterialByMaterialName("매실농축액").getMaterialQuantity());
        materialQuantities.add(materialRepository.findMaterialByMaterialName("콜라겐").getMaterialQuantity());
        materialQuantities.add(materialRepository.findMaterialByMaterialName("박스").getMaterialQuantity());
        materialQuantities.add(materialRepository.findMaterialByMaterialName("파우치").getMaterialQuantity());
        materialQuantities.add(materialRepository.findMaterialByMaterialName("스틱파우치").getMaterialQuantity());
        for(Integer quantities : materialQuantities)
            System.out.println(quantities);

        return materialQuantities;
    }

    // 리스트에 추가된 원자재량 계산기에 세팅
    public void setMaterialStockQuantities(MESInfo mesInfo) {
        List<Integer> stockMaterialQuantities = getMaterialStockQuantities();
        mesInfo.setStockCabbage(stockMaterialQuantities.get(0));
        mesInfo.setStockGarlic(stockMaterialQuantities.get(1));
        mesInfo.setStockPomegranate(stockMaterialQuantities.get(2));
        mesInfo.setStockPlum(stockMaterialQuantities.get(3));
        mesInfo.setStockCollagen(stockMaterialQuantities.get(4));
        mesInfo.setStockBox(stockMaterialQuantities.get(5));
        mesInfo.setStockPouch(stockMaterialQuantities.get(6));
        mesInfo.setStockStickPouch(stockMaterialQuantities.get(7));
    }
}
