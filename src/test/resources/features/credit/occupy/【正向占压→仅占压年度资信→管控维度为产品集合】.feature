@occupy
Feature:信用使用 → 正向占用 → 仅占压超额度 → 管控维度为产品集合

  Scenario:【testcase01】信用使用 → 正向占压 → 仅占压年度资信 → 管控维度为产品集合 → 无停用
    Given 【仅占压年度资信0101】更新客户信用政策
      | CREDIT_GRADE_ID     | OVERDUE_PERIOD_DAYS | CREDIT_CONTROL_DIMENSION |
      | 1326407914543600902 | 90                  | PRODUCT_SET              |
    When 【仅占压年度资信0101】客户在B2B下赊销订单
      | SALE_ORG_CODE | CUSTOMER_ENT_CODE | PRODUCT_VARIETY_CRM_CODE | SKU      | AMOUNT | COUNT | ITEM_ID |
      | 1000002261    | 1000006570        | 1-4O-7                   | 10000266 | 10.33  | 3     | 10      |
      | 1000002261    | 1000006570        | 1-4O-8                   | 10000286 | 11.11  | 3     | 20      |
      | 1000002261    | 1000006570        | 1-292NJV                 | 10000302 | 18.28  | 1     | 30      |
    Then 【仅占压年度资信0101】商务审核应通过
    And 【仅占压年度资信0101】信用使用情况
      | productVarietyCode             | annualAvailableExecuteCreditLimit | annualExecuteCreditLimit | annualOccupiedAmount |
      | ZT-ITEM-PZ-20210401154500-2641 | 113.91                            | 113.91                   | 0                    |
      | ZT-ITEM-PZ-20210401154500-9614 | 101.92                            | 101.92                   | 0                    |
      | ZT-ITEM-PZ-20210401154500-5584 | 80.34                             | 80.34                    | 0                    |
    And 【仅占压年度资信0101】人工审核应通过
    Then 【仅占压年度资信0101】产品集合金额应变动
      | productSetCode | annualAvailableExecuteCreditLimit | annualExecuteCreditLimit | annualOccupiedAmount |
      | YYSYZF         | 121.99                            | 121.99                   | 0.00                 |
      | YYSYYP         | 261.83                            | 326.15                   | 64.32                |
      | YYSYFYP        | 244.51                            | 262.79                   | 18.28                |
    And 【仅占压年度资信0101】品种金额应变动
      | productVarietyCrmCode | annualAvailableExecuteCreditLimit | annualExecuteCreditLimit | annualOccupiedAmount |
      | 1-4O-7                | 82.92                             | 113.91                   | 30.99                |
      | 1-4O-14               | 110.32                            | 110.32                   | 0.00                 |
      | 1-4O-8                | 68.59                             | 101.92                   | 33.33                |
      | 1-4O-3                | 127.23                            | 127.23                   | 0.00                 |
      | 1-4O-9                | 55.22                             | 55.22                    | 0.00                 |
      | 1-292NJV              | 62.06                             | 80.34                    | 18.28                |
      | 1-4O-12               | 121.99                            | 121.99                   | 0.00                 |
    And 【仅占压年度资信0101】流水应变动
      | CREDIT_LIMIT | OCCUPIED_AMOUNT | FROZEN_AMOUNT | OCCUPIED_CHANGE_AMOUNT | CREDIT_TRADE_NO |
      | 113.91       | 0.00            | 0.00          | 30.99                  | CPJ202212060005     |
      | 101.92       | 0.00            | 0.00          | 33.33                  | CPJ202212060005     |
      | 80.34        | 0.00            | 0.00          | 18.28                  | CPJ202212060005     |

  Scenario:【testcase02】信用使用 → 正向占压 → 仅占压年度资信 → 管控维度为产品集合 → 有停用
    Given 【仅占压年度资信0201】更新客户信用政策
      | CREDIT_GRADE_ID     | OVERDUE_PERIOD_DAYS | CREDIT_CONTROL_DIMENSION |
      | 1326407914543600902 | 90                  | PRODUCT_SET              |
    And 【仅占压年度资信0201】冻结产品集合中某品种
      | customerEntCode | productVarietyCode             | productVarietyCrmCode | saleOrgCode |
      | 1000002261      | ZT-ITEM-PZ-20210401154500-7177 | 1-4O-14               | 1000006570  |
    When 【仅占压年度资信0201】客户在B2B下赊销订单
      | SALE_ORG_CODE | CUSTOMER_ENT_CODE | PRODUCT_VARIETY_CRM_CODE | SKU      | AMOUNT | COUNT | ITEM_ID |
      | 1000002261    | 1000006570        | 1-4O-7                   | 10000266 | 10.33  | 3     | 10      |
      | 1000002261    | 1000006570        | 1-4O-8                   | 10000286 | 11.11  | 3     | 20      |
      | 1000002261    | 1000006570        | 1-292NJV                 | 10000302 | 18.28  | 1     | 30      |
    Then 【仅占压年度资信0201】商务审核应通过
    And 【仅占压年度资信0201】信用使用情况
      | productVarietyCode             | annualAvailableExecuteCreditLimit | annualExecuteCreditLimit | annualOccupiedAmount |
      | ZT-ITEM-PZ-20210401154500-2641 | 113.91                            | 113.91                   | 0                    |
      | ZT-ITEM-PZ-20210401154500-9614 | 101.92                            | 101.92                   | 0                    |
      | ZT-ITEM-PZ-20210401154500-5584 | 80.34                             | 80.34                    | 0                    |
    And 【仅占压年度资信0201】人工审核应通过
    Then 【仅占压年度资信0201】产品集合金额应变动
      | productSetCode | annualAvailableExecuteCreditLimit | annualExecuteCreditLimit | annualOccupiedAmount |
      | YYSYZF         | 121.99                            | 121.99                   | 0.00                 |
      | YYSYYP         | 151.51                            | 215.83                   | 64.32                |
      | YYSYFYP        | 244.51                            | 262.79                   | 18.28                |
    And 【仅占压年度资信0201】品种金额应变动
      | productVarietyCrmCode | annualAvailableExecuteCreditLimit | annualExecuteCreditLimit | annualOccupiedAmount |
      | 1-4O-7                | 82.92                             | 113.91                   | 30.99                |
      | 1-4O-14               | 110.32                            | 110.32                   | 0.00                 |
      | 1-4O-8                | 68.59                             | 101.92                   | 33.33                |
      | 1-4O-3                | 127.23                            | 127.23                   | 0.00                 |
      | 1-4O-9                | 55.22                             | 55.22                    | 0.00                 |
      | 1-292NJV              | 62.06                             | 80.34                    | 18.28                |
      | 1-4O-12               | 121.99                            | 121.99                   | 0.00                 |
    And 【仅占压年度资信0201】流水应变动
      | CREDIT_LIMIT | OCCUPIED_AMOUNT | FROZEN_AMOUNT | OCCUPIED_CHANGE_AMOUNT | CREDIT_TRADE_NO |
      | 113.91       | 0.00            | 0.00          | 30.99                  | CPJ202212060005     |
      | 101.92       | 0.00            | 0.00          | 33.33                  | CPJ202212060005     |
      | 80.34        | 0.00            | 0.00          | 18.28                  | CPJ202212060005     |

