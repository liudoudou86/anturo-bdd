package com.anturo.qa.center.order.main.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Author Tesla
 * @Date 2024/05/23
 * @Description
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class OrderDiscountCalcCalculateReqGroupListDTO {

	/**
	 * 药品类型编码
	 */
	private String itemGroupCode;

	/**
	 * 药品类型名称
	 */
	private String itemGroupName;

	/**
	 * 分销渠道编码
	 */
	private String distributionChannelCode;

	/**
	 * 分销渠道名称
	 */
	private String distributionChannelName;

	/**
	 * 原总价
	 */
	private String totalInline;

	/**
	 * 预折折扣
	 */
	private String preDiscountTotal;

	/**
	 * 整单折扣
	 */
	private String orderDiscountTotal;

	/**
	 * 实付款
	 */
	private String realInline;

	/**
	 *
	 */
	private String ifAbove;

	/**
	 * 订单行数据
	 */
	List<com.anturo.qa.center.order.main.model.dto.OrderDiscountCalcCalculateReqGroupListListDTO> list;
}
