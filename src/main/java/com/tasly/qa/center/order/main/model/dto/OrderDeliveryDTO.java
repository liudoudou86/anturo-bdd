package com.anturo.qa.center.order.main.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Author Tesla
 * @Date 2025/04/28
 * @Description
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class OrderDeliveryDTO {

	/**
	 * 日期
	 */
	private String date;

	/**
	 * 交货单号
	 */
	private String deliverNo;

	/**
	 * 冲销交货单号
	 */
	private String writeOffDeliverNo;

	/**
	 * 订单行号
	 */
	private String orderItem;

	/**
	 * 发货数量
	 */
	private String batchNum;

	/**
	 * eccid
	 */
	private String eccid;

	/**
	 * 订单行总数量
	 */
	private String orderItemQuantityTotal;

	/**
	 * 订单号
	 */
	private String orderNo;

	/**
	 * 退货订单号
	 */
	private String refundOrderNo;

	/**
	 * 凭证号
	 */
	private String materialNumber;

	/**
	 * 冲销凭证号
	 */
	private String writeOffMaterialNumber;

}
