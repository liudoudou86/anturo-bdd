package com.anturo.qa.center.order.main.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Author guoqing
 * @Date 2024/09/29
 * @Description 允销大区主表
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class OrderPermittedSalesRegionDTO {

	/**
	 * orderPermittedSalesRegionId
	 */
	private String orderPermittedSalesRegionId;

	/**
	 * sale_org_controlled_product_id
	 */
	private String saleOrgControlledProductId;

	/**
	 * 大区编码
	 */
	private String regionCode;

	/**
	 * 大区名称
	 */
	private String regionName;

	/**
	 * 允销数量
	 */
	private String permittedSaleQuantity;

	/**
	 * 单位编码
	 */
	private String unitCode;

	/**
	 * 单位名称
	 */
	private String unitName;

	/**
	 * 允销开始时间
	 */
	private String permittedSaleBeginDate;

	/**
	 * 允销结束时间
	 */
	private String permittedSaleEndDate;

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
	private Long dr;

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
