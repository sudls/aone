package com.mes.aone.util;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
 public class MESInfo {

    //수주 정보
    String productName = "";
    double salesQty = 0;

    //원자재 발주량
    double cabbage = 0;
    double garlic = 0;
    double pomegranate = 0;
    double plum = 0;
    double Collagen = 0;
    double box = 0;
    double pouch = 0;
    double stickPouch = 0;

   public MESInfo() {

   }
}
