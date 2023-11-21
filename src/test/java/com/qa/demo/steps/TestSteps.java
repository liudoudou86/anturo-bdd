package com.qa.demo.steps;

import io.cucumber.java8.En;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Tesla Liu
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
        Given("前提条件",()-> {
            log.info("前提条件");
        });
        When("当时输入条件时",()-> log.info("当时输入条件时"));
        Then("应该得到的结果",()-> log.info("应该得到的结果"));
    }
}