package com.qa.data;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.qa.operate.request.CreditOrderOperation;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author Tesla Liu
 * @date 2022/11/15 12:40
 * 描述 B2B后台系统订单
 */
@SuppressWarnings("all")
@Setter
@Getter
@Slf4j
public class B2bBackOrder {
    /**
     *  【B2B订单内容】
     *  品种：芪参益气滴丸，单价：19.97，数量：3
     *  品种：藿香正气滴丸，单价：12.85，数量：3
     *  品种：藿香正气滴丸，单价：13.05，数量：3
     * */
    public static JSONArray backOrder01(String orderId) throws Exception {
        // 嵌套JSON数组
        JSONArray denomBOList = new JSONArray();
        String sku10 = "10000558";
        String sku20 = "10000437";
        String sku30 = "10000434";
        // 10订单行
        String id10 = MysqlData.selectOrderId(orderId, sku10);
        JSONObject json01 = new JSONObject();
        json01.put("rebateAmount", new BigDecimal("0").setScale(2, RoundingMode.HALF_UP).toString());
        json01.put("id",id10);
        json01.put("productDesc", "芪参益气滴丸");
        json01.put("productSku", sku10);
        json01.put("itemId", "10");
        json01.put("crmVarietyId", "");
        json01.put("crmSkuId", "");
        json01.put("crmVarietyName", "");
        json01.put("monthRegionRemain", CreditOrderOperation.queryThisUserRemainRebateAmount());
        json01.put("rebateMoneyBeforeUse", "");
        json01.put("rebateMoneyAfterUse", "");
        json01.put("rebateRatio", "0.00%");
        denomBOList.add(json01);
        // 20订单行
        String id20 = MysqlData.selectOrderId(orderId, sku20);
        JSONObject json02 = new JSONObject();
        json02.put("rebateAmount", new BigDecimal("0").setScale(2, RoundingMode.HALF_UP).toString());
        json02.put("id",id20);
        json02.put("productDesc", "藿香正气滴丸");
        json02.put("productSku", sku20);
        json02.put("itemId", "20");
        json02.put("crmVarietyId", "");
        json02.put("crmSkuId", "");
        json02.put("crmVarietyName", "");
        json02.put("monthRegionRemain", CreditOrderOperation.queryThisUserRemainRebateAmount());
        json02.put("rebateMoneyBeforeUse", "");
        json02.put("rebateMoneyAfterUse", "");
        json02.put("rebateRatio", "0.00%");
        denomBOList.add(json02);
        // 30订单行
        String id30 = MysqlData.selectOrderId(orderId, sku30);
        JSONObject json03 = new JSONObject();
        json03.put("rebateAmount", new BigDecimal("0").setScale(2, RoundingMode.HALF_UP).toString());
        json03.put("id",id30);
        json03.put("productDesc", "藿香正气滴丸");
        json03.put("productSku", sku30);
        json03.put("itemId", "30");
        json03.put("crmVarietyId", "");
        json03.put("crmSkuId", "");
        json03.put("crmVarietyName", "");
        json03.put("monthRegionRemain", CreditOrderOperation.queryThisUserRemainRebateAmount());
        json03.put("rebateMoneyBeforeUse", "");
        json03.put("rebateMoneyAfterUse", "");
        json03.put("rebateRatio", "0.00%");
        denomBOList.add(json03);
        return denomBOList;
    }

    /**
     *  【B2B订单内容】
     *  品种：柴胡滴丸，单价：10.33，数量：3
     *  品种：穿心莲内酯滴丸，单价：11.11，数量：3
     *  品种：大山楂丸，单价：18.28，数量：1
     * */
    public static JSONArray backOrder02(String orderId) throws Exception {
        // 嵌套JSON数组
        JSONArray denomBOList = new JSONArray();
        String sku10 = "10000266";
        String sku20 = "10000286";
        String sku30 = "10000302";
        // 10订单行
        String id10 = MysqlData.selectOrderId(orderId, sku10);
        JSONObject json01 = new JSONObject();
        json01.put("rebateAmount", new BigDecimal("0").setScale(2, RoundingMode.HALF_UP).toString());
        json01.put("id",id10);
        json01.put("productDesc", "柴胡滴丸");
        json01.put("productSku", sku10);
        json01.put("itemId", "10");
        json01.put("crmVarietyId", "");
        json01.put("crmSkuId", "");
        json01.put("crmVarietyName", "");
        json01.put("monthRegionRemain", CreditOrderOperation.queryThisUserRemainRebateAmount());
        json01.put("rebateMoneyBeforeUse", "");
        json01.put("rebateMoneyAfterUse", "");
        json01.put("rebateRatio", "2.25%");
        denomBOList.add(json01);
        // 20订单行
        String id20 = MysqlData.selectOrderId(orderId, sku20);
        JSONObject json02 = new JSONObject();
        json02.put("rebateAmount", new BigDecimal("0").setScale(2, RoundingMode.HALF_UP).toString());
        json02.put("id",id20);
        json02.put("productDesc", "穿心莲内酯滴丸");
        json02.put("productSku", sku20);
        json02.put("itemId", "20");
        json02.put("crmVarietyId", "");
        json02.put("crmSkuId", "");
        json02.put("crmVarietyName", "");
        json02.put("monthRegionRemain", CreditOrderOperation.queryThisUserRemainRebateAmount());
        json02.put("rebateMoneyBeforeUse", "");
        json02.put("rebateMoneyAfterUse", "");
        json02.put("rebateRatio", "6.57%");
        denomBOList.add(json02);
        // 30订单行
        String id30 = MysqlData.selectOrderId(orderId, sku30);
        JSONObject json03 = new JSONObject();
        json03.put("rebateAmount", new BigDecimal("0").setScale(2, RoundingMode.HALF_UP).toString());
        json03.put("id",id30);
        json03.put("productDesc", "大山楂丸");
        json03.put("productSku", sku30);
        json03.put("itemId", "30");
        json03.put("crmVarietyId", "");
        json03.put("crmSkuId", "");
        json03.put("crmVarietyName", "");
        json03.put("monthRegionRemain", CreditOrderOperation.queryThisUserRemainRebateAmount());
        json03.put("rebateMoneyBeforeUse", "");
        json03.put("rebateMoneyAfterUse", "");
        json03.put("rebateRatio", "0.00%");
        denomBOList.add(json03);
        return denomBOList;
    }

    /**
     *  【B2B订单内容】
     *  品种：柴胡滴丸，单价：10.33，数量：3
     *  品种：穿心莲内酯滴丸，单价：11.11，数量：3
     * */
    public static JSONArray backOrder03(String orderId) throws Exception {
        // 嵌套JSON数组
        JSONArray denomBOList = new JSONArray();
        String sku10 = "10000266";
        String sku20 = "10000286";
        // 10订单行
        String id10 = MysqlData.selectOrderId(orderId, sku10);
        JSONObject json01 = new JSONObject();
        json01.put("rebateAmount", new BigDecimal("0").setScale(2, RoundingMode.HALF_UP).toString());
        json01.put("id",id10);
        json01.put("productDesc", "柴胡滴丸");
        json01.put("productSku", sku10);
        json01.put("itemId", "10");
        json01.put("crmVarietyId", "");
        json01.put("crmSkuId", "");
        json01.put("crmVarietyName", "");
        json01.put("monthRegionRemain", CreditOrderOperation.queryThisUserRemainRebateAmount());
        json01.put("rebateMoneyBeforeUse", "");
        json01.put("rebateMoneyAfterUse", "");
        json01.put("rebateRatio", "2.25%");
        denomBOList.add(json01);
        // 20订单行
        String id20 = MysqlData.selectOrderId(orderId, sku20);
        JSONObject json02 = new JSONObject();
        json02.put("rebateAmount", new BigDecimal("0").setScale(2, RoundingMode.HALF_UP).toString());
        json02.put("id",id20);
        json02.put("productDesc", "穿心莲内酯滴丸");
        json02.put("productSku", sku20);
        json02.put("itemId", "20");
        json02.put("crmVarietyId", "");
        json02.put("crmSkuId", "");
        json02.put("crmVarietyName", "");
        json02.put("monthRegionRemain", CreditOrderOperation.queryThisUserRemainRebateAmount());
        json02.put("rebateMoneyBeforeUse", "");
        json02.put("rebateMoneyAfterUse", "");
        json02.put("rebateRatio", "6.57%");
        denomBOList.add(json02);
        return denomBOList;
    }

    /**
     *  【B2B订单内容】
     *  品种：柴胡滴丸，单价：11.08，数量：3
     *  品种：穿心莲内酯滴丸，单价：13.30，数量：3
     *  品种：藿香正气滴丸，单价：13.05：，数量：3
     * */
    public static JSONArray backOrder04(String orderId) throws Exception {
        // 嵌套JSON数组
        JSONArray denomBOList = new JSONArray();
        String sku10 = "10000266";
        String sku20 = "10000286";
        String sku30 = "10000434";
        // 10订单行
        String id10 = MysqlData.selectOrderId(orderId, sku10);
        JSONObject json01 = new JSONObject();
        json01.put("rebateAmount", new BigDecimal("0").setScale(2, RoundingMode.HALF_UP).toString());
        json01.put("id",id10);
        json01.put("productDesc", "柴胡滴丸");
        json01.put("productSku", sku10);
        json01.put("itemId", "10");
        json01.put("crmVarietyId", "");
        json01.put("crmSkuId", "");
        json01.put("crmVarietyName", "");
        json01.put("monthRegionRemain", CreditOrderOperation.queryThisUserRemainRebateAmount());
        json01.put("rebateMoneyBeforeUse", "");
        json01.put("rebateMoneyAfterUse", "");
        json01.put("rebateRatio", "0.00%");
        denomBOList.add(json01);
        // 20订单行
        String id20 = MysqlData.selectOrderId(orderId, sku20);
        JSONObject json02 = new JSONObject();
        json02.put("rebateAmount", new BigDecimal("0").setScale(2, RoundingMode.HALF_UP).toString());
        json02.put("id",id20);
        json02.put("productDesc", "穿心莲内酯滴丸");
        json02.put("productSku", sku20);
        json02.put("itemId", "20");
        json02.put("crmVarietyId", "");
        json02.put("crmSkuId", "");
        json02.put("crmVarietyName", "");
        json02.put("monthRegionRemain", CreditOrderOperation.queryThisUserRemainRebateAmount());
        json02.put("rebateMoneyBeforeUse", "");
        json02.put("rebateMoneyAfterUse", "");
        json02.put("rebateRatio", "0.00%");
        denomBOList.add(json02);
        // 30订单行
        String id30 = MysqlData.selectOrderId(orderId, sku30);
        JSONObject json03 = new JSONObject();
        json03.put("rebateAmount", new BigDecimal("0").setScale(2, RoundingMode.HALF_UP).toString());
        json03.put("id",id30);
        json03.put("productDesc", "藿香正气滴丸");
        json03.put("productSku", sku30);
        json03.put("itemId", "30");
        json03.put("crmVarietyId", "");
        json03.put("crmSkuId", "");
        json03.put("crmVarietyName", "");
        json03.put("monthRegionRemain", CreditOrderOperation.queryThisUserRemainRebateAmount());
        json03.put("rebateMoneyBeforeUse", "");
        json03.put("rebateMoneyAfterUse", "");
        json03.put("rebateRatio", "0.00%");
        denomBOList.add(json03);
        return denomBOList;
    }

    /**
     *  【订单发货执行】
     * */
    public static JSONArray orderItemInfo(String batchNum, String itemId) throws Exception {
        // 嵌套JSON数组
        JSONArray orderInfo = new JSONArray();
        JSONObject json = new JSONObject();
        json.put("batchId","202302");
        json.put("batchNum",batchNum);
        json.put("itemId",itemId);
        json.put("repertoryNo","3026");
        orderInfo.add(json);
        return orderInfo;
    }

    /**
     *  【B2B订单内容】
     *  品种：柴胡滴丸，单价：10.33，数量：2
     * */
    public static JSONArray backReturnOrder01(String returnOrderId) throws Exception {
        // 嵌套JSON数组
        JSONArray denomBOList = new JSONArray();
        // 10订单行
        String id10 = MysqlData.selectOrderReturnItemId(returnOrderId,"10");
        JSONObject json01 = new JSONObject();
        json01.put("id",id10);
        json01.put("checkNum", "2.0");
        denomBOList.add(json01);
        return denomBOList;
    }

    /**
     *  【B2B订单申请回款】
     * */
    public static JSONArray returnMoneyEntityBOList(String yuhuankuan,String balance, String orderId, String invoice, String itemid, String vbeln) {
        // 嵌套JSON数组
        JSONArray denomBOList = new JSONArray();
        JSONObject json01 = new JSONObject();
        json01.put("yuhuankuan", yuhuankuan);
        json01.put("id", 1);
        json01.put("balance", balance);
        json01.put("back", new BigDecimal("0").setScale(2, RoundingMode.HALF_UP).toString());
        json01.put("orderId", orderId);
        json01.put("invoice", invoice);
        json01.put("sku", "10000266");
        json01.put("productName", "柴胡滴丸");
        json01.put("spec", "0.551g*6袋/盒");
        json01.put("bottledGoods", "否");
        json01.put("bottledyeararea", "普通");
        json01.put("flag", "b2b");
        json01.put("itemid", itemid);
        json01.put("vbeln", vbeln);
        json01.put("lineNo", orderId + "10000266" + invoice);
        denomBOList.add(json01);
        return denomBOList;
    }

    /**
     *  【B2B订单申请回款】
     * */
    public static JSONArray advanceChargeEntityList(String userName, String payAmount, String timestamp) {
        // 嵌套JSON数组
        JSONArray denomBOList = new JSONArray();
        JSONObject json01 = new JSONObject();
        json01.put("returnMode","1");
        json01.put("userName", userName);
        json01.put("payAmount", payAmount);
        json01.put("billNumber", timestamp);
        json01.put("ticketBank", timestamp);
        json01.put("endorseUnit", timestamp);
        json01.put("methodName", "现金");
        json01.put("ticketDate", timestamp);
        json01.put("ticketUnit", timestamp);
        json01.put("dueDate", timestamp);
        json01.put("returnDate", timestamp);
        json01.put("ledgerAccount", "1001010000");
        json01.put("returnCompany", "1017");
        denomBOList.add(json01);
        return denomBOList;
    }

}
