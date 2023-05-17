package com.mes.aone.dto;


import lombok.*;


@Getter
@Setter
public class MaterialDTO {

    private String materialName;
    private Long currentQuantity;

    public MaterialDTO(String materialName, Long currentQuantity){
        this.materialName = materialName;
        this.currentQuantity = currentQuantity;

    }



}
