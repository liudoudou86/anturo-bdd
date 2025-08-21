package com.anturo.qa.center.order.main.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Author Tesla
 * @Date 2025/05/06
 * @Description
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ReturnOrderSelectIdDTO {

	/**
	 * 销售组织
	 */
	private String saleOrgSapCode;

	/**
	 * 大区编码
	 */
	private String regionCode;

	/**
	 * 客户数据
	 */
	com.anturo.qa.center.order.main.model.dto.CustomerInfoDTO customerInfo;

	/**
	 * 正向订单
	 */
	private String orderNo;

	/**
	 * 客户SAP编码
	 */
	private String customerCodeOrName;

	/**
	 * pageNum
	 */
	private String pageNum;

	/**
	 * pageSize
	 */
	private String pageSize;
}
