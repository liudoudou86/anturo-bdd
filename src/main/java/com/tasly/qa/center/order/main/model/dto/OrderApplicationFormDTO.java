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
public class OrderApplicationFormDTO {

	/**
	 * applicationFormId
	 */
	private String applicationFormId;

	/**
	 * 呈批件编号
	 */
	private String processOrderNo;

	/**
	 * 申请人
	 */
	private String applicant;

	/**
	 * 申请日期
	 */
	private String applicationDate;

	/**
	 * 项目类型
	 */
	private String projectType;

	/**
	 * 板块
	 */
	private String module;

	/**
	 * 状态，1 审批中 2.已通过 3.已否决 4 已撤回
	 */
	private String status;

	/**
	 * 执行开始时间
	 */
	private String executionStartTime;

	/**
	 * 执行结束时间
	 */
	private String executionEndTime;

	/**
	 * 销售组织SAP编码
	 */
	private String saleOrgSapCode;

	/**
	 * 创建人
	 */
	private String createPerson;
	/**
	 * 创建时间
	 */
	private String createTime;

}
