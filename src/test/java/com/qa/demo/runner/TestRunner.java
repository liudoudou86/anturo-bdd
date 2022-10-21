package com.qa.demo.runner;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

/**
 * @author Tesla Liu
 * @date 2022/10/18 17:51
 * 描述 BDD运行器
 */
@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        features = "classpath:features/demo",
        glue = "com.qa.demo.steps",
        plugin = {"pretty", "json:target/cucumber-testng.json", "html:target/cucumber-report"},
        tags = "not (@disable or @todo)"
)
public class TestRunner {
}