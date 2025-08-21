package com.anturo.qa.center.order.main.model.dto;

import cn.hutool.json.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Author Tesla
 * @Date 2025/04/29
 * @Description
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class OrderApproveShippingInfoDTO {

	/**
	 * 订单号
	 */
	private String orderNo;

	/**
	 * 支付方式
	 */
	private String paymentMethod;

	/**
	 * 锁ID
	 */
	private String lockId;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 收货信息
	 */
	private JSONObject shippingInfo;

}
