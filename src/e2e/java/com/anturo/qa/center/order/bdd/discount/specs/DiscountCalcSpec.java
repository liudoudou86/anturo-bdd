package com.anturo.qa.center.order.bdd.discount.specs;

import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.IdUtil;
import com.anturo.qa.center.order.bdd.order.apis.OrderApiOrderManagement01;
import com.anturo.qa.center.order.bdd.order.backgrounds.DiscountBackgroundData01;
import com.anturo.qa.center.order.bdd.order.batchs.OrderBatchUpdateData01;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @Author Tesla
 * @Date 2024/05/20
 * @Description
 */

@SuppressWarnings("all")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class DiscountCalcSpec extends DiscountBackgroundData01 implements En {

	String saleOrgSapCode, regionCode, regionName, customerSapCode, shippingAddress, shippingRecipient, shippingTelephone;

	String preDiscountId, preDiscountDetailId;

	String applicationFormId, applicationFormDetailId, allocationPlanId, allocationPlanDetailId;

	String createTime, beginDate, endDate, processOrderNo, planMonth, lotNo;

	String lty_allocationPlanDetailId, cz_allocationPlanDetailId, mz_allocationPlanDetailId;

	DataTable domainDataTable;

	@Steps(actor = "QA", shared = true)
	OrderApiOrderManagement01 orderApiOrderManagement01;

	@Steps(actor = "QA", shared = true)
	OrderCheckOrderManagement01 orderCheckOrderManagement01;

	@Steps(actor = "QA", shared = true)
	OrderBatchUpdateData01 orderBatchUpdateData01;

	// 终端ID
	Long workerId = NetUtil.ipv4ToLong(NetUtil.getLocalhostStr()) >> 16 & 31;
	// 数据中心ID
	Long dataCenterId = 1L;

	public DiscountCalcSpec() {
		super();
		this.scenario01();
		this.scenario02();
		this.scenario05A();
		this.scenario05B();
		this.scenario06();
		this.scenario07();
		this.scenario08();
		this.scenario09();
		this.scenario10();
		this.scenario11();
		this.scenario12();
		this.scenario13();
		this.scenario14();
		this.scenario15();
		this.scenario16();
		this.scenario17();
		this.scenario18();
		this.scenario19();
		this.scenario20();
		this.scenario21();
		this.scenario22();
	}


	private void scenario01() {
		Given("[整单折扣计算01]当前存在如下客户信息", (DataTable dataTable) -> {
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
		When("[整单折扣计算01]当前存在如下的预折商品信息", (DataTable dataTable) -> {
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
		And("[整单折扣计算01]当前存在如下的有效预折折扣信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String effectiveState = Optional.ofNullable(caseData.get("effectiveState")).orElse("");
				orderBatchUpdateData01.updateOrderPreDiscountDetailEffectiveState(OrderPreDiscountDetailDTO.builder().effectiveState(effectiveState).itemSapCode(itemSapCode).build(), OrderPreDiscountDTO.builder().customerSapCode(customerSapCode).build());
			});
		});
		And("[整单折扣计算01]当前存在如下的整单折扣申请单信息", (DataTable dataTable) -> {
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
		And("[整单折扣计算01]当前存在如下的整单折扣申请单商品信息", (DataTable dataTable) -> {
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
		And("[整单折扣计算01]当前存在如下的整单折扣分配计划信息", () -> {
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
		And("[整单折扣计算01]当前存在如下的整单折扣分配计划详细信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				allocationPlanDetailId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
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
						.createPerson("at")
						.createTime(createTime)
						.build());
			});
		});
		And("[整单折扣计算01]添加商品", (DataTable dataTable) -> {
			log.info("添加商品");
		});
		And("[整单折扣计算01]选择项目", (DataTable dataTable) -> {
			log.info("选择项目");
		});
		Then("[整单折扣计算01]进行整单折扣计算后,订单行分摊如下", (DataTable dataTable) -> {
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
				String calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(2, RoundingMode.HALF_UP).toString();
				String totalInline = new BigDecimal(caseData.get("totalInline")).setScale(2, RoundingMode.HALF_UP).toString();
				String preDiscountTotal = new BigDecimal(caseData.get("preDiscountTotal")).setScale(2, RoundingMode.HALF_UP).toString();
				String orderDiscountTotal = new BigDecimal(caseData.get("orderDiscountTotal")).setScale(2, RoundingMode.HALF_UP).toString();
				if ("PT04".equals(orderWideDiscountSubject)) {
					allocationPlanDetailId = cz_allocationPlanDetailId;
				} else if ("PT02".equals(orderWideDiscountSubject)) {
					allocationPlanDetailId = lty_allocationPlanDetailId;
				} else if ("PT01".equals(orderWideDiscountSubject)) {
					allocationPlanDetailId = mz_allocationPlanDetailId;
				} else {
					allocationPlanDetailId = "";
				}
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
			discountCalcResponse.then().body("resultCode", Matchers.equalTo("0"), "resultMsg", Matchers.equalTo("success"));
			orderCheckOrderManagement01.checkOrderDiscountCalc(dataTable, discountCalcResponse);
			priceBatchClearData01.clearPirceData01("at");
			orderBatchClearData01.clearOrderPreDiscountData01("at");
			orderBatchClearData01.clearDiscountData01("at");
		});
	}


	private void scenario02() {
		Given("[整单折扣计算02]当前存在如下客户信息", (DataTable dataTable) -> {
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
		When("[整单折扣计算02]当前存在如下的预折商品信息", (DataTable dataTable) -> {
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
		And("[整单折扣计算02]当前存在如下的整单折扣申请单信息", (DataTable dataTable) -> {
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
		And("[整单折扣计算02]当前存在如下的整单折扣申请单商品信息", (DataTable dataTable) -> {
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
		And("[整单折扣计算02]当前存在如下的整单折扣分配计划信息", () -> {
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
		And("[整单折扣计算02]当前存在如下的整单折扣分配计划详细信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				allocationPlanDetailId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
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
						.createPerson("at")
						.createTime(createTime)
						.build());
			});
		});
		And("[整单折扣计算02]添加商品", (DataTable dataTable) -> {
			log.info("添加商品");
		});
		Then("[整单折扣计算02]进行整单折扣计算后,订单行分摊如下", (DataTable dataTable) -> {
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
				String calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(2, RoundingMode.HALF_UP).toString();
				String totalInline = new BigDecimal(caseData.get("totalInline")).setScale(2, RoundingMode.HALF_UP).toString();
				String preDiscountTotal = new BigDecimal(caseData.get("preDiscountTotal")).setScale(2, RoundingMode.HALF_UP).toString();
				String orderDiscountTotal = new BigDecimal(caseData.get("orderDiscountTotal")).setScale(2, RoundingMode.HALF_UP).toString();
				if ("PT04".equals(orderWideDiscountSubject)) {
					allocationPlanDetailId = cz_allocationPlanDetailId;
				} else if ("PT02".equals(orderWideDiscountSubject)) {
					allocationPlanDetailId = lty_allocationPlanDetailId;
				} else if ("PT01".equals(orderWideDiscountSubject)) {
					allocationPlanDetailId = mz_allocationPlanDetailId;
				} else {
					allocationPlanDetailId = "";
				}
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
			discountCalcResponse.then().body("resultCode", Matchers.equalTo("0"), "resultMsg", Matchers.equalTo("success"));
			orderCheckOrderManagement01.checkOrderDiscountCalc(dataTable, discountCalcResponse);
			priceBatchClearData01.clearPirceData01("at");
			orderBatchClearData01.clearOrderPreDiscountData01("at");
			orderBatchClearData01.clearDiscountData01("at");
		});
	}

	private void scenario05A() {
		Given("[整单折扣计算05A]当前存在如下客户信息", (DataTable dataTable) -> {
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
		When("[整单折扣计算05A]当前存在如下的预折商品信息", (DataTable dataTable) -> {
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
		And("[整单折扣计算05A]当前存在如下的有效预折折扣信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String effectiveState = Optional.ofNullable(caseData.get("effectiveState")).orElse("");
				orderBatchUpdateData01.updateOrderPreDiscountDetailEffectiveState(OrderPreDiscountDetailDTO.builder().effectiveState(effectiveState).itemSapCode(itemSapCode).build(), OrderPreDiscountDTO.builder().customerSapCode(customerSapCode).build());
			});
		});
		And("[整单折扣计算05A]当前存在如下的整单折扣申请单信息", (DataTable dataTable) -> {
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
		And("[整单折扣计算05A]当前存在如下的整单折扣申请单商品信息", (DataTable dataTable) -> {
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
		And("[整单折扣计算05A]当前存在如下的整单折扣分配计划信息", () -> {
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
		And("[整单折扣计算05A]当前存在如下的整单折扣分配计划详细信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				allocationPlanDetailId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
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
						.createPerson("at")
						.createTime(createTime)
						.build());
			});
		});
		And("[整单折扣计算05A]添加商品", (DataTable dataTable) -> {
			log.info("添加商品");
		});
		And("[整单折扣计算05A]选择项目", (DataTable dataTable) -> {
			log.info("选择项目");
		});
		Then("[整单折扣计算05A]进行整单折扣计算后,订单行分摊如下", (DataTable dataTable) -> {
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
				String calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(2, RoundingMode.HALF_UP).toString();
				String totalInline = new BigDecimal(caseData.get("totalInline")).setScale(2, RoundingMode.HALF_UP).toString();
				String preDiscountTotal = new BigDecimal(caseData.get("preDiscountTotal")).setScale(2, RoundingMode.HALF_UP).toString();
				String orderDiscountTotal = new BigDecimal(caseData.get("orderDiscountTotal")).setScale(2, RoundingMode.HALF_UP).toString();
				if ("PT04".equals(orderWideDiscountSubject)) {
					allocationPlanDetailId = cz_allocationPlanDetailId;
				} else if ("PT02".equals(orderWideDiscountSubject)) {
					allocationPlanDetailId = lty_allocationPlanDetailId;
				} else if ("PT01".equals(orderWideDiscountSubject)) {
					allocationPlanDetailId = mz_allocationPlanDetailId;
				} else {
					allocationPlanDetailId = "";
				}
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
			discountCalcResponse.then().body("resultCode", Matchers.equalTo("0"), "resultMsg", Matchers.equalTo("success"));
			orderCheckOrderManagement01.checkOrderDiscountCalc(dataTable, discountCalcResponse);
			priceBatchClearData01.clearPirceData01("at");
			orderBatchClearData01.clearOrderPreDiscountData01("at");
			orderBatchClearData01.clearDiscountData01("at");
		});
	}

	private void scenario05B() {
		Given("[整单折扣计算05B]当前存在如下客户信息", (DataTable dataTable) -> {
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
		When("[整单折扣计算05B]当前存在如下的预折商品信息", (DataTable dataTable) -> {
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
		And("[整单折扣计算05B]当前存在如下的有效预折折扣信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String effectiveState = Optional.ofNullable(caseData.get("effectiveState")).orElse("");
				orderBatchUpdateData01.updateOrderPreDiscountDetailEffectiveState(OrderPreDiscountDetailDTO.builder().effectiveState(effectiveState).itemSapCode(itemSapCode).build(), OrderPreDiscountDTO.builder().customerSapCode(customerSapCode).build());
			});
		});
		And("[整单折扣计算05B]当前存在如下的整单折扣申请单信息", (DataTable dataTable) -> {
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
		And("[整单折扣计算05B]当前存在如下的整单折扣申请单商品信息", (DataTable dataTable) -> {
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
		And("[整单折扣计算05B]当前存在如下的整单折扣分配计划信息", () -> {
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
		And("[整单折扣计算05B]当前存在如下的整单折扣分配计划详细信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				allocationPlanDetailId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
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
						.createPerson("at")
						.createTime(createTime)
						.build());
			});
		});
		And("[整单折扣计算05B]添加商品", (DataTable dataTable) -> {
			log.info("添加商品");
		});
		And("[整单折扣计算05B]选择项目", (DataTable dataTable) -> {
			log.info("选择项目");
		});
		Then("[整单折扣计算05B]进行整单折扣计算后,订单行分摊如下", (DataTable dataTable) -> {
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
				String calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(2, RoundingMode.HALF_UP).toString();
				String totalInline = new BigDecimal(caseData.get("totalInline")).setScale(2, RoundingMode.HALF_UP).toString();
				String preDiscountTotal = new BigDecimal(caseData.get("preDiscountTotal")).setScale(2, RoundingMode.HALF_UP).toString();
				String orderDiscountTotal = new BigDecimal(caseData.get("orderDiscountTotal")).setScale(2, RoundingMode.HALF_UP).toString();
				if ("PT04".equals(orderWideDiscountSubject)) {
					allocationPlanDetailId = cz_allocationPlanDetailId;
				} else if ("PT02".equals(orderWideDiscountSubject)) {
					allocationPlanDetailId = lty_allocationPlanDetailId;
				} else if ("PT01".equals(orderWideDiscountSubject)) {
					allocationPlanDetailId = mz_allocationPlanDetailId;
				} else {
					allocationPlanDetailId = "";
				}
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
			discountCalcResponse.then().body("resultCode", Matchers.equalTo("0"), "resultMsg", Matchers.equalTo("success"));
			orderCheckOrderManagement01.checkOrderDiscountCalc(dataTable, discountCalcResponse);
			priceBatchClearData01.clearPirceData01("at");
			orderBatchClearData01.clearOrderPreDiscountData01("at");
			orderBatchClearData01.clearDiscountData01("at");
		});
	}


	private void scenario06() {
		Given("[整单折扣计算06]当前存在如下客户信息", (DataTable dataTable) -> {
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
		When("[整单折扣计算06]当前存在如下的预折商品信息", (DataTable dataTable) -> {
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
		And("[整单折扣计算06]当前存在如下的整单折扣申请单信息", (DataTable dataTable) -> {
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
		And("[整单折扣计算06]当前存在如下的整单折扣申请单商品信息", (DataTable dataTable) -> {
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
		And("[整单折扣计算06]当前存在如下的整单折扣分配计划信息", () -> {
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
		And("[整单折扣计算06]当前存在如下的整单折扣分配计划详细信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				allocationPlanDetailId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
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
						.createPerson("at")
						.createTime(createTime)
						.build());
			});
		});
		And("[整单折扣计算06]添加商品", (DataTable dataTable) -> {
			log.info("添加商品");
		});
		And("[整单折扣计算06]选择项目", (DataTable dataTable) -> {
			log.info("选择项目");
		});
		Then("[整单折扣计算06]进行整单折扣计算后,订单行分摊如下", (DataTable dataTable) -> {
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
				String calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(2, RoundingMode.HALF_UP).toString();
				String totalInline = new BigDecimal(caseData.get("totalInline")).setScale(2, RoundingMode.HALF_UP).toString();
				String preDiscountTotal = new BigDecimal(caseData.get("preDiscountTotal")).setScale(2, RoundingMode.HALF_UP).toString();
				String orderDiscountTotal = new BigDecimal(caseData.get("orderDiscountTotal")).setScale(2, RoundingMode.HALF_UP).toString();
				if ("PT04".equals(orderWideDiscountSubject)) {
					allocationPlanDetailId = cz_allocationPlanDetailId;
				} else if ("PT02".equals(orderWideDiscountSubject)) {
					allocationPlanDetailId = lty_allocationPlanDetailId;
				} else if ("PT01".equals(orderWideDiscountSubject)) {
					allocationPlanDetailId = mz_allocationPlanDetailId;
				} else {
					allocationPlanDetailId = "";
				}
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
			discountCalcResponse.then().body("resultCode", Matchers.equalTo("0"), "resultMsg", Matchers.equalTo("success"));
			orderCheckOrderManagement01.checkOrderDiscountCalc(dataTable, discountCalcResponse);
			priceBatchClearData01.clearPirceData01("at");
			orderBatchClearData01.clearOrderPreDiscountData01("at");
			orderBatchClearData01.clearDiscountData01("at");
		});
	}


	private void scenario07() {
		Given("[整单折扣计算07]当前存在如下客户信息", (DataTable dataTable) -> {
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
		When("[整单折扣计算07]当前存在如下的预折商品信息", (DataTable dataTable) -> {
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
		And("[整单折扣计算07]当前存在如下的预折折扣信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				BigDecimal calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(8, RoundingMode.HALF_UP);
				String effectiveState = Optional.ofNullable(caseData.get("effectiveState")).orElse("");
				orderBatchUpdateData01.updateOrderPreDiscountDetailCalculationValue(OrderPreDiscountDetailDTO.builder().effectiveState(effectiveState).calculationValue(calculationValue).itemSapCode(itemSapCode).build(), OrderPreDiscountDTO.builder().customerSapCode(customerSapCode).build());
			});
		});
		And("[整单折扣计算07]当前存在如下的整单折扣申请单信息", (DataTable dataTable) -> {
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
		And("[整单折扣计算07]当前存在如下的整单折扣申请单商品信息", (DataTable dataTable) -> {
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
		And("[整单折扣计算07]当前存在如下的整单折扣分配计划信息", () -> {
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
		And("[整单折扣计算07]当前存在如下的整单折扣分配计划详细信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				allocationPlanDetailId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
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
						.createPerson("at")
						.createTime(createTime)
						.build());
			});
		});
		And("[整单折扣计算07]添加商品", (DataTable dataTable) -> {
			log.info("添加商品");
		});
		And("[整单折扣计算07]选择项目", (DataTable dataTable) -> {
			log.info("选择项目");
		});
		Then("[整单折扣计算07]进行整单折扣计算后,订单行分摊如下", (DataTable dataTable) -> {
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
				String calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(2, RoundingMode.HALF_UP).toString();
				String totalInline = new BigDecimal(caseData.get("totalInline")).setScale(2, RoundingMode.HALF_UP).toString();
				String preDiscountTotal = new BigDecimal(caseData.get("preDiscountTotal")).setScale(2, RoundingMode.HALF_UP).toString();
				String orderDiscountTotal = new BigDecimal(caseData.get("orderDiscountTotal")).setScale(2, RoundingMode.HALF_UP).toString();
				if ("PT04".equals(orderWideDiscountSubject)) {
					allocationPlanDetailId = cz_allocationPlanDetailId;
				} else if ("PT02".equals(orderWideDiscountSubject)) {
					allocationPlanDetailId = lty_allocationPlanDetailId;
				} else if ("PT01".equals(orderWideDiscountSubject)) {
					allocationPlanDetailId = mz_allocationPlanDetailId;
				} else {
					allocationPlanDetailId = "";
				}
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
			discountCalcResponse.then().body("resultCode", Matchers.equalTo("0"), "resultMsg", Matchers.equalTo("success"));
			orderCheckOrderManagement01.checkOrderDiscountCalc(dataTable, discountCalcResponse);
			priceBatchClearData01.clearPirceData01("at");
			orderBatchClearData01.clearOrderPreDiscountData01("at");
			orderBatchClearData01.clearDiscountData01("at");
		});
	}


	private void scenario08() {
		Given("[整单折扣计算08]当前存在如下客户信息", (DataTable dataTable) -> {
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
		When("[整单折扣计算08]当前存在如下的预折商品信息", (DataTable dataTable) -> {
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
		And("[整单折扣计算08]当前存在如下的预折折扣信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				BigDecimal calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(8, RoundingMode.HALF_UP);
				String effectiveState = Optional.ofNullable(caseData.get("effectiveState")).orElse("");
				orderBatchUpdateData01.updateOrderPreDiscountDetailCalculationValue(OrderPreDiscountDetailDTO.builder().effectiveState(effectiveState).calculationValue(calculationValue).itemSapCode(itemSapCode).build(), OrderPreDiscountDTO.builder().customerSapCode(customerSapCode).build());
			});
		});
		And("[整单折扣计算08]当前存在如下的整单折扣申请单信息", (DataTable dataTable) -> {
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
		And("[整单折扣计算08]当前存在如下的整单折扣申请单商品信息", (DataTable dataTable) -> {
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
		And("[整单折扣计算08]当前存在如下的整单折扣分配计划信息", () -> {
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
		And("[整单折扣计算08]当前存在如下的整单折扣分配计划详细信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				allocationPlanDetailId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
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
						.createPerson("at")
						.createTime(createTime)
						.build());
			});
		});
		And("[整单折扣计算08]添加商品", (DataTable dataTable) -> {
			log.info("添加商品");
		});
		And("[整单折扣计算08]选择项目", (DataTable dataTable) -> {
			log.info("选择项目");
		});
		Then("[整单折扣计算08]进行整单折扣计算后,订单行分摊如下", (DataTable dataTable) -> {
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
				String calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(2, RoundingMode.HALF_UP).toString();
				String totalInline = new BigDecimal(caseData.get("totalInline")).setScale(2, RoundingMode.HALF_UP).toString();
				String preDiscountTotal = new BigDecimal(caseData.get("preDiscountTotal")).setScale(2, RoundingMode.HALF_UP).toString();
				String orderDiscountTotal = new BigDecimal(caseData.get("orderDiscountTotal")).setScale(2, RoundingMode.HALF_UP).toString();
				if ("PT04".equals(orderWideDiscountSubject)) {
					allocationPlanDetailId = cz_allocationPlanDetailId;
				} else if ("PT02".equals(orderWideDiscountSubject)) {
					allocationPlanDetailId = lty_allocationPlanDetailId;
				} else if ("PT01".equals(orderWideDiscountSubject)) {
					allocationPlanDetailId = mz_allocationPlanDetailId;
				} else {
					allocationPlanDetailId = "";
				}
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
			discountCalcResponse.then().body("resultCode", Matchers.equalTo("0"), "resultMsg", Matchers.equalTo("success"));
			orderCheckOrderManagement01.checkOrderDiscountCalc(dataTable, discountCalcResponse);
			priceBatchClearData01.clearPirceData01("at");
			orderBatchClearData01.clearOrderPreDiscountData01("at");
			orderBatchClearData01.clearDiscountData01("at");
		});
	}


	private void scenario09() {
		Given("[整单折扣计算09]当前存在如下客户信息", (DataTable dataTable) -> {
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
		When("[整单折扣计算09]当前存在如下的预折商品信息", (DataTable dataTable) -> {
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
		And("[整单折扣计算09]当前存在如下的预折折扣信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				BigDecimal calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(8, RoundingMode.HALF_UP);
				String effectiveState = Optional.ofNullable(caseData.get("effectiveState")).orElse("");
				orderBatchUpdateData01.updateOrderPreDiscountDetailCalculationValue(OrderPreDiscountDetailDTO.builder().effectiveState(effectiveState).calculationValue(calculationValue).itemSapCode(itemSapCode).build(), OrderPreDiscountDTO.builder().customerSapCode(customerSapCode).build());
			});
		});
		And("[整单折扣计算09]当前存在如下的整单折扣申请单信息", (DataTable dataTable) -> {
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
		And("[整单折扣计算09]当前存在如下的整单折扣申请单商品信息", (DataTable dataTable) -> {
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
		And("[整单折扣计算09]当前存在如下的整单折扣分配计划信息", () -> {
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
		And("[整单折扣计算09]当前存在如下的整单折扣分配计划详细信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				allocationPlanDetailId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
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
						.createPerson("at")
						.createTime(createTime)
						.build());
			});
		});
		And("[整单折扣计算09]添加商品", (DataTable dataTable) -> {
			log.info("添加商品");
		});
		And("[整单折扣计算09]选择项目", (DataTable dataTable) -> {
			log.info("选择项目");
		});
		Then("[整单折扣计算09]进行整单折扣计算后,订单行分摊如下", (DataTable dataTable) -> {
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
				String calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(2, RoundingMode.HALF_UP).toString();
				String totalInline = new BigDecimal(caseData.get("totalInline")).setScale(2, RoundingMode.HALF_UP).toString();
				String preDiscountTotal = new BigDecimal(caseData.get("preDiscountTotal")).setScale(2, RoundingMode.HALF_UP).toString();
				String orderDiscountTotal = new BigDecimal(caseData.get("orderDiscountTotal")).setScale(2, RoundingMode.HALF_UP).toString();
				if ("PT04".equals(orderWideDiscountSubject)) {
					allocationPlanDetailId = cz_allocationPlanDetailId;
				} else if ("PT02".equals(orderWideDiscountSubject)) {
					allocationPlanDetailId = lty_allocationPlanDetailId;
				} else if ("PT01".equals(orderWideDiscountSubject)) {
					allocationPlanDetailId = mz_allocationPlanDetailId;
				} else {
					allocationPlanDetailId = "";
				}
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
			discountCalcResponse.then().body("resultCode", Matchers.equalTo("0"), "resultMsg", Matchers.equalTo("success"));
			orderCheckOrderManagement01.checkOrderDiscountCalc(dataTable, discountCalcResponse);
			priceBatchClearData01.clearPirceData01("at");
			orderBatchClearData01.clearOrderPreDiscountData01("at");
			orderBatchClearData01.clearDiscountData01("at");
		});
	}

	private void scenario10() {
		Given("[整单折扣计算10]当前存在如下客户信息", (DataTable dataTable) -> {
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
		When("[整单折扣计算10]当前存在如下的预折商品信息", (DataTable dataTable) -> {
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
		And("[整单折扣计算10]当前存在如下的预折折扣信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				BigDecimal calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(8, RoundingMode.HALF_UP);
				String effectiveState = Optional.ofNullable(caseData.get("effectiveState")).orElse("");
				orderBatchUpdateData01.updateOrderPreDiscountDetailCalculationValue(OrderPreDiscountDetailDTO.builder().effectiveState(effectiveState).calculationValue(calculationValue).itemSapCode(itemSapCode).build(), OrderPreDiscountDTO.builder().customerSapCode(customerSapCode).build());
			});
		});
		And("[整单折扣计算10]当前存在如下的整单折扣申请单信息", (DataTable dataTable) -> {
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
		And("[整单折扣计算10]当前存在如下的整单折扣申请单商品信息", (DataTable dataTable) -> {
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
		And("[整单折扣计算10]当前存在如下的整单折扣分配计划信息", () -> {
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
		And("[整单折扣计算10]当前存在如下的整单折扣分配计划详细信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				allocationPlanDetailId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
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
						.createPerson("at")
						.createTime(createTime)
						.build());
			});
		});
		And("[整单折扣计算10]添加商品", (DataTable dataTable) -> {
			log.info("添加商品");
		});
		And("[整单折扣计算10]选择项目", (DataTable dataTable) -> {
			log.info("选择项目");
		});
		Then("[整单折扣计算10]进行整单折扣计算后,订单行分摊如下", (DataTable dataTable) -> {
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
				String calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(2, RoundingMode.HALF_UP).toString();
				String totalInline = new BigDecimal(caseData.get("totalInline")).setScale(2, RoundingMode.HALF_UP).toString();
				String preDiscountTotal = new BigDecimal(caseData.get("preDiscountTotal")).setScale(2, RoundingMode.HALF_UP).toString();
				String orderDiscountTotal = new BigDecimal(caseData.get("orderDiscountTotal")).setScale(2, RoundingMode.HALF_UP).toString();
				if ("PT04".equals(orderWideDiscountSubject)) {
					allocationPlanDetailId = cz_allocationPlanDetailId;
				} else if ("PT02".equals(orderWideDiscountSubject)) {
					allocationPlanDetailId = lty_allocationPlanDetailId;
				} else if ("PT01".equals(orderWideDiscountSubject)) {
					allocationPlanDetailId = mz_allocationPlanDetailId;
				} else {
					allocationPlanDetailId = "";
				}
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
			discountCalcResponse.then().body("resultCode", Matchers.equalTo("0"), "resultMsg", Matchers.equalTo("success"));
			orderCheckOrderManagement01.checkOrderDiscountCalc(dataTable, discountCalcResponse);
			priceBatchClearData01.clearPirceData01("at");
			orderBatchClearData01.clearOrderPreDiscountData01("at");
			orderBatchClearData01.clearDiscountData01("at");
		});
	}


	private void scenario11() {
		Given("[整单折扣计算11]当前存在如下客户信息", (DataTable dataTable) -> {
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
		When("[整单折扣计算11]当前存在如下的预折商品信息", (DataTable dataTable) -> {
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
		And("[整单折扣计算11]当前存在如下的预折折扣信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				BigDecimal calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(8, RoundingMode.HALF_UP);
				String effectiveState = Optional.ofNullable(caseData.get("effectiveState")).orElse("");
				orderBatchUpdateData01.updateOrderPreDiscountDetailCalculationValue(OrderPreDiscountDetailDTO.builder().effectiveState(effectiveState).calculationValue(calculationValue).itemSapCode(itemSapCode).build(), OrderPreDiscountDTO.builder().customerSapCode(customerSapCode).build());
			});
		});
		And("[整单折扣计算11]当前存在如下的整单折扣申请单信息", (DataTable dataTable) -> {
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
		And("[整单折扣计算11]当前存在如下的整单折扣申请单商品信息", (DataTable dataTable) -> {
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
		And("[整单折扣计算11]当前存在如下的整单折扣分配计划信息", () -> {
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
		And("[整单折扣计算11]当前存在如下的整单折扣分配计划详细信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				allocationPlanDetailId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
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
						.createPerson("at")
						.createTime(createTime)
						.build());
			});
		});
		And("[整单折扣计算11]添加商品", (DataTable dataTable) -> {
			log.info("添加商品");
		});
		And("[整单折扣计算11]选择项目", (DataTable dataTable) -> {
			log.info("选择项目");
		});
		Then("[整单折扣计算11]进行整单折扣计算后,订单行分摊如下", (DataTable dataTable) -> {
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
				String calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(2, RoundingMode.HALF_UP).toString();
				String totalInline = new BigDecimal(caseData.get("totalInline")).setScale(2, RoundingMode.HALF_UP).toString();
				String preDiscountTotal = new BigDecimal(caseData.get("preDiscountTotal")).setScale(2, RoundingMode.HALF_UP).toString();
				String orderDiscountTotal = new BigDecimal(caseData.get("orderDiscountTotal")).setScale(2, RoundingMode.HALF_UP).toString();
				if ("PT04".equals(orderWideDiscountSubject)) {
					allocationPlanDetailId = cz_allocationPlanDetailId;
				} else if ("PT02".equals(orderWideDiscountSubject)) {
					allocationPlanDetailId = lty_allocationPlanDetailId;
				} else if ("PT01".equals(orderWideDiscountSubject)) {
					allocationPlanDetailId = mz_allocationPlanDetailId;
				} else {
					allocationPlanDetailId = "";
				}
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
			discountCalcResponse.then().body("resultCode", Matchers.equalTo("0"), "resultMsg", Matchers.equalTo("success"));
			orderCheckOrderManagement01.checkOrderDiscountCalc(dataTable, discountCalcResponse);
			priceBatchClearData01.clearPirceData01("at");
			orderBatchClearData01.clearOrderPreDiscountData01("at");
			orderBatchClearData01.clearDiscountData01("at");
		});
	}

	private void scenario12() {
		Given("[整单折扣计算12]当前存在如下客户信息", (DataTable dataTable) -> {
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
		When("[整单折扣计算12]当前存在如下的预折商品信息", (DataTable dataTable) -> {
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
		And("[整单折扣计算12]当前存在如下的有效预折折扣信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String effectiveState = Optional.ofNullable(caseData.get("effectiveState")).orElse("");
				orderBatchUpdateData01.updateOrderPreDiscountDetailEffectiveState(OrderPreDiscountDetailDTO.builder().effectiveState(effectiveState).itemSapCode(itemSapCode).build(), OrderPreDiscountDTO.builder().customerSapCode(customerSapCode).build());
			});
		});
		And("[整单折扣计算12]当前存在如下的[纯赠]整单折扣申请单信息", (DataTable dataTable) -> {
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
		And("[整单折扣计算12]当前存在如下的[纯赠]整单折扣申请单商品信息", (DataTable dataTable) -> {
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
		And("[整单折扣计算12]当前存在如下的[纯赠]整单折扣分配计划信息", () -> {
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
		And("[整单折扣计算12]当前存在如下的[纯赠]整单折扣分配计划详细信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				allocationPlanDetailId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
				cz_allocationPlanDetailId = allocationPlanDetailId;
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
						.createPerson("at")
						.createTime(createTime)
						.build());
			});
		});
		And("[整单折扣计算12]当前存在如下的[零头药]整单折扣申请单信息", (DataTable dataTable) -> {
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
		And("[整单折扣计算12]当前存在如下的[零头药]整单折扣申请单商品信息", (DataTable dataTable) -> {
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
		And("[整单折扣计算12]当前存在如下的[零头药]整单折扣分配计划信息", () -> {
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
		And("[整单折扣计算12]当前存在如下的[零头药]整单折扣分配计划详细信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				allocationPlanDetailId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
				lty_allocationPlanDetailId = allocationPlanDetailId;
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
				String lotNo = Optional.ofNullable(caseData.get("lot_no")).orElse("");
				String production_date = Optional.ofNullable(caseData.get("production_date")).orElse("");
				String expiration_date = Optional.ofNullable(caseData.get("expiration_date")).orElse("");
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
						.createPerson("at")
						.createTime(createTime)
						.build());
			});
		});
		And("[整单折扣计算12]添加商品", (DataTable dataTable) -> {
			log.info("添加商品");
		});
		And("[整单折扣计算12]选择项目", (DataTable dataTable) -> {
			log.info("选择项目");
		});
		Then("[整单折扣计算12]进行整单折扣计算后,订单行分摊如下", (DataTable dataTable) -> {
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
				String calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(2, RoundingMode.HALF_UP).toString();
				String totalInline = new BigDecimal(caseData.get("totalInline")).setScale(2, RoundingMode.HALF_UP).toString();
				String preDiscountTotal = new BigDecimal(caseData.get("preDiscountTotal")).setScale(2, RoundingMode.HALF_UP).toString();
				String orderDiscountTotal = new BigDecimal(caseData.get("orderDiscountTotal")).setScale(2, RoundingMode.HALF_UP).toString();
				if ("PT04".equals(orderWideDiscountSubject)) {
					allocationPlanDetailId = cz_allocationPlanDetailId;
				} else if ("PT02".equals(orderWideDiscountSubject)) {
					allocationPlanDetailId = lty_allocationPlanDetailId;
				} else if ("PT01".equals(orderWideDiscountSubject)) {
					allocationPlanDetailId = mz_allocationPlanDetailId;
				} else {
					allocationPlanDetailId = "";
				}
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
			discountCalcResponse.then().body("resultCode", Matchers.equalTo("0"), "resultMsg", Matchers.equalTo("success"));
			orderCheckOrderManagement01.checkOrderDiscountCalc(dataTable, discountCalcResponse);
			priceBatchClearData01.clearPirceData01("at");
			orderBatchClearData01.clearOrderPreDiscountData01("at");
			orderBatchClearData01.clearDiscountData01("at");
		});
	}


	private void scenario13() {
		Given("[整单折扣计算13]当前存在如下客户信息", (DataTable dataTable) -> {
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
		When("[整单折扣计算13]当前存在如下的预折商品信息", (DataTable dataTable) -> {
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
		And("[整单折扣计算13]当前存在如下的预折折扣信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				BigDecimal calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(8, RoundingMode.HALF_UP);
				String effectiveState = Optional.ofNullable(caseData.get("effectiveState")).orElse("");
				orderBatchUpdateData01.updateOrderPreDiscountDetailCalculationValue(OrderPreDiscountDetailDTO.builder().effectiveState(effectiveState).calculationValue(calculationValue).itemSapCode(itemSapCode).build(), OrderPreDiscountDTO.builder().customerSapCode(customerSapCode).build());
			});
		});
		And("[整单折扣计算13]当前存在如下的[纯赠]整单折扣申请单信息", (DataTable dataTable) -> {
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
		And("[整单折扣计算13]当前存在如下的[纯赠]整单折扣申请单商品信息", (DataTable dataTable) -> {
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
		And("[整单折扣计算13]当前存在如下的[纯赠]整单折扣分配计划信息", () -> {
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
		And("[整单折扣计算13]当前存在如下的[纯赠]整单折扣分配计划详细信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				allocationPlanDetailId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
				cz_allocationPlanDetailId = allocationPlanDetailId;
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
						.createPerson("at")
						.createTime(createTime)
						.build());
			});
		});
		And("[整单折扣计算13]当前存在如下的[零头药]整单折扣申请单信息", (DataTable dataTable) -> {
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
		And("[整单折扣计算13]当前存在如下的[零头药]整单折扣申请单商品信息", (DataTable dataTable) -> {
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
		And("[整单折扣计算13]当前存在如下的[零头药]整单折扣分配计划信息", () -> {
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
		And("[整单折扣计算13]当前存在如下的[零头药]整单折扣分配计划详细信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				allocationPlanDetailId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
				lty_allocationPlanDetailId = allocationPlanDetailId;
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
				String lotNo = Optional.ofNullable(caseData.get("lot_no")).orElse("");
				String production_date = Optional.ofNullable(caseData.get("production_date")).orElse("");
				String expiration_date = Optional.ofNullable(caseData.get("expiration_date")).orElse("");
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
						.createPerson("at")
						.createTime(createTime)
						.build());
			});
		});
		And("[整单折扣计算13]添加商品", (DataTable dataTable) -> {
			log.info("添加商品");
		});
		And("[整单折扣计算13]选择项目", (DataTable dataTable) -> {
			log.info("选择项目");
		});
		Then("[整单折扣计算13]进行整单折扣计算后,订单行分摊如下", (DataTable dataTable) -> {
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
				String calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(2, RoundingMode.HALF_UP).toString();
				String totalInline = new BigDecimal(caseData.get("totalInline")).setScale(2, RoundingMode.HALF_UP).toString();
				String preDiscountTotal = new BigDecimal(caseData.get("preDiscountTotal")).setScale(2, RoundingMode.HALF_UP).toString();
				String orderDiscountTotal = new BigDecimal(caseData.get("orderDiscountTotal")).setScale(2, RoundingMode.HALF_UP).toString();
				if ("PT04".equals(orderWideDiscountSubject)) {
					allocationPlanDetailId = cz_allocationPlanDetailId;
				} else if ("PT02".equals(orderWideDiscountSubject)) {
					allocationPlanDetailId = lty_allocationPlanDetailId;
				} else if ("PT01".equals(orderWideDiscountSubject)) {
					allocationPlanDetailId = mz_allocationPlanDetailId;
				} else {
					allocationPlanDetailId = "";
				}
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
			discountCalcResponse.then().body("resultCode", Matchers.equalTo("0"), "resultMsg", Matchers.equalTo("success"));
			orderCheckOrderManagement01.checkOrderDiscountCalc(dataTable, discountCalcResponse);
			priceBatchClearData01.clearPirceData01("at");
			orderBatchClearData01.clearOrderPreDiscountData01("at");
			orderBatchClearData01.clearDiscountData01("at");
		});
	}

	private void scenario14() {
		Given("[整单折扣计算14]当前存在如下客户信息", (DataTable dataTable) -> {
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
		When("[整单折扣计算14]当前存在如下的预折商品信息", (DataTable dataTable) -> {
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
		And("[整单折扣计算14]当前存在如下的预折折扣信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				BigDecimal calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(8, RoundingMode.HALF_UP);
				String effectiveState = Optional.ofNullable(caseData.get("effectiveState")).orElse("");
				orderBatchUpdateData01.updateOrderPreDiscountDetailCalculationValue(OrderPreDiscountDetailDTO.builder().effectiveState(effectiveState).calculationValue(calculationValue).itemSapCode(itemSapCode).build(), OrderPreDiscountDTO.builder().customerSapCode(customerSapCode).build());
			});
		});
		And("[整单折扣计算14]当前存在如下的[纯赠]整单折扣申请单信息", (DataTable dataTable) -> {
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
		And("[整单折扣计算14]当前存在如下的[纯赠]整单折扣申请单商品信息", (DataTable dataTable) -> {
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
		And("[整单折扣计算14]当前存在如下的[纯赠]整单折扣分配计划信息", () -> {
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
		And("[整单折扣计算14]当前存在如下的[纯赠]整单折扣分配计划详细信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				allocationPlanDetailId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
				cz_allocationPlanDetailId = allocationPlanDetailId;
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
						.createPerson("at")
						.createTime(createTime)
						.build());
			});
		});
		And("[整单折扣计算14]当前存在如下的[零头药]整单折扣申请单信息", (DataTable dataTable) -> {
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
		And("[整单折扣计算14]当前存在如下的[零头药]整单折扣申请单商品信息", (DataTable dataTable) -> {
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
		And("[整单折扣计算14]当前存在如下的[零头药]整单折扣分配计划信息", () -> {
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
		And("[整单折扣计算14]当前存在如下的[零头药]整单折扣分配计划详细信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				allocationPlanDetailId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
				lty_allocationPlanDetailId = allocationPlanDetailId;
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
				String lotNo = Optional.ofNullable(caseData.get("lot_no")).orElse("");
				String production_date = Optional.ofNullable(caseData.get("production_date")).orElse("");
				String expiration_date = Optional.ofNullable(caseData.get("expiration_date")).orElse("");
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
						.createPerson("at")
						.createTime(createTime)
						.build());
			});
		});
		And("[整单折扣计算14]添加商品", (DataTable dataTable) -> {
			log.info("添加商品");
		});
		And("[整单折扣计算14]选择项目", (DataTable dataTable) -> {
			log.info("选择项目");
		});
		Then("[整单折扣计算14]进行整单折扣计算后,订单行分摊如下", (DataTable dataTable) -> {
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
				String calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(2, RoundingMode.HALF_UP).toString();
				String totalInline = new BigDecimal(caseData.get("totalInline")).setScale(2, RoundingMode.HALF_UP).toString();
				String preDiscountTotal = new BigDecimal(caseData.get("preDiscountTotal")).setScale(2, RoundingMode.HALF_UP).toString();
				String orderDiscountTotal = new BigDecimal(caseData.get("orderDiscountTotal")).setScale(2, RoundingMode.HALF_UP).toString();
				if ("PT04".equals(orderWideDiscountSubject)) {
					allocationPlanDetailId = cz_allocationPlanDetailId;
				} else if ("PT02".equals(orderWideDiscountSubject)) {
					allocationPlanDetailId = lty_allocationPlanDetailId;
				} else if ("PT01".equals(orderWideDiscountSubject)) {
					allocationPlanDetailId = mz_allocationPlanDetailId;
				} else {
					allocationPlanDetailId = "";
				}
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
			discountCalcResponse.then().body("resultCode", Matchers.equalTo("0"), "resultMsg", Matchers.equalTo("success"));
			orderCheckOrderManagement01.checkOrderDiscountCalc(dataTable, discountCalcResponse);
			priceBatchClearData01.clearPirceData01("at");
			orderBatchClearData01.clearOrderPreDiscountData01("at");
			orderBatchClearData01.clearDiscountData01("at");
		});
	}

	private void scenario15() {
		Given("[整单折扣计算15]当前存在如下客户信息", (DataTable dataTable) -> {
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
		When("[整单折扣计算15]当前存在如下的预折商品信息", (DataTable dataTable) -> {
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
		And("[整单折扣计算15]当前存在如下的预折折扣信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				BigDecimal calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(8, RoundingMode.HALF_UP);
				String effectiveState = Optional.ofNullable(caseData.get("effectiveState")).orElse("");
				orderBatchUpdateData01.updateOrderPreDiscountDetailCalculationValue(OrderPreDiscountDetailDTO.builder().effectiveState(effectiveState).calculationValue(calculationValue).itemSapCode(itemSapCode).build(), OrderPreDiscountDTO.builder().customerSapCode(customerSapCode).build());
			});
		});
		And("[整单折扣计算15]当前存在如下的[纯赠]整单折扣申请单信息", (DataTable dataTable) -> {
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
		And("[整单折扣计算15]当前存在如下的[纯赠]整单折扣申请单商品信息", (DataTable dataTable) -> {
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
		And("[整单折扣计算15]当前存在如下的[纯赠]整单折扣分配计划信息", () -> {
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
		And("[整单折扣计算15]当前存在如下的[纯赠]整单折扣分配计划详细信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				allocationPlanDetailId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
				cz_allocationPlanDetailId = allocationPlanDetailId;
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
						.createPerson("at")
						.createTime(createTime)
						.build());
			});
		});
		And("[整单折扣计算15]当前存在如下的[零头药]整单折扣申请单信息", (DataTable dataTable) -> {
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
		And("[整单折扣计算15]当前存在如下的[零头药]整单折扣申请单商品信息", (DataTable dataTable) -> {
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
		And("[整单折扣计算15]当前存在如下的[零头药]整单折扣分配计划信息", () -> {
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
		And("[整单折扣计算15]当前存在如下的[零头药]整单折扣分配计划详细信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				allocationPlanDetailId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
				lty_allocationPlanDetailId = allocationPlanDetailId;
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
				String lotNo = Optional.ofNullable(caseData.get("lot_no")).orElse("");
				String production_date = Optional.ofNullable(caseData.get("production_date")).orElse("");
				String expiration_date = Optional.ofNullable(caseData.get("expiration_date")).orElse("");
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
						.createPerson("at")
						.createTime(createTime)
						.build());
			});
		});
		And("[整单折扣计算15]添加商品", (DataTable dataTable) -> {
			log.info("添加商品");
		});
		And("[整单折扣计算15]选择项目", (DataTable dataTable) -> {
			log.info("选择项目");
		});
		Then("[整单折扣计算15]进行整单折扣计算后,订单行分摊如下", (DataTable dataTable) -> {
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
				String calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(2, RoundingMode.HALF_UP).toString();
				String totalInline = new BigDecimal(caseData.get("totalInline")).setScale(2, RoundingMode.HALF_UP).toString();
				String preDiscountTotal = new BigDecimal(caseData.get("preDiscountTotal")).setScale(2, RoundingMode.HALF_UP).toString();
				String orderDiscountTotal = new BigDecimal(caseData.get("orderDiscountTotal")).setScale(2, RoundingMode.HALF_UP).toString();
				if ("PT04".equals(orderWideDiscountSubject)) {
					allocationPlanDetailId = cz_allocationPlanDetailId;
				} else if ("PT02".equals(orderWideDiscountSubject)) {
					allocationPlanDetailId = lty_allocationPlanDetailId;
				} else if ("PT01".equals(orderWideDiscountSubject)) {
					allocationPlanDetailId = mz_allocationPlanDetailId;
				} else {
					allocationPlanDetailId = "";
				}
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
			discountCalcResponse.then().body("resultCode", Matchers.equalTo("0"), "resultMsg", Matchers.equalTo("success"));
			orderCheckOrderManagement01.checkOrderDiscountCalc(dataTable, discountCalcResponse);
			priceBatchClearData01.clearPirceData01("at");
			orderBatchClearData01.clearOrderPreDiscountData01("at");
			orderBatchClearData01.clearDiscountData01("at");
		});
	}

	private void scenario16() {
		Given("[整单折扣计算16]当前存在如下客户信息", (DataTable dataTable) -> {
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
		When("[整单折扣计算16]当前存在如下的预折商品信息", (DataTable dataTable) -> {
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
		And("[整单折扣计算16]当前存在如下的预折折扣信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				BigDecimal calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(8, RoundingMode.HALF_UP);
				String effectiveState = Optional.ofNullable(caseData.get("effectiveState")).orElse("");
				orderBatchUpdateData01.updateOrderPreDiscountDetailCalculationValue(OrderPreDiscountDetailDTO.builder().effectiveState(effectiveState).calculationValue(calculationValue).itemSapCode(itemSapCode).build(), OrderPreDiscountDTO.builder().customerSapCode(customerSapCode).build());
			});
		});
		And("[整单折扣计算16]当前存在如下的[纯赠]整单折扣申请单信息", (DataTable dataTable) -> {
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
		And("[整单折扣计算16]当前存在如下的[纯赠]整单折扣申请单商品信息", (DataTable dataTable) -> {
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
		And("[整单折扣计算16]当前存在如下的[纯赠]整单折扣分配计划信息", () -> {
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
		And("[整单折扣计算16]当前存在如下的[纯赠]整单折扣分配计划详细信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				allocationPlanDetailId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
				cz_allocationPlanDetailId = allocationPlanDetailId;
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
						.createPerson("at")
						.createTime(createTime)
						.build());
			});
		});
		And("[整单折扣计算16]当前存在如下的[零头药]整单折扣申请单信息", (DataTable dataTable) -> {
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
		And("[整单折扣计算16]当前存在如下的[零头药]整单折扣申请单商品信息", (DataTable dataTable) -> {
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
		And("[整单折扣计算16]当前存在如下的[零头药]整单折扣分配计划信息", () -> {
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
		And("[整单折扣计算16]当前存在如下的[零头药]整单折扣分配计划详细信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				allocationPlanDetailId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
				lty_allocationPlanDetailId = allocationPlanDetailId;
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
				String lotNo = Optional.ofNullable(caseData.get("lot_no")).orElse("");
				String production_date = Optional.ofNullable(caseData.get("production_date")).orElse("");
				String expiration_date = Optional.ofNullable(caseData.get("expiration_date")).orElse("");
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
						.createPerson("at")
						.createTime(createTime)
						.build());
			});
		});
		And("[整单折扣计算16]添加商品", (DataTable dataTable) -> {
			log.info("添加商品");
		});
		And("[整单折扣计算16]选择项目", (DataTable dataTable) -> {
			log.info("选择项目");
		});
		Then("[整单折扣计算16]进行整单折扣计算后,订单行分摊如下", (DataTable dataTable) -> {
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
				String calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(2, RoundingMode.HALF_UP).toString();
				String totalInline = new BigDecimal(caseData.get("totalInline")).setScale(2, RoundingMode.HALF_UP).toString();
				String preDiscountTotal = new BigDecimal(caseData.get("preDiscountTotal")).setScale(2, RoundingMode.HALF_UP).toString();
				String orderDiscountTotal = new BigDecimal(caseData.get("orderDiscountTotal")).setScale(2, RoundingMode.HALF_UP).toString();
				if ("PT04".equals(orderWideDiscountSubject)) {
					allocationPlanDetailId = cz_allocationPlanDetailId;
				} else if ("PT02".equals(orderWideDiscountSubject)) {
					allocationPlanDetailId = lty_allocationPlanDetailId;
				} else if ("PT01".equals(orderWideDiscountSubject)) {
					allocationPlanDetailId = mz_allocationPlanDetailId;
				} else {
					allocationPlanDetailId = "";
				}
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
			discountCalcResponse.then().body("resultCode", Matchers.equalTo("0"), "resultMsg", Matchers.equalTo("success"));
			orderCheckOrderManagement01.checkOrderDiscountCalc(dataTable, discountCalcResponse);
			priceBatchClearData01.clearPirceData01("at");
			orderBatchClearData01.clearOrderPreDiscountData01("at");
			orderBatchClearData01.clearDiscountData01("at");
		});
	}


	private void scenario17() {
		Given("[整单折扣计算17]当前存在如下客户信息", (DataTable dataTable) -> {
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
		When("[整单折扣计算17]当前存在如下的预折商品信息", (DataTable dataTable) -> {
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
		And("[整单折扣计算17]当前存在如下的有效预折折扣信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String effectiveState = Optional.ofNullable(caseData.get("effectiveState")).orElse("");
				orderBatchUpdateData01.updateOrderPreDiscountDetailEffectiveState(OrderPreDiscountDetailDTO.builder().effectiveState(effectiveState).itemSapCode(itemSapCode).build(), OrderPreDiscountDTO.builder().customerSapCode(customerSapCode).build());
			});
		});
		And("[整单折扣计算17]当前存在如下的整单折扣申请单信息", (DataTable dataTable) -> {
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
		And("[整单折扣计算17]当前存在如下的整单折扣申请单商品信息", (DataTable dataTable) -> {
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
		And("[整单折扣计算17]当前存在如下的整单折扣分配计划信息", () -> {
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
		And("[整单折扣计算17]当前存在如下的整单折扣分配计划详细信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				allocationPlanDetailId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
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
						.createPerson("at")
						.createTime(createTime)
						.build());
			});
		});
		And("[整单折扣计算17]添加商品", (DataTable dataTable) -> {
			log.info("添加商品");
		});
		And("[整单折扣计算17]选择项目", (DataTable dataTable) -> {
			log.info("选择项目");
		});
		Then("[整单折扣计算17]进行整单折扣计算后,订单行分摊如下", (DataTable dataTable) -> {
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
				String calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(2, RoundingMode.HALF_UP).toString();
				String totalInline = new BigDecimal(caseData.get("totalInline")).setScale(2, RoundingMode.HALF_UP).toString();
				String preDiscountTotal = new BigDecimal(caseData.get("preDiscountTotal")).setScale(2, RoundingMode.HALF_UP).toString();
				String orderDiscountTotal = new BigDecimal(caseData.get("orderDiscountTotal")).setScale(2, RoundingMode.HALF_UP).toString();
				if ("PT04".equals(orderWideDiscountSubject)) {
					allocationPlanDetailId = cz_allocationPlanDetailId;
				} else if ("PT02".equals(orderWideDiscountSubject)) {
					allocationPlanDetailId = lty_allocationPlanDetailId;
				} else if ("PT01".equals(orderWideDiscountSubject)) {
					allocationPlanDetailId = mz_allocationPlanDetailId;
				} else {
					allocationPlanDetailId = "";
				}
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
			discountCalcResponse.then().body("resultCode", Matchers.equalTo("0"), "resultMsg", Matchers.equalTo("success"));
			orderCheckOrderManagement01.checkOrderDiscountCalc(dataTable, discountCalcResponse);
			priceBatchClearData01.clearPirceData01("at");
			orderBatchClearData01.clearOrderPreDiscountData01("at");
			orderBatchClearData01.clearDiscountData01("at");
		});
	}


	private void scenario18() {
		Given("[整单折扣计算18]当前存在如下客户信息", (DataTable dataTable) -> {
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
		When("[整单折扣计算18]当前存在如下的预折商品信息", (DataTable dataTable) -> {
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
		And("[整单折扣计算18]当前存在如下的有效预折折扣信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String effectiveState = Optional.ofNullable(caseData.get("effectiveState")).orElse("");
				orderBatchUpdateData01.updateOrderPreDiscountDetailEffectiveState(OrderPreDiscountDetailDTO.builder().effectiveState(effectiveState).itemSapCode(itemSapCode).build(), OrderPreDiscountDTO.builder().customerSapCode(customerSapCode).build());
			});
		});
		And("[整单折扣计算18]当前存在如下的[买赠]整单折扣申请单信息", (DataTable dataTable) -> {
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
		And("[整单折扣计算18]当前存在如下的[买赠]整单折扣申请单商品信息", (DataTable dataTable) -> {
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
		And("[整单折扣计算18]当前存在如下的[买赠]整单折扣分配计划信息", () -> {
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
		And("[整单折扣计算18]当前存在如下的[买赠]整单折扣分配计划详细信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				allocationPlanDetailId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
				mz_allocationPlanDetailId = allocationPlanDetailId;
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
						.createPerson("at")
						.createTime(createTime)
						.build());
			});
		});
		And("[整单折扣计算18]当前存在如下的[纯赠]整单折扣申请单信息", (DataTable dataTable) -> {
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
		And("[整单折扣计算18]当前存在如下的[纯赠]整单折扣申请单商品信息", (DataTable dataTable) -> {
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
		And("[整单折扣计算18]当前存在如下的[纯赠]整单折扣分配计划信息", () -> {
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
		And("[整单折扣计算18]当前存在如下的[纯赠]整单折扣分配计划详细信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				allocationPlanDetailId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
				cz_allocationPlanDetailId = allocationPlanDetailId;
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
						.createPerson("at")
						.createTime(createTime)
						.build());
			});
		});
		And("[整单折扣计算18]当前存在如下的[零头药]整单折扣申请单信息", (DataTable dataTable) -> {
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
		And("[整单折扣计算18]当前存在如下的[零头药]整单折扣申请单商品信息", (DataTable dataTable) -> {
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
		And("[整单折扣计算18]当前存在如下的[零头药]整单折扣分配计划信息", () -> {
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
		And("[整单折扣计算18]当前存在如下的[零头药]整单折扣分配计划详细信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				allocationPlanDetailId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
				lty_allocationPlanDetailId = allocationPlanDetailId;
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
				String lotNo = Optional.ofNullable(caseData.get("lot_no")).orElse("");
				String production_date = Optional.ofNullable(caseData.get("production_date")).orElse("");
				String expiration_date = Optional.ofNullable(caseData.get("expiration_date")).orElse("");
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
						.createPerson("at")
						.createTime(createTime)
						.build());
			});
		});
		And("[整单折扣计算18]添加商品", (DataTable dataTable) -> {
			log.info("添加商品");
		});
		And("[整单折扣计算18]选择项目", (DataTable dataTable) -> {
			log.info("选择项目");
		});
		Then("[整单折扣计算18]进行整单折扣计算后,订单行分摊如下", (DataTable dataTable) -> {
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
				String calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(2, RoundingMode.HALF_UP).toString();
				String totalInline = new BigDecimal(caseData.get("totalInline")).setScale(2, RoundingMode.HALF_UP).toString();
				String preDiscountTotal = new BigDecimal(caseData.get("preDiscountTotal")).setScale(2, RoundingMode.HALF_UP).toString();
				String orderDiscountTotal = new BigDecimal(caseData.get("orderDiscountTotal")).setScale(2, RoundingMode.HALF_UP).toString();
				if ("PT04".equals(orderWideDiscountSubject)) {
					allocationPlanDetailId = cz_allocationPlanDetailId;
				} else if ("PT02".equals(orderWideDiscountSubject)) {
					allocationPlanDetailId = lty_allocationPlanDetailId;
				} else if ("PT01".equals(orderWideDiscountSubject)) {
					allocationPlanDetailId = mz_allocationPlanDetailId;
				} else {
					allocationPlanDetailId = "";
				}
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
			discountCalcResponse.then().body("resultCode", Matchers.equalTo("0"), "resultMsg", Matchers.equalTo("success"));
			orderCheckOrderManagement01.checkOrderDiscountCalc(dataTable, discountCalcResponse);
			priceBatchClearData01.clearPirceData01("at");
			orderBatchClearData01.clearOrderPreDiscountData01("at");
			orderBatchClearData01.clearDiscountData01("at");
		});
	}


	private void scenario19() {
		Given("[整单折扣计算19]当前存在如下客户信息", (DataTable dataTable) -> {
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
		When("[整单折扣计算19]当前存在如下的预折商品信息", (DataTable dataTable) -> {
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
		And("[整单折扣计算19]当前存在如下的预折折扣信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				BigDecimal calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(8, RoundingMode.HALF_UP);
				String effectiveState = Optional.ofNullable(caseData.get("effectiveState")).orElse("");
				orderBatchUpdateData01.updateOrderPreDiscountDetailCalculationValue(OrderPreDiscountDetailDTO.builder().effectiveState(effectiveState).calculationValue(calculationValue).itemSapCode(itemSapCode).build(), OrderPreDiscountDTO.builder().customerSapCode(customerSapCode).build());
			});
		});
		And("[整单折扣计算19]当前存在如下的整单折扣申请单信息", (DataTable dataTable) -> {
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
		And("[整单折扣计算19]当前存在如下的整单折扣申请单商品信息", (DataTable dataTable) -> {
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
		And("[整单折扣计算19]当前存在如下的整单折扣分配计划信息", () -> {
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
		And("[整单折扣计算19]当前存在如下的整单折扣分配计划详细信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				allocationPlanDetailId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
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
						.createPerson("at")
						.createTime(createTime)
						.build());
			});
		});
		And("[整单折扣计算19]添加商品", (DataTable dataTable) -> {
			log.info("添加商品");
		});
		And("[整单折扣计算19]选择项目", (DataTable dataTable) -> {
			log.info("选择项目");
		});
		Then("[整单折扣计算19]进行整单折扣计算后,订单行分摊如下", (DataTable dataTable) -> {
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
				String calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(2, RoundingMode.HALF_UP).toString();
				String totalInline = new BigDecimal(caseData.get("totalInline")).setScale(2, RoundingMode.HALF_UP).toString();
				String preDiscountTotal = new BigDecimal(caseData.get("preDiscountTotal")).setScale(2, RoundingMode.HALF_UP).toString();
				String orderDiscountTotal = new BigDecimal(caseData.get("orderDiscountTotal")).setScale(2, RoundingMode.HALF_UP).toString();
				if ("PT04".equals(orderWideDiscountSubject)) {
					allocationPlanDetailId = cz_allocationPlanDetailId;
				} else if ("PT02".equals(orderWideDiscountSubject)) {
					allocationPlanDetailId = lty_allocationPlanDetailId;
				} else if ("PT01".equals(orderWideDiscountSubject)) {
					allocationPlanDetailId = mz_allocationPlanDetailId;
				} else {
					allocationPlanDetailId = "";
				}
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
			discountCalcResponse.then().body("resultCode", Matchers.equalTo("0"), "resultMsg", Matchers.equalTo("success"));
			orderCheckOrderManagement01.checkOrderDiscountCalc(dataTable, discountCalcResponse);
			priceBatchClearData01.clearPirceData01("at");
			orderBatchClearData01.clearOrderPreDiscountData01("at");
			orderBatchClearData01.clearDiscountData01("at");
		});
	}


	private void scenario20() {
		Given("[整单折扣计算20]当前存在如下客户信息", (DataTable dataTable) -> {
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
		When("[整单折扣计算20]当前存在如下的预折商品信息", (DataTable dataTable) -> {
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
		And("[整单折扣计算20]当前存在如下的预折折扣信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				BigDecimal calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(8, RoundingMode.HALF_UP);
				String effectiveState = Optional.ofNullable(caseData.get("effectiveState")).orElse("");
				orderBatchUpdateData01.updateOrderPreDiscountDetailCalculationValue(OrderPreDiscountDetailDTO.builder().effectiveState(effectiveState).calculationValue(calculationValue).itemSapCode(itemSapCode).build(), OrderPreDiscountDTO.builder().customerSapCode(customerSapCode).build());
			});
		});
		And("[整单折扣计算20]当前存在如下的[买赠]整单折扣申请单信息", (DataTable dataTable) -> {
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
		And("[整单折扣计算20]当前存在如下的[买赠]整单折扣申请单商品信息", (DataTable dataTable) -> {
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
		And("[整单折扣计算20]当前存在如下的[买赠]整单折扣分配计划信息", () -> {
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
		And("[整单折扣计算20]当前存在如下的[买赠]整单折扣分配计划详细信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				allocationPlanDetailId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
				mz_allocationPlanDetailId = allocationPlanDetailId;
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
						.createPerson("at")
						.createTime(createTime)
						.build());
			});
		});
		And("[整单折扣计算20]当前存在如下的[纯赠]整单折扣申请单信息", (DataTable dataTable) -> {
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
		And("[整单折扣计算20]当前存在如下的[纯赠]整单折扣申请单商品信息", (DataTable dataTable) -> {
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
		And("[整单折扣计算20]当前存在如下的[纯赠]整单折扣分配计划信息", () -> {
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
		And("[整单折扣计算20]当前存在如下的[纯赠]整单折扣分配计划详细信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				allocationPlanDetailId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
				cz_allocationPlanDetailId = allocationPlanDetailId;
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
						.createPerson("at")
						.createTime(createTime)
						.build());
			});
		});
		And("[整单折扣计算20]当前存在如下的[零头药]整单折扣申请单信息", (DataTable dataTable) -> {
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
		And("[整单折扣计算20]当前存在如下的[零头药]整单折扣申请单商品信息", (DataTable dataTable) -> {
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
		And("[整单折扣计算20]当前存在如下的[零头药]整单折扣分配计划信息", () -> {
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
		And("[整单折扣计算20]当前存在如下的[零头药]整单折扣分配计划详细信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				allocationPlanDetailId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
				lty_allocationPlanDetailId = allocationPlanDetailId;
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
				String lotNo = Optional.ofNullable(caseData.get("lot_no")).orElse("");
				String production_date = Optional.ofNullable(caseData.get("production_date")).orElse("");
				String expiration_date = Optional.ofNullable(caseData.get("expiration_date")).orElse("");
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
						.createPerson("at")
						.createTime(createTime)
						.build());
			});
		});
		And("[整单折扣计算20]添加商品", (DataTable dataTable) -> {
			log.info("添加商品");
		});
		And("[整单折扣计算20]选择项目", (DataTable dataTable) -> {
			log.info("选择项目");
		});
		Then("[整单折扣计算20]进行整单折扣计算后,订单行分摊如下", (DataTable dataTable) -> {
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
				String calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(2, RoundingMode.HALF_UP).toString();
				String totalInline = new BigDecimal(caseData.get("totalInline")).setScale(2, RoundingMode.HALF_UP).toString();
				String preDiscountTotal = new BigDecimal(caseData.get("preDiscountTotal")).setScale(2, RoundingMode.HALF_UP).toString();
				String orderDiscountTotal = new BigDecimal(caseData.get("orderDiscountTotal")).setScale(2, RoundingMode.HALF_UP).toString();
				if ("PT04".equals(orderWideDiscountSubject)) {
					allocationPlanDetailId = cz_allocationPlanDetailId;
				} else if ("PT02".equals(orderWideDiscountSubject)) {
					allocationPlanDetailId = lty_allocationPlanDetailId;
				} else if ("PT01".equals(orderWideDiscountSubject)) {
					allocationPlanDetailId = mz_allocationPlanDetailId;
				} else {
					allocationPlanDetailId = "";
				}
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
			discountCalcResponse.then().body("resultCode", Matchers.equalTo("0"), "resultMsg", Matchers.equalTo("success"));
			orderCheckOrderManagement01.checkOrderDiscountCalc(dataTable, discountCalcResponse);
			priceBatchClearData01.clearPirceData01("at");
			orderBatchClearData01.clearOrderPreDiscountData01("at");
			orderBatchClearData01.clearDiscountData01("at");
		});
	}


	private void scenario21() {
		Given("[整单折扣计算21]当前存在如下客户信息", (DataTable dataTable) -> {
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
		When("[整单折扣计算21]当前存在如下的预折商品信息", (DataTable dataTable) -> {
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
		And("[整单折扣计算21]当前存在如下的预折折扣信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				BigDecimal calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(8, RoundingMode.HALF_UP);
				String effectiveState = Optional.ofNullable(caseData.get("effectiveState")).orElse("");
				orderBatchUpdateData01.updateOrderPreDiscountDetailCalculationValue(OrderPreDiscountDetailDTO.builder().effectiveState(effectiveState).calculationValue(calculationValue).itemSapCode(itemSapCode).build(), OrderPreDiscountDTO.builder().customerSapCode(customerSapCode).build());
			});
		});
		And("[整单折扣计算21]当前存在如下的整单折扣申请单信息", (DataTable dataTable) -> {
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
		And("[整单折扣计算21]当前存在如下的整单折扣申请单商品信息", (DataTable dataTable) -> {
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
		And("[整单折扣计算21]当前存在如下的整单折扣分配计划信息", () -> {
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
		And("[整单折扣计算21]当前存在如下的整单折扣分配计划详细信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				allocationPlanDetailId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
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
						.createPerson("at")
						.createTime(createTime)
						.build());
			});
		});
		And("[整单折扣计算21]添加商品", (DataTable dataTable) -> {
			log.info("添加商品");
		});
		And("[整单折扣计算21]选择项目", (DataTable dataTable) -> {
			log.info("选择项目");
		});
		Then("[整单折扣计算21]进行整单折扣计算后,订单行分摊如下", (DataTable dataTable) -> {
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
				String calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(2, RoundingMode.HALF_UP).toString();
				String totalInline = new BigDecimal(caseData.get("totalInline")).setScale(2, RoundingMode.HALF_UP).toString();
				String preDiscountTotal = new BigDecimal(caseData.get("preDiscountTotal")).setScale(2, RoundingMode.HALF_UP).toString();
				String orderDiscountTotal = new BigDecimal(caseData.get("orderDiscountTotal")).setScale(2, RoundingMode.HALF_UP).toString();
				if ("PT04".equals(orderWideDiscountSubject)) {
					allocationPlanDetailId = cz_allocationPlanDetailId;
				} else if ("PT02".equals(orderWideDiscountSubject)) {
					allocationPlanDetailId = lty_allocationPlanDetailId;
				} else if ("PT01".equals(orderWideDiscountSubject)) {
					allocationPlanDetailId = mz_allocationPlanDetailId;
				} else {
					allocationPlanDetailId = "";
				}
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
			discountCalcResponse.then().body("resultCode", Matchers.equalTo("0"), "resultMsg", Matchers.equalTo("success"));
			orderCheckOrderManagement01.checkOrderDiscountCalc(dataTable, discountCalcResponse);
			priceBatchClearData01.clearPirceData01("at");
			orderBatchClearData01.clearOrderPreDiscountData01("at");
			orderBatchClearData01.clearDiscountData01("at");
		});
	}


	private void scenario22() {
		Given("[整单折扣计算22]当前存在如下客户信息", (DataTable dataTable) -> {
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
		When("[整单折扣计算22]当前存在如下的预折商品信息", (DataTable dataTable) -> {
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
		And("[整单折扣计算22]当前存在如下的预折折扣信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				BigDecimal calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(8, RoundingMode.HALF_UP);
				String effectiveState = Optional.ofNullable(caseData.get("effectiveState")).orElse("");
				orderBatchUpdateData01.updateOrderPreDiscountDetailCalculationValue(OrderPreDiscountDetailDTO.builder().effectiveState(effectiveState).calculationValue(calculationValue).itemSapCode(itemSapCode).build(), OrderPreDiscountDTO.builder().customerSapCode(customerSapCode).build());
			});
		});
		And("[整单折扣计算22]当前存在如下的整单折扣申请单信息", (DataTable dataTable) -> {
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
		And("[整单折扣计算22]当前存在如下的整单折扣申请单商品信息", (DataTable dataTable) -> {
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
		And("[整单折扣计算22]当前存在如下的整单折扣分配计划信息", () -> {
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
		And("[整单折扣计算22]当前存在如下的整单折扣分配计划详细信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				allocationPlanDetailId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
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
						.createPerson("at")
						.createTime(createTime)
						.build());
			});
		});
		And("[整单折扣计算22]添加商品", (DataTable dataTable) -> {
			log.info("添加商品");
		});
		And("[整单折扣计算22]选择项目", (DataTable dataTable) -> {
			log.info("选择项目");
		});
		Then("[整单折扣计算22]进行整单折扣计算后,订单行分摊如下", (DataTable dataTable) -> {
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
				String calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(2, RoundingMode.HALF_UP).toString();
				String totalInline = new BigDecimal(caseData.get("totalInline")).setScale(2, RoundingMode.HALF_UP).toString();
				String preDiscountTotal = new BigDecimal(caseData.get("preDiscountTotal")).setScale(2, RoundingMode.HALF_UP).toString();
				String orderDiscountTotal = new BigDecimal(caseData.get("orderDiscountTotal")).setScale(2, RoundingMode.HALF_UP).toString();
				if ("PT04".equals(orderWideDiscountSubject)) {
					allocationPlanDetailId = cz_allocationPlanDetailId;
				} else if ("PT02".equals(orderWideDiscountSubject)) {
					allocationPlanDetailId = lty_allocationPlanDetailId;
				} else if ("PT01".equals(orderWideDiscountSubject)) {
					allocationPlanDetailId = mz_allocationPlanDetailId;
				} else {
					allocationPlanDetailId = "";
				}
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
			discountCalcResponse.then().body("resultCode", Matchers.equalTo("0"), "resultMsg", Matchers.equalTo("success"));
			orderCheckOrderManagement01.checkOrderDiscountCalc(dataTable, discountCalcResponse);
			priceBatchClearData01.clearPirceData01("at");
			orderBatchClearData01.clearOrderPreDiscountData01("at");
			orderBatchClearData01.clearDiscountData01("at");
		});
	}

}
