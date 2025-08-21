package com.anturo.qa.center.order.bdd.order.batchs;

import com.anturo.qa.center.order.main.support.PersistanceOperations;
import com.panzer.tool.sql.MySqlTools;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import net.thucydides.core.annotations.Step;

/**
 * @Author guoqing
 * @Date 2024/10/09
 * @Description 价格中心更新数据
 */

@SuppressWarnings("all")
@Getter
@Setter
@Log4j2(topic = "DefaultLogger")
public class PriceBatchUpdateData01 {
	private MySqlTools orderSystemPersist = PersistanceOperations.mysql("order-center");

	@Step(value = "【SQL】设置商品计价单位")
	public void updatePriceUnit(String unit, String unitDesc, String itemCode, String customerSapCode) {
		final String UPDATE_SQL = String.format("UPDATE `anturo-center-price`.pr_price t " +
				"SET t.unit = '%s',t.unit_desc = '%s' " +
				"WHERE t.item_code = '%s' and t.seller_code = 1017 and t.customer_code = '%s';", unit, unitDesc, itemCode, customerSapCode);
		orderSystemPersist.sqlAlter(UPDATE_SQL);
		log.info("修改商品计价单位");
	}

	@Step(value = "【SQL】设置商品计价单位")
	public void updatePriceSkuPrice(String price, String itemCode, String customerSapCode) {
		final String UPDATE_SQL = String.format("UPDATE `anturo-center-price`.pr_price t " +
				"SET t.price = '%s' " +
				"WHERE t.item_code = '%s' and t.seller_code = 1017 and t.customer_code = '%s';", price, itemCode, customerSapCode);
		orderSystemPersist.sqlAlter(UPDATE_SQL);
		log.info("修改商品计价单位");
	}

	@Step(value = "【SQL】设置商品分销渠道")
	public void updatePriceDistributionChannelCode(String distributionChannelCode, String itemCode, String customerSapCode) {
		final String UPDATE_SQL = String.format("UPDATE `anturo-center-price`.pr_price t " +
				"SET t.price = '%s' " +
				"WHERE t.item_code = '%s' and t.seller_code = 1017 and t.customer_code = '%s';", distributionChannelCode, itemCode, customerSapCode);
		orderSystemPersist.sqlAlter(UPDATE_SQL);
		log.info("修改商品分销渠道");
	}


}
