package com.mes.aone.util;


import java.sql.SQLOutput;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Calculator {
    private MESInfo mesInfo;

    public Calculator(MESInfo mesInfo) {
        this.mesInfo = mesInfo;
    }

    void purChaseAmount(){ // 발주량 계산
        if(mesInfo.getProductName().equals("양배추즙")){
            mesInfo.setCabbage(Math.ceil( (mesInfo.salesQty*30/20) / 1000 ) * 1000);
            mesInfo.setBox(Math.ceil((mesInfo.cabbage * 20 / 30) / 500) * 500);
            mesInfo.setPouch(Math.ceil((mesInfo.cabbage * 20) / 1000) * 1000);

            System.out.println("양배추: " + mesInfo.cabbage + " 박스: " + mesInfo.box + " 파우치: " + mesInfo.pouch);

        }else if (mesInfo.getProductName().equals("흑마늘즙")){
            mesInfo.setGarlic(Math.ceil( (mesInfo.salesQty*30/120) / 10 ) * 10);
            mesInfo.setBox(Math.ceil((mesInfo.garlic * 120 / 30) / 500) * 500);
            mesInfo.setPouch(Math.ceil((mesInfo.garlic * 120) / 1000) * 1000);

            System.out.println("흑마늘: " + mesInfo.garlic + " 박스: " + mesInfo.box + " 파우치: " + mesInfo.pouch);

        }else if(mesInfo.getProductName().equals("석류젤리스틱")){
            mesInfo.setPomegranate(Math.ceil( (mesInfo.salesQty * 25/200) / 5 ) * 5);
            mesInfo.setCollagen(Math.ceil((mesInfo.pomegranate * 2 / 5) / 5) * 5);
            mesInfo.setBox(Math.ceil((mesInfo.pomegranate * 200 / 25) / 500) * 500);
            mesInfo.setPouch(Math.ceil((mesInfo.pomegranate * 200) / 1000) * 1000);

            System.out.println("석류액기스: " + mesInfo.pomegranate + " 콜라겐: " + mesInfo.Collagen + " 박스: " + mesInfo.box + " 스틱파우치: " + mesInfo.stickPouch);

        }else {
            mesInfo.setPlum(Math.ceil( (mesInfo.salesQty * 25/200) / 5 ) * 5);
            mesInfo.setCollagen(Math.ceil((mesInfo.plum * 2 / 5) / 5) * 5);
            mesInfo.setBox(Math.ceil((mesInfo.plum * 200 / 25) / 500) * 500);
            mesInfo.setStickPouch(Math.ceil((mesInfo.plum * 200) / 1000) * 1000);

            System.out.println("매실액기스: " + mesInfo.plum + " 콜라겐: " + mesInfo.Collagen + " 박스: " + mesInfo.box + " 스틱파우치: " + mesInfo.stickPouch);

        }
    }

    void materialArrived(){ // 발주 원자재 도착 시간

    }


    void measurement(){ // 원료계량
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = start.plusMinutes(50);

        mesInfo.setMeasurement(end);
        System.out.println("원료계량 시작시간: " + start + "\n원료계량 완료시간: " + end);
    }
    void preProcessing(){ // 전처리
        LocalDateTime start = mesInfo.getMeasurement().plusMinutes(mesInfo.leadPreProcessing);
        LocalDateTime end = start.plusMinutes(60);
        System.out.println("전처리 시작시간: " + start + "\n전처리 완료시간: " + end);
        System.out.println("양배추 허허: " + mesInfo.cabbage);

    }



}
