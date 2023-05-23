package com.mes.aone.repository;

import com.mes.aone.entity.Material;
import com.mes.aone.entity.PurchaseOrder;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder,Long> {

    // 발주번호, 원자재명, 거래처, 기간
    List<PurchaseOrder> findByPurchaseOrderIdAndMaterialNameAndVendorIdAndPurchaseDateBetween(Long searchOrderId, Material searchMaterial, String searchVendor, LocalDateTime purchaseStartDateTime, LocalDateTime purchaseEndDateTime, Sort sort);
    // 발주번호, 원자재명, 거래처
    List<PurchaseOrder> findByPurchaseOrderIdAndMaterialNameAndVendorId(Long searchOrderId, Material searchMaterial, String searchVendor, Sort sort);
    // 발주변호, 원자재명, 기간
    List<PurchaseOrder> findByPurchaseOrderIdAndMaterialNameAndPurchaseDateBetween(Long searchOrderId, Material searchMaterial, LocalDateTime purchaseStartDateTime, LocalDateTime purchaseEndDateTime, Sort sort);
    // 원자재명, 거래처, 기간
    List<PurchaseOrder> findByMaterialNameAndVendorIdAndPurchaseDateBetween(Material searchMaterial, String searchVendor, LocalDateTime purchaseStartDateTime, LocalDateTime purchaseEndDateTime, Sort sort);
    // 발주번호, 원자재명
    List<PurchaseOrder> findByPurchaseOrderIdAndMaterialName(Long searchOrderId, Material searchMaterial, Sort sort);
    // 발주번호, 거래처
    List<PurchaseOrder> findByPurchaseOrderIdAndVendorId(Long searchOrderId, String searchVendor, Sort sort);
    // 발주번호, 기간
    List<PurchaseOrder> findByPurchaseOrderIdAndPurchaseDateBetween(Long searchOrderId, LocalDateTime purchaseStartDateTime, LocalDateTime purchaseEndDateTime, Sort sort);
    // 원자재명, 거래처
    List<PurchaseOrder> findByMaterialNameAndVendorId(Material searchMaterial, String searchVendor, Sort sort);
    // 원자재명, 기간
    List<PurchaseOrder> findByMaterialNameAndPurchaseDateBetween(Material searchMaterial, LocalDateTime purchaseStartDateTime, LocalDateTime purchaseEndDateTime, Sort sort);
    // 거래처, 기간
    List<PurchaseOrder> findByVendorIdAndPurchaseDateBetween(String searchVendor, LocalDateTime purchaseStartDateTime, LocalDateTime purchaseEndDateTime, Sort sort);
    // 발주번호
    List<PurchaseOrder> findByPurchaseOrderId(Long searchOrderId, Sort sort);
    // 원자재명
    List<PurchaseOrder> findByMaterialName(Material searchMaterial, Sort sort);
    // 거래처
    List<PurchaseOrder> findByVendorId(String searchVendor, Sort sort);
    // 기간
    List<PurchaseOrder> findByPurchaseDateBetween(LocalDateTime purchaseStartDateTime, LocalDateTime purchaseEndDateTime, Sort sort);
    // 전체
    List<PurchaseOrder> findAll();

}
