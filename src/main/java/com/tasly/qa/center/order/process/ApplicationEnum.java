package com.anturo.qa.center.order.process;

import java.util.Arrays;
import java.util.Objects;

/**
 * @Author Tesla
 * @Date 2024/05/22
 * @Description
 */


public enum ApplicationEnum {

	/**
	 * 无申请信息
	 */
	NOTHING("No-Applicant", "No-ApplicantName", "No-ApplicantCompanyZtCode",
			"No-ApplicantCompanySapCode", "No-ApplicantCompanyName",
			"No-ApplicantDepartmentCode", "No-ApplicantDepartmentName",
			"No-ApplicantContractNumber") {
		@Override
		public String description() {
			return "Nothing Description";
		}
	};

	private String applicant;

	private String applicantName;

	private String applicantCompanyZtCode;

	private String applicantCompanySapCode;

	private String applicantCompanyName;

	private String applicantDepartmentCode;

	private String applicantDepartmentName;

	private String applicantContractNumber;

	public abstract String description();

	/**
	 * @param applicant               申请人
	 * @param applicantName           申请人姓名
	 * @param applicantCompanyZtCode  申请人所属公司中台编码
	 * @param applicantCompanySapCode 申请人所属公司SAP编码
	 * @param applicantCompanyName    申请人所属公司名称
	 * @param applicantDepartmentCode 申请人所属部门编码
	 * @param applicantDepartmentName 申请人所属部门名称
	 * @param applicantContractNumber 申请人联系电话
	 */
	ApplicationEnum(String applicant, String applicantName, String applicantCompanyZtCode, String applicantCompanySapCode,
	                String applicantCompanyName, String applicantDepartmentCode, String applicantDepartmentName, String applicantContractNumber) {
		this.applicant = applicant;
		this.applicantName = applicantName;
		this.applicantCompanyZtCode = applicantCompanyZtCode;
		this.applicantCompanySapCode = applicantCompanySapCode;
		this.applicantCompanyName = applicantCompanyName;
		this.applicantDepartmentCode = applicantDepartmentCode;
		this.applicantDepartmentName = applicantDepartmentName;
		this.applicantContractNumber = applicantContractNumber;
	}

	public static String fetchApplicant(String applicant) {
		return Arrays.stream(ApplicationEnum.values())
				.filter(applicationEnum -> Objects.equals(applicationEnum.applicant, applicant))
				.findFirst().orElse(ApplicationEnum.NOTHING).getApplicant();
	}

	public static String fetchApplicantName(String applicantName) {
		return Arrays.stream(ApplicationEnum.values())
				.filter(applicationEnum -> Objects.equals(applicationEnum.applicant, applicantName))
				.findFirst().orElse(ApplicationEnum.NOTHING).getApplicantName();
	}

	public static String fetchApplicantCompanyZtCode(String applicantCompanyZtCode) {
		return Arrays.stream(ApplicationEnum.values())
				.filter(applicationEnum -> Objects.equals(applicationEnum.applicant, applicantCompanyZtCode))
				.findFirst().orElse(ApplicationEnum.NOTHING).getApplicantCompanyZtCode();
	}

	public static String fetchApplicantCompanySapCode(String applicantCompanySapCode) {
		return Arrays.stream(ApplicationEnum.values())
				.filter(applicationEnum -> Objects.equals(applicationEnum.applicant, applicantCompanySapCode))
				.findFirst().orElse(ApplicationEnum.NOTHING).getApplicantCompanySapCode();
	}

	public static String fetchApplicantCompanyName(String applicantCompanyName) {
		return Arrays.stream(ApplicationEnum.values())
				.filter(applicationEnum -> Objects.equals(applicationEnum.applicant, applicantCompanyName))
				.findFirst().orElse(ApplicationEnum.NOTHING).getApplicantCompanyName();
	}

	public static String fetchApplicantDepartmentCode(String applicantDepartmentCode) {
		return Arrays.stream(ApplicationEnum.values())
				.filter(applicationEnum -> Objects.equals(applicationEnum.applicant, applicantDepartmentCode))
				.findFirst().orElse(ApplicationEnum.NOTHING).getApplicantDepartmentCode();
	}

	public static String fetchApplicantDepartmentName(String applicantDepartmentName) {
		return Arrays.stream(ApplicationEnum.values())
				.filter(applicationEnum -> Objects.equals(applicationEnum.applicant, applicantDepartmentName))
				.findFirst().orElse(ApplicationEnum.NOTHING).getApplicantDepartmentName();
	}

	public static String fetchApplicantContractNumber(String applicantContractNumber) {
		return Arrays.stream(ApplicationEnum.values())
				.filter(applicationEnum -> Objects.equals(applicationEnum.applicant, applicantContractNumber))
				.findFirst().orElse(ApplicationEnum.NOTHING).getApplicantContractNumber();
	}

	public String getApplicant() {
		return applicant;
	}

	public String getApplicantName() {
		return applicantName;
	}

	public String getApplicantCompanyZtCode() {
		return applicantCompanyZtCode;
	}

	public String getApplicantCompanySapCode() {
		return applicantCompanySapCode;
	}

	public String getApplicantCompanyName() {
		return applicantCompanyName;
	}

	public String getApplicantDepartmentCode() {
		return applicantDepartmentCode;
	}

	public String getApplicantDepartmentName() {
		return applicantDepartmentName;
	}

	public String getApplicantContractNumber() {
		return applicantContractNumber;
	}

}
