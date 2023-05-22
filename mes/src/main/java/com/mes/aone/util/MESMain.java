package com.mes.aone.util;


import java.util.Scanner;

public class MESMain {
    public static void main(String[] args) {
        MESInfo mesInfo = new MESInfo();
        Calculator calculator = new Calculator(mesInfo);

        String productName = "석류젤리스틱"; // 제품명 입력
        int salesAmount = 1500; // 제품량 입력

        mesInfo.setProductName(productName);
        mesInfo.setSalesQty(salesAmount);

        System.out.println("원자재 발주량");

        if (productName.equals("양배추즙") || productName.equals("흑마늘즙")){ // 즙 공정
            calculator.purChaseAmount(); // 발주량 계산 메서드 실행

            calculator.measurement(); // 원료계량 메서드 실행

            calculator.preProcessing(); // 전처리 메서드 실행

            calculator.extraction(); // 추출 메서드 실행
        }else { // 젤리스틱 공정
            calculator.purChaseAmount(); // 발주량 계산 메서드 실행

            calculator.measurement(); // 원료계량 메서드 실행

            calculator.extraction(); // 추출 메서드 실행
        }


    }

}
