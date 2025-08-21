package com.anturo.qa.center.order.main.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Author guoqing
 * @Date 2024/09/29
 * @Description 大区允销一级商
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class OrderPermittedSalesCustomerDTO {

	/**
	 * orderPermittedSalesCustomerId
	 */
	private String orderPermittedSalesCustomerId;

	/**
	 * 销售组织控销商品ID
	 */
	private String saleOrgControlledProductId;

	/**
	 * 允销大区ID
	 */
	private String permittedSalesRegionId;

	/**
	 * 客户中台编码
	 */
	private String customerEntZtCode;

	/**
	 * 客户SAP编码
	 */
	private String customerEntSapCode;

	/**
	 * 客户CRM编码
	 */
	private String customerEntCrmCode;

	/**
	 * 客户名称
	 */
	private String customerName;

	/**
	 * 类型 FIRST_LEVEL_CUSTOMER(一级商), SHARE（共享）
	 */
	private String type;

	/**
	 * 允销数量
	 */
	private String permittedSaleQuantity;

	/**
	 * 已销数量
	 */
	private String hasSaleQuantity;

	/**
	 * 生效状态 启用：ENABLE, 停用：DISABLE
	 */
	private String effectiveState;

	/**
	 * 创建人
	 */
	private String createPerson;

	/**
	 * 创建人名称
	 */
	private String createPersonName;

	/**
	 * 创建时间
	 */
	private String createTime;

	/**
	 * 更新人
	 */
	private String updatePerson;

	/**
	 * 更新人名称
	 */
	private String updatePersonName;

	/**
	 * 更新时间
	 */
	private String updateTime;

	/**
	 * 删除标记, 0 未删除， 1已删除， 默认0
	 */
	private String dr;

	/**
	 * 扩展信息
	 */
	private String extension;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 租户ID
	 */
	private String tenantId;

	/**
	 * 应用实例ID
	 */
	private String instanceId;

}