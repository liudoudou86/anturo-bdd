package com.anturo.qa.center.order.main.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Author guoqing
 * @Date 2024/06/05
 * @Description 订单折扣使用记录
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class OrderDiscountDetailDTO {

	/**
	 * 母单号
	 */
	private String parentOrderNo;

	/**
	 * 母单行号
	 */
	private String parentOrderItemNo;

	/**
	 * 订单号
	 */
	private String orderNo;

	/**
	 * 订单行号
	 */
	private String orderItemNo;

	/**
	 * 分配计划ID
	 */
	private String orderDiscountApplicationPlanId;

	/**
	 * 分配计划明细ID
	 */
	private String orderDiscountApplicationPlanDetailId;

	/**
	 * 操作类型
	 */
	private String operateType;

	/**
	 * 操作金额
	 */
	private String operateAmount;


}
