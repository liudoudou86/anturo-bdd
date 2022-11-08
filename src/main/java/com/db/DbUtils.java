package com.db;

import java.io.IOException;
import java.sql.*;
import java.util.Properties;

/**
 * @author Tesla Liu
 * @date 2022/11/07 14:13
 * 描述 jdbc工具类
 */
public class DbUtils {
    public static Connection conn = null;
    public static PreparedStatement statement = null;
    public static ResultSet resultSet = null;
    public static String driver = null;
    public static String url = null;
    public static String user = null;
    public static  String pwd = null;
    /**
     * 加载配置文件  初始化
     */
    static {
        try {
            Properties properties = new Properties();
            // 加载配置文件   通过类加载器
            properties.load(DbUtils.class.getClassLoader().getResourceAsStream("mysql.properties"));
            driver=properties.getProperty("driver");
            url=properties.getProperty("url");
            user=properties.getProperty("username");
            pwd=properties.getProperty("password");
            // 加载驱动
            Class.forName(driver);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获得连接
     * @return 连接结果
     */
    public static Connection getConnection(){
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(url, user, pwd);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }


    /**
     * 获取操作数据库的对象
     * @param sql sql语句
     * @param ob  参数可变
     * @return PreparedStatement
     */
    public static PreparedStatement getStatement(String sql,Object...ob){
        //加载驱动
        try {
            //创建连接对象
            conn = getConnection();
            //创建执行对象
            statement = conn.prepareStatement(sql);
            //如果有参数  则添加参数
            if (ob.length>0){
                for(int i=0;i<ob.length;i++){
                    statement.setObject(i+1, ob[i]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statement;
    }

    /**
     * 查询
     * 返回查询的结果集合
     * @param sql sql语句
     * @param ob  可变参数
     * @return ResultSet结果集合
     */
    public static ResultSet mySelect(String sql,Object...ob){
        PreparedStatement statement = getStatement(sql, ob);
        try {
            resultSet = statement.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    /**
     * 对数据库的增、删、改
     * @param sql sql语句
     * @param ob  可变参数
     * @return 操作完成的sql语句数量
     */
    public static int myUpdate(String sql,Object...ob){
        PreparedStatement statement = getStatement(sql, ob);
        //执行成功的条数
        int count=0;
        try {
            count=statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    /**
     * 关闭连接  释放资源
     */
    public static void closeAll(){
        // 关闭结果集对象
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        // 关闭PreparedStatement对象
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        // 关闭Connection 对象
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}