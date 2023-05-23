package com.mes.aone.util;


import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;


public class Calculator {
    private MESInfo mesInfo;

    public Calculator(MESInfo mesInfo) {
        this.mesInfo = mesInfo;
    }

    void purChaseAmount() { // 발주량 계산
        if (mesInfo.getProductName().equals("양배추즙")) {
            mesInfo.setCabbage((int) (Math.ceil((double) (mesInfo.salesQty * 30 / 20) / 1000) * 1000));
            mesInfo.setBox((int) (Math.ceil((double) (mesInfo.cabbage * 20 / 30) / 500) * 500));
            mesInfo.setPouch((int) (Math.ceil((double) (mesInfo.cabbage * 20) / 1000) * 1000));

            System.out.println("양배추: " + mesInfo.cabbage + " 박스: " + mesInfo.box + " 파우치: " + mesInfo.pouch);

        } else if (mesInfo.getProductName().equals("흑마늘즙")) {
            mesInfo.setGarlic((int) Math.ceil((double) (mesInfo.salesQty * 30 / 120) / 10) * 10);
            mesInfo.setBox((int) Math.ceil((double) (mesInfo.garlic * 120 / 30) / 500) * 500);
            mesInfo.setPouch((int) Math.ceil((double) (mesInfo.garlic * 120) / 1000) * 1000);

            System.out.println("흑마늘: " + mesInfo.garlic + " 박스: " + mesInfo.box + " 파우치: " + mesInfo.pouch);

        } else if (mesInfo.getProductName().equals("석류젤리스틱")) {
            mesInfo.setPomegranate((int) Math.ceil((double) (mesInfo.salesQty * 25 / 200) / 5) * 5);
            mesInfo.setCollagen((int) Math.ceil((double) (mesInfo.pomegranate * 2 / 5) / 5) * 5);
            mesInfo.setBox((int) Math.ceil((double) (mesInfo.pomegranate * 200 / 25) / 500) * 500);
            mesInfo.setStickPouch((int) Math.ceil((double) (mesInfo.pomegranate * 200) / 1000) * 1000);

            System.out.println("석류액기스: " + mesInfo.pomegranate + " 콜라겐: " + mesInfo.collagen + " 박스: " + mesInfo.box + " 스틱파우치: " + mesInfo.stickPouch);

        } else {
            mesInfo.setPlum((int) Math.ceil((double) (mesInfo.salesQty * 25 / 200) / 5) * 5);
            mesInfo.setCollagen((int) Math.ceil((double) (mesInfo.plum * 2 / 5) / 5) * 5);
            mesInfo.setBox((int) Math.ceil((double) (mesInfo.plum * 200 / 25) / 500) * 500);
            mesInfo.setStickPouch((int) Math.ceil((double) (mesInfo.plum * 200) / 1000) * 1000);

            System.out.println("매실액기스: " + mesInfo.plum + " 콜라겐: " + mesInfo.collagen + " 박스: " + mesInfo.box + " 스틱파우치: " + mesInfo.stickPouch);

        }
    }

    void materialArrived() { // 발주 원자재 도착 시간

    }


    void measurement() { // 원료계량
        LocalDateTime currentTime = LocalDateTime.of(2023, 5, 23, 9, 0, 0); // 원료계량 시작시간

        int workAmount = 0;

        if (mesInfo.productName.equals("양배추즙")) {
            workAmount = mesInfo.cabbage;
        }else if (mesInfo.productName.equals("흑마늘즙")){
            workAmount = mesInfo.garlic;
        }else if (mesInfo.productName.equals("석류젤리스틱")){
            workAmount = mesInfo.pomegranate;
        }else {
            workAmount = mesInfo.plum;
        }

        currentTime = lunchAndLeaveTimeStartCheck(currentTime); // 작업 시작 시 비근무 시간 체크(작업 시작시간 리턴)
        currentTime = lunchAndLeaveTimeFinishCheck(currentTime.plusMinutes(mesInfo.leadMeasurement), currentTime); // 작업 완료 시 비근무 시간 체크(작업 시작시간 리턴)
        System.out.println("원료계량 시작시간: " + currentTime);
        currentTime = currentTime.plusMinutes(mesInfo.leadMeasurement); // 원료계량 리드타임 더하기

        currentTime = lunchAndLeaveTimeStartCheck(currentTime); // 작업 시작 시 비근무 시간 체크(공정 시작시간 리턴)
        currentTime = lunchAndLeaveTimeFinishCheck(currentTime.plusMinutes(30), currentTime); // 작업 시작 시 비근무 시간 체크(작업 시작시간 리턴)
        currentTime = currentTime.plusMinutes(30); // 원료계량 작업시간 더하기
        System.out.println("원료계량 완료시간: " + currentTime);

        mesInfo.setMeasurement(currentTime); // 원료계량 완료시간 set
        mesInfo.setNowMeasurementOutput(workAmount); //

    }

    void preProcessing() { // 전처리
        LocalDateTime currentTime = mesInfo.getMeasurement(); // 원료계량 완료시간을 전처리 시작시간으로 설정

        int workAmount = mesInfo.nowMeasurementOutput; // 작업량
        int output = 0; // 생산량

        for (int i = 0; i < Math.ceil( workAmount / 1000.0); i++) { // 작업 반복횟수 만큼 실행 ex) 2500kg 이면 3번 반복
            currentTime = lunchAndLeaveTimeStartCheck(currentTime); // 작업 시작 시 비근무 시간 체크(작업 시작시간 리턴)
            currentTime = lunchAndLeaveTimeFinishCheck(currentTime.plusMinutes(mesInfo.leadPreProcessing), currentTime); // 작업 완료 시 비근무 시간 체크(작업 시작시간 리턴)
            System.out.println("전처리 " + (i + 1) + " 시작시간: " + currentTime);
            currentTime = currentTime.plusMinutes(mesInfo.leadPreProcessing); // 전처리 리드타임 더하기

            if (i == Math.floor( workAmount / 1000.0)) { // 생산량이 1000kg 미만일 때 (전처리 마지막 작업 시)
                output = workAmount % 1000;
                currentTime = lunchAndLeaveTimeStartCheck(currentTime); // 작업 시작 시 비근무 시간 체크(작업 시작시간 리턴)
                currentTime = currentTime.plusMinutes((int) Math.ceil(output / 1000.0 * 60));// 전처리 작업시간 더하기

            } else {
                output = 1000;
                currentTime = lunchAndLeaveTimeStartCheck(currentTime); // 작업 시작 시 비근무 시간 체크(작업 시작시간 리턴)
                currentTime = currentTime.plusHours(1); // 전처리 작업시간 더하기

            }
            System.out.println("전처리 " + (i + 1) + " 완료시간: " + currentTime);
            mesInfo.nowPreProcessingMachine.add(currentTime);
            mesInfo.nowPreProcessingOutput.add(output);
        }

        mesInfo.setPreProcessing(currentTime); // 전처리 완료시간 set
        mesInfo.setPreProcessingOutput(workAmount); // 전처리 생산량

    }

    void extraction() { // 추출 및 혼합
        LocalDateTime currentTime = null; // 추출 시간 변수 선언

        int output = 0; // 생산량
        int workAmount = mesInfo.preProcessingOutput; // 작업량

        if (mesInfo.productName.equals("양배추즙")) { // 양배추 추출 공정
            for (int i = 0; i < mesInfo.nowPreProcessingMachine.size(); i++) { // 전처리 작업 횟수 만큼 반복
                currentTime = mesInfo.nowPreProcessingMachine.get(i);
                if (mesInfo.pastExtractionMachine1.isBefore(mesInfo.pastExtractionMachine2)) { // 추출기1이 먼저 끝날 때
                    if (currentTime.isBefore(mesInfo.pastExtractionMachine1)) { // 추출기 1의 공정계획이 있으면
                        currentTime = mesInfo.pastExtractionMachine1; // 추출기 1의 공정계획이 끝나는 시간에 시작
                    }
                    currentTime = lunchAndLeaveTimeStartCheck(currentTime);
                    currentTime = lunchAndLeaveTimeFinishCheck(currentTime.plusMinutes(mesInfo.leadExtraction), currentTime);
                    System.out.println("추출기1 - 추출" + (i + 1) + "번째 시작시간: " + currentTime);
                    currentTime = currentTime.plusMinutes(mesInfo.leadExtraction);

                    currentTime = lunchAndLeaveTimeStartCheck(currentTime);
                    currentTime = currentTime.plusHours(72);

                    mesInfo.nowExtractionMachine1.add(currentTime);
                    mesInfo.setPastExtractionMachine1(currentTime);
                    System.out.println("추출기1 - 추출" + (i + 1) + "번째 완료시간: " + currentTime);

                } else { // 추출기2가 먼저 끝날 때
                    if (currentTime.isBefore(mesInfo.pastExtractionMachine2)) { // 추출기 1의 공정계획이 있으면
                        currentTime = mesInfo.pastExtractionMachine2; // 추출기 2의 공정계획이 끝나는 시간에 시작
                    }
                    currentTime = lunchAndLeaveTimeStartCheck(currentTime);
                    currentTime = lunchAndLeaveTimeFinishCheck(currentTime.plusMinutes(mesInfo.leadExtraction), currentTime);
                    System.out.println("추출기2 - 추출" + (i + 1) + "번째 시작시간: " + currentTime);
                    currentTime = currentTime.plusMinutes(mesInfo.leadExtraction);

                    currentTime = lunchAndLeaveTimeStartCheck(currentTime);
                    currentTime = currentTime.plusHours(72);

                    mesInfo.nowExtractionMachine2.add(currentTime);
                    mesInfo.setPastExtractionMachine2(currentTime);
                    System.out.println("추출기2 - 추출" + (i + 1) + "번째 완료시간: " + currentTime);
                }
                mesInfo.nowExtraction.add(currentTime); // 추출 및 혼합 공정계획 추가
                mesInfo.nowExtractionOutput.add(1600); // 추출 및 혼합 생산량 추가
            }
        } else if (mesInfo.productName.equals("흑마늘즙")) {
            for (int i = 0; i < Math.ceil(mesInfo.garlic / 500.0); i++) {
                currentTime = mesInfo.nowPreProcessingMachine.get((int) Math.floor(i / 2.0));
                if (mesInfo.pastExtractionMachine1.isBefore(mesInfo.pastExtractionMachine2)) { // 추출기1이 먼저 끝날 때
                    if (currentTime.isBefore(mesInfo.pastExtractionMachine1)) { // 추출기 1의 공정계획이 있으면
                        currentTime = mesInfo.pastExtractionMachine1; // 추출기 1의 공정계획이 끝나는 시간에 시작
                    }
                    currentTime = lunchAndLeaveTimeStartCheck(currentTime);
                    currentTime = lunchAndLeaveTimeFinishCheck(currentTime.plusMinutes(mesInfo.leadExtraction), currentTime);
                    System.out.println("추출기1 - 추출" + (i + 1) + "번째 시작시간: " + currentTime);
                    currentTime = currentTime.plusMinutes(mesInfo.leadExtraction); // 추출 리드타임 더하기

                    if (i == Math.ceil(mesInfo.garlic / 500.0) - 1) { // 마지막 추출 공정시
                        if (workAmount % 500 == 0) { // 흑마늘이 500kg로 나누어 떨어지면
                            output = 500*24/10;
                            currentTime = currentTime.plusHours(72); // 추출시간 + 가열시간 더하기
                        } else { // 흑마늘이 500kg 미만 일 시
                            output = (workAmount%500)*24/10;
                            currentTime = lunchAndLeaveTimeStartCheck(currentTime);
                            currentTime = currentTime.plusHours(24).plusSeconds(3456 / 10 * (workAmount % 500)); // 추출시간 + 가열시간 더하기
                        }
                    } else {
                        output = 500*24/10;
                        currentTime = lunchAndLeaveTimeStartCheck(currentTime);
                        currentTime = currentTime.plusHours(72); // 추출시간 + 가열시간 더하기
                    }

                    mesInfo.nowExtractionMachine1.add(currentTime); // 추출기 1 공정계획 추가

                    mesInfo.setPastExtractionMachine1(currentTime); // 추출기 1의 마지막 공정시간 set
                    System.out.println("추출기1 - 추출" + (i + 1) + "번째 완료시간: " + currentTime);

                } else { // 추출기2가 먼저 끝날 때
                    if (currentTime.isBefore(mesInfo.pastExtractionMachine2)) { // 추출기 2의 공정계획이 있으면
                        currentTime = mesInfo.pastExtractionMachine2; // 추출기 2의 공정계획이 끝나는 시간에 시작
                    }
                    currentTime = lunchAndLeaveTimeStartCheck(currentTime);
                    currentTime = lunchAndLeaveTimeFinishCheck(currentTime.plusMinutes(mesInfo.leadExtraction), currentTime);
                    System.out.println("추출기2 - 추출" + (i + 1) + "번째 시작시간: " + currentTime);
                    currentTime = currentTime.plusMinutes(mesInfo.leadExtraction); // 추출 리드타임 더하기

                    if (i == Math.ceil(mesInfo.garlic / 500.0) - 1) { // 마지막 추출 공정시
                        if (workAmount % 500 == 0) { // 흑마늘이 500kg로 나누어 떨어지면
                            output = 500*24/10;
                            currentTime = currentTime.plusHours(72); // 추출시간 + 가열시간 더하기
                        } else {
                            output = (workAmount%500)*24/10;
                            currentTime = lunchAndLeaveTimeStartCheck(currentTime);
                            currentTime = currentTime.plusHours(24).plusSeconds(3456 / 10 * (workAmount % 500)); // 추출시간 + 가열시간 더하기
                        }
                    } else {
                        output = 500*24/10;
                        currentTime = lunchAndLeaveTimeStartCheck(currentTime);
                        currentTime = currentTime.plusHours(72); // 추출시간 + 가열시간 더하기
                    }

                    mesInfo.nowExtractionMachine2.add(currentTime); // 추출기2 공정계획 추가
                    mesInfo.setPastExtractionMachine2(currentTime); // 추출기2의 마지막 공정시간 set
                    System.out.println("추출기2 - 추출" + (i + 1) + "번째 완료시간: " + currentTime);
                }
                mesInfo.nowExtractionOutput.add(output); // 추출 및 혼합 생산량 추가
                mesInfo.nowExtraction.add(currentTime); // 추출 및 혼합 공정계획 추가
            }
        } else {
            currentTime = mesInfo.getMeasurement(); // 원료계량 완료시간을 추출 시작시간으로 설정

            if (mesInfo.pastExtractionMachine1.isBefore(mesInfo.pastExtractionMachine2)) { // 추출기1이 먼저 끝날 때
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
            } else { // 추출기2가 먼저 끝날 때
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
            mesInfo.nowExtraction.add(currentTime); // 추출 및 혼합 공정계획 추가
        }
        mesInfo.setExtraction(currentTime); // 모든 추출 및 혼합 완료시간 set
        mesInfo.setExtractionOutput(mesInfo.preProcessingOutput * 16 / 10);
    }

    //충진
    void fill() {
        LocalDateTime currentTime = mesInfo.extraction; // 충진 시간 변수 선언




        int output = 0; // 추출액, 혼합액 총량
        int hour_capacity = 0; // 시간당 생산가능량

        output = mesInfo.extractionOutput;//추출/혼합 완료량
//        hour_capacity = (mesInfo.productName.equals("양배추즙") || mesInfo.productName.equals("흑마늘즙")) ? 1750 : 1500; // 충진시 기계를 1개씩만 사용하면
        hour_capacity = (mesInfo.productName.equals("양배추즙") || mesInfo.productName.equals("흑마늘즙")) ? 1750 * 2 : 1500 * 2; // 충진시 기계 2개를 동시에 돌리면

        double totalPackages = (double) output / ((mesInfo.productName.equals("양배추즙") || mesInfo.productName.equals("흑마늘즙")) ? 0.08 : 0.015); // 혼합액 양으로 만들 수 있는 포 수 (총)
        mesInfo.setFillOutPut((int) totalPackages);//총 포 수 저장

        int totalHours = (int) Math.floor(totalPackages / hour_capacity); // 총 소요 시간 계산
        int remainingPackages = (int) Math.floor((totalPackages % hour_capacity) * (60.0 / hour_capacity)); // 남은 포장 개수 계산

//        System.out.println("총 시간 : "+totalHours);
//        System.out.println("총 분 : "+remainingPackages);

        currentTime = currentTime.plusHours(totalHours); // 작업 끝나는 시간 계산
        currentTime = currentTime.plusMinutes(remainingPackages); // 남은 포장 개수에 대한 분 단위 계산


        mesInfo.setFill(currentTime); // 충진 완료시간 저장
        System.out.println("충진 시작시간: " + currentTime + "\n충진 완료시간: " + currentTime);
    }

    //검사
    void examination() {
        LocalDateTime start = lunchAndLeaveTimeStartCheck(mesInfo.getFill()); // 액제조제 시스템 완료시간
        LocalDateTime end = lunchAndLeaveTimeStartCheck(start.plusMinutes(mesInfo.getLeadExamination())); // 시작 시간이 근무시간이 맞는지 확인, 리드타임 더함

        int output = 0; // 추출액, 혼합액 총량
        int hour_capacity = 0; // 시간당 생산가능량

        output = mesInfo.fillOutPut; //충진된 개수
        System.out.println(output);
        hour_capacity = 5000; //1시간당 5000개 검사가능

        //double totalPackages = (double) output / ((mesInfo.productName.equals("양배추즙") || mesInfo.productName.equals("흑마늘즙")) ? 0.08 : 0.015); // 혼합액 양으로 만들 수 있는 포 수 (총)
        int totalHours = (int) Math.floor((double) output / hour_capacity); // 총 소요 시간 계산
        int remainingExam = (int) Math.floor(((double) output % hour_capacity) * (60.0 / hour_capacity)); // 남은 포장 개수 계산

//         System.out.println("총 시간 : "+totalHours);
//         System.out.println("총 분 : "+remainingExam);


        end = end.plusHours(totalHours); // 작업 끝나는 시간 계산
        end = end.plusMinutes(remainingExam); // 남은 포장 개수에 대한 분 단위 계산

        mesInfo.setExamination(end); // 검사 완료시간 저장
        mesInfo.setExaminationOutput(output);
        System.out.println("검사 시작시간: " + start + "\n검사 완료시간: " + end);
    }

    //열교환(식힘)
    void cooling() {
        LocalDateTime start = mesInfo.getExamination(); // 검사완료 시간
        LocalDateTime end = start.plusDays(1).withHour(9).withMinute(0).withSecond(0); //다음날 오전 종료
        mesInfo.setCooling(end); // 검사 완료시간 저장
        System.out.println("열교환 시작시간: " + start + "\n열교환 완료시간: " + end);
    }


    // 포장
    void packaging() {
//        int inputEa = 0;                                  // inputEa          : 충진/식힘 후 포장할 전체 낱개(ea) 수
        int inputEa = mesInfo.examinationOutput;                                  // inputEa          : 충진/식힘 후 포장할 전체 낱개(ea) 수
        System.out.println("충진 ea: " + inputEa + "ea");

        long outputBox = 0;                                // 총 만들어질 box 수 : inputEa / 30(양배추, 흑마늘)   inputEa / 25(석류젤리스틱, 매실젤리스틱)
        int outputEa = 0;                                 // 남은 ea 수        : inputEa % 30(양배추, 흑마늘)   inputEa % 25(석류젤리스틱, 매실젤리스틱)
        double packagingTime = 0;                           // 포장시간 (분)
        // 포장 관련 상수 정의
        int packagingTimePerBoxSeconds = 18;             // 1박스당 포장 시간 (초)

        if (mesInfo.productName.equals("양배추즙") || mesInfo.productName.equals("흑마늘즙")) {  // 양배추즙, 흑마늘즙이면
//            inputEa = mesInfo.fillOutPut;
            outputBox = inputEa / 30;
            outputEa = inputEa % 30;

        } else {                                                                            // 젤리스틱이면
//            inputEa = mesInfo.fillOutPut;
            outputBox = inputEa / 25;
            outputEa = inputEa % 25;
        }
        System.out.println("포장된 박스 수: " + outputBox + "box");
        System.out.println("포장 후 남은 낱개: " + outputEa + "ea");

        mesInfo.setPackagingBoxOutput(outputBox);                                // 포장된 박스 수
        mesInfo.setPackagingEaOutput(outputEa);                                   // 포장 후 남은 낱개
        packagingTime = outputBox * packagingTimePerBoxSeconds / 60;        // 포장시간(분)
        System.out.println("포장시간: " + packagingTime + "분");


        // 근무 시간 및 점심 시간 설정
        LocalTime startWorkTime = LocalTime.of(9, 0);    // 근무 시작 시간
        LocalTime endWorkTime = LocalTime.of(18, 0);     // 근무 종료 시간
        LocalTime lunchStartTime = LocalTime.of(12, 0);  // 점심 시작 시간
        LocalTime lunchEndTime = LocalTime.of(13, 0);    // 점심 종료 시간

        LocalDateTime leadTimeStart = mesInfo.getCooling();         // 리드타임 시작
        LocalDateTime leadTimeEnd = null;                              // 리드타임 끝

        leadTimeStart = lunchAndLeaveTimeStartCheck(leadTimeStart);     // 포장 리드타임 시작 전 시작 시간 비근무 시간 체크(공정 시작시간 리턴)
        leadTimeEnd = lunchAndLeaveTimeStartCheck(leadTimeStart);       // 포장 리드타임 시작 전 끝나는 시간 비근무 시간 체크(공정 시작시간 리턴)
        leadTimeEnd = lunchAndLeaveTimeFinishCheck(leadTimeStart.plusMinutes(mesInfo.leadPackaging), leadTimeEnd); // 포장리드타임 완료 시 비근무 시간 체크(포장 시작시간 리턴)
        leadTimeEnd = leadTimeEnd.plusMinutes(mesInfo.leadPackaging);    // 원료계량 리드타임 더하기
        System.out.println("포장 리드타임 시작 : " + leadTimeStart);
        System.out.println("포장 리드타임 끝 : " + leadTimeEnd);

        LocalDateTime packingStart = leadTimeEnd;                                   // 포장 시작 시간
        LocalDateTime packingEnd = packingStart.plusMinutes((long) packagingTime);   // 포장 종료 시간

        System.out.println("포장 시작시간 : " + packingStart);

        if (packingEnd.getHour() >= lunchStartTime.getHour() && packingEnd.getHour() <= lunchEndTime.getHour()) {    // 포장끝나는시간 점심시간, 시작시간 점심시간->리드타임에서 거름
            Duration availableTime = Duration.between(packingStart.toLocalTime(), lunchStartTime);
            long nowPackingBox = availableTime.toSeconds() / 18;                     // 지금 작업한 박스
            outputBox = outputBox - nowPackingBox;                                   // 남은 박스 : 오후 작업할 박스
            packagingTime = packagingTime - availableTime.toMinutes();                 // 남은 포장 시간
            packingStart = packingStart.withHour(13).withMinute(0).withSecond(0);    // 1시부터 다시 시작
            packingEnd = packingStart.plusMinutes((long) packagingTime);              // 포장 끝나는시간

        }

        if (packingEnd.getHour() >= endWorkTime.getHour()) {                      // 포장 끝나는 시간이 근무끝시간(18시)보다 뒤이거나 같으면
            Duration availableTime = Duration.between(packingStart.toLocalTime(), endWorkTime);
            long todayPackingBox = availableTime.toSeconds() / 18;              // 오늘 작업한 박스
            outputBox = outputBox - todayPackingBox;                          // 남은 박스 : 익일 작업할 박스

            if (packingStart.getDayOfWeek() == DayOfWeek.FRIDAY) { // 금요일 이면
                packingStart = packingStart.plusDays(3).withHour(9).withMinute(0).withSecond(0);

            } else {
                packingStart = packingStart.plusDays(1).with(startWorkTime);
            }

            packagingTime = packagingTime - availableTime.toMinutes();            // 남은 포장 시간
            packingEnd = packingStart.plusMinutes((long) packagingTime);
        }

        System.out.println("포장 완료시간 : " + packingEnd);

        mesInfo.setPackaging(packingEnd); // 포장 완료시간 set

    }


    //---
    LocalDateTime lunchAndLeaveTimeStartCheck(LocalDateTime startTime) { // 공정 시작 시 점심, 퇴근 시간 체크 메서드

        if (startTime.getDayOfWeek() == DayOfWeek.SATURDAY || startTime.getDayOfWeek() == DayOfWeek.SUNDAY) {
            startTime = startTime.with(TemporalAdjusters.next(DayOfWeek.MONDAY)).withHour(9).withMinute(0).withSecond(0);
            return startTime; // 주말이면 다음주 월요일 09:00 리턴
        }

        if (startTime.getHour() == 12) { // 점심시간 이면
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


    LocalDateTime lunchAndLeaveTimeFinishCheck(LocalDateTime finishTime, LocalDateTime startTime) { // 공정 완료 시 점심, 퇴근 시간 체크 메서드

        if (finishTime.getDayOfWeek() == DayOfWeek.SATURDAY || finishTime.getDayOfWeek() == DayOfWeek.SUNDAY) {
            startTime = finishTime.with(TemporalAdjusters.next(DayOfWeek.MONDAY)).withHour(9).withMinute(0).withSecond(0);
            return startTime; // 주말이면 다음주 월요일 09:00 리턴
        }

        if (finishTime.getHour() == 12) { // 점심시간 이면
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