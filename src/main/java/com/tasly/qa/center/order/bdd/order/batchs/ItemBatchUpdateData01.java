package com.anturo.qa.center.order.bdd.order.batchs;

import com.anturo.qa.center.order.main.support.PersistanceOperations;
import com.panzer.tool.sql.MySqlTools;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import net.thucydides.core.annotations.Step;

/**
 * @Author guoqing
 * @Date 2024/10/10
 * @Description 商品中心更新数据
 */

@SuppressWarnings("all")
@Getter
@Setter
@Log4j2(topic = "DefaultLogger")
public class ItemBatchUpdateData01 {
	private MySqlTools itemSystemPersist = PersistanceOperations.mysql("item-center");

	@Step(value = "【SQL】设置商品销售组织")
	public void updateItSaleOrgProductOrgSapCode(String beforeSaleOrgSapCode, String afterSaleOrgSapCode, String productCode) {
		final String UPDATE_SQL = String.format("UPDATE `anturo-center-item`.it_sale_org_product t \n" +
				"SET t.sale_org_sap_code = '%s' \n" +
				"WHERE product_code = '%s' and sale_org_sap_code = '%s';", afterSaleOrgSapCode, productCode, beforeSaleOrgSapCode);
		itemSystemPersist.sqlAlter(UPDATE_SQL);
		log.info("修改商品销售组织");
	}

	@Step(value = "【SQL】设置商品上下架状态")
	public void updateItSaleOrgProductIsActive(String saleOrgSapCode, String isActive, String productCode) {
		final String UPDATE_SQL = String.format("UPDATE `anturo-center-item`.it_sale_org_product t \n" +
				"SET t.is_active = '%s' \n" +
				"WHERE product_code = '%s' and sale_org_sap_code = '%s';", isActive, productCode, saleOrgSapCode);
		itemSystemPersist.sqlAlter(UPDATE_SQL);
		log.info("修改商品上下架状态");
	}


}
