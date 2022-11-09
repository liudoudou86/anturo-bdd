package com.qa.demo.steps;

import com.db.MysqlUtils;
import io.cucumber.java8.En;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Tesla Liu
 * @date 2022/10/18 18:02
 * 描述 BDD 测试用例集
 */

@Slf4j
public class TestSteps implements En {

    public TestSteps() {
        this.testcase();
    }

    public void testcase() {
        Given("Visit Baidu website",()-> {
            deleteDate();
            insertDate();
            log.info("登录百度一下");
        });
        When("Input {string}",(String string)-> log.info("输入" + string));
        Then("Should be to see the {string}",(String string)-> log.info("可以看到" + string));
    }

    /**
     * 清理历史数据
     * */
    public void deleteDate() throws Exception {
        MysqlUtils.sqlScript("scripts/【清理历史数据】.sql");
        log.info("清理历史数据已完成");
    }

    /**
     * 清理历史数据
     * */
    public void insertDate() throws Exception {
        MysqlUtils.sqlScript("scripts/【仅占压超账期测试数据】.sql");
        log.info("添加测试数据已完成");
    }
}