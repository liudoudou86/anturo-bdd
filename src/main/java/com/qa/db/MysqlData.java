package com.qa.db;

import io.qameta.allure.Step;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Tesla Liu
 * @date 2022/11/10 14:18
 * 描述 封装数据库操作
 */
@SuppressWarnings("all")
@Setter
@Getter
@Slf4j
public class MysqlData {

    @Step(value = "测试数据清理")
    public static void deleteData(String sqlpath) {
        MysqlUtils.sqlScript(sqlpath);
        log.info("测试数据清理已完成");
    }

    @Step(value = "测试数据添加")
    public static void insertData(String sqlpath) {
        MysqlUtils.sqlScript(sqlpath);
        log.info("测试数据添加已完成");
    }

    @Step(value = "测试数据更新")
    public static void updateData(String days, String dimension, String gradeId) {
        String sql = "UPDATE `tasly-center-credit`.common_credit_policy SET OVERDUE_PERIOD_DAYS = " + days + ", CREDIT_CONTROL_DIMENSION = '" + dimension + "' WHERE CREDIT_GRADE_ID = " + gradeId + ";";
        MysqlUtils.sqlUpdate(sql);
        log.info("测试数据更新已完成");
    }

}
