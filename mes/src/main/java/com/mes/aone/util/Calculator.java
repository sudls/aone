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
//        LocalDateTime start = LocalDateTime.of(2023,5,19,15,0,0); // 원료계량 시작시간
        LocalDateTime start = LocalDateTime.of(2023,4,5,10,20,0); // 원료계량 시작시간
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

    //충진
    void fill(){
        LocalDateTime start = lunchAndLeaveTimeStartCheck(mesInfo.getExtraction());  // 액제조제 시스템 완료시간
        LocalDateTime end = lunchAndLeaveTimeStartCheck(start.plusMinutes(mesInfo.getLeadFill())); // 시작 시간이 근무시간이 맞는지 확인, 리드타임 더함

        int output = 0; // 추출액, 혼합액 총량
        int hour_capacity = 0; // 시간당 생산가능량

        output=mesInfo.extractionOutput;//추출/혼합 완료량
        hour_capacity = (mesInfo.productName.equals("양배추즙") || mesInfo.productName.equals("흑마늘즙")) ? 1750 : 1500; // 충진시 기계를 1개씩만 사용하면
//      hour_capacity = (mesInfo.productName.equals("양배추즙") || mesInfo.productName.equals("흑마늘즙")) ? 1750*2 : 1500*2; // 충진시 기계 2개를 동시에 돌리면

        double totalPackages = (double) output / ((mesInfo.productName.equals("양배추즙") || mesInfo.productName.equals("흑마늘즙")) ? 0.08 : 0.015); // 혼합액 양으로 만들 수 있는 포 수 (총)
        int totalHours = (int) Math.floor(totalPackages / hour_capacity); // 총 소요 시간 계산
        int remainingPackages = (int) Math.floor((totalPackages % hour_capacity) * (60.0 / hour_capacity)); // 남은 포장 개수 계산

//        System.out.println("총 시간 : "+totalHours);
//        System.out.println("총 분 : "+remainingPackages);


        end = end.plusHours(totalHours); // 작업 끝나는 시간 계산
        end = end.plusMinutes(remainingPackages); // 남은 포장 개수에 대한 분 단위 계산

        mesInfo.setFill(end); // 충진 완료시간 저장
        System.out.println("충진 시작시간: " + start + "\n충진 완료시간: " + end);
    }

//검사
    void examination(){
        LocalDateTime start = lunchAndLeaveTimeStartCheck(mesInfo.getFill()); // 액제조제 시스템 완료시간
        LocalDateTime end = lunchAndLeaveTimeStartCheck(start.plusMinutes(mesInfo.getLeadExamination())); // 시작 시간이 근무시간이 맞는지 확인, 리드타임 더함

        int output = 0; // 추출액, 혼합액 총량
        int hour_capacity = 0; // 시간당 생산가능량

        output=mesInfo.fillOutPut; //충진된 개수
        hour_capacity = 5000; //1시간당 5000개 검사가능

        //double totalPackages = (double) output / ((mesInfo.productName.equals("양배추즙") || mesInfo.productName.equals("흑마늘즙")) ? 0.08 : 0.015); // 혼합액 양으로 만들 수 있는 포 수 (총)
        int totalHours = (int) Math.floor((double)output / hour_capacity); // 총 소요 시간 계산
        int remainingExam = (int) Math.floor(((double)output  % hour_capacity) * (60.0 / hour_capacity)); // 남은 포장 개수 계산

//         System.out.println("총 시간 : "+totalHours);
//         System.out.println("총 분 : "+remainingExam);


        end = end.plusHours(totalHours); // 작업 끝나는 시간 계산
        end = end.plusMinutes(remainingExam); // 남은 포장 개수에 대한 분 단위 계산

        mesInfo.setExamination(end); // 검사 완료시간 저장
        System.out.println("검사 시작시간: " + start + "\n검사 완료시간: " + end);
    }

//열교환(식힘)
    void cooling(){
        LocalDateTime start = mesInfo.getExamination(); // 검사완료 시간
        LocalDateTime end = start.plusDays(1).withHour(9).withMinute(0).withSecond(0); //다음날 오전 종료
        mesInfo.setCooling(end); // 검사 완료시간 저장
        System.out.println("열교환 시작시간: " + start + "\n열교환 완료시간: " + end);
    }


//---
    LocalDateTime lunchAndLeaveTimeStartCheck(LocalDateTime startTime){ // 공정 시작 시 점심, 퇴근 시간 체크 메서드

        //주말일 경우
        if(startTime.getDayOfWeek() == DayOfWeek.SATURDAY){ // 토요일
            startTime = startTime.plusDays(2).withHour(9).withMinute(0).withSecond(0);
            return startTime; // 월요일 근무 시작시간 리턴
        }else if(startTime.getDayOfWeek() == DayOfWeek.SUNDAY){ // 일요일
            startTime = startTime.plusDays(1).withHour(9).withMinute(0).withSecond(0);
            return startTime; // 월요일 근무 시작시간 리턴
        }

        //평일 근무시간이 아닐경우
        if (startTime.getHour() == 12){ // 점심시간 이면
            startTime = startTime.withHour(13).withMinute(0).withSecond(0);
            return startTime; // 점심 끝나는 시간 리턴
        }

        if(startTime.getHour()<9){ //근무시간 이전일때
            startTime = startTime.withHour(9).withMinute(0).withSecond(0);
            return startTime; // 당일 9시 리턴
        }else if (startTime.getHour()>=18){ //근무시간 이후 일때
            if(startTime.getDayOfWeek() == DayOfWeek.FRIDAY){ // 금요일 이면
                startTime = startTime.plusDays(3).withHour(9).withMinute(0).withSecond(0);// 월요일 근무 시작시간 리턴
            }else {
                startTime = startTime.plusDays(1).withHour(9).withMinute(0).withSecond(0);// 다음날 근무 시작시간 리턴
            }
            return startTime;
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
