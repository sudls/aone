package com.mes.aone.dto;

import com.mes.aone.entity.Stock;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockDTO {

    private String stockName;
    private Long currentStockQty;
    private Integer stockQty;

    public StockDTO(String stockName, Long currentStockQty){
        this.stockName = stockName;
        this.currentStockQty = currentStockQty != null ? currentStockQty : 0L;
    }




}
