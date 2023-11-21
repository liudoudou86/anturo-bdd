@release
Feature:信用使用 → 逆向释放 → 占压年度资信+超额度 → 管控维度为产品集合

  Scenario:【testcase02】有停用 → 订单部分退货
    Given 【占压年度超额度0201】更新客户信用政策
      | CREDIT_GRADE_ID     | OVERDUE_PERIOD_DAYS | CREDIT_CONTROL_DIMENSION |
      | 1326407914543600902 | 500                 | PRODUCT_SET              |
    When 【占压年度超额度0201】客户在B2B下赊销订单
      | SALE_ORG_CODE | CUSTOMER_ENT_CODE | PRODUCT_VARIETY_CRM_CODE | SKU      | AMOUNT | COUNT | ITEM_ID |
      | 1000002261    | 1000006570        | 1-4O-12                  | 10000558 | 19.97  | 3     | 10      |
      | 1000002261    | 1000006570        | 1-4O-3                   | 10000437 | 12.85  | 3     | 20      |
      | 1000002261    | 1000006570        | 1-4O-3                   | 10000434 | 13.05  | 3     | 30      |
    Then 【占压年度超额度0201】商务审核应通过
    And 【占压年度超额度0201】人工审核应通过
    Then 【占压年度超额度0201】产品集合金额应变动
      | productSetCode | annualAvailableExecuteCreditLimit | annualExecuteCreditLimit | annualOccupiedAmount | exceedingQuotaCreditLimit | exceedingQuotaAvailableAmount | exceedingQuotaOccupiedAmount | exceedingQuotaIneffectiveAmount |
      | YYSYZF         | 0.00                              | 121.99                   | 121.99               | 65.69                     | 25.01                         | 39.36                        | 1.32                            |
      | YYSYYP         | 120.42                            | 326.15                   | 205.73               | 147.90                    | 107.97                        | 10.46                        | 29.47                           |
      | YYSYFYP        | 0.00                              | 262.79                   | 262.79               | 140.32                    | 63.88                         | 54.93                        | 21.51                           |
    And 【占压年度超额度0201】品种金额应变动
      | productVarietyCrmCode | annualAvailableExecuteCreditLimit | annualExecuteCreditLimit | annualOccupiedAmount | exceedingQuotaCreditLimit | exceedingQuotaAvailableAmount | exceedingQuotaOccupiedAmount | exceedingQuotaIneffectiveAmount |
      | 1-4O-7                | 57.13                             | 113.91                   | 56.78                | 55.91                     | 51.76                         | 0.91                         | 3.24                            |
      | 1-4O-14               | 72.50                             | 110.32                   | 37.82                | 40.66                     | 27.70                         | 3.87                         | 9.09                            |
      | 1-4O-8                | -9.21                             | 101.92                   | 111.13               | 51.33                     | 28.51                         | 5.68                         | 17.14                           |
      | 1-4O-3                | 11.29                             | 127.23                   | 115.94               | 63.77                     | 51.90                         | 6.11                         | 5.76                            |
      | 1-4O-9                | -0.86                             | 55.22                    | 56.08                | 30.66                     | -15.71                        | 38.60                        | 7.77                            |
      | 1-292NJV              | -10.43                            | 80.34                    | 90.77                | 45.89                     | 27.69                         | 10.22                        | 7.98                            |
      | 1-4O-12               | 0.00                              | 121.99                   | 121.99               | 65.69                     | 25.01                         | 39.36                        | 1.32                            |
    Then 【占压年度超额度0201】临时信用申请应变动
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
    And 【占压年度超额度0201】流水应变动
      | CREDIT_LIMIT | OCCUPIED_AMOUNT | FROZEN_AMOUNT | OCCUPIED_CHANGE_AMOUNT | CREDIT_TRADE_NO |
      | 121.99       | 100.33          | 0.00          | 21.66                  | CPJ202212060005 |
      | 55.22        | 11.11           | 0.00          | 44.97                  | CPJ202212060005 |
      | 65.69        | 1.11            | 1.32          | 38.25                  | PJLZ2022002     |
      | 0.00         | 0.00            | 0.00          | 22.68                  | PJLZ2022002     |
      | 30.66        | 5.87            | 7.77          | 10.05                  | PJLZ2022003     |
    And 【占压年度超额度0201】冻结产品集合中某品种
      | customerEntCode | productVarietyCode             | productVarietyCrmCode | saleOrgCode |
      | 1000002261      | ZT-ITEM-PZ-20210401154500-1194 | 1-4O-9                | 1000006570  |
    When 【占压年度超额度0202】客户在B2B退货
      | SALE_ORG_CODE | CUSTOMER_ENT_CODE | PRODUCT_VARIETY_CRM_CODE | SKU      | AMOUNT | COUNT | ITEM_ID |
      | 1000002261    | 1000006570        | 1-4O-7                   | 10000437 | 25.70  | 2     | 20      |
      | 1000002261    | 1000006570        | 1-4O-7                   | 10000434 | 26.10  | 2     | 30      |
    Then 【占压年度超额度0202】产品集合金额应变动
      | productSetCode | annualAvailableExecuteCreditLimit | annualExecuteCreditLimit | annualOccupiedAmount | exceedingQuotaCreditLimit | exceedingQuotaAvailableAmount | exceedingQuotaOccupiedAmount | exceedingQuotaIneffectiveAmount |
      | YYSYZF         | 0.00                              | 121.99                   | 121.99               | 65.69                     | 25.01                         | 39.36                        | 1.32                            |
      | YYSYYP         | 120.42                            | 326.15                   | 205.73               | 147.90                    | 107.97                        | 10.46                        | 29.47                           |
      | YYSYFYP        | 0.86                              | 207.57                   | 206.71               | 109.66                    | 79.59                         | 16.33                        | 13.74                           |
    And 【占压年度超额度0202】品种金额应变动
      | productVarietyCrmCode | annualAvailableExecuteCreditLimit | annualExecuteCreditLimit | annualOccupiedAmount | exceedingQuotaCreditLimit | exceedingQuotaAvailableAmount | exceedingQuotaOccupiedAmount | exceedingQuotaIneffectiveAmount |
      | 1-4O-7                | 57.13                             | 113.91                   | 56.78                | 55.91                     | 51.76                         | 0.91                         | 3.24                            |
      | 1-4O-14               | 72.50                             | 110.32                   | 37.82                | 40.66                     | 27.70                         | 3.87                         | 9.09                            |
      | 1-4O-8                | -9.21                             | 101.92                   | 111.13               | 51.33                     | 28.51                         | 5.68                         | 17.14                           |
      | 1-4O-3                | 11.29                             | 127.23                   | 115.94               | 63.77                     | 51.90                         | 6.11                         | 5.76                            |
      | 1-4O-9                | 18.21                             | 55.22                    | 37.01                | 30.66                     | 17.02                         | 5.87                         | 7.77                            |
      | 1-292NJV              | -10.43                            | 80.34                    | 90.77                | 45.89                     | 27.69                         | 10.22                        | 7.98                            |
      | 1-4O-12               | 0.00                              | 121.99                   | 121.99               | 65.69                     | 25.01                         | 39.36                        | 1.32                            |
    Then 【占压年度超额度0202】临时信用申请应变动
      | productVarietyCrmCode | TYPE | CREDIT_LIMIT | OCCUPIED_AMOUNT | FROZEN_AMOUNT | TRADE_NO    |
      | 1-4O-7                | 1    | 20.59        | 0.58            | 1.11          | PJLZ2022002 |
      | 1-4O-14               | 1    | 20.11        | 1.56            | 3.22          | PJLZ2022002 |
      | 1-4O-8                | 1    | 30.78        | 2.55            | 9.16          | PJLZ2022002 |
      | 1-4O-3                | 1    | 28.11        | 1.56            | 3.87          | PJLZ2022002 |
      | 1-4O-9                | 1    | 0.00         | 0.00            | 0.00          | PJLZ2022002 |
      | 1-292NJV              | 1    | 0.00         | 0.00            | 0.00          | PJLZ2022002 |
      | 1-4O-12               | 1    | 65.69        | 39.36           | 1.32          | PJLZ2022002 |
      | 1-4O-7                | 1    | 35.32        | 0.33            | 2.13          | PJLZ2022003 |
      | 1-4O-14               | 1    | 20.55        | 2.31            | 5.87          | PJLZ2022003 |
      | 1-4O-8                | 1    | 20.55        | 3.13            | 7.98          | PJLZ2022003 |
      | 1-4O-3                | 1    | 35.66        | 4.55            | 1.89          | PJLZ2022003 |
      | 1-4O-9                | 1    | 30.66        | 5.87            | 7.77          | PJLZ2022003 |
      | 1-292NJV              | 1    | 45.89        | 10.22           | 7.98          | PJLZ2022003 |
      | 1-4O-12               | 1    | 0.00         | 0.00            | 0.00          | PJLZ2022003 |
    And 【占压年度超额度0202】流水应变动
      | CREDIT_LIMIT | OCCUPIED_AMOUNT | FROZEN_AMOUNT | OCCUPIED_CHANGE_AMOUNT | CREDIT_TRADE_NO |
      | 121.99       | 100.33          | 0.00          | 21.66                  | CPJ202212060005 |
      | 55.22        | 11.11           | 0.00          | 44.97                  | CPJ202212060005 |
      | 65.69        | 1.11            | 1.32          | 38.25                  | PJLZ2022002     |
      | 0.00         | 0.00            | 0.00          | 22.68                  | PJLZ2022002     |
      | 30.66        | 5.87            | 7.77          | 10.05                  | PJLZ2022003     |
      | 0.00         | 22.68           | 0.00          | 22.68                  | PJLZ2022002     |
      | 30.66        | 15.92           | 7.77          | 10.05                  | PJLZ2022003     |
      | 55.22        | 56.08           | 0.00          | 19.07                  | CPJ202212060005 |

  Scenario:【testcase03】有停用 → 订单部分退货 → 订单部分退货冲销
    Given 【占压年度超额度0301】更新客户信用政策
      | CREDIT_GRADE_ID     | OVERDUE_PERIOD_DAYS | CREDIT_CONTROL_DIMENSION |
      | 1326407914543600902 | 500                 | PRODUCT_SET              |
    When 【占压年度超额度0301】客户在B2B下赊销订单
      | SALE_ORG_CODE | CUSTOMER_ENT_CODE | PRODUCT_VARIETY_CRM_CODE | SKU      | AMOUNT | COUNT | ITEM_ID |
      | 1000002261    | 1000006570        | 1-4O-12                  | 10000558 | 19.97  | 3     | 10      |
      | 1000002261    | 1000006570        | 1-4O-3                   | 10000437 | 12.85  | 3     | 20      |
      | 1000002261    | 1000006570        | 1-4O-3                   | 10000434 | 13.05  | 3     | 30      |
    Then 【占压年度超额度0301】商务审核应通过
    And 【占压年度超额度0301】人工审核应通过
    Then 【占压年度超额度0301】产品集合金额应变动
      | productSetCode | annualAvailableExecuteCreditLimit | annualExecuteCreditLimit | annualOccupiedAmount | exceedingQuotaCreditLimit | exceedingQuotaAvailableAmount | exceedingQuotaOccupiedAmount | exceedingQuotaIneffectiveAmount |
      | YYSYZF         | 0.00                              | 121.99                   | 121.99               | 65.69                     | 25.01                         | 39.36                        | 1.32                            |
      | YYSYYP         | 120.42                            | 326.15                   | 205.73               | 147.90                    | 107.97                        | 10.46                        | 29.47                           |
      | YYSYFYP        | 0.00                              | 262.79                   | 262.79               | 140.32                    | 63.88                         | 54.93                        | 21.51                           |
    And 【占压年度超额度0301】品种金额应变动
      | productVarietyCrmCode | annualAvailableExecuteCreditLimit | annualExecuteCreditLimit | annualOccupiedAmount | exceedingQuotaCreditLimit | exceedingQuotaAvailableAmount | exceedingQuotaOccupiedAmount | exceedingQuotaIneffectiveAmount |
      | 1-4O-7                | 57.13                             | 113.91                   | 56.78                | 55.91                     | 51.76                         | 0.91                         | 3.24                            |
      | 1-4O-14               | 72.50                             | 110.32                   | 37.82                | 40.66                     | 27.70                         | 3.87                         | 9.09                            |
      | 1-4O-8                | -9.21                             | 101.92                   | 111.13               | 51.33                     | 28.51                         | 5.68                         | 17.14                           |
      | 1-4O-3                | 11.29                             | 127.23                   | 115.94               | 63.77                     | 51.90                         | 6.11                         | 5.76                            |
      | 1-4O-9                | -0.86                             | 55.22                    | 56.08                | 30.66                     | -15.71                        | 38.60                        | 7.77                            |
      | 1-292NJV              | -10.43                            | 80.34                    | 90.77                | 45.89                     | 27.69                         | 10.22                        | 7.98                            |
      | 1-4O-12               | 0.00                              | 121.99                   | 121.99               | 65.69                     | 25.01                         | 39.36                        | 1.32                            |
    Then 【占压年度超额度0301】临时信用申请应变动
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
    And 【占压年度超额度0301】流水应变动
      | CREDIT_LIMIT | OCCUPIED_AMOUNT | FROZEN_AMOUNT | OCCUPIED_CHANGE_AMOUNT | CREDIT_TRADE_NO |
      | 121.99       | 100.33          | 0.00          | 21.66                  | CPJ202212060005 |
      | 55.22        | 11.11           | 0.00          | 44.97                  | CPJ202212060005 |
      | 65.69        | 1.11            | 1.32          | 38.25                  | PJLZ2022002     |
      | 0.00         | 0.00            | 0.00          | 22.68                  | PJLZ2022002     |
      | 30.66        | 5.87            | 7.77          | 10.05                  | PJLZ2022003     |
    When 【占压年度超额度0302】客户在B2B退货
      | SALE_ORG_CODE | CUSTOMER_ENT_CODE | PRODUCT_VARIETY_CRM_CODE | SKU      | AMOUNT | COUNT | ITEM_ID |
      | 1000002261    | 1000006570        | 1-4O-7                   | 10000437 | 25.70  | 2     | 20      |
      | 1000002261    | 1000006570        | 1-4O-7                   | 10000434 | 26.10  | 2     | 30      |
    Then 【占压年度超额度0302】产品集合金额应变动
      | productSetCode | annualAvailableExecuteCreditLimit | annualExecuteCreditLimit | annualOccupiedAmount | exceedingQuotaCreditLimit | exceedingQuotaAvailableAmount | exceedingQuotaOccupiedAmount | exceedingQuotaIneffectiveAmount |
      | YYSYZF         | 0.00                              | 121.99                   | 121.99               | 65.69                     | 25.01                         | 39.36                        | 1.32                            |
      | YYSYYP         | 120.42                            | 326.15                   | 205.73               | 147.90                    | 107.97                        | 10.46                        | 29.47                           |
      | YYSYFYP        | 19.07                             | 262.79                   | 243.72               | 140.32                    | 96.61                         | 22.20                        | 21.51                           |
    And 【占压年度超额度0302】品种金额应变动
      | productVarietyCrmCode | annualAvailableExecuteCreditLimit | annualExecuteCreditLimit | annualOccupiedAmount | exceedingQuotaCreditLimit | exceedingQuotaAvailableAmount | exceedingQuotaOccupiedAmount | exceedingQuotaIneffectiveAmount |
      | 1-4O-7                | 57.13                             | 113.91                   | 56.78                | 55.91                     | 51.76                         | 0.91                         | 3.24                            |
      | 1-4O-14               | 72.50                             | 110.32                   | 37.82                | 40.66                     | 27.70                         | 3.87                         | 9.09                            |
      | 1-4O-8                | -9.21                             | 101.92                   | 111.13               | 51.33                     | 28.51                         | 5.68                         | 17.14                           |
      | 1-4O-3                | 11.29                             | 127.23                   | 115.94               | 63.77                     | 51.90                         | 6.11                         | 5.76                            |
      | 1-4O-9                | 18.21                             | 55.22                    | 37.01                | 30.66                     | 17.02                         | 5.87                         | 7.77                            |
      | 1-292NJV              | -10.43                            | 80.34                    | 90.77                | 45.89                     | 27.69                         | 10.22                        | 7.98                            |
      | 1-4O-12               | 0.00                              | 121.99                   | 121.99               | 65.69                     | 25.01                         | 39.36                        | 1.32                            |
    Then 【占压年度超额度0302】临时信用申请应变动
      | productVarietyCrmCode | TYPE | CREDIT_LIMIT | OCCUPIED_AMOUNT | FROZEN_AMOUNT | TRADE_NO    |
      | 1-4O-7                | 1    | 20.59        | 0.58            | 1.11          | PJLZ2022002 |
      | 1-4O-14               | 1    | 20.11        | 1.56            | 3.22          | PJLZ2022002 |
      | 1-4O-8                | 1    | 30.78        | 2.55            | 9.16          | PJLZ2022002 |
      | 1-4O-3                | 1    | 28.11        | 1.56            | 3.87          | PJLZ2022002 |
      | 1-4O-9                | 1    | 0.00         | 0.00            | 0.00          | PJLZ2022002 |
      | 1-292NJV              | 1    | 0.00         | 0.00            | 0.00          | PJLZ2022002 |
      | 1-4O-12               | 1    | 65.69        | 39.36           | 1.32          | PJLZ2022002 |
      | 1-4O-7                | 1    | 35.32        | 0.33            | 2.13          | PJLZ2022003 |
      | 1-4O-14               | 1    | 20.55        | 2.31            | 5.87          | PJLZ2022003 |
      | 1-4O-8                | 1    | 20.55        | 3.13            | 7.98          | PJLZ2022003 |
      | 1-4O-3                | 1    | 35.66        | 4.55            | 1.89          | PJLZ2022003 |
      | 1-4O-9                | 1    | 30.66        | 5.87            | 7.77          | PJLZ2022003 |
      | 1-292NJV              | 1    | 45.89        | 10.22           | 7.98          | PJLZ2022003 |
      | 1-4O-12               | 1    | 0.00         | 0.00            | 0.00          | PJLZ2022003 |
    And 【占压年度超额度0302】流水应变动
      | CREDIT_LIMIT | OCCUPIED_AMOUNT | FROZEN_AMOUNT | OCCUPIED_CHANGE_AMOUNT | CREDIT_TRADE_NO |
      | 121.99       | 100.33          | 0.00          | 21.66                  | CPJ202212060005 |
      | 55.22        | 11.11           | 0.00          | 44.97                  | CPJ202212060005 |
      | 65.69        | 1.11            | 1.32          | 38.25                  | PJLZ2022002     |
      | 0.00         | 0.00            | 0.00          | 22.68                  | PJLZ2022002     |
      | 30.66        | 5.87            | 7.77          | 10.05                  | PJLZ2022003     |
      | 0.00         | 22.68           | 0.00          | 22.68                  | PJLZ2022002     |
      | 30.66        | 15.92           | 7.77          | 10.05                  | PJLZ2022003     |
      | 55.22        | 56.08           | 0.00          | 19.07                  | CPJ202212060005 |
    And 【占压年度超额度0302】冻结产品集合中某品种
      | customerEntCode | productVarietyCode             | productVarietyCrmCode | saleOrgCode |
      | 1000002261      | ZT-ITEM-PZ-20210401154500-1194 | 1-4O-9                | 1000006570  |
    When 【占压年度超额度0303】客户在B2B退货冲销
      | SALE_ORG_CODE | CUSTOMER_ENT_CODE | PRODUCT_VARIETY_CRM_CODE | SKU      | AMOUNT | COUNT | ITEM_ID |
      | 1000002261    | 1000006570        | 1-4O-7                   | 10000437 | 12.85  | 1     | 20      |
      | 1000002261    | 1000006570        | 1-4O-7                   | 10000434 | 13.05  | 1     | 30      |
    Then 【占压年度超额度0303】产品集合金额应变动
      | productSetCode | annualAvailableExecuteCreditLimit | annualExecuteCreditLimit | annualOccupiedAmount | exceedingQuotaCreditLimit | exceedingQuotaAvailableAmount | exceedingQuotaOccupiedAmount | exceedingQuotaIneffectiveAmount |
      | YYSYZF         | 0.00                              | 121.99                   | 121.99               | 65.69                     | 25.01                         | 39.36                        | 1.32                            |
      | YYSYYP         | 120.42                            | 326.15                   | 205.73               | 147.90                    | 107.97                        | 10.46                        | 29.47                           |
      | YYSYFYP        | 0.86                              | 207.57                   | 206.71               | 109.66                    | 79.59                         | 16.33                        | 13.74                           |
    And 【占压年度超额度0303】品种金额应变动
      | productVarietyCrmCode | annualAvailableExecuteCreditLimit | annualExecuteCreditLimit | annualOccupiedAmount | exceedingQuotaCreditLimit | exceedingQuotaAvailableAmount | exceedingQuotaOccupiedAmount | exceedingQuotaIneffectiveAmount |
      | 1-4O-7                | 57.13                             | 113.91                   | 56.78                | 55.91                     | 51.76                         | 0.91                         | 3.24                            |
      | 1-4O-14               | 72.50                             | 110.32                   | 37.82                | 40.66                     | 27.70                         | 3.87                         | 9.09                            |
      | 1-4O-8                | -9.21                             | 101.92                   | 111.13               | 51.33                     | 28.51                         | 5.68                         | 17.14                           |
      | 1-4O-3                | 11.29                             | 127.23                   | 115.94               | 63.77                     | 51.90                         | 6.11                         | 5.76                            |
      | 1-4O-9                | -0.86                             | 55.22                    | 56.08                | 30.66                     | 10.19                         | 12.70                        | 7.77                            |
      | 1-292NJV              | -10.43                            | 80.34                    | 90.77                | 45.89                     | 27.69                         | 10.22                        | 7.98                            |
      | 1-4O-12               | 0.00                              | 121.99                   | 121.99               | 65.69                     | 25.01                         | 39.36                        | 1.32                            |
    Then 【占压年度超额度0303】临时信用申请应变动
      | productVarietyCrmCode | TYPE | CREDIT_LIMIT | OCCUPIED_AMOUNT | FROZEN_AMOUNT | TRADE_NO    |
      | 1-4O-7                | 1    | 20.59        | 0.58            | 1.11          | PJLZ2022002 |
      | 1-4O-14               | 1    | 20.11        | 1.56            | 3.22          | PJLZ2022002 |
      | 1-4O-8                | 1    | 30.78        | 2.55            | 9.16          | PJLZ2022002 |
      | 1-4O-3                | 1    | 28.11        | 1.56            | 3.87          | PJLZ2022002 |
      | 1-4O-9                | 1    | 0.00         | 4.73            | 0.00          | PJLZ2022002 |
      | 1-292NJV              | 1    | 0.00         | 0.00            | 0.00          | PJLZ2022002 |
      | 1-4O-12               | 1    | 65.69        | 39.36           | 1.32          | PJLZ2022002 |
      | 1-4O-7                | 1    | 35.32        | 0.33            | 2.13          | PJLZ2022003 |
      | 1-4O-14               | 1    | 20.55        | 2.31            | 5.87          | PJLZ2022003 |
      | 1-4O-8                | 1    | 20.55        | 3.13            | 7.98          | PJLZ2022003 |
      | 1-4O-3                | 1    | 35.66        | 4.55            | 1.89          | PJLZ2022003 |
      | 1-4O-9                | 1    | 30.66        | 7.97            | 7.77          | PJLZ2022003 |
      | 1-292NJV              | 1    | 45.89        | 10.22           | 7.98          | PJLZ2022003 |
      | 1-4O-12               | 1    | 0.00         | 0.00            | 0.00          | PJLZ2022003 |
    And 【占压年度超额度0303】流水应变动
      | CREDIT_LIMIT | OCCUPIED_AMOUNT | FROZEN_AMOUNT | OCCUPIED_CHANGE_AMOUNT | CREDIT_TRADE_NO |
      | 121.99       | 100.33          | 0.00          | 21.66                  | CPJ202212060005 |
      | 55.22        | 11.11           | 0.00          | 44.97                  | CPJ202212060005 |
      | 65.69        | 1.11            | 1.32          | 38.25                  | PJLZ2022002     |
      | 0.00         | 0.00            | 0.00          | 22.68                  | PJLZ2022002     |
      | 30.66        | 5.87            | 7.77          | 10.05                  | PJLZ2022003     |
      | 0.00         | 22.68           | 0.00          | 22.68                  | PJLZ2022002     |
      | 30.66        | 15.92           | 7.77          | 10.05                  | PJLZ2022003     |
      | 55.22        | 56.08           | 0.00          | 19.07                  | CPJ202212060005 |
      | 55.22        | 37.01           | 0.00          | 19.07                  | CPJ202212060005 |
      | 0.00         | 0.00            | 0.00          | 4.73                   | PJLZ2022002     |
      | 30.66        | 5.87            | 7.77          | 2.10                   | PJLZ2022003     |

  Scenario:【testcase04】无停用 → 订单部分退货 → 订单部分退货冲销 → 月结转
    Given 【占压年度超额度0401】更新客户信用政策
      | CREDIT_GRADE_ID     | OVERDUE_PERIOD_DAYS | CREDIT_CONTROL_DIMENSION |
      | 1326407914543600902 | 500                 | PRODUCT_SET              |
    When 【占压年度超额度0401】客户在B2B下赊销订单
      | SALE_ORG_CODE | CUSTOMER_ENT_CODE | PRODUCT_VARIETY_CRM_CODE | SKU      | AMOUNT | COUNT | ITEM_ID |
      | 1000002261    | 1000006570        | 1-4O-12                  | 10000558 | 19.97  | 3     | 10      |
      | 1000002261    | 1000006570        | 1-4O-3                   | 10000437 | 12.85  | 3     | 20      |
      | 1000002261    | 1000006570        | 1-4O-3                   | 10000434 | 13.05  | 3     | 30      |
    Then 【占压年度超额度0401】商务审核应通过
    And 【占压年度超额度0401】人工审核应通过
    Then 【占压年度超额度0401】产品集合金额应变动
      | productSetCode | annualAvailableExecuteCreditLimit | annualExecuteCreditLimit | annualOccupiedAmount | exceedingQuotaCreditLimit | exceedingQuotaAvailableAmount | exceedingQuotaOccupiedAmount | exceedingQuotaIneffectiveAmount |
      | YYSYZF         | 0.00                              | 121.99                   | 121.99               | 65.69                     | 25.01                         | 39.36                        | 1.32                            |
      | YYSYYP         | 120.42                            | 326.15                   | 205.73               | 147.90                    | 107.97                        | 10.46                        | 29.47                           |
      | YYSYFYP        | 0.00                              | 262.79                   | 262.79               | 140.32                    | 63.88                         | 54.93                        | 21.51                           |
    And 【占压年度超额度0401】品种金额应变动
      | productVarietyCrmCode | annualAvailableExecuteCreditLimit | annualExecuteCreditLimit | annualOccupiedAmount | exceedingQuotaCreditLimit | exceedingQuotaAvailableAmount | exceedingQuotaOccupiedAmount | exceedingQuotaIneffectiveAmount |
      | 1-4O-7                | 57.13                             | 113.91                   | 56.78                | 55.91                     | 51.76                         | 0.91                         | 3.24                            |
      | 1-4O-14               | 72.50                             | 110.32                   | 37.82                | 40.66                     | 27.70                         | 3.87                         | 9.09                            |
      | 1-4O-8                | -9.21                             | 101.92                   | 111.13               | 51.33                     | 28.51                         | 5.68                         | 17.14                           |
      | 1-4O-3                | 11.29                             | 127.23                   | 115.94               | 63.77                     | 51.90                         | 6.11                         | 5.76                            |
      | 1-4O-9                | -0.86                             | 55.22                    | 56.08                | 30.66                     | -15.71                        | 38.60                        | 7.77                            |
      | 1-292NJV              | -10.43                            | 80.34                    | 90.77                | 45.89                     | 27.69                         | 10.22                        | 7.98                            |
      | 1-4O-12               | 0.00                              | 121.99                   | 121.99               | 65.69                     | 25.01                         | 39.36                        | 1.32                            |
    Then 【占压年度超额度0401】临时信用申请应变动
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
    And 【占压年度超额度0401】流水应变动
      | CREDIT_LIMIT | OCCUPIED_AMOUNT | FROZEN_AMOUNT | OCCUPIED_CHANGE_AMOUNT | CREDIT_TRADE_NO |
      | 121.99       | 100.33          | 0.00          | 21.66                  | CPJ202212060005 |
      | 55.22        | 11.11           | 0.00          | 44.97                  | CPJ202212060005 |
      | 65.69        | 1.11            | 1.32          | 38.25                  | PJLZ2022002     |
      | 0.00         | 0.00            | 0.00          | 22.68                  | PJLZ2022002     |
      | 30.66        | 5.87            | 7.77          | 10.05                  | PJLZ2022003     |
    When 【占压年度超额度0402】客户在B2B退货
      | SALE_ORG_CODE | CUSTOMER_ENT_CODE | PRODUCT_VARIETY_CRM_CODE | SKU      | AMOUNT | COUNT | ITEM_ID |
      | 1000002261    | 1000006570        | 1-4O-7                   | 10000437 | 25.70  | 2     | 20      |
      | 1000002261    | 1000006570        | 1-4O-7                   | 10000434 | 26.10  | 2     | 30      |
    Then 【占压年度超额度0402】产品集合金额应变动
      | productSetCode | annualAvailableExecuteCreditLimit | annualExecuteCreditLimit | annualOccupiedAmount | exceedingQuotaCreditLimit | exceedingQuotaAvailableAmount | exceedingQuotaOccupiedAmount | exceedingQuotaIneffectiveAmount |
      | YYSYZF         | 0.00                              | 121.99                   | 121.99               | 65.69                     | 25.01                         | 39.36                        | 1.32                            |
      | YYSYYP         | 120.42                            | 326.15                   | 205.73               | 147.90                    | 107.97                        | 10.46                        | 29.47                           |
      | YYSYFYP        | 19.07                             | 262.79                   | 243.72               | 140.32                    | 96.61                         | 22.20                        | 21.51                           |
    And 【占压年度超额度0402】品种金额应变动
      | productVarietyCrmCode | annualAvailableExecuteCreditLimit | annualExecuteCreditLimit | annualOccupiedAmount | exceedingQuotaCreditLimit | exceedingQuotaAvailableAmount | exceedingQuotaOccupiedAmount | exceedingQuotaIneffectiveAmount |
      | 1-4O-7                | 57.13                             | 113.91                   | 56.78                | 55.91                     | 51.76                         | 0.91                         | 3.24                            |
      | 1-4O-14               | 72.50                             | 110.32                   | 37.82                | 40.66                     | 27.70                         | 3.87                         | 9.09                            |
      | 1-4O-8                | -9.21                             | 101.92                   | 111.13               | 51.33                     | 28.51                         | 5.68                         | 17.14                           |
      | 1-4O-3                | 11.29                             | 127.23                   | 115.94               | 63.77                     | 51.90                         | 6.11                         | 5.76                            |
      | 1-4O-9                | 18.21                             | 55.22                    | 37.01                | 30.66                     | 17.02                         | 5.87                         | 7.77                            |
      | 1-292NJV              | -10.43                            | 80.34                    | 90.77                | 45.89                     | 27.69                         | 10.22                        | 7.98                            |
      | 1-4O-12               | 0.00                              | 121.99                   | 121.99               | 65.69                     | 25.01                         | 39.36                        | 1.32                            |
    Then 【占压年度超额度0402】临时信用申请应变动
      | productVarietyCrmCode | TYPE | CREDIT_LIMIT | OCCUPIED_AMOUNT | FROZEN_AMOUNT | TRADE_NO    |
      | 1-4O-7                | 1    | 20.59        | 0.58            | 1.11          | PJLZ2022002 |
      | 1-4O-14               | 1    | 20.11        | 1.56            | 3.22          | PJLZ2022002 |
      | 1-4O-8                | 1    | 30.78        | 2.55            | 9.16          | PJLZ2022002 |
      | 1-4O-3                | 1    | 28.11        | 1.56            | 3.87          | PJLZ2022002 |
      | 1-4O-9                | 1    | 0.00         | 0.00            | 0.00          | PJLZ2022002 |
      | 1-292NJV              | 1    | 0.00         | 0.00            | 0.00          | PJLZ2022002 |
      | 1-4O-12               | 1    | 65.69        | 39.36           | 1.32          | PJLZ2022002 |
      | 1-4O-7                | 1    | 35.32        | 0.33            | 2.13          | PJLZ2022003 |
      | 1-4O-14               | 1    | 20.55        | 2.31            | 5.87          | PJLZ2022003 |
      | 1-4O-8                | 1    | 20.55        | 3.13            | 7.98          | PJLZ2022003 |
      | 1-4O-3                | 1    | 35.66        | 4.55            | 1.89          | PJLZ2022003 |
      | 1-4O-9                | 1    | 30.66        | 5.87            | 7.77          | PJLZ2022003 |
      | 1-292NJV              | 1    | 45.89        | 10.22           | 7.98          | PJLZ2022003 |
      | 1-4O-12               | 1    | 0.00         | 0.00            | 0.00          | PJLZ2022003 |
    And 【占压年度超额度0402】流水应变动
      | CREDIT_LIMIT | OCCUPIED_AMOUNT | FROZEN_AMOUNT | OCCUPIED_CHANGE_AMOUNT | CREDIT_TRADE_NO |
      | 121.99       | 100.33          | 0.00          | 21.66                  | CPJ202212060005 |
      | 55.22        | 11.11           | 0.00          | 44.97                  | CPJ202212060005 |
      | 65.69        | 1.11            | 1.32          | 38.25                  | PJLZ2022002     |
      | 0.00         | 0.00            | 0.00          | 22.68                  | PJLZ2022002     |
      | 30.66        | 5.87            | 7.77          | 10.05                  | PJLZ2022003     |
      | 0.00         | 22.68           | 0.00          | 22.68                  | PJLZ2022002     |
      | 30.66        | 15.92           | 7.77          | 10.05                  | PJLZ2022003     |
      | 55.22        | 56.08           | 0.00          | 19.07                  | CPJ202212060005 |
    When 【占压年度超额度0403】客户在B2B退货冲销
      | SALE_ORG_CODE | CUSTOMER_ENT_CODE | PRODUCT_VARIETY_CRM_CODE | SKU      | AMOUNT | COUNT | ITEM_ID |
      | 1000002261    | 1000006570        | 1-4O-7                   | 10000437 | 12.85  | 1     | 20      |
      | 1000002261    | 1000006570        | 1-4O-7                   | 10000434 | 13.05  | 1     | 30      |
    Then 【占压年度超额度0403】产品集合金额应变动
      | productSetCode | annualAvailableExecuteCreditLimit | annualExecuteCreditLimit | annualOccupiedAmount | exceedingQuotaCreditLimit | exceedingQuotaAvailableAmount | exceedingQuotaOccupiedAmount | exceedingQuotaIneffectiveAmount |
      | YYSYZF         | 0.00                              | 121.99                   | 121.99               | 65.69                     | 25.01                         | 39.36                        | 1.32                            |
      | YYSYYP         | 120.42                            | 326.15                   | 205.73               | 147.90                    | 107.97                        | 10.46                        | 29.47                           |
      | YYSYFYP        | 0.00                              | 262.79                   | 262.79               | 140.32                    | 89.78                         | 29.03                        | 21.51                           |
    And 【占压年度超额度0403】品种金额应变动
      | productVarietyCrmCode | annualAvailableExecuteCreditLimit | annualExecuteCreditLimit | annualOccupiedAmount | exceedingQuotaCreditLimit | exceedingQuotaAvailableAmount | exceedingQuotaOccupiedAmount | exceedingQuotaIneffectiveAmount |
      | 1-4O-7                | 57.13                             | 113.91                   | 56.78                | 55.91                     | 51.76                         | 0.91                         | 3.24                            |
      | 1-4O-14               | 72.50                             | 110.32                   | 37.82                | 40.66                     | 27.70                         | 3.87                         | 9.09                            |
      | 1-4O-8                | -9.21                             | 101.92                   | 111.13               | 51.33                     | 28.51                         | 5.68                         | 17.14                           |
      | 1-4O-3                | 11.29                             | 127.23                   | 115.94               | 63.77                     | 51.90                         | 6.11                         | 5.76                            |
      | 1-4O-9                | -0.86                             | 55.22                    | 56.08                | 30.66                     | 10.19                         | 12.70                        | 7.77                            |
      | 1-292NJV              | -10.43                            | 80.34                    | 90.77                | 45.89                     | 27.69                         | 10.22                        | 7.98                            |
      | 1-4O-12               | 0.00                              | 121.99                   | 121.99               | 65.69                     | 25.01                         | 39.36                        | 1.32                            |
    Then 【占压年度超额度0403】临时信用申请应变动
      | productVarietyCrmCode | TYPE | CREDIT_LIMIT | OCCUPIED_AMOUNT | FROZEN_AMOUNT | TRADE_NO    |
      | 1-4O-7                | 1    | 20.59        | 0.58            | 1.11          | PJLZ2022002 |
      | 1-4O-14               | 1    | 20.11        | 1.56            | 3.22          | PJLZ2022002 |
      | 1-4O-8                | 1    | 30.78        | 2.55            | 9.16          | PJLZ2022002 |
      | 1-4O-3                | 1    | 28.11        | 1.56            | 3.87          | PJLZ2022002 |
      | 1-4O-9                | 1    | 0.00         | 4.73            | 0.00          | PJLZ2022002 |
      | 1-292NJV              | 1    | 0.00         | 0.00            | 0.00          | PJLZ2022002 |
      | 1-4O-12               | 1    | 65.69        | 39.36           | 1.32          | PJLZ2022002 |
      | 1-4O-7                | 1    | 35.32        | 0.33            | 2.13          | PJLZ2022003 |
      | 1-4O-14               | 1    | 20.55        | 2.31            | 5.87          | PJLZ2022003 |
      | 1-4O-8                | 1    | 20.55        | 3.13            | 7.98          | PJLZ2022003 |
      | 1-4O-3                | 1    | 35.66        | 4.55            | 1.89          | PJLZ2022003 |
      | 1-4O-9                | 1    | 30.66        | 7.97            | 7.77          | PJLZ2022003 |
      | 1-292NJV              | 1    | 45.89        | 10.22           | 7.98          | PJLZ2022003 |
      | 1-4O-12               | 1    | 0.00         | 0.00            | 0.00          | PJLZ2022003 |
    And 【占压年度超额度0403】流水应变动
      | CREDIT_LIMIT | OCCUPIED_AMOUNT | FROZEN_AMOUNT | OCCUPIED_CHANGE_AMOUNT | CREDIT_TRADE_NO |
      | 121.99       | 100.33          | 0.00          | 21.66                  | CPJ202212060005 |
      | 55.22        | 11.11           | 0.00          | 44.97                  | CPJ202212060005 |
      | 65.69        | 1.11            | 1.32          | 38.25                  | PJLZ2022002     |
      | 0.00         | 0.00            | 0.00          | 22.68                  | PJLZ2022002     |
      | 30.66        | 5.87            | 7.77          | 10.05                  | PJLZ2022003     |
      | 0.00         | 22.68           | 0.00          | 22.68                  | PJLZ2022002     |
      | 30.66        | 15.92           | 7.77          | 10.05                  | PJLZ2022003     |
      | 55.22        | 56.08           | 0.00          | 19.07                  | CPJ202212060005 |
      | 55.22        | 37.01           | 0.00          | 19.07                  | CPJ202212060005 |
      | 0.00         | 0.00            | 0.00          | 4.73                   | PJLZ2022002     |
      | 30.66        | 5.87            | 7.77          | 2.10                   | PJLZ2022003     |
    When 【占压年度超额度0404】月度结转
    Then 【占压年度超额度0404】产品集合金额应变动
      | productSetCode | annualAvailableExecuteCreditLimit | annualExecuteCreditLimit | annualOccupiedAmount | exceedingQuotaCreditLimit | exceedingQuotaAvailableAmount | exceedingQuotaOccupiedAmount | exceedingQuotaIneffectiveAmount |
      | YYSYZF         | -39.36                            | 121.99                   | 161.35               | 0.00                      | 0.00                          | 0.00                         | 0.00                            |
      | YYSYYP         | 109.96                            | 326.15                   | 216.19               | 0.00                      | 0.00                          | 0.00                         | 0.00                            |
      | YYSYFYP        | -29.03                            | 262.79                   | 291.82               | 0.00                      | 0.00                          | 0.00                         | 0.00                            |
    And 【占压年度超额度0404】品种金额应变动
      | productVarietyCrmCode | annualAvailableExecuteCreditLimit | annualExecuteCreditLimit | annualOccupiedAmount | exceedingQuotaCreditLimit | exceedingQuotaAvailableAmount | exceedingQuotaOccupiedAmount | exceedingQuotaIneffectiveAmount |
      | 1-4O-7                | 56.22                             | 113.91                   | 57.69                | 0.00                      | 0.00                          | 0.00                         | 0.00                            |
      | 1-4O-14               | 68.63                             | 110.32                   | 41.69                | 0.00                      | 0.00                          | 0.00                         | 0.00                            |
      | 1-4O-8                | -14.89                            | 101.92                   | 116.81               | 0.00                      | 0.00                          | 0.00                         | 0.00                            |
      | 1-4O-3                | 5.18                              | 127.23                   | 122.05               | 0.00                      | 0.00                          | 0.00                         | 0.00                            |
      | 1-4O-9                | -13.56                            | 55.22                    | 68.78                | 0.00                      | 0.00                          | 0.00                         | 0.00                            |
      | 1-292NJV              | -20.65                            | 80.34                    | 100.99               | 0.00                      | 0.00                          | 0.00                         | 0.00                            |
      | 1-4O-12               | -39.36                            | 121.99                   | 161.35               | 0.00                      | 0.00                          | 0.00                         | 0.00                            |
    And 【占压年度超额度0404】流水应变动
      | CREDIT_LIMIT | OCCUPIED_AMOUNT | FROZEN_AMOUNT | OCCUPIED_CHANGE_AMOUNT | CREDIT_TRADE_NO |
      | 121.99       | 100.33          | 0.00          | 21.66                  | CPJ202212060005 |
      | 55.22        | 11.11           | 0.00          | 44.97                  | CPJ202212060005 |
      | 65.69        | 1.11            | 1.32          | 38.25                  | PJLZ2022002     |
      | 0.00         | 0.00            | 0.00          | 22.68                  | PJLZ2022002     |
      | 30.66        | 5.87            | 7.77          | 10.05                  | PJLZ2022003     |
      | 0.00         | 22.68           | 0.00          | 22.68                  | PJLZ2022002     |
      | 30.66        | 15.92           | 7.77          | 10.05                  | PJLZ2022003     |
      | 55.22        | 56.08           | 0.00          | 19.07                  | CPJ202212060005 |
      | 55.22        | 37.01           | 0.00          | 19.07                  | CPJ202212060005 |
      | 0.00         | 0.00            | 0.00          | 4.73                   | PJLZ2022002     |
      | 30.66        | 5.87            | 7.77          | 2.10                   | PJLZ2022003     |
      | 20.59        | 0.58            | 1.11          | 0.58                   | PJLZ2022002     |
      | 20.11        | 1.56            | 3.22          | 1.56                   | PJLZ2022002     |
      | 30.78        | 2.55            | 9.16          | 2.55                   | PJLZ2022002     |
      | 28.11        | 1.56            | 3.87          | 1.56                   | PJLZ2022002     |
      | 0            | 4.73            | 0             | 4.73                   | PJLZ2022002     |
      | 65.69        | 39.36           | 1.32          | 39.36                  | PJLZ2022002     |
      | 35.32        | 0.33            | 2.13          | 0.33                   | PJLZ2022003     |
      | 20.55        | 2.31            | 5.87          | 2.31                   | PJLZ2022003     |
      | 20.55        | 3.13            | 7.98          | 3.13                   | PJLZ2022003     |
      | 35.66        | 4.55            | 1.89          | 4.55                   | PJLZ2022003     |
      | 30.66        | 7.97            | 7.77          | 7.97                   | PJLZ2022003     |
      | 45.89        | 10.22           | 7.98          | 10.22                  | PJLZ2022003     |
      | 113.91       | 56.78           | 0             | 0.91                   | CPJ202212060005 |
      | 110.32       | 37.82           | 0             | 3.87                   | CPJ202212060005 |
      | 101.92       | 111.13          | 0             | 5.68                   | CPJ202212060005 |
      | 127.23       | 115.94          | 0             | 6.11                   | CPJ202212060005 |
      | 55.22        | 56.08           | 0             | 12.70                  | CPJ202212060005 |
      | 80.34        | 90.77           | 0             | 10.22                  | CPJ202212060005 |
      | 121.99       | 121.99          | 0             | 39.36                  | CPJ202212060005 |

  Scenario:【testcase05】无停用 → 月结转 → 订单部分退货 → 订单部分退货冲销
    Given 【占压年度超额度0501】更新客户信用政策
      | CREDIT_GRADE_ID     | OVERDUE_PERIOD_DAYS | CREDIT_CONTROL_DIMENSION |
      | 1326407914543600902 | 500                 | PRODUCT_SET              |
    When 【占压年度超额度0501】客户在B2B下赊销订单
      | SALE_ORG_CODE | CUSTOMER_ENT_CODE | PRODUCT_VARIETY_CRM_CODE | SKU      | AMOUNT | COUNT | ITEM_ID |
      | 1000002261    | 1000006570        | 1-4O-12                  | 10000558 | 19.97  | 3     | 10      |
      | 1000002261    | 1000006570        | 1-4O-3                   | 10000437 | 12.85  | 3     | 20      |
      | 1000002261    | 1000006570        | 1-4O-3                   | 10000434 | 13.05  | 3     | 30      |
    Then 【占压年度超额度0501】商务审核应通过
    And 【占压年度超额度0501】人工审核应通过
    Then 【占压年度超额度0501】产品集合金额应变动
      | productSetCode | annualAvailableExecuteCreditLimit | annualExecuteCreditLimit | annualOccupiedAmount | exceedingQuotaCreditLimit | exceedingQuotaAvailableAmount | exceedingQuotaOccupiedAmount | exceedingQuotaIneffectiveAmount |
      | YYSYZF         | 0.00                              | 121.99                   | 121.99               | 65.69                     | 25.01                         | 39.36                        | 1.32                            |
      | YYSYYP         | 120.42                            | 326.15                   | 205.73               | 147.90                    | 107.97                        | 10.46                        | 29.47                           |
      | YYSYFYP        | 0.00                              | 262.79                   | 262.79               | 140.32                    | 63.88                         | 54.93                        | 21.51                           |
    And 【占压年度超额度0501】品种金额应变动
      | productVarietyCrmCode | annualAvailableExecuteCreditLimit | annualExecuteCreditLimit | annualOccupiedAmount | exceedingQuotaCreditLimit | exceedingQuotaAvailableAmount | exceedingQuotaOccupiedAmount | exceedingQuotaIneffectiveAmount |
      | 1-4O-7                | 57.13                             | 113.91                   | 56.78                | 55.91                     | 51.76                         | 0.91                         | 3.24                            |
      | 1-4O-14               | 72.50                             | 110.32                   | 37.82                | 40.66                     | 27.70                         | 3.87                         | 9.09                            |
      | 1-4O-8                | -9.21                             | 101.92                   | 111.13               | 51.33                     | 28.51                         | 5.68                         | 17.14                           |
      | 1-4O-3                | 11.29                             | 127.23                   | 115.94               | 63.77                     | 51.90                         | 6.11                         | 5.76                            |
      | 1-4O-9                | -0.86                             | 55.22                    | 56.08                | 30.66                     | -15.71                        | 38.60                        | 7.77                            |
      | 1-292NJV              | -10.43                            | 80.34                    | 90.77                | 45.89                     | 27.69                         | 10.22                        | 7.98                            |
      | 1-4O-12               | 0.00                              | 121.99                   | 121.99               | 65.69                     | 25.01                         | 39.36                        | 1.32                            |
    Then 【占压年度超额度0501】临时信用申请应变动
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
    And 【占压年度超额度0501】流水应变动
      | CREDIT_LIMIT | OCCUPIED_AMOUNT | FROZEN_AMOUNT | OCCUPIED_CHANGE_AMOUNT | CREDIT_TRADE_NO |
      | 121.99       | 100.33          | 0.00          | 21.66                  | CPJ202212060005 |
      | 55.22        | 11.11           | 0.00          | 44.97                  | CPJ202212060005 |
      | 65.69        | 1.11            | 1.32          | 38.25                  | PJLZ2022002     |
      | 0.00         | 0.00            | 0.00          | 22.68                  | PJLZ2022002     |
      | 30.66        | 5.87            | 7.77          | 10.05                  | PJLZ2022003     |
    When 【占压年度超额度0502】月度结转
    Then 【占压年度超额度0502】产品集合金额应变动
      | productSetCode | annualAvailableExecuteCreditLimit | annualExecuteCreditLimit | annualOccupiedAmount | exceedingQuotaCreditLimit | exceedingQuotaAvailableAmount | exceedingQuotaOccupiedAmount | exceedingQuotaIneffectiveAmount |
      | YYSYZF         | -39.36                            | 121.99                   | 161.35               | 0.00                      | 0.00                          | 0.00                         | 0.00                            |
      | YYSYYP         | 109.96                            | 326.15                   | 216.19               | 0.00                      | 0.00                          | 0.00                         | 0.00                            |
      | YYSYFYP        | -54.93                            | 262.79                   | 317.72               | 0.00                      | 0.00                          | 0.00                         | 0.00                            |
    And 【占压年度超额度0502】品种金额应变动
      | productVarietyCrmCode | annualAvailableExecuteCreditLimit | annualExecuteCreditLimit | annualOccupiedAmount | exceedingQuotaCreditLimit | exceedingQuotaAvailableAmount | exceedingQuotaOccupiedAmount | exceedingQuotaIneffectiveAmount |
      | 1-4O-7                | 56.22                             | 113.91                   | 57.69                | 0.00                      | 0.00                          | 0.00                         | 0.00                            |
      | 1-4O-14               | 68.63                             | 110.32                   | 41.69                | 0.00                      | 0.00                          | 0.00                         | 0.00                            |
      | 1-4O-8                | -14.89                            | 101.92                   | 116.81               | 0.00                      | 0.00                          | 0.00                         | 0.00                            |
      | 1-4O-3                | 5.18                              | 127.23                   | 122.05               | 0.00                      | 0.00                          | 0.00                         | 0.00                            |
      | 1-4O-9                | -39.46                            | 55.22                    | 94.68                | 0.00                      | 0.00                          | 0.00                         | 0.00                            |
      | 1-292NJV              | -20.65                            | 80.34                    | 100.99               | 0.00                      | 0.00                          | 0.00                         | 0.00                            |
      | 1-4O-12               | -39.36                            | 121.99                   | 161.35               | 0.00                      | 0.00                          | 0.00                         | 0.00                            |
    And 【占压年度超额度0502】流水应变动
      | CREDIT_LIMIT | OCCUPIED_AMOUNT | FROZEN_AMOUNT | OCCUPIED_CHANGE_AMOUNT | CREDIT_TRADE_NO |
      | 121.99       | 100.33          | 0.00          | 21.66                  | CPJ202212060005 |
      | 55.22        | 11.11           | 0.00          | 44.97                  | CPJ202212060005 |
      | 65.69        | 1.11            | 1.32          | 38.25                  | PJLZ2022002     |
      | 0.00         | 0.00            | 0.00          | 22.68                  | PJLZ2022002     |
      | 30.66        | 5.87            | 7.77          | 10.05                  | PJLZ2022003     |
      | 20.59        | 0.58            | 1.11          | 0.58                   | PJLZ2022002     |
      | 20.11        | 1.56            | 3.22          | 1.56                   | PJLZ2022002     |
      | 30.78        | 2.55            | 9.16          | 2.55                   | PJLZ2022002     |
      | 28.11        | 1.56            | 3.87          | 1.56                   | PJLZ2022002     |
      | 0            | 22.68           | 0             | 22.68                  | PJLZ2022002     |
      | 65.69        | 39.36           | 1.32          | 39.36                  | PJLZ2022002     |
      | 35.32        | 0.33            | 2.13          | 0.33                   | PJLZ2022003     |
      | 20.55        | 2.31            | 5.87          | 2.31                   | PJLZ2022003     |
      | 20.55        | 3.13            | 7.98          | 3.13                   | PJLZ2022003     |
      | 35.66        | 4.55            | 1.89          | 4.55                   | PJLZ2022003     |
      | 30.66        | 15.92           | 7.77          | 15.92                  | PJLZ2022003     |
      | 45.89        | 10.22           | 7.98          | 10.22                  | PJLZ2022003     |
      | 113.91       | 56.78           | 0             | 0.91                   | CPJ202212060005 |
      | 110.32       | 37.82           | 0             | 3.87                   | CPJ202212060005 |
      | 101.92       | 111.13          | 0             | 5.68                   | CPJ202212060005 |
      | 127.23       | 115.94          | 0             | 6.11                   | CPJ202212060005 |
      | 55.22        | 56.08           | 0             | 38.6                   | CPJ202212060005 |
      | 80.34        | 90.77           | 0             | 10.22                  | CPJ202212060005 |
      | 121.99       | 121.99          | 0             | 39.36                  | CPJ202212060005 |
    When 【占压年度超额度0503】客户在B2B退货
      | SALE_ORG_CODE | CUSTOMER_ENT_CODE | PRODUCT_VARIETY_CRM_CODE | SKU      | AMOUNT | COUNT | ITEM_ID |
      | 1000002261    | 1000006570        | 1-4O-7                   | 10000437 | 25.70  | 2     | 20      |
      | 1000002261    | 1000006570        | 1-4O-7                   | 10000434 | 26.10  | 2     | 30      |
    Then 【占压年度超额度0503】产品集合金额应变动
      | productSetCode | annualAvailableExecuteCreditLimit | annualExecuteCreditLimit | annualOccupiedAmount | exceedingQuotaCreditLimit | exceedingQuotaAvailableAmount | exceedingQuotaOccupiedAmount | exceedingQuotaIneffectiveAmount |
      | YYSYZF         | -39.36                            | 121.99                   | 161.35               | 0.00                      | 0.00                          | 0.00                         | 0.00                            |
      | YYSYYP         | 109.96                            | 326.15                   | 216.19               | 0.00                      | 0.00                          | 0.00                         | 0.00                            |
      | YYSYFYP        | -3.13                             | 262.79                   | 265.92               | 0.00                      | 0.00                          | 0.00                         | 0.00                            |
    And 【占压年度超额度0503】品种金额应变动
      | productVarietyCrmCode | annualAvailableExecuteCreditLimit | annualExecuteCreditLimit | annualOccupiedAmount | exceedingQuotaCreditLimit | exceedingQuotaAvailableAmount | exceedingQuotaOccupiedAmount | exceedingQuotaIneffectiveAmount |
      | 1-4O-7                | 56.22                             | 113.91                   | 57.69                | 0.00                      | 0.00                          | 0.00                         | 0.00                            |
      | 1-4O-14               | 68.63                             | 110.32                   | 41.69                | 0.00                      | 0.00                          | 0.00                         | 0.00                            |
      | 1-4O-8                | -14.89                            | 101.92                   | 116.81               | 0.00                      | 0.00                          | 0.00                         | 0.00                            |
      | 1-4O-3                | 5.18                              | 127.23                   | 122.05               | 0.00                      | 0.00                          | 0.00                         | 0.00                            |
      | 1-4O-9                | 12.34                             | 55.22                    | 42.88                | 0.00                      | 0.00                          | 0.00                         | 0.00                            |
      | 1-292NJV              | -20.65                            | 80.34                    | 100.99               | 0.00                      | 0.00                          | 0.00                         | 0.00                            |
      | 1-4O-12               | -39.36                            | 121.99                   | 161.35               | 0.00                      | 0.00                          | 0.00                         | 0.00                            |
    And 【占压年度超额度0503】流水应变动
      | CREDIT_LIMIT | OCCUPIED_AMOUNT | FROZEN_AMOUNT | OCCUPIED_CHANGE_AMOUNT | CREDIT_TRADE_NO |
      | 121.99       | 100.33          | 0.00          | 21.66                  | CPJ202212060005 |
      | 55.22        | 11.11           | 0.00          | 44.97                  | CPJ202212060005 |
      | 65.69        | 1.11            | 1.32          | 38.25                  | PJLZ2022002     |
      | 0.00         | 0.00            | 0.00          | 22.68                  | PJLZ2022002     |
      | 30.66        | 5.87            | 7.77          | 10.05                  | PJLZ2022003     |
      | 20.59        | 0.58            | 1.11          | 0.58                   | PJLZ2022002     |
      | 20.11        | 1.56            | 3.22          | 1.56                   | PJLZ2022002     |
      | 30.78        | 2.55            | 9.16          | 2.55                   | PJLZ2022002     |
      | 28.11        | 1.56            | 3.87          | 1.56                   | PJLZ2022002     |
      | 0            | 22.68           | 0             | 22.68                  | PJLZ2022002     |
      | 65.69        | 39.36           | 1.32          | 39.36                  | PJLZ2022002     |
      | 35.32        | 0.33            | 2.13          | 0.33                   | PJLZ2022003     |
      | 20.55        | 2.31            | 5.87          | 2.31                   | PJLZ2022003     |
      | 20.55        | 3.13            | 7.98          | 3.13                   | PJLZ2022003     |
      | 35.66        | 4.55            | 1.89          | 4.55                   | PJLZ2022003     |
      | 30.66        | 15.92           | 7.77          | 15.92                  | PJLZ2022003     |
      | 45.89        | 10.22           | 7.98          | 10.22                  | PJLZ2022003     |
      | 113.91       | 56.78           | 0             | 0.91                   | CPJ202212060005 |
      | 110.32       | 37.82           | 0             | 3.87                   | CPJ202212060005 |
      | 101.92       | 111.13          | 0             | 5.68                   | CPJ202212060005 |
      | 127.23       | 115.94          | 0             | 6.11                   | CPJ202212060005 |
      | 55.22        | 56.08           | 0             | 38.6                   | CPJ202212060005 |
      | 80.34        | 90.77           | 0             | 10.22                  | CPJ202212060005 |
      | 121.99       | 121.99          | 0             | 39.36                  | CPJ202212060005 |
      | 55.22        | 94.68           | 0             | 51.80                  | CPJ202212060005 |
    When 【占压年度超额度0504】客户在B2B退货冲销
      | SALE_ORG_CODE | CUSTOMER_ENT_CODE | PRODUCT_VARIETY_CRM_CODE | SKU      | AMOUNT | COUNT | ITEM_ID |
      | 1000002261    | 1000006570        | 1-4O-7                   | 10000437 | 12.85  | 1     | 20      |
      | 1000002261    | 1000006570        | 1-4O-7                   | 10000434 | 13.05  | 1     | 30      |
    Then 【占压年度超额度0504】产品集合金额应变动
      | productSetCode | annualAvailableExecuteCreditLimit | annualExecuteCreditLimit | annualOccupiedAmount | exceedingQuotaCreditLimit | exceedingQuotaAvailableAmount | exceedingQuotaOccupiedAmount | exceedingQuotaIneffectiveAmount |
      | YYSYZF         | -39.36                            | 121.99                   | 161.35               | 0.00                      | 0.00                          | 0.00                         | 0.00                            |
      | YYSYYP         | 109.96                            | 326.15                   | 216.19               | 0.00                      | 0.00                          | 0.00                         | 0.00                            |
      | YYSYFYP        | -29.03                            | 262.79                   | 291.82               | 0.00                      | 0.00                          | 0.00                         | 0.00                            |
    And 【占压年度超额度0504】品种金额应变动
      | productVarietyCrmCode | annualAvailableExecuteCreditLimit | annualExecuteCreditLimit | annualOccupiedAmount | exceedingQuotaCreditLimit | exceedingQuotaAvailableAmount | exceedingQuotaOccupiedAmount | exceedingQuotaIneffectiveAmount |
      | 1-4O-7                | 56.22                             | 113.91                   | 57.69                | 0.00                      | 0.00                          | 0.00                         | 0.00                            |
      | 1-4O-14               | 68.63                             | 110.32                   | 41.69                | 0.00                      | 0.00                          | 0.00                         | 0.00                            |
      | 1-4O-8                | -14.89                            | 101.92                   | 116.81               | 0.00                      | 0.00                          | 0.00                         | 0.00                            |
      | 1-4O-3                | 5.18                              | 127.23                   | 122.05               | 0.00                      | 0.00                          | 0.00                         | 0.00                            |
      | 1-4O-9                | -13.56                            | 55.22                    | 68.78                | 0.00                      | 0.00                          | 0.00                         | 0.00                            |
      | 1-292NJV              | -20.65                            | 80.34                    | 100.99               | 0.00                      | 0.00                          | 0.00                         | 0.00                            |
      | 1-4O-12               | -39.36                            | 121.99                   | 161.35               | 0.00                      | 0.00                          | 0.00                         | 0.00                            |
    And 【占压年度超额度0504】流水应变动
      | CREDIT_LIMIT | OCCUPIED_AMOUNT | FROZEN_AMOUNT | OCCUPIED_CHANGE_AMOUNT | CREDIT_TRADE_NO |
      | 121.99       | 100.33          | 0.00          | 21.66                  | CPJ202212060005 |
      | 55.22        | 11.11           | 0.00          | 44.97                  | CPJ202212060005 |
      | 65.69        | 1.11            | 1.32          | 38.25                  | PJLZ2022002     |
      | 0.00         | 0.00            | 0.00          | 22.68                  | PJLZ2022002     |
      | 30.66        | 5.87            | 7.77          | 10.05                  | PJLZ2022003     |
      | 20.59        | 0.58            | 1.11          | 0.58                   | PJLZ2022002     |
      | 20.11        | 1.56            | 3.22          | 1.56                   | PJLZ2022002     |
      | 30.78        | 2.55            | 9.16          | 2.55                   | PJLZ2022002     |
      | 28.11        | 1.56            | 3.87          | 1.56                   | PJLZ2022002     |
      | 0            | 22.68           | 0             | 22.68                  | PJLZ2022002     |
      | 65.69        | 39.36           | 1.32          | 39.36                  | PJLZ2022002     |
      | 35.32        | 0.33            | 2.13          | 0.33                   | PJLZ2022003     |
      | 20.55        | 2.31            | 5.87          | 2.31                   | PJLZ2022003     |
      | 20.55        | 3.13            | 7.98          | 3.13                   | PJLZ2022003     |
      | 35.66        | 4.55            | 1.89          | 4.55                   | PJLZ2022003     |
      | 30.66        | 15.92           | 7.77          | 15.92                  | PJLZ2022003     |
      | 45.89        | 10.22           | 7.98          | 10.22                  | PJLZ2022003     |
      | 113.91       | 56.78           | 0             | 0.91                   | CPJ202212060005 |
      | 110.32       | 37.82           | 0             | 3.87                   | CPJ202212060005 |
      | 101.92       | 111.13          | 0             | 5.68                   | CPJ202212060005 |
      | 127.23       | 115.94          | 0             | 6.11                   | CPJ202212060005 |
      | 55.22        | 56.08           | 0             | 38.6                   | CPJ202212060005 |
      | 80.34        | 90.77           | 0             | 10.22                  | CPJ202212060005 |
      | 121.99       | 121.99          | 0             | 39.36                  | CPJ202212060005 |
      | 55.22        | 94.68           | 0             | 51.80                  | CPJ202212060005 |
      | 55.22        | 42.88           | 0             | 25.90                  | CPJ202212060005 |