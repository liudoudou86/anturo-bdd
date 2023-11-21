package com.qa.data;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.qa.tool.TimeUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;

/**
 * @author Tesla Liu
 * @date 2022/11/24 11:24
 * 描述
 */
@SuppressWarnings("all")
@Setter
@Getter
@Slf4j
public class B2bRefundOrder {
    /**
     *  【B2B退货订单内容】
     *  品种：柴胡滴丸，单价：10.33，数量：2
     * */
    public static JSONArray BACKRETURNORDER01(String orderId) throws SQLException {
        // 嵌套JSON数组
        JSONArray orderItems = new JSONArray();
        // 构建JSON对象
        JSONObject extFields = new JSONObject();
        String sku10 = "10000266";
        // 10订单行
        JSONObject json01 = new JSONObject();
        json01.put("amount", 20.66);
        json01.put("date", TimeUtil.currentTimeStyle01());
        json01.put("extFields", extFields);
        json01.put("instanceId", 0);
        json01.put("orderItemNo", "10");
        json01.put("orderNo", orderId);
        json01.put("originalOrderItemNo", "10");
        json01.put("originalOrderNo", orderId);
        json01.put("productSkuSapCode", sku10);
        json01.put("tenantId", 0);
        json01.put("vbeln", "0");
        orderItems.add(json01);
        return orderItems;
    }

    /**
     *  【B2B退货冲销订单内容】
     *  品种：柴胡滴丸，单价：10.33，数量：1
     * */
    public static JSONArray BACKRETURNWRITEOFFORDER01(String orderId) throws SQLException {
        // 嵌套JSON数组
        JSONArray orderItems = new JSONArray();
        // 构建JSON对象
        JSONObject extFields = new JSONObject();
        String sku10 = "10000266";
        // 10订单行
        JSONObject json01 = new JSONObject();
        json01.put("amount", 10.33);
        json01.put("date", TimeUtil.currentTimeStyle01());
        json01.put("extFields", extFields);
        json01.put("instanceId", 0);
        json01.put("orderItemNo", "10");
        json01.put("orderNo", orderId);
        json01.put("originalOrderItemNo", "10");
        json01.put("originalOrderNo", orderId);
        json01.put("productSkuSapCode", sku10);
        json01.put("tenantId", 0);
        json01.put("vbeln", "0");
        orderItems.add(json01);
        return orderItems;
    }

    /**
     *  【B2B退货订单内容】
     *  品种：藿香正气滴丸，单价：12.85，数量：2
     *  品种：藿香正气滴丸，单价：13.05，数量：2
     * */
    public static JSONArray BACKRETURNORDER02(String orderId) throws SQLException {
        // 嵌套JSON数组
        JSONArray orderItems = new JSONArray();
        // 构建JSON对象
        JSONObject extFields = new JSONObject();
        String sku10 = "10000437";
        String sku20 = "10000434";
        // 10订单行
        JSONObject json01 = new JSONObject();
        json01.put("amount", 25.70);
        json01.put("date", TimeUtil.currentTimeStyle01());
        json01.put("extFields", extFields);
        json01.put("instanceId", 0);
        json01.put("orderItemNo", "20");
        json01.put("orderNo", orderId);
        json01.put("originalOrderItemNo", "20");
        json01.put("originalOrderNo", orderId);
        json01.put("productSkuSapCode", sku10);
        json01.put("tenantId", 0);
        json01.put("vbeln", "0");
        orderItems.add(json01);
        // 20订单行
        JSONObject json02 = new JSONObject();
        json02.put("amount", 26.10);
        json02.put("date", TimeUtil.currentTimeStyle01());
        json02.put("extFields", extFields);
        json02.put("instanceId", 0);
        json02.put("orderItemNo", "30");
        json02.put("orderNo", orderId);
        json02.put("originalOrderItemNo", "30");
        json02.put("originalOrderNo", orderId);
        json02.put("productSkuSapCode", sku20);
        json02.put("tenantId", 0);
        json02.put("vbeln", "0");
        orderItems.add(json02);
        return orderItems;
    }

    /**
     *  【B2B退货冲销订单内容】
     *  品种：藿香正气滴丸，单价：12.85，数量：1
     *  品种：藿香正气滴丸，单价：13.05，数量：1
     * */
    public static JSONArray BACKRETURNWRITEOFFORDER02(String orderId) throws SQLException {
        // 嵌套JSON数组
        JSONArray orderItems = new JSONArray();
        // 构建JSON对象
        JSONObject extFields = new JSONObject();
        String sku10 = "10000437";
        String sku20 = "10000434";
        // 10订单行
        JSONObject json01 = new JSONObject();
        json01.put("amount", 12.85);
        json01.put("date", TimeUtil.currentTimeStyle01());
        json01.put("extFields", extFields);
        json01.put("instanceId", 0);
        json01.put("orderItemNo", "20");
        json01.put("orderNo", orderId);
        json01.put("originalOrderItemNo", "20");
        json01.put("originalOrderNo", orderId);
        json01.put("productSkuSapCode", sku10);
        json01.put("tenantId", 0);
        json01.put("vbeln", "0");
        orderItems.add(json01);
        // 20订单行
        JSONObject json02 = new JSONObject();
        json02.put("amount", 13.05);
        json02.put("date", TimeUtil.currentTimeStyle01());
        json02.put("extFields", extFields);
        json02.put("instanceId", 0);
        json02.put("orderItemNo", "30");
        json02.put("orderNo", orderId);
        json02.put("originalOrderItemNo", "30");
        json02.put("originalOrderNo", orderId);
        json02.put("productSkuSapCode", sku20);
        json02.put("tenantId", 0);
        json02.put("vbeln", "0");
        orderItems.add(json02);
        return orderItems;
    }

}
