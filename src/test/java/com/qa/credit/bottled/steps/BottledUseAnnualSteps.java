package com.qa.credit.bottled.steps;

import com.qa.data.B2bBackOrder;
import com.qa.data.B2bFrontOrder;
import com.qa.data.MysqlData;
import com.qa.operate.check.BottledCheck;
import com.qa.operate.request.B2bOrderOperation;
import com.qa.operate.request.CreditOrderOperation;
import io.cucumber.datatable.DataTable;
import io.cucumber.java8.En;
import io.restassured.response.Response;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import net.thucydides.core.annotations.Steps;

/**
 * @author Tesla Liu
 * @date 2022/12/21 09:30
 * 描述 铺底货正向占压
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class BottledUseAnnualSteps implements En{

    String creditGradeId;
    String overduePeriodDays;
    String creditControlDimension;
    String orderId;
    String sku;
    String itemId;
    String customerEntCode;
    String productVarietyCode;
    String saleOrgCode;

    @Steps(shared = true)
    MysqlData mysqlData;

    @Steps(shared = true)
    B2bOrderOperation b2bOrderOperation;

    @Steps(shared = true)
    CreditOrderOperation creditOrderOperation;

    @Steps(shared = true)
    BottledCheck creditAmountCheck;

    public BottledUseAnnualSteps () {
        this.testcase01();
    }

    public void testcase01() {
        Given("【铺底货0101】更新客户信用政策", (DataTable dataTable) -> {
            mysqlData.deleteCreditData("scripts/【清理信用历史数据】.sql");
            mysqlData.inserCreditData("scripts/【仅年度资信无临时信用】.sql");
            creditGradeId = dataTable.asMaps().get(0).get("CREDIT_GRADE_ID");
            overduePeriodDays = dataTable.asMaps().get(0).get("OVERDUE_PERIOD_DAYS");
            creditControlDimension = dataTable.asMaps().get(0).get("CREDIT_CONTROL_DIMENSION");
            mysqlData.updateEnterpriseGrade(overduePeriodDays, creditControlDimension, creditGradeId);
        });
        When("【铺底货0101】客户在B2B下赊销订单", (DataTable dataTable) -> {
            customerEntCode = dataTable.asMaps().get(0).get("SALE_ORG_CODE");
            b2bOrderOperation.loginB2bFrontStageSystem();
            orderId = b2bOrderOperation.b2bFrontSystemOrder(B2bFrontOrder.FRONTORDER03);
            dataTable.asMaps().forEach(caseData -> {
                sku = caseData.get("SKU");
                itemId = caseData.get("ITEM_ID");
                mysqlData.updateOrderlines(itemId, orderId, sku);
            });
        });
        Then("【铺底货0101】商务审核应通过", () -> {
            b2bOrderOperation.loginB2bBackStageSystem();
            b2bOrderOperation.businessApproval(orderId, B2bBackOrder.backOrder03(orderId), "1","4");
        });
        And("【铺底货0101】信用使用情况",(DataTable dataTable)-> {
            Response creditUseData = b2bOrderOperation.creditUse(orderId);
            creditAmountCheck.CheckCreditUse(dataTable, creditUseData);
        });
        And("【铺底货0101】人工审核应通过", () -> {
            b2bOrderOperation.loginB2bBackStageSystem();
            b2bOrderOperation.manualApproval(orderId, B2bBackOrder.backOrder03(orderId));
        });
    }

}
