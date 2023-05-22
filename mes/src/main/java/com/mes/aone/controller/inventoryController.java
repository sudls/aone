package com.mes.aone.controller;

import com.mes.aone.constant.StockManageState;
import com.mes.aone.dto.PurchaseOrderDTO;
import com.mes.aone.dto.StockManageDTO;
import com.mes.aone.entity.Material;
import com.mes.aone.entity.PurchaseOrder;
import com.mes.aone.entity.Stock;
import com.mes.aone.entity.StockManage;
import com.mes.aone.repository.MaterialStorageRepository;
import com.mes.aone.repository.PurchaseOrderRepository;
import com.mes.aone.repository.StockManageRepository;
import com.mes.aone.repository.StockRepository;
import com.mes.aone.service.MaterialService;
import com.mes.aone.service.PurchaseOrderService;
import com.mes.aone.service.StockManageService;
import com.mes.aone.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

@Controller
@RequiredArgsConstructor
//@AllArgsConstructor
public class inventoryController {


//    private final MaterialService materialService;

//    @GetMapping(value="/inventory1")
//    public String inventoryPage1(Model model){
//
//        model.addAttribute("MaterialStorage", materialService.getMaterialStorage());
//
//        model.addAttribute("Material", materialService.getMaterial());

        //원자재 입출고 현황 (더미)
//         List<MaterialStorageDTO> materialStorageDTOList = new ArrayList<>();

//         String[] materialNames = {"양배추","콜라겐", "흑마늘", "벌꿀", "석류액기스", "매실액기스", "스틱파우치", "파우치","박스"};

//         Random random = new Random();

//         for (int i = 1; i <= 30; i++) {
//             MaterialStorageDTO materialStorageDTO = new MaterialStorageDTO();
//             materialStorageDTO.setMaterialName(materialNames[random.nextInt(materialNames.length)]);
//             materialStorageDTO.setMStorageState(StockManageState.I);
//             materialStorageDTO.setMaterialQty(random.nextInt(1000));
//             materialStorageDTO.setMaterialUnit("kg");
//             materialStorageDTO.setMStorageDate(LocalDateTime.now());
//             materialStorageDTOList.add(materialStorageDTO);
//         }
//         model.addAttribute("materialStorageDTOList",materialStorageDTOList);

//         //원자재 총 수량(더미)
//         List<MaterialStorageNumDTO> materialStorageNumDTOList = new ArrayList<>();
//         for (int i=0; i<9; i++){
//             MaterialStorageNumDTO materialStorageNumDTO = new MaterialStorageNumDTO();
//             materialStorageNumDTO.setMaterialName(materialNames[i]);
//             materialStorageNumDTO.setMaterialQty(random.nextInt(1000));
//             materialStorageNumDTOList.add(materialStorageNumDTO);
//         }
//         model.addAttribute("materialStorageNumDTOList",materialStorageNumDTOList);


//        return"pages/inventoryPage1";
//    }






    private final PurchaseOrderService purchaseOrderService;
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final MaterialService materialService;
    private final StockRepository stockRepository;
    private final StockManageService stockManageService;
    private final StockManageRepository stockManageRepository;
    private final StockService stockService;

    private final MaterialStorageRepository materialStorageRepository;

    @Autowired
    public inventoryController(MaterialService materialService, StockRepository stockRepository,
                               StockManageService stockManageService, StockManageRepository stockManageRepository,
                               StockService stockService, MaterialStorageRepository materialStorageRepository,
                               PurchaseOrderService purchaseOrderService,
                               PurchaseOrderRepository purchaseOrderRepository) {
        this.materialService = materialService;
        this.stockRepository = stockRepository;
        this.stockManageService = stockManageService;
        this.stockManageRepository = stockManageRepository;
        this.stockService=stockService;
        this.materialStorageRepository = materialStorageRepository;
        this.purchaseOrderService = purchaseOrderService;
        this.purchaseOrderRepository = purchaseOrderRepository;
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


    // 생성자 확인하기 -------------------------------------------------
//


    //완제품 총 수량 조회 , 완제품 입출고 내역 조회
    @GetMapping(value="/inventory3")
    public String inventoryPage3(Model model){
        List<Stock> stockList = stockRepository.findAll();
        model.addAttribute("stockList", stockList);

        List<StockManage> stockManageList = stockManageService.getAllStockManage();
        model.addAttribute("stockManageList", stockManageList);
        model.addAttribute("stockManageDTO", new StockManageDTO());


        return"pages/inventoryPage3";
    }

    //완제품 입출고 내역 조회 및 필터링
    @GetMapping(value = "/inventory3/search")
    public String inventoryPage3(
            @RequestParam(value = "searchProduct", required = false) String searchProduct,
            @RequestParam(value = "searchState", required = false) StockManageState searchState,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate stockStartDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate stockEndDate,
            Model model) {

        // 날짜 변환
        LocalDateTime stockStartDateTime = null;
        LocalDateTime stockEndDateTime = null;
        if(stockStartDate != null && stockEndDate != null){
            stockStartDateTime =  LocalDateTime.of(stockStartDate, LocalTime.MIN);
            stockEndDateTime =  LocalDateTime.of(stockEndDate, LocalTime.MAX);
        }

        // 정렬 설정
        Sort sort = Sort.by(Sort.Direction.DESC, "stockManageId");

        List<StockManage> stockManageList = stockManageService.searchStockManage(searchProduct, searchState, stockStartDateTime, stockEndDateTime, sort);

        model.addAttribute("stockManageList", stockManageList);
        model.addAttribute("stockManageDTO", new StockManageDTO());

        return "pages/inventoryPage3";
    }
















//

    // 생성자 확인하기 -------------------------------------------------
//
//    private final StockRepository stockRepository;
//    private final StockManageService stockManageService;
//    private final StockManageRepository stockManageRepository;
//
//    private final StockService stockService;
//    public inventoryController(StockRepository stockRepository, StockManageService stockManageService, StockManageRepository stockManageRepository, StockService stockService) {
//        this.stockRepository = stockRepository;
//        this.stockManageService = stockManageService;
//        this.stockManageRepository = stockManageRepository;
//        this.stockService=stockService;
//    }
    //완제품 총 수량 조회 , 완제품 입출고 내역 조회
/*    @GetMapping(value="/inventory3")
    public String inventoryPage3(Model model){
        List<Stock> stockList = stockRepository.findAll();
        model.addAttribute("stockList", stockList);

        List<StockManage> stockManageList = stockManageService.getAllStockManage();
        model.addAttribute("stockManageList", stockManageList);

        return"pages/inventoryPage3";
    }*/

    //완제품 입출고 내역 조회 및 필터링  (확인후 풀기)
//    @GetMapping(value = "/inventory3")
//    public String inventoryPage3(
//            @RequestParam(value = "searchProduct", required = false) String searchProduct,
//            @RequestParam(value = "searchState", required = false) StockManageState searchState,
//            Model model
//    ) {
//        List<Stock> stockList = stockRepository.findAll();
//        model.addAttribute("stockList", stockList);
//
//        List<StockManage> stockManageList;
//        if (searchProduct != null && !searchProduct.isEmpty() && searchState != null) {
//            stockManageList = stockManageRepository.findByStockManageNameContainingAndStockManageState(searchProduct, searchState);
//        } else if (searchProduct != null && !searchProduct.isEmpty()) {
//            stockManageList = stockManageRepository.findByStockManageNameContaining(searchProduct);
//        } else if (searchState != null) {
//            stockManageList = stockManageRepository.findByStockManageState(searchState);
//        } else {
//            stockManageList = stockManageRepository.findAll();
//        }
//        model.addAttribute("stockManageList", stockManageList);
//
//        return "pages/inventoryPage3";
//    }






}
