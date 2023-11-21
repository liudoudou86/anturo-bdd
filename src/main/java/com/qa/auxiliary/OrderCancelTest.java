package com.qa.auxiliary;

import com.qa.data.B2bBackOrder;
import com.qa.data.B2bFrontOrder;
import com.qa.data.MysqlData;
import com.qa.operate.request.B2bOrderOperation;
import io.qameta.allure.Description;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * @author Tesla Liu
 * @date 2023/02/09 16:54
 * 描述
 */
@SuppressWarnings("all")
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class OrderCancelTest {
    MysqlData mysqlData = new MysqlData();
    B2bOrderOperation b2bOrderOperation = new B2bOrderOperation();

    @Description(value = "【普通赊销订单】订单取消-订单编号04")
    @Test
    public void generalOrderCancelExecution() throws Exception {
        // 测试数据初始化
        mysqlData.deleteCreditData("scripts/【清理信用历史数据】.sql");
        mysqlData.inserCreditData("scripts/【仅年度资信无临时信用】.sql");
        mysqlData.updateEnterpriseGrade("300", "PRODUCT_SET", "1328517153425901698");
        // 订单商务审核&&人工审核
        String orderId = b2bOrderOperation.b2bFrontSystemOrder(B2bFrontOrder.FRONTORDER04);
        b2bOrderOperation.businessApproval(orderId, B2bBackOrder.backOrder04(orderId), "0","0");
        b2bOrderOperation.manualApproval(orderId, B2bBackOrder.backOrder04(orderId));
        TimeUnit.SECONDS.sleep(1);
        // 订单发货OMS回调ECC-ID
        b2bOrderOperation.orderDeliveryOmsApproval(orderId);
        // 发起订单取消
        b2bOrderOperation.orderCancel("10",orderId,"10000266");
        // 订单取消商务审核&&运营审核
        b2bOrderOperation.b2bCancelBusinessApproval(orderId,"10","10000266");
        b2bOrderOperation.b2bCancelOperateApproval(orderId,"10","10000266");
        // 订单取消OMS回调
        b2bOrderOperation.orderCancelOmsApproval(orderId,"10");
        log.info("【订单号】: {}", orderId);
    }
}
