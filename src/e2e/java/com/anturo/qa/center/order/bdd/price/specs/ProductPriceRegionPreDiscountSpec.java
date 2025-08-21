package com.anturo.qa.center.order.bdd.price.specs;

import com.anturo.qa.center.order.bdd.order.apis.OrderApiPrediscountFetchPrice01;
import com.anturo.qa.center.order.bdd.order.backgrounds.ProductBackgroundData02;
import com.anturo.qa.center.order.bdd.order.batchs.PriceBatchClearData01;
import com.anturo.qa.center.order.bdd.order.batchs.PriceBatchInsertData01;
import com.anturo.qa.center.order.bdd.order.checks.OrderCheckOrderManagement01;
import com.anturo.qa.center.order.main.model.dto.OrderRegionPreDiscountFetchProductPriceDTO;
import com.anturo.qa.center.order.main.model.dto.ProductPriceDTO;
import io.cucumber.datatable.DataTable;
import io.cucumber.java8.En;
import io.restassured.response.Response;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import net.thucydides.core.annotations.Steps;
import org.hamcrest.Matchers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Author Tesla
 * @Date 2024/07/31
 * @Description
 */

@SuppressWarnings("all")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class ProductPriceRegionPreDiscountSpec extends ProductBackgroundData02 implements En {

	String saleOrgSapCode, customerSapCode, regionCode, regionName;

	String createTime;

	DataTable domainDataTable;

	@Steps(actor = "QA", shared = true)
	PriceBatchInsertData01 priceBatchInsertData01;

	@Steps(actor = "QA", shared = true)
	PriceBatchClearData01 priceBatchClearData01;

	@Steps(actor = "QA", shared = true)
	OrderApiPrediscountFetchPrice01 orderApiPrediscountFetchPrice01;

	@Steps(actor = "QA", shared = true)
	OrderCheckOrderManagement01 orderCheckOrderManagement01;

	public ProductPriceRegionPreDiscountSpec() {
		super();
		this.scenario01();
		this.scenario02();
		this.scenario03();
		this.scenario04();
		this.scenario05();
		this.scenario06();
	}


	private void scenario01() {
		Given("[大区预折商品获取单价01]当前存在如下客户信息", (DataTable dataTable) -> {
			createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis());
			domainDataTable = dataTable;
			domainDataTable.asMaps().forEach(caseData -> {
				saleOrgSapCode = Optional.ofNullable(caseData.get("saleOrgSapCode")).orElse("");
				customerSapCode = Optional.ofNullable(caseData.get("customerSapCode")).orElse("");
				regionCode = Optional.ofNullable(caseData.get("regionCode")).orElse("");
				regionName = Optional.ofNullable(caseData.get("regionName")).orElse("");
			});
		});
		When("[大区预折商品获取单价01]当前存在如下商品价格信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String dimensionCode = Optional.ofNullable(caseData.get("dimensionCode")).orElse("");
				String dimensionPriority = Optional.ofNullable(caseData.get("dimensionPriority")).orElse("");
				String distributionChannelCode = Optional.ofNullable(caseData.get("distributionChannelCode")).orElse("");
				String priceGroupCode = Optional.ofNullable(caseData.get("priceGroupCode")).orElse("");
				String customerCode = Optional.ofNullable(caseData.get("customerCode")).orElse("");
				String itemCode = Optional.ofNullable(caseData.get("itemCode")).orElse("");
				String price = new BigDecimal(caseData.get("price")).setScale(2, RoundingMode.HALF_UP).toString();
				priceBatchInsertData01.insertProductPriceData01(ProductPriceDTO.builder()
						.dimensionCode(dimensionCode)
						.dimensionPriority(dimensionPriority)
						.sellerCode(saleOrgSapCode)
						.distributionChannelCode(distributionChannelCode)
						.priceGroupCode(priceGroupCode)
						.customerCode(customerCode)
						.itemCode(itemCode)
						.startTime(createTime)
						.price(price)
						.createPerson("at")
						.createTime(createTime)
						.build());
			});
		});
		Then("[大区预折商品获取单价01]预折添加商品时获取价格应该如下", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String itemCode = Optional.ofNullable(caseData.get("itemCode")).orElse("");
				String preType = Optional.ofNullable(caseData.get("preType")).orElse("");
				String price = new BigDecimal(caseData.get("price")).setScale(2, RoundingMode.HALF_UP).toString();
				String distributionChannelCode = Optional.ofNullable(caseData.get("distributionChannelCode")).orElse("");
				String distributionChannelName = Optional.ofNullable(caseData.get("distributionChannelName")).orElse("");
				Response apiResult = orderApiPrediscountFetchPrice01.regionPreDiscountFetchProductPrice(OrderRegionPreDiscountFetchProductPriceDTO.builder()
						.preType(preType)
						.orderType("STANDARD")
						.saleOrgSapCode(saleOrgSapCode)
						.customerRegionCode(regionCode)
						.customerRegionName(regionName)
						.customerSapCode("N")
						.itemSapCodeOrCrmCodeOrDesc(itemCode)
						.build());
				apiResult.then().body("resultCode", Matchers.equalTo("0"), "resultMsg", Matchers.equalTo("success"));
				List<Map<String, String>> testCaseData = dataTable.asMaps().stream().map(map -> {
					Map<String, String> resultMap = new HashMap<>(map);
					resultMap.put("saleOrgSapCode", saleOrgSapCode);
					resultMap.put("customerSapCode", "N");
					resultMap.put("customerRegionCode", regionCode);
					return resultMap;
				}).collect(Collectors.toList());
				orderCheckOrderManagement01.checkAddProductFetchPrice(testCaseData, apiResult);
			});
			priceBatchClearData01.clearPirceData01("at");
			priceBatchClearData01.clearProductData01("at");
		});
	}

	private void scenario02() {
		Given("[大区预折商品获取单价02]当前存在如下客户信息", (DataTable dataTable) -> {
			createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis());
			domainDataTable = dataTable;
			domainDataTable.asMaps().forEach(caseData -> {
				saleOrgSapCode = Optional.ofNullable(caseData.get("saleOrgSapCode")).orElse("");
				customerSapCode = Optional.ofNullable(caseData.get("customerSapCode")).orElse("");
				regionCode = Optional.ofNullable(caseData.get("regionCode")).orElse("");
				regionName = Optional.ofNullable(caseData.get("regionName")).orElse("");
			});
		});
		When("[大区预折商品获取单价02]当前存在如下商品价格信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String dimensionCode = Optional.ofNullable(caseData.get("dimensionCode")).orElse("");
				String dimensionPriority = Optional.ofNullable(caseData.get("dimensionPriority")).orElse("");
				String distributionChannelCode = Optional.ofNullable(caseData.get("distributionChannelCode")).orElse("");
				String priceGroupCode = Optional.ofNullable(caseData.get("priceGroupCode")).orElse("");
				String customerCode = Optional.ofNullable(caseData.get("customerCode")).orElse("");
				String itemCode = Optional.ofNullable(caseData.get("itemCode")).orElse("");
				String price = new BigDecimal(caseData.get("price")).setScale(2, RoundingMode.HALF_UP).toString();
				priceBatchInsertData01.insertProductPriceData01(ProductPriceDTO.builder()
						.dimensionCode(dimensionCode)
						.dimensionPriority(dimensionPriority)
						.sellerCode(saleOrgSapCode)
						.distributionChannelCode(distributionChannelCode)
						.priceGroupCode(priceGroupCode)
						.customerCode(customerCode)
						.itemCode(itemCode)
						.startTime(createTime)
						.price(price)
						.createPerson("at")
						.createTime(createTime)
						.build());
			});
		});
		Then("[大区预折商品获取单价02]预折添加商品时获取价格应该如下", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String itemCode = Optional.ofNullable(caseData.get("itemCode")).orElse("");
				String preType = Optional.ofNullable(caseData.get("preType")).orElse("");
				String price = new BigDecimal(caseData.get("price")).setScale(2, RoundingMode.HALF_UP).toString();
				String distributionChannelCode = Optional.ofNullable(caseData.get("distributionChannelCode")).orElse("");
				String distributionChannelName = Optional.ofNullable(caseData.get("distributionChannelName")).orElse("");
				Response apiResult = orderApiPrediscountFetchPrice01.regionPreDiscountFetchProductPrice(OrderRegionPreDiscountFetchProductPriceDTO.builder()
						.preType(preType)
						.orderType("STANDARD")
						.saleOrgSapCode(saleOrgSapCode)
						.customerRegionCode(regionCode)
						.customerRegionName(regionName)
						.customerSapCode("N")
						.itemSapCodeOrCrmCodeOrDesc(itemCode)
						.build());
				apiResult.then().body("resultCode", Matchers.equalTo("0"), "resultMsg", Matchers.equalTo("success"));
				List<Map<String, String>> testCaseData = dataTable.asMaps().stream().map(map -> {
					Map<String, String> resultMap = new HashMap<>(map);
					resultMap.put("saleOrgSapCode", saleOrgSapCode);
					resultMap.put("customerSapCode", "N");
					resultMap.put("customerRegionCode", regionCode);
					return resultMap;
				}).collect(Collectors.toList());
				orderCheckOrderManagement01.checkAddProductFetchPrice(testCaseData, apiResult);
			});
			priceBatchClearData01.clearPirceData01("at");
			priceBatchClearData01.clearProductData01("at");
		});
	}

	private void scenario03() {
		Given("[大区预折商品获取单价03]当前存在如下客户信息", (DataTable dataTable) -> {
			createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis());
			domainDataTable = dataTable;
			domainDataTable.asMaps().forEach(caseData -> {
				saleOrgSapCode = Optional.ofNullable(caseData.get("saleOrgSapCode")).orElse("");
				customerSapCode = Optional.ofNullable(caseData.get("customerSapCode")).orElse("");
				regionCode = Optional.ofNullable(caseData.get("regionCode")).orElse("");
				regionName = Optional.ofNullable(caseData.get("regionName")).orElse("");
			});
		});
		When("[大区预折商品获取单价03]当前存在如下商品价格信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String dimensionCode = Optional.ofNullable(caseData.get("dimensionCode")).orElse("");
				String dimensionPriority = Optional.ofNullable(caseData.get("dimensionPriority")).orElse("");
				String distributionChannelCode = Optional.ofNullable(caseData.get("distributionChannelCode")).orElse("");
				String priceGroupCode = Optional.ofNullable(caseData.get("priceGroupCode")).orElse("");
				String customerCode = Optional.ofNullable(caseData.get("customerCode")).orElse("");
				String itemCode = Optional.ofNullable(caseData.get("itemCode")).orElse("");
				String price = new BigDecimal(caseData.get("price")).setScale(2, RoundingMode.HALF_UP).toString();
				priceBatchInsertData01.insertProductPriceData01(ProductPriceDTO.builder()
						.dimensionCode(dimensionCode)
						.dimensionPriority(dimensionPriority)
						.sellerCode(saleOrgSapCode)
						.distributionChannelCode(distributionChannelCode)
						.priceGroupCode(priceGroupCode)
						.customerCode(customerCode)
						.itemCode(itemCode)
						.startTime(createTime)
						.price(price)
						.createPerson("at")
						.createTime(createTime)
						.build());
			});
		});
		Then("[大区预折商品获取单价03]预折添加商品时获取价格应该如下", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String itemCode = Optional.ofNullable(caseData.get("itemCode")).orElse("");
				String preType = Optional.ofNullable(caseData.get("preType")).orElse("");
				String price = new BigDecimal(caseData.get("price")).setScale(2, RoundingMode.HALF_UP).toString();
				String distributionChannelCode = Optional.ofNullable(caseData.get("distributionChannelCode")).orElse("");
				String distributionChannelName = Optional.ofNullable(caseData.get("distributionChannelName")).orElse("");
				Response apiResult = orderApiPrediscountFetchPrice01.regionPreDiscountFetchProductPrice(OrderRegionPreDiscountFetchProductPriceDTO.builder()
						.preType(preType)
						.orderType("STANDARD")
						.saleOrgSapCode(saleOrgSapCode)
						.customerRegionCode(regionCode)
						.customerRegionName(regionName)
						.customerSapCode("N")
						.itemSapCodeOrCrmCodeOrDesc(itemCode)
						.build());
				apiResult.then().body("resultCode", Matchers.equalTo("0"), "resultMsg", Matchers.equalTo("success"));
				List<Map<String, String>> testCaseData = dataTable.asMaps().stream().map(map -> {
					Map<String, String> resultMap = new HashMap<>(map);
					resultMap.put("saleOrgSapCode", saleOrgSapCode);
					resultMap.put("customerSapCode", "N");
					resultMap.put("customerRegionCode", regionCode);
					return resultMap;
				}).collect(Collectors.toList());
				orderCheckOrderManagement01.checkAddProductFetchPrice(testCaseData, apiResult);
			});
			priceBatchClearData01.clearPirceData01("at");
			priceBatchClearData01.clearProductData01("at");
		});
	}

	private void scenario04() {
		Given("[大区预折商品获取单价04]当前存在如下客户信息", (DataTable dataTable) -> {
			createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis());
			domainDataTable = dataTable;
			domainDataTable.asMaps().forEach(caseData -> {
				saleOrgSapCode = Optional.ofNullable(caseData.get("saleOrgSapCode")).orElse("");
				customerSapCode = Optional.ofNullable(caseData.get("customerSapCode")).orElse("");
				regionCode = Optional.ofNullable(caseData.get("regionCode")).orElse("");
				regionName = Optional.ofNullable(caseData.get("regionName")).orElse("");
			});
		});
		When("[大区预折商品获取单价04]当前存在如下商品价格信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String dimensionCode = Optional.ofNullable(caseData.get("dimensionCode")).orElse("");
				String dimensionPriority = Optional.ofNullable(caseData.get("dimensionPriority")).orElse("");
				String distributionChannelCode = Optional.ofNullable(caseData.get("distributionChannelCode")).orElse("");
				String priceGroupCode = Optional.ofNullable(caseData.get("priceGroupCode")).orElse("");
				String customerCode = Optional.ofNullable(caseData.get("customerCode")).orElse("");
				String itemCode = Optional.ofNullable(caseData.get("itemCode")).orElse("");
				String price = new BigDecimal(caseData.get("price")).setScale(2, RoundingMode.HALF_UP).toString();
				priceBatchInsertData01.insertProductPriceData01(ProductPriceDTO.builder()
						.dimensionCode(dimensionCode)
						.dimensionPriority(dimensionPriority)
						.sellerCode(saleOrgSapCode)
						.distributionChannelCode(distributionChannelCode)
						.priceGroupCode(priceGroupCode)
						.customerCode(customerCode)
						.itemCode(itemCode)
						.startTime(createTime)
						.price(price)
						.createPerson("at")
						.createTime(createTime)
						.build());
			});
		});
		Then("[大区预折商品获取单价04]预折添加商品时获取价格应该如下", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String itemCode = Optional.ofNullable(caseData.get("itemCode")).orElse("");
				String preType = Optional.ofNullable(caseData.get("preType")).orElse("");
				String price = new BigDecimal(caseData.get("price")).setScale(2, RoundingMode.HALF_UP).toString();
				String distributionChannelCode = Optional.ofNullable(caseData.get("distributionChannelCode")).orElse("");
				String distributionChannelName = Optional.ofNullable(caseData.get("distributionChannelName")).orElse("");
				Response apiResult = orderApiPrediscountFetchPrice01.regionPreDiscountFetchProductPrice(OrderRegionPreDiscountFetchProductPriceDTO.builder()
						.preType(preType)
						.orderType("STANDARD")
						.saleOrgSapCode(saleOrgSapCode)
						.customerRegionCode(regionCode)
						.customerRegionName(regionName)
						.customerSapCode("N")
						.itemSapCodeOrCrmCodeOrDesc(itemCode)
						.build());
				apiResult.then().body("resultCode", Matchers.equalTo("0"), "resultMsg", Matchers.equalTo("success"));
				List<Map<String, String>> testCaseData = dataTable.asMaps().stream().map(map -> {
					Map<String, String> resultMap = new HashMap<>(map);
					resultMap.put("saleOrgSapCode", saleOrgSapCode);
					resultMap.put("customerSapCode", "N");
					resultMap.put("customerRegionCode", regionCode);
					return resultMap;
				}).collect(Collectors.toList());
				orderCheckOrderManagement01.checkAddProductFetchPrice(testCaseData, apiResult);
			});
			priceBatchClearData01.clearPirceData01("at");
			priceBatchClearData01.clearProductData01("at");
		});
	}

	private void scenario05() {
		Given("[大区预折商品获取单价05]当前存在如下客户信息", (DataTable dataTable) -> {
			createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis());
			domainDataTable = dataTable;
			domainDataTable.asMaps().forEach(caseData -> {
				saleOrgSapCode = Optional.ofNullable(caseData.get("saleOrgSapCode")).orElse("");
				customerSapCode = Optional.ofNullable(caseData.get("customerSapCode")).orElse("");
				regionCode = Optional.ofNullable(caseData.get("regionCode")).orElse("");
				regionName = Optional.ofNullable(caseData.get("regionName")).orElse("");
			});
		});
		When("[大区预折商品获取单价05]当前存在如下商品价格信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String dimensionCode = Optional.ofNullable(caseData.get("dimensionCode")).orElse("");
				String dimensionPriority = Optional.ofNullable(caseData.get("dimensionPriority")).orElse("");
				String distributionChannelCode = Optional.ofNullable(caseData.get("distributionChannelCode")).orElse("");
				String priceGroupCode = Optional.ofNullable(caseData.get("priceGroupCode")).orElse("");
				String customerCode = Optional.ofNullable(caseData.get("customerCode")).orElse("");
				String itemCode = Optional.ofNullable(caseData.get("itemCode")).orElse("");
				String price = new BigDecimal(caseData.get("price")).setScale(2, RoundingMode.HALF_UP).toString();
				priceBatchInsertData01.insertProductPriceData01(ProductPriceDTO.builder()
						.dimensionCode(dimensionCode)
						.dimensionPriority(dimensionPriority)
						.sellerCode(saleOrgSapCode)
						.distributionChannelCode(distributionChannelCode)
						.priceGroupCode(priceGroupCode)
						.customerCode(customerCode)
						.itemCode(itemCode)
						.startTime(createTime)
						.price(price)
						.createPerson("at")
						.createTime(createTime)
						.build());
			});
		});
		Then("[大区预折商品获取单价05]预折添加商品时获取价格应该如下", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String itemCode = Optional.ofNullable(caseData.get("itemCode")).orElse("");
				String preType = Optional.ofNullable(caseData.get("preType")).orElse("");
				String price = new BigDecimal(caseData.get("price")).setScale(2, RoundingMode.HALF_UP).toString();
				String distributionChannelCode = Optional.ofNullable(caseData.get("distributionChannelCode")).orElse("");
				String distributionChannelName = Optional.ofNullable(caseData.get("distributionChannelName")).orElse("");
				Response apiResult = orderApiPrediscountFetchPrice01.regionPreDiscountFetchProductPrice(OrderRegionPreDiscountFetchProductPriceDTO.builder()
						.preType(preType)
						.orderType("STANDARD")
						.saleOrgSapCode(saleOrgSapCode)
						.customerRegionCode(regionCode)
						.customerRegionName(regionName)
						.customerSapCode("N")
						.itemSapCodeOrCrmCodeOrDesc(itemCode)
						.build());
				apiResult.then().body("resultCode", Matchers.equalTo("0"), "resultMsg", Matchers.equalTo("success"));
				List<Map<String, String>> testCaseData = dataTable.asMaps().stream().map(map -> {
					Map<String, String> resultMap = new HashMap<>(map);
					resultMap.put("saleOrgSapCode", saleOrgSapCode);
					resultMap.put("customerSapCode", "N");
					resultMap.put("customerRegionCode", regionCode);
					return resultMap;
				}).collect(Collectors.toList());
				orderCheckOrderManagement01.checkAddProductFetchPrice(testCaseData, apiResult);
			});
			priceBatchClearData01.clearPirceData01("at");
			priceBatchClearData01.clearProductData01("at");
		});
	}

	private void scenario06() {
		Given("[大区预折商品获取单价06]当前存在如下客户信息", (DataTable dataTable) -> {
			createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis());
			domainDataTable = dataTable;
			domainDataTable.asMaps().forEach(caseData -> {
				saleOrgSapCode = Optional.ofNullable(caseData.get("saleOrgSapCode")).orElse("");
				customerSapCode = Optional.ofNullable(caseData.get("customerSapCode")).orElse("");
				regionCode = Optional.ofNullable(caseData.get("regionCode")).orElse("");
				regionName = Optional.ofNullable(caseData.get("regionName")).orElse("");
			});
		});
		When("[大区预折商品获取单价06]当前存在如下商品价格信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String dimensionCode = Optional.ofNullable(caseData.get("dimensionCode")).orElse("");
				String dimensionPriority = Optional.ofNullable(caseData.get("dimensionPriority")).orElse("");
				String distributionChannelCode = Optional.ofNullable(caseData.get("distributionChannelCode")).orElse("");
				String priceGroupCode = Optional.ofNullable(caseData.get("priceGroupCode")).orElse("");
				String customerCode = Optional.ofNullable(caseData.get("customerCode")).orElse("");
				String itemCode = Optional.ofNullable(caseData.get("itemCode")).orElse("");
				String price = new BigDecimal(caseData.get("price")).setScale(2, RoundingMode.HALF_UP).toString();
				priceBatchInsertData01.insertProductPriceData01(ProductPriceDTO.builder()
						.dimensionCode(dimensionCode)
						.dimensionPriority(dimensionPriority)
						.sellerCode(saleOrgSapCode)
						.distributionChannelCode(distributionChannelCode)
						.priceGroupCode(priceGroupCode)
						.customerCode(customerCode)
						.itemCode(itemCode)
						.startTime(createTime)
						.price(price)
						.createPerson("at")
						.createTime(createTime)
						.build());
			});
		});
		Then("[大区预折商品获取单价06]预折添加商品时获取价格应该如下", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String itemCode = Optional.ofNullable(caseData.get("itemCode")).orElse("");
				String preType = Optional.ofNullable(caseData.get("preType")).orElse("");
				String price = new BigDecimal(caseData.get("price")).setScale(2, RoundingMode.HALF_UP).toString();
				String distributionChannelCode = Optional.ofNullable(caseData.get("distributionChannelCode")).orElse("");
				String distributionChannelName = Optional.ofNullable(caseData.get("distributionChannelName")).orElse("");
				Response apiResult = orderApiPrediscountFetchPrice01.regionPreDiscountFetchProductPrice(OrderRegionPreDiscountFetchProductPriceDTO.builder()
						.preType(preType)
						.orderType("STANDARD")
						.saleOrgSapCode(saleOrgSapCode)
						.customerRegionCode(regionCode)
						.customerRegionName(regionName)
						.customerSapCode("N")
						.itemSapCodeOrCrmCodeOrDesc(itemCode)
						.build());
				apiResult.then().body("resultCode", Matchers.equalTo("0"), "resultMsg", Matchers.equalTo("success"));
				List<Map<String, String>> testCaseData = dataTable.asMaps().stream().map(map -> {
					Map<String, String> resultMap = new HashMap<>(map);
					resultMap.put("saleOrgSapCode", saleOrgSapCode);
					resultMap.put("customerSapCode", "N");
					resultMap.put("customerRegionCode", regionCode);
					return resultMap;
				}).collect(Collectors.toList());
				orderCheckOrderManagement01.checkAddProductFetchPrice(testCaseData, apiResult);
			});
			priceBatchClearData01.clearPirceData01("at");
			priceBatchClearData01.clearProductData01("at");
		});
	}

}
