package com.mes.aone.dto;

import com.mes.aone.constant.StockManageState;
import com.mes.aone.entity.Stock;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import lombok.ToString;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class StockManageDTO {

    private Long stockManageId;
    private LocalDateTime stockDate;            // 입출고날짜
    private int stockManageQty;                 // 수량
    private StockManageState stockManageState;  // 입출고상태
    private String stockManageName;             // 제품명 : 양배추박스, 양배추포
    private Stock stock;                        //stock 엔티티와 연결
}
