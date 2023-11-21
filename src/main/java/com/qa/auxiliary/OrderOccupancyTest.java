package com.qa.auxiliary;

import com.qa.data.B2bBackOrder;
import com.qa.data.B2bFrontOrder;
import com.qa.data.MysqlData;
import com.qa.operate.request.B2bOrderOperation;
import com.qa.tool.TimeUtil;
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
 * @date 2023/01/28 16:51
 * 描述 B2B辅助测试
 */

@SuppressWarnings("all")
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class OrderOccupancyTest {
    MysqlData mysqlData = new MysqlData();
    B2bOrderOperation b2bOrderOperation = new B2bOrderOperation();
    TimeUtil timeUtil = new TimeUtil();

    @Description(value = "【普通赊销订单】订单发货-订单编号04")
    @Test
    public void generalOrderDeliveryExecution() throws Exception {
        // 测试数据初始化
        mysqlData.deleteCreditData("scripts/【清理信用历史数据】.sql");
        mysqlData.deleteB2bData("scripts/【清理B2B历史数据】.sql");
        mysqlData.inserCreditData("scripts/【占压年度资信加超额度测试数据】.sql");
        mysqlData.updateEnterpriseGrade("300", "PRODUCT_SET", "1328517153425901698");
        // 订单商务审核&&人工审核
        String orderId = b2bOrderOperation.b2bFrontSystemOrder(B2bFrontOrder.FRONTORDER04);
        b2bOrderOperation.businessApproval(orderId, B2bBackOrder.backOrder04(orderId), "0","0");
        b2bOrderOperation.manualApproval(orderId, B2bBackOrder.backOrder04(orderId));
        TimeUnit.SECONDS.sleep(1);
        // 订单发货OMS回调ECC-ID
        b2bOrderOperation.orderDeliveryOmsApproval(orderId);
        // 订单发货正向BNL
        String deliveryEccId = mysqlData.selectOrderEccid(orderId);
        String deliveryBillingId = "BLN-" + timeUtil.randomTimeStamp();
        log.info("【发货billing号】: {}", deliveryBillingId);
        b2bOrderOperation.deliveryBln(deliveryEccId,"10", timeUtil.currentTimeStyle02(),"3","33.24",deliveryBillingId);
        // 发货执行[此处仅针对10订单行操作]
        b2bOrderOperation.orderDelivery(deliveryBillingId, orderId, B2bBackOrder.orderItemInfo("3.0","10"));
        // 推送金税
        String ticketNumber = "LZJS" + timeUtil.currentTimeStamp();
        b2bOrderOperation.pushGoldenTax(deliveryEccId, timeUtil.currentTimeStyle02(), "1", ticketNumber, "10", "11.08", deliveryBillingId);
    }

}
