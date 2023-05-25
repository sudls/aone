package com.mes.aone.service;

import com.mes.aone.constant.Status;
import com.mes.aone.dto.OrderDTO;
import com.mes.aone.entity.SalesOrder;
import com.mes.aone.entity.WorkOrder;
import com.mes.aone.repository.SalesOrderRepository;
import com.mes.aone.repository.WorkOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SalesOrderService {
    private final SalesOrderRepository salesOrderRepository;
    private WorkOrderRepository workOrderRepository;

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
                salesOrder.setSalesStatus(Status.B); // 상태
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



}
