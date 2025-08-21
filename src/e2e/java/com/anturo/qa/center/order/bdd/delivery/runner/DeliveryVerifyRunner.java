package com.anturo.qa.center.order.bdd.delivery.runner;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

/**
 * @Author Tesla
 * @Date 2025/04/29
 * @Description
 */

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
		dryRun = false,
		strict = true,
		stepNotifications = true,
		features = "classpath:features/order/delivery",
		glue = "com.anturo.qa.center.order.bdd.delivery.specs",
		plugin = {"pretty", "json:target/cucumber-testng.json", "html:target/cucumber-report"},
		tags = "not (@disable or @todo)")
public class DeliveryVerifyRunner {
}
