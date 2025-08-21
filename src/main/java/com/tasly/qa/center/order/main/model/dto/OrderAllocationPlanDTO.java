package com.anturo.qa.center.order.main.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Author Tesla
 * @Date 2024/05/22
 * @Description
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class OrderAllocationPlanDTO {

	/**
	 * allocationPlanId
	 */
	private String allocationPlanId;

	/**
	 * orderDiscountApplicationFormId
	 */
	private String orderDiscountApplicationFormId;

	/**
	 * 申请单状态
	 */
	private String status;

	/**
	 * 锁定状态
	 */
	private String lockStatus;

	/**
	 * 计划月
	 */
	private String planMonth;

	/**
	 * 新执行开始时间
	 */
	private String newExecutionStartTime;

	/**
	 * 新执行结束时间
	 */
	private String newExecutionEndTime;

	/**
	 * 创建人
	 */
	private String createPerson;

	/**
	 * 创建时间
	 */
	private String createTime;

}
