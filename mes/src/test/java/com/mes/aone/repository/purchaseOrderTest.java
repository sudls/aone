package com.mes.aone.repository;


import com.mes.aone.entity.Material;
import com.mes.aone.entity.PurchaseOrder;
import com.mes.aone.entity.Vendor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;
import java.util.Date;

@SpringBootTest
public class purchaseOrderTest {

    @Autowired
    PurchaseOrderRepository purchaseOrderRepository;
    @Autowired
    MaterialRepository materialRepository;
    @Autowired
    VendorRepository vendorRepository;

    @Test // 발주 더미 (인설트)
    public void insertPurchaseOrder(){
        PurchaseOrder purchaseOrder = new PurchaseOrder();

        Material material = materialRepository.findByMaterialName("매실액기스");
        purchaseOrder.setMaterialName(material);

        Vendor vendor = vendorRepository.findByVendorId("ven-nh");
        purchaseOrder.setVendorId(vendor);

        purchaseOrder.setPurchaseQty(55);
        purchaseOrder.setPurchaseDate(new Date());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(purchaseOrder.getPurchaseDate());
        calendar.add(Calendar.DAY_OF_MONTH, 3);
        Date newDate = calendar.getTime();

        purchaseOrder.setEstArrival(newDate);

        purchaseOrderRepository.save(purchaseOrder);
    }

}
