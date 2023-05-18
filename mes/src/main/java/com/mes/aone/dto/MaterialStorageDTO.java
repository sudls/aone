package com.mes.aone.dto;

import com.mes.aone.constant.StockManageState;
import com.mes.aone.entity.Stock;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter@Setter
public class MaterialStorageDTO {
    private Long materialStorageId; //id
    private String materialName; //자재명
    private Integer materialQty; //수량
    private String materialUnit; // 단위
    private StockManageState mStorageState; //입출고 상태
    private LocalDateTime mStorageDate; //입출고 날짜
}
