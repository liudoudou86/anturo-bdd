package com.qa.credit.release.steps;

import com.qa.operate.check.AccountPeriodCheck;
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
import net.thucydides.core.annotations.Steps;
import io.cucumber.java8.En;

import java.util.List;
import java.util.Map;

import static com.qa.data.MysqlData.selectCreditJournal;
import static com.qa.data.MysqlData.selectTemporaryCredit;

/**
 * @author Tesla Liu
 * @date 2022/12/01 16:43
 * 描述 仅占压超账期 - 逆向返还
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class BackwardUseOnlyAccountPeriodSteps implements En{
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
    AccountPeriodCheck creditAmountCheck;

    public BackwardUseOnlyAccountPeriodSteps() {
        this.testcase02();
        this.testcase03();
        this.testcase04();
        this.testcase05();
    }

    public void testcase02() {
        Given("【仅占压超账期0201】更新客户信用政策", (DataTable dataTable) -> {
            mysqlData.deleteCreditData("scripts/【清理信用历史数据】.sql");
            mysqlData.inserCreditData("scripts/【仅占压超账期测试数据】.sql");
            creditGradeId = dataTable.asMaps().get(0).get("CREDIT_GRADE_ID");
            overduePeriodDays = dataTable.asMaps().get(0).get("OVERDUE_PERIOD_DAYS");
            creditControlDimension = dataTable.asMaps().get(0).get("CREDIT_CONTROL_DIMENSION");
            mysqlData.updateEnterpriseGrade(overduePeriodDays, creditControlDimension, creditGradeId);
        });
        When("【仅占压超账期0201】客户在B2B下赊销订单",(DataTable dataTable)-> {
            customerEntCode = dataTable.asMaps().get(0).get("SALE_ORG_CODE");
            b2bOrderOperation.loginB2bFrontStageSystem();
            orderId01 = b2bOrderOperation.b2bFrontSystemOrder(B2bFrontOrder.FRONTORDER01);
            dataTable.asMaps().forEach(caseData ->{
                sku = caseData.get("SKU");
                itemId = caseData.get("ITEM_ID");
                mysqlData.updateOrderlines(itemId, orderId01, sku);
            });
        });
        Then("【仅占压超账期0201】商务审核应通过",()-> {
            b2bOrderOperation.loginB2bBackStageSystem();
            // 2为信用类型
            b2bOrderOperation.businessApproval(orderId01, B2bBackOrder.backOrder01(orderId01), "0","0");
        });
        And("【仅占压超账期0201】人工审核应通过",()-> {
            b2bOrderOperation.loginB2bBackStageSystem();
            b2bOrderOperation.manualApproval(orderId01, B2bBackOrder.backOrder01(orderId01));
        });
        Then("【仅占压超账期0201】产品集合金额应变动", (DataTable dataTable)->{
            Response productSetData = creditOrderOperation.queryProductSet(customerEntCode);
            creditAmountCheck.CheckProductSetData(dataTable, productSetData);
        });
        And("【仅占压超账期0201】品种金额应变动",(DataTable dataTable)-> {
            Response productVarietyData = creditOrderOperation.queryProductVariety(customerEntCode);
            creditAmountCheck.CheckProductVarietyData(dataTable, productVarietyData);
        });
        Then("【仅占压超账期0201】临时信用申请应变动", (DataTable dataTable)->{
            List<Map<String, Object>> sqlResult = selectTemporaryCredit(customerEntCode);
            creditAmountCheck.CheckTemporaryCreditData(dataTable, sqlResult);
        });
        And("【仅占压超账期0201】流水应变动", (DataTable dataTable)->{
            List<Map<String, Object>> sqlResult = selectCreditJournal(customerEntCode);
            creditAmountCheck.CheckCreditJounalData(dataTable, sqlResult);
        });
        When("【仅占压超账期0202】客户在B2B下赊销订单",(DataTable dataTable)-> {
            customerEntCode = dataTable.asMaps().get(0).get("SALE_ORG_CODE");
            b2bOrderOperation.loginB2bFrontStageSystem();
            orderId02 = b2bOrderOperation.b2bFrontSystemOrder(B2bFrontOrder.FRONTORDER02);
            dataTable.asMaps().forEach(caseData ->{
                sku = caseData.get("SKU");
                itemId = caseData.get("ITEM_ID");
                mysqlData.updateOrderlines(itemId, orderId02, sku);
            });
        });
        Then("【仅占压超账期0202】商务审核应通过",()-> {
            b2bOrderOperation.loginB2bBackStageSystem();
            b2bOrderOperation.businessApproval(orderId02, B2bBackOrder.backOrder02(orderId02), "0","0");
        });
        And("【仅占压超账期0202】人工审核应通过",()-> {
            b2bOrderOperation.loginB2bBackStageSystem();
            b2bOrderOperation.manualApproval(orderId02, B2bBackOrder.backOrder02(orderId02));
        });
        Then("【仅占压超账期0202】产品集合金额应变动", (DataTable dataTable)->{
            Response productSetData = creditOrderOperation.queryProductSet(customerEntCode);
            creditAmountCheck.CheckProductSetData(dataTable, productSetData);
        });
        And("【仅占压超账期0202】品种金额应变动",(DataTable dataTable)-> {
            Response productVarietyData = creditOrderOperation.queryProductVariety(customerEntCode);
            creditAmountCheck.CheckProductVarietyData(dataTable, productVarietyData);
        });
        Then("【仅占压超账期0202】临时信用申请应变动", (DataTable dataTable)->{
            List<Map<String, Object>> sqlResult = selectTemporaryCredit(customerEntCode);
            creditAmountCheck.CheckTemporaryCreditData(dataTable, sqlResult);
        });
        And("【仅占压超账期0202】流水应变动", (DataTable dataTable)->{
            List<Map<String, Object>> sqlResult = selectCreditJournal(customerEntCode);
            creditAmountCheck.CheckCreditJounalData(dataTable, sqlResult);
        });
        And("【仅占压超账期0202】冻结产品集合中某品种",(DataTable dataTable)->{
            customerEntCode = dataTable.asMaps().get(0).get("customerEntCode");
            productVarietyCode = dataTable.asMaps().get(0).get("productVarietyCode");
            saleOrgCode = dataTable.asMaps().get(0).get("saleOrgCode");
            creditOrderOperation.disableProductVariety(customerEntCode, productVarietyCode, saleOrgCode);
        });
        When("【仅占压超账期0203】客户在B2B退货",(DataTable dataTable)-> {
            creditOrderOperation.orderReturn(orderId01, B2bRefundOrder.BACKRETURNORDER02(orderId01));
        });
        Then("【仅占压超账期0203】产品集合金额应变动", (DataTable dataTable)->{
            Response productSetData = creditOrderOperation.queryProductSet(customerEntCode);
            creditAmountCheck.CheckProductSetData(dataTable, productSetData);
        });
        And("【仅占压超账期0203】品种金额应变动",(DataTable dataTable)-> {
            Response productVarietyData = creditOrderOperation.queryProductVariety(customerEntCode);
            creditAmountCheck.CheckProductVarietyData(dataTable, productVarietyData);
        });
        Then("【仅占压超账期0203】临时信用申请应变动", (DataTable dataTable)->{
            List<Map<String, Object>> sqlResult = selectTemporaryCredit(customerEntCode);
            creditAmountCheck.CheckTemporaryCreditData(dataTable, sqlResult);
        });
        And("【仅占压超账期0203】流水应变动", (DataTable dataTable)->{
            List<Map<String, Object>> sqlResult = selectCreditJournal(customerEntCode);
            creditAmountCheck.CheckCreditJounalData(dataTable, sqlResult);
        });
    }

    public void testcase03() {
        Given("【仅占压超账期0301】更新客户信用政策", (DataTable dataTable) -> {
            mysqlData.deleteCreditData("scripts/【清理信用历史数据】.sql");
            mysqlData.inserCreditData("scripts/【仅占压超账期测试数据】.sql");
            creditGradeId = dataTable.asMaps().get(0).get("CREDIT_GRADE_ID");
            overduePeriodDays = dataTable.asMaps().get(0).get("OVERDUE_PERIOD_DAYS");
            creditControlDimension = dataTable.asMaps().get(0).get("CREDIT_CONTROL_DIMENSION");
            mysqlData.updateEnterpriseGrade(overduePeriodDays, creditControlDimension, creditGradeId);
        });
        When("【仅占压超账期0301】客户在B2B下赊销订单",(DataTable dataTable)-> {
            customerEntCode = dataTable.asMaps().get(0).get("SALE_ORG_CODE");
            b2bOrderOperation.loginB2bFrontStageSystem();
            orderId01 = b2bOrderOperation.b2bFrontSystemOrder(B2bFrontOrder.FRONTORDER01);
            dataTable.asMaps().forEach(caseData ->{
                sku = caseData.get("SKU");
                itemId = caseData.get("ITEM_ID");
                mysqlData.updateOrderlines(itemId, orderId01, sku);
            });
        });
        Then("【仅占压超账期0301】商务审核应通过",()-> {
            b2bOrderOperation.loginB2bBackStageSystem();
            // 2为信用类型
            b2bOrderOperation.businessApproval(orderId01, B2bBackOrder.backOrder01(orderId01), "0","0");
        });
        And("【仅占压超账期0301】人工审核应通过",()-> {
            b2bOrderOperation.loginB2bBackStageSystem();
            b2bOrderOperation.manualApproval(orderId01, B2bBackOrder.backOrder01(orderId01));
        });
        Then("【仅占压超账期0301】产品集合金额应变动", (DataTable dataTable)->{
            Response productSetData = creditOrderOperation.queryProductSet(customerEntCode);
            creditAmountCheck.CheckProductSetData(dataTable, productSetData);
        });
        And("【仅占压超账期0301】品种金额应变动",(DataTable dataTable)-> {
            Response productVarietyData = creditOrderOperation.queryProductVariety(customerEntCode);
            creditAmountCheck.CheckProductVarietyData(dataTable, productVarietyData);
        });
        Then("【仅占压超账期0301】临时信用申请应变动", (DataTable dataTable)->{
            List<Map<String, Object>> sqlResult = selectTemporaryCredit(customerEntCode);
            creditAmountCheck.CheckTemporaryCreditData(dataTable, sqlResult);
        });
        And("【仅占压超账期0301】流水应变动", (DataTable dataTable)->{
            List<Map<String, Object>> sqlResult = selectCreditJournal(customerEntCode);
            creditAmountCheck.CheckCreditJounalData(dataTable, sqlResult);
        });
        When("【仅占压超账期0302】客户在B2B下赊销订单",(DataTable dataTable)-> {
            customerEntCode = dataTable.asMaps().get(0).get("SALE_ORG_CODE");
            b2bOrderOperation.loginB2bFrontStageSystem();
            orderId02 = b2bOrderOperation.b2bFrontSystemOrder(B2bFrontOrder.FRONTORDER02);
            dataTable.asMaps().forEach(caseData ->{
                sku = caseData.get("SKU");
                itemId = caseData.get("ITEM_ID");
                mysqlData.updateOrderlines(itemId, orderId02, sku);
            });
        });
        Then("【仅占压超账期0302】商务审核应通过",()-> {
            b2bOrderOperation.loginB2bBackStageSystem();
            b2bOrderOperation.businessApproval(orderId02, B2bBackOrder.backOrder02(orderId02), "0","0");
        });
        And("【仅占压超账期0302】人工审核应通过",()-> {
            b2bOrderOperation.loginB2bBackStageSystem();
            b2bOrderOperation.manualApproval(orderId02, B2bBackOrder.backOrder02(orderId02));
        });
        Then("【仅占压超账期0302】产品集合金额应变动", (DataTable dataTable)->{
            Response productSetData = creditOrderOperation.queryProductSet(customerEntCode);
            creditAmountCheck.CheckProductSetData(dataTable, productSetData);
        });
        And("【仅占压超账期0302】品种金额应变动",(DataTable dataTable)-> {
            Response productVarietyData = creditOrderOperation.queryProductVariety(customerEntCode);
            creditAmountCheck.CheckProductVarietyData(dataTable, productVarietyData);
        });
        Then("【仅占压超账期0302】临时信用申请应变动", (DataTable dataTable)->{
            List<Map<String, Object>> sqlResult = selectTemporaryCredit(customerEntCode);
            creditAmountCheck.CheckTemporaryCreditData(dataTable, sqlResult);
        });
        And("【仅占压超账期0302】流水应变动", (DataTable dataTable)->{
            List<Map<String, Object>> sqlResult = selectCreditJournal(customerEntCode);
            creditAmountCheck.CheckCreditJounalData(dataTable, sqlResult);
        });
        When("【仅占压超账期0303】客户在B2B退货",(DataTable dataTable)-> {
            creditOrderOperation.orderReturn(orderId01, B2bRefundOrder.BACKRETURNORDER02(orderId01));
        });
        Then("【仅占压超账期0303】产品集合金额应变动", (DataTable dataTable)->{
            Response productSetData = creditOrderOperation.queryProductSet(customerEntCode);
            creditAmountCheck.CheckProductSetData(dataTable, productSetData);
        });
        And("【仅占压超账期0303】品种金额应变动",(DataTable dataTable)-> {
            Response productVarietyData = creditOrderOperation.queryProductVariety(customerEntCode);
            creditAmountCheck.CheckProductVarietyData(dataTable, productVarietyData);
        });
        Then("【仅占压超账期0303】临时信用申请应变动", (DataTable dataTable)->{
            List<Map<String, Object>> sqlResult = selectTemporaryCredit(customerEntCode);
            creditAmountCheck.CheckTemporaryCreditData(dataTable, sqlResult);
        });
        And("【仅占压超账期0303】流水应变动", (DataTable dataTable)->{
            List<Map<String, Object>> sqlResult = selectCreditJournal(customerEntCode);
            creditAmountCheck.CheckCreditJounalData(dataTable, sqlResult);
        });
        And("【仅占压超账期0303】冻结产品集合中某品种",(DataTable dataTable)->{
            customerEntCode = dataTable.asMaps().get(0).get("customerEntCode");
            productVarietyCode = dataTable.asMaps().get(0).get("productVarietyCode");
            saleOrgCode = dataTable.asMaps().get(0).get("saleOrgCode");
            creditOrderOperation.disableProductVariety(customerEntCode, productVarietyCode, saleOrgCode);
        });
        When("【仅占压超账期0304】客户在B2B退货冲销",(DataTable dataTable)-> {
            creditOrderOperation.orderReturnWriteOff(orderId01, B2bRefundOrder.BACKRETURNWRITEOFFORDER02(orderId01));
        });
        Then("【仅占压超账期0304】产品集合金额应变动", (DataTable dataTable)->{
            Response productSetData = creditOrderOperation.queryProductSet(customerEntCode);
            creditAmountCheck.CheckProductSetData(dataTable, productSetData);
        });
        And("【仅占压超账期0304】品种金额应变动",(DataTable dataTable)-> {
            Response productVarietyData = creditOrderOperation.queryProductVariety(customerEntCode);
            creditAmountCheck.CheckProductVarietyData(dataTable, productVarietyData);
        });
        Then("【仅占压超账期0304】临时信用申请应变动", (DataTable dataTable)->{
            List<Map<String, Object>> sqlResult = selectTemporaryCredit(customerEntCode);
            creditAmountCheck.CheckTemporaryCreditData(dataTable, sqlResult);
        });
        And("【仅占压超账期0304】流水应变动", (DataTable dataTable)->{
            List<Map<String, Object>> sqlResult = selectCreditJournal(customerEntCode);
            creditAmountCheck.CheckCreditJounalData(dataTable, sqlResult);
        });
    }

    public void testcase04() {
        Given("【仅占压超账期0401】更新客户信用政策", (DataTable dataTable) -> {
            mysqlData.deleteCreditData("scripts/【清理信用历史数据】.sql");
            mysqlData.inserCreditData("scripts/【仅占压超账期测试数据】.sql");
            creditGradeId = dataTable.asMaps().get(0).get("CREDIT_GRADE_ID");
            overduePeriodDays = dataTable.asMaps().get(0).get("OVERDUE_PERIOD_DAYS");
            creditControlDimension = dataTable.asMaps().get(0).get("CREDIT_CONTROL_DIMENSION");
            mysqlData.updateEnterpriseGrade(overduePeriodDays, creditControlDimension, creditGradeId);
        });
        When("【仅占压超账期0401】客户在B2B下赊销订单",(DataTable dataTable)-> {
            customerEntCode = dataTable.asMaps().get(0).get("SALE_ORG_CODE");
            b2bOrderOperation.loginB2bFrontStageSystem();
            orderId01 = b2bOrderOperation.b2bFrontSystemOrder(B2bFrontOrder.FRONTORDER01);
            dataTable.asMaps().forEach(caseData ->{
                sku = caseData.get("SKU");
                itemId = caseData.get("ITEM_ID");
                mysqlData.updateOrderlines(itemId, orderId01, sku);
            });
        });
        Then("【仅占压超账期0401】商务审核应通过",()-> {
            b2bOrderOperation.loginB2bBackStageSystem();
            // 2为信用类型
            b2bOrderOperation.businessApproval(orderId01, B2bBackOrder.backOrder01(orderId01), "0","0");
        });
        And("【仅占压超账期0401】人工审核应通过",()-> {
            b2bOrderOperation.loginB2bBackStageSystem();
            b2bOrderOperation.manualApproval(orderId01, B2bBackOrder.backOrder01(orderId01));
        });
        Then("【仅占压超账期0401】产品集合金额应变动", (DataTable dataTable)->{
            Response productSetData = creditOrderOperation.queryProductSet(customerEntCode);
            creditAmountCheck.CheckProductSetData(dataTable, productSetData);
        });
        And("【仅占压超账期0401】品种金额应变动",(DataTable dataTable)-> {
            Response productVarietyData = creditOrderOperation.queryProductVariety(customerEntCode);
            creditAmountCheck.CheckProductVarietyData(dataTable, productVarietyData);
        });
        Then("【仅占压超账期0401】临时信用申请应变动", (DataTable dataTable)->{
            List<Map<String, Object>> sqlResult = selectTemporaryCredit(customerEntCode);
            creditAmountCheck.CheckTemporaryCreditData(dataTable, sqlResult);
        });
        And("【仅占压超账期0401】流水应变动", (DataTable dataTable)->{
            List<Map<String, Object>> sqlResult = selectCreditJournal(customerEntCode);
            creditAmountCheck.CheckCreditJounalData(dataTable, sqlResult);
        });
        When("【仅占压超账期0402】客户在B2B下赊销订单",(DataTable dataTable)-> {
            customerEntCode = dataTable.asMaps().get(0).get("SALE_ORG_CODE");
            b2bOrderOperation.loginB2bFrontStageSystem();
            orderId02 = b2bOrderOperation.b2bFrontSystemOrder(B2bFrontOrder.FRONTORDER02);
            dataTable.asMaps().forEach(caseData ->{
                sku = caseData.get("SKU");
                itemId = caseData.get("ITEM_ID");
                mysqlData.updateOrderlines(itemId, orderId02, sku);
            });
        });
        Then("【仅占压超账期0402】商务审核应通过",()-> {
            b2bOrderOperation.loginB2bBackStageSystem();
            b2bOrderOperation.businessApproval(orderId02, B2bBackOrder.backOrder02(orderId02), "0","0");
        });
        And("【仅占压超账期0402】人工审核应通过",()-> {
            b2bOrderOperation.loginB2bBackStageSystem();
            b2bOrderOperation.manualApproval(orderId02, B2bBackOrder.backOrder02(orderId02));
        });
        Then("【仅占压超账期0402】产品集合金额应变动", (DataTable dataTable)->{
            Response productSetData = creditOrderOperation.queryProductSet(customerEntCode);
            creditAmountCheck.CheckProductSetData(dataTable, productSetData);
        });
        And("【仅占压超账期0402】品种金额应变动",(DataTable dataTable)-> {
            Response productVarietyData = creditOrderOperation.queryProductVariety(customerEntCode);
            creditAmountCheck.CheckProductVarietyData(dataTable, productVarietyData);
        });
        Then("【仅占压超账期0402】临时信用申请应变动", (DataTable dataTable)->{
            List<Map<String, Object>> sqlResult = selectTemporaryCredit(customerEntCode);
            creditAmountCheck.CheckTemporaryCreditData(dataTable, sqlResult);
        });
        And("【仅占压超账期0402】流水应变动", (DataTable dataTable)->{
            List<Map<String, Object>> sqlResult = selectCreditJournal(customerEntCode);
            creditAmountCheck.CheckCreditJounalData(dataTable, sqlResult);
        });
        When("【仅占压超账期0403】客户在B2B退货",(DataTable dataTable)-> {
            creditOrderOperation.orderReturn(orderId01, B2bRefundOrder.BACKRETURNORDER02(orderId01));
        });
        Then("【仅占压超账期0403】产品集合金额应变动", (DataTable dataTable)->{
            Response productSetData = creditOrderOperation.queryProductSet(customerEntCode);
            creditAmountCheck.CheckProductSetData(dataTable, productSetData);
        });
        And("【仅占压超账期0403】品种金额应变动",(DataTable dataTable)-> {
            Response productVarietyData = creditOrderOperation.queryProductVariety(customerEntCode);
            creditAmountCheck.CheckProductVarietyData(dataTable, productVarietyData);
        });
        Then("【仅占压超账期0403】临时信用申请应变动", (DataTable dataTable)->{
            List<Map<String, Object>> sqlResult = selectTemporaryCredit(customerEntCode);
            creditAmountCheck.CheckTemporaryCreditData(dataTable, sqlResult);
        });
        And("【仅占压超账期0403】流水应变动", (DataTable dataTable)->{
            List<Map<String, Object>> sqlResult = selectCreditJournal(customerEntCode);
            creditAmountCheck.CheckCreditJounalData(dataTable, sqlResult);
        });
        When("【仅占压超账期0404】客户在B2B退货冲销",(DataTable dataTable)-> {
            creditOrderOperation.orderReturnWriteOff(orderId01, B2bRefundOrder.BACKRETURNWRITEOFFORDER02(orderId01));
        });
        Then("【仅占压超账期0404】产品集合金额应变动", (DataTable dataTable)->{
            Response productSetData = creditOrderOperation.queryProductSet(customerEntCode);
            creditAmountCheck.CheckProductSetData(dataTable, productSetData);
        });
        And("【仅占压超账期0404】品种金额应变动",(DataTable dataTable)-> {
            Response productVarietyData = creditOrderOperation.queryProductVariety(customerEntCode);
            creditAmountCheck.CheckProductVarietyData(dataTable, productVarietyData);
        });
        Then("【仅占压超账期0404】临时信用申请应变动", (DataTable dataTable)->{
            List<Map<String, Object>> sqlResult = selectTemporaryCredit(customerEntCode);
            creditAmountCheck.CheckTemporaryCreditData(dataTable, sqlResult);
        });
        And("【仅占压超账期0404】流水应变动", (DataTable dataTable)->{
            List<Map<String, Object>> sqlResult = selectCreditJournal(customerEntCode);
            creditAmountCheck.CheckCreditJounalData(dataTable, sqlResult);
        });
        When("【仅占压超账期0405】月度结转",()-> {
            mysqlData.updateExecutionMonth(TimeUtil.lastMonthRandomDate(), customerEntCode);
            creditOrderOperation.MonthTransfer();
        });
        Then("【仅占压超账期0405】产品集合金额应变动", (DataTable dataTable)->{
            Response productSetData = creditOrderOperation.queryProductSet(customerEntCode);
            creditAmountCheck.CheckProductSetData(dataTable, productSetData);
        });
        And("【仅占压超账期0405】品种金额应变动",(DataTable dataTable)-> {
            Response productVarietyData = creditOrderOperation.queryProductVariety(customerEntCode);
            creditAmountCheck.CheckProductVarietyData(dataTable, productVarietyData);
        });
        And("【仅占压超账期0405】流水应变动", (DataTable dataTable)->{
            List<Map<String, Object>> sqlResult = selectCreditJournal(customerEntCode);
            creditAmountCheck.CheckCreditJounalData(dataTable, sqlResult);
        });
    }

    public void testcase05() {
        Given("【仅占压超账期0501】更新客户信用政策", (DataTable dataTable) -> {
            mysqlData.deleteCreditData("scripts/【清理信用历史数据】.sql");
            mysqlData.inserCreditData("scripts/【仅占压超账期测试数据】.sql");
            creditGradeId = dataTable.asMaps().get(0).get("CREDIT_GRADE_ID");
            overduePeriodDays = dataTable.asMaps().get(0).get("OVERDUE_PERIOD_DAYS");
            creditControlDimension = dataTable.asMaps().get(0).get("CREDIT_CONTROL_DIMENSION");
            mysqlData.updateEnterpriseGrade(overduePeriodDays, creditControlDimension, creditGradeId);
        });
        When("【仅占压超账期0501】客户在B2B下赊销订单",(DataTable dataTable)-> {
            customerEntCode = dataTable.asMaps().get(0).get("SALE_ORG_CODE");
            b2bOrderOperation.loginB2bFrontStageSystem();
            orderId01 = b2bOrderOperation.b2bFrontSystemOrder(B2bFrontOrder.FRONTORDER01);
            dataTable.asMaps().forEach(caseData ->{
                sku = caseData.get("SKU");
                itemId = caseData.get("ITEM_ID");
                mysqlData.updateOrderlines(itemId, orderId01, sku);
            });
        });
        Then("【仅占压超账期0501】商务审核应通过",()-> {
            b2bOrderOperation.loginB2bBackStageSystem();
            // 2为信用类型
            b2bOrderOperation.businessApproval(orderId01, B2bBackOrder.backOrder01(orderId01), "0","0");
        });
        And("【仅占压超账期0501】人工审核应通过",()-> {
            b2bOrderOperation.loginB2bBackStageSystem();
            b2bOrderOperation.manualApproval(orderId01, B2bBackOrder.backOrder01(orderId01));
        });
        Then("【仅占压超账期0501】产品集合金额应变动", (DataTable dataTable)->{
            Response productSetData = creditOrderOperation.queryProductSet(customerEntCode);
            creditAmountCheck.CheckProductSetData(dataTable, productSetData);
        });
        And("【仅占压超账期0501】品种金额应变动",(DataTable dataTable)-> {
            Response productVarietyData = creditOrderOperation.queryProductVariety(customerEntCode);
            creditAmountCheck.CheckProductVarietyData(dataTable, productVarietyData);
        });
        Then("【仅占压超账期0501】临时信用申请应变动", (DataTable dataTable)->{
            List<Map<String, Object>> sqlResult = selectTemporaryCredit(customerEntCode);
            creditAmountCheck.CheckTemporaryCreditData(dataTable, sqlResult);
        });
        And("【仅占压超账期0501】流水应变动", (DataTable dataTable)->{
            List<Map<String, Object>> sqlResult = selectCreditJournal(customerEntCode);
            creditAmountCheck.CheckCreditJounalData(dataTable, sqlResult);
        });
        When("【仅占压超账期0502】客户在B2B下赊销订单",(DataTable dataTable)-> {
            customerEntCode = dataTable.asMaps().get(0).get("SALE_ORG_CODE");
            b2bOrderOperation.loginB2bFrontStageSystem();
            orderId02 = b2bOrderOperation.b2bFrontSystemOrder(B2bFrontOrder.FRONTORDER02);
            dataTable.asMaps().forEach(caseData ->{
                sku = caseData.get("SKU");
                itemId = caseData.get("ITEM_ID");
                mysqlData.updateOrderlines(itemId, orderId02, sku);
            });
        });
        Then("【仅占压超账期0502】商务审核应通过",()-> {
            b2bOrderOperation.loginB2bBackStageSystem();
            b2bOrderOperation.businessApproval(orderId02, B2bBackOrder.backOrder02(orderId02), "0","0");
        });
        And("【仅占压超账期0502】人工审核应通过",()-> {
            b2bOrderOperation.loginB2bBackStageSystem();
            b2bOrderOperation.manualApproval(orderId02, B2bBackOrder.backOrder02(orderId02));
        });
        Then("【仅占压超账期0502】产品集合金额应变动", (DataTable dataTable)->{
            Response productSetData = creditOrderOperation.queryProductSet(customerEntCode);
            creditAmountCheck.CheckProductSetData(dataTable, productSetData);
        });
        And("【仅占压超账期0502】品种金额应变动",(DataTable dataTable)-> {
            Response productVarietyData = creditOrderOperation.queryProductVariety(customerEntCode);
            creditAmountCheck.CheckProductVarietyData(dataTable, productVarietyData);
        });
        Then("【仅占压超账期0502】临时信用申请应变动", (DataTable dataTable)->{
            List<Map<String, Object>> sqlResult = selectTemporaryCredit(customerEntCode);
            creditAmountCheck.CheckTemporaryCreditData(dataTable, sqlResult);
        });
        And("【仅占压超账期0502】流水应变动", (DataTable dataTable)->{
            List<Map<String, Object>> sqlResult = selectCreditJournal(customerEntCode);
            creditAmountCheck.CheckCreditJounalData(dataTable, sqlResult);
        });
        When("【仅占压超账期0503】月度结转",()-> {
            mysqlData.updateExecutionMonth(TimeUtil.lastMonthRandomDate(), customerEntCode);
            creditOrderOperation.MonthTransfer();
        });
        Then("【仅占压超账期0503】产品集合金额应变动", (DataTable dataTable)->{
            Response productSetData = creditOrderOperation.queryProductSet(customerEntCode);
            creditAmountCheck.CheckProductSetData(dataTable, productSetData);
        });
        And("【仅占压超账期0503】品种金额应变动",(DataTable dataTable)-> {
            Response productVarietyData = creditOrderOperation.queryProductVariety(customerEntCode);
            creditAmountCheck.CheckProductVarietyData(dataTable, productVarietyData);
        });
        And("【仅占压超账期0503】流水应变动", (DataTable dataTable)->{
            List<Map<String, Object>> sqlResult = selectCreditJournal(customerEntCode);
            creditAmountCheck.CheckCreditJounalData(dataTable, sqlResult);
        });
        When("【仅占压超账期0504】客户在B2B退货",(DataTable dataTable)-> {
            creditOrderOperation.orderReturn(orderId01, B2bRefundOrder.BACKRETURNORDER02(orderId01));
        });
        Then("【仅占压超账期0504】产品集合金额应变动", (DataTable dataTable)->{
            Response productSetData = creditOrderOperation.queryProductSet(customerEntCode);
            creditAmountCheck.CheckProductSetData(dataTable, productSetData);
        });
        And("【仅占压超账期0504】品种金额应变动",(DataTable dataTable)-> {
            Response productVarietyData = creditOrderOperation.queryProductVariety(customerEntCode);
            creditAmountCheck.CheckProductVarietyData(dataTable, productVarietyData);
        });
        And("【仅占压超账期0504】流水应变动", (DataTable dataTable)->{
            List<Map<String, Object>> sqlResult = selectCreditJournal(customerEntCode);
            creditAmountCheck.CheckCreditJounalData(dataTable, sqlResult);
        });
        When("【仅占压超账期0505】客户在B2B退货冲销",(DataTable dataTable)-> {
            creditOrderOperation.orderReturnWriteOff(orderId01, B2bRefundOrder.BACKRETURNWRITEOFFORDER02(orderId01));
        });
        Then("【仅占压超账期0505】产品集合金额应变动", (DataTable dataTable)->{
            Response productSetData = creditOrderOperation.queryProductSet(customerEntCode);
            creditAmountCheck.CheckProductSetData(dataTable, productSetData);
        });
        And("【仅占压超账期0505】品种金额应变动",(DataTable dataTable)-> {
            Response productVarietyData = creditOrderOperation.queryProductVariety(customerEntCode);
            creditAmountCheck.CheckProductVarietyData(dataTable, productVarietyData);
        });
        And("【仅占压超账期0505】流水应变动", (DataTable dataTable)->{
            List<Map<String, Object>> sqlResult = selectCreditJournal(customerEntCode);
            creditAmountCheck.CheckCreditJounalData(dataTable, sqlResult);
        });
    }

}
