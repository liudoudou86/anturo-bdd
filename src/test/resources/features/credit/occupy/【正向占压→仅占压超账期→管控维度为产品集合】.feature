@occupy
Feature:信用使用 → 正向占用 → 仅占压超账期 → 管控维度为产品集合

  Scenario:【testcase01】无停用
    Given 【仅占压超账期0101】更新客户信用政策
      | CREDIT_GRADE_ID     | OVERDUE_PERIOD_DAYS | CREDIT_CONTROL_DIMENSION |
      | 1326407914543600902 | 1                   | PRODUCT_SET              |
    When 【仅占压超账期0101】客户在B2B下赊销订单
      | SALE_ORG_CODE | CUSTOMER_ENT_CODE | PRODUCT_VARIETY_CRM_CODE | SKU      | AMOUNT | COUNT | ITEM_ID |
      | 1000002261    | 1000006570        | 1-4O-12                  | 10000558 | 19.97  | 3     | 10      |
      | 1000002261    | 1000006570        | 1-4O-3                   | 10000437 | 12.85  | 3     | 20      |
      | 1000002261    | 1000006570        | 1-4O-3                   | 10000434 | 13.05  | 3     | 30      |
    Then 【仅占压超账期0101】商务审核应通过
    And 【仅占压超账期0101】人工审核应通过
    Then 【仅占压超账期0101】产品集合金额应变动
      | productSetCode | annualAvailableExecuteCreditLimit | exceedingAccountPeriodCreditLimit | exceedingAccountPeriodAvailableAmount | exceedingAccountPeriodOccupiedAmount | exceedingAccountPeriodIneffectiveAmount |
      | YYSYZF         | 65.13                             | 65.69                             | 3.35                                  | 61.02                                | 1.32                                    |
      | YYSYYP         | 120.42                            | 147.90                            | 107.97                                | 10.46                                | 29.47                                   |
      | YYSYFYP        | 134.97                            | 140.32                            | 18.91                                 | 99.90                                | 21.51                                   |
    And 【仅占压超账期0101】品种金额应变动
      | productVarietyCrmCode | annualAvailableExecuteCreditLimit | exceedingAccountPeriodCreditLimit | exceedingAccountPeriodAvailableAmount | exceedingAccountPeriodOccupiedAmount | exceedingAccountPeriodIneffectiveAmount |
      | 1-4O-7                | 57.13                             | 55.91                             | 51.76                                 | 0.91                                 | 3.24                                    |
      | 1-4O-14               | 72.50                             | 40.66                             | 27.70                                 | 3.87                                 | 9.09                                    |
      | 1-4O-8                | -9.21                             | 51.33                             | 28.51                                 | 5.68                                 | 17.14                                   |
      | 1-4O-3                | 101.29                            | 63.77                             | 51.90                                 | 6.11                                 | 5.76                                    |
      | 1-4O-9                | 44.11                             | 30.66                             | -60.68                                | 83.57                                | 7.77                                    |
      | 1-292NJV              | -10.43                            | 45.89                             | 27.69                                 | 10.22                                | 7.98                                    |
      | 1-4O-12               | 65.13                             | 65.69                             | 3.35                                  | 61.02                                | 1.32                                    |
    Then 【仅占压超账期0101】临时信用申请应变动
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
    And 【仅占压超账期0101】流水应变动
      | CREDIT_LIMIT | OCCUPIED_AMOUNT | FROZEN_AMOUNT | OCCUPIED_CHANGE_AMOUNT | CREDIT_TRADE_NO |
      | 65.69        | 1.11            | 1.32          | 59.91                  | PJLZ2022002     |
      | 0.00         | 0.00            | 0.00          | 22.68                  | PJLZ2022002     |
      | 30.66        | 5.87            | 7.77          | 55.02                  | PJLZ2022003     |
    When 【仅占压超账期0102】客户在B2B下赊销订单
      | SALE_ORG_CODE | CUSTOMER_ENT_CODE | PRODUCT_VARIETY_CRM_CODE | SKU      | AMOUNT | COUNT | ITEM_ID |
      | 1000002261    | 1000006570        | 1-4O-7                   | 10000266 | 10.33  | 3     | 10      |
      | 1000002261    | 1000006570        | 1-4O-8                   | 10000286 | 11.11  | 3     | 20      |
      | 1000002261    | 1000006570        | 1-292NJV                 | 10000302 | 18.28  | 1     | 30      |
    Then 【仅占压超账期0102】商务审核应通过
    And 【仅占压超账期0102】人工审核应通过
    Then 【仅占压超账期0102】产品集合金额应变动
      | productSetCode | annualAvailableExecuteCreditLimit | exceedingAccountPeriodCreditLimit | exceedingAccountPeriodAvailableAmount | exceedingAccountPeriodOccupiedAmount | exceedingAccountPeriodIneffectiveAmount |
      | YYSYZF         | 65.13                             | 65.69                             | 3.35                                  | 61.02                                | 1.32                                    |
      | YYSYYP         | 120.42                            | 147.90                            | 43.65                                 | 74.78                                | 29.47                                   |
      | YYSYFYP        | 134.97                            | 140.32                            | 0.63                                  | 118.18                               | 21.51                                   |
    And 【仅占压超账期0102】品种金额应变动
      | productVarietyCrmCode | annualAvailableExecuteCreditLimit | exceedingAccountPeriodCreditLimit | exceedingAccountPeriodAvailableAmount | exceedingAccountPeriodOccupiedAmount | exceedingAccountPeriodIneffectiveAmount |
      | 1-4O-7                | 57.13                             | 55.91                             | 20.77                                 | 31.90                                | 3.24                                    |
      | 1-4O-14               | 72.50                             | 40.66                             | 27.70                                 | 3.87                                 | 9.09                                    |
      | 1-4O-8                | -9.21                             | 51.33                             | -4.82                                 | 39.01                                | 17.14                                   |
      | 1-4O-3                | 101.29                            | 63.77                             | 51.90                                 | 6.11                                 | 5.76                                    |
      | 1-4O-9                | 44.11                             | 30.66                             | -60.68                                | 83.57                                | 7.77                                    |
      | 1-292NJV              | -10.43                            | 45.89                             | 9.41                                  | 28.50                                | 7.98                                    |
      | 1-4O-12               | 65.13                             | 65.69                             | 3.35                                  | 61.02                                | 1.32                                    |
    Then 【仅占压超账期0102】临时信用申请应变动
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
    And 【仅占压超账期0102】流水应变动
      | CREDIT_LIMIT | OCCUPIED_AMOUNT | FROZEN_AMOUNT | OCCUPIED_CHANGE_AMOUNT | CREDIT_TRADE_NO |
      | 65.69        | 1.11            | 1.32          | 59.91                  | PJLZ2022002     |
      | 0.00         | 0.00            | 0.00          | 22.68                  | PJLZ2022002     |
      | 30.66        | 5.87            | 7.77          | 55.02                  | PJLZ2022003     |
      | 20.59        | 0.58            | 1.11          | 30.99                  | PJLZ2022002     |
      | 30.78        | 2.55            | 9.16          | 22.31                  | PJLZ2022002     |
      | 20.55        | 3.13            | 7.98          | 11.02                  | PJLZ2022003     |
      | 45.89        | 10.22           | 7.98          | 18.28                  | PJLZ2022003     |