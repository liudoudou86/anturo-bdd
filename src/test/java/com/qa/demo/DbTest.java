package com.qa.demo;

import com.db.DbUtils;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Tesla Liu
 * @date 2022/11/04 16:16
 * 描述 测试类
 */
public class DbTest {
    public static void main(String[] args) throws SQLException {
        DbUtils dbUtils = null;
        String sql = "select count(1) from credit_grade";
        ResultSet resultSet = dbUtils.mySelect(sql);
        while (resultSet.next()) System.out.println(resultSet.getString(1));
        dbUtils.closeAll();
    }
}
