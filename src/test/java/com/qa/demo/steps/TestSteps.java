package com.qa.demo.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Tesla Liu
 * @date 2022/10/18 18:02
 * 描述 BDD测试用例
 */

@Slf4j
public class TestSteps {

    @Given("Visit Baidu website")
    public void visitBaiduWebsite() {
        log.info("登录百度一下");
    }

    @When("Input {string}")
    public void input(String string) {
        log.info("输入" + string);
    }

    @Then("Should be to see the {string}")
    public void shouldBeToSeeThe(String string) {
        log.info("可以看到" + string);
    }

}
