package com.mes.aone.dto;

import com.mes.aone.entity.ProcessPlan;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
public class ProductionDTO {


    private String productionName;  //제품명

    private int productionQty;  //생산수량

    private String processStage;   //공정단계

    private LocalDateTime endTime; //생산일자

    private String lotNumber;   //lot번호

    public ProductionDTO(String productionName, int productionQty, String processStage,
                         LocalDateTime endTime, String lotNumber){
        this.productionName=productionName;
        this.productionQty=productionQty;
        this.processStage=processStage;
        this.endTime=endTime;
        this.lotNumber=lotNumber;
    }
}
