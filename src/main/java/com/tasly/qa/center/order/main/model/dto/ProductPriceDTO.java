package com.anturo.qa.center.order.main.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Author Tesla
 * @Date 2024/07/30
 * @Description
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ProductPriceDTO {

	/**
	 * 维度编码
	 */
	private String dimensionCode;

	/**
	 * 维度优先级
	 */
	private String dimensionPriority;

	/**
	 * 销售商编码
	 */
	private String sellerCode;

	/**
	 * 分销渠道编码
	 */
	private String distributionChannelCode;

	/**
	 * 价格组编码
	 */
	private String priceGroupCode;

	/**
	 * 客户编码
	 */
	private String customerCode;

	/**
	 * 商品编码
	 */
	private String itemCode;

	/**
	 * 开始日期
	 */
	private String startTime;

	/**
	 * 价格
	 */
	private String price;

	/**
	 * 创建人
	 */
	private String createPerson;

	/**
	 * 创建时间
	 */
	private String createTime;

}
