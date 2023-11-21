package com.qa.credit.occupy.steps;

import com.qa.operate.check.AnnualCheck;
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

/**
 * @author Tesla Liu
 * @date 2022/11/21 15:41
 * 描述 仅占压年度资信 - 正向占压
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class ForwardUseOnlyAnnualSteps implements En {
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

    public ForwardUseOnlyAnnualSteps() {
        this.testcase01();
        this.testcase02();
    }

    public void testcase01() {
        Given("【仅占压年度资信0101】更新客户信用政策", (DataTable dataTable) -> {
            mysqlData.deleteCreditData("scripts/【清理信用历史数据】.sql");
            mysqlData.inserCreditData("scripts/【仅年度资信无临时信用】.sql");
            creditGradeId = dataTable.asMaps().get(0).get("CREDIT_GRADE_ID");
            overduePeriodDays = dataTable.asMaps().get(0).get("OVERDUE_PERIOD_DAYS");
            creditControlDimension = dataTable.asMaps().get(0).get("CREDIT_CONTROL_DIMENSION");
            mysqlData.updateEnterpriseGrade(overduePeriodDays, creditControlDimension, creditGradeId);
        });
        When("【仅占压年度资信0101】客户在B2B下赊销订单",(DataTable dataTable)-> {
            customerEntCode = dataTable.asMaps().get(0).get("SALE_ORG_CODE");
            b2bOrderOperation.loginB2bFrontStageSystem();
            orderId = b2bOrderOperation.b2bFrontSystemOrder(B2bFrontOrder.FRONTORDER02);
            dataTable.asMaps().forEach(caseData ->{
                sku = caseData.get("SKU");
                itemId = caseData.get("ITEM_ID");
                mysqlData.updateOrderlines(itemId, orderId, sku);
            });
        });
        Then("【仅占压年度资信0101】商务审核应通过",()-> {
            b2bOrderOperation.loginB2bBackStageSystem();
            b2bOrderOperation.businessApproval(orderId, B2bBackOrder.backOrder02(orderId), "0","0");
        });
        And("【仅占压年度资信0101】信用使用情况",(DataTable dataTable)-> {
            Response creditUseData = b2bOrderOperation.creditUse(orderId);
            creditAmountCheck.CheckCreditUse(dataTable, creditUseData);
        });
        And("【仅占压年度资信0101】人工审核应通过",()-> {
            b2bOrderOperation.loginB2bBackStageSystem();
            b2bOrderOperation.manualApproval(orderId, B2bBackOrder.backOrder02(orderId));
        });
        Then("【仅占压年度资信0101】产品集合金额应变动", (DataTable dataTable)->{
            Response productSetData = creditOrderOperation.queryProductSet(customerEntCode);
            creditAmountCheck.CheckProductSetData(dataTable, productSetData);
        });
        And("【仅占压年度资信0101】品种金额应变动",(DataTable dataTable)-> {
            Response productVarietyData = creditOrderOperation.queryProductVariety(customerEntCode);
            creditAmountCheck.CheckProductVarietyData(dataTable, productVarietyData);
        });
        And("【仅占压年度资信0101】流水应变动", (DataTable dataTable)->{
            List<Map<String, Object>> sqlResult = selectCreditJournal(customerEntCode);
            creditAmountCheck.CheckCreditJounalData(dataTable, sqlResult);
        });
    }

    public void testcase02() {
        Given("【仅占压年度资信0201】更新客户信用政策", (DataTable dataTable) -> {
            mysqlData.deleteCreditData("scripts/【清理信用历史数据】.sql");
            mysqlData.inserCreditData("scripts/【仅年度资信无临时信用】.sql");
            creditGradeId = dataTable.asMaps().get(0).get("CREDIT_GRADE_ID");
            overduePeriodDays = dataTable.asMaps().get(0).get("OVERDUE_PERIOD_DAYS");
            creditControlDimension = dataTable.asMaps().get(0).get("CREDIT_CONTROL_DIMENSION");
            mysqlData.updateEnterpriseGrade(overduePeriodDays, creditControlDimension, creditGradeId);
        });
        And("【仅占压年度资信0201】冻结产品集合中某品种",(DataTable dataTable)->{
            customerEntCode = dataTable.asMaps().get(0).get("customerEntCode");
            productVarietyCode = dataTable.asMaps().get(0).get("productVarietyCode");
            saleOrgCode = dataTable.asMaps().get(0).get("saleOrgCode");
            creditOrderOperation.disableProductVariety(customerEntCode, productVarietyCode, saleOrgCode);
        });
        When("【仅占压年度资信0201】客户在B2B下赊销订单",(DataTable dataTable)-> {
            customerEntCode = dataTable.asMaps().get(0).get("SALE_ORG_CODE");
            b2bOrderOperation.loginB2bFrontStageSystem();
            orderId = b2bOrderOperation.b2bFrontSystemOrder(B2bFrontOrder.FRONTORDER02);
            dataTable.asMaps().forEach(caseData ->{
                sku = caseData.get("SKU");
                itemId = caseData.get("ITEM_ID");
                mysqlData.updateOrderlines(itemId, orderId, sku);
            });
        });
        Then("【仅占压年度资信0201】商务审核应通过",()-> {
            b2bOrderOperation.loginB2bBackStageSystem();
            b2bOrderOperation.businessApproval(orderId, B2bBackOrder.backOrder02(orderId), "0","0");
        });
        And("【仅占压年度资信0201】信用使用情况",(DataTable dataTable)-> {
            Response creditUseData = b2bOrderOperation.creditUse(orderId);
            creditAmountCheck.CheckCreditUse(dataTable, creditUseData);
        });
        And("【仅占压年度资信0201】人工审核应通过",()-> {
            b2bOrderOperation.loginB2bBackStageSystem();
            b2bOrderOperation.manualApproval(orderId, B2bBackOrder.backOrder02(orderId));
        });
        Then("【仅占压年度资信0201】产品集合金额应变动", (DataTable dataTable)->{
            Response productSetData = creditOrderOperation.queryProductSet(customerEntCode);
            creditAmountCheck.CheckProductSetData(dataTable, productSetData);
        });
        And("【仅占压年度资信0201】品种金额应变动",(DataTable dataTable)-> {
            Response productVarietyData = creditOrderOperation.queryProductVariety(customerEntCode);
            creditAmountCheck.CheckProductVarietyData(dataTable, productVarietyData);
        });
        And("【仅占压年度资信0201】流水应变动", (DataTable dataTable)->{
            List<Map<String, Object>> sqlResult = selectCreditJournal(customerEntCode);
            creditAmountCheck.CheckCreditJounalData(dataTable, sqlResult);
        });
    }

}
