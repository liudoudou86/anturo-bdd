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
 * @Description 渠道中心更新数据
 */

@SuppressWarnings("all")
@Getter
@Setter
@Log4j2(topic = "DefaultLogger")
public class ChannelBatchUpdateData01 {
	private MySqlTools orderSystemPersist = PersistanceOperations.mysql("order-center");

	@Step(value = "【SQL】设置销售组织和客户关系为未关联状态")
	public void updateChannelSapSaleUnassociated(String beforeSaleOrgSapCode, String AfterSaleOrgSapCode, String customerSapCode) {
		final String UPDATE_SQL = String.format("UPDATE `anturo-center-channel`.ch_sap_sale t \n" +
				"SET t.sale = '%s' \n" +
				"WHERE t.sale = '%s' and t.sap_code = '%s';", AfterSaleOrgSapCode, beforeSaleOrgSapCode, customerSapCode);
		orderSystemPersist.sqlAlter(UPDATE_SQL);
		log.info("修改销售组织和客户关系未关联状态");
	}

	@Step(value = "【SQL】设置销售组织和客户关系为关联状态")
	public void updateChannelSapSaleAssociated(String beforeSaleOrgSapCode, String AfterSaleOrgSapCode, String customerSapCode) {
		final String UPDATE_SQL = String.format("UPDATE `anturo-center-channel`.ch_sap_sale t \n" +
				"SET t.sale = '%s' \n" +
				"WHERE t.sale = '%s' and t.sap_code = '%s';", AfterSaleOrgSapCode, beforeSaleOrgSapCode, customerSapCode);
		orderSystemPersist.sqlAlter(UPDATE_SQL);
		log.info("修改销售组织和客户关系为关联状态");
	}


}
