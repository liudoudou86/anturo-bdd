@occupy
Feature:信用使用 → 正向占用 → 仅占压超额度 → 管控维度为产品集合

  Scenario:【testcase01】信用使用 → 正向占压 → 仅占压超额度 → 管控维度为产品集合 → 无停用
    Given 【仅占压超额度0101】更新客户信用政策
      | CREDIT_GRADE_ID     | OVERDUE_PERIOD_DAYS | CREDIT_CONTROL_DIMENSION |
      | 1326407914543600902 | 90                  | PRODUCT_SET              |
    When 【仅占压超额度0101】客户在B2B下赊销订单
      | SALE_ORG_CODE | CUSTOMER_ENT_CODE | PRODUCT_VARIETY_CRM_CODE | SKU      | AMOUNT | COUNT | ITEM_ID |
      | 1000002261    | 1000006570        | 1-4O-12                  | 10000558 | 19.97  | 3     | 10      |
      | 1000002261    | 1000006570        | 1-4O-3                   | 10000437 | 12.85  | 3     | 20      |
      | 1000002261    | 1000006570        | 1-4O-3                   | 10000434 | 13.05  | 3     | 30      |
    Then 【仅占压超额度0101】商务审核应通过
    And 【仅占压超额度0101】人工审核应通过
    Then 【仅占压超额度0101】产品集合金额应变动
      | productSetCode | annualAvailableExecuteCreditLimit | exceedingQuotaCreditLimit | exceedingQuotaAvailableAmount | exceedingQuotaOccupiedAmount | exceedingQuotaIneffectiveAmount |
      | YYSYZF         | 0.00                              | 65.69                     | 3.35                          | 61.02                        | 1.32                            |
      | YYSYYP         | 0.00                              | 147.90                    | 107.97                        | 10.46                        | 29.47                           |
      | YYSYFYP        | 0.00                              | 140.32                    | 18.91                         | 99.90                        | 21.51                           |
    And 【仅占压超额度0101】品种金额应变动
      | productVarietyCrmCode | annualAvailableExecuteCreditLimit | exceedingQuotaCreditLimit | exceedingQuotaAvailableAmount | exceedingQuotaOccupiedAmount | exceedingQuotaIneffectiveAmount |
      | 1-4O-7                | 0.00                              | 55.91                     | 51.76                         | 0.91                         | 3.24                            |
      | 1-4O-14               | 0.00                              | 40.66                     | 27.70                         | 3.87                         | 9.09                            |
      | 1-4O-8                | 0.00                              | 51.33                     | 28.51                         | 5.68                         | 17.14                           |
      | 1-4O-3                | 0.00                              | 63.77                     | 51.90                         | 6.11                         | 5.76                            |
      | 1-4O-9                | 0.00                              | 30.66                     | -60.68                        | 83.57                        | 7.77                            |
      | 1-292NJV              | 0.00                              | 45.89                     | 27.69                         | 10.22                        | 7.98                            |
      | 1-4O-12               | 0.00                              | 65.69                     | 3.35                          | 61.02                        | 1.32                            |
    Then 【仅占压超额度0101】临时信用申请应变动
      | productVarietyCrmCode | TYPE | CREDIT_LIMIT | OCCUPIED_AMOUNT | FROZEN_AMOUNT | TRADE_NO    |
      | 1-4O-7                | 1    | 20.59        | 0.58            | 1.11          | PJLZ2022002 |
      | 1-4O-14               | 1    | 20.11        | 1.56            | 3.22          | PJLZ2022002 |
      | 1-4O-8                | 1    | 30.78        | 2.55            | 9.16          | PJLZ2022002 |
      | 1-4O-3                | 1    | 28.11        | 1.56            | 3.87          | PJLZ2022002 |
      | 1-4O-9                | 1    | 0.00         | 22.68           | 0.00          | PJLZ2022002 |
      | 1-292NJV              | 1    | 0.00         | 0.00            | 0.00          | PJLZ2022002 |
      | 1-4O-12               | 1    | 65.69        | 61.02           | 1.32          | PJLZ2022002 |
      | 1-4O-7                | 1    | 35.32        | 0.33            | 2.13          | PJLZ2022003 |
      | 1-4O-14               | 1    | 20.55        | 2.31            | 5.87          | PJLZ2022003 |
      | 1-4O-8                | 1    | 20.55        | 3.13            | 7.98          | PJLZ2022003 |
      | 1-4O-3                | 1    | 35.66        | 4.55            | 1.89          | PJLZ2022003 |
      | 1-4O-9                | 1    | 30.66        | 60.89           | 7.77          | PJLZ2022003 |
      | 1-292NJV              | 1    | 45.89        | 10.22           | 7.98          | PJLZ2022003 |
      | 1-4O-12               | 1    | 0.00         | 0.00            | 0.00          | PJLZ2022003 |
    And 【仅占压超额度0101】流水应变动
      | CREDIT_LIMIT | OCCUPIED_AMOUNT | FROZEN_AMOUNT | OCCUPIED_CHANGE_AMOUNT | CREDIT_TRADE_NO |
      | 65.69        | 1.11            | 1.32          | 59.91                  | PJLZ2022002     |
      | 0.00         | 0.00            | 0.00          | 22.68                  | PJLZ2022002     |
      | 30.66        | 5.87            | 7.77          | 55.02                  | PJLZ2022003     |
    When 【仅占压超额度0102】客户在B2B下赊销订单
      | SALE_ORG_CODE | CUSTOMER_ENT_CODE | PRODUCT_VARIETY_CRM_CODE | SKU      | AMOUNT | COUNT | ITEM_ID |
      | 1000002261    | 1000006570        | 1-4O-7                   | 10000266 | 10.33  | 3     | 10      |
      | 1000002261    | 1000006570        | 1-4O-8                   | 10000286 | 11.11  | 3     | 20      |
      | 1000002261    | 1000006570        | 1-292NJV                 | 10000302 | 18.28  | 1     | 30      |
    Then 【仅占压超额度0102】商务审核应通过
    And 【仅占压超额度0102】人工审核应通过
    Then 【仅占压超额度0102】产品集合金额应变动
      | productSetCode | annualAvailableExecuteCreditLimit | exceedingQuotaCreditLimit | exceedingQuotaAvailableAmount | exceedingQuotaOccupiedAmount | exceedingQuotaIneffectiveAmount |
      | YYSYZF         | 0.00                              | 65.69                     | 3.35                          | 61.02                        | 1.32                            |
      | YYSYYP         | 0.00                              | 147.90                    | 43.65                         | 74.78                        | 29.47                           |
      | YYSYFYP        | 0.00                              | 140.32                    | 0.63                          | 118.18                       | 21.51                           |
    And 【仅占压超额度0102】品种金额应变动
      | productVarietyCrmCode | annualAvailableExecuteCreditLimit | exceedingQuotaCreditLimit | exceedingQuotaAvailableAmount | exceedingQuotaOccupiedAmount | exceedingQuotaIneffectiveAmount |
      | 1-4O-7                | 0.00                              | 55.91                     | 20.77                         | 31.90                        | 3.24                            |
      | 1-4O-14               | 0.00                              | 40.66                     | 27.70                         | 3.87                         | 9.09                            |
      | 1-4O-8                | 0.00                              | 51.33                     | -4.82                         | 39.01                        | 17.14                           |
      | 1-4O-3                | 0.00                              | 63.77                     | 51.90                         | 6.11                         | 5.76                            |
      | 1-4O-9                | 0.00                              | 30.66                     | -60.68                        | 83.57                        | 7.77                            |
      | 1-292NJV              | 0.00                              | 45.89                     | 9.41                          | 28.50                        | 7.98                            |
      | 1-4O-12               | 0.00                              | 65.69                     | 3.35                          | 61.02                        | 1.32                            |
    Then 【仅占压超额度0102】临时信用申请应变动
      | productVarietyCrmCode | TYPE | CREDIT_LIMIT | OCCUPIED_AMOUNT | FROZEN_AMOUNT | TRADE_NO    |
      | 1-4O-7                | 1    | 20.59        | 31.57           | 1.11          | PJLZ2022002 |
      | 1-4O-14               | 1    | 20.11        | 1.56            | 3.22          | PJLZ2022002 |
      | 1-4O-8                | 1    | 30.78        | 24.86           | 9.16          | PJLZ2022002 |
      | 1-4O-3                | 1    | 28.11        | 1.56            | 3.87          | PJLZ2022002 |
      | 1-4O-9                | 1    | 0.00         | 22.68           | 0.00          | PJLZ2022002 |
      | 1-292NJV              | 1    | 0.00         | 0.00            | 0.00          | PJLZ2022002 |
      | 1-4O-12               | 1    | 65.69        | 61.02           | 1.32          | PJLZ2022002 |
      | 1-4O-7                | 1    | 35.32        | 0.33            | 2.13          | PJLZ2022003 |
      | 1-4O-14               | 1    | 20.55        | 2.31            | 5.87          | PJLZ2022003 |
      | 1-4O-8                | 1    | 20.55        | 14.15           | 7.98          | PJLZ2022003 |
      | 1-4O-3                | 1    | 35.66        | 4.55            | 1.89          | PJLZ2022003 |
      | 1-4O-9                | 1    | 30.66        | 60.89           | 7.77          | PJLZ2022003 |
      | 1-292NJV              | 1    | 45.89        | 28.50           | 7.98          | PJLZ2022003 |
      | 1-4O-12               | 1    | 0.00         | 0.00            | 0.00          | PJLZ2022003 |
    And 【仅占压超额度0102】流水应变动
      | CREDIT_LIMIT | OCCUPIED_AMOUNT | FROZEN_AMOUNT | OCCUPIED_CHANGE_AMOUNT | CREDIT_TRADE_NO |
      | 65.69        | 1.11            | 1.32          | 59.91                  | PJLZ2022002     |
      | 0.00         | 0.00            | 0.00          | 22.68                  | PJLZ2022002     |
      | 30.66        | 5.87            | 7.77          | 55.02                  | PJLZ2022003     |
      | 20.59        | 0.58            | 1.11          | 30.99                  | PJLZ2022002     |
      | 30.78        | 2.55            | 9.16          | 22.31                  | PJLZ2022002     |
      | 20.55        | 3.13            | 7.98          | 11.02                  | PJLZ2022003     |
      | 45.89        | 10.22           | 7.98          | 18.28                  | PJLZ2022003     |

  Scenario:【testcase02】信用使用 → 正向占压 → 仅占压超额度 → 管控维度为产品集合 → 有停用
    Given 【仅占压超额度0201】更新客户信用政策
      | CREDIT_GRADE_ID     | OVERDUE_PERIOD_DAYS | CREDIT_CONTROL_DIMENSION |
      | 1326407914543600902 | 90                  | PRODUCT_SET              |
    And 【仅占压超额度0201】冻结产品集合中某品种
      | customerEntCode | productVarietyCode             | productVarietyCrmCode | saleOrgCode |
      | 1000002261      | ZT-ITEM-PZ-20210401154500-7177 | 1-4O-14               | 1000006570  |
    When 【仅占压超额度0201】客户在B2B下赊销订单
      | SALE_ORG_CODE | CUSTOMER_ENT_CODE | PRODUCT_VARIETY_CRM_CODE | SKU      | AMOUNT | COUNT | ITEM_ID |
      | 1000002261    | 1000006570        | 1-4O-12                  | 10000558 | 19.97  | 3     | 10      |
      | 1000002261    | 1000006570        | 1-4O-3                   | 10000437 | 12.85  | 3     | 20      |
      | 1000002261    | 1000006570        | 1-4O-3                   | 10000434 | 13.05  | 3     | 30      |
    Then 【仅占压超额度0201】商务审核应通过
    And 【仅占压超额度0201】人工审核应通过
    Then 【仅占压超额度0201】产品集合金额应变动
      | productSetCode | annualAvailableExecuteCreditLimit | exceedingQuotaCreditLimit | exceedingQuotaAvailableAmount | exceedingQuotaOccupiedAmount | exceedingQuotaIneffectiveAmount |
      | YYSYZF         | 0.00                              | 65.69                     | 3.35                          | 61.02                        | 1.32                            |
      | YYSYYP         | 0.00                              | 107.24                    | 80.27                         | 6.59                         | 20.38                           |
      | YYSYFYP        | 0.00                              | 140.32                    | 18.91                         | 99.90                        | 21.51                           |
    And 【仅占压超额度0201】品种金额应变动
      | productVarietyCrmCode | annualAvailableExecuteCreditLimit | exceedingQuotaCreditLimit | exceedingQuotaAvailableAmount | exceedingQuotaOccupiedAmount | exceedingQuotaIneffectiveAmount |
      | 1-4O-7                | 0.00                              | 55.91                     | 51.76                         | 0.91                         | 3.24                            |
      | 1-4O-14               | 0.00                              | 40.66                     | 27.70                         | 3.87                         | 9.09                            |
      | 1-4O-8                | 0.00                              | 51.33                     | 28.51                         | 5.68                         | 17.14                           |
      | 1-4O-3                | 0.00                              | 63.77                     | 51.90                         | 6.11                         | 5.76                            |
      | 1-4O-9                | 0.00                              | 30.66                     | -60.68                        | 83.57                        | 7.77                            |
      | 1-292NJV              | 0.00                              | 45.89                     | 27.69                         | 10.22                        | 7.98                            |
      | 1-4O-12               | 0.00                              | 65.69                     | 3.35                          | 61.02                        | 1.32                            |
    Then 【仅占压超额度0201】临时信用申请应变动
      | productVarietyCrmCode | TYPE | CREDIT_LIMIT | OCCUPIED_AMOUNT | FROZEN_AMOUNT | TRADE_NO    |
      | 1-4O-7                | 1    | 20.59        | 0.58            | 1.11          | PJLZ2022002 |
      | 1-4O-14               | 1    | 20.11        | 1.56            | 3.22          | PJLZ2022002 |
      | 1-4O-8                | 1    | 30.78        | 2.55            | 9.16          | PJLZ2022002 |
      | 1-4O-3                | 1    | 28.11        | 1.56            | 3.87          | PJLZ2022002 |
      | 1-4O-9                | 1    | 0.00         | 22.68           | 0.00          | PJLZ2022002 |
      | 1-292NJV              | 1    | 0.00         | 0.00            | 0.00          | PJLZ2022002 |
      | 1-4O-12               | 1    | 65.69        | 61.02           | 1.32          | PJLZ2022002 |
      | 1-4O-7                | 1    | 35.32        | 0.33            | 2.13          | PJLZ2022003 |
      | 1-4O-14               | 1    | 20.55        | 2.31            | 5.87          | PJLZ2022003 |
      | 1-4O-8                | 1    | 20.55        | 3.13            | 7.98          | PJLZ2022003 |
      | 1-4O-3                | 1    | 35.66        | 4.55            | 1.89          | PJLZ2022003 |
      | 1-4O-9                | 1    | 30.66        | 60.89           | 7.77          | PJLZ2022003 |
      | 1-292NJV              | 1    | 45.89        | 10.22           | 7.98          | PJLZ2022003 |
      | 1-4O-12               | 1    | 0.00         | 0.00            | 0.00          | PJLZ2022003 |
    And 【仅占压超额度0201】流水应变动
      | CREDIT_LIMIT | OCCUPIED_AMOUNT | FROZEN_AMOUNT | OCCUPIED_CHANGE_AMOUNT | CREDIT_TRADE_NO |
      | 65.69        | 1.11            | 1.32          | 59.91                  | PJLZ2022002     |
      | 0.00         | 0.00            | 0.00          | 22.68                  | PJLZ2022002     |
      | 30.66        | 5.87            | 7.77          | 55.02                  | PJLZ2022003     |
    When 【仅占压超额度0202】客户在B2B下赊销订单
      | SALE_ORG_CODE | CUSTOMER_ENT_CODE | PRODUCT_VARIETY_CRM_CODE | SKU      | AMOUNT | COUNT | ITEM_ID |
      | 1000002261    | 1000006570        | 1-4O-7                   | 10000266 | 10.33  | 3     | 10      |
      | 1000002261    | 1000006570        | 1-4O-8                   | 10000286 | 11.11  | 3     | 20      |
      | 1000002261    | 1000006570        | 1-292NJV                 | 10000302 | 18.28  | 1     | 30      |
    Then 【仅占压超额度0202】商务审核应通过
    And 【仅占压超额度0202】人工审核应通过
    Then 【仅占压超额度0202】产品集合金额应变动
      | productSetCode | annualAvailableExecuteCreditLimit | exceedingQuotaCreditLimit | exceedingQuotaAvailableAmount | exceedingQuotaOccupiedAmount | exceedingQuotaIneffectiveAmount |
      | YYSYZF         | 0.00                              | 65.69                     | 3.35                          | 61.02                        | 1.32                            |
      | YYSYYP         | 0.00                              | 107.24                    | 15.95                         | 70.91                        | 20.38                           |
      | YYSYFYP        | 0.00                              | 140.32                    | 0.63                          | 118.18                       | 21.51                           |
    And 【仅占压超额度0202】品种金额应变动
      | productVarietyCrmCode | annualAvailableExecuteCreditLimit | exceedingQuotaCreditLimit | exceedingQuotaAvailableAmount | exceedingQuotaOccupiedAmount | exceedingQuotaIneffectiveAmount |
      | 1-4O-7                | 0.00                              | 55.91                     | 20.77                         | 31.90                        | 3.24                            |
      | 1-4O-14               | 0.00                              | 40.66                     | 27.70                         | 3.87                         | 9.09                            |
      | 1-4O-8                | 0.00                              | 51.33                     | -4.82                         | 39.01                        | 17.14                           |
      | 1-4O-3                | 0.00                              | 63.77                     | 51.90                         | 6.11                         | 5.76                            |
      | 1-4O-9                | 0.00                              | 30.66                     | -60.68                        | 83.57                        | 7.77                            |
      | 1-292NJV              | 0.00                              | 45.89                     | 9.41                          | 28.50                        | 7.98                            |
      | 1-4O-12               | 0.00                              | 65.69                     | 3.35                          | 61.02                        | 1.32                            |
    Then 【仅占压超额度0202】临时信用申请应变动
      | productVarietyCrmCode | TYPE | CREDIT_LIMIT | OCCUPIED_AMOUNT | FROZEN_AMOUNT | TRADE_NO    |
      | 1-4O-7                | 1    | 20.59        | 31.57           | 1.11          | PJLZ2022002 |
      | 1-4O-14               | 1    | 20.11        | 1.56            | 3.22          | PJLZ2022002 |
      | 1-4O-8                | 1    | 30.78        | 9.53            | 9.16          | PJLZ2022002 |
      | 1-4O-3                | 1    | 28.11        | 1.56            | 3.87          | PJLZ2022002 |
      | 1-4O-9                | 1    | 0.00         | 22.68           | 0.00          | PJLZ2022002 |
      | 1-292NJV              | 1    | 0.00         | 0.00            | 0.00          | PJLZ2022002 |
      | 1-4O-12               | 1    | 65.69        | 61.02           | 1.32          | PJLZ2022002 |
      | 1-4O-7                | 1    | 35.32        | 0.33            | 2.13          | PJLZ2022003 |
      | 1-4O-14               | 1    | 20.55        | 2.31            | 5.87          | PJLZ2022003 |
      | 1-4O-8                | 1    | 20.55        | 29.48           | 7.98          | PJLZ2022003 |
      | 1-4O-3                | 1    | 35.66        | 4.55            | 1.89          | PJLZ2022003 |
      | 1-4O-9                | 1    | 30.66        | 60.89           | 7.77          | PJLZ2022003 |
      | 1-292NJV              | 1    | 45.89        | 28.50           | 7.98          | PJLZ2022003 |
      | 1-4O-12               | 1    | 0.00         | 0.00            | 0.00          | PJLZ2022003 |
    And 【仅占压超额度0202】流水应变动
      | CREDIT_LIMIT | OCCUPIED_AMOUNT | FROZEN_AMOUNT | OCCUPIED_CHANGE_AMOUNT | CREDIT_TRADE_NO |
      | 65.69        | 1.11            | 1.32          | 59.91                  | PJLZ2022002     |
      | 0.00         | 0.00            | 0.00          | 22.68                  | PJLZ2022002     |
      | 30.66        | 5.87            | 7.77          | 55.02                  | PJLZ2022003     |
      | 20.59        | 0.58            | 1.11          | 30.99                  | PJLZ2022002     |
      | 30.78        | 2.55            | 9.16          | 6.98                   | PJLZ2022002     |
      | 20.55        | 3.13            | 7.98          | 26.35                  | PJLZ2022003     |
      | 45.89        | 10.22           | 7.98          | 18.28                  | PJLZ2022003     |