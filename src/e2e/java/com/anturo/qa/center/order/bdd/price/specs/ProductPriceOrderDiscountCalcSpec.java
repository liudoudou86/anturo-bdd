package com.anturo.qa.center.order.bdd.price.specs;

import com.anturo.qa.center.order.bdd.order.apis.OrderApiOrderManagement01;
import com.anturo.qa.center.order.bdd.order.apis.OrderApiPrediscountFetchPrice01;
import com.anturo.qa.center.order.bdd.order.backgrounds.ProductBackgroundData05;
import com.anturo.qa.center.order.bdd.order.batchs.PriceBatchClearData01;
import com.anturo.qa.center.order.bdd.order.batchs.PriceBatchInsertData01;
import com.anturo.qa.center.order.bdd.order.checks.OrderCheckOrderManagement01;
import com.anturo.qa.center.order.main.model.dto.*;
import com.anturo.qa.center.order.process.CompanyEnum;
import com.anturo.qa.center.order.process.ProductEnum;
import com.anturo.qa.center.order.process.SaleOrgEnum;
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
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author Tesla
 * @Date 2024/08/26
 * @Description
 */


@SuppressWarnings("all")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class ProductPriceOrderDiscountCalcSpec extends ProductBackgroundData05 implements En {

	String saleOrgSapCode, customerSapCode, regionCode, regionName, shippingAddress, shippingRecipient, shippingTelephone;

	String createTime;

	DataTable domainDataTable;

	@Steps(actor = "QA", shared = true)
	PriceBatchInsertData01 priceBatchInsertData01;

	@Steps(actor = "QA", shared = true)
	PriceBatchClearData01 priceBatchClearData01;

	@Steps(actor = "QA", shared = true)
	OrderApiPrediscountFetchPrice01 orderApiPrediscountFetchPrice01;

	@Steps(actor = "QA", shared = true)
	OrderApiOrderManagement01 orderApiOrderManagement01;

	@Steps(actor = "QA", shared = true)
	OrderCheckOrderManagement01 orderCheckOrderManagement01;


	public ProductPriceOrderDiscountCalcSpec() {
		super();
		this.scenario01();
		this.scenario02();
		this.scenario03();
		this.scenario04();
		this.scenario05();
		this.scenario06();
		this.scenario07();
		this.scenario08();
		this.scenario09();
		this.scenario10();
		this.scenario11();
		this.scenario12();
	}

	private void scenario01() {
		Given("[创建订单整单折扣计算获取单价01]当前存在如下客户信息", (DataTable dataTable) -> {
			createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis());
			domainDataTable = dataTable;
			domainDataTable.asMaps().forEach(caseData -> {
				saleOrgSapCode = Optional.ofNullable(caseData.get("saleOrgSapCode")).orElse("");
				customerSapCode = Optional.ofNullable(caseData.get("customerSapCode")).orElse("");
				regionCode = Optional.ofNullable(caseData.get("regionCode")).orElse("");
				regionName = Optional.ofNullable(caseData.get("regionName")).orElse("");
				shippingAddress = Optional.ofNullable(caseData.get("shippingAddress")).orElse("");
				shippingRecipient = Optional.ofNullable(caseData.get("shippingRecipient")).orElse("");
				shippingTelephone = Optional.ofNullable(caseData.get("shippingTelephone")).orElse("");
			});
		});
		When("[创建订单整单折扣计算获取单价01]当前存在如下商品价格信息", (DataTable dataTable) -> {
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
		And("[创建订单整单折扣计算获取单价01]创建订单添加商品时获取价格应该如下", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String itemCode = Optional.ofNullable(caseData.get("itemCode")).orElse("");
				String price = new BigDecimal(caseData.get("price")).setScale(2, RoundingMode.HALF_UP).toString();
				String distributionChannelCode = Optional.ofNullable(caseData.get("distributionChannelCode")).orElse("");
				Response apiResult = orderApiPrediscountFetchPrice01.orderAddProductFetchProductPrice(OrderAddProductFetchProductPriceDTO.builder()
						.orderType("STANDARD")
						.saleOrgSapCode(saleOrgSapCode)
						.customerSapCode(customerSapCode)
						.customerRegionCode(regionCode)
						.itemSapCodeOrCrmCodeOrDesc(itemCode)
						.build());
				apiResult.then().body("resultCode", Matchers.equalTo("0"), "resultMsg", Matchers.equalTo("success"));
				List<Map<String, String>> testCaseData = dataTable.asMaps().stream().map(map -> {
					Map<String, String> resultMap = new HashMap<>(map);
					resultMap.put("saleOrgSapCode", saleOrgSapCode);
					resultMap.put("customerSapCode", customerSapCode);
					resultMap.put("customerRegionCode", regionCode);
					return resultMap;
				}).collect(Collectors.toList());
				orderCheckOrderManagement01.checkAddProductFetchPrice(testCaseData, apiResult);
			});
		});
		Then("[创建订单整单折扣计算获取单价01]进行整单折扣计算后,订单行分摊如下", (DataTable dataTable) -> {
			String totalInlineSum = dataTable.asMaps().stream()
					.map(caseData -> new BigDecimal(caseData.get("totalInline")).setScale(2, RoundingMode.HALF_UP))
					.reduce(BigDecimal::add)
					.orElse(BigDecimal.ZERO).toString();
			String preDiscountTotalSum = dataTable.asMaps().stream()
					.map(caseData -> new BigDecimal(caseData.get("preDiscountTotal")).setScale(2, RoundingMode.HALF_UP))
					.reduce(BigDecimal::add)
					.orElse(BigDecimal.ZERO).toString();
			String orderDiscountTotalSum = dataTable.asMaps().stream()
					.map(caseData -> new BigDecimal(caseData.get("orderDiscountTotal")).setScale(2, RoundingMode.HALF_UP))
					.reduce(BigDecimal::add)
					.orElse(BigDecimal.ZERO).toString();
			List<OrderDiscountCalcCalculateReqGroupListDTO> orderDiscountCalcCalculateReqGroupListDTOS = new ArrayList<>();
			dataTable.asMaps().forEach(caseData -> {
				String id = "100000" + Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String orderWideDiscountSubject = Optional.ofNullable(caseData.get("orderWideDiscountSubject")).orElse("");
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String distributionChannelCode = Optional.ofNullable(caseData.get("distributionChannelCode")).orElse("");
				String distributionChannelName = Optional.ofNullable(caseData.get("distributionChannelName")).orElse("");
				String needShip = Optional.ofNullable(caseData.get("needShip")).orElse("");
				String unitPrice = new BigDecimal(caseData.get("unitPrice")).setScale(2, RoundingMode.HALF_UP).toString();
				String quantity = Optional.ofNullable(caseData.get("quantity")).orElse("");
				String boxRatio = new Integer(caseData.get("boxRatio")).toString();
				String preDiscount = new BigDecimal(caseData.get("preDiscount")).setScale(2, RoundingMode.HALF_UP).toString();
				String calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(2, RoundingMode.HALF_UP).toString();
				String totalInline = new BigDecimal(caseData.get("totalInline")).setScale(2, RoundingMode.HALF_UP).toString();
				String preDiscountTotal = new BigDecimal(caseData.get("preDiscountTotal")).setScale(2, RoundingMode.HALF_UP).toString();
				String orderDiscountTotal = new BigDecimal(caseData.get("orderDiscountTotal")).setScale(2, RoundingMode.HALF_UP).toString();
				orderDiscountCalcCalculateReqGroupListDTOS.add(OrderDiscountCalcCalculateReqGroupListDTO.builder()
						.itemGroupCode("01")
						.itemGroupName("成品药-非管制药品")
						.distributionChannelCode(distributionChannelCode)
						.distributionChannelName(distributionChannelName)
						.totalInline(totalInline)
						.preDiscountTotal(preDiscountTotal)
						.orderDiscountTotal(orderDiscountTotal)
						.list(Arrays.asList(
								OrderDiscountCalcCalculateReqGroupListListDTO.builder()
										.orderWideDiscountSubject(orderWideDiscountSubject)
										.itemSapCode(itemSapCode)
										.itemName(ProductEnum.fetchProductName(itemSapCode))
										.itemDesc(ProductEnum.fetchProductSpec(itemSapCode))
										.itemGroupCode("01")
										.itemGroupName("成品药-非管制药品")
										.distributionChannelCode(distributionChannelCode)
										.distributionChannelName(distributionChannelName)
										.unit("盒")
										.unitDesc("盒")
										.unitPrice(unitPrice)
										.calculationValue(calculationValue)
										.preDiscount(preDiscount)
										.preType("CUSTOMER_PRE_DISCOUNT")
										.calculationType("DISCOUNTED_PRICE")
										.quantity(quantity)
										.packingRatio("")
										.totalInline(totalInline)
										.preDiscountTotal(preDiscountTotal)
										.needShip(needShip)
										.baseUnitCode("盒")
										.baseUnitName("盒")
										.boxRatio(boxRatio)
										.xunit("1")
										.yunit("1")
										.build()))
						.build());
			});
			Response discountCalcResponse = orderApiOrderManagement01.discountCalc(OrderDataDTO.builder()
					.orderType("STANDARD")
					.saleOrgSapCode(saleOrgSapCode)
					.saleOrgName(SaleOrgEnum.fetchName(saleOrgSapCode))
					.regionCode(regionCode)
					.regionName(regionName)
					.customerEntSapCode(customerSapCode)
					.customerEntCrmCode(CompanyEnum.fetchCrmCode(customerSapCode))
					.customerEntZtCode(CompanyEnum.fetchZtCode(customerSapCode))
					.customerName(CompanyEnum.fetchName(customerSapCode))
					.invoiceType("VAT_SPECIAL_INVOICE")
					.shippingPolicy("NON_FULL_SHIPMENT")
					.priority("NORMAL")
					.paymentMethod("PRE_PAYMENT")
					.shippingRecipient(shippingRecipient)
					.shippingTelephone(shippingTelephone)
					.shippingAddress(shippingAddress)
					.remark("自动化测试")
					.calculateReq(OrderDiscountCalcCalculateReqDTO.builder()
							.totalInline(totalInlineSum)
							.preDiscountTotal(preDiscountTotalSum)
							.orderDiscountTotal(orderDiscountTotalSum)
							.groupList(orderDiscountCalcCalculateReqGroupListDTOS)
							.build())
					.build());
			discountCalcResponse.then().body("resultCode", Matchers.equalTo("0"), "resultMsg", Matchers.equalTo("success"));
			orderCheckOrderManagement01.checkOrderDiscountCalc(dataTable, discountCalcResponse);
			priceBatchClearData01.clearPirceData01("at");
			orderBatchClearData01.clearOrderPreDiscountData01("at");
			orderBatchClearData01.clearDiscountData01("at");
		});
	}

	private void scenario02() {
		Given("[创建订单整单折扣计算获取单价02]当前存在如下客户信息", (DataTable dataTable) -> {
			createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis());
			domainDataTable = dataTable;
			domainDataTable.asMaps().forEach(caseData -> {
				saleOrgSapCode = Optional.ofNullable(caseData.get("saleOrgSapCode")).orElse("");
				customerSapCode = Optional.ofNullable(caseData.get("customerSapCode")).orElse("");
				regionCode = Optional.ofNullable(caseData.get("regionCode")).orElse("");
				regionName = Optional.ofNullable(caseData.get("regionName")).orElse("");
				shippingAddress = Optional.ofNullable(caseData.get("shippingAddress")).orElse("");
				shippingRecipient = Optional.ofNullable(caseData.get("shippingRecipient")).orElse("");
				shippingTelephone = Optional.ofNullable(caseData.get("shippingTelephone")).orElse("");
			});
		});
		When("[创建订单整单折扣计算获取单价02]当前存在如下商品价格信息", (DataTable dataTable) -> {
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
		And("[创建订单整单折扣计算获取单价02]创建订单添加商品时获取价格应该如下", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String itemCode = Optional.ofNullable(caseData.get("itemCode")).orElse("");
				String price = new BigDecimal(caseData.get("price")).setScale(2, RoundingMode.HALF_UP).toString();
				String distributionChannelCode = Optional.ofNullable(caseData.get("distributionChannelCode")).orElse("");
				Response apiResult = orderApiPrediscountFetchPrice01.orderAddProductFetchProductPrice(OrderAddProductFetchProductPriceDTO.builder()
						.orderType("STANDARD")
						.saleOrgSapCode(saleOrgSapCode)
						.customerSapCode(customerSapCode)
						.customerRegionCode(regionCode)
						.itemSapCodeOrCrmCodeOrDesc(itemCode)
						.build());
				apiResult.then().body("resultCode", Matchers.equalTo("0"), "resultMsg", Matchers.equalTo("success"));
				List<Map<String, String>> testCaseData = dataTable.asMaps().stream().map(map -> {
					Map<String, String> resultMap = new HashMap<>(map);
					resultMap.put("saleOrgSapCode", saleOrgSapCode);
					resultMap.put("customerSapCode", customerSapCode);
					resultMap.put("customerRegionCode", regionCode);
					return resultMap;
				}).collect(Collectors.toList());
				orderCheckOrderManagement01.checkAddProductFetchPrice(testCaseData, apiResult);
			});
		});
		Then("[创建订单整单折扣计算获取单价02]进行整单折扣计算后,订单行分摊如下", (DataTable dataTable) -> {
			String totalInlineSum = dataTable.asMaps().stream()
					.map(caseData -> new BigDecimal(caseData.get("totalInline")).setScale(2, RoundingMode.HALF_UP))
					.reduce(BigDecimal::add)
					.orElse(BigDecimal.ZERO).toString();
			String preDiscountTotalSum = dataTable.asMaps().stream()
					.map(caseData -> new BigDecimal(caseData.get("preDiscountTotal")).setScale(2, RoundingMode.HALF_UP))
					.reduce(BigDecimal::add)
					.orElse(BigDecimal.ZERO).toString();
			String orderDiscountTotalSum = dataTable.asMaps().stream()
					.map(caseData -> new BigDecimal(caseData.get("orderDiscountTotal")).setScale(2, RoundingMode.HALF_UP))
					.reduce(BigDecimal::add)
					.orElse(BigDecimal.ZERO).toString();
			List<OrderDiscountCalcCalculateReqGroupListDTO> orderDiscountCalcCalculateReqGroupListDTOS = new ArrayList<>();
			dataTable.asMaps().forEach(caseData -> {
				String id = "100000" + Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String orderWideDiscountSubject = Optional.ofNullable(caseData.get("orderWideDiscountSubject")).orElse("");
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String distributionChannelCode = Optional.ofNullable(caseData.get("distributionChannelCode")).orElse("");
				String distributionChannelName = Optional.ofNullable(caseData.get("distributionChannelName")).orElse("");
				String needShip = Optional.ofNullable(caseData.get("needShip")).orElse("");
				String unitPrice = new BigDecimal(caseData.get("unitPrice")).setScale(2, RoundingMode.HALF_UP).toString();
				String quantity = Optional.ofNullable(caseData.get("quantity")).orElse("");
				String boxRatio = new Integer(caseData.get("boxRatio")).toString();
				String preDiscount = new BigDecimal(caseData.get("preDiscount")).setScale(2, RoundingMode.HALF_UP).toString();
				String calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(2, RoundingMode.HALF_UP).toString();
				String totalInline = new BigDecimal(caseData.get("totalInline")).setScale(2, RoundingMode.HALF_UP).toString();
				String preDiscountTotal = new BigDecimal(caseData.get("preDiscountTotal")).setScale(2, RoundingMode.HALF_UP).toString();
				String orderDiscountTotal = new BigDecimal(caseData.get("orderDiscountTotal")).setScale(2, RoundingMode.HALF_UP).toString();
				orderDiscountCalcCalculateReqGroupListDTOS.add(OrderDiscountCalcCalculateReqGroupListDTO.builder()
						.itemGroupCode("01")
						.itemGroupName("成品药-非管制药品")
						.distributionChannelCode(distributionChannelCode)
						.distributionChannelName(distributionChannelName)
						.totalInline(totalInline)
						.preDiscountTotal(preDiscountTotal)
						.orderDiscountTotal(orderDiscountTotal)
						.list(Arrays.asList(
								OrderDiscountCalcCalculateReqGroupListListDTO.builder()
										.orderWideDiscountSubject(orderWideDiscountSubject)
										.itemSapCode(itemSapCode)
										.itemName(ProductEnum.fetchProductName(itemSapCode))
										.itemDesc(ProductEnum.fetchProductSpec(itemSapCode))
										.itemGroupCode("01")
										.itemGroupName("成品药-非管制药品")
										.distributionChannelCode(distributionChannelCode)
										.distributionChannelName(distributionChannelName)
										.unit("盒")
										.unitDesc("盒")
										.unitPrice(unitPrice)
										.calculationValue(calculationValue)
										.preDiscount(preDiscount)
										.preType("CUSTOMER_PRE_DISCOUNT")
										.calculationType("DISCOUNTED_PRICE")
										.quantity(quantity)
										.packingRatio("")
										.totalInline(totalInline)
										.preDiscountTotal(preDiscountTotal)
										.needShip(needShip)
										.baseUnitCode("盒")
										.baseUnitName("盒")
										.boxRatio(boxRatio)
										.xunit("1")
										.yunit("1")
										.build()))
						.build());
			});
			Response discountCalcResponse = orderApiOrderManagement01.discountCalc(OrderDataDTO.builder()
					.orderType("STANDARD")
					.saleOrgSapCode(saleOrgSapCode)
					.saleOrgName(SaleOrgEnum.fetchName(saleOrgSapCode))
					.regionCode(regionCode)
					.regionName(regionName)
					.customerEntSapCode(customerSapCode)
					.customerEntCrmCode(CompanyEnum.fetchCrmCode(customerSapCode))
					.customerEntZtCode(CompanyEnum.fetchZtCode(customerSapCode))
					.customerName(CompanyEnum.fetchName(customerSapCode))
					.invoiceType("VAT_SPECIAL_INVOICE")
					.shippingPolicy("NON_FULL_SHIPMENT")
					.priority("NORMAL")
					.paymentMethod("PRE_PAYMENT")
					.shippingRecipient(shippingRecipient)
					.shippingTelephone(shippingTelephone)
					.shippingAddress(shippingAddress)
					.remark("自动化测试")
					.calculateReq(OrderDiscountCalcCalculateReqDTO.builder()
							.totalInline(totalInlineSum)
							.preDiscountTotal(preDiscountTotalSum)
							.orderDiscountTotal(orderDiscountTotalSum)
							.groupList(orderDiscountCalcCalculateReqGroupListDTOS)
							.build())
					.build());
			discountCalcResponse.then().body("resultCode", Matchers.equalTo("0"), "resultMsg", Matchers.equalTo("success"));
			orderCheckOrderManagement01.checkOrderDiscountCalc(dataTable, discountCalcResponse);
			priceBatchClearData01.clearPirceData01("at");
			orderBatchClearData01.clearOrderPreDiscountData01("at");
			orderBatchClearData01.clearDiscountData01("at");
		});
	}

	private void scenario03() {
		Given("[创建订单整单折扣计算获取单价03]当前存在如下客户信息", (DataTable dataTable) -> {
			createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis());
			domainDataTable = dataTable;
			domainDataTable.asMaps().forEach(caseData -> {
				saleOrgSapCode = Optional.ofNullable(caseData.get("saleOrgSapCode")).orElse("");
				customerSapCode = Optional.ofNullable(caseData.get("customerSapCode")).orElse("");
				regionCode = Optional.ofNullable(caseData.get("regionCode")).orElse("");
				regionName = Optional.ofNullable(caseData.get("regionName")).orElse("");
				shippingAddress = Optional.ofNullable(caseData.get("shippingAddress")).orElse("");
				shippingRecipient = Optional.ofNullable(caseData.get("shippingRecipient")).orElse("");
				shippingTelephone = Optional.ofNullable(caseData.get("shippingTelephone")).orElse("");
			});
		});
		When("[创建订单整单折扣计算获取单价03]当前存在如下商品价格信息", (DataTable dataTable) -> {
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
		And("[创建订单整单折扣计算获取单价03]创建订单添加商品时获取价格应该如下", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String itemCode = Optional.ofNullable(caseData.get("itemCode")).orElse("");
				String price = new BigDecimal(caseData.get("price")).setScale(2, RoundingMode.HALF_UP).toString();
				String distributionChannelCode = Optional.ofNullable(caseData.get("distributionChannelCode")).orElse("");
				Response apiResult = orderApiPrediscountFetchPrice01.orderAddProductFetchProductPrice(OrderAddProductFetchProductPriceDTO.builder()
						.orderType("STANDARD")
						.saleOrgSapCode(saleOrgSapCode)
						.customerSapCode(customerSapCode)
						.customerRegionCode(regionCode)
						.itemSapCodeOrCrmCodeOrDesc(itemCode)
						.build());
				apiResult.then().body("resultCode", Matchers.equalTo("0"), "resultMsg", Matchers.equalTo("success"));
				List<Map<String, String>> testCaseData = dataTable.asMaps().stream().map(map -> {
					Map<String, String> resultMap = new HashMap<>(map);
					resultMap.put("saleOrgSapCode", saleOrgSapCode);
					resultMap.put("customerSapCode", customerSapCode);
					resultMap.put("customerRegionCode", regionCode);
					return resultMap;
				}).collect(Collectors.toList());
				orderCheckOrderManagement01.checkAddProductFetchPrice(testCaseData, apiResult);
			});
		});
		Then("[创建订单整单折扣计算获取单价03]进行整单折扣计算后,订单行分摊如下", (DataTable dataTable) -> {
			String totalInlineSum = dataTable.asMaps().stream()
					.map(caseData -> new BigDecimal(caseData.get("totalInline")).setScale(2, RoundingMode.HALF_UP))
					.reduce(BigDecimal::add)
					.orElse(BigDecimal.ZERO).toString();
			String preDiscountTotalSum = dataTable.asMaps().stream()
					.map(caseData -> new BigDecimal(caseData.get("preDiscountTotal")).setScale(2, RoundingMode.HALF_UP))
					.reduce(BigDecimal::add)
					.orElse(BigDecimal.ZERO).toString();
			String orderDiscountTotalSum = dataTable.asMaps().stream()
					.map(caseData -> new BigDecimal(caseData.get("orderDiscountTotal")).setScale(2, RoundingMode.HALF_UP))
					.reduce(BigDecimal::add)
					.orElse(BigDecimal.ZERO).toString();
			List<OrderDiscountCalcCalculateReqGroupListDTO> orderDiscountCalcCalculateReqGroupListDTOS = new ArrayList<>();
			dataTable.asMaps().forEach(caseData -> {
				String id = "100000" + Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String orderWideDiscountSubject = Optional.ofNullable(caseData.get("orderWideDiscountSubject")).orElse("");
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String distributionChannelCode = Optional.ofNullable(caseData.get("distributionChannelCode")).orElse("");
				String distributionChannelName = Optional.ofNullable(caseData.get("distributionChannelName")).orElse("");
				String needShip = Optional.ofNullable(caseData.get("needShip")).orElse("");
				String unitPrice = new BigDecimal(caseData.get("unitPrice")).setScale(2, RoundingMode.HALF_UP).toString();
				String quantity = Optional.ofNullable(caseData.get("quantity")).orElse("");
				String boxRatio = new Integer(caseData.get("boxRatio")).toString();
				String preDiscount = new BigDecimal(caseData.get("preDiscount")).setScale(2, RoundingMode.HALF_UP).toString();
				String calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(2, RoundingMode.HALF_UP).toString();
				String totalInline = new BigDecimal(caseData.get("totalInline")).setScale(2, RoundingMode.HALF_UP).toString();
				String preDiscountTotal = new BigDecimal(caseData.get("preDiscountTotal")).setScale(2, RoundingMode.HALF_UP).toString();
				String orderDiscountTotal = new BigDecimal(caseData.get("orderDiscountTotal")).setScale(2, RoundingMode.HALF_UP).toString();
				orderDiscountCalcCalculateReqGroupListDTOS.add(OrderDiscountCalcCalculateReqGroupListDTO.builder()
						.itemGroupCode("01")
						.itemGroupName("成品药-非管制药品")
						.distributionChannelCode(distributionChannelCode)
						.distributionChannelName(distributionChannelName)
						.totalInline(totalInline)
						.preDiscountTotal(preDiscountTotal)
						.orderDiscountTotal(orderDiscountTotal)
						.list(Arrays.asList(
								OrderDiscountCalcCalculateReqGroupListListDTO.builder()
										.orderWideDiscountSubject(orderWideDiscountSubject)
										.itemSapCode(itemSapCode)
										.itemName(ProductEnum.fetchProductName(itemSapCode))
										.itemDesc(ProductEnum.fetchProductSpec(itemSapCode))
										.itemGroupCode("01")
										.itemGroupName("成品药-非管制药品")
										.distributionChannelCode(distributionChannelCode)
										.distributionChannelName(distributionChannelName)
										.unit("盒")
										.unitDesc("盒")
										.unitPrice(unitPrice)
										.calculationValue(calculationValue)
										.preDiscount(preDiscount)
										.preType("CUSTOMER_PRE_DISCOUNT")
										.calculationType("DISCOUNTED_PRICE")
										.quantity(quantity)
										.packingRatio("")
										.totalInline(totalInline)
										.preDiscountTotal(preDiscountTotal)
										.needShip(needShip)
										.baseUnitCode("盒")
										.baseUnitName("盒")
										.boxRatio(boxRatio)
										.xunit("1")
										.yunit("1")
										.build()))
						.build());
			});
			Response discountCalcResponse = orderApiOrderManagement01.discountCalc(OrderDataDTO.builder()
					.orderType("STANDARD")
					.saleOrgSapCode(saleOrgSapCode)
					.saleOrgName(SaleOrgEnum.fetchName(saleOrgSapCode))
					.regionCode(regionCode)
					.regionName(regionName)
					.customerEntSapCode(customerSapCode)
					.customerEntCrmCode(CompanyEnum.fetchCrmCode(customerSapCode))
					.customerEntZtCode(CompanyEnum.fetchZtCode(customerSapCode))
					.customerName(CompanyEnum.fetchName(customerSapCode))
					.invoiceType("VAT_SPECIAL_INVOICE")
					.shippingPolicy("NON_FULL_SHIPMENT")
					.priority("NORMAL")
					.paymentMethod("PRE_PAYMENT")
					.shippingRecipient(shippingRecipient)
					.shippingTelephone(shippingTelephone)
					.shippingAddress(shippingAddress)
					.remark("自动化测试")
					.calculateReq(OrderDiscountCalcCalculateReqDTO.builder()
							.totalInline(totalInlineSum)
							.preDiscountTotal(preDiscountTotalSum)
							.orderDiscountTotal(orderDiscountTotalSum)
							.groupList(orderDiscountCalcCalculateReqGroupListDTOS)
							.build())
					.build());
			discountCalcResponse.then().body("resultCode", Matchers.equalTo("0"), "resultMsg", Matchers.equalTo("success"));
			orderCheckOrderManagement01.checkOrderDiscountCalc(dataTable, discountCalcResponse);
			priceBatchClearData01.clearPirceData01("at");
			orderBatchClearData01.clearOrderPreDiscountData01("at");
			orderBatchClearData01.clearDiscountData01("at");
		});
	}

	private void scenario04() {
		Given("[创建订单整单折扣计算获取单价04]当前存在如下客户信息", (DataTable dataTable) -> {
			createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis());
			domainDataTable = dataTable;
			domainDataTable.asMaps().forEach(caseData -> {
				saleOrgSapCode = Optional.ofNullable(caseData.get("saleOrgSapCode")).orElse("");
				customerSapCode = Optional.ofNullable(caseData.get("customerSapCode")).orElse("");
				regionCode = Optional.ofNullable(caseData.get("regionCode")).orElse("");
				regionName = Optional.ofNullable(caseData.get("regionName")).orElse("");
				shippingAddress = Optional.ofNullable(caseData.get("shippingAddress")).orElse("");
				shippingRecipient = Optional.ofNullable(caseData.get("shippingRecipient")).orElse("");
				shippingTelephone = Optional.ofNullable(caseData.get("shippingTelephone")).orElse("");
			});
		});
		When("[创建订单整单折扣计算获取单价04]当前存在如下商品价格信息", (DataTable dataTable) -> {
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
		And("[创建订单整单折扣计算获取单价04]创建订单添加商品时获取价格应该如下", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String itemCode = Optional.ofNullable(caseData.get("itemCode")).orElse("");
				String price = new BigDecimal(caseData.get("price")).setScale(2, RoundingMode.HALF_UP).toString();
				String distributionChannelCode = Optional.ofNullable(caseData.get("distributionChannelCode")).orElse("");
				Response apiResult = orderApiPrediscountFetchPrice01.orderAddProductFetchProductPrice(OrderAddProductFetchProductPriceDTO.builder()
						.orderType("STANDARD")
						.saleOrgSapCode(saleOrgSapCode)
						.customerSapCode(customerSapCode)
						.customerRegionCode(regionCode)
						.itemSapCodeOrCrmCodeOrDesc(itemCode)
						.build());
				apiResult.then().body("resultCode", Matchers.equalTo("0"), "resultMsg", Matchers.equalTo("success"));
				List<Map<String, String>> testCaseData = dataTable.asMaps().stream().map(map -> {
					Map<String, String> resultMap = new HashMap<>(map);
					resultMap.put("saleOrgSapCode", saleOrgSapCode);
					resultMap.put("customerSapCode", customerSapCode);
					resultMap.put("customerRegionCode", regionCode);
					return resultMap;
				}).collect(Collectors.toList());
				orderCheckOrderManagement01.checkAddProductFetchPrice(testCaseData, apiResult);
			});
		});
		Then("[创建订单整单折扣计算获取单价04]进行整单折扣计算后,订单行分摊如下", (DataTable dataTable) -> {
			String totalInlineSum = dataTable.asMaps().stream()
					.map(caseData -> new BigDecimal(caseData.get("totalInline")).setScale(2, RoundingMode.HALF_UP))
					.reduce(BigDecimal::add)
					.orElse(BigDecimal.ZERO).toString();
			String preDiscountTotalSum = dataTable.asMaps().stream()
					.map(caseData -> new BigDecimal(caseData.get("preDiscountTotal")).setScale(2, RoundingMode.HALF_UP))
					.reduce(BigDecimal::add)
					.orElse(BigDecimal.ZERO).toString();
			String orderDiscountTotalSum = dataTable.asMaps().stream()
					.map(caseData -> new BigDecimal(caseData.get("orderDiscountTotal")).setScale(2, RoundingMode.HALF_UP))
					.reduce(BigDecimal::add)
					.orElse(BigDecimal.ZERO).toString();
			List<OrderDiscountCalcCalculateReqGroupListDTO> orderDiscountCalcCalculateReqGroupListDTOS = new ArrayList<>();
			dataTable.asMaps().forEach(caseData -> {
				String id = "100000" + Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String orderWideDiscountSubject = Optional.ofNullable(caseData.get("orderWideDiscountSubject")).orElse("");
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String distributionChannelCode = Optional.ofNullable(caseData.get("distributionChannelCode")).orElse("");
				String distributionChannelName = Optional.ofNullable(caseData.get("distributionChannelName")).orElse("");
				String needShip = Optional.ofNullable(caseData.get("needShip")).orElse("");
				String unitPrice = new BigDecimal(caseData.get("unitPrice")).setScale(2, RoundingMode.HALF_UP).toString();
				String quantity = Optional.ofNullable(caseData.get("quantity")).orElse("");
				String boxRatio = new Integer(caseData.get("boxRatio")).toString();
				String preDiscount = new BigDecimal(caseData.get("preDiscount")).setScale(2, RoundingMode.HALF_UP).toString();
				String calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(2, RoundingMode.HALF_UP).toString();
				String totalInline = new BigDecimal(caseData.get("totalInline")).setScale(2, RoundingMode.HALF_UP).toString();
				String preDiscountTotal = new BigDecimal(caseData.get("preDiscountTotal")).setScale(2, RoundingMode.HALF_UP).toString();
				String orderDiscountTotal = new BigDecimal(caseData.get("orderDiscountTotal")).setScale(2, RoundingMode.HALF_UP).toString();
				orderDiscountCalcCalculateReqGroupListDTOS.add(OrderDiscountCalcCalculateReqGroupListDTO.builder()
						.itemGroupCode("01")
						.itemGroupName("成品药-非管制药品")
						.distributionChannelCode(distributionChannelCode)
						.distributionChannelName(distributionChannelName)
						.totalInline(totalInline)
						.preDiscountTotal(preDiscountTotal)
						.orderDiscountTotal(orderDiscountTotal)
						.list(Arrays.asList(
								OrderDiscountCalcCalculateReqGroupListListDTO.builder()
										.orderWideDiscountSubject(orderWideDiscountSubject)
										.itemSapCode(itemSapCode)
										.itemName(ProductEnum.fetchProductName(itemSapCode))
										.itemDesc(ProductEnum.fetchProductSpec(itemSapCode))
										.itemGroupCode("01")
										.itemGroupName("成品药-非管制药品")
										.distributionChannelCode(distributionChannelCode)
										.distributionChannelName(distributionChannelName)
										.unit("盒")
										.unitDesc("盒")
										.unitPrice(unitPrice)
										.calculationValue(calculationValue)
										.preDiscount(preDiscount)
										.preType("CUSTOMER_PRE_DISCOUNT")
										.calculationType("DISCOUNTED_PRICE")
										.quantity(quantity)
										.packingRatio("")
										.totalInline(totalInline)
										.preDiscountTotal(preDiscountTotal)
										.needShip(needShip)
										.baseUnitCode("盒")
										.baseUnitName("盒")
										.boxRatio(boxRatio)
										.xunit("1")
										.yunit("1")
										.build()))
						.build());
			});
			Response discountCalcResponse = orderApiOrderManagement01.discountCalc(OrderDataDTO.builder()
					.orderType("STANDARD")
					.saleOrgSapCode(saleOrgSapCode)
					.saleOrgName(SaleOrgEnum.fetchName(saleOrgSapCode))
					.regionCode(regionCode)
					.regionName(regionName)
					.customerEntSapCode(customerSapCode)
					.customerEntCrmCode(CompanyEnum.fetchCrmCode(customerSapCode))
					.customerEntZtCode(CompanyEnum.fetchZtCode(customerSapCode))
					.customerName(CompanyEnum.fetchName(customerSapCode))
					.invoiceType("VAT_SPECIAL_INVOICE")
					.shippingPolicy("NON_FULL_SHIPMENT")
					.priority("NORMAL")
					.paymentMethod("PRE_PAYMENT")
					.shippingRecipient(shippingRecipient)
					.shippingTelephone(shippingTelephone)
					.shippingAddress(shippingAddress)
					.remark("自动化测试")
					.calculateReq(OrderDiscountCalcCalculateReqDTO.builder()
							.totalInline(totalInlineSum)
							.preDiscountTotal(preDiscountTotalSum)
							.orderDiscountTotal(orderDiscountTotalSum)
							.groupList(orderDiscountCalcCalculateReqGroupListDTOS)
							.build())
					.build());
			discountCalcResponse.then().body("resultCode", Matchers.equalTo("0"), "resultMsg", Matchers.equalTo("success"));
			orderCheckOrderManagement01.checkOrderDiscountCalc(dataTable, discountCalcResponse);
			priceBatchClearData01.clearPirceData01("at");
			orderBatchClearData01.clearOrderPreDiscountData01("at");
			orderBatchClearData01.clearDiscountData01("at");
		});
	}

	private void scenario05() {
		Given("[创建订单整单折扣计算获取单价05]当前存在如下客户信息", (DataTable dataTable) -> {
			createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis());
			domainDataTable = dataTable;
			domainDataTable.asMaps().forEach(caseData -> {
				saleOrgSapCode = Optional.ofNullable(caseData.get("saleOrgSapCode")).orElse("");
				customerSapCode = Optional.ofNullable(caseData.get("customerSapCode")).orElse("");
				regionCode = Optional.ofNullable(caseData.get("regionCode")).orElse("");
				regionName = Optional.ofNullable(caseData.get("regionName")).orElse("");
				shippingAddress = Optional.ofNullable(caseData.get("shippingAddress")).orElse("");
				shippingRecipient = Optional.ofNullable(caseData.get("shippingRecipient")).orElse("");
				shippingTelephone = Optional.ofNullable(caseData.get("shippingTelephone")).orElse("");
			});
		});
		When("[创建订单整单折扣计算获取单价05]当前存在如下商品价格信息", (DataTable dataTable) -> {
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
		And("[创建订单整单折扣计算获取单价05]创建订单添加商品时获取价格应该如下", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String itemCode = Optional.ofNullable(caseData.get("itemCode")).orElse("");
				String price = new BigDecimal(caseData.get("price")).setScale(2, RoundingMode.HALF_UP).toString();
				String distributionChannelCode = Optional.ofNullable(caseData.get("distributionChannelCode")).orElse("");
				Response apiResult = orderApiPrediscountFetchPrice01.orderAddProductFetchProductPrice(OrderAddProductFetchProductPriceDTO.builder()
						.orderType("STANDARD")
						.saleOrgSapCode(saleOrgSapCode)
						.customerSapCode(customerSapCode)
						.customerRegionCode(regionCode)
						.itemSapCodeOrCrmCodeOrDesc(itemCode)
						.build());
				apiResult.then().body("resultCode", Matchers.equalTo("0"), "resultMsg", Matchers.equalTo("success"));
				List<Map<String, String>> testCaseData = dataTable.asMaps().stream().map(map -> {
					Map<String, String> resultMap = new HashMap<>(map);
					resultMap.put("saleOrgSapCode", saleOrgSapCode);
					resultMap.put("customerSapCode", customerSapCode);
					resultMap.put("customerRegionCode", regionCode);
					return resultMap;
				}).collect(Collectors.toList());
				orderCheckOrderManagement01.checkAddProductFetchPrice(testCaseData, apiResult);
			});
		});
		Then("[创建订单整单折扣计算获取单价05]进行整单折扣计算后,订单行分摊如下", (DataTable dataTable) -> {
			String totalInlineSum = dataTable.asMaps().stream()
					.map(caseData -> new BigDecimal(caseData.get("totalInline")).setScale(2, RoundingMode.HALF_UP))
					.reduce(BigDecimal::add)
					.orElse(BigDecimal.ZERO).toString();
			String preDiscountTotalSum = dataTable.asMaps().stream()
					.map(caseData -> new BigDecimal(caseData.get("preDiscountTotal")).setScale(2, RoundingMode.HALF_UP))
					.reduce(BigDecimal::add)
					.orElse(BigDecimal.ZERO).toString();
			String orderDiscountTotalSum = dataTable.asMaps().stream()
					.map(caseData -> new BigDecimal(caseData.get("orderDiscountTotal")).setScale(2, RoundingMode.HALF_UP))
					.reduce(BigDecimal::add)
					.orElse(BigDecimal.ZERO).toString();
			List<OrderDiscountCalcCalculateReqGroupListDTO> orderDiscountCalcCalculateReqGroupListDTOS = new ArrayList<>();
			dataTable.asMaps().forEach(caseData -> {
				String id = "100000" + Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String orderWideDiscountSubject = Optional.ofNullable(caseData.get("orderWideDiscountSubject")).orElse("");
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String distributionChannelCode = Optional.ofNullable(caseData.get("distributionChannelCode")).orElse("");
				String distributionChannelName = Optional.ofNullable(caseData.get("distributionChannelName")).orElse("");
				String needShip = Optional.ofNullable(caseData.get("needShip")).orElse("");
				String unitPrice = new BigDecimal(caseData.get("unitPrice")).setScale(2, RoundingMode.HALF_UP).toString();
				String quantity = Optional.ofNullable(caseData.get("quantity")).orElse("");
				String boxRatio = new Integer(caseData.get("boxRatio")).toString();
				String preDiscount = new BigDecimal(caseData.get("preDiscount")).setScale(2, RoundingMode.HALF_UP).toString();
				String calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(2, RoundingMode.HALF_UP).toString();
				String totalInline = new BigDecimal(caseData.get("totalInline")).setScale(2, RoundingMode.HALF_UP).toString();
				String preDiscountTotal = new BigDecimal(caseData.get("preDiscountTotal")).setScale(2, RoundingMode.HALF_UP).toString();
				String orderDiscountTotal = new BigDecimal(caseData.get("orderDiscountTotal")).setScale(2, RoundingMode.HALF_UP).toString();
				orderDiscountCalcCalculateReqGroupListDTOS.add(OrderDiscountCalcCalculateReqGroupListDTO.builder()
						.itemGroupCode("01")
						.itemGroupName("成品药-非管制药品")
						.distributionChannelCode(distributionChannelCode)
						.distributionChannelName(distributionChannelName)
						.totalInline(totalInline)
						.preDiscountTotal(preDiscountTotal)
						.orderDiscountTotal(orderDiscountTotal)
						.list(Arrays.asList(
								OrderDiscountCalcCalculateReqGroupListListDTO.builder()
										.orderWideDiscountSubject(orderWideDiscountSubject)
										.itemSapCode(itemSapCode)
										.itemName(ProductEnum.fetchProductName(itemSapCode))
										.itemDesc(ProductEnum.fetchProductSpec(itemSapCode))
										.itemGroupCode("01")
										.itemGroupName("成品药-非管制药品")
										.distributionChannelCode(distributionChannelCode)
										.distributionChannelName(distributionChannelName)
										.unit("盒")
										.unitDesc("盒")
										.unitPrice(unitPrice)
										.calculationValue(calculationValue)
										.preDiscount(preDiscount)
										.preType("CUSTOMER_PRE_DISCOUNT")
										.calculationType("DISCOUNTED_PRICE")
										.quantity(quantity)
										.packingRatio("")
										.totalInline(totalInline)
										.preDiscountTotal(preDiscountTotal)
										.needShip(needShip)
										.baseUnitCode("盒")
										.baseUnitName("盒")
										.boxRatio(boxRatio)
										.xunit("1")
										.yunit("1")
										.build()))
						.build());
			});
			Response discountCalcResponse = orderApiOrderManagement01.discountCalc(OrderDataDTO.builder()
					.orderType("STANDARD")
					.saleOrgSapCode(saleOrgSapCode)
					.saleOrgName(SaleOrgEnum.fetchName(saleOrgSapCode))
					.regionCode(regionCode)
					.regionName(regionName)
					.customerEntSapCode(customerSapCode)
					.customerEntCrmCode(CompanyEnum.fetchCrmCode(customerSapCode))
					.customerEntZtCode(CompanyEnum.fetchZtCode(customerSapCode))
					.customerName(CompanyEnum.fetchName(customerSapCode))
					.invoiceType("VAT_SPECIAL_INVOICE")
					.shippingPolicy("NON_FULL_SHIPMENT")
					.priority("NORMAL")
					.paymentMethod("PRE_PAYMENT")
					.shippingRecipient(shippingRecipient)
					.shippingTelephone(shippingTelephone)
					.shippingAddress(shippingAddress)
					.remark("自动化测试")
					.calculateReq(OrderDiscountCalcCalculateReqDTO.builder()
							.totalInline(totalInlineSum)
							.preDiscountTotal(preDiscountTotalSum)
							.orderDiscountTotal(orderDiscountTotalSum)
							.groupList(orderDiscountCalcCalculateReqGroupListDTOS)
							.build())
					.build());
			discountCalcResponse.then().body("resultCode", Matchers.equalTo("0"), "resultMsg", Matchers.equalTo("success"));
			orderCheckOrderManagement01.checkOrderDiscountCalc(dataTable, discountCalcResponse);
			priceBatchClearData01.clearPirceData01("at");
			orderBatchClearData01.clearOrderPreDiscountData01("at");
			orderBatchClearData01.clearDiscountData01("at");
		});
	}

	private void scenario06() {
		Given("[创建订单整单折扣计算获取单价06]当前存在如下客户信息", (DataTable dataTable) -> {
			createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis());
			domainDataTable = dataTable;
			domainDataTable.asMaps().forEach(caseData -> {
				saleOrgSapCode = Optional.ofNullable(caseData.get("saleOrgSapCode")).orElse("");
				customerSapCode = Optional.ofNullable(caseData.get("customerSapCode")).orElse("");
				regionCode = Optional.ofNullable(caseData.get("regionCode")).orElse("");
				regionName = Optional.ofNullable(caseData.get("regionName")).orElse("");
				shippingAddress = Optional.ofNullable(caseData.get("shippingAddress")).orElse("");
				shippingRecipient = Optional.ofNullable(caseData.get("shippingRecipient")).orElse("");
				shippingTelephone = Optional.ofNullable(caseData.get("shippingTelephone")).orElse("");
			});
		});
		When("[创建订单整单折扣计算获取单价06]当前存在如下商品价格信息", (DataTable dataTable) -> {
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
		And("[创建订单整单折扣计算获取单价06]创建订单添加商品时获取价格应该如下", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String itemCode = Optional.ofNullable(caseData.get("itemCode")).orElse("");
				String price = new BigDecimal(caseData.get("price")).setScale(2, RoundingMode.HALF_UP).toString();
				String distributionChannelCode = Optional.ofNullable(caseData.get("distributionChannelCode")).orElse("");
				Response apiResult = orderApiPrediscountFetchPrice01.orderAddProductFetchProductPrice(OrderAddProductFetchProductPriceDTO.builder()
						.orderType("STANDARD")
						.saleOrgSapCode(saleOrgSapCode)
						.customerSapCode(customerSapCode)
						.customerRegionCode(regionCode)
						.itemSapCodeOrCrmCodeOrDesc(itemCode)
						.build());
				apiResult.then().body("resultCode", Matchers.equalTo("0"), "resultMsg", Matchers.equalTo("success"));
				List<Map<String, String>> testCaseData = dataTable.asMaps().stream().map(map -> {
					Map<String, String> resultMap = new HashMap<>(map);
					resultMap.put("saleOrgSapCode", saleOrgSapCode);
					resultMap.put("customerSapCode", customerSapCode);
					resultMap.put("customerRegionCode", regionCode);
					return resultMap;
				}).collect(Collectors.toList());
				orderCheckOrderManagement01.checkAddProductFetchPrice(testCaseData, apiResult);
			});
		});
		Then("[创建订单整单折扣计算获取单价06]进行整单折扣计算后,订单行分摊如下", (DataTable dataTable) -> {
			String totalInlineSum = dataTable.asMaps().stream()
					.map(caseData -> new BigDecimal(caseData.get("totalInline")).setScale(2, RoundingMode.HALF_UP))
					.reduce(BigDecimal::add)
					.orElse(BigDecimal.ZERO).toString();
			String preDiscountTotalSum = dataTable.asMaps().stream()
					.map(caseData -> new BigDecimal(caseData.get("preDiscountTotal")).setScale(2, RoundingMode.HALF_UP))
					.reduce(BigDecimal::add)
					.orElse(BigDecimal.ZERO).toString();
			String orderDiscountTotalSum = dataTable.asMaps().stream()
					.map(caseData -> new BigDecimal(caseData.get("orderDiscountTotal")).setScale(2, RoundingMode.HALF_UP))
					.reduce(BigDecimal::add)
					.orElse(BigDecimal.ZERO).toString();
			List<OrderDiscountCalcCalculateReqGroupListDTO> orderDiscountCalcCalculateReqGroupListDTOS = new ArrayList<>();
			dataTable.asMaps().forEach(caseData -> {
				String id = "100000" + Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String orderWideDiscountSubject = Optional.ofNullable(caseData.get("orderWideDiscountSubject")).orElse("");
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String distributionChannelCode = Optional.ofNullable(caseData.get("distributionChannelCode")).orElse("");
				String distributionChannelName = Optional.ofNullable(caseData.get("distributionChannelName")).orElse("");
				String needShip = Optional.ofNullable(caseData.get("needShip")).orElse("");
				String unitPrice = new BigDecimal(caseData.get("unitPrice")).setScale(2, RoundingMode.HALF_UP).toString();
				String quantity = Optional.ofNullable(caseData.get("quantity")).orElse("");
				String boxRatio = new Integer(caseData.get("boxRatio")).toString();
				String preDiscount = new BigDecimal(caseData.get("preDiscount")).setScale(2, RoundingMode.HALF_UP).toString();
				String calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(2, RoundingMode.HALF_UP).toString();
				String totalInline = new BigDecimal(caseData.get("totalInline")).setScale(2, RoundingMode.HALF_UP).toString();
				String preDiscountTotal = new BigDecimal(caseData.get("preDiscountTotal")).setScale(2, RoundingMode.HALF_UP).toString();
				String orderDiscountTotal = new BigDecimal(caseData.get("orderDiscountTotal")).setScale(2, RoundingMode.HALF_UP).toString();
				orderDiscountCalcCalculateReqGroupListDTOS.add(OrderDiscountCalcCalculateReqGroupListDTO.builder()
						.itemGroupCode("01")
						.itemGroupName("成品药-非管制药品")
						.distributionChannelCode(distributionChannelCode)
						.distributionChannelName(distributionChannelName)
						.totalInline(totalInline)
						.preDiscountTotal(preDiscountTotal)
						.orderDiscountTotal(orderDiscountTotal)
						.list(Arrays.asList(
								OrderDiscountCalcCalculateReqGroupListListDTO.builder()
										.orderWideDiscountSubject(orderWideDiscountSubject)
										.itemSapCode(itemSapCode)
										.itemName(ProductEnum.fetchProductName(itemSapCode))
										.itemDesc(ProductEnum.fetchProductSpec(itemSapCode))
										.itemGroupCode("01")
										.itemGroupName("成品药-非管制药品")
										.distributionChannelCode(distributionChannelCode)
										.distributionChannelName(distributionChannelName)
										.unit("盒")
										.unitDesc("盒")
										.unitPrice(unitPrice)
										.calculationValue(calculationValue)
										.preDiscount(preDiscount)
										.preType("CUSTOMER_PRE_DISCOUNT")
										.calculationType("DISCOUNTED_PRICE")
										.quantity(quantity)
										.packingRatio("")
										.totalInline(totalInline)
										.preDiscountTotal(preDiscountTotal)
										.needShip(needShip)
										.baseUnitCode("盒")
										.baseUnitName("盒")
										.boxRatio(boxRatio)
										.xunit("1")
										.yunit("1")
										.build()))
						.build());
			});
			Response discountCalcResponse = orderApiOrderManagement01.discountCalc(OrderDataDTO.builder()
					.orderType("STANDARD")
					.saleOrgSapCode(saleOrgSapCode)
					.saleOrgName(SaleOrgEnum.fetchName(saleOrgSapCode))
					.regionCode(regionCode)
					.regionName(regionName)
					.customerEntSapCode(customerSapCode)
					.customerEntCrmCode(CompanyEnum.fetchCrmCode(customerSapCode))
					.customerEntZtCode(CompanyEnum.fetchZtCode(customerSapCode))
					.customerName(CompanyEnum.fetchName(customerSapCode))
					.invoiceType("VAT_SPECIAL_INVOICE")
					.shippingPolicy("NON_FULL_SHIPMENT")
					.priority("NORMAL")
					.paymentMethod("PRE_PAYMENT")
					.shippingRecipient(shippingRecipient)
					.shippingTelephone(shippingTelephone)
					.shippingAddress(shippingAddress)
					.remark("自动化测试")
					.calculateReq(OrderDiscountCalcCalculateReqDTO.builder()
							.totalInline(totalInlineSum)
							.preDiscountTotal(preDiscountTotalSum)
							.orderDiscountTotal(orderDiscountTotalSum)
							.groupList(orderDiscountCalcCalculateReqGroupListDTOS)
							.build())
					.build());
			discountCalcResponse.then().body("resultCode", Matchers.equalTo("0"), "resultMsg", Matchers.equalTo("success"));
			orderCheckOrderManagement01.checkOrderDiscountCalc(dataTable, discountCalcResponse);
			priceBatchClearData01.clearPirceData01("at");
			orderBatchClearData01.clearOrderPreDiscountData01("at");
			orderBatchClearData01.clearDiscountData01("at");
		});
	}

	private void scenario07() {
		Given("[创建订单整单折扣计算获取单价07]当前存在如下客户信息", (DataTable dataTable) -> {
			createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis());
			domainDataTable = dataTable;
			domainDataTable.asMaps().forEach(caseData -> {
				saleOrgSapCode = Optional.ofNullable(caseData.get("saleOrgSapCode")).orElse("");
				customerSapCode = Optional.ofNullable(caseData.get("customerSapCode")).orElse("");
				regionCode = Optional.ofNullable(caseData.get("regionCode")).orElse("");
				regionName = Optional.ofNullable(caseData.get("regionName")).orElse("");
				shippingAddress = Optional.ofNullable(caseData.get("shippingAddress")).orElse("");
				shippingRecipient = Optional.ofNullable(caseData.get("shippingRecipient")).orElse("");
				shippingTelephone = Optional.ofNullable(caseData.get("shippingTelephone")).orElse("");
			});
		});
		When("[创建订单整单折扣计算获取单价07]当前存在如下商品价格信息", (DataTable dataTable) -> {
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
		And("[创建订单整单折扣计算获取单价07]创建订单添加商品时获取价格应该如下", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String itemCode = Optional.ofNullable(caseData.get("itemCode")).orElse("");
				String price = new BigDecimal(caseData.get("price")).setScale(2, RoundingMode.HALF_UP).toString();
				String distributionChannelCode = Optional.ofNullable(caseData.get("distributionChannelCode")).orElse("");
				Response apiResult = orderApiPrediscountFetchPrice01.orderAddProductFetchProductPrice(OrderAddProductFetchProductPriceDTO.builder()
						.orderType("STANDARD")
						.saleOrgSapCode(saleOrgSapCode)
						.customerSapCode(customerSapCode)
						.customerRegionCode(regionCode)
						.itemSapCodeOrCrmCodeOrDesc(itemCode)
						.build());
				apiResult.then().body("resultCode", Matchers.equalTo("0"), "resultMsg", Matchers.equalTo("success"));
				List<Map<String, String>> testCaseData = dataTable.asMaps().stream().map(map -> {
					Map<String, String> resultMap = new HashMap<>(map);
					resultMap.put("saleOrgSapCode", saleOrgSapCode);
					resultMap.put("customerSapCode", customerSapCode);
					resultMap.put("customerRegionCode", regionCode);
					return resultMap;
				}).collect(Collectors.toList());
				orderCheckOrderManagement01.checkAddProductFetchPrice(testCaseData, apiResult);
			});
		});
		Then("[创建订单整单折扣计算获取单价07]进行整单折扣计算后,订单行分摊如下", (DataTable dataTable) -> {
			String totalInlineSum = dataTable.asMaps().stream()
					.map(caseData -> new BigDecimal(caseData.get("totalInline")).setScale(2, RoundingMode.HALF_UP))
					.reduce(BigDecimal::add)
					.orElse(BigDecimal.ZERO).toString();
			String preDiscountTotalSum = dataTable.asMaps().stream()
					.map(caseData -> new BigDecimal(caseData.get("preDiscountTotal")).setScale(2, RoundingMode.HALF_UP))
					.reduce(BigDecimal::add)
					.orElse(BigDecimal.ZERO).toString();
			String orderDiscountTotalSum = dataTable.asMaps().stream()
					.map(caseData -> new BigDecimal(caseData.get("orderDiscountTotal")).setScale(2, RoundingMode.HALF_UP))
					.reduce(BigDecimal::add)
					.orElse(BigDecimal.ZERO).toString();
			List<OrderDiscountCalcCalculateReqGroupListDTO> orderDiscountCalcCalculateReqGroupListDTOS = new ArrayList<>();
			dataTable.asMaps().forEach(caseData -> {
				String id = "100000" + Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String orderWideDiscountSubject = Optional.ofNullable(caseData.get("orderWideDiscountSubject")).orElse("");
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String distributionChannelCode = Optional.ofNullable(caseData.get("distributionChannelCode")).orElse("");
				String distributionChannelName = Optional.ofNullable(caseData.get("distributionChannelName")).orElse("");
				String needShip = Optional.ofNullable(caseData.get("needShip")).orElse("");
				String unitPrice = new BigDecimal(caseData.get("unitPrice")).setScale(2, RoundingMode.HALF_UP).toString();
				String quantity = Optional.ofNullable(caseData.get("quantity")).orElse("");
				String boxRatio = new Integer(caseData.get("boxRatio")).toString();
				String preDiscount = new BigDecimal(caseData.get("preDiscount")).setScale(2, RoundingMode.HALF_UP).toString();
				String calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(2, RoundingMode.HALF_UP).toString();
				String totalInline = new BigDecimal(caseData.get("totalInline")).setScale(2, RoundingMode.HALF_UP).toString();
				String preDiscountTotal = new BigDecimal(caseData.get("preDiscountTotal")).setScale(2, RoundingMode.HALF_UP).toString();
				String orderDiscountTotal = new BigDecimal(caseData.get("orderDiscountTotal")).setScale(2, RoundingMode.HALF_UP).toString();
				orderDiscountCalcCalculateReqGroupListDTOS.add(OrderDiscountCalcCalculateReqGroupListDTO.builder()
						.itemGroupCode("01")
						.itemGroupName("成品药-非管制药品")
						.distributionChannelCode(distributionChannelCode)
						.distributionChannelName(distributionChannelName)
						.totalInline(totalInline)
						.preDiscountTotal(preDiscountTotal)
						.orderDiscountTotal(orderDiscountTotal)
						.list(Arrays.asList(
								OrderDiscountCalcCalculateReqGroupListListDTO.builder()
										.orderWideDiscountSubject(orderWideDiscountSubject)
										.itemSapCode(itemSapCode)
										.itemName(ProductEnum.fetchProductName(itemSapCode))
										.itemDesc(ProductEnum.fetchProductSpec(itemSapCode))
										.itemGroupCode("01")
										.itemGroupName("成品药-非管制药品")
										.distributionChannelCode(distributionChannelCode)
										.distributionChannelName(distributionChannelName)
										.unit("盒")
										.unitDesc("盒")
										.unitPrice(unitPrice)
										.calculationValue(calculationValue)
										.preDiscount(preDiscount)
										.preType("CUSTOMER_PRE_DISCOUNT")
										.calculationType("DISCOUNTED_PRICE")
										.quantity(quantity)
										.packingRatio("")
										.totalInline(totalInline)
										.preDiscountTotal(preDiscountTotal)
										.needShip(needShip)
										.baseUnitCode("盒")
										.baseUnitName("盒")
										.boxRatio(boxRatio)
										.xunit("1")
										.yunit("1")
										.build()))
						.build());
			});
			Response discountCalcResponse = orderApiOrderManagement01.discountCalc(OrderDataDTO.builder()
					.orderType("STANDARD")
					.saleOrgSapCode(saleOrgSapCode)
					.saleOrgName(SaleOrgEnum.fetchName(saleOrgSapCode))
					.regionCode(regionCode)
					.regionName(regionName)
					.customerEntSapCode(customerSapCode)
					.customerEntCrmCode(CompanyEnum.fetchCrmCode(customerSapCode))
					.customerEntZtCode(CompanyEnum.fetchZtCode(customerSapCode))
					.customerName(CompanyEnum.fetchName(customerSapCode))
					.invoiceType("VAT_SPECIAL_INVOICE")
					.shippingPolicy("NON_FULL_SHIPMENT")
					.priority("NORMAL")
					.paymentMethod("PRE_PAYMENT")
					.shippingRecipient(shippingRecipient)
					.shippingTelephone(shippingTelephone)
					.shippingAddress(shippingAddress)
					.remark("自动化测试")
					.calculateReq(OrderDiscountCalcCalculateReqDTO.builder()
							.totalInline(totalInlineSum)
							.preDiscountTotal(preDiscountTotalSum)
							.orderDiscountTotal(orderDiscountTotalSum)
							.groupList(orderDiscountCalcCalculateReqGroupListDTOS)
							.build())
					.build());
			discountCalcResponse.then().body("resultCode", Matchers.equalTo("0"), "resultMsg", Matchers.equalTo("success"));
			orderCheckOrderManagement01.checkOrderDiscountCalc(dataTable, discountCalcResponse);
			priceBatchClearData01.clearPirceData01("at");
			orderBatchClearData01.clearOrderPreDiscountData01("at");
			orderBatchClearData01.clearDiscountData01("at");
		});
	}

	private void scenario08() {
		Given("[创建订单整单折扣计算获取单价08]当前存在如下客户信息", (DataTable dataTable) -> {
			createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis());
			domainDataTable = dataTable;
			domainDataTable.asMaps().forEach(caseData -> {
				saleOrgSapCode = Optional.ofNullable(caseData.get("saleOrgSapCode")).orElse("");
				customerSapCode = Optional.ofNullable(caseData.get("customerSapCode")).orElse("");
				regionCode = Optional.ofNullable(caseData.get("regionCode")).orElse("");
				regionName = Optional.ofNullable(caseData.get("regionName")).orElse("");
				shippingAddress = Optional.ofNullable(caseData.get("shippingAddress")).orElse("");
				shippingRecipient = Optional.ofNullable(caseData.get("shippingRecipient")).orElse("");
				shippingTelephone = Optional.ofNullable(caseData.get("shippingTelephone")).orElse("");
			});
		});
		When("[创建订单整单折扣计算获取单价08]当前存在如下商品价格信息", (DataTable dataTable) -> {
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
		And("[创建订单整单折扣计算获取单价08]创建订单添加商品时获取价格应该如下", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String itemCode = Optional.ofNullable(caseData.get("itemCode")).orElse("");
				String price = new BigDecimal(caseData.get("price")).setScale(2, RoundingMode.HALF_UP).toString();
				String distributionChannelCode = Optional.ofNullable(caseData.get("distributionChannelCode")).orElse("");
				Response apiResult = orderApiPrediscountFetchPrice01.orderAddProductFetchProductPrice(OrderAddProductFetchProductPriceDTO.builder()
						.orderType("STANDARD")
						.saleOrgSapCode(saleOrgSapCode)
						.customerSapCode(customerSapCode)
						.customerRegionCode(regionCode)
						.itemSapCodeOrCrmCodeOrDesc(itemCode)
						.build());
				apiResult.then().body("resultCode", Matchers.equalTo("0"), "resultMsg", Matchers.equalTo("success"));
				List<Map<String, String>> testCaseData = dataTable.asMaps().stream().map(map -> {
					Map<String, String> resultMap = new HashMap<>(map);
					resultMap.put("saleOrgSapCode", saleOrgSapCode);
					resultMap.put("customerSapCode", customerSapCode);
					resultMap.put("customerRegionCode", regionCode);
					return resultMap;
				}).collect(Collectors.toList());
				orderCheckOrderManagement01.checkAddProductFetchPrice(testCaseData, apiResult);
			});
		});
		Then("[创建订单整单折扣计算获取单价08]进行整单折扣计算后,订单行分摊如下", (DataTable dataTable) -> {
			String totalInlineSum = dataTable.asMaps().stream()
					.map(caseData -> new BigDecimal(caseData.get("totalInline")).setScale(2, RoundingMode.HALF_UP))
					.reduce(BigDecimal::add)
					.orElse(BigDecimal.ZERO).toString();
			String preDiscountTotalSum = dataTable.asMaps().stream()
					.map(caseData -> new BigDecimal(caseData.get("preDiscountTotal")).setScale(2, RoundingMode.HALF_UP))
					.reduce(BigDecimal::add)
					.orElse(BigDecimal.ZERO).toString();
			String orderDiscountTotalSum = dataTable.asMaps().stream()
					.map(caseData -> new BigDecimal(caseData.get("orderDiscountTotal")).setScale(2, RoundingMode.HALF_UP))
					.reduce(BigDecimal::add)
					.orElse(BigDecimal.ZERO).toString();
			List<OrderDiscountCalcCalculateReqGroupListDTO> orderDiscountCalcCalculateReqGroupListDTOS = new ArrayList<>();
			dataTable.asMaps().forEach(caseData -> {
				String id = "100000" + Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String orderWideDiscountSubject = Optional.ofNullable(caseData.get("orderWideDiscountSubject")).orElse("");
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String distributionChannelCode = Optional.ofNullable(caseData.get("distributionChannelCode")).orElse("");
				String distributionChannelName = Optional.ofNullable(caseData.get("distributionChannelName")).orElse("");
				String needShip = Optional.ofNullable(caseData.get("needShip")).orElse("");
				String unitPrice = new BigDecimal(caseData.get("unitPrice")).setScale(2, RoundingMode.HALF_UP).toString();
				String quantity = Optional.ofNullable(caseData.get("quantity")).orElse("");
				String boxRatio = new Integer(caseData.get("boxRatio")).toString();
				String preDiscount = new BigDecimal(caseData.get("preDiscount")).setScale(2, RoundingMode.HALF_UP).toString();
				String calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(2, RoundingMode.HALF_UP).toString();
				String totalInline = new BigDecimal(caseData.get("totalInline")).setScale(2, RoundingMode.HALF_UP).toString();
				String preDiscountTotal = new BigDecimal(caseData.get("preDiscountTotal")).setScale(2, RoundingMode.HALF_UP).toString();
				String orderDiscountTotal = new BigDecimal(caseData.get("orderDiscountTotal")).setScale(2, RoundingMode.HALF_UP).toString();
				orderDiscountCalcCalculateReqGroupListDTOS.add(OrderDiscountCalcCalculateReqGroupListDTO.builder()
						.itemGroupCode("01")
						.itemGroupName("成品药-非管制药品")
						.distributionChannelCode(distributionChannelCode)
						.distributionChannelName(distributionChannelName)
						.totalInline(totalInline)
						.preDiscountTotal(preDiscountTotal)
						.orderDiscountTotal(orderDiscountTotal)
						.list(Arrays.asList(
								OrderDiscountCalcCalculateReqGroupListListDTO.builder()
										.orderWideDiscountSubject(orderWideDiscountSubject)
										.itemSapCode(itemSapCode)
										.itemName(ProductEnum.fetchProductName(itemSapCode))
										.itemDesc(ProductEnum.fetchProductSpec(itemSapCode))
										.itemGroupCode("01")
										.itemGroupName("成品药-非管制药品")
										.distributionChannelCode(distributionChannelCode)
										.distributionChannelName(distributionChannelName)
										.unit("盒")
										.unitDesc("盒")
										.unitPrice(unitPrice)
										.calculationValue(calculationValue)
										.preDiscount(preDiscount)
										.preType("CUSTOMER_PRE_DISCOUNT")
										.calculationType("DISCOUNTED_PRICE")
										.quantity(quantity)
										.packingRatio("")
										.totalInline(totalInline)
										.preDiscountTotal(preDiscountTotal)
										.needShip(needShip)
										.baseUnitCode("盒")
										.baseUnitName("盒")
										.boxRatio(boxRatio)
										.xunit("1")
										.yunit("1")
										.build()))
						.build());
			});
			Response discountCalcResponse = orderApiOrderManagement01.discountCalc(OrderDataDTO.builder()
					.orderType("STANDARD")
					.saleOrgSapCode(saleOrgSapCode)
					.saleOrgName(SaleOrgEnum.fetchName(saleOrgSapCode))
					.regionCode(regionCode)
					.regionName(regionName)
					.customerEntSapCode(customerSapCode)
					.customerEntCrmCode(CompanyEnum.fetchCrmCode(customerSapCode))
					.customerEntZtCode(CompanyEnum.fetchZtCode(customerSapCode))
					.customerName(CompanyEnum.fetchName(customerSapCode))
					.invoiceType("VAT_SPECIAL_INVOICE")
					.shippingPolicy("NON_FULL_SHIPMENT")
					.priority("NORMAL")
					.paymentMethod("PRE_PAYMENT")
					.shippingRecipient(shippingRecipient)
					.shippingTelephone(shippingTelephone)
					.shippingAddress(shippingAddress)
					.remark("自动化测试")
					.calculateReq(OrderDiscountCalcCalculateReqDTO.builder()
							.totalInline(totalInlineSum)
							.preDiscountTotal(preDiscountTotalSum)
							.orderDiscountTotal(orderDiscountTotalSum)
							.groupList(orderDiscountCalcCalculateReqGroupListDTOS)
							.build())
					.build());
			discountCalcResponse.then().body("resultCode", Matchers.equalTo("0"), "resultMsg", Matchers.equalTo("success"));
			orderCheckOrderManagement01.checkOrderDiscountCalc(dataTable, discountCalcResponse);
			priceBatchClearData01.clearPirceData01("at");
			orderBatchClearData01.clearOrderPreDiscountData01("at");
			orderBatchClearData01.clearDiscountData01("at");
		});
	}

	private void scenario09() {
		Given("[创建订单整单折扣计算获取单价09]当前存在如下客户信息", (DataTable dataTable) -> {
			createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis());
			domainDataTable = dataTable;
			domainDataTable.asMaps().forEach(caseData -> {
				saleOrgSapCode = Optional.ofNullable(caseData.get("saleOrgSapCode")).orElse("");
				customerSapCode = Optional.ofNullable(caseData.get("customerSapCode")).orElse("");
				regionCode = Optional.ofNullable(caseData.get("regionCode")).orElse("");
				regionName = Optional.ofNullable(caseData.get("regionName")).orElse("");
				shippingAddress = Optional.ofNullable(caseData.get("shippingAddress")).orElse("");
				shippingRecipient = Optional.ofNullable(caseData.get("shippingRecipient")).orElse("");
				shippingTelephone = Optional.ofNullable(caseData.get("shippingTelephone")).orElse("");
			});
		});
		When("[创建订单整单折扣计算获取单价09]当前存在如下商品价格信息", (DataTable dataTable) -> {
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
		And("[创建订单整单折扣计算获取单价09]创建订单添加商品时获取价格应该如下", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String itemCode = Optional.ofNullable(caseData.get("itemCode")).orElse("");
				String price = new BigDecimal(caseData.get("price")).setScale(2, RoundingMode.HALF_UP).toString();
				String distributionChannelCode = Optional.ofNullable(caseData.get("distributionChannelCode")).orElse("");
				Response apiResult = orderApiPrediscountFetchPrice01.orderAddProductFetchProductPrice(OrderAddProductFetchProductPriceDTO.builder()
						.orderType("STANDARD")
						.saleOrgSapCode(saleOrgSapCode)
						.customerSapCode(customerSapCode)
						.customerRegionCode(regionCode)
						.itemSapCodeOrCrmCodeOrDesc(itemCode)
						.build());
				apiResult.then().body("resultCode", Matchers.equalTo("0"), "resultMsg", Matchers.equalTo("success"));
				List<Map<String, String>> testCaseData = dataTable.asMaps().stream().map(map -> {
					Map<String, String> resultMap = new HashMap<>(map);
					resultMap.put("saleOrgSapCode", saleOrgSapCode);
					resultMap.put("customerSapCode", customerSapCode);
					resultMap.put("customerRegionCode", regionCode);
					return resultMap;
				}).collect(Collectors.toList());
				orderCheckOrderManagement01.checkAddProductFetchPrice(testCaseData, apiResult);
			});
		});
		Then("[创建订单整单折扣计算获取单价09]进行整单折扣计算后,订单行分摊如下", (DataTable dataTable) -> {
			String totalInlineSum = dataTable.asMaps().stream()
					.map(caseData -> new BigDecimal(caseData.get("totalInline")).setScale(2, RoundingMode.HALF_UP))
					.reduce(BigDecimal::add)
					.orElse(BigDecimal.ZERO).toString();
			String preDiscountTotalSum = dataTable.asMaps().stream()
					.map(caseData -> new BigDecimal(caseData.get("preDiscountTotal")).setScale(2, RoundingMode.HALF_UP))
					.reduce(BigDecimal::add)
					.orElse(BigDecimal.ZERO).toString();
			String orderDiscountTotalSum = dataTable.asMaps().stream()
					.map(caseData -> new BigDecimal(caseData.get("orderDiscountTotal")).setScale(2, RoundingMode.HALF_UP))
					.reduce(BigDecimal::add)
					.orElse(BigDecimal.ZERO).toString();
			List<OrderDiscountCalcCalculateReqGroupListDTO> orderDiscountCalcCalculateReqGroupListDTOS = new ArrayList<>();
			dataTable.asMaps().forEach(caseData -> {
				String id = "100000" + Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String orderWideDiscountSubject = Optional.ofNullable(caseData.get("orderWideDiscountSubject")).orElse("");
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String distributionChannelCode = Optional.ofNullable(caseData.get("distributionChannelCode")).orElse("");
				String distributionChannelName = Optional.ofNullable(caseData.get("distributionChannelName")).orElse("");
				String needShip = Optional.ofNullable(caseData.get("needShip")).orElse("");
				String unitPrice = new BigDecimal(caseData.get("unitPrice")).setScale(2, RoundingMode.HALF_UP).toString();
				String quantity = Optional.ofNullable(caseData.get("quantity")).orElse("");
				String boxRatio = new Integer(caseData.get("boxRatio")).toString();
				String preDiscount = new BigDecimal(caseData.get("preDiscount")).setScale(2, RoundingMode.HALF_UP).toString();
				String calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(2, RoundingMode.HALF_UP).toString();
				String totalInline = new BigDecimal(caseData.get("totalInline")).setScale(2, RoundingMode.HALF_UP).toString();
				String preDiscountTotal = new BigDecimal(caseData.get("preDiscountTotal")).setScale(2, RoundingMode.HALF_UP).toString();
				String orderDiscountTotal = new BigDecimal(caseData.get("orderDiscountTotal")).setScale(2, RoundingMode.HALF_UP).toString();
				orderDiscountCalcCalculateReqGroupListDTOS.add(OrderDiscountCalcCalculateReqGroupListDTO.builder()
						.itemGroupCode("01")
						.itemGroupName("成品药-非管制药品")
						.distributionChannelCode(distributionChannelCode)
						.distributionChannelName(distributionChannelName)
						.totalInline(totalInline)
						.preDiscountTotal(preDiscountTotal)
						.orderDiscountTotal(orderDiscountTotal)
						.list(Arrays.asList(
								OrderDiscountCalcCalculateReqGroupListListDTO.builder()
										.orderWideDiscountSubject(orderWideDiscountSubject)
										.itemSapCode(itemSapCode)
										.itemName(ProductEnum.fetchProductName(itemSapCode))
										.itemDesc(ProductEnum.fetchProductSpec(itemSapCode))
										.itemGroupCode("01")
										.itemGroupName("成品药-非管制药品")
										.distributionChannelCode(distributionChannelCode)
										.distributionChannelName(distributionChannelName)
										.unit("盒")
										.unitDesc("盒")
										.unitPrice(unitPrice)
										.calculationValue(calculationValue)
										.preDiscount(preDiscount)
										.preType("CUSTOMER_PRE_DISCOUNT")
										.calculationType("DISCOUNTED_PRICE")
										.quantity(quantity)
										.packingRatio("")
										.totalInline(totalInline)
										.preDiscountTotal(preDiscountTotal)
										.needShip(needShip)
										.baseUnitCode("盒")
										.baseUnitName("盒")
										.boxRatio(boxRatio)
										.xunit("1")
										.yunit("1")
										.build()))
						.build());
			});
			Response discountCalcResponse = orderApiOrderManagement01.discountCalc(OrderDataDTO.builder()
					.orderType("STANDARD")
					.saleOrgSapCode(saleOrgSapCode)
					.saleOrgName(SaleOrgEnum.fetchName(saleOrgSapCode))
					.regionCode(regionCode)
					.regionName(regionName)
					.customerEntSapCode(customerSapCode)
					.customerEntCrmCode(CompanyEnum.fetchCrmCode(customerSapCode))
					.customerEntZtCode(CompanyEnum.fetchZtCode(customerSapCode))
					.customerName(CompanyEnum.fetchName(customerSapCode))
					.invoiceType("VAT_SPECIAL_INVOICE")
					.shippingPolicy("NON_FULL_SHIPMENT")
					.priority("NORMAL")
					.paymentMethod("PRE_PAYMENT")
					.shippingRecipient(shippingRecipient)
					.shippingTelephone(shippingTelephone)
					.shippingAddress(shippingAddress)
					.remark("自动化测试")
					.calculateReq(OrderDiscountCalcCalculateReqDTO.builder()
							.totalInline(totalInlineSum)
							.preDiscountTotal(preDiscountTotalSum)
							.orderDiscountTotal(orderDiscountTotalSum)
							.groupList(orderDiscountCalcCalculateReqGroupListDTOS)
							.build())
					.build());
			discountCalcResponse.then().body("resultCode", Matchers.equalTo("0"), "resultMsg", Matchers.equalTo("success"));
			orderCheckOrderManagement01.checkOrderDiscountCalc(dataTable, discountCalcResponse);
			priceBatchClearData01.clearPirceData01("at");
			orderBatchClearData01.clearOrderPreDiscountData01("at");
			orderBatchClearData01.clearDiscountData01("at");
		});
	}

	private void scenario10() {
		Given("[创建订单整单折扣计算获取单价10]当前存在如下客户信息", (DataTable dataTable) -> {
			createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis());
			domainDataTable = dataTable;
			domainDataTable.asMaps().forEach(caseData -> {
				saleOrgSapCode = Optional.ofNullable(caseData.get("saleOrgSapCode")).orElse("");
				customerSapCode = Optional.ofNullable(caseData.get("customerSapCode")).orElse("");
				regionCode = Optional.ofNullable(caseData.get("regionCode")).orElse("");
				regionName = Optional.ofNullable(caseData.get("regionName")).orElse("");
				shippingAddress = Optional.ofNullable(caseData.get("shippingAddress")).orElse("");
				shippingRecipient = Optional.ofNullable(caseData.get("shippingRecipient")).orElse("");
				shippingTelephone = Optional.ofNullable(caseData.get("shippingTelephone")).orElse("");
			});
		});
		When("[创建订单整单折扣计算获取单价10]当前存在如下商品价格信息", (DataTable dataTable) -> {
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
		And("[创建订单整单折扣计算获取单价10]创建订单添加商品时获取价格应该如下", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String itemCode = Optional.ofNullable(caseData.get("itemCode")).orElse("");
				String price = new BigDecimal(caseData.get("price")).setScale(2, RoundingMode.HALF_UP).toString();
				String distributionChannelCode = Optional.ofNullable(caseData.get("distributionChannelCode")).orElse("");
				Response apiResult = orderApiPrediscountFetchPrice01.orderAddProductFetchProductPrice(OrderAddProductFetchProductPriceDTO.builder()
						.orderType("STANDARD")
						.saleOrgSapCode(saleOrgSapCode)
						.customerSapCode(customerSapCode)
						.customerRegionCode(regionCode)
						.itemSapCodeOrCrmCodeOrDesc(itemCode)
						.build());
				apiResult.then().body("resultCode", Matchers.equalTo("0"), "resultMsg", Matchers.equalTo("success"));
				List<Map<String, String>> testCaseData = dataTable.asMaps().stream().map(map -> {
					Map<String, String> resultMap = new HashMap<>(map);
					resultMap.put("saleOrgSapCode", saleOrgSapCode);
					resultMap.put("customerSapCode", customerSapCode);
					resultMap.put("customerRegionCode", regionCode);
					return resultMap;
				}).collect(Collectors.toList());
				orderCheckOrderManagement01.checkAddProductFetchPrice(testCaseData, apiResult);
			});
		});
		Then("[创建订单整单折扣计算获取单价10]进行整单折扣计算后,订单行分摊如下", (DataTable dataTable) -> {
			String totalInlineSum = dataTable.asMaps().stream()
					.map(caseData -> new BigDecimal(caseData.get("totalInline")).setScale(2, RoundingMode.HALF_UP))
					.reduce(BigDecimal::add)
					.orElse(BigDecimal.ZERO).toString();
			String preDiscountTotalSum = dataTable.asMaps().stream()
					.map(caseData -> new BigDecimal(caseData.get("preDiscountTotal")).setScale(2, RoundingMode.HALF_UP))
					.reduce(BigDecimal::add)
					.orElse(BigDecimal.ZERO).toString();
			String orderDiscountTotalSum = dataTable.asMaps().stream()
					.map(caseData -> new BigDecimal(caseData.get("orderDiscountTotal")).setScale(2, RoundingMode.HALF_UP))
					.reduce(BigDecimal::add)
					.orElse(BigDecimal.ZERO).toString();
			List<OrderDiscountCalcCalculateReqGroupListDTO> orderDiscountCalcCalculateReqGroupListDTOS = new ArrayList<>();
			dataTable.asMaps().forEach(caseData -> {
				String id = "100000" + Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String orderWideDiscountSubject = Optional.ofNullable(caseData.get("orderWideDiscountSubject")).orElse("");
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String distributionChannelCode = Optional.ofNullable(caseData.get("distributionChannelCode")).orElse("");
				String distributionChannelName = Optional.ofNullable(caseData.get("distributionChannelName")).orElse("");
				String needShip = Optional.ofNullable(caseData.get("needShip")).orElse("");
				String unitPrice = new BigDecimal(caseData.get("unitPrice")).setScale(2, RoundingMode.HALF_UP).toString();
				String quantity = Optional.ofNullable(caseData.get("quantity")).orElse("");
				String boxRatio = new Integer(caseData.get("boxRatio")).toString();
				String preDiscount = new BigDecimal(caseData.get("preDiscount")).setScale(2, RoundingMode.HALF_UP).toString();
				String calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(2, RoundingMode.HALF_UP).toString();
				String totalInline = new BigDecimal(caseData.get("totalInline")).setScale(2, RoundingMode.HALF_UP).toString();
				String preDiscountTotal = new BigDecimal(caseData.get("preDiscountTotal")).setScale(2, RoundingMode.HALF_UP).toString();
				String orderDiscountTotal = new BigDecimal(caseData.get("orderDiscountTotal")).setScale(2, RoundingMode.HALF_UP).toString();
				orderDiscountCalcCalculateReqGroupListDTOS.add(OrderDiscountCalcCalculateReqGroupListDTO.builder()
						.itemGroupCode("01")
						.itemGroupName("成品药-非管制药品")
						.distributionChannelCode(distributionChannelCode)
						.distributionChannelName(distributionChannelName)
						.totalInline(totalInline)
						.preDiscountTotal(preDiscountTotal)
						.orderDiscountTotal(orderDiscountTotal)
						.list(Arrays.asList(
								OrderDiscountCalcCalculateReqGroupListListDTO.builder()
										.orderWideDiscountSubject(orderWideDiscountSubject)
										.itemSapCode(itemSapCode)
										.itemName(ProductEnum.fetchProductName(itemSapCode))
										.itemDesc(ProductEnum.fetchProductSpec(itemSapCode))
										.itemGroupCode("01")
										.itemGroupName("成品药-非管制药品")
										.distributionChannelCode(distributionChannelCode)
										.distributionChannelName(distributionChannelName)
										.unit("盒")
										.unitDesc("盒")
										.unitPrice(unitPrice)
										.calculationValue(calculationValue)
										.preDiscount(preDiscount)
										.preType("CUSTOMER_PRE_DISCOUNT")
										.calculationType("DISCOUNTED_PRICE")
										.quantity(quantity)
										.packingRatio("")
										.totalInline(totalInline)
										.preDiscountTotal(preDiscountTotal)
										.needShip(needShip)
										.baseUnitCode("盒")
										.baseUnitName("盒")
										.boxRatio(boxRatio)
										.xunit("1")
										.yunit("1")
										.build()))
						.build());
			});
			Response discountCalcResponse = orderApiOrderManagement01.discountCalc(OrderDataDTO.builder()
					.orderType("STANDARD")
					.saleOrgSapCode(saleOrgSapCode)
					.saleOrgName(SaleOrgEnum.fetchName(saleOrgSapCode))
					.regionCode(regionCode)
					.regionName(regionName)
					.customerEntSapCode(customerSapCode)
					.customerEntCrmCode(CompanyEnum.fetchCrmCode(customerSapCode))
					.customerEntZtCode(CompanyEnum.fetchZtCode(customerSapCode))
					.customerName(CompanyEnum.fetchName(customerSapCode))
					.invoiceType("VAT_SPECIAL_INVOICE")
					.shippingPolicy("NON_FULL_SHIPMENT")
					.priority("NORMAL")
					.paymentMethod("PRE_PAYMENT")
					.shippingRecipient(shippingRecipient)
					.shippingTelephone(shippingTelephone)
					.shippingAddress(shippingAddress)
					.remark("自动化测试")
					.calculateReq(OrderDiscountCalcCalculateReqDTO.builder()
							.totalInline(totalInlineSum)
							.preDiscountTotal(preDiscountTotalSum)
							.orderDiscountTotal(orderDiscountTotalSum)
							.groupList(orderDiscountCalcCalculateReqGroupListDTOS)
							.build())
					.build());
			discountCalcResponse.then().body("resultCode", Matchers.equalTo("0"), "resultMsg", Matchers.equalTo("success"));
			orderCheckOrderManagement01.checkOrderDiscountCalc(dataTable, discountCalcResponse);
			priceBatchClearData01.clearPirceData01("at");
			orderBatchClearData01.clearOrderPreDiscountData01("at");
			orderBatchClearData01.clearDiscountData01("at");
		});
	}

	private void scenario11() {
		Given("[创建订单整单折扣计算获取单价11]当前存在如下客户信息", (DataTable dataTable) -> {
			createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis());
			domainDataTable = dataTable;
			domainDataTable.asMaps().forEach(caseData -> {
				saleOrgSapCode = Optional.ofNullable(caseData.get("saleOrgSapCode")).orElse("");
				customerSapCode = Optional.ofNullable(caseData.get("customerSapCode")).orElse("");
				regionCode = Optional.ofNullable(caseData.get("regionCode")).orElse("");
				regionName = Optional.ofNullable(caseData.get("regionName")).orElse("");
				shippingAddress = Optional.ofNullable(caseData.get("shippingAddress")).orElse("");
				shippingRecipient = Optional.ofNullable(caseData.get("shippingRecipient")).orElse("");
				shippingTelephone = Optional.ofNullable(caseData.get("shippingTelephone")).orElse("");
			});
		});
		When("[创建订单整单折扣计算获取单价11]当前存在如下商品价格信息", (DataTable dataTable) -> {
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
		And("[创建订单整单折扣计算获取单价11]创建订单添加商品时获取价格应该如下", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String itemCode = Optional.ofNullable(caseData.get("itemCode")).orElse("");
				String price = new BigDecimal(caseData.get("price")).setScale(2, RoundingMode.HALF_UP).toString();
				String distributionChannelCode = Optional.ofNullable(caseData.get("distributionChannelCode")).orElse("");
				Response apiResult = orderApiPrediscountFetchPrice01.orderAddProductFetchProductPrice(OrderAddProductFetchProductPriceDTO.builder()
						.orderType("STANDARD")
						.saleOrgSapCode(saleOrgSapCode)
						.customerSapCode(customerSapCode)
						.customerRegionCode(regionCode)
						.itemSapCodeOrCrmCodeOrDesc(itemCode)
						.build());
				apiResult.then().body("resultCode", Matchers.equalTo("0"), "resultMsg", Matchers.equalTo("success"));
				List<Map<String, String>> testCaseData = dataTable.asMaps().stream().map(map -> {
					Map<String, String> resultMap = new HashMap<>(map);
					resultMap.put("saleOrgSapCode", saleOrgSapCode);
					resultMap.put("customerSapCode", customerSapCode);
					resultMap.put("customerRegionCode", regionCode);
					return resultMap;
				}).collect(Collectors.toList());
				orderCheckOrderManagement01.checkAddProductFetchPrice(testCaseData, apiResult);
			});
		});
		Then("[创建订单整单折扣计算获取单价11]进行整单折扣计算后,订单行分摊如下", (DataTable dataTable) -> {
			String totalInlineSum = dataTable.asMaps().stream()
					.map(caseData -> new BigDecimal(caseData.get("totalInline")).setScale(2, RoundingMode.HALF_UP))
					.reduce(BigDecimal::add)
					.orElse(BigDecimal.ZERO).toString();
			String preDiscountTotalSum = dataTable.asMaps().stream()
					.map(caseData -> new BigDecimal(caseData.get("preDiscountTotal")).setScale(2, RoundingMode.HALF_UP))
					.reduce(BigDecimal::add)
					.orElse(BigDecimal.ZERO).toString();
			String orderDiscountTotalSum = dataTable.asMaps().stream()
					.map(caseData -> new BigDecimal(caseData.get("orderDiscountTotal")).setScale(2, RoundingMode.HALF_UP))
					.reduce(BigDecimal::add)
					.orElse(BigDecimal.ZERO).toString();
			List<OrderDiscountCalcCalculateReqGroupListDTO> orderDiscountCalcCalculateReqGroupListDTOS = new ArrayList<>();
			dataTable.asMaps().forEach(caseData -> {
				String id = "100000" + Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String orderWideDiscountSubject = Optional.ofNullable(caseData.get("orderWideDiscountSubject")).orElse("");
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String distributionChannelCode = Optional.ofNullable(caseData.get("distributionChannelCode")).orElse("");
				String distributionChannelName = Optional.ofNullable(caseData.get("distributionChannelName")).orElse("");
				String needShip = Optional.ofNullable(caseData.get("needShip")).orElse("");
				String unitPrice = new BigDecimal(caseData.get("unitPrice")).setScale(2, RoundingMode.HALF_UP).toString();
				String quantity = Optional.ofNullable(caseData.get("quantity")).orElse("");
				String boxRatio = new Integer(caseData.get("boxRatio")).toString();
				String preDiscount = new BigDecimal(caseData.get("preDiscount")).setScale(2, RoundingMode.HALF_UP).toString();
				String calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(2, RoundingMode.HALF_UP).toString();
				String totalInline = new BigDecimal(caseData.get("totalInline")).setScale(2, RoundingMode.HALF_UP).toString();
				String preDiscountTotal = new BigDecimal(caseData.get("preDiscountTotal")).setScale(2, RoundingMode.HALF_UP).toString();
				String orderDiscountTotal = new BigDecimal(caseData.get("orderDiscountTotal")).setScale(2, RoundingMode.HALF_UP).toString();
				orderDiscountCalcCalculateReqGroupListDTOS.add(OrderDiscountCalcCalculateReqGroupListDTO.builder()
						.itemGroupCode("01")
						.itemGroupName("成品药-非管制药品")
						.distributionChannelCode(distributionChannelCode)
						.distributionChannelName(distributionChannelName)
						.totalInline(totalInline)
						.preDiscountTotal(preDiscountTotal)
						.orderDiscountTotal(orderDiscountTotal)
						.list(Arrays.asList(
								OrderDiscountCalcCalculateReqGroupListListDTO.builder()
										.orderWideDiscountSubject(orderWideDiscountSubject)
										.itemSapCode(itemSapCode)
										.itemName(ProductEnum.fetchProductName(itemSapCode))
										.itemDesc(ProductEnum.fetchProductSpec(itemSapCode))
										.itemGroupCode("01")
										.itemGroupName("成品药-非管制药品")
										.distributionChannelCode(distributionChannelCode)
										.distributionChannelName(distributionChannelName)
										.unit("盒")
										.unitDesc("盒")
										.unitPrice(unitPrice)
										.calculationValue(calculationValue)
										.preDiscount(preDiscount)
										.preType("CUSTOMER_PRE_DISCOUNT")
										.calculationType("DISCOUNTED_PRICE")
										.quantity(quantity)
										.packingRatio("")
										.totalInline(totalInline)
										.preDiscountTotal(preDiscountTotal)
										.needShip(needShip)
										.baseUnitCode("盒")
										.baseUnitName("盒")
										.boxRatio(boxRatio)
										.xunit("1")
										.yunit("1")
										.build()))
						.build());
			});
			Response discountCalcResponse = orderApiOrderManagement01.discountCalc(OrderDataDTO.builder()
					.orderType("STANDARD")
					.saleOrgSapCode(saleOrgSapCode)
					.saleOrgName(SaleOrgEnum.fetchName(saleOrgSapCode))
					.regionCode(regionCode)
					.regionName(regionName)
					.customerEntSapCode(customerSapCode)
					.customerEntCrmCode(CompanyEnum.fetchCrmCode(customerSapCode))
					.customerEntZtCode(CompanyEnum.fetchZtCode(customerSapCode))
					.customerName(CompanyEnum.fetchName(customerSapCode))
					.invoiceType("VAT_SPECIAL_INVOICE")
					.shippingPolicy("NON_FULL_SHIPMENT")
					.priority("NORMAL")
					.paymentMethod("PRE_PAYMENT")
					.shippingRecipient(shippingRecipient)
					.shippingTelephone(shippingTelephone)
					.shippingAddress(shippingAddress)
					.remark("自动化测试")
					.calculateReq(OrderDiscountCalcCalculateReqDTO.builder()
							.totalInline(totalInlineSum)
							.preDiscountTotal(preDiscountTotalSum)
							.orderDiscountTotal(orderDiscountTotalSum)
							.groupList(orderDiscountCalcCalculateReqGroupListDTOS)
							.build())
					.build());
			discountCalcResponse.then().body("resultCode", Matchers.equalTo("0"), "resultMsg", Matchers.equalTo("success"));
			orderCheckOrderManagement01.checkOrderDiscountCalc(dataTable, discountCalcResponse);
			priceBatchClearData01.clearPirceData01("at");
			orderBatchClearData01.clearOrderPreDiscountData01("at");
			orderBatchClearData01.clearDiscountData01("at");
		});
	}

	private void scenario12() {
		Given("[创建订单整单折扣计算获取单价12]当前存在如下客户信息", (DataTable dataTable) -> {
			createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis());
			domainDataTable = dataTable;
			domainDataTable.asMaps().forEach(caseData -> {
				saleOrgSapCode = Optional.ofNullable(caseData.get("saleOrgSapCode")).orElse("");
				customerSapCode = Optional.ofNullable(caseData.get("customerSapCode")).orElse("");
				regionCode = Optional.ofNullable(caseData.get("regionCode")).orElse("");
				regionName = Optional.ofNullable(caseData.get("regionName")).orElse("");
				shippingAddress = Optional.ofNullable(caseData.get("shippingAddress")).orElse("");
				shippingRecipient = Optional.ofNullable(caseData.get("shippingRecipient")).orElse("");
				shippingTelephone = Optional.ofNullable(caseData.get("shippingTelephone")).orElse("");
			});
		});
		When("[创建订单整单折扣计算获取单价12]当前存在如下商品价格信息", (DataTable dataTable) -> {
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
		And("[创建订单整单折扣计算获取单价12]创建订单添加商品时获取价格应该如下", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String itemCode = Optional.ofNullable(caseData.get("itemCode")).orElse("");
				String price = new BigDecimal(caseData.get("price")).setScale(2, RoundingMode.HALF_UP).toString();
				String distributionChannelCode = Optional.ofNullable(caseData.get("distributionChannelCode")).orElse("");
				Response apiResult = orderApiPrediscountFetchPrice01.orderAddProductFetchProductPrice(OrderAddProductFetchProductPriceDTO.builder()
						.orderType("STANDARD")
						.saleOrgSapCode(saleOrgSapCode)
						.customerSapCode(customerSapCode)
						.customerRegionCode(regionCode)
						.itemSapCodeOrCrmCodeOrDesc(itemCode)
						.build());
				apiResult.then().body("resultCode", Matchers.equalTo("0"), "resultMsg", Matchers.equalTo("success"));
				List<Map<String, String>> testCaseData = dataTable.asMaps().stream().map(map -> {
					Map<String, String> resultMap = new HashMap<>(map);
					resultMap.put("saleOrgSapCode", saleOrgSapCode);
					resultMap.put("customerSapCode", customerSapCode);
					resultMap.put("customerRegionCode", regionCode);
					return resultMap;
				}).collect(Collectors.toList());
				orderCheckOrderManagement01.checkAddProductFetchPrice(testCaseData, apiResult);
			});
		});
		Then("[创建订单整单折扣计算获取单价12]进行整单折扣计算后,订单行分摊如下", (DataTable dataTable) -> {
			String totalInlineSum = dataTable.asMaps().stream()
					.map(caseData -> new BigDecimal(caseData.get("totalInline")).setScale(2, RoundingMode.HALF_UP))
					.reduce(BigDecimal::add)
					.orElse(BigDecimal.ZERO).toString();
			String preDiscountTotalSum = dataTable.asMaps().stream()
					.map(caseData -> new BigDecimal(caseData.get("preDiscountTotal")).setScale(2, RoundingMode.HALF_UP))
					.reduce(BigDecimal::add)
					.orElse(BigDecimal.ZERO).toString();
			String orderDiscountTotalSum = dataTable.asMaps().stream()
					.map(caseData -> new BigDecimal(caseData.get("orderDiscountTotal")).setScale(2, RoundingMode.HALF_UP))
					.reduce(BigDecimal::add)
					.orElse(BigDecimal.ZERO).toString();
			List<OrderDiscountCalcCalculateReqGroupListDTO> orderDiscountCalcCalculateReqGroupListDTOS = new ArrayList<>();
			dataTable.asMaps().forEach(caseData -> {
				String id = "100000" + Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String orderWideDiscountSubject = Optional.ofNullable(caseData.get("orderWideDiscountSubject")).orElse("");
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String distributionChannelCode = Optional.ofNullable(caseData.get("distributionChannelCode")).orElse("");
				String distributionChannelName = Optional.ofNullable(caseData.get("distributionChannelName")).orElse("");
				String needShip = Optional.ofNullable(caseData.get("needShip")).orElse("");
				String unitPrice = new BigDecimal(caseData.get("unitPrice")).setScale(2, RoundingMode.HALF_UP).toString();
				String quantity = Optional.ofNullable(caseData.get("quantity")).orElse("");
				String boxRatio = new Integer(caseData.get("boxRatio")).toString();
				String preDiscount = new BigDecimal(caseData.get("preDiscount")).setScale(2, RoundingMode.HALF_UP).toString();
				String calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(2, RoundingMode.HALF_UP).toString();
				String totalInline = new BigDecimal(caseData.get("totalInline")).setScale(2, RoundingMode.HALF_UP).toString();
				String preDiscountTotal = new BigDecimal(caseData.get("preDiscountTotal")).setScale(2, RoundingMode.HALF_UP).toString();
				String orderDiscountTotal = new BigDecimal(caseData.get("orderDiscountTotal")).setScale(2, RoundingMode.HALF_UP).toString();
				orderDiscountCalcCalculateReqGroupListDTOS.add(OrderDiscountCalcCalculateReqGroupListDTO.builder()
						.itemGroupCode("01")
						.itemGroupName("成品药-非管制药品")
						.distributionChannelCode(distributionChannelCode)
						.distributionChannelName(distributionChannelName)
						.totalInline(totalInline)
						.preDiscountTotal(preDiscountTotal)
						.orderDiscountTotal(orderDiscountTotal)
						.list(Arrays.asList(
								OrderDiscountCalcCalculateReqGroupListListDTO.builder()
										.orderWideDiscountSubject(orderWideDiscountSubject)
										.itemSapCode(itemSapCode)
										.itemName(ProductEnum.fetchProductName(itemSapCode))
										.itemDesc(ProductEnum.fetchProductSpec(itemSapCode))
										.itemGroupCode("01")
										.itemGroupName("成品药-非管制药品")
										.distributionChannelCode(distributionChannelCode)
										.distributionChannelName(distributionChannelName)
										.unit("盒")
										.unitDesc("盒")
										.unitPrice(unitPrice)
										.calculationValue(calculationValue)
										.preDiscount(preDiscount)
										.preType("CUSTOMER_PRE_DISCOUNT")
										.calculationType("DISCOUNTED_PRICE")
										.quantity(quantity)
										.packingRatio("")
										.totalInline(totalInline)
										.preDiscountTotal(preDiscountTotal)
										.needShip(needShip)
										.baseUnitCode("盒")
										.baseUnitName("盒")
										.boxRatio(boxRatio)
										.xunit("1")
										.yunit("1")
										.build()))
						.build());
			});
			Response discountCalcResponse = orderApiOrderManagement01.discountCalc(OrderDataDTO.builder()
					.orderType("STANDARD")
					.saleOrgSapCode(saleOrgSapCode)
					.saleOrgName(SaleOrgEnum.fetchName(saleOrgSapCode))
					.regionCode(regionCode)
					.regionName(regionName)
					.customerEntSapCode(customerSapCode)
					.customerEntCrmCode(CompanyEnum.fetchCrmCode(customerSapCode))
					.customerEntZtCode(CompanyEnum.fetchZtCode(customerSapCode))
					.customerName(CompanyEnum.fetchName(customerSapCode))
					.invoiceType("VAT_SPECIAL_INVOICE")
					.shippingPolicy("NON_FULL_SHIPMENT")
					.priority("NORMAL")
					.paymentMethod("PRE_PAYMENT")
					.shippingRecipient(shippingRecipient)
					.shippingTelephone(shippingTelephone)
					.shippingAddress(shippingAddress)
					.remark("自动化测试")
					.calculateReq(OrderDiscountCalcCalculateReqDTO.builder()
							.totalInline(totalInlineSum)
							.preDiscountTotal(preDiscountTotalSum)
							.orderDiscountTotal(orderDiscountTotalSum)
							.groupList(orderDiscountCalcCalculateReqGroupListDTOS)
							.build())
					.build());
			discountCalcResponse.then().body("resultCode", Matchers.equalTo("0"), "resultMsg", Matchers.equalTo("success"));
			orderCheckOrderManagement01.checkOrderDiscountCalc(dataTable, discountCalcResponse);
			priceBatchClearData01.clearPirceData01("at");
			orderBatchClearData01.clearOrderPreDiscountData01("at");
			orderBatchClearData01.clearDiscountData01("at");
		});
	}

}
