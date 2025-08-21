package com.qa.demo.steps;

import io.cucumber.java8.En;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author Tesla
 * @date 2022/10/18 18:02
 * 描述 BDD 测试用例集
 */

@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class TestSteps implements En {

	public TestSteps() {
		this.testcase();
	}

	public void testcase() {
		Given("访问百度一下网站", () -> log.info("登录百度一下"));
		When("输入搜索词", () -> log.info("输入BDD"));
		Then("应该可以看到搜索词", () -> log.info("可以看到BDD"));
	}
}