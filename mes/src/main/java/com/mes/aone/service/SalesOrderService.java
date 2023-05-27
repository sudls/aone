package com.mes.aone.service;

import com.mes.aone.constant.Status;
import com.mes.aone.dto.OrderDTO;
import com.mes.aone.entity.ProcessPlan;
import com.mes.aone.entity.PurchaseOrder;
import com.mes.aone.entity.SalesOrder;
import com.mes.aone.entity.WorkOrder;
import com.mes.aone.repository.*;
import com.mes.aone.util.Calculator;
import com.mes.aone.util.MESInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
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
            if (salesOrder != null) {
                salesOrder.setSalesStatus(Status.B); // 상태 업데이트
                salesOrder.setSalesDate(LocalDateTime.now()); // 수주일 업데이트


                MESInfo mesInfo = new MESInfo();
                Calculator calculator = new Calculator(mesInfo);
                mesInfo.setProductName(salesOrder.getProductName()); //수주 제품명
                mesInfo.setSalesQty(salesOrder.getSalesQty()); // 수주량
                mesInfo.setSalesDay(salesOrder.getSalesDate()); // 수주일

                // 예상납품일 계산기 실행
                if (mesInfo.getProductName().equals("양배추즙") || mesInfo.getProductName().equals("흑마늘즙")){ // 즙 공정
                    String purchaseCheck = calculator.purChaseAmount(); // 발주량 계산 메서드 실행
                    if (purchaseCheck.equals("enough")){ // 재고가 충분하면
                        mesInfo.setEstDelivery(LocalDateTime.now()); // 당일 출고
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

                createProcessPlan(mesInfo);
                createPurchaseOrder(mesInfo);

                salesOrderRepository.save(salesOrder);
            }

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
        ProcessPlan processPlan = new ProcessPlan();
        // 원료계량
        processPlan.setProcessStage("원료계량");
        processPlan.setFacilityId(null);
        processPlan.setStartTime(mesInfo.getStartMeasurement());
        processPlan.setEndTime(mesInfo.getFinishMeasurement());
//        processPlan.setWorkOrder(workOrderRepository.findByWorkOrderId());
        processPlan.setWorkOrder(null);
        processPlanRepository.save(processPlan);
        // 전처리
        for (int i=0; i<mesInfo.getStartPreProcessing().size(); i++){
            processPlan = new ProcessPlan();
            processPlan.setProcessStage("전처리");
            processPlan.setFacilityId(null);
            processPlan.setStartTime(mesInfo.getStartPreProcessing().get(i));
            processPlan.setEndTime(mesInfo.getFinishPreProcessing().get(i));
            processPlan.setWorkOrder(null);
            processPlanRepository.save(processPlan);
        }
        // 추출 및 혼합
        for (int i=0; i<mesInfo.getStartExtractionMachine1().size(); i++){ // 추출기1
            processPlan = new ProcessPlan();
            processPlan.setProcessStage("추출 및 혼합");
            processPlan.setFacilityId(facilityRepository.findByFacilityId("extraction_1"));
            processPlan.setStartTime(mesInfo.getStartExtractionMachine1().get(i));
            processPlan.setEndTime(mesInfo.getFinishExtractionMachine1().get(i));
            processPlan.setWorkOrder(null);
            processPlanRepository.save(processPlan);
        }
        for (int i=0; i<mesInfo.getStartExtractionMachine2().size(); i++){ // 추출기2
            processPlan = new ProcessPlan();
            processPlan.setProcessStage("추출 및 혼합");
            processPlan.setFacilityId(facilityRepository.findByFacilityId("extraction_2"));
            processPlan.setStartTime(mesInfo.getStartExtractionMachine2().get(i));
            processPlan.setEndTime(mesInfo.getFinishExtractionMachine2().get(i));
            processPlan.setWorkOrder(null);
            processPlanRepository.save(processPlan);
        }
        // 충진
        for (int i=0; i<mesInfo.getStartFillingLiquidMachine().size(); i++){ // 충진기(즙)
            processPlan = new ProcessPlan();
            processPlan.setProcessStage("충진");
            processPlan.setFacilityId(facilityRepository.findByFacilityId("pouch_1"));
            processPlan.setStartTime(mesInfo.getStartFillingLiquidMachine().get(i));
            processPlan.setEndTime(mesInfo.getFinishFillingLiquidMachine().get(i));
            processPlan.setWorkOrder(null);
            processPlanRepository.save(processPlan);
        }
        for (int i=0; i<mesInfo.getStartFillingJellyMachine().size(); i++){ // 충진기(젤리)
            processPlan = new ProcessPlan();
            processPlan.setProcessStage("충진");
            processPlan.setFacilityId(facilityRepository.findByFacilityId("liquid_stick_1"));
            processPlan.setStartTime(mesInfo.getStartFillingJellyMachine().get(i));
            processPlan.setEndTime(mesInfo.getFinishFillingJellyMachine().get(i));
            processPlan.setWorkOrder(null);
            processPlanRepository.save(processPlan);
        }
        // 검사
        for (int i=0; i<mesInfo.getStartExamination().size(); i++){ // 검사
            processPlan = new ProcessPlan();
            processPlan.setProcessStage("검사");
            processPlan.setFacilityId(facilityRepository.findByFacilityId("inspection"));
            processPlan.setStartTime(mesInfo.getStartExamination().get(i));
            processPlan.setEndTime(mesInfo.getFinishExamination().get(i));
            processPlan.setWorkOrder(null);
            processPlanRepository.save(processPlan);
        }
        // 포장
        for (int i=0; i<mesInfo.getStartPackaging().size(); i++){ // 포장
            processPlan = new ProcessPlan();
            processPlan.setProcessStage("포장");
            processPlan.setFacilityId(null);
            processPlan.setStartTime(mesInfo.getStartPackaging().get(i));
            processPlan.setEndTime(mesInfo.getFinishPackaging().get(i));
            processPlan.setWorkOrder(null);
            processPlanRepository.save(processPlan);
        }
        // 출하
    }

    // 수주 확정 시 DB 발주 테이블 인설트
    public void createPurchaseOrder(MESInfo mesInfo){
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        List<String> keys = new ArrayList(mesInfo.getPurchaseMap().keySet());

        for (int i=0; i<keys.size(); i++){
            purchaseOrder = new PurchaseOrder();
            purchaseOrder.setMaterialName(materialRepository.findByMaterialName(keys.get(i)));
            purchaseOrder.setPurchaseQty(mesInfo.getPurchaseMap().get(keys.get(i)));
            purchaseOrder.setVendorId("ven-11");
            purchaseOrder.setPurchaseDate(mesInfo.getSalesDay());
            purchaseOrder.setEstArrival(mesInfo.getPurchaseAndTimeMap().get(keys.get(i)));

            purchaseOrderRepository.save(purchaseOrder);
        }


    }

    // 수주 등록 시 작업 지시 테이블 인설트(대기상태)
    public void createWorkOrder(WorkOrder workOrder){
        workOrderRepository.save(workOrder);
    }




}
