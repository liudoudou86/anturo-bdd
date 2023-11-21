package com.qa.credit.occupy.runner;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

/**
 * @author Tesla Liu
 * @date 2022/11/09 17:49
 * 描述 正向占用
 */
@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        strict = true,
        stepNotifications = true,
        features = "classpath:features/credit/occupy",
        glue = "com.qa.credit.occupy.steps",
        plugin = {"pretty", "json:target/cucumber-testng.json", "html:target/cucumber-report"},
        tags = "@occupy"
)
public class CreditUseOccupyRunner {
}