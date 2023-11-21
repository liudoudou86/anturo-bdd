package com.qa.credit.bottled.runner;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

/**
 * @author Tesla Liu
 * @date 2022/12/21 09:28
 * 描述 铺底货
 */
@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        strict = true,
        stepNotifications = true,
        features = "classpath:features/credit/bottled",
        glue = "com.qa.credit.bottled.steps",
        plugin = {"pretty", "json:target/cucumber-testng.json", "html:target/cucumber-report"},
        tags = "@bottled"
)
public class CreditUseBottledRunner {
}
