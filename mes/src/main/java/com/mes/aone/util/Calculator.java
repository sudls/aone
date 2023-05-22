package com.mes.aone.util;


import java.sql.SQLOutput;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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
            mesInfo.setStickPouch((int) Math.ceil( (double) (mesInfo.pomegranate * 200) / 1000) * 1000);

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
        LocalDateTime start = LocalDateTime.of(2023,5,19,15,0,0); // 원료계량 시작시간
        LocalDateTime end = start; // 원료계량 완료시간(변수) 선언;
        end = lunchAndLeaveTimeStartCheck(end); // 작업 시작 시 비근무 시간 체크(공정 시작시간 리턴)
        end = lunchAndLeaveTimeFinishCheck(end.plusMinutes(mesInfo.leadMeasurement), end); // 작업 완료 시 비근무 시간 체크(공정 시작시간 리턴)
        end = end.plusMinutes(mesInfo.leadMeasurement); // 원료계량 리드타임 더하기

        end = lunchAndLeaveTimeStartCheck(end); // 공정 시작 시 비근무 시간 체크(공정 시작시간 리턴)
        end = lunchAndLeaveTimeFinishCheck(end.plusMinutes(30), end);
        end = end.plusMinutes(30); // 원료계량 작업시간 더하기

        mesInfo.setMeasurement(end); // 원료계량 완료시간 set
        System.out.println("원료계량 시작시간: " + start + "\n원료계량 완료시간: " + end);
    }
    void preProcessing(){ // 전처리
        int workAmount = 0; // 작업량
        int realTimeOutput = 0; // 실시간 생산완료된 양
        if (mesInfo.productName.equals("양배추즙")){
            workAmount = mesInfo.cabbage;
        }else {
            workAmount = mesInfo.garlic;
        }

        LocalDateTime start = mesInfo.getMeasurement(); // 원료계량 완료시간을 전처리 시작시간으로 설정
        LocalDateTime end = start; // 전처리 완료시간(변수) 선언

        for(int i = 1; i <= Math.ceil((double) workAmount/1000); i++){ // 작업 반복횟수 만큼 실행 ex) 2500kg 이면 3번 반복
            end = lunchAndLeaveTimeStartCheck(end); // 작업 시작 시 비근무 시간 체크(공정 시작시간 리턴)
            end = lunchAndLeaveTimeFinishCheck(end.plusMinutes(mesInfo.leadPreProcessing), end); // 작업 완료 시 비근무 시간 체크(공정 시작시간 리턴)
            end = end.plusMinutes(mesInfo.leadPreProcessing); // 전처리 리드타임 더하기

            if (workAmount - realTimeOutput < 1000){ // 생산량이 1000kg 미만일 때 (전처리 마지막 작업 시)
                end = lunchAndLeaveTimeStartCheck(end); // 작업 시작 시 비근무 시간 체크(공정 시작시간 리턴)
                end = end.plusMinutes((int) Math.ceil(workAmount%1000/1000.0*60));// 전처리 작업시간 더하기
            }else {
                end = lunchAndLeaveTimeStartCheck(end); // 작업 시작 시 비근무 시간 체크(공정 시작시간 리턴)
                end = end.plusHours(1); // 전처리 작업시간 더하기
                realTimeOutput = realTimeOutput + 1000; // 실시간 생산량 추가
            }
        }

        mesInfo.setPreProcessing(end); // 전처리 완료시간 set
        System.out.println("전처리 시작시간: " + start + "\n전처리 완료시간: " + end);

    }

    void extraction(){ // 추출 및 혼합
        int workLoad = 0; // 작업량
        if (mesInfo.productName.equals("양배추즙")){
            workLoad = mesInfo.cabbage;
        } else if (mesInfo.productName.equals("흑마늘즙")){
            workLoad = mesInfo.garlic;
        } else if (mesInfo.productName.equals("석류젤리스틱")){
            workLoad = mesInfo.pomegranate;
        } else {
            workLoad = mesInfo.plum;
        }

        LocalDateTime start = null;
        LocalDateTime end = null;

        // 제품별 공정 시간 계산
        if (mesInfo.productName.equals("양배추즙")){
            start = mesInfo.getPreProcessing(); // 전처리 완료시간을 추출 시작시간으로 설정
            end = start; // 추출 완료시간(변수) 선언

            end = lunchAndLeaveTimeStartCheck(end); // 작업 시작 시 비근무 시간 체크(공정 시작시간 리턴)
            end = lunchAndLeaveTimeFinishCheck(end.plusMinutes(mesInfo.leadExtraction), end); // 작업 완료 시 비근무 시간 체크(공정 시작시간 리턴)
            end = end.plusMinutes(mesInfo.leadExtraction); // 추출 리드타임 더하기

            end = lunchAndLeaveTimeStartCheck(end); // 작업 시작 시 비근무 시간 체크(공정 시작시간 리턴)
            end = end.plusHours(72); // 추출시간 + 가열시간 더하기
        }else if (mesInfo.productName.equals("흑마늘즙")){
            start = mesInfo.getPreProcessing(); // 전처리 완료시간을 추출 시작시간으로 설정
            end = start; // 추출 완료시간(변수) 선언

            end = lunchAndLeaveTimeStartCheck(end);
            end = lunchAndLeaveTimeFinishCheck(end.plusMinutes(mesInfo.leadExtraction), end);
            end = end.plusMinutes(mesInfo.leadExtraction); // 추출 리드타임 더하기

            end = lunchAndLeaveTimeStartCheck(end);
            end = end.plusHours(24).plusSeconds(3456/10*workLoad); // 추출시간 + 가열시간 더하기
        }else { // 젤리스틱
            start = mesInfo.getMeasurement(); // 원료계량 완료시간을 추출 시작시간으로 설정
            end = start; // 추출 완료시간(변수) 선언

            end = lunchAndLeaveTimeStartCheck(end);
            end = lunchAndLeaveTimeFinishCheck(end.plusMinutes(mesInfo.leadMixing), end);
            end = end.plusMinutes(mesInfo.leadMixing); // 혼합 리드타임 더하기

            end = lunchAndLeaveTimeStartCheck(end);
            end = end.plusHours(8); // 가열시간 더하기
        }

        mesInfo.setExtraction(end); // 추출 및 혼합 완료시간 set

        System.out.println("추출 및 혼합 시작시간: " + start + "\n추출 및 혼합 완료시간: " + end);
    }








    LocalDateTime lunchAndLeaveTimeStartCheck(LocalDateTime startTime){ // 공정 시작 시 점심, 퇴근 시간 체크 메서드

        if (startTime.getHour() == 12){ // 점심시간 이면
            startTime = startTime.withHour(13).withMinute(0).withSecond(0);
            return startTime; // 점심 끝나는 시간 리턴
        }
        if(!(9<=startTime.getHour() && startTime.getHour()<=17)){ // 퇴근시간 이면(근무시간이 아니면)
            if(startTime.getDayOfWeek() == DayOfWeek.FRIDAY){ // 금요일 이면
                startTime = startTime.plusDays(3).withHour(9).withMinute(0).withSecond(0);
                return startTime; // 월요일 근무 시작시간 리턴
            }else {
                startTime = startTime.plusDays(1).withHour(9);
                return startTime; // 다음날 근무 시작시간 리턴
            }
        }
        return startTime; // 근무 시간 안걸리면 그대로 리턴
    }



    LocalDateTime lunchAndLeaveTimeFinishCheck(LocalDateTime finishTime, LocalDateTime startTime){ // 공정 완료 시 점심, 퇴근 시간 체크 메서드

        if (finishTime.getHour() == 12){ // 점심시간 이면
            startTime = finishTime.withHour(13).withMinute(0).withSecond(0);
            return startTime; // 점심 끝나는 시간 리턴
        }
        if(!(9<=finishTime.getHour() && finishTime.getHour()<=17)){ // 퇴근시간 이면(근무시간이 아니면)
            if(finishTime.getDayOfWeek() == DayOfWeek.FRIDAY){ // 금요일 이면
                startTime = finishTime.plusDays(3).withHour(9);
                return startTime; // 월요일 근무 시작시간 리턴
            }else {
                startTime = finishTime.plusDays(1).withHour(9);
                return startTime; // 다음날 근무 시작시간 리턴
            }
        }
        return startTime; // 근무 시간 안걸리면 그대로 리턴
    }




}
