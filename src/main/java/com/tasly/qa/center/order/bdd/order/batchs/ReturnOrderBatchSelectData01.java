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
 * @Author Tesla
 * @Date 2025/05/06
 * @Description
 */

@SuppressWarnings("all")
@Getter
@Setter
@Log4j2(topic = "DefaultLogger")
public class ReturnOrderBatchSelectData01 {

	private MySqlTools orderSystemPersist = PersistanceOperations.mysql("order-center");

	@Step(value = "【SQL】 根据订单号[{0}] 查询订单行ID")
	public String queryOrderItemIdData01(String orderNo, String orderItem) {
		String Id = "";
		final String SQL = String.format("select ID\n" +
				"from `anturo-center-order`.order_purchase_order_item\n" +
				"where ORDER_NO = '%s'\n" +
				"  and ITEM_LINE = '%s';", orderNo, orderItem);
		List<Map<String, Object>> sqlResult = orderSystemPersist.sqlQuery(SQL);
		log.info("SQL结果为：" + sqlResult);
		if (!sqlResult.isEmpty()) {
			Id = sqlResult.get(0).get("ID").toString();
			log.info("ID为：" + Id);
		}
		return Id;
	}

	@Step(value = "【SQL】 根据交货单流水号[{0}] 查询交货单ID")
	public String queryDeliveryIdData01(String materialNumber) {
		String Id = "";
		final String SQL = String.format("select id\n" +
				"from `anturo-center-order`.order_fulfillment_shipment_whse_post_receipt\n" +
				"where mblnr = '%s';", materialNumber);
		List<Map<String, Object>> sqlResult = orderSystemPersist.sqlQuery(SQL);
		log.info("SQL结果为：" + sqlResult);
		if (!sqlResult.isEmpty()) {
			Id = sqlResult.get(0).get("id").toString();
			log.info("ID为：" + Id);
		}
		return Id;
	}

	@Step(value = "【SQL】 根据退货主表ID[{0}] 查询退货Detail表ID")
	public String queryReturnOrderDetailIdData01(String returnOrderId) {
		String Id = "";
		final String SQL = String.format("select ID\n" +
				"from `anturo-center-order`.order_fulfillment_return_detail\n" +
				"where RETURN_ID = '%s';", returnOrderId);
		List<Map<String, Object>> sqlResult = orderSystemPersist.sqlQuery(SQL);
		log.info("SQL结果为：" + sqlResult);
		if (!sqlResult.isEmpty()) {
			Id = sqlResult.get(0).get("ID").toString();
			log.info("ID为：" + Id);
		}
		return Id;
	}

	@Step(value = "【SQL】 根据退货主表ID[{0}] 查询退货订单号")
	public String queryReturnOrderNoData01(String returnOrderId) {
		String refundOrderNo = "";
		final String SQL = String.format("select RETURN_ORDER_NO\n" +
				"from `anturo-center-order`.order_fulfillment_return\n" +
				"where ID = '%s';", returnOrderId);
		List<Map<String, Object>> sqlResult = orderSystemPersist.sqlQuery(SQL);
		log.info("SQL结果为：" + sqlResult);
		if (!sqlResult.isEmpty()) {
			refundOrderNo = sqlResult.get(0).get("RETURN_ORDER_NO").toString();
			log.info("退货订单号：" + refundOrderNo);
		}
		return refundOrderNo;
	}

}
