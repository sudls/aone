package com.mes.aone.util;


import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.HashMap;
import java.util.Map;


public class Calculator {
    private MESInfo mesInfo;

    public Calculator(MESInfo mesInfo) {
        this.mesInfo = mesInfo;
    }



    String purChaseAmount() { // 발주량 계산
        int requiredProduct = 0;
        int requiredCabbage;
        int requiredGarlic;
        int requiredPomegranate;
        int requiredPlum;
        int requiredCollagen;   // 필요 콜라겐
        int requiredBox;        // 필요 박스
        int requiredPouch;        // 필요 파우치(즙)
        int requiredStickPouch;        // 필요 파우치(스틱)

        LocalDateTime shipmentDate = null;      // 출하일
        Map<String, Integer> purchaseMap = null;     // 발주량

        if (mesInfo.getProductName().equals("양배추즙")) {                // 창고 재고량 확인 전 세팅
            requiredCabbage = mesInfo.salesQty;
            if (mesInfo.cabbagePackaging >= requiredCabbage) {            // 재고량 > 주문량
                mesInfo.setShipmentBox(requiredCabbage);                // 출하량
                mesInfo.setCabbagePackaging(mesInfo.cabbagePackaging - requiredCabbage);        // 창고 재고 뺴기
                return "enough";
            } else {                                                    // 재고량 < 주문량
                requiredProduct = requiredCabbage - mesInfo.cabbagePackaging;
                System.out.println("창고 완제품 재고 수: " + mesInfo.getCabbagePackaging() + " 박스");
                System.out.println("만들어야 할 양배추즙 : " + requiredProduct + " 박스");

                mesInfo.setCabbage((int) (Math.ceil((double) (requiredProduct * 30 / 20) / 1000) * 1000));        // 양배추 주문량
                System.out.println("양배추 주문 수: " + mesInfo.getCabbage() + "kg");

                requiredBox = (int) (Math.ceil((double) (mesInfo.cabbage * 20 / 30)));      // 필요한 박스량
                System.out.println("필요 박스 수: " + requiredBox + " 박스");
                if (mesInfo.stockBox < requiredBox) {          // 창고 박스 재고량이 필요 박스보다 작을때
                    requiredBox = requiredBox - mesInfo.stockBox;
                    int orderBoxRoundedUp = (int) Math.ceil((double) requiredBox / 500) * 500;      // 박스 주문 500단위 올림
                    mesInfo.setBox(orderBoxRoundedUp);
                    System.out.println("창고 박스 수: " + mesInfo.getStockBox() + " 박스");
                    System.out.println("발주 박스 수: " + mesInfo.getBox() + " 박스");

                } else {                                    // 창고 박스 재고량이 충분할 때
                    System.out.println("창고 박스 수: " + mesInfo.getStockBox() + " 박스");
                    System.out.println("발주 박스 수: " + mesInfo.getBox() + " 박스");

                }


                requiredPouch = (int) (Math.ceil((double) (mesInfo.cabbage * 20)));      // 필요한 파우치 수
                System.out.println("필요 파우치 수: " + requiredPouch + "ea");
                if (mesInfo.stockPouch < requiredPouch) {          // 창고재고량이 필요량보다 작을때
                    requiredPouch = requiredPouch - mesInfo.stockPouch;      // 주문파우치 = 필요 파우치- 창고 파우치
                    int orderPouchRoundedUp = (int) Math.ceil((double) requiredPouch / 1000) * 1000;      // 파우치 주문 1000단위 올림
                    mesInfo.setPouch(orderPouchRoundedUp);
                    System.out.println("창고 파우치 수: " + mesInfo.getStockPouch() + "ea");
                    System.out.println("발주 파우치 수: " + mesInfo.getPouch() + "ea");


                } else {                                    // 창고 파우치 재고량이 충분할 때
                    System.out.println("창고 파우치 수: " + mesInfo.getStockPouch() + "ea");
                    System.out.println("발주 파우치 수: " + mesInfo.getPouch() + "ea");

                }

                purchaseMap = new HashMap<>();
                purchaseMap.put("양배추",  mesInfo.cabbage);
                purchaseMap.put("박스",  mesInfo.box);
                purchaseMap.put("파우치",  mesInfo.pouch);
                mesInfo.setPurchaseMap(purchaseMap);


                for (Map.Entry<String, Integer> entry : mesInfo.getPurchaseMap().entrySet()) {
                    String key = entry.getKey();
                    Integer value = entry.getValue();
                    System.out.println("키: " + key + ", 값: " + value);
                }

                System.out.println("양배추: " + mesInfo.cabbage + " 박스: " + mesInfo.box + " 파우치: " + mesInfo.pouch);

            }
        } else if (mesInfo.getProductName().equals("흑마늘즙")) {
            requiredGarlic = mesInfo.salesQty;
            if (mesInfo.garlicPackaging >= requiredGarlic) {            // 재고량 > 주문량
                mesInfo.setShipmentBox(requiredGarlic);                // 출하량
                mesInfo.setGarlicPackaging(mesInfo.garlicPackaging - requiredGarlic);        // 창고 재고 뺴기
                return "enough";
            } else {
                requiredProduct = requiredGarlic - mesInfo.garlicPackaging;
                System.out.println("창고 완제품 재고 수: " + mesInfo.getGarlicPackaging() + " 박스");
                System.out.println("만들어야 할 흑마늘즙: " + requiredProduct + " 박스");

                mesInfo.setGarlic((int) Math.ceil((double) (requiredProduct * 30 / 120) / 10) * 10);  // 흑마늘 주문량
                System.out.println("흑마늘 주문 수: " + mesInfo.getGarlic() + "kg");

                requiredBox = (int) (Math.ceil((double) (mesInfo.garlic * 120 / 30)));      // 필요한 박스량
                System.out.println("필요 박스 수: " + requiredBox + " 박스");
                if (mesInfo.stockBox < requiredBox) {          // 창고 박스 재고량이 필요 박스보다 작을때
                    requiredBox = requiredBox - mesInfo.getStockBox();              // 필요박스 = 필요박스 - 창고박스
                    int orderBoxRoundedUp = (int) Math.ceil((double) requiredBox / 500) * 500;      // 박스 주문 500단위 올림
                    mesInfo.setBox(orderBoxRoundedUp);
                    System.out.println("창고 박스 수: " + mesInfo.getStockBox() + " 박스");
                    System.out.println("발주 박스 수: " + mesInfo.getBox() + " 박스");

                } else {                                    // 창고 박스 재고량이 충분할 때
                    mesInfo.setStockBox(mesInfo.stockBox - requiredBox);         // 창고 박스 = 창고 박스 수 - 필요 박스 수
                    System.out.println("창고 박스 수: " + mesInfo.getStockBox() + " 박스");
                    System.out.println("발주 박스 수: " + mesInfo.getBox() + " 박스");

                }


                requiredPouch = (int) (Math.ceil((double) (mesInfo.garlic * 120)));      // 필요한 파우치 수
                System.out.println("필요 파우치 수: " + requiredPouch + "ea");
                if (mesInfo.stockPouch < requiredPouch) {          // 창고재고량이 필요량보다 작을때
                    requiredPouch = requiredPouch - mesInfo.stockPouch;       // 주문파우치 = 필요 파우치 - 창고 파우치
                    int orderPouchRoundedUp = (int) Math.ceil((double) requiredPouch / 1000) * 1000;      // 파우치 주문 1000단위 올림
                    mesInfo.setPouch(orderPouchRoundedUp);
                    System.out.println("창고 파우치 수: " + mesInfo.getStockPouch() + "ea");
                    System.out.println("발주 파우치 수: " + mesInfo.getPouch() + "ea");

                } else {                                    // 창고 파우치 재고량이 충분할 때
//                    mesInfo.setStockPouch(mesInfo.stockPouch - requiredPouch);         // 창고 파우치 = 창고 파우치 수 - 필요 파우치 수
                    System.out.println("창고 파우치 수: " + mesInfo.getStockPouch() + "ea");
                    System.out.println("발주 파우치 수: " + mesInfo.getPouch() + "ea");

                }

                purchaseMap = new HashMap<>();
                purchaseMap.put("흑마늘",  mesInfo.garlic);
                purchaseMap.put("박스",  mesInfo.box);
                purchaseMap.put("파우치",  mesInfo.pouch);
                mesInfo.setPurchaseMap(purchaseMap);
                for (Map.Entry<String, Integer> entry : mesInfo.getPurchaseMap().entrySet()) {
                    String key = entry.getKey();
                    Integer value = entry.getValue();
                    System.out.println("키: " + key + ", 값: " + value);
                }

                System.out.println("흑마늘: " + mesInfo.garlic + " 박스: " + mesInfo.box + " 파우치: " + mesInfo.pouch);
            }

        } else if (mesInfo.getProductName().equals("석류젤리스틱")) {
            requiredPomegranate = mesInfo.salesQty;
            if (mesInfo.pomegranatePackaging >= requiredPomegranate) {            // 재고량 > 주문량
                mesInfo.setShipmentBox(requiredPomegranate);                // 출하량
                mesInfo.setPomegranatePackaging(mesInfo.pomegranatePackaging - requiredPomegranate);        // 창고 재고 뺴기
                return "enough";
            } else {
                requiredProduct = requiredPomegranate - mesInfo.pomegranatePackaging;
                System.out.println("창고 완제품 재고 수: " + mesInfo.getPomegranatePackaging() + " 박스");
                System.out.println("주문할 원재고 수: " + requiredProduct + " 박스");

                // 석류 농축액 주문량
                mesInfo.setPomegranate((int) Math.ceil((double) (requiredProduct * 25 / 200) / 5) * 5);   // 석류 주문량
                System.out.println("석류농축액 주문 수: " + mesInfo.getPomegranate() + "kg");
                // 콜라겐 주문량
                requiredCollagen = (int) (Math.ceil((double) (mesInfo.pomegranate * 2 / 5)));      // 필요한 박스량
                System.out.println("필요 콜라겐 수: " + requiredCollagen + "kg");
                if (mesInfo.stockCollagen < requiredCollagen) {          // 창고 박스 재고량이 필요 박스보다 작을때
                    requiredCollagen = requiredCollagen - mesInfo.getStockCollagen();              // 필요박스 = 필요박스 - 창고박스
                    int orderBoxRoundedUp = (int) Math.ceil((double) requiredCollagen / 5) * 5;      // 박스 주문 500단위 올림
                    mesInfo.setCollagen(orderBoxRoundedUp);
                    System.out.println("창고 콜라겐 수: " + mesInfo.getStockCollagen() + " kg");
                    System.out.println("발주 콜라겐 수: " + mesInfo.getCollagen() + " kg");

                } else {                                    // 창고 박스 재고량이 충분할 때
                    mesInfo.setStockCollagen(mesInfo.stockCollagen - requiredCollagen);         // 창고 박스 = 창고 박스 수 - 필요 박스 수
                    System.out.println("창고 콜라겐 수: " + mesInfo.getStockCollagen() + " kg");
                    System.out.println("발주 콜라겐 수: " + mesInfo.getCollagen() + " kg");

                }
                // 박스 주문량
                requiredBox = (int) (Math.ceil((double) (mesInfo.pomegranate * 200 / 25)));      // 필요한 박스량
                System.out.println("필요 박스 수: " + requiredBox + " 박스");
                if (mesInfo.stockBox < requiredBox) {          // 창고 박스 재고량이 필요 박스보다 작을때
                    requiredBox = requiredBox - mesInfo.getStockBox();              // 필요박스 = 필요박스 - 창고박스
                    int orderBoxRoundedUp = (int) Math.ceil((double) requiredBox / 500) * 500;      // 박스 주문 500단위 올림
                    mesInfo.setBox(orderBoxRoundedUp);
                    System.out.println("창고 박스 수: " + mesInfo.getStockBox() + " 박스");
                    System.out.println("발주 박스 수: " + mesInfo.getBox() + " 박스");

                } else {                                    // 창고 박스 재고량이 충분할 때
                    mesInfo.setStockBox(mesInfo.stockBox - requiredBox);         // 창고 박스 = 창고 박스 수 - 필요 박스 수
                    System.out.println("창고 박스 수: " + mesInfo.getStockBox() + " 박스");
                    System.out.println("발주 박스 수: " + mesInfo.getBox() + " 박스");

                }
                // 파우치 주문량
                requiredStickPouch = (int) (Math.ceil((double) (mesInfo.pomegranate * 200)));      // 필요한 파우치스틱 수
                System.out.println("필요 스틱파우치 수: " + requiredStickPouch + "ea");
                if (mesInfo.stockStickPouch < requiredStickPouch) {          // 창고재고량이 필요량보다 작을때
                    requiredStickPouch = requiredStickPouch - mesInfo.stockStickPouch;       // 주문파우치스틱 = 필요 파우치 - 창고 파우치
                    int orderPouchRoundedUp = (int) Math.ceil((double) requiredStickPouch / 1000) * 1000;      // 파우치스틱 주문 1000단위 올림
                    mesInfo.setStickPouch(orderPouchRoundedUp);
                    System.out.println("창고 파우치스틱 수: " + mesInfo.getStockStickPouch() + "ea");
                    System.out.println("발주 파우치스틱 수: " + mesInfo.getStickPouch() + "ea");

                } else {                                    // 창고 파우치 재고량이 충분할 때
                    System.out.println("창고 스틱파우치 수: " + mesInfo.getStockStickPouch() + "ea");
                    System.out.println("발주 스틱파우치 수: " + mesInfo.getStickPouch() + "ea");

                }

                purchaseMap = new HashMap<>();
                purchaseMap.put("석류농축액",  mesInfo.pomegranate);
                purchaseMap.put("콜라겐",  mesInfo.collagen);
                purchaseMap.put("박스",  mesInfo.box);
                purchaseMap.put("스틱파우치",  mesInfo.stickPouch);
                mesInfo.setPurchaseMap(purchaseMap);

                for (Map.Entry<String, Integer> entry : mesInfo.getPurchaseMap().entrySet()) {
                    String key = entry.getKey();
                    Integer value = entry.getValue();
                    System.out.println("키: " + key + ", 값: " + value);
                }

                System.out.println("석류농축액: " + mesInfo.pomegranate + " 콜라겐: " + mesInfo.collagen + " 박스: " + mesInfo.box + " 스틱파우치: " + mesInfo.stickPouch);
            }
        } else {
                requiredPlum = mesInfo.salesQty;
            if (mesInfo.plumPackaging >= requiredPlum) {            // 재고량 > 주문량
                mesInfo.setShipmentBox(requiredPlum);                // 출하량
                mesInfo.setPlumPackaging(mesInfo.plumPackaging - requiredPlum);        // 창고 재고 뺴기
                return "enough";
            } else {
                requiredProduct = requiredPlum - mesInfo.plumPackaging;
                System.out.println("남은 재고 수: " + mesInfo.getPlumPackaging() + " 박스");
                System.out.println("주문할 원재고 수: " + requiredProduct + " 박스");
                // 매실 액기스 주문량
                mesInfo.setPlum((int) Math.ceil( (double) (requiredProduct * 25/200) / 5 ) * 5);
                System.out.println("매실농축액 주문 수: " + mesInfo.getPlum() + "kg");
                // 콜라겐 주문량
                requiredCollagen = (int) (Math.ceil((double) (mesInfo.plum * 2 / 5)));      // 필요한 콜라겐량
                System.out.println("필요 콜라겐 수: " + requiredCollagen + "kg");
                if(mesInfo.stockCollagen < requiredCollagen){          // 창고 콜라겐 재고량이 필요 콜라겐보다 작을때
                    requiredCollagen = requiredCollagen - mesInfo.getStockCollagen();              // 필요콜라겐 = 필요콜라겐 - 창고콜라겐
                    int orderBoxRoundedUp = (int) Math.ceil((double) requiredCollagen / 5) * 5;      // 콜라겐 주문 5단위 올림
                    mesInfo.setCollagen(orderBoxRoundedUp);
                    System.out.println("창고 콜라겐 수: " + mesInfo.getStockCollagen() + " kg");
                    System.out.println("발주 콜라겐 수: " + mesInfo.getCollagen() + " kg");

                } else {                                    // 창고 박스 재고량이 충분할 때
                    mesInfo.setStockCollagen(mesInfo.stockCollagen - requiredCollagen);         // 창고 콜라겐 = 창고 콜라겐 수 - 필요 콜라겐 수
                    System.out.println("창고 콜라겐 수: " + mesInfo.getStockCollagen() + " kg");
                    System.out.println("발주 콜라겐 수: " + mesInfo.getCollagen() + " kg");

                }
                // 박스 주문량
                requiredBox = (int) (Math.ceil((double) (mesInfo.plum * 200 / 25)));      // 필요한 박스량
                System.out.println("필요 박스 수: " + requiredBox + " 박스");
                if(mesInfo.stockBox < requiredBox){          // 창고 박스 재고량이 필요 박스보다 작을때
                    requiredBox = requiredBox - mesInfo.getStockBox();              // 필요박스 = 필요박스 - 창고박스
                    int orderBoxRoundedUp = (int) Math.ceil((double) requiredBox / 500) * 500;      // 박스 주문 500단위 올림
                    mesInfo.setBox(orderBoxRoundedUp);
                    System.out.println("창고 박스 수: " + mesInfo.getStockBox() + " 박스");
                    System.out.println("발주 박스 수: " + mesInfo.getBox() + " 박스");

                } else {                                    // 창고 박스 재고량이 충분할 때
                    mesInfo.setStockBox(mesInfo.stockBox - requiredBox);         // 창고 박스 = 창고 박스 수 - 필요 박스 수
                    System.out.println("창고 박스 수: " + mesInfo.getStockBox() + " 박스");
                    System.out.println("발주 박스 수: " + mesInfo.getBox() + " 박스");

                }

                // 파우치 스틱 주문량
                requiredStickPouch = (int) (Math.ceil((double) (mesInfo.plum * 200)));      // 필요한 파우치스틱 수
                System.out.println("필요 스틱파우치 수: " + requiredStickPouch + "ea");
                if(mesInfo.stockStickPouch < requiredStickPouch){          // 창고재고량이 필요량보다 작을때
                    requiredStickPouch = requiredStickPouch - mesInfo.stockStickPouch;       // 주문파우치스틱 = 필요 파우치 - 창고 파우치
                    int orderPouchRoundedUp = (int) Math.ceil((double) requiredStickPouch / 1000) * 1000;      // 파우치스틱 주문 1000단위 올림
                    mesInfo.setStickPouch(orderPouchRoundedUp);
                    System.out.println("창고 파우치스틱 수: " + mesInfo.getStockStickPouch() + "ea");
                    System.out.println("발주 파우치스틱 수: " + mesInfo.getStickPouch() + "ea");

                } else {                                    // 창고 파우치 재고량이 충분할 때
                    System.out.println("창고 스틱파우치 수: " + mesInfo.getStockStickPouch() + "ea");
                    System.out.println("발주 스틱파우치 수: " + mesInfo.getStickPouch() + "ea");

                }

                purchaseMap = new HashMap<>();
                purchaseMap.put("매실농축액",  mesInfo.plum);
                purchaseMap.put("콜라겐",  mesInfo.collagen);
                purchaseMap.put("박스",  mesInfo.box);
                purchaseMap.put("스틱파우치",  mesInfo.stickPouch);
                mesInfo.setPurchaseMap(purchaseMap);

                for (Map.Entry<String, Integer> entry : mesInfo.getPurchaseMap().entrySet()) {
                    String key = entry.getKey();
                    Integer value = entry.getValue();
                    System.out.println("키: " + key + ", 값: " + value);
                }

                System.out.println("매실농축액: " + mesInfo.plum + " 콜라겐: " + mesInfo.collagen + " 박스: " + mesInfo.box + " 스틱파우치: " + mesInfo.stickPouch);
            }
         }
        return "";
    }



    void materialArrived() { // 발주 원자재 도착 시간
        LocalDateTime salesDate = mesInfo.salesDate;        // 수주시간
        LocalDateTime stockOrderDate = null;                // 원자재 주문시간
        LocalDateTime stockInDate = null;                   // 창고 입고시간
        int leadTime = 0;                                // 리드타임
        Map<String, Integer> purchaseMap = null;
        LocalDateTime latestStockInDate = null;            // 가장 마지막 도착일자


        for (Map.Entry<String, Integer> entry : mesInfo.getPurchaseMap().entrySet()) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            if (!(value == 0 || value == null)) {             // 주문할게 있으면
                switch (key) {
                    case "양배추":
                    case "흑마늘":
                    case "박스":
                    case "파우치":
                    case "스틱파우치":
                        leadTime = 2;
                        break;
                    case "석류농축액":
                    case "매실농축액":
                    case "콜라겐":
                        leadTime = 3;
                        break;

                }
            // 원자재 발주일, 발주자재, 발주량
            if (salesDate.getDayOfWeek() == DayOfWeek.MONDAY || salesDate.getDayOfWeek() == DayOfWeek.TUESDAY ||                   // 수주일이 월, 화, 수, 목이면
                    salesDate.getDayOfWeek() == DayOfWeek.WEDNESDAY || salesDate.getDayOfWeek() == DayOfWeek.THURSDAY) {

                stockOrderDate = salesDate.plusDays(1).withHour(9).withMinute(0).withSecond(0);
                mesInfo.setRowMaterialName(key);
                mesInfo.setStockOrderDate(stockOrderDate);
                mesInfo.setRowMaterialAmount(value);

                System.out.println(
                        "\n발주자재: " + key +
                                "\n발주일: " + stockOrderDate +
                                "\n발주량: " + value);
            }else if(salesDate.getDayOfWeek()== DayOfWeek.FRIDAY){  // 수주일이 금요일이면 3일뒤 오전 9시 주문(월요일)      -> 주문시간 = 이틀뒤 오전 9시 주문(월요일)

                stockOrderDate = salesDate.plusDays(3).withHour(9).withMinute(0).withSecond(0);
                mesInfo.setRowMaterialName(key);
                mesInfo.setStockOrderDate(stockOrderDate);
                mesInfo.setRowMaterialAmount(value);
                System.out.println(
                        "\n발주자재: " + key +
                        "\n발주일: " + stockOrderDate +
                        "\n발주량: " + value);
            }

            // 원자재 입고일, 입고자재, 입고량, 원자재창고 자재량, 출고자재, 출고자재, 출고량,
            // 주문 요일이 목,금요일이면 +2일(주말)
            if (stockOrderDate.getDayOfWeek() == DayOfWeek.THURSDAY || stockOrderDate.getDayOfWeek() == DayOfWeek.FRIDAY) {
                stockOrderDate = stockOrderDate.plusDays(2);
                stockInDate = stockOrderDate.plusDays(leadTime);  // 도착일 = 주문요일 + 리드타임
            } else {
                stockInDate = stockOrderDate.plusDays(leadTime);  // 도착일 = 주문요일 + 리드타임
            }

            // 입고 가능한 시간인지 확인하고, 월, 수, 금 오전 10시에 입고시간 설정
            DayOfWeek dayOfWeek = stockInDate.getDayOfWeek();
            if (dayOfWeek == DayOfWeek.MONDAY || dayOfWeek == DayOfWeek.WEDNESDAY || dayOfWeek == DayOfWeek.FRIDAY) {
                stockInDate = stockInDate.withHour(10).withMinute(0).withSecond(0);
            } else if (dayOfWeek == DayOfWeek.TUESDAY || dayOfWeek == DayOfWeek.THURSDAY) {
                stockInDate = stockInDate.plusDays(1).withHour(10).withMinute(0).withSecond(0);
            } else if (dayOfWeek == DayOfWeek.SATURDAY) {
                stockInDate = stockInDate.plusDays(2).withHour(10).withMinute(0).withSecond(0);
            } else if (dayOfWeek == DayOfWeek.SUNDAY) {
                stockInDate = stockInDate.plusDays(1).withHour(10).withMinute(0).withSecond(0);
            }

            mesInfo.setArrivalMaterial(stockInDate);      // 도착일
            System.out.println("도착일: " + mesInfo.getArrivalMaterial());
            System.out.println("자재명: " + mesInfo.getRowMaterialName());
            System.out.println("수량: " + mesInfo.getRowMaterialAmount());

            // 가장 마지막 도착일자인지 확인 후 업데이트
            if (latestStockInDate == null || stockInDate.isAfter(latestStockInDate)) {
                latestStockInDate = stockInDate;
            }
            }
        }
        mesInfo.setLastStockInDate(latestStockInDate);
        System.out.println();
        System.out.println("가장 마지막 도착일: " + mesInfo.getLastStockInDate());
    }



    void measurement(){ // 원료계량

        int workAmount = mesInfo.cabbage;

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
        mesInfo.setMeasurementOutput(workAmount);

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
        mesInfo.setPreProcessingOutput(workAmount);

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
        mesInfo.setExtractionOutput(mesInfo.preProcessingOutput * 16 / 10);
    }

    //충진
    void fill(){
        LocalDateTime start = lunchAndLeaveTimeStartCheck(mesInfo.getExtraction());  // 액제조제 시스템 완료시간
        LocalDateTime end = lunchAndLeaveTimeStartCheck(start.plusMinutes(mesInfo.getLeadFill())); // 시작 시간이 근무시간이 맞는지 확인, 리드타임 더함


        int output = 0; // 추출액, 혼합액 총량
        int hour_capacity = 0; // 시간당 생산가능량

      output=mesInfo.extractionOutput;//추출/혼합 완료량
//        hour_capacity = (mesInfo.productName.equals("양배추즙") || mesInfo.productName.equals("흑마늘즙")) ? 1750 : 1500; // 충진시 기계를 1개씩만 사용하면
        hour_capacity = (mesInfo.productName.equals("양배추즙") || mesInfo.productName.equals("흑마늘즙")) ? 1750*2 : 1500*2; // 충진시 기계 2개를 동시에 돌리면

        double totalPackages = (double) output / ((mesInfo.productName.equals("양배추즙") || mesInfo.productName.equals("흑마늘즙")) ? 0.08 : 0.015); // 혼합액 양으로 만들 수 있는 포 수 (총)
        mesInfo.setFillOutPut((int)totalPackages);//총 포 수 저장
      
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
        System.out.println(output);
        hour_capacity = 5000; //1시간당 5000개 검사가능

        //double totalPackages = (double) output / ((mesInfo.productName.equals("양배추즙") || mesInfo.productName.equals("흑마늘즙")) ? 0.08 : 0.015); // 혼합액 양으로 만들 수 있는 포 수 (총)
        int totalHours = (int) Math.floor((double)output / hour_capacity); // 총 소요 시간 계산
        int remainingExam = (int) Math.floor(((double)output  % hour_capacity) * (60.0 / hour_capacity)); // 남은 포장 개수 계산

//         System.out.println("총 시간 : "+totalHours);
//         System.out.println("총 분 : "+remainingExam);


        end = end.plusHours(totalHours); // 작업 끝나는 시간 계산
        end = end.plusMinutes(remainingExam); // 남은 포장 개수에 대한 분 단위 계산

        mesInfo.setExamination(end); // 검사 완료시간 저장
        mesInfo.setExaminationOutput(output);
        System.out.println("검사 시작시간: " + start + "\n검사 완료시간: " + end);
    }

//열교환(식힘)
    void cooling(){
        LocalDateTime start = mesInfo.getExamination(); // 검사완료 시간
        LocalDateTime end = start.plusDays(1).withHour(9).withMinute(0).withSecond(0); //다음날 오전 종료
        mesInfo.setCooling(end); // 검사 완료시간 저장
        System.out.println("열교환 시작시간: " + start + "\n열교환 완료시간: " + end);
    }

      
      

    // 포장
    void packaging(){
//        int inputEa = 0;                                  // inputEa          : 충진/식힘 후 포장할 전체 낱개(ea) 수
        int inputEa = mesInfo.examinationOutput;                                  // inputEa          : 충진/식힘 후 포장할 전체 낱개(ea) 수
        System.out.println("충진 ea: " + inputEa + "ea");

        long outputBox = 0;                                // 총 만들어질 box 수 : inputEa / 30(양배추, 흑마늘)   inputEa / 25(석류젤리스틱, 매실젤리스틱)
        int outputEa = 0;                                 // 남은 ea 수        : inputEa % 30(양배추, 흑마늘)   inputEa % 25(석류젤리스틱, 매실젤리스틱)
        double packagingTime = 0;                           // 포장시간 (분)
        // 포장 관련 상수 정의
        int packagingTimePerBoxSeconds = 18;             // 1박스당 포장 시간 (초)

        if (mesInfo.productName.equals("양배추즙") || mesInfo.productName.equals("흑마늘즙")){  // 양배추즙, 흑마늘즙이면
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
        System.out.println("포장 리드타임 시작 : " + leadTimeStart );
        System.out.println("포장 리드타임 끝 : " + leadTimeEnd);

        LocalDateTime packingStart = leadTimeEnd;                                   // 포장 시작 시간
        LocalDateTime packingEnd = packingStart.plusMinutes((long)packagingTime);   // 포장 종료 시간

        System.out.println("포장 시작시간 : " + packingStart);

        if (packingEnd.getHour() >= lunchStartTime.getHour() && packingEnd.getHour()<=lunchEndTime.getHour()) {    // 포장끝나는시간 점심시간, 시작시간 점심시간->리드타임에서 거름
            Duration availableTime = Duration.between(packingStart.toLocalTime(), lunchStartTime);
            long nowPackingBox = availableTime.toSeconds() / 18;                     // 지금 작업한 박스
            outputBox = outputBox - nowPackingBox;                                   // 남은 박스 : 오후 작업할 박스
            packagingTime = packagingTime-availableTime.toMinutes();                 // 남은 포장 시간
            packingStart = packingStart.withHour(13).withMinute(0).withSecond(0);    // 1시부터 다시 시작
            packingEnd = packingStart.plusMinutes((long)packagingTime);              // 포장 끝나는시간

        }

        if(packingEnd.getHour() >= endWorkTime.getHour()){                      // 포장 끝나는 시간이 근무끝시간(18시)보다 뒤이거나 같으면
            Duration availableTime = Duration.between(packingStart.toLocalTime(), endWorkTime);
            long todayPackingBox = availableTime.toSeconds() / 18;              // 오늘 작업한 박스
              outputBox = outputBox - todayPackingBox;                          // 남은 박스 : 익일 작업할 박스

            if(packingStart.getDayOfWeek() == DayOfWeek.FRIDAY){ // 금요일 이면
                packingStart = packingStart.plusDays(3).withHour(9).withMinute(0).withSecond(0);

            }else {
                packingStart = packingStart.plusDays(1).with(startWorkTime);
            }

            packagingTime = packagingTime-availableTime.toMinutes();            // 남은 포장 시간
            packingEnd = packingStart.plusMinutes((long)packagingTime);
        }

        System.out.println("포장 완료시간 : " + packingEnd);

        mesInfo.setPackaging(packingEnd); // 포장 완료시간 set

    }


//---
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
