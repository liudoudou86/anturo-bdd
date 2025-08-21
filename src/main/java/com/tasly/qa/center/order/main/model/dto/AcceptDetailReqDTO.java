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
public class AcceptDetailReqDTO {

	/**
	 * 数量
	 */
	private String quantity;

	/**
	 * 退货行ID
	 */
	private String returnItemId;

	/**
	 * 退货行号
	 */
	private String itemLine;

	/**
	 * 订单号
	 */
	private String orderNo;

	/**
	 * 订单行ID
	 */
	private String itemId;

	/**
	 * 交货单ID
	 */
	private String whsePostReceiptId;

}
