package com.anturo.qa.center.order.main.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

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
public class ReturnOrderRespDTO {

	/**
	 * 订单号
	 */
	private String orderNo;

	/**
	 * 备注
	 */
	private String reason;

	/**
	 * isReceiveDrugInvoice
	 */
	private String isReceiveDrugInvoice;

	/**
	 * isResendDrug
	 */
	private String isResendDrug;

	/**
	 * isReturnPurveyorDrug
	 */
	private String isReturnPurveyorDrug;

	/**
	 * 退货订单详细内容
	 */
	List<com.anturo.qa.center.order.main.model.dto.ReturnOrderDetailRespDTO> returnOrderDetailRespDTOList;
}
