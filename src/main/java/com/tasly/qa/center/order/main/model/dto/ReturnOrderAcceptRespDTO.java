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
public class ReturnOrderAcceptRespDTO {

	/**
	 * 退货订单ID
	 */
	private String returnOrderId;

	/**
	 * 退货订单详细内容
	 */
	List<com.anturo.qa.center.order.main.model.dto.AcceptDetailReqDTO> acceptDetailReqDTOList;
}
