package com.anturo.qa.center.order.main.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Author Tesla
 * @Date 2024/08/01
 * @Description
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class OrderSelectProjectFetchProductPriceDTO {

	/**
	 * 订单类型
	 */
	private String orderType;

	/**
	 * 销售组织
	 */
	private String saleOrgSapCode;

	/**
	 * 客户编码
	 */
	private String customerSapCode;

	/**
	 * 大区编码
	 */
	private String customerRegionCode;

}
