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
 * @Date 2025/04/29
 * @Description
 */

@SuppressWarnings("all")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class DeliveryVerifySameDataFilterSpec implements En {

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

	public DeliveryVerifySameDataFilterSpec() {
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
		Given("[交货单校验相同过滤01]当前存在如下客户信息", (DataTable dataTable) -> {
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
		When("[交货单校验相同过滤01]创建订单且下发SAP至待发货", () -> {
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
		And("[交货单校验相同过滤01]SAP发送交货单数据如下", (DataTable dataTable) -> {
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
		Then("[交货单校验相同过滤01]交货单过滤重复数据正常入库", (DataTable dataTable) -> {
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

	private void scenario02() {
		Given("[交货单校验相同过滤02]当前存在如下客户信息", (DataTable dataTable) -> {
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
		When("[交货单校验相同过滤02]创建订单且下发SAP至待发货", () -> {
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
		And("[交货单校验相同过滤02]SAP发送交货单数据如下", (DataTable dataTable) -> {
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
		Then("[交货单校验相同过滤02]交货单过滤重复数据正常入库", (DataTable dataTable) -> {
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
		Given("[交货单校验相同过滤03]当前存在如下客户信息", (DataTable dataTable) -> {
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
		When("[交货单校验相同过滤03]创建订单且下发SAP至待发货", () -> {
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
		And("[交货单校验相同过滤03]SAP发送交货单数据如下", (DataTable dataTable) -> {
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
		And("[交货单校验相同过滤03]SAP发送交货单重复数据如下", (DataTable dataTable) -> {
			List<OrderDeliveryDTO> orderDeliveryDTOS = new ArrayList<>();
			dataTable.asMaps().stream().forEach(
					caseData -> {
						String orderItem = caseData.get("orderItem");
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
		Then("[交货单校验相同过滤03]交货单过滤重复数据正常入库", (DataTable dataTable) -> {
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
		Given("[交货单校验相同过滤04]当前存在如下客户信息", (DataTable dataTable) -> {
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
		When("[交货单校验相同过滤04]创建订单且下发SAP至待发货", () -> {
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
		And("[交货单校验相同过滤04]SAP发送交货单数据如下", (DataTable dataTable) -> {
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
		And("[交货单校验相同过滤04]SAP发送交货单10行冲销数据", (DataTable dataTable) -> {
			writeOffDeliveryNumber = "220" + RandomUtil.randomNumbers(7);
			writeOffMaterialNumber = "440" + RandomUtil.randomNumbers(7);
			List<OrderDeliveryDTO> orderDeliveryDTOS = new ArrayList<>();
			dataTable.asMaps().stream().forEach(
					caseData -> {
						String orderItem = caseData.get("orderItem");
						orderDeliveryDTOS.add(OrderDeliveryDTO.builder()
								.date(createDate)
								.deliverNo(writeOffDeliveryNumber)
								.orderItem(orderItem)
								.batchNum(batchNum)
								.eccid(eccId)
								.orderItemQuantityTotal(batchNum)
								.orderNo(orderNo)
								.materialNumber(writeOffMaterialNumber)
								.writeOffMaterialNumber(materialNumber)
								.build());
					}
			);
			orderApiDelivery01.writeOffPositiveDeliveryShipment(orderDeliveryDTOS)
					.then()
					.body(Matchers.containsString("<data>true</data>"));
		});
		Then("[交货单校验相同过滤04]交货单过滤重复数据正常入库", (DataTable dataTable) -> {
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
		Given("[交货单校验相同过滤05]当前存在如下客户信息", (DataTable dataTable) -> {
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
		When("[交货单校验相同过滤05]创建订单且下发SAP至待发货", () -> {
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
		And("[交货单校验相同过滤05]SAP发送交货单数据如下", (DataTable dataTable) -> {
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
		And("[交货单校验相同过滤05]SAP发送交货单10行冲销数据", (DataTable dataTable) -> {
			writeOffDeliveryNumber = "220" + RandomUtil.randomNumbers(7);
			writeOffMaterialNumber = "440" + RandomUtil.randomNumbers(7);
			List<OrderDeliveryDTO> orderDeliveryDTOS = new ArrayList<>();
			dataTable.asMaps().stream().forEach(
					caseData -> {
						String orderItem = caseData.get("orderItem");
						orderDeliveryDTOS.add(OrderDeliveryDTO.builder()
								.date(createDate)
								.deliverNo(writeOffDeliveryNumber)
								.orderItem(orderItem)
								.batchNum(batchNum)
								.eccid(eccId)
								.orderItemQuantityTotal(batchNum)
								.orderNo(orderNo)
								.materialNumber(writeOffMaterialNumber)
								.writeOffMaterialNumber(materialNumber)
								.build());
					}
			);
			orderApiDelivery01.writeOffPositiveDeliveryShipment(orderDeliveryDTOS)
					.then()
					.body(Matchers.containsString("<data>true</data>"));
		});
		Then("[交货单校验相同过滤05]交货单过滤重复数据正常入库", (DataTable dataTable) -> {
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
		Given("[交货单校验相同过滤06]当前存在如下客户信息", (DataTable dataTable) -> {
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
		When("[交货单校验相同过滤06]创建订单且下发SAP至待发货", () -> {
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
		And("[交货单校验相同过滤06]SAP发送交货单数据如下", (DataTable dataTable) -> {
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
		And("[交货单校验相同过滤06]SAP发送交货单10行冲销数据", (DataTable dataTable) -> {
			writeOffDeliveryNumber = "220" + RandomUtil.randomNumbers(7);
			writeOffMaterialNumber = "440" + RandomUtil.randomNumbers(7);
			List<OrderDeliveryDTO> orderDeliveryDTOS = new ArrayList<>();
			dataTable.asMaps().stream().forEach(
					caseData -> {
						String orderItem = caseData.get("orderItem");
						orderDeliveryDTOS.add(OrderDeliveryDTO.builder()
								.date(createDate)
								.deliverNo(writeOffDeliveryNumber)
								.orderItem(orderItem)
								.batchNum(batchNum)
								.eccid(eccId)
								.orderItemQuantityTotal(batchNum)
								.orderNo(orderNo)
								.materialNumber(writeOffMaterialNumber)
								.writeOffMaterialNumber(materialNumber)
								.build());
					}
			);
			orderApiDelivery01.writeOffPositiveDeliveryShipment(orderDeliveryDTOS)
					.then()
					.body(Matchers.containsString("<data>true</data>"));
		});
		And("[交货单校验相同过滤06]SAP发送交货单10行重复冲销数据", (DataTable dataTable) -> {
			List<OrderDeliveryDTO> orderDeliveryDTOS = new ArrayList<>();
			dataTable.asMaps().stream().forEach(
					caseData -> {
						String orderItem = caseData.get("orderItem");
						orderDeliveryDTOS.add(OrderDeliveryDTO.builder()
								.date(createDate)
								.deliverNo(writeOffDeliveryNumber)
								.orderItem(orderItem)
								.batchNum(batchNum)
								.eccid(eccId)
								.orderItemQuantityTotal(batchNum)
								.orderNo(orderNo)
								.materialNumber(writeOffMaterialNumber)
								.writeOffMaterialNumber(materialNumber)
								.build());
					}
			);
			orderApiDelivery01.writeOffPositiveDeliveryShipment(orderDeliveryDTOS)
					.then()
					.body(Matchers.containsString("<data>true</data>"));
		});
		Then("[交货单校验相同过滤06]交货单过滤重复数据正常入库", (DataTable dataTable) -> {
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
		Given("[交货单校验相同过滤07]当前存在如下客户信息", (DataTable dataTable) -> {
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
		When("[交货单校验相同过滤07]创建订单且下发SAP至待发货", () -> {
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
		And("[交货单校验相同过滤07]SAP发送交货单数据如下", (DataTable dataTable) -> {
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
		Then("[交货单校验相同过滤07]交货单过滤重复数据正常入库", (DataTable dataTable) -> {
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
		When("[交货单校验相同过滤07]创建退货订单且下发SAP", (DataTable dataTable) -> {
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
		And("[交货单校验相同过滤07]SAP发送退货交货单数据如下", (DataTable dataTable) -> {
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
		Then("[交货单校验相同过滤07]退货交货单过滤重复数据正常入库", (DataTable dataTable) -> {
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
		Given("[交货单校验相同过滤08]当前存在如下客户信息", (DataTable dataTable) -> {
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
		When("[交货单校验相同过滤08]创建订单且下发SAP至待发货", () -> {
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
		And("[交货单校验相同过滤08]SAP发送交货单数据如下", (DataTable dataTable) -> {
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
		Then("[交货单校验相同过滤08]交货单过滤重复数据正常入库", (DataTable dataTable) -> {
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
		When("[交货单校验相同过滤08]创建退货订单且下发SAP", (DataTable dataTable) -> {
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
		And("[交货单校验相同过滤08]SAP发送退货交货单数据如下", (DataTable dataTable) -> {
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
		Then("[交货单校验相同过滤08]退货交货单过滤重复数据正常入库", (DataTable dataTable) -> {
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
		Given("[交货单校验相同过滤09]当前存在如下客户信息", (DataTable dataTable) -> {
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
		When("[交货单校验相同过滤09]创建订单且下发SAP至待发货", () -> {
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
		And("[交货单校验相同过滤09]SAP发送交货单数据如下", (DataTable dataTable) -> {
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
		Then("[交货单校验相同过滤09]交货单过滤重复数据正常入库", (DataTable dataTable) -> {
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
		When("[交货单校验相同过滤09]创建退货订单且下发SAP", (DataTable dataTable) -> {
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
		And("[交货单校验相同过滤09]SAP发送退货交货单数据如下", (DataTable dataTable) -> {
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
		And("[交货单校验相同过滤09]SAP发送退货交货单重复数据如下", (DataTable dataTable) -> {
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
		Then("[交货单校验相同过滤09]退货交货单过滤重复数据正常入库", (DataTable dataTable) -> {
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
		Given("[交货单校验相同过滤10]当前存在如下客户信息", (DataTable dataTable) -> {
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
		When("[交货单校验相同过滤10]创建订单且下发SAP至待发货", () -> {
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
		And("[交货单校验相同过滤10]SAP发送交货单数据如下", (DataTable dataTable) -> {
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
		Then("[交货单校验相同过滤10]交货单过滤重复数据正常入库", (DataTable dataTable) -> {
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
		When("[交货单校验相同过滤10]创建退货订单且下发SAP", (DataTable dataTable) -> {
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
		And("[交货单校验相同过滤10]SAP发送退货交货单数据如下", (DataTable dataTable) -> {
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
		And("[交货单校验相同过滤10]SAP发送退货冲销交货单数据如下", (DataTable dataTable) -> {
			writeOffReturnDeliveryNumber = "220" + RandomUtil.randomNumbers(7);
			writeOffReturnMaterialNumber = "440" + RandomUtil.randomNumbers(7);
			List<OrderDeliveryDTO> orderDeliveryDTOS = new ArrayList<>();
			dataTable.asMaps().stream().forEach(
					caseData -> {
						String returnOrderItem = caseData.get("returnOrderItem");
						String deliveryBatchNum = caseData.get("deliveryBatchNum");
						orderDeliveryDTOS.add(OrderDeliveryDTO.builder()
								.date(createDate)
								.deliverNo(writeOffReturnDeliveryNumber)
								.orderItem(returnOrderItem)
								.batchNum(deliveryBatchNum)
								.eccid(returnEccId)
								.orderItemQuantityTotal(deliveryBatchNum)
								.orderNo(orderNo)
								.refundOrderNo(returnOrderNo)
								.materialNumber(writeOffReturnMaterialNumber)
								.writeOffMaterialNumber(returnMaterialNumber)
								.build());
					}
			);
			orderApiDelivery01.writeOffRefundDeliveryShipment(orderDeliveryDTOS)
					.then()
					.body(Matchers.containsString("<data>true</data>"));
		});
		And("[交货单校验相同过滤10]SAP发送退货交货单重复数据如下", (DataTable dataTable) -> {
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
		Then("[交货单校验相同过滤10]退货交货单过滤重复数据正常入库", (DataTable dataTable) -> {
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
		Given("[交货单校验相同过滤11]当前存在如下客户信息", (DataTable dataTable) -> {
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
		When("[交货单校验相同过滤11]创建订单且下发SAP至待发货", () -> {
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
		And("[交货单校验相同过滤11]SAP发送交货单数据如下", (DataTable dataTable) -> {
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
		Then("[交货单校验相同过滤11]交货单过滤重复数据正常入库", (DataTable dataTable) -> {
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
		When("[交货单校验相同过滤11]创建退货订单且下发SAP", (DataTable dataTable) -> {
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
		And("[交货单校验相同过滤11]SAP发送退货交货单数据如下", (DataTable dataTable) -> {
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
		And("[交货单校验相同过滤11]SAP发送退货冲销交货单数据如下", (DataTable dataTable) -> {
			writeOffReturnDeliveryNumber = "220" + RandomUtil.randomNumbers(7);
			writeOffReturnMaterialNumber = "440" + RandomUtil.randomNumbers(7);
			List<OrderDeliveryDTO> orderDeliveryDTOS = new ArrayList<>();
			dataTable.asMaps().stream().forEach(
					caseData -> {
						String returnOrderItem = caseData.get("returnOrderItem");
						String deliveryBatchNum = caseData.get("deliveryBatchNum");
						orderDeliveryDTOS.add(OrderDeliveryDTO.builder()
								.date(createDate)
								.deliverNo(writeOffReturnDeliveryNumber)
								.orderItem(returnOrderItem)
								.batchNum(deliveryBatchNum)
								.eccid(returnEccId)
								.orderItemQuantityTotal(deliveryBatchNum)
								.orderNo(orderNo)
								.refundOrderNo(returnOrderNo)
								.materialNumber(writeOffReturnMaterialNumber)
								.writeOffMaterialNumber(returnMaterialNumber)
								.build());
					}
			);
			orderApiDelivery01.writeOffRefundDeliveryShipment(orderDeliveryDTOS)
					.then()
					.body(Matchers.containsString("<data>true</data>"));
		});
		And("[交货单校验相同过滤11]SAP发送退货交货单重复数据如下", (DataTable dataTable) -> {
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
		Then("[交货单校验相同过滤11]退货交货单过滤重复数据正常入库", (DataTable dataTable) -> {
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
		Given("[交货单校验相同过滤12]当前存在如下客户信息", (DataTable dataTable) -> {
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
		When("[交货单校验相同过滤12]创建订单且下发SAP至待发货", () -> {
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
		And("[交货单校验相同过滤12]SAP发送交货单数据如下", (DataTable dataTable) -> {
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
		Then("[交货单校验相同过滤12]交货单过滤重复数据正常入库", (DataTable dataTable) -> {
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
		When("[交货单校验相同过滤12]创建退货订单且下发SAP", (DataTable dataTable) -> {
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
		And("[交货单校验相同过滤12]SAP发送退货交货单数据如下", (DataTable dataTable) -> {
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
		And("[交货单校验相同过滤12]SAP发送退货冲销交货单数据如下", (DataTable dataTable) -> {
			writeOffReturnDeliveryNumber = "220" + RandomUtil.randomNumbers(7);
			writeOffReturnMaterialNumber = "440" + RandomUtil.randomNumbers(7);
			List<OrderDeliveryDTO> orderDeliveryDTOS = new ArrayList<>();
			dataTable.asMaps().stream().forEach(
					caseData -> {
						String returnOrderItem = caseData.get("returnOrderItem");
						String deliveryBatchNum = caseData.get("deliveryBatchNum");
						orderDeliveryDTOS.add(OrderDeliveryDTO.builder()
								.date(createDate)
								.deliverNo(writeOffReturnDeliveryNumber)
								.orderItem(returnOrderItem)
								.batchNum(deliveryBatchNum)
								.eccid(returnEccId)
								.orderItemQuantityTotal(deliveryBatchNum)
								.orderNo(orderNo)
								.refundOrderNo(returnOrderNo)
								.materialNumber(writeOffReturnMaterialNumber)
								.writeOffMaterialNumber(returnMaterialNumber)
								.build());
					}
			);
			orderApiDelivery01.writeOffRefundDeliveryShipment(orderDeliveryDTOS)
					.then()
					.body(Matchers.containsString("<data>true</data>"));
		});
		And("[交货单校验相同过滤12]SAP发送退货重复冲销交货单数据如下", (DataTable dataTable) -> {
			List<OrderDeliveryDTO> orderDeliveryDTOS = new ArrayList<>();
			dataTable.asMaps().stream().forEach(
					caseData -> {
						String returnOrderItem = caseData.get("returnOrderItem");
						String deliveryBatchNum = caseData.get("deliveryBatchNum");
						orderDeliveryDTOS.add(OrderDeliveryDTO.builder()
								.date(createDate)
								.deliverNo(writeOffReturnDeliveryNumber)
								.orderItem(returnOrderItem)
								.batchNum(deliveryBatchNum)
								.eccid(returnEccId)
								.orderItemQuantityTotal(deliveryBatchNum)
								.orderNo(orderNo)
								.refundOrderNo(returnOrderNo)
								.materialNumber(writeOffReturnMaterialNumber)
								.writeOffMaterialNumber(returnMaterialNumber)
								.build());
					}
			);
			orderApiDelivery01.writeOffRefundDeliveryShipment(orderDeliveryDTOS)
					.then()
					.body(Matchers.containsString("<data>true</data>"));
		});
		And("[交货单校验相同过滤12]SAP发送退货交货单重复数据如下", (DataTable dataTable) -> {
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
		Then("[交货单校验相同过滤12]退货交货单过滤重复数据正常入库", (DataTable dataTable) -> {
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
