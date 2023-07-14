package com.mes.aone.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Getter
@Setter
public class MESInfo {
//  private MaterialService materialService;

   //수주 정보
   String productName = ""; // 제품명
   int salesQty = 0; // 수주량(박스)
   LocalDateTime salesDay = LocalDateTime.now(); // 수주일
   Long salesOrderId = null; // 수주 id
   Long workOrderId = null; // 작업지시 id


  // 창고 원자재량(--)
    int stockCabbage;       // 양배추
    int stockGarlic;         // 흑마늘
    int stockPomegranate;    // 석류농축액
    int stockPlum;           // 매실농축액
    int stockCollagen; // 콜라겐
    int stockBox; // 박스
    int stockPouch; // 파우치(즙)
    int stockStickPouch; // 파우치(스틱)

//    public void setMaterialStockQuantities() {
//     List<Integer> stockMaterialQuantities = materialService.getMaterialStockQuantities();
//     int stockCabbage = stockMaterialQuantities.get(0);
//     int stockGarlic = stockMaterialQuantities.get(1);
//     int stockPomegranate = stockMaterialQuantities.get(2);
//     int stockPlum = stockMaterialQuantities.get(3);
//     int stockCollagen = stockMaterialQuantities.get(4);
//     int stockBox = stockMaterialQuantities.get(5);ca
//     int stockPouch = stockMaterialQuantities.get(6);
//     int stockStickPouch = stockMaterialQuantities.get(7);
//    }



 // 창고 재고량(--)
    int cabbagePackaging =0;    // 양배추즙 박스포장
    int garlicPackaging = 0;    // 흑마늘즙 박스포장
    int pomegranatePackaging = 0; // 석류젤리스틱 박스포장
    int plumPackaging = 0;       // 매실젤리스틱 박스포장

  // 생산해야 할 박스 수
  int makeBox;


   //원자재 발주량
   int cabbage = 0; // 양배추즙
   int garlic = 0; // 흑마늘즙
   int pomegranate = 0; // 석류젤리스틱
   int plum = 0; // 매실젤리스틱
   int collagen = 0; // 콜라겐
   int box = 0; // 박스
   int pouch = 0; // 파우치(즙)
   int stickPouch = 0; // 파우치(스틱)
   int shipmentBox = 0;             // 출하량
  
  
   // 원자재발주 후 output
    LocalDateTime stockOrderDate = null; // 원자재 주문시간
    String rowMaterialName = "";        // 발주 원자재 이름 - 입고 원자재 - 출고 원자재
    int rowMaterialAmount = 0;          // 발주량 - 입고량
    LocalDateTime arrivalMaterial = null;     // 도착시간 (원자재 창고 입고시간)
    LocalDateTime lastStockInDate = null;     // 가장 마지막 도착시간
    Map<String, LocalDateTime> purchaseAndTimeMap = null;
    Map<String, Integer> purchaseMap = null;
  


   //리드 타임
   int leadMeasurement = 20; // 원료계랑 리드타임
   int leadPreProcessing = 20; // 전처리 리드타임
   int leadExtraction = 60; // 추출 리드타임
   int leadMixing = 20; // 혼합 리드타임
   int leadFill = 20; // 충진 리드타임
   int leadExamination = 10; //검사 리드타임
   int leadPackaging = 20; // 포장 리드타임

   // 공정 완료 시간
//   LocalDateTime measurement = null; // 원료계량 완료시간
   LocalDateTime preProcessing = null; // 전처리 완료시간
   LocalDateTime extraction = null; // 추출 완료시간
   LocalDateTime mixing = null; // 혼합 완료시간
   LocalDateTime fill = null; // 충진 완료시간
   LocalDateTime examination= null; //검사 완료시간
   LocalDateTime cooling= null; //열교환 완료시간
   LocalDateTime packaging = null; // 포장 완료시간


   // 설비/공정별 마지막 공정시간
   LocalDateTime pastPreProcessingMachine = LocalDateTime.of(1,3,19,6,0,0);
   LocalDateTime pastExtractionMachine1 = LocalDateTime.of(1,3,23,6,0,0);
   LocalDateTime pastExtractionMachine2 = LocalDateTime.of(1,3,23,7,0,0);
   LocalDateTime pastFillingLiquidMachine = LocalDateTime.of(1,3,19,6,0,0);
   LocalDateTime pastFillingJellyMachine = LocalDateTime.of(1,3,19,6,0,0);
   LocalDateTime pastExaminationMachine = LocalDateTime.of(1,3,19,6,0,0);
   LocalDateTime pastPackagingTime = LocalDateTime.of(1,3,19,6,0,0);

   // 설비별 실시간 공정 계획(시작 시간)
   List<LocalDateTime> startExtractionMachine1 = new ArrayList<>(); // 추출기1
   List<LocalDateTime> startExtractionMachine2 = new ArrayList<>(); // 추출기2
   List<LocalDateTime> startFillingLiquidMachine = new ArrayList<>(); // 충진기(즙)
   List<LocalDateTime> startFillingJellyMachine = new ArrayList<>(); // 충진기(젤리)

   // 설비별 실시간 공정 계획(완료 시간)
   List<LocalDateTime> finishExtractionMachine1 = new ArrayList<>(); // 추출기1
   List<LocalDateTime> finishExtractionMachine2 = new ArrayList<>(); // 추출기2
   List<LocalDateTime> finishFillingLiquidMachine = new ArrayList<>(); // 충진기(즙)
   List<LocalDateTime> finishFillingJellyMachine = new ArrayList<>(); // 충진기(젤리)

   // 공정별 실시간 공정 계획(시작시간)
   LocalDateTime startMeasurement = null; // 원료계량 완료시간
   List<LocalDateTime> startPreProcessing = new ArrayList<>(); // 전처리
   List<LocalDateTime> startExtraction = new ArrayList<>(); // 추출 및 혼합
   List<LocalDateTime> startFilling = new ArrayList<>(); // 충진
   List<LocalDateTime> startExamination = new ArrayList<>(); // 검사
   List<LocalDateTime> startCooling = new ArrayList<>(); // 열교환
   List<LocalDateTime> startPackaging = new ArrayList<>(); // 포장

   // 공정별 실시간 공정 계획(완료시간)
   LocalDateTime finishMeasurement = null; // 원료계량 완료시간
   List<LocalDateTime> finishPreProcessing = new ArrayList<>(); // 전처리
   List<LocalDateTime> finishExtraction = new ArrayList<>(); // 추출 및 혼합
   List<LocalDateTime> finishFilling = new ArrayList<>(); // 충진
   List<LocalDateTime> finishExamination = new ArrayList<>(); // 검사
   List<LocalDateTime> finishCooling = new ArrayList<>(); // 열교환
   List<LocalDateTime> finishPackaging = new ArrayList<>(); // 포장



   // 설비별 실시간 생산량
   List<Integer> nowExtractionMachine1Output = new ArrayList<>(); // 추출기1
   List<Integer> nowExtractionMachine2Output = new ArrayList<>(); // 추출기2
   List<Integer> nowFillingLiquidMachineOutput = new ArrayList<>(); // 충진기(즙)
   List<Integer> nowFillingJellyMachineOutput = new ArrayList<>(); // 충진기(젤리)
   
   // 공정별 실시간 생산량
   int nowMeasurementOutput = 0; // 원료계량
   List<Integer> nowPreProcessingOutput = new ArrayList<>(); // 전처리 생산량
   List<Integer> nowExtractionOutput = new ArrayList<>(); // 추출 생산량
   List<Integer> nowFillingOutput = new ArrayList<>(); // 충진 생산량
   List<Integer> nowExaminationOutput = new ArrayList<>(); // 검사 생산량
   List<Integer> nowCoolingOutput = new ArrayList<>(); // 식힘 생산량
   List<Integer> nowPackagingOutput = new ArrayList<>(); // 포장 생산량


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

   LocalDateTime estDelivery = null; // 예상 납품일


/* Map<String, LocalDateTime> outputAndTimeMap = null;
 Map<String, Integer> outputMap = null;*/

 // 출고량(필요한 원자재량)
 int requiredCabbage = 0; // 양배추즙
 int requiredGarlic = 0; // 흑마늘즙
 int requiredPomegranate = 0; // 석류젤리스틱
 int requiredPlum = 0; // 매실젤리스틱
 int requiredCollagen = 0; // 콜라겐
 int requiredBox = 0; // 박스
 int requiredPouch = 0; // 파우치(즙)
 int requiredStickPouch = 0; // 파우치(스틱)

 Map<String, Integer> requiredMaterial = null;




   public MESInfo() {

   }
}