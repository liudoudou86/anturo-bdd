package com.anturo.qa.center.order.main.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Author guoqing
 * @Date 2024/10/09
 * @Description
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ItMediumPackagingSpecificationDTO {

	/**
	 * 商品编码
	 */
	private String productCode;

	/**
	 * 中包规格
	 */
	private String mediumPackagingSpecification;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 数量
	 */
	private String quantity;

	/**
	 * 扩展信息
	 */
	private String extension;

	/**
	 * 租户ID
	 */
	private String tenantId;

	/**
	 * 应用实例ID
	 */
	private String instanceId;

	/**
	 * 创建时间
	 */
	private String createTime;

	/**
	 * 修改时间
	 */
	private String updateTime;

	/**
	 * 创建人名称
	 */
	private String createPersonName;

	/**
	 * 创建人
	 */
	private String createPerson;

	/**
	 * 修改人名称
	 */
	private String updatePersonName;

	/**
	 * 修改人
	 */
	private String updatePerson;

	/**
	 * 逻辑删除标志,0表示未删除，1表示删除
	 */
	private Integer dr;

}
