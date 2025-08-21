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
public class CustomerInfoDTO {

	/**
	 * 客户中台编码
	 */
	private String code;

	/**
	 * 客户名称
	 */
	private String name;

	/**
	 * 客户名称
	 */
	private String label;

	/**
	 * 客户中台编码
	 */
	private String value;

	/**
	 * 客户SAP编码
	 */
	private String sapCode;

	/**
	 * 客户CRM编码
	 */
	private String crmCode;

	/**
	 * 大区编码
	 */
	private String regionCode;

	/**
	 * 大区名称
	 */
	private String regionName;

}
