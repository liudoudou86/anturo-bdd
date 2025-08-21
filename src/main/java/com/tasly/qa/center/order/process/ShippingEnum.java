package com.anturo.qa.center.order.process;

import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

/**
 * @Author Tesla
 * @Date 2025/04/29
 * @Description
 */

@Getter
public enum ShippingEnum {

	/**
	 * 无公司
	 */
	NOTHING("No-SapCode", "No-OrderBody") {
		@Override
		public String description() {
			return "Nothing Description";
		}
	};

	private String sapCode;

	private String orderBody;

	public abstract String description();

	ShippingEnum(String sapCode, String orderBody) {
		this.sapCode = sapCode;
		this.orderBody = orderBody;
	}

	public static String fetchSapCode(String sapCode) {
		return Arrays.stream(ShippingEnum.values())
				.filter(shippingEnum -> Objects.equals(shippingEnum.sapCode, sapCode))
				.findFirst().orElse(ShippingEnum.NOTHING).getSapCode();
	}

	public static String fetchOrderBody(String sapCode) {
		return Arrays.stream(ShippingEnum.values())
				.filter(shippingEnum -> Objects.equals(shippingEnum.sapCode, sapCode))
				.findFirst().orElse(ShippingEnum.NOTHING).getOrderBody();
	}

}
