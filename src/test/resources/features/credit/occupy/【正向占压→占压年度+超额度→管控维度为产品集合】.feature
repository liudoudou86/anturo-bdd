@occupy
Feature:信用使用 → 正向占用 → 占压年度资信+超额度 → 管控维度为产品集合

  Scenario:【testcase01】信用使用 → 正向占压 → 占压年度资信加超额度 → 管控维度为产品集合 → 无停用
    Given 【占压年度超额度0101】更新客户信用政策
      | CREDIT_GRADE_ID     | OVERDUE_PERIOD_DAYS | CREDIT_CONTROL_DIMENSION |
      | 1326407914543600902 | 90                  | PRODUCT_SET              |
    When 【占压年度超额度0101】客户在B2B下赊销订单
      | SALE_ORG_CODE | CUSTOMER_ENT_CODE | PRODUCT_VARIETY_CRM_CODE | SKU      | AMOUNT | COUNT | ITEM_ID |
      | 1000002261    | 1000006570        | 1-4O-12                  | 10000558 | 19.97  | 3     | 10      |
      | 1000002261    | 1000006570        | 1-4O-3                   | 10000437 | 12.85  | 3     | 20      |
      | 1000002261    | 1000006570        | 1-4O-3                   | 10000434 | 13.05  | 3     | 30      |
    Then 【占压年度超额度0101】商务审核应通过
    And 【占压年度超额度0101】人工审核应通过
    Then 【占压年度超额度0101】产品集合金额应变动
      | productSetCode | annualAvailableExecuteCreditLimit | annualExecuteCreditLimit | annualOccupiedAmount | exceedingQuotaCreditLimit | exceedingQuotaAvailableAmount | exceedingQuotaOccupiedAmount | exceedingQuotaIneffectiveAmount |
      | YYSYZF         | 0.00                              | 121.99                   | 121.99               | 65.69                     | 25.01                         | 39.36                        | 1.32                            |
      | YYSYYP         | 120.42                            | 326.15                   | 205.73               | 147.90                    | 107.97                        | 10.46                        | 29.47                           |
      | YYSYFYP        | 0.00                              | 262.79                   | 262.79               | 140.32                    | 63.88                         | 54.93                        | 21.51                           |
    And 【占压年度超额度0101】品种金额应变动
      | productVarietyCrmCode | annualAvailableExecuteCreditLimit | annualExecuteCreditLimit | annualOccupiedAmount | exceedingQuotaCreditLimit | exceedingQuotaAvailableAmount | exceedingQuotaOccupiedAmount | exceedingQuotaIneffectiveAmount |
      | 1-4O-7                | 57.13                             | 113.91                   | 56.78                | 55.91                     | 51.76                         | 0.91                         | 3.24                            |
      | 1-4O-14               | 72.50                             | 110.32                   | 37.82                | 40.66                     | 27.70                         | 3.87                         | 9.09                            |
      | 1-4O-8                | -9.21                             | 101.92                   | 111.13               | 51.33                     | 28.51                         | 5.68                         | 17.14                           |
      | 1-4O-3                | 11.29                             | 127.23                   | 115.94               | 63.77                     | 51.90                         | 6.11                         | 5.76                            |
      | 1-4O-9                | -0.86                             | 55.22                    | 56.08                | 30.66                     | -15.71                        | 38.60                        | 7.77                            |
      | 1-292NJV              | -10.43                            | 80.34                    | 90.77                | 45.89                     | 27.69                         | 10.22                        | 7.98                            |
      | 1-4O-12               | 0.00                              | 121.99                   | 121.99               | 65.69                     | 25.01                         | 39.36                        | 1.32                            |
    Then 【占压年度超额度0101】临时信用申请应变动
      | productVarietyCrmCode | TYPE | CREDIT_LIMIT | OCCUPIED_AMOUNT | FROZEN_AMOUNT | TRADE_NO    |
      | 1-4O-7                | 1    | 20.59        | 0.58            | 1.11          | PJLZ2022002 |
      | 1-4O-14               | 1    | 20.11        | 1.56            | 3.22          | PJLZ2022002 |
      | 1-4O-8                | 1    | 30.78        | 2.55            | 9.16          | PJLZ2022002 |
      | 1-4O-3                | 1    | 28.11        | 1.56            | 3.87          | PJLZ2022002 |
      | 1-4O-9                | 1    | 0.00         | 22.68           | 0.00          | PJLZ2022002 |
      | 1-292NJV              | 1    | 0.00         | 0.00            | 0.00          | PJLZ2022002 |
      | 1-4O-12               | 1    | 65.69        | 39.36           | 1.32          | PJLZ2022002 |
      | 1-4O-7                | 1    | 35.32        | 0.33            | 2.13          | PJLZ2022003 |
      | 1-4O-14               | 1    | 20.55        | 2.31            | 5.87          | PJLZ2022003 |
      | 1-4O-8                | 1    | 20.55        | 3.13            | 7.98          | PJLZ2022003 |
      | 1-4O-3                | 1    | 35.66        | 4.55            | 1.89          | PJLZ2022003 |
      | 1-4O-9                | 1    | 30.66        | 15.92           | 7.77          | PJLZ2022003 |
      | 1-292NJV              | 1    | 45.89        | 10.22           | 7.98          | PJLZ2022003 |
      | 1-4O-12               | 1    | 0.00         | 0.00            | 0.00          | PJLZ2022003 |
    And 【占压年度超额度0101】流水应变动
      | CREDIT_LIMIT | OCCUPIED_AMOUNT | FROZEN_AMOUNT | OCCUPIED_CHANGE_AMOUNT | CREDIT_TRADE_NO |
      | 121.99       | 100.33          | 0.00          | 21.66                  | CPJ202212060005     |
      | 55.22        | 11.11           | 0.00          | 44.97                  | CPJ202212060005     |
      | 65.69        | 1.11            | 1.32          | 38.25                  | PJLZ2022002     |
      | 0.00         | 0.00            | 0.00          | 22.68                  | PJLZ2022002     |
      | 30.66        | 5.87            | 7.77          | 10.05                  | PJLZ2022003     |