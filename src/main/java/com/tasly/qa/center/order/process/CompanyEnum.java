package com.anturo.qa.center.order.process;

import java.util.Arrays;
import java.util.Objects;

/**
 * @Author Tesla
 * @Date 2024/03/08
 * @Description 客户数据枚举
 */

public enum CompanyEnum {

	/**
	 * 空公司
	 */
	EMPTY("N", "", "", "") {
		@Override
		public String description() {
			return "Nothing Description";
		}
	},

	/**
	 * 无公司
	 */
	NOTHING("No-SapCode", "No-CrmCode", "No-ZtCode", "No-Name") {
		@Override
		public String description() {
			return "Nothing Description";
		}
	};

	private String sapCode;

	private String crmCode;

	private String ztCode;

	private String name;

	public abstract String description();

	/**
	 * @param sapCode sapCode
	 * @param crmCode crmCode
	 * @param ztCode  ztCode
	 * @param name    name
	 */
	CompanyEnum(String sapCode, String crmCode, String ztCode, String name) {
		this.sapCode = sapCode;
		this.crmCode = crmCode;
		this.ztCode = ztCode;
		this.name = name;
	}

	public static String fetchCrmCode(String sapCode) {
		return Arrays.stream(CompanyEnum.values())
				.filter(companyEnum -> Objects.equals(companyEnum.sapCode, sapCode))
				.findFirst().orElse(CompanyEnum.NOTHING).getCrmCode();
	}

	public static String fetchZtCode(String sapCode) {
		return Arrays.stream(CompanyEnum.values())
				.filter(companyEnum -> Objects.equals(companyEnum.sapCode, sapCode))
				.findFirst().orElse(CompanyEnum.NOTHING).getZtCode();
	}

	public static String fetchName(String sapCode) {
		return Arrays.stream(CompanyEnum.values())
				.filter(companyEnum -> Objects.equals(companyEnum.sapCode, sapCode))
				.findFirst().orElse(CompanyEnum.NOTHING).getName();
	}

	public String getSapCode() {
		return sapCode;
	}

	public String getCrmCode() {
		return crmCode;
	}

	public String getZtCode() {
		return ztCode;
	}

	public String getName() {
		return name;
	}

}
