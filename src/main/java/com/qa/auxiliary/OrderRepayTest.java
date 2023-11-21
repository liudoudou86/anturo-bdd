package com.qa.auxiliary;

import com.qa.data.B2bBackOrder;
import com.qa.data.B2bFrontOrder;
import com.qa.data.MysqlData;
import com.qa.operate.request.B2bOrderOperation;
import com.qa.tool.TimeUtil;
import com.qa.tool.YamlUtil;
import io.qameta.allure.Description;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * @Author Tesla Liu
 * @Date 2023/02/13
 * @Description
 */
@SuppressWarnings("all")
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class OrderRepayTest {

    MysqlData mysqlData = new MysqlData();
    B2bOrderOperation b2bOrderOperation = new B2bOrderOperation();
    TimeUtil timeUtil = new TimeUtil();

    @Description(value = "【普通赊销订单】订单回款-订单编号04")
    @Test
    public void generalOrderCollectMoneyExecution() throws Exception {
        // 测试数据初始化
        mysqlData.deleteCreditData("scripts/【清理信用历史数据】.sql");
        mysqlData.deleteB2bData("scripts/【清理B2B历史数据】.sql");
        mysqlData.inserCreditData("scripts/【仅年度资信无临时信用】.sql");
        mysqlData.updateEnterpriseGrade("300", "PRODUCT_SET", "1328517153425901698");
        // 订单商务审核&&人工审核
        String orderId = b2bOrderOperation.b2bFrontSystemOrder(B2bFrontOrder.FRONTORDER04);
        b2bOrderOperation.businessApproval(orderId, B2bBackOrder.backOrder04(orderId), "0", "0");
        b2bOrderOperation.manualApproval(orderId, B2bBackOrder.backOrder04(orderId));
        TimeUnit.SECONDS.sleep(1);
        // 订单发货OMS回调ECC-ID
        b2bOrderOperation.orderDeliveryOmsApproval(orderId);
        // 订单发货正向BNL
        String deliveryEccId = mysqlData.selectOrderEccid(orderId);
        String deliveryBillingId = "BLN-" + timeUtil.randomTimeStamp();
        log.info("【发货billing号】: {}", deliveryBillingId);
        b2bOrderOperation.deliveryBln(deliveryEccId, "10", timeUtil.currentTimeStyle02(), "3", "33.24", deliveryBillingId);
        // 推送金税
        String ticketNumber = "LZJS" + timeUtil.currentTimeStamp();
        b2bOrderOperation.pushGoldenTax(deliveryEccId, timeUtil.currentTimeStyle02(), "1", ticketNumber, "10", "11.08", deliveryBillingId);
        // 申请回款
        String frontUserName = String.valueOf(YamlUtil.INSTANCE.getValueByKey("Account.FrontStage.username"));
        String collectMoneyAmount = "3.33";
        b2bOrderOperation.applyCollectMoney(
                B2bBackOrder.returnMoneyEntityBOList(collectMoneyAmount,"11.08", orderId, ticketNumber, "10", deliveryBillingId),
                B2bBackOrder.advanceChargeEntityList(frontUserName, collectMoneyAmount, timeUtil.currentTimeStamp())
        );
        // 待回款审核
        String collectMoneyId = mysqlData.selectCollectMoneyId();
        b2bOrderOperation.collectMoneyApproval(collectMoneyId,timeUtil.currentTimeStyle01());
        // 回款冲销
        String collectMoneyApplyId = mysqlData.selectCollectMoneyApplyId();
        String collectMoneyAdvanceId = mysqlData.selectCollectMoneyAdvanceId(collectMoneyApplyId);
        b2bOrderOperation.collectMoneyWriteOff(collectMoneyApplyId,collectMoneyAdvanceId,timeUtil.currentTimeStyle01());
        log.info("【订单号】: {}", orderId);
    }
}
