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
public class OrderDiscountCalcCalculateReqDTO {

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
	private String discountProportion;

	/**
	 * 订单行数据
	 */
	List<com.anturo.qa.center.order.main.model.dto.OrderDiscountCalcCalculateReqGroupListDTO> groupList;
}
