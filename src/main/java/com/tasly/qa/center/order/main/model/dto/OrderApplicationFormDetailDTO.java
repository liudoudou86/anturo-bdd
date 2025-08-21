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
public class OrderApplicationFormDetailDTO {

	/**
	 * applicationFormDetailId
	 */
	private String applicationFormDetailId;

	/**
	 * applicationFormId
	 */
	private String applicationFormId;

	/**
	 * 商品编码
	 */
	private String itemSapCode;

	/**
	 * 申请数量
	 */
	private String quantity;

	/**
	 * 单位
	 */
	private String units;

	/**
	 * 已使用数据
	 */
	private String usedQuantity;

	/**
	 * 创建人
	 */
	private String createPerson;

	/**
	 * 创建时间
	 */
	private String createTime;

}
