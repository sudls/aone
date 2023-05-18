package com.mes.aone.util;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Calculator {
    MESInfo mesInfo;

    public Calculator() {

    }

    void purChaseAmount(MESInfo mesInfo){ // 발주량 계산
        System.out.println("여기까지");
        System.out.println(mesInfo);
        mesInfo.setCabbage(Math.ceil( (mesInfo.salesQty*30/20) / 1000 ) * 1000);
        mesInfo.setBox(Math.ceil((mesInfo.cabbage * 20 / 30) / 500) * 500);
        mesInfo.setPouch(Math.ceil((mesInfo.cabbage * 20) / 1000) * 1000);


    }



}
