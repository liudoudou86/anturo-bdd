package com.anturo.qa.center.order.main.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Author Tesla
 * @Date 2024/06/04
 * @Description
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class OrderQueryPageByConditionDTO {

	/**
	 * 销售组织
	 */
	private String saleOrgSapCode;

	/**
	 * 客户
	 */
	private String customerCodeOrName;

	/**
	 * 订单号
	 */
	private String orderNo;

	/**
	 * 订单状态
	 */
	private String state;

	/**
	 * 取消状态
	 */
	private String cancelStatus;

	/**
	 * 支付方式
	 */
	private String paymentMethod;

	/**
	 * 开始时间
	 */
	private String createStartTime;

	/**
	 * 结束时间
	 */
	private String createEndTime;

	/**
	 * 页码
	 */
	private String pageNum;

	/**
	 * 页数
	 */
	private String pageSize;

}
