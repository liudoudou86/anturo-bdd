package com.qa.credit.occupy.steps;

import com.qa.operate.check.AccountPeriodCheck;
import com.qa.operate.request.B2bOrderOperation;
import com.qa.operate.request.CreditOrderOperation;
import com.qa.data.B2bBackOrder;
import com.qa.data.B2bFrontOrder;
import com.qa.data.MysqlData;
import io.cucumber.datatable.DataTable;
import io.cucumber.java8.En;
import io.restassured.response.Response;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import net.thucydides.core.annotations.Steps;

import java.util.List;
import java.util.Map;

import static com.qa.data.MysqlData.selectCreditJournal;
import static com.qa.data.MysqlData.selectTemporaryCredit;

/**
 * @author Tesla Liu
 * @date 2022/11/10 09:50
 * 描述 仅占压超账期
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class ForwardUseOnlyAccountPeriodSteps implements En {

    String creditGradeId;
    String overduePeriodDays;
    String creditControlDimension;
    String orderId01, orderId02;
    String sku;
    String itemId;
    String customerEntCode;

    @Steps(shared = true)
    MysqlData mysqlData;

    @Steps(shared = true)
    B2bOrderOperation b2bOrderOperation;

    @Steps(shared = true)
    CreditOrderOperation creditOrderOperation;

    @Steps(shared = true)
    AccountPeriodCheck creditAmountCheck;

    public ForwardUseOnlyAccountPeriodSteps() {
        this.testcase01();
    }

    public void testcase01() {
        Given("【仅占压超账期0101】更新客户信用政策", (DataTable dataTable) -> {
            mysqlData.deleteCreditData("scripts/【清理信用历史数据】.sql");
            mysqlData.inserCreditData("scripts/【仅占压超账期测试数据】.sql");
            creditGradeId = dataTable.asMaps().get(0).get("CREDIT_GRADE_ID");
            overduePeriodDays = dataTable.asMaps().get(0).get("OVERDUE_PERIOD_DAYS");
            creditControlDimension = dataTable.asMaps().get(0).get("CREDIT_CONTROL_DIMENSION");
            mysqlData.updateEnterpriseGrade(overduePeriodDays, creditControlDimension, creditGradeId);
        });
        When("【仅占压超账期0101】客户在B2B下赊销订单",(DataTable dataTable)-> {
            customerEntCode = dataTable.asMaps().get(0).get("SALE_ORG_CODE");
            b2bOrderOperation.loginB2bFrontStageSystem();
            orderId01 = b2bOrderOperation.b2bFrontSystemOrder(B2bFrontOrder.FRONTORDER01);
            dataTable.asMaps().forEach(caseData ->{
                sku = caseData.get("SKU");
                itemId = caseData.get("ITEM_ID");
                mysqlData.updateOrderlines(itemId, orderId01, sku);
            });
        });
        Then("【仅占压超账期0101】商务审核应通过",()-> {
            b2bOrderOperation.loginB2bBackStageSystem();
            // 2为信用类型
            b2bOrderOperation.businessApproval(orderId01, B2bBackOrder.backOrder01(orderId01), "0","0");
        });
        And("【仅占压超账期0101】人工审核应通过",()-> {
            b2bOrderOperation.loginB2bBackStageSystem();
            b2bOrderOperation.manualApproval(orderId01, B2bBackOrder.backOrder01(orderId01));
        });
        Then("【仅占压超账期0101】产品集合金额应变动", (DataTable dataTable)->{
            Response productSetData = creditOrderOperation.queryProductSet(customerEntCode);
            creditAmountCheck.CheckProductSetData(dataTable, productSetData);
        });
        And("【仅占压超账期0101】品种金额应变动",(DataTable dataTable)-> {
            Response productVarietyData = creditOrderOperation.queryProductVariety(customerEntCode);
            creditAmountCheck.CheckProductVarietyData(dataTable, productVarietyData);
        });
        Then("【仅占压超账期0101】临时信用申请应变动", (DataTable dataTable)->{
            List<Map<String, Object>> sqlResult = selectTemporaryCredit(customerEntCode);
            creditAmountCheck.CheckTemporaryCreditData(dataTable, sqlResult);
        });
        And("【仅占压超账期0101】流水应变动", (DataTable dataTable)->{
            List<Map<String, Object>> sqlResult = selectCreditJournal(customerEntCode);
            creditAmountCheck.CheckCreditJounalData(dataTable, sqlResult);
        });
        When("【仅占压超账期0102】客户在B2B下赊销订单",(DataTable dataTable)-> {
            customerEntCode = dataTable.asMaps().get(0).get("SALE_ORG_CODE");
            b2bOrderOperation.loginB2bFrontStageSystem();
            orderId02 = b2bOrderOperation.b2bFrontSystemOrder(B2bFrontOrder.FRONTORDER02);
            dataTable.asMaps().forEach(caseData ->{
                sku = caseData.get("SKU");
                itemId = caseData.get("ITEM_ID");
                mysqlData.updateOrderlines(itemId, orderId02, sku);
            });
        });
        Then("【仅占压超账期0102】商务审核应通过",()-> {
            b2bOrderOperation.loginB2bBackStageSystem();
            b2bOrderOperation.businessApproval(orderId02, B2bBackOrder.backOrder02(orderId02), "0","0");
        });
        And("【仅占压超账期0102】人工审核应通过",()-> {
            b2bOrderOperation.loginB2bBackStageSystem();
            b2bOrderOperation.manualApproval(orderId02, B2bBackOrder.backOrder02(orderId02));
        });
        Then("【仅占压超账期0102】产品集合金额应变动", (DataTable dataTable)->{
            Response productSetData = creditOrderOperation.queryProductSet(customerEntCode);
            creditAmountCheck.CheckProductSetData(dataTable, productSetData);
        });
        And("【仅占压超账期0102】品种金额应变动",(DataTable dataTable)-> {
            Response productVarietyData = creditOrderOperation.queryProductVariety(customerEntCode);
            creditAmountCheck.CheckProductVarietyData(dataTable, productVarietyData);
        });
        Then("【仅占压超账期0102】临时信用申请应变动", (DataTable dataTable)->{
            List<Map<String, Object>> sqlResult = selectTemporaryCredit(customerEntCode);
            creditAmountCheck.CheckTemporaryCreditData(dataTable, sqlResult);
        });
        And("【仅占压超账期0102】流水应变动", (DataTable dataTable)->{
            List<Map<String, Object>> sqlResult = selectCreditJournal(customerEntCode);
            creditAmountCheck.CheckCreditJounalData(dataTable, sqlResult);
        });
    }

}
