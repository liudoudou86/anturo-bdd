package com.anturo.qa.center.order.main.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Author Tesla
 * @Date 2024/05/22
 * @Description
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class OrderAllocationPlanDetailDTO {

	/**
	 * applicationPlanDetailId
	 */
	private String allocationPlanDetailId;

	/**
	 * allocationPlanId
	 */
	private String allocationPlanId;

	/**
	 * applicationFormDetailId
	 */
	private String applicationFormDetailId;

	/**
	 * 项目行号
	 */
	private String lineNumber;

	/**
	 * 客户SAP编码
	 */
	private String customerEntSapCode;

	/**
	 * 销售组织SAP编码
	 */
	private String saleOrgSapCode;

	/**
	 * 大区编码
	 */
	private String regionCode;

	/**
	 * 大区名称
	 */
	private String regionName;

	/**
	 * 原品编码
	 */
	private String originalItemSapCode;

	/**
	 * 原品分销渠道编码
	 */
	private String originalDistributionChannelCode;

	/**
	 * 原品分销渠道名称
	 */
	private String originalDistributionChannelName;

	/**
	 * 赠品编码
	 */
	private String giftItemSapCode;

	/**
	 * 赠品分销渠道编码
	 */
	private String giftDistributionChannelCode;

	/**
	 * 赠品分销渠道名称
	 */
	private String giftDistributionChannelName;

	/**
	 * 分配数量
	 */
	private String allocationQuantity;

	/**
	 * 已使用数据
	 */
	private String usedQuantity;

	/**
	 * 单位
	 */
	private String unit;

	/**
	 * 买赠比
	 */
	private String purchaseGiftRatio;

	/**
	 * 原品数量
	 */
	private Integer originalQuantity;

	/**
	 * 赠品数量
	 */
	private Integer giftQuantity;

	/**
	 * 起赠量
	 */
	private Integer initialBonusQuantity;

	/**
	 * 赠品是否发货
	 */
	private String needShip;

	/**
	 * 零头药-批号
	 */
	private String lotNo;

	/**
	 * 零头药-生产日期
	 */
	private String productionDate;

	/**
	 * 零头药-有效期
	 */
	private String expirationDate;

	/**
	 * 作废状态
	 */
	private String invalidStatus;

	/**
	 * 创建人
	 */
	private String createPerson;

	/**
	 * 创建时间
	 */
	private String createTime;

	/**
	 * 备注
	 */
	private String remark;


}
