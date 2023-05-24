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
        System.out.println("-------------------공정 리스트--------------------------------------------------------");
        System.out.println("전처리 생산계획 리스트: " + mesInfo.nowPreProcessingMachine);
        System.out.println("전처리 생산량 리스트: " + mesInfo.nowPreProcessingOutput);
        System.out.println("추출기1 생산계획 리스트: " + mesInfo.nowExtractionMachine1);
        System.out.println("추출기2 생산계획 리스트: " + mesInfo.nowExtractionMachine2);
        System.out.println("추출 및 혼합 생산계획 리스트: " + mesInfo.nowExtraction);
        System.out.println("추출 및 혼합 생산량 리스트: " + mesInfo.nowExtractionOutput);
        System.out.println("충진 생산계획 리스트: " + mesInfo.nowFilling);
        System.out.println("충진 생산량 리스트: " + mesInfo.nowFillingOutput);
        System.out.println("검사 생산계획 리스트: " + mesInfo.nowExamination);
        System.out.println("검사 생산량 리스트: " + mesInfo.nowExaminationOutput);
        System.out.println("냉각 계획 리스트: " + mesInfo.nowCooling);
        System.out.println("냉각 생산량 리스트: " + mesInfo.nowCoolingOutput);
        System.out.println("포장 계획 리스트: " + mesInfo.nowPackaging);
        System.out.println("포장 생산량 리스트: " + mesInfo.nowPackagingOutput);


    }

}
