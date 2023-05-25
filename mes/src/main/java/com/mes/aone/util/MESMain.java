package com.mes.aone.util;


import java.time.LocalDateTime;

public class MESMain {
    public static void main(String[] args) {
        MESInfo mesInfo = new MESInfo();
        Calculator calculator = new Calculator(mesInfo);

        String productName = "석류젤리스틱"; // 제품명 입력
        int salesAmount = 385; // 제품량 입력
        LocalDateTime salesDate = LocalDateTime.of(2023,5,23,15,46,45);


        mesInfo.setProductName(productName);
        mesInfo.setSalesQty(salesAmount);
        mesInfo.setSalesDate(salesDate);




        System.out.println("원자재 발주량");
        String purchaseCheck = "";
        if (productName.equals("양배추즙") || productName.equals("흑마늘즙")){ // 즙 공정
            purchaseCheck = calculator.purChaseAmount(); // 발주량 계산 메서드 실행
            if (purchaseCheck.equals("enough")){
                mesInfo.setEstDay(LocalDateTime.now());
            } else {
                calculator.materialArrived(); // 발주 원자재 도착시간 메서드 실행

//            calculator.measurement(); // 원료계량 메서드 실행
//            calculator.preProcessing(); // 전처리 메서드 실행
//            calculator.extraction(); // 추출 메서드 실행
//            calculator.fill();//충진 메서드 실행
//            calculator.examination();//검사 메서드 실행
//            calculator.cooling();//열교환 메서드 실행
//
//             calculator.packaging(); // 포장 메서드 실행
            }





        }else { // 젤리스틱 공정
            purchaseCheck = calculator.purChaseAmount(); // 발주량 계산 메서드 실행
            if (purchaseCheck.equals("enough")){
                mesInfo.setEstDay(LocalDateTime.now());
            } else {
                calculator.materialArrived(); // 발주 원자재 도착시간 메서드 실행

//            calculator.measurement(); // 원료계량 메서드 실행
//            calculator.extraction(); // 추출 메서드 실행
//            calculator.fill();//충진 메서드 실행
//            calculator.examination();//검사 메서드 실행
//            calculator.cooling();//열교환 메서드 실행
//
//             calculator.packaging(); // 포장 메서드 실행
            }

        }

        System.out.println("전처리 공정계획 리스트: " + mesInfo.nowPreProcessingMachine);
        System.out.println("추출기1 공정계획 리스트: " + mesInfo.nowExtractionMachine1);
        System.out.println("추출기2 공정계획 리스트: " + mesInfo.nowExtractionMachine2);

        System.out.println(mesInfo.estDay);


    }

}
