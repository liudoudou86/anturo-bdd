package com.anturo.qa.center.order.bdd.order.batchs;

import com.anturo.qa.center.order.main.model.dto.OrderPreDiscountDTO;
import com.anturo.qa.center.order.main.model.dto.OrderPreDiscountDetailDTO;
import com.anturo.qa.center.order.main.support.PersistanceOperations;
import com.panzer.tool.sql.MySqlTools;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import net.thucydides.core.annotations.Step;

import java.text.MessageFormat;

/**
 * @Author guoqing
 * @Date 2024/05/28
 * @Description 订单中心更新数据
 */

@SuppressWarnings("all")
@Getter
@Setter
@Log4j2(topic = "DefaultLogger")
public class OrderBatchUpdateData01 {
	private MySqlTools orderSystemPersist = PersistanceOperations.mysql("order-center");

	@Step(value = "【SQL】根据客户更新预折生效状态")
	public void updateOrderPreDiscountEffectiveState(String effectiveState, String customerSapCode, String saleOrgSapCode) {
		final String UPDATE_SQL = String.format("UPDATE `anturo-center-order`.order_pre_discount\n" +
				"SET EFFECTIVE_STATE = '%s'\n" +
				"WHERE CUSTOMER_ENT_SAP_CODE = '%s'\n" +
				"  and SALE_ORG_SAP_CODE = '%s';", effectiveState, customerSapCode, saleOrgSapCode);
		orderSystemPersist.sqlAlter(UPDATE_SQL);
		log.info("修改客户预折生效状态");
	}

	@Step(value = "【SQL】根据创建者更新整单折扣锁定状态")
	public void updateDiscountAllocationPlanLockState(String lockState, String createPerson) {
		final String UPDATE_SQL = String.format("UPDATE `anturo-center-order`.order_discount_allocation_plan\n" +
				"SET LOCK_STATUS = '%s'\n" +
				"WHERE CREATE_PERSON = '%s';", lockState, createPerson);
		orderSystemPersist.sqlAlter(UPDATE_SQL);
		log.info("修改创建者整单折扣锁定状态");
	}

	@Step(value = "【SQL】根据客户和商品更新客户预折生效状态")
	public void updateOrderPreDiscountDetailEffectiveState(OrderPreDiscountDetailDTO orderPreDiscountDetailDTO, OrderPreDiscountDTO orderPreDiscountDTO) {
		final String UPDATE_SQL = String.format("UPDATE `anturo-center-order`.order_pre_discount_detail d\n" +
				"INNER JOIN `anturo-center-order`.order_pre_discount t\n" +
				"ON d.PRE_DISCOUNT_ID = t.id\n" +
				"SET d.EFFECTIVE_STATE = '%s'\n" +
				"WHERE t.CUSTOMER_ENT_SAP_CODE = '%s'\n" +
				"AND d.ITEM_SAP_CODE = '%s';", orderPreDiscountDetailDTO.getEffectiveState(), orderPreDiscountDTO.getCustomerSapCode(), orderPreDiscountDetailDTO.getItemSapCode());
		orderSystemPersist.sqlAlter(UPDATE_SQL);
		log.info(MessageFormat.format("修改客户预折信息：客户[{0}]、商品[{1}]、生效状态[{2}] 成功", orderPreDiscountDTO.getCustomerSapCode(), orderPreDiscountDetailDTO.getItemSapCode(), orderPreDiscountDetailDTO.getEffectiveState()));
	}

	@Step(value = "【SQL】根据客户和商品更新客户预折折后价格")
	public void updateOrderPreDiscountDetailCalculationValue(OrderPreDiscountDetailDTO orderPreDiscountDetailDTO, OrderPreDiscountDTO orderPreDiscountDTO) {
		final String UPDATE_SQL = String.format("UPDATE `anturo-center-order`.order_pre_discount_detail d\n" +
				"INNER JOIN `anturo-center-order`.order_pre_discount t\n" +
				"ON d.PRE_DISCOUNT_ID = t.id\n" +
				"SET d.CALCULATION_VALUE = '%s',\n" +
				"d.EFFECTIVE_STATE = '%s'\n" +
				"WHERE t.CUSTOMER_ENT_SAP_CODE = '%s'\n" +
				"AND d.ITEM_SAP_CODE = '%s';", orderPreDiscountDetailDTO.getCalculationValue(), orderPreDiscountDetailDTO.getEffectiveState(), orderPreDiscountDTO.getCustomerSapCode(), orderPreDiscountDetailDTO.getItemSapCode());
		orderSystemPersist.sqlAlter(UPDATE_SQL);
		log.info(MessageFormat.format("修改客户预折信息：客户[{0}]、商品[{1}]、折后价格[{2}]、生效状态[{3}] 成功", orderPreDiscountDTO.getCustomerSapCode(), orderPreDiscountDetailDTO.getItemSapCode(), orderPreDiscountDetailDTO.getCalculationValue(), orderPreDiscountDetailDTO.getEffectiveState()));
	}

	@Step(value = "【SQL】更新分配计划的待分配数量状态")
	public void updateOrderDiscountAllocationPlanDetailUsedQuantity(String allocationPlanDetailId, String usedQuantity) {
		final String UPDATE_SQL = String.format("UPDATE `anturo-center-order`.order_discount_allocation_plan_detail t \n" +
				"SET t.USED_QUANTITY = '%s' WHERE t.ID = '%s';", usedQuantity, allocationPlanDetailId);
		orderSystemPersist.sqlAlter(UPDATE_SQL);
		log.info("修改分配计划的待分配数量状态");
	}

	@Step(value = "【SQL】更新分配计划明细的作废状态")
	public void updateOrderDiscountAllocationPlanDetailInvalidStatus(String allocationPlanDetailId, String invalidStatus) {
		final String UPDATE_SQL = String.format("UPDATE `anturo-center-order`.order_discount_allocation_plan_detail t \n" +
				"SET t.invalid_status = '%s' WHERE t.ID = '%s';", invalidStatus, allocationPlanDetailId);
		orderSystemPersist.sqlAlter(UPDATE_SQL);
		log.info("修改分配计划明细的作废状态");
	}


}
