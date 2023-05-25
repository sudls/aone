package com.mes.aone.util;



import java.time.LocalDateTime;
import java.util.Scanner;

public class MESMain {
    public static void main(String[] args) {
        MESInfo mesInfo = new MESInfo();
        Calculator calculator = new Calculator(mesInfo);

        String productName = "양배추즙"; // 제품명 입력
        int salesAmount = 3000; // 제품량 입력

        mesInfo.setProductName(productName);
        mesInfo.setSalesQty(salesAmount);


        if (productName.equals("양배추즙") || productName.equals("흑마늘즙")){ // 즙 공정

            System.out.println("-------------------발주---------------------------------------------------------");
            calculator.purChaseAmount(); // 발주량 계산 메서드 실행
            System.out.println("-------------------원료계량------------------------------------------------------");
            calculator.measurement(); // 원료계량 메서드 실행
            System.out.println("-------------------전처리--------------------------------------------------------");
            calculator.preProcessing(); // 전처리 메서드 실행
            System.out.println("-------------------추출--------------------------------------------------------");
            calculator.extraction(); // 추출 메서드 실행
            System.out.println("-------------------충진--------------------------------------------------------");
            calculator.fill();//충진 메서드 실행
            System.out.println("-------------------검사--------------------------------------------------------");
            calculator.examination();//검사 메서드 실행
            System.out.println("-------------------열교환--------------------------------------------------------");
            calculator.cooling();//열교환 메서드 실행
            System.out.println("-------------------포장--------------------------------------------------------");
            calculator.packaging(); // 포장 메서드 실행



        }else { // 젤리스틱 공정
            System.out.println("-------------------발주-----------------------------------------------------------");
            calculator.purChaseAmount(); // 발주량 계산 메서드 실행
            System.out.println("-------------------원료계량--------------------------------------------------------");
            calculator.measurement(); // 원료계량 메서드 실행
            System.out.println("-------------------추출--------------------------------------------------------");
            calculator.extraction(); // 추출 메서드 실행
            System.out.println("-------------------충진--------------------------------------------------------");
            calculator.fill();//충진 메서드 실행
            System.out.println("-------------------검사--------------------------------------------------------");
            calculator.examination();//검사 메서드 실행
            System.out.println("-------------------열교환--------------------------------------------------------");
            calculator.cooling();//열교환 메서드 실행
            System.out.println("-------------------포장--------------------------------------------------------");
            calculator.packaging(); // 포장 메서드 실행


        }
        System.out.println("-------------------공정계획 리스트--------------------------------------------------------");
        System.out.println("원료계량 : " + mesInfo.nowMeasurementOutput);
        System.out.println("전처리 시작시간 리스트: " + mesInfo.startPreProcessing);
        System.out.println("전처리 완료시간 리스트: " + mesInfo.finishPreProcessing);
        System.out.println("전처리 생산량 리스트: " + mesInfo.nowPreProcessingOutput);
        System.out.println("추출 및 혼합 시작시간 리스트: " + mesInfo.startExtraction);
        System.out.println("추출 및 혼합 완료시간 리스트: " + mesInfo.finishExtraction);
        System.out.println("추출 및 혼합 생산량 리스트: " + mesInfo.nowExtractionOutput);
        System.out.println("충진 시작시간 리스트: " + mesInfo.startFilling);
        System.out.println("충진 완료시간 리스트: " + mesInfo.finishFilling);
        System.out.println("충진 생산량 리스트: " + mesInfo.nowFillingOutput);
        System.out.println("검사 시작시간 리스트: " + mesInfo.startExamination);
        System.out.println("검사 완료시간 리스트: " + mesInfo.finishExamination);
        System.out.println("검사 생산량 리스트: " + mesInfo.nowExaminationOutput);
        System.out.println("냉각 시작시간 리스트: " + mesInfo.startCooling);
        System.out.println("냉각 완료시간 리스트: " + mesInfo.finishCooling);
        System.out.println("냉각 생산량 리스트: " + mesInfo.nowCoolingOutput);
        System.out.println("포장 시작시간 리스트: " + mesInfo.startPackaging);
        System.out.println("포장 완료시간 리스트: " + mesInfo.finishPackaging);
        System.out.println("포장 생산량 리스트: " + mesInfo.nowPackagingOutput);

        System.out.println("-------------------설비별 공정계획 리스트--------------------------------------------------------");
        System.out.println("추출기1 시작시간 리스트: " + mesInfo.startExtractionMachine1);
        System.out.println("추출기1 완료시간 리스트: " + mesInfo.finishExtractionMachine1);
        System.out.println("추출기1 생산량 리스트: " + mesInfo.nowExtractionMachine1Output);
        System.out.println("추출기2 시작시간 리스트: " + mesInfo.startExtractionMachine2);
        System.out.println("추출기2 완료시간 리스트: " + mesInfo.finishExtractionMachine2);
        System.out.println("추출기2 생산량 리스트: " + mesInfo.nowExtractionMachine2Output);
        System.out.println("충진(즙) 시작시간 리스트: " + mesInfo.startFillingLiquidMachine);
        System.out.println("충진(즙) 완료시간 리스트: " + mesInfo.finishFillingLiquidMachine);
        System.out.println("충진(즙) 생산량 리스트: " + mesInfo.nowFillingLiquidMachineOutput);
        System.out.println("충진(젤리) 시작시간 리스트: " + mesInfo.startFillingJellyMachine);
        System.out.println("충진(젤리) 완료시간 리스트: " + mesInfo.finishFillingJellyMachine);
        System.out.println("충진(젤리) 생산량 리스트: " + mesInfo.nowFillingJellyMachineOutput);


    }

}
