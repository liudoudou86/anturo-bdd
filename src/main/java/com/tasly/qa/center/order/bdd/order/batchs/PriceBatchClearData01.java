package com.anturo.qa.center.order.bdd.order.batchs;


import com.anturo.qa.center.order.main.support.PersistanceOperations;
import com.google.common.collect.ImmutableSet;
import com.panzer.tool.sql.MySqlTools;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import net.thucydides.core.annotations.Step;

import java.util.Set;


/**
 * @Author Tesla
 * @Date 2024/05/22
 * @Description 价格中心清除数据
 */

@SuppressWarnings("all")
@Getter
@Setter
@Log4j2(topic = "DefaultLogger")
public class PriceBatchClearData01 {

	private MySqlTools priceSystemPersist = PersistanceOperations.mysql("price-center");

	private MySqlTools itemSystemPersist = PersistanceOperations.mysql("item-center");

	@Step(value = "【CLEAR】清除价格自动化测试数据")
	public void clearPirceData01(String createPerson) {
		final String SQL = String.format("delete from `anturo-center-price`.pr_price where create_person = '%s';", createPerson);
		priceSystemPersist.delete(SQL);
		log.info("[价格中心] 清除价格自动化测试数据成功");
	}

	@Step(value = "【CLEAR】清除商品自动化测试数据")
	public void clearProductData01(String createPerson) {
		Set<String> SQL_SET = ImmutableSet.of(
				String.format("delete from `anturo-center-item`.it_product where create_person = '%s';", createPerson),
				String.format("delete from `anturo-center-item`.it_sale_org_product where create_person = '%s';", createPerson)
		);
		SQL_SET.stream().forEach(
				sql -> itemSystemPersist.sqlAlter(sql)
		);
		log.info("[商品中心] 清除商品自动化测试数据成功");
	}

}
