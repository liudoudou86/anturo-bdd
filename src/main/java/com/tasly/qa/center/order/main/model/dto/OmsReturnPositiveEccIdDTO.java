package com.anturo.qa.center.order.main.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Author Tesla
 * @Date 2025/04/29
 * @Description
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class OmsReturnPositiveEccIdDTO {

	/**
	 * 信息
	 */
	private String eccMessage;

	/**
	 * ECCID
	 */
	private String eccOrderId;

	/**
	 * ERP审批状态
	 */
	private String erpStatus;

	/**
	 * 订单号
	 */
	private String originalOrderId;

}
