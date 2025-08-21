package com.anturo.qa.center.order.bdd.order.apis;

import com.anturo.qa.center.order.main.model.pojo.Domain;
import com.anturo.qa.center.order.main.support.RestfulApiInitializer;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

/**
 * @Author Tesla
 * @Date 2024/12/27
 * @Description
 */

@SuppressWarnings("all")
@Setter
@Getter
@Slf4j
public class UpdateCenterTokenApi01 {

	private Domain b2bDomain = RestfulApiInitializer.platform("b2b-server").getDomain();

	@Step(value = "[REQUEST]b2b服务获取token")
	public Response b2bServerGetToken(String token) {
		Response response = SerenityRest.given()
				.baseUri(String.format("%s://%s:%s", b2bDomain.getProtocal(), b2bDomain.getHost(), b2bDomain.getPort()))
				.basePath(String.format("/newToken/stg/%s", token))
				.contentType(ContentType.URLENC)
				.log().all().when()
				.get().then()
				.log().all()
				.statusCode(200)
				.extract().response();
		return response;
	}

}
