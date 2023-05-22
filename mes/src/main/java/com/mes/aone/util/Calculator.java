package com.mes.aone.util;


import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class Calculator {
    private MESInfo mesInfo;

    public Calculator(MESInfo mesInfo) {
        this.mesInfo = mesInfo;
    }

    void purChaseAmount(){ // 발주량 계산
        if(mesInfo.getProductName().equals("양배추즙")){
            mesInfo.setCabbage((int) (Math.ceil( (double) (mesInfo.salesQty * 30 / 20) / 1000 ) * 1000));
            mesInfo.setBox((int) (Math.ceil((double) (mesInfo.cabbage * 20 / 30) / 500) * 500));
            mesInfo.setPouch((int) (Math.ceil((double) (mesInfo.cabbage * 20) / 1000) * 1000));

            System.out.println("양배추: " + mesInfo.cabbage + " 박스: " + mesInfo.box + " 파우치: " + mesInfo.pouch);

        }else if (mesInfo.getProductName().equals("흑마늘즙")){
            mesInfo.setGarlic((int) Math.ceil( (double) (mesInfo.salesQty * 30 / 120) / 10 ) * 10);
            mesInfo.setBox((int) Math.ceil( (double) (mesInfo.garlic * 120 / 30) / 500) * 500);
            mesInfo.setPouch((int) Math.ceil( (double) (mesInfo.garlic * 120) / 1000) * 1000);

            System.out.println("흑마늘: " + mesInfo.garlic + " 박스: " + mesInfo.box + " 파우치: " + mesInfo.pouch);

        }else if(mesInfo.getProductName().equals("석류젤리스틱")){
            mesInfo.setPomegranate((int) Math.ceil( (double) (mesInfo.salesQty * 25/200) / 5 ) * 5);
            mesInfo.setCollagen((int) Math.ceil( (double) (mesInfo.pomegranate * 2 / 5) / 5) * 5);
            mesInfo.setBox((int) Math.ceil( (double) (mesInfo.pomegranate * 200 / 25) / 500) * 500);
            mesInfo.setPouch((int) Math.ceil( (double) (mesInfo.pomegranate * 200) / 1000) * 1000);

            System.out.println("석류액기스: " + mesInfo.pomegranate + " 콜라겐: " + mesInfo.Collagen + " 박스: " + mesInfo.box + " 스틱파우치: " + mesInfo.stickPouch);

        }else {
            mesInfo.setPlum((int) Math.ceil( (double) (mesInfo.salesQty * 25/200) / 5 ) * 5);
            mesInfo.setCollagen((int) Math.ceil( (double) (mesInfo.plum * 2 / 5) / 5) * 5);
            mesInfo.setBox((int) Math.ceil( (double) (mesInfo.plum * 200 / 25) / 500) * 500);
            mesInfo.setStickPouch((int) Math.ceil( (double) (mesInfo.plum * 200) / 1000) * 1000);

            System.out.println("매실액기스: " + mesInfo.plum + " 콜라겐: " + mesInfo.Collagen + " 박스: " + mesInfo.box + " 스틱파우치: " + mesInfo.stickPouch);

        }
    }

    void materialArrived(){ // 발주 원자재 도착 시간

    }


    void measurement(){ // 원료계량
        LocalDateTime start = LocalDateTime.of(2023,5,19,15,0,0);
        LocalDateTime end = start;
        end = lunchAndLeaveTimeStartCheck(end);
        end = lunchAndLeaveTimeFinishCheck(end.plusMinutes(mesInfo.leadMeasurement), end);
        end = end.plusMinutes(mesInfo.leadMeasurement); // 리드타임

        end = lunchAndLeaveTimeStartCheck(end);
        end = lunchAndLeaveTimeFinishCheck(end.plusMinutes(30), end);
        end = end.plusMinutes(30); // 작업시간

        mesInfo.setMeasurement(end);
        System.out.println("원료계량 시작시간: " + start + "\n원료계량 완료시간: " + end);
    }
    void preProcessing(){ // 전처리
        int output = 0;
        int realTimeOutput = 1000;
        if (mesInfo.productName.equals("양배추즙")){
            output = mesInfo.cabbage;
        }else {
            output = mesInfo.garlic;
        }

        LocalDateTime start = mesInfo.getMeasurement();
        LocalDateTime end = start;
        for(int i = 1; i <= Math.ceil((double) output/1000); i++){
            end = lunchAndLeaveTimeStartCheck(end);
            end = lunchAndLeaveTimeFinishCheck(end.plusMinutes(mesInfo.leadPreProcessing), end);
            end = end.plusMinutes(mesInfo.leadPreProcessing); // 리드타임
            if (output - realTimeOutput < 0){
                end = lunchAndLeaveTimeStartCheck(end);
                end = end.plusMinutes((int) Math.ceil(output%1000/1000.0*60));// 작업시간
            }else {
                end = lunchAndLeaveTimeStartCheck(end);
                end = end.plusHours(1); // 작업시간
                realTimeOutput = realTimeOutput + 1000;
            }
//            System.out.println("전처리 " + i + ": " + end);
        }

        mesInfo.setPreProcessing(end);
        System.out.println("전처리 시작시간: " + start + "\n전처리 완료시간: " + end);
    }



    // 포장
    void packaging(){


    }



    LocalDateTime lunchAndLeaveTimeStartCheck(LocalDateTime startTime){ //점심, 퇴근시간 체크(작업시작)

        if (startTime.getHour() == 12){ // 점심시간 이면
            startTime = startTime.withHour(13).withMinute(0).withSecond(0);
            return startTime;
        }
        if(!(9<=startTime.getHour() && startTime.getHour()<=17)){ // 퇴근시간 이면(근무시간이 아니면)
            if(startTime.getDayOfWeek() == DayOfWeek.FRIDAY){ // 금요일 이면
                startTime = startTime.plusDays(3).withHour(9).withMinute(0).withSecond(0);
                return startTime;
            }else {
                startTime = startTime.plusDays(1).withHour(9);
                return startTime;
            }
        }
        return startTime;
    }
    LocalDateTime lunchAndLeaveTimeFinishCheck(LocalDateTime finishTime, LocalDateTime startTime){ // 점심, 퇴근시간 체크(작업완료)

        if (finishTime.getHour() == 12){ // 점심시간 이면
            startTime = finishTime.withHour(13).withMinute(0).withSecond(0);
            return startTime;
        }
        if(!(9<=finishTime.getHour() && finishTime.getHour()<=17)){ // 퇴근시간 이면(근무시간이 아니면)
            if(finishTime.getDayOfWeek() == DayOfWeek.FRIDAY){ // 금요일 이면
                startTime = finishTime.plusDays(3).withHour(9);
                return startTime;
            }else {
                startTime = finishTime.plusDays(1).withHour(9);
                return startTime;
            }
        }
        return startTime;
    }






}
