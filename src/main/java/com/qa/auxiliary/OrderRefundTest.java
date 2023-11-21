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
 * @date 2023/01/29 11:18
 * 描述 B2B辅助测试
 */

@SuppressWarnings("all")
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class OrderRefundTest {
    MysqlData mysqlData = new MysqlData();
    B2bOrderOperation b2bOrderOperation = new B2bOrderOperation();
    TimeUtil timeUtil = new TimeUtil();

    @Description(value = "【普通赊销订单】订单退货-订单编号04")
    @Test
    public void generalOrderReturnExecution() throws Exception {
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
        // 订单发货正向BNL
        String deliveryEccId = mysqlData.selectOrderEccid(orderId);
        String deliveryBillingId = "BLN-" + timeUtil.randomTimeStamp();
        log.info("【发货billing号】: {}", deliveryBillingId);
        b2bOrderOperation.deliveryBln(deliveryEccId,"10", timeUtil.currentTimeStyle02(),"3","33.24",deliveryBillingId);
        // 发货执行[此处仅针对10订单行操作]
        b2bOrderOperation.orderDelivery(deliveryBillingId, orderId, B2bBackOrder.orderItemInfo("3.0","10"));
        // 前台发起退货
        b2bOrderOperation.b2bFrontStageSystemOrderReturn(B2bFrontOrder.frontReturnOrder01(timeUtil.currentTimeStamp(),orderId,"2"));
        // 后台审核
        String orderReturnId = mysqlData.selectOrderReturnId(orderId);
        b2bOrderOperation.b2bReturnBusinessApproval(orderReturnId);
        b2bOrderOperation.b2bReturnManualApproval(orderReturnId);
        b2bOrderOperation.b2bReturnOperateApproval(orderReturnId,B2bBackOrder.backReturnOrder01(orderReturnId));
        TimeUnit.SECONDS.sleep(1);
        // 订单退货OMS回调ECC-ID
        String returnEccId = "R" + deliveryEccId;
        String returnBillingId = "R" + deliveryBillingId;
        log.info("【退货billing号】: {}", returnBillingId);
        b2bOrderOperation.orderReturnOmsApproval(returnEccId, orderId, "10",orderReturnId);
        // 订单退货正向BLN
        b2bOrderOperation.returnBln(returnEccId,"10",timeUtil.currentTimeStyle02(),"-2","-22.16",returnBillingId);
        // 订单退货冲销BLN
        String writeOffEccId = "W" + deliveryBillingId;
        b2bOrderOperation.writeOffBln(returnEccId,"10",timeUtil.currentTimeStyle02(),"2","22.16",returnBillingId,writeOffEccId);
        log.info("【订单号】: {}", orderId);
        // 推送退货金税
        String ticketNumber = "LZJS" + timeUtil.currentTimeStamp();
        b2bOrderOperation.pushGoldenTax(returnEccId, timeUtil.currentTimeStyle02(), "-1", ticketNumber, "10", "-11.08", returnBillingId);
    }
}
