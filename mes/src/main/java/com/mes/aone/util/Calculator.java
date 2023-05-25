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

    public String purChaseAmount() { // 발주량 계산
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


    public  void materialArrived() { // 발주 원자재 도착 시간
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


    public void measurement() { // 원료계량
        System.out.println("수주일: " + mesInfo.salesDay);
        LocalDateTime currentTime = mesInfo.salesDay; // 원료계량 시작시간

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
        mesInfo.setStartMeasurement(currentTime);
        currentTime = currentTime.plusMinutes(mesInfo.leadMeasurement); // 원료계량 리드타임 더하기

        currentTime = lunchAndLeaveTimeStartCheck(currentTime); // 작업 시작 시 비근무 시간 체크(공정 시작시간 리턴)
        currentTime = lunchAndLeaveTimeFinishCheck(currentTime.plusMinutes(30), currentTime); // 작업 시작 시 비근무 시간 체크(작업 시작시간 리턴)
        currentTime = currentTime.plusMinutes(30); // 원료계량 작업시간 더하기
        System.out.println("원료계량 완료시간: " + currentTime);

        mesInfo.setFinishMeasurement(currentTime); // 원료계량 완료시간 set
        mesInfo.setNowMeasurementOutput(workAmount); //

    }

    public void preProcessing() { // 전처리
        LocalDateTime currentTime = mesInfo.getFinishMeasurement(); // 원료계량 완료시간을 전처리 시작시간으로 설정

        int workAmount = mesInfo.nowMeasurementOutput; // 작업량
        int output = 0; // 생산량

        for (int i = 0; i < Math.ceil( workAmount / 1000.0); i++) { // 작업 반복횟수 만큼 실행 ex) 2500kg 이면 3번 반복
            currentTime = lunchAndLeaveTimeStartCheck(currentTime); // 작업 시작 시 비근무 시간 체크(작업 시작시간 리턴)
            currentTime = lunchAndLeaveTimeFinishCheck(currentTime.plusMinutes(mesInfo.leadPreProcessing), currentTime); // 작업 완료 시 비근무 시간 체크(작업 시작시간 리턴)
            System.out.println("전처리 " + (i + 1) + " 시작시간: " + currentTime);
            mesInfo.startPreProcessing.add(currentTime);
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
            mesInfo.finishPreProcessing.add(currentTime);
            mesInfo.nowPreProcessingOutput.add(output);
        }

        mesInfo.setPreProcessing(currentTime); // 전처리 완료시간 set
        mesInfo.setPreProcessingOutput(workAmount); // 전처리 생산량

    }

    public void extraction() { // 추출 및 혼합
        LocalDateTime currentTime = null; // 추출 시간 변수 선언

        int output = 0; // 생산량
        int workAmount = mesInfo.preProcessingOutput; // 작업량

        if (mesInfo.productName.equals("양배추즙")) { // 양배추 추출 공정
            for (int i = 0; i < mesInfo.finishPreProcessing.size(); i++) { // 전처리 작업 횟수 만큼 반복
                currentTime = mesInfo.finishPreProcessing.get(i);
                if (mesInfo.pastExtractionMachine1.isBefore(mesInfo.pastExtractionMachine2)) { // 추출기1이 먼저 끝날 때
                    if (currentTime.isBefore(mesInfo.pastExtractionMachine1)) { // 추출기 1의 공정계획이 있으면
                        currentTime = mesInfo.pastExtractionMachine1; // 추출기 1의 공정계획이 끝나는 시간에 시작
                    }
                    currentTime = lunchAndLeaveTimeStartCheck(currentTime);
                    currentTime = lunchAndLeaveTimeFinishCheck(currentTime.plusMinutes(mesInfo.leadExtraction), currentTime);
                    System.out.println("추출기1 - 추출" + (i + 1) + "번째 시작시간: " + currentTime);
                    mesInfo.startExtractionMachine1.add(currentTime);
                    mesInfo.startExtraction.add(currentTime);
                    currentTime = currentTime.plusMinutes(mesInfo.leadExtraction);

                    currentTime = lunchAndLeaveTimeStartCheck(currentTime);
                    currentTime = currentTime.plusHours(72);

                    mesInfo.nowExtractionMachine1Output.add(1600);
                    mesInfo.finishExtractionMachine1.add(currentTime);
                    mesInfo.setPastExtractionMachine1(currentTime);
                    System.out.println("추출기1 - 추출" + (i + 1) + "번째 완료시간: " + currentTime);

                } else { // 추출기2가 먼저 끝날 때
                    if (currentTime.isBefore(mesInfo.pastExtractionMachine2)) { // 추출기 1의 공정계획이 있으면
                        currentTime = mesInfo.pastExtractionMachine2; // 추출기 2의 공정계획이 끝나는 시간에 시작
                    }
                    currentTime = lunchAndLeaveTimeStartCheck(currentTime);
                    currentTime = lunchAndLeaveTimeFinishCheck(currentTime.plusMinutes(mesInfo.leadExtraction), currentTime);
                    System.out.println("추출기2 - 추출" + (i + 1) + "번째 시작시간: " + currentTime);
                    mesInfo.startExtractionMachine2.add(currentTime);
                    mesInfo.startExtraction.add(currentTime);
                    currentTime = currentTime.plusMinutes(mesInfo.leadExtraction);

                    currentTime = lunchAndLeaveTimeStartCheck(currentTime);
                    currentTime = currentTime.plusHours(72);

                    mesInfo.nowExtractionMachine2Output.add(1600);
                    mesInfo.finishExtractionMachine2.add(currentTime);
                    mesInfo.setPastExtractionMachine2(currentTime);
                    System.out.println("추출기2 - 추출" + (i + 1) + "번째 완료시간: " + currentTime);
                }
                mesInfo.finishExtraction.add(currentTime); // 추출 및 혼합 공정계획 추가
                mesInfo.nowExtractionOutput.add(1600); // 추출 및 혼합 생산량 추가
            }
        } else if (mesInfo.productName.equals("흑마늘즙")) {
            for (int i = 0; i < Math.ceil(mesInfo.garlic / 500.0); i++) {
                currentTime = mesInfo.finishPreProcessing.get((int) Math.floor(i / 2.0));
                if (mesInfo.pastExtractionMachine1.isBefore(mesInfo.pastExtractionMachine2)) { // 추출기1이 먼저 끝날 때
                    if (currentTime.isBefore(mesInfo.pastExtractionMachine1)) { // 추출기 1의 공정계획이 있으면
                        currentTime = mesInfo.pastExtractionMachine1; // 추출기 1의 공정계획이 끝나는 시간에 시작
                    }
                    currentTime = lunchAndLeaveTimeStartCheck(currentTime);
                    currentTime = lunchAndLeaveTimeFinishCheck(currentTime.plusMinutes(mesInfo.leadExtraction), currentTime);
                    System.out.println("추출기1 - 추출" + (i + 1) + "번째 시작시간: " + currentTime);
                    mesInfo.startExtractionMachine1.add(currentTime);
                    mesInfo.startExtraction.add(currentTime);
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

                    mesInfo.nowExtractionMachine1Output.add(output);
                    mesInfo.finishExtractionMachine1.add(currentTime); // 추출기 1 공정계획 추가
                    mesInfo.setPastExtractionMachine1(currentTime); // 추출기 1의 마지막 공정시간 set
                    System.out.println("추출기1 - 추출" + (i + 1) + "번째 완료시간: " + currentTime);

                } else { // 추출기2가 먼저 끝날 때
                    if (currentTime.isBefore(mesInfo.pastExtractionMachine2)) { // 추출기 2의 공정계획이 있으면
                        currentTime = mesInfo.pastExtractionMachine2; // 추출기 2의 공정계획이 끝나는 시간에 시작
                    }
                    currentTime = lunchAndLeaveTimeStartCheck(currentTime);
                    currentTime = lunchAndLeaveTimeFinishCheck(currentTime.plusMinutes(mesInfo.leadExtraction), currentTime);
                    System.out.println("추출기2 - 추출" + (i + 1) + "번째 시작시간: " + currentTime);
                    mesInfo.startExtractionMachine2.add(currentTime);
                    mesInfo.startExtraction.add(currentTime);
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

                    mesInfo.nowExtractionMachine2Output.add(output);
                    mesInfo.finishExtractionMachine2.add(currentTime); // 추출기2 공정계획 추가
                    mesInfo.setPastExtractionMachine2(currentTime); // 추출기2의 마지막 공정시간 set
                    System.out.println("추출기2 - 추출" + (i + 1) + "번째 완료시간: " + currentTime);
                }
                mesInfo.nowExtractionOutput.add(output); // 추출 및 혼합 생산량 추가
                mesInfo.finishExtraction.add(currentTime); // 추출 및 혼합 공정계획 추가
            }
        } else {
            currentTime = mesInfo.getFinishMeasurement(); // 원료계량 완료시간을 혼합 시작시간으로 설정

            if (mesInfo.pastExtractionMachine1.isBefore(mesInfo.pastExtractionMachine2)) { // 추출기1이 먼저 끝날 때
                if (currentTime.isBefore(mesInfo.pastExtractionMachine1)) { // 추출기 1의 공정계획이 있으면
                    currentTime = mesInfo.pastExtractionMachine1; // 추출기 1의 공정계획이 끝나는 시간에 시작
                }
                currentTime = lunchAndLeaveTimeStartCheck(currentTime);
                currentTime = lunchAndLeaveTimeFinishCheck(currentTime.plusMinutes(mesInfo.leadMixing), currentTime);
                System.out.println("추출기1 시작시간: " + currentTime);
                mesInfo.startExtractionMachine1.add(currentTime);
                mesInfo.startExtraction.add(currentTime);
                currentTime = currentTime.plusMinutes(mesInfo.leadMixing); // 혼합 리드타임 더하기

                currentTime = lunchAndLeaveTimeStartCheck(currentTime);
                currentTime = currentTime.plusHours(8); // 가열시간 더하기

                System.out.println("추출기1 완료시간: " + currentTime);
                mesInfo.nowExtractionMachine1Output.add(mesInfo.nowMeasurementOutput*3);
                mesInfo.finishExtractionMachine1.add(currentTime); // 추출기1 공정계획 추가
                mesInfo.setPastExtractionMachine1(currentTime); // 추출기1의 마지막 공정시간 set
            } else { // 추출기2가 먼저 끝날 때
                if (currentTime.isBefore(mesInfo.pastExtractionMachine2)) { // 추출기 2의 공정계획이 있으면
                    currentTime = mesInfo.pastExtractionMachine2; // 추출기 2의 공정계획이 끝나는 시간에 시작
                }
                currentTime = lunchAndLeaveTimeStartCheck(currentTime);
                currentTime = lunchAndLeaveTimeFinishCheck(currentTime.plusMinutes(mesInfo.leadMixing), currentTime);
                System.out.println("추출기2 시작시간: " + currentTime);
                mesInfo.startExtractionMachine2.add(currentTime);
                mesInfo.startExtraction.add(currentTime);
                currentTime = currentTime.plusMinutes(mesInfo.leadMixing); // 혼합 리드타임 더하기

                currentTime = lunchAndLeaveTimeStartCheck(currentTime);
                currentTime = currentTime.plusHours(8); // 가열시간 더하기

                System.out.println("추출기2 완료시간: " + currentTime);
                mesInfo.nowExtractionMachine2Output.add(mesInfo.nowMeasurementOutput*3);
                mesInfo.finishExtractionMachine2.add(currentTime); // 추출기2 공정계획 추가
                mesInfo.setPastExtractionMachine2(currentTime); // 추출기2의 마지막 공정시간 set
            }
            mesInfo.finishExtraction.add(currentTime); // 추출 및 혼합 공정계획 추가
            mesInfo.nowExtractionOutput.add(mesInfo.nowMeasurementOutput*3);
        }
    }

    //충진
    public void fill() {
        LocalDateTime currentTime = null; // 충진 시간 변수 선언
        List<LocalDateTime> extractionTimeList = mesInfo.finishExtraction; // 추출/혼합 완료시간 리스트
        LocalDateTime beforeFillingTime = (mesInfo.productName.equals("양배추즙") || mesInfo.productName.equals("흑마늘즙")) ? mesInfo.pastFillingLiquidMachine : mesInfo.pastFillingJellyMachine; // 이전 충진 공정 완료시간

        List<Integer> extractionOutputList = mesInfo.nowExtractionOutput; // 추출액 생산량 리스트
        int workAmount = 0; // 작업량
        int packages = 0; // 혼합액 양으로 만들 수 있는 포 수 (총)
        int hourCapacity = (mesInfo.productName.equals("양배추즙") || mesInfo.productName.equals("흑마늘즙")) ? 1750 * 2 : 1500 * 2; // 시간당 생산가능량
        double totalHours = 0; // 총 소요 시간 계산
        int addHours = 0; // 시간 정수형으로 변환
        int addMinutes = 0;

        for (int i=0; i<extractionTimeList.size(); i++){ // 추출 공정 수 만큼 반복
            currentTime = extractionTimeList.get(i); // i번째 추출 및 혼합 완료시간 (충진 시작시간)
            workAmount = extractionOutputList.get(i);
            if (mesInfo.productName.equals("양배추즙")){
                packages = (int) (workAmount/0.08);
            }else if (mesInfo.productName.equals("흑마늘즙")){
                packages = (int) (workAmount/0.02);
            }else {
                packages = (int) (workAmount/0.015);
            }
            totalHours = (double) packages / hourCapacity;
            addHours = (int) totalHours;
            addMinutes = (int) ((totalHours - addHours) * 60);
            if(currentTime.isBefore(beforeFillingTime)){
                currentTime = beforeFillingTime; // 이전 공정이 있으면 공정완료시간을 시작시간으로
            }
            // 리드 타임
            currentTime = lunchAndLeaveTimeStartCheck(currentTime);
            currentTime = lunchAndLeaveTimeFinishCheck(currentTime.plusMinutes(20), currentTime);
            System.out.println("충진" + (i+1) + " 번째 시작시간: " + currentTime);
            ((mesInfo.productName.equals("양배추즙") || mesInfo.productName.equals("흑마늘즙")) ? mesInfo.startFillingLiquidMachine : mesInfo.startFillingJellyMachine).add(currentTime);
            mesInfo.startFilling.add(currentTime);
            currentTime = currentTime.plusMinutes(20);

            // 작업 시간
            currentTime = lunchAndLeaveTimeStartCheck(currentTime);
            currentTime = currentTime.plusHours(addHours).plusMinutes(addMinutes);
            System.out.println("충진" + (i+1) + " 번째 완료시간: " + currentTime);

            beforeFillingTime = currentTime;
            // 즙이면 즙충진기, 젤리면 젤리충진기에 생산량 저장
            ((mesInfo.productName.equals("양배추즙") || mesInfo.productName.equals("흑마늘즙")) ? mesInfo.nowFillingLiquidMachineOutput : mesInfo.nowFillingJellyMachineOutput).add(packages);
            // 즙이면 즙충진기, 젤리면 젤리충진기에 완료시간 저장
            ((mesInfo.productName.equals("양배추즙") || mesInfo.productName.equals("흑마늘즙")) ? mesInfo.finishFillingLiquidMachine : mesInfo.finishFillingJellyMachine).add(currentTime);
            mesInfo.finishFilling.add(currentTime);
            mesInfo.nowFillingOutput.add(packages);
        }
    }

    //검사
    public void examination() {
        LocalDateTime currentTime = null; // 검사 시간 변수 선언
        List<LocalDateTime> fillingTimeList = mesInfo.finishFilling; // 충진 완료시간 리스트
        LocalDateTime beforeExaminationTime = mesInfo.pastExaminationMachine; // 이전 검사 공정 완료시간

        List<Integer> fillingOutputList = mesInfo.nowFillingOutput; // 충진 생산량 리스트
        int workAmount = 0; // 작업량
        int hourCapacity = 5000; // 시간당 생산가능량
        double totalHours = 0; // 총 소요 시간 계산
        int addHours = 0; // 시간 정수형으로 변환
        int addMinutes = 0;

        for (int i=0; i<fillingTimeList.size(); i++){ // 충진 공정 수 만큼 반복
            currentTime = fillingTimeList.get(i); // i번째 충진 완료시간 (검사 시작시간)
            workAmount = fillingOutputList.get(i);

            totalHours = (double) workAmount / hourCapacity;
            addHours = (int) totalHours;
            addMinutes = (int) ((totalHours - addHours) * 60);
            if(currentTime.isBefore(beforeExaminationTime)){
                currentTime = beforeExaminationTime; // 이전 공정이 있으면 공정완료시간을 시작시간으로
            }
            // 리드 타임
            currentTime = lunchAndLeaveTimeStartCheck(currentTime);
            currentTime = lunchAndLeaveTimeFinishCheck(currentTime.plusMinutes(mesInfo.leadExamination), currentTime);
            System.out.println("검사" + (i+1) + " 번째 시작시간: " + currentTime);
            mesInfo.startExamination.add(currentTime);
            currentTime = currentTime.plusMinutes(mesInfo.leadExamination);

            // 작업 시간
            currentTime = lunchAndLeaveTimeStartCheck(currentTime);
            currentTime = currentTime.plusHours(addHours).plusMinutes(addMinutes);
            System.out.println("검사" + (i+1) + " 번째 완료시간: " + currentTime);

            beforeExaminationTime = currentTime;
            mesInfo.finishExamination.add(currentTime);
            mesInfo.nowExaminationOutput.add(workAmount);
        }
    }

    //열교환(식힘)
    public void cooling() {
        LocalDateTime currentTime = null; // 검사 시간 변수 선언
        List<LocalDateTime> examinationTimeList = mesInfo.finishExamination; // 검사 완료시간 리스트

        List<Integer> examinationOutput = mesInfo.nowExaminationOutput; // 검사 생산량 리스트
        int workAmount = 0; // 작업량

        for (int i=0; i<examinationTimeList.size(); i++){ // 충진 공정 수 만큼 반복
            mesInfo.startCooling.add(examinationTimeList.get(i)); // 냉각 시작 시간 리스트 추가
            currentTime = examinationTimeList.get(i).plusDays(1).withHour(9).withMinute(0).withSecond(0);
            workAmount = examinationOutput.get(i);
            System.out.println("냉각" + (i+1) + " 번째 완료시간: " + currentTime);

            mesInfo.finishCooling.add(currentTime);
            mesInfo.nowCoolingOutput.add(workAmount);
        }
    }


    // 포장
    public void packaging() {
        LocalDateTime currentTime = null; // 충진 시간 변수 선언
        List<LocalDateTime> coolingTimeList = mesInfo.finishCooling; // 냉각 완료시간 리스트
        LocalDateTime beforePackagingTime = mesInfo.pastPackagingTime; // 이전 포장 공정 완료시간

        List<Integer> coolingOutputList = mesInfo.nowCoolingOutput; // 냉각 생산량 리스트
        int workAmount = 0; // 작업량(포)
        int box = 0; // 작업량(포)로 만들 수 있는 박스 수
        int hourCapacity = 200; // 시간당 박스 생산량
        double totalHours = 0; // 총 소요 시간 계산
        int addHours = 0; // 시간 정수형으로 변환
        int addMinutes = 0;

        for (int i=0; i<coolingTimeList.size(); i++){ // 냉각 공정 수 만큼 반복
            currentTime = coolingTimeList.get(i);
            workAmount = coolingOutputList.get(i); // i번째 냉각 생산량 (포)
            box = (mesInfo.productName.equals("양배추즙") || mesInfo.productName.equals("흑마늘즙") ? workAmount/30 : workAmount/25 ); // 박스 생산량

            if(currentTime.isBefore(beforePackagingTime)){
                currentTime = beforePackagingTime; // 이전 공정이 있으면 공정완료시간을 시작시간으로
            }
            
            // 리드 타임
            currentTime = lunchAndLeaveTimeStartCheck(currentTime);
            currentTime = lunchAndLeaveTimeFinishCheck(currentTime.plusMinutes(mesInfo.leadPackaging), currentTime);
            System.out.println("포장" + (i+1) + " 번째 시작시간: " + currentTime);
            mesInfo.startPackaging.add(currentTime);
            currentTime = currentTime.plusMinutes(mesInfo.leadPackaging);

            // 작업 시간
            for(int k=0; k<box; k++){
                currentTime = lunchAndLeaveTimeStartCheck(currentTime);
                currentTime = lunchAndLeaveTimeFinishCheck(currentTime.plusSeconds(18), currentTime);
                currentTime = currentTime.plusSeconds(18);
            }
            System.out.println("포장" + (i+1) + " 번째 완료시간: " + currentTime);

            beforePackagingTime = currentTime;
            mesInfo.finishPackaging.add(currentTime);
            mesInfo.nowPackagingOutput.add(box);
        }
        mesInfo.setEstDelivery(currentTime);
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