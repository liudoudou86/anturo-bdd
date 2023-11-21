package com.qa.credit.release.steps;

import com.qa.operate.check.QuotaCheck;
import com.qa.operate.request.B2bOrderOperation;
import com.qa.operate.request.CreditOrderOperation;
import com.qa.data.B2bBackOrder;
import com.qa.data.B2bFrontOrder;
import com.qa.data.B2bRefundOrder;
import com.qa.data.MysqlData;
import com.qa.tool.TimeUtil;
import io.cucumber.datatable.DataTable;
import io.restassured.response.Response;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import io.cucumber.java8.En;
import net.thucydides.core.annotations.Steps;

import java.util.List;
import java.util.Map;

import static com.qa.data.MysqlData.selectCreditJournal;
import static com.qa.data.MysqlData.selectTemporaryCredit;

/**
 * @author Tesla Liu
 * @date 2022/11/28 13:49
 * 描述 仅占压超额度 - 逆向返还
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class BackwardUseOnlyQuotaSteps implements En{
    String creditGradeId;
    String overduePeriodDays;
    String creditControlDimension;
    String orderId01, orderId02;
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
    QuotaCheck creditAmountCheck;

    public BackwardUseOnlyQuotaSteps() {
        this.testcase03();
        this.testcase04();
        this.testcase05();
        this.testcase06();
    }

    public void testcase03() {
        Given("【仅占压超额度0301】更新客户信用政策", (DataTable dataTable) -> {
            mysqlData.deleteCreditData("scripts/【清理信用历史数据】.sql");
            mysqlData.inserCreditData("scripts/【仅占压超额度测试数据】.sql");
            creditGradeId = dataTable.asMaps().get(0).get("CREDIT_GRADE_ID");
            overduePeriodDays = dataTable.asMaps().get(0).get("OVERDUE_PERIOD_DAYS");
            creditControlDimension = dataTable.asMaps().get(0).get("CREDIT_CONTROL_DIMENSION");
            mysqlData.updateEnterpriseGrade(overduePeriodDays, creditControlDimension, creditGradeId);
        });
        When("【仅占压超额度0301】客户在B2B下赊销订单",(DataTable dataTable)-> {
            customerEntCode = dataTable.asMaps().get(0).get("SALE_ORG_CODE");
            b2bOrderOperation.loginB2bFrontStageSystem();
            orderId01 = b2bOrderOperation.b2bFrontSystemOrder(B2bFrontOrder.FRONTORDER01);
            dataTable.asMaps().forEach(caseData ->{
                sku = caseData.get("SKU");
                itemId = caseData.get("ITEM_ID");
                mysqlData.updateOrderlines(itemId, orderId01, sku);
            });
        });
        Then("【仅占压超额度0301】商务审核应通过",()-> {
            b2bOrderOperation.loginB2bBackStageSystem();
            b2bOrderOperation.businessApproval(orderId01, B2bBackOrder.backOrder01(orderId01), "0","0");
        });
        And("【仅占压超额度0301】人工审核应通过",()-> {
            b2bOrderOperation.loginB2bBackStageSystem();
            b2bOrderOperation.manualApproval(orderId01, B2bBackOrder.backOrder01(orderId01));
        });
        Then("【仅占压超额度0301】产品集合金额应变动", (DataTable dataTable)->{
            Response productSetData = creditOrderOperation.queryProductSet(customerEntCode);
            creditAmountCheck.CheckProductSetData(dataTable, productSetData);
        });
        And("【仅占压超额度0301】品种金额应变动",(DataTable dataTable)-> {
            Response productVarietyData = creditOrderOperation.queryProductVariety(customerEntCode);
            creditAmountCheck.CheckProductVarietyData(dataTable, productVarietyData);
        });
        Then("【仅占压超额度0301】临时信用申请应变动", (DataTable dataTable)->{
            List<Map<String, Object>> sqlResult = selectTemporaryCredit(customerEntCode);
            creditAmountCheck.CheckTemporaryCreditData(dataTable, sqlResult);
        });
        And("【仅占压超额度0301】流水应变动", (DataTable dataTable)->{
            List<Map<String, Object>> sqlResult = selectCreditJournal(customerEntCode);
            creditAmountCheck.CheckCreditJounalData(dataTable, sqlResult);
        });
        When("【仅占压超额度0302】客户在B2B下赊销订单",(DataTable dataTable)-> {
            customerEntCode = dataTable.asMaps().get(0).get("SALE_ORG_CODE");
            b2bOrderOperation.loginB2bFrontStageSystem();
            orderId02 = b2bOrderOperation.b2bFrontSystemOrder(B2bFrontOrder.FRONTORDER02);
            dataTable.asMaps().forEach(caseData ->{
                sku = caseData.get("SKU");
                itemId = caseData.get("ITEM_ID");
                mysqlData.updateOrderlines(itemId, orderId02, sku);
            });
        });
        Then("【仅占压超额度0302】商务审核应通过",()-> {
            b2bOrderOperation.loginB2bBackStageSystem();
            b2bOrderOperation.businessApproval(orderId02, B2bBackOrder.backOrder02(orderId02), "0","0");
        });
        And("【仅占压超额度0302】人工审核应通过",()-> {
            b2bOrderOperation.loginB2bBackStageSystem();
            b2bOrderOperation.manualApproval(orderId02, B2bBackOrder.backOrder02(orderId02));
        });
        Then("【仅占压超额度0302】产品集合金额应变动", (DataTable dataTable)->{
            Response productSetData = creditOrderOperation.queryProductSet(customerEntCode);
            creditAmountCheck.CheckProductSetData(dataTable, productSetData);
        });
        And("【仅占压超额度0302】品种金额应变动",(DataTable dataTable)-> {
            Response productVarietyData = creditOrderOperation.queryProductVariety(customerEntCode);
            creditAmountCheck.CheckProductVarietyData(dataTable, productVarietyData);
        });
        Then("【仅占压超额度0302】临时信用申请应变动", (DataTable dataTable)->{
            List<Map<String, Object>> sqlResult = selectTemporaryCredit(customerEntCode);
            creditAmountCheck.CheckTemporaryCreditData(dataTable, sqlResult);
        });
        And("【仅占压超额度0302】流水应变动", (DataTable dataTable)->{
            List<Map<String, Object>> sqlResult = selectCreditJournal(customerEntCode);
            creditAmountCheck.CheckCreditJounalData(dataTable, sqlResult);
        });
        And("【仅占压超额度0302】冻结产品集合中某品种",(DataTable dataTable)->{
            customerEntCode = dataTable.asMaps().get(0).get("customerEntCode");
            productVarietyCode = dataTable.asMaps().get(0).get("productVarietyCode");
            saleOrgCode = dataTable.asMaps().get(0).get("saleOrgCode");
            creditOrderOperation.disableProductVariety(customerEntCode, productVarietyCode, saleOrgCode);
        });
        When("【仅占压超额度0303】客户在B2B退货",(DataTable dataTable)-> {
            creditOrderOperation.orderReturn(orderId01, B2bRefundOrder.BACKRETURNORDER02(orderId01));
        });
        Then("【仅占压超额度0303】产品集合金额应变动", (DataTable dataTable)->{
            Response productSetData = creditOrderOperation.queryProductSet(customerEntCode);
            creditAmountCheck.CheckProductSetData(dataTable, productSetData);
        });
        And("【仅占压超额度0303】品种金额应变动",(DataTable dataTable)-> {
            Response productVarietyData = creditOrderOperation.queryProductVariety(customerEntCode);
            creditAmountCheck.CheckProductVarietyData(dataTable, productVarietyData);
        });
        Then("【仅占压超额度0303】临时信用申请应变动", (DataTable dataTable)->{
            List<Map<String, Object>> sqlResult = selectTemporaryCredit(customerEntCode);
            creditAmountCheck.CheckTemporaryCreditData(dataTable, sqlResult);
        });
        And("【仅占压超额度0303】流水应变动", (DataTable dataTable)->{
            List<Map<String, Object>> sqlResult = selectCreditJournal(customerEntCode);
            creditAmountCheck.CheckCreditJounalData(dataTable, sqlResult);
        });
    }

    public void testcase04() {
        Given("【仅占压超额度0401】更新客户信用政策", (DataTable dataTable) -> {
            mysqlData.deleteCreditData("scripts/【清理信用历史数据】.sql");
            mysqlData.inserCreditData("scripts/【仅占压超额度测试数据】.sql");
            creditGradeId = dataTable.asMaps().get(0).get("CREDIT_GRADE_ID");
            overduePeriodDays = dataTable.asMaps().get(0).get("OVERDUE_PERIOD_DAYS");
            creditControlDimension = dataTable.asMaps().get(0).get("CREDIT_CONTROL_DIMENSION");
            mysqlData.updateEnterpriseGrade(overduePeriodDays, creditControlDimension, creditGradeId);
        });
        When("【仅占压超额度0401】客户在B2B下赊销订单",(DataTable dataTable)-> {
            customerEntCode = dataTable.asMaps().get(0).get("SALE_ORG_CODE");
            b2bOrderOperation.loginB2bFrontStageSystem();
            orderId01 = b2bOrderOperation.b2bFrontSystemOrder(B2bFrontOrder.FRONTORDER01);
            dataTable.asMaps().forEach(caseData ->{
                sku = caseData.get("SKU");
                itemId = caseData.get("ITEM_ID");
                mysqlData.updateOrderlines(itemId, orderId01, sku);
            });
        });
        Then("【仅占压超额度0401】商务审核应通过",()-> {
            b2bOrderOperation.loginB2bBackStageSystem();
            b2bOrderOperation.businessApproval(orderId01, B2bBackOrder.backOrder01(orderId01), "0","0");
        });
        And("【仅占压超额度0401】人工审核应通过",()-> {
            b2bOrderOperation.loginB2bBackStageSystem();
            b2bOrderOperation.manualApproval(orderId01, B2bBackOrder.backOrder01(orderId01));
        });
        Then("【仅占压超额度0401】产品集合金额应变动", (DataTable dataTable)->{
            Response productSetData = creditOrderOperation.queryProductSet(customerEntCode);
            creditAmountCheck.CheckProductSetData(dataTable, productSetData);
        });
        And("【仅占压超额度0401】品种金额应变动",(DataTable dataTable)-> {
            Response productVarietyData = creditOrderOperation.queryProductVariety(customerEntCode);
            creditAmountCheck.CheckProductVarietyData(dataTable, productVarietyData);
        });
        Then("【仅占压超额度0401】临时信用申请应变动", (DataTable dataTable)->{
            List<Map<String, Object>> sqlResult = selectTemporaryCredit(customerEntCode);
            creditAmountCheck.CheckTemporaryCreditData(dataTable, sqlResult);
        });
        And("【仅占压超额度0401】流水应变动", (DataTable dataTable)->{
            List<Map<String, Object>> sqlResult = selectCreditJournal(customerEntCode);
            creditAmountCheck.CheckCreditJounalData(dataTable, sqlResult);
        });
        When("【仅占压超额度0402】客户在B2B下赊销订单",(DataTable dataTable)-> {
            customerEntCode = dataTable.asMaps().get(0).get("SALE_ORG_CODE");
            b2bOrderOperation.loginB2bFrontStageSystem();
            orderId02 = b2bOrderOperation.b2bFrontSystemOrder(B2bFrontOrder.FRONTORDER02);
            dataTable.asMaps().forEach(caseData ->{
                sku = caseData.get("SKU");
                itemId = caseData.get("ITEM_ID");
                mysqlData.updateOrderlines(itemId, orderId02, sku);
            });
        });
        Then("【仅占压超额度0402】商务审核应通过",()-> {
            b2bOrderOperation.loginB2bBackStageSystem();
            b2bOrderOperation.businessApproval(orderId02, B2bBackOrder.backOrder02(orderId02), "0","0");
        });
        And("【仅占压超额度0402】人工审核应通过",()-> {
            b2bOrderOperation.loginB2bBackStageSystem();
            b2bOrderOperation.manualApproval(orderId02, B2bBackOrder.backOrder02(orderId02));
        });
        Then("【仅占压超额度0402】产品集合金额应变动", (DataTable dataTable)->{
            Response productSetData = creditOrderOperation.queryProductSet(customerEntCode);
            creditAmountCheck.CheckProductSetData(dataTable, productSetData);
        });
        And("【仅占压超额度0402】品种金额应变动",(DataTable dataTable)-> {
            Response productVarietyData = creditOrderOperation.queryProductVariety(customerEntCode);
            creditAmountCheck.CheckProductVarietyData(dataTable, productVarietyData);
        });
        Then("【仅占压超额度0402】临时信用申请应变动", (DataTable dataTable)->{
            List<Map<String, Object>> sqlResult = selectTemporaryCredit(customerEntCode);
            creditAmountCheck.CheckTemporaryCreditData(dataTable, sqlResult);
        });
        And("【仅占压超额度0402】流水应变动", (DataTable dataTable)->{
            List<Map<String, Object>> sqlResult = selectCreditJournal(customerEntCode);
            creditAmountCheck.CheckCreditJounalData(dataTable, sqlResult);
        });
        When("【仅占压超额度0403】客户在B2B退货",(DataTable dataTable)-> {
            creditOrderOperation.orderReturn(orderId01, B2bRefundOrder.BACKRETURNORDER02(orderId01));
        });
        Then("【仅占压超额度0403】产品集合金额应变动", (DataTable dataTable)->{
            Response productSetData = creditOrderOperation.queryProductSet(customerEntCode);
            creditAmountCheck.CheckProductSetData(dataTable, productSetData);
        });
        And("【仅占压超额度0403】品种金额应变动",(DataTable dataTable)-> {
            Response productVarietyData = creditOrderOperation.queryProductVariety(customerEntCode);
            creditAmountCheck.CheckProductVarietyData(dataTable, productVarietyData);
        });
        Then("【仅占压超额度0403】临时信用申请应变动", (DataTable dataTable)->{
            List<Map<String, Object>> sqlResult = selectTemporaryCredit(customerEntCode);
            creditAmountCheck.CheckTemporaryCreditData(dataTable, sqlResult);
        });
        And("【仅占压超额度0403】流水应变动", (DataTable dataTable)->{
            List<Map<String, Object>> sqlResult = selectCreditJournal(customerEntCode);
            creditAmountCheck.CheckCreditJounalData(dataTable, sqlResult);
        });
        And("【仅占压超额度0403】冻结产品集合中某品种",(DataTable dataTable)->{
            customerEntCode = dataTable.asMaps().get(0).get("customerEntCode");
            productVarietyCode = dataTable.asMaps().get(0).get("productVarietyCode");
            saleOrgCode = dataTable.asMaps().get(0).get("saleOrgCode");
            creditOrderOperation.disableProductVariety(customerEntCode, productVarietyCode, saleOrgCode);
        });
        When("【仅占压超额度0404】客户在B2B退货冲销",(DataTable dataTable)-> {
            creditOrderOperation.orderReturnWriteOff(orderId01, B2bRefundOrder.BACKRETURNWRITEOFFORDER02(orderId01));
        });
        Then("【仅占压超额度0404】产品集合金额应变动", (DataTable dataTable)->{
            Response productSetData = creditOrderOperation.queryProductSet(customerEntCode);
            creditAmountCheck.CheckProductSetData(dataTable, productSetData);
        });
        And("【仅占压超额度0404】品种金额应变动",(DataTable dataTable)-> {
            Response productVarietyData = creditOrderOperation.queryProductVariety(customerEntCode);
            creditAmountCheck.CheckProductVarietyData(dataTable, productVarietyData);
        });
        Then("【仅占压超额度0404】临时信用申请应变动", (DataTable dataTable)->{
            List<Map<String, Object>> sqlResult = selectTemporaryCredit(customerEntCode);
            creditAmountCheck.CheckTemporaryCreditData(dataTable, sqlResult);
        });
        And("【仅占压超额度0404】流水应变动", (DataTable dataTable)->{
            List<Map<String, Object>> sqlResult = selectCreditJournal(customerEntCode);
            creditAmountCheck.CheckCreditJounalData(dataTable, sqlResult);
        });
    }

    public void testcase05() {
        Given("【仅占压超额度0501】更新客户信用政策", (DataTable dataTable) -> {
            mysqlData.deleteCreditData("scripts/【清理信用历史数据】.sql");
            mysqlData.inserCreditData("scripts/【仅占压超额度测试数据】.sql");
            creditGradeId = dataTable.asMaps().get(0).get("CREDIT_GRADE_ID");
            overduePeriodDays = dataTable.asMaps().get(0).get("OVERDUE_PERIOD_DAYS");
            creditControlDimension = dataTable.asMaps().get(0).get("CREDIT_CONTROL_DIMENSION");
            mysqlData.updateEnterpriseGrade(overduePeriodDays, creditControlDimension, creditGradeId);
        });
        When("【仅占压超额度0501】客户在B2B下赊销订单",(DataTable dataTable)-> {
            customerEntCode = dataTable.asMaps().get(0).get("SALE_ORG_CODE");
            b2bOrderOperation.loginB2bFrontStageSystem();
            orderId01 = b2bOrderOperation.b2bFrontSystemOrder(B2bFrontOrder.FRONTORDER01);
            dataTable.asMaps().forEach(caseData ->{
                sku = caseData.get("SKU");
                itemId = caseData.get("ITEM_ID");
                mysqlData.updateOrderlines(itemId, orderId01, sku);
            });
        });
        Then("【仅占压超额度0501】商务审核应通过",()-> {
            b2bOrderOperation.loginB2bBackStageSystem();
            b2bOrderOperation.businessApproval(orderId01, B2bBackOrder.backOrder01(orderId01), "0","0");
        });
        And("【仅占压超额度0501】人工审核应通过",()-> {
            b2bOrderOperation.loginB2bBackStageSystem();
            b2bOrderOperation.manualApproval(orderId01, B2bBackOrder.backOrder01(orderId01));
        });
        Then("【仅占压超额度0501】产品集合金额应变动", (DataTable dataTable)->{
            Response productSetData = creditOrderOperation.queryProductSet(customerEntCode);
            creditAmountCheck.CheckProductSetData(dataTable, productSetData);
        });
        And("【仅占压超额度0501】品种金额应变动",(DataTable dataTable)-> {
            Response productVarietyData = creditOrderOperation.queryProductVariety(customerEntCode);
            creditAmountCheck.CheckProductVarietyData(dataTable, productVarietyData);
        });
        Then("【仅占压超额度0501】临时信用申请应变动", (DataTable dataTable)->{
            List<Map<String, Object>> sqlResult = selectTemporaryCredit(customerEntCode);
            creditAmountCheck.CheckTemporaryCreditData(dataTable, sqlResult);
        });
        And("【仅占压超额度0501】流水应变动", (DataTable dataTable)->{
            List<Map<String, Object>> sqlResult = selectCreditJournal(customerEntCode);
            creditAmountCheck.CheckCreditJounalData(dataTable, sqlResult);
        });
        When("【仅占压超额度0502】客户在B2B下赊销订单",(DataTable dataTable)-> {
            customerEntCode = dataTable.asMaps().get(0).get("SALE_ORG_CODE");
            b2bOrderOperation.loginB2bFrontStageSystem();
            orderId02 = b2bOrderOperation.b2bFrontSystemOrder(B2bFrontOrder.FRONTORDER02);
            dataTable.asMaps().forEach(caseData ->{
                sku = caseData.get("SKU");
                itemId = caseData.get("ITEM_ID");
                mysqlData.updateOrderlines(itemId, orderId02, sku);
            });
        });
        Then("【仅占压超额度0502】商务审核应通过",()-> {
            b2bOrderOperation.loginB2bBackStageSystem();
            b2bOrderOperation.businessApproval(orderId02, B2bBackOrder.backOrder02(orderId02), "0","0");
        });
        And("【仅占压超额度0502】人工审核应通过",()-> {
            b2bOrderOperation.loginB2bBackStageSystem();
            b2bOrderOperation.manualApproval(orderId02, B2bBackOrder.backOrder02(orderId02));
        });
        Then("【仅占压超额度0502】产品集合金额应变动", (DataTable dataTable)->{
            Response productSetData = creditOrderOperation.queryProductSet(customerEntCode);
            creditAmountCheck.CheckProductSetData(dataTable, productSetData);
        });
        And("【仅占压超额度0502】品种金额应变动",(DataTable dataTable)-> {
            Response productVarietyData = creditOrderOperation.queryProductVariety(customerEntCode);
            creditAmountCheck.CheckProductVarietyData(dataTable, productVarietyData);
        });
        Then("【仅占压超额度0502】临时信用申请应变动", (DataTable dataTable)->{
            List<Map<String, Object>> sqlResult = selectTemporaryCredit(customerEntCode);
            creditAmountCheck.CheckTemporaryCreditData(dataTable, sqlResult);
        });
        And("【仅占压超额度0502】流水应变动", (DataTable dataTable)->{
            List<Map<String, Object>> sqlResult = selectCreditJournal(customerEntCode);
            creditAmountCheck.CheckCreditJounalData(dataTable, sqlResult);
        });
        When("【仅占压超额度0503】客户在B2B退货",(DataTable dataTable)-> {
            creditOrderOperation.orderReturn(orderId01, B2bRefundOrder.BACKRETURNORDER02(orderId01));
        });
        Then("【仅占压超额度0503】产品集合金额应变动", (DataTable dataTable)->{
            Response productSetData = creditOrderOperation.queryProductSet(customerEntCode);
            creditAmountCheck.CheckProductSetData(dataTable, productSetData);
        });
        And("【仅占压超额度0503】品种金额应变动",(DataTable dataTable)-> {
            Response productVarietyData = creditOrderOperation.queryProductVariety(customerEntCode);
            creditAmountCheck.CheckProductVarietyData(dataTable, productVarietyData);
        });
        Then("【仅占压超额度0503】临时信用申请应变动", (DataTable dataTable)->{
            List<Map<String, Object>> sqlResult = selectTemporaryCredit(customerEntCode);
            creditAmountCheck.CheckTemporaryCreditData(dataTable, sqlResult);
        });
        And("【仅占压超额度0503】流水应变动", (DataTable dataTable)->{
            List<Map<String, Object>> sqlResult = selectCreditJournal(customerEntCode);
            creditAmountCheck.CheckCreditJounalData(dataTable, sqlResult);
        });
        When("【仅占压超额度0504】客户在B2B退货冲销",(DataTable dataTable)-> {
            creditOrderOperation.orderReturnWriteOff(orderId01, B2bRefundOrder.BACKRETURNWRITEOFFORDER02(orderId01));
        });
        Then("【仅占压超额度0504】产品集合金额应变动", (DataTable dataTable)->{
            Response productSetData = creditOrderOperation.queryProductSet(customerEntCode);
            creditAmountCheck.CheckProductSetData(dataTable, productSetData);
        });
        And("【仅占压超额度0504】品种金额应变动",(DataTable dataTable)-> {
            Response productVarietyData = creditOrderOperation.queryProductVariety(customerEntCode);
            creditAmountCheck.CheckProductVarietyData(dataTable, productVarietyData);
        });
        Then("【仅占压超额度0504】临时信用申请应变动", (DataTable dataTable)->{
            List<Map<String, Object>> sqlResult = selectTemporaryCredit(customerEntCode);
            creditAmountCheck.CheckTemporaryCreditData(dataTable, sqlResult);
        });
        And("【仅占压超额度0504】流水应变动", (DataTable dataTable)->{
            List<Map<String, Object>> sqlResult = selectCreditJournal(customerEntCode);
            creditAmountCheck.CheckCreditJounalData(dataTable, sqlResult);
        });
        When("【仅占压超额度0505】月度结转",()-> {
            mysqlData.updateExecutionMonth(TimeUtil.lastMonthRandomDate(), customerEntCode);
            creditOrderOperation.MonthTransfer();
        });
        Then("【仅占压超额度0505】产品集合金额应变动", (DataTable dataTable)->{
            Response productSetData = creditOrderOperation.queryProductSet(customerEntCode);
            creditAmountCheck.CheckProductSetData(dataTable, productSetData);
        });
        And("【仅占压超额度0505】品种金额应变动",(DataTable dataTable)-> {
            Response productVarietyData = creditOrderOperation.queryProductVariety(customerEntCode);
            creditAmountCheck.CheckProductVarietyData(dataTable, productVarietyData);
        });
        And("【仅占压超额度0505】流水应变动", (DataTable dataTable)->{
            List<Map<String, Object>> sqlResult = selectCreditJournal(customerEntCode);
            creditAmountCheck.CheckCreditJounalData(dataTable, sqlResult);
        });
    }

    public void testcase06() {
        Given("【仅占压超额度0601】更新客户信用政策", (DataTable dataTable) -> {
            mysqlData.deleteCreditData("scripts/【清理信用历史数据】.sql");
            mysqlData.inserCreditData("scripts/【仅占压超额度测试数据】.sql");
            creditGradeId = dataTable.asMaps().get(0).get("CREDIT_GRADE_ID");
            overduePeriodDays = dataTable.asMaps().get(0).get("OVERDUE_PERIOD_DAYS");
            creditControlDimension = dataTable.asMaps().get(0).get("CREDIT_CONTROL_DIMENSION");
            mysqlData.updateEnterpriseGrade(overduePeriodDays, creditControlDimension, creditGradeId);
        });
        When("【仅占压超额度0601】客户在B2B下赊销订单",(DataTable dataTable)-> {
            customerEntCode = dataTable.asMaps().get(0).get("SALE_ORG_CODE");
            b2bOrderOperation.loginB2bFrontStageSystem();
            orderId01 = b2bOrderOperation.b2bFrontSystemOrder(B2bFrontOrder.FRONTORDER01);
            dataTable.asMaps().forEach(caseData ->{
                sku = caseData.get("SKU");
                itemId = caseData.get("ITEM_ID");
                mysqlData.updateOrderlines(itemId, orderId01, sku);
            });
        });
        Then("【仅占压超额度0601】商务审核应通过",()-> {
            b2bOrderOperation.loginB2bBackStageSystem();
            b2bOrderOperation.businessApproval(orderId01, B2bBackOrder.backOrder01(orderId01), "0","0");
        });
        And("【仅占压超额度0601】人工审核应通过",()-> {
            b2bOrderOperation.loginB2bBackStageSystem();
            b2bOrderOperation.manualApproval(orderId01, B2bBackOrder.backOrder01(orderId01));
        });
        Then("【仅占压超额度0601】产品集合金额应变动", (DataTable dataTable)->{
            Response productSetData = creditOrderOperation.queryProductSet(customerEntCode);
            creditAmountCheck.CheckProductSetData(dataTable, productSetData);
        });
        And("【仅占压超额度0601】品种金额应变动",(DataTable dataTable)-> {
            Response productVarietyData = creditOrderOperation.queryProductVariety(customerEntCode);
            creditAmountCheck.CheckProductVarietyData(dataTable, productVarietyData);
        });
        Then("【仅占压超额度0601】临时信用申请应变动", (DataTable dataTable)->{
            List<Map<String, Object>> sqlResult = selectTemporaryCredit(customerEntCode);
            creditAmountCheck.CheckTemporaryCreditData(dataTable, sqlResult);
        });
        And("【仅占压超额度0601】流水应变动", (DataTable dataTable)->{
            List<Map<String, Object>> sqlResult = selectCreditJournal(customerEntCode);
            creditAmountCheck.CheckCreditJounalData(dataTable, sqlResult);
        });
        When("【仅占压超额度0602】客户在B2B下赊销订单",(DataTable dataTable)-> {
            customerEntCode = dataTable.asMaps().get(0).get("SALE_ORG_CODE");
            b2bOrderOperation.loginB2bFrontStageSystem();
            orderId02 = b2bOrderOperation.b2bFrontSystemOrder(B2bFrontOrder.FRONTORDER02);
            dataTable.asMaps().forEach(caseData ->{
                sku = caseData.get("SKU");
                itemId = caseData.get("ITEM_ID");
                mysqlData.updateOrderlines(itemId, orderId02, sku);
            });
        });
        Then("【仅占压超额度0602】商务审核应通过",()-> {
            b2bOrderOperation.loginB2bBackStageSystem();
            b2bOrderOperation.businessApproval(orderId02, B2bBackOrder.backOrder02(orderId02), "0","0");
        });
        And("【仅占压超额度0602】人工审核应通过",()-> {
            b2bOrderOperation.loginB2bBackStageSystem();
            b2bOrderOperation.manualApproval(orderId02, B2bBackOrder.backOrder02(orderId02));
        });
        Then("【仅占压超额度0602】产品集合金额应变动", (DataTable dataTable)->{
            Response productSetData = creditOrderOperation.queryProductSet(customerEntCode);
            creditAmountCheck.CheckProductSetData(dataTable, productSetData);
        });
        And("【仅占压超额度0602】品种金额应变动",(DataTable dataTable)-> {
            Response productVarietyData = creditOrderOperation.queryProductVariety(customerEntCode);
            creditAmountCheck.CheckProductVarietyData(dataTable, productVarietyData);
        });
        Then("【仅占压超额度0602】临时信用申请应变动", (DataTable dataTable)->{
            List<Map<String, Object>> sqlResult = selectTemporaryCredit(customerEntCode);
            creditAmountCheck.CheckTemporaryCreditData(dataTable, sqlResult);
        });
        And("【仅占压超额度0602】流水应变动", (DataTable dataTable)->{
            List<Map<String, Object>> sqlResult = selectCreditJournal(customerEntCode);
            creditAmountCheck.CheckCreditJounalData(dataTable, sqlResult);
        });
        When("【仅占压超额度0603】月度结转",()-> {
            mysqlData.updateExecutionMonth(TimeUtil.lastMonthRandomDate(), customerEntCode);
            creditOrderOperation.MonthTransfer();
        });
        Then("【仅占压超额度0603】产品集合金额应变动", (DataTable dataTable)->{
            Response productSetData = creditOrderOperation.queryProductSet(customerEntCode);
            creditAmountCheck.CheckProductSetData(dataTable, productSetData);
        });
        And("【仅占压超额度0603】品种金额应变动",(DataTable dataTable)-> {
            Response productVarietyData = creditOrderOperation.queryProductVariety(customerEntCode);
            creditAmountCheck.CheckProductVarietyData(dataTable, productVarietyData);
        });
        And("【仅占压超额度0603】流水应变动", (DataTable dataTable)->{
            List<Map<String, Object>> sqlResult = selectCreditJournal(customerEntCode);
            creditAmountCheck.CheckCreditJounalData(dataTable, sqlResult);
        });
        When("【仅占压超额度0604】客户在B2B退货",(DataTable dataTable)-> {
            creditOrderOperation.orderReturn(orderId01, B2bRefundOrder.BACKRETURNORDER02(orderId01));
        });
        Then("【仅占压超额度0604】产品集合金额应变动", (DataTable dataTable)->{
            Response productSetData = creditOrderOperation.queryProductSet(customerEntCode);
            creditAmountCheck.CheckProductSetData(dataTable, productSetData);
        });
        And("【仅占压超额度0604】品种金额应变动",(DataTable dataTable)-> {
            Response productVarietyData = creditOrderOperation.queryProductVariety(customerEntCode);
            creditAmountCheck.CheckProductVarietyData(dataTable, productVarietyData);
        });
        And("【仅占压超额度0604】流水应变动", (DataTable dataTable)->{
            List<Map<String, Object>> sqlResult = selectCreditJournal(customerEntCode);
            creditAmountCheck.CheckCreditJounalData(dataTable, sqlResult);
        });
        When("【仅占压超额度0605】客户在B2B退货冲销",(DataTable dataTable)-> {
            creditOrderOperation.orderReturnWriteOff(orderId01, B2bRefundOrder.BACKRETURNWRITEOFFORDER02(orderId01));
        });
        Then("【仅占压超额度0605】产品集合金额应变动", (DataTable dataTable)->{
            Response productSetData = creditOrderOperation.queryProductSet(customerEntCode);
            creditAmountCheck.CheckProductSetData(dataTable, productSetData);
        });
        And("【仅占压超额度0605】品种金额应变动",(DataTable dataTable)-> {
            Response productVarietyData = creditOrderOperation.queryProductVariety(customerEntCode);
            creditAmountCheck.CheckProductVarietyData(dataTable, productVarietyData);
        });
        And("【仅占压超额度0605】流水应变动", (DataTable dataTable)->{
            List<Map<String, Object>> sqlResult = selectCreditJournal(customerEntCode);
            creditAmountCheck.CheckCreditJounalData(dataTable, sqlResult);
        });
    }

}
