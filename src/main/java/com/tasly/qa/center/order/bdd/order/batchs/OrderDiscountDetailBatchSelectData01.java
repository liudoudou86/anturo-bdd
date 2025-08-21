package com.anturo.qa.center.order.bdd.order.batchs;

import com.anturo.qa.center.order.main.support.PersistanceOperations;
import com.panzer.tool.sql.MySqlTools;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import net.thucydides.core.annotations.Step;

import java.util.List;
import java.util.Map;

/**
 * @Author guoqing
 * @Date 2024/06/05
 * @Description 订单中心-订单折扣使用记录数据操作
 */

@SuppressWarnings("all")
@Getter
@Setter
@Log4j2(topic = "DefaultLogger")
public class OrderDiscountDetailBatchSelectData01 {

	private MySqlTools orderSystemPersist = PersistanceOperations.mysql("order-center");

	@Step(value = "【SQL】 根据母单号[{0}] 查询订单折扣使用记录数据")
	public List<Map<String, Object>> queryOrderDiscountDetailData01(String parentsOrderNo) {
		final String SQL = String.format("SELECT t.PARENT_ORDER_NO parentOrderNo,\n" +
				"       t.PARENT_ORDER_ITEM_NO parentOrderItemNo,\n" +
				"       t.ORDER_NO orderNo,\n" +
				"       t.ORDER_ITEM_NO orderItemNo,\n" +
				"       t.ORDER_DISCOUNT_APPLICATION_PLAN_ID orderDiscountApplicationPlanId,\n" +
				"       t.ORDER_DISCOUNT_APPLICATION_PLAN_DETAIL_ID orderDiscountApplicationPlanDetailId,\n" +
				"       t.OPERATE_TYPE operateType,\n" +
				"       t.OPERATE_AMOUNT operateAmount\n" +
				"FROM `anturo-center-order`.order_discount_detail t\n" +
				"WHERE t.PARENT_ORDER_NO = '%s'\n" +
				"  and t.dr = 0\n" +
				"ORDER BY t.ID asc;", parentsOrderNo);
		List<Map<String, Object>> sqlResult = orderSystemPersist.sqlQuery(SQL);
		log.info("SQL结果为：" + sqlResult);
		return sqlResult;
	}

}
