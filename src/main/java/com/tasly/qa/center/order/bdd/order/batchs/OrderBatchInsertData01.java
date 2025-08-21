package com.anturo.qa.center.order.bdd.order.batchs;

import com.anturo.qa.center.order.main.model.dto.*;
import com.anturo.qa.center.order.main.support.PersistanceOperations;
import com.anturo.qa.center.order.process.ApplicationEnum;
import com.anturo.qa.center.order.process.CompanyEnum;
import com.anturo.qa.center.order.process.ProductEnum;
import com.anturo.qa.center.order.process.SaleOrgEnum;
import com.panzer.tool.sql.MySqlTools;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import net.thucydides.core.annotations.Step;

/**
 * @Author Tesla
 * @Date 2024/05/21
 * @Description 订单中心插入数据
 */

@SuppressWarnings("all")
@Getter
@Setter
@Log4j2(topic = "DefaultLogger")
public class OrderBatchInsertData01 {

	private MySqlTools orderSystemPersist = PersistanceOperations.mysql("order-center");

	@Step(value = "【INSERT】插入预折主表测试数据")
	public void insertOrderPreDidcountData01(OrderPreDiscountDTO orderPreDiscountDTO) {
		final String SQL = String.format("INSERT INTO `anturo-center-order`.order_pre_discount (ID, CUSTOMER_ENT_ZT_CODE, CUSTOMER_ENT_SAP_CODE,\n" +
						"                                                     CUSTOMER_ENT_CRM_CODE, CUSTOMER_NAME, SALE_ORG_ZT_CODE,\n" +
						"                                                     SALE_ORG_SAP_CODE, SALE_ORG_NAME, REGION_CODE, REGION_NAME,\n" +
						"                                                     EFFECTIVE_STATE, PRE_TYPE, ORDER_TYPE, CREATE_PERSON, CREATE_PERSON_NAME,\n" +
						"                                                     CREATE_TIME, UPDATE_PERSON, UPDATE_PERSON_NAME, UPDATE_TIME, DR,\n" +
						"                                                     EXTENSION, REMARK, TENANT_ID, INSTANCE_ID)\n" +
						"VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s',\n" +
						"        '%s', '%s', '%s', '%s', 'STANDARD', '%s', null, '%s', null,\n" +
						"        null, null, 0, null, null, '1', '1306316410046615556');\n", orderPreDiscountDTO.getPreDiscountId(),
				CompanyEnum.fetchZtCode(orderPreDiscountDTO.getCustomerSapCode()), orderPreDiscountDTO.getCustomerSapCode(),
				CompanyEnum.fetchCrmCode(orderPreDiscountDTO.getCustomerSapCode()), CompanyEnum.fetchName(orderPreDiscountDTO.getCustomerSapCode()),
				SaleOrgEnum.fetchZtCode(orderPreDiscountDTO.getSaleOrgSapCode()), orderPreDiscountDTO.getSaleOrgSapCode(),
				SaleOrgEnum.fetchName(orderPreDiscountDTO.getSaleOrgSapCode()), orderPreDiscountDTO.getRegionCode(), orderPreDiscountDTO.getRegionName(),
				orderPreDiscountDTO.getEffectiveState(), orderPreDiscountDTO.getPreType(), orderPreDiscountDTO.getCreatePerson(), orderPreDiscountDTO.getCreateTime()
		);
		int sqlResult = orderSystemPersist.insert(SQL);
		log.info("[订单中心] 插入预折主表测试数据成功");
	}

	@Step(value = "【INSERT】插入预折Detail表测试数据")
	public void insertOrderPreDidcountDetailData01(OrderPreDiscountDetailDTO orderPreDiscountDetailDTO) {
		final String SQL = String.format("INSERT INTO `anturo-center-order`.order_pre_discount_detail (ID, PRE_DISCOUNT_ID, ITEM_SAP_CODE, ITEM_NAME, ITEM_DESC,\n" +
						"                                                            DISTRIBUTION_CHANNEL_CODE, DISTRIBUTION_CHANNEL_NAME,\n" +
						"                                                            CURRENT_PRICE, CALCULATION_TYPE, CALCULATION_VALUE,\n" +
						"                                                            BEGIN_DATE, END_DATE, EFFECTIVE_STATE, PRE_TYPE,\n" +
						"                                                            CREATE_PERSON, CREATE_PERSON_NAME, CREATE_TIME,\n" +
						"                                                            UPDATE_PERSON, UPDATE_PERSON_NAME, UPDATE_TIME, DR,\n" +
						"                                                            EXTENSION, REMARK, TENANT_ID, INSTANCE_ID)\n" +
						"VALUES ('%s', '%s', '%s', '%s',\n" +
						"        '%s', '%s', '%s', '%s', '%s', '%s', '%s',\n" +
						"        '%s', '%s', '%s', '%s', null, '%s',\n" +
						"        null, null, null, 0, null, null, '1', '1306316410046615556');", orderPreDiscountDetailDTO.getId(),
				orderPreDiscountDetailDTO.getPreDiscountId(), orderPreDiscountDetailDTO.getItemSapCode(),
				ProductEnum.fetchProductName(orderPreDiscountDetailDTO.getItemSapCode()),
				ProductEnum.fetchProductSpec(orderPreDiscountDetailDTO.getItemSapCode()), orderPreDiscountDetailDTO.getDistributionChannelCode(),
				orderPreDiscountDetailDTO.getDistributionChannelName(), orderPreDiscountDetailDTO.getCurrentPrice(),
				orderPreDiscountDetailDTO.getCalculationType(), orderPreDiscountDetailDTO.getCalculationValue(), orderPreDiscountDetailDTO.getBeginDate(),
				orderPreDiscountDetailDTO.getEndDate(), orderPreDiscountDetailDTO.getEffectiveState(), orderPreDiscountDetailDTO.getPreType(),
				orderPreDiscountDetailDTO.getCreatePerson(), orderPreDiscountDetailDTO.getCreateTime()
		);
		int sqlResult = orderSystemPersist.insert(SQL);
		log.info("[订单中心] 插入预折Detail表测试数据成功");
	}

	@Step(value = "【INSERT】插入整单折扣申请单主表测试数据")
	public void insertOrderApplicationFormData01(OrderApplicationFormDTO orderApplicationFormDTO) {
		final String SQL = String.format("INSERT INTO `anturo-center-order`.order_discount_application_form (ID, PROCESS_ORDER_NO, APPLICANT, APPLICANT_NAME,\n" +
						"                                                                  APPLICATION_DATE, APPLICANT_COMPANY_ZT_CODE,\n" +
						"                                                                  APPLICANT_COMPANY_SAP_CODE, APPLICANT_COMPANY_NAME,\n" +
						"                                                                  APPLICANT_DEPARTMENT_CODE, APPLICANT_DEPARTMENT_NAME,\n" +
						"                                                                  APPLICANT_CONTRACT_NUMBER, REPORT_SUBJECT,\n" +
						"                                                                  PROJECT_TYPE, PROJECT_NAME, MODULE, STATUS,\n" +
						"                                                                  EXECUTION_START_TIME, EXECUTION_END_TIME,\n" +
						"                                                                  EXECUTION_PERSON, SALE_ORG_ZT_CODE, SALE_ORG_SAP_CODE,\n" +
						"                                                                  SALE_ORG_NAME, REPORT_CONTENT, ATTACHMENT_ADDRESS,\n" +
						"                                                                  CREATE_PERSON, CREATE_PERSON_NAME, CREATE_TIME,\n" +
						"                                                                  UPDATE_PERSON, UPDATE_PERSON_NAME, UPDATE_TIME, DR,\n" +
						"                                                                  EXTENSION, REMARK, TENANT_ID, INSTANCE_ID)\n" +
						"VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s',\n" +
						"        '%s', '%s', '%s', '呈报主题-自动化测试', '%s', '项目名称-自动化测试', '%s', '%s', '%s', '%s', null,\n" +
						"        '%s', '%s', '%s', '呈报内容-自动化测试', null, '%s', null, '%s', null, null,\n" +
						"        null, 0, null, null, '1', '1306316410046615556');\n", orderApplicationFormDTO.getApplicationFormId(),
				orderApplicationFormDTO.getProcessOrderNo(), ApplicationEnum.fetchApplicant(orderApplicationFormDTO.getApplicant()),
				ApplicationEnum.fetchApplicantName(orderApplicationFormDTO.getApplicant()), orderApplicationFormDTO.getApplicationDate(),
				ApplicationEnum.fetchApplicantCompanyZtCode(orderApplicationFormDTO.getApplicant()), ApplicationEnum.fetchApplicantCompanySapCode(orderApplicationFormDTO.getApplicant()),
				ApplicationEnum.fetchApplicantCompanyName(orderApplicationFormDTO.getApplicant()), ApplicationEnum.fetchApplicantDepartmentCode(orderApplicationFormDTO.getApplicant()),
				ApplicationEnum.fetchApplicantDepartmentName(orderApplicationFormDTO.getApplicant()), ApplicationEnum.fetchApplicantContractNumber(orderApplicationFormDTO.getApplicant()),
				orderApplicationFormDTO.getProjectType(), orderApplicationFormDTO.getModule(), orderApplicationFormDTO.getStatus(),
				orderApplicationFormDTO.getExecutionStartTime(), orderApplicationFormDTO.getExecutionEndTime(), SaleOrgEnum.fetchZtCode(orderApplicationFormDTO.getSaleOrgSapCode()),
				orderApplicationFormDTO.getSaleOrgSapCode(), SaleOrgEnum.fetchName(orderApplicationFormDTO.getSaleOrgSapCode()), orderApplicationFormDTO.getCreatePerson(),
				orderApplicationFormDTO.getCreateTime()
		);
		int sqlResult = orderSystemPersist.insert(SQL);
		log.info("[订单中心] 插入整单折扣申请单主表测试数据成功");
	}

	@Step(value = "【INSERT】插入整单折扣申请单Detail表测试数据")
	public void insertOrderApplicationFormDetailData01(OrderApplicationFormDetailDTO orderApplicationFormDetailDTO) {
		final String SQL = String.format("INSERT INTO `anturo-center-order`.order_discount_application_form_detail (ID, ORDER_DISCOUNT_APPLICATION_FORM_ID,\n" +
						"                                                                         ITEM_SAP_CODE, ITEM_NAME, ITEM_DESC, QUANTITY,\n" +
						"                                                                         UNITS, USED_QUANTITY, CREATE_PERSON,\n" +
						"                                                                         CREATE_PERSON_NAME, CREATE_TIME, UPDATE_PERSON,\n" +
						"                                                                         UPDATE_PERSON_NAME, UPDATE_TIME, DR, EXTENSION,\n" +
						"                                                                         TENANT_ID, REMARK, INSTANCE_ID)\n" +
						"VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', null, '%s', null, '%s', null, null, null, 0, null, '1',\n" +
						"        null, '1306316410046615556');", orderApplicationFormDetailDTO.getApplicationFormDetailId(),
				orderApplicationFormDetailDTO.getApplicationFormId(), orderApplicationFormDetailDTO.getItemSapCode(),
				ProductEnum.fetchProductName(orderApplicationFormDetailDTO.getItemSapCode()), ProductEnum.fetchProductSpec(orderApplicationFormDetailDTO.getItemSapCode()),
				orderApplicationFormDetailDTO.getQuantity(), orderApplicationFormDetailDTO.getUnits(), orderApplicationFormDetailDTO.getCreatePerson(),
				orderApplicationFormDetailDTO.getCreateTime()
		);
		int sqlResult = orderSystemPersist.insert(SQL);
		log.info("[订单中心] 插入整单折扣申请单Detail表测试数据成功");
	}

	@Step(value = "【INSERT】插入整单折扣分配计划主表测试数据")
	public void insertOrderAllocationPlanData01(OrderAllocationPlanDTO orderAllocationPlanDTO) {
		final String SQL = String.format("INSERT INTO `anturo-center-order`.order_discount_allocation_plan (ID, ALLOCATION_PLAN_NAME,\n" +
						"                                                                 ORDER_DISCOUNT_APPLICATION_FORM_ID, STATUS,\n" +
						"                                                                 LOCK_STATUS, PLAN_MONTH,\n" +
						"                                                                 NEW_EXECUTION_START_TIME,\n" +
						"                                                                 NEW_EXECUTION_END_TIME, CREATE_PERSON,\n" +
						"                                                                 CREATE_PERSON_NAME, CREATE_TIME, UPDATE_PERSON,\n" +
						"                                                                 UPDATE_TIME, UPDATE_PERSON_NAME, DR, EXTENSION, REMARK,\n" +
						"                                                                 TENANT_ID, INSTANCE_ID)\n" +
						"VALUES ('%s', '分配计划-自动化测试', '%s', '%s', '%s', '%s', '%s',\n" +
						"        '%s', '%s', null, '%s', null, null,\n" +
						"        null, 0, null, null, '1', '1306316410046615556');\n", orderAllocationPlanDTO.getAllocationPlanId(),
				orderAllocationPlanDTO.getOrderDiscountApplicationFormId(), orderAllocationPlanDTO.getStatus(), orderAllocationPlanDTO.getLockStatus(),
				orderAllocationPlanDTO.getPlanMonth(), orderAllocationPlanDTO.getNewExecutionStartTime(), orderAllocationPlanDTO.getNewExecutionEndTime(),
				orderAllocationPlanDTO.getCreatePerson(), orderAllocationPlanDTO.getCreateTime()
		);
		int sqlResult = orderSystemPersist.insert(SQL);
		log.info("[订单中心] 插入整单折扣分配计划主表测试数据成功");
	}

	@Step(value = "【INSERT】插入整单折扣分配计划Detail表测试数据")
	public void insertOrderApplicationPlanDetailData01(OrderAllocationPlanDetailDTO orderAllocationPlanDetailDTO) {
		final String SQL = String.format("INSERT INTO `anturo-center-order`.order_discount_allocation_plan_detail (ID, order_discount_allocation_plan_id,\n" +
						"                                                                        ORDER_DISCOUNT_APPLICATION_DETAIL_ID,\n" +
						"                                                                        line_number, CUSTOMER_ENT_ZT_CODE,\n" +
						"                                                                        CUSTOMER_ENT_SAP_CODE, CUSTOMER_ENT_CRM_CODE,\n" +
						"                                                                        CUSTOMER_NAME, REGION_CODE,\n" +
						"                                                                        REGION_NAME, ORIGINAL_ITEM_SAP_CODE,\n" +
						"                                                                        ORIGINAL_ITEM_NAME, ORIGINAL_ITEM_DESC,\n" +
						"                                                                        original_distribution_channel_code,\n" +
						"                                                                        original_distribution_channel_name,\n" +
						"                                                                        GIFT_ITEM_SAP_CODE, GIFT_ITEM_NAME,\n" +
						"                                                                        GIFT_ITEM_DESC, gift_distribution_channel_code,\n" +
						"                                                                        gift_distribution_channel_name,\n" +
						"                                                                        ALLOCATION_QUANTITY, USED_QUANTITY, UNIT,\n" +
						"                                                                        PURCHASE_GIFT_RATIO, ORIGINAL_QUANTITY,\n" +
						"                                                                        GIFT_QUANTITY, INITIAL_BONUS_QUANTITY, NEED_SHIP,\n" +
						"                                                                        lot_no, production_date, expiration_date,\n" +
						"                                                                        invalid_status, CREATE_PERSON,\n" +
						"                                                                        CREATE_PERSON_NAME, CREATE_TIME, UPDATE_PERSON,\n" +
						"                                                                        UPDATE_PERSON_NAME, UPDATE_TIME, DR, EXTENSION,\n" +
						"                                                                        REMARK, TENANT_ID, INSTANCE_ID, VERSION)\n" +
						"VALUES ('%s', '%s', '%s', null, '%s', '%s', '%s',\n" +
						"        '%s', '%s', '%s', '%s', '%s',\n" +
						"        '%s', '%s', '%s', '%s', '%s',\n" +
						"        '%s', '%s', '%s', '%s', '0', '%s', null, '%s', '%s', '%s', '%s', '%s', '%s', '%s', '%s',\n" +
						"        '%s', null, '%s', null, null, null, 0, null,\n" +
						"        '%s', '1', '1306316410046615556', 0);",
				orderAllocationPlanDetailDTO.getAllocationPlanDetailId(),
				orderAllocationPlanDetailDTO.getAllocationPlanId(),
				orderAllocationPlanDetailDTO.getApplicationFormDetailId(),
				CompanyEnum.fetchZtCode(orderAllocationPlanDetailDTO.getCustomerEntSapCode()),
				orderAllocationPlanDetailDTO.getCustomerEntSapCode(),
				CompanyEnum.fetchCrmCode(orderAllocationPlanDetailDTO.getCustomerEntSapCode()),
				CompanyEnum.fetchName(orderAllocationPlanDetailDTO.getCustomerEntSapCode()),
				orderAllocationPlanDetailDTO.getRegionCode(),
				orderAllocationPlanDetailDTO.getRegionName(),
				orderAllocationPlanDetailDTO.getOriginalItemSapCode(),
				ProductEnum.fetchProductName(orderAllocationPlanDetailDTO.getOriginalItemSapCode()), ProductEnum.fetchProductSpec(orderAllocationPlanDetailDTO.getOriginalItemSapCode()),
				orderAllocationPlanDetailDTO.getOriginalDistributionChannelCode(), orderAllocationPlanDetailDTO.getOriginalDistributionChannelName(),
				orderAllocationPlanDetailDTO.getGiftItemSapCode(), ProductEnum.fetchProductName(orderAllocationPlanDetailDTO.getGiftItemSapCode()),
				ProductEnum.fetchProductSpec(orderAllocationPlanDetailDTO.getGiftItemSapCode()), orderAllocationPlanDetailDTO.getGiftDistributionChannelCode(),
				orderAllocationPlanDetailDTO.getGiftDistributionChannelName(), orderAllocationPlanDetailDTO.getAllocationQuantity(),
				orderAllocationPlanDetailDTO.getUnit(), orderAllocationPlanDetailDTO.getOriginalQuantity(), orderAllocationPlanDetailDTO.getGiftQuantity(),
				orderAllocationPlanDetailDTO.getInitialBonusQuantity(), orderAllocationPlanDetailDTO.getNeedShip(), orderAllocationPlanDetailDTO.getLotNo(),
				orderAllocationPlanDetailDTO.getProductionDate(), orderAllocationPlanDetailDTO.getExpirationDate(), orderAllocationPlanDetailDTO.getInvalidStatus(),
				orderAllocationPlanDetailDTO.getCreatePerson(), orderAllocationPlanDetailDTO.getCreateTime(), orderAllocationPlanDetailDTO.getRemark()
		);
		int sqlResult = orderSystemPersist.insert(SQL);
		log.info("[订单中心] 插入整单折扣分配计划Detail表测试数据成功");
	}


	@Step(value = "【INSERT】插入销售组织控销商品表测试数据")
	public void insertOrderSaleOrgControlledProductData01(OrderSaleOrgControlledProductDTO orderSaleOrgControlledProductDTO) {
		final String SQL = String.format("INSERT INTO `anturo-center-order`.order_sale_org_controlled_product \n" +
						" (ID, SALE_ORG_ZT_CODE, SALE_ORG_SAP_CODE, SALE_ORG_NAME, PRODUCT_SAP_SKU, \n" +
						"PRODUCT_DESCRIPTION, EFFECTIVE_STATE, CREATE_PERSON, CREATE_PERSON_NAME, CREATE_TIME, \n" +
						"UPDATE_PERSON, UPDATE_PERSON_NAME, UPDATE_TIME, DR, EXTENSION, \n" +
						"REMARK, TENANT_ID, INSTANCE_ID) \n" +
						"VALUES \n" +
						"('%s', null, '%s', '%s', '%s', \n" +
						"'%s', '%s', '%s',  '自动化测试', '%s', \n" +
						"'%s', '自动化测试', '%s', 0, null, \n" +
						"null, '1', '1306316410046615556');",
				orderSaleOrgControlledProductDTO.getSaleOrgControlledProductId(),
				orderSaleOrgControlledProductDTO.getSaleOrgSapCode(),
				SaleOrgEnum.fetchName(orderSaleOrgControlledProductDTO.getSaleOrgSapCode()),
				orderSaleOrgControlledProductDTO.getProductSapSku(),
				ProductEnum.fetchProductSpec(orderSaleOrgControlledProductDTO.getProductSapSku()),
				orderSaleOrgControlledProductDTO.getEffectiveState(),
				orderSaleOrgControlledProductDTO.getCreatePerson(),
				orderSaleOrgControlledProductDTO.getCreateTime(),
				orderSaleOrgControlledProductDTO.getCreatePerson(),
				orderSaleOrgControlledProductDTO.getCreateTime()
		);

		int sqlResult = orderSystemPersist.insert(SQL);
		log.info("[订单中心] 插入销售组织控销商品表测试数据测试数据成功");
	}

	@Step(value = "【INSERT】插入允销大区表测试数据")
	public void insertOrderPermittedSalesRegionData01(OrderPermittedSalesRegionDTO orderPermittedSalesRegionDTO) {
		final String SQL = String.format("INSERT INTO `anturo-center-order`.order_permitted_sales_region \n" +
						"(ID, sale_org_controlled_product_id, REGION_CODE, REGION_NAME, PERMITTED_SALE_QUANTITY, \n" +
						"UNIT_CODE, UNIT_NAME, PERMITTED_SALE_BEGIN_DATE, PERMITTED_SALE_END_DATE, EFFECTIVE_STATE, \n" +
						"CREATE_PERSON, CREATE_PERSON_NAME, CREATE_TIME, UPDATE_PERSON, UPDATE_PERSON_NAME, \n" +
						"UPDATE_TIME, DR, EXTENSION, REMARK, TENANT_ID, \n" +
						"INSTANCE_ID) VALUES ('%s', '%s', '%s', '%s', '%s', \n" +
						"'%s', '%s', '%s', '%s', '%s', \n" +
						"'%s', '自动化测试', '%s', '%s', '自动化测试', \n" +
						"'%s', 0, null, null, '1', '1306316410046615556');",
				orderPermittedSalesRegionDTO.getOrderPermittedSalesRegionId(),
				orderPermittedSalesRegionDTO.getSaleOrgControlledProductId(),
				orderPermittedSalesRegionDTO.getRegionCode(),
				orderPermittedSalesRegionDTO.getRegionName(),
				orderPermittedSalesRegionDTO.getPermittedSaleQuantity(),
				orderPermittedSalesRegionDTO.getUnitCode(),
				orderPermittedSalesRegionDTO.getUnitName(),
				orderPermittedSalesRegionDTO.getPermittedSaleBeginDate(),
				orderPermittedSalesRegionDTO.getPermittedSaleEndDate(),
				orderPermittedSalesRegionDTO.getEffectiveState(),
				orderPermittedSalesRegionDTO.getCreatePerson(),
				orderPermittedSalesRegionDTO.getCreateTime(),
				orderPermittedSalesRegionDTO.getCreatePerson(),
				orderPermittedSalesRegionDTO.getCreateTime()
		);

		int sqlResult = orderSystemPersist.insert(SQL);
		log.info("[订单中心] 插入允销大区表测试数据成功");
	}

	@Step(value = "【INSERT】插入大区允销一级商表测试数据")
	public void insertOrderPermittedSalesCustomerData01(OrderPermittedSalesCustomerDTO orderPermittedSalesCustomerDTO) {
		final String SQL = String.format("INSERT INTO `anturo-center-order`.order_permitted_sales_customer  \n" +
						"(ID, sale_org_controlled_product_id, PERMITTED_SALES_REGION_ID, CUSTOMER_ENT_ZT_CODE, CUSTOMER_ENT_SAP_CODE,  \n" +
						"CUSTOMER_ENT_CRM_CODE, CUSTOMER_NAME, TYPE, PERMITTED_SALE_QUANTITY, HAS_SALE_QUANTITY,  \n" +
						"EFFECTIVE_STATE, CREATE_PERSON, CREATE_PERSON_NAME, CREATE_TIME, UPDATE_PERSON,  \n" +
						"UPDATE_PERSON_NAME, UPDATE_TIME, DR, EXTENSION, REMARK, \n" +
						"TENANT_ID, INSTANCE_ID)  \n" +
						"VALUES ('%s','%s','%s','%s','%s',  \n" +
						"'%s', '%s','%s', '%s','%s',  \n" +
						"'%s', '%s',  '自动化测试', '%s', '%s',  \n" +
						" '自动化测试', '%s', 0, null, null,  \n" +
						"'1', '1306316410046615556');",
				orderPermittedSalesCustomerDTO.getOrderPermittedSalesCustomerId(),
				orderPermittedSalesCustomerDTO.getSaleOrgControlledProductId(),
				orderPermittedSalesCustomerDTO.getPermittedSalesRegionId(),
				orderPermittedSalesCustomerDTO.getCustomerEntZtCode(),
				orderPermittedSalesCustomerDTO.getCustomerEntSapCode(),
				orderPermittedSalesCustomerDTO.getCustomerEntCrmCode(),
				orderPermittedSalesCustomerDTO.getCustomerName(),
				orderPermittedSalesCustomerDTO.getType(),
				orderPermittedSalesCustomerDTO.getPermittedSaleQuantity(),
				orderPermittedSalesCustomerDTO.getHasSaleQuantity(),
				orderPermittedSalesCustomerDTO.getEffectiveState(),
				orderPermittedSalesCustomerDTO.getCreatePerson(),
				orderPermittedSalesCustomerDTO.getCreateTime(),
				orderPermittedSalesCustomerDTO.getCreatePerson(),
				orderPermittedSalesCustomerDTO.getCreateTime()
		);

		int sqlResult = orderSystemPersist.insert(SQL);
		log.info("[订单中心] 插入大区允销一级商表测试数据成功");
	}

}
