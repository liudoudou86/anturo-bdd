package com.qa.execution;

import com.qa.auxiliary.OrderCancelTest;
import com.qa.auxiliary.OrderRepayTest;
import com.qa.auxiliary.OrderOccupancyTest;
import com.qa.auxiliary.OrderRefundTest;
import io.qameta.allure.Description;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author Tesla Liu
 * @date 2023/01/28 17:11
 * 描述 测试执行类
 */
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class TestDemo {

    OrderOccupancyTest orderDeliveryTest = new OrderOccupancyTest();
    OrderRefundTest orderReturnTest = new OrderRefundTest();
    OrderCancelTest orderCancelTest = new OrderCancelTest();
    OrderRepayTest orderCollectMoney = new OrderRepayTest();

    @Description("订单辅助测试")
    @Test
    public void operation() throws Exception {
        orderDeliveryTest.generalOrderDeliveryExecution();
        orderReturnTest.generalOrderReturnExecution();
        orderCancelTest.generalOrderCancelExecution();
        orderCollectMoney.generalOrderCollectMoneyExecution();
    }
}
