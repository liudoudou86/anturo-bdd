package com.anturo.qa.center.order.process;

import java.util.Arrays;
import java.util.Objects;

/**
 * @Author Tesla
 * @Date 2024/03/08
 * @Description 商品数据枚举
 */

public enum ProductEnum {

	/**
	 * 无品规
	 */
	NOTHING("No-ProductSku", "No-ProductName", "No-ProductSpec", "No-ProductZtCode", "No-ProductCrmCode") {
		@Override
		public String description() {
			return "Nothing Description";
		}
	};

	private String productSku;
	private String productName;
	private String productSpec;
	private String productZtCode;
	private String productCrmCode;

	public abstract String description();

	/**
	 * @param productSku     品规CODE
	 * @param productName    品规名称
	 * @param productSpec    品规说明
	 * @param productZtCode  品规中台CODE
	 * @param productCrmCode 品规CRMCODE
	 */
	ProductEnum(String productSku, String productName, String productSpec, String productZtCode, String productCrmCode) {
		this.productSku = productSku;
		this.productName = productName;
		this.productSpec = productSpec;
		this.productZtCode = productZtCode;
		this.productCrmCode = productCrmCode;
	}

	public static String fetchProductName(String productSku) {
		return Arrays.stream(ProductEnum.values())
				.filter(productEnum -> Objects.equals(productEnum.productSku, productSku))
				.findFirst().orElse(ProductEnum.NOTHING).getProductName();
	}

	public static String fetchProductSpec(String productSku) {
		return Arrays.stream(ProductEnum.values())
				.filter(productEnum -> Objects.equals(productEnum.productSku, productSku))
				.findFirst().orElse(ProductEnum.NOTHING).getProductSpec();
	}

	public static String fetchProductZtCode(String productSku) {
		return Arrays.stream(ProductEnum.values())
				.filter(productEnum -> Objects.equals(productEnum.productSku, productSku))
				.findFirst().orElse(ProductEnum.NOTHING).getProductZtCode();
	}

	public static String fetchProductCrmCode(String productSku) {
		return Arrays.stream(ProductEnum.values())
				.filter(productEnum -> Objects.equals(productEnum.productSku, productSku))
				.findFirst().orElse(ProductEnum.NOTHING).getProductCrmCode();
	}

	public String getProductSku() {
		return productSku;
	}

	public String getProductName() {
		return productName;
	}

	public String getProductSpec() {
		return productSpec;
	}

	public String getProductZtCode() {
		return productZtCode;
	}

	public String getProductCrmCode() {
		return productCrmCode;
	}

}
