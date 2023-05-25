package com.mes.aone.controller;


import com.mes.aone.constant.MaterialState;
import com.mes.aone.entity.Material;
import com.mes.aone.entity.MaterialStorage;
import com.mes.aone.repository.MaterialRepository;
import com.mes.aone.repository.MaterialStorageRepository;
import com.mes.aone.constant.StockManageState;
import com.mes.aone.dto.PurchaseOrderDTO;
import com.mes.aone.entity.PurchaseOrder;
import com.mes.aone.entity.StockManage;
import com.mes.aone.repository.PurchaseOrderRepository;
import com.mes.aone.repository.StockManageRepository;
import com.mes.aone.repository.StockRepository;
import com.mes.aone.service.MaterialService;
import com.mes.aone.service.PurchaseOrderService;
import com.mes.aone.service.StockManageService;
import com.mes.aone.service.StockService;


import org.springframework.beans.factory.annotation.Autowired;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class inventoryController {
    private final MaterialRepository materialRepository;
    private final StockRepository stockRepository;
    private final StockManageService stockManageService;
    private final StockManageRepository stockManageRepository;
    private final StockService stockService;
    private final MaterialStorageRepository materialStorageRepository;
  
  private final PurchaseOrderService purchaseOrderService;
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final MaterialService materialService;

    @Autowired
    public inventoryController(MaterialRepository materialRepository, MaterialService materialService, StockRepository stockRepository,
                               StockManageService stockManageService, StockManageRepository stockManageRepository,
                               StockService stockService, MaterialStorageRepository materialStorageRepository,
                               PurchaseOrderService purchaseOrderService,
                               PurchaseOrderRepository purchaseOrderRepository) {
        this.materialRepository = materialRepository;
        this.materialService = materialService;
        this.stockRepository = stockRepository;
        this.stockManageService = stockManageService;
        this.stockManageRepository = stockManageRepository;
        this.stockService=stockService;
        this.materialStorageRepository = materialStorageRepository;
        this.purchaseOrderService = purchaseOrderService;
        this.purchaseOrderRepository = purchaseOrderRepository;
    }

    //원자재 조회
    @GetMapping(value="/inventory1")
    public String inventoryPage1(Model model) {
        List<MaterialStorage> materialStorageList = materialStorageRepository.findAll(Sort.by(Sort.Direction.DESC, "materialStorageId"));       // 내림차순

        model.addAttribute("materialStorageList", materialStorageList);
        model.addAttribute("Material", materialService.getMaterial());

        return "pages/inventoryPage1";
    }


    //원자재 검색
    @GetMapping(value="/inventory1/search")
    public String inventoryPage1(
            @RequestParam(value = "searchMaterial", required = false) String searchMaterial,
            @RequestParam(value = "searchMState", required = false) MaterialState searchMState,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate matStartDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate matEndDate,
            Model model) {
        Sort sort = Sort.by(Sort.Direction.DESC, "materialStorageId");
        List<MaterialStorage> materialStorageList;

        LocalDateTime startDate = null;
        LocalDateTime endDate = null;

        Material material = new Material();
        material.setMaterialName(searchMaterial);

        if (matStartDate != null) {
            startDate = matStartDate.atStartOfDay();
        }

        if (matEndDate != null) {
            endDate = matEndDate.atTime(LocalTime.MAX);
        }

        if (searchMaterial != null && !searchMaterial.isEmpty() && searchMState != null && startDate != null && endDate != null) {
            // 제품명, 입출고상태, 기간
            materialStorageList = materialStorageRepository.findByMaterialNameAndMaterialStorageStateAndMaterialStorageDateBetween(material, searchMState, startDate, endDate, sort);
        } else if (searchMaterial != null && !searchMaterial.isEmpty() && searchMState != null) {
            // 제품명, 입출고상태
            materialStorageList = materialStorageRepository.findByMaterialNameAndMaterialStorageState(material, searchMState, sort);
        } else if (searchMaterial != null && !searchMaterial.isEmpty() && startDate != null && endDate != null) {
            // 제품명, 기간
            materialStorageList = materialStorageRepository.findByMaterialNameAndMaterialStorageDateBetween(material, startDate, endDate, sort);
        } else if (searchMState != null && startDate != null && endDate != null) {
            // 입출고상태, 기간
            materialStorageList = materialStorageRepository.findByMaterialStorageStateAndMaterialStorageDateBetween(searchMState, startDate, endDate, sort);
        } else if (searchMaterial != null && !searchMaterial.isEmpty()) {
            // 제품명
            materialStorageList = materialStorageRepository.findByMaterialName(material, sort);
        } else if (searchMState != null) {
            // 입출고상태
            materialStorageList = materialStorageRepository.findByMaterialStorageState(searchMState, sort);
        } else if (startDate != null && endDate != null) {
            // 기간
            materialStorageList = materialStorageRepository.findByMaterialStorageDateBetween(startDate, endDate, sort);
        } else {
            // 모든 검색 조건이 제공되지 않은 경우
            materialStorageList = materialStorageRepository.findAll(Sort.by(Sort.Direction.DESC, "materialStorageId"));
        }

        model.addAttribute("materialStorageList", materialStorageList);
        model.addAttribute("Material", materialService.getMaterial());

        return "pages/inventoryPage1";
    }


    // 원자재 발주 조회 리스트
    @GetMapping(value="/inventory2")
    public String inventoryPage2(Model model){
        List<PurchaseOrder> purchaseOrderList = purchaseOrderRepository.findAll(Sort.by(Sort.Direction.DESC, "purchaseOrderId"));       // 내림차순
        model.addAttribute("purchaseOrderDTOList",purchaseOrderList);
        model.addAttribute("purchaseorderDTO", new PurchaseOrderDTO());

        return"pages/inventoryPage2";
    }


        // 원자재 입출고 내역 조회 및 필터링
    @GetMapping(value = "/inventory2/search")
    public String inventoryPage2(
            @RequestParam(value = "searchPurchaseOrderId", required = false) Long searchOrderId,
            @RequestParam(value = "searchMaterial", required = false) Material searchMaterial,
            @RequestParam(value = "searchVendor", required = false) String searchVendor,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate purchaseStartDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate purchaseEndDate,
            Model model) {

        // 날짜 변환
        LocalDateTime purchaseStartDateTime = null;
        LocalDateTime purchaseEndDateTime = null;
        if(purchaseStartDate != null && purchaseEndDate != null){
            purchaseStartDateTime =  LocalDateTime.of(purchaseStartDate, LocalTime.MIN);
            purchaseEndDateTime =  LocalDateTime.of(purchaseEndDate, LocalTime.MAX);
        }

        // 정렬 설정
        Sort sort = Sort.by(Sort.Direction.DESC, "purchaseOrderId");

        List<PurchaseOrder> purchaseOrderList = purchaseOrderService.searchPurchaseOrder(searchOrderId, searchMaterial, searchVendor, purchaseStartDateTime, purchaseEndDateTime, sort);

        model.addAttribute("purchaseOrderDTOList", purchaseOrderList);
        model.addAttribute("purchaseOrderDTO", new PurchaseOrderDTO());

        return "pages/inventoryPage2";
    }

    //완제품 리스트
    @GetMapping(value = "/inventory3")
    public String inventoryPage3(Model model) {
        List<StockManage> stockManageList = stockManageRepository.findAll(Sort.by(Sort.Direction.DESC, "stockManageId"));
        model.addAttribute("stockManageList", stockManageList);
        model.addAttribute("Stock", stockService.getStock());
        model.addAttribute("StockSumList", stockService.getSumStock());

        return "pages/inventoryPage3";
    }

    //완제품 검색
    @GetMapping(value = "/inventory3/search")
    public String inventoryPage3(
            @RequestParam(value = "searchProduct", required = false) String searchProduct,
            @RequestParam(value = "searchState", required = false) StockManageState searchState,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate stockStartDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate stockEndDate,
            Model model) {

        Sort sort = Sort.by(Sort.Direction.DESC, "stockManageId");
        List<StockManage> stockManageList;

        LocalDateTime startDate = null;
        LocalDateTime endDate = null;

        if (stockStartDate != null) {
            startDate = stockStartDate.atStartOfDay();
        }

        if (stockEndDate != null) {
            endDate = stockEndDate.atTime(LocalTime.MAX);
        }

        if (searchProduct != null && !searchProduct.isEmpty() && searchState != null && startDate != null && endDate != null) {
            // 제품명, 입출고상태, 기간
            stockManageList = stockManageRepository.findByStockManageNameAndStockManageStateAndStockDateBetween(searchProduct, searchState, startDate, endDate, sort);
        } else if (searchProduct != null && !searchProduct.isEmpty() && searchState != null) {
            // 제품명, 입출고상태
            stockManageList = stockManageRepository.findByStockManageNameAndStockManageState(searchProduct, searchState, sort);
        } else if (searchProduct != null && !searchProduct.isEmpty() && startDate != null && endDate != null) {
            // 제품명, 기간
            stockManageList = stockManageRepository.findByStockManageNameAndStockDateBetween(searchProduct, startDate, endDate, sort);
        } else if (searchState != null && startDate != null && endDate != null) {
            // 입출고상태, 기간
            stockManageList = stockManageRepository.findByStockManageStateAndStockDateBetween(searchState, startDate, endDate, sort);
        } else if (searchProduct != null && !searchProduct.isEmpty()) {
            // 제품명
            stockManageList = stockManageRepository.findByStockManageName(searchProduct, sort);
        } else if (searchState != null) {
            // 입출고상태
            stockManageList = stockManageRepository.findByStockManageState(searchState, sort);
        } else if (startDate != null && endDate != null) {
            // 기간
            stockManageList = stockManageRepository.findByStockDateBetween(startDate, endDate, sort);
        } else {
            // 모든 검색 조건이 제공되지 않은 경우
            stockManageList = stockManageRepository.findAll(Sort.by(Sort.Direction.DESC, "purchaseOrderId"));
        }

        model.addAttribute("stockManageList", stockManageList);
        model.addAttribute("Stock", stockService.getStock());
        model.addAttribute("StockSumList", stockService.getSumStock());

        return "pages/inventoryPage3";
    }
}
