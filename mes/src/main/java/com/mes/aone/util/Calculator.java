package com.mes.aone.util;


import java.sql.SQLOutput;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
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
        LocalDateTime currentTime = LocalDateTime.of(2023,5,23,9,0,0); // 원료계량 시작시간
        currentTime = lunchAndLeaveTimeStartCheck(currentTime); // 작업 시작 시 비근무 시간 체크(작업 시작시간 리턴)
        currentTime = lunchAndLeaveTimeFinishCheck(currentTime.plusMinutes(mesInfo.leadMeasurement), currentTime); // 작업 완료 시 비근무 시간 체크(작업 시작시간 리턴)
        System.out.println("원료계량 시작시간: " + currentTime);
        currentTime = currentTime.plusMinutes(mesInfo.leadMeasurement); // 원료계량 리드타임 더하기

        currentTime = lunchAndLeaveTimeStartCheck(currentTime); // 작업 시작 시 비근무 시간 체크(공정 시작시간 리턴)
        currentTime = lunchAndLeaveTimeFinishCheck(currentTime.plusMinutes(30), currentTime); // 작업 시작 시 비근무 시간 체크(작업 시작시간 리턴)
        currentTime = currentTime.plusMinutes(30); // 원료계량 작업시간 더하기
        System.out.println("원료계량 완료시간: " + currentTime);

        mesInfo.setMeasurement(currentTime); // 원료계량 완료시간 set
    }
    void preProcessing(){ // 전처리
        LocalDateTime currentTime = mesInfo.getMeasurement(); // 원료계량 완료시간을 전처리 시작시간으로 설정


        int workAmount = 0; // 작업량
        int realTimeOutput = 0; // 실시간 생산완료된 양
        if (mesInfo.productName.equals("양배추즙")){
            workAmount = mesInfo.cabbage;
        }else {
            workAmount = mesInfo.garlic;
        }

        for(int i = 0; i < Math.ceil((double) workAmount/1000); i++){ // 작업 반복횟수 만큼 실행 ex) 2500kg 이면 3번 반복
            currentTime = lunchAndLeaveTimeStartCheck(currentTime); // 작업 시작 시 비근무 시간 체크(작업 시작시간 리턴)
            currentTime = lunchAndLeaveTimeFinishCheck(currentTime.plusMinutes(mesInfo.leadPreProcessing), currentTime); // 작업 완료 시 비근무 시간 체크(작업 시작시간 리턴)
            System.out.println("전처리 " + (i+1) + " 시작시간: " + currentTime);
            currentTime = currentTime.plusMinutes(mesInfo.leadPreProcessing); // 전처리 리드타임 더하기

            if (workAmount - realTimeOutput < 1000){ // 생산량이 1000kg 미만일 때 (전처리 마지막 작업 시)
                currentTime = lunchAndLeaveTimeStartCheck(currentTime); // 작업 시작 시 비근무 시간 체크(작업 시작시간 리턴)
                currentTime = currentTime.plusMinutes((int) Math.ceil(workAmount%1000/1000.0*60));// 전처리 작업시간 더하기
                System.out.println("전처리 " + (i+1) + " 완료시간: " + currentTime);
                mesInfo.nowPreProcessingMachine.add(currentTime);
            }else {
                currentTime = lunchAndLeaveTimeStartCheck(currentTime); // 작업 시작 시 비근무 시간 체크(작업 시작시간 리턴)
                currentTime = currentTime.plusHours(1); // 전처리 작업시간 더하기
                System.out.println("전처리 " + (i+1) + " 완료시간: " + currentTime);
                mesInfo.nowPreProcessingMachine.add(currentTime);
                realTimeOutput = realTimeOutput + 1000; // 실시간 생산량 추가
            }
        }

        mesInfo.setPreProcessing(currentTime); // 전처리 완료시간 set

    }

    void extraction(){ // 추출 및 혼합
        LocalDateTime currentTime = null; // 추출 시간 변수 선언

        int workAmount = 0; // 작업량
        if (mesInfo.productName.equals("양배추즙")){
            workAmount = mesInfo.cabbage;
        } else if (mesInfo.productName.equals("흑마늘즙")){
            workAmount = mesInfo.garlic;
        } else if (mesInfo.productName.equals("석류젤리스틱")){
            workAmount = mesInfo.pomegranate;
        } else {
            workAmount = mesInfo.plum;
        }

        if(mesInfo.productName.equals("양배추즙")){ // 양배추 추출 공정
            for (int i = 0; i < mesInfo.nowPreProcessingMachine.size(); i++){ // 전처리 작업 횟수 만큼 반복
                currentTime = mesInfo.nowPreProcessingMachine.get(i);
                if(mesInfo.pastExtractionMachine1.isBefore(mesInfo.pastExtractionMachine2)){ // 추출기1이 먼저 끝날 때
                    if (currentTime.isBefore(mesInfo.pastExtractionMachine1)) { // 추출기 1의 공정계획이 있으면
                        currentTime = mesInfo.pastExtractionMachine1; // 추출기 1의 공정계획이 끝나는 시간에 시작
                    }
                    currentTime = lunchAndLeaveTimeStartCheck(currentTime);
                    currentTime = lunchAndLeaveTimeFinishCheck(currentTime.plusMinutes(mesInfo.leadExtraction), currentTime);
                    System.out.println("추출기1 - 추출" + (i+1) + "번째 시작시간: " + currentTime);
                    currentTime = currentTime.plusMinutes(mesInfo.leadExtraction);

                    currentTime = lunchAndLeaveTimeStartCheck(currentTime);
                    currentTime = currentTime.plusHours(72);

                    mesInfo.nowExtractionMachine1.add(currentTime);
                    mesInfo.setPastExtractionMachine1(currentTime);

                    System.out.println("추출기1 - 추출" + (i+1) + "번째 완료시간: " + currentTime);

                }else { // 추출기2가 먼저 끝날 때
                    if (currentTime.isBefore(mesInfo.pastExtractionMachine2)) { // 추출기 1의 공정계획이 있으면
                        currentTime = mesInfo.pastExtractionMachine2; // 추출기 2의 공정계획이 끝나는 시간에 시작
                    }
                    currentTime = lunchAndLeaveTimeStartCheck(currentTime);
                    currentTime = lunchAndLeaveTimeFinishCheck(currentTime.plusMinutes(mesInfo.leadExtraction), currentTime);
                    System.out.println("추출기2 - 추출" + (i+1) + "번째 시작시간: " + currentTime);
                    currentTime = currentTime.plusMinutes(mesInfo.leadExtraction);

                    currentTime = lunchAndLeaveTimeStartCheck(currentTime);
                    currentTime = currentTime.plusHours(72);

                    mesInfo.nowExtractionMachine2.add(currentTime);
                    mesInfo.setPastExtractionMachine2(currentTime);

                    System.out.println("추출기2 - 추출" + (i+1) + "번째 완료시간: " + currentTime);
                }
            }
        } else if(mesInfo.productName.equals("흑마늘즙")){
            for (int i = 0 ; i < Math.ceil(mesInfo.garlic/500.0) ; i++){
                currentTime = mesInfo.nowPreProcessingMachine.get((int) Math.floor(i/2.0));
                if(mesInfo.pastExtractionMachine1.isBefore(mesInfo.pastExtractionMachine2)){ // 추출기1이 먼저 끝날 때
                    if (currentTime.isBefore(mesInfo.pastExtractionMachine1)) { // 추출기 1의 공정계획이 있으면
                        currentTime = mesInfo.pastExtractionMachine1; // 추출기 1의 공정계획이 끝나는 시간에 시작
                    }
                    currentTime = lunchAndLeaveTimeStartCheck(currentTime);
                    currentTime = lunchAndLeaveTimeFinishCheck(currentTime.plusMinutes(mesInfo.leadExtraction), currentTime);
                    System.out.println("추출기1 - 추출" + (i+1) + "번째 시작시간: " + currentTime);
                    currentTime = currentTime.plusMinutes(mesInfo.leadExtraction); // 추출 리드타임 더하기

                    if (i == Math.ceil(mesInfo.garlic/500.0)-1){ // 마지막 추출 공정시
                        if (workAmount % 500 == 0){ // 흑마늘이 500kg로 나누어 떨어지면
                            currentTime = currentTime.plusHours(72); // 추출시간 + 가열시간 더하기
                        }else{ // 흑마늘이 500kg 미만 일 시
                            currentTime = lunchAndLeaveTimeStartCheck(currentTime);
                            currentTime = currentTime.plusHours(24).plusSeconds(3456 / 10 * (workAmount%500)); // 추출시간 + 가열시간 더하기
                        }
                    }else {
                        currentTime = lunchAndLeaveTimeStartCheck(currentTime);
                        currentTime = currentTime.plusHours(72); // 추출시간 + 가열시간 더하기
                    }

                    mesInfo.nowExtractionMachine1.add(currentTime); // 추출기 1 공정계획 추가
                    mesInfo.setPastExtractionMachine1(currentTime); // 추출기 1의 마지막 공정시간 set
                    System.out.println("추출기1 - 추출" + (i+1) + "번째 완료시간: " + currentTime);

                }else { // 추출기2가 먼저 끝날 때
                    if (currentTime.isBefore(mesInfo.pastExtractionMachine2)) { // 추출기 2의 공정계획이 있으면
                        currentTime = mesInfo.pastExtractionMachine2; // 추출기 2의 공정계획이 끝나는 시간에 시작
                    }
                    currentTime = lunchAndLeaveTimeStartCheck(currentTime);
                    currentTime = lunchAndLeaveTimeFinishCheck(currentTime.plusMinutes(mesInfo.leadExtraction), currentTime);
                    System.out.println("추출기2 - 추출" + (i+1) + "번째 시작시간: " + currentTime);
                    currentTime = currentTime.plusMinutes(mesInfo.leadExtraction); // 추출 리드타임 더하기

                    if (i == Math.ceil(mesInfo.garlic/500.0)-1){
                        if (workAmount % 500 == 0){
                            currentTime = currentTime.plusHours(72); // 추출시간 + 가열시간 더하기
                        }else {
                            currentTime = lunchAndLeaveTimeStartCheck(currentTime);
                            currentTime = currentTime.plusHours(24).plusSeconds(3456 / 10 * (workAmount%500)); // 추출시간 + 가열시간 더하기
                        }
                    }else {
                        currentTime = lunchAndLeaveTimeStartCheck(currentTime);
                        currentTime = currentTime.plusHours(72); // 추출시간 + 가열시간 더하기
                    }

                    mesInfo.nowExtractionMachine2.add(currentTime); // 추출기2 공정계획 추가
                    mesInfo.setPastExtractionMachine2(currentTime); // 추출기2의 마지막 공정시간 set
                    System.out.println("추출기2 - 추출" + (i+1) + "번째 완료시간: " + currentTime);
                }
            }
        }  else {
            currentTime = mesInfo.getMeasurement(); // 원료계량 완료시간을 추출 시작시간으로 설정

            if(mesInfo.pastExtractionMachine1.isBefore(mesInfo.pastExtractionMachine2)){ // 추출기1이 먼저 끝날 때
                if (currentTime.isBefore(mesInfo.pastExtractionMachine1)) { // 추출기 1의 공정계획이 있으면
                    currentTime = mesInfo.pastExtractionMachine1; // 추출기 1의 공정계획이 끝나는 시간에 시작
                }
                currentTime = lunchAndLeaveTimeStartCheck(currentTime);
                currentTime = lunchAndLeaveTimeFinishCheck(currentTime.plusMinutes(mesInfo.leadMixing), currentTime);
                System.out.println("추출기1 시작시간: " + currentTime);
                currentTime = currentTime.plusMinutes(mesInfo.leadMixing); // 혼합 리드타임 더하기

                currentTime = lunchAndLeaveTimeStartCheck(currentTime);
                currentTime = currentTime.plusHours(8); // 가열시간 더하기

                System.out.println("추출기1 완료시간: " + currentTime);
                mesInfo.nowExtractionMachine1.add(currentTime); // 추출기1 공정계획 추가
                mesInfo.setPastExtractionMachine1(currentTime); // 추출기1의 마지막 공정시간 set
            }else { // 추출기2가 먼저 끝날 때
                if (currentTime.isBefore(mesInfo.pastExtractionMachine2)) { // 추출기 2의 공정계획이 있으면
                    currentTime = mesInfo.pastExtractionMachine2; // 추출기 2의 공정계획이 끝나는 시간에 시작
                }
                currentTime = lunchAndLeaveTimeStartCheck(currentTime);
                currentTime = lunchAndLeaveTimeFinishCheck(currentTime.plusMinutes(mesInfo.leadMixing), currentTime);
                System.out.println("추출기1 시작시간: " + currentTime);
                currentTime = currentTime.plusMinutes(mesInfo.leadMixing); // 혼합 리드타임 더하기

                currentTime = lunchAndLeaveTimeStartCheck(currentTime);
                currentTime = currentTime.plusHours(8); // 가열시간 더하기

                System.out.println("추출기2 완료시간: " + currentTime);

                mesInfo.nowExtractionMachine2.add(currentTime); // 추출기2 공정계획 추가
                mesInfo.setPastExtractionMachine2(currentTime); // 추출기2의 마지막 공정시간 set
            }
        }
        mesInfo.setExtraction(currentTime); // 추출 및 혼합 완료시간 set
    }


    LocalDateTime lunchAndLeaveTimeStartCheck(LocalDateTime startTime){ // 공정 시작 시 점심, 퇴근 시간 체크 메서드

        if(startTime.getDayOfWeek() == DayOfWeek.SATURDAY || startTime.getDayOfWeek() == DayOfWeek.SUNDAY){
            startTime = startTime.with(TemporalAdjusters.next(DayOfWeek.MONDAY)).withHour(9).withMinute(0).withSecond(0);
            return startTime; // 주말이면 다음주 월요일 09:00 리턴
        }

        if (startTime.getHour() == 12){ // 점심시간 이면
            startTime = startTime.withHour(13).withMinute(0).withSecond(0);
            return startTime; // 점심 끝나는 시간 리턴
        }

        if (startTime.getHour() >= 18) { // 퇴근 시간 이후 당일
            if (startTime.getDayOfWeek() == DayOfWeek.FRIDAY) { // 금요일인 경우
                startTime = startTime.with(TemporalAdjusters.next(DayOfWeek.MONDAY)).withHour(9).withMinute(0).withSecond(0);
                return startTime; // 다음주 월요일 09:00 리턴
            }
            startTime = startTime.plusDays(1).withHour(9).withMinute(0).withSecond(0);
            return startTime; // 다음날 09:00 리턴
        }

        if (startTime.getHour() < 9) { // 퇴근 시간 이후 다음날
            startTime = startTime.withHour(9).withMinute(0).withSecond(0);
            return startTime; // 당일 09:00 리턴
        }

        return startTime; // 근무 시간 안걸리면 그대로 리턴
    }

    LocalDateTime lunchAndLeaveTimeFinishCheck(LocalDateTime finishTime, LocalDateTime startTime){ // 공정 완료 시 점심, 퇴근 시간 체크 메서드

        if(finishTime.getDayOfWeek() == DayOfWeek.SATURDAY || finishTime.getDayOfWeek() == DayOfWeek.SUNDAY){
            startTime = finishTime.with(TemporalAdjusters.next(DayOfWeek.MONDAY)).withHour(9).withMinute(0).withSecond(0);
            return startTime; // 주말이면 다음주 월요일 09:00 리턴
        }

        if (finishTime.getHour() == 12){ // 점심시간 이면
            startTime = finishTime.withHour(13).withMinute(0).withSecond(0);
            return startTime; // 점심 끝나는 시간 리턴
        }

        if (finishTime.getHour() >= 18) { // 퇴근 시간 이후 당일
            if (finishTime.getDayOfWeek() == DayOfWeek.FRIDAY) { // 금요일인 경우
                startTime = finishTime.with(TemporalAdjusters.next(DayOfWeek.MONDAY)).withHour(9).withMinute(0).withSecond(0);
                return startTime; // 다음주 월요일 09:00 리턴
            }
            startTime = finishTime.plusDays(1).withHour(9).withMinute(0).withSecond(0);
            return startTime; // 다음날 09:00 리턴
        }

        if (finishTime.getHour() < 9) { // 퇴근 시간 이후 다음날
            startTime = finishTime.withHour(9).withMinute(0).withSecond(0);
            return startTime; // 당일 09:00 리턴
        }

        return startTime; // 근무 시간 안걸리면 그대로 리턴
    }




}
