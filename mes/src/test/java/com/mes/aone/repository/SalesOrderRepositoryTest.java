package com.mes.aone.repository;

import com.mes.aone.contant.Status;
import com.mes.aone.entity.SalesOrder;
import com.mes.aone.entity.Vendor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
//@TestPropertySource(locations = "classpath:application-test.properties")
class SalesOrderRepositoryTest {
    @Autowired
    SalesOrderRepository salesOrderRepository;
    @Autowired
    VendorRepository vendorRepository;

//    @Test
//    @DisplayName("수주 등록테스트")
//    public void createSalesOrderTest(){
//        Vendor vendor = vendorRepository.findByVendorId("ven-11");
//        SalesOrder sOrder = new SalesOrder();
//
//        sOrder.setProductName("양배추즙");
//        sOrder.setVendorId(vendor);
//        sOrder.setSalesQty(384);
//
//        SalesOrder savedSalesOrder = salesOrderRepository.save(sOrder);
//        System.out.println(savedSalesOrder.toString());
//    }



    @BeforeEach
//    @DisplayName("수주등록테스트")
    public void createSalesOrderList() {        // 수주 등록
        Vendor vendor = vendorRepository.findByVendorId("거래처아이디");
         for(int i=1; i<10; i++) {
             SalesOrder sOrder = new SalesOrder();

             sOrder.setProductName("테스트즙" + i);
             sOrder.setVendorId(vendor);
             sOrder.setSalesQty(384 + i);

             SalesOrder savedSalesOrder = salesOrderRepository.save(sOrder);
             System.out.println(savedSalesOrder.toString());
         }
    }

    @Test
    @DisplayName("수주전체내용 조회테스트")
    public void findAllTest(){
        this.createSalesOrderList();
        List<SalesOrder> salesOrderList = salesOrderRepository.findAll();

        for(SalesOrder salesOrder : salesOrderList) {
            System.out.println(salesOrder.toString());
        }
    }


    @Test
    @DisplayName("수주상태 변경 테스트")     // 콘솔에서는 변경되는데 디비에서는 안찍힘
    public void updateSalesOrderStateTest(){
        SalesOrder salesOrder = salesOrderRepository.findBySalesOrderId(5L);
        salesOrder.setSalesStatus(Status.C);
        salesOrderRepository.save(salesOrder);
        System.out.println(salesOrder.toString());
    }

}