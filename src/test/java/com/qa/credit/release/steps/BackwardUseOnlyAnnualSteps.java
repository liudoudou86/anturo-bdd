package com.qa.credit.release.steps;

import com.qa.operate.check.AnnualCheck;
import com.qa.tool.TimeUtil;
import io.cucumber.java8.En;
import com.qa.operate.request.B2bOrderOperation;
import com.qa.operate.request.CreditOrderOperation;
import com.qa.data.B2bBackOrder;
import com.qa.data.B2bFrontOrder;
import com.qa.data.B2bRefundOrder;
import com.qa.data.MysqlData;
import io.cucumber.datatable.DataTable;
import io.restassured.response.Response;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import net.thucydides.core.annotations.Steps;

import java.util.List;
import java.util.Map;

import static com.qa.data.MysqlData.selectCreditJournal;

/**
 * @author Tesla Liu
 * @date 2022/11/28 13:37
 * 描述 仅占压年度资信 - 逆向返还
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class BackwardUseOnlyAnnualSteps implements En{
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
    AnnualCheck creditAmountCheck;

    public BackwardUseOnlyAnnualSteps() {
        this.testcase03();
        this.testcase04();
        this.testcase05();
        this.testcase06();
    }

    public void testcase03() {
        Given("【仅占压年度资信0301】更新客户信用政策", (DataTable dataTable) -> {
            mysqlData.deleteCreditData("scripts/【清理信用历史数据】.sql");
            mysqlData.inserCreditData("scripts/【仅年度资信无临时信用】.sql");
            creditGradeId = dataTable.asMaps().get(0).get("CREDIT_GRADE_ID");
            overduePeriodDays = dataTable.asMaps().get(0).get("OVERDUE_PERIOD_DAYS");
            creditControlDimension = dataTable.asMaps().get(0).get("CREDIT_CONTROL_DIMENSION");
            mysqlData.updateEnterpriseGrade(overduePeriodDays, creditControlDimension, creditGradeId);
        });
        When("【仅占压年度资信0301】客户在B2B下赊销订单",(DataTable dataTable)-> {
            customerEntCode = dataTable.asMaps().get(0).get("SALE_ORG_CODE");
            b2bOrderOperation.loginB2bFrontStageSystem();
            orderId = b2bOrderOperation.b2bFrontSystemOrder(B2bFrontOrder.FRONTORDER02);
            dataTable.asMaps().forEach(caseData ->{
                sku = caseData.get("SKU");
                itemId = caseData.get("ITEM_ID");
                mysqlData.updateOrderlines(itemId, orderId, sku);
            });
        });
        Then("【仅占压年度资信0301】商务审核应通过",()-> {
            b2bOrderOperation.loginB2bBackStageSystem();
            b2bOrderOperation.businessApproval(orderId, B2bBackOrder.backOrder02(orderId), "0","0");
        });
        And("【仅占压年度资信0301】人工审核应通过",()-> {
            b2bOrderOperation.loginB2bBackStageSystem();
            b2bOrderOperation.manualApproval(orderId, B2bBackOrder.backOrder02(orderId));
        });
        Then("【仅占压年度资信0301】产品集合金额应变动", (DataTable dataTable)->{
            Response productSetData = creditOrderOperation.queryProductSet(customerEntCode);
            creditAmountCheck.CheckProductSetData(dataTable, productSetData);
        });
        And("【仅占压年度资信0301】品种金额应变动",(DataTable dataTable)-> {
            Response productVarietyData = creditOrderOperation.queryProductVariety(customerEntCode);
            creditAmountCheck.CheckProductVarietyData(dataTable, productVarietyData);
        });
        And("【仅占压年度资信0301】流水应变动", (DataTable dataTable)->{
            List<Map<String, Object>> sqlResult = selectCreditJournal(customerEntCode);
            creditAmountCheck.CheckCreditJounalData(dataTable, sqlResult);
        });
        And("【仅占压年度资信0301】冻结产品集合中某品种",(DataTable dataTable)->{
            customerEntCode = dataTable.asMaps().get(0).get("customerEntCode");
            productVarietyCode = dataTable.asMaps().get(0).get("productVarietyCode");
            saleOrgCode = dataTable.asMaps().get(0).get("saleOrgCode");
            creditOrderOperation.disableProductVariety(customerEntCode, productVarietyCode, saleOrgCode);
        });
        When("【仅占压年度资信0302】客户在B2B退货",(DataTable dataTable)-> {
            creditOrderOperation.orderReturn(orderId, B2bRefundOrder.BACKRETURNORDER01(orderId));
        });
        Then("【仅占压年度资信0302】产品集合金额应变动", (DataTable dataTable)->{
            Response productSetData = creditOrderOperation.queryProductSet(customerEntCode);
            creditAmountCheck.CheckProductSetData(dataTable, productSetData);
        });
        And("【仅占压年度资信0302】品种金额应变动",(DataTable dataTable)-> {
            Response productVarietyData = creditOrderOperation.queryProductVariety(customerEntCode);
            creditAmountCheck.CheckProductVarietyData(dataTable, productVarietyData);
        });
        And("【仅占压年度资信0302】流水应变动", (DataTable dataTable)->{
            List<Map<String, Object>> sqlResult = selectCreditJournal(customerEntCode);
            creditAmountCheck.CheckCreditJounalData(dataTable, sqlResult);
        });
    }

    public void testcase04() {
        Given("【仅占压年度资信0401】更新客户信用政策", (DataTable dataTable) -> {
            mysqlData.deleteCreditData("scripts/【清理信用历史数据】.sql");
            mysqlData.inserCreditData("scripts/【仅年度资信无临时信用】.sql");
            creditGradeId = dataTable.asMaps().get(0).get("CREDIT_GRADE_ID");
            overduePeriodDays = dataTable.asMaps().get(0).get("OVERDUE_PERIOD_DAYS");
            creditControlDimension = dataTable.asMaps().get(0).get("CREDIT_CONTROL_DIMENSION");
            mysqlData.updateEnterpriseGrade(overduePeriodDays, creditControlDimension, creditGradeId);
        });
        When("【仅占压年度资信0401】客户在B2B下赊销订单",(DataTable dataTable)-> {
            customerEntCode = dataTable.asMaps().get(0).get("SALE_ORG_CODE");
            b2bOrderOperation.loginB2bFrontStageSystem();
            orderId = b2bOrderOperation.b2bFrontSystemOrder(B2bFrontOrder.FRONTORDER02);
            dataTable.asMaps().forEach(caseData ->{
                sku = caseData.get("SKU");
                itemId = caseData.get("ITEM_ID");
                mysqlData.updateOrderlines(itemId, orderId, sku);
            });
        });
        Then("【仅占压年度资信0401】商务审核应通过",()-> {
            b2bOrderOperation.loginB2bBackStageSystem();
            b2bOrderOperation.businessApproval(orderId, B2bBackOrder.backOrder02(orderId), "0","0");
        });
        And("【仅占压年度资信0401】人工审核应通过",()-> {
            b2bOrderOperation.loginB2bBackStageSystem();
            b2bOrderOperation.manualApproval(orderId, B2bBackOrder.backOrder02(orderId));
        });
        Then("【仅占压年度资信0401】产品集合金额应变动", (DataTable dataTable)->{
            Response productSetData = creditOrderOperation.queryProductSet(customerEntCode);
            creditAmountCheck.CheckProductSetData(dataTable, productSetData);
        });
        And("【仅占压年度资信0401】品种金额应变动",(DataTable dataTable)-> {
            Response productVarietyData = creditOrderOperation.queryProductVariety(customerEntCode);
            creditAmountCheck.CheckProductVarietyData(dataTable, productVarietyData);
        });
        And("【仅占压年度资信0401】流水应变动", (DataTable dataTable)->{
            List<Map<String, Object>> sqlResult = selectCreditJournal(customerEntCode);
            creditAmountCheck.CheckCreditJounalData(dataTable, sqlResult);
        });
        When("【仅占压年度资信0402】客户在B2B退货",(DataTable dataTable)-> {
            creditOrderOperation.orderReturn(orderId, B2bRefundOrder.BACKRETURNORDER01(orderId));
        });
        Then("【仅占压年度资信0402】产品集合金额应变动", (DataTable dataTable)->{
            Response productSetData = creditOrderOperation.queryProductSet(customerEntCode);
            creditAmountCheck.CheckProductSetData(dataTable, productSetData);
        });
        And("【仅占压年度资信0402】品种金额应变动",(DataTable dataTable)-> {
            Response productVarietyData = creditOrderOperation.queryProductVariety(customerEntCode);
            creditAmountCheck.CheckProductVarietyData(dataTable, productVarietyData);
        });
        And("【仅占压年度资信0402】流水应变动", (DataTable dataTable)->{
            List<Map<String, Object>> sqlResult = selectCreditJournal(customerEntCode);
            creditAmountCheck.CheckCreditJounalData(dataTable, sqlResult);
        });
        And("【仅占压年度资信0402】冻结产品集合中某品种",(DataTable dataTable)->{
            customerEntCode = dataTable.asMaps().get(0).get("customerEntCode");
            productVarietyCode = dataTable.asMaps().get(0).get("productVarietyCode");
            saleOrgCode = dataTable.asMaps().get(0).get("saleOrgCode");
            creditOrderOperation.disableProductVariety(customerEntCode, productVarietyCode, saleOrgCode);
        });
        When("【仅占压年度资信0403】客户在B2B退货冲销",(DataTable dataTable)-> {
            creditOrderOperation.orderReturnWriteOff(orderId, B2bRefundOrder.BACKRETURNWRITEOFFORDER01(orderId));
        });
        Then("【仅占压年度资信0403】产品集合金额应变动", (DataTable dataTable)->{
            Response productSetData = creditOrderOperation.queryProductSet(customerEntCode);
            creditAmountCheck.CheckProductSetData(dataTable, productSetData);
        });
        And("【仅占压年度资信0403】品种金额应变动",(DataTable dataTable)-> {
            Response productVarietyData = creditOrderOperation.queryProductVariety(customerEntCode);
            creditAmountCheck.CheckProductVarietyData(dataTable, productVarietyData);
        });
        And("【仅占压年度资信0403】流水应变动", (DataTable dataTable)->{
            List<Map<String, Object>> sqlResult = selectCreditJournal(customerEntCode);
            creditAmountCheck.CheckCreditJounalData(dataTable, sqlResult);
        });
    }

    public void testcase05() {
        Given("【仅占压年度资信0501】更新客户信用政策", (DataTable dataTable) -> {
            mysqlData.deleteCreditData("scripts/【清理信用历史数据】.sql");
            mysqlData.inserCreditData("scripts/【仅年度资信无临时信用】.sql");
            creditGradeId = dataTable.asMaps().get(0).get("CREDIT_GRADE_ID");
            overduePeriodDays = dataTable.asMaps().get(0).get("OVERDUE_PERIOD_DAYS");
            creditControlDimension = dataTable.asMaps().get(0).get("CREDIT_CONTROL_DIMENSION");
            mysqlData.updateEnterpriseGrade(overduePeriodDays, creditControlDimension, creditGradeId);
        });
        When("【仅占压年度资信0501】客户在B2B下赊销订单",(DataTable dataTable)-> {
            customerEntCode = dataTable.asMaps().get(0).get("SALE_ORG_CODE");
            b2bOrderOperation.loginB2bFrontStageSystem();
            orderId = b2bOrderOperation.b2bFrontSystemOrder(B2bFrontOrder.FRONTORDER02);
            dataTable.asMaps().forEach(caseData ->{
                sku = caseData.get("SKU");
                itemId = caseData.get("ITEM_ID");
                mysqlData.updateOrderlines(itemId, orderId, sku);
            });
        });
        Then("【仅占压年度资信0501】商务审核应通过",()-> {
            b2bOrderOperation.loginB2bBackStageSystem();
            b2bOrderOperation.businessApproval(orderId, B2bBackOrder.backOrder02(orderId), "0","0");
        });
        And("【仅占压年度资信0501】人工审核应通过",()-> {
            b2bOrderOperation.loginB2bBackStageSystem();
            b2bOrderOperation.manualApproval(orderId, B2bBackOrder.backOrder02(orderId));
        });
        Then("【仅占压年度资信0501】产品集合金额应变动", (DataTable dataTable)->{
            Response productSetData = creditOrderOperation.queryProductSet(customerEntCode);
            creditAmountCheck.CheckProductSetData(dataTable, productSetData);
        });
        And("【仅占压年度资信0501】品种金额应变动",(DataTable dataTable)-> {
            Response productVarietyData = creditOrderOperation.queryProductVariety(customerEntCode);
            creditAmountCheck.CheckProductVarietyData(dataTable, productVarietyData);
        });
        And("【仅占压年度资信0501】流水应变动", (DataTable dataTable)->{
            List<Map<String, Object>> sqlResult = selectCreditJournal(customerEntCode);
            creditAmountCheck.CheckCreditJounalData(dataTable, sqlResult);
        });
        When("【仅占压年度资信0502】客户在B2B退货",(DataTable dataTable)-> {
            creditOrderOperation.orderReturn(orderId, B2bRefundOrder.BACKRETURNORDER01(orderId));
        });
        Then("【仅占压年度资信0502】产品集合金额应变动", (DataTable dataTable)->{
            Response productSetData = creditOrderOperation.queryProductSet(customerEntCode);
            creditAmountCheck.CheckProductSetData(dataTable, productSetData);
        });
        And("【仅占压年度资信0502】品种金额应变动",(DataTable dataTable)-> {
            Response productVarietyData = creditOrderOperation.queryProductVariety(customerEntCode);
            creditAmountCheck.CheckProductVarietyData(dataTable, productVarietyData);
        });
        And("【仅占压年度资信0502】流水应变动", (DataTable dataTable)->{
            List<Map<String, Object>> sqlResult = selectCreditJournal(customerEntCode);
            creditAmountCheck.CheckCreditJounalData(dataTable, sqlResult);
        });
        When("【仅占压年度资信0503】客户在B2B退货冲销",(DataTable dataTable)-> {
            creditOrderOperation.orderReturnWriteOff(orderId, B2bRefundOrder.BACKRETURNWRITEOFFORDER01(orderId));
        });
        Then("【仅占压年度资信0503】产品集合金额应变动", (DataTable dataTable)->{
            Response productSetData = creditOrderOperation.queryProductSet(customerEntCode);
            creditAmountCheck.CheckProductSetData(dataTable, productSetData);
        });
        And("【仅占压年度资信0503】品种金额应变动",(DataTable dataTable)-> {
            Response productVarietyData = creditOrderOperation.queryProductVariety(customerEntCode);
            creditAmountCheck.CheckProductVarietyData(dataTable, productVarietyData);
        });
        And("【仅占压年度资信0503】流水应变动", (DataTable dataTable)->{
            List<Map<String, Object>> sqlResult = selectCreditJournal(customerEntCode);
            creditAmountCheck.CheckCreditJounalData(dataTable, sqlResult);
        });
        When("【仅占压年度资信0504】月度结转",()-> {
            mysqlData.updateExecutionMonth("2022-10-01", customerEntCode);
            creditOrderOperation.MonthTransfer();
        });
        Then("【仅占压年度资信0504】产品集合金额应变动", (DataTable dataTable)->{
            Response productSetData = creditOrderOperation.queryProductSet(customerEntCode);
            creditAmountCheck.CheckProductSetData(dataTable, productSetData);
        });
        And("【仅占压年度资信0504】品种金额应变动",(DataTable dataTable)-> {
            Response productVarietyData = creditOrderOperation.queryProductVariety(customerEntCode);
            creditAmountCheck.CheckProductVarietyData(dataTable, productVarietyData);
        });
    }

    public void testcase06() {
        Given("【仅占压年度资信0601】更新客户信用政策", (DataTable dataTable) -> {
            mysqlData.deleteCreditData("scripts/【清理信用历史数据】.sql");
            mysqlData.inserCreditData("scripts/【仅年度资信无临时信用】.sql");
            creditGradeId = dataTable.asMaps().get(0).get("CREDIT_GRADE_ID");
            overduePeriodDays = dataTable.asMaps().get(0).get("OVERDUE_PERIOD_DAYS");
            creditControlDimension = dataTable.asMaps().get(0).get("CREDIT_CONTROL_DIMENSION");
            mysqlData.updateEnterpriseGrade(overduePeriodDays, creditControlDimension, creditGradeId);
        });
        When("【仅占压年度资信0601】客户在B2B下赊销订单",(DataTable dataTable)-> {
            customerEntCode = dataTable.asMaps().get(0).get("SALE_ORG_CODE");
            b2bOrderOperation.loginB2bFrontStageSystem();
            orderId = b2bOrderOperation.b2bFrontSystemOrder(B2bFrontOrder.FRONTORDER02);
            dataTable.asMaps().forEach(caseData ->{
                sku = caseData.get("SKU");
                itemId = caseData.get("ITEM_ID");
                mysqlData.updateOrderlines(itemId, orderId, sku);
            });
        });
        Then("【仅占压年度资信0601】商务审核应通过",()-> {
            b2bOrderOperation.loginB2bBackStageSystem();
            b2bOrderOperation.businessApproval(orderId, B2bBackOrder.backOrder02(orderId), "0","0");
        });
        And("【仅占压年度资信0601】人工审核应通过",()-> {
            b2bOrderOperation.loginB2bBackStageSystem();
            b2bOrderOperation.manualApproval(orderId, B2bBackOrder.backOrder02(orderId));
        });
        Then("【仅占压年度资信0601】产品集合金额应变动", (DataTable dataTable)->{
            Response productSetData = creditOrderOperation.queryProductSet(customerEntCode);
            creditAmountCheck.CheckProductSetData(dataTable, productSetData);
        });
        And("【仅占压年度资信0601】品种金额应变动",(DataTable dataTable)-> {
            Response productVarietyData = creditOrderOperation.queryProductVariety(customerEntCode);
            creditAmountCheck.CheckProductVarietyData(dataTable, productVarietyData);
        });
        And("【仅占压年度资信0601】流水应变动", (DataTable dataTable)->{
            List<Map<String, Object>> sqlResult = selectCreditJournal(customerEntCode);
            creditAmountCheck.CheckCreditJounalData(dataTable, sqlResult);
        });
        When("【仅占压年度资信0602】月度结转",()-> {
            mysqlData.updateExecutionMonth(TimeUtil.lastMonthRandomDate(), customerEntCode);
            creditOrderOperation.MonthTransfer();
        });
        Then("【仅占压年度资信0602】产品集合金额应变动", (DataTable dataTable)->{
            Response productSetData = creditOrderOperation.queryProductSet(customerEntCode);
            creditAmountCheck.CheckProductSetData(dataTable, productSetData);
        });
        And("【仅占压年度资信0602】品种金额应变动",(DataTable dataTable)-> {
            Response productVarietyData = creditOrderOperation.queryProductVariety(customerEntCode);
            creditAmountCheck.CheckProductVarietyData(dataTable, productVarietyData);
        });
        When("【仅占压年度资信0603】客户在B2B退货",(DataTable dataTable)-> {
            creditOrderOperation.orderReturn(orderId, B2bRefundOrder.BACKRETURNORDER01(orderId));
        });
        Then("【仅占压年度资信0603】产品集合金额应变动", (DataTable dataTable)->{
            Response productSetData = creditOrderOperation.queryProductSet(customerEntCode);
            creditAmountCheck.CheckProductSetData(dataTable, productSetData);
        });
        And("【仅占压年度资信0603】品种金额应变动",(DataTable dataTable)-> {
            Response productVarietyData = creditOrderOperation.queryProductVariety(customerEntCode);
            creditAmountCheck.CheckProductVarietyData(dataTable, productVarietyData);
        });
        And("【仅占压年度资信0603】流水应变动", (DataTable dataTable)->{
            List<Map<String, Object>> sqlResult = selectCreditJournal(customerEntCode);
            creditAmountCheck.CheckCreditJounalData(dataTable, sqlResult);
        });
        When("【仅占压年度资信0604】客户在B2B退货冲销",(DataTable dataTable)-> {
            creditOrderOperation.orderReturnWriteOff(orderId, B2bRefundOrder.BACKRETURNWRITEOFFORDER01(orderId));
        });
        Then("【仅占压年度资信0604】产品集合金额应变动", (DataTable dataTable)->{
            Response productSetData = creditOrderOperation.queryProductSet(customerEntCode);
            creditAmountCheck.CheckProductSetData(dataTable, productSetData);
        });
        And("【仅占压年度资信0604】品种金额应变动",(DataTable dataTable)-> {
            Response productVarietyData = creditOrderOperation.queryProductVariety(customerEntCode);
            creditAmountCheck.CheckProductVarietyData(dataTable, productVarietyData);
        });
        And("【仅占压年度资信0604】流水应变动", (DataTable dataTable)->{
            List<Map<String, Object>> sqlResult = selectCreditJournal(customerEntCode);
            creditAmountCheck.CheckCreditJounalData(dataTable, sqlResult);
        });
    }

}
