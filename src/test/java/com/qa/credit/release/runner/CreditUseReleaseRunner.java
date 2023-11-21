package com.qa.credit.release.runner;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

/**
 * @author Tesla Liu
 * @date 2022/12/02 12:38
 * 描述
 */
@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        strict = true,
        stepNotifications = true,
        features = "classpath:features/credit/release",
        glue = "com.qa.credit.release.steps",
        plugin = {"pretty", "json:target/cucumber-testng.json", "html:target/cucumber-report"},
        tags = "@release"
)
public class CreditUseReleaseRunner {
}
