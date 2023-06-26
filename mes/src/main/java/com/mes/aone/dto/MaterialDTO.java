package com.mes.aone.dto;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter
@Setter
public class MaterialDTO {

    private String materialName;
    private Long currentQuantity;

    public MaterialDTO(String materialName, Long currentQuantity){
        this.materialName = materialName;
        this.currentQuantity = currentQuantity != null? currentQuantity : 0L;
    }

    public Long getCurrentQuantity() {
        return currentQuantity;
    }

    public void setCurrentQuantity(Long currentQuantity) {
        this.currentQuantity = currentQuantity;
    }

}
