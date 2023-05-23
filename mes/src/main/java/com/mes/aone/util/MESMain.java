package com.mes.aone.util;



import java.time.LocalDateTime;
import java.util.Scanner;

public class MESMain {
    public static void main(String[] args) {
        MESInfo mesInfo = new MESInfo();
        Calculator calculator = new Calculator(mesInfo);

        String productName = "흑마늘즙"; // 제품명 입력
        int salesAmount = 5000; // 제품량 입력

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

        System.out.println("전처리 공정계획 리스트: " + mesInfo.nowPreProcessingMachine);
        System.out.println("전처리 생산량 리스트: " + mesInfo.nowPreProcessingOutput);
        System.out.println("추출기1 공정계획 리스트: " + mesInfo.nowExtractionMachine1);
        System.out.println("추출기2 공정계획 리스트: " + mesInfo.nowExtractionMachine2);
        System.out.println("추출 및 혼합 공정계획 리스트: " + mesInfo.nowExtraction);
        System.out.println("추출 및 혼합 생산량 리스트: " + mesInfo.nowExtractionOutput);


    }

}
