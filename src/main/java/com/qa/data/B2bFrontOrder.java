package com.qa.data;

/**
 * @author Tesla Liu
 * @date 2022/11/10 15:59
 * 描述 B2B前台系统订单
 */
public class B2bFrontOrder {
    /**
     *  【B2B订单内容】
     *  品种：芪参益气滴丸，单价：19.97，数量：3
     *  品种：藿香正气滴丸，单价：12.85，数量：3
     *  品种：藿香正气滴丸，单价：13.05，数量：3
     * */
    public static final String FRONTORDER01 = "{\"buyerMessage\":\"\",\"buyerPayType\":\"0\",\"buyerPayMethod\":\"支付宝\",\"orderLines\":[{\"shoppingCartPkId\":4883,\"productSku\":\"10000558\",\"productQuantity\":\"3\",\"channelCode\":\"12\"},{\"shoppingCartPkId\":4884,\"productSku\":\"10000434\",\"productQuantity\":\"3\",\"channelCode\":\"12\"},{\"shoppingCartPkId\":4885,\"productSku\":\"10000437\",\"productQuantity\":\"3\",\"channelCode\":\"12\"}],\"couponIdList\":[],\"score\":0,\"freight\":0,\"express_code\":\"\",\"delivery_service\":\"\",\"receiverAddress\":\"萧山区萧山经济技术开发区桥南区块春水路2号\",\"parentCode\":\"0040002654\",\"receiverName\":\"马宇飞\",\"contactNumber\":\"18357012576\",\"province\":\"3\",\"city\":\"35\",\"county\":\"408\",\"innerSource\":1}";

    /**
     *  【B2B订单内容】
     *  品种：柴胡滴丸，单价：11.08，数量：3
     *  品种：穿心莲内酯滴丸，单价：13.30，数量：3
     *  品种：大山楂丸，单价：18.28，数量：1
     * */
    public static final String FRONTORDER02 = "{\"buyerMessage\":\"\",\"buyerPayType\":\"0\",\"buyerPayMethod\":\"支付宝\",\"orderLines\":[{\"shoppingCartPkId\":4898,\"productSku\":\"10000286\",\"productQuantity\":\"3\",\"channelCode\":\"12\"},{\"shoppingCartPkId\":4899,\"productSku\":\"10000266\",\"productQuantity\":\"3\",\"channelCode\":\"12\"},{\"shoppingCartPkId\":4897,\"productSku\":\"10000302\",\"productQuantity\":\"1\",\"channelCode\":\"12\"}],\"couponIdList\":[],\"score\":0,\"freight\":0,\"express_code\":\"\",\"delivery_service\":\"\",\"receiverAddress\":\"杭州市上城区雷霆路60号长城大厦11、12楼\",\"parentCode\":\"0010511959\",\"receiverName\":\"马宇飞\",\"contactNumber\":\"18357012576\",\"province\":\"3\",\"city\":\"35\",\"county\":\"408\",\"innerSource\":1}";

    /**
     *  【B2B订单内容】
     *  品种：柴胡滴丸，单价：11.08，数量：3
     *  品种：穿心莲内酯滴丸，单价：13.30，数量：3
     * */
    public static final String FRONTORDER03 = "{\"buyerMessage\":\"\",\"buyerPayType\":\"0\",\"buyerPayMethod\":\"支付宝\",\"orderLines\":[{\"shoppingCartPkId\":4984,\"productSku\":\"10000286\",\"productQuantity\":\"3\",\"channelCode\":\"12\"},{\"shoppingCartPkId\":4985,\"productSku\":\"10000266\",\"productQuantity\":\"3\",\"channelCode\":\"12\"}],\"couponIdList\":[],\"score\":0,\"freight\":0,\"express_code\":\"\",\"delivery_service\":\"\",\"receiverAddress\":\"萧山区萧山经济技术开发区桥南区块春水路2号\",\"parentCode\":\"0040002654\",\"receiverName\":\"马宇飞\",\"contactNumber\":\"18357012576\",\"province\":\"3\",\"city\":\"35\",\"county\":\"408\",\"innerSource\":1}";

    /**
     *  【B2B订单内容】
     *  品种：柴胡滴丸，单价：11.08，数量：3
     *  品种：穿心莲内酯滴丸，单价：13.30，数量：3
     *  品种：藿香正气滴丸，单价：13.05：，数量：3
     * */
    public static final String FRONTORDER04 = "{\"buyerMessage\":\"\",\"buyerPayType\":\"0\",\"buyerPayMethod\":\"支付宝\",\"orderLines\":[{\"shoppingCartPkId\":4894,\"productSku\":\"10000266\",\"productQuantity\":\"3\",\"channelCode\":\"12\"},{\"shoppingCartPkId\":4895,\"productSku\":\"10000286\",\"productQuantity\":\"3\",\"channelCode\":\"12\"},{\"shoppingCartPkId\":4896,\"productSku\":\"10000434\",\"productQuantity\":\"3\",\"channelCode\":\"12\"}],\"couponIdList\":[],\"score\":0,\"freight\":0,\"express_code\":\"\",\"delivery_service\":\"\",\"receiverAddress\":\"杭州市上城区雷霆路60号长城大厦11、12楼\",\"parentCode\":\"0010511959\",\"receiverName\":\"马宇飞\",\"contactNumber\":\"18357012576\",\"province\":\"3\",\"city\":\"35\",\"county\":\"408\",\"innerSource\":1}";

    /**
     *  【B2B订单内容-退货】
     *  品种：柴胡滴丸，单价：11.08，数量：2
     * */
    public static String frontReturnOrder01(String timestamp, String orderId, String returnNum) {
        return "{\"createTime\":"+timestamp+",\"orderId\":\""+orderId+"\",\"orderReturnList\":[{\"batchId\":\"202302\",\"batchNum\":3,\"imageUrl\":\"https://devtoc.blob.core.chinacloudapi.cn/devotcimg/2daf579ebff14225af44486ce3e2e75b.jpg\",\"itemId\":\"10\",\"name\":\"柴胡滴丸\",\"repertoryNum\":\"3026\",\"returnNum\":"+returnNum+",\"skuCode\":\"10000266\",\"spec\":\"0.551g*6袋/盒\"}],\"retRefundType\":\"1\",\"returnComment\":\"\",\"returnGoodsType\":\"1\",\"returnGoodsTypeList\":[{\"id\":\"1\",\"value\":\"库存不足\"},{\"id\":\"2\",\"value\":\"更改价税信息\"},{\"id\":\"3\",\"value\":\"发票信息变更\"},{\"id\":\"4\",\"value\":\"客户信息变更\"},{\"id\":\"5\",\"value\":\"采购数量调整\"},{\"id\":\"6\",\"value\":\"采购品种修改\"},{\"id\":\"7\",\"value\":\"收货地址修改\"},{\"id\":\"8\",\"value\":\"其他\"}],\"telMobile\":\"13524863971\"}";
    }

}