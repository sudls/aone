package com.mes.aone.service;

import com.mes.aone.constant.MaterialState;
import com.mes.aone.constant.ShipmentState;
import com.mes.aone.constant.Status;
import com.mes.aone.constant.StockManageState;
import com.mes.aone.dto.OrderDTO;
import com.mes.aone.entity.*;
import com.mes.aone.repository.*;
import com.mes.aone.util.Calculator;
import com.mes.aone.util.MESInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;



import java.util.ArrayList;
import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class SalesOrderService {
    private final SalesOrderRepository salesOrderRepository;
    private final WorkOrderRepository workOrderRepository;
    private final ProcessPlanRepository processPlanRepository;
    private final FacilityRepository facilityRepository;
    private final MaterialRepository materialRepository;
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final MaterialStorageRepository materialStorageRepository;
    private final ShipmentRepository shipmentRepository;
    private final WorkResultRepository workResultRepository;
    private final StockRepository stockRepository;
    private final StockManageRepository stockManageRepository;
    private final ProductionRepository productionRepository;
    private final LotRepository lotRepository;


    // 수주 등록
    public Long createSalesOrder(OrderDTO orderDTO) throws Exception{
        SalesOrder salesOrder = orderDTO.createSalesOrder();
        salesOrderRepository.save(salesOrder);

        return salesOrder.getSalesOrderId();
    }


    // 수주 확정
    public void confirmSalesOrderState(String[] selectedIds){

        for (String salesOrderId : selectedIds) {

            Long orderId = Long.parseLong(salesOrderId); // 형변환 String -> Long
            SalesOrder salesOrder = salesOrderRepository.findBySalesOrderId(orderId);
            WorkOrder workOrder = workOrderRepository.findBySalesOrder(salesOrder);
            workOrder.setWorkStatus(Status.B); // 작업지시를 진행중으로 변경

            if (salesOrder != null) {

                salesOrder.setSalesStatus(Status.B); // 상태 업데이트
                salesOrder.setSalesDate(LocalDateTime.now()); // 수주일 업데이트

                MESInfo mesInfo = new MESInfo();
                Calculator calculator = new Calculator(mesInfo);
                mesInfo.setProductName(salesOrder.getProductName()); // 수주 제품명
                mesInfo.setSalesQty(salesOrder.getSalesQty()); // 수주량
                mesInfo.setSalesDay(salesOrder.getSalesDate()); // 수주일
                mesInfo.setSalesOrderId(salesOrder.getSalesOrderId());// 수주 아이디

                // 완제품 재고량 세팅
//                mesInfo.setCabbagePackaging(getFnishedProduct("양배추즙"));
//                mesInfo.setGarlicPackaging(getFnishedProduct("흑마늘즙"));
//                mesInfo.setPomegranatePackaging(getFnishedProduct("석류젤리스틱"));
//                mesInfo.setPlumPackaging(getFnishedProduct("매실젤리스틱"));
//                System.out.println("완제품 재고량 세팅 완료");

                // 공정 계획 세팅
                mesInfo.setPastMeasurement(getProcessFinishTime("원료계량"));
                mesInfo.setPastPreProcessingMachine(getProcessFinishTime("전처리"));
                mesInfo.setPastExtractionMachine1(getFacilityFinishTime("extraction_1"));
                mesInfo.setPastExtractionMachine2(getFacilityFinishTime("extraction_2"));
                mesInfo.setPastFillingLiquidMachine(getFacilityFinishTime("pouch_1"));
                mesInfo.setPastFillingJellyMachine(getFacilityFinishTime("liquid_stick_1"));
                mesInfo.setPastExaminationMachine(getFacilityFinishTime("inspection"));
                mesInfo.setPastPackagingTime(getProcessFinishTime("포장"));

                // 예상납품일 계산기 실행
                if (mesInfo.getProductName().equals("양배추즙") || mesInfo.getProductName().equals("흑마늘즙")){ // 즙 공정
                    String purchaseCheck = calculator.purChaseAmount(); // 발주량 계산 메서드 실행
                    if (purchaseCheck.equals("enough")){ // 재고가 충분하면
                        mesInfo.setEstDelivery(LocalDateTime.now()); // 당일 출고
                        System.out.println("재고가 충분하면 들어옴");
                    } else {
                        calculator.materialArrived(); // 발주 원자재 도착시간 메서드 실행
                        calculator.measurement(); // 원료계량 메서드 실행
                        calculator.preProcessing(); // 전처리 메서드 실행
                        calculator.extraction(); // 추출 메서드 실행
                        calculator.fill();//충진 메서드 실행
                        calculator.examination();//검사 메서드 실행
                        calculator.cooling();//열교환 메서드 실행
                        calculator.packaging(); // 포장 메서드 실행
                    }
                }else { // 젤리스틱 공정
                    String purchaseCheck = calculator.purChaseAmount(); // 발주량 계산 메서드 실행
                    if (purchaseCheck.equals("enough")){ //재고가 충분하면
                        mesInfo.setEstDelivery(LocalDateTime.now()); // 당일 출고
                    } else {
                        calculator.materialArrived(); // 발주 원자재 도착시간 메서드 실행
                        calculator.measurement(); // 원료계량 메서드 실행
                        calculator.extraction(); // 추출 메서드 실행
                        calculator.fill();//충진 메서드 실행
                        calculator.examination();//검사 메서드 실행
                        calculator.cooling();//열교환 메서드 실행
                        calculator.packaging(); // 포장 메서드 실행
                    }
                }

                salesOrder.setEstDelivery(mesInfo.getEstDelivery()); // 예상 납품일 업데이트
                System.out.println("예상 납품일 업데이트: " + mesInfo.getEstDelivery());

                createProcessPlan(mesInfo);
                System.out.println("공정계획 들어감");
                createPurchaseOrder(mesInfo);
                System.out.println("발주 현황 들어감");
                createMaterialStorage(mesInfo);
                System.out.println("원자재 입출고 들어감");
                createWorkResult(mesInfo, workOrder);
                System.out.println("작업 실적 들어감");

                salesOrderRepository.save(salesOrder);
                workOrderRepository.save(workOrder);

            }

        }
    }

    @Transactional
    public void standByState(List<SalesOrder> salesOrderStateAs){
        for (SalesOrder salesOrderStateA : salesOrderStateAs) {
            MESInfo mesInfo = new MESInfo();
            Calculator calculator = new Calculator(mesInfo);
            mesInfo.setProductName(salesOrderStateA.getProductName());
            mesInfo.setSalesQty(salesOrderStateA.getSalesQty());
            mesInfo.setSalesDay(LocalDateTime.now());         // 현재 업데이트 시간

            mesInfo.setPastPreProcessingMachine(getProcessFinishTime("전처리"));
            mesInfo.setPastExtractionMachine1(getFacilityFinishTime("extraction_1"));
            mesInfo.setPastExtractionMachine2(getFacilityFinishTime("extraction_2"));
            mesInfo.setPastFillingLiquidMachine(getFacilityFinishTime("pouch_1"));
            mesInfo.setPastFillingJellyMachine(getFacilityFinishTime("liquid_stick_1"));
            mesInfo.setPastExaminationMachine(getFacilityFinishTime("inspection"));
            mesInfo.setPastPackagingTime(getProcessFinishTime("포장"));


            // 예상납품일 계산기 실행
            String purchaseCheck = calculator.purChaseAmount(); // 발주량 계산 메서드 실행
            if (purchaseCheck.equals("enough")){ // 완제품 재고가 충분하면
                mesInfo.setEstDelivery(LocalDateTime.now()); // 당일 출고
            } else {                                             // 완제품 재고 불충분 시
                calculator.materialArrived(); // 발주 원자재 도착시간 메서드 실행
                calculator.measurement(); // 원료계량 메서드 실행
                if (mesInfo.getProductName().equals("양배추즙") || mesInfo.getProductName().equals("흑마늘즙"))  // 즙 공정일 경우 전처리
                    calculator.preProcessing(); // 전처리 메서드 실행
                calculator.extraction(); // 추출 메서드 실행
                calculator.fill();//충진 메서드 실행
                calculator.examination();//검사 메서드 실행
                calculator.cooling();//열교환 메서드 실행
                calculator.packaging(); // 포장 메서드 실행
            }

            // 예상납품일 업데이트
            salesOrderStateA.setEstDelivery(mesInfo.getEstDelivery());
        }
    }

    // 수주 취소
    public void cancelSalesOrderState(String[] selectedIds){
        for (String salesOrderId : selectedIds) {
            Long orderId = Long.parseLong(salesOrderId); // 형변환 String -> Long
            SalesOrder salesOrder = salesOrderRepository.findBySalesOrderId(orderId);
            if (salesOrder != null) {
                salesOrder.setSalesStatus(Status.C); // 상태
                salesOrderRepository.save(salesOrder);
            }
        }
    }

    // 수주 삭제
    public void deleteSalesOrderState(String[] selectedIds){
        for (String salesOrderId : selectedIds) {
            Long orderId = Long.parseLong(salesOrderId); // 형변환 String -> Long
            SalesOrder salesOrder = salesOrderRepository.findBySalesOrderId(orderId);
            salesOrderRepository.delete(salesOrder);
        }
    }

//    // 수주 검색
//    public List<SalesOrder> searchSalesOrder(String productName, String vendorId, LocalDateTime startDateTime, LocalDateTime endDateTime, Status salesStatus) {
//        QSalesOrder qSalesOrder = QSalesOrder.salesOrder;
//        BooleanBuilder builder = new BooleanBuilder();
//
//        if(productName != null){
//            builder.and(qSalesOrder.productName.eq(productName));
//        }
//        if(vendorId != null){
//            builder.and(qSalesOrder.vendorId.eq(vendorId));
//        }
//        if(startDateTime != null && endDateTime != null){
//            builder.and(qSalesOrder.salesDate.between(startDateTime, endDateTime));
//        }
//        if(salesStatus != null){
//            builder.and(qSalesOrder.salesStatus.eq(salesStatus));
//        }
//        Sort sort = Sort.by(Sort.Direction.DESC, "salesOrderId");
//        return (List<SalesOrder>) salesOrderRepository.findAll(builder, sort);
//    }



    // 수주 기간 검색
    public List<SalesOrder> searchSalesOrder(String searchProduct, String searchVendor, Status searchState , LocalDateTime startDateTime, LocalDateTime endDateTime, Sort sort){

        if (searchProduct != null && !searchProduct.isEmpty() && searchVendor != null && !searchVendor.isEmpty() && searchState != null && startDateTime != null && endDateTime!= null) {
            // 제품명, 거래처명, 수주상태, 기간 검색
            return salesOrderRepository.findByProductNameAndVendorIdAndSalesStatusAndSalesDateBetween(searchProduct, searchVendor, searchState, startDateTime, endDateTime, sort);
        } else if (searchProduct != null && !searchProduct.isEmpty() && searchVendor != null && !searchVendor.isEmpty() && searchState != null) {
            // 제품명, 거래처명, 수주상태로 검색
            return salesOrderRepository.findByProductNameAndVendorIdAndSalesStatus(searchProduct, searchVendor, searchState, sort);
        } else if (searchProduct != null && !searchProduct.isEmpty() && searchVendor != null && !searchVendor.isEmpty() && startDateTime != null && endDateTime!= null) {
            // 제품명, 거래처, 기간으로 검색
            return salesOrderRepository.findByProductNameAndVendorIdAndSalesDateBetween(searchProduct, searchVendor, startDateTime, endDateTime, sort);
        } else if (searchProduct != null && !searchProduct.isEmpty() && searchState != null && startDateTime != null && endDateTime!= null) {
            // 제품명, 수주상태, 기간으로 검색
            return salesOrderRepository.findByProductNameAndSalesStatusAndSalesDateBetween(searchProduct, searchState, startDateTime, endDateTime, sort);
        } else if (searchProduct != null && !searchProduct.isEmpty() && searchVendor != null && !searchVendor.isEmpty()) {
            // 제품명과 거래처로 검색한 경우
            return salesOrderRepository.findByProductNameAndVendorId(searchProduct, searchVendor, sort);
        } else if (searchProduct != null && !searchProduct.isEmpty() && searchState != null) {
            // 제품명과 수주상태로 검색한 경우
            return salesOrderRepository.findByProductNameAndSalesStatus(searchProduct, searchState, sort);
        } else if (searchProduct != null && !searchProduct.isEmpty() && startDateTime != null && endDateTime!= null) {
            // 제품명과 기간으로 검색한 경우
            return salesOrderRepository.findByProductNameAndSalesDateBetween(searchProduct, startDateTime, endDateTime, sort);
        } else if (searchVendor != null && !searchVendor.isEmpty() && searchState != null) {
            // 거래처와 수주상태로 검색한 경우
            return salesOrderRepository.findByVendorIdAndSalesStatus(searchVendor, searchState, sort);
        } else if (searchProduct != null && !searchProduct.isEmpty()) {
            // 제품명으로 검색한 경우
            return salesOrderRepository.findByProductName(searchProduct, sort);
        } else if (searchVendor != null && !searchVendor.isEmpty()) {
            // 거래처로 검색한 경우
            return salesOrderRepository.findByVendorId(searchVendor, sort);
        } else if (searchState != null) {
            // 수주상태로 검색한 경우
            return salesOrderRepository.findBySalesStatus(searchState, sort);
        } else if (startDateTime != null && endDateTime!= null) {
            // 기간으로 검색한 경우
            return salesOrderRepository.findBySalesDateBetween(startDateTime, endDateTime, sort);
        } else {
            // 모든 검색 조건이 제공되지 않은 경우
            return salesOrderRepository.findAll(Sort.by(Sort.Direction.DESC, "salesOrderId"));
        }
    }

    // 수주 확정 시 DB 공정계획 인설트
    public void createProcessPlan(MESInfo mesInfo){
        System.out.println("mesInfo정보임디냐"+mesInfo.getSalesOrderId());
        ProcessPlan processPlan = new ProcessPlan();
        // 원료계량
        processPlan.setProcessStage("원료계량");
        processPlan.setFacilityId(null);
        processPlan.setStartTime(mesInfo.getStartMeasurement());
        processPlan.setEndTime(mesInfo.getFinishMeasurement());
        processPlan.setWorkOrder(workOrderRepository.findBySalesOrderSalesOrderId(mesInfo.getSalesOrderId()));
        processPlanRepository.save(processPlan);
        createProduction(mesInfo, processPlan, mesInfo.getNowMeasurementOutput(), 0);

        // 전처리
        for (int i=0; i<mesInfo.getStartPreProcessing().size(); i++){
            processPlan = new ProcessPlan();
            processPlan.setProcessStage("전처리");
            processPlan.setFacilityId(null);
            processPlan.setStartTime(mesInfo.getStartPreProcessing().get(i));
            processPlan.setEndTime(mesInfo.getFinishPreProcessing().get(i));
            processPlan.setWorkOrder(workOrderRepository.findBySalesOrderSalesOrderId(mesInfo.getSalesOrderId()));
            processPlanRepository.save(processPlan);
            createProduction(mesInfo, processPlan, mesInfo.getNowPreProcessingOutput().get(i), i);
        }
        // 추출 및 혼합
        int exKey = 0;
        for (int i=0; i<mesInfo.getStartExtractionMachine1().size(); i++){ // 추출기1
            processPlan = new ProcessPlan();
            processPlan.setProcessStage("추출 및 혼합");
            processPlan.setFacilityId(facilityRepository.findByFacilityId("extraction_1"));
            processPlan.setStartTime(mesInfo.getStartExtractionMachine1().get(i));
            processPlan.setEndTime(mesInfo.getFinishExtractionMachine1().get(i));
            processPlan.setWorkOrder(workOrderRepository.findBySalesOrderSalesOrderId(mesInfo.getSalesOrderId()));
            processPlanRepository.save(processPlan);
            createProduction(mesInfo, processPlan, mesInfo.getNowExtractionMachine1Output().get(i), exKey++);
        }
        for (int i=0; i<mesInfo.getStartExtractionMachine2().size(); i++){ // 추출기2
            processPlan = new ProcessPlan();
            processPlan.setProcessStage("추출 및 혼합");
            processPlan.setFacilityId(facilityRepository.findByFacilityId("extraction_2"));
            processPlan.setStartTime(mesInfo.getStartExtractionMachine2().get(i));
            processPlan.setEndTime(mesInfo.getFinishExtractionMachine2().get(i));
            processPlan.setWorkOrder(workOrderRepository.findBySalesOrderSalesOrderId(mesInfo.getSalesOrderId()));
            processPlanRepository.save(processPlan);
            createProduction(mesInfo, processPlan, mesInfo.getNowExtractionMachine2Output().get(i), exKey++);
        }
        // 충진
        for (int i=0; i<mesInfo.getStartFillingLiquidMachine().size(); i++){ // 충진기(즙)
            processPlan = new ProcessPlan();
            processPlan.setProcessStage("충진");
            processPlan.setFacilityId(facilityRepository.findByFacilityId("pouch_1"));
            processPlan.setStartTime(mesInfo.getStartFillingLiquidMachine().get(i));
            processPlan.setEndTime(mesInfo.getFinishFillingLiquidMachine().get(i));
            processPlan.setWorkOrder(workOrderRepository.findBySalesOrderSalesOrderId(mesInfo.getSalesOrderId()));
            processPlanRepository.save(processPlan);
            createProduction(mesInfo, processPlan, mesInfo.getNowFillingLiquidMachineOutput().get(i), i);
        }
        for (int i=0; i<mesInfo.getStartFillingJellyMachine().size(); i++){ // 충진기(젤리)
            processPlan = new ProcessPlan();
            processPlan.setProcessStage("충진");
            processPlan.setFacilityId(facilityRepository.findByFacilityId("liquid_stick_1"));
            processPlan.setStartTime(mesInfo.getStartFillingJellyMachine().get(i));
            processPlan.setEndTime(mesInfo.getFinishFillingJellyMachine().get(i));
            processPlan.setWorkOrder(workOrderRepository.findBySalesOrderSalesOrderId(mesInfo.getSalesOrderId()));
            processPlanRepository.save(processPlan);
            createProduction(mesInfo, processPlan, mesInfo.getNowFillingJellyMachineOutput().get(i), i);
        }
        // 검사
        for (int i=0; i<mesInfo.getStartExamination().size(); i++){ // 검사
            processPlan = new ProcessPlan();
            processPlan.setProcessStage("검사");
            processPlan.setFacilityId(facilityRepository.findByFacilityId("inspection"));
            processPlan.setStartTime(mesInfo.getStartExamination().get(i));
            processPlan.setEndTime(mesInfo.getFinishExamination().get(i));
            processPlan.setWorkOrder(workOrderRepository.findBySalesOrderSalesOrderId(mesInfo.getSalesOrderId()));
            processPlanRepository.save(processPlan);
            createProduction(mesInfo, processPlan, mesInfo.getNowExaminationOutput().get(i), i);
        }
        // 포장
        for (int i=0; i<mesInfo.getStartPackaging().size(); i++){ // 포장
            processPlan = new ProcessPlan();
            processPlan.setProcessStage("포장");
            processPlan.setFacilityId(null);
            processPlan.setStartTime(mesInfo.getStartPackaging().get(i));
            processPlan.setEndTime(mesInfo.getFinishPackaging().get(i));
            processPlan.setWorkOrder(workOrderRepository.findBySalesOrderSalesOrderId(mesInfo.getSalesOrderId()));
            processPlanRepository.save(processPlan);
            createProduction(mesInfo, processPlan, mesInfo.getNowPackagingOutput().get(i), i);

        }


        // 완제품 창고 입고 insert
        StockManage stockManage = new StockManage();

        int sumPackage=0;
        for (int i=0; i<mesInfo.getNowPackagingOutput().size(); i++){ // 포장
            sumPackage = mesInfo.getNowPackagingOutput().get(i) + sumPackage;
        }
        stockManage.setStockDate(mesInfo.getEstDelivery());
        stockManage.setStockManageQty(sumPackage);
        stockManage.setStockManageState(StockManageState.I);
        stockManage.setStockManageName(mesInfo.getProductName());
        stockManage.setStock(stockRepository.findByStockName(mesInfo.getProductName()));
        stockManageRepository.save(stockManage);


        // 출하
        Shipment shipment = new Shipment();
        shipment.setShipmentProduct(mesInfo.getProductName());
        shipment.setShipmentQty(mesInfo.getSalesQty());
        shipment.setShipmentDate(processPlanRepository.getShipmentDate(mesInfo.getSalesOrderId()));


        LocalDateTime currentDateTime = LocalDateTime.now();
//         LocalDateTime currentDateTime = LocalDateTime.of(2024,10,15,9,0,0);

        LocalDateTime shipmentDate = processPlanRepository.getShipmentDate(mesInfo.getSalesOrderId());

        if (currentDateTime.isAfter(shipmentDate)) {
            shipment.setShipmentState(ShipmentState.B);

            WorkResult workResult = new WorkResult();
            workResult.setWorkOrder(workOrderRepository.findBySalesOrderSalesOrderId(mesInfo.getSalesOrderId()));
            workResult.setWorkFinishDate(processPlanRepository.getShipmentDate(mesInfo.getSalesOrderId()));
            workResult.setWorkFinishQty(mesInfo.getSalesQty());
            workResultRepository.save(workResult);

        } else {
            shipment.setShipmentState(ShipmentState.A);
        }
        shipment.setSalesOrder(salesOrderRepository.findBySalesOrderId(mesInfo.getSalesOrderId()));
        shipment.setProduction(null);
        shipmentRepository.save(shipment);


        // 완제품 창고 출고 insert
        StockManage stockManage1 = new StockManage();
        stockManage1.setStockDate(mesInfo.getEstDelivery());
        stockManage1.setStockManageQty(mesInfo.getSalesQty());
        stockManage1.setStockManageState(StockManageState.O);
        stockManage1.setStockManageName(mesInfo.getProductName());
        stockManage1.setStock(stockRepository.findByStockName(mesInfo.getProductName()));
        stockManageRepository.save(stockManage1);
        }



    // 수주 확정 시 DB 발주 테이블 인설트
    public void createPurchaseOrder(MESInfo mesInfo){
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        List<String> keys = new ArrayList(mesInfo.getPurchaseMap().keySet());

        for (int i=0; i<keys.size(); i++){
            purchaseOrder = new PurchaseOrder();
            purchaseOrder.setMaterialName(materialRepository.findByMaterialName(keys.get(i)));
            purchaseOrder.setPurchaseQty(mesInfo.getPurchaseMap().get(keys.get(i)));
            purchaseOrder.setVendorId(materialRepository.findVendorIdByMaterialName(keys.get(i)));
            purchaseOrder.setPurchaseDate(mesInfo.getSalesDay());
            purchaseOrder.setEstArrival(mesInfo.getPurchaseAndTimeMap().get(keys.get(i)));
            purchaseOrderRepository.save(purchaseOrder);
        }
    }

    // 수주 등록 시 작업 지시 테이블 인설트(대기상태)  -> workOrderRepository
//    public void createWorkOrder(WorkOrder workOrder){
//        workOrderRepository.save(workOrder);
//    }

    // 공정별 마지막 공정시간
    public LocalDateTime getProcessFinishTime(String processStage){
        System.out.println(processStage);
        List<ProcessPlan> processPlan = processPlanRepository.findProcessPlanByProcessStage(processStage);
        System.out.println(processPlan);
        LocalDateTime processEndTime;

        if (processPlan.isEmpty()){
            processEndTime = LocalDateTime.of(1,1,1,1,1,1);
        } else {
            processEndTime = processPlan.get(0).getEndTime();
        }
        System.out.println(processStage + processEndTime);
        return processEndTime;
    }

    // 설비별 마지막 공정시간
    public LocalDateTime getFacilityFinishTime(String facilityId){
        System.out.println(facilityId);
        List<ProcessPlan> processPlan = processPlanRepository.findProcessPlanByFacilityId(facilityRepository.findByFacilityId(facilityId));
        System.out.println(processPlan);
        LocalDateTime facilityEndTime;

        if (processPlan.isEmpty()){
            facilityEndTime = LocalDateTime.of(1,1,1,1,1,1);
        }else {
            facilityEndTime = processPlan.get(0).getEndTime();
        }
        System.out.println(facilityId + facilityEndTime);
        return facilityEndTime;
    }


    //메인화면에 수주 정보 출력
    public List<Map<String, Object>> getEventList() {
//        List<SalesOrder> salesOrderList = salesOrderRepository.findAll();
        List<SalesOrder> salesOrderList = salesOrderRepository.findBySalesStatusNot(Status.C);

        List<Map<String, Object>> eventList = new ArrayList<>();

        for (SalesOrder salesOrder : salesOrderList){
            Map<String, Object> eventData = new HashMap<>();


//            eventData.put("start", salesOrder.getSalesDate());
            eventData.put("start", salesOrder.getSalesDate().toLocalDate()); //날짜만 반환
            eventData.put("title",salesOrder.getSalesOrderId() +"_" + salesOrder.getProductName());
            eventData.put("extendedProps", Collections.singletonMap("status", salesOrder.getSalesStatus()));


            eventList.add(eventData);
        }

        return eventList;
    }


    // 수주 확정시 db에 원자재 입출고 테이블 insert
    public void createMaterialStorage(MESInfo mesInfo) {
        List<String> keys = new ArrayList<>(mesInfo.getPurchaseMap().keySet());

        for (int i = 0; i < keys.size(); i++) {
            String materialName = keys.get(i);
            int quantity = mesInfo.getPurchaseMap().get(materialName);

            // 원자재 재고 객체 생성
            MaterialStorage materialStorage = new MaterialStorage();
            materialStorage.setMaterialName(materialRepository.findByMaterialName(materialName));
            materialStorage.setMaterialQty(quantity);

            // unit 설정
            if (materialName.contains("파") || materialName.contains("박")) {
                materialStorage.setUnit("ea");
            } else {
                materialStorage.setUnit("kg");
            }

            materialStorage.setMaterialStorageState(MaterialState.I);  // 입고 상태로 설정
            materialStorage.setMaterialStorageDate(mesInfo.getArrivalMaterial());  // 입고날짜

            // 원자재 재고를 데이터베이스에 저장
            materialStorageRepository.save(materialStorage);
        }

        List<String> materialKeys = new ArrayList<>(mesInfo.getRequiredMaterial().keySet());

        for (int i = 0; i < materialKeys.size(); i++) {
            String materialName = materialKeys.get(i);
            if (mesInfo.getRequiredMaterial().get(materialName) == 0 || mesInfo.getRequiredMaterial().get(materialName) == null){
            }
            int quantity = mesInfo.getRequiredMaterial().get(materialName);

            // 원자재 재고 객체 생성
            MaterialStorage materialStorage = new MaterialStorage();
            materialStorage.setMaterialName(materialRepository.findByMaterialName(materialName));
            materialStorage.setMaterialQty(quantity);

            // unit 설정
            if (materialName.contains("파") || materialName.contains("박")) {
                materialStorage.setUnit("ea");
            } else {
                materialStorage.setUnit("kg");
            }

            materialStorage.setMaterialStorageState(MaterialState.O);  // 출고 상태로 설정
            materialStorage.setMaterialStorageDate(mesInfo.getLastStockInDate());  // 출고날짜

            // 원자재 재고를 데이터베이스에 저장
            materialStorageRepository.save(materialStorage);
        }


    }

    // 수주 확정 시 작업실적 insert
    public void createWorkResult(MESInfo mesInfo, WorkOrder workOrder) {
        WorkResult workResult = new WorkResult();

        workResult.setWorkOrder(workOrder);
        workResult.setWorkFinishQty(workOrder.getWorkOrderQty());
        workResult.setWorkFinishDate(mesInfo.getEstDelivery());

        workResultRepository.save(workResult);
    }

    public int getFnishedProduct(String productName) {
        return stockRepository.findByStockName(productName).getStockQty();
    }

    // 생산계획 insert
    public void createProduction(MESInfo mesInfo, ProcessPlan processPlan, int productionQty, int bk ){
        String productName = "";
        String finishedProductName = "";
        String processStage = "";
        Production production = new Production();
        Lot lot = new Lot();

        production.setProductionQty(productionQty);
        production.setProcessPlan(processPlanRepository.findByProcessPlanId(processPlan.getProcessPlanId()));

        // 완제품 lot
        if (mesInfo.getProductName().equals("양배추즙")) {
            finishedProductName = "cbg";
        } else if (mesInfo.getProductName().equals("흑마늘즙")) {
            finishedProductName = "gal";
        } else if (mesInfo.getProductName().equals("석류젤리스틱")) {
            finishedProductName = "pom";
        } else {
            finishedProductName = "plm";
        }

        // 공정 단계 lot
        if(processPlan.getProcessStage().equals("원료계량")){
            processStage = "ms";
            if (mesInfo.getProductName().equals("양배추즙")) {
                productName = "양배추";
            } else if (mesInfo.getProductName().equals("흑마늘즙")) {
                productName = "흑마늘";
            } else if (mesInfo.getProductName().equals("석류젤리스틱")) {
                productName = "석류농축액";
            } else {
                productName = "매실농축액";
            }
        } else if (processPlan.getProcessStage().equals("전처리")){
            processStage = "pp";
            if (mesInfo.getProductName().equals("양배추즙")) {
                productName = "양배추";
            } else {
                productName = "흑마늘";
            }
        } else if (processPlan.getProcessStage().equals("추출 및 혼합")){
            if (processPlan.getFacilityId().getFacilityId().equals("extraction_1")) {
                processStage = "et01";
            } else {
                processStage = "et02";
            }
            if (mesInfo.getProductName().equals("양배추즙")) {
                productName = "양배추 추출액";
            } else if (mesInfo.getProductName().equals("흑마늘즙")) {
                productName = "흑마늘 추출액";
            } else if (mesInfo.getProductName().equals("석류젤리스틱")) {
                productName = "석류액기스";
            } else {
                productName = "매실액기스";
            }
        } else if (processPlan.getProcessStage().equals("충진")){
            if (processPlan.getFacilityId().getFacilityId().equals("pouch_1")) {
                processStage = "exl";
            } else {
                processStage = "exj";
            }

            if (mesInfo.getProductName().equals("양배추즙")) {
                productName = "양배추즙(포)";
            } else if (mesInfo.getProductName().equals("흑마늘즙")) {
                productName = "흑마늘즙(포)";
            } else if (mesInfo.getProductName().equals("석류젤리스틱")) {
                productName = "석류젤리스틱(포)";
            } else {
                productName = "매실젤리스틱(포)";
            }
        } else if (processPlan.getProcessStage().equals("검사")){
            processStage = "cl";
            if (mesInfo.getProductName().equals("양배추즙")) {
                productName = "양배추즙(포)";
            } else if (mesInfo.getProductName().equals("흑마늘즙")) {
                productName = "흑마늘즙(포)";
            } else if (mesInfo.getProductName().equals("석류젤리스틱")) {
                productName = "석류젤리스틱(포)";
            } else {
                productName = "매실젤리스틱(포)";
            }
        } else {
            processStage = "pa";
            if (mesInfo.getProductName().equals("양배추즙")) {
                productName = "양배추즙(박스)";
            } else if (mesInfo.getProductName().equals("흑마늘즙")) {
                productName = "흑마늘즙(박스)";
            } else if (mesInfo.getProductName().equals("석류젤리스틱")) {
                productName = "석류젤리스틱(박스)";
            } else {
                productName = "매실젤리스틱(박스)";
            }
        }
        production.setProductionName(productName);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMddHHmm");
        String processFinishTime = processPlan.getEndTime().format(formatter);
        production.setLotNumber(finishedProductName + "-" + processStage + "-" + processFinishTime);

        // mesinfo에 lot번호 저장
        if(processPlan.getProcessStage().equals("원료계량")){
            mesInfo.setLotMeasurement(production.getLotNumber());
        } else if (processPlan.getProcessStage().equals("전처리")){
            mesInfo.getLotPreProcessing().add(production.getLotNumber());
        } else if (processPlan.getProcessStage().equals("추출 및 혼합")){
            mesInfo.getLotExtraction().add(production.getLotNumber());
        } else if (processPlan.getProcessStage().equals("충진")){
            mesInfo.getLotFilling().add(production.getLotNumber());
        } else if (processPlan.getProcessStage().equals("검사")){
            mesInfo.getLotExamination().add(production.getLotNumber());
        } else {
            mesInfo.getLotPackaging().add(production.getLotNumber());
        }

        // 이전 공정 lot 세팅
        String parentLot = null;
        if(processPlan.getProcessStage().equals("원료계량")){

        } else if (processPlan.getProcessStage().equals("전처리")){
            parentLot = mesInfo.getLotMeasurement();
        } else if (processPlan.getProcessStage().equals("추출 및 혼합")){
            if (mesInfo.getProductName().equals("양배추즙")){
                parentLot = mesInfo.getLotPreProcessing().get(bk);
            } else if (mesInfo.getProductName().equals("흑마늘즙")) {
                parentLot = mesInfo.getLotPreProcessing().get(bk/2);
            } else {
                parentLot = mesInfo.getLotMeasurement();
            }
        } else if (processPlan.getProcessStage().equals("충진")){
            parentLot = mesInfo.getLotExtraction().get(bk);
        } else if (processPlan.getProcessStage().equals("검사")){
            parentLot = mesInfo.getLotFilling().get(bk);
        } else {
            parentLot = mesInfo.getLotExamination().get(bk);
        }
        lot.setLotNum(production.getLotNumber());
        lot.setParentLotNum(parentLot);
        lot.setProduction(production);

        productionRepository.save(production);
        lotRepository.save(lot);

    }
}











