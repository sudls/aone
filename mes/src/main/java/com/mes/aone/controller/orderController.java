package com.mes.aone.controller;

import com.mes.aone.constant.SalesStatus;
import com.mes.aone.dto.OrderDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class orderController {
    @GetMapping(value="/order")
    public String orderPage(Model model){

        List<OrderDTO> orderDTOList = new ArrayList<>();

        for(int i=1; i<=30; i++ ){
            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setSalesOrderId(Long.valueOf(i));
            orderDTO.setProductName("양배추즙");
            orderDTO.setVendorId("ven-nh");
            orderDTO.setSalesDate(LocalDateTime.now());
            orderDTO.setSalesQty(342);
            if(i%5==0){
                orderDTO.setSalesStatus(SalesStatus.B);
            }else if (i%7==0){
                orderDTO.setSalesStatus(SalesStatus.C);
            }else{
                    orderDTO.setSalesStatus(SalesStatus.A);
                }



            orderDTOList.add(orderDTO);
        }

        model.addAttribute("orderDTOList",orderDTOList);

        return"pages/orderPage";
    }
}
