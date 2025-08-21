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
 * @Date 2025/04/29
 * @Description
 */

@SuppressWarnings("all")
@Getter
@Setter
@Log4j2(topic = "DefaultLogger")
public class DeliveryBatchSelectData01 {

	private MySqlTools orderSystemPersist = PersistanceOperations.mysql("order-center");

	@Step(value = "【SQL】 根据订单号[{0}] 查询订单交货单数据")
	public List<Map<String, Object>> queryOrderDeliveryData01(String orderNo) {
		final String SQL = String.format("SELECT item_id, num, bwart, status\n" +
				"FROM `anturo-center-order`.order_fulfillment_shipment_whse_post_receipt\n" +
				"WHERE bstkd = '%s';", orderNo);
		List<Map<String, Object>> sqlResult = orderSystemPersist.sqlQuery(SQL);
		log.info("SQL结果为：" + sqlResult);
		return sqlResult;
	}
}
