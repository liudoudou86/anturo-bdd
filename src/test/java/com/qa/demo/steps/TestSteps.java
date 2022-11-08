package com.qa.demo.steps;

import com.db.DbUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.io.File;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Tesla Liu
 * @date 2022/10/18 18:02
 * 描述 BDD 测试用例
 */

@Slf4j
public class TestSteps {

    @Given("Visit Baidu website")
    public void visitBaiduWebsite() throws Exception {
        deleteDate();
        insertDate();
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

    /**
     * 清理历史数据
     * */
    public void deleteDate() throws Exception {
        String SQL_PATH = URLDecoder.decode(ClassLoader.getSystemResource("scripts/【清理历史数据】.sql").getPath(), "UTF-8");
        List<String> SQLS = Arrays.stream(FileUtils.readFileToString(new File(SQL_PATH), Charset.defaultCharset()).trim().split(";"))
                .map(String::trim).collect(Collectors.toList());
        List<Pair<String, Integer>> flags = new ArrayList<>();
        SQLS.forEach(sql -> flags.add(Pair.of(sql, DbUtils.myUpdate(sql))));
        log.info("清理历史数据已完成");
    }

    /**
     * 清理历史数据
     * */
    public void insertDate() throws Exception {
        String SQL_PATH = URLDecoder.decode(ClassLoader.getSystemResource("scripts/【添加测试数据】.sql").getPath(), "UTF-8");
        List<String> SQLS = Arrays.stream(FileUtils.readFileToString(new File(SQL_PATH), Charset.defaultCharset()).trim().split(";"))
                .map(String::trim).collect(Collectors.toList());
        List<Pair<String, Integer>> flags = new ArrayList<>();
        SQLS.forEach(sql -> flags.add(Pair.of(sql, DbUtils.myUpdate(sql))));
        log.info("添加测试数据已完成");
    }
}