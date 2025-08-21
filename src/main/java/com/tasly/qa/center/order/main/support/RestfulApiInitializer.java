package com.anturo.qa.center.order.main.support;


import com.anturo.qa.center.order.main.model.pojo.Domain;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.Getter;
import org.hamcrest.Matchers;

import java.text.MessageFormat;
import java.util.Map;
import java.util.Optional;

/**
 * @Author Mayer Yu
 * @Date 2022/2/16 14:30
 **/
@Getter
public class RestfulApiInitializer {

	private static Map<String, Map<String, ? extends Map<String, Object>>> yaml = com.anturo.qa.center.order.main.support.AutoConfigureSelector.config();
	private Domain domain;

	public RestfulApiInitializer(String platform) {
		this.domain = Domain.builder()
				.url(Optional.ofNullable(yaml.get("api").get(platform).get("url")).orElse("URL").toString())
				.protocal(Optional.ofNullable(yaml.get("api").get(platform).get("protocal")).orElse("PROTOCAL").toString())
				.host(Optional.ofNullable(yaml.get("api").get(platform).get("host")).orElse("IP").toString())
				.port(Optional.ofNullable(yaml.get("api").get(platform).get("port")).orElse("PORT").toString())
				.session(Optional.ofNullable(yaml.get("api").get(platform).get("session")).orElse("NO-SESSION").toString())
				.token(Optional.ofNullable(yaml.get("api").get(platform).get("token")).orElse("NO-TOKEN").toString())
				.build();
	}

	public RestfulApiInitializer(String platform, boolean webSession) {
		String session;
		if (webSession) {
			String profile = yaml.get("bdd").get("profile").get("current").toString();
			session = Optional.ofNullable(
					RestAssured.given()
							.baseUri("http://127.0.0.1:14003").basePath(MessageFormat.format("/webSession/{0}", profile))
							.get()
							.then()
							.statusCode(200)
							.body("msg", Matchers.containsString("success"))
							.extract().response().jsonPath().getString("data")
			).orElse("NO-SESSION");
		} else {
			session = Optional.ofNullable(
					yaml.get("api").get(platform).get("session")
			).orElse("NO-SESSION").toString();
		}
		this.domain = Domain.builder()
				.url(Optional.ofNullable(yaml.get("api").get(platform).get("url")).orElse("URL").toString())
				.protocal(Optional.ofNullable(yaml.get("api").get(platform).get("protocal")).orElse("PROTOCAL").toString())
				.host(Optional.ofNullable(yaml.get("api").get(platform).get("host")).orElse("IP").toString())
				.port(Optional.ofNullable(yaml.get("api").get(platform).get("port")).orElse("PORT").toString())
				.session(session)
				.token(Optional.ofNullable(yaml.get("api").get(platform).get("token")).orElse("NO-TOKEN").toString())
				.build();
	}

	public RestfulApiInitializer(String platform, boolean webSession, boolean webToken) {
		String token;
		if (webToken) {
			String profile = yaml.get("bdd").get("profile").get("current").toString();
			token = Optional.ofNullable(
					RestAssured.given()
							.baseUri("http://127.0.0.1:14003").basePath(MessageFormat.format("/webToken/{0}", profile))
							.get()
							.then()
							.statusCode(200)
							.body("msg", Matchers.containsString("success"))
							.extract().response().jsonPath().getString("data")
			).orElse("NO-TOKEN");
		} else {
			token = Optional.ofNullable(
					yaml.get("api").get(platform).get("token")
			).orElse("NO-TOKEN").toString();
		}
		this.domain = Domain.builder()
				.url(Optional.ofNullable(yaml.get("api").get(platform).get("url")).orElse("URL").toString())
				.protocal(Optional.ofNullable(yaml.get("api").get(platform).get("protocal")).orElse("PROTOCAL").toString())
				.host(Optional.ofNullable(yaml.get("api").get(platform).get("host")).orElse("IP").toString())
				.port(Optional.ofNullable(yaml.get("api").get(platform).get("port")).orElse("PORT").toString())
				.session(Optional.ofNullable(yaml.get("api").get(platform).get("session")).orElse("NO-SESSION").toString())
				.token(token)
				.build();
	}


	public static RestfulApiInitializer platform(String platform) {
		return new RestfulApiInitializer(platform);
	}

	public static RestfulApiInitializer platform(String platform, boolean webSession) {
		return new RestfulApiInitializer(platform, webSession);
	}

	public static RestfulApiInitializer platform(String platform, boolean webSession, boolean webToken) {
		return new RestfulApiInitializer(platform, webSession, webToken);
	}

	public static Domain b2bFrontStageApiInitializer(boolean webSession) {
		String session;
		if (webSession) {
			String profile = yaml.get("bdd").get("profile").get("current").toString();
			session = Optional.ofNullable(
					RestAssured.given()
							.baseUri("http://127.0.0.1:14003").basePath(MessageFormat.format("/getB2bFrontSession/{0}", profile))
							.get()
							.then()
							.statusCode(200)
							.body("msg", Matchers.containsString("success"))
							.extract().response().jsonPath().getString("data")
			).orElse("NO-SESSION");
		} else {
			session = Optional.ofNullable(
					yaml.get("api").get("b2b").get("front_session")
			).orElse("NO-SESSION").toString();
		}
		return Domain.builder()
				.url(Optional.ofNullable(yaml.get("api").get("b2b").get("front_url")).orElse("B2B-FrontStage-Domain-Url").toString())
				.host("")
				.port("")
				.session(session)
				.build();
	}

	public static Domain b2bBackStageApiInitializer(boolean webSession) {
		String session;
		if (webSession) {
			String profile = yaml.get("bdd").get("profile").get("current").toString();
			session = Optional.ofNullable(
					RestAssured.given()
							.baseUri("http://127.0.0.1:14003").basePath(MessageFormat.format("/getB2bBackSession/{0}", profile))
							.get()
							.then()
							.statusCode(200)
							.body("msg", Matchers.containsString("success"))
							.extract().response().jsonPath().getString("data")
			).orElse("NO-SESSION");
		} else {
			session = Optional.ofNullable(
					yaml.get("api").get("b2b").get("back_session")
			).orElse("NO-SESSION").toString();
		}
		return Domain.builder()
				.url(Optional.ofNullable(yaml.get("api").get("b2b").get("back_url")).orElse("B2B-BackStage-Domain-Url").toString())
				.host("")
				.port("")
				.session(session)
				.build();
	}

	public static Domain erpApiInitializer() {
		return Domain.builder()
				.url(Optional.ofNullable(yaml.get("api").get("erp").get("url")).orElse("ERP-Domain-Url").toString())
				.host("")
				.port("")
				.session("No-Need")
				.build();
	}

	public static void refreshB2bScope(String b2bFrontStageUsername, String b2bFrontStagePassword, String b2bBackStageUsername, String b2bBackStagePassword) {
		String b2bDomainFrontUrl = Optional.ofNullable(yaml.get("api").get("b2b").get("front_url")).orElse("B2B-Domain-Front-Url").toString();
		String b2bDomainBackUrl = Optional.ofNullable(yaml.get("api").get("b2b").get("back_url")).orElse("B2B-Domain-Back-Url").toString();
		Response b2BFrontStageResponse = RestAssured.given()
				.baseUri(b2bDomainFrontUrl).basePath("/anturob2bmobile/login")
				.contentType(ContentType.URLENC)
				.urlEncodingEnabled(true)
				.queryParams("username", b2bFrontStageUsername, "password", b2bFrontStagePassword)
				.and().log().all()
				.when().post()
				.then().log().all()
				.statusCode(200)
				.body("resCode", Matchers.equalTo(200), "message", Matchers.equalTo("登陆成功"))
				.extract().response();
		String b2BFrontStageSession = Optional.ofNullable(b2BFrontStageResponse.getHeaders().getValue("Set-Cookie").split("[=|;]")[1]).orElse("NO-B2B-FRONT-STAGE-SESSION");
		Response b2BBackStageResponse = RestAssured.given().redirects().follow(true)
				.baseUri(b2bDomainBackUrl).basePath("/anturob2bbms/login")
				.contentType(ContentType.JSON)
				.queryParams("username", b2bBackStageUsername, "password", b2bBackStagePassword)
				.and().log().all()
				.when().post()
				.then().log().all()
				.statusCode(302)
				.extract().response();
		String b2BBackStageLocationUrl = Optional.ofNullable(b2BBackStageResponse.getHeader("Location")).orElse("NO-B2B-BACK-STAGE-HEADER-LOCATION");
		String b2BBackStageRedirectSession = Optional.ofNullable(b2BBackStageResponse.getCookies().get("SESSION")).orElse("NO-B2B-BACK-STAGE-REDIRECT-SESSION");
		Response b2BBackStageRedirectResponse = RestAssured.given()
				.cookies("SESSION", b2BBackStageRedirectSession)
				.and().log().all()
				.when().get(b2BBackStageLocationUrl)
				.then().log().all()
				.statusCode(200)
				.body(Matchers.containsString("<span>订单管理</span>"))
				.extract().response();
		System.err.println(String.format("B2B前台登录SESSION为：%s", b2BFrontStageSession));
		System.err.println(String.format("B2B后台登录SESSION为：%s", b2BBackStageRedirectSession));
		// 开始初始化当前B2B环境SESSION
		String profile = yaml.get("bdd").get("profile").get("current").toString();
		RestAssured.given()
				.baseUri("http://127.0.0.1:14003").basePath("/newB2bFrontSession/{env}/{session}")
				.contentType(ContentType.JSON)
				.pathParam("env", profile)
				.pathParam("session", b2BFrontStageSession)
				.and().log().all()
				.when().get()
				.then().log().all()
				.statusCode(200)
				.body("code", Matchers.equalTo(200), "msg", Matchers.containsString(String.format("refresh b2b_front_session_%s success", profile)));
		RestAssured.given()
				.baseUri("http://127.0.0.1:14003").basePath("/newB2bBackSession/{env}/{session}")
				.contentType(ContentType.JSON)
				.pathParam("env", profile)
				.pathParam("session", b2BBackStageRedirectSession)
				.and().log().all()
				.when().get()
				.then().log().all()
				.statusCode(200)
				.body("code", Matchers.equalTo(200), "msg", Matchers.containsString(String.format("refresh b2b_back_session_%s success", profile)));
	}

	public static void refreshToken(String token) {
		String profile = yaml.get("bdd").get("profile").get("current").toString();
		RestAssured.given()
				.baseUri("http://127.0.0.1:14003").basePath("/newToken/{env}/{token}")
				.contentType(ContentType.JSON)
				.pathParam("env", profile)
				.pathParam("token", token)
				.and().log().all()
				.when().get()
				.then().log().all()
				.statusCode(200)
				.body("code", Matchers.equalTo(200), "msg", Matchers.containsString(String.format("update token_%s success", profile)));
	}
}

