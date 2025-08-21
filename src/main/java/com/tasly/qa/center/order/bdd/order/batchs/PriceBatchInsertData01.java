package com.anturo.qa.center.order.bdd.order.batchs;

import com.anturo.qa.center.order.main.model.dto.ProductPriceDTO;
import com.anturo.qa.center.order.main.support.PersistanceOperations;
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
 * @Date 2024/07/30
 * @Description
 */

@SuppressWarnings("all")
@Getter
@Setter
@Log4j2(topic = "DefaultLogger")
public class PriceBatchInsertData01 {

	private MySqlTools priceSystemPersist = PersistanceOperations.mysql("price-center");

	@Step(value = "【INSERT】插入商品价格测试数据")
	public void insertProductPriceData01(ProductPriceDTO sqlDTO) {
		final String SQL = String.format("INSERT INTO `anturo-center-price`.pr_price (logical_primary_key, price_unique_code, dimension_code, dimension_name,\n" +
						"                                           dimension_priority, seller_code, seller_name, distribution_channel_code,\n" +
						"                                           distribution_channel_name, distribution_channel_priority, price_group_code,\n" +
						"                                           price_group_name, customer_code, customer_name, item_code, item_name,\n" +
						"                                           variety_code, variety_name, start_time, end_time, price_type, price,\n" +
						"                                           currency, synchronised_time, creator, creator_name, creation_date,\n" +
						"                                           sap_import_id, extension, tenant_id, instance_id, owner_id, manager_id,\n" +
						"                                           commercial_format, dr, create_person, create_time, create_person_name,\n" +
						"                                           update_person, update_time, quantity, unit, unit_desc)\n" +
						"VALUES ('1803704502172389380', null, '%s',\n" +
						"        null, '%s', '%s', '%s', '%s', null, null, '%s', null, '%s', '%s', '%s', '%s', null,\n" +
						"        null, '%s', '9999-12-31 23:59:59', 2, '%s', 'CNY', null, null, null,\n" +
						"        null, null, null, -1, -1, null, null, null, 0, '%s', '%s', null,\n" +
						"        null, null, '1', 'PIL', '丸');", sqlDTO.getDimensionCode(), sqlDTO.getDimensionPriority(), sqlDTO.getSellerCode(),
				SaleOrgEnum.fetchName(sqlDTO.getSellerCode()), sqlDTO.getDistributionChannelCode(), sqlDTO.getPriceGroupCode(), sqlDTO.getCustomerCode(),
				CompanyEnum.fetchName(sqlDTO.getCustomerCode()), sqlDTO.getItemCode(), ProductEnum.fetchProductName(sqlDTO.getItemCode()),
				sqlDTO.getStartTime(), sqlDTO.getPrice(), sqlDTO.getCreatePerson(), sqlDTO.getCreateTime()
		);
		int sqlResult = priceSystemPersist.insert(SQL);
		log.info("[价格中心] 插入商品价格测试数据成功");
	}
}
