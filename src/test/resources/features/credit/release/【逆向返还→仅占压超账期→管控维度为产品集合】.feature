@release
Feature:信用使用 → 逆向释放 → 仅占压超账期 → 管控维度为产品集合

  Scenario:【testcase02】有停用 → 订单部分退货
    Given 【仅占压超账期0201】更新客户信用政策
      | CREDIT_GRADE_ID     | OVERDUE_PERIOD_DAYS | CREDIT_CONTROL_DIMENSION |
      | 1326407914543600902 | 1                   | PRODUCT_SET              |
    When 【仅占压超账期0201】客户在B2B下赊销订单
      | SALE_ORG_CODE | CUSTOMER_ENT_CODE | PRODUCT_VARIETY_CRM_CODE | SKU      | AMOUNT | COUNT | ITEM_ID |
      | 1000002261    | 1000006570        | 1-4O-12                  | 10000558 | 19.97  | 3     | 10      |
      | 1000002261    | 1000006570        | 1-4O-3                   | 10000437 | 12.85  | 3     | 20      |
      | 1000002261    | 1000006570        | 1-4O-3                   | 10000434 | 13.05  | 3     | 30      |
    Then 【仅占压超账期0201】商务审核应通过
    And 【仅占压超账期0201】人工审核应通过
    Then 【仅占压超账期0201】产品集合金额应变动
      | productSetCode | annualAvailableExecuteCreditLimit | exceedingAccountPeriodCreditLimit | exceedingAccountPeriodAvailableAmount | exceedingAccountPeriodOccupiedAmount | exceedingAccountPeriodIneffectiveAmount |
      | YYSYZF         | 65.13                             | 65.69                             | 3.35                                  | 61.02                                | 1.32                                    |
      | YYSYYP         | 120.42                            | 147.90                            | 107.97                                | 10.46                                | 29.47                                   |
      | YYSYFYP        | 134.97                            | 140.32                            | 18.91                                 | 99.90                                | 21.51                                   |
    And 【仅占压超账期0201】品种金额应变动
      | productVarietyCrmCode | annualAvailableExecuteCreditLimit | exceedingAccountPeriodCreditLimit | exceedingAccountPeriodAvailableAmount | exceedingAccountPeriodOccupiedAmount | exceedingAccountPeriodIneffectiveAmount |
      | 1-4O-7                | 57.13                             | 55.91                             | 51.76                                 | 0.91                                 | 3.24                                    |
      | 1-4O-14               | 72.50                             | 40.66                             | 27.70                                 | 3.87                                 | 9.09                                    |
      | 1-4O-8                | -9.21                             | 51.33                             | 28.51                                 | 5.68                                 | 17.14                                   |
      | 1-4O-3                | 101.29                            | 63.77                             | 51.90                                 | 6.11                                 | 5.76                                    |
      | 1-4O-9                | 44.11                             | 30.66                             | -60.68                                | 83.57                                | 7.77                                    |
      | 1-292NJV              | -10.43                            | 45.89                             | 27.69                                 | 10.22                                | 7.98                                    |
      | 1-4O-12               | 65.13                             | 65.69                             | 3.35                                  | 61.02                                | 1.32                                    |
    Then 【仅占压超账期0201】临时信用申请应变动
      | productVarietyCrmCode | TYPE | CREDIT_LIMIT | OCCUPIED_AMOUNT | FROZEN_AMOUNT | TRADE_NO    |
      | 1-4O-7                | 2    | 20.59        | 0.58            | 1.11          | PJLZ2022002 |
      | 1-4O-14               | 2    | 20.11        | 1.56            | 3.22          | PJLZ2022002 |
      | 1-4O-8                | 2    | 30.78        | 2.55            | 9.16          | PJLZ2022002 |
      | 1-4O-3                | 2    | 28.11        | 1.56            | 3.87          | PJLZ2022002 |
      | 1-4O-9                | 2    | 0.00         | 22.68           | 0.00          | PJLZ2022002 |
      | 1-292NJV              | 2    | 0.00         | 0.00            | 0.00          | PJLZ2022002 |
      | 1-4O-12               | 2    | 65.69        | 61.02           | 1.32          | PJLZ2022002 |
      | 1-4O-7                | 2    | 35.32        | 0.33            | 2.13          | PJLZ2022003 |
      | 1-4O-14               | 2    | 20.55        | 2.31            | 5.87          | PJLZ2022003 |
      | 1-4O-8                | 2    | 20.55        | 3.13            | 7.98          | PJLZ2022003 |
      | 1-4O-3                | 2    | 35.66        | 4.55            | 1.89          | PJLZ2022003 |
      | 1-4O-9                | 2    | 30.66        | 60.89           | 7.77          | PJLZ2022003 |
      | 1-292NJV              | 2    | 45.89        | 10.22           | 7.98          | PJLZ2022003 |
      | 1-4O-12               | 2    | 0.00         | 0.00            | 0.00          | PJLZ2022003 |
    And 【仅占压超账期0201】流水应变动
      | CREDIT_LIMIT | OCCUPIED_AMOUNT | FROZEN_AMOUNT | OCCUPIED_CHANGE_AMOUNT | CREDIT_TRADE_NO |
      | 65.69        | 1.11            | 1.32          | 59.91                  | PJLZ2022002     |
      | 0.00         | 0.00            | 0.00          | 22.68                  | PJLZ2022002     |
      | 30.66        | 5.87            | 7.77          | 55.02                  | PJLZ2022003     |
    When 【仅占压超账期0202】客户在B2B下赊销订单
      | SALE_ORG_CODE | CUSTOMER_ENT_CODE | PRODUCT_VARIETY_CRM_CODE | SKU      | AMOUNT | COUNT | ITEM_ID |
      | 1000002261    | 1000006570        | 1-4O-7                   | 10000266 | 10.33  | 3     | 10      |
      | 1000002261    | 1000006570        | 1-4O-8                   | 10000286 | 11.11  | 3     | 20      |
      | 1000002261    | 1000006570        | 1-292NJV                 | 10000302 | 18.28  | 1     | 30      |
    Then 【仅占压超账期0202】商务审核应通过
    And 【仅占压超账期0202】人工审核应通过
    Then 【仅占压超账期0202】产品集合金额应变动
      | productSetCode | annualAvailableExecuteCreditLimit | exceedingAccountPeriodCreditLimit | exceedingAccountPeriodAvailableAmount | exceedingAccountPeriodOccupiedAmount | exceedingAccountPeriodIneffectiveAmount |
      | YYSYZF         | 65.13                             | 65.69                             | 3.35                                  | 61.02                                | 1.32                                    |
      | YYSYYP         | 120.42                            | 147.90                            | 43.65                                 | 74.78                                | 29.47                                   |
      | YYSYFYP        | 134.97                            | 140.32                            | 0.63                                  | 118.18                               | 21.51                                   |
    And 【仅占压超账期0202】品种金额应变动
      | productVarietyCrmCode | annualAvailableExecuteCreditLimit | exceedingAccountPeriodCreditLimit | exceedingAccountPeriodAvailableAmount | exceedingAccountPeriodOccupiedAmount | exceedingAccountPeriodIneffectiveAmount |
      | 1-4O-7                | 57.13                             | 55.91                             | 20.77                                 | 31.90                                | 3.24                                    |
      | 1-4O-14               | 72.50                             | 40.66                             | 27.70                                 | 3.87                                 | 9.09                                    |
      | 1-4O-8                | -9.21                             | 51.33                             | -4.82                                 | 39.01                                | 17.14                                   |
      | 1-4O-3                | 101.29                            | 63.77                             | 51.90                                 | 6.11                                 | 5.76                                    |
      | 1-4O-9                | 44.11                             | 30.66                             | -60.68                                | 83.57                                | 7.77                                    |
      | 1-292NJV              | -10.43                            | 45.89                             | 9.41                                  | 28.50                                | 7.98                                    |
      | 1-4O-12               | 65.13                             | 65.69                             | 3.35                                  | 61.02                                | 1.32                                    |
    Then 【仅占压超账期0202】临时信用申请应变动
      | productVarietyCrmCode | TYPE | CREDIT_LIMIT | OCCUPIED_AMOUNT | FROZEN_AMOUNT | TRADE_NO    |
      | 1-4O-7                | 2    | 20.59        | 31.57           | 1.11          | PJLZ2022002 |
      | 1-4O-14               | 2    | 20.11        | 1.56            | 3.22          | PJLZ2022002 |
      | 1-4O-8                | 2    | 30.78        | 24.86           | 9.16          | PJLZ2022002 |
      | 1-4O-3                | 2    | 28.11        | 1.56            | 3.87          | PJLZ2022002 |
      | 1-4O-9                | 2    | 0.00         | 22.68           | 0.00          | PJLZ2022002 |
      | 1-292NJV              | 2    | 0.00         | 0.00            | 0.00          | PJLZ2022002 |
      | 1-4O-12               | 2    | 65.69        | 61.02           | 1.32          | PJLZ2022002 |
      | 1-4O-7                | 2    | 35.32        | 0.33            | 2.13          | PJLZ2022003 |
      | 1-4O-14               | 2    | 20.55        | 2.31            | 5.87          | PJLZ2022003 |
      | 1-4O-8                | 2    | 20.55        | 14.15           | 7.98          | PJLZ2022003 |
      | 1-4O-3                | 2    | 35.66        | 4.55            | 1.89          | PJLZ2022003 |
      | 1-4O-9                | 2    | 30.66        | 60.89           | 7.77          | PJLZ2022003 |
      | 1-292NJV              | 2    | 45.89        | 28.50           | 7.98          | PJLZ2022003 |
      | 1-4O-12               | 2    | 0.00         | 0.00            | 0.00          | PJLZ2022003 |
    And 【仅占压超账期0202】流水应变动
      | CREDIT_LIMIT | OCCUPIED_AMOUNT | FROZEN_AMOUNT | OCCUPIED_CHANGE_AMOUNT | CREDIT_TRADE_NO |
      | 65.69        | 1.11            | 1.32          | 59.91                  | PJLZ2022002     |
      | 0.00         | 0.00            | 0.00          | 22.68                  | PJLZ2022002     |
      | 30.66        | 5.87            | 7.77          | 55.02                  | PJLZ2022003     |
      | 20.59        | 0.58            | 1.11          | 30.99                  | PJLZ2022002     |
      | 30.78        | 2.55            | 9.16          | 22.31                  | PJLZ2022002     |
      | 20.55        | 3.13            | 7.98          | 11.02                  | PJLZ2022003     |
      | 45.89        | 10.22           | 7.98          | 18.28                  | PJLZ2022003     |
    And 【仅占压超账期0202】冻结产品集合中某品种
      | customerEntCode | productVarietyCode             | productVarietyCrmCode | saleOrgCode |
      | 1000002261      | ZT-ITEM-PZ-20210401154500-1194 | 1-4O-9                | 1000006570  |
    When 【仅占压超账期0203】客户在B2B退货
      | SALE_ORG_CODE | CUSTOMER_ENT_CODE | PRODUCT_VARIETY_CRM_CODE | SKU      | AMOUNT | COUNT | ITEM_ID |
      | 1000002261    | 1000006570        | 1-4O-7                   | 10000437 | 25.70  | 2     | 20      |
      | 1000002261    | 1000006570        | 1-4O-7                   | 10000434 | 26.10  | 2     | 30      |
    Then 【仅占压超账期0203】产品集合金额应变动
      | productSetCode | annualAvailableExecuteCreditLimit | exceedingAccountPeriodCreditLimit | exceedingAccountPeriodAvailableAmount | exceedingAccountPeriodOccupiedAmount | exceedingAccountPeriodIneffectiveAmount |
      | YYSYZF         | 65.13                             | 65.69                             | 3.35                                  | 61.02                                | 1.32                                    |
      | YYSYYP         | 120.42                            | 147.90                            | 43.65                                 | 74.78                                | 29.47                                   |
      | YYSYFYP        | 90.86                             | 109.66                            | 61.31                                 | 34.61                                | 13.74                                   |
    And 【仅占压超账期0203】品种金额应变动
      | productVarietyCrmCode | annualAvailableExecuteCreditLimit | exceedingAccountPeriodCreditLimit | exceedingAccountPeriodAvailableAmount | exceedingAccountPeriodOccupiedAmount | exceedingAccountPeriodIneffectiveAmount |
      | 1-4O-7                | 57.13                             | 55.91                             | 20.77                                 | 31.90                                | 3.24                                    |
      | 1-4O-14               | 72.50                             | 40.66                             | 27.70                                 | 3.87                                 | 9.09                                    |
      | 1-4O-8                | -9.21                             | 51.33                             | -4.82                                 | 39.01                                | 17.14                                   |
      | 1-4O-3                | 101.29                            | 63.77                             | 51.90                                 | 6.11                                 | 5.76                                    |
      | 1-4O-9                | 44.11                             | 30.66                             | -8.88                                 | 31.77                                | 7.77                                    |
      | 1-292NJV              | -10.43                            | 45.89                             | 9.41                                  | 28.50                                | 7.98                                    |
      | 1-4O-12               | 65.13                             | 65.69                             | 3.35                                  | 61.02                                | 1.32                                    |
    Then 【仅占压超账期0203】临时信用申请应变动
      | productVarietyCrmCode | TYPE | CREDIT_LIMIT | OCCUPIED_AMOUNT | FROZEN_AMOUNT | TRADE_NO    |
      | 1-4O-7                | 2    | 20.59        | 31.57           | 1.11          | PJLZ2022002 |
      | 1-4O-14               | 2    | 20.11        | 1.56            | 3.22          | PJLZ2022002 |
      | 1-4O-8                | 2    | 30.78        | 24.86           | 9.16          | PJLZ2022002 |
      | 1-4O-3                | 2    | 28.11        | 1.56            | 3.87          | PJLZ2022002 |
      | 1-4O-9                | 2    | 0.00         | 7.56            | 0.00          | PJLZ2022002 |
      | 1-292NJV              | 2    | 0.00         | 0.00            | 0.00          | PJLZ2022002 |
      | 1-4O-12               | 2    | 65.69        | 61.02           | 1.32          | PJLZ2022002 |
      | 1-4O-7                | 2    | 35.32        | 0.33            | 2.13          | PJLZ2022003 |
      | 1-4O-14               | 2    | 20.55        | 2.31            | 5.87          | PJLZ2022003 |
      | 1-4O-8                | 2    | 20.55        | 14.15           | 7.98          | PJLZ2022003 |
      | 1-4O-3                | 2    | 35.66        | 4.55            | 1.89          | PJLZ2022003 |
      | 1-4O-9                | 2    | 30.66        | 24.21           | 7.77          | PJLZ2022003 |
      | 1-292NJV              | 2    | 45.89        | 28.50           | 7.98          | PJLZ2022003 |
      | 1-4O-12               | 2    | 0.00         | 0.00            | 0.00          | PJLZ2022003 |
    And 【仅占压超账期0203】流水应变动
      | CREDIT_LIMIT | OCCUPIED_AMOUNT | FROZEN_AMOUNT | OCCUPIED_CHANGE_AMOUNT | CREDIT_TRADE_NO |
      | 65.69        | 1.11            | 1.32          | 59.91                  | PJLZ2022002     |
      | 0.00         | 0.00            | 0.00          | 22.68                  | PJLZ2022002     |
      | 30.66        | 5.87            | 7.77          | 55.02                  | PJLZ2022003     |
      | 20.59        | 0.58            | 1.11          | 30.99                  | PJLZ2022002     |
      | 30.78        | 2.55            | 9.16          | 22.31                  | PJLZ2022002     |
      | 20.55        | 3.13            | 7.98          | 11.02                  | PJLZ2022003     |
      | 45.89        | 10.22           | 7.98          | 18.28                  | PJLZ2022003     |
      | 0.00         | 22.68           | 0.00          | 15.12                  | PJLZ2022002     |
      | 30.66        | 60.89           | 7.77          | 36.68                  | PJLZ2022003     |

  Scenario:【testcase03】有停用 → 订单部分退货 → 订单部分退货冲销
    Given 【仅占压超账期0301】更新客户信用政策
      | CREDIT_GRADE_ID     | OVERDUE_PERIOD_DAYS | CREDIT_CONTROL_DIMENSION |
      | 1326407914543600902 | 1                   | PRODUCT_SET              |
    When 【仅占压超账期0301】客户在B2B下赊销订单
      | SALE_ORG_CODE | CUSTOMER_ENT_CODE | PRODUCT_VARIETY_CRM_CODE | SKU      | AMOUNT | COUNT | ITEM_ID |
      | 1000002261    | 1000006570        | 1-4O-12                  | 10000558 | 19.97  | 3     | 10      |
      | 1000002261    | 1000006570        | 1-4O-3                   | 10000437 | 12.85  | 3     | 20      |
      | 1000002261    | 1000006570        | 1-4O-3                   | 10000434 | 13.05  | 3     | 30      |
    Then 【仅占压超账期0301】商务审核应通过
    And 【仅占压超账期0301】人工审核应通过
    Then 【仅占压超账期0301】产品集合金额应变动
      | productSetCode | annualAvailableExecuteCreditLimit | exceedingAccountPeriodCreditLimit | exceedingAccountPeriodAvailableAmount | exceedingAccountPeriodOccupiedAmount | exceedingAccountPeriodIneffectiveAmount |
      | YYSYZF         | 65.13                             | 65.69                             | 3.35                                  | 61.02                                | 1.32                                    |
      | YYSYYP         | 120.42                            | 147.90                            | 107.97                                | 10.46                                | 29.47                                   |
      | YYSYFYP        | 134.97                            | 140.32                            | 18.91                                 | 99.90                                | 21.51                                   |
    And 【仅占压超账期0301】品种金额应变动
      | productVarietyCrmCode | annualAvailableExecuteCreditLimit | exceedingAccountPeriodCreditLimit | exceedingAccountPeriodAvailableAmount | exceedingAccountPeriodOccupiedAmount | exceedingAccountPeriodIneffectiveAmount |
      | 1-4O-7                | 57.13                             | 55.91                             | 51.76                                 | 0.91                                 | 3.24                                    |
      | 1-4O-14               | 72.50                             | 40.66                             | 27.70                                 | 3.87                                 | 9.09                                    |
      | 1-4O-8                | -9.21                             | 51.33                             | 28.51                                 | 5.68                                 | 17.14                                   |
      | 1-4O-3                | 101.29                            | 63.77                             | 51.90                                 | 6.11                                 | 5.76                                    |
      | 1-4O-9                | 44.11                             | 30.66                             | -60.68                                | 83.57                                | 7.77                                    |
      | 1-292NJV              | -10.43                            | 45.89                             | 27.69                                 | 10.22                                | 7.98                                    |
      | 1-4O-12               | 65.13                             | 65.69                             | 3.35                                  | 61.02                                | 1.32                                    |
    Then 【仅占压超账期0301】临时信用申请应变动
      | productVarietyCrmCode | TYPE | CREDIT_LIMIT | OCCUPIED_AMOUNT | FROZEN_AMOUNT | TRADE_NO    |
      | 1-4O-7                | 2    | 20.59        | 0.58            | 1.11          | PJLZ2022002 |
      | 1-4O-14               | 2    | 20.11        | 1.56            | 3.22          | PJLZ2022002 |
      | 1-4O-8                | 2    | 30.78        | 2.55            | 9.16          | PJLZ2022002 |
      | 1-4O-3                | 2    | 28.11        | 1.56            | 3.87          | PJLZ2022002 |
      | 1-4O-9                | 2    | 0.00         | 22.68           | 0.00          | PJLZ2022002 |
      | 1-292NJV              | 2    | 0.00         | 0.00            | 0.00          | PJLZ2022002 |
      | 1-4O-12               | 2    | 65.69        | 61.02           | 1.32          | PJLZ2022002 |
      | 1-4O-7                | 2    | 35.32        | 0.33            | 2.13          | PJLZ2022003 |
      | 1-4O-14               | 2    | 20.55        | 2.31            | 5.87          | PJLZ2022003 |
      | 1-4O-8                | 2    | 20.55        | 3.13            | 7.98          | PJLZ2022003 |
      | 1-4O-3                | 2    | 35.66        | 4.55            | 1.89          | PJLZ2022003 |
      | 1-4O-9                | 2    | 30.66        | 60.89           | 7.77          | PJLZ2022003 |
      | 1-292NJV              | 2    | 45.89        | 10.22           | 7.98          | PJLZ2022003 |
      | 1-4O-12               | 2    | 0.00         | 0.00            | 0.00          | PJLZ2022003 |
    And 【仅占压超账期0301】流水应变动
      | CREDIT_LIMIT | OCCUPIED_AMOUNT | FROZEN_AMOUNT | OCCUPIED_CHANGE_AMOUNT | CREDIT_TRADE_NO |
      | 65.69        | 1.11            | 1.32          | 59.91                  | PJLZ2022002     |
      | 0.00         | 0.00            | 0.00          | 22.68                  | PJLZ2022002     |
      | 30.66        | 5.87            | 7.77          | 55.02                  | PJLZ2022003     |
    When 【仅占压超账期0302】客户在B2B下赊销订单
      | SALE_ORG_CODE | CUSTOMER_ENT_CODE | PRODUCT_VARIETY_CRM_CODE | SKU      | AMOUNT | COUNT | ITEM_ID |
      | 1000002261    | 1000006570        | 1-4O-7                   | 10000266 | 10.33  | 3     | 10      |
      | 1000002261    | 1000006570        | 1-4O-8                   | 10000286 | 11.11  | 3     | 20      |
      | 1000002261    | 1000006570        | 1-292NJV                 | 10000302 | 18.28  | 1     | 30      |
    Then 【仅占压超账期0302】商务审核应通过
    And 【仅占压超账期0302】人工审核应通过
    Then 【仅占压超账期0302】产品集合金额应变动
      | productSetCode | annualAvailableExecuteCreditLimit | exceedingAccountPeriodCreditLimit | exceedingAccountPeriodAvailableAmount | exceedingAccountPeriodOccupiedAmount | exceedingAccountPeriodIneffectiveAmount |
      | YYSYZF         | 65.13                             | 65.69                             | 3.35                                  | 61.02                                | 1.32                                    |
      | YYSYYP         | 120.42                            | 147.90                            | 43.65                                 | 74.78                                | 29.47                                   |
      | YYSYFYP        | 134.97                            | 140.32                            | 0.63                                  | 118.18                               | 21.51                                   |
    And 【仅占压超账期0302】品种金额应变动
      | productVarietyCrmCode | annualAvailableExecuteCreditLimit | exceedingAccountPeriodCreditLimit | exceedingAccountPeriodAvailableAmount | exceedingAccountPeriodOccupiedAmount | exceedingAccountPeriodIneffectiveAmount |
      | 1-4O-7                | 57.13                             | 55.91                             | 20.77                                 | 31.90                                | 3.24                                    |
      | 1-4O-14               | 72.50                             | 40.66                             | 27.70                                 | 3.87                                 | 9.09                                    |
      | 1-4O-8                | -9.21                             | 51.33                             | -4.82                                 | 39.01                                | 17.14                                   |
      | 1-4O-3                | 101.29                            | 63.77                             | 51.90                                 | 6.11                                 | 5.76                                    |
      | 1-4O-9                | 44.11                             | 30.66                             | -60.68                                | 83.57                                | 7.77                                    |
      | 1-292NJV              | -10.43                            | 45.89                             | 9.41                                  | 28.50                                | 7.98                                    |
      | 1-4O-12               | 65.13                             | 65.69                             | 3.35                                  | 61.02                                | 1.32                                    |
    Then 【仅占压超账期0302】临时信用申请应变动
      | productVarietyCrmCode | TYPE | CREDIT_LIMIT | OCCUPIED_AMOUNT | FROZEN_AMOUNT | TRADE_NO    |
      | 1-4O-7                | 2    | 20.59        | 31.57           | 1.11          | PJLZ2022002 |
      | 1-4O-14               | 2    | 20.11        | 1.56            | 3.22          | PJLZ2022002 |
      | 1-4O-8                | 2    | 30.78        | 24.86           | 9.16          | PJLZ2022002 |
      | 1-4O-3                | 2    | 28.11        | 1.56            | 3.87          | PJLZ2022002 |
      | 1-4O-9                | 2    | 0.00         | 22.68           | 0.00          | PJLZ2022002 |
      | 1-292NJV              | 2    | 0.00         | 0.00            | 0.00          | PJLZ2022002 |
      | 1-4O-12               | 2    | 65.69        | 61.02           | 1.32          | PJLZ2022002 |
      | 1-4O-7                | 2    | 35.32        | 0.33            | 2.13          | PJLZ2022003 |
      | 1-4O-14               | 2    | 20.55        | 2.31            | 5.87          | PJLZ2022003 |
      | 1-4O-8                | 2    | 20.55        | 14.15           | 7.98          | PJLZ2022003 |
      | 1-4O-3                | 2    | 35.66        | 4.55            | 1.89          | PJLZ2022003 |
      | 1-4O-9                | 2    | 30.66        | 60.89           | 7.77          | PJLZ2022003 |
      | 1-292NJV              | 2    | 45.89        | 28.50           | 7.98          | PJLZ2022003 |
      | 1-4O-12               | 2    | 0.00         | 0.00            | 0.00          | PJLZ2022003 |
    And 【仅占压超账期0302】流水应变动
      | CREDIT_LIMIT | OCCUPIED_AMOUNT | FROZEN_AMOUNT | OCCUPIED_CHANGE_AMOUNT | CREDIT_TRADE_NO |
      | 65.69        | 1.11            | 1.32          | 59.91                  | PJLZ2022002     |
      | 0.00         | 0.00            | 0.00          | 22.68                  | PJLZ2022002     |
      | 30.66        | 5.87            | 7.77          | 55.02                  | PJLZ2022003     |
      | 20.59        | 0.58            | 1.11          | 30.99                  | PJLZ2022002     |
      | 30.78        | 2.55            | 9.16          | 22.31                  | PJLZ2022002     |
      | 20.55        | 3.13            | 7.98          | 11.02                  | PJLZ2022003     |
      | 45.89        | 10.22           | 7.98          | 18.28                  | PJLZ2022003     |
    When 【仅占压超账期0303】客户在B2B退货
      | SALE_ORG_CODE | CUSTOMER_ENT_CODE | PRODUCT_VARIETY_CRM_CODE | SKU      | AMOUNT | COUNT | ITEM_ID |
      | 1000002261    | 1000006570        | 1-4O-7                   | 10000437 | 25.70  | 2     | 20      |
      | 1000002261    | 1000006570        | 1-4O-7                   | 10000434 | 26.10  | 2     | 30      |
    Then 【仅占压超账期0303】产品集合金额应变动
      | productSetCode | annualAvailableExecuteCreditLimit | exceedingAccountPeriodCreditLimit | exceedingAccountPeriodAvailableAmount | exceedingAccountPeriodOccupiedAmount | exceedingAccountPeriodIneffectiveAmount |
      | YYSYZF         | 65.13                             | 65.69                             | 3.35                                  | 61.02                                | 1.32                                    |
      | YYSYYP         | 120.42                            | 147.90                            | 43.65                                 | 74.78                                | 29.47                                   |
      | YYSYFYP        | 134.97                            | 140.32                            | 52.43                                 | 66.38                                | 21.51                                   |
    And 【仅占压超账期0303】品种金额应变动
      | productVarietyCrmCode | annualAvailableExecuteCreditLimit | exceedingAccountPeriodCreditLimit | exceedingAccountPeriodAvailableAmount | exceedingAccountPeriodOccupiedAmount | exceedingAccountPeriodIneffectiveAmount |
      | 1-4O-7                | 57.13                             | 55.91                             | 20.77                                 | 31.90                                | 3.24                                    |
      | 1-4O-14               | 72.50                             | 40.66                             | 27.70                                 | 3.87                                 | 9.09                                    |
      | 1-4O-8                | -9.21                             | 51.33                             | -4.82                                 | 39.01                                | 17.14                                   |
      | 1-4O-3                | 101.29                            | 63.77                             | 51.90                                 | 6.11                                 | 5.76                                    |
      | 1-4O-9                | 44.11                             | 30.66                             | -8.88                                 | 31.77                                | 7.77                                    |
      | 1-292NJV              | -10.43                            | 45.89                             | 9.41                                  | 28.50                                | 7.98                                    |
      | 1-4O-12               | 65.13                             | 65.69                             | 3.35                                  | 61.02                                | 1.32                                    |
    Then 【仅占压超账期0303】临时信用申请应变动
      | productVarietyCrmCode | TYPE | CREDIT_LIMIT | OCCUPIED_AMOUNT | FROZEN_AMOUNT | TRADE_NO    |
      | 1-4O-7                | 2    | 20.59        | 31.57           | 1.11          | PJLZ2022002 |
      | 1-4O-14               | 2    | 20.11        | 1.56            | 3.22          | PJLZ2022002 |
      | 1-4O-8                | 2    | 30.78        | 24.86           | 9.16          | PJLZ2022002 |
      | 1-4O-3                | 2    | 28.11        | 1.56            | 3.87          | PJLZ2022002 |
      | 1-4O-9                | 2    | 0.00         | 7.56            | 0.00          | PJLZ2022002 |
      | 1-292NJV              | 2    | 0.00         | 0.00            | 0.00          | PJLZ2022002 |
      | 1-4O-12               | 2    | 65.69        | 61.02           | 1.32          | PJLZ2022002 |
      | 1-4O-7                | 2    | 35.32        | 0.33            | 2.13          | PJLZ2022003 |
      | 1-4O-14               | 2    | 20.55        | 2.31            | 5.87          | PJLZ2022003 |
      | 1-4O-8                | 2    | 20.55        | 14.15           | 7.98          | PJLZ2022003 |
      | 1-4O-3                | 2    | 35.66        | 4.55            | 1.89          | PJLZ2022003 |
      | 1-4O-9                | 2    | 30.66        | 24.21           | 7.77          | PJLZ2022003 |
      | 1-292NJV              | 2    | 45.89        | 28.50           | 7.98          | PJLZ2022003 |
      | 1-4O-12               | 2    | 0.00         | 0.00            | 0.00          | PJLZ2022003 |
    And 【仅占压超账期0303】流水应变动
      | CREDIT_LIMIT | OCCUPIED_AMOUNT | FROZEN_AMOUNT | OCCUPIED_CHANGE_AMOUNT | CREDIT_TRADE_NO |
      | 65.69        | 1.11            | 1.32          | 59.91                  | PJLZ2022002     |
      | 0.00         | 0.00            | 0.00          | 22.68                  | PJLZ2022002     |
      | 30.66        | 5.87            | 7.77          | 55.02                  | PJLZ2022003     |
      | 20.59        | 0.58            | 1.11          | 30.99                  | PJLZ2022002     |
      | 30.78        | 2.55            | 9.16          | 22.31                  | PJLZ2022002     |
      | 20.55        | 3.13            | 7.98          | 11.02                  | PJLZ2022003     |
      | 45.89        | 10.22           | 7.98          | 18.28                  | PJLZ2022003     |
      | 0.00         | 22.68           | 0.00          | 15.12                  | PJLZ2022002     |
      | 30.66        | 60.89           | 7.77          | 36.68                  | PJLZ2022003     |
    And 【仅占压超账期0303】冻结产品集合中某品种
      | customerEntCode | productVarietyCode             | productVarietyCrmCode | saleOrgCode |
      | 1000002261      | ZT-ITEM-PZ-20210401154500-1194 | 1-4O-9                | 1000006570  |
    When 【仅占压超账期0304】客户在B2B退货冲销
      | SALE_ORG_CODE | CUSTOMER_ENT_CODE | PRODUCT_VARIETY_CRM_CODE | SKU      | AMOUNT | COUNT | ITEM_ID |
      | 1000002261    | 1000006570        | 1-4O-7                   | 10000437 | 12.85  | 1     | 20      |
      | 1000002261    | 1000006570        | 1-4O-7                   | 10000434 | 13.05  | 1     | 30      |
    Then 【仅占压超账期0304】产品集合金额应变动
      | productSetCode | annualAvailableExecuteCreditLimit | exceedingAccountPeriodCreditLimit | exceedingAccountPeriodAvailableAmount | exceedingAccountPeriodOccupiedAmount | exceedingAccountPeriodIneffectiveAmount |
      | YYSYZF         | 65.13                             | 65.69                             | 3.35                                  | 61.02                                | 1.32                                    |
      | YYSYYP         | 120.42                            | 147.90                            | 43.65                                 | 74.78                                | 29.47                                   |
      | YYSYFYP        | 90.86                             | 109.66                            | 61.31                                 | 34.61                                | 13.74                                   |
    And 【仅占压超账期0304】品种金额应变动
      | productVarietyCrmCode | annualAvailableExecuteCreditLimit | exceedingAccountPeriodCreditLimit | exceedingAccountPeriodAvailableAmount | exceedingAccountPeriodOccupiedAmount | exceedingAccountPeriodIneffectiveAmount |
      | 1-4O-7                | 57.13                             | 55.91                             | 20.77                                 | 31.90                                | 3.24                                    |
      | 1-4O-14               | 72.50                             | 40.66                             | 27.70                                 | 3.87                                 | 9.09                                    |
      | 1-4O-8                | -9.21                             | 51.33                             | -4.82                                 | 39.01                                | 17.14                                   |
      | 1-4O-3                | 101.29                            | 63.77                             | 51.90                                 | 6.11                                 | 5.76                                    |
      | 1-4O-9                | 44.11                             | 30.66                             | -34.78                                | 57.67                                | 7.77                                    |
      | 1-292NJV              | -10.43                            | 45.89                             | 9.41                                  | 28.50                                | 7.98                                    |
      | 1-4O-12               | 65.13                             | 65.69                             | 3.35                                  | 61.02                                | 1.32                                    |
    Then 【仅占压超账期0304】临时信用申请应变动
      | productVarietyCrmCode | TYPE | CREDIT_LIMIT | OCCUPIED_AMOUNT | FROZEN_AMOUNT | TRADE_NO    |
      | 1-4O-7                | 2    | 20.59        | 31.57           | 1.11          | PJLZ2022002 |
      | 1-4O-14               | 2    | 20.11        | 1.56            | 3.22          | PJLZ2022002 |
      | 1-4O-8                | 2    | 30.78        | 24.86           | 9.16          | PJLZ2022002 |
      | 1-4O-3                | 2    | 28.11        | 1.56            | 3.87          | PJLZ2022002 |
      | 1-4O-9                | 2    | 0.00         | 15.12           | 0.00          | PJLZ2022002 |
      | 1-292NJV              | 2    | 0.00         | 0.00            | 0.00          | PJLZ2022002 |
      | 1-4O-12               | 2    | 65.69        | 61.02           | 1.32          | PJLZ2022002 |
      | 1-4O-7                | 2    | 35.32        | 0.33            | 2.13          | PJLZ2022003 |
      | 1-4O-14               | 2    | 20.55        | 2.31            | 5.87          | PJLZ2022003 |
      | 1-4O-8                | 2    | 20.55        | 14.15           | 7.98          | PJLZ2022003 |
      | 1-4O-3                | 2    | 35.66        | 4.55            | 1.89          | PJLZ2022003 |
      | 1-4O-9                | 2    | 30.66        | 42.55           | 7.77          | PJLZ2022003 |
      | 1-292NJV              | 2    | 45.89        | 28.50           | 7.98          | PJLZ2022003 |
      | 1-4O-12               | 2    | 0.00         | 0.00            | 0.00          | PJLZ2022003 |
    And 【仅占压超账期0304】流水应变动
      | CREDIT_LIMIT | OCCUPIED_AMOUNT | FROZEN_AMOUNT | OCCUPIED_CHANGE_AMOUNT | CREDIT_TRADE_NO |
      | 65.69        | 1.11            | 1.32          | 59.91                  | PJLZ2022002     |
      | 0.00         | 0.00            | 0.00          | 22.68                  | PJLZ2022002     |
      | 30.66        | 5.87            | 7.77          | 55.02                  | PJLZ2022003     |
      | 20.59        | 0.58            | 1.11          | 30.99                  | PJLZ2022002     |
      | 30.78        | 2.55            | 9.16          | 22.31                  | PJLZ2022002     |
      | 20.55        | 3.13            | 7.98          | 11.02                  | PJLZ2022003     |
      | 45.89        | 10.22           | 7.98          | 18.28                  | PJLZ2022003     |
      | 0.00         | 22.68           | 0.00          | 15.12                  | PJLZ2022002     |
      | 30.66        | 60.89           | 7.77          | 36.68                  | PJLZ2022003     |
      | 0.00         | 7.56            | 0.00          | 7.56                   | PJLZ2022002     |
      | 30.66        | 24.21           | 7.77          | 18.34                  | PJLZ2022003     |

  Scenario:【testcase04】无停用 → 订单部分退货 → 订单部分退货冲销 → 月结转
    Given 【仅占压超账期0401】更新客户信用政策
      | CREDIT_GRADE_ID     | OVERDUE_PERIOD_DAYS | CREDIT_CONTROL_DIMENSION |
      | 1326407914543600902 | 1                   | PRODUCT_SET              |
    When 【仅占压超账期0401】客户在B2B下赊销订单
      | SALE_ORG_CODE | CUSTOMER_ENT_CODE | PRODUCT_VARIETY_CRM_CODE | SKU      | AMOUNT | COUNT | ITEM_ID |
      | 1000002261    | 1000006570        | 1-4O-12                  | 10000558 | 19.97  | 3     | 10      |
      | 1000002261    | 1000006570        | 1-4O-3                   | 10000437 | 12.85  | 3     | 20      |
      | 1000002261    | 1000006570        | 1-4O-3                   | 10000434 | 13.05  | 3     | 30      |
    Then 【仅占压超账期0401】商务审核应通过
    And 【仅占压超账期0401】人工审核应通过
    Then 【仅占压超账期0401】产品集合金额应变动
      | productSetCode | annualAvailableExecuteCreditLimit | exceedingAccountPeriodCreditLimit | exceedingAccountPeriodAvailableAmount | exceedingAccountPeriodOccupiedAmount | exceedingAccountPeriodIneffectiveAmount |
      | YYSYZF         | 65.13                             | 65.69                             | 3.35                                  | 61.02                                | 1.32                                    |
      | YYSYYP         | 120.42                            | 147.90                            | 107.97                                | 10.46                                | 29.47                                   |
      | YYSYFYP        | 134.97                            | 140.32                            | 18.91                                 | 99.90                                | 21.51                                   |
    And 【仅占压超账期0401】品种金额应变动
      | productVarietyCrmCode | annualAvailableExecuteCreditLimit | exceedingAccountPeriodCreditLimit | exceedingAccountPeriodAvailableAmount | exceedingAccountPeriodOccupiedAmount | exceedingAccountPeriodIneffectiveAmount |
      | 1-4O-7                | 57.13                             | 55.91                             | 51.76                                 | 0.91                                 | 3.24                                    |
      | 1-4O-14               | 72.50                             | 40.66                             | 27.70                                 | 3.87                                 | 9.09                                    |
      | 1-4O-8                | -9.21                             | 51.33                             | 28.51                                 | 5.68                                 | 17.14                                   |
      | 1-4O-3                | 101.29                            | 63.77                             | 51.90                                 | 6.11                                 | 5.76                                    |
      | 1-4O-9                | 44.11                             | 30.66                             | -60.68                                | 83.57                                | 7.77                                    |
      | 1-292NJV              | -10.43                            | 45.89                             | 27.69                                 | 10.22                                | 7.98                                    |
      | 1-4O-12               | 65.13                             | 65.69                             | 3.35                                  | 61.02                                | 1.32                                    |
    Then 【仅占压超账期0401】临时信用申请应变动
      | productVarietyCrmCode | TYPE | CREDIT_LIMIT | OCCUPIED_AMOUNT | FROZEN_AMOUNT | TRADE_NO    |
      | 1-4O-7                | 2    | 20.59        | 0.58            | 1.11          | PJLZ2022002 |
      | 1-4O-14               | 2    | 20.11        | 1.56            | 3.22          | PJLZ2022002 |
      | 1-4O-8                | 2    | 30.78        | 2.55            | 9.16          | PJLZ2022002 |
      | 1-4O-3                | 2    | 28.11        | 1.56            | 3.87          | PJLZ2022002 |
      | 1-4O-9                | 2    | 0.00         | 22.68           | 0.00          | PJLZ2022002 |
      | 1-292NJV              | 2    | 0.00         | 0.00            | 0.00          | PJLZ2022002 |
      | 1-4O-12               | 2    | 65.69        | 61.02           | 1.32          | PJLZ2022002 |
      | 1-4O-7                | 2    | 35.32        | 0.33            | 2.13          | PJLZ2022003 |
      | 1-4O-14               | 2    | 20.55        | 2.31            | 5.87          | PJLZ2022003 |
      | 1-4O-8                | 2    | 20.55        | 3.13            | 7.98          | PJLZ2022003 |
      | 1-4O-3                | 2    | 35.66        | 4.55            | 1.89          | PJLZ2022003 |
      | 1-4O-9                | 2    | 30.66        | 60.89           | 7.77          | PJLZ2022003 |
      | 1-292NJV              | 2    | 45.89        | 10.22           | 7.98          | PJLZ2022003 |
      | 1-4O-12               | 2    | 0.00         | 0.00            | 0.00          | PJLZ2022003 |
    And 【仅占压超账期0401】流水应变动
      | CREDIT_LIMIT | OCCUPIED_AMOUNT | FROZEN_AMOUNT | OCCUPIED_CHANGE_AMOUNT | CREDIT_TRADE_NO |
      | 65.69        | 1.11            | 1.32          | 59.91                  | PJLZ2022002     |
      | 0.00         | 0.00            | 0.00          | 22.68                  | PJLZ2022002     |
      | 30.66        | 5.87            | 7.77          | 55.02                  | PJLZ2022003     |
    When 【仅占压超账期0402】客户在B2B下赊销订单
      | SALE_ORG_CODE | CUSTOMER_ENT_CODE | PRODUCT_VARIETY_CRM_CODE | SKU      | AMOUNT | COUNT | ITEM_ID |
      | 1000002261    | 1000006570        | 1-4O-7                   | 10000266 | 10.33  | 3     | 10      |
      | 1000002261    | 1000006570        | 1-4O-8                   | 10000286 | 11.11  | 3     | 20      |
      | 1000002261    | 1000006570        | 1-292NJV                 | 10000302 | 18.28  | 1     | 30      |
    Then 【仅占压超账期0402】商务审核应通过
    And 【仅占压超账期0402】人工审核应通过
    Then 【仅占压超账期0402】产品集合金额应变动
      | productSetCode | annualAvailableExecuteCreditLimit | exceedingAccountPeriodCreditLimit | exceedingAccountPeriodAvailableAmount | exceedingAccountPeriodOccupiedAmount | exceedingAccountPeriodIneffectiveAmount |
      | YYSYZF         | 65.13                             | 65.69                             | 3.35                                  | 61.02                                | 1.32                                    |
      | YYSYYP         | 120.42                            | 147.90                            | 43.65                                 | 74.78                                | 29.47                                   |
      | YYSYFYP        | 134.97                            | 140.32                            | 0.63                                  | 118.18                               | 21.51                                   |
    And 【仅占压超账期0402】品种金额应变动
      | productVarietyCrmCode | annualAvailableExecuteCreditLimit | exceedingAccountPeriodCreditLimit | exceedingAccountPeriodAvailableAmount | exceedingAccountPeriodOccupiedAmount | exceedingAccountPeriodIneffectiveAmount |
      | 1-4O-7                | 57.13                             | 55.91                             | 20.77                                 | 31.90                                | 3.24                                    |
      | 1-4O-14               | 72.50                             | 40.66                             | 27.70                                 | 3.87                                 | 9.09                                    |
      | 1-4O-8                | -9.21                             | 51.33                             | -4.82                                 | 39.01                                | 17.14                                   |
      | 1-4O-3                | 101.29                            | 63.77                             | 51.90                                 | 6.11                                 | 5.76                                    |
      | 1-4O-9                | 44.11                             | 30.66                             | -60.68                                | 83.57                                | 7.77                                    |
      | 1-292NJV              | -10.43                            | 45.89                             | 9.41                                  | 28.50                                | 7.98                                    |
      | 1-4O-12               | 65.13                             | 65.69                             | 3.35                                  | 61.02                                | 1.32                                    |
    Then 【仅占压超账期0402】临时信用申请应变动
      | productVarietyCrmCode | TYPE | CREDIT_LIMIT | OCCUPIED_AMOUNT | FROZEN_AMOUNT | TRADE_NO    |
      | 1-4O-7                | 2    | 20.59        | 31.57           | 1.11          | PJLZ2022002 |
      | 1-4O-14               | 2    | 20.11        | 1.56            | 3.22          | PJLZ2022002 |
      | 1-4O-8                | 2    | 30.78        | 24.86           | 9.16          | PJLZ2022002 |
      | 1-4O-3                | 2    | 28.11        | 1.56            | 3.87          | PJLZ2022002 |
      | 1-4O-9                | 2    | 0.00         | 22.68           | 0.00          | PJLZ2022002 |
      | 1-292NJV              | 2    | 0.00         | 0.00            | 0.00          | PJLZ2022002 |
      | 1-4O-12               | 2    | 65.69        | 61.02           | 1.32          | PJLZ2022002 |
      | 1-4O-7                | 2    | 35.32        | 0.33            | 2.13          | PJLZ2022003 |
      | 1-4O-14               | 2    | 20.55        | 2.31            | 5.87          | PJLZ2022003 |
      | 1-4O-8                | 2    | 20.55        | 14.15           | 7.98          | PJLZ2022003 |
      | 1-4O-3                | 2    | 35.66        | 4.55            | 1.89          | PJLZ2022003 |
      | 1-4O-9                | 2    | 30.66        | 60.89           | 7.77          | PJLZ2022003 |
      | 1-292NJV              | 2    | 45.89        | 28.50           | 7.98          | PJLZ2022003 |
      | 1-4O-12               | 2    | 0.00         | 0.00            | 0.00          | PJLZ2022003 |
    And 【仅占压超账期0402】流水应变动
      | CREDIT_LIMIT | OCCUPIED_AMOUNT | FROZEN_AMOUNT | OCCUPIED_CHANGE_AMOUNT | CREDIT_TRADE_NO |
      | 65.69        | 1.11            | 1.32          | 59.91                  | PJLZ2022002     |
      | 0.00         | 0.00            | 0.00          | 22.68                  | PJLZ2022002     |
      | 30.66        | 5.87            | 7.77          | 55.02                  | PJLZ2022003     |
      | 20.59        | 0.58            | 1.11          | 30.99                  | PJLZ2022002     |
      | 30.78        | 2.55            | 9.16          | 22.31                  | PJLZ2022002     |
      | 20.55        | 3.13            | 7.98          | 11.02                  | PJLZ2022003     |
      | 45.89        | 10.22           | 7.98          | 18.28                  | PJLZ2022003     |
    When 【仅占压超账期0403】客户在B2B退货
      | SALE_ORG_CODE | CUSTOMER_ENT_CODE | PRODUCT_VARIETY_CRM_CODE | SKU      | AMOUNT | COUNT | ITEM_ID |
      | 1000002261    | 1000006570        | 1-4O-7                   | 10000437 | 25.70  | 2     | 20      |
      | 1000002261    | 1000006570        | 1-4O-7                   | 10000434 | 26.10  | 2     | 30      |
    Then 【仅占压超账期0403】产品集合金额应变动
      | productSetCode | annualAvailableExecuteCreditLimit | exceedingAccountPeriodCreditLimit | exceedingAccountPeriodAvailableAmount | exceedingAccountPeriodOccupiedAmount | exceedingAccountPeriodIneffectiveAmount |
      | YYSYZF         | 65.13                             | 65.69                             | 3.35                                  | 61.02                                | 1.32                                    |
      | YYSYYP         | 120.42                            | 147.90                            | 43.65                                 | 74.78                                | 29.47                                   |
      | YYSYFYP        | 134.97                            | 140.32                            | 52.43                                 | 66.38                                | 21.51                                   |
    And 【仅占压超账期0403】品种金额应变动
      | productVarietyCrmCode | annualAvailableExecuteCreditLimit | exceedingAccountPeriodCreditLimit | exceedingAccountPeriodAvailableAmount | exceedingAccountPeriodOccupiedAmount | exceedingAccountPeriodIneffectiveAmount |
      | 1-4O-7                | 57.13                             | 55.91                             | 20.77                                 | 31.90                                | 3.24                                    |
      | 1-4O-14               | 72.50                             | 40.66                             | 27.70                                 | 3.87                                 | 9.09                                    |
      | 1-4O-8                | -9.21                             | 51.33                             | -4.82                                 | 39.01                                | 17.14                                   |
      | 1-4O-3                | 101.29                            | 63.77                             | 51.90                                 | 6.11                                 | 5.76                                    |
      | 1-4O-9                | 44.11                             | 30.66                             | -8.88                                 | 31.77                                | 7.77                                    |
      | 1-292NJV              | -10.43                            | 45.89                             | 9.41                                  | 28.50                                | 7.98                                    |
      | 1-4O-12               | 65.13                             | 65.69                             | 3.35                                  | 61.02                                | 1.32                                    |
    Then 【仅占压超账期0403】临时信用申请应变动
      | productVarietyCrmCode | TYPE | CREDIT_LIMIT | OCCUPIED_AMOUNT | FROZEN_AMOUNT | TRADE_NO    |
      | 1-4O-7                | 2    | 20.59        | 31.57           | 1.11          | PJLZ2022002 |
      | 1-4O-14               | 2    | 20.11        | 1.56            | 3.22          | PJLZ2022002 |
      | 1-4O-8                | 2    | 30.78        | 24.86           | 9.16          | PJLZ2022002 |
      | 1-4O-3                | 2    | 28.11        | 1.56            | 3.87          | PJLZ2022002 |
      | 1-4O-9                | 2    | 0.00         | 7.56            | 0.00          | PJLZ2022002 |
      | 1-292NJV              | 2    | 0.00         | 0.00            | 0.00          | PJLZ2022002 |
      | 1-4O-12               | 2    | 65.69        | 61.02           | 1.32          | PJLZ2022002 |
      | 1-4O-7                | 2    | 35.32        | 0.33            | 2.13          | PJLZ2022003 |
      | 1-4O-14               | 2    | 20.55        | 2.31            | 5.87          | PJLZ2022003 |
      | 1-4O-8                | 2    | 20.55        | 14.15           | 7.98          | PJLZ2022003 |
      | 1-4O-3                | 2    | 35.66        | 4.55            | 1.89          | PJLZ2022003 |
      | 1-4O-9                | 2    | 30.66        | 24.21           | 7.77          | PJLZ2022003 |
      | 1-292NJV              | 2    | 45.89        | 28.50           | 7.98          | PJLZ2022003 |
      | 1-4O-12               | 2    | 0.00         | 0.00            | 0.00          | PJLZ2022003 |
    And 【仅占压超账期0403】流水应变动
      | CREDIT_LIMIT | OCCUPIED_AMOUNT | FROZEN_AMOUNT | OCCUPIED_CHANGE_AMOUNT | CREDIT_TRADE_NO |
      | 65.69        | 1.11            | 1.32          | 59.91                  | PJLZ2022002     |
      | 0.00         | 0.00            | 0.00          | 22.68                  | PJLZ2022002     |
      | 30.66        | 5.87            | 7.77          | 55.02                  | PJLZ2022003     |
      | 20.59        | 0.58            | 1.11          | 30.99                  | PJLZ2022002     |
      | 30.78        | 2.55            | 9.16          | 22.31                  | PJLZ2022002     |
      | 20.55        | 3.13            | 7.98          | 11.02                  | PJLZ2022003     |
      | 45.89        | 10.22           | 7.98          | 18.28                  | PJLZ2022003     |
      | 0.00         | 22.68           | 0.00          | 15.12                  | PJLZ2022002     |
      | 30.66        | 60.89           | 7.77          | 36.68                  | PJLZ2022003     |
    When 【仅占压超账期0404】客户在B2B退货冲销
      | SALE_ORG_CODE | CUSTOMER_ENT_CODE | PRODUCT_VARIETY_CRM_CODE | SKU      | AMOUNT | COUNT | ITEM_ID |
      | 1000002261    | 1000006570        | 1-4O-7                   | 10000437 | 12.85  | 1     | 20      |
      | 1000002261    | 1000006570        | 1-4O-7                   | 10000434 | 13.05  | 1     | 30      |
    Then 【仅占压超账期0404】产品集合金额应变动
      | productSetCode | annualAvailableExecuteCreditLimit | exceedingAccountPeriodCreditLimit | exceedingAccountPeriodAvailableAmount | exceedingAccountPeriodOccupiedAmount | exceedingAccountPeriodIneffectiveAmount |
      | YYSYZF         | 65.13                             | 65.69                             | 3.35                                  | 61.02                                | 1.32                                    |
      | YYSYYP         | 120.42                            | 147.90                            | 43.65                                 | 74.78                                | 29.47                                   |
      | YYSYFYP        | 134.97                            | 140.32                            | 26.53                                 | 92.28                                | 21.51                                   |
    And 【仅占压超账期0404】品种金额应变动
      | productVarietyCrmCode | annualAvailableExecuteCreditLimit | exceedingAccountPeriodCreditLimit | exceedingAccountPeriodAvailableAmount | exceedingAccountPeriodOccupiedAmount | exceedingAccountPeriodIneffectiveAmount |
      | 1-4O-7                | 57.13                             | 55.91                             | 20.77                                 | 31.90                                | 3.24                                    |
      | 1-4O-14               | 72.50                             | 40.66                             | 27.70                                 | 3.87                                 | 9.09                                    |
      | 1-4O-8                | -9.21                             | 51.33                             | -4.82                                 | 39.01                                | 17.14                                   |
      | 1-4O-3                | 101.29                            | 63.77                             | 51.90                                 | 6.11                                 | 5.76                                    |
      | 1-4O-9                | 44.11                             | 30.66                             | -34.78                                | 57.67                                | 7.77                                    |
      | 1-292NJV              | -10.43                            | 45.89                             | 9.41                                  | 28.50                                | 7.98                                    |
      | 1-4O-12               | 65.13                             | 65.69                             | 3.35                                  | 61.02                                | 1.32                                    |
    Then 【仅占压超账期0404】临时信用申请应变动
      | productVarietyCrmCode | TYPE | CREDIT_LIMIT | OCCUPIED_AMOUNT | FROZEN_AMOUNT | TRADE_NO    |
      | 1-4O-7                | 2    | 20.59        | 31.57           | 1.11          | PJLZ2022002 |
      | 1-4O-14               | 2    | 20.11        | 1.56            | 3.22          | PJLZ2022002 |
      | 1-4O-8                | 2    | 30.78        | 24.86           | 9.16          | PJLZ2022002 |
      | 1-4O-3                | 2    | 28.11        | 1.56            | 3.87          | PJLZ2022002 |
      | 1-4O-9                | 2    | 0.00         | 15.12           | 0.00          | PJLZ2022002 |
      | 1-292NJV              | 2    | 0.00         | 0.00            | 0.00          | PJLZ2022002 |
      | 1-4O-12               | 2    | 65.69        | 61.02           | 1.32          | PJLZ2022002 |
      | 1-4O-7                | 2    | 35.32        | 0.33            | 2.13          | PJLZ2022003 |
      | 1-4O-14               | 2    | 20.55        | 2.31            | 5.87          | PJLZ2022003 |
      | 1-4O-8                | 2    | 20.55        | 14.15           | 7.98          | PJLZ2022003 |
      | 1-4O-3                | 2    | 35.66        | 4.55            | 1.89          | PJLZ2022003 |
      | 1-4O-9                | 2    | 30.66        | 42.55           | 7.77          | PJLZ2022003 |
      | 1-292NJV              | 2    | 45.89        | 28.50           | 7.98          | PJLZ2022003 |
      | 1-4O-12               | 2    | 0.00         | 0.00            | 0.00          | PJLZ2022003 |
    And 【仅占压超账期0404】流水应变动
      | CREDIT_LIMIT | OCCUPIED_AMOUNT | FROZEN_AMOUNT | OCCUPIED_CHANGE_AMOUNT | CREDIT_TRADE_NO |
      | 65.69        | 1.11            | 1.32          | 59.91                  | PJLZ2022002     |
      | 0.00         | 0.00            | 0.00          | 22.68                  | PJLZ2022002     |
      | 30.66        | 5.87            | 7.77          | 55.02                  | PJLZ2022003     |
      | 20.59        | 0.58            | 1.11          | 30.99                  | PJLZ2022002     |
      | 30.78        | 2.55            | 9.16          | 22.31                  | PJLZ2022002     |
      | 20.55        | 3.13            | 7.98          | 11.02                  | PJLZ2022003     |
      | 45.89        | 10.22           | 7.98          | 18.28                  | PJLZ2022003     |
      | 0.00         | 22.68           | 0.00          | 15.12                  | PJLZ2022002     |
      | 30.66        | 60.89           | 7.77          | 36.68                  | PJLZ2022003     |
      | 0.00         | 7.56            | 0.00          | 7.56                   | PJLZ2022002     |
      | 30.66        | 24.21           | 7.77          | 18.34                  | PJLZ2022003     |
    When 【仅占压超账期0405】月度结转
    Then 【仅占压超账期0405】产品集合金额应变动
      | productSetCode | annualAvailableExecuteCreditLimit | exceedingAccountPeriodCreditLimit | exceedingAccountPeriodAvailableAmount | exceedingAccountPeriodOccupiedAmount | exceedingAccountPeriodIneffectiveAmount |
      | YYSYZF         | 4.11                              | 0.00                              | 0.00                                  | 0.00                                 | 0.00                                    |
      | YYSYYP         | 45.64                             | 0.00                              | 0.00                                  | 0.00                                 | 0.00                                    |
      | YYSYFYP        | 42.69                             | 0.00                              | 0.00                                  | 0.00                                 | 0.00                                    |
    And 【仅占压超账期0405】品种金额应变动
      | productVarietyCrmCode | annualAvailableExecuteCreditLimit | exceedingAccountPeriodCreditLimit | exceedingAccountPeriodAvailableAmount | exceedingAccountPeriodOccupiedAmount | exceedingAccountPeriodIneffectiveAmount |
      | 1-4O-7                | 25.23                             | 0.00                              | 0.00                                  | 0.00                                 | 0.00                                    |
      | 1-4O-14               | 68.63                             | 0.00                              | 0.00                                  | 0.00                                 | 0.00                                    |
      | 1-4O-8                | -48.22                            | 0.00                              | 0.00                                  | 0.00                                 | 0.00                                    |
      | 1-4O-3                | 95.18                             | 0.00                              | 0.00                                  | 0.00                                 | 0.00                                    |
      | 1-4O-9                | -13.56                            | 0.00                              | 0.00                                  | 0.00                                 | 0.00                                    |
      | 1-292NJV              | -38.93                            | 0.00                              | 0.00                                  | 0.00                                 | 0.00                                    |
      | 1-4O-12               | 4.11                              | 0.00                              | 0.00                                  | 0.00                                 | 0.00                                    |
    And 【仅占压超账期0405】流水应变动
      | CREDIT_LIMIT | OCCUPIED_AMOUNT | FROZEN_AMOUNT | OCCUPIED_CHANGE_AMOUNT | CREDIT_TRADE_NO |
      | 65.69        | 1.11            | 1.32          | 59.91                  | PJLZ2022002     |
      | 0.00         | 0.00            | 0.00          | 22.68                  | PJLZ2022002     |
      | 30.66        | 5.87            | 7.77          | 55.02                  | PJLZ2022003     |
      | 20.59        | 0.58            | 1.11          | 30.99                  | PJLZ2022002     |
      | 30.78        | 2.55            | 9.16          | 22.31                  | PJLZ2022002     |
      | 20.55        | 3.13            | 7.98          | 11.02                  | PJLZ2022003     |
      | 45.89        | 10.22           | 7.98          | 18.28                  | PJLZ2022003     |
      | 0.00         | 22.68           | 0.00          | 15.12                  | PJLZ2022002     |
      | 30.66        | 60.89           | 7.77          | 36.68                  | PJLZ2022003     |
      | 0.00         | 7.56            | 0.00          | 7.56                   | PJLZ2022002     |
      | 30.66        | 24.21           | 7.77          | 18.34                  | PJLZ2022003     |
      | 20.59        | 31.57           | 1.11          | 31.57                  | PJLZ2022002     |
      | 20.11        | 1.56            | 3.22          | 1.56                   | PJLZ2022002     |
      | 30.78        | 24.86           | 9.16          | 24.86                  | PJLZ2022002     |
      | 28.11        | 1.56            | 3.87          | 1.56                   | PJLZ2022002     |
      | 0            | 15.12           | 0             | 15.12                  | PJLZ2022002     |
      | 65.69        | 61.02           | 1.32          | 61.02                  | PJLZ2022002     |
      | 35.32        | 0.33            | 2.13          | 0.33                   | PJLZ2022003     |
      | 20.55        | 2.31            | 5.87          | 2.31                   | PJLZ2022003     |
      | 20.55        | 14.15           | 7.98          | 14.15                  | PJLZ2022003     |
      | 35.66        | 4.55            | 1.89          | 4.55                   | PJLZ2022003     |
      | 30.66        | 42.55           | 7.77          | 42.55                  | PJLZ2022003     |
      | 45.89        | 28.50           | 7.98          | 28.50                  | PJLZ2022003     |
      | 113.91       | 56.78           | 0             | 31.90                  | CPJ202212060005     |
      | 110.32       | 37.82           | 0             | 3.87                   | CPJ202212060005     |
      | 101.92       | 111.13          | 0             | 39.01                  | CPJ202212060005     |
      | 127.23       | 25.94           | 0             | 6.11                   | CPJ202212060005     |
      | 55.22        | 11.11           | 0             | 57.67                  | CPJ202212060005     |
      | 80.34        | 90.77           | 0             | 28.50                  | CPJ202212060005     |
      | 121.99       | 56.86           | 0             | 61.02                  | CPJ202212060005     |

  Scenario:【testcase05】无停用 → 月结转 → 订单部分退货 → 订单部分退货冲销
    Given 【仅占压超账期0501】更新客户信用政策
      | CREDIT_GRADE_ID     | OVERDUE_PERIOD_DAYS | CREDIT_CONTROL_DIMENSION |
      | 1326407914543600902 | 1                   | PRODUCT_SET              |
    When 【仅占压超账期0501】客户在B2B下赊销订单
      | SALE_ORG_CODE | CUSTOMER_ENT_CODE | PRODUCT_VARIETY_CRM_CODE | SKU      | AMOUNT | COUNT | ITEM_ID |
      | 1000002261    | 1000006570        | 1-4O-12                  | 10000558 | 19.97  | 3     | 10      |
      | 1000002261    | 1000006570        | 1-4O-3                   | 10000437 | 12.85  | 3     | 20      |
      | 1000002261    | 1000006570        | 1-4O-3                   | 10000434 | 13.05  | 3     | 30      |
    Then 【仅占压超账期0501】商务审核应通过
    And 【仅占压超账期0501】人工审核应通过
    Then 【仅占压超账期0501】产品集合金额应变动
      | productSetCode | annualAvailableExecuteCreditLimit | exceedingAccountPeriodCreditLimit | exceedingAccountPeriodAvailableAmount | exceedingAccountPeriodOccupiedAmount | exceedingAccountPeriodIneffectiveAmount |
      | YYSYZF         | 65.13                             | 65.69                             | 3.35                                  | 61.02                                | 1.32                                    |
      | YYSYYP         | 120.42                            | 147.90                            | 107.97                                | 10.46                                | 29.47                                   |
      | YYSYFYP        | 134.97                            | 140.32                            | 18.91                                 | 99.90                                | 21.51                                   |
    And 【仅占压超账期0501】品种金额应变动
      | productVarietyCrmCode | annualAvailableExecuteCreditLimit | exceedingAccountPeriodCreditLimit | exceedingAccountPeriodAvailableAmount | exceedingAccountPeriodOccupiedAmount | exceedingAccountPeriodIneffectiveAmount |
      | 1-4O-7                | 57.13                             | 55.91                             | 51.76                                 | 0.91                                 | 3.24                                    |
      | 1-4O-14               | 72.50                             | 40.66                             | 27.70                                 | 3.87                                 | 9.09                                    |
      | 1-4O-8                | -9.21                             | 51.33                             | 28.51                                 | 5.68                                 | 17.14                                   |
      | 1-4O-3                | 101.29                            | 63.77                             | 51.90                                 | 6.11                                 | 5.76                                    |
      | 1-4O-9                | 44.11                             | 30.66                             | -60.68                                | 83.57                                | 7.77                                    |
      | 1-292NJV              | -10.43                            | 45.89                             | 27.69                                 | 10.22                                | 7.98                                    |
      | 1-4O-12               | 65.13                             | 65.69                             | 3.35                                  | 61.02                                | 1.32                                    |
    Then 【仅占压超账期0501】临时信用申请应变动
      | productVarietyCrmCode | TYPE | CREDIT_LIMIT | OCCUPIED_AMOUNT | FROZEN_AMOUNT | TRADE_NO    |
      | 1-4O-7                | 2    | 20.59        | 0.58            | 1.11          | PJLZ2022002 |
      | 1-4O-14               | 2    | 20.11        | 1.56            | 3.22          | PJLZ2022002 |
      | 1-4O-8                | 2    | 30.78        | 2.55            | 9.16          | PJLZ2022002 |
      | 1-4O-3                | 2    | 28.11        | 1.56            | 3.87          | PJLZ2022002 |
      | 1-4O-9                | 2    | 0.00         | 22.68           | 0.00          | PJLZ2022002 |
      | 1-292NJV              | 2    | 0.00         | 0.00            | 0.00          | PJLZ2022002 |
      | 1-4O-12               | 2    | 65.69        | 61.02           | 1.32          | PJLZ2022002 |
      | 1-4O-7                | 2    | 35.32        | 0.33            | 2.13          | PJLZ2022003 |
      | 1-4O-14               | 2    | 20.55        | 2.31            | 5.87          | PJLZ2022003 |
      | 1-4O-8                | 2    | 20.55        | 3.13            | 7.98          | PJLZ2022003 |
      | 1-4O-3                | 2    | 35.66        | 4.55            | 1.89          | PJLZ2022003 |
      | 1-4O-9                | 2    | 30.66        | 60.89           | 7.77          | PJLZ2022003 |
      | 1-292NJV              | 2    | 45.89        | 10.22           | 7.98          | PJLZ2022003 |
      | 1-4O-12               | 2    | 0.00         | 0.00            | 0.00          | PJLZ2022003 |
    And 【仅占压超账期0501】流水应变动
      | CREDIT_LIMIT | OCCUPIED_AMOUNT | FROZEN_AMOUNT | OCCUPIED_CHANGE_AMOUNT | CREDIT_TRADE_NO |
      | 65.69        | 1.11            | 1.32          | 59.91                  | PJLZ2022002     |
      | 0.00         | 0.00            | 0.00          | 22.68                  | PJLZ2022002     |
      | 30.66        | 5.87            | 7.77          | 55.02                  | PJLZ2022003     |
    When 【仅占压超账期0502】客户在B2B下赊销订单
      | SALE_ORG_CODE | CUSTOMER_ENT_CODE | PRODUCT_VARIETY_CRM_CODE | SKU      | AMOUNT | COUNT | ITEM_ID |
      | 1000002261    | 1000006570        | 1-4O-7                   | 10000266 | 10.33  | 3     | 10      |
      | 1000002261    | 1000006570        | 1-4O-8                   | 10000286 | 11.11  | 3     | 20      |
      | 1000002261    | 1000006570        | 1-292NJV                 | 10000302 | 18.28  | 1     | 30      |
    Then 【仅占压超账期0502】商务审核应通过
    And 【仅占压超账期0502】人工审核应通过
    Then 【仅占压超账期0502】产品集合金额应变动
      | productSetCode | annualAvailableExecuteCreditLimit | exceedingAccountPeriodCreditLimit | exceedingAccountPeriodAvailableAmount | exceedingAccountPeriodOccupiedAmount | exceedingAccountPeriodIneffectiveAmount |
      | YYSYZF         | 65.13                             | 65.69                             | 3.35                                  | 61.02                                | 1.32                                    |
      | YYSYYP         | 120.42                            | 147.90                            | 43.65                                 | 74.78                                | 29.47                                   |
      | YYSYFYP        | 134.97                            | 140.32                            | 0.63                                  | 118.18                               | 21.51                                   |
    And 【仅占压超账期0502】品种金额应变动
      | productVarietyCrmCode | annualAvailableExecuteCreditLimit | exceedingAccountPeriodCreditLimit | exceedingAccountPeriodAvailableAmount | exceedingAccountPeriodOccupiedAmount | exceedingAccountPeriodIneffectiveAmount |
      | 1-4O-7                | 57.13                             | 55.91                             | 20.77                                 | 31.90                                | 3.24                                    |
      | 1-4O-14               | 72.50                             | 40.66                             | 27.70                                 | 3.87                                 | 9.09                                    |
      | 1-4O-8                | -9.21                             | 51.33                             | -4.82                                 | 39.01                                | 17.14                                   |
      | 1-4O-3                | 101.29                            | 63.77                             | 51.90                                 | 6.11                                 | 5.76                                    |
      | 1-4O-9                | 44.11                             | 30.66                             | -60.68                                | 83.57                                | 7.77                                    |
      | 1-292NJV              | -10.43                            | 45.89                             | 9.41                                  | 28.50                                | 7.98                                    |
      | 1-4O-12               | 65.13                             | 65.69                             | 3.35                                  | 61.02                                | 1.32                                    |
    Then 【仅占压超账期0502】临时信用申请应变动
      | productVarietyCrmCode | TYPE | CREDIT_LIMIT | OCCUPIED_AMOUNT | FROZEN_AMOUNT | TRADE_NO    |
      | 1-4O-7                | 2    | 20.59        | 31.57           | 1.11          | PJLZ2022002 |
      | 1-4O-14               | 2    | 20.11        | 1.56            | 3.22          | PJLZ2022002 |
      | 1-4O-8                | 2    | 30.78        | 24.86           | 9.16          | PJLZ2022002 |
      | 1-4O-3                | 2    | 28.11        | 1.56            | 3.87          | PJLZ2022002 |
      | 1-4O-9                | 2    | 0.00         | 22.68           | 0.00          | PJLZ2022002 |
      | 1-292NJV              | 2    | 0.00         | 0.00            | 0.00          | PJLZ2022002 |
      | 1-4O-12               | 2    | 65.69        | 61.02           | 1.32          | PJLZ2022002 |
      | 1-4O-7                | 2    | 35.32        | 0.33            | 2.13          | PJLZ2022003 |
      | 1-4O-14               | 2    | 20.55        | 2.31            | 5.87          | PJLZ2022003 |
      | 1-4O-8                | 2    | 20.55        | 14.15           | 7.98          | PJLZ2022003 |
      | 1-4O-3                | 2    | 35.66        | 4.55            | 1.89          | PJLZ2022003 |
      | 1-4O-9                | 2    | 30.66        | 60.89           | 7.77          | PJLZ2022003 |
      | 1-292NJV              | 2    | 45.89        | 28.50           | 7.98          | PJLZ2022003 |
      | 1-4O-12               | 2    | 0.00         | 0.00            | 0.00          | PJLZ2022003 |
    And 【仅占压超账期0502】流水应变动
      | CREDIT_LIMIT | OCCUPIED_AMOUNT | FROZEN_AMOUNT | OCCUPIED_CHANGE_AMOUNT | CREDIT_TRADE_NO |
      | 65.69        | 1.11            | 1.32          | 59.91                  | PJLZ2022002     |
      | 0.00         | 0.00            | 0.00          | 22.68                  | PJLZ2022002     |
      | 30.66        | 5.87            | 7.77          | 55.02                  | PJLZ2022003     |
      | 20.59        | 0.58            | 1.11          | 30.99                  | PJLZ2022002     |
      | 30.78        | 2.55            | 9.16          | 22.31                  | PJLZ2022002     |
      | 20.55        | 3.13            | 7.98          | 11.02                  | PJLZ2022003     |
      | 45.89        | 10.22           | 7.98          | 18.28                  | PJLZ2022003     |
    When 【仅占压超账期0503】月度结转
    Then 【仅占压超账期0503】产品集合金额应变动
      | productSetCode | annualAvailableExecuteCreditLimit | exceedingAccountPeriodCreditLimit | exceedingAccountPeriodAvailableAmount | exceedingAccountPeriodOccupiedAmount | exceedingAccountPeriodIneffectiveAmount |
      | YYSYZF         | 4.11                              | 0.00                              | 0.00                                  | 0.00                                 | 0.00                                    |
      | YYSYYP         | 45.64                             | 0.00                              | 0.00                                  | 0.00                                 | 0.00                                    |
      | YYSYFYP        | 16.79                             | 0.00                              | 0.00                                  | 0.00                                 | 0.00                                    |
    And 【仅占压超账期0503】品种金额应变动
      | productVarietyCrmCode | annualAvailableExecuteCreditLimit | exceedingAccountPeriodCreditLimit | exceedingAccountPeriodAvailableAmount | exceedingAccountPeriodOccupiedAmount | exceedingAccountPeriodIneffectiveAmount |
      | 1-4O-7                | 25.23                             | 0.00                              | 0.00                                  | 0.00                                 | 0.00                                    |
      | 1-4O-14               | 68.63                             | 0.00                              | 0.00                                  | 0.00                                 | 0.00                                    |
      | 1-4O-8                | -48.22                            | 0.00                              | 0.00                                  | 0.00                                 | 0.00                                    |
      | 1-4O-3                | 95.18                             | 0.00                              | 0.00                                  | 0.00                                 | 0.00                                    |
      | 1-4O-9                | -39.46                            | 0.00                              | 0.00                                  | 0.00                                 | 0.00                                    |
      | 1-292NJV              | -38.93                            | 0.00                              | 0.00                                  | 0.00                                 | 0.00                                    |
      | 1-4O-12               | 4.11                              | 0.00                              | 0.00                                  | 0.00                                 | 0.00                                    |
    And 【仅占压超账期0503】流水应变动
      | CREDIT_LIMIT | OCCUPIED_AMOUNT | FROZEN_AMOUNT | OCCUPIED_CHANGE_AMOUNT | CREDIT_TRADE_NO |
      | 65.69        | 1.11            | 1.32          | 59.91                  | PJLZ2022002     |
      | 0.00         | 0.00            | 0.00          | 22.68                  | PJLZ2022002     |
      | 30.66        | 5.87            | 7.77          | 55.02                  | PJLZ2022003     |
      | 20.59        | 0.58            | 1.11          | 30.99                  | PJLZ2022002     |
      | 30.78        | 2.55            | 9.16          | 22.31                  | PJLZ2022002     |
      | 20.55        | 3.13            | 7.98          | 11.02                  | PJLZ2022003     |
      | 45.89        | 10.22           | 7.98          | 18.28                  | PJLZ2022003     |
      | 20.59        | 31.57           | 1.11          | 31.57                  | PJLZ2022002     |
      | 20.11        | 1.56            | 3.22          | 1.56                   | PJLZ2022002     |
      | 30.78        | 24.86           | 9.16          | 24.86                  | PJLZ2022002     |
      | 28.11        | 1.56            | 3.87          | 1.56                   | PJLZ2022002     |
      | 0            | 22.68           | 0             | 22.68                  | PJLZ2022002     |
      | 65.69        | 61.02           | 1.32          | 61.02                  | PJLZ2022002     |
      | 35.32        | 0.33            | 2.13          | 0.33                   | PJLZ2022003     |
      | 20.55        | 2.31            | 5.87          | 2.31                   | PJLZ2022003     |
      | 20.55        | 14.15           | 7.98          | 14.15                  | PJLZ2022003     |
      | 35.66        | 4.55            | 1.89          | 4.55                   | PJLZ2022003     |
      | 30.66        | 60.89           | 7.77          | 60.89                  | PJLZ2022003     |
      | 45.89        | 28.50           | 7.98          | 28.50                  | PJLZ2022003     |
      | 113.91       | 56.78           | 0             | 31.90                  | CPJ202212060005     |
      | 110.32       | 37.82           | 0             | 3.87                   | CPJ202212060005     |
      | 101.92       | 111.13          | 0             | 39.01                  | CPJ202212060005     |
      | 127.23       | 25.94           | 0             | 6.11                   | CPJ202212060005     |
      | 55.22        | 11.11           | 0             | 83.57                  | CPJ202212060005     |
      | 80.34        | 90.77           | 0             | 28.50                  | CPJ202212060005     |
      | 121.99       | 56.86           | 0             | 61.02                  | CPJ202212060005     |
    When 【仅占压超账期0504】客户在B2B退货
      | SALE_ORG_CODE | CUSTOMER_ENT_CODE | PRODUCT_VARIETY_CRM_CODE | SKU      | AMOUNT | COUNT | ITEM_ID |
      | 1000002261    | 1000006570        | 1-4O-7                   | 10000437 | 25.70  | 2     | 20      |
      | 1000002261    | 1000006570        | 1-4O-7                   | 10000434 | 26.10  | 2     | 30      |
    Then 【仅占压超账期0504】产品集合金额应变动
      | productSetCode | annualAvailableExecuteCreditLimit | exceedingAccountPeriodCreditLimit | exceedingAccountPeriodAvailableAmount | exceedingAccountPeriodOccupiedAmount | exceedingAccountPeriodIneffectiveAmount |
      | YYSYZF         | 4.11                              | 0.00                              | 0.00                                  | 0.00                                 | 0.00                                    |
      | YYSYYP         | 45.64                             | 0.00                              | 0.00                                  | 0.00                                 | 0.00                                    |
      | YYSYFYP        | 68.59                             | 0.00                              | 0.00                                  | 0.00                                 | 0.00                                    |
    And 【仅占压超账期0504】品种金额应变动
      | productVarietyCrmCode | annualAvailableExecuteCreditLimit | exceedingAccountPeriodCreditLimit | exceedingAccountPeriodAvailableAmount | exceedingAccountPeriodOccupiedAmount | exceedingAccountPeriodIneffectiveAmount |
      | 1-4O-7                | 25.23                             | 0.00                              | 0.00                                  | 0.00                                 | 0.00                                    |
      | 1-4O-14               | 68.63                             | 0.00                              | 0.00                                  | 0.00                                 | 0.00                                    |
      | 1-4O-8                | -48.22                            | 0.00                              | 0.00                                  | 0.00                                 | 0.00                                    |
      | 1-4O-3                | 95.18                             | 0.00                              | 0.00                                  | 0.00                                 | 0.00                                    |
      | 1-4O-9                | 12.34                             | 0.00                              | 0.00                                  | 0.00                                 | 0.00                                    |
      | 1-292NJV              | -38.93                            | 0.00                              | 0.00                                  | 0.00                                 | 0.00                                    |
      | 1-4O-12               | 4.11                              | 0.00                              | 0.00                                  | 0.00                                 | 0.00                                    |
    And 【仅占压超账期0504】流水应变动
      | CREDIT_LIMIT | OCCUPIED_AMOUNT | FROZEN_AMOUNT | OCCUPIED_CHANGE_AMOUNT | CREDIT_TRADE_NO |
      | 65.69        | 1.11            | 1.32          | 59.91                  | PJLZ2022002     |
      | 0.00         | 0.00            | 0.00          | 22.68                  | PJLZ2022002     |
      | 30.66        | 5.87            | 7.77          | 55.02                  | PJLZ2022003     |
      | 20.59        | 0.58            | 1.11          | 30.99                  | PJLZ2022002     |
      | 30.78        | 2.55            | 9.16          | 22.31                  | PJLZ2022002     |
      | 20.55        | 3.13            | 7.98          | 11.02                  | PJLZ2022003     |
      | 45.89        | 10.22           | 7.98          | 18.28                  | PJLZ2022003     |
      | 20.59        | 31.57           | 1.11          | 31.57                  | PJLZ2022002     |
      | 20.11        | 1.56            | 3.22          | 1.56                   | PJLZ2022002     |
      | 30.78        | 24.86           | 9.16          | 24.86                  | PJLZ2022002     |
      | 28.11        | 1.56            | 3.87          | 1.56                   | PJLZ2022002     |
      | 0            | 22.68           | 0             | 22.68                  | PJLZ2022002     |
      | 65.69        | 61.02           | 1.32          | 61.02                  | PJLZ2022002     |
      | 35.32        | 0.33            | 2.13          | 0.33                   | PJLZ2022003     |
      | 20.55        | 2.31            | 5.87          | 2.31                   | PJLZ2022003     |
      | 20.55        | 14.15           | 7.98          | 14.15                  | PJLZ2022003     |
      | 35.66        | 4.55            | 1.89          | 4.55                   | PJLZ2022003     |
      | 30.66        | 60.89           | 7.77          | 60.89                  | PJLZ2022003     |
      | 45.89        | 28.50           | 7.98          | 28.50                  | PJLZ2022003     |
      | 113.91       | 56.78           | 0             | 31.90                  | CPJ202212060005     |
      | 110.32       | 37.82           | 0             | 3.87                   | CPJ202212060005     |
      | 101.92       | 111.13          | 0             | 39.01                  | CPJ202212060005     |
      | 127.23       | 25.94           | 0             | 6.11                   | CPJ202212060005     |
      | 55.22        | 11.11           | 0             | 83.57                  | CPJ202212060005     |
      | 80.34        | 90.77           | 0             | 28.50                  | CPJ202212060005     |
      | 121.99       | 56.86           | 0             | 61.02                  | CPJ202212060005     |
      | 55.22        | 94.68           | 0             | 51.80                  | CPJ202212060005     |
    When 【仅占压超账期0505】客户在B2B退货冲销
      | SALE_ORG_CODE | CUSTOMER_ENT_CODE | PRODUCT_VARIETY_CRM_CODE | SKU      | AMOUNT | COUNT | ITEM_ID |
      | 1000002261    | 1000006570        | 1-4O-7                   | 10000437 | 12.85  | 1     | 20      |
      | 1000002261    | 1000006570        | 1-4O-7                   | 10000434 | 13.05  | 1     | 30      |
    Then 【仅占压超账期0505】产品集合金额应变动
      | productSetCode | annualAvailableExecuteCreditLimit | exceedingAccountPeriodCreditLimit | exceedingAccountPeriodAvailableAmount | exceedingAccountPeriodOccupiedAmount | exceedingAccountPeriodIneffectiveAmount |
      | YYSYZF         | 4.11                              | 0.00                              | 0.00                                  | 0.00                                 | 0.00                                    |
      | YYSYYP         | 45.64                             | 0.00                              | 0.00                                  | 0.00                                 | 0.00                                    |
      | YYSYFYP        | 42.69                             | 0.00                              | 0.00                                  | 0.00                                 | 0.00                                    |
    And 【仅占压超账期0505】品种金额应变动
      | productVarietyCrmCode | annualAvailableExecuteCreditLimit | exceedingAccountPeriodCreditLimit | exceedingAccountPeriodAvailableAmount | exceedingAccountPeriodOccupiedAmount | exceedingAccountPeriodIneffectiveAmount |
      | 1-4O-7                | 25.23                             | 0.00                              | 0.00                                  | 0.00                                 | 0.00                                    |
      | 1-4O-14               | 68.63                             | 0.00                              | 0.00                                  | 0.00                                 | 0.00                                    |
      | 1-4O-8                | -48.22                            | 0.00                              | 0.00                                  | 0.00                                 | 0.00                                    |
      | 1-4O-3                | 95.18                             | 0.00                              | 0.00                                  | 0.00                                 | 0.00                                    |
      | 1-4O-9                | -13.56                            | 0.00                              | 0.00                                  | 0.00                                 | 0.00                                    |
      | 1-292NJV              | -38.93                            | 0.00                              | 0.00                                  | 0.00                                 | 0.00                                    |
      | 1-4O-12               | 4.11                              | 0.00                              | 0.00                                  | 0.00                                 | 0.00                                    |
    And 【仅占压超账期0505】流水应变动
      | CREDIT_LIMIT | OCCUPIED_AMOUNT | FROZEN_AMOUNT | OCCUPIED_CHANGE_AMOUNT | CREDIT_TRADE_NO |
      | 65.69        | 1.11            | 1.32          | 59.91                  | PJLZ2022002     |
      | 0.00         | 0.00            | 0.00          | 22.68                  | PJLZ2022002     |
      | 30.66        | 5.87            | 7.77          | 55.02                  | PJLZ2022003     |
      | 20.59        | 0.58            | 1.11          | 30.99                  | PJLZ2022002     |
      | 30.78        | 2.55            | 9.16          | 22.31                  | PJLZ2022002     |
      | 20.55        | 3.13            | 7.98          | 11.02                  | PJLZ2022003     |
      | 45.89        | 10.22           | 7.98          | 18.28                  | PJLZ2022003     |
      | 20.59        | 31.57           | 1.11          | 31.57                  | PJLZ2022002     |
      | 20.11        | 1.56            | 3.22          | 1.56                   | PJLZ2022002     |
      | 30.78        | 24.86           | 9.16          | 24.86                  | PJLZ2022002     |
      | 28.11        | 1.56            | 3.87          | 1.56                   | PJLZ2022002     |
      | 0            | 22.68           | 0             | 22.68                  | PJLZ2022002     |
      | 65.69        | 61.02           | 1.32          | 61.02                  | PJLZ2022002     |
      | 35.32        | 0.33            | 2.13          | 0.33                   | PJLZ2022003     |
      | 20.55        | 2.31            | 5.87          | 2.31                   | PJLZ2022003     |
      | 20.55        | 14.15           | 7.98          | 14.15                  | PJLZ2022003     |
      | 35.66        | 4.55            | 1.89          | 4.55                   | PJLZ2022003     |
      | 30.66        | 60.89           | 7.77          | 60.89                  | PJLZ2022003     |
      | 45.89        | 28.50           | 7.98          | 28.50                  | PJLZ2022003     |
      | 113.91       | 56.78           | 0             | 31.90                  | CPJ202212060005     |
      | 110.32       | 37.82           | 0             | 3.87                   | CPJ202212060005     |
      | 101.92       | 111.13          | 0             | 39.01                  | CPJ202212060005     |
      | 127.23       | 25.94           | 0             | 6.11                   | CPJ202212060005     |
      | 55.22        | 11.11           | 0             | 83.57                  | CPJ202212060005     |
      | 80.34        | 90.77           | 0             | 28.50                  | CPJ202212060005     |
      | 121.99       | 56.86           | 0             | 61.02                  | CPJ202212060005     |
      | 55.22        | 94.68           | 0             | 51.80                  | CPJ202212060005     |
      | 55.22        | 42.88           | 0             | 25.90                  | CPJ202212060005     |