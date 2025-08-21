package com.anturo.qa.center.order.main.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

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
public class OrderDataDTO {

	/**
	 * 订单类型
	 */
	private String orderType;

	/**
	 * 证照号
	 */
	private String deliveryNo;

	/**
	 * 销售组织ZT编码
	 */
	private String saleOrgZtCode;

	/**
	 * 销售组织SAP编码
	 */
	private String saleOrgSapCode;

	/**
	 * 销售组织名称
	 */
	private String saleOrgName;

	/**
	 * 大区编码
	 */
	private String regionCode;

	/**
	 * 大区名称
	 */
	private String regionName;

	/**
	 * 客户SAP编码
	 */
	private String customerEntSapCode;

	/**
	 * 客户CRM编码
	 */
	private String customerEntCrmCode;

	/**
	 * 客户ZT编码
	 */
	private String customerEntZtCode;

	/**
	 * 客户SAP名称
	 */
	private String customerName;

	/**
	 * 发票类型
	 */
	private String invoiceType;

	/**
	 * 支付方式
	 */
	private String paymentMethod;

	/**
	 * 整件发货
	 */
	private String shippingPolicy;

	/**
	 * 是否加急
	 */
	private String priority;

	/**
	 * 收货地址
	 */
	private String shippingAddress;

	/**
	 * 收货人姓名
	 */
	private String shippingRecipient;

	/**
	 * 收货人手机号
	 */
	private String shippingTelephone;

	/**
	 * 合同备注
	 */
	private String contractRemark;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 订单数据
	 */
	com.anturo.qa.center.order.main.model.dto.OrderDiscountCalcCalculateReqDTO calculateReq;

}
