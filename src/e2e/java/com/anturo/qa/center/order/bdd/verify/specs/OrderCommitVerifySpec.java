package com.anturo.qa.center.order.bdd.verify.specs;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.net.NetUtil;
import cn.hutool.core.util.IdUtil;
import com.anturo.qa.center.order.bdd.order.apis.OrderApiOrderManagement01;
import com.anturo.qa.center.order.bdd.order.backgrounds.OrderCommitCheckBackgroundData01;
import com.anturo.qa.center.order.bdd.order.batchs.*;
import com.anturo.qa.center.order.main.model.dto.*;
import com.anturo.qa.center.order.process.CompanyEnum;
import com.anturo.qa.center.order.process.ProductEnum;
import com.anturo.qa.center.order.process.RegionEnum;
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
 * @Author guoqing
 * @Date 2024/09/27
 * @Description
 */

@SuppressWarnings("all")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class OrderCommitVerifySpec extends OrderCommitCheckBackgroundData01 implements En {

	String saleOrgSapCode, regionCode, regionName, customerSapCode, shippingRecipient, shippingTelephone, shippingAddress, saleOrgSapCodeNew, saleOrgSapCodeNewItemCode;

	String preDiscountId, preDiscountDetailId;

	String applicationFormId, applicationFormDetailId, allocationPlanId, allocationPlanDetailId;

	String createTime, beginDate, endDate, processOrderNo, planMonth, lotNo;

	String lty_allocationPlanDetailId, cz_allocationPlanDetailId, mz_allocationPlanDetailId;

	String allocationPlanDetailId_001, allocationPlanDetailId_002, allocationPlanDetailId_003, allocationPlanDetailId_004, allocationPlanDetailId_201, allocationPlanDetailId_202, allocationPlanDetailId_203, allocationPlanDetailId_301, allocationPlanDetailId_302, allocationPlanDetailId_401;

	String allocationPlanId_001, allocationPlanId_002, allocationPlanId_003, allocationPlanId_004;

	String saleOrgControlledProductId, permittedSalesRegionId, orderPermittedSalesCustomerId, permittedSaleBeginDate, permittedSaleEndDate;

	DataTable domainDataTable;

	String itemCodeTmp;

	@Steps(actor = "QA", shared = true)
	OrderApiOrderManagement01 orderApiOrderManagement01;

	@Steps(actor = "QA", shared = true)
	OrderBatchUpdateData01 orderBatchUpdateData01;

	@Steps(actor = "QA", shared = true)
	ChannelBatchUpdateData01 channelBatchUpdateData01;

	@Steps(actor = "QA", shared = true)
	ItemBatchUpdateData01 itemBatchUpdateData01;

	@Steps(actor = "QA", shared = true)
	PriceBatchUpdateData01 priceBatchUpdateData01;

	@Steps(actor = "QA", shared = true)
	ItemBatchInsertData01 itemBatchInsertData01;

	@Steps(actor = "QA", shared = true)
	RebateBatchUpdateData01 rebateBatchUpdateData01;

	// 终端ID
	Long workerId = NetUtil.ipv4ToLong(NetUtil.getLocalhostStr()) >> 16 & 31;
	// 数据中心ID
	Long dataCenterId = 1L;

	public OrderCommitVerifySpec() {
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
		this.scenario14();
		this.scenario15();
		this.scenario16();
		this.scenario17();
		this.scenario18();
		this.scenario19();
		this.scenario20();
		this.scenario21();
		this.scenario22();
		this.scenario23();
		this.scenario24();
		this.scenario25();
		this.scenario26();
		this.scenario27();
		this.scenario28();
		this.scenario29();
		this.scenario30();
		this.scenario31();
		this.scenario32();
		this.scenario33();
		this.scenario34();
		this.scenario35();
		this.scenario36();
		this.scenario37();
		this.scenario38();
		this.scenario39();
		this.scenario40();
		this.scenario41();
		this.scenario42();
		this.scenario43();
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
		Given("[创建订单提交校验01]当前存在如下客户信息", (DataTable dataTable) -> {
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
		When("[创建订单提交校验01]当前存在如下的预折商品信息", (DataTable dataTable) -> {
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
						.build());
			});
		});
		And("[创建订单提交校验01]当前存在如下的预折折扣信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				BigDecimal calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(8, RoundingMode.HALF_UP);
				String effectiveState = Optional.ofNullable(caseData.get("effectiveState")).orElse("");
				orderBatchUpdateData01.updateOrderPreDiscountDetailCalculationValue(OrderPreDiscountDetailDTO.builder()
								.effectiveState(effectiveState)
								.calculationValue(calculationValue)
								.itemSapCode(itemSapCode)
								.build(),
						OrderPreDiscountDTO.builder()
								.customerSapCode(customerSapCode)
								.build());
			});
		});
		And("[创建订单提交校验01]当前存在如下的整单折扣申请单信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验01]当前存在如下的整单折扣申请单商品信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验01]当前存在如下的整单折扣分配计划信息", () -> {
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
		And("[创建订单提交校验01]当前存在如下的整单折扣分配计划详细信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验01]添加商品", (DataTable dataTable) -> {
			log.info("添加商品");
		});
		And("[创建订单提交校验01]选择项目", (DataTable dataTable) -> {
			log.info("选择项目");
		});
		Then("[创建订单提交校验01]提交订单校验提示拦截且提示错误信息", (DataTable dataTable) -> {
			String totalInlineSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("totalInline")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
			String preDiscountTotalSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("preDiscountTotal")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
			String orderDiscountTotalSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("orderDiscountTotal")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
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
				orderDiscountCalcCalculateReqGroupListListDTOS.add(OrderDiscountCalcCalculateReqGroupListListDTO.builder().orderWideDiscountSubject(orderWideDiscountSubject).discountAllocationPlanId(allocationPlanId).discountAllocationPlanDetailId(allocationPlanDetailId).discountId(id).preDiscountDetailId(id).itemSapCode(itemSapCode).itemName(ProductEnum.fetchProductName(itemSapCode)).itemDesc(ProductEnum.fetchProductSpec(itemSapCode)).itemGroupCode("01").itemGroupName("成品药-非管制药品").distributionChannelCode("22").distributionChannelName("分销-非医疗").unit("盒").unitDesc("盒").unitPrice(unitPrice).calculationValue(calculationValue).preDiscount(preDiscount).preType("")     // 支付方式为空
						.calculationType("DISCOUNTED_PRICE").quantity(quantity).packingRatio("").totalInline(totalInline).preDiscountTotal(preDiscountTotal).needShip(needShip).baseUnitCode("盒").baseUnitName("盒").boxRatio(boxRatio).xunit("1").yunit("1").build());
			});
			OrderDiscountCalcCalculateReqDTO calculateReq = orderApiOrderManagement01.discountCalcAfterData(OrderDataDTO.builder().orderType("STANDARD").saleOrgSapCode(saleOrgSapCode).saleOrgName(SaleOrgEnum.fetchName(saleOrgSapCode)).regionCode(regionCode).regionName(regionName).customerEntSapCode(customerSapCode).customerEntCrmCode(CompanyEnum.fetchCrmCode(customerSapCode)).customerEntZtCode(CompanyEnum.fetchZtCode(customerSapCode)).customerName(CompanyEnum.fetchName(customerSapCode)).invoiceType("VAT_SPECIAL_INVOICE").shippingPolicy("NON_FULL_SHIPMENT").priority("NORMAL").paymentMethod("").shippingRecipient(shippingRecipient).shippingTelephone(shippingTelephone).shippingAddress(shippingAddress).remark("自动化测试").calculateReq(OrderDiscountCalcCalculateReqDTO.builder().totalInline(totalInlineSum).preDiscountTotal(preDiscountTotalSum).orderDiscountTotal(orderDiscountTotalSum).groupList(Arrays.asList(OrderDiscountCalcCalculateReqGroupListDTO.builder().itemGroupCode("01").itemGroupName("成品药-非管制药品").distributionChannelCode("22").distributionChannelName("分销-非医疗").totalInline(totalInlineSum).preDiscountTotal(preDiscountTotalSum).orderDiscountTotal(orderDiscountTotalSum).list(orderDiscountCalcCalculateReqGroupListListDTOS).build())).build()).build());
			Response createOrderSubmitResponse = orderApiOrderManagement01.createOrderBefore(OrderDataDTO.builder().orderType("STANDARD").saleOrgSapCode(saleOrgSapCode).saleOrgName(SaleOrgEnum.fetchName(saleOrgSapCode)).regionCode(regionCode).regionName(regionName).customerEntSapCode(customerSapCode).customerEntCrmCode(CompanyEnum.fetchCrmCode(customerSapCode)).customerEntZtCode(CompanyEnum.fetchZtCode(customerSapCode)).customerName(CompanyEnum.fetchName(customerSapCode)).invoiceType("VAT_SPECIAL_INVOICE").shippingPolicy("NON_FULL_SHIPMENT").priority("NORMAL").paymentMethod("").shippingRecipient(shippingRecipient).shippingTelephone(shippingTelephone).shippingAddress(shippingAddress).remark("自动化测试").calculateReq(calculateReq).build());
			createOrderSubmitResponse.then().body("resultCode", Matchers.equalTo("100000"), "resultMsg", Matchers.equalTo("支付方式不能为空"));
			priceBatchClearData01.clearPirceData01("at");
			orderBatchClearData01.clearOrderPreDiscountData01("at");
			orderBatchClearData01.clearDiscountData01("at");
			orderBatchClearData01.clearPermittedSalesData01("at");
		});
	}

	private void scenario02() {
		Given("[创建订单提交校验02]当前存在如下客户信息", (DataTable dataTable) -> {
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
		When("[创建订单提交校验02]当前存在如下的预折商品信息", (DataTable dataTable) -> {
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
						.build());
			});
		});
		And("[创建订单提交校验02]当前存在如下的预折折扣信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				BigDecimal calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(8, RoundingMode.HALF_UP);
				String effectiveState = Optional.ofNullable(caseData.get("effectiveState")).orElse("");
				orderBatchUpdateData01.updateOrderPreDiscountDetailCalculationValue(OrderPreDiscountDetailDTO.builder()
								.effectiveState(effectiveState)
								.calculationValue(calculationValue)
								.itemSapCode(itemSapCode)
								.build(),
						OrderPreDiscountDTO.builder()
								.customerSapCode(customerSapCode)
								.build());
			});
		});
		And("[创建订单提交校验02]当前存在如下的整单折扣申请单信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验02]当前存在如下的整单折扣申请单商品信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验02]当前存在如下的整单折扣分配计划信息", () -> {
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
		And("[创建订单提交校验02]当前存在如下的整单折扣分配计划详细信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验02]添加商品", (DataTable dataTable) -> {
			log.info("添加商品");
		});
		And("[创建订单提交校验02]选择项目", (DataTable dataTable) -> {
			log.info("选择项目");
		});
		Then("[创建订单提交校验02]创建订单应校验失败且提示错误信息", (DataTable dataTable) -> {
			String totalInlineSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("totalInline")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
			String preDiscountTotalSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("preDiscountTotal")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
			String orderDiscountTotalSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("orderDiscountTotal")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
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
			OrderDiscountCalcCalculateReqDTO calculateReq = orderApiOrderManagement01.discountCalcAfterData(OrderDataDTO.builder().orderType("STANDARD").saleOrgSapCode(saleOrgSapCode).saleOrgName(SaleOrgEnum.fetchName(saleOrgSapCode)).regionCode(regionCode).regionName(regionName).customerEntSapCode(customerSapCode).customerEntCrmCode(CompanyEnum.fetchCrmCode(customerSapCode)).customerEntZtCode(CompanyEnum.fetchZtCode(customerSapCode)).customerName(CompanyEnum.fetchName(customerSapCode)).invoiceType("VAT_SPECIAL_INVOICE").shippingPolicy("NON_FULL_SHIPMENT").priority("NORMAL").paymentMethod("BOTTOM_GOODS") // 支付方式为铺底货
					.shippingRecipient(shippingRecipient).shippingTelephone(shippingTelephone).shippingAddress(shippingAddress).remark("自动化测试").calculateReq(OrderDiscountCalcCalculateReqDTO.builder().totalInline(totalInlineSum).preDiscountTotal(preDiscountTotalSum).orderDiscountTotal(orderDiscountTotalSum).groupList(Arrays.asList(OrderDiscountCalcCalculateReqGroupListDTO.builder().itemGroupCode("01").itemGroupName("成品药-非管制药品").distributionChannelCode("22").distributionChannelName("分销-非医疗").totalInline(totalInlineSum).preDiscountTotal(preDiscountTotalSum).orderDiscountTotal(orderDiscountTotalSum).list(orderDiscountCalcCalculateReqGroupListListDTOS).build())).build()).build());
			Response createOrderSubmitResponse = orderApiOrderManagement01.createOrderBefore(OrderDataDTO.builder().orderType("STANDARD").saleOrgSapCode(saleOrgSapCode).saleOrgName(SaleOrgEnum.fetchName(saleOrgSapCode)).regionCode(regionCode).regionName(regionName).customerEntSapCode(customerSapCode).customerEntCrmCode(CompanyEnum.fetchCrmCode(customerSapCode)).customerEntZtCode(CompanyEnum.fetchZtCode(customerSapCode)).customerName(CompanyEnum.fetchName(customerSapCode)).invoiceType("VAT_SPECIAL_INVOICE").shippingPolicy("NON_FULL_SHIPMENT").priority("NORMAL").paymentMethod("BOTTOM_GOODS").shippingRecipient(shippingRecipient).shippingTelephone(shippingTelephone).shippingAddress(shippingAddress).remark("自动化测试").calculateReq(calculateReq).build());
			createOrderSubmitResponse.then().body("resultCode", Matchers.equalTo("100000"), "resultMsg", Matchers.equalTo("铺底货订单不可参加活动，请修改支付方式!"));
			priceBatchClearData01.clearPirceData01("at");
			orderBatchClearData01.clearOrderPreDiscountData01("at");
			orderBatchClearData01.clearDiscountData01("at");
			orderBatchClearData01.clearPermittedSalesData01("at");
		});
	}

	private void scenario03() {
		Given("[创建订单提交校验03]当前存在如下客户信息", (DataTable dataTable) -> {
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
		When("[创建订单提交校验03]当前存在如下的预折商品信息", (DataTable dataTable) -> {
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
						.build());
			});
		});
		And("[创建订单提交校验03]当前存在如下的预折折扣信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				BigDecimal calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(8, RoundingMode.HALF_UP);
				String effectiveState = Optional.ofNullable(caseData.get("effectiveState")).orElse("");
				orderBatchUpdateData01.updateOrderPreDiscountDetailCalculationValue(OrderPreDiscountDetailDTO.builder()
								.effectiveState(effectiveState)
								.calculationValue(calculationValue)
								.itemSapCode(itemSapCode)
								.build(),
						OrderPreDiscountDTO.builder()
								.customerSapCode(customerSapCode)
								.build());
			});
		});
		And("[创建订单提交校验03]当前存在如下的整单折扣申请单信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验03]当前存在如下的整单折扣申请单商品信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验03]当前存在如下的整单折扣分配计划信息", () -> {
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
		And("[创建订单提交校验03]当前存在如下的整单折扣分配计划详细信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验03]添加商品", (DataTable dataTable) -> {
			log.info("添加商品");
		});
		And("[创建订单提交校验03]选择项目", (DataTable dataTable) -> {
			log.info("选择项目");
		});
		Then("[创建订单提交校验03]创建订单应校验失败且提示错误信息", (DataTable dataTable) -> {
			String totalInlineSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("totalInline")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
			String preDiscountTotalSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("preDiscountTotal")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
			String orderDiscountTotalSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("orderDiscountTotal")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
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
			OrderDiscountCalcCalculateReqDTO calculateReq = orderApiOrderManagement01.discountCalcAfterData(OrderDataDTO.builder()
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
							.build())
					.build());
			Response createOrderSubmitResponse = orderApiOrderManagement01.createOrderBefore(OrderDataDTO.builder()
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
					.calculateReq(calculateReq)
					.build());
			createOrderSubmitResponse.then().body("resultCode", Matchers.equalTo("100000"), "resultMsg", Matchers.equalTo("收货人不能为空"));
			priceBatchClearData01.clearPirceData01("at");
			orderBatchClearData01.clearOrderPreDiscountData01("at");
			orderBatchClearData01.clearDiscountData01("at");
			orderBatchClearData01.clearPermittedSalesData01("at");
		});
	}

	private void scenario04() {
		Given("[创建订单提交校验04]当前存在如下客户信息", (DataTable dataTable) -> {
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
		When("[创建订单提交校验04]当前存在如下的预折商品信息", (DataTable dataTable) -> {
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
						.build());
			});
		});
		And("[创建订单提交校验04]当前存在如下的预折折扣信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				BigDecimal calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(8, RoundingMode.HALF_UP);
				String effectiveState = Optional.ofNullable(caseData.get("effectiveState")).orElse("");
				orderBatchUpdateData01.updateOrderPreDiscountDetailCalculationValue(OrderPreDiscountDetailDTO.builder()
								.effectiveState(effectiveState)
								.calculationValue(calculationValue)
								.itemSapCode(itemSapCode)
								.build(),
						OrderPreDiscountDTO.builder()
								.customerSapCode(customerSapCode)
								.build());
			});
		});
		And("[创建订单提交校验04]当前存在如下的整单折扣申请单信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验04]当前存在如下的整单折扣申请单商品信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验04]当前存在如下的整单折扣分配计划信息", () -> {
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
		And("[创建订单提交校验04]当前存在如下的整单折扣分配计划详细信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验04]添加商品", (DataTable dataTable) -> {
			log.info("添加商品");
		});
		And("[创建订单提交校验04]选择项目", (DataTable dataTable) -> {
			log.info("选择项目");
		});
		Then("[创建订单提交校验04]创建订单应校验失败且提示错误信息", (DataTable dataTable) -> {
			String totalInlineSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("totalInline")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
			String preDiscountTotalSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("preDiscountTotal")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
			String orderDiscountTotalSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("orderDiscountTotal")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
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
			OrderDiscountCalcCalculateReqDTO calculateReq = orderApiOrderManagement01.discountCalcAfterData(OrderDataDTO.builder()
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
							.build())
					.build());
			Response createOrderSubmitResponse = orderApiOrderManagement01.createOrderBefore(OrderDataDTO.builder()
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
					.calculateReq(calculateReq)
					.build());
			createOrderSubmitResponse.then().body("resultCode", Matchers.equalTo("100000"), "resultMsg", Matchers.equalTo("收货地址不能为空"));
			priceBatchClearData01.clearPirceData01("at");
			orderBatchClearData01.clearOrderPreDiscountData01("at");
			orderBatchClearData01.clearDiscountData01("at");
			orderBatchClearData01.clearPermittedSalesData01("at");
		});
	}

	private void scenario05() {
		Given("[创建订单提交校验05]当前存在如下客户信息", (DataTable dataTable) -> {
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
		When("[创建订单提交校验05]当前存在如下的预折商品信息", (DataTable dataTable) -> {
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
						.build());
			});
		});
		And("[创建订单提交校验05]当前存在如下的预折折扣信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				BigDecimal calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(8, RoundingMode.HALF_UP);
				String effectiveState = Optional.ofNullable(caseData.get("effectiveState")).orElse("");
				orderBatchUpdateData01.updateOrderPreDiscountDetailCalculationValue(OrderPreDiscountDetailDTO.builder()
								.effectiveState(effectiveState)
								.calculationValue(calculationValue)
								.itemSapCode(itemSapCode)
								.build(),
						OrderPreDiscountDTO.builder()
								.customerSapCode(customerSapCode)
								.build());
			});
		});
		And("[创建订单提交校验05]当前存在如下的整单折扣申请单信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验05]当前存在如下的整单折扣申请单商品信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验05]当前存在如下的整单折扣分配计划信息", () -> {
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
		And("[创建订单提交校验05]当前存在如下的整单折扣分配计划详细信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验05]添加商品", (DataTable dataTable) -> {
			log.info("添加商品");
		});
		And("[创建订单提交校验05]选择项目", (DataTable dataTable) -> {
			log.info("选择项目");
		});
		Then("[创建订单提交校验05]创建订单应校验失败且提示错误信息", (DataTable dataTable) -> {
			String totalInlineSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("totalInline")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
			String preDiscountTotalSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("preDiscountTotal")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
			String orderDiscountTotalSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("orderDiscountTotal")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
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
			OrderDiscountCalcCalculateReqDTO calculateReq = orderApiOrderManagement01.discountCalcAfterData(OrderDataDTO.builder()
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
							.build())
					.build());
			Response createOrderSubmitResponse = orderApiOrderManagement01.createOrderBefore(OrderDataDTO.builder()
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
					.calculateReq(calculateReq)
					.build());
			createOrderSubmitResponse.then().body("resultCode", Matchers.equalTo("100000"), "resultMsg", Matchers.equalTo("收货人不能为空"));
			priceBatchClearData01.clearPirceData01("at");
			orderBatchClearData01.clearOrderPreDiscountData01("at");
			orderBatchClearData01.clearDiscountData01("at");
			orderBatchClearData01.clearPermittedSalesData01("at");
		});
	}

	private void scenario06() {
		Given("[创建订单提交校验06]当前存在如下客户信息", (DataTable dataTable) -> {
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
		When("[创建订单提交校验06]当前存在如下的预折商品信息", (DataTable dataTable) -> {
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
						.build());
			});
		});
		And("[创建订单提交校验06]当前存在如下的预折折扣信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				BigDecimal calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(8, RoundingMode.HALF_UP);
				String effectiveState = Optional.ofNullable(caseData.get("effectiveState")).orElse("");
				orderBatchUpdateData01.updateOrderPreDiscountDetailCalculationValue(OrderPreDiscountDetailDTO.builder()
								.effectiveState(effectiveState)
								.calculationValue(calculationValue)
								.itemSapCode(itemSapCode)
								.build(),
						OrderPreDiscountDTO.builder()
								.customerSapCode(customerSapCode)
								.build());
			});
		});
		And("[创建订单提交校验06]当前存在如下的整单折扣申请单信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验06]当前存在如下的整单折扣申请单商品信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验06]当前存在如下的整单折扣分配计划信息", () -> {
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
		And("[创建订单提交校验06]当前存在如下的整单折扣分配计划详细信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验06]添加商品", (DataTable dataTable) -> {
			log.info("添加商品");
		});
		And("[创建订单提交校验06]选择项目", (DataTable dataTable) -> {
			log.info("选择项目");
		});
		Then("[创建订单提交校验06]创建订单应校验失败且提示错误信息", (DataTable dataTable) -> {
			String totalInlineSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("totalInline")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
			String preDiscountTotalSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("preDiscountTotal")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
			String orderDiscountTotalSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("orderDiscountTotal")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
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
			OrderDiscountCalcCalculateReqDTO calculateReq = orderApiOrderManagement01.discountCalcAfterData(OrderDataDTO.builder()
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
							.build())
					.build());
			Response createOrderSubmitResponse = orderApiOrderManagement01.createOrderBefore(OrderDataDTO.builder()
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
					.calculateReq(calculateReq)
					.build());
			createOrderSubmitResponse.then().body("resultCode", Matchers.equalTo("100000"), "resultMsg", Matchers.equalTo("收货电话不能为空"));
			priceBatchClearData01.clearPirceData01("at");
			orderBatchClearData01.clearOrderPreDiscountData01("at");
			orderBatchClearData01.clearDiscountData01("at");
			orderBatchClearData01.clearPermittedSalesData01("at");
		});
	}


	private void scenario07() {
		Given("[创建订单提交校验07]当前存在如下客户信息", (DataTable dataTable) -> {
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
		When("[创建订单提交校验07]当前存在如下的预折商品信息", (DataTable dataTable) -> {
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
						.build());
			});
		});
		And("[创建订单提交校验07]当前存在如下的预折折扣信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				BigDecimal calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(8, RoundingMode.HALF_UP);
				String effectiveState = Optional.ofNullable(caseData.get("effectiveState")).orElse("");
				orderBatchUpdateData01.updateOrderPreDiscountDetailCalculationValue(OrderPreDiscountDetailDTO.builder()
								.effectiveState(effectiveState)
								.calculationValue(calculationValue)
								.itemSapCode(itemSapCode)
								.build(),
						OrderPreDiscountDTO.builder()
								.customerSapCode(customerSapCode)
								.build());
			});
		});
		And("[创建订单提交校验07]当前存在如下的整单折扣申请单信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验07]当前存在如下的整单折扣申请单商品信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验07]当前存在如下的整单折扣分配计划信息", () -> {
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
		And("[创建订单提交校验07]当前存在如下的整单折扣分配计划详细信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验07]添加商品", (DataTable dataTable) -> {
			log.info("添加商品");
		});
		And("[创建订单提交校验07]选择项目", (DataTable dataTable) -> {
			log.info("选择项目");
		});
		Then("[创建订单提交校验07]创建订单应校验失败且提示错误信息", (DataTable dataTable) -> {
			String totalInlineSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("totalInline")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
			String preDiscountTotalSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("preDiscountTotal")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
			String orderDiscountTotalSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("orderDiscountTotal")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
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
			OrderDiscountCalcCalculateReqDTO calculateReq = orderApiOrderManagement01.discountCalcAfterData(OrderDataDTO.builder()
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
							.build())
					.build());
			Response createOrderSubmitResponse = orderApiOrderManagement01.createOrderBefore(OrderDataDTO.builder()
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
					.calculateReq(calculateReq)
					.build());
			createOrderSubmitResponse.then().body("resultCode", Matchers.equalTo("100000"), "resultMsg", Matchers.equalTo("以下商品数量非正整数,请修改:[商品数量非正整数,商品:10000841,商品描述:醒脑静注射液，10ml/支，2支/盒，240盒/箱,分销渠道:分销-非医疗, 商品数量非正整数,商品:10000456,商品描述:荆花胃康胶丸，80mg/粒，12粒/盒，300盒/箱,分销渠道:分销-非医疗]"));
			priceBatchClearData01.clearPirceData01("at");
			orderBatchClearData01.clearOrderPreDiscountData01("at");
			orderBatchClearData01.clearDiscountData01("at");
			orderBatchClearData01.clearPermittedSalesData01("at");
		});
	}


	private void scenario08() {
		Given("[创建订单提交校验08]当前存在如下客户信息", (DataTable dataTable) -> {
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
		When("[创建订单提交校验08]当前存在如下的预折商品信息", (DataTable dataTable) -> {
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
						.build());
			});
		});
		And("[创建订单提交校验08]当前存在如下的预折折扣信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				BigDecimal calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(8, RoundingMode.HALF_UP);
				String effectiveState = Optional.ofNullable(caseData.get("effectiveState")).orElse("");
				orderBatchUpdateData01.updateOrderPreDiscountDetailCalculationValue(OrderPreDiscountDetailDTO.builder()
								.effectiveState(effectiveState)
								.calculationValue(calculationValue)
								.itemSapCode(itemSapCode)
								.build(),
						OrderPreDiscountDTO.builder()
								.customerSapCode(customerSapCode)
								.build());
			});
		});
		And("[创建订单提交校验08]当前存在如下的整单折扣申请单信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验08]当前存在如下的整单折扣申请单商品信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验08]当前存在如下的整单折扣分配计划信息", () -> {
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
		And("[创建订单提交校验08]当前存在如下的整单折扣分配计划详细信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验08]添加商品", (DataTable dataTable) -> {
			log.info("添加商品");
		});
		And("[创建订单提交校验08]选择项目", (DataTable dataTable) -> {
			log.info("选择项目");
		});
		Then("[创建订单提交校验08]创建订单应校验失败且提示错误信息", (DataTable dataTable) -> {
			String totalInlineSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("totalInline")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
			String preDiscountTotalSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("preDiscountTotal")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
			String orderDiscountTotalSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("orderDiscountTotal")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
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
				orderDiscountCalcCalculateReqGroupListListDTOS.add(OrderDiscountCalcCalculateReqGroupListListDTO.builder().orderWideDiscountSubject(orderWideDiscountSubject).discountAllocationPlanId(allocationPlanId).discountAllocationPlanDetailId(allocationPlanDetailId).discountId(id).preDiscountDetailId(id).itemSapCode(itemSapCode).itemName(ProductEnum.fetchProductName(itemSapCode)).itemDesc(ProductEnum.fetchProductSpec(itemSapCode)).itemGroupCode("01").itemGroupName("成品药-非管制药品").distributionChannelCode("22").distributionChannelName("分销-非医疗").unit("盒").unitDesc("盒").unitPrice(unitPrice).calculationValue(calculationValue).preDiscount(preDiscount).preType("CUSTOMER_PRE_DISCOUNT").calculationType("DISCOUNTED_PRICE").quantity("0").packingRatio("").totalInline(totalInline).preDiscountTotal(preDiscountTotal).needShip(needShip).baseUnitCode("盒").baseUnitName("盒").boxRatio(boxRatio).xunit("1").yunit("1").build());
			});
			OrderDiscountCalcCalculateReqDTO calculateReq = orderApiOrderManagement01.discountCalcAfterData(OrderDataDTO.builder()
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
							.build())
					.build());
			Response createOrderSubmitResponse = orderApiOrderManagement01.createOrderBefore(OrderDataDTO.builder()
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
					.calculateReq(calculateReq)
					.build());
			createOrderSubmitResponse.then().body("resultCode", Matchers.equalTo("100000"), "resultMsg", Matchers.equalTo("以下商品数量非正整数,请修改:[商品数量非正整数,商品:10000841,商品描述:醒脑静注射液，10ml/支，2支/盒，240盒/箱,分销渠道:分销-非医疗]"));
			priceBatchClearData01.clearPirceData01("at");
			orderBatchClearData01.clearOrderPreDiscountData01("at");
			orderBatchClearData01.clearDiscountData01("at");
			orderBatchClearData01.clearPermittedSalesData01("at");
		});
	}


	private void scenario09() {
		Given("[创建订单提交校验09]当前存在如下客户信息", (DataTable dataTable) -> {
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
		When("[创建订单提交校验09]当前存在如下的预折商品信息", (DataTable dataTable) -> {
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
						.build());
			});
		});
		And("[创建订单提交校验09]当前存在如下的预折折扣信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				BigDecimal calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(8, RoundingMode.HALF_UP);
				String effectiveState = Optional.ofNullable(caseData.get("effectiveState")).orElse("");
				orderBatchUpdateData01.updateOrderPreDiscountDetailCalculationValue(OrderPreDiscountDetailDTO.builder()
								.effectiveState(effectiveState)
								.calculationValue(calculationValue)
								.itemSapCode(itemSapCode)
								.build(),
						OrderPreDiscountDTO.builder()
								.customerSapCode(customerSapCode)
								.build());
			});
		});
		And("[创建订单提交校验09]当前存在如下的整单折扣申请单信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验09]当前存在如下的整单折扣申请单商品信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验09]当前存在如下的整单折扣分配计划信息", () -> {
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
		And("[创建订单提交校验09]当前存在如下的整单折扣分配计划详细信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验09]添加商品", (DataTable dataTable) -> {
			log.info("添加商品");
		});
		And("[创建订单提交校验09]选择项目", (DataTable dataTable) -> {
			log.info("选择项目");
		});
		Then("[创建订单提交校验09]创建订单应校验失败且提示错误信息", (DataTable dataTable) -> {
			String totalInlineSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("totalInline")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
			String preDiscountTotalSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("preDiscountTotal")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
			String orderDiscountTotalSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("orderDiscountTotal")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
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
				orderDiscountCalcCalculateReqGroupListListDTOS.add(OrderDiscountCalcCalculateReqGroupListListDTO.builder().orderWideDiscountSubject(orderWideDiscountSubject).discountAllocationPlanId(allocationPlanId).discountAllocationPlanDetailId(allocationPlanDetailId).discountId(id).preDiscountDetailId(id).itemSapCode(itemSapCode).itemName(ProductEnum.fetchProductName(itemSapCode)).itemDesc(ProductEnum.fetchProductSpec(itemSapCode)).itemGroupCode("01").itemGroupName("成品药-非管制药品").distributionChannelCode("22").distributionChannelName("分销-非医疗").unit("盒").unitDesc("盒").unitPrice(unitPrice).calculationValue(calculationValue).preDiscount(preDiscount).preType("CUSTOMER_PRE_DISCOUNT").calculationType("DISCOUNTED_PRICE").quantity(quantity).packingRatio("0.34").totalInline(totalInline).preDiscountTotal(preDiscountTotal).needShip(needShip).baseUnitCode("盒").baseUnitName("盒").boxRatio(boxRatio).xunit("1").yunit("1").build());
			});
			Response discountCalcResponse = orderApiOrderManagement01.discountCalcAfterDataErr(OrderDataDTO.builder().saleOrgSapCode(saleOrgSapCode).saleOrgName(SaleOrgEnum.fetchName(saleOrgSapCode)).regionCode(regionCode).regionName(regionName).customerEntSapCode(customerSapCode).customerEntCrmCode(CompanyEnum.fetchCrmCode(customerSapCode)).customerEntZtCode(CompanyEnum.fetchZtCode(customerSapCode)).customerName(CompanyEnum.fetchName(customerSapCode)).invoiceType("VAT_SPECIAL_INVOICE").shippingPolicy("NON_FULL_SHIPMENT").priority("NORMAL").paymentMethod("PRE_PAYMENT").shippingRecipient(shippingRecipient).shippingTelephone(shippingTelephone).shippingAddress(shippingAddress).remark("自动化测试").calculateReq(OrderDiscountCalcCalculateReqDTO.builder().totalInline(totalInlineSum).preDiscountTotal(preDiscountTotalSum).orderDiscountTotal(orderDiscountTotalSum).groupList(Arrays.asList(OrderDiscountCalcCalculateReqGroupListDTO.builder().itemGroupCode("01").itemGroupName("成品药-非管制药品").distributionChannelCode("22").distributionChannelName("分销-非医疗").totalInline(totalInlineSum).preDiscountTotal(preDiscountTotalSum).orderDiscountTotal(orderDiscountTotalSum).list(orderDiscountCalcCalculateReqGroupListListDTOS).build())).build()).build());
			discountCalcResponse.then().body("resultCode", Matchers.equalTo("2000"), "resultMsg", Matchers.equalTo("参数类型不匹配{1.95},required：{java.lang.Integer}"));
			priceBatchClearData01.clearPirceData01("at");
			orderBatchClearData01.clearOrderPreDiscountData01("at");
			orderBatchClearData01.clearDiscountData01("at");
			orderBatchClearData01.clearPermittedSalesData01("at");
		});
	}

	private void scenario10() {
		Given("[创建订单提交校验10]当前存在如下客户信息", (DataTable dataTable) -> {
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
		When("[创建订单提交校验10]当前存在如下的预折商品信息", (DataTable dataTable) -> {
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
						.build());
			});
		});
		And("[创建订单提交校验10]当前存在如下的预折折扣信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				BigDecimal calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(8, RoundingMode.HALF_UP);
				String effectiveState = Optional.ofNullable(caseData.get("effectiveState")).orElse("");
				orderBatchUpdateData01.updateOrderPreDiscountDetailCalculationValue(OrderPreDiscountDetailDTO.builder()
								.effectiveState(effectiveState)
								.calculationValue(calculationValue)
								.itemSapCode(itemSapCode)
								.build(),
						OrderPreDiscountDTO.builder()
								.customerSapCode(customerSapCode)
								.build());
			});
		});
		And("[创建订单提交校验10]当前存在如下的整单折扣申请单信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验10]当前存在如下的整单折扣申请单商品信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验10]当前存在如下的整单折扣分配计划信息", () -> {
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
		And("[创建订单提交校验10]当前存在如下的整单折扣分配计划详细信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验10]添加商品", (DataTable dataTable) -> {
			log.info("添加商品");
		});
		And("[创建订单提交校验10]选择项目", (DataTable dataTable) -> {
			log.info("选择项目");
		});
		Then("[创建订单提交校验10]创建订单应校验失败且提示错误信息", (DataTable dataTable) -> {
			String totalInlineSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("totalInline")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
			String preDiscountTotalSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("preDiscountTotal")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
			String orderDiscountTotalSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("orderDiscountTotal")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
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
				orderDiscountCalcCalculateReqGroupListListDTOS.add(OrderDiscountCalcCalculateReqGroupListListDTO.builder().orderWideDiscountSubject(orderWideDiscountSubject).discountAllocationPlanId(allocationPlanId).discountAllocationPlanDetailId(allocationPlanDetailId).discountId(id).preDiscountDetailId(id).itemSapCode(itemSapCode).itemName(ProductEnum.fetchProductName(itemSapCode)).itemDesc(ProductEnum.fetchProductSpec(itemSapCode)).itemGroupCode("01").itemGroupName("成品药-非管制药品").distributionChannelCode("22").distributionChannelName("分销-非医疗").unit("盒").unitDesc("盒").unitPrice(unitPrice).calculationValue(calculationValue).preDiscount(preDiscount).preType("CUSTOMER_PRE_DISCOUNT").calculationType("DISCOUNTED_PRICE").quantity("0").packingRatio("").totalInline(totalInline).preDiscountTotal(preDiscountTotal).needShip(needShip).baseUnitCode("盒").baseUnitName("盒").boxRatio(boxRatio).xunit("1").yunit("1").build());
			});
			OrderDiscountCalcCalculateReqDTO calculateReq = orderApiOrderManagement01.discountCalcAfterData(OrderDataDTO.builder()
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
							.build())
					.build());
			Response createOrderSubmitResponse = orderApiOrderManagement01.createOrderBefore(OrderDataDTO.builder()
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
					.calculateReq(calculateReq)
					.build());
			createOrderSubmitResponse.then().body("resultCode", Matchers.equalTo("100000"), "resultMsg", Matchers.equalTo("以下商品数量非正整数,请修改:[商品数量非正整数,商品:10000841,商品描述:醒脑静注射液，10ml/支，2支/盒，240盒/箱,分销渠道:分销-非医疗, 商品数量非正整数,商品:10000265,商品描述:柴胡滴丸，0.551g/袋，10袋/盒，300盒/箱，OTC,分销渠道:分销-非医疗, 商品数量非正整数,商品:10000456,商品描述:荆花胃康胶丸，80mg/粒，12粒/盒，300盒/箱,分销渠道:分销-非医疗, 商品数量非正整数,商品:10000265,商品描述:柴胡滴丸，0.551g/袋，10袋/盒，300盒/箱，OTC,分销渠道:分销-非医疗, 商品数量非正整数,商品:10000265,商品描述:柴胡滴丸，0.551g/袋，10袋/盒，300盒/箱，OTC,分销渠道:分销-非医疗, 商品数量非正整数,商品:10000265,商品描述:柴胡滴丸，0.551g/袋，10袋/盒，300盒/箱，OTC,分销渠道:分销-非医疗]"));
			priceBatchClearData01.clearPirceData01("at");
			orderBatchClearData01.clearOrderPreDiscountData01("at");
			orderBatchClearData01.clearDiscountData01("at");
			orderBatchClearData01.clearPermittedSalesData01("at");
		});
	}


	private void scenario11() {
		Given("[创建订单提交校验11]当前存在如下客户信息", (DataTable dataTable) -> {
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
		When("[创建订单提交校验11]当前存在如下的预折商品信息", (DataTable dataTable) -> {
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
						.build());
			});
		});
		And("[创建订单提交校验11]当前存在如下的预折折扣信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				BigDecimal calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(8, RoundingMode.HALF_UP);
				String effectiveState = Optional.ofNullable(caseData.get("effectiveState")).orElse("");
				orderBatchUpdateData01.updateOrderPreDiscountDetailCalculationValue(OrderPreDiscountDetailDTO.builder()
								.effectiveState(effectiveState)
								.calculationValue(calculationValue)
								.itemSapCode(itemSapCode)
								.build(),
						OrderPreDiscountDTO.builder()
								.customerSapCode(customerSapCode)
								.build());
			});
		});
		And("[创建订单提交校验11]当前存在如下的整单折扣申请单信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验11]当前存在如下的整单折扣申请单商品信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验11]当前存在如下的整单折扣分配计划信息", () -> {
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
		And("[创建订单提交校验11]当前存在如下的整单折扣分配计划详细信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验11]添加商品", (DataTable dataTable) -> {
			log.info("添加商品");
		});
		And("[创建订单提交校验11]选择项目", (DataTable dataTable) -> {
			log.info("选择项目");
		});
		And("[创建订单提交校验11]当前存在如下的商品禁销信息", (DataTable dataTable) -> {
			saleOrgControlledProductId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			permittedSalesRegionId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			orderPermittedSalesCustomerId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			dataTable.asMaps().stream().forEach(caseData -> {
				String saleOrgSapCode = Optional.ofNullable(caseData.get("saleOrgSapCode")).orElse("");
				String regionCode = Optional.ofNullable(caseData.get("regionCode")).orElse("");
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String permittedSaleQuantityRegion = Optional.ofNullable(caseData.get("permittedSaleQuantityRegion")).orElse("");
				String hasSaleQuantityUnits = Optional.ofNullable(caseData.get("hasSaleQuantityUnits")).orElse("");
				String type = Optional.ofNullable(caseData.get("type")).orElse("");
				String customerName = Optional.ofNullable(caseData.get("customerName")).orElse("");
				String unitCode = Optional.ofNullable(caseData.get("unitCode")).orElse("");
				String unitName = Optional.ofNullable(caseData.get("unitName")).orElse("");
				String customerEntZtCode = Optional.ofNullable(caseData.get("customerEntZtCode")).orElse("");
				String customerEntSapCode = Optional.ofNullable(caseData.get("customerEntSapCode")).orElse("");
				String customerEntCrmCode = Optional.ofNullable(caseData.get("customerEntCrmCode")).orElse("");
				String saleOrgEffectiveState = Optional.ofNullable(caseData.get("saleOrgEffectiveState")).orElse("");
				String regionEffectiveState = Optional.ofNullable(caseData.get("regionEffectiveState")).orElse("");
				String customerEffectiveState = Optional.ofNullable(caseData.get("customerEffectiveState")).orElse("");
				String hasSaleQuantityCustomer = Optional.ofNullable(caseData.get("hasSaleQuantityCustomer")).orElse("");
				String permittedSaleQuantityCustomer = Optional.ofNullable(caseData.get("permittedSaleQuantityCustomer")).orElse("");
				permittedSaleBeginDate = new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis());
				permittedSaleEndDate = new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis());
				orderBatchInsertData01.insertOrderSaleOrgControlledProductData01(OrderSaleOrgControlledProductDTO.builder().saleOrgControlledProductId(saleOrgControlledProductId).saleOrgSapCode(saleOrgSapCode).productSapSku(itemSapCode).effectiveState(saleOrgEffectiveState).createPerson("at").createTime(createTime).build());
				orderBatchInsertData01.insertOrderPermittedSalesRegionData01(OrderPermittedSalesRegionDTO.builder().orderPermittedSalesRegionId(permittedSalesRegionId).saleOrgControlledProductId(saleOrgControlledProductId).regionCode(regionCode).regionName(RegionEnum.fetchName(regionCode)).permittedSaleQuantity(permittedSaleQuantityRegion).unitCode(unitCode).unitName(unitName).permittedSaleBeginDate(permittedSaleBeginDate).permittedSaleEndDate(permittedSaleEndDate).effectiveState(regionEffectiveState).createPerson("at").createTime(createTime).build());
				orderBatchInsertData01.insertOrderPermittedSalesCustomerData01(OrderPermittedSalesCustomerDTO.builder().orderPermittedSalesCustomerId(orderPermittedSalesCustomerId).saleOrgControlledProductId(saleOrgControlledProductId).permittedSalesRegionId(permittedSalesRegionId).customerEntZtCode(customerEntZtCode).customerEntSapCode(customerEntSapCode).customerEntCrmCode(customerEntCrmCode).customerName(customerName).permittedSaleQuantity(permittedSaleQuantityCustomer).hasSaleQuantity(hasSaleQuantityCustomer).type(type).effectiveState(customerEffectiveState).createPerson("at").createTime(createTime).build());
			});

			log.info("设置商品禁销信息");
		});
		Then("[创建订单提交校验11]创建订单应校验失败且提示错误信息", (DataTable dataTable) -> {
			String totalInlineSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("totalInline")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
			String preDiscountTotalSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("preDiscountTotal")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
			String orderDiscountTotalSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("orderDiscountTotal")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
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
			OrderDiscountCalcCalculateReqDTO calculateReq = orderApiOrderManagement01.discountCalcAfterData(OrderDataDTO.builder()
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
							.build())
					.build());
			Response createOrderSubmitResponse = orderApiOrderManagement01.createOrderBefore(OrderDataDTO.builder()
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
					.calculateReq(calculateReq)
					.build());
			createOrderSubmitResponse.then().body("resultCode", Matchers.equalTo("100000"), "resultMsg", Matchers.equalTo("以下商品为限销商品,无法下单:\n商品编码:10000841,商品描述:醒脑静注射液，10ml/支，2支/盒，240盒/箱,原因:限销品，客户所在大区未配置允销政策，不允许购买\n"));
			priceBatchClearData01.clearPirceData01("at");
			orderBatchClearData01.clearOrderPreDiscountData01("at");
			orderBatchClearData01.clearDiscountData01("at");
			orderBatchClearData01.clearPermittedSalesData01("at");
		});
	}

	private void scenario12() {
		Given("[创建订单提交校验12]当前存在如下客户信息", (DataTable dataTable) -> {
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
		When("[创建订单提交校验12]当前存在如下的预折商品信息", (DataTable dataTable) -> {
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
						.build());
			});
		});
		And("[创建订单提交校验12]当前存在如下的预折折扣信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				BigDecimal calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(8, RoundingMode.HALF_UP);
				String effectiveState = Optional.ofNullable(caseData.get("effectiveState")).orElse("");
				orderBatchUpdateData01.updateOrderPreDiscountDetailCalculationValue(OrderPreDiscountDetailDTO.builder()
								.effectiveState(effectiveState)
								.calculationValue(calculationValue)
								.itemSapCode(itemSapCode)
								.build(),
						OrderPreDiscountDTO.builder()
								.customerSapCode(customerSapCode)
								.build());
			});
		});
		And("[创建订单提交校验12]当前存在如下的整单折扣申请单信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验12]当前存在如下的整单折扣申请单商品信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验12]当前存在如下的整单折扣分配计划信息", () -> {
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
		And("[创建订单提交校验12]当前存在如下的整单折扣分配计划详细信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验12]添加商品", (DataTable dataTable) -> {
			log.info("添加商品");
		});
		And("[创建订单提交校验12]选择项目", (DataTable dataTable) -> {
			log.info("选择项目");
		});
		And("[创建订单提交校验12]当前存在如下的商品禁销信息", (DataTable dataTable) -> {
			saleOrgControlledProductId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			permittedSalesRegionId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			orderPermittedSalesCustomerId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			dataTable.asMaps().stream().forEach(caseData -> {
				String saleOrgSapCode = Optional.ofNullable(caseData.get("saleOrgSapCode")).orElse("");
				String regionCode = Optional.ofNullable(caseData.get("regionCode")).orElse("");
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String permittedSaleQuantityRegion = Optional.ofNullable(caseData.get("permittedSaleQuantityRegion")).orElse("");
				String hasSaleQuantityUnits = Optional.ofNullable(caseData.get("hasSaleQuantityUnits")).orElse("");
				String type = Optional.ofNullable(caseData.get("type")).orElse("");
				String customerName = Optional.ofNullable(caseData.get("customerName")).orElse("");
				String unitCode = Optional.ofNullable(caseData.get("unitCode")).orElse("");
				String unitName = Optional.ofNullable(caseData.get("unitName")).orElse("");
				String customerEntZtCode = Optional.ofNullable(caseData.get("customerEntZtCode")).orElse("");
				String customerEntSapCode = Optional.ofNullable(caseData.get("customerEntSapCode")).orElse("");
				String customerEntCrmCode = Optional.ofNullable(caseData.get("customerEntCrmCode")).orElse("");
				String saleOrgEffectiveState = Optional.ofNullable(caseData.get("saleOrgEffectiveState")).orElse("");
				String regionEffectiveState = Optional.ofNullable(caseData.get("regionEffectiveState")).orElse("");
				String customerEffectiveState = Optional.ofNullable(caseData.get("customerEffectiveState")).orElse("");
				String hasSaleQuantityCustomer = Optional.ofNullable(caseData.get("hasSaleQuantityCustomer")).orElse("");
				String permittedSaleQuantityCustomer = Optional.ofNullable(caseData.get("permittedSaleQuantityCustomer")).orElse("");
				permittedSaleBeginDate = new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis());
				permittedSaleEndDate = new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis());
				orderBatchInsertData01.insertOrderSaleOrgControlledProductData01(OrderSaleOrgControlledProductDTO.builder().saleOrgControlledProductId(saleOrgControlledProductId).saleOrgSapCode(saleOrgSapCode).productSapSku(itemSapCode).effectiveState(saleOrgEffectiveState).createPerson("at").createTime(createTime).build());
				orderBatchInsertData01.insertOrderPermittedSalesRegionData01(OrderPermittedSalesRegionDTO.builder().orderPermittedSalesRegionId(permittedSalesRegionId).saleOrgControlledProductId(saleOrgControlledProductId).regionCode(regionCode).regionName(RegionEnum.fetchName(regionCode)).permittedSaleQuantity(permittedSaleQuantityRegion).unitCode(unitCode).unitName(unitName).permittedSaleBeginDate(permittedSaleBeginDate).permittedSaleEndDate(permittedSaleEndDate).effectiveState(regionEffectiveState).createPerson("at").createTime(createTime).build());
				orderBatchInsertData01.insertOrderPermittedSalesCustomerData01(OrderPermittedSalesCustomerDTO.builder().orderPermittedSalesCustomerId(orderPermittedSalesCustomerId).saleOrgControlledProductId(saleOrgControlledProductId).permittedSalesRegionId(permittedSalesRegionId).customerEntZtCode(customerEntZtCode).customerEntSapCode(customerEntSapCode).customerEntCrmCode(customerEntCrmCode).customerName(customerName).permittedSaleQuantity(permittedSaleQuantityCustomer).hasSaleQuantity(hasSaleQuantityCustomer).type(type).effectiveState(customerEffectiveState).createPerson("at").createTime(createTime).build());
			});

			log.info("设置商品禁销信息");
		});
		Then("[创建订单提交校验12]创建订单应校验失败且提示错误信息", (DataTable dataTable) -> {
			String totalInlineSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("totalInline")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
			String preDiscountTotalSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("preDiscountTotal")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
			String orderDiscountTotalSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("orderDiscountTotal")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
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
			OrderDiscountCalcCalculateReqDTO calculateReq = orderApiOrderManagement01.discountCalcAfterData(OrderDataDTO.builder()
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
							.build())
					.build());
			Response createOrderSubmitResponse = orderApiOrderManagement01.createOrderBefore(OrderDataDTO.builder()
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
					.calculateReq(calculateReq)
					.build());
			createOrderSubmitResponse.then().body("resultCode", Matchers.equalTo("100000"), "resultMsg", Matchers.equalTo("以下商品为限销商品,无法下单:\n商品编码:10000841,商品描述:醒脑静注射液，10ml/支，2支/盒，240盒/箱,原因:限销品，剩余数量不足，当前可购的最大数量：4.00盒,订单购买数量:5盒\n"));
			priceBatchClearData01.clearPirceData01("at");
			orderBatchClearData01.clearOrderPreDiscountData01("at");
			orderBatchClearData01.clearDiscountData01("at");
			orderBatchClearData01.clearPermittedSalesData01("at");
		});
	}

	private void scenario13() {
		Given("[创建订单提交校验13]当前存在如下客户信息", (DataTable dataTable) -> {
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
		When("[创建订单提交校验13]当前存在如下的预折商品信息", (DataTable dataTable) -> {
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
						.build());
			});
		});
		And("[创建订单提交校验13]当前存在如下的预折折扣信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				BigDecimal calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(8, RoundingMode.HALF_UP);
				String effectiveState = Optional.ofNullable(caseData.get("effectiveState")).orElse("");
				orderBatchUpdateData01.updateOrderPreDiscountDetailCalculationValue(OrderPreDiscountDetailDTO.builder()
								.effectiveState(effectiveState)
								.calculationValue(calculationValue)
								.itemSapCode(itemSapCode)
								.build(),
						OrderPreDiscountDTO.builder()
								.customerSapCode(customerSapCode)
								.build());
			});
		});
		And("[创建订单提交校验13]当前存在如下的整单折扣申请单信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验13]当前存在如下的整单折扣申请单商品信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验13]当前存在如下的整单折扣分配计划信息", () -> {
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
		And("[创建订单提交校验13]当前存在如下的整单折扣分配计划详细信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验13]添加商品", (DataTable dataTable) -> {
			log.info("添加商品");
		});
		And("[创建订单提交校验13]选择项目", (DataTable dataTable) -> {
			log.info("选择项目");
		});
		And("[创建订单提交校验13]当前存在如下的商品禁销信息", (DataTable dataTable) -> {
			saleOrgControlledProductId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			permittedSalesRegionId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			orderPermittedSalesCustomerId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			dataTable.asMaps().stream().forEach(caseData -> {
				String saleOrgSapCode = Optional.ofNullable(caseData.get("saleOrgSapCode")).orElse("");
				String regionCode = Optional.ofNullable(caseData.get("regionCode")).orElse("");
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String permittedSaleQuantityRegion = Optional.ofNullable(caseData.get("permittedSaleQuantityRegion")).orElse("");
				String hasSaleQuantityUnits = Optional.ofNullable(caseData.get("hasSaleQuantityUnits")).orElse("");
				String type = Optional.ofNullable(caseData.get("type")).orElse("");
				String customerName = Optional.ofNullable(caseData.get("customerName")).orElse("");
				String unitCode = Optional.ofNullable(caseData.get("unitCode")).orElse("");
				String unitName = Optional.ofNullable(caseData.get("unitName")).orElse("");
				String customerEntZtCode = Optional.ofNullable(caseData.get("customerEntZtCode")).orElse("");
				String customerEntSapCode = Optional.ofNullable(caseData.get("customerEntSapCode")).orElse("");
				String customerEntCrmCode = Optional.ofNullable(caseData.get("customerEntCrmCode")).orElse("");
				String saleOrgEffectiveState = Optional.ofNullable(caseData.get("saleOrgEffectiveState")).orElse("");
				String regionEffectiveState = Optional.ofNullable(caseData.get("regionEffectiveState")).orElse("");
				String customerEffectiveState = Optional.ofNullable(caseData.get("customerEffectiveState")).orElse("");
				String hasSaleQuantityCustomer = Optional.ofNullable(caseData.get("hasSaleQuantityCustomer")).orElse("");
				String permittedSaleQuantityCustomer = Optional.ofNullable(caseData.get("permittedSaleQuantityCustomer")).orElse("");
				permittedSaleBeginDate = new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis());
				permittedSaleEndDate = new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis());
				orderBatchInsertData01.insertOrderSaleOrgControlledProductData01(OrderSaleOrgControlledProductDTO.builder().saleOrgControlledProductId(saleOrgControlledProductId).saleOrgSapCode(saleOrgSapCode).productSapSku(itemSapCode).effectiveState(saleOrgEffectiveState).createPerson("at").createTime(createTime).build());
				orderBatchInsertData01.insertOrderPermittedSalesRegionData01(OrderPermittedSalesRegionDTO.builder().orderPermittedSalesRegionId(permittedSalesRegionId).saleOrgControlledProductId(saleOrgControlledProductId).regionCode(regionCode).regionName(RegionEnum.fetchName(regionCode)).permittedSaleQuantity(permittedSaleQuantityRegion).unitCode(unitCode).unitName(unitName).permittedSaleBeginDate(permittedSaleBeginDate).permittedSaleEndDate(permittedSaleEndDate).effectiveState(regionEffectiveState).createPerson("at").createTime(createTime).build());
				orderBatchInsertData01.insertOrderPermittedSalesCustomerData01(OrderPermittedSalesCustomerDTO.builder().orderPermittedSalesCustomerId(orderPermittedSalesCustomerId).saleOrgControlledProductId(saleOrgControlledProductId).permittedSalesRegionId(permittedSalesRegionId).customerEntZtCode(customerEntZtCode).customerEntSapCode(customerEntSapCode).customerEntCrmCode(customerEntCrmCode).customerName(customerName).permittedSaleQuantity(permittedSaleQuantityCustomer).hasSaleQuantity(hasSaleQuantityCustomer).type(type).effectiveState(customerEffectiveState).createPerson("at").createTime(createTime).build());
			});

			log.info("设置商品禁销信息");
		});
		And("[创建订单提交校验13]当前存在如下的商品一级商允销信息", (DataTable dataTable) -> {
			orderPermittedSalesCustomerId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			dataTable.asMaps().stream().forEach(caseData -> {
				String type = Optional.ofNullable(caseData.get("type")).orElse("");
				String permittedSaleQuantityCustomer = Optional.ofNullable(caseData.get("permittedSaleQuantityCustomer")).orElse("");
				String hasSaleQuantityCustomer = Optional.ofNullable(caseData.get("hasSaleQuantityCustomer")).orElse("");
				String customerEffectiveState = Optional.ofNullable(caseData.get("customerEffectiveState")).orElse("");
				orderBatchInsertData01.insertOrderPermittedSalesCustomerData01(OrderPermittedSalesCustomerDTO.builder().orderPermittedSalesCustomerId(orderPermittedSalesCustomerId).saleOrgControlledProductId(saleOrgControlledProductId).permittedSalesRegionId(permittedSalesRegionId).customerEntSapCode(customerSapCode).customerEntCrmCode(CompanyEnum.fetchCrmCode(customerSapCode)).customerEntZtCode(CompanyEnum.fetchZtCode(customerSapCode)).customerName(CompanyEnum.fetchName(customerSapCode)).permittedSaleQuantity(permittedSaleQuantityCustomer).hasSaleQuantity(hasSaleQuantityCustomer).type(type).effectiveState(customerEffectiveState).createPerson("at").createTime(createTime).build());
			});

			log.info("设置商品一级商允销信息");
		});
		Then("[创建订单提交校验13]创建订单应校验失败且提示错误信息", (DataTable dataTable) -> {
			String totalInlineSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("totalInline")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
			String preDiscountTotalSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("preDiscountTotal")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
			String orderDiscountTotalSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("orderDiscountTotal")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
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
			OrderDiscountCalcCalculateReqDTO calculateReq = orderApiOrderManagement01.discountCalcAfterData(OrderDataDTO.builder()
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
							.build())
					.build());
			Response createOrderSubmitResponse = orderApiOrderManagement01.createOrderBefore(OrderDataDTO.builder()
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
					.calculateReq(calculateReq)
					.build());
			createOrderSubmitResponse.then().body("resultCode", Matchers.equalTo("100000"), "resultMsg", Matchers.equalTo("以下商品为限销商品,无法下单:\n商品编码:10000841,商品描述:醒脑静注射液，10ml/支，2支/盒，240盒/箱,原因:限销品，剩余数量不足，当前可购的最大数量：4.00盒,订单购买数量:5盒\n"));
			priceBatchClearData01.clearPirceData01("at");
			orderBatchClearData01.clearOrderPreDiscountData01("at");
			orderBatchClearData01.clearDiscountData01("at");
			orderBatchClearData01.clearPermittedSalesData01("at");
		});
	}


	private void scenario14() {
		Given("[创建订单提交校验14]当前存在如下客户信息", (DataTable dataTable) -> {
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
		When("[创建订单提交校验14]当前存在如下的预折商品信息", (DataTable dataTable) -> {
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
						.build());
			});
		});
		And("[创建订单提交校验14]当前存在如下的预折折扣信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				BigDecimal calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(8, RoundingMode.HALF_UP);
				String effectiveState = Optional.ofNullable(caseData.get("effectiveState")).orElse("");
				orderBatchUpdateData01.updateOrderPreDiscountDetailCalculationValue(OrderPreDiscountDetailDTO.builder()
								.effectiveState(effectiveState)
								.calculationValue(calculationValue)
								.itemSapCode(itemSapCode)
								.build(),
						OrderPreDiscountDTO.builder()
								.customerSapCode(customerSapCode)
								.build());
			});
		});
		And("[创建订单提交校验14]当前存在如下的整单折扣申请单信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验14]当前存在如下的整单折扣申请单商品信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验14]当前存在如下的整单折扣分配计划信息", () -> {
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
		And("[创建订单提交校验14]当前存在如下的整单折扣分配计划详细信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验14]添加商品", (DataTable dataTable) -> {
			log.info("添加商品");
		});
		And("[创建订单提交校验14]选择项目", (DataTable dataTable) -> {
			log.info("选择项目");
		});
		And("[创建订单提交校验14]当前存在如下的商品禁销信息", (DataTable dataTable) -> {
			saleOrgControlledProductId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			permittedSalesRegionId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			orderPermittedSalesCustomerId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			dataTable.asMaps().stream().forEach(caseData -> {
				String saleOrgSapCode = Optional.ofNullable(caseData.get("saleOrgSapCode")).orElse("");
				String regionCode = Optional.ofNullable(caseData.get("regionCode")).orElse("");
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String permittedSaleQuantityRegion = Optional.ofNullable(caseData.get("permittedSaleQuantityRegion")).orElse("");
				String hasSaleQuantityUnits = Optional.ofNullable(caseData.get("hasSaleQuantityUnits")).orElse("");
				String type = Optional.ofNullable(caseData.get("type")).orElse("");
				String customerName = Optional.ofNullable(caseData.get("customerName")).orElse("");
				String unitCode = Optional.ofNullable(caseData.get("unitCode")).orElse("");
				String unitName = Optional.ofNullable(caseData.get("unitName")).orElse("");
				String customerEntZtCode = Optional.ofNullable(caseData.get("customerEntZtCode")).orElse("");
				String customerEntSapCode = Optional.ofNullable(caseData.get("customerEntSapCode")).orElse("");
				String customerEntCrmCode = Optional.ofNullable(caseData.get("customerEntCrmCode")).orElse("");
				String saleOrgEffectiveState = Optional.ofNullable(caseData.get("saleOrgEffectiveState")).orElse("");
				String regionEffectiveState = Optional.ofNullable(caseData.get("regionEffectiveState")).orElse("");
				String customerEffectiveState = Optional.ofNullable(caseData.get("customerEffectiveState")).orElse("");
				String hasSaleQuantityCustomer = Optional.ofNullable(caseData.get("hasSaleQuantityCustomer")).orElse("");
				String permittedSaleQuantityCustomer = Optional.ofNullable(caseData.get("permittedSaleQuantityCustomer")).orElse("");
				permittedSaleBeginDate = new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis());
				permittedSaleEndDate = new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis());
				orderBatchInsertData01.insertOrderSaleOrgControlledProductData01(OrderSaleOrgControlledProductDTO.builder().saleOrgControlledProductId(saleOrgControlledProductId).saleOrgSapCode(saleOrgSapCode).productSapSku(itemSapCode).effectiveState(saleOrgEffectiveState).createPerson("at").createTime(createTime).build());
				orderBatchInsertData01.insertOrderPermittedSalesRegionData01(OrderPermittedSalesRegionDTO.builder().orderPermittedSalesRegionId(permittedSalesRegionId).saleOrgControlledProductId(saleOrgControlledProductId).regionCode(regionCode).regionName(RegionEnum.fetchName(regionCode)).permittedSaleQuantity(permittedSaleQuantityRegion).unitCode(unitCode).unitName(unitName).permittedSaleBeginDate(permittedSaleBeginDate).permittedSaleEndDate(permittedSaleEndDate).effectiveState(regionEffectiveState).createPerson("at").createTime(createTime).build());
				orderBatchInsertData01.insertOrderPermittedSalesCustomerData01(OrderPermittedSalesCustomerDTO.builder().orderPermittedSalesCustomerId(orderPermittedSalesCustomerId).saleOrgControlledProductId(saleOrgControlledProductId).permittedSalesRegionId(permittedSalesRegionId).customerEntZtCode(customerEntZtCode).customerEntSapCode(customerEntSapCode).customerEntCrmCode(customerEntCrmCode).customerName(customerName).permittedSaleQuantity(permittedSaleQuantityCustomer).hasSaleQuantity(hasSaleQuantityCustomer).type(type).effectiveState(customerEffectiveState).createPerson("at").createTime(createTime).build());
			});

			log.info("设置商品禁销信息");
		});
		Then("[创建订单提交校验14]创建订单应校验失败且提示错误信息", (DataTable dataTable) -> {
			String totalInlineSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("totalInline")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
			String preDiscountTotalSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("preDiscountTotal")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
			String orderDiscountTotalSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("orderDiscountTotal")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
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
			OrderDiscountCalcCalculateReqDTO calculateReq = orderApiOrderManagement01.discountCalcAfterData(OrderDataDTO.builder()
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
							.build())
					.build());
			Response createOrderSubmitResponse = orderApiOrderManagement01.createOrderBefore(OrderDataDTO.builder()
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
					.calculateReq(calculateReq)
					.build());
			createOrderSubmitResponse.then().body("resultCode", Matchers.equalTo("100000"), "resultMsg", Matchers.equalTo("以下商品为限销商品,无法下单:\n商品编码:10000265,商品描述:柴胡滴丸，0.551g/袋，10袋/盒，300盒/箱，OTC,原因:限销品，客户所在大区未配置允销政策，不允许购买\n"));
			priceBatchClearData01.clearPirceData01("at");
			orderBatchClearData01.clearOrderPreDiscountData01("at");
			orderBatchClearData01.clearDiscountData01("at");
			orderBatchClearData01.clearPermittedSalesData01("at");
		});
	}


	private void scenario15() {
		Given("[创建订单提交校验15]当前存在如下客户信息", (DataTable dataTable) -> {
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
		When("[创建订单提交校验15]当前存在如下的预折商品信息", (DataTable dataTable) -> {
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
						.build());
			});
		});
		And("[创建订单提交校验15]当前存在如下的预折折扣信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				BigDecimal calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(8, RoundingMode.HALF_UP);
				String effectiveState = Optional.ofNullable(caseData.get("effectiveState")).orElse("");
				orderBatchUpdateData01.updateOrderPreDiscountDetailCalculationValue(OrderPreDiscountDetailDTO.builder()
								.effectiveState(effectiveState)
								.calculationValue(calculationValue)
								.itemSapCode(itemSapCode)
								.build(),
						OrderPreDiscountDTO.builder()
								.customerSapCode(customerSapCode)
								.build());
			});
		});
		And("[创建订单提交校验15]当前存在如下的整单折扣申请单信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验15]当前存在如下的整单折扣申请单商品信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验15]当前存在如下的整单折扣分配计划信息", () -> {
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
		And("[创建订单提交校验15]当前存在如下的整单折扣分配计划详细信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验15]添加商品", (DataTable dataTable) -> {
			log.info("添加商品");
		});
		And("[创建订单提交校验15]选择项目", (DataTable dataTable) -> {
			log.info("选择项目");
		});
		And("[创建订单提交校验15]当前存在如下的商品禁销信息", (DataTable dataTable) -> {
			saleOrgControlledProductId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			permittedSalesRegionId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			orderPermittedSalesCustomerId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			dataTable.asMaps().stream().forEach(caseData -> {
				String saleOrgSapCode = Optional.ofNullable(caseData.get("saleOrgSapCode")).orElse("");
				String regionCode = Optional.ofNullable(caseData.get("regionCode")).orElse("");
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String permittedSaleQuantityRegion = Optional.ofNullable(caseData.get("permittedSaleQuantityRegion")).orElse("");
				String hasSaleQuantityUnits = Optional.ofNullable(caseData.get("hasSaleQuantityUnits")).orElse("");
				String type = Optional.ofNullable(caseData.get("type")).orElse("");
				String customerName = Optional.ofNullable(caseData.get("customerName")).orElse("");
				String unitCode = Optional.ofNullable(caseData.get("unitCode")).orElse("");
				String unitName = Optional.ofNullable(caseData.get("unitName")).orElse("");
				String customerEntZtCode = Optional.ofNullable(caseData.get("customerEntZtCode")).orElse("");
				String customerEntSapCode = Optional.ofNullable(caseData.get("customerEntSapCode")).orElse("");
				String customerEntCrmCode = Optional.ofNullable(caseData.get("customerEntCrmCode")).orElse("");
				String saleOrgEffectiveState = Optional.ofNullable(caseData.get("saleOrgEffectiveState")).orElse("");
				String regionEffectiveState = Optional.ofNullable(caseData.get("regionEffectiveState")).orElse("");
				String customerEffectiveState = Optional.ofNullable(caseData.get("customerEffectiveState")).orElse("");
				String hasSaleQuantityCustomer = Optional.ofNullable(caseData.get("hasSaleQuantityCustomer")).orElse("");
				String permittedSaleQuantityCustomer = Optional.ofNullable(caseData.get("permittedSaleQuantityCustomer")).orElse("");
				permittedSaleBeginDate = new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis());
				permittedSaleEndDate = new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis());
				orderBatchInsertData01.insertOrderSaleOrgControlledProductData01(OrderSaleOrgControlledProductDTO.builder().saleOrgControlledProductId(saleOrgControlledProductId).saleOrgSapCode(saleOrgSapCode).productSapSku(itemSapCode).effectiveState(saleOrgEffectiveState).createPerson("at").createTime(createTime).build());
				orderBatchInsertData01.insertOrderPermittedSalesRegionData01(OrderPermittedSalesRegionDTO.builder().orderPermittedSalesRegionId(permittedSalesRegionId).saleOrgControlledProductId(saleOrgControlledProductId).regionCode(regionCode).regionName(RegionEnum.fetchName(regionCode)).permittedSaleQuantity(permittedSaleQuantityRegion).unitCode(unitCode).unitName(unitName).permittedSaleBeginDate(permittedSaleBeginDate).permittedSaleEndDate(permittedSaleEndDate).effectiveState(regionEffectiveState).createPerson("at").createTime(createTime).build());
				orderBatchInsertData01.insertOrderPermittedSalesCustomerData01(OrderPermittedSalesCustomerDTO.builder().orderPermittedSalesCustomerId(orderPermittedSalesCustomerId).saleOrgControlledProductId(saleOrgControlledProductId).permittedSalesRegionId(permittedSalesRegionId).customerEntZtCode(customerEntZtCode).customerEntSapCode(customerEntSapCode).customerEntCrmCode(customerEntCrmCode).customerName(customerName).permittedSaleQuantity(permittedSaleQuantityCustomer).hasSaleQuantity(hasSaleQuantityCustomer).type(type).effectiveState(customerEffectiveState).createPerson("at").createTime(createTime).build());
			});

			log.info("设置商品禁销信息");
		});
		Then("[创建订单提交校验15]创建订单应校验失败且提示错误信息", (DataTable dataTable) -> {
			String totalInlineSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("totalInline")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
			String preDiscountTotalSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("preDiscountTotal")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
			String orderDiscountTotalSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("orderDiscountTotal")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
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
			OrderDiscountCalcCalculateReqDTO calculateReq = orderApiOrderManagement01.discountCalcAfterData(OrderDataDTO.builder()
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
							.build())
					.build());
			Response createOrderSubmitResponse = orderApiOrderManagement01.createOrderBefore(OrderDataDTO.builder()
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
					.calculateReq(calculateReq)
					.build());
			createOrderSubmitResponse.then().body("resultCode", Matchers.equalTo("100000"), "resultMsg", Matchers.equalTo("以下商品为限销商品,无法下单:\n商品编码:10000265,商品描述:柴胡滴丸，0.551g/袋，10袋/盒，300盒/箱，OTC,原因:限销品，剩余数量不足，当前可购的最大数量：2.00盒,订单购买数量:3盒\n"));
			priceBatchClearData01.clearPirceData01("at");
			orderBatchClearData01.clearOrderPreDiscountData01("at");
			orderBatchClearData01.clearDiscountData01("at");
			orderBatchClearData01.clearPermittedSalesData01("at");
		});
	}

	private void scenario16() {
		Given("[创建订单提交校验16]当前存在如下客户信息", (DataTable dataTable) -> {
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
		When("[创建订单提交校验16]当前存在如下的预折商品信息", (DataTable dataTable) -> {
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
						.build());
			});
		});
		And("[创建订单提交校验16]当前存在如下的预折折扣信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				BigDecimal calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(8, RoundingMode.HALF_UP);
				String effectiveState = Optional.ofNullable(caseData.get("effectiveState")).orElse("");
				orderBatchUpdateData01.updateOrderPreDiscountDetailCalculationValue(OrderPreDiscountDetailDTO.builder()
								.effectiveState(effectiveState)
								.calculationValue(calculationValue)
								.itemSapCode(itemSapCode)
								.build(),
						OrderPreDiscountDTO.builder()
								.customerSapCode(customerSapCode)
								.build());
			});
		});
		And("[创建订单提交校验16]当前存在如下的整单折扣申请单信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验16]当前存在如下的整单折扣申请单商品信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验16]当前存在如下的整单折扣分配计划信息", () -> {
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
		And("[创建订单提交校验16]当前存在如下的整单折扣分配计划详细信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验16]添加商品", (DataTable dataTable) -> {
			log.info("添加商品");
		});
		And("[创建订单提交校验16]选择项目", (DataTable dataTable) -> {
			log.info("选择项目");
		});
		And("[创建订单提交校验16]当前存在如下的商品禁销信息", (DataTable dataTable) -> {
			saleOrgControlledProductId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			permittedSalesRegionId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			orderPermittedSalesCustomerId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			dataTable.asMaps().stream().forEach(caseData -> {
				String saleOrgSapCode = Optional.ofNullable(caseData.get("saleOrgSapCode")).orElse("");
				String regionCode = Optional.ofNullable(caseData.get("regionCode")).orElse("");
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String permittedSaleQuantityRegion = Optional.ofNullable(caseData.get("permittedSaleQuantityRegion")).orElse("");
				String hasSaleQuantityUnits = Optional.ofNullable(caseData.get("hasSaleQuantityUnits")).orElse("");
				String type = Optional.ofNullable(caseData.get("type")).orElse("");
				String customerName = Optional.ofNullable(caseData.get("customerName")).orElse("");
				String unitCode = Optional.ofNullable(caseData.get("unitCode")).orElse("");
				String unitName = Optional.ofNullable(caseData.get("unitName")).orElse("");
				String customerEntZtCode = Optional.ofNullable(caseData.get("customerEntZtCode")).orElse("");
				String customerEntSapCode = Optional.ofNullable(caseData.get("customerEntSapCode")).orElse("");
				String customerEntCrmCode = Optional.ofNullable(caseData.get("customerEntCrmCode")).orElse("");
				String saleOrgEffectiveState = Optional.ofNullable(caseData.get("saleOrgEffectiveState")).orElse("");
				String regionEffectiveState = Optional.ofNullable(caseData.get("regionEffectiveState")).orElse("");
				String customerEffectiveState = Optional.ofNullable(caseData.get("customerEffectiveState")).orElse("");
				String hasSaleQuantityCustomer = Optional.ofNullable(caseData.get("hasSaleQuantityCustomer")).orElse("");
				String permittedSaleQuantityCustomer = Optional.ofNullable(caseData.get("permittedSaleQuantityCustomer")).orElse("");
				permittedSaleBeginDate = new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis());
				permittedSaleEndDate = new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis());
				orderBatchInsertData01.insertOrderSaleOrgControlledProductData01(OrderSaleOrgControlledProductDTO.builder().saleOrgControlledProductId(saleOrgControlledProductId).saleOrgSapCode(saleOrgSapCode).productSapSku(itemSapCode).effectiveState(saleOrgEffectiveState).createPerson("at").createTime(createTime).build());
				orderBatchInsertData01.insertOrderPermittedSalesRegionData01(OrderPermittedSalesRegionDTO.builder().orderPermittedSalesRegionId(permittedSalesRegionId).saleOrgControlledProductId(saleOrgControlledProductId).regionCode(regionCode).regionName(RegionEnum.fetchName(regionCode)).permittedSaleQuantity(permittedSaleQuantityRegion).unitCode(unitCode).unitName(unitName).permittedSaleBeginDate(permittedSaleBeginDate).permittedSaleEndDate(permittedSaleEndDate).effectiveState(regionEffectiveState).createPerson("at").createTime(createTime).build());
				orderBatchInsertData01.insertOrderPermittedSalesCustomerData01(OrderPermittedSalesCustomerDTO.builder().orderPermittedSalesCustomerId(orderPermittedSalesCustomerId).saleOrgControlledProductId(saleOrgControlledProductId).permittedSalesRegionId(permittedSalesRegionId).customerEntZtCode(customerEntZtCode).customerEntSapCode(customerEntSapCode).customerEntCrmCode(customerEntCrmCode).customerName(customerName).permittedSaleQuantity(permittedSaleQuantityCustomer).hasSaleQuantity(hasSaleQuantityCustomer).type(type).effectiveState(customerEffectiveState).createPerson("at").createTime(createTime).build());
			});

			log.info("设置商品禁销信息");
		});
		And("[创建订单提交校验16]当前存在如下的商品一级商允销信息", (DataTable dataTable) -> {
			orderPermittedSalesCustomerId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			dataTable.asMaps().stream().forEach(caseData -> {
				String type = Optional.ofNullable(caseData.get("type")).orElse("");
				String permittedSaleQuantityCustomer = Optional.ofNullable(caseData.get("permittedSaleQuantityCustomer")).orElse("");
				String hasSaleQuantityCustomer = Optional.ofNullable(caseData.get("hasSaleQuantityCustomer")).orElse("");
				String customerEffectiveState = Optional.ofNullable(caseData.get("customerEffectiveState")).orElse("");
				orderBatchInsertData01.insertOrderPermittedSalesCustomerData01(OrderPermittedSalesCustomerDTO.builder().orderPermittedSalesCustomerId(orderPermittedSalesCustomerId).saleOrgControlledProductId(saleOrgControlledProductId).permittedSalesRegionId(permittedSalesRegionId).customerEntSapCode(customerSapCode).customerEntCrmCode(CompanyEnum.fetchCrmCode(customerSapCode)).customerEntZtCode(CompanyEnum.fetchZtCode(customerSapCode)).customerName(CompanyEnum.fetchName(customerSapCode)).permittedSaleQuantity(permittedSaleQuantityCustomer).hasSaleQuantity(hasSaleQuantityCustomer).type(type).effectiveState(customerEffectiveState).createPerson("at").createTime(createTime).build());
			});

			log.info("设置商品一级商允销信息");
		});
		Then("[创建订单提交校验16]创建订单应校验失败且提示错误信息", (DataTable dataTable) -> {
			String totalInlineSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("totalInline")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
			String preDiscountTotalSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("preDiscountTotal")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
			String orderDiscountTotalSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("orderDiscountTotal")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
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
			OrderDiscountCalcCalculateReqDTO calculateReq = orderApiOrderManagement01.discountCalcAfterData(OrderDataDTO.builder()
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
							.build())
					.build());
			Response createOrderSubmitResponse = orderApiOrderManagement01.createOrderBefore(OrderDataDTO.builder()
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
					.calculateReq(calculateReq)
					.build());
			createOrderSubmitResponse.then().body("resultCode", Matchers.equalTo("100000"), "resultMsg", Matchers.equalTo("以下商品为限销商品,无法下单:\n商品编码:10000265,商品描述:柴胡滴丸，0.551g/袋，10袋/盒，300盒/箱，OTC,原因:限销品，剩余数量不足，当前可购的最大数量：2.00盒,订单购买数量:3盒\n"));
			priceBatchClearData01.clearPirceData01("at");
			orderBatchClearData01.clearOrderPreDiscountData01("at");
			orderBatchClearData01.clearDiscountData01("at");
			orderBatchClearData01.clearPermittedSalesData01("at");
		});
	}

	private void scenario17() {
		Given("[创建订单提交校验17]当前存在如下客户信息", (DataTable dataTable) -> {
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
		When("[创建订单提交校验17]当前存在如下的预折商品信息", (DataTable dataTable) -> {
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
						.build());
			});
		});
		And("[创建订单提交校验17]当前存在如下的预折折扣信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				BigDecimal calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(8, RoundingMode.HALF_UP);
				String effectiveState = Optional.ofNullable(caseData.get("effectiveState")).orElse("");
				orderBatchUpdateData01.updateOrderPreDiscountDetailCalculationValue(OrderPreDiscountDetailDTO.builder()
								.effectiveState(effectiveState)
								.calculationValue(calculationValue)
								.itemSapCode(itemSapCode)
								.build(),
						OrderPreDiscountDTO.builder()
								.customerSapCode(customerSapCode)
								.build());
			});
		});
		And("[创建订单提交校验17]当前存在如下的整单折扣申请单信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验17]当前存在如下的整单折扣申请单商品信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验17]当前存在如下的整单折扣分配计划信息", () -> {
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
		And("[创建订单提交校验17]当前存在如下的整单折扣分配计划详细信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验17]添加商品", (DataTable dataTable) -> {
			log.info("添加商品");
		});
		And("[创建订单提交校验17]选择项目", (DataTable dataTable) -> {
			log.info("选择项目");
		});
		And("[创建订单提交校验17]当前存在如下的商品禁销信息", (DataTable dataTable) -> {
			saleOrgControlledProductId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			permittedSalesRegionId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			orderPermittedSalesCustomerId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			dataTable.asMaps().stream().forEach(caseData -> {
				String saleOrgSapCode = Optional.ofNullable(caseData.get("saleOrgSapCode")).orElse("");
				String regionCode = Optional.ofNullable(caseData.get("regionCode")).orElse("");
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String permittedSaleQuantityRegion = Optional.ofNullable(caseData.get("permittedSaleQuantityRegion")).orElse("");
				String hasSaleQuantityUnits = Optional.ofNullable(caseData.get("hasSaleQuantityUnits")).orElse("");
				String type = Optional.ofNullable(caseData.get("type")).orElse("");
				String customerName = Optional.ofNullable(caseData.get("customerName")).orElse("");
				String unitCode = Optional.ofNullable(caseData.get("unitCode")).orElse("");
				String unitName = Optional.ofNullable(caseData.get("unitName")).orElse("");
				String customerEntZtCode = Optional.ofNullable(caseData.get("customerEntZtCode")).orElse("");
				String customerEntSapCode = Optional.ofNullable(caseData.get("customerEntSapCode")).orElse("");
				String customerEntCrmCode = Optional.ofNullable(caseData.get("customerEntCrmCode")).orElse("");
				String saleOrgEffectiveState = Optional.ofNullable(caseData.get("saleOrgEffectiveState")).orElse("");
				String regionEffectiveState = Optional.ofNullable(caseData.get("regionEffectiveState")).orElse("");
				String customerEffectiveState = Optional.ofNullable(caseData.get("customerEffectiveState")).orElse("");
				String hasSaleQuantityCustomer = Optional.ofNullable(caseData.get("hasSaleQuantityCustomer")).orElse("");
				String permittedSaleQuantityCustomer = Optional.ofNullable(caseData.get("permittedSaleQuantityCustomer")).orElse("");
				permittedSaleBeginDate = new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis());
				permittedSaleEndDate = new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis());
				orderBatchInsertData01.insertOrderSaleOrgControlledProductData01(OrderSaleOrgControlledProductDTO.builder().saleOrgControlledProductId(saleOrgControlledProductId).saleOrgSapCode(saleOrgSapCode).productSapSku(itemSapCode).effectiveState(saleOrgEffectiveState).createPerson("at").createTime(createTime).build());
				orderBatchInsertData01.insertOrderPermittedSalesRegionData01(OrderPermittedSalesRegionDTO.builder().orderPermittedSalesRegionId(permittedSalesRegionId).saleOrgControlledProductId(saleOrgControlledProductId).regionCode(regionCode).regionName(RegionEnum.fetchName(regionCode)).permittedSaleQuantity(permittedSaleQuantityRegion).unitCode(unitCode).unitName(unitName).permittedSaleBeginDate(permittedSaleBeginDate).permittedSaleEndDate(permittedSaleEndDate).effectiveState(regionEffectiveState).createPerson("at").createTime(createTime).build());
				orderBatchInsertData01.insertOrderPermittedSalesCustomerData01(OrderPermittedSalesCustomerDTO.builder().orderPermittedSalesCustomerId(orderPermittedSalesCustomerId).saleOrgControlledProductId(saleOrgControlledProductId).permittedSalesRegionId(permittedSalesRegionId).customerEntZtCode(customerEntZtCode).customerEntSapCode(customerEntSapCode).customerEntCrmCode(customerEntCrmCode).customerName(customerName).permittedSaleQuantity(permittedSaleQuantityCustomer).hasSaleQuantity(hasSaleQuantityCustomer).type(type).effectiveState(customerEffectiveState).createPerson("at").createTime(createTime).build());
			});

			log.info("设置商品禁销信息");
		});
		And("[创建订单提交校验17]当前存在如下的商品一级商允销信息", (DataTable dataTable) -> {
			orderPermittedSalesCustomerId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			dataTable.asMaps().stream().forEach(caseData -> {
				String type = Optional.ofNullable(caseData.get("type")).orElse("");
				String permittedSaleQuantityCustomer = Optional.ofNullable(caseData.get("permittedSaleQuantityCustomer")).orElse("");
				String hasSaleQuantityCustomer = Optional.ofNullable(caseData.get("hasSaleQuantityCustomer")).orElse("");
				String customerEffectiveState = Optional.ofNullable(caseData.get("customerEffectiveState")).orElse("");
				orderBatchInsertData01.insertOrderPermittedSalesCustomerData01(OrderPermittedSalesCustomerDTO.builder().orderPermittedSalesCustomerId(orderPermittedSalesCustomerId).saleOrgControlledProductId(saleOrgControlledProductId).permittedSalesRegionId(permittedSalesRegionId).customerEntSapCode(customerSapCode).customerEntCrmCode(CompanyEnum.fetchCrmCode(customerSapCode)).customerEntZtCode(CompanyEnum.fetchZtCode(customerSapCode)).customerName(CompanyEnum.fetchName(customerSapCode)).permittedSaleQuantity(permittedSaleQuantityCustomer).hasSaleQuantity(hasSaleQuantityCustomer).type(type).effectiveState(customerEffectiveState).createPerson("at").createTime(createTime).build());
			});

			log.info("设置商品一级商允销信息");
		});
		Then("[创建订单提交校验17]创建订单应校验失败且提示错误信息", (DataTable dataTable) -> {
			String totalInlineSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("totalInline")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
			String preDiscountTotalSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("preDiscountTotal")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
			String orderDiscountTotalSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("orderDiscountTotal")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
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
			OrderDiscountCalcCalculateReqDTO calculateReq = orderApiOrderManagement01.discountCalcAfterData(OrderDataDTO.builder()
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
							.build())
					.build());
			//  销售组织和客户关系变化
			channelBatchUpdateData01.updateChannelSapSaleUnassociated(saleOrgSapCode, "auto", customerSapCode);
			Response createOrderSubmitResponse = orderApiOrderManagement01.createOrderBefore(OrderDataDTO.builder()
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
					.calculateReq(calculateReq)
					.build());
			createOrderSubmitResponse.then().body("resultCode", Matchers.equalTo("100000"), "resultMsg", Matchers.equalTo("销售组织与客户关系校验失败：未查询到客户信息"));
			priceBatchClearData01.clearPirceData01("at");
			orderBatchClearData01.clearOrderPreDiscountData01("at");
			orderBatchClearData01.clearDiscountData01("at");
			orderBatchClearData01.clearPermittedSalesData01("at");
			channelBatchUpdateData01.updateChannelSapSaleAssociated("auto", saleOrgSapCode, customerSapCode);
		});
	}

	private void scenario18() {
		Given("[创建订单提交校验18]当前存在如下客户信息", (DataTable dataTable) -> {
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
		When("[创建订单提交校验18]当前存在如下的预折商品信息", (DataTable dataTable) -> {
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
						.build());
			});
		});
		And("[创建订单提交校验18]当前存在如下的预折折扣信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				BigDecimal calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(8, RoundingMode.HALF_UP);
				String effectiveState = Optional.ofNullable(caseData.get("effectiveState")).orElse("");
				orderBatchUpdateData01.updateOrderPreDiscountDetailCalculationValue(OrderPreDiscountDetailDTO.builder()
								.effectiveState(effectiveState)
								.calculationValue(calculationValue)
								.itemSapCode(itemSapCode)
								.build(),
						OrderPreDiscountDTO.builder()
								.customerSapCode(customerSapCode)
								.build());
			});
		});
		And("[创建订单提交校验18]当前存在如下的整单折扣申请单信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验18]当前存在如下的整单折扣申请单商品信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验18]当前存在如下的整单折扣分配计划信息", () -> {
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
		And("[创建订单提交校验18]当前存在如下的整单折扣分配计划详细信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验18]添加商品", (DataTable dataTable) -> {
			log.info("添加商品");
		});
		And("[创建订单提交校验18]选择项目", (DataTable dataTable) -> {
			log.info("选择项目");
		});
		Then("[创建订单提交校验18]创建订单应校验失败且提示错误信息", (DataTable dataTable) -> {
			String totalInlineSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("totalInline")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
			String preDiscountTotalSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("preDiscountTotal")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
			String orderDiscountTotalSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("orderDiscountTotal")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
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
			OrderDiscountCalcCalculateReqDTO calculateReq = orderApiOrderManagement01.discountCalcAfterData(OrderDataDTO.builder().orderType("STANDARD").saleOrgSapCode(saleOrgSapCode).saleOrgName(SaleOrgEnum.fetchName(saleOrgSapCode)).regionCode(regionCode).regionName(regionName).customerEntSapCode(customerSapCode).customerEntCrmCode(CompanyEnum.fetchCrmCode(customerSapCode)).customerEntZtCode(CompanyEnum.fetchZtCode(customerSapCode)).customerName(CompanyEnum.fetchName(customerSapCode)).invoiceType("VAT_SPECIAL_INVOICE").shippingPolicy("FULL_SHIPMENT").priority("NORMAL").paymentMethod("PRE_PAYMENT").shippingRecipient(shippingRecipient).shippingTelephone(shippingTelephone).shippingAddress(shippingAddress).remark("自动化测试").calculateReq(OrderDiscountCalcCalculateReqDTO.builder().totalInline(totalInlineSum).preDiscountTotal(preDiscountTotalSum).orderDiscountTotal(orderDiscountTotalSum).groupList(Arrays.asList(OrderDiscountCalcCalculateReqGroupListDTO.builder().itemGroupCode("01").itemGroupName("成品药-非管制药品").distributionChannelCode("22").distributionChannelName("分销-非医疗").totalInline(totalInlineSum).preDiscountTotal(preDiscountTotalSum).orderDiscountTotal(orderDiscountTotalSum).list(orderDiscountCalcCalculateReqGroupListListDTOS).build())).build()).build());

			Response createOrderSubmitResponse = orderApiOrderManagement01.createOrderBefore(OrderDataDTO.builder().orderType("STANDARD").saleOrgSapCode(saleOrgSapCode).saleOrgName(SaleOrgEnum.fetchName(saleOrgSapCode)).regionCode(regionCode).regionName(regionName).customerEntSapCode(customerSapCode).customerEntCrmCode(CompanyEnum.fetchCrmCode(customerSapCode)).customerEntZtCode(CompanyEnum.fetchZtCode(customerSapCode)).customerName(CompanyEnum.fetchName(customerSapCode)).invoiceType("VAT_SPECIAL_INVOICE").shippingPolicy("FULL_SHIPMENT").priority("NORMAL").paymentMethod("PRE_PAYMENT").shippingRecipient(shippingRecipient).shippingTelephone(shippingTelephone).shippingAddress(shippingAddress).remark("自动化测试").calculateReq(calculateReq).build());
			createOrderSubmitResponse.then().body("resultCode", Matchers.equalTo("100000"), "resultMsg", Matchers.equalTo("商品 10000841 醒脑静注射液，10ml/支，2支/盒，240盒/箱 数量非整箱发货，请修改数量"));
			priceBatchClearData01.clearPirceData01("at");
			orderBatchClearData01.clearOrderPreDiscountData01("at");
			orderBatchClearData01.clearDiscountData01("at");
			orderBatchClearData01.clearPermittedSalesData01("at");

		});
	}

	private void scenario19() {
		Given("[创建订单提交校验19]当前存在如下客户信息", (DataTable dataTable) -> {
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
		When("[创建订单提交校验19]当前存在如下的预折商品信息", (DataTable dataTable) -> {
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
						.build());
			});
		});
		And("[创建订单提交校验19]当前存在如下的预折折扣信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				BigDecimal calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(8, RoundingMode.HALF_UP);
				String effectiveState = Optional.ofNullable(caseData.get("effectiveState")).orElse("");
				orderBatchUpdateData01.updateOrderPreDiscountDetailCalculationValue(OrderPreDiscountDetailDTO.builder()
								.effectiveState(effectiveState)
								.calculationValue(calculationValue)
								.itemSapCode(itemSapCode)
								.build(),
						OrderPreDiscountDTO.builder()
								.customerSapCode(customerSapCode)
								.build());
			});
		});
		And("[创建订单提交校验19]当前存在如下的整单折扣申请单信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验19]当前存在如下的整单折扣申请单商品信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验19]当前存在如下的整单折扣分配计划信息", () -> {
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
		And("[创建订单提交校验19]当前存在如下的整单折扣分配计划详细信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验19]添加商品", (DataTable dataTable) -> {
			log.info("添加商品");
		});
		And("[创建订单提交校验19]选择项目", (DataTable dataTable) -> {
			log.info("选择项目");
		});
		And("[创建订单提交校验19]修改商品计价单位", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String unit = Optional.ofNullable(caseData.get("unit")).orElse("");
				String unitDesc = Optional.ofNullable(caseData.get("unitDesc")).orElse("");
				priceBatchUpdateData01.updatePriceUnit(unit, unitDesc, itemSapCode, customerSapCode);
			});
			log.info("修改商品计价单位");
		});
		Then("[创建订单提交校验19]创建订单应校验失败且提示错误信息", (DataTable dataTable) -> {
			String totalInlineSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("totalInline")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
			String preDiscountTotalSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("preDiscountTotal")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
			String orderDiscountTotalSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("orderDiscountTotal")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
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
				orderDiscountCalcCalculateReqGroupListListDTOS.add(OrderDiscountCalcCalculateReqGroupListListDTO.builder().orderWideDiscountSubject(orderWideDiscountSubject).discountAllocationPlanId(allocationPlanId).discountAllocationPlanDetailId(allocationPlanDetailId).discountId(id).preDiscountDetailId(id).itemSapCode(itemSapCode).itemName(ProductEnum.fetchProductName(itemSapCode)).itemDesc(ProductEnum.fetchProductSpec(itemSapCode)).itemGroupCode("01").itemGroupName("成品药-非管制药品").distributionChannelCode("22").distributionChannelName("分销-非医疗").unit("PC1").unitDesc("支").unitPrice(unitPrice).calculationValue(calculationValue).preDiscount(preDiscount).preType("CUSTOMER_PRE_DISCOUNT").calculationType("DISCOUNTED_PRICE").quantity(quantity).packingRatio("").totalInline(totalInline).preDiscountTotal(preDiscountTotal).needShip(needShip).baseUnitCode("HE").baseUnitName("盒").boxRatio(boxRatio).xunit("1").yunit("1").build());
			});
			OrderDiscountCalcCalculateReqDTO calculateReq = orderApiOrderManagement01.discountCalcAfterData(OrderDataDTO.builder().orderType("STANDARD").saleOrgSapCode(saleOrgSapCode).saleOrgName(SaleOrgEnum.fetchName(saleOrgSapCode)).regionCode(regionCode).regionName(regionName).customerEntSapCode(customerSapCode).customerEntCrmCode(CompanyEnum.fetchCrmCode(customerSapCode)).customerEntZtCode(CompanyEnum.fetchZtCode(customerSapCode)).customerName(CompanyEnum.fetchName(customerSapCode)).invoiceType("VAT_SPECIAL_INVOICE").shippingPolicy("FULL_SHIPMENT").priority("NORMAL").paymentMethod("PRE_PAYMENT").shippingRecipient(shippingRecipient).shippingTelephone(shippingTelephone).shippingAddress(shippingAddress).remark("自动化测试").calculateReq(OrderDiscountCalcCalculateReqDTO.builder().totalInline(totalInlineSum).preDiscountTotal(preDiscountTotalSum).orderDiscountTotal(orderDiscountTotalSum).groupList(Arrays.asList(OrderDiscountCalcCalculateReqGroupListDTO.builder().itemGroupCode("01").itemGroupName("成品药-非管制药品").distributionChannelCode("22").distributionChannelName("分销-非医疗").totalInline(totalInlineSum).preDiscountTotal(preDiscountTotalSum).orderDiscountTotal(orderDiscountTotalSum).list(orderDiscountCalcCalculateReqGroupListListDTOS).build())).build()).build());
			Response createOrderSubmitResponse = orderApiOrderManagement01.createOrderBefore(OrderDataDTO.builder().orderType("STANDARD").saleOrgSapCode(saleOrgSapCode).saleOrgName(SaleOrgEnum.fetchName(saleOrgSapCode)).regionCode(regionCode).regionName(regionName).customerEntSapCode(customerSapCode).customerEntCrmCode(CompanyEnum.fetchCrmCode(customerSapCode)).customerEntZtCode(CompanyEnum.fetchZtCode(customerSapCode)).customerName(CompanyEnum.fetchName(customerSapCode)).invoiceType("VAT_SPECIAL_INVOICE").shippingPolicy("FULL_SHIPMENT").priority("NORMAL").paymentMethod("PRE_PAYMENT").shippingRecipient(shippingRecipient).shippingTelephone(shippingTelephone).shippingAddress(shippingAddress).remark("自动化测试").calculateReq(calculateReq).build());
			createOrderSubmitResponse.then().body("resultCode", Matchers.equalTo("100000"), "resultMsg", Matchers.equalTo("商品 10000841 醒脑静注射液，10ml/支，2支/盒，240盒/箱 数量非整箱发货，请修改数量"));
			priceBatchClearData01.clearPirceData01("at");
			orderBatchClearData01.clearOrderPreDiscountData01("at");
			orderBatchClearData01.clearDiscountData01("at");
			orderBatchClearData01.clearPermittedSalesData01("at");
		});
	}

	private void scenario20() {
		Given("[创建订单提交校验20]当前存在如下客户信息", (DataTable dataTable) -> {
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
		When("[创建订单提交校验20]当前存在如下的预折商品信息", (DataTable dataTable) -> {
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
						.build());
			});
		});
		And("[创建订单提交校验20]当前存在如下的预折折扣信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				BigDecimal calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(8, RoundingMode.HALF_UP);
				String effectiveState = Optional.ofNullable(caseData.get("effectiveState")).orElse("");
				orderBatchUpdateData01.updateOrderPreDiscountDetailCalculationValue(OrderPreDiscountDetailDTO.builder()
								.effectiveState(effectiveState)
								.calculationValue(calculationValue)
								.itemSapCode(itemSapCode)
								.build(),
						OrderPreDiscountDTO.builder()
								.customerSapCode(customerSapCode)
								.build());
			});
		});
		And("[创建订单提交校验20]当前存在如下的整单折扣申请单信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验20]当前存在如下的整单折扣申请单商品信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验20]当前存在如下的整单折扣分配计划信息", () -> {
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
		And("[创建订单提交校验20]当前存在如下的整单折扣分配计划详细信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验20]添加商品", (DataTable dataTable) -> {
			log.info("添加商品");
		});
		And("[创建订单提交校验20]选择项目", (DataTable dataTable) -> {
			log.info("选择项目");
		});
		And("[创建订单提交校验20]设置商品中包装", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String mediumPackagingSpecification = Optional.ofNullable(caseData.get("mediumPackagingSpecification")).orElse("");
				String name = Optional.ofNullable(caseData.get("name")).orElse("");
				String quantity = Optional.ofNullable(caseData.get("quantity")).orElse("");
				itemBatchInsertData01.insertItMediumPackagingSpecificationData01(ItMediumPackagingSpecificationDTO.builder()
						.productCode(itemSapCode)
						.mediumPackagingSpecification(mediumPackagingSpecification)
						.name(name)
						.quantity(quantity)
						.createPerson("at")
						.createTime(createTime)
						.build());
			});
			log.info("设置商品中包装");
		});
		Then("[创建订单提交校验20]创建订单应校验失败且提示错误信息", (DataTable dataTable) -> {
			String totalInlineSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("totalInline")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
			String preDiscountTotalSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("preDiscountTotal")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
			String orderDiscountTotalSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("orderDiscountTotal")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
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
			OrderDiscountCalcCalculateReqDTO calculateReq = orderApiOrderManagement01.discountCalcAfterData(OrderDataDTO.builder()
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
							.build())
					.build());
			Response createOrderSubmitResponse = orderApiOrderManagement01.createOrderBefore(OrderDataDTO.builder()
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
					.calculateReq(calculateReq)
					.build());
			createOrderSubmitResponse.then().body("resultCode", Matchers.equalTo("100000"), "resultMsg", Matchers.equalTo("商品：10000841的数量不符合包装规格要求"));
			priceBatchClearData01.clearPirceData01("at");
			orderBatchClearData01.clearOrderPreDiscountData01("at");
			orderBatchClearData01.clearDiscountData01("at");
			orderBatchClearData01.clearPermittedSalesData01("at");
		});
	}


	private void scenario21() {
		Given("[创建订单提交校验21]当前存在如下客户信息", (DataTable dataTable) -> {
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
		When("[创建订单提交校验21]当前存在如下的预折商品信息", (DataTable dataTable) -> {
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
						.build());
			});
		});
		And("[创建订单提交校验21]当前存在如下的预折折扣信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				BigDecimal calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(8, RoundingMode.HALF_UP);
				String effectiveState = Optional.ofNullable(caseData.get("effectiveState")).orElse("");
				orderBatchUpdateData01.updateOrderPreDiscountDetailCalculationValue(OrderPreDiscountDetailDTO.builder()
								.effectiveState(effectiveState)
								.calculationValue(calculationValue)
								.itemSapCode(itemSapCode)
								.build(),
						OrderPreDiscountDTO.builder()
								.customerSapCode(customerSapCode)
								.build());
			});
		});
		And("[创建订单提交校验21]当前存在如下的整单折扣申请单信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验21]当前存在如下的整单折扣申请单商品信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验21]当前存在如下的整单折扣分配计划信息", () -> {
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
		And("[创建订单提交校验21]当前存在如下的整单折扣分配计划详细信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验21]添加商品", (DataTable dataTable) -> {
			log.info("添加商品");
		});
		And("[创建订单提交校验21]选择项目", (DataTable dataTable) -> {
			log.info("选择项目");
		});
		Then("[创建订单提交校验21]创建订单应校验失败且提示错误信息", (DataTable dataTable) -> {
			String totalInlineSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("totalInline")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
			String preDiscountTotalSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("preDiscountTotal")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
			String orderDiscountTotalSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("orderDiscountTotal")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
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
			OrderDiscountCalcCalculateReqDTO calculateReq = orderApiOrderManagement01.discountCalcAfterData(OrderDataDTO.builder()
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
							.build())
					.build());
			Response createOrderSubmitResponse = orderApiOrderManagement01.createOrderBefore(OrderDataDTO.builder()
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
					.calculateReq(calculateReq)
					.build());
			createOrderSubmitResponse.then().body("resultCode", Matchers.equalTo("100000"), "resultMsg", Matchers.equalTo("以下赠品数量已超可赠最大数量，请修改:\n商品编码:10000841,商品描述:醒脑静注射液，10ml/支，2支/盒，240盒/箱,当前最大可分配数量:0,最大数量来源[待分配可用数量],分配数量:200"));
			priceBatchClearData01.clearPirceData01("at");
			orderBatchClearData01.clearOrderPreDiscountData01("at");
			orderBatchClearData01.clearDiscountData01("at");
			orderBatchClearData01.clearPermittedSalesData01("at");
		});
	}

	private void scenario22() {
		Given("[创建订单提交校验22]当前存在如下客户信息", (DataTable dataTable) -> {
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
		When("[创建订单提交校验22]当前存在如下的预折商品信息", (DataTable dataTable) -> {
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
						.build());
			});
		});
		And("[创建订单提交校验22]当前存在如下的预折折扣信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				BigDecimal calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(8, RoundingMode.HALF_UP);
				String effectiveState = Optional.ofNullable(caseData.get("effectiveState")).orElse("");
				orderBatchUpdateData01.updateOrderPreDiscountDetailCalculationValue(OrderPreDiscountDetailDTO.builder()
								.effectiveState(effectiveState)
								.calculationValue(calculationValue)
								.itemSapCode(itemSapCode)
								.build(),
						OrderPreDiscountDTO.builder()
								.customerSapCode(customerSapCode)
								.build());
			});
		});
		And("[创建订单提交校验22]当前存在如下的整单折扣申请单信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验22]当前存在如下的整单折扣申请单商品信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验22]当前存在如下的整单折扣分配计划信息", () -> {
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
		And("[创建订单提交校验22]当前存在如下的整单折扣分配计划详细信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验22]添加商品", (DataTable dataTable) -> {
			log.info("添加商品");
		});
		And("[创建订单提交校验22]选择项目", (DataTable dataTable) -> {
			log.info("选择项目");
		});
		Then("[创建订单提交校验22]创建订单应校验失败且提示错误信息", (DataTable dataTable) -> {
			String totalInlineSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("totalInline")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
			String preDiscountTotalSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("preDiscountTotal")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
			String orderDiscountTotalSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("orderDiscountTotal")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
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
			OrderDiscountCalcCalculateReqDTO calculateReq = orderApiOrderManagement01.discountCalcAfterData(OrderDataDTO.builder()
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
							.build())
					.build());
			Response createOrderSubmitResponse = orderApiOrderManagement01.createOrderBefore(OrderDataDTO.builder()
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
					.calculateReq(calculateReq)
					.build());
			// 前端控制无法输入，后端未做校验
			// createOrderSubmitResponse.then().body("resultCode", Matchers.equalTo("100000"), "resultMsg", Matchers.equalTo("以下赠品数量已超可赠最大数量，请修改:\n商品编码:10000841,商品描述:醒脑静注射液，10ml/支，2支/盒，240盒/箱,当前最大可分配数量:0,最大数量来源[待分配可用数量],分配数量:200"));
			createOrderSubmitResponse.then().body("resultCode", Matchers.equalTo("0"), "resultMsg", Matchers.equalTo("success"));
			priceBatchClearData01.clearPirceData01("at");
			orderBatchClearData01.clearOrderPreDiscountData01("at");
			orderBatchClearData01.clearDiscountData01("at");
			orderBatchClearData01.clearPermittedSalesData01("at");
		});
	}

	private void scenario23() {
		Given("[创建订单提交校验23]当前存在如下客户信息", (DataTable dataTable) -> {
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
		When("[创建订单提交校验23]当前存在如下的预折商品信息", (DataTable dataTable) -> {
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
						.build());
			});
		});
		And("[创建订单提交校验23]当前存在如下的预折折扣信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				BigDecimal calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(8, RoundingMode.HALF_UP);
				String effectiveState = Optional.ofNullable(caseData.get("effectiveState")).orElse("");
				orderBatchUpdateData01.updateOrderPreDiscountDetailCalculationValue(OrderPreDiscountDetailDTO.builder()
								.effectiveState(effectiveState)
								.calculationValue(calculationValue)
								.itemSapCode(itemSapCode)
								.build(),
						OrderPreDiscountDTO.builder()
								.customerSapCode(customerSapCode)
								.build());
			});
		});
		And("[创建订单提交校验23]当前存在如下的整单折扣申请单信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验23]当前存在如下的整单折扣申请单商品信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验23]当前存在如下的整单折扣分配计划信息", () -> {
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
		And("[创建订单提交校验23]当前存在如下的整单折扣分配计划详细信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验23]添加商品", (DataTable dataTable) -> {
			log.info("添加商品");
		});
		And("[创建订单提交校验23]选择项目", (DataTable dataTable) -> {
			log.info("选择项目");
		});
		And("[创建订单提交校验23]赠品待分配数量变化", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String usedQuantity = Optional.ofNullable(caseData.get("usedQuantity")).orElse("");
				orderBatchUpdateData01.updateOrderDiscountAllocationPlanDetailUsedQuantity(allocationPlanDetailId, usedQuantity);
			});
			log.info("设置赠品待分配数量");
		});
		Then("[创建订单提交校验23]创建订单应校验失败且提示错误信息", (DataTable dataTable) -> {
			String totalInlineSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("totalInline")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
			String preDiscountTotalSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("preDiscountTotal")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
			String orderDiscountTotalSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("orderDiscountTotal")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
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
			OrderDiscountCalcCalculateReqDTO calculateReq = orderApiOrderManagement01.discountCalcAfterData(OrderDataDTO.builder()
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
							.build())
					.build());
			Response createOrderSubmitResponse = orderApiOrderManagement01.createOrderBefore(OrderDataDTO.builder()
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
					.calculateReq(calculateReq)
					.build());
			createOrderSubmitResponse.then().body("resultCode", Matchers.equalTo("100000"), "resultMsg", Matchers.equalTo("以下商品行数量已超过分配计划中的待分配数量，请调整:[项目类型:纯赠,商品编码:10000265,商品描述:柴胡滴丸，0.551g/袋，10袋/盒，300盒/箱，OTC,可用数量:98,当前需要分配数量:4]"));
			priceBatchClearData01.clearPirceData01("at");
			orderBatchClearData01.clearOrderPreDiscountData01("at");
			orderBatchClearData01.clearDiscountData01("at");
			orderBatchClearData01.clearPermittedSalesData01("at");
		});
	}


	private void scenario24() {
		Given("[创建订单提交校验24]当前存在如下客户信息", (DataTable dataTable) -> {
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
		When("[创建订单提交校验24]当前存在如下的预折商品信息", (DataTable dataTable) -> {
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
						.build());
			});
		});
		And("[创建订单提交校验24]当前存在如下的预折折扣信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				BigDecimal calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(8, RoundingMode.HALF_UP);
				String effectiveState = Optional.ofNullable(caseData.get("effectiveState")).orElse("");
				orderBatchUpdateData01.updateOrderPreDiscountDetailCalculationValue(OrderPreDiscountDetailDTO.builder()
								.effectiveState(effectiveState)
								.calculationValue(calculationValue)
								.itemSapCode(itemSapCode)
								.build(),
						OrderPreDiscountDTO.builder()
								.customerSapCode(customerSapCode)
								.build());
			});
		});
		And("[创建订单提交校验24]当前存在如下的整单折扣申请单信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验24]当前存在如下的整单折扣申请单商品信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验24]当前存在如下的整单折扣分配计划信息", () -> {
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
		And("[创建订单提交校验24]当前存在如下的整单折扣分配计划详细信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验24]添加商品", (DataTable dataTable) -> {
			log.info("添加商品");
		});
		And("[创建订单提交校验24]选择项目", (DataTable dataTable) -> {
			log.info("选择项目");
		});
		Then("[创建订单提交校验24]创建订单应校验失败且提示错误信息", (DataTable dataTable) -> {
			String totalInlineSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("totalInline")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
			String preDiscountTotalSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("preDiscountTotal")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
			String orderDiscountTotalSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("orderDiscountTotal")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
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
			OrderDiscountCalcCalculateReqDTO calculateReq = orderApiOrderManagement01.discountCalcAfterData(OrderDataDTO.builder()
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
							.build())
					.build());
			// 商品对应的分配计划状态变更为作废状态
			orderBatchUpdateData01.updateOrderDiscountAllocationPlanDetailInvalidStatus(allocationPlanDetailId, "CANCEL");
			Response createOrderSubmitResponse = orderApiOrderManagement01.createOrderBefore(OrderDataDTO.builder()
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
					.calculateReq(calculateReq)
					.build());
			createOrderSubmitResponse.then().body("resultCode", Matchers.equalTo("100000"), "resultMsg", Matchers.equalTo("项目类型:纯赠,商品编码:10000265,商品描述:柴胡滴丸，0.551g/袋，10袋/盒，300盒/箱，OTC,整单折扣明细已作废，不允许使用"));
			priceBatchClearData01.clearPirceData01("at");
			orderBatchClearData01.clearOrderPreDiscountData01("at");
			orderBatchClearData01.clearDiscountData01("at");
			orderBatchClearData01.clearPermittedSalesData01("at");
		});
	}


	private void scenario25() {
		Given("[创建订单提交校验25]当前存在如下客户信息", (DataTable dataTable) -> {
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
		When("[创建订单提交校验25]当前存在如下的预折商品信息", (DataTable dataTable) -> {
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
						.build());
			});
		});
		And("[创建订单提交校验25]当前存在如下的预折折扣信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				BigDecimal calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(8, RoundingMode.HALF_UP);
				String effectiveState = Optional.ofNullable(caseData.get("effectiveState")).orElse("");
				orderBatchUpdateData01.updateOrderPreDiscountDetailCalculationValue(OrderPreDiscountDetailDTO.builder()
								.effectiveState(effectiveState)
								.calculationValue(calculationValue)
								.itemSapCode(itemSapCode)
								.build(),
						OrderPreDiscountDTO.builder()
								.customerSapCode(customerSapCode)
								.build());
			});
		});
		And("[创建订单提交校验25]当前存在如下的整单折扣申请单信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验25]当前存在如下的整单折扣申请单商品信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验25]当前存在如下的整单折扣分配计划信息", () -> {
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
		And("[创建订单提交校验25]当前存在如下的整单折扣分配计划详细信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验25]添加商品", (DataTable dataTable) -> {
			log.info("添加商品");
		});
		And("[创建订单提交校验25]选择项目", (DataTable dataTable) -> {
			log.info("选择项目");
		});
		And("[创建订单提交校验25]修改大区月度折让可用余额", () -> {
			String formattedDate = DateUtil.format(DateUtil.date(), "yyyy-MM");
			rebateBatchUpdateData01.updateRegionRebateLimitRegionLimitAmount(SaleOrgEnum.fetchZtCode(saleOrgSapCode), regionCode, BigDecimal.ONE, formattedDate);
			log.info("设置大区月度折让可用余额");
		});
		Then("[创建订单提交校验25]创建订单应校验失败且提示错误信息", (DataTable dataTable) -> {
			String totalInlineSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("totalInline")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
			String preDiscountTotalSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("preDiscountTotal")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
			String orderDiscountTotalSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("orderDiscountTotal")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
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
			OrderDiscountCalcCalculateReqDTO calculateReq = orderApiOrderManagement01.discountCalcAfterData(OrderDataDTO.builder()
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
							.build())
					.build());
			Response createOrderSubmitResponse = orderApiOrderManagement01.createOrderBefore(OrderDataDTO.builder()
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
					.calculateReq(calculateReq)
					.build());
			createOrderSubmitResponse.then().body("resultCode", Matchers.equalTo("100000"), "resultMsg", Matchers.equalTo("整单折扣已超大区折让上限！当前大区折让可用余额==1"));
			priceBatchClearData01.clearPirceData01("at");
			orderBatchClearData01.clearOrderPreDiscountData01("at");
			orderBatchClearData01.clearDiscountData01("at");
			orderBatchClearData01.clearPermittedSalesData01("at");
		});
	}


	private void scenario26() {
		Given("[创建订单提交校验26]当前存在如下客户信息", (DataTable dataTable) -> {
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
		When("[创建订单提交校验26]当前存在如下的预折商品信息", (DataTable dataTable) -> {
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
						.build());
			});
		});
		And("[创建订单提交校验26]当前存在如下的预折折扣信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				BigDecimal calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(8, RoundingMode.HALF_UP);
				String effectiveState = Optional.ofNullable(caseData.get("effectiveState")).orElse("");
				orderBatchUpdateData01.updateOrderPreDiscountDetailCalculationValue(OrderPreDiscountDetailDTO.builder()
								.effectiveState(effectiveState)
								.calculationValue(calculationValue)
								.itemSapCode(itemSapCode)
								.build(),
						OrderPreDiscountDTO.builder()
								.customerSapCode(customerSapCode)
								.build());
			});
		});
		And("[创建订单提交校验26]当前存在如下的整单折扣申请单信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验26]当前存在如下的整单折扣申请单商品信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验26]当前存在如下的整单折扣分配计划信息", () -> {
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
		And("[创建订单提交校验26]当前存在如下的整单折扣分配计划详细信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验26]添加商品", (DataTable dataTable) -> {
			log.info("添加商品");
		});
		And("[创建订单提交校验26]选择项目", (DataTable dataTable) -> {
			log.info("选择项目");
		});
		Then("[创建订单提交校验26]创建订单应校验失败且提示错误信息", (DataTable dataTable) -> {
			String totalInlineSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("totalInline")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
			String preDiscountTotalSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("preDiscountTotal")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
			String orderDiscountTotalSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("orderDiscountTotal")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
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
			OrderDiscountCalcCalculateReqDTO calculateReq = orderApiOrderManagement01.discountCalcAfterData(OrderDataDTO.builder()
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
							.build())
					.build());
			Response createOrderSubmitResponse = orderApiOrderManagement01.createOrderBefore(OrderDataDTO.builder()
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
					.calculateReq(calculateReq)
					.build());
			createOrderSubmitResponse.then().body("resultCode", Matchers.equalTo("100000"), "resultMsg", Matchers.equalTo("产品组:【成品药-非管制药品】,分销渠道:【分销-非医疗】,预折+整单折扣超过最大32%的折扣比例"));
			priceBatchClearData01.clearPirceData01("at");
			orderBatchClearData01.clearOrderPreDiscountData01("at");
			orderBatchClearData01.clearDiscountData01("at");
			orderBatchClearData01.clearPermittedSalesData01("at");
		});
	}

	private void scenario27() {
		Given("[创建订单提交校验27]当前存在如下客户信息", (DataTable dataTable) -> {
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
		When("[创建订单提交校验27]当前存在如下的预折商品信息", (DataTable dataTable) -> {
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
						.build());
			});
		});
		And("[创建订单提交校验27]当前存在如下的预折折扣信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				BigDecimal calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(8, RoundingMode.HALF_UP);
				String effectiveState = Optional.ofNullable(caseData.get("effectiveState")).orElse("");
				orderBatchUpdateData01.updateOrderPreDiscountDetailCalculationValue(OrderPreDiscountDetailDTO.builder()
								.effectiveState(effectiveState)
								.calculationValue(calculationValue)
								.itemSapCode(itemSapCode)
								.build(),
						OrderPreDiscountDTO.builder()
								.customerSapCode(customerSapCode)
								.build());
			});
		});
		And("[创建订单提交校验27]当前存在如下的整单折扣申请单信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验27]当前存在如下的整单折扣申请单商品信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验27]当前存在如下的整单折扣分配计划信息", () -> {
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
		And("[创建订单提交校验27]当前存在如下的整单折扣分配计划详细信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验27]添加商品", (DataTable dataTable) -> {
			log.info("添加商品");
		});
		And("[创建订单提交校验27]选择项目", (DataTable dataTable) -> {
			log.info("选择项目");
		});
		Then("[创建订单提交校验27]创建订单应校验失败且提示错误信息", (DataTable dataTable) -> {
			String totalInlineSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("totalInline")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
			String preDiscountTotalSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("preDiscountTotal")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
			String orderDiscountTotalSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("orderDiscountTotal")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
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
				itemCodeTmp = itemSapCode;
			});
			OrderDiscountCalcCalculateReqDTO calculateReq = orderApiOrderManagement01.discountCalcAfterData(OrderDataDTO.builder()
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
							.build())
					.build());
			// 修改商品价格（商品价格变小，造成折后价格大于当前商品单价）
			priceBatchUpdateData01.updatePriceSkuPrice(BigDecimal.ONE.toPlainString(), itemCodeTmp, customerSapCode);
			Response createOrderSubmitResponse = orderApiOrderManagement01.createOrderBefore(OrderDataDTO.builder()
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
					.calculateReq(calculateReq)
					.build());
			// 价格变化校验先于预折折扣小于0校验，因此先被商品价格变更校验拦截，未能提示
			// createOrderSubmitResponse.then().body("resultCode", Matchers.equalTo("100000"), "resultMsg", Matchers.equalTo("商品编码【10000841】,商品名称:【醒脑静注射液】,预折折扣小于0，请联系总部核实！"));
			createOrderSubmitResponse.then().body("resultCode", Matchers.equalTo("100000"), "resultMsg", Matchers.equalTo("商品:10000841,商品描述:醒脑静注射液，10ml/支，2支/盒，240盒/箱价格变更，请重新提交订单"));
			priceBatchClearData01.clearPirceData01("at");
			orderBatchClearData01.clearOrderPreDiscountData01("at");
			orderBatchClearData01.clearDiscountData01("at");
			orderBatchClearData01.clearPermittedSalesData01("at");
		});
	}

	private void scenario28() {
		Given("[创建订单提交校验28]当前存在如下客户信息", (DataTable dataTable) -> {
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
		When("[创建订单提交校验28]当前存在如下的预折商品信息", (DataTable dataTable) -> {
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
						.build());
			});
		});
		And("[创建订单提交校验28]当前存在如下的预折折扣信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				BigDecimal calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(8, RoundingMode.HALF_UP);
				String effectiveState = Optional.ofNullable(caseData.get("effectiveState")).orElse("");
				orderBatchUpdateData01.updateOrderPreDiscountDetailCalculationValue(OrderPreDiscountDetailDTO.builder()
								.effectiveState(effectiveState)
								.calculationValue(calculationValue)
								.itemSapCode(itemSapCode)
								.build(),
						OrderPreDiscountDTO.builder()
								.customerSapCode(customerSapCode)
								.build());
			});
		});
		And("[创建订单提交校验28]当前存在如下的整单折扣申请单信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验28]当前存在如下的整单折扣申请单商品信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验28]当前存在如下的整单折扣分配计划信息", () -> {
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
		And("[创建订单提交校验28]当前存在如下的整单折扣分配计划详细信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验28]添加商品", (DataTable dataTable) -> {
			log.info("添加商品");
		});
		And("[创建订单提交校验28]选择项目", (DataTable dataTable) -> {
			log.info("选择项目");
		});
		And("[创建订单提交校验28]商品销售组织变更", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				saleOrgSapCodeNewItemCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				saleOrgSapCodeNew = Optional.ofNullable(caseData.get("saleOrgSapCodeNew")).orElse("");
				itemBatchUpdateData01.updateItSaleOrgProductOrgSapCode(saleOrgSapCode, saleOrgSapCodeNew, ProductEnum.fetchProductZtCode(saleOrgSapCodeNewItemCode));
			});
			log.info("设置商品销售组织");
		});
		Then("[创建订单提交校验28]创建订单应校验失败且提示错误信息", (DataTable dataTable) -> {
			String totalInlineSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("totalInline")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
			String preDiscountTotalSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("preDiscountTotal")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
			String orderDiscountTotalSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("orderDiscountTotal")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
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
			OrderDiscountCalcCalculateReqDTO calculateReq = orderApiOrderManagement01.discountCalcAfterData(OrderDataDTO.builder()
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
							.build())
					.build());
			Response createOrderSubmitResponse = orderApiOrderManagement01.createOrderBefore(OrderDataDTO.builder()
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
					.calculateReq(calculateReq)
					.build());
			createOrderSubmitResponse.then().body("resultCode", Matchers.equalTo("100000"), "resultMsg", Matchers.equalTo("商品:10000841,商品描述:醒脑静注射液，10ml/支，2支/盒，240盒/箱已下架或销售组织变更，请重新提交订单"));
			priceBatchClearData01.clearPirceData01("at");
			orderBatchClearData01.clearOrderPreDiscountData01("at");
			orderBatchClearData01.clearDiscountData01("at");
			orderBatchClearData01.clearPermittedSalesData01("at");
			itemBatchUpdateData01.updateItSaleOrgProductOrgSapCode(saleOrgSapCodeNew, saleOrgSapCode, ProductEnum.fetchProductZtCode(saleOrgSapCodeNewItemCode));
		});
	}

	private void scenario29() {
		Given("[创建订单提交校验29]当前存在如下客户信息", (DataTable dataTable) -> {
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
		When("[创建订单提交校验29]当前存在如下的预折商品信息", (DataTable dataTable) -> {
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
						.build());
			});
		});
		And("[创建订单提交校验29]当前存在如下的预折折扣信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				BigDecimal calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(8, RoundingMode.HALF_UP);
				String effectiveState = Optional.ofNullable(caseData.get("effectiveState")).orElse("");
				orderBatchUpdateData01.updateOrderPreDiscountDetailCalculationValue(OrderPreDiscountDetailDTO.builder()
								.effectiveState(effectiveState)
								.calculationValue(calculationValue)
								.itemSapCode(itemSapCode)
								.build(),
						OrderPreDiscountDTO.builder()
								.customerSapCode(customerSapCode)
								.build());
			});
		});
		And("[创建订单提交校验29]当前存在如下的整单折扣申请单信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验29]当前存在如下的整单折扣申请单商品信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验29]当前存在如下的整单折扣分配计划信息", () -> {
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
		And("[创建订单提交校验29]当前存在如下的整单折扣分配计划详细信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验29]添加商品", (DataTable dataTable) -> {
			log.info("添加商品");
		});
		And("[创建订单提交校验29]选择项目", (DataTable dataTable) -> {
			log.info("选择项目");
		});
		And("[创建订单提交校验29]订单行商品下架", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String isActive = Optional.ofNullable(caseData.get("isActive")).orElse("");
				itemBatchUpdateData01.updateItSaleOrgProductIsActive(saleOrgSapCode, isActive, ProductEnum.fetchProductZtCode(itemSapCode));
			});
			log.info("设置商品下架");
		});
		Then("[创建订单提交校验29]创建订单应校验失败且提示错误信息", (DataTable dataTable) -> {
			String totalInlineSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("totalInline")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
			String preDiscountTotalSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("preDiscountTotal")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
			String orderDiscountTotalSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("orderDiscountTotal")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
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
			OrderDiscountCalcCalculateReqDTO calculateReq = orderApiOrderManagement01.discountCalcAfterData(OrderDataDTO.builder()
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
							.build())
					.build());
			Response createOrderSubmitResponse = orderApiOrderManagement01.createOrderBefore(OrderDataDTO.builder()
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
					.calculateReq(calculateReq)
					.build());
			createOrderSubmitResponse.then().body("resultCode", Matchers.equalTo("100000"), "resultMsg", Matchers.equalTo("商品:10000841,商品描述:醒脑静注射液，10ml/支，2支/盒，240盒/箱已下架或销售组织变更，请重新提交订单"));
			priceBatchClearData01.clearPirceData01("at");
			orderBatchClearData01.clearOrderPreDiscountData01("at");
			orderBatchClearData01.clearDiscountData01("at");
			orderBatchClearData01.clearPermittedSalesData01("at");
		});
	}

	private void scenario30() {
		Given("[创建订单提交校验30]当前存在如下客户信息", (DataTable dataTable) -> {
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
		When("[创建订单提交校验30]当前存在如下的预折商品信息", (DataTable dataTable) -> {
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
						.build());
			});
		});
		And("[创建订单提交校验30]当前存在如下的预折折扣信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				BigDecimal calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(8, RoundingMode.HALF_UP);
				String effectiveState = Optional.ofNullable(caseData.get("effectiveState")).orElse("");
				orderBatchUpdateData01.updateOrderPreDiscountDetailCalculationValue(OrderPreDiscountDetailDTO.builder()
								.effectiveState(effectiveState)
								.calculationValue(calculationValue)
								.itemSapCode(itemSapCode)
								.build(),
						OrderPreDiscountDTO.builder()
								.customerSapCode(customerSapCode)
								.build());
			});
		});
		And("[创建订单提交校验30]当前存在如下的整单折扣申请单信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验30]当前存在如下的整单折扣申请单商品信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验30]当前存在如下的整单折扣分配计划信息", () -> {
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
		And("[创建订单提交校验30]当前存在如下的整单折扣分配计划详细信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验30]添加商品", (DataTable dataTable) -> {
			log.info("添加商品");
		});
		And("[创建订单提交校验30]选择项目", (DataTable dataTable) -> {
			log.info("选择项目");
		});
		Then("[创建订单提交校验30]创建订单应校验失败且提示错误信息", (DataTable dataTable) -> {
			String totalInlineSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("totalInline")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
			String preDiscountTotalSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("preDiscountTotal")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
			String orderDiscountTotalSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("orderDiscountTotal")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
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
				itemCodeTmp = itemSapCode;
			});
			OrderDiscountCalcCalculateReqDTO calculateReq = orderApiOrderManagement01.discountCalcAfterData(OrderDataDTO.builder()
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
							.build())
					.build());
			// 订单行商品分销渠道变更
			priceBatchUpdateData01.updatePriceDistributionChannelCode("13", itemCodeTmp, customerSapCode);
			Response createOrderSubmitResponse = orderApiOrderManagement01.createOrderBefore(OrderDataDTO.builder()
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
					.calculateReq(calculateReq)
					.build());
			// createOrderSubmitResponse.then().body("resultCode", Matchers.equalTo("100000"), "resultMsg", Matchers.equalTo("商品:10000841,商品描述:醒脑静注射液，10ml/支，2支/盒，240盒/箱已下架或销售组织变更，请重新提交订单"));
			createOrderSubmitResponse.then().body("resultCode", Matchers.equalTo("100000"), "resultMsg", Matchers.equalTo("商品:10000841,商品描述:醒脑静注射液，10ml/支，2支/盒，240盒/箱价格变更，请重新提交订单"));
			priceBatchClearData01.clearPirceData01("at");
			orderBatchClearData01.clearOrderPreDiscountData01("at");
			orderBatchClearData01.clearDiscountData01("at");
			orderBatchClearData01.clearPermittedSalesData01("at");
		});
	}

	private void scenario31() {
		Given("[创建订单提交校验31]当前存在如下客户信息", (DataTable dataTable) -> {
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
		When("[创建订单提交校验31]当前存在如下的预折商品信息", (DataTable dataTable) -> {
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
						.build());
			});
		});
		And("[创建订单提交校验31]当前存在如下的预折折扣信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				BigDecimal calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(8, RoundingMode.HALF_UP);
				String effectiveState = Optional.ofNullable(caseData.get("effectiveState")).orElse("");
				orderBatchUpdateData01.updateOrderPreDiscountDetailCalculationValue(OrderPreDiscountDetailDTO.builder()
								.effectiveState(effectiveState)
								.calculationValue(calculationValue)
								.itemSapCode(itemSapCode)
								.build(),
						OrderPreDiscountDTO.builder()
								.customerSapCode(customerSapCode)
								.build());
			});
		});
		And("[创建订单提交校验31]当前存在如下的整单折扣申请单信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验31]当前存在如下的整单折扣申请单商品信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验31]当前存在如下的整单折扣分配计划信息", () -> {
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
		And("[创建订单提交校验31]当前存在如下的整单折扣分配计划详细信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验31]添加商品", (DataTable dataTable) -> {
			log.info("添加商品");
		});
		And("[创建订单提交校验31]选择项目", (DataTable dataTable) -> {
			log.info("选择项目");
		});
		Then("[创建订单提交校验31]创建订单应校验失败且提示错误信息", (DataTable dataTable) -> {
			String totalInlineSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("totalInline")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
			String preDiscountTotalSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("preDiscountTotal")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
			String orderDiscountTotalSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("orderDiscountTotal")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
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
				itemCodeTmp = itemSapCode;
			});
			OrderDiscountCalcCalculateReqDTO calculateReq = orderApiOrderManagement01.discountCalcAfterData(OrderDataDTO.builder()
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
							.build())
					.build());
			// 订单行商品单价变更
			priceBatchUpdateData01.updatePriceSkuPrice("9.90", itemCodeTmp, customerSapCode);
			Response createOrderSubmitResponse = orderApiOrderManagement01.createOrderBefore(OrderDataDTO.builder()
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
					.calculateReq(calculateReq)
					.build());
			createOrderSubmitResponse.then().body("resultCode", Matchers.equalTo("100000"), "resultMsg", Matchers.equalTo("商品:10000841,商品描述:醒脑静注射液，10ml/支，2支/盒，240盒/箱价格变更，请重新提交订单"));
			priceBatchClearData01.clearPirceData01("at");
			orderBatchClearData01.clearOrderPreDiscountData01("at");
			orderBatchClearData01.clearDiscountData01("at");
			orderBatchClearData01.clearPermittedSalesData01("at");
		});
	}

	private void scenario32() {
		Given("[创建订单提交校验32]当前存在如下客户信息", (DataTable dataTable) -> {
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
		When("[创建订单提交校验32]当前存在如下的预折商品信息", (DataTable dataTable) -> {
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
						.build());
			});
		});
		And("[创建订单提交校验32]当前存在如下的预折折扣信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				BigDecimal calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(8, RoundingMode.HALF_UP);
				String effectiveState = Optional.ofNullable(caseData.get("effectiveState")).orElse("");
				orderBatchUpdateData01.updateOrderPreDiscountDetailCalculationValue(OrderPreDiscountDetailDTO.builder()
								.effectiveState(effectiveState)
								.calculationValue(calculationValue)
								.itemSapCode(itemSapCode)
								.build(),
						OrderPreDiscountDTO.builder()
								.customerSapCode(customerSapCode)
								.build());
			});
		});
		And("[创建订单提交校验32]当前存在如下的整单折扣申请单信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验32]当前存在如下的整单折扣申请单商品信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验32]当前存在如下的整单折扣分配计划信息", () -> {
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
		And("[创建订单提交校验32]当前存在如下的整单折扣分配计划详细信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验32]添加商品", (DataTable dataTable) -> {
			log.info("添加商品");
		});
		And("[创建订单提交校验32]选择项目", (DataTable dataTable) -> {
			log.info("选择项目");
		});
		Then("[创建订单提交校验32]创建订单应校验失败且提示错误信息", (DataTable dataTable) -> {
			String totalInlineSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("totalInline")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
			String preDiscountTotalSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("preDiscountTotal")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
			String orderDiscountTotalSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("orderDiscountTotal")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
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
				itemCodeTmp = itemSapCode;
			});
			OrderDiscountCalcCalculateReqDTO calculateReq = orderApiOrderManagement01.discountCalcAfterData(OrderDataDTO.builder()
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
							.build())
					.build());
			// 订单行商品预折折扣变更
			orderBatchUpdateData01.updateOrderPreDiscountDetailCalculationValue(OrderPreDiscountDetailDTO.builder().effectiveState("ENABLE").calculationValue(new BigDecimal("6.6")).itemSapCode(itemCodeTmp).build(), OrderPreDiscountDTO.builder().customerSapCode(customerSapCode).build());
			Response createOrderSubmitResponse = orderApiOrderManagement01.createOrderBefore(OrderDataDTO.builder()
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
					.calculateReq(calculateReq)
					.build());
			createOrderSubmitResponse.then().body("resultCode", Matchers.equalTo("100000"), "resultMsg", Matchers.equalTo("商品编码【10000841】,商品名称:【醒脑静注射液】,预折价格不一致"));
			priceBatchClearData01.clearPirceData01("at");
			orderBatchClearData01.clearOrderPreDiscountData01("at");
			orderBatchClearData01.clearDiscountData01("at");
			orderBatchClearData01.clearPermittedSalesData01("at");
		});
	}

	private void scenario33() {
		Given("[创建订单提交校验33]当前存在如下客户信息", (DataTable dataTable) -> {
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
		When("[创建订单提交校验33]当前存在如下的预折商品信息", (DataTable dataTable) -> {
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
						.build());
			});
		});
		And("[创建订单提交校验33]当前存在如下的预折折扣信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				BigDecimal calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(8, RoundingMode.HALF_UP);
				String effectiveState = Optional.ofNullable(caseData.get("effectiveState")).orElse("");
				orderBatchUpdateData01.updateOrderPreDiscountDetailCalculationValue(OrderPreDiscountDetailDTO.builder()
								.effectiveState(effectiveState)
								.calculationValue(calculationValue)
								.itemSapCode(itemSapCode)
								.build(),
						OrderPreDiscountDTO.builder()
								.customerSapCode(customerSapCode)
								.build());
			});
		});
		And("[创建订单提交校验33]当前存在如下的整单折扣申请单信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验33]当前存在如下的整单折扣申请单商品信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验33]当前存在如下的整单折扣分配计划信息", () -> {
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
		And("[创建订单提交校验33]当前存在如下的整单折扣分配计划详细信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验33]添加商品", (DataTable dataTable) -> {
			log.info("添加商品");
		});
		And("[创建订单提交校验33]选择项目", (DataTable dataTable) -> {
			log.info("选择项目");
		});
		And("[创建订单提交校验33]当前存在如下的商品禁销信息", (DataTable dataTable) -> {
			saleOrgControlledProductId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			permittedSalesRegionId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			orderPermittedSalesCustomerId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			dataTable.asMaps().stream().forEach(caseData -> {
				String saleOrgSapCode = Optional.ofNullable(caseData.get("saleOrgSapCode")).orElse("");
				String regionCode = Optional.ofNullable(caseData.get("regionCode")).orElse("");
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String permittedSaleQuantityRegion = Optional.ofNullable(caseData.get("permittedSaleQuantityRegion")).orElse("");
				String hasSaleQuantityUnits = Optional.ofNullable(caseData.get("hasSaleQuantityUnits")).orElse("");
				String type = Optional.ofNullable(caseData.get("type")).orElse("");
				String customerName = Optional.ofNullable(caseData.get("customerName")).orElse("");
				String unitCode = Optional.ofNullable(caseData.get("unitCode")).orElse("");
				String unitName = Optional.ofNullable(caseData.get("unitName")).orElse("");
				String customerEntZtCode = Optional.ofNullable(caseData.get("customerEntZtCode")).orElse("");
				String customerEntSapCode = Optional.ofNullable(caseData.get("customerEntSapCode")).orElse("");
				String customerEntCrmCode = Optional.ofNullable(caseData.get("customerEntCrmCode")).orElse("");
				String saleOrgEffectiveState = Optional.ofNullable(caseData.get("saleOrgEffectiveState")).orElse("");
				String regionEffectiveState = Optional.ofNullable(caseData.get("regionEffectiveState")).orElse("");
				String customerEffectiveState = Optional.ofNullable(caseData.get("customerEffectiveState")).orElse("");
				String hasSaleQuantityCustomer = Optional.ofNullable(caseData.get("hasSaleQuantityCustomer")).orElse("");
				String permittedSaleQuantityCustomer = Optional.ofNullable(caseData.get("permittedSaleQuantityCustomer")).orElse("");
				permittedSaleBeginDate = new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis());
				permittedSaleEndDate = new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis());
				orderBatchInsertData01.insertOrderSaleOrgControlledProductData01(OrderSaleOrgControlledProductDTO.builder().saleOrgControlledProductId(saleOrgControlledProductId).saleOrgSapCode(saleOrgSapCode).productSapSku(itemSapCode).effectiveState(saleOrgEffectiveState).createPerson("at").createTime(createTime).build());
				orderBatchInsertData01.insertOrderPermittedSalesRegionData01(OrderPermittedSalesRegionDTO.builder().orderPermittedSalesRegionId(permittedSalesRegionId).saleOrgControlledProductId(saleOrgControlledProductId).regionCode(regionCode).regionName(RegionEnum.fetchName(regionCode)).permittedSaleQuantity(permittedSaleQuantityRegion).unitCode(unitCode).unitName(unitName).permittedSaleBeginDate(permittedSaleBeginDate).permittedSaleEndDate(permittedSaleEndDate).effectiveState(regionEffectiveState).createPerson("at").createTime(createTime).build());
				orderBatchInsertData01.insertOrderPermittedSalesCustomerData01(OrderPermittedSalesCustomerDTO.builder().orderPermittedSalesCustomerId(orderPermittedSalesCustomerId).saleOrgControlledProductId(saleOrgControlledProductId).permittedSalesRegionId(permittedSalesRegionId).customerEntZtCode(customerEntZtCode).customerEntSapCode(customerEntSapCode).customerEntCrmCode(customerEntCrmCode).customerName(customerName).permittedSaleQuantity(permittedSaleQuantityCustomer).hasSaleQuantity(hasSaleQuantityCustomer).type(type).effectiveState(customerEffectiveState).createPerson("at").createTime(createTime).build());
			});

			log.info("设置商品禁销信息");
		});
		Then("[创建订单提交校验33]创建订单提交校验通过", (DataTable dataTable) -> {
			String totalInlineSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("totalInline")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
			String preDiscountTotalSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("preDiscountTotal")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
			String orderDiscountTotalSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("orderDiscountTotal")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
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
			OrderDiscountCalcCalculateReqDTO calculateReq = orderApiOrderManagement01.discountCalcAfterData(OrderDataDTO.builder()
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
							.build())
					.build());
			Response createOrderSubmitResponse = orderApiOrderManagement01.createOrderBefore(OrderDataDTO.builder()
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
					.calculateReq(calculateReq)
					.build());
			createOrderSubmitResponse.then().body("resultCode", Matchers.equalTo("0"), "resultMsg", Matchers.equalTo("success"));
			priceBatchClearData01.clearPirceData01("at");
			orderBatchClearData01.clearOrderPreDiscountData01("at");
			orderBatchClearData01.clearDiscountData01("at");
			orderBatchClearData01.clearPermittedSalesData01("at");
		});
	}

	private void scenario34() {
		Given("[创建订单提交校验34]当前存在如下客户信息", (DataTable dataTable) -> {
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
		When("[创建订单提交校验34]当前存在如下的预折商品信息", (DataTable dataTable) -> {
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
						.build());
			});
		});
		And("[创建订单提交校验34]当前存在如下的预折折扣信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				BigDecimal calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(8, RoundingMode.HALF_UP);
				String effectiveState = Optional.ofNullable(caseData.get("effectiveState")).orElse("");
				orderBatchUpdateData01.updateOrderPreDiscountDetailCalculationValue(OrderPreDiscountDetailDTO.builder()
								.effectiveState(effectiveState)
								.calculationValue(calculationValue)
								.itemSapCode(itemSapCode)
								.build(),
						OrderPreDiscountDTO.builder()
								.customerSapCode(customerSapCode)
								.build());
			});
		});
		And("[创建订单提交校验34]当前存在如下的整单折扣申请单信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验34]当前存在如下的整单折扣申请单商品信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验34]当前存在如下的整单折扣分配计划信息", () -> {
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
		And("[创建订单提交校验34]当前存在如下的整单折扣分配计划详细信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验34]添加商品", (DataTable dataTable) -> {
			log.info("添加商品");
		});
		And("[创建订单提交校验34]选择项目", (DataTable dataTable) -> {
			log.info("选择项目");
		});
		And("[创建订单提交校验34]当前存在如下的商品禁销信息", (DataTable dataTable) -> {
			saleOrgControlledProductId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			permittedSalesRegionId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			orderPermittedSalesCustomerId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			dataTable.asMaps().stream().forEach(caseData -> {
				String saleOrgSapCode = Optional.ofNullable(caseData.get("saleOrgSapCode")).orElse("");
				String regionCode = Optional.ofNullable(caseData.get("regionCode")).orElse("");
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String permittedSaleQuantityRegion = Optional.ofNullable(caseData.get("permittedSaleQuantityRegion")).orElse("");
				String hasSaleQuantityUnits = Optional.ofNullable(caseData.get("hasSaleQuantityUnits")).orElse("");
				String type = Optional.ofNullable(caseData.get("type")).orElse("");
				String customerName = Optional.ofNullable(caseData.get("customerName")).orElse("");
				String unitCode = Optional.ofNullable(caseData.get("unitCode")).orElse("");
				String unitName = Optional.ofNullable(caseData.get("unitName")).orElse("");
				String customerEntZtCode = Optional.ofNullable(caseData.get("customerEntZtCode")).orElse("");
				String customerEntSapCode = Optional.ofNullable(caseData.get("customerEntSapCode")).orElse("");
				String customerEntCrmCode = Optional.ofNullable(caseData.get("customerEntCrmCode")).orElse("");
				String saleOrgEffectiveState = Optional.ofNullable(caseData.get("saleOrgEffectiveState")).orElse("");
				String regionEffectiveState = Optional.ofNullable(caseData.get("regionEffectiveState")).orElse("");
				String customerEffectiveState = Optional.ofNullable(caseData.get("customerEffectiveState")).orElse("");
				String hasSaleQuantityCustomer = Optional.ofNullable(caseData.get("hasSaleQuantityCustomer")).orElse("");
				String permittedSaleQuantityCustomer = Optional.ofNullable(caseData.get("permittedSaleQuantityCustomer")).orElse("");
				permittedSaleBeginDate = new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis());
				permittedSaleEndDate = new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis());
				orderBatchInsertData01.insertOrderSaleOrgControlledProductData01(OrderSaleOrgControlledProductDTO.builder().saleOrgControlledProductId(saleOrgControlledProductId).saleOrgSapCode(saleOrgSapCode).productSapSku(itemSapCode).effectiveState(saleOrgEffectiveState).createPerson("at").createTime(createTime).build());
				orderBatchInsertData01.insertOrderPermittedSalesRegionData01(OrderPermittedSalesRegionDTO.builder().orderPermittedSalesRegionId(permittedSalesRegionId).saleOrgControlledProductId(saleOrgControlledProductId).regionCode(regionCode).regionName(RegionEnum.fetchName(regionCode)).permittedSaleQuantity(permittedSaleQuantityRegion).unitCode(unitCode).unitName(unitName).permittedSaleBeginDate(permittedSaleBeginDate).permittedSaleEndDate(permittedSaleEndDate).effectiveState(regionEffectiveState).createPerson("at").createTime(createTime).build());
				orderBatchInsertData01.insertOrderPermittedSalesCustomerData01(OrderPermittedSalesCustomerDTO.builder().orderPermittedSalesCustomerId(orderPermittedSalesCustomerId).saleOrgControlledProductId(saleOrgControlledProductId).permittedSalesRegionId(permittedSalesRegionId).customerEntZtCode(customerEntZtCode).customerEntSapCode(customerEntSapCode).customerEntCrmCode(customerEntCrmCode).customerName(customerName).permittedSaleQuantity(permittedSaleQuantityCustomer).hasSaleQuantity(hasSaleQuantityCustomer).type(type).effectiveState(customerEffectiveState).createPerson("at").createTime(createTime).build());
			});

			log.info("设置商品禁销信息");
		});
		And("[创建订单提交校验34]当前存在如下的商品一级商允销信息", (DataTable dataTable) -> {
			orderPermittedSalesCustomerId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			dataTable.asMaps().stream().forEach(caseData -> {
				String type = Optional.ofNullable(caseData.get("type")).orElse("");
				String permittedSaleQuantityCustomer = Optional.ofNullable(caseData.get("permittedSaleQuantityCustomer")).orElse("");
				String hasSaleQuantityCustomer = Optional.ofNullable(caseData.get("hasSaleQuantityCustomer")).orElse("");
				String customerEffectiveState = Optional.ofNullable(caseData.get("customerEffectiveState")).orElse("");
				orderBatchInsertData01.insertOrderPermittedSalesCustomerData01(OrderPermittedSalesCustomerDTO.builder().orderPermittedSalesCustomerId(orderPermittedSalesCustomerId).saleOrgControlledProductId(saleOrgControlledProductId).permittedSalesRegionId(permittedSalesRegionId).customerEntSapCode(customerSapCode).customerEntCrmCode(CompanyEnum.fetchCrmCode(customerSapCode)).customerEntZtCode(CompanyEnum.fetchZtCode(customerSapCode)).customerName(CompanyEnum.fetchName(customerSapCode)).permittedSaleQuantity(permittedSaleQuantityCustomer).hasSaleQuantity(hasSaleQuantityCustomer).type(type).effectiveState(customerEffectiveState).createPerson("at").createTime(createTime).build());
			});

			log.info("设置商品一级商允销信息");
		});
		Then("[创建订单提交校验34]创建订单提交校验通过", (DataTable dataTable) -> {
			String totalInlineSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("totalInline")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
			String preDiscountTotalSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("preDiscountTotal")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
			String orderDiscountTotalSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("orderDiscountTotal")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
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
			OrderDiscountCalcCalculateReqDTO calculateReq = orderApiOrderManagement01.discountCalcAfterData(OrderDataDTO.builder()
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
							.build())
					.build());
			Response createOrderSubmitResponse = orderApiOrderManagement01.createOrderBefore(OrderDataDTO.builder()
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
					.calculateReq(calculateReq)
					.build());
			createOrderSubmitResponse.then().body("resultCode", Matchers.equalTo("0"), "resultMsg", Matchers.equalTo("success"));
			priceBatchClearData01.clearPirceData01("at");
			orderBatchClearData01.clearOrderPreDiscountData01("at");
			orderBatchClearData01.clearDiscountData01("at");
			orderBatchClearData01.clearPermittedSalesData01("at");
		});
	}

	private void scenario35() {
		Given("[创建订单提交校验35]当前存在如下客户信息", (DataTable dataTable) -> {
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
		When("[创建订单提交校验35]当前存在如下的预折商品信息", (DataTable dataTable) -> {
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
						.build());
			});
		});
		And("[创建订单提交校验35]当前存在如下的预折折扣信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				BigDecimal calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(8, RoundingMode.HALF_UP);
				String effectiveState = Optional.ofNullable(caseData.get("effectiveState")).orElse("");
				orderBatchUpdateData01.updateOrderPreDiscountDetailCalculationValue(OrderPreDiscountDetailDTO.builder()
								.effectiveState(effectiveState)
								.calculationValue(calculationValue)
								.itemSapCode(itemSapCode)
								.build(),
						OrderPreDiscountDTO.builder()
								.customerSapCode(customerSapCode)
								.build());
			});
		});
		And("[创建订单提交校验35]当前存在如下的整单折扣申请单信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验35]当前存在如下的整单折扣申请单商品信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验35]当前存在如下的整单折扣分配计划信息", () -> {
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
		And("[创建订单提交校验35]当前存在如下的整单折扣分配计划详细信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验35]添加商品", (DataTable dataTable) -> {
			log.info("添加商品");
		});
		And("[创建订单提交校验35]选择项目", (DataTable dataTable) -> {
			log.info("选择项目");
		});
		And("[创建订单提交校验35]当前存在如下的商品禁销信息", (DataTable dataTable) -> {
			saleOrgControlledProductId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			permittedSalesRegionId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			orderPermittedSalesCustomerId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			dataTable.asMaps().stream().forEach(caseData -> {
				String saleOrgSapCode = Optional.ofNullable(caseData.get("saleOrgSapCode")).orElse("");
				String regionCode = Optional.ofNullable(caseData.get("regionCode")).orElse("");
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String permittedSaleQuantityRegion = Optional.ofNullable(caseData.get("permittedSaleQuantityRegion")).orElse("");
				String hasSaleQuantityUnits = Optional.ofNullable(caseData.get("hasSaleQuantityUnits")).orElse("");
				String type = Optional.ofNullable(caseData.get("type")).orElse("");
				String customerName = Optional.ofNullable(caseData.get("customerName")).orElse("");
				String unitCode = Optional.ofNullable(caseData.get("unitCode")).orElse("");
				String unitName = Optional.ofNullable(caseData.get("unitName")).orElse("");
				String customerEntZtCode = Optional.ofNullable(caseData.get("customerEntZtCode")).orElse("");
				String customerEntSapCode = Optional.ofNullable(caseData.get("customerEntSapCode")).orElse("");
				String customerEntCrmCode = Optional.ofNullable(caseData.get("customerEntCrmCode")).orElse("");
				String saleOrgEffectiveState = Optional.ofNullable(caseData.get("saleOrgEffectiveState")).orElse("");
				String regionEffectiveState = Optional.ofNullable(caseData.get("regionEffectiveState")).orElse("");
				String customerEffectiveState = Optional.ofNullable(caseData.get("customerEffectiveState")).orElse("");
				String hasSaleQuantityCustomer = Optional.ofNullable(caseData.get("hasSaleQuantityCustomer")).orElse("");
				String permittedSaleQuantityCustomer = Optional.ofNullable(caseData.get("permittedSaleQuantityCustomer")).orElse("");
				permittedSaleBeginDate = new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis());
				permittedSaleEndDate = new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis());
				orderBatchInsertData01.insertOrderSaleOrgControlledProductData01(OrderSaleOrgControlledProductDTO.builder().saleOrgControlledProductId(saleOrgControlledProductId).saleOrgSapCode(saleOrgSapCode).productSapSku(itemSapCode).effectiveState(saleOrgEffectiveState).createPerson("at").createTime(createTime).build());
				orderBatchInsertData01.insertOrderPermittedSalesRegionData01(OrderPermittedSalesRegionDTO.builder().orderPermittedSalesRegionId(permittedSalesRegionId).saleOrgControlledProductId(saleOrgControlledProductId).regionCode(regionCode).regionName(RegionEnum.fetchName(regionCode)).permittedSaleQuantity(permittedSaleQuantityRegion).unitCode(unitCode).unitName(unitName).permittedSaleBeginDate(permittedSaleBeginDate).permittedSaleEndDate(permittedSaleEndDate).effectiveState(regionEffectiveState).createPerson("at").createTime(createTime).build());
				orderBatchInsertData01.insertOrderPermittedSalesCustomerData01(OrderPermittedSalesCustomerDTO.builder().orderPermittedSalesCustomerId(orderPermittedSalesCustomerId).saleOrgControlledProductId(saleOrgControlledProductId).permittedSalesRegionId(permittedSalesRegionId).customerEntZtCode(customerEntZtCode).customerEntSapCode(customerEntSapCode).customerEntCrmCode(customerEntCrmCode).customerName(customerName).permittedSaleQuantity(permittedSaleQuantityCustomer).hasSaleQuantity(hasSaleQuantityCustomer).type(type).effectiveState(customerEffectiveState).createPerson("at").createTime(createTime).build());
			});

			log.info("设置商品禁销信息");
		});
		Then("[创建订单提交校验35]创建订单应校验失败且提示错误信息", (DataTable dataTable) -> {
			String totalInlineSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("totalInline")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
			String preDiscountTotalSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("preDiscountTotal")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
			String orderDiscountTotalSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("orderDiscountTotal")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
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
			OrderDiscountCalcCalculateReqDTO calculateReq = orderApiOrderManagement01.discountCalcAfterData(OrderDataDTO.builder()
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
							.build())
					.build());
			Response createOrderSubmitResponse = orderApiOrderManagement01.createOrderBefore(OrderDataDTO.builder()
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
					.calculateReq(calculateReq)
					.build());
			createOrderSubmitResponse.then().body("resultCode", Matchers.equalTo("100000"), "resultMsg", Matchers.equalTo("以下商品为限销商品,无法下单:\n商品编码:10000841,商品描述:醒脑静注射液，10ml/支，2支/盒，240盒/箱,原因:限销品，剩余数量不足，当前可购的最大数量：4.00盒,订单购买数量:5盒\n"));
			priceBatchClearData01.clearPirceData01("at");
			orderBatchClearData01.clearOrderPreDiscountData01("at");
			orderBatchClearData01.clearDiscountData01("at");
			orderBatchClearData01.clearPermittedSalesData01("at");
		});
	}

	private void scenario36() {
		Given("[创建订单提交校验36]当前存在如下客户信息", (DataTable dataTable) -> {
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
		When("[创建订单提交校验36]当前存在如下的预折商品信息", (DataTable dataTable) -> {
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
						.build());
			});
		});
		And("[创建订单提交校验36]当前存在如下的预折折扣信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				BigDecimal calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(8, RoundingMode.HALF_UP);
				String effectiveState = Optional.ofNullable(caseData.get("effectiveState")).orElse("");
				orderBatchUpdateData01.updateOrderPreDiscountDetailCalculationValue(OrderPreDiscountDetailDTO.builder()
								.effectiveState(effectiveState)
								.calculationValue(calculationValue)
								.itemSapCode(itemSapCode)
								.build(),
						OrderPreDiscountDTO.builder()
								.customerSapCode(customerSapCode)
								.build());
			});
		});
		And("[创建订单提交校验36]当前存在如下的整单折扣申请单信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验36]当前存在如下的整单折扣申请单商品信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验36]当前存在如下的整单折扣分配计划信息", () -> {
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
		And("[创建订单提交校验36]当前存在如下的整单折扣分配计划详细信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验36]添加商品", (DataTable dataTable) -> {
			log.info("添加商品");
		});
		And("[创建订单提交校验36]选择项目", (DataTable dataTable) -> {
			log.info("选择项目");
		});
		And("[创建订单提交校验36]当前存在如下的商品禁销信息", (DataTable dataTable) -> {
			saleOrgControlledProductId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			permittedSalesRegionId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			orderPermittedSalesCustomerId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			dataTable.asMaps().stream().forEach(caseData -> {
				String saleOrgSapCode = Optional.ofNullable(caseData.get("saleOrgSapCode")).orElse("");
				String regionCode = Optional.ofNullable(caseData.get("regionCode")).orElse("");
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String permittedSaleQuantityRegion = Optional.ofNullable(caseData.get("permittedSaleQuantityRegion")).orElse("");
				String hasSaleQuantityUnits = Optional.ofNullable(caseData.get("hasSaleQuantityUnits")).orElse("");
				String type = Optional.ofNullable(caseData.get("type")).orElse("");
				String customerName = Optional.ofNullable(caseData.get("customerName")).orElse("");
				String unitCode = Optional.ofNullable(caseData.get("unitCode")).orElse("");
				String unitName = Optional.ofNullable(caseData.get("unitName")).orElse("");
				String customerEntZtCode = Optional.ofNullable(caseData.get("customerEntZtCode")).orElse("");
				String customerEntSapCode = Optional.ofNullable(caseData.get("customerEntSapCode")).orElse("");
				String customerEntCrmCode = Optional.ofNullable(caseData.get("customerEntCrmCode")).orElse("");
				String saleOrgEffectiveState = Optional.ofNullable(caseData.get("saleOrgEffectiveState")).orElse("");
				String regionEffectiveState = Optional.ofNullable(caseData.get("regionEffectiveState")).orElse("");
				String customerEffectiveState = Optional.ofNullable(caseData.get("customerEffectiveState")).orElse("");
				String hasSaleQuantityCustomer = Optional.ofNullable(caseData.get("hasSaleQuantityCustomer")).orElse("");
				String permittedSaleQuantityCustomer = Optional.ofNullable(caseData.get("permittedSaleQuantityCustomer")).orElse("");
				permittedSaleBeginDate = new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis());
				permittedSaleEndDate = new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis());
				orderBatchInsertData01.insertOrderSaleOrgControlledProductData01(OrderSaleOrgControlledProductDTO.builder().saleOrgControlledProductId(saleOrgControlledProductId).saleOrgSapCode(saleOrgSapCode).productSapSku(itemSapCode).effectiveState(saleOrgEffectiveState).createPerson("at").createTime(createTime).build());
				orderBatchInsertData01.insertOrderPermittedSalesRegionData01(OrderPermittedSalesRegionDTO.builder().orderPermittedSalesRegionId(permittedSalesRegionId).saleOrgControlledProductId(saleOrgControlledProductId).regionCode(regionCode).regionName(RegionEnum.fetchName(regionCode)).permittedSaleQuantity(permittedSaleQuantityRegion).unitCode(unitCode).unitName(unitName).permittedSaleBeginDate(permittedSaleBeginDate).permittedSaleEndDate(permittedSaleEndDate).effectiveState(regionEffectiveState).createPerson("at").createTime(createTime).build());
				orderBatchInsertData01.insertOrderPermittedSalesCustomerData01(OrderPermittedSalesCustomerDTO.builder().orderPermittedSalesCustomerId(orderPermittedSalesCustomerId).saleOrgControlledProductId(saleOrgControlledProductId).permittedSalesRegionId(permittedSalesRegionId).customerEntZtCode(customerEntZtCode).customerEntSapCode(customerEntSapCode).customerEntCrmCode(customerEntCrmCode).customerName(customerName).permittedSaleQuantity(permittedSaleQuantityCustomer).hasSaleQuantity(hasSaleQuantityCustomer).type(type).effectiveState(customerEffectiveState).createPerson("at").createTime(createTime).build());
			});

			log.info("设置商品禁销信息");
		});
		And("[创建订单提交校验36]当前存在如下的商品一级商允销信息", (DataTable dataTable) -> {
			orderPermittedSalesCustomerId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			dataTable.asMaps().stream().forEach(caseData -> {
				String type = Optional.ofNullable(caseData.get("type")).orElse("");
				String permittedSaleQuantityCustomer = Optional.ofNullable(caseData.get("permittedSaleQuantityCustomer")).orElse("");
				String hasSaleQuantityCustomer = Optional.ofNullable(caseData.get("hasSaleQuantityCustomer")).orElse("");
				String customerEffectiveState = Optional.ofNullable(caseData.get("customerEffectiveState")).orElse("");
				orderBatchInsertData01.insertOrderPermittedSalesCustomerData01(OrderPermittedSalesCustomerDTO.builder().orderPermittedSalesCustomerId(orderPermittedSalesCustomerId).saleOrgControlledProductId(saleOrgControlledProductId).permittedSalesRegionId(permittedSalesRegionId).customerEntSapCode(customerSapCode).customerEntCrmCode(CompanyEnum.fetchCrmCode(customerSapCode)).customerEntZtCode(CompanyEnum.fetchZtCode(customerSapCode)).customerName(CompanyEnum.fetchName(customerSapCode)).permittedSaleQuantity(permittedSaleQuantityCustomer).hasSaleQuantity(hasSaleQuantityCustomer).type(type).effectiveState(customerEffectiveState).createPerson("at").createTime(createTime).build());
			});

			log.info("设置商品一级商允销信息");
		});
		Then("[创建订单提交校验36]创建订单应校验失败且提示错误信息", (DataTable dataTable) -> {
			String totalInlineSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("totalInline")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
			String preDiscountTotalSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("preDiscountTotal")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
			String orderDiscountTotalSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("orderDiscountTotal")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
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
			OrderDiscountCalcCalculateReqDTO calculateReq = orderApiOrderManagement01.discountCalcAfterData(OrderDataDTO.builder()
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
							.build())
					.build());
			Response createOrderSubmitResponse = orderApiOrderManagement01.createOrderBefore(OrderDataDTO.builder()
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
					.calculateReq(calculateReq)
					.build());
			createOrderSubmitResponse.then().body("resultCode", Matchers.equalTo("100000"), "resultMsg", Matchers.equalTo("以下商品为限销商品,无法下单:\n商品编码:10000841,商品描述:醒脑静注射液，10ml/支，2支/盒，240盒/箱,原因:限销品，剩余数量不足，当前可购的最大数量：4.00盒,订单购买数量:5盒\n"));
			priceBatchClearData01.clearPirceData01("at");
			orderBatchClearData01.clearOrderPreDiscountData01("at");
			orderBatchClearData01.clearDiscountData01("at");
			orderBatchClearData01.clearPermittedSalesData01("at");
		});
	}

	private void scenario37() {
		Given("[创建订单提交校验37]当前存在如下客户信息", (DataTable dataTable) -> {
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
		When("[创建订单提交校验37]当前存在如下的预折商品信息", (DataTable dataTable) -> {
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
						.build());
			});
		});
		And("[创建订单提交校验37]当前存在如下的预折折扣信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				BigDecimal calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(8, RoundingMode.HALF_UP);
				String effectiveState = Optional.ofNullable(caseData.get("effectiveState")).orElse("");
				orderBatchUpdateData01.updateOrderPreDiscountDetailCalculationValue(OrderPreDiscountDetailDTO.builder()
								.effectiveState(effectiveState)
								.calculationValue(calculationValue)
								.itemSapCode(itemSapCode)
								.build(),
						OrderPreDiscountDTO.builder()
								.customerSapCode(customerSapCode)
								.build());
			});
		});
		And("[创建订单提交校验37]当前存在如下的整单折扣申请单信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验37]当前存在如下的整单折扣申请单商品信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验37]当前存在如下的整单折扣分配计划信息", () -> {
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
		And("[创建订单提交校验37]当前存在如下的整单折扣分配计划详细信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验37]添加商品", (DataTable dataTable) -> {
			log.info("添加商品");
		});
		And("[创建订单提交校验37]选择项目", (DataTable dataTable) -> {
			log.info("选择项目");
		});
		And("[创建订单提交校验37]当前存在如下的商品禁销信息", (DataTable dataTable) -> {
			saleOrgControlledProductId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			permittedSalesRegionId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			orderPermittedSalesCustomerId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			dataTable.asMaps().stream().forEach(caseData -> {
				String saleOrgSapCode = Optional.ofNullable(caseData.get("saleOrgSapCode")).orElse("");
				String regionCode = Optional.ofNullable(caseData.get("regionCode")).orElse("");
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String permittedSaleQuantityRegion = Optional.ofNullable(caseData.get("permittedSaleQuantityRegion")).orElse("");
				String hasSaleQuantityUnits = Optional.ofNullable(caseData.get("hasSaleQuantityUnits")).orElse("");
				String type = Optional.ofNullable(caseData.get("type")).orElse("");
				String customerName = Optional.ofNullable(caseData.get("customerName")).orElse("");
				String unitCode = Optional.ofNullable(caseData.get("unitCode")).orElse("");
				String unitName = Optional.ofNullable(caseData.get("unitName")).orElse("");
				String customerEntZtCode = Optional.ofNullable(caseData.get("customerEntZtCode")).orElse("");
				String customerEntSapCode = Optional.ofNullable(caseData.get("customerEntSapCode")).orElse("");
				String customerEntCrmCode = Optional.ofNullable(caseData.get("customerEntCrmCode")).orElse("");
				String saleOrgEffectiveState = Optional.ofNullable(caseData.get("saleOrgEffectiveState")).orElse("");
				String regionEffectiveState = Optional.ofNullable(caseData.get("regionEffectiveState")).orElse("");
				String customerEffectiveState = Optional.ofNullable(caseData.get("customerEffectiveState")).orElse("");
				String hasSaleQuantityCustomer = Optional.ofNullable(caseData.get("hasSaleQuantityCustomer")).orElse("");
				String permittedSaleQuantityCustomer = Optional.ofNullable(caseData.get("permittedSaleQuantityCustomer")).orElse("");
				permittedSaleBeginDate = new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis());
				permittedSaleEndDate = new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis());
				orderBatchInsertData01.insertOrderSaleOrgControlledProductData01(OrderSaleOrgControlledProductDTO.builder().saleOrgControlledProductId(saleOrgControlledProductId).saleOrgSapCode(saleOrgSapCode).productSapSku(itemSapCode).effectiveState(saleOrgEffectiveState).createPerson("at").createTime(createTime).build());
				orderBatchInsertData01.insertOrderPermittedSalesRegionData01(OrderPermittedSalesRegionDTO.builder().orderPermittedSalesRegionId(permittedSalesRegionId).saleOrgControlledProductId(saleOrgControlledProductId).regionCode(regionCode).regionName(RegionEnum.fetchName(regionCode)).permittedSaleQuantity(permittedSaleQuantityRegion).unitCode(unitCode).unitName(unitName).permittedSaleBeginDate(permittedSaleBeginDate).permittedSaleEndDate(permittedSaleEndDate).effectiveState(regionEffectiveState).createPerson("at").createTime(createTime).build());
				orderBatchInsertData01.insertOrderPermittedSalesCustomerData01(OrderPermittedSalesCustomerDTO.builder().orderPermittedSalesCustomerId(orderPermittedSalesCustomerId).saleOrgControlledProductId(saleOrgControlledProductId).permittedSalesRegionId(permittedSalesRegionId).customerEntZtCode(customerEntZtCode).customerEntSapCode(customerEntSapCode).customerEntCrmCode(customerEntCrmCode).customerName(customerName).permittedSaleQuantity(permittedSaleQuantityCustomer).hasSaleQuantity(hasSaleQuantityCustomer).type(type).effectiveState(customerEffectiveState).createPerson("at").createTime(createTime).build());
			});

			log.info("设置商品禁销信息");
		});
		Then("[创建订单提交校验37]创建订单应校验失败且提示错误信息", (DataTable dataTable) -> {
			String totalInlineSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("totalInline")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
			String preDiscountTotalSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("preDiscountTotal")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
			String orderDiscountTotalSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("orderDiscountTotal")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
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
			OrderDiscountCalcCalculateReqDTO calculateReq = orderApiOrderManagement01.discountCalcAfterData(OrderDataDTO.builder()
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
							.build())
					.build());
			Response createOrderSubmitResponse = orderApiOrderManagement01.createOrderBefore(OrderDataDTO.builder()
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
					.calculateReq(calculateReq)
					.build());
			createOrderSubmitResponse.then().body("resultCode", Matchers.equalTo("100000"), "resultMsg", Matchers.equalTo("以下商品为限销商品,无法下单:\n商品编码:10000265,商品描述:柴胡滴丸，0.551g/袋，10袋/盒，300盒/箱，OTC,原因:限销品，剩余数量不足，当前可购的最大数量：2.00盒,订单购买数量:3盒\n"));
			priceBatchClearData01.clearPirceData01("at");
			orderBatchClearData01.clearOrderPreDiscountData01("at");
			orderBatchClearData01.clearDiscountData01("at");
			orderBatchClearData01.clearPermittedSalesData01("at");
		});
	}

	private void scenario38() {
		Given("[创建订单提交校验38]当前存在如下客户信息", (DataTable dataTable) -> {
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
		When("[创建订单提交校验38]当前存在如下的预折商品信息", (DataTable dataTable) -> {
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
						.build());
			});
		});
		And("[创建订单提交校验38]当前存在如下的预折折扣信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				BigDecimal calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(8, RoundingMode.HALF_UP);
				String effectiveState = Optional.ofNullable(caseData.get("effectiveState")).orElse("");
				orderBatchUpdateData01.updateOrderPreDiscountDetailCalculationValue(OrderPreDiscountDetailDTO.builder()
								.effectiveState(effectiveState)
								.calculationValue(calculationValue)
								.itemSapCode(itemSapCode)
								.build(),
						OrderPreDiscountDTO.builder()
								.customerSapCode(customerSapCode)
								.build());
			});
		});
		And("[创建订单提交校验38]当前存在如下的整单折扣申请单信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验38]当前存在如下的整单折扣申请单商品信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验38]当前存在如下的整单折扣分配计划信息", () -> {
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
		And("[创建订单提交校验38]当前存在如下的整单折扣分配计划详细信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验38]添加商品", (DataTable dataTable) -> {
			log.info("添加商品");
		});
		And("[创建订单提交校验38]选择项目", (DataTable dataTable) -> {
			log.info("选择项目");
		});
		And("[创建订单提交校验38]当前存在如下的商品禁销信息", (DataTable dataTable) -> {
			saleOrgControlledProductId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			permittedSalesRegionId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			orderPermittedSalesCustomerId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			dataTable.asMaps().stream().forEach(caseData -> {
				String saleOrgSapCode = Optional.ofNullable(caseData.get("saleOrgSapCode")).orElse("");
				String regionCode = Optional.ofNullable(caseData.get("regionCode")).orElse("");
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String permittedSaleQuantityRegion = Optional.ofNullable(caseData.get("permittedSaleQuantityRegion")).orElse("");
				String hasSaleQuantityUnits = Optional.ofNullable(caseData.get("hasSaleQuantityUnits")).orElse("");
				String type = Optional.ofNullable(caseData.get("type")).orElse("");
				String customerName = Optional.ofNullable(caseData.get("customerName")).orElse("");
				String unitCode = Optional.ofNullable(caseData.get("unitCode")).orElse("");
				String unitName = Optional.ofNullable(caseData.get("unitName")).orElse("");
				String customerEntZtCode = Optional.ofNullable(caseData.get("customerEntZtCode")).orElse("");
				String customerEntSapCode = Optional.ofNullable(caseData.get("customerEntSapCode")).orElse("");
				String customerEntCrmCode = Optional.ofNullable(caseData.get("customerEntCrmCode")).orElse("");
				String saleOrgEffectiveState = Optional.ofNullable(caseData.get("saleOrgEffectiveState")).orElse("");
				String regionEffectiveState = Optional.ofNullable(caseData.get("regionEffectiveState")).orElse("");
				String customerEffectiveState = Optional.ofNullable(caseData.get("customerEffectiveState")).orElse("");
				String hasSaleQuantityCustomer = Optional.ofNullable(caseData.get("hasSaleQuantityCustomer")).orElse("");
				String permittedSaleQuantityCustomer = Optional.ofNullable(caseData.get("permittedSaleQuantityCustomer")).orElse("");
				permittedSaleBeginDate = new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis());
				permittedSaleEndDate = new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis());
				orderBatchInsertData01.insertOrderSaleOrgControlledProductData01(OrderSaleOrgControlledProductDTO.builder().saleOrgControlledProductId(saleOrgControlledProductId).saleOrgSapCode(saleOrgSapCode).productSapSku(itemSapCode).effectiveState(saleOrgEffectiveState).createPerson("at").createTime(createTime).build());
				orderBatchInsertData01.insertOrderPermittedSalesRegionData01(OrderPermittedSalesRegionDTO.builder().orderPermittedSalesRegionId(permittedSalesRegionId).saleOrgControlledProductId(saleOrgControlledProductId).regionCode(regionCode).regionName(RegionEnum.fetchName(regionCode)).permittedSaleQuantity(permittedSaleQuantityRegion).unitCode(unitCode).unitName(unitName).permittedSaleBeginDate(permittedSaleBeginDate).permittedSaleEndDate(permittedSaleEndDate).effectiveState(regionEffectiveState).createPerson("at").createTime(createTime).build());
				orderBatchInsertData01.insertOrderPermittedSalesCustomerData01(OrderPermittedSalesCustomerDTO.builder().orderPermittedSalesCustomerId(orderPermittedSalesCustomerId).saleOrgControlledProductId(saleOrgControlledProductId).permittedSalesRegionId(permittedSalesRegionId).customerEntZtCode(customerEntZtCode).customerEntSapCode(customerEntSapCode).customerEntCrmCode(customerEntCrmCode).customerName(customerName).permittedSaleQuantity(permittedSaleQuantityCustomer).hasSaleQuantity(hasSaleQuantityCustomer).type(type).effectiveState(customerEffectiveState).createPerson("at").createTime(createTime).build());
			});

			log.info("设置商品禁销信息");
		});
		Then("[创建订单提交校验38]创建订单提交校验通过", (DataTable dataTable) -> {
			String totalInlineSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("totalInline")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
			String preDiscountTotalSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("preDiscountTotal")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
			String orderDiscountTotalSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("orderDiscountTotal")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
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
			OrderDiscountCalcCalculateReqDTO calculateReq = orderApiOrderManagement01.discountCalcAfterData(OrderDataDTO.builder()
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
							.build())
					.build());
			Response createOrderSubmitResponse = orderApiOrderManagement01.createOrderBefore(OrderDataDTO.builder()
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
					.calculateReq(calculateReq)
					.build());
			createOrderSubmitResponse.then().body("resultCode", Matchers.equalTo("0"), "resultMsg", Matchers.equalTo("success"));
			priceBatchClearData01.clearPirceData01("at");
			orderBatchClearData01.clearOrderPreDiscountData01("at");
			orderBatchClearData01.clearDiscountData01("at");
			orderBatchClearData01.clearPermittedSalesData01("at");
		});
	}

	private void scenario39() {
		Given("[创建订单提交校验39]当前存在如下客户信息", (DataTable dataTable) -> {
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
		When("[创建订单提交校验39]当前存在如下的预折商品信息", (DataTable dataTable) -> {
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
						.build());
			});
		});
		And("[创建订单提交校验39]当前存在如下的预折折扣信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				BigDecimal calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(8, RoundingMode.HALF_UP);
				String effectiveState = Optional.ofNullable(caseData.get("effectiveState")).orElse("");
				orderBatchUpdateData01.updateOrderPreDiscountDetailCalculationValue(OrderPreDiscountDetailDTO.builder()
								.effectiveState(effectiveState)
								.calculationValue(calculationValue)
								.itemSapCode(itemSapCode)
								.build(),
						OrderPreDiscountDTO.builder()
								.customerSapCode(customerSapCode)
								.build());
			});
		});
		And("[创建订单提交校验39]当前存在如下的整单折扣申请单信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验39]当前存在如下的整单折扣申请单商品信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验39]当前存在如下的整单折扣分配计划信息", () -> {
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
		And("[创建订单提交校验39]当前存在如下的整单折扣分配计划详细信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验39]添加商品", (DataTable dataTable) -> {
			log.info("添加商品");
		});
		And("[创建订单提交校验39]选择项目", (DataTable dataTable) -> {
			log.info("选择项目");
		});
		And("[创建订单提交校验39]当前存在如下的商品禁销信息", (DataTable dataTable) -> {
			saleOrgControlledProductId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			permittedSalesRegionId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			orderPermittedSalesCustomerId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			dataTable.asMaps().stream().forEach(caseData -> {
				String saleOrgSapCode = Optional.ofNullable(caseData.get("saleOrgSapCode")).orElse("");
				String regionCode = Optional.ofNullable(caseData.get("regionCode")).orElse("");
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String permittedSaleQuantityRegion = Optional.ofNullable(caseData.get("permittedSaleQuantityRegion")).orElse("");
				String hasSaleQuantityUnits = Optional.ofNullable(caseData.get("hasSaleQuantityUnits")).orElse("");
				String type = Optional.ofNullable(caseData.get("type")).orElse("");
				String customerName = Optional.ofNullable(caseData.get("customerName")).orElse("");
				String unitCode = Optional.ofNullable(caseData.get("unitCode")).orElse("");
				String unitName = Optional.ofNullable(caseData.get("unitName")).orElse("");
				String customerEntZtCode = Optional.ofNullable(caseData.get("customerEntZtCode")).orElse("");
				String customerEntSapCode = Optional.ofNullable(caseData.get("customerEntSapCode")).orElse("");
				String customerEntCrmCode = Optional.ofNullable(caseData.get("customerEntCrmCode")).orElse("");
				String saleOrgEffectiveState = Optional.ofNullable(caseData.get("saleOrgEffectiveState")).orElse("");
				String regionEffectiveState = Optional.ofNullable(caseData.get("regionEffectiveState")).orElse("");
				String customerEffectiveState = Optional.ofNullable(caseData.get("customerEffectiveState")).orElse("");
				String hasSaleQuantityCustomer = Optional.ofNullable(caseData.get("hasSaleQuantityCustomer")).orElse("");
				String permittedSaleQuantityCustomer = Optional.ofNullable(caseData.get("permittedSaleQuantityCustomer")).orElse("");
				permittedSaleBeginDate = new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis());
				permittedSaleEndDate = new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis());
				orderBatchInsertData01.insertOrderSaleOrgControlledProductData01(OrderSaleOrgControlledProductDTO.builder().saleOrgControlledProductId(saleOrgControlledProductId).saleOrgSapCode(saleOrgSapCode).productSapSku(itemSapCode).effectiveState(saleOrgEffectiveState).createPerson("at").createTime(createTime).build());
				orderBatchInsertData01.insertOrderPermittedSalesRegionData01(OrderPermittedSalesRegionDTO.builder().orderPermittedSalesRegionId(permittedSalesRegionId).saleOrgControlledProductId(saleOrgControlledProductId).regionCode(regionCode).regionName(RegionEnum.fetchName(regionCode)).permittedSaleQuantity(permittedSaleQuantityRegion).unitCode(unitCode).unitName(unitName).permittedSaleBeginDate(permittedSaleBeginDate).permittedSaleEndDate(permittedSaleEndDate).effectiveState(regionEffectiveState).createPerson("at").createTime(createTime).build());
				orderBatchInsertData01.insertOrderPermittedSalesCustomerData01(OrderPermittedSalesCustomerDTO.builder().orderPermittedSalesCustomerId(orderPermittedSalesCustomerId).saleOrgControlledProductId(saleOrgControlledProductId).permittedSalesRegionId(permittedSalesRegionId).customerEntZtCode(customerEntZtCode).customerEntSapCode(customerEntSapCode).customerEntCrmCode(customerEntCrmCode).customerName(customerName).permittedSaleQuantity(permittedSaleQuantityCustomer).hasSaleQuantity(hasSaleQuantityCustomer).type(type).effectiveState(customerEffectiveState).createPerson("at").createTime(createTime).build());
			});

			log.info("设置商品禁销信息");
		});
		And("[创建订单提交校验39]当前存在如下的商品一级商允销信息", (DataTable dataTable) -> {
			orderPermittedSalesCustomerId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			dataTable.asMaps().stream().forEach(caseData -> {
				String type = Optional.ofNullable(caseData.get("type")).orElse("");
				String permittedSaleQuantityCustomer = Optional.ofNullable(caseData.get("permittedSaleQuantityCustomer")).orElse("");
				String hasSaleQuantityCustomer = Optional.ofNullable(caseData.get("hasSaleQuantityCustomer")).orElse("");
				String customerEffectiveState = Optional.ofNullable(caseData.get("customerEffectiveState")).orElse("");
				orderBatchInsertData01.insertOrderPermittedSalesCustomerData01(OrderPermittedSalesCustomerDTO.builder().orderPermittedSalesCustomerId(orderPermittedSalesCustomerId).saleOrgControlledProductId(saleOrgControlledProductId).permittedSalesRegionId(permittedSalesRegionId).customerEntSapCode(customerSapCode).customerEntCrmCode(CompanyEnum.fetchCrmCode(customerSapCode)).customerEntZtCode(CompanyEnum.fetchZtCode(customerSapCode)).customerName(CompanyEnum.fetchName(customerSapCode)).permittedSaleQuantity(permittedSaleQuantityCustomer).hasSaleQuantity(hasSaleQuantityCustomer).type(type).effectiveState(customerEffectiveState).createPerson("at").createTime(createTime).build());
			});

			log.info("设置商品一级商允销信息");
		});
		Then("[创建订单提交校验39]创建订单提交校验通过", (DataTable dataTable) -> {
			String totalInlineSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("totalInline")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
			String preDiscountTotalSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("preDiscountTotal")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
			String orderDiscountTotalSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("orderDiscountTotal")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
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
			OrderDiscountCalcCalculateReqDTO calculateReq = orderApiOrderManagement01.discountCalcAfterData(OrderDataDTO.builder()
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
							.build())
					.build());
			Response createOrderSubmitResponse = orderApiOrderManagement01.createOrderBefore(OrderDataDTO.builder()
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
					.calculateReq(calculateReq)
					.build());
			createOrderSubmitResponse.then().body("resultCode", Matchers.equalTo("0"), "resultMsg", Matchers.equalTo("success"));
			priceBatchClearData01.clearPirceData01("at");
			orderBatchClearData01.clearOrderPreDiscountData01("at");
			orderBatchClearData01.clearDiscountData01("at");
			orderBatchClearData01.clearPermittedSalesData01("at");
		});
	}

	private void scenario40() {
		Given("[创建订单提交校验40]当前存在如下客户信息", (DataTable dataTable) -> {
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
		When("[创建订单提交校验40]当前存在如下的预折商品信息", (DataTable dataTable) -> {
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
						.build());
			});
		});
		And("[创建订单提交校验40]当前存在如下的预折折扣信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				BigDecimal calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(8, RoundingMode.HALF_UP);
				String effectiveState = Optional.ofNullable(caseData.get("effectiveState")).orElse("");
				orderBatchUpdateData01.updateOrderPreDiscountDetailCalculationValue(OrderPreDiscountDetailDTO.builder()
								.effectiveState(effectiveState)
								.calculationValue(calculationValue)
								.itemSapCode(itemSapCode)
								.build(),
						OrderPreDiscountDTO.builder()
								.customerSapCode(customerSapCode)
								.build());
			});
		});
		And("[创建订单提交校验40]当前存在如下的整单折扣申请单信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验40]当前存在如下的整单折扣申请单商品信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验40]当前存在如下的整单折扣分配计划信息", () -> {
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
		And("[创建订单提交校验40]当前存在如下的整单折扣分配计划详细信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验40]添加商品", (DataTable dataTable) -> {
			log.info("添加商品");
		});
		And("[创建订单提交校验40]选择项目", (DataTable dataTable) -> {
			log.info("选择项目");
		});
		And("[创建订单提交校验40]当前存在如下的商品禁销信息", (DataTable dataTable) -> {
			saleOrgControlledProductId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			permittedSalesRegionId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			orderPermittedSalesCustomerId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			dataTable.asMaps().stream().forEach(caseData -> {
				String saleOrgSapCode = Optional.ofNullable(caseData.get("saleOrgSapCode")).orElse("");
				String regionCode = Optional.ofNullable(caseData.get("regionCode")).orElse("");
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String permittedSaleQuantityRegion = Optional.ofNullable(caseData.get("permittedSaleQuantityRegion")).orElse("");
				String hasSaleQuantityUnits = Optional.ofNullable(caseData.get("hasSaleQuantityUnits")).orElse("");
				String type = Optional.ofNullable(caseData.get("type")).orElse("");
				String customerName = Optional.ofNullable(caseData.get("customerName")).orElse("");
				String unitCode = Optional.ofNullable(caseData.get("unitCode")).orElse("");
				String unitName = Optional.ofNullable(caseData.get("unitName")).orElse("");
				String customerEntZtCode = Optional.ofNullable(caseData.get("customerEntZtCode")).orElse("");
				String customerEntSapCode = Optional.ofNullable(caseData.get("customerEntSapCode")).orElse("");
				String customerEntCrmCode = Optional.ofNullable(caseData.get("customerEntCrmCode")).orElse("");
				String saleOrgEffectiveState = Optional.ofNullable(caseData.get("saleOrgEffectiveState")).orElse("");
				String regionEffectiveState = Optional.ofNullable(caseData.get("regionEffectiveState")).orElse("");
				String customerEffectiveState = Optional.ofNullable(caseData.get("customerEffectiveState")).orElse("");
				String hasSaleQuantityCustomer = Optional.ofNullable(caseData.get("hasSaleQuantityCustomer")).orElse("");
				String permittedSaleQuantityCustomer = Optional.ofNullable(caseData.get("permittedSaleQuantityCustomer")).orElse("");
				permittedSaleBeginDate = new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis());
				permittedSaleEndDate = new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis());
				orderBatchInsertData01.insertOrderSaleOrgControlledProductData01(OrderSaleOrgControlledProductDTO.builder().saleOrgControlledProductId(saleOrgControlledProductId).saleOrgSapCode(saleOrgSapCode).productSapSku(itemSapCode).effectiveState(saleOrgEffectiveState).createPerson("at").createTime(createTime).build());
				orderBatchInsertData01.insertOrderPermittedSalesRegionData01(OrderPermittedSalesRegionDTO.builder().orderPermittedSalesRegionId(permittedSalesRegionId).saleOrgControlledProductId(saleOrgControlledProductId).regionCode(regionCode).regionName(RegionEnum.fetchName(regionCode)).permittedSaleQuantity(permittedSaleQuantityRegion).unitCode(unitCode).unitName(unitName).permittedSaleBeginDate(permittedSaleBeginDate).permittedSaleEndDate(permittedSaleEndDate).effectiveState(regionEffectiveState).createPerson("at").createTime(createTime).build());
				orderBatchInsertData01.insertOrderPermittedSalesCustomerData01(OrderPermittedSalesCustomerDTO.builder().orderPermittedSalesCustomerId(orderPermittedSalesCustomerId).saleOrgControlledProductId(saleOrgControlledProductId).permittedSalesRegionId(permittedSalesRegionId).customerEntZtCode(customerEntZtCode).customerEntSapCode(customerEntSapCode).customerEntCrmCode(customerEntCrmCode).customerName(customerName).permittedSaleQuantity(permittedSaleQuantityCustomer).hasSaleQuantity(hasSaleQuantityCustomer).type(type).effectiveState(customerEffectiveState).createPerson("at").createTime(createTime).build());
			});

			log.info("设置商品禁销信息");
		});
		Then("[创建订单提交校验40]创建订单应校验失败且提示错误信息", (DataTable dataTable) -> {
			String totalInlineSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("totalInline")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
			String preDiscountTotalSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("preDiscountTotal")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
			String orderDiscountTotalSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("orderDiscountTotal")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
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
			OrderDiscountCalcCalculateReqDTO calculateReq = orderApiOrderManagement01.discountCalcAfterData(OrderDataDTO.builder()
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
							.build())
					.build());
			Response createOrderSubmitResponse = orderApiOrderManagement01.createOrderBefore(OrderDataDTO.builder()
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
					.calculateReq(calculateReq)
					.build());
			createOrderSubmitResponse.then().body("resultCode", Matchers.equalTo("100000"), "resultMsg", Matchers.equalTo("以下商品为限销商品,无法下单:\n商品编码:10000841,商品描述:醒脑静注射液，10ml/支，2支/盒，240盒/箱,原因:限销品，剩余数量不足，当前可购的最大数量：9.00盒,订单购买数量:10盒\n"));
			priceBatchClearData01.clearPirceData01("at");
			orderBatchClearData01.clearOrderPreDiscountData01("at");
			orderBatchClearData01.clearDiscountData01("at");
			orderBatchClearData01.clearPermittedSalesData01("at");
		});
	}

	private void scenario41() {
		Given("[创建订单提交校验41]当前存在如下客户信息", (DataTable dataTable) -> {
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
		When("[创建订单提交校验41]当前存在如下的预折商品信息", (DataTable dataTable) -> {
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
						.build());
			});
		});
		And("[创建订单提交校验41]当前存在如下的预折折扣信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				BigDecimal calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(8, RoundingMode.HALF_UP);
				String effectiveState = Optional.ofNullable(caseData.get("effectiveState")).orElse("");
				orderBatchUpdateData01.updateOrderPreDiscountDetailCalculationValue(OrderPreDiscountDetailDTO.builder()
								.effectiveState(effectiveState)
								.calculationValue(calculationValue)
								.itemSapCode(itemSapCode)
								.build(),
						OrderPreDiscountDTO.builder()
								.customerSapCode(customerSapCode)
								.build());
			});
		});
		And("[创建订单提交校验41]当前存在如下的整单折扣申请单信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验41]当前存在如下的整单折扣申请单商品信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验41]当前存在如下的整单折扣分配计划信息", () -> {
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
		And("[创建订单提交校验41]当前存在如下的整单折扣分配计划详细信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验41]添加商品", (DataTable dataTable) -> {
			log.info("添加商品");
		});
		And("[创建订单提交校验41]选择项目", (DataTable dataTable) -> {
			log.info("选择项目");
		});
		And("[创建订单提交校验41]当前存在如下的商品禁销信息", (DataTable dataTable) -> {
			saleOrgControlledProductId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			permittedSalesRegionId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			orderPermittedSalesCustomerId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			dataTable.asMaps().stream().forEach(caseData -> {
				String saleOrgSapCode = Optional.ofNullable(caseData.get("saleOrgSapCode")).orElse("");
				String regionCode = Optional.ofNullable(caseData.get("regionCode")).orElse("");
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String permittedSaleQuantityRegion = Optional.ofNullable(caseData.get("permittedSaleQuantityRegion")).orElse("");
				String hasSaleQuantityUnits = Optional.ofNullable(caseData.get("hasSaleQuantityUnits")).orElse("");
				String type = Optional.ofNullable(caseData.get("type")).orElse("");
				String customerName = Optional.ofNullable(caseData.get("customerName")).orElse("");
				String unitCode = Optional.ofNullable(caseData.get("unitCode")).orElse("");
				String unitName = Optional.ofNullable(caseData.get("unitName")).orElse("");
				String customerEntZtCode = Optional.ofNullable(caseData.get("customerEntZtCode")).orElse("");
				String customerEntSapCode = Optional.ofNullable(caseData.get("customerEntSapCode")).orElse("");
				String customerEntCrmCode = Optional.ofNullable(caseData.get("customerEntCrmCode")).orElse("");
				String saleOrgEffectiveState = Optional.ofNullable(caseData.get("saleOrgEffectiveState")).orElse("");
				String regionEffectiveState = Optional.ofNullable(caseData.get("regionEffectiveState")).orElse("");
				String customerEffectiveState = Optional.ofNullable(caseData.get("customerEffectiveState")).orElse("");
				String hasSaleQuantityCustomer = Optional.ofNullable(caseData.get("hasSaleQuantityCustomer")).orElse("");
				String permittedSaleQuantityCustomer = Optional.ofNullable(caseData.get("permittedSaleQuantityCustomer")).orElse("");
				permittedSaleBeginDate = new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis());
				permittedSaleEndDate = new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis());
				orderBatchInsertData01.insertOrderSaleOrgControlledProductData01(OrderSaleOrgControlledProductDTO.builder().saleOrgControlledProductId(saleOrgControlledProductId).saleOrgSapCode(saleOrgSapCode).productSapSku(itemSapCode).effectiveState(saleOrgEffectiveState).createPerson("at").createTime(createTime).build());
				orderBatchInsertData01.insertOrderPermittedSalesRegionData01(OrderPermittedSalesRegionDTO.builder().orderPermittedSalesRegionId(permittedSalesRegionId).saleOrgControlledProductId(saleOrgControlledProductId).regionCode(regionCode).regionName(RegionEnum.fetchName(regionCode)).permittedSaleQuantity(permittedSaleQuantityRegion).unitCode(unitCode).unitName(unitName).permittedSaleBeginDate(permittedSaleBeginDate).permittedSaleEndDate(permittedSaleEndDate).effectiveState(regionEffectiveState).createPerson("at").createTime(createTime).build());
				orderBatchInsertData01.insertOrderPermittedSalesCustomerData01(OrderPermittedSalesCustomerDTO.builder().orderPermittedSalesCustomerId(orderPermittedSalesCustomerId).saleOrgControlledProductId(saleOrgControlledProductId).permittedSalesRegionId(permittedSalesRegionId).customerEntZtCode(customerEntZtCode).customerEntSapCode(customerEntSapCode).customerEntCrmCode(customerEntCrmCode).customerName(customerName).permittedSaleQuantity(permittedSaleQuantityCustomer).hasSaleQuantity(hasSaleQuantityCustomer).type(type).effectiveState(customerEffectiveState).createPerson("at").createTime(createTime).build());
			});

			log.info("设置商品禁销信息");
		});
		And("[创建订单提交校验41]当前存在如下的商品一级商允销信息", (DataTable dataTable) -> {
			orderPermittedSalesCustomerId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			dataTable.asMaps().stream().forEach(caseData -> {
				String type = Optional.ofNullable(caseData.get("type")).orElse("");
				String permittedSaleQuantityCustomer = Optional.ofNullable(caseData.get("permittedSaleQuantityCustomer")).orElse("");
				String hasSaleQuantityCustomer = Optional.ofNullable(caseData.get("hasSaleQuantityCustomer")).orElse("");
				String customerEffectiveState = Optional.ofNullable(caseData.get("customerEffectiveState")).orElse("");
				orderBatchInsertData01.insertOrderPermittedSalesCustomerData01(OrderPermittedSalesCustomerDTO.builder().orderPermittedSalesCustomerId(orderPermittedSalesCustomerId).saleOrgControlledProductId(saleOrgControlledProductId).permittedSalesRegionId(permittedSalesRegionId).customerEntSapCode(customerSapCode).customerEntCrmCode(CompanyEnum.fetchCrmCode(customerSapCode)).customerEntZtCode(CompanyEnum.fetchZtCode(customerSapCode)).customerName(CompanyEnum.fetchName(customerSapCode)).permittedSaleQuantity(permittedSaleQuantityCustomer).hasSaleQuantity(hasSaleQuantityCustomer).type(type).effectiveState(customerEffectiveState).createPerson("at").createTime(createTime).build());
			});

			log.info("设置商品一级商允销信息");
		});
		Then("[创建订单提交校验41]创建订单应校验失败且提示错误信息", (DataTable dataTable) -> {
			String totalInlineSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("totalInline")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
			String preDiscountTotalSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("preDiscountTotal")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
			String orderDiscountTotalSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("orderDiscountTotal")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
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
			OrderDiscountCalcCalculateReqDTO calculateReq = orderApiOrderManagement01.discountCalcAfterData(OrderDataDTO.builder()
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
							.build())
					.build());
			Response createOrderSubmitResponse = orderApiOrderManagement01.createOrderBefore(OrderDataDTO.builder()
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
					.calculateReq(calculateReq)
					.build());
			createOrderSubmitResponse.then().body("resultCode", Matchers.equalTo("100000"), "resultMsg", Matchers.equalTo("以下商品为限销商品,无法下单:\n商品编码:10000841,商品描述:醒脑静注射液，10ml/支，2支/盒，240盒/箱,原因:限销品，剩余数量不足，当前可购的最大数量：9.00盒,订单购买数量:10盒\n"));
			priceBatchClearData01.clearPirceData01("at");
			orderBatchClearData01.clearOrderPreDiscountData01("at");
			orderBatchClearData01.clearDiscountData01("at");
			orderBatchClearData01.clearPermittedSalesData01("at");
		});
	}

	private void scenario42() {
		Given("[创建订单提交校验42]当前存在如下客户信息", (DataTable dataTable) -> {
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
		When("[创建订单提交校验42]当前存在如下的预折商品信息", (DataTable dataTable) -> {
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
						.build());
			});
		});
		And("[创建订单提交校验42]当前存在如下的预折折扣信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				BigDecimal calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(8, RoundingMode.HALF_UP);
				String effectiveState = Optional.ofNullable(caseData.get("effectiveState")).orElse("");
				orderBatchUpdateData01.updateOrderPreDiscountDetailCalculationValue(OrderPreDiscountDetailDTO.builder()
								.effectiveState(effectiveState)
								.calculationValue(calculationValue)
								.itemSapCode(itemSapCode)
								.build(),
						OrderPreDiscountDTO.builder()
								.customerSapCode(customerSapCode)
								.build());
			});
		});
		And("[创建订单提交校验42]当前存在如下的整单折扣申请单信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验42]当前存在如下的整单折扣申请单商品信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验42]当前存在如下的整单折扣分配计划信息", () -> {
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
		And("[创建订单提交校验42]当前存在如下的整单折扣分配计划详细信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验42]添加商品", (DataTable dataTable) -> {
			log.info("添加商品");
		});
		And("[创建订单提交校验42]选择项目", (DataTable dataTable) -> {
			log.info("选择项目");
		});
		And("[创建订单提交校验42]当前存在如下的商品禁销信息", (DataTable dataTable) -> {
			saleOrgControlledProductId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			permittedSalesRegionId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			orderPermittedSalesCustomerId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			dataTable.asMaps().stream().forEach(caseData -> {
				String saleOrgSapCode = Optional.ofNullable(caseData.get("saleOrgSapCode")).orElse("");
				String regionCode = Optional.ofNullable(caseData.get("regionCode")).orElse("");
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String permittedSaleQuantityRegion = Optional.ofNullable(caseData.get("permittedSaleQuantityRegion")).orElse("");
				String hasSaleQuantityUnits = Optional.ofNullable(caseData.get("hasSaleQuantityUnits")).orElse("");
				String type = Optional.ofNullable(caseData.get("type")).orElse("");
				String customerName = Optional.ofNullable(caseData.get("customerName")).orElse("");
				String unitCode = Optional.ofNullable(caseData.get("unitCode")).orElse("");
				String unitName = Optional.ofNullable(caseData.get("unitName")).orElse("");
				String customerEntZtCode = Optional.ofNullable(caseData.get("customerEntZtCode")).orElse("");
				String customerEntSapCode = Optional.ofNullable(caseData.get("customerEntSapCode")).orElse("");
				String customerEntCrmCode = Optional.ofNullable(caseData.get("customerEntCrmCode")).orElse("");
				String saleOrgEffectiveState = Optional.ofNullable(caseData.get("saleOrgEffectiveState")).orElse("");
				String regionEffectiveState = Optional.ofNullable(caseData.get("regionEffectiveState")).orElse("");
				String customerEffectiveState = Optional.ofNullable(caseData.get("customerEffectiveState")).orElse("");
				String hasSaleQuantityCustomer = Optional.ofNullable(caseData.get("hasSaleQuantityCustomer")).orElse("");
				String permittedSaleQuantityCustomer = Optional.ofNullable(caseData.get("permittedSaleQuantityCustomer")).orElse("");
				permittedSaleBeginDate = new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis());
				permittedSaleEndDate = new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis());
				orderBatchInsertData01.insertOrderSaleOrgControlledProductData01(OrderSaleOrgControlledProductDTO.builder().saleOrgControlledProductId(saleOrgControlledProductId).saleOrgSapCode(saleOrgSapCode).productSapSku(itemSapCode).effectiveState(saleOrgEffectiveState).createPerson("at").createTime(createTime).build());
				orderBatchInsertData01.insertOrderPermittedSalesRegionData01(OrderPermittedSalesRegionDTO.builder().orderPermittedSalesRegionId(permittedSalesRegionId).saleOrgControlledProductId(saleOrgControlledProductId).regionCode(regionCode).regionName(RegionEnum.fetchName(regionCode)).permittedSaleQuantity(permittedSaleQuantityRegion).unitCode(unitCode).unitName(unitName).permittedSaleBeginDate(permittedSaleBeginDate).permittedSaleEndDate(permittedSaleEndDate).effectiveState(regionEffectiveState).createPerson("at").createTime(createTime).build());
				orderBatchInsertData01.insertOrderPermittedSalesCustomerData01(OrderPermittedSalesCustomerDTO.builder().orderPermittedSalesCustomerId(orderPermittedSalesCustomerId).saleOrgControlledProductId(saleOrgControlledProductId).permittedSalesRegionId(permittedSalesRegionId).customerEntZtCode(customerEntZtCode).customerEntSapCode(customerEntSapCode).customerEntCrmCode(customerEntCrmCode).customerName(customerName).permittedSaleQuantity(permittedSaleQuantityCustomer).hasSaleQuantity(hasSaleQuantityCustomer).type(type).effectiveState(customerEffectiveState).createPerson("at").createTime(createTime).build());
			});

			log.info("设置商品禁销信息");
		});
		Then("[创建订单提交校验42]创建订单提交校验通过", (DataTable dataTable) -> {
			String totalInlineSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("totalInline")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
			String preDiscountTotalSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("preDiscountTotal")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
			String orderDiscountTotalSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("orderDiscountTotal")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
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
			OrderDiscountCalcCalculateReqDTO calculateReq = orderApiOrderManagement01.discountCalcAfterData(OrderDataDTO.builder()
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
							.build())
					.build());
			Response createOrderSubmitResponse = orderApiOrderManagement01.createOrderBefore(OrderDataDTO.builder()
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
					.calculateReq(calculateReq)
					.build());
			createOrderSubmitResponse.then().body("resultCode", Matchers.equalTo("0"), "resultMsg", Matchers.equalTo("success"));
			priceBatchClearData01.clearPirceData01("at");
			orderBatchClearData01.clearOrderPreDiscountData01("at");
			orderBatchClearData01.clearDiscountData01("at");
			orderBatchClearData01.clearPermittedSalesData01("at");
		});
	}

	private void scenario43() {
		Given("[创建订单提交校验43]当前存在如下客户信息", (DataTable dataTable) -> {
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
		When("[创建订单提交校验43]当前存在如下的预折商品信息", (DataTable dataTable) -> {
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
						.build());
			});
		});
		And("[创建订单提交校验43]当前存在如下的预折折扣信息", (DataTable dataTable) -> {
			dataTable.asMaps().stream().forEach(caseData -> {
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				BigDecimal calculationValue = new BigDecimal(caseData.get("calculationValue")).setScale(8, RoundingMode.HALF_UP);
				String effectiveState = Optional.ofNullable(caseData.get("effectiveState")).orElse("");
				orderBatchUpdateData01.updateOrderPreDiscountDetailCalculationValue(OrderPreDiscountDetailDTO.builder()
								.effectiveState(effectiveState)
								.calculationValue(calculationValue)
								.itemSapCode(itemSapCode)
								.build(),
						OrderPreDiscountDTO.builder()
								.customerSapCode(customerSapCode)
								.build());
			});
		});
		And("[创建订单提交校验43]当前存在如下的整单折扣申请单信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验43]当前存在如下的整单折扣申请单商品信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验43]当前存在如下的整单折扣分配计划信息", () -> {
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
		And("[创建订单提交校验43]当前存在如下的整单折扣分配计划详细信息", (DataTable dataTable) -> {
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
		And("[创建订单提交校验43]添加商品", (DataTable dataTable) -> {
			log.info("添加商品");
		});
		And("[创建订单提交校验43]选择项目", (DataTable dataTable) -> {
			log.info("选择项目");
		});
		And("[创建订单提交校验43]当前存在如下的商品禁销信息", (DataTable dataTable) -> {
			saleOrgControlledProductId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			permittedSalesRegionId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			orderPermittedSalesCustomerId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			dataTable.asMaps().stream().forEach(caseData -> {
				String saleOrgSapCode = Optional.ofNullable(caseData.get("saleOrgSapCode")).orElse("");
				String regionCode = Optional.ofNullable(caseData.get("regionCode")).orElse("");
				String itemSapCode = Optional.ofNullable(caseData.get("itemSapCode")).orElse("");
				String permittedSaleQuantityRegion = Optional.ofNullable(caseData.get("permittedSaleQuantityRegion")).orElse("");
				String hasSaleQuantityUnits = Optional.ofNullable(caseData.get("hasSaleQuantityUnits")).orElse("");
				String type = Optional.ofNullable(caseData.get("type")).orElse("");
				String customerName = Optional.ofNullable(caseData.get("customerName")).orElse("");
				String unitCode = Optional.ofNullable(caseData.get("unitCode")).orElse("");
				String unitName = Optional.ofNullable(caseData.get("unitName")).orElse("");
				String customerEntZtCode = Optional.ofNullable(caseData.get("customerEntZtCode")).orElse("");
				String customerEntSapCode = Optional.ofNullable(caseData.get("customerEntSapCode")).orElse("");
				String customerEntCrmCode = Optional.ofNullable(caseData.get("customerEntCrmCode")).orElse("");
				String saleOrgEffectiveState = Optional.ofNullable(caseData.get("saleOrgEffectiveState")).orElse("");
				String regionEffectiveState = Optional.ofNullable(caseData.get("regionEffectiveState")).orElse("");
				String customerEffectiveState = Optional.ofNullable(caseData.get("customerEffectiveState")).orElse("");
				String hasSaleQuantityCustomer = Optional.ofNullable(caseData.get("hasSaleQuantityCustomer")).orElse("");
				String permittedSaleQuantityCustomer = Optional.ofNullable(caseData.get("permittedSaleQuantityCustomer")).orElse("");
				permittedSaleBeginDate = new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis());
				permittedSaleEndDate = new SimpleDateFormat("yyyy-MM-dd").format(System.currentTimeMillis());
				orderBatchInsertData01.insertOrderSaleOrgControlledProductData01(OrderSaleOrgControlledProductDTO.builder().saleOrgControlledProductId(saleOrgControlledProductId).saleOrgSapCode(saleOrgSapCode).productSapSku(itemSapCode).effectiveState(saleOrgEffectiveState).createPerson("at").createTime(createTime).build());
				orderBatchInsertData01.insertOrderPermittedSalesRegionData01(OrderPermittedSalesRegionDTO.builder().orderPermittedSalesRegionId(permittedSalesRegionId).saleOrgControlledProductId(saleOrgControlledProductId).regionCode(regionCode).regionName(RegionEnum.fetchName(regionCode)).permittedSaleQuantity(permittedSaleQuantityRegion).unitCode(unitCode).unitName(unitName).permittedSaleBeginDate(permittedSaleBeginDate).permittedSaleEndDate(permittedSaleEndDate).effectiveState(regionEffectiveState).createPerson("at").createTime(createTime).build());
				orderBatchInsertData01.insertOrderPermittedSalesCustomerData01(OrderPermittedSalesCustomerDTO.builder().orderPermittedSalesCustomerId(orderPermittedSalesCustomerId).saleOrgControlledProductId(saleOrgControlledProductId).permittedSalesRegionId(permittedSalesRegionId).customerEntZtCode(customerEntZtCode).customerEntSapCode(customerEntSapCode).customerEntCrmCode(customerEntCrmCode).customerName(customerName).permittedSaleQuantity(permittedSaleQuantityCustomer).hasSaleQuantity(hasSaleQuantityCustomer).type(type).effectiveState(customerEffectiveState).createPerson("at").createTime(createTime).build());
			});

			log.info("设置商品禁销信息");
		});
		And("[创建订单提交校验43]当前存在如下的商品一级商允销信息", (DataTable dataTable) -> {
			orderPermittedSalesCustomerId = IdUtil.getSnowflake(workerId, dataCenterId).nextIdStr();
			dataTable.asMaps().stream().forEach(caseData -> {
				String type = Optional.ofNullable(caseData.get("type")).orElse("");
				String permittedSaleQuantityCustomer = Optional.ofNullable(caseData.get("permittedSaleQuantityCustomer")).orElse("");
				String hasSaleQuantityCustomer = Optional.ofNullable(caseData.get("hasSaleQuantityCustomer")).orElse("");
				String customerEffectiveState = Optional.ofNullable(caseData.get("customerEffectiveState")).orElse("");
				orderBatchInsertData01.insertOrderPermittedSalesCustomerData01(OrderPermittedSalesCustomerDTO.builder().orderPermittedSalesCustomerId(orderPermittedSalesCustomerId).saleOrgControlledProductId(saleOrgControlledProductId).permittedSalesRegionId(permittedSalesRegionId).customerEntSapCode(customerSapCode).customerEntCrmCode(CompanyEnum.fetchCrmCode(customerSapCode)).customerEntZtCode(CompanyEnum.fetchZtCode(customerSapCode)).customerName(CompanyEnum.fetchName(customerSapCode)).permittedSaleQuantity(permittedSaleQuantityCustomer).hasSaleQuantity(hasSaleQuantityCustomer).type(type).effectiveState(customerEffectiveState).createPerson("at").createTime(createTime).build());
			});

			log.info("设置商品一级商允销信息");
		});
		Then("[创建订单提交校验43]创建订单提交校验通过", (DataTable dataTable) -> {
			String totalInlineSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("totalInline")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
			String preDiscountTotalSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("preDiscountTotal")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
			String orderDiscountTotalSum = dataTable.asMaps().stream().map(caseData -> new BigDecimal(caseData.get("orderDiscountTotal")).setScale(2, RoundingMode.HALF_UP)).reduce(BigDecimal::add).orElse(BigDecimal.ZERO).toString();
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
			OrderDiscountCalcCalculateReqDTO calculateReq = orderApiOrderManagement01.discountCalcAfterData(OrderDataDTO.builder()
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
							.build())
					.build());
			Response createOrderSubmitResponse = orderApiOrderManagement01.createOrderBefore(OrderDataDTO.builder()
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
					.calculateReq(calculateReq)
					.build());
			createOrderSubmitResponse.then().body("resultCode", Matchers.equalTo("0"), "resultMsg", Matchers.equalTo("success"));
			priceBatchClearData01.clearPirceData01("at");
			orderBatchClearData01.clearOrderPreDiscountData01("at");
			orderBatchClearData01.clearDiscountData01("at");
			orderBatchClearData01.clearPermittedSalesData01("at");
		});
	}

}
