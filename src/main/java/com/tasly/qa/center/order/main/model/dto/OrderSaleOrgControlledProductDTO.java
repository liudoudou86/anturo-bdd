package com.anturo.qa.center.order.main.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Author guoqing
 * @Date 2024/09/29
 * @Description 销售组织控销商品
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class OrderSaleOrgControlledProductDTO {

	/**
	 * ID
	 */
	private String saleOrgControlledProductId;

	/**
	 * 销售组织中台编码
	 */
	private String saleOrgZtCode;

	/**
	 * 销售组织SAP编码
	 */
	private String saleOrgSapCode;

	/**
	 * 销售组织名称
	 */
	private String saleOrgName;

	/**
	 * 商品sap编码
	 */
	private String productSapSku;

	/**
	 * 商品描述
	 */
	private String productDescription;

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