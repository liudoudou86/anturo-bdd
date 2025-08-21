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
public class OrderDiscountCalcCalculateReqGroupListListDTO {

	/**
	 * 商品类型
	 */
	private String orderWideDiscountSubject;

	/**
	 * 整单折扣分配计划ID
	 */
	private String discountAllocationPlanId;

	/**
	 * 整单折扣分配计划详细ID
	 */
	private String discountAllocationPlanDetailId;

	/**
	 * 整单折扣ID
	 */
	private String discountId;

	/**
	 * 预折折扣详细ID
	 */
	private String preDiscountDetailId;

	/**
	 * 商品SAP编码
	 */
	private String itemSapCode;

	/**
	 * 商品SAP名称
	 */
	private String itemName;

	/**
	 * 商品SAP描述
	 */
	private String itemDesc;

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
	 * 单价
	 */
	private String unitPrice;

	/**
	 * 单位
	 */
	private String unit;

	/**
	 * 单位描述
	 */
	private String unitDesc;

	/**
	 * 数量
	 */
	private String quantity;

	/**
	 * 包装比率
	 */
	private String packingRatio;

	/**
	 * 预折
	 */
	private String preDiscount;

	/**
	 * 预折比率
	 */
	private String preDiscountRate;

	/**
	 * 计算数值
	 */
	private String calculationValue;

	/**
	 * 基础单位名称
	 */
	private String baseUnitName;

	/**
	 * 基础单位编码
	 */
	private String baseUnitCode;

	/**
	 * 原总价
	 */
	private String totalInline;

	/**
	 * 预折折扣
	 */
	private String preDiscountTotal;

	/**
	 * 预折后总金额
	 */
	private String preDiscountAfterTotalInline;

	/**
	 * 整单折扣
	 */
	private String orderDiscountTotal;

	/**
	 * 实付款
	 */
	private String realInline;

	/**
	 * 是否发货,1:发货
	 */
	private String needShip;

	/**
	 * 箱装比
	 */
	private String boxRatio;

	/**
	 * 盒箱比
	 */
	private String boxRatioCompare;

	/**
	 * 盒箱比
	 */
	private String boxRatioCompareDesc;

	/**
	 * 预折类型
	 */
	private String preType;

	/**
	 * 计算类型
	 */
	private String calculationType;

	/**
	 *
	 */
	private String xunit;

	/**
	 *
	 */
	private String yunit;

	/**
	 *
	 */
	private String salseSpec;

	/**
	 *
	 */
	private String discountProportion;

}
