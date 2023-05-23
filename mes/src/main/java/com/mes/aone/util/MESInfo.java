package com.mes.aone.util;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
   int collagen = 0; // 콜라겐
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
   LocalDateTime examination= null; //검사 완료시간
   LocalDateTime cooling= null; //열교환 완료시간
   LocalDateTime packaging = null; // 포장 완료시간


   // 설비별 마지막 공정시간
   LocalDateTime pastPreProcessingMachine = LocalDateTime.of(2023,5,19,6,0,0);
   LocalDateTime pastExtractionMachine1 = LocalDateTime.of(2023,5,23,6,0,0);
   LocalDateTime pastExtractionMachine2 = LocalDateTime.of(2023,5,23,7,0,0);
   LocalDateTime pastFillingLiquidMachine = LocalDateTime.of(2023,5,19,6,0,0);
   LocalDateTime pastJellyMachine = LocalDateTime.of(2023,5,19,6,0,0);

   // 현재 설비별 공정 계획
   List<LocalDateTime> nowPreProcessingMachine = new ArrayList<>(); // 전처리
   List<LocalDateTime> nowExtractionMachine1 = new ArrayList<>(); // 추출기1
   List<LocalDateTime> nowExtractionMachine2 = new ArrayList<>(); // 추출기2
   List<LocalDateTime> nowFillingLiquidMachine = new ArrayList<>(); // 충진기(즙)
   List<LocalDateTime> nowFillingJellyMachine = new ArrayList<>(); // 충진기(젤리)

   // 현재 공정별 공정 계획
   List<LocalDateTime> nowPreProcessing = new ArrayList<>();
   List<LocalDateTime> nowExtraction = new ArrayList<>();
   List<LocalDateTime> nowFilling = new ArrayList<>();
   
   // 현재 공정별 생산량
   int nowMeasurementOutput = 0; // 원료계량
   List<Integer> nowPreProcessingOutput = new ArrayList<>(); // 전처리 생산량
   List<Integer> nowExtractionOutput = new ArrayList<>(); // 추출 생산량
   List<Integer> nowFillingOutput = new ArrayList<>(); // 충진 생산량



   //추출혼합 작업 완료량
   int measurementOutput = 0;
   int preProcessingOutput = 0;
   int extractionOutput=0;// 양배추 1ton기준 (추출액 : 1600L)
   //충진 완료량
   int fillOutPut=0;              // 충진 후 낱개
   int examinationOutput = 0;
   // 포장 후 set
   long packagingBoxOutput = 0;         // 포장 된 박스
   long packagingEaOutput = 0;           // 포장 후 남은 낱개




   public MESInfo() {

   }
}