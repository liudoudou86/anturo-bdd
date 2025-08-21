package com.anturo.qa.center.order.bdd.order.batchs;

import com.anturo.qa.center.order.main.support.PersistanceOperations;
import com.panzer.tool.sql.MySqlTools;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import net.thucydides.core.annotations.Step;

import java.math.BigDecimal;

/**
 * @Author guoqing
 * @Date 2024/10/10
 * @Description 返利中心更新数据
 */

@SuppressWarnings("all")
@Getter
@Setter
@Log4j2(topic = "DefaultLogger")
public class RebateBatchUpdateData01 {
	private MySqlTools rebateSystemPersist = PersistanceOperations.mysql("rebate-center");

	@Step(value = "【SQL】设置大区上限金额")
	public void updateRegionRebateLimitRegionLimitAmount(String saleOrgCode, String discountRegionCode, BigDecimal additionalAmount, String rebateMonth) {
		final String UPDATE_SQL = String.format("UPDATE `anturo-center-rebate`.region_rebate_limit " +
				"SET REGION_LIMIT_AMOUNT = USED_AMOUNT + WHOLE_ORDER_DISCOUNT + %s " +
				"WHERE SALE_ORG_CODE = '%s' " +
				"AND DISCOUNT_REGION_CODE = '%s' " +
				"AND REBATE_MONTH_STR = '%s';", additionalAmount, saleOrgCode, discountRegionCode, rebateMonth);
		rebateSystemPersist.sqlAlter(UPDATE_SQL);
		log.info("修改大区上限金额");
	}


}
