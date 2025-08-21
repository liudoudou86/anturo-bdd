package com.anturo.qa.center.order.main.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Author Tesla
 * @Date 2024/07/30
 * @Description
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class OrderRegionPreDiscountFetchProductPriceDTO {

	/**
	 * 预折类型
	 */
	private String preType;

	/**
	 * 订单类型
	 */
	private String orderType;

	/**
	 * 销售组织
	 */
	private String saleOrgSapCode;

	/**
	 * 大区编码
	 */
	private String customerRegionCode;


	/**
	 * 大区名称
	 */
	private String customerRegionName;

	/**
	 * 客户SAP编码
	 */
	private String customerSapCode;

	/**
	 * 商品SKU
	 */
	private String itemSapCodeOrCrmCodeOrDesc;
}
