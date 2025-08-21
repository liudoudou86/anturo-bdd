package com.anturo.qa.center.order.main.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @Author Tesla
 * @Date 2024/05/21
 * @Description
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class OrderPreDiscountDetailDTO {

	/**
	 * 主键ID
	 */
	private String id;

	/**
	 * preDiscountDetailId
	 */
	private String preDiscountDetailId;

	/**
	 * preDiscountId
	 */
	private String preDiscountId;

	/**
	 * 商品SAP编码
	 */
	private String itemSapCode;

	/**
	 * 分销渠道编码
	 */
	private String distributionChannelCode;

	/**
	 * 分销渠道名称
	 */
	private String distributionChannelName;

	/**
	 * 当前单价
	 */
	private BigDecimal currentPrice;

	/**
	 * 计算方式
	 */
	private String calculationType;

	/**
	 * 计算价格
	 */
	private BigDecimal calculationValue;

	/**
	 * 开始日期
	 */
	private String beginDate;

	/**
	 * 结束日期
	 */
	private String endDate;

	/**
	 * 生效状态
	 */
	private String effectiveState;

	/**
	 * 预折类型
	 */
	private String preType;

	/**
	 * 创建人
	 */
	private String createPerson;

	/**
	 * 创建时间
	 */
	private String createTime;

}
