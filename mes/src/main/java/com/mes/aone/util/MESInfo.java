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
    String productName = ""; // 제품명
    int salesQty = 0; // 수주량(박스)



    //원자재 발주량
    int cabbage = 0; // 양배추즙
    int garlic = 0; // 흑마늘즙
    int pomegranate = 0; // 석류젤리스틱
    int plum = 0; // 매실젤리스틱
    int Collagen = 0; // 콜라겐
    int box = 0; // 박스
    int pouch = 0; // 파우치(즙)
    int stickPouch = 0; // 파우치(스틱)

    //리드 타임
    int leadMeasurement = 20; // 원료계랑 리드타임
    int leadPreProcessing = 20; // 전처리 리드타임
    int leadExtraction = 60; // 추출 리드타임
    int leadMixing = 20; // 혼합 리드타임
    int leadFill = 20; // 충진 리드타임
    int leadExamination = 10; //검사 리드타임
    int leadPackaging = 20; // 포장 리드타임

    // 공정 완료 시간
    LocalDateTime measurement = null; // 원료계량 완료시간
    LocalDateTime preProcessing = null; // 전처리 완료시간
    LocalDateTime extraction = null; // 추출 완료시간
    LocalDateTime mixing = null; // 혼합 완료시간
    LocalDateTime fill = null; // 충진 완료시간
    LocalDateTime examination=null; //검사 완료시간
    LocalDateTime cooling=null; //열교환 완료시간
    LocalDateTime packaging = null; // 포장 완료시간


    //추출혼합 작업 완료량
    int extractionOutput=1600;// 양배추 1ton기준 (추출액 : 1600L)
   //충진 완료량
    int fillOutPut= 20000;//양배추 1ton기준 (20000포 충진)








   public MESInfo() {

   }
}
