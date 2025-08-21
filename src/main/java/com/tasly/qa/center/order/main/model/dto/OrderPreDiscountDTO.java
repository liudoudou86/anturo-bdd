package com.anturo.qa.center.order.main.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Author Tesla
 * @Date 2024/05/20
 * @Description
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class OrderPreDiscountDTO {

	/**
	 * preDiscountId
	 */
	private String preDiscountId;

	/**
	 * 客户SAP编码
	 */
	private String customerSapCode;

	/**
	 * 销售组织SAP编码
	 */
	private String saleOrgSapCode;

	/**
	 * 大区编码
	 */
	private String regionCode;

	/**
	 * 大区名称
	 */
	private String regionName;

	/**
	 * 生效状态
	 */
	private String effectiveState;

	/**
	 * 预折类型
	 */
	private String preType;

	/**
	 * 创建人
	 */
	private String createPerson;

	/**
	 * 创建时间
	 */
	private String createTime;

}
