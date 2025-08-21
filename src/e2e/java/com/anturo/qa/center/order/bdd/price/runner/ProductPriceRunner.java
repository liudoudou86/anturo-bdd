package com.anturo.qa.center.order.bdd.price.runner;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

/**
 * @Author Tesla
 * @Date 2024/07/29
 * @Description
 */

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
		dryRun = false,
		strict = true,
		stepNotifications = true,
		features = "classpath:features/order/price",
		glue = "com.anturo.qa.center.order.bdd.price.specs",
		plugin = {"pretty", "json:target/cucumber-testng.json", "html:target/cucumber-report"},
		tags = "not (@disable or @todo)")
public class ProductPriceRunner {
}
