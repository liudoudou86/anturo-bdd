package com.qa.demo.steps;

import com.db.MysqlUtils;
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
        Given("访问百度一下网站",()-> log.info("登录百度一下"));
        When("输入搜索词",()-> log.info("输入BDD"));
        Then("应该可以看到搜索词",()-> log.info("可以看到BDD"));
    }

    /**
     * 清理历史数据
     * */
    public void deleteData() {
        MysqlUtils.sqlScript("scripts/【清理历史数据】.sql");
        log.info("测试数据清理已完成");
    }

    /**
     * 添加测试数据
     * */
    public void insertData() {
        MysqlUtils.sqlScript("scripts/【仅年度资信无临时信用】.sql");
        log.info("测试数据添加已完成");
    }

    /**
     * 更新测试数据
     * */
    public void updateData() {
        MysqlUtils.sqlUpdate("UPDATE `tasly-center-credit`.customer_credit_grade SET CREDIT_GRADE_ID = 1 WHERE CUSTOMER_ENT_ZT_CODE = '1000002261';");
        log.info("测试数据更新已完成");
    }
}