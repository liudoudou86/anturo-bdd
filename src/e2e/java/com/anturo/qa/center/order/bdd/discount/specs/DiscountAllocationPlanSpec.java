package com.anturo.qa.center.order.bdd.discount.specs;

import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.IdUtil;
import com.anturo.qa.center.order.bdd.order.apis.OrderApiOrderManagement01;
import com.anturo.qa.center.order.bdd.order.backgrounds.DiscountAllocationPlanBackgroundData01;
import com.anturo.qa.center.order.bdd.order.batchs.OrderBatchUpdateData01;
import com.anturo.qa.center.order.bdd.order.batchs.OrderDiscountDetailBatchSelectData01;
import com.anturo.qa.center.order.bdd.order.checks.DiscountCheckAllocationPlan01;
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
import org.apache.commons.lang3.StringUtils;
import org.hamcrest.Matchers;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Author guoqing
 * @Date 2024/06/05
 * @Description
 */

@SuppressWarnings("all")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class DiscountAllocationPlanSpec extends DiscountAllocationPlanBackgroundData01 implements En {

	String saleOrgSapCode, regionCode, regionName, customerSapCode, shippingAddress, shippingRecipient, shippingTelephone;

	String preDiscountId, preDiscountDetailId;

	String applicationFormId, applicationFormDetailId, allocationPlanId, allocationPlanDetailId;

	String createTime, beginDate, endDate, processOrderNo, planMonth, lotNo;

	String lty_allocationPlanDetailId, cz_allocationPlanDetailId, mz_allocationPlanDetailId;

	String allocationPlanDetailId_001, allocationPlanDetailId_002, allocationPlanDetailId_003, allocationPlanDetailId_004, allocationPlanDetailId_201, allocationPlanDetailId_202, allocationPlanDetailId_203, allocationPlanDetailId_301, allocationPlanDetailId_302, allocationPlanDetailId_401;

	String allocationPlanId_001, allocationPlanId_002, allocationPlanId_003, allocationPlanId_004;

	DataTable domainDataTable;

	@Steps(actor = "QA", shared = true)
	OrderApiOrderManagement01 orderApiOrderManagement01;

	@Steps(actor = "QA", shared = true)
	OrderCheckOrderManagement01 orderCheckOrderManagement01;

	@Steps(actor = "QA", shared = true)
	OrderBatchUpdateData01 orderBatchUpdateData01;

	@Steps(actor = "QA", shared = true)
	OrderDiscountDetailBatchSelectData01 orderDiscountDetailBatchSelectData01;

	@Steps(actor = "QA", shared = true)
	DiscountCheckAllocationPlan01 discountCheckAllocationPlan01;

	// 终端ID
	Long workerId = NetUtil.ipv4ToLong(NetUtil.getLocalhostStr()) >> 16 & 31;
	// 数据中心ID
	Long dataCenterId = 1L;

	public DiscountAllocationPlanSpec() {
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
		this.scenario13();
	}

	private void setAllocationPlanInfoByRemark(String remark, String allocationPlanId, String allocationPlanDetailId) {
		switch (remark) {
			case "Detail_001":
				allocationPlanDetailId_001 = allocationPlanDetailId;
				allocationPlanId_001 = allocationPlanId;
				break;
			case "Detail_002":
				allocationPlanDetailId_002 = allocationPlanDetailId;
				allocationPlanId_001 = allocationPlanId;
				break;
			case "Detail_003":
				allocationPlanDetailId_003 = allocationPlanDetailId;
				allocationPlanId_001 = allocationPlanId;
				break;
			case "Detail_004":
				allocationPlanDetailId_004 = allocationPlanDetailId;
				allocationPlanId_001 = allocationPlanId;
				break;
			case "Detail_201":
				allocationPlanDetailId_201 = allocationPlanDetailId;
				allocationPlanId_002 = allocationPlanId;
				break;
			case "Detail_202":
				allocationPlanDetailId_202 = allocationPlanDetailId;
				allocationPlanId_002 = allocationPlanId;
				break;
			case "Detail_203":
				allocationPlanDetailId_203 = allocationPlanDetailId;
				allocationPlanId_002 = allocationPlanId;
				break;
			case "Detail_301":
				allocationPlanDetailId_301 = allocationPlanDetailId;
				allocationPlanId_003 = allocationPlanId;
				break;
			case "Detail_302":
				allocationPlanDetailId_302 = allocationPlanDetailId;
				allocationPlanId_003 = allocationPlanId;
				break;
			case "Detail_401":
				allocationPlanDetailId_401 = allocationPlanDetailId;
				allocationPlanId_004 = allocationPlanId;
				break;
			default:
				break;
		}
	}


	private void getAllocationPlanInfoByRemark(String remark) {
		switch (remark) {
			case "Detail_001":
				allocationPlanDetailId = allocationPlanDetailId_001;
				allocationPlanId = allocationPlanId_001;
				break;
			case "Detail_002":
				allocationPlanDetailId = allocationPlanDetailId_002;
				allocationPlanId = allocationPlanId_001;
				break;
			case "Detail_003":
				allocationPlanDetailId = allocationPlanDetailId_003;
				allocationPlanId = allocationPlanId_001;
				break;
			case "Detail_004":
				allocationPlanDetailId = allocationPlanDetailId_004;
				allocationPlanId = allocationPlanId_001;
				break;
			case "Detail_201":
				allocationPlanDetailId = allocationPlanDetailId_201;
				allocationPlanId = allocationPlanId_002;
				break;
			case "Detail_202":
				allocationPlanDetailId = allocationPlanDetailId_202;
				allocationPlanId = allocationPlanId_002;
				break;
			case "Detail_203":
				allocationPlanDetailId = allocationPlanDetailId_203;
				allocationPlanId = allocationPlanId_002;
				break;
			case "Detail_301":
				allocationPlanDetailId = allocationPlanDetailId_301;
				allocationPlanId = allocationPlanId_003;
				break;
			case "Detail_302":
				allocationPlanDetailId = allocationPlanDetailId_302;
				allocationPlanId = allocationPlanId_003;
				break;
			case "Detail_401":
				allocationPlanDetailId = allocationPlanDetailId_401;
				allocationPlanId = allocationPlanId_004;
				break;
			default:
				allocationPlanDetailId = "";
				break;
		}
	}


	private void scenario01() {
		Given("[整单折扣分配计划分摊01]当前存在如下客户信息", (DataTable dataTable) -> {
			createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis());
			domainDataTable = dataTable;
			domainDataTable.asMaps().forEach(caseData -> {
				saleOrgSapCode = Optional.ofNullable(caseData.get("saleOrgSapCode")).orElse("");
				regionCode = Optional.ofNullable(caseData.get("regionCode")).orElse("");
				regionName = Optional.ofNullable(caseData.get("regionName")).orElse("");
				customerSapCode = Optional.ofNullable(caseData.get("customerSapCode")).orElse("");
				shippingAddress = Optional.ofNullable(caseData.get("shippingAddress")).orElse("");
				shippingRecipient = Optional.ofNullable(caseData.get("shippingRecipient")).orElse("");
				shippingTelephone = Optional.ofNullable(caseData.get("shippingTelephone")).orElse("");
			});
		});
		When("[整单折扣分配计划分摊01]当前存在如下的预折商品信息", (DataTable dataTable) -> {
			preDiscountId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			preDiscountDetailId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			beginDate = new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis());
			endDate = new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis() + 24 * 60 * 60 * 1000);
			orderBatchInsertData01.insertOrderPreDidcountData01(OrderPreDiscountDTO.builder()
					.preDiscountId(preDiscountId)
					.customerSapCode(customerSapCode)
					.saleOrgSapCode(saleOrgSapCode)
					.regionCode("")
					.regionName("")
					.effectiveState("ENABLE")
					.preType("CUSTOMER_PRE_DISCOUNT")
					.createPerson("at")
					.createTime(createTime)
					.build());
			dataTable.asMaps().stream().forEach(caseData -> {
				String id = "100000" + Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				BigDecimal currentPrice = new BigDecimal(caseData.get("currentPrice")).setScale(8, RoundingMode.HALF_UP);
				String calculationType = Optional.ofNullable(caseData.get("calculationType")).orElse("");
				BigDecimal calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(8, RoundingMode.HALF_UP);
				String preType = Optional.ofNullable(caseData.get("preType")).orElse("");
				String effectiveState = Optional.ofNullable(caseData.get("effectiveState")).orElse("");
				String distributionChannelCode = Optional.ofNullable(caseData.get("distributionChannelCode")).orElse("");
				String distributionChannelName = Optional.ofNullable(caseData.get("distributionChannelName")).orElse("");
				orderBatchInsertData01.insertOrderPreDidcountDetailData01(OrderPreDiscountDetailDTO.builder()
						.id(id)
						.preDiscountDetailId(preDiscountDetailId)
						.preDiscountId(preDiscountId)
						.itemSapCode(itemSapCode)
						.distributionChannelCode(distributionChannelCode)
						.distributionChannelName(distributionChannelName)
						.currentPrice(currentPrice)
						.calculationType(calculationType)
						.calculationValue(calculationValue)
						.beginDate(beginDate)
						.endDate(endDate)
						.effectiveState(effectiveState)
						.preType("CUSTOMER_PRE_DISCOUNT")
						.createPerson("at")
						.createTime(createTime)
						.build()
				);
			});
		});
		And("[整单折扣分配计划分摊01]当前存在如下的有效预折折扣信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String effectiveState = Optional.ofNullable(caseData.get("effectiveState")).orElse("");
				orderBatchUpdateData01.updateOrderPreDiscountDetailEffectiveState(OrderPreDiscountDetailDTO.builder().effectiveState(effectiveState).itemSapCode(itemSapCode).build(), OrderPreDiscountDTO.builder().customerSapCode(customerSapCode).build());
			});
		});
		And("[整单折扣分配计划分摊01]当前存在如下的整单折扣申请单信息", (DataTable dataTable) -> {
			applicationFormId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			processOrderNo = "ZDZK" + new SimpleDateFormat("yyyyMMddHHmm").format(System.currentTimeMillis());
			dataTable.asMaps().stream().forEach(caseData -> {
				String projectType = Optional.ofNullable(caseData.get("projectType")).orElse("");
				String module = Optional.ofNullable(caseData.get("module")).orElse("");
				orderBatchInsertData01.insertOrderApplicationFormData01(OrderApplicationFormDTO.builder()
						.applicationFormId(applicationFormId)
						.processOrderNo(processOrderNo)
						.applicant("at-OTC分公司")
						.applicationDate(createTime)
						.projectType(projectType)
						.module(module)
						.status("APPROVED")
						.executionStartTime(beginDate)
						.executionEndTime(endDate)
						.saleOrgSapCode(saleOrgSapCode)
						.createPerson("at")
						.createTime(createTime)
						.build());
			});
		});
		And("[整单折扣分配计划分摊01]当前存在如下的整单折扣申请单商品信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				applicationFormDetailId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String quantity = Optional.ofNullable(caseData.get("quantity")).orElse("");
				String units = Optional.ofNullable(caseData.get("units")).orElse("");
				orderBatchInsertData01.insertOrderApplicationFormDetailData01(OrderApplicationFormDetailDTO.builder()
						.applicationFormDetailId(applicationFormDetailId)
						.applicationFormId(applicationFormId)
						.itemSapCode(itemSapCode)
						.quantity(quantity)
						.units(units)
						.createPerson("at")
						.createTime(createTime)
						.build());
			});
		});
		And("[整单折扣分配计划分摊01]当前存在如下的整单折扣分配计划信息", () -> {
			allocationPlanId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			planMonth = new SimpleDateFormat("yyyy-MM").format(System.currentTimeMillis());
			orderBatchInsertData01.insertOrderAllocationPlanData01(OrderAllocationPlanDTO.builder()
					.allocationPlanId(allocationPlanId)
					.orderDiscountApplicationFormId(applicationFormId)
					.status("IN_PROGRESS")
					.lockStatus("UNLOCK")
					.planMonth(planMonth)
					.newExecutionStartTime(beginDate)
					.newExecutionEndTime(endDate)
					.createPerson("at")
					.createTime(createTime)
					.build());
		});
		And("[整单折扣分配计划分摊01]当前存在如下的整单折扣分配计划详细信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String lotNo = Optional.ofNullable(caseData.get("lot_no")).orElse("");
				String remark = Optional.ofNullable(caseData.get("remark")).orElse("");
				allocationPlanDetailId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
				setAllocationPlanInfoByRemark(remark, allocationPlanId, allocationPlanDetailId);
				String originalItemSapCode = Optional.ofNullable(caseData.get("originalItemSapCode")).orElse("");
				String originalDistributionChannelCode = Optional.ofNullable(caseData.get("originalDistributionChannelCode")).orElse("");
				String originalDistributionChannelName = Optional.ofNullable(caseData.get("originalDistributionChannelName")).orElse("");
				String giftItemSapCode = Optional.ofNullable(caseData.get("giftItemSapCode")).orElse("");
				String giftDistributionChannelCode = Optional.ofNullable(caseData.get("giftDistributionChannelCode")).orElse("");
				String giftDistributionChannelName = Optional.ofNullable(caseData.get("giftDistributionChannelName")).orElse("");
				String allocationQuantity = Optional.ofNullable(caseData.get("allocationQuantity")).orElse("");
				Integer originalQuantity = Integer.valueOf(Optional.ofNullable(caseData.get("originalQuantity")).orElse("0"));
				Integer giftQuantity = Integer.parseInt(Optional.ofNullable(caseData.get("giftQuantity")).orElse("0"));
				Integer initialBonusQuantity = Integer.parseInt(Optional.ofNullable(caseData.get("initialBonusQuantity")).orElse("0"));
				String needShip = Optional.ofNullable(caseData.get("needShip")).orElse("");
				String unit = Optional.ofNullable(caseData.get("unit")).orElse("");
				String invalidStatus = Optional.ofNullable(caseData.get("invalidStatus")).orElse("");
				orderBatchInsertData01.insertOrderApplicationPlanDetailData01(OrderAllocationPlanDetailDTO.builder()
						.allocationPlanDetailId(allocationPlanDetailId)
						.allocationPlanId(allocationPlanId)
						.applicationFormDetailId(applicationFormDetailId)
						.customerEntSapCode(customerSapCode)
						.saleOrgSapCode(saleOrgSapCode)
						.regionCode(regionCode)
						.regionName(regionName)
						.originalItemSapCode(originalItemSapCode)
						.originalDistributionChannelCode(originalDistributionChannelCode)
						.originalDistributionChannelName(originalDistributionChannelName)
						.giftItemSapCode(giftItemSapCode)
						.giftDistributionChannelCode(giftDistributionChannelCode)
						.giftDistributionChannelName(giftDistributionChannelName)
						.allocationQuantity(allocationQuantity)
						.originalQuantity(originalQuantity)
						.giftQuantity(giftQuantity)
						.initialBonusQuantity(initialBonusQuantity)
						.unit(unit)
						.lotNo(lotNo)
						.productionDate(beginDate)
						.expirationDate(endDate)
						.needShip(needShip)
						.invalidStatus(invalidStatus)
						.remark(remark)
						.createPerson("at")
						.createTime(createTime)
						.build());
			});
		});
		And("[整单折扣分配计划分摊01]添加商品", (DataTable dataTable) -> {
			log.info("添加商品");
		});
		And("[整单折扣分配计划分摊01]选择项目", (DataTable dataTable) -> {
			log.info("选择项目");
		});
		Then("[整单折扣分配计划分摊01]创建订单应校验通过且提交成功", (DataTable dataTable) -> {
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
			List<OrderDiscountCalcCalculateReqGroupListListDTO> orderDiscountCalcCalculateReqGroupListListDTOS = new ArrayList<>();
			dataTable.asMaps().forEach(caseData -> {
				String id = "100000" + Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String orderWideDiscountSubject = Optional.ofNullable(caseData.get("orderWideDiscountSubject")).orElse("");
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String needShip = Optional.ofNullable(caseData.get("needShip")).orElse("");
				String unitPrice = new BigDecimal(caseData.get("unitPrice")).setScale(2, RoundingMode.HALF_UP).toString();
				String quantity = Optional.ofNullable(caseData.get("quantity")).orElse("");
				String boxRatio = new Integer(caseData.get("boxRatio")).toString();
				String preDiscount = new BigDecimal(caseData.get("preDiscount")).setScale(2, RoundingMode.HALF_UP).toString();
				if ("0.00".equals(preDiscount)) {
					id = ""; // 如果是"0"，则将id设置为空字符串
				}
				String calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(2, RoundingMode.HALF_UP).toString();
				String totalInline = new BigDecimal(caseData.get("totalInline")).setScale(2, RoundingMode.HALF_UP).toString();
				String preDiscountTotal = new BigDecimal(caseData.get("preDiscountTotal")).setScale(2, RoundingMode.HALF_UP).toString();
				String orderDiscountTotal = new BigDecimal(caseData.get("orderDiscountTotal")).setScale(2, RoundingMode.HALF_UP).toString();
				String remark = Optional.ofNullable(caseData.get("remark")).orElse("");
				getAllocationPlanInfoByRemark(remark);
				String discountProportion = String.format("%.4f", new BigDecimal(caseData.get("preDiscountTotal").toString()).add(new BigDecimal(caseData.get("orderDiscountTotal").toString())).divide(new BigDecimal(caseData.get("totalInline").toString()), 4, RoundingMode.HALF_UP).doubleValue());
				orderDiscountCalcCalculateReqGroupListListDTOS.add(OrderDiscountCalcCalculateReqGroupListListDTO.builder()
						.orderWideDiscountSubject(orderWideDiscountSubject)
						.discountAllocationPlanId(allocationPlanId)
						.discountAllocationPlanDetailId(allocationPlanDetailId)
						.discountId(id)
						.preDiscountDetailId(id)
						.itemSapCode(itemSapCode)
						.itemName(ProductEnum.fetchProductName(itemSapCode))
						.itemDesc(ProductEnum.fetchProductSpec(itemSapCode))
						.itemGroupCode("01")
						.itemGroupName("成品药-非管制药品")
						.distributionChannelCode("22")
						.distributionChannelName("分销-非医疗")
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
						.discountProportion(discountProportion)
						.build());
			});
			OrderDiscountCalcCalculateReqDTO calculateReq = orderApiOrderManagement01.discountCalcAfterData(OrderDataDTO.builder()
					.orderType("STANDARD")
					.deliveryNo(customerSapCode)
					.saleOrgSapCode(saleOrgSapCode)
					.saleOrgName(SaleOrgEnum.fetchName(saleOrgSapCode))
					.saleOrgZtCode(SaleOrgEnum.fetchZtCode(saleOrgSapCode))
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
					.contractRemark("自动化测试-合同备注")
					.remark("自动化测试-订单备注")
					.calculateReq(OrderDiscountCalcCalculateReqDTO.builder()
							.totalInline(totalInlineSum)
							.preDiscountTotal(preDiscountTotalSum)
							.orderDiscountTotal(orderDiscountTotalSum)
							.groupList(Arrays.asList(OrderDiscountCalcCalculateReqGroupListDTO.builder()
									.itemGroupCode("01")
									.itemGroupName("成品药-非管制药品")
									.distributionChannelCode("22")
									.distributionChannelName("分销-非医疗")
									.totalInline(totalInlineSum)
									.preDiscountTotal(preDiscountTotalSum)
									.orderDiscountTotal(orderDiscountTotalSum)
									.list(orderDiscountCalcCalculateReqGroupListListDTOS)
									.build()))
							.build()
					)
					.build());
			Response createOrderSubmitBeforeRsponse = orderApiOrderManagement01.createOrderBefore(OrderDataDTO.builder()
					.orderType("STANDARD")
					.deliveryNo(customerSapCode)
					.saleOrgSapCode(saleOrgSapCode)
					.saleOrgName(SaleOrgEnum.fetchName(saleOrgSapCode))
					.saleOrgZtCode(SaleOrgEnum.fetchZtCode(saleOrgSapCode))
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
					.contractRemark("自动化测试-合同备注")
					.remark("自动化测试-订单备注")
					.calculateReq(calculateReq)
					.build());
			Response createOrderVerifyResponse = orderApiOrderManagement01.createOrderVerify(OrderDataDTO.builder()
					.orderType("STANDARD")
					.deliveryNo(customerSapCode)
					.saleOrgSapCode(saleOrgSapCode)
					.saleOrgName(SaleOrgEnum.fetchName(saleOrgSapCode))
					.saleOrgZtCode(SaleOrgEnum.fetchZtCode(saleOrgSapCode))
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
					.contractRemark("自动化测试-合同备注")
					.remark("自动化测试-订单备注")
					.calculateReq(calculateReq)
					.build());
			createOrderVerifyResponse.then().body("resultCode", Matchers.equalTo("0"), "resultMsg", Matchers.equalTo("success"));
			Response createOrderSubmitResponse = orderApiOrderManagement01.createOrderSubmit(OrderDataDTO.builder()
					.orderType("STANDARD")
					.deliveryNo(customerSapCode)
					.saleOrgSapCode(saleOrgSapCode)
					.saleOrgName(SaleOrgEnum.fetchName(saleOrgSapCode))
					.saleOrgZtCode(SaleOrgEnum.fetchZtCode(saleOrgSapCode))
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
					.contractRemark("自动化测试-合同备注")
					.remark("自动化测试-订单备注")
					.calculateReq(calculateReq)
					.build());
			createOrderSubmitResponse.then().body("resultCode", Matchers.equalTo("0"), "resultMsg", Matchers.equalTo("success"));
		});
		Then("[整单折扣分配计划分摊01]订单整单折扣使用记录成功", (DataTable dataTable) -> {
			List<Map<String, String>> caseDataMap = new ArrayList<>();
			String orderNo = orderApiOrderManagement01.selectOrderInfo(OrderQueryPageByConditionDTO.builder()
					.state("PENDING_BUSINESS_REVIEW")
					.cancelStatus("NOT_CANCEL")
					.saleOrgSapCode(saleOrgSapCode)
					.customerCodeOrName(customerSapCode)
					.build());
			String parentsOrderNo = StringUtils.left(orderNo, 22);
			dataTable.asMaps().forEach(caseData -> {
				Map<String, String> processedData = new HashMap<>();
				processedData.put("parentOrderNo", parentsOrderNo);
				processedData.put("parentOrderItemNo", Optional.ofNullable(caseData.get("parentOrderItemNo")).orElse(""));
				processedData.put("orderNo", orderNo);
				processedData.put("orderItemNo", Optional.ofNullable(caseData.get("orderItemNo")).orElse(""));
				String remark = Optional.ofNullable(caseData.get("remark")).orElse("");
				switch (remark) {
					case "Detail_001":
						allocationPlanDetailId = allocationPlanDetailId_001;
						break;
					case "Detail_002":
						allocationPlanDetailId = allocationPlanDetailId_002;
						break;
					default:
						allocationPlanDetailId = "";
						break;
				}
				processedData.put("orderDiscountApplicationPlanId", allocationPlanId);
				processedData.put("orderDiscountApplicationPlanDetailId", allocationPlanDetailId);
				processedData.put("operateType", Optional.ofNullable(caseData.get("operateType")).orElse(""));
				processedData.put("operateAmount", Optional.ofNullable(caseData.get("operateAmount")).orElse(""));
				caseDataMap.add(processedData);
			});
			List<Map<String, Object>> sqlResult = orderDiscountDetailBatchSelectData01.queryOrderDiscountDetailData01(parentsOrderNo);
			discountCheckAllocationPlan01.checkDiscountAllocationPlan(caseDataMap, sqlResult);
			orderBatchClearData01.clearOrderData01(orderNo);
			priceBatchClearData01.clearPirceData01("at");
			orderBatchClearData01.clearOrderPreDiscountData01("at");
			orderBatchClearData01.clearDiscountData01("at");
		});
	}


	private void scenario02() {
		Given("[整单折扣分配计划分摊02]当前存在如下客户信息", (DataTable dataTable) -> {
			createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis());
			domainDataTable = dataTable;
			domainDataTable.asMaps().forEach(caseData -> {
				saleOrgSapCode = Optional.ofNullable(caseData.get("saleOrgSapCode")).orElse("");
				regionCode = Optional.ofNullable(caseData.get("regionCode")).orElse("");
				regionName = Optional.ofNullable(caseData.get("regionName")).orElse("");
				customerSapCode = Optional.ofNullable(caseData.get("customerSapCode")).orElse("");
				shippingAddress = Optional.ofNullable(caseData.get("shippingAddress")).orElse("");
				shippingRecipient = Optional.ofNullable(caseData.get("shippingRecipient")).orElse("");
				shippingTelephone = Optional.ofNullable(caseData.get("shippingTelephone")).orElse("");
			});
		});
		When("[整单折扣分配计划分摊02]当前存在如下的预折商品信息", (DataTable dataTable) -> {
			preDiscountId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			preDiscountDetailId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			beginDate = new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis());
			endDate = new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis() + 24 * 60 * 60 * 1000);
			orderBatchInsertData01.insertOrderPreDidcountData01(OrderPreDiscountDTO.builder()
					.preDiscountId(preDiscountId)
					.customerSapCode(customerSapCode)
					.saleOrgSapCode(saleOrgSapCode)
					.regionCode("")
					.regionName("")
					.effectiveState("ENABLE")
					.preType("CUSTOMER_PRE_DISCOUNT")
					.createPerson("at")
					.createTime(createTime)
					.build());
			dataTable.asMaps().stream().forEach(caseData -> {
				String id = "100000" + Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				BigDecimal currentPrice = new BigDecimal(caseData.get("currentPrice")).setScale(8, RoundingMode.HALF_UP);
				String calculationType = Optional.ofNullable(caseData.get("calculationType")).orElse("");
				BigDecimal calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(8, RoundingMode.HALF_UP);
				String preType = Optional.ofNullable(caseData.get("preType")).orElse("");
				String effectiveState = Optional.ofNullable(caseData.get("effectiveState")).orElse("");
				String distributionChannelCode = Optional.ofNullable(caseData.get("distributionChannelCode")).orElse("");
				String distributionChannelName = Optional.ofNullable(caseData.get("distributionChannelName")).orElse("");
				orderBatchInsertData01.insertOrderPreDidcountDetailData01(OrderPreDiscountDetailDTO.builder()
						.id(id)
						.preDiscountDetailId(preDiscountDetailId)
						.preDiscountId(preDiscountId)
						.itemSapCode(itemSapCode)
						.distributionChannelCode(distributionChannelCode)
						.distributionChannelName(distributionChannelName)
						.currentPrice(currentPrice)
						.calculationType(calculationType)
						.calculationValue(calculationValue)
						.beginDate(beginDate)
						.endDate(endDate)
						.effectiveState(effectiveState)
						.preType("CUSTOMER_PRE_DISCOUNT")
						.createPerson("at")
						.createTime(createTime)
						.build()
				);
			});
		});
		And("[整单折扣分配计划分摊02]当前存在如下的预折折扣信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				BigDecimal calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(8, RoundingMode.HALF_UP);
				String effectiveState = Optional.ofNullable(caseData.get("effectiveState")).orElse("");
				orderBatchUpdateData01.updateOrderPreDiscountDetailCalculationValue(OrderPreDiscountDetailDTO.builder().effectiveState(effectiveState).calculationValue(calculationValue).itemSapCode(itemSapCode).build(), OrderPreDiscountDTO.builder().customerSapCode(customerSapCode).build());
			});
		});
		And("[整单折扣分配计划分摊02]当前存在如下的整单折扣申请单信息", (DataTable dataTable) -> {
			applicationFormId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			processOrderNo = "ZDZK" + new SimpleDateFormat("yyyyMMddHHmm").format(System.currentTimeMillis());
			dataTable.asMaps().stream().forEach(caseData -> {
				String projectType = Optional.ofNullable(caseData.get("projectType")).orElse("");
				String module = Optional.ofNullable(caseData.get("module")).orElse("");
				orderBatchInsertData01.insertOrderApplicationFormData01(OrderApplicationFormDTO.builder()
						.applicationFormId(applicationFormId)
						.processOrderNo(processOrderNo)
						.applicant("at-OTC分公司")
						.applicationDate(createTime)
						.projectType(projectType)
						.module(module)
						.status("APPROVED")
						.executionStartTime(beginDate)
						.executionEndTime(endDate)
						.saleOrgSapCode(saleOrgSapCode)
						.createPerson("at")
						.createTime(createTime)
						.build());
			});
		});
		And("[整单折扣分配计划分摊02]当前存在如下的整单折扣申请单商品信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				applicationFormDetailId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String quantity = Optional.ofNullable(caseData.get("quantity")).orElse("");
				String units = Optional.ofNullable(caseData.get("units")).orElse("");
				orderBatchInsertData01.insertOrderApplicationFormDetailData01(OrderApplicationFormDetailDTO.builder()
						.applicationFormDetailId(applicationFormDetailId)
						.applicationFormId(applicationFormId)
						.itemSapCode(itemSapCode)
						.quantity(quantity)
						.units(units)
						.createPerson("at")
						.createTime(createTime)
						.build());
			});
		});
		And("[整单折扣分配计划分摊02]当前存在如下的整单折扣分配计划信息", () -> {
			allocationPlanId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			planMonth = new SimpleDateFormat("yyyy-MM").format(System.currentTimeMillis());
			orderBatchInsertData01.insertOrderAllocationPlanData01(OrderAllocationPlanDTO.builder()
					.allocationPlanId(allocationPlanId)
					.orderDiscountApplicationFormId(applicationFormId)
					.status("IN_PROGRESS")
					.lockStatus("UNLOCK")
					.planMonth(planMonth)
					.newExecutionStartTime(beginDate)
					.newExecutionEndTime(endDate)
					.createPerson("at")
					.createTime(createTime)
					.build());
		});
		And("[整单折扣分配计划分摊02]当前存在如下的整单折扣分配计划详细信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String lotNo = Optional.ofNullable(caseData.get("lot_no")).orElse("");
				String remark = Optional.ofNullable(caseData.get("remark")).orElse("");
				allocationPlanDetailId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
				setAllocationPlanInfoByRemark(remark, allocationPlanId, allocationPlanDetailId);
				String originalItemSapCode = Optional.ofNullable(caseData.get("originalItemSapCode")).orElse("");
				String originalDistributionChannelCode = Optional.ofNullable(caseData.get("originalDistributionChannelCode")).orElse("");
				String originalDistributionChannelName = Optional.ofNullable(caseData.get("originalDistributionChannelName")).orElse("");
				String giftItemSapCode = Optional.ofNullable(caseData.get("giftItemSapCode")).orElse("");
				String giftDistributionChannelCode = Optional.ofNullable(caseData.get("giftDistributionChannelCode")).orElse("");
				String giftDistributionChannelName = Optional.ofNullable(caseData.get("giftDistributionChannelName")).orElse("");
				String allocationQuantity = Optional.ofNullable(caseData.get("allocationQuantity")).orElse("");
				Integer originalQuantity = Integer.valueOf(Optional.ofNullable(caseData.get("originalQuantity")).orElse("0"));
				Integer giftQuantity = Integer.parseInt(Optional.ofNullable(caseData.get("giftQuantity")).orElse("0"));
				Integer initialBonusQuantity = Integer.parseInt(Optional.ofNullable(caseData.get("initialBonusQuantity")).orElse("0"));
				String needShip = Optional.ofNullable(caseData.get("needShip")).orElse("");
				String unit = Optional.ofNullable(caseData.get("unit")).orElse("");
				String invalidStatus = Optional.ofNullable(caseData.get("invalidStatus")).orElse("");
				orderBatchInsertData01.insertOrderApplicationPlanDetailData01(OrderAllocationPlanDetailDTO.builder()
						.allocationPlanDetailId(allocationPlanDetailId)
						.allocationPlanId(allocationPlanId)
						.applicationFormDetailId(applicationFormDetailId)
						.customerEntSapCode(customerSapCode)
						.saleOrgSapCode(saleOrgSapCode)
						.regionCode(regionCode)
						.regionName(regionName)
						.originalItemSapCode(originalItemSapCode)
						.originalDistributionChannelCode(originalDistributionChannelCode)
						.originalDistributionChannelName(originalDistributionChannelName)
						.giftItemSapCode(giftItemSapCode)
						.giftDistributionChannelCode(giftDistributionChannelCode)
						.giftDistributionChannelName(giftDistributionChannelName)
						.allocationQuantity(allocationQuantity)
						.originalQuantity(originalQuantity)
						.giftQuantity(giftQuantity)
						.initialBonusQuantity(initialBonusQuantity)
						.unit(unit)
						.lotNo(lotNo)
						.productionDate(beginDate)
						.expirationDate(endDate)
						.needShip(needShip)
						.invalidStatus(invalidStatus)
						.remark(remark)
						.createPerson("at")
						.createTime(createTime)
						.build());
			});
		});
		And("[整单折扣分配计划分摊02]添加商品", (DataTable dataTable) -> {
			log.info("添加商品");
		});
		And("[整单折扣分配计划分摊02]选择项目", (DataTable dataTable) -> {
			log.info("选择项目");
		});
		Then("[整单折扣分配计划分摊02]创建订单应校验通过且提交成功", (DataTable dataTable) -> {
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
			List<OrderDiscountCalcCalculateReqGroupListListDTO> orderDiscountCalcCalculateReqGroupListListDTOS = new ArrayList<>();
			dataTable.asMaps().forEach(caseData -> {
				String id = "100000" + Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String orderWideDiscountSubject = Optional.ofNullable(caseData.get("orderWideDiscountSubject")).orElse("");
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String needShip = Optional.ofNullable(caseData.get("needShip")).orElse("");
				String unitPrice = new BigDecimal(caseData.get("unitPrice")).setScale(2, RoundingMode.HALF_UP).toString();
				String quantity = Optional.ofNullable(caseData.get("quantity")).orElse("");
				String boxRatio = new Integer(caseData.get("boxRatio")).toString();
				String preDiscount = new BigDecimal(caseData.get("preDiscount")).setScale(2, RoundingMode.HALF_UP).toString();
				if ("0.00".equals(preDiscount)) {
					id = ""; // 如果是"0"，则将id设置为空字符串
				}
				String calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(2, RoundingMode.HALF_UP).toString();
				String totalInline = new BigDecimal(caseData.get("totalInline")).setScale(2, RoundingMode.HALF_UP).toString();
				String preDiscountTotal = new BigDecimal(caseData.get("preDiscountTotal")).setScale(2, RoundingMode.HALF_UP).toString();
				String orderDiscountTotal = new BigDecimal(caseData.get("orderDiscountTotal")).setScale(2, RoundingMode.HALF_UP).toString();
				String remark = Optional.ofNullable(caseData.get("remark")).orElse("");
				String discountProportion = String.format("%.4f", new BigDecimal(caseData.get("preDiscountTotal").toString()).add(new BigDecimal(caseData.get("orderDiscountTotal").toString())).divide(new BigDecimal(caseData.get("totalInline").toString()), 4, RoundingMode.HALF_UP).doubleValue());
				getAllocationPlanInfoByRemark(remark);
				orderDiscountCalcCalculateReqGroupListListDTOS.add(OrderDiscountCalcCalculateReqGroupListListDTO.builder()
						.orderWideDiscountSubject(orderWideDiscountSubject)
						.discountAllocationPlanId(allocationPlanId)
						.discountAllocationPlanDetailId(allocationPlanDetailId)
						.discountId(id)
						.preDiscountDetailId(id)
						.itemSapCode(itemSapCode)
						.itemName(ProductEnum.fetchProductName(itemSapCode))
						.itemDesc(ProductEnum.fetchProductSpec(itemSapCode))
						.itemGroupCode("01")
						.itemGroupName("成品药-非管制药品")
						.distributionChannelCode("22")
						.distributionChannelName("分销-非医疗")
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
						.discountProportion(discountProportion)
						.build());
			});
			OrderDiscountCalcCalculateReqDTO calculateReq = orderApiOrderManagement01.discountCalcAfterData(OrderDataDTO.builder()
					.orderType("STANDARD")
					.deliveryNo(customerSapCode)
					.saleOrgSapCode(saleOrgSapCode)
					.saleOrgName(SaleOrgEnum.fetchName(saleOrgSapCode))
					.saleOrgZtCode(SaleOrgEnum.fetchZtCode(saleOrgSapCode))
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
					.contractRemark("自动化测试-合同备注")
					.remark("自动化测试-订单备注")
					.calculateReq(OrderDiscountCalcCalculateReqDTO.builder()
							.totalInline(totalInlineSum)
							.preDiscountTotal(preDiscountTotalSum)
							.orderDiscountTotal(orderDiscountTotalSum)
							.groupList(Arrays.asList(OrderDiscountCalcCalculateReqGroupListDTO.builder()
									.itemGroupCode("01")
									.itemGroupName("成品药-非管制药品")
									.distributionChannelCode("22")
									.distributionChannelName("分销-非医疗")
									.totalInline(totalInlineSum)
									.preDiscountTotal(preDiscountTotalSum)
									.orderDiscountTotal(orderDiscountTotalSum)
									.list(orderDiscountCalcCalculateReqGroupListListDTOS)
									.build()))
							.build()
					)
					.build());
			Response createOrderVerifyResponse = orderApiOrderManagement01.createOrderVerify(OrderDataDTO.builder()
					.orderType("STANDARD")
					.deliveryNo(customerSapCode)
					.saleOrgSapCode(saleOrgSapCode)
					.saleOrgName(SaleOrgEnum.fetchName(saleOrgSapCode))
					.saleOrgZtCode(SaleOrgEnum.fetchZtCode(saleOrgSapCode))
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
					.contractRemark("自动化测试-合同备注")
					.remark("自动化测试-订单备注")
					.calculateReq(calculateReq)
					.build());
			createOrderVerifyResponse.then().body("resultCode", Matchers.equalTo("0"), "resultMsg", Matchers.equalTo("success"));
			Response createOrderSubmitResponse = orderApiOrderManagement01.createOrderSubmit(OrderDataDTO.builder()
					.orderType("STANDARD")
					.deliveryNo(customerSapCode)
					.saleOrgSapCode(saleOrgSapCode)
					.saleOrgName(SaleOrgEnum.fetchName(saleOrgSapCode))
					.saleOrgZtCode(SaleOrgEnum.fetchZtCode(saleOrgSapCode))
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
					.contractRemark("自动化测试-合同备注")
					.remark("自动化测试-订单备注")
					.calculateReq(calculateReq)
					.build());
			createOrderSubmitResponse.then().body("resultCode", Matchers.equalTo("0"), "resultMsg", Matchers.equalTo("success"));
		});
		Then("[整单折扣分配计划分摊02]订单整单折扣使用记录成功", (DataTable dataTable) -> {
			List<Map<String, String>> caseDataMap = new ArrayList<>();
			String orderNo = orderApiOrderManagement01.selectOrderInfo(OrderQueryPageByConditionDTO.builder()
					.state("PENDING_BUSINESS_REVIEW")
					.cancelStatus("NOT_CANCEL")
					.saleOrgSapCode(saleOrgSapCode)
					.customerCodeOrName(customerSapCode)
					.build());
			String parentsOrderNo = StringUtils.left(orderNo, 22);
			dataTable.asMaps().forEach(caseData -> {
				Map<String, String> processedData = new HashMap<>();
				processedData.put("parentOrderNo", parentsOrderNo);
				processedData.put("parentOrderItemNo", Optional.ofNullable(caseData.get("parentOrderItemNo")).orElse(""));
				processedData.put("orderNo", orderNo);
				processedData.put("orderItemNo", Optional.ofNullable(caseData.get("orderItemNo")).orElse(""));
				String remark = Optional.ofNullable(caseData.get("remark")).orElse("");
				getAllocationPlanInfoByRemark(remark);
				processedData.put("orderDiscountApplicationPlanId", allocationPlanId);
				processedData.put("orderDiscountApplicationPlanDetailId", allocationPlanDetailId);
				processedData.put("operateType", Optional.ofNullable(caseData.get("operateType")).orElse(""));
				processedData.put("operateAmount", Optional.ofNullable(caseData.get("operateAmount")).orElse(""));
				caseDataMap.add(processedData);
			});
			List<Map<String, Object>> sqlResult = orderDiscountDetailBatchSelectData01.queryOrderDiscountDetailData01(parentsOrderNo);
			discountCheckAllocationPlan01.checkDiscountAllocationPlan(caseDataMap, sqlResult);
			orderBatchClearData01.clearOrderData01(orderNo);
			priceBatchClearData01.clearPirceData01("at");
			orderBatchClearData01.clearOrderPreDiscountData01("at");
			orderBatchClearData01.clearDiscountData01("at");
		});
	}


	private void scenario03() {
		Given("[整单折扣分配计划分摊03]当前存在如下客户信息", (DataTable dataTable) -> {
			createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis());
			domainDataTable = dataTable;
			domainDataTable.asMaps().forEach(caseData -> {
				saleOrgSapCode = Optional.ofNullable(caseData.get("saleOrgSapCode")).orElse("");
				regionCode = Optional.ofNullable(caseData.get("regionCode")).orElse("");
				regionName = Optional.ofNullable(caseData.get("regionName")).orElse("");
				customerSapCode = Optional.ofNullable(caseData.get("customerSapCode")).orElse("");
				shippingAddress = Optional.ofNullable(caseData.get("shippingAddress")).orElse("");
				shippingRecipient = Optional.ofNullable(caseData.get("shippingRecipient")).orElse("");
				shippingTelephone = Optional.ofNullable(caseData.get("shippingTelephone")).orElse("");
			});
		});
		When("[整单折扣分配计划分摊03]当前存在如下的预折商品信息", (DataTable dataTable) -> {
			preDiscountId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			preDiscountDetailId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			beginDate = new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis());
			endDate = new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis() + 24 * 60 * 60 * 1000);
			orderBatchInsertData01.insertOrderPreDidcountData01(OrderPreDiscountDTO.builder()
					.preDiscountId(preDiscountId)
					.customerSapCode(customerSapCode)
					.saleOrgSapCode(saleOrgSapCode)
					.regionCode("")
					.regionName("")
					.effectiveState("ENABLE")
					.preType("CUSTOMER_PRE_DISCOUNT")
					.createPerson("at")
					.createTime(createTime)
					.build());
			dataTable.asMaps().stream().forEach(caseData -> {
				String id = "100000" + Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				BigDecimal currentPrice = new BigDecimal(caseData.get("currentPrice")).setScale(8, RoundingMode.HALF_UP);
				String calculationType = Optional.ofNullable(caseData.get("calculationType")).orElse("");
				BigDecimal calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(8, RoundingMode.HALF_UP);
				String preType = Optional.ofNullable(caseData.get("preType")).orElse("");
				String effectiveState = Optional.ofNullable(caseData.get("effectiveState")).orElse("");
				String distributionChannelCode = Optional.ofNullable(caseData.get("distributionChannelCode")).orElse("");
				String distributionChannelName = Optional.ofNullable(caseData.get("distributionChannelName")).orElse("");
				orderBatchInsertData01.insertOrderPreDidcountDetailData01(OrderPreDiscountDetailDTO.builder()
						.id(id)
						.preDiscountDetailId(preDiscountDetailId)
						.preDiscountId(preDiscountId)
						.itemSapCode(itemSapCode)
						.distributionChannelCode(distributionChannelCode)
						.distributionChannelName(distributionChannelName)
						.currentPrice(currentPrice)
						.calculationType(calculationType)
						.calculationValue(calculationValue)
						.beginDate(beginDate)
						.endDate(endDate)
						.effectiveState(effectiveState)
						.preType("CUSTOMER_PRE_DISCOUNT")
						.createPerson("at")
						.createTime(createTime)
						.build()
				);
			});
		});
		And("[整单折扣分配计划分摊03]当前存在如下的预折折扣信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				BigDecimal calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(8, RoundingMode.HALF_UP);
				String effectiveState = Optional.ofNullable(caseData.get("effectiveState")).orElse("");
				orderBatchUpdateData01.updateOrderPreDiscountDetailCalculationValue(OrderPreDiscountDetailDTO.builder().effectiveState(effectiveState).calculationValue(calculationValue).itemSapCode(itemSapCode).build(), OrderPreDiscountDTO.builder().customerSapCode(customerSapCode).build());
			});
		});
		And("[整单折扣分配计划分摊03]当前存在如下的整单折扣申请单信息", (DataTable dataTable) -> {
			applicationFormId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			processOrderNo = "ZDZK" + new SimpleDateFormat("yyyyMMddHHmm").format(System.currentTimeMillis());
			dataTable.asMaps().stream().forEach(caseData -> {
				String projectType = Optional.ofNullable(caseData.get("projectType")).orElse("");
				String module = Optional.ofNullable(caseData.get("module")).orElse("");
				orderBatchInsertData01.insertOrderApplicationFormData01(OrderApplicationFormDTO.builder()
						.applicationFormId(applicationFormId)
						.processOrderNo(processOrderNo)
						.applicant("at-OTC分公司")
						.applicationDate(createTime)
						.projectType(projectType)
						.module(module)
						.status("APPROVED")
						.executionStartTime(beginDate)
						.executionEndTime(endDate)
						.saleOrgSapCode(saleOrgSapCode)
						.createPerson("at")
						.createTime(createTime)
						.build());
			});
		});
		And("[整单折扣分配计划分摊03]当前存在如下的整单折扣申请单商品信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				applicationFormDetailId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String quantity = Optional.ofNullable(caseData.get("quantity")).orElse("");
				String units = Optional.ofNullable(caseData.get("units")).orElse("");
				orderBatchInsertData01.insertOrderApplicationFormDetailData01(OrderApplicationFormDetailDTO.builder()
						.applicationFormDetailId(applicationFormDetailId)
						.applicationFormId(applicationFormId)
						.itemSapCode(itemSapCode)
						.quantity(quantity)
						.units(units)
						.createPerson("at")
						.createTime(createTime)
						.build());
			});
		});
		And("[整单折扣分配计划分摊03]当前存在如下的整单折扣分配计划信息", () -> {
			allocationPlanId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			planMonth = new SimpleDateFormat("yyyy-MM").format(System.currentTimeMillis());
			orderBatchInsertData01.insertOrderAllocationPlanData01(OrderAllocationPlanDTO.builder()
					.allocationPlanId(allocationPlanId)
					.orderDiscountApplicationFormId(applicationFormId)
					.status("IN_PROGRESS")
					.lockStatus("UNLOCK")
					.planMonth(planMonth)
					.newExecutionStartTime(beginDate)
					.newExecutionEndTime(endDate)
					.createPerson("at")
					.createTime(createTime)
					.build());
		});
		And("[整单折扣分配计划分摊03]当前存在如下的整单折扣分配计划详细信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String lotNo = Optional.ofNullable(caseData.get("lot_no")).orElse("");
				String remark = Optional.ofNullable(caseData.get("remark")).orElse("");
				allocationPlanDetailId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
				setAllocationPlanInfoByRemark(remark, allocationPlanId, allocationPlanDetailId);
				String originalItemSapCode = Optional.ofNullable(caseData.get("originalItemSapCode")).orElse("");
				String originalDistributionChannelCode = Optional.ofNullable(caseData.get("originalDistributionChannelCode")).orElse("");
				String originalDistributionChannelName = Optional.ofNullable(caseData.get("originalDistributionChannelName")).orElse("");
				String giftItemSapCode = Optional.ofNullable(caseData.get("giftItemSapCode")).orElse("");
				String giftDistributionChannelCode = Optional.ofNullable(caseData.get("giftDistributionChannelCode")).orElse("");
				String giftDistributionChannelName = Optional.ofNullable(caseData.get("giftDistributionChannelName")).orElse("");
				String allocationQuantity = Optional.ofNullable(caseData.get("allocationQuantity")).orElse("");
				Integer originalQuantity = Integer.valueOf(Optional.ofNullable(caseData.get("originalQuantity")).orElse("0"));
				Integer giftQuantity = Integer.parseInt(Optional.ofNullable(caseData.get("giftQuantity")).orElse("0"));
				Integer initialBonusQuantity = Integer.parseInt(Optional.ofNullable(caseData.get("initialBonusQuantity")).orElse("0"));
				String needShip = Optional.ofNullable(caseData.get("needShip")).orElse("");
				String unit = Optional.ofNullable(caseData.get("unit")).orElse("");
				String invalidStatus = Optional.ofNullable(caseData.get("invalidStatus")).orElse("");
				orderBatchInsertData01.insertOrderApplicationPlanDetailData01(OrderAllocationPlanDetailDTO.builder()
						.allocationPlanDetailId(allocationPlanDetailId)
						.allocationPlanId(allocationPlanId)
						.applicationFormDetailId(applicationFormDetailId)
						.customerEntSapCode(customerSapCode)
						.saleOrgSapCode(saleOrgSapCode)
						.regionCode(regionCode)
						.regionName(regionName)
						.originalItemSapCode(originalItemSapCode)
						.originalDistributionChannelCode(originalDistributionChannelCode)
						.originalDistributionChannelName(originalDistributionChannelName)
						.giftItemSapCode(giftItemSapCode)
						.giftDistributionChannelCode(giftDistributionChannelCode)
						.giftDistributionChannelName(giftDistributionChannelName)
						.allocationQuantity(allocationQuantity)
						.originalQuantity(originalQuantity)
						.giftQuantity(giftQuantity)
						.initialBonusQuantity(initialBonusQuantity)
						.unit(unit)
						.lotNo(lotNo)
						.productionDate(beginDate)
						.expirationDate(endDate)
						.needShip(needShip)
						.invalidStatus(invalidStatus)
						.remark(remark)
						.createPerson("at")
						.createTime(createTime)
						.build());
			});
		});
		And("[整单折扣分配计划分摊03]添加商品", (DataTable dataTable) -> {
			log.info("添加商品");
		});
		And("[整单折扣分配计划分摊03]选择项目", (DataTable dataTable) -> {
			log.info("选择项目");
		});
		Then("[整单折扣分配计划分摊03]创建订单应校验通过且提交成功", (DataTable dataTable) -> {
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
			List<OrderDiscountCalcCalculateReqGroupListListDTO> orderDiscountCalcCalculateReqGroupListListDTOS = new ArrayList<>();
			dataTable.asMaps().forEach(caseData -> {
				String id = "100000" + Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String orderWideDiscountSubject = Optional.ofNullable(caseData.get("orderWideDiscountSubject")).orElse("");
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String needShip = Optional.ofNullable(caseData.get("needShip")).orElse("");
				String unitPrice = new BigDecimal(caseData.get("unitPrice")).setScale(2, RoundingMode.HALF_UP).toString();
				String quantity = Optional.ofNullable(caseData.get("quantity")).orElse("");
				String boxRatio = new Integer(caseData.get("boxRatio")).toString();
				String preDiscount = new BigDecimal(caseData.get("preDiscount")).setScale(2, RoundingMode.HALF_UP).toString();
				if ("0.00".equals(preDiscount)) {
					id = ""; // 如果是"0"，则将id设置为空字符串
				}
				String calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(2, RoundingMode.HALF_UP).toString();
				String totalInline = new BigDecimal(caseData.get("totalInline")).setScale(2, RoundingMode.HALF_UP).toString();
				String preDiscountTotal = new BigDecimal(caseData.get("preDiscountTotal")).setScale(2, RoundingMode.HALF_UP).toString();
				String orderDiscountTotal = new BigDecimal(caseData.get("orderDiscountTotal")).setScale(2, RoundingMode.HALF_UP).toString();
				String discountProportion = String.format("%.4f", new BigDecimal(caseData.get("preDiscountTotal").toString()).add(new BigDecimal(caseData.get("orderDiscountTotal").toString())).divide(new BigDecimal(caseData.get("totalInline").toString()), 4, RoundingMode.HALF_UP).doubleValue());
				String remark = Optional.ofNullable(caseData.get("remark")).orElse("");
				getAllocationPlanInfoByRemark(remark);
				orderDiscountCalcCalculateReqGroupListListDTOS.add(OrderDiscountCalcCalculateReqGroupListListDTO.builder()
						.orderWideDiscountSubject(orderWideDiscountSubject)
						.discountAllocationPlanId(allocationPlanId)
						.discountAllocationPlanDetailId(allocationPlanDetailId)
						.discountId(id)
						.preDiscountDetailId(id)
						.itemSapCode(itemSapCode)
						.itemName(ProductEnum.fetchProductName(itemSapCode))
						.itemDesc(ProductEnum.fetchProductSpec(itemSapCode))
						.itemGroupCode("01")
						.itemGroupName("成品药-非管制药品")
						.distributionChannelCode("22")
						.distributionChannelName("分销-非医疗")
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
						.discountProportion(discountProportion)
						.build());
			});
			OrderDiscountCalcCalculateReqDTO calculateReq = orderApiOrderManagement01.discountCalcAfterData(OrderDataDTO.builder()
					.orderType("STANDARD")
					.deliveryNo(customerSapCode)
					.saleOrgSapCode(saleOrgSapCode)
					.saleOrgName(SaleOrgEnum.fetchName(saleOrgSapCode))
					.saleOrgZtCode(SaleOrgEnum.fetchZtCode(saleOrgSapCode))
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
					.contractRemark("自动化测试-合同备注")
					.remark("自动化测试-订单备注")
					.calculateReq(OrderDiscountCalcCalculateReqDTO.builder()
							.totalInline(totalInlineSum)
							.preDiscountTotal(preDiscountTotalSum)
							.orderDiscountTotal(orderDiscountTotalSum)
							.groupList(Arrays.asList(OrderDiscountCalcCalculateReqGroupListDTO.builder()
									.itemGroupCode("01")
									.itemGroupName("成品药-非管制药品")
									.distributionChannelCode("22")
									.distributionChannelName("分销-非医疗")
									.totalInline(totalInlineSum)
									.preDiscountTotal(preDiscountTotalSum)
									.orderDiscountTotal(orderDiscountTotalSum)
									.list(orderDiscountCalcCalculateReqGroupListListDTOS)
									.build()))
							.build()
					)
					.build());
			Response createOrderVerifyResponse = orderApiOrderManagement01.createOrderVerify(OrderDataDTO.builder()
					.orderType("STANDARD")
					.deliveryNo(customerSapCode)
					.saleOrgSapCode(saleOrgSapCode)
					.saleOrgName(SaleOrgEnum.fetchName(saleOrgSapCode))
					.saleOrgZtCode(SaleOrgEnum.fetchZtCode(saleOrgSapCode))
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
					.contractRemark("自动化测试-合同备注")
					.remark("自动化测试-订单备注")
					.calculateReq(calculateReq)
					.build());
			createOrderVerifyResponse.then().body("resultCode", Matchers.equalTo("0"), "resultMsg", Matchers.equalTo("success"));
			Response createOrderSubmitResponse = orderApiOrderManagement01.createOrderSubmit(OrderDataDTO.builder()
					.orderType("STANDARD")
					.deliveryNo(customerSapCode)
					.saleOrgSapCode(saleOrgSapCode)
					.saleOrgName(SaleOrgEnum.fetchName(saleOrgSapCode))
					.saleOrgZtCode(SaleOrgEnum.fetchZtCode(saleOrgSapCode))
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
					.contractRemark("自动化测试-合同备注")
					.remark("自动化测试-订单备注")
					.calculateReq(calculateReq)
					.build());
			createOrderSubmitResponse.then().body("resultCode", Matchers.equalTo("0"), "resultMsg", Matchers.equalTo("success"));
		});
		Then("[整单折扣分配计划分摊03]订单整单折扣使用记录成功", (DataTable dataTable) -> {
			List<Map<String, String>> caseDataMap = new ArrayList<>();
			String orderNo = orderApiOrderManagement01.selectOrderInfo(OrderQueryPageByConditionDTO.builder()
					.state("PENDING_BUSINESS_REVIEW")
					.cancelStatus("NOT_CANCEL")
					.saleOrgSapCode(saleOrgSapCode)
					.customerCodeOrName(customerSapCode)
					.build());
			String parentsOrderNo = StringUtils.left(orderNo, 22);
			dataTable.asMaps().forEach(caseData -> {
				Map<String, String> processedData = new HashMap<>();
				processedData.put("parentOrderNo", parentsOrderNo);
				processedData.put("parentOrderItemNo", Optional.ofNullable(caseData.get("parentOrderItemNo")).orElse(""));
				processedData.put("orderNo", orderNo);
				processedData.put("orderItemNo", Optional.ofNullable(caseData.get("orderItemNo")).orElse(""));
				String remark = Optional.ofNullable(caseData.get("remark")).orElse("");
				getAllocationPlanInfoByRemark(remark);
				processedData.put("orderDiscountApplicationPlanId", allocationPlanId);
				processedData.put("orderDiscountApplicationPlanDetailId", allocationPlanDetailId);
				processedData.put("operateType", Optional.ofNullable(caseData.get("operateType")).orElse(""));
				processedData.put("operateAmount", Optional.ofNullable(caseData.get("operateAmount")).orElse(""));
				caseDataMap.add(processedData);
			});
			List<Map<String, Object>> sqlResult = orderDiscountDetailBatchSelectData01.queryOrderDiscountDetailData01(parentsOrderNo);
			discountCheckAllocationPlan01.checkDiscountAllocationPlan(caseDataMap, sqlResult);
			orderBatchClearData01.clearOrderData01(orderNo);
			priceBatchClearData01.clearPirceData01("at");
			orderBatchClearData01.clearOrderPreDiscountData01("at");
			orderBatchClearData01.clearDiscountData01("at");
		});
	}

	private void scenario04() {
		Given("[整单折扣分配计划分摊04]当前存在如下客户信息", (DataTable dataTable) -> {
			createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis());
			domainDataTable = dataTable;
			domainDataTable.asMaps().forEach(caseData -> {
				saleOrgSapCode = Optional.ofNullable(caseData.get("saleOrgSapCode")).orElse("");
				regionCode = Optional.ofNullable(caseData.get("regionCode")).orElse("");
				regionName = Optional.ofNullable(caseData.get("regionName")).orElse("");
				customerSapCode = Optional.ofNullable(caseData.get("customerSapCode")).orElse("");
				shippingAddress = Optional.ofNullable(caseData.get("shippingAddress")).orElse("");
				shippingRecipient = Optional.ofNullable(caseData.get("shippingRecipient")).orElse("");
				shippingTelephone = Optional.ofNullable(caseData.get("shippingTelephone")).orElse("");
			});
		});
		When("[整单折扣分配计划分摊04]当前存在如下的预折商品信息", (DataTable dataTable) -> {
			preDiscountId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			preDiscountDetailId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			beginDate = new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis());
			endDate = new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis() + 24 * 60 * 60 * 1000);
			orderBatchInsertData01.insertOrderPreDidcountData01(OrderPreDiscountDTO.builder()
					.preDiscountId(preDiscountId)
					.customerSapCode(customerSapCode)
					.saleOrgSapCode(saleOrgSapCode)
					.regionCode("")
					.regionName("")
					.effectiveState("ENABLE")
					.preType("CUSTOMER_PRE_DISCOUNT")
					.createPerson("at")
					.createTime(createTime)
					.build());
			dataTable.asMaps().stream().forEach(caseData -> {
				String id = "100000" + Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				BigDecimal currentPrice = new BigDecimal(caseData.get("currentPrice")).setScale(8, RoundingMode.HALF_UP);
				String calculationType = Optional.ofNullable(caseData.get("calculationType")).orElse("");
				BigDecimal calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(8, RoundingMode.HALF_UP);
				String preType = Optional.ofNullable(caseData.get("preType")).orElse("");
				String effectiveState = Optional.ofNullable(caseData.get("effectiveState")).orElse("");
				String distributionChannelCode = Optional.ofNullable(caseData.get("distributionChannelCode")).orElse("");
				String distributionChannelName = Optional.ofNullable(caseData.get("distributionChannelName")).orElse("");
				orderBatchInsertData01.insertOrderPreDidcountDetailData01(OrderPreDiscountDetailDTO.builder()
						.id(id)
						.preDiscountDetailId(preDiscountDetailId)
						.preDiscountId(preDiscountId)
						.itemSapCode(itemSapCode)
						.distributionChannelCode(distributionChannelCode)
						.distributionChannelName(distributionChannelName)
						.currentPrice(currentPrice)
						.calculationType(calculationType)
						.calculationValue(calculationValue)
						.beginDate(beginDate)
						.endDate(endDate)
						.effectiveState(effectiveState)
						.preType("CUSTOMER_PRE_DISCOUNT")
						.createPerson("at")
						.createTime(createTime)
						.build()
				);
			});
		});
		And("[整单折扣分配计划分摊04]当前存在如下的预折折扣信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				BigDecimal calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(8, RoundingMode.HALF_UP);
				String effectiveState = Optional.ofNullable(caseData.get("effectiveState")).orElse("");
				orderBatchUpdateData01.updateOrderPreDiscountDetailCalculationValue(OrderPreDiscountDetailDTO.builder().effectiveState(effectiveState).calculationValue(calculationValue).itemSapCode(itemSapCode).build(), OrderPreDiscountDTO.builder().customerSapCode(customerSapCode).build());
			});
		});
		And("[整单折扣分配计划分摊04]当前存在如下的整单折扣申请单信息", (DataTable dataTable) -> {
			applicationFormId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			processOrderNo = "ZDZK" + new SimpleDateFormat("yyyyMMddHHmm").format(System.currentTimeMillis());
			dataTable.asMaps().stream().forEach(caseData -> {
				String projectType = Optional.ofNullable(caseData.get("projectType")).orElse("");
				String module = Optional.ofNullable(caseData.get("module")).orElse("");
				orderBatchInsertData01.insertOrderApplicationFormData01(OrderApplicationFormDTO.builder()
						.applicationFormId(applicationFormId)
						.processOrderNo(processOrderNo)
						.applicant("at-OTC分公司")
						.applicationDate(createTime)
						.projectType(projectType)
						.module(module)
						.status("APPROVED")
						.executionStartTime(beginDate)
						.executionEndTime(endDate)
						.saleOrgSapCode(saleOrgSapCode)
						.createPerson("at")
						.createTime(createTime)
						.build());
			});
		});
		And("[整单折扣分配计划分摊04]当前存在如下的整单折扣申请单商品信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				applicationFormDetailId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String quantity = Optional.ofNullable(caseData.get("quantity")).orElse("");
				String units = Optional.ofNullable(caseData.get("units")).orElse("");
				orderBatchInsertData01.insertOrderApplicationFormDetailData01(OrderApplicationFormDetailDTO.builder()
						.applicationFormDetailId(applicationFormDetailId)
						.applicationFormId(applicationFormId)
						.itemSapCode(itemSapCode)
						.quantity(quantity)
						.units(units)
						.createPerson("at")
						.createTime(createTime)
						.build());
			});
		});
		And("[整单折扣分配计划分摊04]当前存在如下的[零头药-AT_FP001]整单折扣分配计划信息", () -> {
			allocationPlanId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			allocationPlanId_001 = allocationPlanId;
			planMonth = new SimpleDateFormat("yyyy-MM").format(System.currentTimeMillis());
			orderBatchInsertData01.insertOrderAllocationPlanData01(OrderAllocationPlanDTO.builder()
					.allocationPlanId(allocationPlanId)
					.orderDiscountApplicationFormId(applicationFormId)
					.status("IN_PROGRESS")
					.lockStatus("UNLOCK")
					.planMonth(planMonth)
					.newExecutionStartTime(beginDate)
					.newExecutionEndTime(endDate)
					.createPerson("at")
					.createTime(createTime)
					.build());
		});
		And("[整单折扣分配计划分摊04]当前存在如下的[零头药-AT_FP001]整单折扣分配计划详细信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String lotNo = Optional.ofNullable(caseData.get("lot_no")).orElse("");
				String remark = Optional.ofNullable(caseData.get("remark")).orElse("");
				allocationPlanDetailId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
				setAllocationPlanInfoByRemark(remark, allocationPlanId, allocationPlanDetailId);
				String originalItemSapCode = Optional.ofNullable(caseData.get("originalItemSapCode")).orElse("");
				String originalDistributionChannelCode = Optional.ofNullable(caseData.get("originalDistributionChannelCode")).orElse("");
				String originalDistributionChannelName = Optional.ofNullable(caseData.get("originalDistributionChannelName")).orElse("");
				String giftItemSapCode = Optional.ofNullable(caseData.get("giftItemSapCode")).orElse("");
				String giftDistributionChannelCode = Optional.ofNullable(caseData.get("giftDistributionChannelCode")).orElse("");
				String giftDistributionChannelName = Optional.ofNullable(caseData.get("giftDistributionChannelName")).orElse("");
				String allocationQuantity = Optional.ofNullable(caseData.get("allocationQuantity")).orElse("");
				Integer originalQuantity = Integer.valueOf(Optional.ofNullable(caseData.get("originalQuantity")).orElse("0"));
				Integer giftQuantity = Integer.parseInt(Optional.ofNullable(caseData.get("giftQuantity")).orElse("0"));
				Integer initialBonusQuantity = Integer.parseInt(Optional.ofNullable(caseData.get("initialBonusQuantity")).orElse("0"));
				String needShip = Optional.ofNullable(caseData.get("needShip")).orElse("");
				String unit = Optional.ofNullable(caseData.get("unit")).orElse("");
				String invalidStatus = Optional.ofNullable(caseData.get("invalidStatus")).orElse("");
				orderBatchInsertData01.insertOrderApplicationPlanDetailData01(OrderAllocationPlanDetailDTO.builder()
						.allocationPlanDetailId(allocationPlanDetailId)
						.allocationPlanId(allocationPlanId)
						.applicationFormDetailId(applicationFormDetailId)
						.customerEntSapCode(customerSapCode)
						.saleOrgSapCode(saleOrgSapCode)
						.regionCode(regionCode)
						.regionName(regionName)
						.originalItemSapCode(originalItemSapCode)
						.originalDistributionChannelCode(originalDistributionChannelCode)
						.originalDistributionChannelName(originalDistributionChannelName)
						.giftItemSapCode(giftItemSapCode)
						.giftDistributionChannelCode(giftDistributionChannelCode)
						.giftDistributionChannelName(giftDistributionChannelName)
						.allocationQuantity(allocationQuantity)
						.originalQuantity(originalQuantity)
						.giftQuantity(giftQuantity)
						.initialBonusQuantity(initialBonusQuantity)
						.unit(unit)
						.lotNo(lotNo)
						.productionDate(beginDate)
						.expirationDate(endDate)
						.needShip(needShip)
						.invalidStatus(invalidStatus)
						.remark(remark)
						.createPerson("at")
						.createTime(createTime)
						.build());
			});
		});
		And("[整单折扣分配计划分摊04]当前存在如下的[零头药-AT_FP002]整单折扣分配计划信息", () -> {
			allocationPlanId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			planMonth = new SimpleDateFormat("yyyy-MM").format(System.currentTimeMillis());
			orderBatchInsertData01.insertOrderAllocationPlanData01(OrderAllocationPlanDTO.builder()
					.allocationPlanId(allocationPlanId)
					.orderDiscountApplicationFormId(applicationFormId)
					.status("IN_PROGRESS")
					.lockStatus("UNLOCK")
					.planMonth(planMonth)
					.newExecutionStartTime(beginDate)
					.newExecutionEndTime(endDate)
					.createPerson("at")
					.createTime(createTime)
					.build());
		});
		And("[整单折扣分配计划分摊04]当前存在如下的[零头药-AT_FP002]整单折扣分配计划详细信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String lotNo = Optional.ofNullable(caseData.get("lot_no")).orElse("");
				String remark = Optional.ofNullable(caseData.get("remark")).orElse("");
				allocationPlanDetailId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
				setAllocationPlanInfoByRemark(remark, allocationPlanId, allocationPlanDetailId);
				String originalItemSapCode = Optional.ofNullable(caseData.get("originalItemSapCode")).orElse("");
				String originalDistributionChannelCode = Optional.ofNullable(caseData.get("originalDistributionChannelCode")).orElse("");
				String originalDistributionChannelName = Optional.ofNullable(caseData.get("originalDistributionChannelName")).orElse("");
				String giftItemSapCode = Optional.ofNullable(caseData.get("giftItemSapCode")).orElse("");
				String giftDistributionChannelCode = Optional.ofNullable(caseData.get("giftDistributionChannelCode")).orElse("");
				String giftDistributionChannelName = Optional.ofNullable(caseData.get("giftDistributionChannelName")).orElse("");
				String allocationQuantity = Optional.ofNullable(caseData.get("allocationQuantity")).orElse("");
				Integer originalQuantity = Integer.valueOf(Optional.ofNullable(caseData.get("originalQuantity")).orElse("0"));
				Integer giftQuantity = Integer.parseInt(Optional.ofNullable(caseData.get("giftQuantity")).orElse("0"));
				Integer initialBonusQuantity = Integer.parseInt(Optional.ofNullable(caseData.get("initialBonusQuantity")).orElse("0"));
				String needShip = Optional.ofNullable(caseData.get("needShip")).orElse("");
				String unit = Optional.ofNullable(caseData.get("unit")).orElse("");
				String invalidStatus = Optional.ofNullable(caseData.get("invalidStatus")).orElse("");
				orderBatchInsertData01.insertOrderApplicationPlanDetailData01(OrderAllocationPlanDetailDTO.builder()
						.allocationPlanDetailId(allocationPlanDetailId)
						.allocationPlanId(allocationPlanId)
						.applicationFormDetailId(applicationFormDetailId)
						.customerEntSapCode(customerSapCode)
						.saleOrgSapCode(saleOrgSapCode)
						.regionCode(regionCode)
						.regionName(regionName)
						.originalItemSapCode(originalItemSapCode)
						.originalDistributionChannelCode(originalDistributionChannelCode)
						.originalDistributionChannelName(originalDistributionChannelName)
						.giftItemSapCode(giftItemSapCode)
						.giftDistributionChannelCode(giftDistributionChannelCode)
						.giftDistributionChannelName(giftDistributionChannelName)
						.allocationQuantity(allocationQuantity)
						.originalQuantity(originalQuantity)
						.giftQuantity(giftQuantity)
						.initialBonusQuantity(initialBonusQuantity)
						.unit(unit)
						.lotNo(lotNo)
						.productionDate(beginDate)
						.expirationDate(endDate)
						.needShip(needShip)
						.invalidStatus(invalidStatus)
						.remark(remark)
						.createPerson("at")
						.createTime(createTime)
						.build());
			});
		});
		And("[整单折扣分配计划分摊04]添加商品", (DataTable dataTable) -> {
			log.info("添加商品");
		});
		And("[整单折扣分配计划分摊04]选择项目", (DataTable dataTable) -> {
			log.info("选择项目");
		});
		Then("[整单折扣分配计划分摊04]创建订单应校验通过且提交成功", (DataTable dataTable) -> {
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
			List<OrderDiscountCalcCalculateReqGroupListListDTO> orderDiscountCalcCalculateReqGroupListListDTOS = new ArrayList<>();
			dataTable.asMaps().forEach(caseData -> {
				String id = "100000" + Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String orderWideDiscountSubject = Optional.ofNullable(caseData.get("orderWideDiscountSubject")).orElse("");
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String needShip = Optional.ofNullable(caseData.get("needShip")).orElse("");
				String unitPrice = new BigDecimal(caseData.get("unitPrice")).setScale(2, RoundingMode.HALF_UP).toString();
				String quantity = Optional.ofNullable(caseData.get("quantity")).orElse("");
				String boxRatio = new Integer(caseData.get("boxRatio")).toString();
				String preDiscount = new BigDecimal(caseData.get("preDiscount")).setScale(2, RoundingMode.HALF_UP).toString();
				if ("0.00".equals(preDiscount)) {
					id = ""; // 如果是"0"，则将id设置为空字符串
				}
				String calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(2, RoundingMode.HALF_UP).toString();
				String totalInline = new BigDecimal(caseData.get("totalInline")).setScale(2, RoundingMode.HALF_UP).toString();
				String preDiscountTotal = new BigDecimal(caseData.get("preDiscountTotal")).setScale(2, RoundingMode.HALF_UP).toString();
				String orderDiscountTotal = new BigDecimal(caseData.get("orderDiscountTotal")).setScale(2, RoundingMode.HALF_UP).toString();
				String discountProportion = String.format("%.4f", new BigDecimal(caseData.get("preDiscountTotal").toString()).add(new BigDecimal(caseData.get("orderDiscountTotal").toString())).divide(new BigDecimal(caseData.get("totalInline").toString()), 4, RoundingMode.HALF_UP).doubleValue());
				String remark = Optional.ofNullable(caseData.get("remark")).orElse("");
				getAllocationPlanInfoByRemark(remark);
				orderDiscountCalcCalculateReqGroupListListDTOS.add(OrderDiscountCalcCalculateReqGroupListListDTO.builder()
						.orderWideDiscountSubject(orderWideDiscountSubject)
						.discountAllocationPlanId(allocationPlanId)
						.discountAllocationPlanDetailId(allocationPlanDetailId)
						.discountId(id)
						.preDiscountDetailId(id)
						.itemSapCode(itemSapCode)
						.itemName(ProductEnum.fetchProductName(itemSapCode))
						.itemDesc(ProductEnum.fetchProductSpec(itemSapCode))
						.itemGroupCode("01")
						.itemGroupName("成品药-非管制药品")
						.distributionChannelCode("22")
						.distributionChannelName("分销-非医疗")
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
						.discountProportion(discountProportion)
						.build());
			});
			OrderDiscountCalcCalculateReqDTO calculateReq = orderApiOrderManagement01.discountCalcAfterData(OrderDataDTO.builder()
					.orderType("STANDARD")
					.deliveryNo(customerSapCode)
					.saleOrgSapCode(saleOrgSapCode)
					.saleOrgName(SaleOrgEnum.fetchName(saleOrgSapCode))
					.saleOrgZtCode(SaleOrgEnum.fetchZtCode(saleOrgSapCode))
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
					.contractRemark("自动化测试-合同备注")
					.remark("自动化测试-订单备注")
					.calculateReq(OrderDiscountCalcCalculateReqDTO.builder()
							.totalInline(totalInlineSum)
							.preDiscountTotal(preDiscountTotalSum)
							.orderDiscountTotal(orderDiscountTotalSum)
							.groupList(Arrays.asList(OrderDiscountCalcCalculateReqGroupListDTO.builder()
									.itemGroupCode("01")
									.itemGroupName("成品药-非管制药品")
									.distributionChannelCode("22")
									.distributionChannelName("分销-非医疗")
									.totalInline(totalInlineSum)
									.preDiscountTotal(preDiscountTotalSum)
									.orderDiscountTotal(orderDiscountTotalSum)
									.list(orderDiscountCalcCalculateReqGroupListListDTOS)
									.build()))
							.build()
					)
					.build());
			Response createOrderVerifyResponse = orderApiOrderManagement01.createOrderVerify(OrderDataDTO.builder()
					.orderType("STANDARD")
					.deliveryNo(customerSapCode)
					.saleOrgSapCode(saleOrgSapCode)
					.saleOrgName(SaleOrgEnum.fetchName(saleOrgSapCode))
					.saleOrgZtCode(SaleOrgEnum.fetchZtCode(saleOrgSapCode))
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
					.contractRemark("自动化测试-合同备注")
					.remark("自动化测试-订单备注")
					.calculateReq(calculateReq)
					.build());
			createOrderVerifyResponse.then().body("resultCode", Matchers.equalTo("0"), "resultMsg", Matchers.equalTo("success"));
			Response createOrderSubmitResponse = orderApiOrderManagement01.createOrderSubmit(OrderDataDTO.builder()
					.orderType("STANDARD")
					.deliveryNo(customerSapCode)
					.saleOrgSapCode(saleOrgSapCode)
					.saleOrgName(SaleOrgEnum.fetchName(saleOrgSapCode))
					.saleOrgZtCode(SaleOrgEnum.fetchZtCode(saleOrgSapCode))
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
					.contractRemark("自动化测试-合同备注")
					.remark("自动化测试-订单备注")
					.calculateReq(calculateReq)
					.build());
			createOrderSubmitResponse.then().body("resultCode", Matchers.equalTo("0"), "resultMsg", Matchers.equalTo("success"));
		});
		Then("[整单折扣分配计划分摊04]订单整单折扣使用记录成功", (DataTable dataTable) -> {
			List<Map<String, String>> caseDataMap = new ArrayList<>();
			String orderNo = orderApiOrderManagement01.selectOrderInfo(OrderQueryPageByConditionDTO.builder()
					.state("PENDING_BUSINESS_REVIEW")
					.cancelStatus("NOT_CANCEL")
					.saleOrgSapCode(saleOrgSapCode)
					.customerCodeOrName(customerSapCode)
					.build());
			String parentsOrderNo = StringUtils.left(orderNo, 22);
			dataTable.asMaps().forEach(caseData -> {
				Map<String, String> processedData = new HashMap<>();
				processedData.put("parentOrderNo", parentsOrderNo);
				processedData.put("parentOrderItemNo", Optional.ofNullable(caseData.get("parentOrderItemNo")).orElse(""));
				processedData.put("orderNo", orderNo);
				processedData.put("orderItemNo", Optional.ofNullable(caseData.get("orderItemNo")).orElse(""));
				String remark = Optional.ofNullable(caseData.get("remark")).orElse("");
				getAllocationPlanInfoByRemark(remark);
				processedData.put("orderDiscountApplicationPlanId", allocationPlanId);
				processedData.put("orderDiscountApplicationPlanDetailId", allocationPlanDetailId);
				processedData.put("operateType", Optional.ofNullable(caseData.get("operateType")).orElse(""));
				processedData.put("operateAmount", Optional.ofNullable(caseData.get("operateAmount")).orElse(""));
				caseDataMap.add(processedData);
			});
			List<Map<String, Object>> sqlResult = orderDiscountDetailBatchSelectData01.queryOrderDiscountDetailData01(parentsOrderNo);
			discountCheckAllocationPlan01.checkDiscountAllocationPlan(caseDataMap, sqlResult);
			orderBatchClearData01.clearOrderData01(orderNo);
			priceBatchClearData01.clearPirceData01("at");
			orderBatchClearData01.clearOrderPreDiscountData01("at");
			orderBatchClearData01.clearDiscountData01("at");
		});
	}


	private void scenario05() {
		Given("[整单折扣分配计划分摊05]当前存在如下客户信息", (DataTable dataTable) -> {
			createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis());
			domainDataTable = dataTable;
			domainDataTable.asMaps().forEach(caseData -> {
				saleOrgSapCode = Optional.ofNullable(caseData.get("saleOrgSapCode")).orElse("");
				regionCode = Optional.ofNullable(caseData.get("regionCode")).orElse("");
				regionName = Optional.ofNullable(caseData.get("regionName")).orElse("");
				customerSapCode = Optional.ofNullable(caseData.get("customerSapCode")).orElse("");
				shippingAddress = Optional.ofNullable(caseData.get("shippingAddress")).orElse("");
				shippingRecipient = Optional.ofNullable(caseData.get("shippingRecipient")).orElse("");
				shippingTelephone = Optional.ofNullable(caseData.get("shippingTelephone")).orElse("");
			});
		});
		When("[整单折扣分配计划分摊05]当前存在如下的预折商品信息", (DataTable dataTable) -> {
			preDiscountId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			preDiscountDetailId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			beginDate = new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis());
			endDate = new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis() + 24 * 60 * 60 * 1000);
			orderBatchInsertData01.insertOrderPreDidcountData01(OrderPreDiscountDTO.builder()
					.preDiscountId(preDiscountId)
					.customerSapCode(customerSapCode)
					.saleOrgSapCode(saleOrgSapCode)
					.regionCode("")
					.regionName("")
					.effectiveState("ENABLE")
					.preType("CUSTOMER_PRE_DISCOUNT")
					.createPerson("at")
					.createTime(createTime)
					.build());
			dataTable.asMaps().stream().forEach(caseData -> {
				String id = "100000" + Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				BigDecimal currentPrice = new BigDecimal(caseData.get("currentPrice")).setScale(8, RoundingMode.HALF_UP);
				String calculationType = Optional.ofNullable(caseData.get("calculationType")).orElse("");
				BigDecimal calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(8, RoundingMode.HALF_UP);
				String preType = Optional.ofNullable(caseData.get("preType")).orElse("");
				String effectiveState = Optional.ofNullable(caseData.get("effectiveState")).orElse("");
				String distributionChannelCode = Optional.ofNullable(caseData.get("distributionChannelCode")).orElse("");
				String distributionChannelName = Optional.ofNullable(caseData.get("distributionChannelName")).orElse("");
				orderBatchInsertData01.insertOrderPreDidcountDetailData01(OrderPreDiscountDetailDTO.builder()
						.id(id)
						.preDiscountDetailId(preDiscountDetailId)
						.preDiscountId(preDiscountId)
						.itemSapCode(itemSapCode)
						.distributionChannelCode(distributionChannelCode)
						.distributionChannelName(distributionChannelName)
						.currentPrice(currentPrice)
						.calculationType(calculationType)
						.calculationValue(calculationValue)
						.beginDate(beginDate)
						.endDate(endDate)
						.effectiveState(effectiveState)
						.preType("CUSTOMER_PRE_DISCOUNT")
						.createPerson("at")
						.createTime(createTime)
						.build()
				);
			});
		});
		And("[整单折扣分配计划分摊05]当前存在如下的预折折扣信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				BigDecimal calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(8, RoundingMode.HALF_UP);
				String effectiveState = Optional.ofNullable(caseData.get("effectiveState")).orElse("");
				orderBatchUpdateData01.updateOrderPreDiscountDetailCalculationValue(OrderPreDiscountDetailDTO.builder().effectiveState(effectiveState).calculationValue(calculationValue).itemSapCode(itemSapCode).build(), OrderPreDiscountDTO.builder().customerSapCode(customerSapCode).build());
			});
		});
		And("[整单折扣分配计划分摊05]当前存在如下的整单折扣申请单信息", (DataTable dataTable) -> {
			applicationFormId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			processOrderNo = "ZDZK" + new SimpleDateFormat("yyyyMMddHHmm").format(System.currentTimeMillis());
			dataTable.asMaps().stream().forEach(caseData -> {
				String projectType = Optional.ofNullable(caseData.get("projectType")).orElse("");
				String module = Optional.ofNullable(caseData.get("module")).orElse("");
				orderBatchInsertData01.insertOrderApplicationFormData01(OrderApplicationFormDTO.builder()
						.applicationFormId(applicationFormId)
						.processOrderNo(processOrderNo)
						.applicant("at-OTC分公司")
						.applicationDate(createTime)
						.projectType(projectType)
						.module(module)
						.status("APPROVED")
						.executionStartTime(beginDate)
						.executionEndTime(endDate)
						.saleOrgSapCode(saleOrgSapCode)
						.createPerson("at")
						.createTime(createTime)
						.build());
			});
		});
		And("[整单折扣分配计划分摊05]当前存在如下的整单折扣申请单商品信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				applicationFormDetailId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String quantity = Optional.ofNullable(caseData.get("quantity")).orElse("");
				String units = Optional.ofNullable(caseData.get("units")).orElse("");
				orderBatchInsertData01.insertOrderApplicationFormDetailData01(OrderApplicationFormDetailDTO.builder()
						.applicationFormDetailId(applicationFormDetailId)
						.applicationFormId(applicationFormId)
						.itemSapCode(itemSapCode)
						.quantity(quantity)
						.units(units)
						.createPerson("at")
						.createTime(createTime)
						.build());
			});
		});
		And("[整单折扣分配计划分摊05]当前存在如下的[零头药-AT_FP001]整单折扣分配计划信息", () -> {
			allocationPlanId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			allocationPlanId_001 = allocationPlanId;
			planMonth = new SimpleDateFormat("yyyy-MM").format(System.currentTimeMillis());
			orderBatchInsertData01.insertOrderAllocationPlanData01(OrderAllocationPlanDTO.builder()
					.allocationPlanId(allocationPlanId)
					.orderDiscountApplicationFormId(applicationFormId)
					.status("IN_PROGRESS")
					.lockStatus("UNLOCK")
					.planMonth(planMonth)
					.newExecutionStartTime(beginDate)
					.newExecutionEndTime(endDate)
					.createPerson("at")
					.createTime(createTime)
					.build());
		});
		And("[整单折扣分配计划分摊05]当前存在如下的[零头药-AT_FP001]整单折扣分配计划详细信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String lotNo = Optional.ofNullable(caseData.get("lot_no")).orElse("");
				String remark = Optional.ofNullable(caseData.get("remark")).orElse("");
				allocationPlanDetailId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
				setAllocationPlanInfoByRemark(remark, allocationPlanId, allocationPlanDetailId);
				String originalItemSapCode = Optional.ofNullable(caseData.get("originalItemSapCode")).orElse("");
				String originalDistributionChannelCode = Optional.ofNullable(caseData.get("originalDistributionChannelCode")).orElse("");
				String originalDistributionChannelName = Optional.ofNullable(caseData.get("originalDistributionChannelName")).orElse("");
				String giftItemSapCode = Optional.ofNullable(caseData.get("giftItemSapCode")).orElse("");
				String giftDistributionChannelCode = Optional.ofNullable(caseData.get("giftDistributionChannelCode")).orElse("");
				String giftDistributionChannelName = Optional.ofNullable(caseData.get("giftDistributionChannelName")).orElse("");
				String allocationQuantity = Optional.ofNullable(caseData.get("allocationQuantity")).orElse("");
				Integer originalQuantity = Integer.valueOf(Optional.ofNullable(caseData.get("originalQuantity")).orElse("0"));
				Integer giftQuantity = Integer.parseInt(Optional.ofNullable(caseData.get("giftQuantity")).orElse("0"));
				Integer initialBonusQuantity = Integer.parseInt(Optional.ofNullable(caseData.get("initialBonusQuantity")).orElse("0"));
				String needShip = Optional.ofNullable(caseData.get("needShip")).orElse("");
				String unit = Optional.ofNullable(caseData.get("unit")).orElse("");
				String invalidStatus = Optional.ofNullable(caseData.get("invalidStatus")).orElse("");
				orderBatchInsertData01.insertOrderApplicationPlanDetailData01(OrderAllocationPlanDetailDTO.builder()
						.allocationPlanDetailId(allocationPlanDetailId)
						.allocationPlanId(allocationPlanId)
						.applicationFormDetailId(applicationFormDetailId)
						.customerEntSapCode(customerSapCode)
						.saleOrgSapCode(saleOrgSapCode)
						.regionCode(regionCode)
						.regionName(regionName)
						.originalItemSapCode(originalItemSapCode)
						.originalDistributionChannelCode(originalDistributionChannelCode)
						.originalDistributionChannelName(originalDistributionChannelName)
						.giftItemSapCode(giftItemSapCode)
						.giftDistributionChannelCode(giftDistributionChannelCode)
						.giftDistributionChannelName(giftDistributionChannelName)
						.allocationQuantity(allocationQuantity)
						.originalQuantity(originalQuantity)
						.giftQuantity(giftQuantity)
						.initialBonusQuantity(initialBonusQuantity)
						.unit(unit)
						.lotNo(lotNo)
						.productionDate(beginDate)
						.expirationDate(endDate)
						.needShip(needShip)
						.invalidStatus(invalidStatus)
						.remark(remark)
						.createPerson("at")
						.createTime(createTime)
						.build());
			});
		});
		And("[整单折扣分配计划分摊05]当前存在如下的[零头药-AT_FP002]整单折扣分配计划信息", () -> {
			allocationPlanId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			planMonth = new SimpleDateFormat("yyyy-MM").format(System.currentTimeMillis());
			orderBatchInsertData01.insertOrderAllocationPlanData01(OrderAllocationPlanDTO.builder()
					.allocationPlanId(allocationPlanId)
					.orderDiscountApplicationFormId(applicationFormId)
					.status("IN_PROGRESS")
					.lockStatus("UNLOCK")
					.planMonth(planMonth)
					.newExecutionStartTime(beginDate)
					.newExecutionEndTime(endDate)
					.createPerson("at")
					.createTime(createTime)
					.build());
		});
		And("[整单折扣分配计划分摊05]当前存在如下的[零头药-AT_FP002]整单折扣分配计划详细信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String lotNo = Optional.ofNullable(caseData.get("lot_no")).orElse("");
				String remark = Optional.ofNullable(caseData.get("remark")).orElse("");
				allocationPlanDetailId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
				setAllocationPlanInfoByRemark(remark, allocationPlanId, allocationPlanDetailId);
				String originalItemSapCode = Optional.ofNullable(caseData.get("originalItemSapCode")).orElse("");
				String originalDistributionChannelCode = Optional.ofNullable(caseData.get("originalDistributionChannelCode")).orElse("");
				String originalDistributionChannelName = Optional.ofNullable(caseData.get("originalDistributionChannelName")).orElse("");
				String giftItemSapCode = Optional.ofNullable(caseData.get("giftItemSapCode")).orElse("");
				String giftDistributionChannelCode = Optional.ofNullable(caseData.get("giftDistributionChannelCode")).orElse("");
				String giftDistributionChannelName = Optional.ofNullable(caseData.get("giftDistributionChannelName")).orElse("");
				String allocationQuantity = Optional.ofNullable(caseData.get("allocationQuantity")).orElse("");
				Integer originalQuantity = Integer.valueOf(Optional.ofNullable(caseData.get("originalQuantity")).orElse("0"));
				Integer giftQuantity = Integer.parseInt(Optional.ofNullable(caseData.get("giftQuantity")).orElse("0"));
				Integer initialBonusQuantity = Integer.parseInt(Optional.ofNullable(caseData.get("initialBonusQuantity")).orElse("0"));
				String needShip = Optional.ofNullable(caseData.get("needShip")).orElse("");
				String unit = Optional.ofNullable(caseData.get("unit")).orElse("");
				String invalidStatus = Optional.ofNullable(caseData.get("invalidStatus")).orElse("");
				orderBatchInsertData01.insertOrderApplicationPlanDetailData01(OrderAllocationPlanDetailDTO.builder()
						.allocationPlanDetailId(allocationPlanDetailId)
						.allocationPlanId(allocationPlanId)
						.applicationFormDetailId(applicationFormDetailId)
						.customerEntSapCode(customerSapCode)
						.saleOrgSapCode(saleOrgSapCode)
						.regionCode(regionCode)
						.regionName(regionName)
						.originalItemSapCode(originalItemSapCode)
						.originalDistributionChannelCode(originalDistributionChannelCode)
						.originalDistributionChannelName(originalDistributionChannelName)
						.giftItemSapCode(giftItemSapCode)
						.giftDistributionChannelCode(giftDistributionChannelCode)
						.giftDistributionChannelName(giftDistributionChannelName)
						.allocationQuantity(allocationQuantity)
						.originalQuantity(originalQuantity)
						.giftQuantity(giftQuantity)
						.initialBonusQuantity(initialBonusQuantity)
						.unit(unit)
						.lotNo(lotNo)
						.productionDate(beginDate)
						.expirationDate(endDate)
						.needShip(needShip)
						.invalidStatus(invalidStatus)
						.remark(remark)
						.createPerson("at")
						.createTime(createTime)
						.build());
			});
		});
		And("[整单折扣分配计划分摊05]添加商品", (DataTable dataTable) -> {
			log.info("添加商品");
		});
		And("[整单折扣分配计划分摊05]选择项目", (DataTable dataTable) -> {
			log.info("选择项目");
		});
		Then("[整单折扣分配计划分摊05]创建订单应校验通过且提交成功", (DataTable dataTable) -> {
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
			List<OrderDiscountCalcCalculateReqGroupListListDTO> orderDiscountCalcCalculateReqGroupListListDTOS = new ArrayList<>();
			dataTable.asMaps().forEach(caseData -> {
				String id = "100000" + Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String orderWideDiscountSubject = Optional.ofNullable(caseData.get("orderWideDiscountSubject")).orElse("");
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String needShip = Optional.ofNullable(caseData.get("needShip")).orElse("");
				String unitPrice = new BigDecimal(caseData.get("unitPrice")).setScale(2, RoundingMode.HALF_UP).toString();
				String quantity = Optional.ofNullable(caseData.get("quantity")).orElse("");
				String boxRatio = new Integer(caseData.get("boxRatio")).toString();
				String preDiscount = new BigDecimal(caseData.get("preDiscount")).setScale(2, RoundingMode.HALF_UP).toString();
				if ("0.00".equals(preDiscount)) {
					id = ""; // 如果是"0"，则将id设置为空字符串
				}
				String calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(2, RoundingMode.HALF_UP).toString();
				String totalInline = new BigDecimal(caseData.get("totalInline")).setScale(2, RoundingMode.HALF_UP).toString();
				String preDiscountTotal = new BigDecimal(caseData.get("preDiscountTotal")).setScale(2, RoundingMode.HALF_UP).toString();
				String orderDiscountTotal = new BigDecimal(caseData.get("orderDiscountTotal")).setScale(2, RoundingMode.HALF_UP).toString();
				String discountProportion = String.format("%.4f", new BigDecimal(caseData.get("preDiscountTotal").toString()).add(new BigDecimal(caseData.get("orderDiscountTotal").toString())).divide(new BigDecimal(caseData.get("totalInline").toString()), 4, RoundingMode.HALF_UP).doubleValue());
				String remark = Optional.ofNullable(caseData.get("remark")).orElse("");
				getAllocationPlanInfoByRemark(remark);
				orderDiscountCalcCalculateReqGroupListListDTOS.add(OrderDiscountCalcCalculateReqGroupListListDTO.builder()
						.orderWideDiscountSubject(orderWideDiscountSubject)
						.discountAllocationPlanId(allocationPlanId)
						.discountAllocationPlanDetailId(allocationPlanDetailId)
						.discountId(id)
						.preDiscountDetailId(id)
						.itemSapCode(itemSapCode)
						.itemName(ProductEnum.fetchProductName(itemSapCode))
						.itemDesc(ProductEnum.fetchProductSpec(itemSapCode))
						.itemGroupCode("01")
						.itemGroupName("成品药-非管制药品")
						.distributionChannelCode("22")
						.distributionChannelName("分销-非医疗")
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
						.discountProportion(discountProportion)
						.build());
			});
			OrderDiscountCalcCalculateReqDTO calculateReq = orderApiOrderManagement01.discountCalcAfterData(OrderDataDTO.builder()
					.orderType("STANDARD")
					.deliveryNo(customerSapCode)
					.saleOrgSapCode(saleOrgSapCode)
					.saleOrgName(SaleOrgEnum.fetchName(saleOrgSapCode))
					.saleOrgZtCode(SaleOrgEnum.fetchZtCode(saleOrgSapCode))
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
					.contractRemark("自动化测试-合同备注")
					.remark("自动化测试-订单备注")
					.calculateReq(OrderDiscountCalcCalculateReqDTO.builder()
							.totalInline(totalInlineSum)
							.preDiscountTotal(preDiscountTotalSum)
							.orderDiscountTotal(orderDiscountTotalSum)
							.groupList(Arrays.asList(OrderDiscountCalcCalculateReqGroupListDTO.builder()
									.itemGroupCode("01")
									.itemGroupName("成品药-非管制药品")
									.distributionChannelCode("22")
									.distributionChannelName("分销-非医疗")
									.totalInline(totalInlineSum)
									.preDiscountTotal(preDiscountTotalSum)
									.orderDiscountTotal(orderDiscountTotalSum)
									.list(orderDiscountCalcCalculateReqGroupListListDTOS)
									.build()))
							.build()
					)
					.build());
			Response createOrderVerifyResponse = orderApiOrderManagement01.createOrderVerify(OrderDataDTO.builder()
					.orderType("STANDARD")
					.deliveryNo(customerSapCode)
					.saleOrgSapCode(saleOrgSapCode)
					.saleOrgName(SaleOrgEnum.fetchName(saleOrgSapCode))
					.saleOrgZtCode(SaleOrgEnum.fetchZtCode(saleOrgSapCode))
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
					.contractRemark("自动化测试-合同备注")
					.remark("自动化测试-订单备注")
					.calculateReq(calculateReq)
					.build());
			createOrderVerifyResponse.then().body("resultCode", Matchers.equalTo("0"), "resultMsg", Matchers.equalTo("success"));
			Response createOrderSubmitResponse = orderApiOrderManagement01.createOrderSubmit(OrderDataDTO.builder()
					.orderType("STANDARD")
					.deliveryNo(customerSapCode)
					.saleOrgSapCode(saleOrgSapCode)
					.saleOrgName(SaleOrgEnum.fetchName(saleOrgSapCode))
					.saleOrgZtCode(SaleOrgEnum.fetchZtCode(saleOrgSapCode))
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
					.contractRemark("自动化测试-合同备注")
					.remark("自动化测试-订单备注")
					.calculateReq(calculateReq)
					.build());
			createOrderSubmitResponse.then().body("resultCode", Matchers.equalTo("0"), "resultMsg", Matchers.equalTo("success"));
		});
		Then("[整单折扣分配计划分摊05]订单整单折扣使用记录成功", (DataTable dataTable) -> {
			List<Map<String, String>> caseDataMap = new ArrayList<>();
			String orderNo = orderApiOrderManagement01.selectOrderInfo(OrderQueryPageByConditionDTO.builder()
					.state("PENDING_BUSINESS_REVIEW")
					.cancelStatus("NOT_CANCEL")
					.saleOrgSapCode(saleOrgSapCode)
					.customerCodeOrName(customerSapCode)
					.build());
			String parentsOrderNo = StringUtils.left(orderNo, 22);
			dataTable.asMaps().forEach(caseData -> {
				Map<String, String> processedData = new HashMap<>();
				processedData.put("parentOrderNo", parentsOrderNo);
				processedData.put("parentOrderItemNo", Optional.ofNullable(caseData.get("parentOrderItemNo")).orElse(""));
				processedData.put("orderNo", orderNo);
				processedData.put("orderItemNo", Optional.ofNullable(caseData.get("orderItemNo")).orElse(""));
				String remark = Optional.ofNullable(caseData.get("remark")).orElse("");
				getAllocationPlanInfoByRemark(remark);
				processedData.put("orderDiscountApplicationPlanId", allocationPlanId);
				processedData.put("orderDiscountApplicationPlanDetailId", allocationPlanDetailId);
				processedData.put("operateType", Optional.ofNullable(caseData.get("operateType")).orElse(""));
				processedData.put("operateAmount", Optional.ofNullable(caseData.get("operateAmount")).orElse(""));
				caseDataMap.add(processedData);
			});
			List<Map<String, Object>> sqlResult = orderDiscountDetailBatchSelectData01.queryOrderDiscountDetailData01(parentsOrderNo);
			discountCheckAllocationPlan01.checkDiscountAllocationPlan(caseDataMap, sqlResult);
			orderBatchClearData01.clearOrderData01(orderNo);
			priceBatchClearData01.clearPirceData01("at");
			orderBatchClearData01.clearOrderPreDiscountData01("at");
			orderBatchClearData01.clearDiscountData01("at");
		});
	}

	private void scenario06() {
		Given("[整单折扣分配计划分摊06]当前存在如下客户信息", (DataTable dataTable) -> {
			createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis());
			domainDataTable = dataTable;
			domainDataTable.asMaps().forEach(caseData -> {
				saleOrgSapCode = Optional.ofNullable(caseData.get("saleOrgSapCode")).orElse("");
				regionCode = Optional.ofNullable(caseData.get("regionCode")).orElse("");
				regionName = Optional.ofNullable(caseData.get("regionName")).orElse("");
				customerSapCode = Optional.ofNullable(caseData.get("customerSapCode")).orElse("");
				shippingAddress = Optional.ofNullable(caseData.get("shippingAddress")).orElse("");
				shippingRecipient = Optional.ofNullable(caseData.get("shippingRecipient")).orElse("");
				shippingTelephone = Optional.ofNullable(caseData.get("shippingTelephone")).orElse("");
			});
		});
		When("[整单折扣分配计划分摊06]当前存在如下的预折商品信息", (DataTable dataTable) -> {
			preDiscountId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			preDiscountDetailId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			beginDate = new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis());
			endDate = new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis() + 24 * 60 * 60 * 1000);
			orderBatchInsertData01.insertOrderPreDidcountData01(OrderPreDiscountDTO.builder()
					.preDiscountId(preDiscountId)
					.customerSapCode(customerSapCode)
					.saleOrgSapCode(saleOrgSapCode)
					.regionCode("")
					.regionName("")
					.effectiveState("ENABLE")
					.preType("CUSTOMER_PRE_DISCOUNT")
					.createPerson("at")
					.createTime(createTime)
					.build());
			dataTable.asMaps().stream().forEach(caseData -> {
				String id = "100000" + Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				BigDecimal currentPrice = new BigDecimal(caseData.get("currentPrice")).setScale(8, RoundingMode.HALF_UP);
				String calculationType = Optional.ofNullable(caseData.get("calculationType")).orElse("");
				BigDecimal calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(8, RoundingMode.HALF_UP);
				String preType = Optional.ofNullable(caseData.get("preType")).orElse("");
				String effectiveState = Optional.ofNullable(caseData.get("effectiveState")).orElse("");
				String distributionChannelCode = Optional.ofNullable(caseData.get("distributionChannelCode")).orElse("");
				String distributionChannelName = Optional.ofNullable(caseData.get("distributionChannelName")).orElse("");
				orderBatchInsertData01.insertOrderPreDidcountDetailData01(OrderPreDiscountDetailDTO.builder()
						.id(id)
						.preDiscountDetailId(preDiscountDetailId)
						.preDiscountId(preDiscountId)
						.itemSapCode(itemSapCode)
						.distributionChannelCode(distributionChannelCode)
						.distributionChannelName(distributionChannelName)
						.currentPrice(currentPrice)
						.calculationType(calculationType)
						.calculationValue(calculationValue)
						.beginDate(beginDate)
						.endDate(endDate)
						.effectiveState(effectiveState)
						.preType("CUSTOMER_PRE_DISCOUNT")
						.createPerson("at")
						.createTime(createTime)
						.build()
				);
			});
		});
		And("[整单折扣分配计划分摊06]当前存在如下的预折折扣信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				BigDecimal calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(8, RoundingMode.HALF_UP);
				String effectiveState = Optional.ofNullable(caseData.get("effectiveState")).orElse("");
				orderBatchUpdateData01.updateOrderPreDiscountDetailCalculationValue(OrderPreDiscountDetailDTO.builder().effectiveState(effectiveState).calculationValue(calculationValue).itemSapCode(itemSapCode).build(), OrderPreDiscountDTO.builder().customerSapCode(customerSapCode).build());
			});
		});
		And("[整单折扣分配计划分摊06]当前存在如下的整单折扣申请单信息", (DataTable dataTable) -> {
			applicationFormId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			processOrderNo = "ZDZK" + new SimpleDateFormat("yyyyMMddHHmm").format(System.currentTimeMillis());
			dataTable.asMaps().stream().forEach(caseData -> {
				String projectType = Optional.ofNullable(caseData.get("projectType")).orElse("");
				String module = Optional.ofNullable(caseData.get("module")).orElse("");
				orderBatchInsertData01.insertOrderApplicationFormData01(OrderApplicationFormDTO.builder()
						.applicationFormId(applicationFormId)
						.processOrderNo(processOrderNo)
						.applicant("at-OTC分公司")
						.applicationDate(createTime)
						.projectType(projectType)
						.module(module)
						.status("APPROVED")
						.executionStartTime(beginDate)
						.executionEndTime(endDate)
						.saleOrgSapCode(saleOrgSapCode)
						.createPerson("at")
						.createTime(createTime)
						.build());
			});
		});
		And("[整单折扣分配计划分摊06]当前存在如下的整单折扣申请单商品信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				applicationFormDetailId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String quantity = Optional.ofNullable(caseData.get("quantity")).orElse("");
				String units = Optional.ofNullable(caseData.get("units")).orElse("");
				orderBatchInsertData01.insertOrderApplicationFormDetailData01(OrderApplicationFormDetailDTO.builder()
						.applicationFormDetailId(applicationFormDetailId)
						.applicationFormId(applicationFormId)
						.itemSapCode(itemSapCode)
						.quantity(quantity)
						.units(units)
						.createPerson("at")
						.createTime(createTime)
						.build());
			});
		});
		And("[整单折扣分配计划分摊06]当前存在如下的[零头药-AT_FP001]整单折扣分配计划信息", () -> {
			allocationPlanId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			allocationPlanId_001 = allocationPlanId;
			planMonth = new SimpleDateFormat("yyyy-MM").format(System.currentTimeMillis());
			orderBatchInsertData01.insertOrderAllocationPlanData01(OrderAllocationPlanDTO.builder()
					.allocationPlanId(allocationPlanId)
					.orderDiscountApplicationFormId(applicationFormId)
					.status("IN_PROGRESS")
					.lockStatus("UNLOCK")
					.planMonth(planMonth)
					.newExecutionStartTime(beginDate)
					.newExecutionEndTime(endDate)
					.createPerson("at")
					.createTime(createTime)
					.build());
		});
		And("[整单折扣分配计划分摊06]当前存在如下的[零头药-AT_FP001]整单折扣分配计划详细信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String lotNo = Optional.ofNullable(caseData.get("lot_no")).orElse("");
				String remark = Optional.ofNullable(caseData.get("remark")).orElse("");
				allocationPlanDetailId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
				setAllocationPlanInfoByRemark(remark, allocationPlanId, allocationPlanDetailId);
				String originalItemSapCode = Optional.ofNullable(caseData.get("originalItemSapCode")).orElse("");
				String originalDistributionChannelCode = Optional.ofNullable(caseData.get("originalDistributionChannelCode")).orElse("");
				String originalDistributionChannelName = Optional.ofNullable(caseData.get("originalDistributionChannelName")).orElse("");
				String giftItemSapCode = Optional.ofNullable(caseData.get("giftItemSapCode")).orElse("");
				String giftDistributionChannelCode = Optional.ofNullable(caseData.get("giftDistributionChannelCode")).orElse("");
				String giftDistributionChannelName = Optional.ofNullable(caseData.get("giftDistributionChannelName")).orElse("");
				String allocationQuantity = Optional.ofNullable(caseData.get("allocationQuantity")).orElse("");
				Integer originalQuantity = Integer.valueOf(Optional.ofNullable(caseData.get("originalQuantity")).orElse("0"));
				Integer giftQuantity = Integer.parseInt(Optional.ofNullable(caseData.get("giftQuantity")).orElse("0"));
				Integer initialBonusQuantity = Integer.parseInt(Optional.ofNullable(caseData.get("initialBonusQuantity")).orElse("0"));
				String needShip = Optional.ofNullable(caseData.get("needShip")).orElse("");
				String unit = Optional.ofNullable(caseData.get("unit")).orElse("");
				String invalidStatus = Optional.ofNullable(caseData.get("invalidStatus")).orElse("");
				orderBatchInsertData01.insertOrderApplicationPlanDetailData01(OrderAllocationPlanDetailDTO.builder()
						.allocationPlanDetailId(allocationPlanDetailId)
						.allocationPlanId(allocationPlanId)
						.applicationFormDetailId(applicationFormDetailId)
						.customerEntSapCode(customerSapCode)
						.saleOrgSapCode(saleOrgSapCode)
						.regionCode(regionCode)
						.regionName(regionName)
						.originalItemSapCode(originalItemSapCode)
						.originalDistributionChannelCode(originalDistributionChannelCode)
						.originalDistributionChannelName(originalDistributionChannelName)
						.giftItemSapCode(giftItemSapCode)
						.giftDistributionChannelCode(giftDistributionChannelCode)
						.giftDistributionChannelName(giftDistributionChannelName)
						.allocationQuantity(allocationQuantity)
						.originalQuantity(originalQuantity)
						.giftQuantity(giftQuantity)
						.initialBonusQuantity(initialBonusQuantity)
						.unit(unit)
						.lotNo(lotNo)
						.productionDate(beginDate)
						.expirationDate(endDate)
						.needShip(needShip)
						.invalidStatus(invalidStatus)
						.remark(remark)
						.createPerson("at")
						.createTime(createTime)
						.build());
			});
		});
		And("[整单折扣分配计划分摊06]当前存在如下的[零头药-AT_FP002]整单折扣分配计划信息", () -> {
			allocationPlanId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			planMonth = new SimpleDateFormat("yyyy-MM").format(System.currentTimeMillis());
			orderBatchInsertData01.insertOrderAllocationPlanData01(OrderAllocationPlanDTO.builder()
					.allocationPlanId(allocationPlanId)
					.orderDiscountApplicationFormId(applicationFormId)
					.status("IN_PROGRESS")
					.lockStatus("UNLOCK")
					.planMonth(planMonth)
					.newExecutionStartTime(beginDate)
					.newExecutionEndTime(endDate)
					.createPerson("at")
					.createTime(createTime)
					.build());
		});
		And("[整单折扣分配计划分摊06]当前存在如下的[零头药-AT_FP002]整单折扣分配计划详细信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String lotNo = Optional.ofNullable(caseData.get("lot_no")).orElse("");
				String remark = Optional.ofNullable(caseData.get("remark")).orElse("");
				allocationPlanDetailId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
				setAllocationPlanInfoByRemark(remark, allocationPlanId, allocationPlanDetailId);
				String originalItemSapCode = Optional.ofNullable(caseData.get("originalItemSapCode")).orElse("");
				String originalDistributionChannelCode = Optional.ofNullable(caseData.get("originalDistributionChannelCode")).orElse("");
				String originalDistributionChannelName = Optional.ofNullable(caseData.get("originalDistributionChannelName")).orElse("");
				String giftItemSapCode = Optional.ofNullable(caseData.get("giftItemSapCode")).orElse("");
				String giftDistributionChannelCode = Optional.ofNullable(caseData.get("giftDistributionChannelCode")).orElse("");
				String giftDistributionChannelName = Optional.ofNullable(caseData.get("giftDistributionChannelName")).orElse("");
				String allocationQuantity = Optional.ofNullable(caseData.get("allocationQuantity")).orElse("");
				Integer originalQuantity = Integer.valueOf(Optional.ofNullable(caseData.get("originalQuantity")).orElse("0"));
				Integer giftQuantity = Integer.parseInt(Optional.ofNullable(caseData.get("giftQuantity")).orElse("0"));
				Integer initialBonusQuantity = Integer.parseInt(Optional.ofNullable(caseData.get("initialBonusQuantity")).orElse("0"));
				String needShip = Optional.ofNullable(caseData.get("needShip")).orElse("");
				String unit = Optional.ofNullable(caseData.get("unit")).orElse("");
				String invalidStatus = Optional.ofNullable(caseData.get("invalidStatus")).orElse("");
				orderBatchInsertData01.insertOrderApplicationPlanDetailData01(OrderAllocationPlanDetailDTO.builder()
						.allocationPlanDetailId(allocationPlanDetailId)
						.allocationPlanId(allocationPlanId)
						.applicationFormDetailId(applicationFormDetailId)
						.customerEntSapCode(customerSapCode)
						.saleOrgSapCode(saleOrgSapCode)
						.regionCode(regionCode)
						.regionName(regionName)
						.originalItemSapCode(originalItemSapCode)
						.originalDistributionChannelCode(originalDistributionChannelCode)
						.originalDistributionChannelName(originalDistributionChannelName)
						.giftItemSapCode(giftItemSapCode)
						.giftDistributionChannelCode(giftDistributionChannelCode)
						.giftDistributionChannelName(giftDistributionChannelName)
						.allocationQuantity(allocationQuantity)
						.originalQuantity(originalQuantity)
						.giftQuantity(giftQuantity)
						.initialBonusQuantity(initialBonusQuantity)
						.unit(unit)
						.lotNo(lotNo)
						.productionDate(beginDate)
						.expirationDate(endDate)
						.needShip(needShip)
						.invalidStatus(invalidStatus)
						.remark(remark)
						.createPerson("at")
						.createTime(createTime)
						.build());
			});
		});
		And("[整单折扣分配计划分摊06]添加商品", (DataTable dataTable) -> {
			log.info("添加商品");
		});
		And("[整单折扣分配计划分摊06]选择项目", (DataTable dataTable) -> {
			log.info("选择项目");
		});
		Then("[整单折扣分配计划分摊06]创建订单应校验通过且提交成功", (DataTable dataTable) -> {
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
			List<OrderDiscountCalcCalculateReqGroupListListDTO> orderDiscountCalcCalculateReqGroupListListDTOS = new ArrayList<>();
			dataTable.asMaps().forEach(caseData -> {
				String id = "100000" + Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String orderWideDiscountSubject = Optional.ofNullable(caseData.get("orderWideDiscountSubject")).orElse("");
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String needShip = Optional.ofNullable(caseData.get("needShip")).orElse("");
				String unitPrice = new BigDecimal(caseData.get("unitPrice")).setScale(2, RoundingMode.HALF_UP).toString();
				String quantity = Optional.ofNullable(caseData.get("quantity")).orElse("");
				String boxRatio = new Integer(caseData.get("boxRatio")).toString();
				String preDiscount = new BigDecimal(caseData.get("preDiscount")).setScale(2, RoundingMode.HALF_UP).toString();
				if ("0.00".equals(preDiscount)) {
					id = ""; // 如果是"0"，则将id设置为空字符串
				}
				String calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(2, RoundingMode.HALF_UP).toString();
				String totalInline = new BigDecimal(caseData.get("totalInline")).setScale(2, RoundingMode.HALF_UP).toString();
				String preDiscountTotal = new BigDecimal(caseData.get("preDiscountTotal")).setScale(2, RoundingMode.HALF_UP).toString();
				String orderDiscountTotal = new BigDecimal(caseData.get("orderDiscountTotal")).setScale(2, RoundingMode.HALF_UP).toString();
				String discountProportion = String.format("%.4f", new BigDecimal(caseData.get("preDiscountTotal").toString()).add(new BigDecimal(caseData.get("orderDiscountTotal").toString())).divide(new BigDecimal(caseData.get("totalInline").toString()), 4, RoundingMode.HALF_UP).doubleValue());
				String remark = Optional.ofNullable(caseData.get("remark")).orElse("");
				getAllocationPlanInfoByRemark(remark);
				orderDiscountCalcCalculateReqGroupListListDTOS.add(OrderDiscountCalcCalculateReqGroupListListDTO.builder()
						.orderWideDiscountSubject(orderWideDiscountSubject)
						.discountAllocationPlanId(allocationPlanId)
						.discountAllocationPlanDetailId(allocationPlanDetailId)
						.discountId(id)
						.preDiscountDetailId(id)
						.itemSapCode(itemSapCode)
						.itemName(ProductEnum.fetchProductName(itemSapCode))
						.itemDesc(ProductEnum.fetchProductSpec(itemSapCode))
						.itemGroupCode("01")
						.itemGroupName("成品药-非管制药品")
						.distributionChannelCode("22")
						.distributionChannelName("分销-非医疗")
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
						.discountProportion(discountProportion)
						.build());
			});
			OrderDiscountCalcCalculateReqDTO calculateReq = orderApiOrderManagement01.discountCalcAfterData(OrderDataDTO.builder()
					.orderType("STANDARD")
					.deliveryNo(customerSapCode)
					.saleOrgSapCode(saleOrgSapCode)
					.saleOrgName(SaleOrgEnum.fetchName(saleOrgSapCode))
					.saleOrgZtCode(SaleOrgEnum.fetchZtCode(saleOrgSapCode))
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
					.contractRemark("自动化测试-合同备注")
					.remark("自动化测试-订单备注")
					.calculateReq(OrderDiscountCalcCalculateReqDTO.builder()
							.totalInline(totalInlineSum)
							.preDiscountTotal(preDiscountTotalSum)
							.orderDiscountTotal(orderDiscountTotalSum)
							.groupList(Arrays.asList(OrderDiscountCalcCalculateReqGroupListDTO.builder()
									.itemGroupCode("01")
									.itemGroupName("成品药-非管制药品")
									.distributionChannelCode("22")
									.distributionChannelName("分销-非医疗")
									.totalInline(totalInlineSum)
									.preDiscountTotal(preDiscountTotalSum)
									.orderDiscountTotal(orderDiscountTotalSum)
									.list(orderDiscountCalcCalculateReqGroupListListDTOS)
									.build()))
							.build()
					)
					.build());
			Response createOrderVerifyResponse = orderApiOrderManagement01.createOrderVerify(OrderDataDTO.builder()
					.orderType("STANDARD")
					.deliveryNo(customerSapCode)
					.saleOrgSapCode(saleOrgSapCode)
					.saleOrgName(SaleOrgEnum.fetchName(saleOrgSapCode))
					.saleOrgZtCode(SaleOrgEnum.fetchZtCode(saleOrgSapCode))
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
					.contractRemark("自动化测试-合同备注")
					.remark("自动化测试-订单备注")
					.calculateReq(calculateReq)
					.build());
			createOrderVerifyResponse.then().body("resultCode", Matchers.equalTo("0"), "resultMsg", Matchers.equalTo("success"));
			Response createOrderSubmitResponse = orderApiOrderManagement01.createOrderSubmit(OrderDataDTO.builder()
					.orderType("STANDARD")
					.deliveryNo(customerSapCode)
					.saleOrgSapCode(saleOrgSapCode)
					.saleOrgName(SaleOrgEnum.fetchName(saleOrgSapCode))
					.saleOrgZtCode(SaleOrgEnum.fetchZtCode(saleOrgSapCode))
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
					.contractRemark("自动化测试-合同备注")
					.remark("自动化测试-订单备注")
					.calculateReq(calculateReq)
					.build());
			createOrderSubmitResponse.then().body("resultCode", Matchers.equalTo("0"), "resultMsg", Matchers.equalTo("success"));
		});
		Then("[整单折扣分配计划分摊06]订单整单折扣使用记录成功", (DataTable dataTable) -> {
			List<Map<String, String>> caseDataMap = new ArrayList<>();
			String orderNo = orderApiOrderManagement01.selectOrderInfo(OrderQueryPageByConditionDTO.builder()
					.state("PENDING_BUSINESS_REVIEW")
					.cancelStatus("NOT_CANCEL")
					.saleOrgSapCode(saleOrgSapCode)
					.customerCodeOrName(customerSapCode)
					.build());
			String parentsOrderNo = StringUtils.left(orderNo, 22);
			dataTable.asMaps().forEach(caseData -> {
				Map<String, String> processedData = new HashMap<>();
				processedData.put("parentOrderNo", parentsOrderNo);
				processedData.put("parentOrderItemNo", Optional.ofNullable(caseData.get("parentOrderItemNo")).orElse(""));
				processedData.put("orderNo", orderNo);
				processedData.put("orderItemNo", Optional.ofNullable(caseData.get("orderItemNo")).orElse(""));
				String remark = Optional.ofNullable(caseData.get("remark")).orElse("");
				getAllocationPlanInfoByRemark(remark);
				processedData.put("orderDiscountApplicationPlanId", allocationPlanId);
				processedData.put("orderDiscountApplicationPlanDetailId", allocationPlanDetailId);
				processedData.put("operateType", Optional.ofNullable(caseData.get("operateType")).orElse(""));
				processedData.put("operateAmount", Optional.ofNullable(caseData.get("operateAmount")).orElse(""));
				caseDataMap.add(processedData);
			});
			List<Map<String, Object>> sqlResult = orderDiscountDetailBatchSelectData01.queryOrderDiscountDetailData01(parentsOrderNo);
			discountCheckAllocationPlan01.checkDiscountAllocationPlan(caseDataMap, sqlResult);
			orderBatchClearData01.clearOrderData01(orderNo);
			priceBatchClearData01.clearPirceData01("at");
			orderBatchClearData01.clearOrderPreDiscountData01("at");
			orderBatchClearData01.clearDiscountData01("at");
		});
	}


	private void scenario07() {
		Given("[整单折扣分配计划分摊07]当前存在如下客户信息", (DataTable dataTable) -> {
			createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis());
			domainDataTable = dataTable;
			domainDataTable.asMaps().forEach(caseData -> {
				saleOrgSapCode = Optional.ofNullable(caseData.get("saleOrgSapCode")).orElse("");
				regionCode = Optional.ofNullable(caseData.get("regionCode")).orElse("");
				regionName = Optional.ofNullable(caseData.get("regionName")).orElse("");
				customerSapCode = Optional.ofNullable(caseData.get("customerSapCode")).orElse("");
				shippingAddress = Optional.ofNullable(caseData.get("shippingAddress")).orElse("");
				shippingRecipient = Optional.ofNullable(caseData.get("shippingRecipient")).orElse("");
				shippingTelephone = Optional.ofNullable(caseData.get("shippingTelephone")).orElse("");
			});
		});
		When("[整单折扣分配计划分摊07]当前存在如下的预折商品信息", (DataTable dataTable) -> {
			preDiscountId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			preDiscountDetailId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			beginDate = new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis());
			endDate = new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis() + 24 * 60 * 60 * 1000);
			orderBatchInsertData01.insertOrderPreDidcountData01(OrderPreDiscountDTO.builder()
					.preDiscountId(preDiscountId)
					.customerSapCode(customerSapCode)
					.saleOrgSapCode(saleOrgSapCode)
					.regionCode("")
					.regionName("")
					.effectiveState("ENABLE")
					.preType("CUSTOMER_PRE_DISCOUNT")
					.createPerson("at")
					.createTime(createTime)
					.build());
			dataTable.asMaps().stream().forEach(caseData -> {
				String id = "100000" + Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				BigDecimal currentPrice = new BigDecimal(caseData.get("currentPrice")).setScale(8, RoundingMode.HALF_UP);
				String calculationType = Optional.ofNullable(caseData.get("calculationType")).orElse("");
				BigDecimal calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(8, RoundingMode.HALF_UP);
				String preType = Optional.ofNullable(caseData.get("preType")).orElse("");
				String effectiveState = Optional.ofNullable(caseData.get("effectiveState")).orElse("");
				String distributionChannelCode = Optional.ofNullable(caseData.get("distributionChannelCode")).orElse("");
				String distributionChannelName = Optional.ofNullable(caseData.get("distributionChannelName")).orElse("");
				orderBatchInsertData01.insertOrderPreDidcountDetailData01(OrderPreDiscountDetailDTO.builder()
						.id(id)
						.preDiscountDetailId(preDiscountDetailId)
						.preDiscountId(preDiscountId)
						.itemSapCode(itemSapCode)
						.distributionChannelCode(distributionChannelCode)
						.distributionChannelName(distributionChannelName)
						.currentPrice(currentPrice)
						.calculationType(calculationType)
						.calculationValue(calculationValue)
						.beginDate(beginDate)
						.endDate(endDate)
						.effectiveState(effectiveState)
						.preType("CUSTOMER_PRE_DISCOUNT")
						.createPerson("at")
						.createTime(createTime)
						.build()
				);
			});
		});
		And("[整单折扣分配计划分摊07]当前存在如下的预折折扣信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				BigDecimal calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(8, RoundingMode.HALF_UP);
				String effectiveState = Optional.ofNullable(caseData.get("effectiveState")).orElse("");
				orderBatchUpdateData01.updateOrderPreDiscountDetailCalculationValue(OrderPreDiscountDetailDTO.builder().effectiveState(effectiveState).calculationValue(calculationValue).itemSapCode(itemSapCode).build(), OrderPreDiscountDTO.builder().customerSapCode(customerSapCode).build());
			});
		});
		And("[整单折扣分配计划分摊07]当前存在如下的整单折扣申请单信息", (DataTable dataTable) -> {
			applicationFormId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			processOrderNo = "ZDZK" + new SimpleDateFormat("yyyyMMddHHmm").format(System.currentTimeMillis());
			dataTable.asMaps().stream().forEach(caseData -> {
				String projectType = Optional.ofNullable(caseData.get("projectType")).orElse("");
				String module = Optional.ofNullable(caseData.get("module")).orElse("");
				orderBatchInsertData01.insertOrderApplicationFormData01(OrderApplicationFormDTO.builder()
						.applicationFormId(applicationFormId)
						.processOrderNo(processOrderNo)
						.applicant("at-OTC分公司")
						.applicationDate(createTime)
						.projectType(projectType)
						.module(module)
						.status("APPROVED")
						.executionStartTime(beginDate)
						.executionEndTime(endDate)
						.saleOrgSapCode(saleOrgSapCode)
						.createPerson("at")
						.createTime(createTime)
						.build());
			});
		});
		And("[整单折扣分配计划分摊07]当前存在如下的整单折扣申请单商品信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				applicationFormDetailId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String quantity = Optional.ofNullable(caseData.get("quantity")).orElse("");
				String units = Optional.ofNullable(caseData.get("units")).orElse("");
				orderBatchInsertData01.insertOrderApplicationFormDetailData01(OrderApplicationFormDetailDTO.builder()
						.applicationFormDetailId(applicationFormDetailId)
						.applicationFormId(applicationFormId)
						.itemSapCode(itemSapCode)
						.quantity(quantity)
						.units(units)
						.createPerson("at")
						.createTime(createTime)
						.build());
			});
		});
		And("[整单折扣分配计划分摊07]当前存在如下的[零头药-AT_FP001]整单折扣分配计划信息", () -> {
			allocationPlanId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			allocationPlanId_001 = allocationPlanId;
			planMonth = new SimpleDateFormat("yyyy-MM").format(System.currentTimeMillis());
			orderBatchInsertData01.insertOrderAllocationPlanData01(OrderAllocationPlanDTO.builder()
					.allocationPlanId(allocationPlanId)
					.orderDiscountApplicationFormId(applicationFormId)
					.status("IN_PROGRESS")
					.lockStatus("UNLOCK")
					.planMonth(planMonth)
					.newExecutionStartTime(beginDate)
					.newExecutionEndTime(endDate)
					.createPerson("at")
					.createTime(createTime)
					.build());
		});
		And("[整单折扣分配计划分摊07]当前存在如下的[零头药-AT_FP001]整单折扣分配计划详细信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String lotNo = Optional.ofNullable(caseData.get("lot_no")).orElse("");
				String remark = Optional.ofNullable(caseData.get("remark")).orElse("");
				allocationPlanDetailId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
				setAllocationPlanInfoByRemark(remark, allocationPlanId, allocationPlanDetailId);
				String originalItemSapCode = Optional.ofNullable(caseData.get("originalItemSapCode")).orElse("");
				String originalDistributionChannelCode = Optional.ofNullable(caseData.get("originalDistributionChannelCode")).orElse("");
				String originalDistributionChannelName = Optional.ofNullable(caseData.get("originalDistributionChannelName")).orElse("");
				String giftItemSapCode = Optional.ofNullable(caseData.get("giftItemSapCode")).orElse("");
				String giftDistributionChannelCode = Optional.ofNullable(caseData.get("giftDistributionChannelCode")).orElse("");
				String giftDistributionChannelName = Optional.ofNullable(caseData.get("giftDistributionChannelName")).orElse("");
				String allocationQuantity = Optional.ofNullable(caseData.get("allocationQuantity")).orElse("");
				Integer originalQuantity = Integer.valueOf(Optional.ofNullable(caseData.get("originalQuantity")).orElse("0"));
				Integer giftQuantity = Integer.parseInt(Optional.ofNullable(caseData.get("giftQuantity")).orElse("0"));
				Integer initialBonusQuantity = Integer.parseInt(Optional.ofNullable(caseData.get("initialBonusQuantity")).orElse("0"));
				String needShip = Optional.ofNullable(caseData.get("needShip")).orElse("");
				String unit = Optional.ofNullable(caseData.get("unit")).orElse("");
				String invalidStatus = Optional.ofNullable(caseData.get("invalidStatus")).orElse("");
				orderBatchInsertData01.insertOrderApplicationPlanDetailData01(OrderAllocationPlanDetailDTO.builder()
						.allocationPlanDetailId(allocationPlanDetailId)
						.allocationPlanId(allocationPlanId)
						.applicationFormDetailId(applicationFormDetailId)
						.customerEntSapCode(customerSapCode)
						.saleOrgSapCode(saleOrgSapCode)
						.regionCode(regionCode)
						.regionName(regionName)
						.originalItemSapCode(originalItemSapCode)
						.originalDistributionChannelCode(originalDistributionChannelCode)
						.originalDistributionChannelName(originalDistributionChannelName)
						.giftItemSapCode(giftItemSapCode)
						.giftDistributionChannelCode(giftDistributionChannelCode)
						.giftDistributionChannelName(giftDistributionChannelName)
						.allocationQuantity(allocationQuantity)
						.originalQuantity(originalQuantity)
						.giftQuantity(giftQuantity)
						.initialBonusQuantity(initialBonusQuantity)
						.unit(unit)
						.lotNo(lotNo)
						.productionDate(beginDate)
						.expirationDate(endDate)
						.needShip(needShip)
						.invalidStatus(invalidStatus)
						.remark(remark)
						.createPerson("at")
						.createTime(createTime)
						.build());
			});
		});
		And("[整单折扣分配计划分摊07]当前存在如下的[零头药-AT_FP002]整单折扣分配计划信息", () -> {
			allocationPlanId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			planMonth = new SimpleDateFormat("yyyy-MM").format(System.currentTimeMillis());
			orderBatchInsertData01.insertOrderAllocationPlanData01(OrderAllocationPlanDTO.builder()
					.allocationPlanId(allocationPlanId)
					.orderDiscountApplicationFormId(applicationFormId)
					.status("IN_PROGRESS")
					.lockStatus("UNLOCK")
					.planMonth(planMonth)
					.newExecutionStartTime(beginDate)
					.newExecutionEndTime(endDate)
					.createPerson("at")
					.createTime(createTime)
					.build());
		});
		And("[整单折扣分配计划分摊07]当前存在如下的[零头药-AT_FP002]整单折扣分配计划详细信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String lotNo = Optional.ofNullable(caseData.get("lot_no")).orElse("");
				String remark = Optional.ofNullable(caseData.get("remark")).orElse("");
				allocationPlanDetailId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
				setAllocationPlanInfoByRemark(remark, allocationPlanId, allocationPlanDetailId);
				String originalItemSapCode = Optional.ofNullable(caseData.get("originalItemSapCode")).orElse("");
				String originalDistributionChannelCode = Optional.ofNullable(caseData.get("originalDistributionChannelCode")).orElse("");
				String originalDistributionChannelName = Optional.ofNullable(caseData.get("originalDistributionChannelName")).orElse("");
				String giftItemSapCode = Optional.ofNullable(caseData.get("giftItemSapCode")).orElse("");
				String giftDistributionChannelCode = Optional.ofNullable(caseData.get("giftDistributionChannelCode")).orElse("");
				String giftDistributionChannelName = Optional.ofNullable(caseData.get("giftDistributionChannelName")).orElse("");
				String allocationQuantity = Optional.ofNullable(caseData.get("allocationQuantity")).orElse("");
				Integer originalQuantity = Integer.valueOf(Optional.ofNullable(caseData.get("originalQuantity")).orElse("0"));
				Integer giftQuantity = Integer.parseInt(Optional.ofNullable(caseData.get("giftQuantity")).orElse("0"));
				Integer initialBonusQuantity = Integer.parseInt(Optional.ofNullable(caseData.get("initialBonusQuantity")).orElse("0"));
				String needShip = Optional.ofNullable(caseData.get("needShip")).orElse("");
				String unit = Optional.ofNullable(caseData.get("unit")).orElse("");
				String invalidStatus = Optional.ofNullable(caseData.get("invalidStatus")).orElse("");
				orderBatchInsertData01.insertOrderApplicationPlanDetailData01(OrderAllocationPlanDetailDTO.builder()
						.allocationPlanDetailId(allocationPlanDetailId)
						.allocationPlanId(allocationPlanId)
						.applicationFormDetailId(applicationFormDetailId)
						.customerEntSapCode(customerSapCode)
						.saleOrgSapCode(saleOrgSapCode)
						.regionCode(regionCode)
						.regionName(regionName)
						.originalItemSapCode(originalItemSapCode)
						.originalDistributionChannelCode(originalDistributionChannelCode)
						.originalDistributionChannelName(originalDistributionChannelName)
						.giftItemSapCode(giftItemSapCode)
						.giftDistributionChannelCode(giftDistributionChannelCode)
						.giftDistributionChannelName(giftDistributionChannelName)
						.allocationQuantity(allocationQuantity)
						.originalQuantity(originalQuantity)
						.giftQuantity(giftQuantity)
						.initialBonusQuantity(initialBonusQuantity)
						.unit(unit)
						.lotNo(lotNo)
						.productionDate(beginDate)
						.expirationDate(endDate)
						.needShip(needShip)
						.invalidStatus(invalidStatus)
						.remark(remark)
						.createPerson("at")
						.createTime(createTime)
						.build());
			});
		});
		And("[整单折扣分配计划分摊07]添加商品", (DataTable dataTable) -> {
			log.info("添加商品");
		});
		And("[整单折扣分配计划分摊07]选择项目", (DataTable dataTable) -> {
			log.info("选择项目");
		});
		Then("[整单折扣分配计划分摊07]创建订单应校验通过且提交成功", (DataTable dataTable) -> {
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
			List<OrderDiscountCalcCalculateReqGroupListListDTO> orderDiscountCalcCalculateReqGroupListListDTOS = new ArrayList<>();
			dataTable.asMaps().forEach(caseData -> {
				String id = "100000" + Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String orderWideDiscountSubject = Optional.ofNullable(caseData.get("orderWideDiscountSubject")).orElse("");
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String needShip = Optional.ofNullable(caseData.get("needShip")).orElse("");
				String unitPrice = new BigDecimal(caseData.get("unitPrice")).setScale(2, RoundingMode.HALF_UP).toString();
				String quantity = Optional.ofNullable(caseData.get("quantity")).orElse("");
				String boxRatio = new Integer(caseData.get("boxRatio")).toString();
				String preDiscount = new BigDecimal(caseData.get("preDiscount")).setScale(2, RoundingMode.HALF_UP).toString();
				if ("0.00".equals(preDiscount)) {
					id = ""; // 如果是"0"，则将id设置为空字符串
				}
				String calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(2, RoundingMode.HALF_UP).toString();
				String totalInline = new BigDecimal(caseData.get("totalInline")).setScale(2, RoundingMode.HALF_UP).toString();
				String preDiscountTotal = new BigDecimal(caseData.get("preDiscountTotal")).setScale(2, RoundingMode.HALF_UP).toString();
				String orderDiscountTotal = new BigDecimal(caseData.get("orderDiscountTotal")).setScale(2, RoundingMode.HALF_UP).toString();
				String discountProportion = String.format("%.4f", new BigDecimal(caseData.get("preDiscountTotal").toString()).add(new BigDecimal(caseData.get("orderDiscountTotal").toString())).divide(new BigDecimal(caseData.get("totalInline").toString()), 4, RoundingMode.HALF_UP).doubleValue());
				String remark = Optional.ofNullable(caseData.get("remark")).orElse("");
				getAllocationPlanInfoByRemark(remark);
				orderDiscountCalcCalculateReqGroupListListDTOS.add(OrderDiscountCalcCalculateReqGroupListListDTO.builder()
						.orderWideDiscountSubject(orderWideDiscountSubject)
						.discountAllocationPlanId(allocationPlanId)
						.discountAllocationPlanDetailId(allocationPlanDetailId)
						.discountId(id)
						.preDiscountDetailId(id)
						.itemSapCode(itemSapCode)
						.itemName(ProductEnum.fetchProductName(itemSapCode))
						.itemDesc(ProductEnum.fetchProductSpec(itemSapCode))
						.itemGroupCode("01")
						.itemGroupName("成品药-非管制药品")
						.distributionChannelCode("22")
						.distributionChannelName("分销-非医疗")
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
						.discountProportion(discountProportion)
						.build());
			});
			OrderDiscountCalcCalculateReqDTO calculateReq = orderApiOrderManagement01.discountCalcAfterData(OrderDataDTO.builder()
					.orderType("STANDARD")
					.deliveryNo(customerSapCode)
					.saleOrgSapCode(saleOrgSapCode)
					.saleOrgName(SaleOrgEnum.fetchName(saleOrgSapCode))
					.saleOrgZtCode(SaleOrgEnum.fetchZtCode(saleOrgSapCode))
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
					.contractRemark("自动化测试-合同备注")
					.remark("自动化测试-订单备注")
					.calculateReq(OrderDiscountCalcCalculateReqDTO.builder()
							.totalInline(totalInlineSum)
							.preDiscountTotal(preDiscountTotalSum)
							.orderDiscountTotal(orderDiscountTotalSum)
							.groupList(Arrays.asList(OrderDiscountCalcCalculateReqGroupListDTO.builder()
									.itemGroupCode("01")
									.itemGroupName("成品药-非管制药品")
									.distributionChannelCode("22")
									.distributionChannelName("分销-非医疗")
									.totalInline(totalInlineSum)
									.preDiscountTotal(preDiscountTotalSum)
									.orderDiscountTotal(orderDiscountTotalSum)
									.list(orderDiscountCalcCalculateReqGroupListListDTOS)
									.build()))
							.build()
					)
					.build());
			Response createOrderVerifyResponse = orderApiOrderManagement01.createOrderVerify(OrderDataDTO.builder()
					.orderType("STANDARD")
					.deliveryNo(customerSapCode)
					.saleOrgSapCode(saleOrgSapCode)
					.saleOrgName(SaleOrgEnum.fetchName(saleOrgSapCode))
					.saleOrgZtCode(SaleOrgEnum.fetchZtCode(saleOrgSapCode))
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
					.contractRemark("自动化测试-合同备注")
					.remark("自动化测试-订单备注")
					.calculateReq(calculateReq)
					.build());
			createOrderVerifyResponse.then().body("resultCode", Matchers.equalTo("0"), "resultMsg", Matchers.equalTo("success"));
			Response createOrderSubmitResponse = orderApiOrderManagement01.createOrderSubmit(OrderDataDTO.builder()
					.orderType("STANDARD")
					.deliveryNo(customerSapCode)
					.saleOrgSapCode(saleOrgSapCode)
					.saleOrgName(SaleOrgEnum.fetchName(saleOrgSapCode))
					.saleOrgZtCode(SaleOrgEnum.fetchZtCode(saleOrgSapCode))
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
					.contractRemark("自动化测试-合同备注")
					.remark("自动化测试-订单备注")
					.calculateReq(calculateReq)
					.build());
			createOrderSubmitResponse.then().body("resultCode", Matchers.equalTo("0"), "resultMsg", Matchers.equalTo("success"));
		});
		Then("[整单折扣分配计划分摊07]订单整单折扣使用记录成功", (DataTable dataTable) -> {
			List<Map<String, String>> caseDataMap = new ArrayList<>();
			String orderNo = orderApiOrderManagement01.selectOrderInfo(OrderQueryPageByConditionDTO.builder()
					.state("PENDING_BUSINESS_REVIEW")
					.cancelStatus("NOT_CANCEL")
					.saleOrgSapCode(saleOrgSapCode)
					.customerCodeOrName(customerSapCode)
					.build());
			String parentsOrderNo = StringUtils.left(orderNo, 22);
			dataTable.asMaps().forEach(caseData -> {
				Map<String, String> processedData = new HashMap<>();
				processedData.put("parentOrderNo", parentsOrderNo);
				processedData.put("parentOrderItemNo", Optional.ofNullable(caseData.get("parentOrderItemNo")).orElse(""));
				processedData.put("orderNo", orderNo);
				processedData.put("orderItemNo", Optional.ofNullable(caseData.get("orderItemNo")).orElse(""));
				String remark = Optional.ofNullable(caseData.get("remark")).orElse("");
				getAllocationPlanInfoByRemark(remark);
				processedData.put("orderDiscountApplicationPlanId", allocationPlanId);
				processedData.put("orderDiscountApplicationPlanDetailId", allocationPlanDetailId);
				processedData.put("operateType", Optional.ofNullable(caseData.get("operateType")).orElse(""));
				processedData.put("operateAmount", Optional.ofNullable(caseData.get("operateAmount")).orElse(""));
				caseDataMap.add(processedData);
			});
			List<Map<String, Object>> sqlResult = orderDiscountDetailBatchSelectData01.queryOrderDiscountDetailData01(parentsOrderNo);
			discountCheckAllocationPlan01.checkDiscountAllocationPlan(caseDataMap, sqlResult);
			orderBatchClearData01.clearOrderData01(orderNo);
			priceBatchClearData01.clearPirceData01("at");
			orderBatchClearData01.clearOrderPreDiscountData01("at");
			orderBatchClearData01.clearDiscountData01("at");
		});
	}


	private void scenario08() {
		Given("[整单折扣分配计划分摊08]当前存在如下客户信息", (DataTable dataTable) -> {
			createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis());
			domainDataTable = dataTable;
			domainDataTable.asMaps().forEach(caseData -> {
				saleOrgSapCode = Optional.ofNullable(caseData.get("saleOrgSapCode")).orElse("");
				regionCode = Optional.ofNullable(caseData.get("regionCode")).orElse("");
				regionName = Optional.ofNullable(caseData.get("regionName")).orElse("");
				customerSapCode = Optional.ofNullable(caseData.get("customerSapCode")).orElse("");
				shippingAddress = Optional.ofNullable(caseData.get("shippingAddress")).orElse("");
				shippingRecipient = Optional.ofNullable(caseData.get("shippingRecipient")).orElse("");
				shippingTelephone = Optional.ofNullable(caseData.get("shippingTelephone")).orElse("");
			});
		});
		When("[整单折扣分配计划分摊08]当前存在如下的预折商品信息", (DataTable dataTable) -> {
			preDiscountId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			preDiscountDetailId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			beginDate = new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis());
			endDate = new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis() + 24 * 60 * 60 * 1000);
			orderBatchInsertData01.insertOrderPreDidcountData01(OrderPreDiscountDTO.builder()
					.preDiscountId(preDiscountId)
					.customerSapCode(customerSapCode)
					.saleOrgSapCode(saleOrgSapCode)
					.regionCode("")
					.regionName("")
					.effectiveState("ENABLE")
					.preType("CUSTOMER_PRE_DISCOUNT")
					.createPerson("at")
					.createTime(createTime)
					.build());
			dataTable.asMaps().stream().forEach(caseData -> {
				String id = "100000" + Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				BigDecimal currentPrice = new BigDecimal(caseData.get("currentPrice")).setScale(8, RoundingMode.HALF_UP);
				String calculationType = Optional.ofNullable(caseData.get("calculationType")).orElse("");
				BigDecimal calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(8, RoundingMode.HALF_UP);
				String preType = Optional.ofNullable(caseData.get("preType")).orElse("");
				String effectiveState = Optional.ofNullable(caseData.get("effectiveState")).orElse("");
				String distributionChannelCode = Optional.ofNullable(caseData.get("distributionChannelCode")).orElse("");
				String distributionChannelName = Optional.ofNullable(caseData.get("distributionChannelName")).orElse("");
				orderBatchInsertData01.insertOrderPreDidcountDetailData01(OrderPreDiscountDetailDTO.builder()
						.id(id)
						.preDiscountDetailId(preDiscountDetailId)
						.preDiscountId(preDiscountId)
						.itemSapCode(itemSapCode)
						.distributionChannelCode(distributionChannelCode)
						.distributionChannelName(distributionChannelName)
						.currentPrice(currentPrice)
						.calculationType(calculationType)
						.calculationValue(calculationValue)
						.beginDate(beginDate)
						.endDate(endDate)
						.effectiveState(effectiveState)
						.preType("CUSTOMER_PRE_DISCOUNT")
						.createPerson("at")
						.createTime(createTime)
						.build()
				);
			});
		});
		And("[整单折扣分配计划分摊08]当前存在如下的预折折扣信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				BigDecimal calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(8, RoundingMode.HALF_UP);
				String effectiveState = Optional.ofNullable(caseData.get("effectiveState")).orElse("");
				orderBatchUpdateData01.updateOrderPreDiscountDetailCalculationValue(OrderPreDiscountDetailDTO.builder().effectiveState(effectiveState).calculationValue(calculationValue).itemSapCode(itemSapCode).build(), OrderPreDiscountDTO.builder().customerSapCode(customerSapCode).build());
			});
		});
		And("[整单折扣分配计划分摊08]当前存在如下的整单折扣申请单信息", (DataTable dataTable) -> {
			applicationFormId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			processOrderNo = "ZDZK" + new SimpleDateFormat("yyyyMMddHHmm").format(System.currentTimeMillis());
			dataTable.asMaps().stream().forEach(caseData -> {
				String projectType = Optional.ofNullable(caseData.get("projectType")).orElse("");
				String module = Optional.ofNullable(caseData.get("module")).orElse("");
				orderBatchInsertData01.insertOrderApplicationFormData01(OrderApplicationFormDTO.builder()
						.applicationFormId(applicationFormId)
						.processOrderNo(processOrderNo)
						.applicant("at-OTC分公司")
						.applicationDate(createTime)
						.projectType(projectType)
						.module(module)
						.status("APPROVED")
						.executionStartTime(beginDate)
						.executionEndTime(endDate)
						.saleOrgSapCode(saleOrgSapCode)
						.createPerson("at")
						.createTime(createTime)
						.build());
			});
		});
		And("[整单折扣分配计划分摊08]当前存在如下的整单折扣申请单商品信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				applicationFormDetailId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String quantity = Optional.ofNullable(caseData.get("quantity")).orElse("");
				String units = Optional.ofNullable(caseData.get("units")).orElse("");
				orderBatchInsertData01.insertOrderApplicationFormDetailData01(OrderApplicationFormDetailDTO.builder()
						.applicationFormDetailId(applicationFormDetailId)
						.applicationFormId(applicationFormId)
						.itemSapCode(itemSapCode)
						.quantity(quantity)
						.units(units)
						.createPerson("at")
						.createTime(createTime)
						.build());
			});
		});
		And("[整单折扣分配计划分摊08]当前存在如下的[零头药-AT_FP001]整单折扣分配计划信息", () -> {
			allocationPlanId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			allocationPlanId_001 = allocationPlanId;
			planMonth = new SimpleDateFormat("yyyy-MM").format(System.currentTimeMillis());
			orderBatchInsertData01.insertOrderAllocationPlanData01(OrderAllocationPlanDTO.builder()
					.allocationPlanId(allocationPlanId)
					.orderDiscountApplicationFormId(applicationFormId)
					.status("IN_PROGRESS")
					.lockStatus("UNLOCK")
					.planMonth(planMonth)
					.newExecutionStartTime(beginDate)
					.newExecutionEndTime(endDate)
					.createPerson("at")
					.createTime(createTime)
					.build());
		});
		And("[整单折扣分配计划分摊08]当前存在如下的[零头药-AT_FP001]整单折扣分配计划详细信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String lotNo = Optional.ofNullable(caseData.get("lot_no")).orElse("");
				String remark = Optional.ofNullable(caseData.get("remark")).orElse("");
				allocationPlanDetailId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
				setAllocationPlanInfoByRemark(remark, allocationPlanId, allocationPlanDetailId);
				String originalItemSapCode = Optional.ofNullable(caseData.get("originalItemSapCode")).orElse("");
				String originalDistributionChannelCode = Optional.ofNullable(caseData.get("originalDistributionChannelCode")).orElse("");
				String originalDistributionChannelName = Optional.ofNullable(caseData.get("originalDistributionChannelName")).orElse("");
				String giftItemSapCode = Optional.ofNullable(caseData.get("giftItemSapCode")).orElse("");
				String giftDistributionChannelCode = Optional.ofNullable(caseData.get("giftDistributionChannelCode")).orElse("");
				String giftDistributionChannelName = Optional.ofNullable(caseData.get("giftDistributionChannelName")).orElse("");
				String allocationQuantity = Optional.ofNullable(caseData.get("allocationQuantity")).orElse("");
				Integer originalQuantity = Integer.valueOf(Optional.ofNullable(caseData.get("originalQuantity")).orElse("0"));
				Integer giftQuantity = Integer.parseInt(Optional.ofNullable(caseData.get("giftQuantity")).orElse("0"));
				Integer initialBonusQuantity = Integer.parseInt(Optional.ofNullable(caseData.get("initialBonusQuantity")).orElse("0"));
				String needShip = Optional.ofNullable(caseData.get("needShip")).orElse("");
				String unit = Optional.ofNullable(caseData.get("unit")).orElse("");
				String invalidStatus = Optional.ofNullable(caseData.get("invalidStatus")).orElse("");
				orderBatchInsertData01.insertOrderApplicationPlanDetailData01(OrderAllocationPlanDetailDTO.builder()
						.allocationPlanDetailId(allocationPlanDetailId)
						.allocationPlanId(allocationPlanId)
						.applicationFormDetailId(applicationFormDetailId)
						.customerEntSapCode(customerSapCode)
						.saleOrgSapCode(saleOrgSapCode)
						.regionCode(regionCode)
						.regionName(regionName)
						.originalItemSapCode(originalItemSapCode)
						.originalDistributionChannelCode(originalDistributionChannelCode)
						.originalDistributionChannelName(originalDistributionChannelName)
						.giftItemSapCode(giftItemSapCode)
						.giftDistributionChannelCode(giftDistributionChannelCode)
						.giftDistributionChannelName(giftDistributionChannelName)
						.allocationQuantity(allocationQuantity)
						.originalQuantity(originalQuantity)
						.giftQuantity(giftQuantity)
						.initialBonusQuantity(initialBonusQuantity)
						.unit(unit)
						.lotNo(lotNo)
						.productionDate(beginDate)
						.expirationDate(endDate)
						.needShip(needShip)
						.invalidStatus(invalidStatus)
						.remark(remark)
						.createPerson("at")
						.createTime(createTime)
						.build());
			});
		});
		And("[整单折扣分配计划分摊08]当前存在如下的[零头药-AT_FP002]整单折扣分配计划信息", () -> {
			allocationPlanId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			planMonth = new SimpleDateFormat("yyyy-MM").format(System.currentTimeMillis());
			orderBatchInsertData01.insertOrderAllocationPlanData01(OrderAllocationPlanDTO.builder()
					.allocationPlanId(allocationPlanId)
					.orderDiscountApplicationFormId(applicationFormId)
					.status("IN_PROGRESS")
					.lockStatus("UNLOCK")
					.planMonth(planMonth)
					.newExecutionStartTime(beginDate)
					.newExecutionEndTime(endDate)
					.createPerson("at")
					.createTime(createTime)
					.build());
		});
		And("[整单折扣分配计划分摊08]当前存在如下的[零头药-AT_FP002]整单折扣分配计划详细信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String lotNo = Optional.ofNullable(caseData.get("lot_no")).orElse("");
				String remark = Optional.ofNullable(caseData.get("remark")).orElse("");
				allocationPlanDetailId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
				setAllocationPlanInfoByRemark(remark, allocationPlanId, allocationPlanDetailId);
				String originalItemSapCode = Optional.ofNullable(caseData.get("originalItemSapCode")).orElse("");
				String originalDistributionChannelCode = Optional.ofNullable(caseData.get("originalDistributionChannelCode")).orElse("");
				String originalDistributionChannelName = Optional.ofNullable(caseData.get("originalDistributionChannelName")).orElse("");
				String giftItemSapCode = Optional.ofNullable(caseData.get("giftItemSapCode")).orElse("");
				String giftDistributionChannelCode = Optional.ofNullable(caseData.get("giftDistributionChannelCode")).orElse("");
				String giftDistributionChannelName = Optional.ofNullable(caseData.get("giftDistributionChannelName")).orElse("");
				String allocationQuantity = Optional.ofNullable(caseData.get("allocationQuantity")).orElse("");
				Integer originalQuantity = Integer.valueOf(Optional.ofNullable(caseData.get("originalQuantity")).orElse("0"));
				Integer giftQuantity = Integer.parseInt(Optional.ofNullable(caseData.get("giftQuantity")).orElse("0"));
				Integer initialBonusQuantity = Integer.parseInt(Optional.ofNullable(caseData.get("initialBonusQuantity")).orElse("0"));
				String needShip = Optional.ofNullable(caseData.get("needShip")).orElse("");
				String unit = Optional.ofNullable(caseData.get("unit")).orElse("");
				String invalidStatus = Optional.ofNullable(caseData.get("invalidStatus")).orElse("");
				orderBatchInsertData01.insertOrderApplicationPlanDetailData01(OrderAllocationPlanDetailDTO.builder()
						.allocationPlanDetailId(allocationPlanDetailId)
						.allocationPlanId(allocationPlanId)
						.applicationFormDetailId(applicationFormDetailId)
						.customerEntSapCode(customerSapCode)
						.saleOrgSapCode(saleOrgSapCode)
						.regionCode(regionCode)
						.regionName(regionName)
						.originalItemSapCode(originalItemSapCode)
						.originalDistributionChannelCode(originalDistributionChannelCode)
						.originalDistributionChannelName(originalDistributionChannelName)
						.giftItemSapCode(giftItemSapCode)
						.giftDistributionChannelCode(giftDistributionChannelCode)
						.giftDistributionChannelName(giftDistributionChannelName)
						.allocationQuantity(allocationQuantity)
						.originalQuantity(originalQuantity)
						.giftQuantity(giftQuantity)
						.initialBonusQuantity(initialBonusQuantity)
						.unit(unit)
						.lotNo(lotNo)
						.productionDate(beginDate)
						.expirationDate(endDate)
						.needShip(needShip)
						.invalidStatus(invalidStatus)
						.remark(remark)
						.createPerson("at")
						.createTime(createTime)
						.build());
			});
		});
		And("[整单折扣分配计划分摊08]添加商品", (DataTable dataTable) -> {
			log.info("添加商品");
		});
		And("[整单折扣分配计划分摊08]选择项目", (DataTable dataTable) -> {
			log.info("选择项目");
		});
		Then("[整单折扣分配计划分摊08]创建订单应校验通过且提交成功", (DataTable dataTable) -> {
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
			List<OrderDiscountCalcCalculateReqGroupListListDTO> orderDiscountCalcCalculateReqGroupListListDTOS = new ArrayList<>();
			dataTable.asMaps().forEach(caseData -> {
				String id = "100000" + Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String orderWideDiscountSubject = Optional.ofNullable(caseData.get("orderWideDiscountSubject")).orElse("");
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String needShip = Optional.ofNullable(caseData.get("needShip")).orElse("");
				String unitPrice = new BigDecimal(caseData.get("unitPrice")).setScale(2, RoundingMode.HALF_UP).toString();
				String quantity = Optional.ofNullable(caseData.get("quantity")).orElse("");
				String boxRatio = new Integer(caseData.get("boxRatio")).toString();
				String preDiscount = new BigDecimal(caseData.get("preDiscount")).setScale(2, RoundingMode.HALF_UP).toString();
				if ("0.00".equals(preDiscount)) {
					id = ""; // 如果是"0"，则将id设置为空字符串
				}
				String calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(2, RoundingMode.HALF_UP).toString();
				String totalInline = new BigDecimal(caseData.get("totalInline")).setScale(2, RoundingMode.HALF_UP).toString();
				String preDiscountTotal = new BigDecimal(caseData.get("preDiscountTotal")).setScale(2, RoundingMode.HALF_UP).toString();
				String orderDiscountTotal = new BigDecimal(caseData.get("orderDiscountTotal")).setScale(2, RoundingMode.HALF_UP).toString();
				String discountProportion = String.format("%.4f", new BigDecimal(caseData.get("preDiscountTotal").toString()).add(new BigDecimal(caseData.get("orderDiscountTotal").toString())).divide(new BigDecimal(caseData.get("totalInline").toString()), 4, RoundingMode.HALF_UP).doubleValue());
				String remark = Optional.ofNullable(caseData.get("remark")).orElse("");
				getAllocationPlanInfoByRemark(remark);
				orderDiscountCalcCalculateReqGroupListListDTOS.add(OrderDiscountCalcCalculateReqGroupListListDTO.builder()
						.orderWideDiscountSubject(orderWideDiscountSubject)
						.discountAllocationPlanId(allocationPlanId)
						.discountAllocationPlanDetailId(allocationPlanDetailId)
						.discountId(id)
						.preDiscountDetailId(id)
						.itemSapCode(itemSapCode)
						.itemName(ProductEnum.fetchProductName(itemSapCode))
						.itemDesc(ProductEnum.fetchProductSpec(itemSapCode))
						.itemGroupCode("01")
						.itemGroupName("成品药-非管制药品")
						.distributionChannelCode("22")
						.distributionChannelName("分销-非医疗")
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
						.discountProportion(discountProportion)
						.build());
			});
			OrderDiscountCalcCalculateReqDTO calculateReq = orderApiOrderManagement01.discountCalcAfterData(OrderDataDTO.builder()
					.orderType("STANDARD")
					.deliveryNo(customerSapCode)
					.saleOrgSapCode(saleOrgSapCode)
					.saleOrgName(SaleOrgEnum.fetchName(saleOrgSapCode))
					.saleOrgZtCode(SaleOrgEnum.fetchZtCode(saleOrgSapCode))
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
					.contractRemark("自动化测试-合同备注")
					.remark("自动化测试-订单备注")
					.calculateReq(OrderDiscountCalcCalculateReqDTO.builder()
							.totalInline(totalInlineSum)
							.preDiscountTotal(preDiscountTotalSum)
							.orderDiscountTotal(orderDiscountTotalSum)
							.groupList(Arrays.asList(OrderDiscountCalcCalculateReqGroupListDTO.builder()
									.itemGroupCode("01")
									.itemGroupName("成品药-非管制药品")
									.distributionChannelCode("22")
									.distributionChannelName("分销-非医疗")
									.totalInline(totalInlineSum)
									.preDiscountTotal(preDiscountTotalSum)
									.orderDiscountTotal(orderDiscountTotalSum)
									.list(orderDiscountCalcCalculateReqGroupListListDTOS)
									.build()))
							.build()
					)
					.build());
			Response createOrderVerifyResponse = orderApiOrderManagement01.createOrderVerify(OrderDataDTO.builder()
					.orderType("STANDARD")
					.deliveryNo(customerSapCode)
					.saleOrgSapCode(saleOrgSapCode)
					.saleOrgName(SaleOrgEnum.fetchName(saleOrgSapCode))
					.saleOrgZtCode(SaleOrgEnum.fetchZtCode(saleOrgSapCode))
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
					.contractRemark("自动化测试-合同备注")
					.remark("自动化测试-订单备注")
					.calculateReq(calculateReq)
					.build());
			createOrderVerifyResponse.then().body("resultCode", Matchers.equalTo("0"), "resultMsg", Matchers.equalTo("success"));
			Response createOrderSubmitResponse = orderApiOrderManagement01.createOrderSubmit(OrderDataDTO.builder()
					.orderType("STANDARD")
					.deliveryNo(customerSapCode)
					.saleOrgSapCode(saleOrgSapCode)
					.saleOrgName(SaleOrgEnum.fetchName(saleOrgSapCode))
					.saleOrgZtCode(SaleOrgEnum.fetchZtCode(saleOrgSapCode))
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
					.contractRemark("自动化测试-合同备注")
					.remark("自动化测试-订单备注")
					.calculateReq(calculateReq)
					.build());
			createOrderSubmitResponse.then().body("resultCode", Matchers.equalTo("0"), "resultMsg", Matchers.equalTo("success"));
		});
		Then("[整单折扣分配计划分摊08]订单整单折扣使用记录成功", (DataTable dataTable) -> {
			List<Map<String, String>> caseDataMap = new ArrayList<>();
			String orderNo = orderApiOrderManagement01.selectOrderInfo(OrderQueryPageByConditionDTO.builder()
					.state("PENDING_BUSINESS_REVIEW")
					.cancelStatus("NOT_CANCEL")
					.saleOrgSapCode(saleOrgSapCode)
					.customerCodeOrName(customerSapCode)
					.build());
			String parentsOrderNo = StringUtils.left(orderNo, 22);
			dataTable.asMaps().forEach(caseData -> {
				Map<String, String> processedData = new HashMap<>();
				processedData.put("parentOrderNo", parentsOrderNo);
				processedData.put("parentOrderItemNo", Optional.ofNullable(caseData.get("parentOrderItemNo")).orElse(""));
				processedData.put("orderNo", orderNo);
				processedData.put("orderItemNo", Optional.ofNullable(caseData.get("orderItemNo")).orElse(""));
				String remark = Optional.ofNullable(caseData.get("remark")).orElse("");
				getAllocationPlanInfoByRemark(remark);
				processedData.put("orderDiscountApplicationPlanId", allocationPlanId);
				processedData.put("orderDiscountApplicationPlanDetailId", allocationPlanDetailId);
				processedData.put("operateType", Optional.ofNullable(caseData.get("operateType")).orElse(""));
				processedData.put("operateAmount", Optional.ofNullable(caseData.get("operateAmount")).orElse(""));
				caseDataMap.add(processedData);
			});
			List<Map<String, Object>> sqlResult = orderDiscountDetailBatchSelectData01.queryOrderDiscountDetailData01(parentsOrderNo);
			discountCheckAllocationPlan01.checkDiscountAllocationPlan(caseDataMap, sqlResult);
			orderBatchClearData01.clearOrderData01(orderNo);
			priceBatchClearData01.clearPirceData01("at");
			orderBatchClearData01.clearOrderPreDiscountData01("at");
			orderBatchClearData01.clearDiscountData01("at");
		});
	}


	private void scenario09() {
		Given("[整单折扣分配计划分摊09]当前存在如下客户信息", (DataTable dataTable) -> {
			createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis());
			domainDataTable = dataTable;
			domainDataTable.asMaps().forEach(caseData -> {
				saleOrgSapCode = Optional.ofNullable(caseData.get("saleOrgSapCode")).orElse("");
				regionCode = Optional.ofNullable(caseData.get("regionCode")).orElse("");
				regionName = Optional.ofNullable(caseData.get("regionName")).orElse("");
				customerSapCode = Optional.ofNullable(caseData.get("customerSapCode")).orElse("");
				shippingAddress = Optional.ofNullable(caseData.get("shippingAddress")).orElse("");
				shippingRecipient = Optional.ofNullable(caseData.get("shippingRecipient")).orElse("");
				shippingTelephone = Optional.ofNullable(caseData.get("shippingTelephone")).orElse("");
			});
		});
		When("[整单折扣分配计划分摊09]当前存在如下的预折商品信息", (DataTable dataTable) -> {
			preDiscountId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			preDiscountDetailId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			beginDate = new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis());
			endDate = new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis() + 24 * 60 * 60 * 1000);
			orderBatchInsertData01.insertOrderPreDidcountData01(OrderPreDiscountDTO.builder()
					.preDiscountId(preDiscountId)
					.customerSapCode(customerSapCode)
					.saleOrgSapCode(saleOrgSapCode)
					.regionCode("")
					.regionName("")
					.effectiveState("ENABLE")
					.preType("CUSTOMER_PRE_DISCOUNT")
					.createPerson("at")
					.createTime(createTime)
					.build());
			dataTable.asMaps().stream().forEach(caseData -> {
				String id = "100000" + Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				BigDecimal currentPrice = new BigDecimal(caseData.get("currentPrice")).setScale(8, RoundingMode.HALF_UP);
				String calculationType = Optional.ofNullable(caseData.get("calculationType")).orElse("");
				BigDecimal calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(8, RoundingMode.HALF_UP);
				String preType = Optional.ofNullable(caseData.get("preType")).orElse("");
				String effectiveState = Optional.ofNullable(caseData.get("effectiveState")).orElse("");
				String distributionChannelCode = Optional.ofNullable(caseData.get("distributionChannelCode")).orElse("");
				String distributionChannelName = Optional.ofNullable(caseData.get("distributionChannelName")).orElse("");
				orderBatchInsertData01.insertOrderPreDidcountDetailData01(OrderPreDiscountDetailDTO.builder()
						.id(id)
						.preDiscountDetailId(preDiscountDetailId)
						.preDiscountId(preDiscountId)
						.itemSapCode(itemSapCode)
						.distributionChannelCode(distributionChannelCode)
						.distributionChannelName(distributionChannelName)
						.currentPrice(currentPrice)
						.calculationType(calculationType)
						.calculationValue(calculationValue)
						.beginDate(beginDate)
						.endDate(endDate)
						.effectiveState(effectiveState)
						.preType("CUSTOMER_PRE_DISCOUNT")
						.createPerson("at")
						.createTime(createTime)
						.build()
				);
			});
		});
		And("[整单折扣分配计划分摊09]当前存在如下的预折折扣信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				BigDecimal calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(8, RoundingMode.HALF_UP);
				String effectiveState = Optional.ofNullable(caseData.get("effectiveState")).orElse("");
				orderBatchUpdateData01.updateOrderPreDiscountDetailCalculationValue(OrderPreDiscountDetailDTO.builder().effectiveState(effectiveState).calculationValue(calculationValue).itemSapCode(itemSapCode).build(), OrderPreDiscountDTO.builder().customerSapCode(customerSapCode).build());
			});
		});
		And("[整单折扣分配计划分摊09]当前存在如下的[纯赠]整单折扣申请单信息", (DataTable dataTable) -> {
			applicationFormId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			processOrderNo = "ZDZK" + new SimpleDateFormat("yyyyMMddHHmm").format(System.currentTimeMillis()) + "_1";
			dataTable.asMaps().stream().forEach(caseData -> {
				String projectType = Optional.ofNullable(caseData.get("projectType")).orElse("");
				String module = Optional.ofNullable(caseData.get("module")).orElse("");
				orderBatchInsertData01.insertOrderApplicationFormData01(OrderApplicationFormDTO.builder()
						.applicationFormId(applicationFormId)
						.processOrderNo(processOrderNo)
						.applicant("at-OTC分公司")
						.applicationDate(createTime)
						.projectType(projectType)
						.module(module)
						.status("APPROVED")
						.executionStartTime(beginDate)
						.executionEndTime(endDate)
						.saleOrgSapCode(saleOrgSapCode)
						.createPerson("at")
						.createTime(createTime)
						.build());
			});
		});
		And("[整单折扣分配计划分摊09]当前存在如下的[纯赠]整单折扣申请单商品信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				applicationFormDetailId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String quantity = Optional.ofNullable(caseData.get("quantity")).orElse("");
				String units = Optional.ofNullable(caseData.get("units")).orElse("");
				orderBatchInsertData01.insertOrderApplicationFormDetailData01(OrderApplicationFormDetailDTO.builder()
						.applicationFormDetailId(applicationFormDetailId)
						.applicationFormId(applicationFormId)
						.itemSapCode(itemSapCode)
						.quantity(quantity)
						.units(units)
						.createPerson("at")
						.createTime(createTime)
						.build());
			});
		});
		And("[整单折扣分配计划分摊09]当前存在如下的[纯赠-AT_FP001]整单折扣分配计划信息", () -> {
			allocationPlanId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			allocationPlanId_001 = allocationPlanId;
			planMonth = new SimpleDateFormat("yyyy-MM").format(System.currentTimeMillis());
			orderBatchInsertData01.insertOrderAllocationPlanData01(OrderAllocationPlanDTO.builder()
					.allocationPlanId(allocationPlanId)
					.orderDiscountApplicationFormId(applicationFormId)
					.status("IN_PROGRESS")
					.lockStatus("UNLOCK")
					.planMonth(planMonth)
					.newExecutionStartTime(beginDate)
					.newExecutionEndTime(endDate)
					.createPerson("at")
					.createTime(createTime)
					.build());
		});
		And("[整单折扣分配计划分摊09]当前存在如下的[纯赠-AT_FP001]整单折扣分配计划详细信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String remark = Optional.ofNullable(caseData.get("remark")).orElse("");
				allocationPlanDetailId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
				setAllocationPlanInfoByRemark(remark, allocationPlanId, allocationPlanDetailId);
				String originalItemSapCode = Optional.ofNullable(caseData.get("originalItemSapCode")).orElse("");
				String originalDistributionChannelCode = Optional.ofNullable(caseData.get("originalDistributionChannelCode")).orElse("");
				String originalDistributionChannelName = Optional.ofNullable(caseData.get("originalDistributionChannelName")).orElse("");
				String giftItemSapCode = Optional.ofNullable(caseData.get("giftItemSapCode")).orElse("");
				String giftDistributionChannelCode = Optional.ofNullable(caseData.get("giftDistributionChannelCode")).orElse("");
				String giftDistributionChannelName = Optional.ofNullable(caseData.get("giftDistributionChannelName")).orElse("");
				String allocationQuantity = Optional.ofNullable(caseData.get("allocationQuantity")).orElse("");
				Integer originalQuantity = Integer.valueOf(Optional.ofNullable(caseData.get("originalQuantity")).orElse("0"));
				Integer giftQuantity = Integer.parseInt(Optional.ofNullable(caseData.get("giftQuantity")).orElse("0"));
				Integer initialBonusQuantity = Integer.parseInt(Optional.ofNullable(caseData.get("initialBonusQuantity")).orElse("0"));
				String needShip = Optional.ofNullable(caseData.get("needShip")).orElse("");
				String unit = Optional.ofNullable(caseData.get("unit")).orElse("");
				String invalidStatus = Optional.ofNullable(caseData.get("invalidStatus")).orElse("");
				String lotNo = Optional.ofNullable(caseData.get("lotNo")).orElse("");
				orderBatchInsertData01.insertOrderApplicationPlanDetailData01(OrderAllocationPlanDetailDTO.builder()
						.allocationPlanDetailId(allocationPlanDetailId)
						.allocationPlanId(allocationPlanId)
						.applicationFormDetailId(applicationFormDetailId)
						.customerEntSapCode(customerSapCode)
						.saleOrgSapCode(saleOrgSapCode)
						.regionCode(regionCode)
						.regionName(regionName)
						.originalItemSapCode(originalItemSapCode)
						.originalDistributionChannelCode(originalDistributionChannelCode)
						.originalDistributionChannelName(originalDistributionChannelName)
						.giftItemSapCode(giftItemSapCode)
						.giftDistributionChannelCode(giftDistributionChannelCode)
						.giftDistributionChannelName(giftDistributionChannelName)
						.allocationQuantity(allocationQuantity)
						.originalQuantity(originalQuantity)
						.giftQuantity(giftQuantity)
						.initialBonusQuantity(initialBonusQuantity)
						.unit(unit)
						.lotNo(lotNo)
						.productionDate(beginDate)
						.expirationDate(endDate)
						.needShip(needShip)
						.invalidStatus(invalidStatus)
						.remark(remark)
						.createPerson("at")
						.createTime(createTime)
						.build());
			});
		});
		And("[整单折扣分配计划分摊09]当前存在如下的[捐赠]整单折扣申请单信息", (DataTable dataTable) -> {
			applicationFormId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			processOrderNo = "ZDZK" + new SimpleDateFormat("yyyyMMddHHmm").format(System.currentTimeMillis()) + "_2";
			dataTable.asMaps().stream().forEach(caseData -> {
				String projectType = Optional.ofNullable(caseData.get("projectType")).orElse("");
				String module = Optional.ofNullable(caseData.get("module")).orElse("");
				orderBatchInsertData01.insertOrderApplicationFormData01(OrderApplicationFormDTO.builder()
						.applicationFormId(applicationFormId)
						.processOrderNo(processOrderNo)
						.applicant("at-OTC分公司")
						.applicationDate(createTime)
						.projectType(projectType)
						.module(module)
						.status("APPROVED")
						.executionStartTime(beginDate)
						.executionEndTime(endDate)
						.saleOrgSapCode(saleOrgSapCode)
						.createPerson("at")
						.createTime(createTime)
						.build());
			});
		});
		And("[整单折扣分配计划分摊09]当前存在如下的[捐赠]整单折扣申请单商品信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				applicationFormDetailId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String quantity = Optional.ofNullable(caseData.get("quantity")).orElse("");
				String units = Optional.ofNullable(caseData.get("units")).orElse("");
				orderBatchInsertData01.insertOrderApplicationFormDetailData01(OrderApplicationFormDetailDTO.builder()
						.applicationFormDetailId(applicationFormDetailId)
						.applicationFormId(applicationFormId)
						.itemSapCode(itemSapCode)
						.quantity(quantity)
						.units(units)
						.createPerson("at")
						.createTime(createTime)
						.build());
			});
		});
		And("[整单折扣分配计划分摊09]当前存在如下的[捐赠-AT_FP002]整单折扣分配计划信息", () -> {
			allocationPlanId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			allocationPlanId_002 = allocationPlanId;
			planMonth = new SimpleDateFormat("yyyy-MM").format(System.currentTimeMillis());
			orderBatchInsertData01.insertOrderAllocationPlanData01(OrderAllocationPlanDTO.builder()
					.allocationPlanId(allocationPlanId)
					.orderDiscountApplicationFormId(applicationFormId)
					.status("IN_PROGRESS")
					.lockStatus("UNLOCK")
					.planMonth(planMonth)
					.newExecutionStartTime(beginDate)
					.newExecutionEndTime(endDate)
					.createPerson("at")
					.createTime(createTime)
					.build());
		});
		And("[整单折扣分配计划分摊09]当前存在如下的[捐赠-AT_FP002]整单折扣分配计划详细信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String remark = Optional.ofNullable(caseData.get("remark")).orElse("");
				allocationPlanDetailId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
				setAllocationPlanInfoByRemark(remark, allocationPlanId, allocationPlanDetailId);
				String originalItemSapCode = Optional.ofNullable(caseData.get("originalItemSapCode")).orElse("");
				String originalDistributionChannelCode = Optional.ofNullable(caseData.get("originalDistributionChannelCode")).orElse("");
				String originalDistributionChannelName = Optional.ofNullable(caseData.get("originalDistributionChannelName")).orElse("");
				String giftItemSapCode = Optional.ofNullable(caseData.get("giftItemSapCode")).orElse("");
				String giftDistributionChannelCode = Optional.ofNullable(caseData.get("giftDistributionChannelCode")).orElse("");
				String giftDistributionChannelName = Optional.ofNullable(caseData.get("giftDistributionChannelName")).orElse("");
				String allocationQuantity = Optional.ofNullable(caseData.get("allocationQuantity")).orElse("");
				Integer originalQuantity = Integer.valueOf(Optional.ofNullable(caseData.get("originalQuantity")).orElse("0"));
				Integer giftQuantity = Integer.parseInt(Optional.ofNullable(caseData.get("giftQuantity")).orElse("0"));
				Integer initialBonusQuantity = Integer.parseInt(Optional.ofNullable(caseData.get("initialBonusQuantity")).orElse("0"));
				String needShip = Optional.ofNullable(caseData.get("needShip")).orElse("");
				String unit = Optional.ofNullable(caseData.get("unit")).orElse("");
				String invalidStatus = Optional.ofNullable(caseData.get("invalidStatus")).orElse("");
				String lotNo = Optional.ofNullable(caseData.get("lotNo")).orElse("");
				orderBatchInsertData01.insertOrderApplicationPlanDetailData01(OrderAllocationPlanDetailDTO.builder()
						.allocationPlanDetailId(allocationPlanDetailId)
						.allocationPlanId(allocationPlanId)
						.applicationFormDetailId(applicationFormDetailId)
						.customerEntSapCode(customerSapCode)
						.saleOrgSapCode(saleOrgSapCode)
						.regionCode(regionCode)
						.regionName(regionName)
						.originalItemSapCode(originalItemSapCode)
						.originalDistributionChannelCode(originalDistributionChannelCode)
						.originalDistributionChannelName(originalDistributionChannelName)
						.giftItemSapCode(giftItemSapCode)
						.giftDistributionChannelCode(giftDistributionChannelCode)
						.giftDistributionChannelName(giftDistributionChannelName)
						.allocationQuantity(allocationQuantity)
						.originalQuantity(originalQuantity)
						.giftQuantity(giftQuantity)
						.initialBonusQuantity(initialBonusQuantity)
						.unit(unit)
						.lotNo(lotNo)
						.productionDate(beginDate)
						.expirationDate(endDate)
						.needShip(needShip)
						.invalidStatus(invalidStatus)
						.remark(remark)
						.createPerson("at")
						.createTime(createTime)
						.build());
			});
		});
		And("[整单折扣分配计划分摊09]添加商品", (DataTable dataTable) -> {
			log.info("添加商品");
		});
		And("[整单折扣分配计划分摊09]选择项目", (DataTable dataTable) -> {
			log.info("选择项目");
		});
		Then("[整单折扣分配计划分摊09]创建订单应校验通过且提交成功", (DataTable dataTable) -> {
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
			List<OrderDiscountCalcCalculateReqGroupListListDTO> orderDiscountCalcCalculateReqGroupListListDTOS = new ArrayList<>();
			dataTable.asMaps().forEach(caseData -> {
				String id = "100000" + Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String orderWideDiscountSubject = Optional.ofNullable(caseData.get("orderWideDiscountSubject")).orElse("");
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String needShip = Optional.ofNullable(caseData.get("needShip")).orElse("");
				String unitPrice = new BigDecimal(caseData.get("unitPrice")).setScale(2, RoundingMode.HALF_UP).toString();
				String quantity = Optional.ofNullable(caseData.get("quantity")).orElse("");
				String boxRatio = new Integer(caseData.get("boxRatio")).toString();
				String preDiscount = new BigDecimal(caseData.get("preDiscount")).setScale(2, RoundingMode.HALF_UP).toString();
				if ("0.00".equals(preDiscount)) {
					id = ""; // 如果是"0"，则将id设置为空字符串
				}
				String calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(2, RoundingMode.HALF_UP).toString();
				String totalInline = new BigDecimal(caseData.get("totalInline")).setScale(2, RoundingMode.HALF_UP).toString();
				String preDiscountTotal = new BigDecimal(caseData.get("preDiscountTotal")).setScale(2, RoundingMode.HALF_UP).toString();
				String orderDiscountTotal = new BigDecimal(caseData.get("orderDiscountTotal")).setScale(2, RoundingMode.HALF_UP).toString();
				String discountProportion = String.format("%.4f", new BigDecimal(caseData.get("preDiscountTotal").toString()).add(new BigDecimal(caseData.get("orderDiscountTotal").toString())).divide(new BigDecimal(caseData.get("totalInline").toString()), 4, RoundingMode.HALF_UP).doubleValue());
				String remark = Optional.ofNullable(caseData.get("remark")).orElse("");
				getAllocationPlanInfoByRemark(remark);
				orderDiscountCalcCalculateReqGroupListListDTOS.add(OrderDiscountCalcCalculateReqGroupListListDTO.builder()
						.orderWideDiscountSubject(orderWideDiscountSubject)
						.discountAllocationPlanId(allocationPlanId)
						.discountAllocationPlanDetailId(allocationPlanDetailId)
						.discountId(id)
						.preDiscountDetailId(id)
						.itemSapCode(itemSapCode)
						.itemName(ProductEnum.fetchProductName(itemSapCode))
						.itemDesc(ProductEnum.fetchProductSpec(itemSapCode))
						.itemGroupCode("01")
						.itemGroupName("成品药-非管制药品")
						.distributionChannelCode("22")
						.distributionChannelName("分销-非医疗")
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
						.discountProportion(discountProportion)
						.build());
			});
			OrderDiscountCalcCalculateReqDTO calculateReq = orderApiOrderManagement01.discountCalcAfterData(OrderDataDTO.builder()
					.orderType("STANDARD")
					.deliveryNo(customerSapCode)
					.saleOrgSapCode(saleOrgSapCode)
					.saleOrgName(SaleOrgEnum.fetchName(saleOrgSapCode))
					.saleOrgZtCode(SaleOrgEnum.fetchZtCode(saleOrgSapCode))
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
					.contractRemark("自动化测试-合同备注")
					.remark("自动化测试-订单备注")
					.calculateReq(OrderDiscountCalcCalculateReqDTO.builder()
							.totalInline(totalInlineSum)
							.preDiscountTotal(preDiscountTotalSum)
							.orderDiscountTotal(orderDiscountTotalSum)
							.groupList(Arrays.asList(OrderDiscountCalcCalculateReqGroupListDTO.builder()
									.itemGroupCode("01")
									.itemGroupName("成品药-非管制药品")
									.distributionChannelCode("22")
									.distributionChannelName("分销-非医疗")
									.totalInline(totalInlineSum)
									.preDiscountTotal(preDiscountTotalSum)
									.orderDiscountTotal(orderDiscountTotalSum)
									.list(orderDiscountCalcCalculateReqGroupListListDTOS)
									.build()))
							.build()
					)
					.build());
			Response createOrderVerifyResponse = orderApiOrderManagement01.createOrderVerify(OrderDataDTO.builder()
					.orderType("STANDARD")
					.deliveryNo(customerSapCode)
					.saleOrgSapCode(saleOrgSapCode)
					.saleOrgName(SaleOrgEnum.fetchName(saleOrgSapCode))
					.saleOrgZtCode(SaleOrgEnum.fetchZtCode(saleOrgSapCode))
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
					.contractRemark("自动化测试-合同备注")
					.remark("自动化测试-订单备注")
					.calculateReq(calculateReq)
					.build());
			createOrderVerifyResponse.then().body("resultCode", Matchers.equalTo("0"), "resultMsg", Matchers.equalTo("success"));
			Response createOrderSubmitResponse = orderApiOrderManagement01.createOrderSubmit(OrderDataDTO.builder()
					.orderType("STANDARD")
					.deliveryNo(customerSapCode)
					.saleOrgSapCode(saleOrgSapCode)
					.saleOrgName(SaleOrgEnum.fetchName(saleOrgSapCode))
					.saleOrgZtCode(SaleOrgEnum.fetchZtCode(saleOrgSapCode))
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
					.contractRemark("自动化测试-合同备注")
					.remark("自动化测试-订单备注")
					.calculateReq(calculateReq)
					.build());
			createOrderSubmitResponse.then().body("resultCode", Matchers.equalTo("0"), "resultMsg", Matchers.equalTo("success"));
		});
		Then("[整单折扣分配计划分摊09]订单整单折扣使用记录成功", (DataTable dataTable) -> {
			List<Map<String, String>> caseDataMap = new ArrayList<>();
			String orderNo = orderApiOrderManagement01.selectOrderInfo(OrderQueryPageByConditionDTO.builder()
					.state("PENDING_BUSINESS_REVIEW")
					.cancelStatus("NOT_CANCEL")
					.saleOrgSapCode(saleOrgSapCode)
					.customerCodeOrName(customerSapCode)
					.build());
			String parentsOrderNo = StringUtils.left(orderNo, 22);
			dataTable.asMaps().forEach(caseData -> {
				Map<String, String> processedData = new HashMap<>();
				processedData.put("parentOrderNo", parentsOrderNo);
				processedData.put("parentOrderItemNo", Optional.ofNullable(caseData.get("parentOrderItemNo")).orElse(""));
				processedData.put("orderNo", orderNo);
				processedData.put("orderItemNo", Optional.ofNullable(caseData.get("orderItemNo")).orElse(""));
				String remark = Optional.ofNullable(caseData.get("remark")).orElse("");
				getAllocationPlanInfoByRemark(remark);
				processedData.put("orderDiscountApplicationPlanId", allocationPlanId);
				processedData.put("orderDiscountApplicationPlanDetailId", allocationPlanDetailId);
				processedData.put("operateType", Optional.ofNullable(caseData.get("operateType")).orElse(""));
				processedData.put("operateAmount", Optional.ofNullable(caseData.get("operateAmount")).orElse(""));
				caseDataMap.add(processedData);
			});
			List<Map<String, Object>> sqlResult = orderDiscountDetailBatchSelectData01.queryOrderDiscountDetailData01(parentsOrderNo);
			discountCheckAllocationPlan01.checkDiscountAllocationPlan(caseDataMap, sqlResult);
			orderBatchClearData01.clearOrderData01(orderNo);
			priceBatchClearData01.clearPirceData01("at");
			orderBatchClearData01.clearOrderPreDiscountData01("at");
			orderBatchClearData01.clearDiscountData01("at");
		});
	}


	private void scenario10() {
		Given("[整单折扣分配计划分摊10]当前存在如下客户信息", (DataTable dataTable) -> {
			createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis());
			domainDataTable = dataTable;
			domainDataTable.asMaps().forEach(caseData -> {
				saleOrgSapCode = Optional.ofNullable(caseData.get("saleOrgSapCode")).orElse("");
				regionCode = Optional.ofNullable(caseData.get("regionCode")).orElse("");
				regionName = Optional.ofNullable(caseData.get("regionName")).orElse("");
				customerSapCode = Optional.ofNullable(caseData.get("customerSapCode")).orElse("");
				shippingAddress = Optional.ofNullable(caseData.get("shippingAddress")).orElse("");
				shippingRecipient = Optional.ofNullable(caseData.get("shippingRecipient")).orElse("");
				shippingTelephone = Optional.ofNullable(caseData.get("shippingTelephone")).orElse("");
			});
		});
		When("[整单折扣分配计划分摊10]当前存在如下的预折商品信息", (DataTable dataTable) -> {
			preDiscountId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			preDiscountDetailId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			beginDate = new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis());
			endDate = new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis() + 24 * 60 * 60 * 1000);
			orderBatchInsertData01.insertOrderPreDidcountData01(OrderPreDiscountDTO.builder()
					.preDiscountId(preDiscountId)
					.customerSapCode(customerSapCode)
					.saleOrgSapCode(saleOrgSapCode)
					.regionCode("")
					.regionName("")
					.effectiveState("ENABLE")
					.preType("CUSTOMER_PRE_DISCOUNT")
					.createPerson("at")
					.createTime(createTime)
					.build());
			dataTable.asMaps().stream().forEach(caseData -> {
				String id = "100000" + Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				BigDecimal currentPrice = new BigDecimal(caseData.get("currentPrice")).setScale(8, RoundingMode.HALF_UP);
				String calculationType = Optional.ofNullable(caseData.get("calculationType")).orElse("");
				BigDecimal calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(8, RoundingMode.HALF_UP);
				String preType = Optional.ofNullable(caseData.get("preType")).orElse("");
				String effectiveState = Optional.ofNullable(caseData.get("effectiveState")).orElse("");
				String distributionChannelCode = Optional.ofNullable(caseData.get("distributionChannelCode")).orElse("");
				String distributionChannelName = Optional.ofNullable(caseData.get("distributionChannelName")).orElse("");
				orderBatchInsertData01.insertOrderPreDidcountDetailData01(OrderPreDiscountDetailDTO.builder()
						.id(id)
						.preDiscountDetailId(preDiscountDetailId)
						.preDiscountId(preDiscountId)
						.itemSapCode(itemSapCode)
						.distributionChannelCode(distributionChannelCode)
						.distributionChannelName(distributionChannelName)
						.currentPrice(currentPrice)
						.calculationType(calculationType)
						.calculationValue(calculationValue)
						.beginDate(beginDate)
						.endDate(endDate)
						.effectiveState(effectiveState)
						.preType("CUSTOMER_PRE_DISCOUNT")
						.createPerson("at")
						.createTime(createTime)
						.build()
				);
			});
		});
		And("[整单折扣分配计划分摊10]当前存在如下的预折折扣信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				BigDecimal calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(8, RoundingMode.HALF_UP);
				String effectiveState = Optional.ofNullable(caseData.get("effectiveState")).orElse("");
				orderBatchUpdateData01.updateOrderPreDiscountDetailCalculationValue(OrderPreDiscountDetailDTO.builder().effectiveState(effectiveState).calculationValue(calculationValue).itemSapCode(itemSapCode).build(), OrderPreDiscountDTO.builder().customerSapCode(customerSapCode).build());
			});
		});
		And("[整单折扣分配计划分摊10]当前存在如下的[纯赠]整单折扣申请单信息", (DataTable dataTable) -> {
			applicationFormId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			processOrderNo = "ZDZK" + new SimpleDateFormat("yyyyMMddHHmm").format(System.currentTimeMillis()) + "_1";
			dataTable.asMaps().stream().forEach(caseData -> {
				String projectType = Optional.ofNullable(caseData.get("projectType")).orElse("");
				String module = Optional.ofNullable(caseData.get("module")).orElse("");
				orderBatchInsertData01.insertOrderApplicationFormData01(OrderApplicationFormDTO.builder()
						.applicationFormId(applicationFormId)
						.processOrderNo(processOrderNo)
						.applicant("at-OTC分公司")
						.applicationDate(createTime)
						.projectType(projectType)
						.module(module)
						.status("APPROVED")
						.executionStartTime(beginDate)
						.executionEndTime(endDate)
						.saleOrgSapCode(saleOrgSapCode)
						.createPerson("at")
						.createTime(createTime)
						.build());
			});
		});
		And("[整单折扣分配计划分摊10]当前存在如下的[纯赠]整单折扣申请单商品信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				applicationFormDetailId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String quantity = Optional.ofNullable(caseData.get("quantity")).orElse("");
				String units = Optional.ofNullable(caseData.get("units")).orElse("");
				orderBatchInsertData01.insertOrderApplicationFormDetailData01(OrderApplicationFormDetailDTO.builder()
						.applicationFormDetailId(applicationFormDetailId)
						.applicationFormId(applicationFormId)
						.itemSapCode(itemSapCode)
						.quantity(quantity)
						.units(units)
						.createPerson("at")
						.createTime(createTime)
						.build());
			});
		});
		And("[整单折扣分配计划分摊10]当前存在如下的[纯赠-AT_FP001]整单折扣分配计划信息", () -> {
			allocationPlanId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			allocationPlanId_001 = allocationPlanId;
			planMonth = new SimpleDateFormat("yyyy-MM").format(System.currentTimeMillis());
			orderBatchInsertData01.insertOrderAllocationPlanData01(OrderAllocationPlanDTO.builder()
					.allocationPlanId(allocationPlanId)
					.orderDiscountApplicationFormId(applicationFormId)
					.status("IN_PROGRESS")
					.lockStatus("UNLOCK")
					.planMonth(planMonth)
					.newExecutionStartTime(beginDate)
					.newExecutionEndTime(endDate)
					.createPerson("at")
					.createTime(createTime)
					.build());
		});
		And("[整单折扣分配计划分摊10]当前存在如下的[纯赠-AT_FP001]整单折扣分配计划详细信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String remark = Optional.ofNullable(caseData.get("remark")).orElse("");
				allocationPlanDetailId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
				setAllocationPlanInfoByRemark(remark, allocationPlanId, allocationPlanDetailId);
				String originalItemSapCode = Optional.ofNullable(caseData.get("originalItemSapCode")).orElse("");
				String originalDistributionChannelCode = Optional.ofNullable(caseData.get("originalDistributionChannelCode")).orElse("");
				String originalDistributionChannelName = Optional.ofNullable(caseData.get("originalDistributionChannelName")).orElse("");
				String giftItemSapCode = Optional.ofNullable(caseData.get("giftItemSapCode")).orElse("");
				String giftDistributionChannelCode = Optional.ofNullable(caseData.get("giftDistributionChannelCode")).orElse("");
				String giftDistributionChannelName = Optional.ofNullable(caseData.get("giftDistributionChannelName")).orElse("");
				String allocationQuantity = Optional.ofNullable(caseData.get("allocationQuantity")).orElse("");
				Integer originalQuantity = Integer.valueOf(Optional.ofNullable(caseData.get("originalQuantity")).orElse("0"));
				Integer giftQuantity = Integer.parseInt(Optional.ofNullable(caseData.get("giftQuantity")).orElse("0"));
				Integer initialBonusQuantity = Integer.parseInt(Optional.ofNullable(caseData.get("initialBonusQuantity")).orElse("0"));
				String needShip = Optional.ofNullable(caseData.get("needShip")).orElse("");
				String unit = Optional.ofNullable(caseData.get("unit")).orElse("");
				String invalidStatus = Optional.ofNullable(caseData.get("invalidStatus")).orElse("");
				String lotNo = Optional.ofNullable(caseData.get("lotNo")).orElse("");
				orderBatchInsertData01.insertOrderApplicationPlanDetailData01(OrderAllocationPlanDetailDTO.builder()
						.allocationPlanDetailId(allocationPlanDetailId)
						.allocationPlanId(allocationPlanId)
						.applicationFormDetailId(applicationFormDetailId)
						.customerEntSapCode(customerSapCode)
						.saleOrgSapCode(saleOrgSapCode)
						.regionCode(regionCode)
						.regionName(regionName)
						.originalItemSapCode(originalItemSapCode)
						.originalDistributionChannelCode(originalDistributionChannelCode)
						.originalDistributionChannelName(originalDistributionChannelName)
						.giftItemSapCode(giftItemSapCode)
						.giftDistributionChannelCode(giftDistributionChannelCode)
						.giftDistributionChannelName(giftDistributionChannelName)
						.allocationQuantity(allocationQuantity)
						.originalQuantity(originalQuantity)
						.giftQuantity(giftQuantity)
						.initialBonusQuantity(initialBonusQuantity)
						.unit(unit)
						.lotNo(lotNo)
						.productionDate(beginDate)
						.expirationDate(endDate)
						.needShip(needShip)
						.invalidStatus(invalidStatus)
						.remark(remark)
						.createPerson("at")
						.createTime(createTime)
						.build());
			});
		});
		And("[整单折扣分配计划分摊10]当前存在如下的[捐赠]整单折扣申请单信息", (DataTable dataTable) -> {
			applicationFormId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			processOrderNo = "ZDZK" + new SimpleDateFormat("yyyyMMddHHmm").format(System.currentTimeMillis()) + "_2";
			dataTable.asMaps().stream().forEach(caseData -> {
				String projectType = Optional.ofNullable(caseData.get("projectType")).orElse("");
				String module = Optional.ofNullable(caseData.get("module")).orElse("");
				orderBatchInsertData01.insertOrderApplicationFormData01(OrderApplicationFormDTO.builder()
						.applicationFormId(applicationFormId)
						.processOrderNo(processOrderNo)
						.applicant("at-OTC分公司")
						.applicationDate(createTime)
						.projectType(projectType)
						.module(module)
						.status("APPROVED")
						.executionStartTime(beginDate)
						.executionEndTime(endDate)
						.saleOrgSapCode(saleOrgSapCode)
						.createPerson("at")
						.createTime(createTime)
						.build());
			});
		});
		And("[整单折扣分配计划分摊10]当前存在如下的[捐赠]整单折扣申请单商品信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				applicationFormDetailId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String quantity = Optional.ofNullable(caseData.get("quantity")).orElse("");
				String units = Optional.ofNullable(caseData.get("units")).orElse("");
				orderBatchInsertData01.insertOrderApplicationFormDetailData01(OrderApplicationFormDetailDTO.builder()
						.applicationFormDetailId(applicationFormDetailId)
						.applicationFormId(applicationFormId)
						.itemSapCode(itemSapCode)
						.quantity(quantity)
						.units(units)
						.createPerson("at")
						.createTime(createTime)
						.build());
			});
		});
		And("[整单折扣分配计划分摊10]当前存在如下的[捐赠-AT_FP002]整单折扣分配计划信息", () -> {
			allocationPlanId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			allocationPlanId_002 = allocationPlanId;
			planMonth = new SimpleDateFormat("yyyy-MM").format(System.currentTimeMillis());
			orderBatchInsertData01.insertOrderAllocationPlanData01(OrderAllocationPlanDTO.builder()
					.allocationPlanId(allocationPlanId)
					.orderDiscountApplicationFormId(applicationFormId)
					.status("IN_PROGRESS")
					.lockStatus("UNLOCK")
					.planMonth(planMonth)
					.newExecutionStartTime(beginDate)
					.newExecutionEndTime(endDate)
					.createPerson("at")
					.createTime(createTime)
					.build());
		});
		And("[整单折扣分配计划分摊10]当前存在如下的[捐赠-AT_FP002]整单折扣分配计划详细信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String remark = Optional.ofNullable(caseData.get("remark")).orElse("");
				allocationPlanDetailId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
				setAllocationPlanInfoByRemark(remark, allocationPlanId, allocationPlanDetailId);
				String originalItemSapCode = Optional.ofNullable(caseData.get("originalItemSapCode")).orElse("");
				String originalDistributionChannelCode = Optional.ofNullable(caseData.get("originalDistributionChannelCode")).orElse("");
				String originalDistributionChannelName = Optional.ofNullable(caseData.get("originalDistributionChannelName")).orElse("");
				String giftItemSapCode = Optional.ofNullable(caseData.get("giftItemSapCode")).orElse("");
				String giftDistributionChannelCode = Optional.ofNullable(caseData.get("giftDistributionChannelCode")).orElse("");
				String giftDistributionChannelName = Optional.ofNullable(caseData.get("giftDistributionChannelName")).orElse("");
				String allocationQuantity = Optional.ofNullable(caseData.get("allocationQuantity")).orElse("");
				Integer originalQuantity = Integer.valueOf(Optional.ofNullable(caseData.get("originalQuantity")).orElse("0"));
				Integer giftQuantity = Integer.parseInt(Optional.ofNullable(caseData.get("giftQuantity")).orElse("0"));
				Integer initialBonusQuantity = Integer.parseInt(Optional.ofNullable(caseData.get("initialBonusQuantity")).orElse("0"));
				String needShip = Optional.ofNullable(caseData.get("needShip")).orElse("");
				String unit = Optional.ofNullable(caseData.get("unit")).orElse("");
				String invalidStatus = Optional.ofNullable(caseData.get("invalidStatus")).orElse("");
				String lotNo = Optional.ofNullable(caseData.get("lotNo")).orElse("");
				orderBatchInsertData01.insertOrderApplicationPlanDetailData01(OrderAllocationPlanDetailDTO.builder()
						.allocationPlanDetailId(allocationPlanDetailId)
						.allocationPlanId(allocationPlanId)
						.applicationFormDetailId(applicationFormDetailId)
						.customerEntSapCode(customerSapCode)
						.saleOrgSapCode(saleOrgSapCode)
						.regionCode(regionCode)
						.regionName(regionName)
						.originalItemSapCode(originalItemSapCode)
						.originalDistributionChannelCode(originalDistributionChannelCode)
						.originalDistributionChannelName(originalDistributionChannelName)
						.giftItemSapCode(giftItemSapCode)
						.giftDistributionChannelCode(giftDistributionChannelCode)
						.giftDistributionChannelName(giftDistributionChannelName)
						.allocationQuantity(allocationQuantity)
						.originalQuantity(originalQuantity)
						.giftQuantity(giftQuantity)
						.initialBonusQuantity(initialBonusQuantity)
						.unit(unit)
						.lotNo(lotNo)
						.productionDate(beginDate)
						.expirationDate(endDate)
						.needShip(needShip)
						.invalidStatus(invalidStatus)
						.remark(remark)
						.createPerson("at")
						.createTime(createTime)
						.build());
			});
		});
		And("[整单折扣分配计划分摊10]添加商品", (DataTable dataTable) -> {
			log.info("添加商品");
		});
		And("[整单折扣分配计划分摊10]选择项目", (DataTable dataTable) -> {
			log.info("选择项目");
		});
		Then("[整单折扣分配计划分摊10]创建订单应校验通过且提交成功", (DataTable dataTable) -> {
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
			List<OrderDiscountCalcCalculateReqGroupListListDTO> orderDiscountCalcCalculateReqGroupListListDTOS = new ArrayList<>();
			dataTable.asMaps().forEach(caseData -> {
				String id = "100000" + Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String orderWideDiscountSubject = Optional.ofNullable(caseData.get("orderWideDiscountSubject")).orElse("");
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String needShip = Optional.ofNullable(caseData.get("needShip")).orElse("");
				String unitPrice = new BigDecimal(caseData.get("unitPrice")).setScale(2, RoundingMode.HALF_UP).toString();
				String quantity = Optional.ofNullable(caseData.get("quantity")).orElse("");
				String boxRatio = new Integer(caseData.get("boxRatio")).toString();
				String preDiscount = new BigDecimal(caseData.get("preDiscount")).setScale(2, RoundingMode.HALF_UP).toString();
				if ("0.00".equals(preDiscount)) {
					id = ""; // 如果是"0"，则将id设置为空字符串
				}
				String calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(2, RoundingMode.HALF_UP).toString();
				String totalInline = new BigDecimal(caseData.get("totalInline")).setScale(2, RoundingMode.HALF_UP).toString();
				String preDiscountTotal = new BigDecimal(caseData.get("preDiscountTotal")).setScale(2, RoundingMode.HALF_UP).toString();
				String orderDiscountTotal = new BigDecimal(caseData.get("orderDiscountTotal")).setScale(2, RoundingMode.HALF_UP).toString();
				String discountProportion = String.format("%.4f", new BigDecimal(caseData.get("preDiscountTotal").toString()).add(new BigDecimal(caseData.get("orderDiscountTotal").toString())).divide(new BigDecimal(caseData.get("totalInline").toString()), 4, RoundingMode.HALF_UP).doubleValue());
				String remark = Optional.ofNullable(caseData.get("remark")).orElse("");
				getAllocationPlanInfoByRemark(remark);
				orderDiscountCalcCalculateReqGroupListListDTOS.add(OrderDiscountCalcCalculateReqGroupListListDTO.builder()
						.orderWideDiscountSubject(orderWideDiscountSubject)
						.discountAllocationPlanId(allocationPlanId)
						.discountAllocationPlanDetailId(allocationPlanDetailId)
						.discountId(id)
						.preDiscountDetailId(id)
						.itemSapCode(itemSapCode)
						.itemName(ProductEnum.fetchProductName(itemSapCode))
						.itemDesc(ProductEnum.fetchProductSpec(itemSapCode))
						.itemGroupCode("01")
						.itemGroupName("成品药-非管制药品")
						.distributionChannelCode("22")
						.distributionChannelName("分销-非医疗")
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
						.discountProportion(discountProportion)
						.build());
			});
			OrderDiscountCalcCalculateReqDTO calculateReq = orderApiOrderManagement01.discountCalcAfterData(OrderDataDTO.builder()
					.orderType("STANDARD")
					.deliveryNo(customerSapCode)
					.saleOrgSapCode(saleOrgSapCode)
					.saleOrgName(SaleOrgEnum.fetchName(saleOrgSapCode))
					.saleOrgZtCode(SaleOrgEnum.fetchZtCode(saleOrgSapCode))
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
					.contractRemark("自动化测试-合同备注")
					.remark("自动化测试-订单备注")
					.calculateReq(OrderDiscountCalcCalculateReqDTO.builder()
							.totalInline(totalInlineSum)
							.preDiscountTotal(preDiscountTotalSum)
							.orderDiscountTotal(orderDiscountTotalSum)
							.groupList(Arrays.asList(OrderDiscountCalcCalculateReqGroupListDTO.builder()
									.itemGroupCode("01")
									.itemGroupName("成品药-非管制药品")
									.distributionChannelCode("22")
									.distributionChannelName("分销-非医疗")
									.totalInline(totalInlineSum)
									.preDiscountTotal(preDiscountTotalSum)
									.orderDiscountTotal(orderDiscountTotalSum)
									.list(orderDiscountCalcCalculateReqGroupListListDTOS)
									.build()))
							.build()
					)
					.build());
			Response createOrderVerifyResponse = orderApiOrderManagement01.createOrderVerify(OrderDataDTO.builder()
					.orderType("STANDARD")
					.deliveryNo(customerSapCode)
					.saleOrgSapCode(saleOrgSapCode)
					.saleOrgName(SaleOrgEnum.fetchName(saleOrgSapCode))
					.saleOrgZtCode(SaleOrgEnum.fetchZtCode(saleOrgSapCode))
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
					.contractRemark("自动化测试-合同备注")
					.remark("自动化测试-订单备注")
					.calculateReq(calculateReq)
					.build());
			createOrderVerifyResponse.then().body("resultCode", Matchers.equalTo("0"), "resultMsg", Matchers.equalTo("success"));
			Response createOrderSubmitResponse = orderApiOrderManagement01.createOrderSubmit(OrderDataDTO.builder()
					.orderType("STANDARD")
					.deliveryNo(customerSapCode)
					.saleOrgSapCode(saleOrgSapCode)
					.saleOrgName(SaleOrgEnum.fetchName(saleOrgSapCode))
					.saleOrgZtCode(SaleOrgEnum.fetchZtCode(saleOrgSapCode))
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
					.contractRemark("自动化测试-合同备注")
					.remark("自动化测试-订单备注")
					.calculateReq(calculateReq)
					.build());
			createOrderSubmitResponse.then().body("resultCode", Matchers.equalTo("0"), "resultMsg", Matchers.equalTo("success"));
		});
		Then("[整单折扣分配计划分摊10]订单整单折扣使用记录成功", (DataTable dataTable) -> {
			List<Map<String, String>> caseDataMap = new ArrayList<>();
			String orderNo = orderApiOrderManagement01.selectOrderInfo(OrderQueryPageByConditionDTO.builder()
					.state("PENDING_BUSINESS_REVIEW")
					.cancelStatus("NOT_CANCEL")
					.saleOrgSapCode(saleOrgSapCode)
					.customerCodeOrName(customerSapCode)
					.build());
			String parentsOrderNo = StringUtils.left(orderNo, 22);
			dataTable.asMaps().forEach(caseData -> {
				Map<String, String> processedData = new HashMap<>();
				processedData.put("parentOrderNo", parentsOrderNo);
				processedData.put("parentOrderItemNo", Optional.ofNullable(caseData.get("parentOrderItemNo")).orElse(""));
				processedData.put("orderNo", orderNo);
				processedData.put("orderItemNo", Optional.ofNullable(caseData.get("orderItemNo")).orElse(""));
				String remark = Optional.ofNullable(caseData.get("remark")).orElse("");
				getAllocationPlanInfoByRemark(remark);
				processedData.put("orderDiscountApplicationPlanId", allocationPlanId);
				processedData.put("orderDiscountApplicationPlanDetailId", allocationPlanDetailId);
				processedData.put("operateType", Optional.ofNullable(caseData.get("operateType")).orElse(""));
				processedData.put("operateAmount", Optional.ofNullable(caseData.get("operateAmount")).orElse(""));
				caseDataMap.add(processedData);
			});
			List<Map<String, Object>> sqlResult = orderDiscountDetailBatchSelectData01.queryOrderDiscountDetailData01(parentsOrderNo);
			discountCheckAllocationPlan01.checkDiscountAllocationPlan(caseDataMap, sqlResult);
			orderBatchClearData01.clearOrderData01(orderNo);
			priceBatchClearData01.clearPirceData01("at");
			orderBatchClearData01.clearOrderPreDiscountData01("at");
			orderBatchClearData01.clearDiscountData01("at");
		});
	}


	private void scenario11() {
		Given("[整单折扣分配计划分摊11]当前存在如下客户信息", (DataTable dataTable) -> {
			createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis());
			domainDataTable = dataTable;
			domainDataTable.asMaps().forEach(caseData -> {
				saleOrgSapCode = Optional.ofNullable(caseData.get("saleOrgSapCode")).orElse("");
				regionCode = Optional.ofNullable(caseData.get("regionCode")).orElse("");
				regionName = Optional.ofNullable(caseData.get("regionName")).orElse("");
				customerSapCode = Optional.ofNullable(caseData.get("customerSapCode")).orElse("");
				shippingAddress = Optional.ofNullable(caseData.get("shippingAddress")).orElse("");
				shippingRecipient = Optional.ofNullable(caseData.get("shippingRecipient")).orElse("");
				shippingTelephone = Optional.ofNullable(caseData.get("shippingTelephone")).orElse("");
			});
		});
		When("[整单折扣分配计划分摊11]当前存在如下的预折商品信息", (DataTable dataTable) -> {
			preDiscountId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			preDiscountDetailId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			beginDate = new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis());
			endDate = new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis() + 24 * 60 * 60 * 1000);
			orderBatchInsertData01.insertOrderPreDidcountData01(OrderPreDiscountDTO.builder()
					.preDiscountId(preDiscountId)
					.customerSapCode(customerSapCode)
					.saleOrgSapCode(saleOrgSapCode)
					.regionCode("")
					.regionName("")
					.effectiveState("ENABLE")
					.preType("CUSTOMER_PRE_DISCOUNT")
					.createPerson("at")
					.createTime(createTime)
					.build());
			dataTable.asMaps().stream().forEach(caseData -> {
				String id = "100000" + Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				BigDecimal currentPrice = new BigDecimal(caseData.get("currentPrice")).setScale(8, RoundingMode.HALF_UP);
				String calculationType = Optional.ofNullable(caseData.get("calculationType")).orElse("");
				BigDecimal calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(8, RoundingMode.HALF_UP);
				String preType = Optional.ofNullable(caseData.get("preType")).orElse("");
				String effectiveState = Optional.ofNullable(caseData.get("effectiveState")).orElse("");
				String distributionChannelCode = Optional.ofNullable(caseData.get("distributionChannelCode")).orElse("");
				String distributionChannelName = Optional.ofNullable(caseData.get("distributionChannelName")).orElse("");
				orderBatchInsertData01.insertOrderPreDidcountDetailData01(OrderPreDiscountDetailDTO.builder()
						.id(id)
						.preDiscountDetailId(preDiscountDetailId)
						.preDiscountId(preDiscountId)
						.itemSapCode(itemSapCode)
						.distributionChannelCode(distributionChannelCode)
						.distributionChannelName(distributionChannelName)
						.currentPrice(currentPrice)
						.calculationType(calculationType)
						.calculationValue(calculationValue)
						.beginDate(beginDate)
						.endDate(endDate)
						.effectiveState(effectiveState)
						.preType("CUSTOMER_PRE_DISCOUNT")
						.createPerson("at")
						.createTime(createTime)
						.build()
				);
			});
		});
		And("[整单折扣分配计划分摊11]当前存在如下的预折折扣信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				BigDecimal calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(8, RoundingMode.HALF_UP);
				String effectiveState = Optional.ofNullable(caseData.get("effectiveState")).orElse("");
				orderBatchUpdateData01.updateOrderPreDiscountDetailCalculationValue(OrderPreDiscountDetailDTO.builder().effectiveState(effectiveState).calculationValue(calculationValue).itemSapCode(itemSapCode).build(), OrderPreDiscountDTO.builder().customerSapCode(customerSapCode).build());
			});
		});
		And("[整单折扣分配计划分摊11]当前存在如下的[纯赠]整单折扣申请单信息", (DataTable dataTable) -> {
			applicationFormId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			processOrderNo = "ZDZK" + new SimpleDateFormat("yyyyMMddHHmm").format(System.currentTimeMillis()) + "_1";
			dataTable.asMaps().stream().forEach(caseData -> {
				String projectType = Optional.ofNullable(caseData.get("projectType")).orElse("");
				String module = Optional.ofNullable(caseData.get("module")).orElse("");
				orderBatchInsertData01.insertOrderApplicationFormData01(OrderApplicationFormDTO.builder()
						.applicationFormId(applicationFormId)
						.processOrderNo(processOrderNo)
						.applicant("at-OTC分公司")
						.applicationDate(createTime)
						.projectType(projectType)
						.module(module)
						.status("APPROVED")
						.executionStartTime(beginDate)
						.executionEndTime(endDate)
						.saleOrgSapCode(saleOrgSapCode)
						.createPerson("at")
						.createTime(createTime)
						.build());
			});
		});
		And("[整单折扣分配计划分摊11]当前存在如下的[纯赠]整单折扣申请单商品信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				applicationFormDetailId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String quantity = Optional.ofNullable(caseData.get("quantity")).orElse("");
				String units = Optional.ofNullable(caseData.get("units")).orElse("");
				orderBatchInsertData01.insertOrderApplicationFormDetailData01(OrderApplicationFormDetailDTO.builder()
						.applicationFormDetailId(applicationFormDetailId)
						.applicationFormId(applicationFormId)
						.itemSapCode(itemSapCode)
						.quantity(quantity)
						.units(units)
						.createPerson("at")
						.createTime(createTime)
						.build());
			});
		});
		And("[整单折扣分配计划分摊11]当前存在如下的[纯赠-AT_FP001]整单折扣分配计划信息", () -> {
			allocationPlanId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			allocationPlanId_001 = allocationPlanId;
			planMonth = new SimpleDateFormat("yyyy-MM").format(System.currentTimeMillis());
			orderBatchInsertData01.insertOrderAllocationPlanData01(OrderAllocationPlanDTO.builder()
					.allocationPlanId(allocationPlanId)
					.orderDiscountApplicationFormId(applicationFormId)
					.status("IN_PROGRESS")
					.lockStatus("UNLOCK")
					.planMonth(planMonth)
					.newExecutionStartTime(beginDate)
					.newExecutionEndTime(endDate)
					.createPerson("at")
					.createTime(createTime)
					.build());
		});
		And("[整单折扣分配计划分摊11]当前存在如下的[纯赠-AT_FP001]整单折扣分配计划详细信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String remark = Optional.ofNullable(caseData.get("remark")).orElse("");
				allocationPlanDetailId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
				setAllocationPlanInfoByRemark(remark, allocationPlanId, allocationPlanDetailId);
				String originalItemSapCode = Optional.ofNullable(caseData.get("originalItemSapCode")).orElse("");
				String originalDistributionChannelCode = Optional.ofNullable(caseData.get("originalDistributionChannelCode")).orElse("");
				String originalDistributionChannelName = Optional.ofNullable(caseData.get("originalDistributionChannelName")).orElse("");
				String giftItemSapCode = Optional.ofNullable(caseData.get("giftItemSapCode")).orElse("");
				String giftDistributionChannelCode = Optional.ofNullable(caseData.get("giftDistributionChannelCode")).orElse("");
				String giftDistributionChannelName = Optional.ofNullable(caseData.get("giftDistributionChannelName")).orElse("");
				String allocationQuantity = Optional.ofNullable(caseData.get("allocationQuantity")).orElse("");
				Integer originalQuantity = Integer.valueOf(Optional.ofNullable(caseData.get("originalQuantity")).orElse("0"));
				Integer giftQuantity = Integer.parseInt(Optional.ofNullable(caseData.get("giftQuantity")).orElse("0"));
				Integer initialBonusQuantity = Integer.parseInt(Optional.ofNullable(caseData.get("initialBonusQuantity")).orElse("0"));
				String needShip = Optional.ofNullable(caseData.get("needShip")).orElse("");
				String unit = Optional.ofNullable(caseData.get("unit")).orElse("");
				String invalidStatus = Optional.ofNullable(caseData.get("invalidStatus")).orElse("");
				String lotNo = Optional.ofNullable(caseData.get("lotNo")).orElse("");
				orderBatchInsertData01.insertOrderApplicationPlanDetailData01(OrderAllocationPlanDetailDTO.builder()
						.allocationPlanDetailId(allocationPlanDetailId)
						.allocationPlanId(allocationPlanId)
						.applicationFormDetailId(applicationFormDetailId)
						.customerEntSapCode(customerSapCode)
						.saleOrgSapCode(saleOrgSapCode)
						.regionCode(regionCode)
						.regionName(regionName)
						.originalItemSapCode(originalItemSapCode)
						.originalDistributionChannelCode(originalDistributionChannelCode)
						.originalDistributionChannelName(originalDistributionChannelName)
						.giftItemSapCode(giftItemSapCode)
						.giftDistributionChannelCode(giftDistributionChannelCode)
						.giftDistributionChannelName(giftDistributionChannelName)
						.allocationQuantity(allocationQuantity)
						.originalQuantity(originalQuantity)
						.giftQuantity(giftQuantity)
						.initialBonusQuantity(initialBonusQuantity)
						.unit(unit)
						.lotNo(lotNo)
						.productionDate(beginDate)
						.expirationDate(endDate)
						.needShip(needShip)
						.invalidStatus(invalidStatus)
						.remark(remark)
						.createPerson("at")
						.createTime(createTime)
						.build());
			});
		});
		And("[整单折扣分配计划分摊11]当前存在如下的[捐赠]整单折扣申请单信息", (DataTable dataTable) -> {
			applicationFormId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			processOrderNo = "ZDZK" + new SimpleDateFormat("yyyyMMddHHmm").format(System.currentTimeMillis()) + "_2";
			dataTable.asMaps().stream().forEach(caseData -> {
				String projectType = Optional.ofNullable(caseData.get("projectType")).orElse("");
				String module = Optional.ofNullable(caseData.get("module")).orElse("");
				orderBatchInsertData01.insertOrderApplicationFormData01(OrderApplicationFormDTO.builder()
						.applicationFormId(applicationFormId)
						.processOrderNo(processOrderNo)
						.applicant("at-OTC分公司")
						.applicationDate(createTime)
						.projectType(projectType)
						.module(module)
						.status("APPROVED")
						.executionStartTime(beginDate)
						.executionEndTime(endDate)
						.saleOrgSapCode(saleOrgSapCode)
						.createPerson("at")
						.createTime(createTime)
						.build());
			});
		});
		And("[整单折扣分配计划分摊11]当前存在如下的[捐赠]整单折扣申请单商品信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				applicationFormDetailId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String quantity = Optional.ofNullable(caseData.get("quantity")).orElse("");
				String units = Optional.ofNullable(caseData.get("units")).orElse("");
				orderBatchInsertData01.insertOrderApplicationFormDetailData01(OrderApplicationFormDetailDTO.builder()
						.applicationFormDetailId(applicationFormDetailId)
						.applicationFormId(applicationFormId)
						.itemSapCode(itemSapCode)
						.quantity(quantity)
						.units(units)
						.createPerson("at")
						.createTime(createTime)
						.build());
			});
		});
		And("[整单折扣分配计划分摊11]当前存在如下的[捐赠-AT_FP002]整单折扣分配计划信息", () -> {
			allocationPlanId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			allocationPlanId_002 = allocationPlanId;
			planMonth = new SimpleDateFormat("yyyy-MM").format(System.currentTimeMillis());
			orderBatchInsertData01.insertOrderAllocationPlanData01(OrderAllocationPlanDTO.builder()
					.allocationPlanId(allocationPlanId)
					.orderDiscountApplicationFormId(applicationFormId)
					.status("IN_PROGRESS")
					.lockStatus("UNLOCK")
					.planMonth(planMonth)
					.newExecutionStartTime(beginDate)
					.newExecutionEndTime(endDate)
					.createPerson("at")
					.createTime(createTime)
					.build());
		});
		And("[整单折扣分配计划分摊11]当前存在如下的[捐赠-AT_FP002]整单折扣分配计划详细信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String remark = Optional.ofNullable(caseData.get("remark")).orElse("");
				allocationPlanDetailId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
				setAllocationPlanInfoByRemark(remark, allocationPlanId, allocationPlanDetailId);
				String originalItemSapCode = Optional.ofNullable(caseData.get("originalItemSapCode")).orElse("");
				String originalDistributionChannelCode = Optional.ofNullable(caseData.get("originalDistributionChannelCode")).orElse("");
				String originalDistributionChannelName = Optional.ofNullable(caseData.get("originalDistributionChannelName")).orElse("");
				String giftItemSapCode = Optional.ofNullable(caseData.get("giftItemSapCode")).orElse("");
				String giftDistributionChannelCode = Optional.ofNullable(caseData.get("giftDistributionChannelCode")).orElse("");
				String giftDistributionChannelName = Optional.ofNullable(caseData.get("giftDistributionChannelName")).orElse("");
				String allocationQuantity = Optional.ofNullable(caseData.get("allocationQuantity")).orElse("");
				Integer originalQuantity = Integer.valueOf(Optional.ofNullable(caseData.get("originalQuantity")).orElse("0"));
				Integer giftQuantity = Integer.parseInt(Optional.ofNullable(caseData.get("giftQuantity")).orElse("0"));
				Integer initialBonusQuantity = Integer.parseInt(Optional.ofNullable(caseData.get("initialBonusQuantity")).orElse("0"));
				String needShip = Optional.ofNullable(caseData.get("needShip")).orElse("");
				String unit = Optional.ofNullable(caseData.get("unit")).orElse("");
				String invalidStatus = Optional.ofNullable(caseData.get("invalidStatus")).orElse("");
				String lotNo = Optional.ofNullable(caseData.get("lotNo")).orElse("");
				orderBatchInsertData01.insertOrderApplicationPlanDetailData01(OrderAllocationPlanDetailDTO.builder()
						.allocationPlanDetailId(allocationPlanDetailId)
						.allocationPlanId(allocationPlanId)
						.applicationFormDetailId(applicationFormDetailId)
						.customerEntSapCode(customerSapCode)
						.saleOrgSapCode(saleOrgSapCode)
						.regionCode(regionCode)
						.regionName(regionName)
						.originalItemSapCode(originalItemSapCode)
						.originalDistributionChannelCode(originalDistributionChannelCode)
						.originalDistributionChannelName(originalDistributionChannelName)
						.giftItemSapCode(giftItemSapCode)
						.giftDistributionChannelCode(giftDistributionChannelCode)
						.giftDistributionChannelName(giftDistributionChannelName)
						.allocationQuantity(allocationQuantity)
						.originalQuantity(originalQuantity)
						.giftQuantity(giftQuantity)
						.initialBonusQuantity(initialBonusQuantity)
						.unit(unit)
						.lotNo(lotNo)
						.productionDate(beginDate)
						.expirationDate(endDate)
						.needShip(needShip)
						.invalidStatus(invalidStatus)
						.remark(remark)
						.createPerson("at")
						.createTime(createTime)
						.build());
			});
		});
		And("[整单折扣分配计划分摊11]添加商品", (DataTable dataTable) -> {
			log.info("添加商品");
		});
		And("[整单折扣分配计划分摊11]选择项目", (DataTable dataTable) -> {
			log.info("选择项目");
		});
		Then("[整单折扣分配计划分摊11]创建订单应校验通过且提交成功", (DataTable dataTable) -> {
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
			List<OrderDiscountCalcCalculateReqGroupListListDTO> orderDiscountCalcCalculateReqGroupListListDTOS = new ArrayList<>();
			dataTable.asMaps().forEach(caseData -> {
				String id = "100000" + Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String orderWideDiscountSubject = Optional.ofNullable(caseData.get("orderWideDiscountSubject")).orElse("");
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String needShip = Optional.ofNullable(caseData.get("needShip")).orElse("");
				String unitPrice = new BigDecimal(caseData.get("unitPrice")).setScale(2, RoundingMode.HALF_UP).toString();
				String quantity = Optional.ofNullable(caseData.get("quantity")).orElse("");
				String boxRatio = new Integer(caseData.get("boxRatio")).toString();
				String preDiscount = new BigDecimal(caseData.get("preDiscount")).setScale(2, RoundingMode.HALF_UP).toString();
				if ("0.00".equals(preDiscount)) {
					id = ""; // 如果是"0"，则将id设置为空字符串
				}
				String calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(2, RoundingMode.HALF_UP).toString();
				String totalInline = new BigDecimal(caseData.get("totalInline")).setScale(2, RoundingMode.HALF_UP).toString();
				String preDiscountTotal = new BigDecimal(caseData.get("preDiscountTotal")).setScale(2, RoundingMode.HALF_UP).toString();
				String orderDiscountTotal = new BigDecimal(caseData.get("orderDiscountTotal")).setScale(2, RoundingMode.HALF_UP).toString();
				String discountProportion = String.format("%.4f", new BigDecimal(caseData.get("preDiscountTotal").toString()).add(new BigDecimal(caseData.get("orderDiscountTotal").toString())).divide(new BigDecimal(caseData.get("totalInline").toString()), 4, RoundingMode.HALF_UP).doubleValue());
				String remark = Optional.ofNullable(caseData.get("remark")).orElse("");
				getAllocationPlanInfoByRemark(remark);
				orderDiscountCalcCalculateReqGroupListListDTOS.add(OrderDiscountCalcCalculateReqGroupListListDTO.builder()
						.orderWideDiscountSubject(orderWideDiscountSubject)
						.discountAllocationPlanId(allocationPlanId)
						.discountAllocationPlanDetailId(allocationPlanDetailId)
						.discountId(id)
						.preDiscountDetailId(id)
						.itemSapCode(itemSapCode)
						.itemName(ProductEnum.fetchProductName(itemSapCode))
						.itemDesc(ProductEnum.fetchProductSpec(itemSapCode))
						.itemGroupCode("01")
						.itemGroupName("成品药-非管制药品")
						.distributionChannelCode("22")
						.distributionChannelName("分销-非医疗")
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
						.discountProportion(discountProportion)
						.build());
			});
			OrderDiscountCalcCalculateReqDTO calculateReq = orderApiOrderManagement01.discountCalcAfterData(OrderDataDTO.builder()
					.orderType("STANDARD")
					.deliveryNo(customerSapCode)
					.saleOrgSapCode(saleOrgSapCode)
					.saleOrgName(SaleOrgEnum.fetchName(saleOrgSapCode))
					.saleOrgZtCode(SaleOrgEnum.fetchZtCode(saleOrgSapCode))
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
					.contractRemark("自动化测试-合同备注")
					.remark("自动化测试-订单备注")
					.calculateReq(OrderDiscountCalcCalculateReqDTO.builder()
							.totalInline(totalInlineSum)
							.preDiscountTotal(preDiscountTotalSum)
							.orderDiscountTotal(orderDiscountTotalSum)
							.groupList(Arrays.asList(OrderDiscountCalcCalculateReqGroupListDTO.builder()
									.itemGroupCode("01")
									.itemGroupName("成品药-非管制药品")
									.distributionChannelCode("22")
									.distributionChannelName("分销-非医疗")
									.totalInline(totalInlineSum)
									.preDiscountTotal(preDiscountTotalSum)
									.orderDiscountTotal(orderDiscountTotalSum)
									.list(orderDiscountCalcCalculateReqGroupListListDTOS)
									.build()))
							.build()
					)
					.build());
			Response createOrderVerifyResponse = orderApiOrderManagement01.createOrderVerify(OrderDataDTO.builder()
					.orderType("STANDARD")
					.deliveryNo(customerSapCode)
					.saleOrgSapCode(saleOrgSapCode)
					.saleOrgName(SaleOrgEnum.fetchName(saleOrgSapCode))
					.saleOrgZtCode(SaleOrgEnum.fetchZtCode(saleOrgSapCode))
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
					.contractRemark("自动化测试-合同备注")
					.remark("自动化测试-订单备注")
					.calculateReq(calculateReq)
					.build());
			createOrderVerifyResponse.then().body("resultCode", Matchers.equalTo("0"), "resultMsg", Matchers.equalTo("success"));
			Response createOrderSubmitResponse = orderApiOrderManagement01.createOrderSubmit(OrderDataDTO.builder()
					.orderType("STANDARD")
					.deliveryNo(customerSapCode)
					.saleOrgSapCode(saleOrgSapCode)
					.saleOrgName(SaleOrgEnum.fetchName(saleOrgSapCode))
					.saleOrgZtCode(SaleOrgEnum.fetchZtCode(saleOrgSapCode))
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
					.contractRemark("自动化测试-合同备注")
					.remark("自动化测试-订单备注")
					.calculateReq(calculateReq)
					.build());
			createOrderSubmitResponse.then().body("resultCode", Matchers.equalTo("0"), "resultMsg", Matchers.equalTo("success"));
		});
		Then("[整单折扣分配计划分摊11]订单整单折扣使用记录成功", (DataTable dataTable) -> {
			List<Map<String, String>> caseDataMap = new ArrayList<>();
			String orderNo = orderApiOrderManagement01.selectOrderInfo(OrderQueryPageByConditionDTO.builder()
					.state("PENDING_BUSINESS_REVIEW")
					.cancelStatus("NOT_CANCEL")
					.saleOrgSapCode(saleOrgSapCode)
					.customerCodeOrName(customerSapCode)
					.build());
			String parentsOrderNo = StringUtils.left(orderNo, 22);
			dataTable.asMaps().forEach(caseData -> {
				Map<String, String> processedData = new HashMap<>();
				processedData.put("parentOrderNo", parentsOrderNo);
				processedData.put("parentOrderItemNo", Optional.ofNullable(caseData.get("parentOrderItemNo")).orElse(""));
				processedData.put("orderNo", orderNo);
				processedData.put("orderItemNo", Optional.ofNullable(caseData.get("orderItemNo")).orElse(""));
				String remark = Optional.ofNullable(caseData.get("remark")).orElse("");
				getAllocationPlanInfoByRemark(remark);
				processedData.put("orderDiscountApplicationPlanId", allocationPlanId);
				processedData.put("orderDiscountApplicationPlanDetailId", allocationPlanDetailId);
				processedData.put("operateType", Optional.ofNullable(caseData.get("operateType")).orElse(""));
				processedData.put("operateAmount", Optional.ofNullable(caseData.get("operateAmount")).orElse(""));
				caseDataMap.add(processedData);
			});
			List<Map<String, Object>> sqlResult = orderDiscountDetailBatchSelectData01.queryOrderDiscountDetailData01(parentsOrderNo);
			discountCheckAllocationPlan01.checkDiscountAllocationPlan(caseDataMap, sqlResult);
			orderBatchClearData01.clearOrderData01(orderNo);
			priceBatchClearData01.clearPirceData01("at");
			orderBatchClearData01.clearOrderPreDiscountData01("at");
			orderBatchClearData01.clearDiscountData01("at");
		});
	}


	private void scenario12() {
		Given("[整单折扣分配计划分摊12]当前存在如下客户信息", (DataTable dataTable) -> {
			createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis());
			domainDataTable = dataTable;
			domainDataTable.asMaps().forEach(caseData -> {
				saleOrgSapCode = Optional.ofNullable(caseData.get("saleOrgSapCode")).orElse("");
				regionCode = Optional.ofNullable(caseData.get("regionCode")).orElse("");
				regionName = Optional.ofNullable(caseData.get("regionName")).orElse("");
				customerSapCode = Optional.ofNullable(caseData.get("customerSapCode")).orElse("");
				shippingAddress = Optional.ofNullable(caseData.get("shippingAddress")).orElse("");
				shippingRecipient = Optional.ofNullable(caseData.get("shippingRecipient")).orElse("");
				shippingTelephone = Optional.ofNullable(caseData.get("shippingTelephone")).orElse("");
			});
		});
		When("[整单折扣分配计划分摊12]当前存在如下的预折商品信息", (DataTable dataTable) -> {
			preDiscountId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			preDiscountDetailId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			beginDate = new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis());
			endDate = new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis() + 24 * 60 * 60 * 1000);
			orderBatchInsertData01.insertOrderPreDidcountData01(OrderPreDiscountDTO.builder()
					.preDiscountId(preDiscountId)
					.customerSapCode(customerSapCode)
					.saleOrgSapCode(saleOrgSapCode)
					.regionCode("")
					.regionName("")
					.effectiveState("ENABLE")
					.preType("CUSTOMER_PRE_DISCOUNT")
					.createPerson("at")
					.createTime(createTime)
					.build());
			dataTable.asMaps().stream().forEach(caseData -> {
				String id = "100000" + Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				BigDecimal currentPrice = new BigDecimal(caseData.get("currentPrice")).setScale(8, RoundingMode.HALF_UP);
				String calculationType = Optional.ofNullable(caseData.get("calculationType")).orElse("");
				BigDecimal calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(8, RoundingMode.HALF_UP);
				String preType = Optional.ofNullable(caseData.get("preType")).orElse("");
				String effectiveState = Optional.ofNullable(caseData.get("effectiveState")).orElse("");
				String distributionChannelCode = Optional.ofNullable(caseData.get("distributionChannelCode")).orElse("");
				String distributionChannelName = Optional.ofNullable(caseData.get("distributionChannelName")).orElse("");
				orderBatchInsertData01.insertOrderPreDidcountDetailData01(OrderPreDiscountDetailDTO.builder()
						.id(id)
						.preDiscountDetailId(preDiscountDetailId)
						.preDiscountId(preDiscountId)
						.itemSapCode(itemSapCode)
						.distributionChannelCode(distributionChannelCode)
						.distributionChannelName(distributionChannelName)
						.currentPrice(currentPrice)
						.calculationType(calculationType)
						.calculationValue(calculationValue)
						.beginDate(beginDate)
						.endDate(endDate)
						.effectiveState(effectiveState)
						.preType("CUSTOMER_PRE_DISCOUNT")
						.createPerson("at")
						.createTime(createTime)
						.build()
				);
			});
		});
		And("[整单折扣分配计划分摊12]当前存在如下的预折折扣信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				BigDecimal calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(8, RoundingMode.HALF_UP);
				String effectiveState = Optional.ofNullable(caseData.get("effectiveState")).orElse("");
				orderBatchUpdateData01.updateOrderPreDiscountDetailCalculationValue(OrderPreDiscountDetailDTO.builder().effectiveState(effectiveState).calculationValue(calculationValue).itemSapCode(itemSapCode).build(), OrderPreDiscountDTO.builder().customerSapCode(customerSapCode).build());
			});
		});
		And("[整单折扣分配计划分摊12]当前存在如下的[纯赠]整单折扣申请单信息", (DataTable dataTable) -> {
			applicationFormId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			processOrderNo = "ZDZK" + new SimpleDateFormat("yyyyMMddHHmm").format(System.currentTimeMillis()) + "_1";
			dataTable.asMaps().stream().forEach(caseData -> {
				String projectType = Optional.ofNullable(caseData.get("projectType")).orElse("");
				String module = Optional.ofNullable(caseData.get("module")).orElse("");
				orderBatchInsertData01.insertOrderApplicationFormData01(OrderApplicationFormDTO.builder()
						.applicationFormId(applicationFormId)
						.processOrderNo(processOrderNo)
						.applicant("at-OTC分公司")
						.applicationDate(createTime)
						.projectType(projectType)
						.module(module)
						.status("APPROVED")
						.executionStartTime(beginDate)
						.executionEndTime(endDate)
						.saleOrgSapCode(saleOrgSapCode)
						.createPerson("at")
						.createTime(createTime)
						.build());
			});
		});
		And("[整单折扣分配计划分摊12]当前存在如下的[纯赠]整单折扣申请单商品信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				applicationFormDetailId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String quantity = Optional.ofNullable(caseData.get("quantity")).orElse("");
				String units = Optional.ofNullable(caseData.get("units")).orElse("");
				orderBatchInsertData01.insertOrderApplicationFormDetailData01(OrderApplicationFormDetailDTO.builder()
						.applicationFormDetailId(applicationFormDetailId)
						.applicationFormId(applicationFormId)
						.itemSapCode(itemSapCode)
						.quantity(quantity)
						.units(units)
						.createPerson("at")
						.createTime(createTime)
						.build());
			});
		});
		And("[整单折扣分配计划分摊12]当前存在如下的[纯赠-AT_FP001]整单折扣分配计划信息", () -> {
			allocationPlanId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			allocationPlanId_001 = allocationPlanId;
			planMonth = new SimpleDateFormat("yyyy-MM").format(System.currentTimeMillis());
			orderBatchInsertData01.insertOrderAllocationPlanData01(OrderAllocationPlanDTO.builder()
					.allocationPlanId(allocationPlanId)
					.orderDiscountApplicationFormId(applicationFormId)
					.status("IN_PROGRESS")
					.lockStatus("UNLOCK")
					.planMonth(planMonth)
					.newExecutionStartTime(beginDate)
					.newExecutionEndTime(endDate)
					.createPerson("at")
					.createTime(createTime)
					.build());
		});
		And("[整单折扣分配计划分摊12]当前存在如下的[纯赠-AT_FP001]整单折扣分配计划详细信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String remark = Optional.ofNullable(caseData.get("remark")).orElse("");
				allocationPlanDetailId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
				setAllocationPlanInfoByRemark(remark, allocationPlanId, allocationPlanDetailId);
				String originalItemSapCode = Optional.ofNullable(caseData.get("originalItemSapCode")).orElse("");
				String originalDistributionChannelCode = Optional.ofNullable(caseData.get("originalDistributionChannelCode")).orElse("");
				String originalDistributionChannelName = Optional.ofNullable(caseData.get("originalDistributionChannelName")).orElse("");
				String giftItemSapCode = Optional.ofNullable(caseData.get("giftItemSapCode")).orElse("");
				String giftDistributionChannelCode = Optional.ofNullable(caseData.get("giftDistributionChannelCode")).orElse("");
				String giftDistributionChannelName = Optional.ofNullable(caseData.get("giftDistributionChannelName")).orElse("");
				String allocationQuantity = Optional.ofNullable(caseData.get("allocationQuantity")).orElse("");
				Integer originalQuantity = Integer.valueOf(Optional.ofNullable(caseData.get("originalQuantity")).orElse("0"));
				Integer giftQuantity = Integer.parseInt(Optional.ofNullable(caseData.get("giftQuantity")).orElse("0"));
				Integer initialBonusQuantity = Integer.parseInt(Optional.ofNullable(caseData.get("initialBonusQuantity")).orElse("0"));
				String needShip = Optional.ofNullable(caseData.get("needShip")).orElse("");
				String unit = Optional.ofNullable(caseData.get("unit")).orElse("");
				String invalidStatus = Optional.ofNullable(caseData.get("invalidStatus")).orElse("");
				String lotNo = Optional.ofNullable(caseData.get("lotNo")).orElse("");
				orderBatchInsertData01.insertOrderApplicationPlanDetailData01(OrderAllocationPlanDetailDTO.builder()
						.allocationPlanDetailId(allocationPlanDetailId)
						.allocationPlanId(allocationPlanId)
						.applicationFormDetailId(applicationFormDetailId)
						.customerEntSapCode(customerSapCode)
						.saleOrgSapCode(saleOrgSapCode)
						.regionCode(regionCode)
						.regionName(regionName)
						.originalItemSapCode(originalItemSapCode)
						.originalDistributionChannelCode(originalDistributionChannelCode)
						.originalDistributionChannelName(originalDistributionChannelName)
						.giftItemSapCode(giftItemSapCode)
						.giftDistributionChannelCode(giftDistributionChannelCode)
						.giftDistributionChannelName(giftDistributionChannelName)
						.allocationQuantity(allocationQuantity)
						.originalQuantity(originalQuantity)
						.giftQuantity(giftQuantity)
						.initialBonusQuantity(initialBonusQuantity)
						.unit(unit)
						.lotNo(lotNo)
						.productionDate(beginDate)
						.expirationDate(endDate)
						.needShip(needShip)
						.invalidStatus(invalidStatus)
						.remark(remark)
						.createPerson("at")
						.createTime(createTime)
						.build());
			});
		});
		And("[整单折扣分配计划分摊12]当前存在如下的[捐赠]整单折扣申请单信息", (DataTable dataTable) -> {
			applicationFormId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			processOrderNo = "ZDZK" + new SimpleDateFormat("yyyyMMddHHmm").format(System.currentTimeMillis()) + "_2";
			dataTable.asMaps().stream().forEach(caseData -> {
				String projectType = Optional.ofNullable(caseData.get("projectType")).orElse("");
				String module = Optional.ofNullable(caseData.get("module")).orElse("");
				orderBatchInsertData01.insertOrderApplicationFormData01(OrderApplicationFormDTO.builder()
						.applicationFormId(applicationFormId)
						.processOrderNo(processOrderNo)
						.applicant("at-OTC分公司")
						.applicationDate(createTime)
						.projectType(projectType)
						.module(module)
						.status("APPROVED")
						.executionStartTime(beginDate)
						.executionEndTime(endDate)
						.saleOrgSapCode(saleOrgSapCode)
						.createPerson("at")
						.createTime(createTime)
						.build());
			});
		});
		And("[整单折扣分配计划分摊12]当前存在如下的[捐赠]整单折扣申请单商品信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				applicationFormDetailId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String quantity = Optional.ofNullable(caseData.get("quantity")).orElse("");
				String units = Optional.ofNullable(caseData.get("units")).orElse("");
				orderBatchInsertData01.insertOrderApplicationFormDetailData01(OrderApplicationFormDetailDTO.builder()
						.applicationFormDetailId(applicationFormDetailId)
						.applicationFormId(applicationFormId)
						.itemSapCode(itemSapCode)
						.quantity(quantity)
						.units(units)
						.createPerson("at")
						.createTime(createTime)
						.build());
			});
		});
		And("[整单折扣分配计划分摊12]当前存在如下的[捐赠-AT_FP002]整单折扣分配计划信息", () -> {
			allocationPlanId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			allocationPlanId_002 = allocationPlanId;
			planMonth = new SimpleDateFormat("yyyy-MM").format(System.currentTimeMillis());
			orderBatchInsertData01.insertOrderAllocationPlanData01(OrderAllocationPlanDTO.builder()
					.allocationPlanId(allocationPlanId)
					.orderDiscountApplicationFormId(applicationFormId)
					.status("IN_PROGRESS")
					.lockStatus("UNLOCK")
					.planMonth(planMonth)
					.newExecutionStartTime(beginDate)
					.newExecutionEndTime(endDate)
					.createPerson("at")
					.createTime(createTime)
					.build());
		});
		And("[整单折扣分配计划分摊12]当前存在如下的[捐赠-AT_FP002]整单折扣分配计划详细信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String remark = Optional.ofNullable(caseData.get("remark")).orElse("");
				allocationPlanDetailId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
				setAllocationPlanInfoByRemark(remark, allocationPlanId, allocationPlanDetailId);
				String originalItemSapCode = Optional.ofNullable(caseData.get("originalItemSapCode")).orElse("");
				String originalDistributionChannelCode = Optional.ofNullable(caseData.get("originalDistributionChannelCode")).orElse("");
				String originalDistributionChannelName = Optional.ofNullable(caseData.get("originalDistributionChannelName")).orElse("");
				String giftItemSapCode = Optional.ofNullable(caseData.get("giftItemSapCode")).orElse("");
				String giftDistributionChannelCode = Optional.ofNullable(caseData.get("giftDistributionChannelCode")).orElse("");
				String giftDistributionChannelName = Optional.ofNullable(caseData.get("giftDistributionChannelName")).orElse("");
				String allocationQuantity = Optional.ofNullable(caseData.get("allocationQuantity")).orElse("");
				Integer originalQuantity = Integer.valueOf(Optional.ofNullable(caseData.get("originalQuantity")).orElse("0"));
				Integer giftQuantity = Integer.parseInt(Optional.ofNullable(caseData.get("giftQuantity")).orElse("0"));
				Integer initialBonusQuantity = Integer.parseInt(Optional.ofNullable(caseData.get("initialBonusQuantity")).orElse("0"));
				String needShip = Optional.ofNullable(caseData.get("needShip")).orElse("");
				String unit = Optional.ofNullable(caseData.get("unit")).orElse("");
				String invalidStatus = Optional.ofNullable(caseData.get("invalidStatus")).orElse("");
				String lotNo = Optional.ofNullable(caseData.get("lotNo")).orElse("");
				orderBatchInsertData01.insertOrderApplicationPlanDetailData01(OrderAllocationPlanDetailDTO.builder()
						.allocationPlanDetailId(allocationPlanDetailId)
						.allocationPlanId(allocationPlanId)
						.applicationFormDetailId(applicationFormDetailId)
						.customerEntSapCode(customerSapCode)
						.saleOrgSapCode(saleOrgSapCode)
						.regionCode(regionCode)
						.regionName(regionName)
						.originalItemSapCode(originalItemSapCode)
						.originalDistributionChannelCode(originalDistributionChannelCode)
						.originalDistributionChannelName(originalDistributionChannelName)
						.giftItemSapCode(giftItemSapCode)
						.giftDistributionChannelCode(giftDistributionChannelCode)
						.giftDistributionChannelName(giftDistributionChannelName)
						.allocationQuantity(allocationQuantity)
						.originalQuantity(originalQuantity)
						.giftQuantity(giftQuantity)
						.initialBonusQuantity(initialBonusQuantity)
						.unit(unit)
						.lotNo(lotNo)
						.productionDate(beginDate)
						.expirationDate(endDate)
						.needShip(needShip)
						.invalidStatus(invalidStatus)
						.remark(remark)
						.createPerson("at")
						.createTime(createTime)
						.build());
			});
		});
		And("[整单折扣分配计划分摊12]添加商品", (DataTable dataTable) -> {
			log.info("添加商品");
		});
		And("[整单折扣分配计划分摊12]选择项目", (DataTable dataTable) -> {
			log.info("选择项目");
		});
		Then("[整单折扣分配计划分摊12]创建订单应校验通过且提交成功", (DataTable dataTable) -> {
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
			List<OrderDiscountCalcCalculateReqGroupListListDTO> orderDiscountCalcCalculateReqGroupListListDTOS = new ArrayList<>();
			dataTable.asMaps().forEach(caseData -> {
				String id = "100000" + Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String orderWideDiscountSubject = Optional.ofNullable(caseData.get("orderWideDiscountSubject")).orElse("");
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String needShip = Optional.ofNullable(caseData.get("needShip")).orElse("");
				String unitPrice = new BigDecimal(caseData.get("unitPrice")).setScale(2, RoundingMode.HALF_UP).toString();
				String quantity = Optional.ofNullable(caseData.get("quantity")).orElse("");
				String boxRatio = new Integer(caseData.get("boxRatio")).toString();
				String preDiscount = new BigDecimal(caseData.get("preDiscount")).setScale(2, RoundingMode.HALF_UP).toString();
				if ("0.00".equals(preDiscount)) {
					id = ""; // 如果是"0"，则将id设置为空字符串
				}
				String calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(2, RoundingMode.HALF_UP).toString();
				String totalInline = new BigDecimal(caseData.get("totalInline")).setScale(2, RoundingMode.HALF_UP).toString();
				String preDiscountTotal = new BigDecimal(caseData.get("preDiscountTotal")).setScale(2, RoundingMode.HALF_UP).toString();
				String orderDiscountTotal = new BigDecimal(caseData.get("orderDiscountTotal")).setScale(2, RoundingMode.HALF_UP).toString();
				String discountProportion = String.format("%.4f", new BigDecimal(caseData.get("preDiscountTotal").toString()).add(new BigDecimal(caseData.get("orderDiscountTotal").toString())).divide(new BigDecimal(caseData.get("totalInline").toString()), 4, RoundingMode.HALF_UP).doubleValue());
				String remark = Optional.ofNullable(caseData.get("remark")).orElse("");
				getAllocationPlanInfoByRemark(remark);
				orderDiscountCalcCalculateReqGroupListListDTOS.add(OrderDiscountCalcCalculateReqGroupListListDTO.builder()
						.orderWideDiscountSubject(orderWideDiscountSubject)
						.discountAllocationPlanId(allocationPlanId)
						.discountAllocationPlanDetailId(allocationPlanDetailId)
						.discountId(id)
						.preDiscountDetailId(id)
						.itemSapCode(itemSapCode)
						.itemName(ProductEnum.fetchProductName(itemSapCode))
						.itemDesc(ProductEnum.fetchProductSpec(itemSapCode))
						.itemGroupCode("01")
						.itemGroupName("成品药-非管制药品")
						.distributionChannelCode("22")
						.distributionChannelName("分销-非医疗")
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
						.discountProportion(discountProportion)
						.build());
			});
			OrderDiscountCalcCalculateReqDTO calculateReq = orderApiOrderManagement01.discountCalcAfterData(OrderDataDTO.builder()
					.orderType("STANDARD")
					.deliveryNo(customerSapCode)
					.saleOrgSapCode(saleOrgSapCode)
					.saleOrgName(SaleOrgEnum.fetchName(saleOrgSapCode))
					.saleOrgZtCode(SaleOrgEnum.fetchZtCode(saleOrgSapCode))
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
					.contractRemark("自动化测试-合同备注")
					.remark("自动化测试-订单备注")
					.calculateReq(OrderDiscountCalcCalculateReqDTO.builder()
							.totalInline(totalInlineSum)
							.preDiscountTotal(preDiscountTotalSum)
							.orderDiscountTotal(orderDiscountTotalSum)
							.groupList(Arrays.asList(OrderDiscountCalcCalculateReqGroupListDTO.builder()
									.itemGroupCode("01")
									.itemGroupName("成品药-非管制药品")
									.distributionChannelCode("22")
									.distributionChannelName("分销-非医疗")
									.totalInline(totalInlineSum)
									.preDiscountTotal(preDiscountTotalSum)
									.orderDiscountTotal(orderDiscountTotalSum)
									.list(orderDiscountCalcCalculateReqGroupListListDTOS)
									.build()))
							.build()
					)
					.build());
			Response createOrderVerifyResponse = orderApiOrderManagement01.createOrderVerify(OrderDataDTO.builder()
					.orderType("STANDARD")
					.deliveryNo(customerSapCode)
					.saleOrgSapCode(saleOrgSapCode)
					.saleOrgName(SaleOrgEnum.fetchName(saleOrgSapCode))
					.saleOrgZtCode(SaleOrgEnum.fetchZtCode(saleOrgSapCode))
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
					.contractRemark("自动化测试-合同备注")
					.remark("自动化测试-订单备注")
					.calculateReq(calculateReq)
					.build());
			createOrderVerifyResponse.then().body("resultCode", Matchers.equalTo("0"), "resultMsg", Matchers.equalTo("success"));
			Response createOrderSubmitResponse = orderApiOrderManagement01.createOrderSubmit(OrderDataDTO.builder()
					.orderType("STANDARD")
					.deliveryNo(customerSapCode)
					.saleOrgSapCode(saleOrgSapCode)
					.saleOrgName(SaleOrgEnum.fetchName(saleOrgSapCode))
					.saleOrgZtCode(SaleOrgEnum.fetchZtCode(saleOrgSapCode))
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
					.contractRemark("自动化测试-合同备注")
					.remark("自动化测试-订单备注")
					.calculateReq(calculateReq)
					.build());
			createOrderSubmitResponse.then().body("resultCode", Matchers.equalTo("0"), "resultMsg", Matchers.equalTo("success"));
		});
		Then("[整单折扣分配计划分摊12]订单整单折扣使用记录成功", (DataTable dataTable) -> {
			List<Map<String, String>> caseDataMap = new ArrayList<>();
			String orderNo = orderApiOrderManagement01.selectOrderInfo(OrderQueryPageByConditionDTO.builder()
					.state("PENDING_BUSINESS_REVIEW")
					.cancelStatus("NOT_CANCEL")
					.saleOrgSapCode(saleOrgSapCode)
					.customerCodeOrName(customerSapCode)
					.build());
			String parentsOrderNo = StringUtils.left(orderNo, 22);
			dataTable.asMaps().forEach(caseData -> {
				Map<String, String> processedData = new HashMap<>();
				processedData.put("parentOrderNo", parentsOrderNo);
				processedData.put("parentOrderItemNo", Optional.ofNullable(caseData.get("parentOrderItemNo")).orElse(""));
				processedData.put("orderNo", orderNo);
				processedData.put("orderItemNo", Optional.ofNullable(caseData.get("orderItemNo")).orElse(""));
				String remark = Optional.ofNullable(caseData.get("remark")).orElse("");
				getAllocationPlanInfoByRemark(remark);
				processedData.put("orderDiscountApplicationPlanId", allocationPlanId);
				processedData.put("orderDiscountApplicationPlanDetailId", allocationPlanDetailId);
				processedData.put("operateType", Optional.ofNullable(caseData.get("operateType")).orElse(""));
				processedData.put("operateAmount", Optional.ofNullable(caseData.get("operateAmount")).orElse(""));
				caseDataMap.add(processedData);
			});
			List<Map<String, Object>> sqlResult = orderDiscountDetailBatchSelectData01.queryOrderDiscountDetailData01(parentsOrderNo);
			discountCheckAllocationPlan01.checkDiscountAllocationPlan(caseDataMap, sqlResult);
			orderBatchClearData01.clearOrderData01(orderNo);
			priceBatchClearData01.clearPirceData01("at");
			orderBatchClearData01.clearOrderPreDiscountData01("at");
			orderBatchClearData01.clearDiscountData01("at");
		});
	}


	private void scenario13() {
		Given("[整单折扣分配计划分摊13]当前存在如下客户信息", (DataTable dataTable) -> {
			createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis());
			domainDataTable = dataTable;
			domainDataTable.asMaps().forEach(caseData -> {
				saleOrgSapCode = Optional.ofNullable(caseData.get("saleOrgSapCode")).orElse("");
				regionCode = Optional.ofNullable(caseData.get("regionCode")).orElse("");
				regionName = Optional.ofNullable(caseData.get("regionName")).orElse("");
				customerSapCode = Optional.ofNullable(caseData.get("customerSapCode")).orElse("");
				shippingAddress = Optional.ofNullable(caseData.get("shippingAddress")).orElse("");
				shippingRecipient = Optional.ofNullable(caseData.get("shippingRecipient")).orElse("");
				shippingTelephone = Optional.ofNullable(caseData.get("shippingTelephone")).orElse("");
			});
		});
		When("[整单折扣分配计划分摊13]当前存在如下的预折商品信息", (DataTable dataTable) -> {
			preDiscountId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			preDiscountDetailId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			beginDate = new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis());
			endDate = new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis() + 24 * 60 * 60 * 1000);
			orderBatchInsertData01.insertOrderPreDidcountData01(OrderPreDiscountDTO.builder()
					.preDiscountId(preDiscountId)
					.customerSapCode(customerSapCode)
					.saleOrgSapCode(saleOrgSapCode)
					.regionCode("")
					.regionName("")
					.effectiveState("ENABLE")
					.preType("CUSTOMER_PRE_DISCOUNT")
					.createPerson("at")
					.createTime(createTime)
					.build());
			dataTable.asMaps().stream().forEach(caseData -> {
				String id = "100000" + Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				BigDecimal currentPrice = new BigDecimal(caseData.get("currentPrice")).setScale(8, RoundingMode.HALF_UP);
				String calculationType = Optional.ofNullable(caseData.get("calculationType")).orElse("");
				BigDecimal calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(8, RoundingMode.HALF_UP);
				String preType = Optional.ofNullable(caseData.get("preType")).orElse("");
				String effectiveState = Optional.ofNullable(caseData.get("effectiveState")).orElse("");
				String distributionChannelCode = Optional.ofNullable(caseData.get("distributionChannelCode")).orElse("");
				String distributionChannelName = Optional.ofNullable(caseData.get("distributionChannelName")).orElse("");
				orderBatchInsertData01.insertOrderPreDidcountDetailData01(OrderPreDiscountDetailDTO.builder()
						.id(id)
						.preDiscountDetailId(preDiscountDetailId)
						.preDiscountId(preDiscountId)
						.itemSapCode(itemSapCode)
						.distributionChannelCode(distributionChannelCode)
						.distributionChannelName(distributionChannelName)
						.currentPrice(currentPrice)
						.calculationType(calculationType)
						.calculationValue(calculationValue)
						.beginDate(beginDate)
						.endDate(endDate)
						.effectiveState(effectiveState)
						.preType("CUSTOMER_PRE_DISCOUNT")
						.createPerson("at")
						.createTime(createTime)
						.build()
				);
			});
		});
		And("[整单折扣分配计划分摊13]当前存在如下的预折折扣信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				BigDecimal calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(8, RoundingMode.HALF_UP);
				String effectiveState = Optional.ofNullable(caseData.get("effectiveState")).orElse("");
				orderBatchUpdateData01.updateOrderPreDiscountDetailCalculationValue(OrderPreDiscountDetailDTO.builder().effectiveState(effectiveState).calculationValue(calculationValue).itemSapCode(itemSapCode).build(), OrderPreDiscountDTO.builder().customerSapCode(customerSapCode).build());
			});
		});
		And("[整单折扣分配计划分摊13]当前存在如下的[买赠-AT_FP001]整单折扣申请单信息", (DataTable dataTable) -> {
			applicationFormId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			processOrderNo = "ZDZK" + new SimpleDateFormat("yyyyMMddHHmm").format(System.currentTimeMillis()) + "_1";
			dataTable.asMaps().stream().forEach(caseData -> {
				String projectType = Optional.ofNullable(caseData.get("projectType")).orElse("");
				String module = Optional.ofNullable(caseData.get("module")).orElse("");
				orderBatchInsertData01.insertOrderApplicationFormData01(OrderApplicationFormDTO.builder()
						.applicationFormId(applicationFormId)
						.processOrderNo(processOrderNo)
						.applicant("at-OTC分公司")
						.applicationDate(createTime)
						.projectType(projectType)
						.module(module)
						.status("APPROVED")
						.executionStartTime(beginDate)
						.executionEndTime(endDate)
						.saleOrgSapCode(saleOrgSapCode)
						.createPerson("at")
						.createTime(createTime)
						.build());
			});
		});
		And("[整单折扣分配计划分摊13]当前存在如下的[买赠-AT_FP001]整单折扣申请单商品信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				applicationFormDetailId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String quantity = Optional.ofNullable(caseData.get("quantity")).orElse("");
				String units = Optional.ofNullable(caseData.get("units")).orElse("");
				orderBatchInsertData01.insertOrderApplicationFormDetailData01(OrderApplicationFormDetailDTO.builder()
						.applicationFormDetailId(applicationFormDetailId)
						.applicationFormId(applicationFormId)
						.itemSapCode(itemSapCode)
						.quantity(quantity)
						.units(units)
						.createPerson("at")
						.createTime(createTime)
						.build());
			});
		});
		And("[整单折扣分配计划分摊13]当前存在如下的[买赠-AT_FP001]整单折扣分配计划信息", () -> {
			allocationPlanId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			allocationPlanId_001 = allocationPlanId;
			planMonth = new SimpleDateFormat("yyyy-MM").format(System.currentTimeMillis());
			orderBatchInsertData01.insertOrderAllocationPlanData01(OrderAllocationPlanDTO.builder()
					.allocationPlanId(allocationPlanId)
					.orderDiscountApplicationFormId(applicationFormId)
					.status("IN_PROGRESS")
					.lockStatus("UNLOCK")
					.planMonth(planMonth)
					.newExecutionStartTime(beginDate)
					.newExecutionEndTime(endDate)
					.createPerson("at")
					.createTime(createTime)
					.build());
		});
		And("[整单折扣分配计划分摊13]当前存在如下的[买赠-AT_FP001]整单折扣分配计划详细信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String remark = Optional.ofNullable(caseData.get("remark")).orElse("");
				allocationPlanDetailId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
				setAllocationPlanInfoByRemark(remark, allocationPlanId, allocationPlanDetailId);
				String originalItemSapCode = Optional.ofNullable(caseData.get("originalItemSapCode")).orElse("");
				String originalDistributionChannelCode = Optional.ofNullable(caseData.get("originalDistributionChannelCode")).orElse("");
				String originalDistributionChannelName = Optional.ofNullable(caseData.get("originalDistributionChannelName")).orElse("");
				String giftItemSapCode = Optional.ofNullable(caseData.get("giftItemSapCode")).orElse("");
				String giftDistributionChannelCode = Optional.ofNullable(caseData.get("giftDistributionChannelCode")).orElse("");
				String giftDistributionChannelName = Optional.ofNullable(caseData.get("giftDistributionChannelName")).orElse("");
				String allocationQuantity = Optional.ofNullable(caseData.get("allocationQuantity")).orElse("");
				Integer originalQuantity = Integer.valueOf(Optional.ofNullable(caseData.get("originalQuantity")).orElse("0"));
				Integer giftQuantity = Integer.parseInt(Optional.ofNullable(caseData.get("giftQuantity")).orElse("0"));
				Integer initialBonusQuantity = Integer.parseInt(Optional.ofNullable(caseData.get("initialBonusQuantity")).orElse("0"));
				String needShip = Optional.ofNullable(caseData.get("needShip")).orElse("");
				String unit = Optional.ofNullable(caseData.get("unit")).orElse("");
				String invalidStatus = Optional.ofNullable(caseData.get("invalidStatus")).orElse("");
				String lotNo = Optional.ofNullable(caseData.get("lotNo")).orElse("");
				orderBatchInsertData01.insertOrderApplicationPlanDetailData01(OrderAllocationPlanDetailDTO.builder()
						.allocationPlanDetailId(allocationPlanDetailId)
						.allocationPlanId(allocationPlanId)
						.applicationFormDetailId(applicationFormDetailId)
						.customerEntSapCode(customerSapCode)
						.saleOrgSapCode(saleOrgSapCode)
						.regionCode(regionCode)
						.regionName(regionName)
						.originalItemSapCode(originalItemSapCode)
						.originalDistributionChannelCode(originalDistributionChannelCode)
						.originalDistributionChannelName(originalDistributionChannelName)
						.giftItemSapCode(giftItemSapCode)
						.giftDistributionChannelCode(giftDistributionChannelCode)
						.giftDistributionChannelName(giftDistributionChannelName)
						.allocationQuantity(allocationQuantity)
						.originalQuantity(originalQuantity)
						.giftQuantity(giftQuantity)
						.initialBonusQuantity(initialBonusQuantity)
						.unit(unit)
						.lotNo(lotNo)
						.productionDate(beginDate)
						.expirationDate(endDate)
						.needShip(needShip)
						.invalidStatus(invalidStatus)
						.remark(remark)
						.createPerson("at")
						.createTime(createTime)
						.build());
			});
		});
		And("[整单折扣分配计划分摊13]当前存在如下的[纯赠-AT_FP002]整单折扣申请单信息", (DataTable dataTable) -> {
			applicationFormId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			processOrderNo = "ZDZK" + new SimpleDateFormat("yyyyMMddHHmm").format(System.currentTimeMillis()) + "_2";
			dataTable.asMaps().stream().forEach(caseData -> {
				String projectType = Optional.ofNullable(caseData.get("projectType")).orElse("");
				String module = Optional.ofNullable(caseData.get("module")).orElse("");
				orderBatchInsertData01.insertOrderApplicationFormData01(OrderApplicationFormDTO.builder()
						.applicationFormId(applicationFormId)
						.processOrderNo(processOrderNo)
						.applicant("at-OTC分公司")
						.applicationDate(createTime)
						.projectType(projectType)
						.module(module)
						.status("APPROVED")
						.executionStartTime(beginDate)
						.executionEndTime(endDate)
						.saleOrgSapCode(saleOrgSapCode)
						.createPerson("at")
						.createTime(createTime)
						.build());
			});
		});
		And("[整单折扣分配计划分摊13]当前存在如下的[纯赠-AT_FP002]整单折扣申请单商品信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				applicationFormDetailId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String quantity = Optional.ofNullable(caseData.get("quantity")).orElse("");
				String units = Optional.ofNullable(caseData.get("units")).orElse("");
				orderBatchInsertData01.insertOrderApplicationFormDetailData01(OrderApplicationFormDetailDTO.builder()
						.applicationFormDetailId(applicationFormDetailId)
						.applicationFormId(applicationFormId)
						.itemSapCode(itemSapCode)
						.quantity(quantity)
						.units(units)
						.createPerson("at")
						.createTime(createTime)
						.build());
			});
		});
		And("[整单折扣分配计划分摊13]当前存在如下的[纯赠-AT_FP002]整单折扣分配计划信息", () -> {
			allocationPlanId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			allocationPlanId_001 = allocationPlanId;
			planMonth = new SimpleDateFormat("yyyy-MM").format(System.currentTimeMillis());
			orderBatchInsertData01.insertOrderAllocationPlanData01(OrderAllocationPlanDTO.builder()
					.allocationPlanId(allocationPlanId)
					.orderDiscountApplicationFormId(applicationFormId)
					.status("IN_PROGRESS")
					.lockStatus("UNLOCK")
					.planMonth(planMonth)
					.newExecutionStartTime(beginDate)
					.newExecutionEndTime(endDate)
					.createPerson("at")
					.createTime(createTime)
					.build());
		});
		And("[整单折扣分配计划分摊13]当前存在如下的[纯赠-AT_FP002]整单折扣分配计划详细信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String remark = Optional.ofNullable(caseData.get("remark")).orElse("");
				allocationPlanDetailId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
				setAllocationPlanInfoByRemark(remark, allocationPlanId, allocationPlanDetailId);
				String originalItemSapCode = Optional.ofNullable(caseData.get("originalItemSapCode")).orElse("");
				String originalDistributionChannelCode = Optional.ofNullable(caseData.get("originalDistributionChannelCode")).orElse("");
				String originalDistributionChannelName = Optional.ofNullable(caseData.get("originalDistributionChannelName")).orElse("");
				String giftItemSapCode = Optional.ofNullable(caseData.get("giftItemSapCode")).orElse("");
				String giftDistributionChannelCode = Optional.ofNullable(caseData.get("giftDistributionChannelCode")).orElse("");
				String giftDistributionChannelName = Optional.ofNullable(caseData.get("giftDistributionChannelName")).orElse("");
				String allocationQuantity = Optional.ofNullable(caseData.get("allocationQuantity")).orElse("");
				Integer originalQuantity = Integer.valueOf(Optional.ofNullable(caseData.get("originalQuantity")).orElse("0"));
				Integer giftQuantity = Integer.parseInt(Optional.ofNullable(caseData.get("giftQuantity")).orElse("0"));
				Integer initialBonusQuantity = Integer.parseInt(Optional.ofNullable(caseData.get("initialBonusQuantity")).orElse("0"));
				String needShip = Optional.ofNullable(caseData.get("needShip")).orElse("");
				String unit = Optional.ofNullable(caseData.get("unit")).orElse("");
				String invalidStatus = Optional.ofNullable(caseData.get("invalidStatus")).orElse("");
				String lotNo = Optional.ofNullable(caseData.get("lotNo")).orElse("");
				orderBatchInsertData01.insertOrderApplicationPlanDetailData01(OrderAllocationPlanDetailDTO.builder()
						.allocationPlanDetailId(allocationPlanDetailId)
						.allocationPlanId(allocationPlanId)
						.applicationFormDetailId(applicationFormDetailId)
						.customerEntSapCode(customerSapCode)
						.saleOrgSapCode(saleOrgSapCode)
						.regionCode(regionCode)
						.regionName(regionName)
						.originalItemSapCode(originalItemSapCode)
						.originalDistributionChannelCode(originalDistributionChannelCode)
						.originalDistributionChannelName(originalDistributionChannelName)
						.giftItemSapCode(giftItemSapCode)
						.giftDistributionChannelCode(giftDistributionChannelCode)
						.giftDistributionChannelName(giftDistributionChannelName)
						.allocationQuantity(allocationQuantity)
						.originalQuantity(originalQuantity)
						.giftQuantity(giftQuantity)
						.initialBonusQuantity(initialBonusQuantity)
						.unit(unit)
						.lotNo(lotNo)
						.productionDate(beginDate)
						.expirationDate(endDate)
						.needShip(needShip)
						.invalidStatus(invalidStatus)
						.remark(remark)
						.createPerson("at")
						.createTime(createTime)
						.build());
			});
		});
		And("[整单折扣分配计划分摊13]当前存在如下的[零头药-AT_FP003]整单折扣申请单信息", (DataTable dataTable) -> {
			applicationFormId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			processOrderNo = "ZDZK" + new SimpleDateFormat("yyyyMMddHHmm").format(System.currentTimeMillis()) + "_3";
			dataTable.asMaps().stream().forEach(caseData -> {
				String projectType = Optional.ofNullable(caseData.get("projectType")).orElse("");
				String module = Optional.ofNullable(caseData.get("module")).orElse("");
				orderBatchInsertData01.insertOrderApplicationFormData01(OrderApplicationFormDTO.builder()
						.applicationFormId(applicationFormId)
						.processOrderNo(processOrderNo)
						.applicant("at-OTC分公司")
						.applicationDate(createTime)
						.projectType(projectType)
						.module(module)
						.status("APPROVED")
						.executionStartTime(beginDate)
						.executionEndTime(endDate)
						.saleOrgSapCode(saleOrgSapCode)
						.createPerson("at")
						.createTime(createTime)
						.build());
			});
		});
		And("[整单折扣分配计划分摊13]当前存在如下的[零头药-AT_FP003]整单折扣申请单商品信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				applicationFormDetailId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String quantity = Optional.ofNullable(caseData.get("quantity")).orElse("");
				String units = Optional.ofNullable(caseData.get("units")).orElse("");
				orderBatchInsertData01.insertOrderApplicationFormDetailData01(OrderApplicationFormDetailDTO.builder()
						.applicationFormDetailId(applicationFormDetailId)
						.applicationFormId(applicationFormId)
						.itemSapCode(itemSapCode)
						.quantity(quantity)
						.units(units)
						.createPerson("at")
						.createTime(createTime)
						.build());
			});
		});
		And("[整单折扣分配计划分摊13]当前存在如下的[零头药-AT_FP003]整单折扣分配计划信息", () -> {
			allocationPlanId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			allocationPlanId_001 = allocationPlanId;
			planMonth = new SimpleDateFormat("yyyy-MM").format(System.currentTimeMillis());
			orderBatchInsertData01.insertOrderAllocationPlanData01(OrderAllocationPlanDTO.builder()
					.allocationPlanId(allocationPlanId)
					.orderDiscountApplicationFormId(applicationFormId)
					.status("IN_PROGRESS")
					.lockStatus("UNLOCK")
					.planMonth(planMonth)
					.newExecutionStartTime(beginDate)
					.newExecutionEndTime(endDate)
					.createPerson("at")
					.createTime(createTime)
					.build());
		});
		And("[整单折扣分配计划分摊13]当前存在如下的[零头药-AT_FP003]整单折扣分配计划详细信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String lotNo = Optional.ofNullable(caseData.get("lot_no")).orElse("");
				String remark = Optional.ofNullable(caseData.get("remark")).orElse("");
				allocationPlanDetailId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
				setAllocationPlanInfoByRemark(remark, allocationPlanId, allocationPlanDetailId);
				String originalItemSapCode = Optional.ofNullable(caseData.get("originalItemSapCode")).orElse("");
				String originalDistributionChannelCode = Optional.ofNullable(caseData.get("originalDistributionChannelCode")).orElse("");
				String originalDistributionChannelName = Optional.ofNullable(caseData.get("originalDistributionChannelName")).orElse("");
				String giftItemSapCode = Optional.ofNullable(caseData.get("giftItemSapCode")).orElse("");
				String giftDistributionChannelCode = Optional.ofNullable(caseData.get("giftDistributionChannelCode")).orElse("");
				String giftDistributionChannelName = Optional.ofNullable(caseData.get("giftDistributionChannelName")).orElse("");
				String allocationQuantity = Optional.ofNullable(caseData.get("allocationQuantity")).orElse("");
				Integer originalQuantity = Integer.valueOf(Optional.ofNullable(caseData.get("originalQuantity")).orElse("0"));
				Integer giftQuantity = Integer.parseInt(Optional.ofNullable(caseData.get("giftQuantity")).orElse("0"));
				Integer initialBonusQuantity = Integer.parseInt(Optional.ofNullable(caseData.get("initialBonusQuantity")).orElse("0"));
				String needShip = Optional.ofNullable(caseData.get("needShip")).orElse("");
				String unit = Optional.ofNullable(caseData.get("unit")).orElse("");
				String invalidStatus = Optional.ofNullable(caseData.get("invalidStatus")).orElse("");
				orderBatchInsertData01.insertOrderApplicationPlanDetailData01(OrderAllocationPlanDetailDTO.builder()
						.allocationPlanDetailId(allocationPlanDetailId)
						.allocationPlanId(allocationPlanId)
						.applicationFormDetailId(applicationFormDetailId)
						.customerEntSapCode(customerSapCode)
						.saleOrgSapCode(saleOrgSapCode)
						.regionCode(regionCode)
						.regionName(regionName)
						.originalItemSapCode(originalItemSapCode)
						.originalDistributionChannelCode(originalDistributionChannelCode)
						.originalDistributionChannelName(originalDistributionChannelName)
						.giftItemSapCode(giftItemSapCode)
						.giftDistributionChannelCode(giftDistributionChannelCode)
						.giftDistributionChannelName(giftDistributionChannelName)
						.allocationQuantity(allocationQuantity)
						.originalQuantity(originalQuantity)
						.giftQuantity(giftQuantity)
						.initialBonusQuantity(initialBonusQuantity)
						.unit(unit)
						.lotNo(lotNo)
						.productionDate(beginDate)
						.expirationDate(endDate)
						.needShip(needShip)
						.invalidStatus(invalidStatus)
						.remark(remark)
						.createPerson("at")
						.createTime(createTime)
						.build());
			});
		});
		And("[整单折扣分配计划分摊13]当前存在如下的[捐赠-AT_FP004]整单折扣申请单信息", (DataTable dataTable) -> {
			applicationFormId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			processOrderNo = "ZDZK" + new SimpleDateFormat("yyyyMMddHHmm").format(System.currentTimeMillis()) + "_4";
			dataTable.asMaps().stream().forEach(caseData -> {
				String projectType = Optional.ofNullable(caseData.get("projectType")).orElse("");
				String module = Optional.ofNullable(caseData.get("module")).orElse("");
				orderBatchInsertData01.insertOrderApplicationFormData01(OrderApplicationFormDTO.builder()
						.applicationFormId(applicationFormId)
						.processOrderNo(processOrderNo)
						.applicant("at-OTC分公司")
						.applicationDate(createTime)
						.projectType(projectType)
						.module(module)
						.status("APPROVED")
						.executionStartTime(beginDate)
						.executionEndTime(endDate)
						.saleOrgSapCode(saleOrgSapCode)
						.createPerson("at")
						.createTime(createTime)
						.build());
			});
		});
		And("[整单折扣分配计划分摊13]当前存在如下的[捐赠-AT_FP004]整单折扣申请单商品信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				applicationFormDetailId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String quantity = Optional.ofNullable(caseData.get("quantity")).orElse("");
				String units = Optional.ofNullable(caseData.get("units")).orElse("");
				orderBatchInsertData01.insertOrderApplicationFormDetailData01(OrderApplicationFormDetailDTO.builder()
						.applicationFormDetailId(applicationFormDetailId)
						.applicationFormId(applicationFormId)
						.itemSapCode(itemSapCode)
						.quantity(quantity)
						.units(units)
						.createPerson("at")
						.createTime(createTime)
						.build());
			});
		});
		And("[整单折扣分配计划分摊13]当前存在如下的[捐赠-AT_FP004]整单折扣分配计划信息", () -> {
			allocationPlanId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			allocationPlanId_002 = allocationPlanId;
			planMonth = new SimpleDateFormat("yyyy-MM").format(System.currentTimeMillis());
			orderBatchInsertData01.insertOrderAllocationPlanData01(OrderAllocationPlanDTO.builder()
					.allocationPlanId(allocationPlanId)
					.orderDiscountApplicationFormId(applicationFormId)
					.status("IN_PROGRESS")
					.lockStatus("UNLOCK")
					.planMonth(planMonth)
					.newExecutionStartTime(beginDate)
					.newExecutionEndTime(endDate)
					.createPerson("at")
					.createTime(createTime)
					.build());
		});
		And("[整单折扣分配计划分摊13]当前存在如下的[捐赠-AT_FP004]整单折扣分配计划详细信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String remark = Optional.ofNullable(caseData.get("remark")).orElse("");
				allocationPlanDetailId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
				setAllocationPlanInfoByRemark(remark, allocationPlanId, allocationPlanDetailId);
				String originalItemSapCode = Optional.ofNullable(caseData.get("originalItemSapCode")).orElse("");
				String originalDistributionChannelCode = Optional.ofNullable(caseData.get("originalDistributionChannelCode")).orElse("");
				String originalDistributionChannelName = Optional.ofNullable(caseData.get("originalDistributionChannelName")).orElse("");
				String giftItemSapCode = Optional.ofNullable(caseData.get("giftItemSapCode")).orElse("");
				String giftDistributionChannelCode = Optional.ofNullable(caseData.get("giftDistributionChannelCode")).orElse("");
				String giftDistributionChannelName = Optional.ofNullable(caseData.get("giftDistributionChannelName")).orElse("");
				String allocationQuantity = Optional.ofNullable(caseData.get("allocationQuantity")).orElse("");
				Integer originalQuantity = Integer.valueOf(Optional.ofNullable(caseData.get("originalQuantity")).orElse("0"));
				Integer giftQuantity = Integer.parseInt(Optional.ofNullable(caseData.get("giftQuantity")).orElse("0"));
				Integer initialBonusQuantity = Integer.parseInt(Optional.ofNullable(caseData.get("initialBonusQuantity")).orElse("0"));
				String needShip = Optional.ofNullable(caseData.get("needShip")).orElse("");
				String unit = Optional.ofNullable(caseData.get("unit")).orElse("");
				String invalidStatus = Optional.ofNullable(caseData.get("invalidStatus")).orElse("");
				String lotNo = Optional.ofNullable(caseData.get("lotNo")).orElse("");
				orderBatchInsertData01.insertOrderApplicationPlanDetailData01(OrderAllocationPlanDetailDTO.builder()
						.allocationPlanDetailId(allocationPlanDetailId)
						.allocationPlanId(allocationPlanId)
						.applicationFormDetailId(applicationFormDetailId)
						.customerEntSapCode(customerSapCode)
						.saleOrgSapCode(saleOrgSapCode)
						.regionCode(regionCode)
						.regionName(regionName)
						.originalItemSapCode(originalItemSapCode)
						.originalDistributionChannelCode(originalDistributionChannelCode)
						.originalDistributionChannelName(originalDistributionChannelName)
						.giftItemSapCode(giftItemSapCode)
						.giftDistributionChannelCode(giftDistributionChannelCode)
						.giftDistributionChannelName(giftDistributionChannelName)
						.allocationQuantity(allocationQuantity)
						.originalQuantity(originalQuantity)
						.giftQuantity(giftQuantity)
						.initialBonusQuantity(initialBonusQuantity)
						.unit(unit)
						.lotNo(lotNo)
						.productionDate(beginDate)
						.expirationDate(endDate)
						.needShip(needShip)
						.invalidStatus(invalidStatus)
						.remark(remark)
						.createPerson("at")
						.createTime(createTime)
						.build());
			});
		});
		And("[整单折扣分配计划分摊13]添加商品", (DataTable dataTable) -> {
			log.info("添加商品");
		});
		And("[整单折扣分配计划分摊13]选择项目", (DataTable dataTable) -> {
			log.info("选择项目");
		});
		Then("[整单折扣分配计划分摊13]创建订单应校验通过且提交成功", (DataTable dataTable) -> {
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
			List<OrderDiscountCalcCalculateReqGroupListListDTO> orderDiscountCalcCalculateReqGroupListListDTOS = new ArrayList<>();
			dataTable.asMaps().forEach(caseData -> {
				String id = "100000" + Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String orderWideDiscountSubject = Optional.ofNullable(caseData.get("orderWideDiscountSubject")).orElse("");
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String needShip = Optional.ofNullable(caseData.get("needShip")).orElse("");
				String unitPrice = new BigDecimal(caseData.get("unitPrice")).setScale(2, RoundingMode.HALF_UP).toString();
				String quantity = Optional.ofNullable(caseData.get("quantity")).orElse("");
				String boxRatio = new Integer(caseData.get("boxRatio")).toString();
				String preDiscount = new BigDecimal(caseData.get("preDiscount")).setScale(2, RoundingMode.HALF_UP).toString();
				if ("0.00".equals(preDiscount)) {
					id = ""; // 如果是"0"，则将id设置为空字符串
				}
				String calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(2, RoundingMode.HALF_UP).toString();
				String totalInline = new BigDecimal(caseData.get("totalInline")).setScale(2, RoundingMode.HALF_UP).toString();
				String preDiscountTotal = new BigDecimal(caseData.get("preDiscountTotal")).setScale(2, RoundingMode.HALF_UP).toString();
				String orderDiscountTotal = new BigDecimal(caseData.get("orderDiscountTotal")).setScale(2, RoundingMode.HALF_UP).toString();
				String discountProportion = String.format("%.4f", new BigDecimal(caseData.get("preDiscountTotal").toString()).add(new BigDecimal(caseData.get("orderDiscountTotal").toString())).divide(new BigDecimal(caseData.get("totalInline").toString()), 4, RoundingMode.HALF_UP).doubleValue());
				String remark = Optional.ofNullable(caseData.get("remark")).orElse("");
				getAllocationPlanInfoByRemark(remark);
				orderDiscountCalcCalculateReqGroupListListDTOS.add(OrderDiscountCalcCalculateReqGroupListListDTO.builder()
						.orderWideDiscountSubject(orderWideDiscountSubject)
						.discountAllocationPlanId(allocationPlanId)
						.discountAllocationPlanDetailId(allocationPlanDetailId)
						.discountId(id)
						.preDiscountDetailId(id)
						.itemSapCode(itemSapCode)
						.itemName(ProductEnum.fetchProductName(itemSapCode))
						.itemDesc(ProductEnum.fetchProductSpec(itemSapCode))
						.itemGroupCode("01")
						.itemGroupName("成品药-非管制药品")
						.distributionChannelCode("22")
						.distributionChannelName("分销-非医疗")
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
						.discountProportion(discountProportion)
						.build());
			});
			OrderDiscountCalcCalculateReqDTO calculateReq = orderApiOrderManagement01.discountCalcAfterData(OrderDataDTO.builder()
					.orderType("STANDARD")
					.deliveryNo(customerSapCode)
					.saleOrgSapCode(saleOrgSapCode)
					.saleOrgName(SaleOrgEnum.fetchName(saleOrgSapCode))
					.saleOrgZtCode(SaleOrgEnum.fetchZtCode(saleOrgSapCode))
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
					.contractRemark("自动化测试-合同备注")
					.remark("自动化测试-订单备注")
					.calculateReq(OrderDiscountCalcCalculateReqDTO.builder()
							.totalInline(totalInlineSum)
							.preDiscountTotal(preDiscountTotalSum)
							.orderDiscountTotal(orderDiscountTotalSum)
							.groupList(Arrays.asList(OrderDiscountCalcCalculateReqGroupListDTO.builder()
									.itemGroupCode("01")
									.itemGroupName("成品药-非管制药品")
									.distributionChannelCode("22")
									.distributionChannelName("分销-非医疗")
									.totalInline(totalInlineSum)
									.preDiscountTotal(preDiscountTotalSum)
									.orderDiscountTotal(orderDiscountTotalSum)
									.list(orderDiscountCalcCalculateReqGroupListListDTOS)
									.build()))
							.build()
					)
					.build());
			Response createOrderVerifyResponse = orderApiOrderManagement01.createOrderVerify(OrderDataDTO.builder()
					.orderType("STANDARD")
					.deliveryNo(customerSapCode)
					.saleOrgSapCode(saleOrgSapCode)
					.saleOrgName(SaleOrgEnum.fetchName(saleOrgSapCode))
					.saleOrgZtCode(SaleOrgEnum.fetchZtCode(saleOrgSapCode))
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
					.contractRemark("自动化测试-合同备注")
					.remark("自动化测试-订单备注")
					.calculateReq(calculateReq)
					.build());
			createOrderVerifyResponse.then().body("resultCode", Matchers.equalTo("0"), "resultMsg", Matchers.equalTo("success"));
			Response createOrderSubmitResponse = orderApiOrderManagement01.createOrderSubmit(OrderDataDTO.builder()
					.orderType("STANDARD")
					.deliveryNo(customerSapCode)
					.saleOrgSapCode(saleOrgSapCode)
					.saleOrgName(SaleOrgEnum.fetchName(saleOrgSapCode))
					.saleOrgZtCode(SaleOrgEnum.fetchZtCode(saleOrgSapCode))
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
					.contractRemark("自动化测试-合同备注")
					.remark("自动化测试-订单备注")
					.calculateReq(calculateReq)
					.build());
			createOrderSubmitResponse.then().body("resultCode", Matchers.equalTo("0"), "resultMsg", Matchers.equalTo("success"));
		});
		Then("[整单折扣分配计划分摊13]订单整单折扣使用记录成功", (DataTable dataTable) -> {
			List<Map<String, String>> caseDataMap = new ArrayList<>();
			String orderNo = orderApiOrderManagement01.selectOrderInfo(OrderQueryPageByConditionDTO.builder()
					.state("PENDING_BUSINESS_REVIEW")
					.cancelStatus("NOT_CANCEL")
					.saleOrgSapCode(saleOrgSapCode)
					.customerCodeOrName(customerSapCode)
					.build());
			String parentsOrderNo = StringUtils.left(orderNo, 22);
			dataTable.asMaps().forEach(caseData -> {
				Map<String, String> processedData = new HashMap<>();
				processedData.put("parentOrderNo", parentsOrderNo);
				processedData.put("parentOrderItemNo", Optional.ofNullable(caseData.get("parentOrderItemNo")).orElse(""));
				processedData.put("orderNo", orderNo);
				processedData.put("orderItemNo", Optional.ofNullable(caseData.get("orderItemNo")).orElse(""));
				String remark = Optional.ofNullable(caseData.get("remark")).orElse("");
				getAllocationPlanInfoByRemark(remark);
				processedData.put("orderDiscountApplicationPlanId", allocationPlanId);
				processedData.put("orderDiscountApplicationPlanDetailId", allocationPlanDetailId);
				processedData.put("operateType", Optional.ofNullable(caseData.get("operateType")).orElse(""));
				processedData.put("operateAmount", Optional.ofNullable(caseData.get("operateAmount")).orElse(""));
				caseDataMap.add(processedData);
			});
			List<Map<String, Object>> sqlResult = orderDiscountDetailBatchSelectData01.queryOrderDiscountDetailData01(parentsOrderNo);
			discountCheckAllocationPlan01.checkDiscountAllocationPlan(caseDataMap, sqlResult);
			orderBatchClearData01.clearOrderData01(orderNo);
			priceBatchClearData01.clearPirceData01("at");
			orderBatchClearData01.clearOrderPreDiscountData01("at");
			orderBatchClearData01.clearDiscountData01("at");
		});
	}

}
