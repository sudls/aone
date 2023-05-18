package com.mes.aone.util;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    //리드 타임
    int leadMeasurement = 20;
    int leadPreProcessing = 20;
    int leadExtract = 60;
    int leadMixing = 20;
    int leadFill = 20;
    int leadPackaging = 20;

    // 공정 완료 시간
    LocalDateTime measurement = null;









   public MESInfo() {

   }
}
