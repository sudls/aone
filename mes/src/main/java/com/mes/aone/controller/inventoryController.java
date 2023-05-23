package com.mes.aone.controller;

import com.mes.aone.contant.State;
import com.mes.aone.entity.Material;
import com.mes.aone.entity.MaterialStorage;
import com.mes.aone.repository.MaterialRepository;
import com.mes.aone.repository.MaterialStorageRepository;
import com.mes.aone.service.MaterialService;

import com.mes.aone.constant.StockManageState;

import com.mes.aone.entity.StockManage;
import com.mes.aone.repository.StockManageRepository;
import com.mes.aone.repository.StockRepository;
import com.mes.aone.service.StockManageService;
import com.mes.aone.service.StockService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class inventoryController {

    private final MaterialService materialService;

    private final MaterialRepository materialRepository;
    private final StockRepository stockRepository;
    private final StockManageService stockManageService;
    private final StockManageRepository stockManageRepository;
    private final StockService stockService;

    private final MaterialStorageRepository materialStorageRepository;

    @Autowired
    public inventoryController(MaterialService materialService, MaterialRepository materialRepository, StockRepository stockRepository,
                               StockManageService stockManageService, StockManageRepository stockManageRepository,
                               StockService stockService, MaterialStorageRepository materialStorageRepository) {
        this.materialService = materialService;
        this.materialRepository = materialRepository;
        this.stockRepository = stockRepository;
        this.stockManageService = stockManageService;
        this.stockManageRepository = stockManageRepository;
        this.stockService=stockService;
        this.materialStorageRepository = materialStorageRepository;
    }

    //원자재 조회, 검색
    @GetMapping(value="/inventory1")
    public String inventoryPage1(Model model) {
        List<MaterialStorage> materialStorageList = materialStorageRepository.findAll();

        model.addAttribute("materialStorageList", materialStorageList);
        model.addAttribute("Material", materialService.getMaterial());

        return "pages/inventoryPage1";
    }


    @GetMapping(value="/inventory2")
    public String inventoryPage2(){
        return"pages/inventoryPage2";
    }


    //완제품 입출고 내역 조회 및 필터링
    @GetMapping(value = "/inventory3")
    public String inventoryPage3(
            @RequestParam(value = "searchProduct", required = false) String searchProduct,
            @RequestParam(value = "searchState", required = false) StockManageState searchState,
            Model model
    ) {
        List<StockManage> stockManageList;
        if (searchProduct != null && !searchProduct.isEmpty() && searchState != null) {
            stockManageList = stockManageRepository.findByStockManageNameContainingAndStockManageState(searchProduct, searchState);
        } else if (searchProduct != null && !searchProduct.isEmpty()) {
            stockManageList = stockManageRepository.findByStockManageNameContaining(searchProduct);
        } else if (searchState != null) {
            stockManageList = stockManageRepository.findByStockManageState(searchState);
        } else {
            stockManageList = stockManageRepository.findAll();
        }
        model.addAttribute("stockManageList", stockManageList);
        model.addAttribute("Stock", stockService.getStock());
        model.addAttribute("StockSumList", stockService.getSumStock());

        return "pages/inventoryPage3";
    }


}
