package com.anturo.qa.center.order.process;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Objects;

/**
 * @Author guoqing
 * @Date 2024/09/29
 * @Description 大区数据枚举
 */
public enum RegionEnum {

	/**
	 * 安徽大区
	 */
	REGION_100000("100000", "安徽大区") {
		@Override
		public String description() {
			return StringUtils.join(Arrays.asList(this.getCode(), this.getName()), "|");
		}
	},

	/**
	 * 天津大区
	 */
	REGION_340000("340000", "天津大区") {
		@Override
		public String description() {
			return StringUtils.join(Arrays.asList(this.getCode(), this.getName()), "|");
		}
	},

	/**
	 * 未知大区
	 */
	UNKNOWN_REGION("UNKNOWN", "未知大区") {
		@Override
		public String description() {
			return "Unknown Description";
		}
	};

	private String code;
	private String name;

	public abstract String description();

	/**
	 * @param code 区域代码
	 * @param name 区域名称
	 */
	RegionEnum(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public static String fetchName(String code) {
		return Arrays.stream(RegionEnum.values())
				.filter(regionEnum -> Objects.equals(regionEnum.code, code))
				.findFirst().orElse(RegionEnum.UNKNOWN_REGION).getName();
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}
}