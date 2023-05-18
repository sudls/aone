package com.mes.aone.util;


public class MESMain {
    public static void main(String[] args) {
        MESInfo mesInfo = new MESInfo();
        Calculator calculator = new Calculator();
        mesInfo.setSalesQty(384);
        mesInfo.setProductName("양배추");
        calculator.purChaseAmount(mesInfo);



        // 다른 작업 수행

        // 결과 출력
        System.out.println("양배추: " + mesInfo.cabbage + " 박스: " + mesInfo.box + " 파우치: " + mesInfo.pouch);
    }

}
