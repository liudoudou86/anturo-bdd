package com.anturo.qa.center.order.bdd.verify.runner;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

/**
 * @Author Tesla
 * @Date 2024/06/19
 * @Description
 */

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
		dryRun = false,
		strict = true,
		stepNotifications = true,
		features = "classpath:features/order/verify",
		glue = "com.anturo.qa.center.order.bdd.verify.specs",
		plugin = {"pretty", "json:target/cucumber-testng.json", "html:target/cucumber-report"},
		tags = "not (@disable or @todo)")
public class OrderVerifyRunner {
}
