package com.mes.aone.service;

import com.mes.aone.entity.Material;
import com.mes.aone.entity.PurchaseOrder;
import com.mes.aone.repository.PurchaseOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PurchaseOrderService {

    private final PurchaseOrderRepository purchaseOrderRepository;

    // 수주 다중 검색
    public List<PurchaseOrder> searchPurchaseOrder(Long searchOrderId, Material searchMaterial, String searchVendor, LocalDateTime purchaseStartDateTime, LocalDateTime purchaseEndDateTime, Sort sort){

        if (searchOrderId != null && searchMaterial != null && searchVendor != null && !searchVendor.isEmpty() && purchaseStartDateTime != null && purchaseEndDateTime!= null) {
            // 발주번호, 원자재명, 거래처, 기간
            return purchaseOrderRepository.findByPurchaseOrderIdAndMaterialNameAndVendorIdAndPurchaseDateBetween(searchOrderId, searchMaterial, searchVendor, purchaseStartDateTime, purchaseEndDateTime, sort);
        } else if (searchOrderId != null && searchMaterial != null && searchVendor != null && !searchVendor.isEmpty()) {
            // 발주번호, 원자재명, 거래처
            return purchaseOrderRepository.findByPurchaseOrderIdAndMaterialNameAndVendorId(searchOrderId, searchMaterial, searchVendor, sort);
        } else if (searchOrderId != null && searchMaterial != null && purchaseStartDateTime != null && purchaseEndDateTime!= null) {
            // 발주번호, 원자재명, 기간
            return purchaseOrderRepository.findByPurchaseOrderIdAndMaterialNameAndPurchaseDateBetween(searchOrderId, searchMaterial, purchaseStartDateTime, purchaseEndDateTime, sort);
        } else if (searchMaterial != null && searchVendor != null && !searchVendor.isEmpty() && purchaseStartDateTime != null && purchaseEndDateTime!= null) {
            // 원자재명, 거래처, 기간
            return purchaseOrderRepository.findByMaterialNameAndVendorIdAndPurchaseDateBetween(searchMaterial, searchVendor, purchaseStartDateTime, purchaseEndDateTime, sort);
        } else if (searchOrderId != null && searchMaterial != null) {
            // 발주번호, 원자재명
            return purchaseOrderRepository.findByPurchaseOrderIdAndMaterialName(searchOrderId, searchMaterial, sort);
        } else if (searchOrderId != null && searchVendor != null && !searchVendor.isEmpty()) {
            // 발주번호, 거래처
            return purchaseOrderRepository.findByPurchaseOrderIdAndVendorId(searchOrderId, searchVendor, sort);
        } else if (searchOrderId != null && purchaseStartDateTime != null && purchaseEndDateTime!= null) {
            // 발주번호, 기간
            return purchaseOrderRepository.findByPurchaseOrderIdAndPurchaseDateBetween(searchOrderId, purchaseStartDateTime, purchaseEndDateTime, sort);
        } else if (searchMaterial != null && searchVendor != null && !searchVendor.isEmpty()) {
            // 원자재명, 거래처
            return purchaseOrderRepository.findByMaterialNameAndVendorId(searchMaterial, searchVendor, sort);
        } else if (searchMaterial != null && purchaseStartDateTime != null && purchaseEndDateTime!= null) {
            // 원자재명, 기간
            return purchaseOrderRepository.findByMaterialNameAndPurchaseDateBetween(searchMaterial, purchaseStartDateTime, purchaseEndDateTime, sort);
        } else if (searchVendor != null && !searchVendor.isEmpty() && purchaseStartDateTime != null && purchaseEndDateTime!= null) {
            // 거래처, 기간
            return purchaseOrderRepository.findByVendorIdAndPurchaseDateBetween(searchVendor, purchaseStartDateTime, purchaseEndDateTime, sort);
        } else if (searchOrderId != null) {
            // 발주번호
            return purchaseOrderRepository.findByPurchaseOrderId(searchOrderId, sort);
        } else if (searchMaterial != null) {
            // 원자재명
            return purchaseOrderRepository.findByMaterialName(searchMaterial, sort);
        } else if (searchVendor != null && !searchVendor.isEmpty()) {
            // 거래처
            return purchaseOrderRepository.findByVendorId(searchVendor, sort);
        }else if (purchaseStartDateTime != null && purchaseEndDateTime!= null) {
            // 기간
            return purchaseOrderRepository.findByPurchaseDateBetween(purchaseStartDateTime, purchaseEndDateTime, sort);
        } else {
            // 모든 검색 조건이 제공되지 않은 경우
            return purchaseOrderRepository.findAll(Sort.by(Sort.Direction.DESC, "purchaseOrderId"));
        }
    }
}
