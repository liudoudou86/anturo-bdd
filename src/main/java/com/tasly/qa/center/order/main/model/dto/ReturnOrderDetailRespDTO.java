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
public class ReturnOrderDetailRespDTO {

	/**
	 * id
	 */
	private String id;

	/**
	 * 订单号
	 */
	private String orderNo;

	/**
	 * 订单行号
	 */
	private String itemLine;

	/**
	 * 订单行ID
	 */
	private String itemId;

	/**
	 * 交货单ID
	 */
	private String whsePostReceiptId;

	/**
	 * 订单行类型
	 */
	private String orderWideDiscountSubject;

	/**
	 * sku
	 */
	private String sku;

	/**
	 * description
	 */
	private String description;

	/**
	 * batchNum
	 */
	private String batchNum;

	/**
	 * location
	 */
	private String location;

	/**
	 * isReturnable
	 */
	private String isReturnable;

	/**
	 * oriQuantity
	 */
	private String oriQuantity;

	/**
	 * quantity
	 */
	private String quantity;

	/**
	 * checkNum
	 */
	private String checkNum;

	/**
	 * 商品单价
	 */
	private String unitPrice;

	/**
	 * 预折价格
	 */
	private String preDiscountUnitPrice;

	/**
	 * 折让价格
	 */
	private String rebateTotal;

	/**
	 * 退货订单行价格
	 */
	private String returnOrderLinePrice;

	/**
	 * selected
	 */
	private String selected;
}
