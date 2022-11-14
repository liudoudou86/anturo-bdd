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
        MysqlUtils.sqlScript("creditdb.properties", sqlpath);
        log.info("测试数据清理已完成");
    }

    @Step(value = "测试数据添加")
    public static void insertData(String sqlpath) {
        MysqlUtils.sqlScript("creditdb.properties", sqlpath);
        log.info("测试数据添加已完成");
    }

    @Step(value = "更新客户账期")
    public static void updateEnterpriseGrade(String overduePeriodDays, String creditControlDimension, String creditGradeId) {
        String sql = "UPDATE `tasly-center-credit`.common_credit_policy SET OVERDUE_PERIOD_DAYS = " + overduePeriodDays + ", CREDIT_CONTROL_DIMENSION = '" + creditControlDimension + "' WHERE CREDIT_GRADE_ID = " + creditGradeId + ";";
        MysqlUtils.sqlUpdate("creditdb.properties", sql);
        log.info("超账期已配置完成");
    }

    @Step(value = "更新订单行")
    public static void updateOrderlines(String line, String orderId, String sku) {
        String sql = "UPDATE `b2bstg`.orderlines SET ITEM_ID = " + line + " WHERE ORDER_ID = '" + orderId + "' AND PRODUCT_SKU = '" + sku + "';";
        MysqlUtils.sqlUpdate("b2bdb.properties", sql);
        log.info("订单行已更新");
    }

    @Step(value = "查询ID")
    public static void selectOrderId(String orderId, String sku) {
        String sql = "SELECT ID FROM `b2bstg`.orderlines WHERE ORDER_ID = '" + orderId + "' AND PRODUCT_SKU = '" + sku + "';";
        log.info(sql);
        MysqlUtils.sqlUpdate("b2bdb.properties", sql);
    }

}
