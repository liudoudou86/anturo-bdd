@release
Feature:信用使用 → 逆向释放 → 仅占压年度资信 → 管控维度为产品集合

  Scenario:【testcase03】有停用 → 订单部分退货
    Given 【仅占压年度资信0301】更新客户信用政策
      | CREDIT_GRADE_ID     | OVERDUE_PERIOD_DAYS | CREDIT_CONTROL_DIMENSION |
      | 1326407914543600902 | 500                 | PRODUCT_SET              |
    When 【仅占压年度资信0301】客户在B2B下赊销订单
      | SALE_ORG_CODE | CUSTOMER_ENT_CODE | PRODUCT_VARIETY_CRM_CODE | SKU      | AMOUNT | COUNT | ITEM_ID |
      | 1000002261    | 1000006570        | 1-4O-7                   | 10000266 | 10.33  | 3     | 10      |
      | 1000002261    | 1000006570        | 1-4O-8                   | 10000286 | 11.11  | 3     | 20      |
      | 1000002261    | 1000006570        | 1-292NJV                 | 10000302 | 18.28  | 1     | 30      |
    Then 【仅占压年度资信0301】商务审核应通过
    And 【仅占压年度资信0301】人工审核应通过
    Then 【仅占压年度资信0301】产品集合金额应变动
      | productSetCode | annualAvailableExecuteCreditLimit | annualExecuteCreditLimit | annualOccupiedAmount |
      | YYSYZF         | 121.99                            | 121.99                   | 0.00                 |
      | YYSYYP         | 261.83                            | 326.15                   | 64.32                |
      | YYSYFYP        | 244.51                            | 262.79                   | 18.28                |
    And 【仅占压年度资信0301】品种金额应变动
      | productVarietyCrmCode | annualAvailableExecuteCreditLimit | annualExecuteCreditLimit | annualOccupiedAmount |
      | 1-4O-7                | 82.92                             | 113.91                   | 30.99                |
      | 1-4O-14               | 110.32                            | 110.32                   | 0.00                 |
      | 1-4O-8                | 68.59                             | 101.92                   | 33.33                |
      | 1-4O-3                | 127.23                            | 127.23                   | 0.00                 |
      | 1-4O-9                | 55.22                             | 55.22                    | 0.00                 |
      | 1-292NJV              | 62.06                             | 80.34                    | 18.28                |
      | 1-4O-12               | 121.99                            | 121.99                   | 0.00                 |
    And 【仅占压年度资信0301】流水应变动
      | CREDIT_LIMIT | OCCUPIED_AMOUNT | FROZEN_AMOUNT | OCCUPIED_CHANGE_AMOUNT | CREDIT_TRADE_NO |
      | 113.91       | 0.00            | 0.00          | 30.99                  | CPJ202212060005 |
      | 101.92       | 0.00            | 0.00          | 33.33                  | CPJ202212060005 |
      | 80.34        | 0.00            | 0.00          | 18.28                  | CPJ202212060005 |
    And 【仅占压年度资信0301】冻结产品集合中某品种
      | customerEntCode | productVarietyCode             | productVarietyCrmCode | saleOrgCode |
      | 1000002261      | ZT-ITEM-PZ-20210401154500-2641 | 1-4O-7                | 1000006570  |
    When 【仅占压年度资信0302】客户在B2B退货
      | SALE_ORG_CODE | CUSTOMER_ENT_CODE | PRODUCT_VARIETY_CRM_CODE | SKU      | AMOUNT | COUNT | ITEM_ID |
      | 1000006570    | 1000002261        | 1-4O-7                   | 10000266 | 20.66  | 2     | 10      |
    Then 【仅占压年度资信0302】产品集合金额应变动
      | productSetCode | annualAvailableExecuteCreditLimit | annualExecuteCreditLimit | annualOccupiedAmount |
      | YYSYZF         | 121.99                            | 121.99                   | 0.00                 |
      | YYSYYP         | 178.91                            | 212.24                   | 33.33                |
      | YYSYFYP        | 244.51                            | 262.79                   | 18.28                |
    And 【仅占压年度资信0302】品种金额应变动
      | productVarietyCrmCode | annualAvailableExecuteCreditLimit | annualExecuteCreditLimit | annualOccupiedAmount |
      | 1-4O-7                | 103.58                            | 113.91                   | 10.33                |
      | 1-4O-14               | 110.32                            | 110.32                   | 0.00                 |
      | 1-4O-8                | 68.59                             | 101.92                   | 33.33                |
      | 1-4O-3                | 127.23                            | 127.23                   | 0.00                 |
      | 1-4O-9                | 55.22                             | 55.22                    | 0.00                 |
      | 1-292NJV              | 62.06                             | 80.34                    | 18.28                |
      | 1-4O-12               | 121.99                            | 121.99                   | 0.00                 |
    And 【仅占压年度资信0302】流水应变动
      | CREDIT_LIMIT | OCCUPIED_AMOUNT | FROZEN_AMOUNT | OCCUPIED_CHANGE_AMOUNT | CREDIT_TRADE_NO |
      | 113.91       | 0.00            | 0.00          | 30.99                  | CPJ202212060005 |
      | 101.92       | 0.00            | 0.00          | 33.33                  | CPJ202212060005 |
      | 80.34        | 0.00            | 0.00          | 18.28                  | CPJ202212060005 |
      | 113.91       | 30.99           | 0.00          | 20.66                  | CPJ202212060005 |

  Scenario:【testcase04】有停用 → 订单部分退货 → 订单部分退货冲销
    Given 【仅占压年度资信0401】更新客户信用政策
      | CREDIT_GRADE_ID     | OVERDUE_PERIOD_DAYS | CREDIT_CONTROL_DIMENSION |
      | 1326407914543600902 | 500                 | PRODUCT_SET              |
    When 【仅占压年度资信0401】客户在B2B下赊销订单
      | SALE_ORG_CODE | CUSTOMER_ENT_CODE | PRODUCT_VARIETY_CRM_CODE | SKU      | AMOUNT | COUNT | ITEM_ID |
      | 1000002261    | 1000006570        | 1-4O-7                   | 10000266 | 10.33  | 3     | 10      |
      | 1000002261    | 1000006570        | 1-4O-8                   | 10000286 | 11.11  | 3     | 20      |
      | 1000002261    | 1000006570        | 1-292NJV                 | 10000302 | 18.28  | 1     | 30      |
    Then 【仅占压年度资信0401】商务审核应通过
    And 【仅占压年度资信0401】人工审核应通过
    Then 【仅占压年度资信0401】产品集合金额应变动
      | productSetCode | annualAvailableExecuteCreditLimit | annualExecuteCreditLimit | annualOccupiedAmount |
      | YYSYZF         | 121.99                            | 121.99                   | 0.00                 |
      | YYSYYP         | 261.83                            | 326.15                   | 64.32                |
      | YYSYFYP        | 244.51                            | 262.79                   | 18.28                |
    And 【仅占压年度资信0401】品种金额应变动
      | productVarietyCrmCode | annualAvailableExecuteCreditLimit | annualExecuteCreditLimit | annualOccupiedAmount |
      | 1-4O-7                | 82.92                             | 113.91                   | 30.99                |
      | 1-4O-14               | 110.32                            | 110.32                   | 0.00                 |
      | 1-4O-8                | 68.59                             | 101.92                   | 33.33                |
      | 1-4O-3                | 127.23                            | 127.23                   | 0.00                 |
      | 1-4O-9                | 55.22                             | 55.22                    | 0.00                 |
      | 1-292NJV              | 62.06                             | 80.34                    | 18.28                |
      | 1-4O-12               | 121.99                            | 121.99                   | 0.00                 |
    And 【仅占压年度资信0401】流水应变动
      | CREDIT_LIMIT | OCCUPIED_AMOUNT | FROZEN_AMOUNT | OCCUPIED_CHANGE_AMOUNT | CREDIT_TRADE_NO |
      | 113.91       | 0.00            | 0.00          | 30.99                  | CPJ202212060005 |
      | 101.92       | 0.00            | 0.00          | 33.33                  | CPJ202212060005 |
      | 80.34        | 0.00            | 0.00          | 18.28                  | CPJ202212060005 |
    When 【仅占压年度资信0402】客户在B2B退货
      | SALE_ORG_CODE | CUSTOMER_ENT_CODE | PRODUCT_VARIETY_CRM_CODE | SKU      | AMOUNT | COUNT | ITEM_ID |
      | 1000002261    | 1000006570        | 1-4O-7                   | 10000266 | 20.66  | 2     | 10      |
    Then 【仅占压年度资信0402】产品集合金额应变动
      | productSetCode | annualAvailableExecuteCreditLimit | annualExecuteCreditLimit | annualOccupiedAmount |
      | YYSYZF         | 121.99                            | 121.99                   | 0.00                 |
      | YYSYYP         | 282.49                            | 326.15                   | 43.66                |
      | YYSYFYP        | 244.51                            | 262.79                   | 18.28                |
    And 【仅占压年度资信0402】品种金额应变动
      | productVarietyCrmCode | annualAvailableExecuteCreditLimit | annualExecuteCreditLimit | annualOccupiedAmount |
      | 1-4O-7                | 103.58                            | 113.91                   | 10.33                |
      | 1-4O-14               | 110.32                            | 110.32                   | 0.00                 |
      | 1-4O-8                | 68.59                             | 101.92                   | 33.33                |
      | 1-4O-3                | 127.23                            | 127.23                   | 0.00                 |
      | 1-4O-9                | 55.22                             | 55.22                    | 0.00                 |
      | 1-292NJV              | 62.06                             | 80.34                    | 18.28                |
      | 1-4O-12               | 121.99                            | 121.99                   | 0.00                 |
    And 【仅占压年度资信0402】流水应变动
      | CREDIT_LIMIT | OCCUPIED_AMOUNT | FROZEN_AMOUNT | OCCUPIED_CHANGE_AMOUNT | CREDIT_TRADE_NO |
      | 113.91       | 0.00            | 0.00          | 30.99                  | CPJ202212060005 |
      | 101.92       | 0.00            | 0.00          | 33.33                  | CPJ202212060005 |
      | 80.34        | 0.00            | 0.00          | 18.28                  | CPJ202212060005 |
      | 113.91       | 30.99           | 0.00          | 20.66                  | CPJ202212060005 |
    And 【仅占压年度资信0402】冻结产品集合中某品种
      | customerEntCode | productVarietyCode             | productVarietyCrmCode | saleOrgCode |
      | 1000002261      | ZT-ITEM-PZ-20210401154500-2641 | 1-4O-7                | 1000006570  |
    When 【仅占压年度资信0403】客户在B2B退货冲销
      | SALE_ORG_CODE | CUSTOMER_ENT_CODE | PRODUCT_VARIETY_CRM_CODE | SKU      | AMOUNT | COUNT | ITEM_ID |
      | 1000002261    | 1000006570        | 1-4O-7                   | 10000266 | 10.33  | 1     | 10      |
    Then 【仅占压年度资信0403】产品集合金额应变动
      | productSetCode | annualAvailableExecuteCreditLimit | annualExecuteCreditLimit | annualOccupiedAmount |
      | YYSYZF         | 121.99                            | 121.99                   | 0.00                 |
      | YYSYYP         | 178.91                            | 212.24                   | 33.33                |
      | YYSYFYP        | 244.51                            | 262.79                   | 18.28                |
    And 【仅占压年度资信0403】品种金额应变动
      | productVarietyCrmCode | annualAvailableExecuteCreditLimit | annualExecuteCreditLimit | annualOccupiedAmount |
      | 1-4O-7                | 93.25                             | 113.91                   | 20.66                |
      | 1-4O-14               | 110.32                            | 110.32                   | 0.00                 |
      | 1-4O-8                | 68.59                             | 101.92                   | 33.33                |
      | 1-4O-3                | 127.23                            | 127.23                   | 0.00                 |
      | 1-4O-9                | 55.22                             | 55.22                    | 0.00                 |
      | 1-292NJV              | 62.06                             | 80.34                    | 18.28                |
      | 1-4O-12               | 121.99                            | 121.99                   | 0.00                 |
    And 【仅占压年度资信0403】流水应变动
      | CREDIT_LIMIT | OCCUPIED_AMOUNT | FROZEN_AMOUNT | OCCUPIED_CHANGE_AMOUNT | CREDIT_TRADE_NO |
      | 113.91       | 0.00            | 0.00          | 30.99                  | CPJ202212060005 |
      | 101.92       | 0.00            | 0.00          | 33.33                  | CPJ202212060005 |
      | 80.34        | 0.00            | 0.00          | 18.28                  | CPJ202212060005 |
      | 113.91       | 30.99           | 0.00          | 20.66                  | CPJ202212060005 |
      | 113.91       | 10.33           | 0.00          | 10.33                  | CPJ202212060005 |

  Scenario:【testcase05】【当月】无停用 → 订单部分退货&&退货冲销 → 月结转
    Given 【仅占压年度资信0501】更新客户信用政策
      | CREDIT_GRADE_ID     | OVERDUE_PERIOD_DAYS | CREDIT_CONTROL_DIMENSION |
      | 1326407914543600902 | 500                 | PRODUCT_SET              |
    When 【仅占压年度资信0501】客户在B2B下赊销订单
      | SALE_ORG_CODE | CUSTOMER_ENT_CODE | PRODUCT_VARIETY_CRM_CODE | SKU      | AMOUNT | COUNT | ITEM_ID |
      | 1000002261    | 1000006570        | 1-4O-7                   | 10000266 | 10.33  | 3     | 10      |
      | 1000002261    | 1000006570        | 1-4O-8                   | 10000286 | 11.11  | 3     | 20      |
      | 1000002261    | 1000006570        | 1-292NJV                 | 10000302 | 18.28  | 1     | 30      |
    Then 【仅占压年度资信0501】商务审核应通过
    And 【仅占压年度资信0501】人工审核应通过
    Then 【仅占压年度资信0501】产品集合金额应变动
      | productSetCode | annualAvailableExecuteCreditLimit | annualExecuteCreditLimit | annualOccupiedAmount |
      | YYSYZF         | 121.99                            | 121.99                   | 0.00                 |
      | YYSYYP         | 261.83                            | 326.15                   | 64.32                |
      | YYSYFYP        | 244.51                            | 262.79                   | 18.28                |
    And 【仅占压年度资信0501】品种金额应变动
      | productVarietyCrmCode | annualAvailableExecuteCreditLimit | annualExecuteCreditLimit | annualOccupiedAmount |
      | 1-4O-7                | 82.92                             | 113.91                   | 30.99                |
      | 1-4O-14               | 110.32                            | 110.32                   | 0.00                 |
      | 1-4O-8                | 68.59                             | 101.92                   | 33.33                |
      | 1-4O-3                | 127.23                            | 127.23                   | 0.00                 |
      | 1-4O-9                | 55.22                             | 55.22                    | 0.00                 |
      | 1-292NJV              | 62.06                             | 80.34                    | 18.28                |
      | 1-4O-12               | 121.99                            | 121.99                   | 0.00                 |
    And 【仅占压年度资信0501】流水应变动
      | CREDIT_LIMIT | OCCUPIED_AMOUNT | FROZEN_AMOUNT | OCCUPIED_CHANGE_AMOUNT | CREDIT_TRADE_NO |
      | 113.91       | 0.00            | 0.00          | 30.99                  | CPJ202212060005 |
      | 101.92       | 0.00            | 0.00          | 33.33                  | CPJ202212060005 |
      | 80.34        | 0.00            | 0.00          | 18.28                  | CPJ202212060005 |
    When 【仅占压年度资信0502】客户在B2B退货
      | SALE_ORG_CODE | CUSTOMER_ENT_CODE | PRODUCT_VARIETY_CRM_CODE | SKU      | AMOUNT | COUNT | ITEM_ID |
      | 1000002261    | 1000006570        | 1-4O-7                   | 10000266 | 20.66  | 2     | 10      |
    Then 【仅占压年度资信0502】产品集合金额应变动
      | productSetCode | annualAvailableExecuteCreditLimit | annualExecuteCreditLimit | annualOccupiedAmount |
      | YYSYZF         | 121.99                            | 121.99                   | 0.00                 |
      | YYSYYP         | 282.49                            | 326.15                   | 43.66                |
      | YYSYFYP        | 244.51                            | 262.79                   | 18.28                |
    And 【仅占压年度资信0502】品种金额应变动
      | productVarietyCrmCode | annualAvailableExecuteCreditLimit | annualExecuteCreditLimit | annualOccupiedAmount |
      | 1-4O-7                | 103.58                            | 113.91                   | 10.33                |
      | 1-4O-14               | 110.32                            | 110.32                   | 0.00                 |
      | 1-4O-8                | 68.59                             | 101.92                   | 33.33                |
      | 1-4O-3                | 127.23                            | 127.23                   | 0.00                 |
      | 1-4O-9                | 55.22                             | 55.22                    | 0.00                 |
      | 1-292NJV              | 62.06                             | 80.34                    | 18.28                |
      | 1-4O-12               | 121.99                            | 121.99                   | 0.00                 |
    And 【仅占压年度资信0502】流水应变动
      | CREDIT_LIMIT | OCCUPIED_AMOUNT | FROZEN_AMOUNT | OCCUPIED_CHANGE_AMOUNT | CREDIT_TRADE_NO |
      | 113.91       | 0.00            | 0.00          | 30.99                  | CPJ202212060005 |
      | 101.92       | 0.00            | 0.00          | 33.33                  | CPJ202212060005 |
      | 80.34        | 0.00            | 0.00          | 18.28                  | CPJ202212060005 |
      | 113.91       | 30.99           | 0.00          | 20.66                  | CPJ202212060005 |
    When 【仅占压年度资信0503】客户在B2B退货冲销
      | SALE_ORG_CODE | CUSTOMER_ENT_CODE | PRODUCT_VARIETY_CRM_CODE | SKU      | AMOUNT | COUNT | ITEM_ID |
      | 1000002261    | 1000006570        | 1-4O-7                   | 10000266 | 10.33  | 1     | 10      |
    Then 【仅占压年度资信0503】产品集合金额应变动
      | productSetCode | annualAvailableExecuteCreditLimit | annualExecuteCreditLimit | annualOccupiedAmount |
      | YYSYZF         | 121.99                            | 121.99                   | 0.00                 |
      | YYSYYP         | 272.16                            | 326.15                   | 53.99                |
      | YYSYFYP        | 244.51                            | 262.79                   | 18.28                |
    And 【仅占压年度资信0503】品种金额应变动
      | productVarietyCrmCode | annualAvailableExecuteCreditLimit | annualExecuteCreditLimit | annualOccupiedAmount |
      | 1-4O-7                | 93.25                             | 113.91                   | 20.66                |
      | 1-4O-14               | 110.32                            | 110.32                   | 0.00                 |
      | 1-4O-8                | 68.59                             | 101.92                   | 33.33                |
      | 1-4O-3                | 127.23                            | 127.23                   | 0.00                 |
      | 1-4O-9                | 55.22                             | 55.22                    | 0.00                 |
      | 1-292NJV              | 62.06                             | 80.34                    | 18.28                |
      | 1-4O-12               | 121.99                            | 121.99                   | 0.00                 |
    And 【仅占压年度资信0503】流水应变动
      | CREDIT_LIMIT | OCCUPIED_AMOUNT | FROZEN_AMOUNT | OCCUPIED_CHANGE_AMOUNT | CREDIT_TRADE_NO |
      | 113.91       | 0.00            | 0.00          | 30.99                  | CPJ202212060005 |
      | 101.92       | 0.00            | 0.00          | 33.33                  | CPJ202212060005 |
      | 80.34        | 0.00            | 0.00          | 18.28                  | CPJ202212060005 |
      | 113.91       | 30.99           | 0.00          | 20.66                  | CPJ202212060005 |
      | 113.91       | 10.33           | 0.00          | 10.33                  | CPJ202212060005 |
    When 【仅占压年度资信0504】月度结转
    Then 【仅占压年度资信0504】产品集合金额应变动
      | productSetCode | annualAvailableExecuteCreditLimit | annualExecuteCreditLimit | annualOccupiedAmount |
      | YYSYZF         | 121.99                            | 121.99                   | 0.00                 |
      | YYSYYP         | 272.16                            | 326.15                   | 53.99                |
      | YYSYFYP        | 244.51                            | 262.79                   | 18.28                |
    And 【仅占压年度资信0504】品种金额应变动
      | productVarietyCrmCode | annualAvailableExecuteCreditLimit | annualExecuteCreditLimit | annualOccupiedAmount |
      | 1-4O-7                | 93.25                             | 113.91                   | 20.66                |
      | 1-4O-14               | 110.32                            | 110.32                   | 0.00                 |
      | 1-4O-8                | 68.59                             | 101.92                   | 33.33                |
      | 1-4O-3                | 127.23                            | 127.23                   | 0.00                 |
      | 1-4O-9                | 55.22                             | 55.22                    | 0.00                 |
      | 1-292NJV              | 62.06                             | 80.34                    | 18.28                |
      | 1-4O-12               | 121.99                            | 121.99                   | 0.00                 |

  Scenario:【testcase06】【跨月】无停用 → 先月结转 → 订单部分退货&&退货冲销
    Given 【仅占压年度资信0601】更新客户信用政策
      | CREDIT_GRADE_ID     | OVERDUE_PERIOD_DAYS | CREDIT_CONTROL_DIMENSION |
      | 1326407914543600902 | 500                 | PRODUCT_SET              |
    When 【仅占压年度资信0601】客户在B2B下赊销订单
      | SALE_ORG_CODE | CUSTOMER_ENT_CODE | PRODUCT_VARIETY_CRM_CODE | SKU      | AMOUNT | COUNT | ITEM_ID |
      | 1000002261    | 1000006570        | 1-4O-7                   | 10000266 | 10.33  | 3     | 10      |
      | 1000002261    | 1000006570        | 1-4O-8                   | 10000286 | 11.11  | 3     | 20      |
      | 1000002261    | 1000006570        | 1-292NJV                 | 10000302 | 18.28  | 1     | 30      |
    Then 【仅占压年度资信0601】商务审核应通过
    And 【仅占压年度资信0601】人工审核应通过
    Then 【仅占压年度资信0601】产品集合金额应变动
      | productSetCode | annualAvailableExecuteCreditLimit | annualExecuteCreditLimit | annualOccupiedAmount |
      | YYSYZF         | 121.99                            | 121.99                   | 0.00                 |
      | YYSYYP         | 261.83                            | 326.15                   | 64.32                |
      | YYSYFYP        | 244.51                            | 262.79                   | 18.28                |
    And 【仅占压年度资信0601】品种金额应变动
      | productVarietyCrmCode | annualAvailableExecuteCreditLimit | annualExecuteCreditLimit | annualOccupiedAmount |
      | 1-4O-7                | 82.92                             | 113.91                   | 30.99                |
      | 1-4O-14               | 110.32                            | 110.32                   | 0.00                 |
      | 1-4O-8                | 68.59                             | 101.92                   | 33.33                |
      | 1-4O-3                | 127.23                            | 127.23                   | 0.00                 |
      | 1-4O-9                | 55.22                             | 55.22                    | 0.00                 |
      | 1-292NJV              | 62.06                             | 80.34                    | 18.28                |
      | 1-4O-12               | 121.99                            | 121.99                   | 0.00                 |
    And 【仅占压年度资信0601】流水应变动
      | CREDIT_LIMIT | OCCUPIED_AMOUNT | FROZEN_AMOUNT | OCCUPIED_CHANGE_AMOUNT | CREDIT_TRADE_NO |
      | 113.91       | 0.00            | 0.00          | 30.99                  | CPJ202212060005 |
      | 101.92       | 0.00            | 0.00          | 33.33                  | CPJ202212060005 |
      | 80.34        | 0.00            | 0.00          | 18.28                  | CPJ202212060005 |
    When 【仅占压年度资信0602】月度结转
    Then 【仅占压年度资信0602】产品集合金额应变动
      | productSetCode | annualAvailableExecuteCreditLimit | annualExecuteCreditLimit | annualOccupiedAmount |
      | YYSYZF         | 121.99                            | 121.99                   | 0.00                 |
      | YYSYYP         | 261.83                            | 326.15                   | 64.32                |
      | YYSYFYP        | 244.51                            | 262.79                   | 18.28                |
    And 【仅占压年度资信0602】品种金额应变动
      | productVarietyCrmCode | annualAvailableExecuteCreditLimit | annualExecuteCreditLimit | annualOccupiedAmount |
      | 1-4O-7                | 82.92                             | 113.91                   | 30.99                |
      | 1-4O-14               | 110.32                            | 110.32                   | 0.00                 |
      | 1-4O-8                | 68.59                             | 101.92                   | 33.33                |
      | 1-4O-3                | 127.23                            | 127.23                   | 0.00                 |
      | 1-4O-9                | 55.22                             | 55.22                    | 0.00                 |
      | 1-292NJV              | 62.06                             | 80.34                    | 18.28                |
      | 1-4O-12               | 121.99                            | 121.99                   | 0.00                 |
    When 【仅占压年度资信0603】客户在B2B退货
      | SALE_ORG_CODE | CUSTOMER_ENT_CODE | PRODUCT_VARIETY_CRM_CODE | SKU      | AMOUNT | COUNT | ITEM_ID |
      | 1000002261    | 1000006570        | 1-4O-7                   | 10000266 | 20.66  | 2     | 10      |
    Then 【仅占压年度资信0603】产品集合金额应变动
      | productSetCode | annualAvailableExecuteCreditLimit | annualExecuteCreditLimit | annualOccupiedAmount |
      | YYSYZF         | 121.99                            | 121.99                   | 0.00                 |
      | YYSYYP         | 282.49                            | 326.15                   | 43.66                |
      | YYSYFYP        | 244.51                            | 262.79                   | 18.28                |
    And 【仅占压年度资信0603】品种金额应变动
      | productVarietyCrmCode | annualAvailableExecuteCreditLimit | annualExecuteCreditLimit | annualOccupiedAmount |
      | 1-4O-7                | 103.58                            | 113.91                   | 10.33                |
      | 1-4O-14               | 110.32                            | 110.32                   | 0.00                 |
      | 1-4O-8                | 68.59                             | 101.92                   | 33.33                |
      | 1-4O-3                | 127.23                            | 127.23                   | 0.00                 |
      | 1-4O-9                | 55.22                             | 55.22                    | 0.00                 |
      | 1-292NJV              | 62.06                             | 80.34                    | 18.28                |
      | 1-4O-12               | 121.99                            | 121.99                   | 0.00                 |
    And 【仅占压年度资信0603】流水应变动
      | CREDIT_LIMIT | OCCUPIED_AMOUNT | FROZEN_AMOUNT | OCCUPIED_CHANGE_AMOUNT | CREDIT_TRADE_NO |
      | 113.91       | 0.00            | 0.00          | 30.99                  | CPJ202212060005 |
      | 101.92       | 0.00            | 0.00          | 33.33                  | CPJ202212060005 |
      | 80.34        | 0.00            | 0.00          | 18.28                  | CPJ202212060005 |
      | 113.91       | 30.99           | 0.00          | 20.66                  | CPJ202212060005 |
    When 【仅占压年度资信0604】客户在B2B退货冲销
      | SALE_ORG_CODE | CUSTOMER_ENT_CODE | PRODUCT_VARIETY_CRM_CODE | SKU      | AMOUNT | COUNT | ITEM_ID |
      | 1000002261    | 1000006570        | 1-4O-7                   | 10000266 | 10.33  | 1     | 10      |
    Then 【仅占压年度资信0604】产品集合金额应变动
      | productSetCode | annualAvailableExecuteCreditLimit | annualExecuteCreditLimit | annualOccupiedAmount |
      | YYSYZF         | 121.99                            | 121.99                   | 0.00                 |
      | YYSYYP         | 272.16                            | 326.15                   | 53.99                |
      | YYSYFYP        | 244.51                            | 262.79                   | 18.28                |
    And 【仅占压年度资信0604】品种金额应变动
      | productVarietyCrmCode | annualAvailableExecuteCreditLimit | annualExecuteCreditLimit | annualOccupiedAmount |
      | 1-4O-7                | 93.25                             | 113.91                   | 20.66                |
      | 1-4O-14               | 110.32                            | 110.32                   | 0.00                 |
      | 1-4O-8                | 68.59                             | 101.92                   | 33.33                |
      | 1-4O-3                | 127.23                            | 127.23                   | 0.00                 |
      | 1-4O-9                | 55.22                             | 55.22                    | 0.00                 |
      | 1-292NJV              | 62.06                             | 80.34                    | 18.28                |
      | 1-4O-12               | 121.99                            | 121.99                   | 0.00                 |
    And 【仅占压年度资信0604】流水应变动
      | CREDIT_LIMIT | OCCUPIED_AMOUNT | FROZEN_AMOUNT | OCCUPIED_CHANGE_AMOUNT | CREDIT_TRADE_NO |
      | 113.91       | 0.00            | 0.00          | 30.99                  | CPJ202212060005 |
      | 101.92       | 0.00            | 0.00          | 33.33                  | CPJ202212060005 |
      | 80.34        | 0.00            | 0.00          | 18.28                  | CPJ202212060005 |
      | 113.91       | 30.99           | 0.00          | 20.66                  | CPJ202212060005 |
      | 113.91       | 10.33           | 0.00          | 10.33                  | CPJ202212060005 |