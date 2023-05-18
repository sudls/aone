package com.mes.aone.util;


import java.util.Scanner;

public class MESMain {
    public static void main(String[] args) {
        MESInfo mesInfo = new MESInfo();
        Calculator calculator = new Calculator(mesInfo);

//        Scanner scanner = new Scanner(System.in);
//
//        System.out.print("수주제품명을 입력하세요: ");
//        String productName = scanner.nextLine();
//        System.out.println("입력한 제품명: " + productName);
//
//        if(!productName.equals("양배추즙") & !productName.equals("흑마늘즙") & !productName.equals("석류젤리스틱") & !productName.equals("매실젤리스틱")){
//            System.out.println("제품명을 확인해주세요");
//            return;
//        }
//
//        System.out.print("수주량을 입력하세요: ");
//        double salesAmount = scanner.nextDouble();
//        System.out.println("입력한 수주량: " + salesAmount);
//        scanner.nextLine(); // 버퍼 비우기
//        scanner.close();
        String productName = "양배추즙";
        int salesAmount = 900;

        mesInfo.setProductName(productName); // 수주제품명
        mesInfo.setSalesQty(salesAmount); // 수주량

        System.out.println("원자재 발주량");
        calculator.purChaseAmount(); // 발주량 계산 메서드 실행

        calculator.measurement(); // 원료계량 메서드 실행
        
        calculator.preProcessing(); // 전처리 메서드 실행

    }

}
