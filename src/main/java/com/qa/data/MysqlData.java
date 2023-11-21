package com.qa.data;

import com.qa.tool.MysqlUtil;
import io.qameta.allure.Description;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Description(value = "信用测试数据清理")
    public void deleteCreditData(String sqlpath) {
        MysqlUtil.sqlScript("DataBase.Credit", sqlpath);
        log.info("信用测试数据清理已完成");
    }

    @Description(value = "B2B测试数据清理")
    public void deleteB2bData(String sqlpath) {
        MysqlUtil.sqlScript("DataBase.B2B", sqlpath);
        log.info("B2B测试数据清理已完成");
    }

    @Description(value = "测试数据添加")
    public void inserCreditData(String sqlpath) {
        MysqlUtil.sqlScript("DataBase.Credit", sqlpath);
        log.info("信用测试数据添加已完成");
    }

    @Description(value = "更新客户超账期天数")
    public void updateEnterpriseGrade(String overduePeriodDays, String creditControlDimension, String creditGradeId) {
        String sql = "UPDATE common_credit_policy SET OVERDUE_PERIOD_DAYS = " + overduePeriodDays + ", CREDIT_CONTROL_DIMENSION = '" + creditControlDimension + "' WHERE CREDIT_GRADE_ID = " + creditGradeId + ";";
        MysqlUtil.sqlUpdate("DataBase.Credit", sql);
        log.info("超账期已配置完成");
    }

    @Description(value = "更新品种订单行号")
    public void updateOrderlines(String line, String orderId, String sku) {
        String sql = "UPDATE orderlines SET ITEM_ID = " + line + " WHERE ORDER_ID = '" + orderId + "' AND PRODUCT_SKU = '" + sku + "';";
        MysqlUtil.sqlUpdate("DataBase.B2B", sql);
        log.info("订单行已更新");
    }

    @Description(value = "查询品种订单行ID")
    public static String selectOrderId(String orderId, String sku) throws SQLException {
        String sql = "SELECT ID FROM orderlines WHERE ORDER_ID = '" + orderId + "' AND PRODUCT_SKU = '" + sku + "';";
        ResultSet resultSet = MysqlUtil.sqlSelect("DataBase.B2B", sql);
        String result = null;
        while (resultSet.next()) {
            result = resultSet.getString("ID");
        }
        return result;
    }

    @Description(value = "查询临时信用申请")
    public static List<Map<String, Object>> selectTemporaryCredit(String customerEntCode) throws SQLException {
        List<Map<String, Object>> sqlResult = new ArrayList<Map<String,Object>>();
        String sql = "SELECT TYPE, CREDIT_LIMIT, OCCUPIED_AMOUNT, FROZEN_AMOUNT, TRADE_NO FROM temporary_credit WHERE CUSTOMER_ENT_CODE = '" + customerEntCode + "' AND SALE_ORG_CODE = '1000006570'AND EFFECTIVE_STATE = '1';";
        ResultSet resultSet = MysqlUtil.sqlSelect("DataBase.Credit", sql);
        // 提取键名的元数据
        ResultSetMetaData md = resultSet.getMetaData();
        // 提取行数
        int columnCount = md.getColumnCount();
        while (resultSet.next()) {
            Map<String, Object> rowData = new HashMap<String, Object>();
            for (int i = 1; i <= columnCount; i++) {
                rowData.put(md.getColumnName(i), resultSet.getObject(i));
            }
            sqlResult.add(rowData);
        }
        return sqlResult;
    }

    @Description(value = "查询信用流水")
    public static List<Map<String, Object>> selectCreditJournal(String customerEntCode) throws SQLException {
        List<Map<String, Object>> sqlResult = new ArrayList<Map<String,Object>>();
        String sql = "SELECT CREDIT_LIMIT, OCCUPIED_AMOUNT, FROZEN_AMOUNT, OCCUPIED_CHANGE_AMOUNT, CREDIT_TRADE_NO FROM credit_journal WHERE CUSTOMER_ENT_CODE = '" + customerEntCode + "';";
        ResultSet resultSet = MysqlUtil.sqlSelect("DataBase.Credit", sql);
        // 提取键名的元数据
        ResultSetMetaData md = resultSet.getMetaData();
        // 提取行数
        int columnCount = md.getColumnCount();
        while (resultSet.next()) {
            Map<String, Object> rowData = new HashMap<String, Object>();
            for (int i = 1; i <= columnCount; i++) {
                rowData.put(md.getColumnName(i), resultSet.getObject(i));
            }
            sqlResult.add(rowData);
        }
        return sqlResult;
    }

    @Description(value = "更新临时信用月结转日期")
    public void updateExecutionMonth(String date, String customerEntCode) {
        String sql = "UPDATE temporary_credit SET EXECUTION_MONTH = '" + date + "' WHERE CUSTOMER_ENT_CODE = '" + customerEntCode + "';";
        MysqlUtil.sqlUpdate("DataBase.Credit", sql);
        log.info("月结转日期已更新");
    }

    @Description(value = "更新导入年度资信生效日期")
    public void updateAnnualEffectuate(String date, String customerEntCode, String tradeNo) {
        String sql = "UPDATE annual_credit_detail_adjustment_apply SET EXECUTION_DATE = '" + date + "' WHERE CUSTOMER_ENT_CODE = '" + customerEntCode + "' AND TRADE_NO = '" + tradeNo + "';";
        log.info(sql);
        MysqlUtil.sqlUpdate("DataBase.Credit", sql);
        log.info("年度资信生效日期已更新");
    }

    @Description(value = "人工改造为历史订单")
    public void historyOrder(String orderId) {
        String sql_operate = "DELETE FROM order_credit_operate WHERE ORDER_ID = '" + orderId + "';";
        MysqlUtil.sqlUpdate("DataBase.B2B", sql_operate);
        String sql_journal = "DELETE FROM order_credit_operate_journal WHERE ORDER_ID = '" + orderId + "';";
        MysqlUtil.sqlUpdate("DataBase.B2B", sql_journal);
        String sql_credit = "DELETE FROM credit_release_progress WHERE CHANGE_TRADE_NO = '" + orderId + "';";
        MysqlUtil.sqlUpdate("DataBase.Credit", sql_credit);
    }

    @Description(value = "查询订单ECCID")
    public static String selectOrderEccid(String orderId) throws SQLException {
        String sql = "SELECT ERP_ORDER_ID FROM orders WHERE ORDER_ID = '" + orderId + "';";
        ResultSet resultSet = MysqlUtil.sqlSelect("DataBase.B2B", sql);
        String result = null;
        while (resultSet.next()) {
            result = resultSet.getString("ERP_ORDER_ID");
        }
        return result;
    }

    @Description(value = "查询订单退货订单号")
    public static String selectOrderReturnId(String orderId) throws SQLException {
        String sql = "SELECT ORDER_RETURN_ID FROM order_return_goods_info WHERE ORDER_ID = '" + orderId + "';";
        ResultSet resultSet = MysqlUtil.sqlSelect("DataBase.B2B", sql);
        String result = null;
        while (resultSet.next()) {
            result = resultSet.getString("ORDER_RETURN_ID");
        }
        return result;
    }

    @Description(value = "查询订单退货ID号")
    public static String selectOrderReturnItemId(String returnOrderId, String itemId) throws SQLException {
        String sql = "SELECT ID FROM order_return_goods_item WHERE ORDER_RETURN_ID = '" + returnOrderId + "' AND ITEM_ID = '" + itemId + "';";;
        ResultSet resultSet = MysqlUtil.sqlSelect("DataBase.B2B", sql);
        String result = null;
        while (resultSet.next()) {
            result = resultSet.getString("ID");
        }
        return result;
    }

    @Description(value = "查询待审核回款ID号")
    public static String selectCollectMoneyId() throws SQLException {
        String sql = "SELECT ID FROM return_money WHERE APPLY_STATUS = '0' AND APPLY_USER = 'admin' ORDER BY APPLY_TIME DESC LIMIT 1;";
        ResultSet resultSet = MysqlUtil.sqlSelect("DataBase.B2B", sql);
        String result = null;
        while (resultSet.next()) {
            result = resultSet.getString("ID");
        }
        return result;
    }

    @Description(value = "查询审核通过回款APPLYID号")
    public static String selectCollectMoneyApplyId() throws SQLException {
        String sql = "SELECT APPLY_ID FROM return_money WHERE APPLY_STATUS = '1' AND APPLY_USER = 'admin' ORDER BY APPLY_TIME DESC LIMIT 1;";
        ResultSet resultSet = MysqlUtil.sqlSelect("DataBase.B2B", sql);
        String result = null;
        while (resultSet.next()) {
            result = resultSet.getString("APPLY_ID");
        }
        return result;
    }

    @Description(value = "查询待审核回款ADVANCEID号")
    public static String selectCollectMoneyAdvanceId(String applyId) throws SQLException {
        String sql = "SELECT ID FROM advance_charge WHERE RETURN_ID = '"+ applyId + "';";
        ResultSet resultSet = MysqlUtil.sqlSelect("DataBase.B2B", sql);
        String result = null;
        while (resultSet.next()) {
            result = resultSet.getString("ID");
        }
        return result;
    }

}
