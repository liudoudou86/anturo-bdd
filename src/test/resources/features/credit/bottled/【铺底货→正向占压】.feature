@bottled
Feature:信用使用 → 铺底货 → 正向占压

  Scenario:【testcase01】信用使用 → 铺底货 → 正向占压 → 正常场景
    Given 【铺底货0101】更新客户信用政策
      | CREDIT_GRADE_ID     | OVERDUE_PERIOD_DAYS | CREDIT_CONTROL_DIMENSION |
      | 1326407914543600902 | 500                 | PRODUCT_SET              |
    When 【铺底货0101】客户在B2B下赊销订单
      | SALE_ORG_CODE | CUSTOMER_ENT_CODE | PRODUCT_VARIETY_CRM_CODE | SKU      | AMOUNT | COUNT | ITEM_ID |
      | 1000002261    | 1000006570        | 1-4O-7                   | 10000266 | 10.33  | 3     | 10      |
      | 1000002261    | 1000006570        | 1-4O-8                   | 10000286 | 11.11  | 3     | 20      |
    Then 【铺底货0101】商务审核应通过
    And 【铺底货0101】信用使用情况
      | productVarietyCode             | annualAvailableExecuteCreditLimit | annualExecuteCreditLimit | annualOccupiedAmount |
      | ZT-ITEM-PZ-20210401154500-2641 | 113.91                            | 113.91                   | 0                    |
      | ZT-ITEM-PZ-20210401154500-9614 | 101.92                            | 101.92                   | 0                    |
    And 【铺底货0101】人工审核应通过