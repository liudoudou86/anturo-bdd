package com.anturo.qa.center.order.bdd.delivery.specs;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import com.anturo.qa.center.order.bdd.order.apis.OrderApiDelivery01;
import com.anturo.qa.center.order.bdd.order.apis.OrderApiOrderManagement01;
import com.anturo.qa.center.order.bdd.order.apis.OrderApiReturnOrderManagement01;
import com.anturo.qa.center.order.bdd.order.batchs.DeliveryBatchSelectData01;
import com.anturo.qa.center.order.bdd.order.batchs.ReturnOrderBatchSelectData01;
import com.anturo.qa.center.order.bdd.order.checks.DeliveryCheckOrderManagement01;
import com.anturo.qa.center.order.main.model.dto.*;
import com.anturo.qa.center.order.process.CompanyEnum;
import com.anturo.qa.center.order.process.ProductEnum;
import com.anturo.qa.center.order.process.ShippingEnum;
import io.cucumber.datatable.DataTable;
import io.cucumber.java8.En;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import net.thucydides.core.annotations.Steps;
import org.hamcrest.Matchers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author Tesla
 * @Date 2025/05/08
 * @Description
 */

@SuppressWarnings("all")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class DeliveryVerifyQuantityExceedLimitSpec implements En {

	String saleOrgSapCode, customerSapCode, regionCode, regionName;

	String createDate, createTime, orderNo;

	String eccId, deliveryNumber, materialNumber, writeOffDeliveryNumber, writeOffMaterialNumber;

	String batchNum, returnBatchNum, returnOrderItem;

	String returnEccId, returnDeliveryNumber, returnMaterialNumber, writeOffReturnDeliveryNumber, writeOffReturnMaterialNumber;

	DataTable domainDataTable;

	String itemId, whsePostReceiptId, returnOrderId, returnItemId, returnOrderNo;

	@Steps(actor = "QA", shared = true)
	OrderApiOrderManagement01 orderApiOrderManagement01;

	@Steps(actor = "QA", shared = true)
	OrderApiDelivery01 orderApiDelivery01;

	@Steps(actor = "QA", shared = true)
	DeliveryBatchSelectData01 deliveryBatchSelectData01;

	@Steps(actor = "QA", shared = true)
	DeliveryCheckOrderManagement01 deliveryCheckOrderManagement01;

	@Steps(actor = "QA", shared = true)
	OrderApiReturnOrderManagement01 orderApiReturnOrderManagement01;

	@Steps(actor = "QA", shared = true)
	ReturnOrderBatchSelectData01 returnOrderBatchSelectData01;

	public DeliveryVerifyQuantityExceedLimitSpec() {
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
		Given("[交货单校验超发01]当前存在如下客户信息", (DataTable dataTable) -> {
			createDate = new SimpleDateFormat("yyyyMMdd").format(System.currentTimeMillis());
			createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis());
			domainDataTable = dataTable;
			domainDataTable.asMaps().forEach(caseData -> {
				saleOrgSapCode = Optional.ofNullable(caseData.get("saleOrgSapCode")).orElse("");
				customerSapCode = Optional.ofNullable(caseData.get("customerSapCode")).orElse("");
				regionCode = Optional.ofNullable(caseData.get("regionCode")).orElse("");
				regionName = Optional.ofNullable(caseData.get("regionName")).orElse("");
			});
		});
		When("[交货单校验超发01]创建订单且下发SAP至待发货", () -> {
			eccId = "110" + RandomUtil.randomNumbers(7);
			// 创建订单
			orderApiDelivery01.copyOrderSubmit(customerSapCode);
			// 查看订单号
			orderNo = orderApiOrderManagement01.selectOrderInfo(OrderQueryPageByConditionDTO.builder()
					.saleOrgSapCode("1017,1401,2010,2820,2810,2020,2060,2610,2070")
					.customerCodeOrName(customerSapCode)
					.pageNum("1")
					.pageSize("10")
					.state("PENDING_BUSINESS_REVIEW")
					.build());
			// 订单商务审核
			orderApiDelivery01.orderApprove(OrderApproveShippingInfoDTO.builder()
					.orderNo(orderNo)
					.paymentMethod("PRE_PAYMENT")
					.shippingInfo(JSONUtil.parseObj(ShippingEnum.fetchOrderBody(customerSapCode)))
					.build());
			// 订单下发OMS
			orderApiDelivery01.orderSendToOmsTask();
			// SAP返回ECCID
			orderApiDelivery01.omsReturnPositiveEccId(OmsReturnPositiveEccIdDTO.builder()
					.eccMessage("自动化审核通过")
					.eccOrderId(eccId)
					.erpStatus("S")
					.originalOrderId(orderNo)
					.build());
		});
		And("[交货单校验超发01]SAP发送交货单数据如下", (DataTable dataTable) -> {
			deliveryNumber = "220" + RandomUtil.randomNumbers(7);
			materialNumber = "440" + RandomUtil.randomNumbers(7);
			List<OrderDeliveryDTO> orderDeliveryDTOS = new ArrayList<>();
			dataTable.asMaps().stream().forEach(
					caseData -> {
						String orderItem = caseData.get("orderItem");
						batchNum = caseData.get("batchNum");
						orderDeliveryDTOS.add(OrderDeliveryDTO.builder()
								.date(createDate)
								.deliverNo(deliveryNumber)
								.orderItem(orderItem)
								.batchNum(batchNum)
								.eccid(eccId)
								.orderItemQuantityTotal(batchNum)
								.orderNo(orderNo)
								.materialNumber(materialNumber)
								.build());
					}
			);
			orderApiDelivery01.createPositiveDeliveryShipment(orderDeliveryDTOS)
					.then()
					.body(Matchers.containsString("<data>false</data>"));
		});
		Then("[交货单校验超发01]数据被拦截无法入库", () -> {
			List<Map<String, String>> caseDataMap = new ArrayList<>();
			List<Map<String, Object>> sqlResult = deliveryBatchSelectData01.queryOrderDeliveryData01(orderNo);
			deliveryCheckOrderManagement01.checkDelivery(caseDataMap, sqlResult);
		});
	}

	private void scenario02() {
		Given("[交货单校验超发02]当前存在如下客户信息", (DataTable dataTable) -> {
			createDate = new SimpleDateFormat("yyyyMMdd").format(System.currentTimeMillis());
			createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis());
			domainDataTable = dataTable;
			domainDataTable.asMaps().forEach(caseData -> {
				saleOrgSapCode = Optional.ofNullable(caseData.get("saleOrgSapCode")).orElse("");
				customerSapCode = Optional.ofNullable(caseData.get("customerSapCode")).orElse("");
				regionCode = Optional.ofNullable(caseData.get("regionCode")).orElse("");
				regionName = Optional.ofNullable(caseData.get("regionName")).orElse("");
			});
		});
		When("[交货单校验超发02]创建订单且下发SAP至待发货", () -> {
			eccId = "110" + RandomUtil.randomNumbers(7);
			// 创建订单
			orderApiDelivery01.copyOrderSubmit(customerSapCode);
			// 查看订单号
			orderNo = orderApiOrderManagement01.selectOrderInfo(OrderQueryPageByConditionDTO.builder()
					.saleOrgSapCode("1017,1401,2010,2820,2810,2020,2060,2610,2070")
					.customerCodeOrName(customerSapCode)
					.pageNum("1")
					.pageSize("10")
					.state("PENDING_BUSINESS_REVIEW")
					.build());
			// 订单商务审核
			orderApiDelivery01.orderApprove(OrderApproveShippingInfoDTO.builder()
					.orderNo(orderNo)
					.paymentMethod("PRE_PAYMENT")
					.shippingInfo(JSONUtil.parseObj(ShippingEnum.fetchOrderBody(customerSapCode)))
					.build());
			// 订单下发OMS
			orderApiDelivery01.orderSendToOmsTask();
			// SAP返回ECCID
			orderApiDelivery01.omsReturnPositiveEccId(OmsReturnPositiveEccIdDTO.builder()
					.eccMessage("自动化审核通过")
					.eccOrderId(eccId)
					.erpStatus("S")
					.originalOrderId(orderNo)
					.build());
		});
		And("[交货单校验超发02]SAP发送交货单数据如下", (DataTable dataTable) -> {
			deliveryNumber = "220" + RandomUtil.randomNumbers(7);
			materialNumber = "440" + RandomUtil.randomNumbers(7);
			List<OrderDeliveryDTO> orderDeliveryDTOS = new ArrayList<>();
			dataTable.asMaps().stream().forEach(
					caseData -> {
						String orderItem = caseData.get("orderItem");
						batchNum = caseData.get("batchNum");
						orderDeliveryDTOS.add(OrderDeliveryDTO.builder()
								.date(createDate)
								.deliverNo(deliveryNumber)
								.orderItem(orderItem)
								.batchNum(batchNum)
								.eccid(eccId)
								.orderItemQuantityTotal(batchNum)
								.orderNo(orderNo)
								.materialNumber(materialNumber)
								.build());
					}
			);
			orderApiDelivery01.createPositiveDeliveryShipment(orderDeliveryDTOS)
					.then()
					.body(Matchers.containsString("<data>true</data>"));
		});
		Then("[交货单校验超发02]交货单数据正常入库", (DataTable dataTable) -> {
			List<Map<String, String>> caseDataMap = new ArrayList<>();
			dataTable.asMaps().forEach(caseData -> {
				Map<String, String> processedData = new HashMap<>();
				processedData.put("item_id", Optional.ofNullable(caseData.get("orderItem")).orElse(""));
				processedData.put("num", Optional.ofNullable(caseData.get("batchNum")).orElse(""));
				processedData.put("bwart", Optional.ofNullable(caseData.get("type")).orElse(""));
				processedData.put("status", Optional.ofNullable(caseData.get("status")).orElse(""));
				caseDataMap.add(processedData);
			});
			List<Map<String, Object>> sqlResult = deliveryBatchSelectData01.queryOrderDeliveryData01(orderNo);
			deliveryCheckOrderManagement01.checkDelivery(caseDataMap, sqlResult);
		});
	}

	private void scenario03() {
		Given("[交货单校验超发03]当前存在如下客户信息", (DataTable dataTable) -> {
			createDate = new SimpleDateFormat("yyyyMMdd").format(System.currentTimeMillis());
			createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis());
			domainDataTable = dataTable;
			domainDataTable.asMaps().forEach(caseData -> {
				saleOrgSapCode = Optional.ofNullable(caseData.get("saleOrgSapCode")).orElse("");
				customerSapCode = Optional.ofNullable(caseData.get("customerSapCode")).orElse("");
				regionCode = Optional.ofNullable(caseData.get("regionCode")).orElse("");
				regionName = Optional.ofNullable(caseData.get("regionName")).orElse("");
			});
		});
		When("[交货单校验超发03]创建订单且下发SAP至待发货", () -> {
			eccId = "110" + RandomUtil.randomNumbers(7);
			// 创建订单
			orderApiDelivery01.copyOrderSubmit(customerSapCode);
			// 查看订单号
			orderNo = orderApiOrderManagement01.selectOrderInfo(OrderQueryPageByConditionDTO.builder()
					.saleOrgSapCode("1017,1401,2010,2820,2810,2020,2060,2610,2070")
					.customerCodeOrName(customerSapCode)
					.pageNum("1")
					.pageSize("10")
					.state("PENDING_BUSINESS_REVIEW")
					.build());
			// 订单商务审核
			orderApiDelivery01.orderApprove(OrderApproveShippingInfoDTO.builder()
					.orderNo(orderNo)
					.paymentMethod("PRE_PAYMENT")
					.shippingInfo(JSONUtil.parseObj(ShippingEnum.fetchOrderBody(customerSapCode)))
					.build());
			// 订单下发OMS
			orderApiDelivery01.orderSendToOmsTask();
			// SAP返回ECCID
			orderApiDelivery01.omsReturnPositiveEccId(OmsReturnPositiveEccIdDTO.builder()
					.eccMessage("自动化审核通过")
					.eccOrderId(eccId)
					.erpStatus("S")
					.originalOrderId(orderNo)
					.build());
		});
		And("[交货单校验超发03]SAP发送交货单数据如下", (DataTable dataTable) -> {
			deliveryNumber = "220" + RandomUtil.randomNumbers(7);
			materialNumber = "440" + RandomUtil.randomNumbers(7);
			List<OrderDeliveryDTO> orderDeliveryDTOS = new ArrayList<>();
			dataTable.asMaps().stream().forEach(
					caseData -> {
						String orderItem = caseData.get("orderItem");
						batchNum = caseData.get("batchNum");
						orderDeliveryDTOS.add(OrderDeliveryDTO.builder()
								.date(createDate)
								.deliverNo(deliveryNumber)
								.orderItem(orderItem)
								.batchNum(batchNum)
								.eccid(eccId)
								.orderItemQuantityTotal(batchNum)
								.orderNo(orderNo)
								.materialNumber(materialNumber)
								.build());
					}
			);
			orderApiDelivery01.createPositiveDeliveryShipment(orderDeliveryDTOS)
					.then()
					.body(Matchers.containsString("<data>true</data>"));
		});
		Then("[交货单校验超发03]交货单数据正常入库", (DataTable dataTable) -> {
			List<Map<String, String>> caseDataMap = new ArrayList<>();
			dataTable.asMaps().forEach(caseData -> {
				Map<String, String> processedData = new HashMap<>();
				processedData.put("item_id", Optional.ofNullable(caseData.get("orderItem")).orElse(""));
				processedData.put("num", Optional.ofNullable(caseData.get("batchNum")).orElse(""));
				processedData.put("bwart", Optional.ofNullable(caseData.get("type")).orElse(""));
				processedData.put("status", Optional.ofNullable(caseData.get("status")).orElse(""));
				caseDataMap.add(processedData);
			});
			List<Map<String, Object>> sqlResult = deliveryBatchSelectData01.queryOrderDeliveryData01(orderNo);
			deliveryCheckOrderManagement01.checkDelivery(caseDataMap, sqlResult);
		});
	}

	private void scenario04() {
		Given("[交货单校验超发04]当前存在如下客户信息", (DataTable dataTable) -> {
			createDate = new SimpleDateFormat("yyyyMMdd").format(System.currentTimeMillis());
			createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis());
			domainDataTable = dataTable;
			domainDataTable.asMaps().forEach(caseData -> {
				saleOrgSapCode = Optional.ofNullable(caseData.get("saleOrgSapCode")).orElse("");
				customerSapCode = Optional.ofNullable(caseData.get("customerSapCode")).orElse("");
				regionCode = Optional.ofNullable(caseData.get("regionCode")).orElse("");
				regionName = Optional.ofNullable(caseData.get("regionName")).orElse("");
			});
		});
		When("[交货单校验超发04]创建订单且下发SAP至待发货", () -> {
			eccId = "110" + RandomUtil.randomNumbers(7);
			// 创建订单
			orderApiDelivery01.copyOrderSubmit(customerSapCode);
			// 查看订单号
			orderNo = orderApiOrderManagement01.selectOrderInfo(OrderQueryPageByConditionDTO.builder()
					.saleOrgSapCode("1017,1401,2010,2820,2810,2020,2060,2610,2070")
					.customerCodeOrName(customerSapCode)
					.pageNum("1")
					.pageSize("10")
					.state("PENDING_BUSINESS_REVIEW")
					.build());
			// 订单商务审核
			orderApiDelivery01.orderApprove(OrderApproveShippingInfoDTO.builder()
					.orderNo(orderNo)
					.paymentMethod("PRE_PAYMENT")
					.shippingInfo(JSONUtil.parseObj(ShippingEnum.fetchOrderBody(customerSapCode)))
					.build());
			// 订单下发OMS
			orderApiDelivery01.orderSendToOmsTask();
			// SAP返回ECCID
			orderApiDelivery01.omsReturnPositiveEccId(OmsReturnPositiveEccIdDTO.builder()
					.eccMessage("自动化审核通过")
					.eccOrderId(eccId)
					.erpStatus("S")
					.originalOrderId(orderNo)
					.build());
		});
		And("[交货单校验超发04]SAP发送交货单数据如下", (DataTable dataTable) -> {
			deliveryNumber = "220" + RandomUtil.randomNumbers(7);
			materialNumber = "440" + RandomUtil.randomNumbers(7);
			List<OrderDeliveryDTO> orderDeliveryDTOS = new ArrayList<>();
			dataTable.asMaps().stream().forEach(
					caseData -> {
						String orderItem = caseData.get("orderItem");
						batchNum = caseData.get("batchNum");
						orderDeliveryDTOS.add(OrderDeliveryDTO.builder()
								.date(createDate)
								.deliverNo(deliveryNumber)
								.orderItem(orderItem)
								.batchNum(batchNum)
								.eccid(eccId)
								.orderItemQuantityTotal(batchNum)
								.orderNo(orderNo)
								.materialNumber(materialNumber)
								.build());
					}
			);
			orderApiDelivery01.createPositiveDeliveryShipment(orderDeliveryDTOS)
					.then()
					.body(Matchers.containsString("<data>true</data>"));
		});
		And("[交货单校验超发04]SAP再次发送交货单数据如下", (DataTable dataTable) -> {
			deliveryNumber = "220" + RandomUtil.randomNumbers(7);
			materialNumber = "440" + RandomUtil.randomNumbers(7);
			List<OrderDeliveryDTO> orderDeliveryDTOS = new ArrayList<>();
			dataTable.asMaps().stream().forEach(
					caseData -> {
						String orderItem = caseData.get("orderItem");
						batchNum = caseData.get("batchNum");
						orderDeliveryDTOS.add(OrderDeliveryDTO.builder()
								.date(createDate)
								.deliverNo(deliveryNumber)
								.orderItem(orderItem)
								.batchNum(batchNum)
								.eccid(eccId)
								.orderItemQuantityTotal(batchNum)
								.orderNo(orderNo)
								.materialNumber(materialNumber)
								.build());
					}
			);
			orderApiDelivery01.createPositiveDeliveryShipment(orderDeliveryDTOS)
					.then()
					.body(Matchers.containsString("<data>false</data>"));
		});
		Then("[交货单校验超发04]交货单数据入库数据如下", (DataTable dataTable) -> {
			List<Map<String, String>> caseDataMap = new ArrayList<>();
			dataTable.asMaps().forEach(caseData -> {
				Map<String, String> processedData = new HashMap<>();
				processedData.put("item_id", Optional.ofNullable(caseData.get("orderItem")).orElse(""));
				processedData.put("num", Optional.ofNullable(caseData.get("batchNum")).orElse(""));
				processedData.put("bwart", Optional.ofNullable(caseData.get("type")).orElse(""));
				processedData.put("status", Optional.ofNullable(caseData.get("status")).orElse(""));
				caseDataMap.add(processedData);
			});
			List<Map<String, Object>> sqlResult = deliveryBatchSelectData01.queryOrderDeliveryData01(orderNo);
			deliveryCheckOrderManagement01.checkDelivery(caseDataMap, sqlResult);
		});
	}

	private void scenario05() {
		Given("[交货单校验超发05]当前存在如下客户信息", (DataTable dataTable) -> {
			createDate = new SimpleDateFormat("yyyyMMdd").format(System.currentTimeMillis());
			createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis());
			domainDataTable = dataTable;
			domainDataTable.asMaps().forEach(caseData -> {
				saleOrgSapCode = Optional.ofNullable(caseData.get("saleOrgSapCode")).orElse("");
				customerSapCode = Optional.ofNullable(caseData.get("customerSapCode")).orElse("");
				regionCode = Optional.ofNullable(caseData.get("regionCode")).orElse("");
				regionName = Optional.ofNullable(caseData.get("regionName")).orElse("");
			});
		});
		When("[交货单校验超发05]创建订单且下发SAP至待发货", () -> {
			eccId = "110" + RandomUtil.randomNumbers(7);
			// 创建订单
			orderApiDelivery01.copyOrderSubmit(customerSapCode);
			// 查看订单号
			orderNo = orderApiOrderManagement01.selectOrderInfo(OrderQueryPageByConditionDTO.builder()
					.saleOrgSapCode("1017,1401,2010,2820,2810,2020,2060,2610,2070")
					.customerCodeOrName(customerSapCode)
					.pageNum("1")
					.pageSize("10")
					.state("PENDING_BUSINESS_REVIEW")
					.build());
			// 订单商务审核
			orderApiDelivery01.orderApprove(OrderApproveShippingInfoDTO.builder()
					.orderNo(orderNo)
					.paymentMethod("PRE_PAYMENT")
					.shippingInfo(JSONUtil.parseObj(ShippingEnum.fetchOrderBody(customerSapCode)))
					.build());
			// 订单下发OMS
			orderApiDelivery01.orderSendToOmsTask();
			// SAP返回ECCID
			orderApiDelivery01.omsReturnPositiveEccId(OmsReturnPositiveEccIdDTO.builder()
					.eccMessage("自动化审核通过")
					.eccOrderId(eccId)
					.erpStatus("S")
					.originalOrderId(orderNo)
					.build());
		});
		And("[交货单校验超发05]SAP发送交货单数据如下", (DataTable dataTable) -> {
			deliveryNumber = "220" + RandomUtil.randomNumbers(7);
			materialNumber = "440" + RandomUtil.randomNumbers(7);
			List<OrderDeliveryDTO> orderDeliveryDTOS = new ArrayList<>();
			dataTable.asMaps().stream().forEach(
					caseData -> {
						String orderItem = caseData.get("orderItem");
						batchNum = caseData.get("batchNum");
						orderDeliveryDTOS.add(OrderDeliveryDTO.builder()
								.date(createDate)
								.deliverNo(deliveryNumber)
								.orderItem(orderItem)
								.batchNum(batchNum)
								.eccid(eccId)
								.orderItemQuantityTotal(batchNum)
								.orderNo(orderNo)
								.materialNumber(materialNumber)
								.build());
					}
			);
			orderApiDelivery01.createPositiveDeliveryShipment(orderDeliveryDTOS)
					.then()
					.body(Matchers.containsString("<data>true</data>"));
		});
		And("[交货单校验超发05]SAP再次发送交货单数据如下", (DataTable dataTable) -> {
			deliveryNumber = "220" + RandomUtil.randomNumbers(7);
			materialNumber = "440" + RandomUtil.randomNumbers(7);
			List<OrderDeliveryDTO> orderDeliveryDTOS = new ArrayList<>();
			dataTable.asMaps().stream().forEach(
					caseData -> {
						String orderItem = caseData.get("orderItem");
						batchNum = caseData.get("batchNum");
						orderDeliveryDTOS.add(OrderDeliveryDTO.builder()
								.date(createDate)
								.deliverNo(deliveryNumber)
								.orderItem(orderItem)
								.batchNum(batchNum)
								.eccid(eccId)
								.orderItemQuantityTotal(batchNum)
								.orderNo(orderNo)
								.materialNumber(materialNumber)
								.build());
					}
			);
			orderApiDelivery01.createPositiveDeliveryShipment(orderDeliveryDTOS)
					.then()
					.body(Matchers.containsString("<data>true</data>"));
		});
		Then("[交货单校验超发05]交货单数据入库数据如下", (DataTable dataTable) -> {
			List<Map<String, String>> caseDataMap = new ArrayList<>();
			dataTable.asMaps().forEach(caseData -> {
				Map<String, String> processedData = new HashMap<>();
				processedData.put("item_id", Optional.ofNullable(caseData.get("orderItem")).orElse(""));
				processedData.put("num", Optional.ofNullable(caseData.get("batchNum")).orElse(""));
				processedData.put("bwart", Optional.ofNullable(caseData.get("type")).orElse(""));
				processedData.put("status", Optional.ofNullable(caseData.get("status")).orElse(""));
				caseDataMap.add(processedData);
			});
			List<Map<String, Object>> sqlResult = deliveryBatchSelectData01.queryOrderDeliveryData01(orderNo);
			deliveryCheckOrderManagement01.checkDelivery(caseDataMap, sqlResult);
		});
	}

	private void scenario06() {
		Given("[交货单校验超发06]当前存在如下客户信息", (DataTable dataTable) -> {
			createDate = new SimpleDateFormat("yyyyMMdd").format(System.currentTimeMillis());
			createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis());
			domainDataTable = dataTable;
			domainDataTable.asMaps().forEach(caseData -> {
				saleOrgSapCode = Optional.ofNullable(caseData.get("saleOrgSapCode")).orElse("");
				customerSapCode = Optional.ofNullable(caseData.get("customerSapCode")).orElse("");
				regionCode = Optional.ofNullable(caseData.get("regionCode")).orElse("");
				regionName = Optional.ofNullable(caseData.get("regionName")).orElse("");
			});
		});
		When("[交货单校验超发06]创建订单且下发SAP至待发货", () -> {
			eccId = "110" + RandomUtil.randomNumbers(7);
			// 创建订单
			orderApiDelivery01.copyOrderSubmit(customerSapCode);
			// 查看订单号
			orderNo = orderApiOrderManagement01.selectOrderInfo(OrderQueryPageByConditionDTO.builder()
					.saleOrgSapCode("1017,1401,2010,2820,2810,2020,2060,2610,2070")
					.customerCodeOrName(customerSapCode)
					.pageNum("1")
					.pageSize("10")
					.state("PENDING_BUSINESS_REVIEW")
					.build());
			// 订单商务审核
			orderApiDelivery01.orderApprove(OrderApproveShippingInfoDTO.builder()
					.orderNo(orderNo)
					.paymentMethod("PRE_PAYMENT")
					.shippingInfo(JSONUtil.parseObj(ShippingEnum.fetchOrderBody(customerSapCode)))
					.build());
			// 订单下发OMS
			orderApiDelivery01.orderSendToOmsTask();
			// SAP返回ECCID
			orderApiDelivery01.omsReturnPositiveEccId(OmsReturnPositiveEccIdDTO.builder()
					.eccMessage("自动化审核通过")
					.eccOrderId(eccId)
					.erpStatus("S")
					.originalOrderId(orderNo)
					.build());
		});
		And("[交货单校验超发06]SAP发送交货单数据如下", (DataTable dataTable) -> {
			deliveryNumber = "220" + RandomUtil.randomNumbers(7);
			materialNumber = "440" + RandomUtil.randomNumbers(7);
			List<OrderDeliveryDTO> orderDeliveryDTOS = new ArrayList<>();
			dataTable.asMaps().stream().forEach(
					caseData -> {
						String orderItem = caseData.get("orderItem");
						batchNum = caseData.get("batchNum");
						orderDeliveryDTOS.add(OrderDeliveryDTO.builder()
								.date(createDate)
								.deliverNo(deliveryNumber)
								.orderItem(orderItem)
								.batchNum(batchNum)
								.eccid(eccId)
								.orderItemQuantityTotal(batchNum)
								.orderNo(orderNo)
								.materialNumber(materialNumber)
								.build());
					}
			);
			orderApiDelivery01.createPositiveDeliveryShipment(orderDeliveryDTOS)
					.then()
					.body(Matchers.containsString("<data>true</data>"));
		});
		And("[交货单校验超发06]SAP再次发送交货单数据如下", (DataTable dataTable) -> {
			deliveryNumber = "220" + RandomUtil.randomNumbers(7);
			materialNumber = "440" + RandomUtil.randomNumbers(7);
			List<OrderDeliveryDTO> orderDeliveryDTOS = new ArrayList<>();
			dataTable.asMaps().stream().forEach(
					caseData -> {
						String orderItem = caseData.get("orderItem");
						batchNum = caseData.get("batchNum");
						orderDeliveryDTOS.add(OrderDeliveryDTO.builder()
								.date(createDate)
								.deliverNo(deliveryNumber)
								.orderItem(orderItem)
								.batchNum(batchNum)
								.eccid(eccId)
								.orderItemQuantityTotal(batchNum)
								.orderNo(orderNo)
								.materialNumber(materialNumber)
								.build());
					}
			);
			orderApiDelivery01.createPositiveDeliveryShipment(orderDeliveryDTOS)
					.then()
					.body(Matchers.containsString("<data>true</data>"));
		});
		Then("[交货单校验超发06]交货单数据入库数据如下", (DataTable dataTable) -> {
			List<Map<String, String>> caseDataMap = new ArrayList<>();
			dataTable.asMaps().forEach(caseData -> {
				Map<String, String> processedData = new HashMap<>();
				processedData.put("item_id", Optional.ofNullable(caseData.get("orderItem")).orElse(""));
				processedData.put("num", Optional.ofNullable(caseData.get("batchNum")).orElse(""));
				processedData.put("bwart", Optional.ofNullable(caseData.get("type")).orElse(""));
				processedData.put("status", Optional.ofNullable(caseData.get("status")).orElse(""));
				caseDataMap.add(processedData);
			});
			List<Map<String, Object>> sqlResult = deliveryBatchSelectData01.queryOrderDeliveryData01(orderNo);
			deliveryCheckOrderManagement01.checkDelivery(caseDataMap, sqlResult);
		});
	}

	private void scenario07() {
		Given("[交货单校验超发07]当前存在如下客户信息", (DataTable dataTable) -> {
			createDate = new SimpleDateFormat("yyyyMMdd").format(System.currentTimeMillis());
			createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis());
			domainDataTable = dataTable;
			domainDataTable.asMaps().forEach(caseData -> {
				saleOrgSapCode = Optional.ofNullable(caseData.get("saleOrgSapCode")).orElse("");
				customerSapCode = Optional.ofNullable(caseData.get("customerSapCode")).orElse("");
				regionCode = Optional.ofNullable(caseData.get("regionCode")).orElse("");
				regionName = Optional.ofNullable(caseData.get("regionName")).orElse("");
			});
		});
		When("[交货单校验超发07]创建订单且下发SAP至待发货", () -> {
			eccId = "110" + RandomUtil.randomNumbers(7);
			// 创建订单
			orderApiDelivery01.copyOrderSubmit(customerSapCode);
			// 查看订单号
			orderNo = orderApiOrderManagement01.selectOrderInfo(OrderQueryPageByConditionDTO.builder()
					.saleOrgSapCode("1017,1401,2010,2820,2810,2020,2060,2610,2070")
					.customerCodeOrName(customerSapCode)
					.pageNum("1")
					.pageSize("10")
					.state("PENDING_BUSINESS_REVIEW")
					.build());
			// 订单商务审核
			orderApiDelivery01.orderApprove(OrderApproveShippingInfoDTO.builder()
					.orderNo(orderNo)
					.paymentMethod("PRE_PAYMENT")
					.shippingInfo(JSONUtil.parseObj(ShippingEnum.fetchOrderBody(customerSapCode)))
					.build());
			// 订单下发OMS
			orderApiDelivery01.orderSendToOmsTask();
			// SAP返回ECCID
			orderApiDelivery01.omsReturnPositiveEccId(OmsReturnPositiveEccIdDTO.builder()
					.eccMessage("自动化审核通过")
					.eccOrderId(eccId)
					.erpStatus("S")
					.originalOrderId(orderNo)
					.build());
		});
		And("[交货单校验超发07]SAP发送交货单数据如下", (DataTable dataTable) -> {
			deliveryNumber = "220" + RandomUtil.randomNumbers(7);
			materialNumber = "440" + RandomUtil.randomNumbers(7);
			List<OrderDeliveryDTO> orderDeliveryDTOS = new ArrayList<>();
			dataTable.asMaps().stream().forEach(
					caseData -> {
						String orderItem = caseData.get("orderItem");
						batchNum = caseData.get("batchNum");
						orderDeliveryDTOS.add(OrderDeliveryDTO.builder()
								.date(createDate)
								.deliverNo(deliveryNumber)
								.orderItem(orderItem)
								.batchNum(batchNum)
								.eccid(eccId)
								.orderItemQuantityTotal(batchNum)
								.orderNo(orderNo)
								.materialNumber(materialNumber)
								.build());
					}
			);
			orderApiDelivery01.createPositiveDeliveryShipment(orderDeliveryDTOS)
					.then()
					.body(Matchers.containsString("<data>true</data>"));
		});
		Then("[交货单校验超发07]交货单过滤重复数据正常入库", (DataTable dataTable) -> {
			List<Map<String, String>> caseDataMap = new ArrayList<>();
			dataTable.asMaps().forEach(caseData -> {
				Map<String, String> processedData = new HashMap<>();
				processedData.put("item_id", Optional.ofNullable(caseData.get("orderItem")).orElse(""));
				processedData.put("num", Optional.ofNullable(caseData.get("batchNum")).orElse(""));
				processedData.put("bwart", Optional.ofNullable(caseData.get("type")).orElse(""));
				processedData.put("status", Optional.ofNullable(caseData.get("status")).orElse(""));
				caseDataMap.add(processedData);
			});
			List<Map<String, Object>> sqlResult = deliveryBatchSelectData01.queryOrderDeliveryData01(orderNo);
			deliveryCheckOrderManagement01.checkDelivery(caseDataMap, sqlResult);
		});
		When("[交货单校验超发07]创建退货订单且下发SAP", (DataTable dataTable) -> {
			List<ReturnOrderDetailRespDTO> returnOrderDetailRespDTOS = new ArrayList<>();
			List<AcceptDetailReqDTO> acceptDetailReqDTOS = new ArrayList<>();
			List<String> refundOrderItems = new ArrayList<>();
			dataTable.asMaps().forEach(caseData -> {
				returnOrderItem = Optional.ofNullable(caseData.get("returnOrderItem")).orElse("");
				String sku = Optional.ofNullable(caseData.get("sku")).orElse("");
				returnBatchNum = Optional.ofNullable(caseData.get("returnBatchNum")).orElse("");
				String unitPrice = new BigDecimal(caseData.get("unitPrice")).setScale(2, RoundingMode.HALF_UP).toString();
				String returnOrderLinePrice = NumberUtil.mul(unitPrice, returnBatchNum).negate().toString();
				itemId = returnOrderBatchSelectData01.queryOrderItemIdData01(orderNo, returnOrderItem);
				whsePostReceiptId = returnOrderBatchSelectData01.queryDeliveryIdData01(materialNumber);
				refundOrderItems.add(returnOrderItem);
				returnOrderDetailRespDTOS.add(ReturnOrderDetailRespDTO.builder()
						.orderNo(orderNo)
						.itemLine(returnOrderItem)
						.itemId(itemId)
						.whsePostReceiptId(whsePostReceiptId)
						.orderWideDiscountSubject("PT00")
						.sku(sku)
						.description(ProductEnum.fetchProductSpec(sku))
						.batchNum("202504")
						.location("1201")
						.isReturnable(batchNum)
						.oriQuantity(batchNum)
						.quantity(returnBatchNum)
						.unitPrice(unitPrice)
						.preDiscountUnitPrice(unitPrice)
						.rebateTotal("0")
						.returnOrderLinePrice(returnOrderLinePrice)
						.selected("true")
						.build());
			});
			orderApiReturnOrderManagement01.returnOrderSubmit(ReturnOrderRespDTO.builder()
					.orderNo(orderNo)
					.reason("接口自动化")
					.isReceiveDrugInvoice("0")
					.isResendDrug("0")
					.isReturnPurveyorDrug("")
					.returnOrderDetailRespDTOList(returnOrderDetailRespDTOS)
					.build());
			returnOrderId = orderApiReturnOrderManagement01.returnOrderSelectId(ReturnOrderSelectIdDTO.builder()
					.saleOrgSapCode("1017,1401,2010,2820,2810,2020,2060,2610,2070")
					.regionCode(regionCode)
					.customerInfo(CustomerInfoDTO.builder()
							.code(CompanyEnum.fetchZtCode(customerSapCode))
							.name(CompanyEnum.fetchName(customerSapCode))
							.label("天津旭海津药医药有限公司-0010020753")
							.value(CompanyEnum.fetchZtCode(customerSapCode))
							.sapCode(customerSapCode)
							.crmCode(CompanyEnum.fetchCrmCode(customerSapCode))
							.regionCode(regionCode)
							.regionName(regionName)
							.build())
					.orderNo(orderNo)
					.customerCodeOrName(customerSapCode)
					.pageNum("1")
					.pageSize("10")
					.build());
			returnItemId = returnOrderBatchSelectData01.queryReturnOrderDetailIdData01(returnOrderId);
			acceptDetailReqDTOS.add(AcceptDetailReqDTO.builder()
					.quantity(returnBatchNum)
					.returnItemId(returnItemId)
					.itemLine(returnOrderItem)
					.orderNo(orderNo)
					.itemId(itemId)
					.whsePostReceiptId(whsePostReceiptId)
					.build());
			orderApiReturnOrderManagement01.returnOrderBusinessReview(returnOrderId);
			orderApiReturnOrderManagement01.returnOrderManualReview(returnOrderId);
			orderApiReturnOrderManagement01.returnOrderAcceptReview(ReturnOrderAcceptRespDTO.builder()
					.returnOrderId(returnOrderId)
					.acceptDetailReqDTOList(acceptDetailReqDTOS)
					.build());
			returnOrderNo = returnOrderBatchSelectData01.queryReturnOrderNoData01(returnOrderId);
			returnEccId = "110" + RandomUtil.randomNumbers(7);
			orderApiReturnOrderManagement01.omsReturnReverseEccId(OmsReturnReverseEccIdDTO.builder()
					.orderId(orderNo)
					.orderReturnId(returnOrderNo)
					.eccOrderID(returnEccId)
					.eccMessage("自动化审核通过")
					.erpStatus("S")
					.itemIds(refundOrderItems)
					.build());
		});
		And("[交货单校验超发07]SAP发送退货交货单数据如下", (DataTable dataTable) -> {
			returnDeliveryNumber = "220" + RandomUtil.randomNumbers(7);
			returnMaterialNumber = "440" + RandomUtil.randomNumbers(7);
			List<OrderDeliveryDTO> orderDeliveryDTOS = new ArrayList<>();
			dataTable.asMaps().stream().forEach(
					caseData -> {
						String returnOrderItem = caseData.get("returnOrderItem");
						String deliveryBatchNum = caseData.get("deliveryBatchNum");
						orderDeliveryDTOS.add(OrderDeliveryDTO.builder()
								.date(createDate)
								.deliverNo(returnDeliveryNumber)
								.orderItem(returnOrderItem)
								.batchNum(deliveryBatchNum)
								.eccid(returnEccId)
								.orderItemQuantityTotal(deliveryBatchNum)
								.orderNo(orderNo)
								.refundOrderNo(returnOrderNo)
								.materialNumber(returnMaterialNumber)
								.build());
					}
			);
			orderApiDelivery01.createRefundDeliveryShipment(orderDeliveryDTOS)
					.then()
					.body(Matchers.containsString("<data>false</data>"));
		});
		Then("[交货单校验超发07]退货交货单数据入库数据如下", (DataTable dataTable) -> {
			List<Map<String, String>> caseDataMap = new ArrayList<>();
			dataTable.asMaps().forEach(caseData -> {
				Map<String, String> processedData = new HashMap<>();
				processedData.put("item_id", Optional.ofNullable(caseData.get("orderItem")).orElse(""));
				processedData.put("num", Optional.ofNullable(caseData.get("batchNum")).orElse(""));
				processedData.put("bwart", Optional.ofNullable(caseData.get("type")).orElse(""));
				processedData.put("status", Optional.ofNullable(caseData.get("status")).orElse(""));
				caseDataMap.add(processedData);
			});
			List<Map<String, Object>> sqlResult = deliveryBatchSelectData01.queryOrderDeliveryData01(orderNo);
			deliveryCheckOrderManagement01.checkDelivery(caseDataMap, sqlResult);
		});
	}

	private void scenario08() {
		Given("[交货单校验超发08]当前存在如下客户信息", (DataTable dataTable) -> {
			createDate = new SimpleDateFormat("yyyyMMdd").format(System.currentTimeMillis());
			createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis());
			domainDataTable = dataTable;
			domainDataTable.asMaps().forEach(caseData -> {
				saleOrgSapCode = Optional.ofNullable(caseData.get("saleOrgSapCode")).orElse("");
				customerSapCode = Optional.ofNullable(caseData.get("customerSapCode")).orElse("");
				regionCode = Optional.ofNullable(caseData.get("regionCode")).orElse("");
				regionName = Optional.ofNullable(caseData.get("regionName")).orElse("");
			});
		});
		When("[交货单校验超发08]创建订单且下发SAP至待发货", () -> {
			eccId = "110" + RandomUtil.randomNumbers(7);
			// 创建订单
			orderApiDelivery01.copyOrderSubmit(customerSapCode);
			// 查看订单号
			orderNo = orderApiOrderManagement01.selectOrderInfo(OrderQueryPageByConditionDTO.builder()
					.saleOrgSapCode("1017,1401,2010,2820,2810,2020,2060,2610,2070")
					.customerCodeOrName(customerSapCode)
					.pageNum("1")
					.pageSize("10")
					.state("PENDING_BUSINESS_REVIEW")
					.build());
			// 订单商务审核
			orderApiDelivery01.orderApprove(OrderApproveShippingInfoDTO.builder()
					.orderNo(orderNo)
					.paymentMethod("PRE_PAYMENT")
					.shippingInfo(JSONUtil.parseObj(ShippingEnum.fetchOrderBody(customerSapCode)))
					.build());
			// 订单下发OMS
			orderApiDelivery01.orderSendToOmsTask();
			// SAP返回ECCID
			orderApiDelivery01.omsReturnPositiveEccId(OmsReturnPositiveEccIdDTO.builder()
					.eccMessage("自动化审核通过")
					.eccOrderId(eccId)
					.erpStatus("S")
					.originalOrderId(orderNo)
					.build());
		});
		And("[交货单校验超发08]SAP发送交货单数据如下", (DataTable dataTable) -> {
			deliveryNumber = "220" + RandomUtil.randomNumbers(7);
			materialNumber = "440" + RandomUtil.randomNumbers(7);
			List<OrderDeliveryDTO> orderDeliveryDTOS = new ArrayList<>();
			dataTable.asMaps().stream().forEach(
					caseData -> {
						String orderItem = caseData.get("orderItem");
						batchNum = caseData.get("batchNum");
						orderDeliveryDTOS.add(OrderDeliveryDTO.builder()
								.date(createDate)
								.deliverNo(deliveryNumber)
								.orderItem(orderItem)
								.batchNum(batchNum)
								.eccid(eccId)
								.orderItemQuantityTotal(batchNum)
								.orderNo(orderNo)
								.materialNumber(materialNumber)
								.build());
					}
			);
			orderApiDelivery01.createPositiveDeliveryShipment(orderDeliveryDTOS)
					.then()
					.body(Matchers.containsString("<data>true</data>"));
		});
		Then("[交货单校验超发08]交货单过滤重复数据正常入库", (DataTable dataTable) -> {
			List<Map<String, String>> caseDataMap = new ArrayList<>();
			dataTable.asMaps().forEach(caseData -> {
				Map<String, String> processedData = new HashMap<>();
				processedData.put("item_id", Optional.ofNullable(caseData.get("orderItem")).orElse(""));
				processedData.put("num", Optional.ofNullable(caseData.get("batchNum")).orElse(""));
				processedData.put("bwart", Optional.ofNullable(caseData.get("type")).orElse(""));
				processedData.put("status", Optional.ofNullable(caseData.get("status")).orElse(""));
				caseDataMap.add(processedData);
			});
			List<Map<String, Object>> sqlResult = deliveryBatchSelectData01.queryOrderDeliveryData01(orderNo);
			deliveryCheckOrderManagement01.checkDelivery(caseDataMap, sqlResult);
		});
		When("[交货单校验超发08]创建退货订单且下发SAP", (DataTable dataTable) -> {
			List<ReturnOrderDetailRespDTO> returnOrderDetailRespDTOS = new ArrayList<>();
			List<AcceptDetailReqDTO> acceptDetailReqDTOS = new ArrayList<>();
			List<String> refundOrderItems = new ArrayList<>();
			dataTable.asMaps().forEach(caseData -> {
				returnOrderItem = Optional.ofNullable(caseData.get("returnOrderItem")).orElse("");
				String sku = Optional.ofNullable(caseData.get("sku")).orElse("");
				returnBatchNum = Optional.ofNullable(caseData.get("returnBatchNum")).orElse("");
				String unitPrice = new BigDecimal(caseData.get("unitPrice")).setScale(2, RoundingMode.HALF_UP).toString();
				String returnOrderLinePrice = NumberUtil.mul(unitPrice, returnBatchNum).negate().toString();
				itemId = returnOrderBatchSelectData01.queryOrderItemIdData01(orderNo, returnOrderItem);
				whsePostReceiptId = returnOrderBatchSelectData01.queryDeliveryIdData01(materialNumber);
				refundOrderItems.add(returnOrderItem);
				returnOrderDetailRespDTOS.add(ReturnOrderDetailRespDTO.builder()
						.orderNo(orderNo)
						.itemLine(returnOrderItem)
						.itemId(itemId)
						.whsePostReceiptId(whsePostReceiptId)
						.orderWideDiscountSubject("PT00")
						.sku(sku)
						.description(ProductEnum.fetchProductSpec(sku))
						.batchNum("202504")
						.location("1201")
						.isReturnable(batchNum)
						.oriQuantity(batchNum)
						.quantity(returnBatchNum)
						.unitPrice(unitPrice)
						.preDiscountUnitPrice(unitPrice)
						.rebateTotal("0")
						.returnOrderLinePrice(returnOrderLinePrice)
						.selected("true")
						.build());
			});
			orderApiReturnOrderManagement01.returnOrderSubmit(ReturnOrderRespDTO.builder()
					.orderNo(orderNo)
					.reason("接口自动化")
					.isReceiveDrugInvoice("0")
					.isResendDrug("0")
					.isReturnPurveyorDrug("")
					.returnOrderDetailRespDTOList(returnOrderDetailRespDTOS)
					.build());
			returnOrderId = orderApiReturnOrderManagement01.returnOrderSelectId(ReturnOrderSelectIdDTO.builder()
					.saleOrgSapCode("1017,1401,2010,2820,2810,2020,2060,2610,2070")
					.regionCode(regionCode)
					.customerInfo(CustomerInfoDTO.builder()
							.code(CompanyEnum.fetchZtCode(customerSapCode))
							.name(CompanyEnum.fetchName(customerSapCode))
							.label("天津旭海津药医药有限公司-0010020753")
							.value(CompanyEnum.fetchZtCode(customerSapCode))
							.sapCode(customerSapCode)
							.crmCode(CompanyEnum.fetchCrmCode(customerSapCode))
							.regionCode(regionCode)
							.regionName(regionName)
							.build())
					.orderNo(orderNo)
					.customerCodeOrName(customerSapCode)
					.pageNum("1")
					.pageSize("10")
					.build());
			returnItemId = returnOrderBatchSelectData01.queryReturnOrderDetailIdData01(returnOrderId);
			acceptDetailReqDTOS.add(AcceptDetailReqDTO.builder()
					.quantity(returnBatchNum)
					.returnItemId(returnItemId)
					.itemLine(returnOrderItem)
					.orderNo(orderNo)
					.itemId(itemId)
					.whsePostReceiptId(whsePostReceiptId)
					.build());
			orderApiReturnOrderManagement01.returnOrderBusinessReview(returnOrderId);
			orderApiReturnOrderManagement01.returnOrderManualReview(returnOrderId);
			orderApiReturnOrderManagement01.returnOrderAcceptReview(ReturnOrderAcceptRespDTO.builder()
					.returnOrderId(returnOrderId)
					.acceptDetailReqDTOList(acceptDetailReqDTOS)
					.build());
			returnOrderNo = returnOrderBatchSelectData01.queryReturnOrderNoData01(returnOrderId);
			returnEccId = "110" + RandomUtil.randomNumbers(7);
			orderApiReturnOrderManagement01.omsReturnReverseEccId(OmsReturnReverseEccIdDTO.builder()
					.orderId(orderNo)
					.orderReturnId(returnOrderNo)
					.eccOrderID(returnEccId)
					.eccMessage("自动化审核通过")
					.erpStatus("S")
					.itemIds(refundOrderItems)
					.build());
		});
		And("[交货单校验超发08]SAP发送退货交货单数据如下", (DataTable dataTable) -> {
			returnDeliveryNumber = "220" + RandomUtil.randomNumbers(7);
			returnMaterialNumber = "440" + RandomUtil.randomNumbers(7);
			List<OrderDeliveryDTO> orderDeliveryDTOS = new ArrayList<>();
			dataTable.asMaps().stream().forEach(
					caseData -> {
						String returnOrderItem = caseData.get("returnOrderItem");
						String deliveryBatchNum = caseData.get("deliveryBatchNum");
						orderDeliveryDTOS.add(OrderDeliveryDTO.builder()
								.date(createDate)
								.deliverNo(returnDeliveryNumber)
								.orderItem(returnOrderItem)
								.batchNum(deliveryBatchNum)
								.eccid(returnEccId)
								.orderItemQuantityTotal(deliveryBatchNum)
								.orderNo(orderNo)
								.refundOrderNo(returnOrderNo)
								.materialNumber(returnMaterialNumber)
								.build());
					}
			);
			orderApiDelivery01.createRefundDeliveryShipment(orderDeliveryDTOS)
					.then()
					.body(Matchers.containsString("<data>true</data>"));
		});
		Then("[交货单校验超发08]退货交货单数据入库数据如下", (DataTable dataTable) -> {
			List<Map<String, String>> caseDataMap = new ArrayList<>();
			dataTable.asMaps().forEach(caseData -> {
				Map<String, String> processedData = new HashMap<>();
				processedData.put("item_id", Optional.ofNullable(caseData.get("orderItem")).orElse(""));
				processedData.put("num", Optional.ofNullable(caseData.get("batchNum")).orElse(""));
				processedData.put("bwart", Optional.ofNullable(caseData.get("type")).orElse(""));
				processedData.put("status", Optional.ofNullable(caseData.get("status")).orElse(""));
				caseDataMap.add(processedData);
			});
			List<Map<String, Object>> sqlResult = deliveryBatchSelectData01.queryOrderDeliveryData01(orderNo);
			deliveryCheckOrderManagement01.checkDelivery(caseDataMap, sqlResult);
		});
	}

	private void scenario09() {
		Given("[交货单校验超发09]当前存在如下客户信息", (DataTable dataTable) -> {
			createDate = new SimpleDateFormat("yyyyMMdd").format(System.currentTimeMillis());
			createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis());
			domainDataTable = dataTable;
			domainDataTable.asMaps().forEach(caseData -> {
				saleOrgSapCode = Optional.ofNullable(caseData.get("saleOrgSapCode")).orElse("");
				customerSapCode = Optional.ofNullable(caseData.get("customerSapCode")).orElse("");
				regionCode = Optional.ofNullable(caseData.get("regionCode")).orElse("");
				regionName = Optional.ofNullable(caseData.get("regionName")).orElse("");
			});
		});
		When("[交货单校验超发09]创建订单且下发SAP至待发货", () -> {
			eccId = "110" + RandomUtil.randomNumbers(7);
			// 创建订单
			orderApiDelivery01.copyOrderSubmit(customerSapCode);
			// 查看订单号
			orderNo = orderApiOrderManagement01.selectOrderInfo(OrderQueryPageByConditionDTO.builder()
					.saleOrgSapCode("1017,1401,2010,2820,2810,2020,2060,2610,2070")
					.customerCodeOrName(customerSapCode)
					.pageNum("1")
					.pageSize("10")
					.state("PENDING_BUSINESS_REVIEW")
					.build());
			// 订单商务审核
			orderApiDelivery01.orderApprove(OrderApproveShippingInfoDTO.builder()
					.orderNo(orderNo)
					.paymentMethod("PRE_PAYMENT")
					.shippingInfo(JSONUtil.parseObj(ShippingEnum.fetchOrderBody(customerSapCode)))
					.build());
			// 订单下发OMS
			orderApiDelivery01.orderSendToOmsTask();
			// SAP返回ECCID
			orderApiDelivery01.omsReturnPositiveEccId(OmsReturnPositiveEccIdDTO.builder()
					.eccMessage("自动化审核通过")
					.eccOrderId(eccId)
					.erpStatus("S")
					.originalOrderId(orderNo)
					.build());
		});
		And("[交货单校验超发09]SAP发送交货单数据如下", (DataTable dataTable) -> {
			deliveryNumber = "220" + RandomUtil.randomNumbers(7);
			materialNumber = "440" + RandomUtil.randomNumbers(7);
			List<OrderDeliveryDTO> orderDeliveryDTOS = new ArrayList<>();
			dataTable.asMaps().stream().forEach(
					caseData -> {
						String orderItem = caseData.get("orderItem");
						batchNum = caseData.get("batchNum");
						orderDeliveryDTOS.add(OrderDeliveryDTO.builder()
								.date(createDate)
								.deliverNo(deliveryNumber)
								.orderItem(orderItem)
								.batchNum(batchNum)
								.eccid(eccId)
								.orderItemQuantityTotal(batchNum)
								.orderNo(orderNo)
								.materialNumber(materialNumber)
								.build());
					}
			);
			orderApiDelivery01.createPositiveDeliveryShipment(orderDeliveryDTOS)
					.then()
					.body(Matchers.containsString("<data>true</data>"));
		});
		Then("[交货单校验超发09]交货单过滤重复数据正常入库", (DataTable dataTable) -> {
			List<Map<String, String>> caseDataMap = new ArrayList<>();
			dataTable.asMaps().forEach(caseData -> {
				Map<String, String> processedData = new HashMap<>();
				processedData.put("item_id", Optional.ofNullable(caseData.get("orderItem")).orElse(""));
				processedData.put("num", Optional.ofNullable(caseData.get("batchNum")).orElse(""));
				processedData.put("bwart", Optional.ofNullable(caseData.get("type")).orElse(""));
				processedData.put("status", Optional.ofNullable(caseData.get("status")).orElse(""));
				caseDataMap.add(processedData);
			});
			List<Map<String, Object>> sqlResult = deliveryBatchSelectData01.queryOrderDeliveryData01(orderNo);
			deliveryCheckOrderManagement01.checkDelivery(caseDataMap, sqlResult);
		});
		When("[交货单校验超发09]创建退货订单且下发SAP", (DataTable dataTable) -> {
			List<ReturnOrderDetailRespDTO> returnOrderDetailRespDTOS = new ArrayList<>();
			List<AcceptDetailReqDTO> acceptDetailReqDTOS = new ArrayList<>();
			List<String> refundOrderItems = new ArrayList<>();
			dataTable.asMaps().forEach(caseData -> {
				returnOrderItem = Optional.ofNullable(caseData.get("returnOrderItem")).orElse("");
				String sku = Optional.ofNullable(caseData.get("sku")).orElse("");
				returnBatchNum = Optional.ofNullable(caseData.get("returnBatchNum")).orElse("");
				String unitPrice = new BigDecimal(caseData.get("unitPrice")).setScale(2, RoundingMode.HALF_UP).toString();
				String returnOrderLinePrice = NumberUtil.mul(unitPrice, returnBatchNum).negate().toString();
				itemId = returnOrderBatchSelectData01.queryOrderItemIdData01(orderNo, returnOrderItem);
				whsePostReceiptId = returnOrderBatchSelectData01.queryDeliveryIdData01(materialNumber);
				refundOrderItems.add(returnOrderItem);
				returnOrderDetailRespDTOS.add(ReturnOrderDetailRespDTO.builder()
						.orderNo(orderNo)
						.itemLine(returnOrderItem)
						.itemId(itemId)
						.whsePostReceiptId(whsePostReceiptId)
						.orderWideDiscountSubject("PT00")
						.sku(sku)
						.description(ProductEnum.fetchProductSpec(sku))
						.batchNum("202504")
						.location("1201")
						.isReturnable(batchNum)
						.oriQuantity(batchNum)
						.quantity(returnBatchNum)
						.unitPrice(unitPrice)
						.preDiscountUnitPrice(unitPrice)
						.rebateTotal("0")
						.returnOrderLinePrice(returnOrderLinePrice)
						.selected("true")
						.build());
			});
			orderApiReturnOrderManagement01.returnOrderSubmit(ReturnOrderRespDTO.builder()
					.orderNo(orderNo)
					.reason("接口自动化")
					.isReceiveDrugInvoice("0")
					.isResendDrug("0")
					.isReturnPurveyorDrug("")
					.returnOrderDetailRespDTOList(returnOrderDetailRespDTOS)
					.build());
			returnOrderId = orderApiReturnOrderManagement01.returnOrderSelectId(ReturnOrderSelectIdDTO.builder()
					.saleOrgSapCode("1017,1401,2010,2820,2810,2020,2060,2610,2070")
					.regionCode(regionCode)
					.customerInfo(CustomerInfoDTO.builder()
							.code(CompanyEnum.fetchZtCode(customerSapCode))
							.name(CompanyEnum.fetchName(customerSapCode))
							.label("天津旭海津药医药有限公司-0010020753")
							.value(CompanyEnum.fetchZtCode(customerSapCode))
							.sapCode(customerSapCode)
							.crmCode(CompanyEnum.fetchCrmCode(customerSapCode))
							.regionCode(regionCode)
							.regionName(regionName)
							.build())
					.orderNo(orderNo)
					.customerCodeOrName(customerSapCode)
					.pageNum("1")
					.pageSize("10")
					.build());
			returnItemId = returnOrderBatchSelectData01.queryReturnOrderDetailIdData01(returnOrderId);
			acceptDetailReqDTOS.add(AcceptDetailReqDTO.builder()
					.quantity(returnBatchNum)
					.returnItemId(returnItemId)
					.itemLine(returnOrderItem)
					.orderNo(orderNo)
					.itemId(itemId)
					.whsePostReceiptId(whsePostReceiptId)
					.build());
			orderApiReturnOrderManagement01.returnOrderBusinessReview(returnOrderId);
			orderApiReturnOrderManagement01.returnOrderManualReview(returnOrderId);
			orderApiReturnOrderManagement01.returnOrderAcceptReview(ReturnOrderAcceptRespDTO.builder()
					.returnOrderId(returnOrderId)
					.acceptDetailReqDTOList(acceptDetailReqDTOS)
					.build());
			returnOrderNo = returnOrderBatchSelectData01.queryReturnOrderNoData01(returnOrderId);
			returnEccId = "110" + RandomUtil.randomNumbers(7);
			orderApiReturnOrderManagement01.omsReturnReverseEccId(OmsReturnReverseEccIdDTO.builder()
					.orderId(orderNo)
					.orderReturnId(returnOrderNo)
					.eccOrderID(returnEccId)
					.eccMessage("自动化审核通过")
					.erpStatus("S")
					.itemIds(refundOrderItems)
					.build());
		});
		And("[交货单校验超发09]SAP发送退货交货单数据如下", (DataTable dataTable) -> {
			returnDeliveryNumber = "220" + RandomUtil.randomNumbers(7);
			returnMaterialNumber = "440" + RandomUtil.randomNumbers(7);
			List<OrderDeliveryDTO> orderDeliveryDTOS = new ArrayList<>();
			dataTable.asMaps().stream().forEach(
					caseData -> {
						String returnOrderItem = caseData.get("returnOrderItem");
						String deliveryBatchNum = caseData.get("deliveryBatchNum");
						orderDeliveryDTOS.add(OrderDeliveryDTO.builder()
								.date(createDate)
								.deliverNo(returnDeliveryNumber)
								.orderItem(returnOrderItem)
								.batchNum(deliveryBatchNum)
								.eccid(returnEccId)
								.orderItemQuantityTotal(deliveryBatchNum)
								.orderNo(orderNo)
								.refundOrderNo(returnOrderNo)
								.materialNumber(returnMaterialNumber)
								.build());
					}
			);
			orderApiDelivery01.createRefundDeliveryShipment(orderDeliveryDTOS)
					.then()
					.body(Matchers.containsString("<data>true</data>"));
		});
		Then("[交货单校验超发09]退货交货单数据入库数据如下", (DataTable dataTable) -> {
			List<Map<String, String>> caseDataMap = new ArrayList<>();
			dataTable.asMaps().forEach(caseData -> {
				Map<String, String> processedData = new HashMap<>();
				processedData.put("item_id", Optional.ofNullable(caseData.get("orderItem")).orElse(""));
				processedData.put("num", Optional.ofNullable(caseData.get("batchNum")).orElse(""));
				processedData.put("bwart", Optional.ofNullable(caseData.get("type")).orElse(""));
				processedData.put("status", Optional.ofNullable(caseData.get("status")).orElse(""));
				caseDataMap.add(processedData);
			});
			List<Map<String, Object>> sqlResult = deliveryBatchSelectData01.queryOrderDeliveryData01(orderNo);
			deliveryCheckOrderManagement01.checkDelivery(caseDataMap, sqlResult);
		});
	}

	private void scenario10() {
		Given("[交货单校验超发10]当前存在如下客户信息", (DataTable dataTable) -> {
			createDate = new SimpleDateFormat("yyyyMMdd").format(System.currentTimeMillis());
			createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis());
			domainDataTable = dataTable;
			domainDataTable.asMaps().forEach(caseData -> {
				saleOrgSapCode = Optional.ofNullable(caseData.get("saleOrgSapCode")).orElse("");
				customerSapCode = Optional.ofNullable(caseData.get("customerSapCode")).orElse("");
				regionCode = Optional.ofNullable(caseData.get("regionCode")).orElse("");
				regionName = Optional.ofNullable(caseData.get("regionName")).orElse("");
			});
		});
		When("[交货单校验超发10]创建订单且下发SAP至待发货", () -> {
			eccId = "110" + RandomUtil.randomNumbers(7);
			// 创建订单
			orderApiDelivery01.copyOrderSubmit(customerSapCode);
			// 查看订单号
			orderNo = orderApiOrderManagement01.selectOrderInfo(OrderQueryPageByConditionDTO.builder()
					.saleOrgSapCode("1017,1401,2010,2820,2810,2020,2060,2610,2070")
					.customerCodeOrName(customerSapCode)
					.pageNum("1")
					.pageSize("10")
					.state("PENDING_BUSINESS_REVIEW")
					.build());
			// 订单商务审核
			orderApiDelivery01.orderApprove(OrderApproveShippingInfoDTO.builder()
					.orderNo(orderNo)
					.paymentMethod("PRE_PAYMENT")
					.shippingInfo(JSONUtil.parseObj(ShippingEnum.fetchOrderBody(customerSapCode)))
					.build());
			// 订单下发OMS
			orderApiDelivery01.orderSendToOmsTask();
			// SAP返回ECCID
			orderApiDelivery01.omsReturnPositiveEccId(OmsReturnPositiveEccIdDTO.builder()
					.eccMessage("自动化审核通过")
					.eccOrderId(eccId)
					.erpStatus("S")
					.originalOrderId(orderNo)
					.build());
		});
		And("[交货单校验超发10]SAP发送交货单数据如下", (DataTable dataTable) -> {
			deliveryNumber = "220" + RandomUtil.randomNumbers(7);
			materialNumber = "440" + RandomUtil.randomNumbers(7);
			List<OrderDeliveryDTO> orderDeliveryDTOS = new ArrayList<>();
			dataTable.asMaps().stream().forEach(
					caseData -> {
						String orderItem = caseData.get("orderItem");
						batchNum = caseData.get("batchNum");
						orderDeliveryDTOS.add(OrderDeliveryDTO.builder()
								.date(createDate)
								.deliverNo(deliveryNumber)
								.orderItem(orderItem)
								.batchNum(batchNum)
								.eccid(eccId)
								.orderItemQuantityTotal(batchNum)
								.orderNo(orderNo)
								.materialNumber(materialNumber)
								.build());
					}
			);
			orderApiDelivery01.createPositiveDeliveryShipment(orderDeliveryDTOS)
					.then()
					.body(Matchers.containsString("<data>true</data>"));
		});
		Then("[交货单校验超发10]交货单过滤重复数据正常入库", (DataTable dataTable) -> {
			List<Map<String, String>> caseDataMap = new ArrayList<>();
			dataTable.asMaps().forEach(caseData -> {
				Map<String, String> processedData = new HashMap<>();
				processedData.put("item_id", Optional.ofNullable(caseData.get("orderItem")).orElse(""));
				processedData.put("num", Optional.ofNullable(caseData.get("batchNum")).orElse(""));
				processedData.put("bwart", Optional.ofNullable(caseData.get("type")).orElse(""));
				processedData.put("status", Optional.ofNullable(caseData.get("status")).orElse(""));
				caseDataMap.add(processedData);
			});
			List<Map<String, Object>> sqlResult = deliveryBatchSelectData01.queryOrderDeliveryData01(orderNo);
			deliveryCheckOrderManagement01.checkDelivery(caseDataMap, sqlResult);
		});
		When("[交货单校验超发10]创建退货订单且下发SAP", (DataTable dataTable) -> {
			List<ReturnOrderDetailRespDTO> returnOrderDetailRespDTOS = new ArrayList<>();
			List<AcceptDetailReqDTO> acceptDetailReqDTOS = new ArrayList<>();
			List<String> refundOrderItems = new ArrayList<>();
			dataTable.asMaps().forEach(caseData -> {
				returnOrderItem = Optional.ofNullable(caseData.get("returnOrderItem")).orElse("");
				String sku = Optional.ofNullable(caseData.get("sku")).orElse("");
				returnBatchNum = Optional.ofNullable(caseData.get("returnBatchNum")).orElse("");
				String unitPrice = new BigDecimal(caseData.get("unitPrice")).setScale(2, RoundingMode.HALF_UP).toString();
				String returnOrderLinePrice = NumberUtil.mul(unitPrice, returnBatchNum).negate().toString();
				itemId = returnOrderBatchSelectData01.queryOrderItemIdData01(orderNo, returnOrderItem);
				whsePostReceiptId = returnOrderBatchSelectData01.queryDeliveryIdData01(materialNumber);
				refundOrderItems.add(returnOrderItem);
				returnOrderDetailRespDTOS.add(ReturnOrderDetailRespDTO.builder()
						.orderNo(orderNo)
						.itemLine(returnOrderItem)
						.itemId(itemId)
						.whsePostReceiptId(whsePostReceiptId)
						.orderWideDiscountSubject("PT00")
						.sku(sku)
						.description(ProductEnum.fetchProductSpec(sku))
						.batchNum("202504")
						.location("1201")
						.isReturnable(batchNum)
						.oriQuantity(batchNum)
						.quantity(returnBatchNum)
						.unitPrice(unitPrice)
						.preDiscountUnitPrice(unitPrice)
						.rebateTotal("0")
						.returnOrderLinePrice(returnOrderLinePrice)
						.selected("true")
						.build());
			});
			orderApiReturnOrderManagement01.returnOrderSubmit(ReturnOrderRespDTO.builder()
					.orderNo(orderNo)
					.reason("接口自动化")
					.isReceiveDrugInvoice("0")
					.isResendDrug("0")
					.isReturnPurveyorDrug("")
					.returnOrderDetailRespDTOList(returnOrderDetailRespDTOS)
					.build());
			returnOrderId = orderApiReturnOrderManagement01.returnOrderSelectId(ReturnOrderSelectIdDTO.builder()
					.saleOrgSapCode("1017,1401,2010,2820,2810,2020,2060,2610,2070")
					.regionCode(regionCode)
					.customerInfo(CustomerInfoDTO.builder()
							.code(CompanyEnum.fetchZtCode(customerSapCode))
							.name(CompanyEnum.fetchName(customerSapCode))
							.label("天津旭海津药医药有限公司-0010020753")
							.value(CompanyEnum.fetchZtCode(customerSapCode))
							.sapCode(customerSapCode)
							.crmCode(CompanyEnum.fetchCrmCode(customerSapCode))
							.regionCode(regionCode)
							.regionName(regionName)
							.build())
					.orderNo(orderNo)
					.customerCodeOrName(customerSapCode)
					.pageNum("1")
					.pageSize("10")
					.build());
			returnItemId = returnOrderBatchSelectData01.queryReturnOrderDetailIdData01(returnOrderId);
			acceptDetailReqDTOS.add(AcceptDetailReqDTO.builder()
					.quantity(returnBatchNum)
					.returnItemId(returnItemId)
					.itemLine(returnOrderItem)
					.orderNo(orderNo)
					.itemId(itemId)
					.whsePostReceiptId(whsePostReceiptId)
					.build());
			orderApiReturnOrderManagement01.returnOrderBusinessReview(returnOrderId);
			orderApiReturnOrderManagement01.returnOrderManualReview(returnOrderId);
			orderApiReturnOrderManagement01.returnOrderAcceptReview(ReturnOrderAcceptRespDTO.builder()
					.returnOrderId(returnOrderId)
					.acceptDetailReqDTOList(acceptDetailReqDTOS)
					.build());
			returnOrderNo = returnOrderBatchSelectData01.queryReturnOrderNoData01(returnOrderId);
			returnEccId = "110" + RandomUtil.randomNumbers(7);
			orderApiReturnOrderManagement01.omsReturnReverseEccId(OmsReturnReverseEccIdDTO.builder()
					.orderId(orderNo)
					.orderReturnId(returnOrderNo)
					.eccOrderID(returnEccId)
					.eccMessage("自动化审核通过")
					.erpStatus("S")
					.itemIds(refundOrderItems)
					.build());
		});
		And("[交货单校验超发10]SAP发送退货交货单数据如下", (DataTable dataTable) -> {
			returnDeliveryNumber = "220" + RandomUtil.randomNumbers(7);
			returnMaterialNumber = "440" + RandomUtil.randomNumbers(7);
			List<OrderDeliveryDTO> orderDeliveryDTOS = new ArrayList<>();
			dataTable.asMaps().stream().forEach(
					caseData -> {
						String returnOrderItem = caseData.get("returnOrderItem");
						String deliveryBatchNum = caseData.get("deliveryBatchNum");
						orderDeliveryDTOS.add(OrderDeliveryDTO.builder()
								.date(createDate)
								.deliverNo(returnDeliveryNumber)
								.orderItem(returnOrderItem)
								.batchNum(deliveryBatchNum)
								.eccid(returnEccId)
								.orderItemQuantityTotal(deliveryBatchNum)
								.orderNo(orderNo)
								.refundOrderNo(returnOrderNo)
								.materialNumber(returnMaterialNumber)
								.build());
					}
			);
			orderApiDelivery01.createRefundDeliveryShipment(orderDeliveryDTOS)
					.then()
					.body(Matchers.containsString("<data>true</data>"));
		});
		And("[交货单校验超发10]SAP再次发送退货交货单数据如下", (DataTable dataTable) -> {
			returnDeliveryNumber = "220" + RandomUtil.randomNumbers(7);
			returnMaterialNumber = "440" + RandomUtil.randomNumbers(7);
			List<OrderDeliveryDTO> orderDeliveryDTOS = new ArrayList<>();
			dataTable.asMaps().stream().forEach(
					caseData -> {
						String returnOrderItem = caseData.get("returnOrderItem");
						String deliveryBatchNum = caseData.get("deliveryBatchNum");
						orderDeliveryDTOS.add(OrderDeliveryDTO.builder()
								.date(createDate)
								.deliverNo(returnDeliveryNumber)
								.orderItem(returnOrderItem)
								.batchNum(deliveryBatchNum)
								.eccid(returnEccId)
								.orderItemQuantityTotal(deliveryBatchNum)
								.orderNo(orderNo)
								.refundOrderNo(returnOrderNo)
								.materialNumber(returnMaterialNumber)
								.build());
					}
			);
			orderApiDelivery01.createRefundDeliveryShipment(orderDeliveryDTOS)
					.then()
					.body(Matchers.containsString("<data>false</data>"));
		});
		Then("[交货单校验超发10]退货交货单数据入库数据如下", (DataTable dataTable) -> {
			List<Map<String, String>> caseDataMap = new ArrayList<>();
			dataTable.asMaps().forEach(caseData -> {
				Map<String, String> processedData = new HashMap<>();
				processedData.put("item_id", Optional.ofNullable(caseData.get("orderItem")).orElse(""));
				processedData.put("num", Optional.ofNullable(caseData.get("batchNum")).orElse(""));
				processedData.put("bwart", Optional.ofNullable(caseData.get("type")).orElse(""));
				processedData.put("status", Optional.ofNullable(caseData.get("status")).orElse(""));
				caseDataMap.add(processedData);
			});
			List<Map<String, Object>> sqlResult = deliveryBatchSelectData01.queryOrderDeliveryData01(orderNo);
			deliveryCheckOrderManagement01.checkDelivery(caseDataMap, sqlResult);
		});
	}

	private void scenario11() {
		Given("[交货单校验超发11]当前存在如下客户信息", (DataTable dataTable) -> {
			createDate = new SimpleDateFormat("yyyyMMdd").format(System.currentTimeMillis());
			createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis());
			domainDataTable = dataTable;
			domainDataTable.asMaps().forEach(caseData -> {
				saleOrgSapCode = Optional.ofNullable(caseData.get("saleOrgSapCode")).orElse("");
				customerSapCode = Optional.ofNullable(caseData.get("customerSapCode")).orElse("");
				regionCode = Optional.ofNullable(caseData.get("regionCode")).orElse("");
				regionName = Optional.ofNullable(caseData.get("regionName")).orElse("");
			});
		});
		When("[交货单校验超发11]创建订单且下发SAP至待发货", () -> {
			eccId = "110" + RandomUtil.randomNumbers(7);
			// 创建订单
			orderApiDelivery01.copyOrderSubmit(customerSapCode);
			// 查看订单号
			orderNo = orderApiOrderManagement01.selectOrderInfo(OrderQueryPageByConditionDTO.builder()
					.saleOrgSapCode("1017,1401,2010,2820,2810,2020,2060,2610,2070")
					.customerCodeOrName(customerSapCode)
					.pageNum("1")
					.pageSize("10")
					.state("PENDING_BUSINESS_REVIEW")
					.build());
			// 订单商务审核
			orderApiDelivery01.orderApprove(OrderApproveShippingInfoDTO.builder()
					.orderNo(orderNo)
					.paymentMethod("PRE_PAYMENT")
					.shippingInfo(JSONUtil.parseObj(ShippingEnum.fetchOrderBody(customerSapCode)))
					.build());
			// 订单下发OMS
			orderApiDelivery01.orderSendToOmsTask();
			// SAP返回ECCID
			orderApiDelivery01.omsReturnPositiveEccId(OmsReturnPositiveEccIdDTO.builder()
					.eccMessage("自动化审核通过")
					.eccOrderId(eccId)
					.erpStatus("S")
					.originalOrderId(orderNo)
					.build());
		});
		And("[交货单校验超发11]SAP发送交货单数据如下", (DataTable dataTable) -> {
			deliveryNumber = "220" + RandomUtil.randomNumbers(7);
			materialNumber = "440" + RandomUtil.randomNumbers(7);
			List<OrderDeliveryDTO> orderDeliveryDTOS = new ArrayList<>();
			dataTable.asMaps().stream().forEach(
					caseData -> {
						String orderItem = caseData.get("orderItem");
						batchNum = caseData.get("batchNum");
						orderDeliveryDTOS.add(OrderDeliveryDTO.builder()
								.date(createDate)
								.deliverNo(deliveryNumber)
								.orderItem(orderItem)
								.batchNum(batchNum)
								.eccid(eccId)
								.orderItemQuantityTotal(batchNum)
								.orderNo(orderNo)
								.materialNumber(materialNumber)
								.build());
					}
			);
			orderApiDelivery01.createPositiveDeliveryShipment(orderDeliveryDTOS)
					.then()
					.body(Matchers.containsString("<data>true</data>"));
		});
		Then("[交货单校验超发11]交货单过滤重复数据正常入库", (DataTable dataTable) -> {
			List<Map<String, String>> caseDataMap = new ArrayList<>();
			dataTable.asMaps().forEach(caseData -> {
				Map<String, String> processedData = new HashMap<>();
				processedData.put("item_id", Optional.ofNullable(caseData.get("orderItem")).orElse(""));
				processedData.put("num", Optional.ofNullable(caseData.get("batchNum")).orElse(""));
				processedData.put("bwart", Optional.ofNullable(caseData.get("type")).orElse(""));
				processedData.put("status", Optional.ofNullable(caseData.get("status")).orElse(""));
				caseDataMap.add(processedData);
			});
			List<Map<String, Object>> sqlResult = deliveryBatchSelectData01.queryOrderDeliveryData01(orderNo);
			deliveryCheckOrderManagement01.checkDelivery(caseDataMap, sqlResult);
		});
		When("[交货单校验超发11]创建退货订单且下发SAP", (DataTable dataTable) -> {
			List<ReturnOrderDetailRespDTO> returnOrderDetailRespDTOS = new ArrayList<>();
			List<AcceptDetailReqDTO> acceptDetailReqDTOS = new ArrayList<>();
			List<String> refundOrderItems = new ArrayList<>();
			dataTable.asMaps().forEach(caseData -> {
				returnOrderItem = Optional.ofNullable(caseData.get("returnOrderItem")).orElse("");
				String sku = Optional.ofNullable(caseData.get("sku")).orElse("");
				returnBatchNum = Optional.ofNullable(caseData.get("returnBatchNum")).orElse("");
				String unitPrice = new BigDecimal(caseData.get("unitPrice")).setScale(2, RoundingMode.HALF_UP).toString();
				String returnOrderLinePrice = NumberUtil.mul(unitPrice, returnBatchNum).negate().toString();
				itemId = returnOrderBatchSelectData01.queryOrderItemIdData01(orderNo, returnOrderItem);
				whsePostReceiptId = returnOrderBatchSelectData01.queryDeliveryIdData01(materialNumber);
				refundOrderItems.add(returnOrderItem);
				returnOrderDetailRespDTOS.add(ReturnOrderDetailRespDTO.builder()
						.orderNo(orderNo)
						.itemLine(returnOrderItem)
						.itemId(itemId)
						.whsePostReceiptId(whsePostReceiptId)
						.orderWideDiscountSubject("PT00")
						.sku(sku)
						.description(ProductEnum.fetchProductSpec(sku))
						.batchNum("202504")
						.location("1201")
						.isReturnable(batchNum)
						.oriQuantity(batchNum)
						.quantity(returnBatchNum)
						.unitPrice(unitPrice)
						.preDiscountUnitPrice(unitPrice)
						.rebateTotal("0")
						.returnOrderLinePrice(returnOrderLinePrice)
						.selected("true")
						.build());
			});
			orderApiReturnOrderManagement01.returnOrderSubmit(ReturnOrderRespDTO.builder()
					.orderNo(orderNo)
					.reason("接口自动化")
					.isReceiveDrugInvoice("0")
					.isResendDrug("0")
					.isReturnPurveyorDrug("")
					.returnOrderDetailRespDTOList(returnOrderDetailRespDTOS)
					.build());
			returnOrderId = orderApiReturnOrderManagement01.returnOrderSelectId(ReturnOrderSelectIdDTO.builder()
					.saleOrgSapCode("1017,1401,2010,2820,2810,2020,2060,2610,2070")
					.regionCode(regionCode)
					.customerInfo(CustomerInfoDTO.builder()
							.code(CompanyEnum.fetchZtCode(customerSapCode))
							.name(CompanyEnum.fetchName(customerSapCode))
							.label("天津旭海津药医药有限公司-0010020753")
							.value(CompanyEnum.fetchZtCode(customerSapCode))
							.sapCode(customerSapCode)
							.crmCode(CompanyEnum.fetchCrmCode(customerSapCode))
							.regionCode(regionCode)
							.regionName(regionName)
							.build())
					.orderNo(orderNo)
					.customerCodeOrName(customerSapCode)
					.pageNum("1")
					.pageSize("10")
					.build());
			returnItemId = returnOrderBatchSelectData01.queryReturnOrderDetailIdData01(returnOrderId);
			acceptDetailReqDTOS.add(AcceptDetailReqDTO.builder()
					.quantity(returnBatchNum)
					.returnItemId(returnItemId)
					.itemLine(returnOrderItem)
					.orderNo(orderNo)
					.itemId(itemId)
					.whsePostReceiptId(whsePostReceiptId)
					.build());
			orderApiReturnOrderManagement01.returnOrderBusinessReview(returnOrderId);
			orderApiReturnOrderManagement01.returnOrderManualReview(returnOrderId);
			orderApiReturnOrderManagement01.returnOrderAcceptReview(ReturnOrderAcceptRespDTO.builder()
					.returnOrderId(returnOrderId)
					.acceptDetailReqDTOList(acceptDetailReqDTOS)
					.build());
			returnOrderNo = returnOrderBatchSelectData01.queryReturnOrderNoData01(returnOrderId);
			returnEccId = "110" + RandomUtil.randomNumbers(7);
			orderApiReturnOrderManagement01.omsReturnReverseEccId(OmsReturnReverseEccIdDTO.builder()
					.orderId(orderNo)
					.orderReturnId(returnOrderNo)
					.eccOrderID(returnEccId)
					.eccMessage("自动化审核通过")
					.erpStatus("S")
					.itemIds(refundOrderItems)
					.build());
		});
		And("[交货单校验超发11]SAP发送退货交货单数据如下", (DataTable dataTable) -> {
			returnDeliveryNumber = "220" + RandomUtil.randomNumbers(7);
			returnMaterialNumber = "440" + RandomUtil.randomNumbers(7);
			List<OrderDeliveryDTO> orderDeliveryDTOS = new ArrayList<>();
			dataTable.asMaps().stream().forEach(
					caseData -> {
						String returnOrderItem = caseData.get("returnOrderItem");
						String deliveryBatchNum = caseData.get("deliveryBatchNum");
						orderDeliveryDTOS.add(OrderDeliveryDTO.builder()
								.date(createDate)
								.deliverNo(returnDeliveryNumber)
								.orderItem(returnOrderItem)
								.batchNum(deliveryBatchNum)
								.eccid(returnEccId)
								.orderItemQuantityTotal(deliveryBatchNum)
								.orderNo(orderNo)
								.refundOrderNo(returnOrderNo)
								.materialNumber(returnMaterialNumber)
								.build());
					}
			);
			orderApiDelivery01.createRefundDeliveryShipment(orderDeliveryDTOS)
					.then()
					.body(Matchers.containsString("<data>true</data>"));
		});
		And("[交货单校验超发11]SAP再次发送退货交货单数据如下", (DataTable dataTable) -> {
			returnDeliveryNumber = "220" + RandomUtil.randomNumbers(7);
			returnMaterialNumber = "440" + RandomUtil.randomNumbers(7);
			List<OrderDeliveryDTO> orderDeliveryDTOS = new ArrayList<>();
			dataTable.asMaps().stream().forEach(
					caseData -> {
						String returnOrderItem = caseData.get("returnOrderItem");
						String deliveryBatchNum = caseData.get("deliveryBatchNum");
						orderDeliveryDTOS.add(OrderDeliveryDTO.builder()
								.date(createDate)
								.deliverNo(returnDeliveryNumber)
								.orderItem(returnOrderItem)
								.batchNum(deliveryBatchNum)
								.eccid(returnEccId)
								.orderItemQuantityTotal(deliveryBatchNum)
								.orderNo(orderNo)
								.refundOrderNo(returnOrderNo)
								.materialNumber(returnMaterialNumber)
								.build());
					}
			);
			orderApiDelivery01.createRefundDeliveryShipment(orderDeliveryDTOS)
					.then()
					.body(Matchers.containsString("<data>true</data>"));
		});
		Then("[交货单校验超发11]退货交货单数据入库数据如下", (DataTable dataTable) -> {
			List<Map<String, String>> caseDataMap = new ArrayList<>();
			dataTable.asMaps().forEach(caseData -> {
				Map<String, String> processedData = new HashMap<>();
				processedData.put("item_id", Optional.ofNullable(caseData.get("orderItem")).orElse(""));
				processedData.put("num", Optional.ofNullable(caseData.get("batchNum")).orElse(""));
				processedData.put("bwart", Optional.ofNullable(caseData.get("type")).orElse(""));
				processedData.put("status", Optional.ofNullable(caseData.get("status")).orElse(""));
				caseDataMap.add(processedData);
			});
			List<Map<String, Object>> sqlResult = deliveryBatchSelectData01.queryOrderDeliveryData01(orderNo);
			deliveryCheckOrderManagement01.checkDelivery(caseDataMap, sqlResult);
		});
	}

	private void scenario12() {
		Given("[交货单校验超发12]当前存在如下客户信息", (DataTable dataTable) -> {
			createDate = new SimpleDateFormat("yyyyMMdd").format(System.currentTimeMillis());
			createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis());
			domainDataTable = dataTable;
			domainDataTable.asMaps().forEach(caseData -> {
				saleOrgSapCode = Optional.ofNullable(caseData.get("saleOrgSapCode")).orElse("");
				customerSapCode = Optional.ofNullable(caseData.get("customerSapCode")).orElse("");
				regionCode = Optional.ofNullable(caseData.get("regionCode")).orElse("");
				regionName = Optional.ofNullable(caseData.get("regionName")).orElse("");
			});
		});
		When("[交货单校验超发12]创建订单且下发SAP至待发货", () -> {
			eccId = "110" + RandomUtil.randomNumbers(7);
			// 创建订单
			orderApiDelivery01.copyOrderSubmit(customerSapCode);
			// 查看订单号
			orderNo = orderApiOrderManagement01.selectOrderInfo(OrderQueryPageByConditionDTO.builder()
					.saleOrgSapCode("1017,1401,2010,2820,2810,2020,2060,2610,2070")
					.customerCodeOrName(customerSapCode)
					.pageNum("1")
					.pageSize("10")
					.state("PENDING_BUSINESS_REVIEW")
					.build());
			// 订单商务审核
			orderApiDelivery01.orderApprove(OrderApproveShippingInfoDTO.builder()
					.orderNo(orderNo)
					.paymentMethod("PRE_PAYMENT")
					.shippingInfo(JSONUtil.parseObj(ShippingEnum.fetchOrderBody(customerSapCode)))
					.build());
			// 订单下发OMS
			orderApiDelivery01.orderSendToOmsTask();
			// SAP返回ECCID
			orderApiDelivery01.omsReturnPositiveEccId(OmsReturnPositiveEccIdDTO.builder()
					.eccMessage("自动化审核通过")
					.eccOrderId(eccId)
					.erpStatus("S")
					.originalOrderId(orderNo)
					.build());
		});
		And("[交货单校验超发12]SAP发送交货单数据如下", (DataTable dataTable) -> {
			deliveryNumber = "220" + RandomUtil.randomNumbers(7);
			materialNumber = "440" + RandomUtil.randomNumbers(7);
			List<OrderDeliveryDTO> orderDeliveryDTOS = new ArrayList<>();
			dataTable.asMaps().stream().forEach(
					caseData -> {
						String orderItem = caseData.get("orderItem");
						batchNum = caseData.get("batchNum");
						orderDeliveryDTOS.add(OrderDeliveryDTO.builder()
								.date(createDate)
								.deliverNo(deliveryNumber)
								.orderItem(orderItem)
								.batchNum(batchNum)
								.eccid(eccId)
								.orderItemQuantityTotal(batchNum)
								.orderNo(orderNo)
								.materialNumber(materialNumber)
								.build());
					}
			);
			orderApiDelivery01.createPositiveDeliveryShipment(orderDeliveryDTOS)
					.then()
					.body(Matchers.containsString("<data>true</data>"));
		});
		Then("[交货单校验超发12]交货单过滤重复数据正常入库", (DataTable dataTable) -> {
			List<Map<String, String>> caseDataMap = new ArrayList<>();
			dataTable.asMaps().forEach(caseData -> {
				Map<String, String> processedData = new HashMap<>();
				processedData.put("item_id", Optional.ofNullable(caseData.get("orderItem")).orElse(""));
				processedData.put("num", Optional.ofNullable(caseData.get("batchNum")).orElse(""));
				processedData.put("bwart", Optional.ofNullable(caseData.get("type")).orElse(""));
				processedData.put("status", Optional.ofNullable(caseData.get("status")).orElse(""));
				caseDataMap.add(processedData);
			});
			List<Map<String, Object>> sqlResult = deliveryBatchSelectData01.queryOrderDeliveryData01(orderNo);
			deliveryCheckOrderManagement01.checkDelivery(caseDataMap, sqlResult);
		});
		When("[交货单校验超发12]创建退货订单且下发SAP", (DataTable dataTable) -> {
			List<ReturnOrderDetailRespDTO> returnOrderDetailRespDTOS = new ArrayList<>();
			List<AcceptDetailReqDTO> acceptDetailReqDTOS = new ArrayList<>();
			List<String> refundOrderItems = new ArrayList<>();
			dataTable.asMaps().forEach(caseData -> {
				returnOrderItem = Optional.ofNullable(caseData.get("returnOrderItem")).orElse("");
				String sku = Optional.ofNullable(caseData.get("sku")).orElse("");
				returnBatchNum = Optional.ofNullable(caseData.get("returnBatchNum")).orElse("");
				String unitPrice = new BigDecimal(caseData.get("unitPrice")).setScale(2, RoundingMode.HALF_UP).toString();
				String returnOrderLinePrice = NumberUtil.mul(unitPrice, returnBatchNum).negate().toString();
				itemId = returnOrderBatchSelectData01.queryOrderItemIdData01(orderNo, returnOrderItem);
				whsePostReceiptId = returnOrderBatchSelectData01.queryDeliveryIdData01(materialNumber);
				refundOrderItems.add(returnOrderItem);
				returnOrderDetailRespDTOS.add(ReturnOrderDetailRespDTO.builder()
						.orderNo(orderNo)
						.itemLine(returnOrderItem)
						.itemId(itemId)
						.whsePostReceiptId(whsePostReceiptId)
						.orderWideDiscountSubject("PT00")
						.sku(sku)
						.description(ProductEnum.fetchProductSpec(sku))
						.batchNum("202504")
						.location("1201")
						.isReturnable(batchNum)
						.oriQuantity(batchNum)
						.quantity(returnBatchNum)
						.unitPrice(unitPrice)
						.preDiscountUnitPrice(unitPrice)
						.rebateTotal("0")
						.returnOrderLinePrice(returnOrderLinePrice)
						.selected("true")
						.build());
			});
			orderApiReturnOrderManagement01.returnOrderSubmit(ReturnOrderRespDTO.builder()
					.orderNo(orderNo)
					.reason("接口自动化")
					.isReceiveDrugInvoice("0")
					.isResendDrug("0")
					.isReturnPurveyorDrug("")
					.returnOrderDetailRespDTOList(returnOrderDetailRespDTOS)
					.build());
			returnOrderId = orderApiReturnOrderManagement01.returnOrderSelectId(ReturnOrderSelectIdDTO.builder()
					.saleOrgSapCode("1017,1401,2010,2820,2810,2020,2060,2610,2070")
					.regionCode(regionCode)
					.customerInfo(CustomerInfoDTO.builder()
							.code(CompanyEnum.fetchZtCode(customerSapCode))
							.name(CompanyEnum.fetchName(customerSapCode))
							.label("天津旭海津药医药有限公司-0010020753")
							.value(CompanyEnum.fetchZtCode(customerSapCode))
							.sapCode(customerSapCode)
							.crmCode(CompanyEnum.fetchCrmCode(customerSapCode))
							.regionCode(regionCode)
							.regionName(regionName)
							.build())
					.orderNo(orderNo)
					.customerCodeOrName(customerSapCode)
					.pageNum("1")
					.pageSize("10")
					.build());
			returnItemId = returnOrderBatchSelectData01.queryReturnOrderDetailIdData01(returnOrderId);
			acceptDetailReqDTOS.add(AcceptDetailReqDTO.builder()
					.quantity(returnBatchNum)
					.returnItemId(returnItemId)
					.itemLine(returnOrderItem)
					.orderNo(orderNo)
					.itemId(itemId)
					.whsePostReceiptId(whsePostReceiptId)
					.build());
			orderApiReturnOrderManagement01.returnOrderBusinessReview(returnOrderId);
			orderApiReturnOrderManagement01.returnOrderManualReview(returnOrderId);
			orderApiReturnOrderManagement01.returnOrderAcceptReview(ReturnOrderAcceptRespDTO.builder()
					.returnOrderId(returnOrderId)
					.acceptDetailReqDTOList(acceptDetailReqDTOS)
					.build());
			returnOrderNo = returnOrderBatchSelectData01.queryReturnOrderNoData01(returnOrderId);
			returnEccId = "110" + RandomUtil.randomNumbers(7);
			orderApiReturnOrderManagement01.omsReturnReverseEccId(OmsReturnReverseEccIdDTO.builder()
					.orderId(orderNo)
					.orderReturnId(returnOrderNo)
					.eccOrderID(returnEccId)
					.eccMessage("自动化审核通过")
					.erpStatus("S")
					.itemIds(refundOrderItems)
					.build());
		});
		And("[交货单校验超发12]SAP发送退货交货单数据如下", (DataTable dataTable) -> {
			returnDeliveryNumber = "220" + RandomUtil.randomNumbers(7);
			returnMaterialNumber = "440" + RandomUtil.randomNumbers(7);
			List<OrderDeliveryDTO> orderDeliveryDTOS = new ArrayList<>();
			dataTable.asMaps().stream().forEach(
					caseData -> {
						String returnOrderItem = caseData.get("returnOrderItem");
						String deliveryBatchNum = caseData.get("deliveryBatchNum");
						orderDeliveryDTOS.add(OrderDeliveryDTO.builder()
								.date(createDate)
								.deliverNo(returnDeliveryNumber)
								.orderItem(returnOrderItem)
								.batchNum(deliveryBatchNum)
								.eccid(returnEccId)
								.orderItemQuantityTotal(deliveryBatchNum)
								.orderNo(orderNo)
								.refundOrderNo(returnOrderNo)
								.materialNumber(returnMaterialNumber)
								.build());
					}
			);
			orderApiDelivery01.createRefundDeliveryShipment(orderDeliveryDTOS)
					.then()
					.body(Matchers.containsString("<data>true</data>"));
		});
		And("[交货单校验超发12]SAP再次发送退货交货单数据如下", (DataTable dataTable) -> {
			returnDeliveryNumber = "220" + RandomUtil.randomNumbers(7);
			returnMaterialNumber = "440" + RandomUtil.randomNumbers(7);
			List<OrderDeliveryDTO> orderDeliveryDTOS = new ArrayList<>();
			dataTable.asMaps().stream().forEach(
					caseData -> {
						String returnOrderItem = caseData.get("returnOrderItem");
						String deliveryBatchNum = caseData.get("deliveryBatchNum");
						orderDeliveryDTOS.add(OrderDeliveryDTO.builder()
								.date(createDate)
								.deliverNo(returnDeliveryNumber)
								.orderItem(returnOrderItem)
								.batchNum(deliveryBatchNum)
								.eccid(returnEccId)
								.orderItemQuantityTotal(deliveryBatchNum)
								.orderNo(orderNo)
								.refundOrderNo(returnOrderNo)
								.materialNumber(returnMaterialNumber)
								.build());
					}
			);
			orderApiDelivery01.createRefundDeliveryShipment(orderDeliveryDTOS)
					.then()
					.body(Matchers.containsString("<data>true</data>"));
		});
		Then("[交货单校验超发12]退货交货单数据入库数据如下", (DataTable dataTable) -> {
			List<Map<String, String>> caseDataMap = new ArrayList<>();
			dataTable.asMaps().forEach(caseData -> {
				Map<String, String> processedData = new HashMap<>();
				processedData.put("item_id", Optional.ofNullable(caseData.get("orderItem")).orElse(""));
				processedData.put("num", Optional.ofNullable(caseData.get("batchNum")).orElse(""));
				processedData.put("bwart", Optional.ofNullable(caseData.get("type")).orElse(""));
				processedData.put("status", Optional.ofNullable(caseData.get("status")).orElse(""));
				caseDataMap.add(processedData);
			});
			List<Map<String, Object>> sqlResult = deliveryBatchSelectData01.queryOrderDeliveryData01(orderNo);
			deliveryCheckOrderManagement01.checkDelivery(caseDataMap, sqlResult);
		});
	}

}
