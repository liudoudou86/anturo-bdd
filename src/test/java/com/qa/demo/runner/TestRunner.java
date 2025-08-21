package com.qa.demo.runner;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

/**
 * @Author Tesla
 * @date 2022/10/18 17:51
 * 描述 BDD 启动器
 */
@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
		strict = true,
		stepNotifications = true,
		features = "classpath:features/demo",
		glue = "com.qa.demo.steps",
		plugin = {"pretty", "json:target/cucumber-testng.json", "html:target/cucumber-report"},
		tags = "@test"
)
public class TestRunner {
}