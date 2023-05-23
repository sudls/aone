package com.mes.aone.dto;

import com.mes.aone.constant.StockManageState;
import com.mes.aone.entity.Stock;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
public class StockManageDTO {

    private Long stockManageId;

    // 입출고날짜
    private LocalDateTime stockDate;

    // 수량
    private Integer stockManageQty;

    // 입출고상태
    private StockManageState stockManageState;

    // 제품명 : 양배추즙, 석류젤리스틱
    private String stockManageName;
}
