Feature: 创建订单提交校验逻辑

  Background: [前置条件] 预折和整单折扣的数据初始化
    Given [创建订单提交校验基础数据01]清除价格\预折\整单折扣的测试数据
    When [创建订单提交校验基础数据01]商品价格测试数据初始化


  @orderCommitVerify01
  Scenario Outline: [场景01]创建订单时，支付方式=空，提交订单校验提示拦截，提示错误信息
  "[主要测试点]
  1. 有预折
  2. 有整单折扣
  3. 支付方式为空
  4. 提交订单校验提示拦截，提示错误信息"
    Given [创建订单提交校验01]当前存在如下客户信息
      | saleOrgSapCode   | regionCode   | customerSapCode   | regionName   | customerName   | shippingAddress   | shippingRecipient   | shippingTelephone   |
      | <saleOrgSapCode> | <regionCode> | <customerSapCode> | <regionName> | <customerName> | <shippingAddress> | <shippingRecipient> | <shippingTelephone> |
    When [创建订单提交校验01]当前存在如下的预折商品信息
      | itemSapCode | currentPrice | calculationType  | calculationValue | preType               | effectiveState | distributionChannelCode | distributionChannelName |
      | 10000841    | 10           | DISCOUNTED_PRICE | 8                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000265    | 6            | DISCOUNTED_PRICE | 5                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000289    | 5            | DISCOUNTED_PRICE | 4.5              | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000556    | 30           | DISCOUNTED_PRICE | 27               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000456    | 20           | DISCOUNTED_PRICE | 18               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000835    | 40           | DISCOUNTED_PRICE | 32               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000633    | 50           | DISCOUNTED_PRICE | 40               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000478    | 17           | DISCOUNTED_PRICE | 13               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
    And [创建订单提交校验01]当前存在如下的预折折扣信息
      | itemSapCode | calculationValue | effectiveState |
      | 10000841    | 8                | ENABLE         |
      | 10000456    | 18               | ENABLE         |
      | 10000556    | 27               | ENABLE         |
      | 10000265    | 5                | ENABLE         |
    And [创建订单提交校验01]当前存在如下的整单折扣申请单信息
      | projectType | module |
      | PT02        | KM03   |
    And [创建订单提交校验01]当前存在如下的整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 10000265    | 1000     | 盒     |
      | 10000289    | 1000     | 盒     |
    And [创建订单提交校验01]当前存在如下的整单折扣分配计划信息
    And [创建订单提交校验01]当前存在如下的整单折扣分配计划详细信息
      | originalItemSapCode | giftItemSapCode | originalDistributionChannelCode | giftDistributionChannelCode | allocationQuantity | originalQuantity | giftQuantity | initialBonusQuantity | needShip | invalidStatus | unit | originalDistributionChannelName | giftDistributionChannelName | lot_no      | production_date | expiration_date | remark     |
      |                     | 10000265        |                                 | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | AT_LTY_0001 | 2024-05-01      | 2025-12-31      | Detail_001 |
      |                     | 10000265        |                                 | 22                          | 100                |                  |              |                      | 0        | NORMAL        | 盒    |                                 | 分销-非医疗                      | AT_LTY_0002 | 2024-05-01      | 2025-12-31      | Detail_002 |
    And [创建订单提交校验01]添加商品
      | itemSapCode | quantity |
      | 10000841    | 10       |
      | 10000456    | 10       |
      | 10000556    | 10       |
    Then [创建订单提交校验01]提交订单校验提示拦截且提示错误信息
      | orderWideDiscountSubject | itemSapCode | needShip | unitPrice | preDiscount | calculationValue | boxRatio | quantity | totalInline | preDiscountTotal | orderDiscountTotal | realInline | remark     |
      | PT00                     | 10000841    | 1        | 10        | 2           | 8                | 1        | 10       | 100         | 20               | 1.43               | 78.57      |            |
      | PT00                     | 10000265    | 1        | 6         | 1           | 5                | 1        | 10       | 60          | 10               | 2.86               | 47.14      |            |
      | PT00                     | 10000456    | 1        | 20        | 2           | 18               | 1        | 1        | 20          | 2                | 2.29               | 15.71      |            |
      | PT00                     | 10000265    | 1        | 6         | 1           | 5                | 1        | 8        | 48          | 8                | 2.29               | 37.71      |            |
      | PT00                     | 10000265    | 1        | 6         | 1           | 5                | 1        | 2        | 12          | 2                | 0.57               | 9.43       |            |
      | PT02                     | 10000265    | 1        | 6         | 1           | 5                | 1        | 2        | 12          | 2                | 0.56               | 9.44       | Detail_001 |

    Examples: 基础信息
      | saleOrgSapCode | regionCode | customerSapCode | regionName | customerName | shippingRecipient | shippingTelephone | shippingAddress |


  @orderCommitVerify02
  Scenario Outline: [场景02]创建订单时，支付方式=铺底货，且使用了整单折扣，提交订单校验提示拦截，提示错误信息
  "[主要测试点]
  1. 有预折
  2. 有整单折扣
  3. 支付方式为铺底货
  4. 创建订单失败"
    Given [创建订单提交校验02]当前存在如下客户信息
      | saleOrgSapCode   | regionCode   | customerSapCode   | regionName   | customerName   | shippingAddress   | shippingRecipient   | shippingTelephone   |
      | <saleOrgSapCode> | <regionCode> | <customerSapCode> | <regionName> | <customerName> | <shippingAddress> | <shippingRecipient> | <shippingTelephone> |
    When [创建订单提交校验02]当前存在如下的预折商品信息
      | itemSapCode | currentPrice | calculationType  | calculationValue | preType               | effectiveState | distributionChannelCode | distributionChannelName |
      | 10000841    | 10           | DISCOUNTED_PRICE | 8                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000265    | 6            | DISCOUNTED_PRICE | 5                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000289    | 5            | DISCOUNTED_PRICE | 4.5              | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000556    | 30           | DISCOUNTED_PRICE | 27               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000456    | 20           | DISCOUNTED_PRICE | 18               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000835    | 40           | DISCOUNTED_PRICE | 32               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000633    | 50           | DISCOUNTED_PRICE | 40               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000478    | 17           | DISCOUNTED_PRICE | 13               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
    And [创建订单提交校验02]当前存在如下的预折折扣信息
      | itemSapCode | calculationValue | effectiveState |
      | 10000841    | 8                | ENABLE         |
      | 10000456    | 18               | ENABLE         |
      | 10000556    | 27               | ENABLE         |
      | 10000265    | 5                | ENABLE         |
    And [创建订单提交校验02]当前存在如下的整单折扣申请单信息
      | projectType | module             |
      | PT02        | KM03  [创建订单提交校验02] |
    And [创建订单提交校验02]当前存在如下的整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 10000265    | 1000     | 盒     |
      | 10000289    | 1000     | 盒     |
    And [创建订单提交校验02]当前存在如下的整单折扣分配计划信息
    And [创建订单提交校验02]当前存在如下的整单折扣分配计划详细信息
      | originalItemSapCode | giftItemSapCode | originalDistributionChannelCode | giftDistributionChannelCode | allocationQuantity | originalQuantity | giftQuantity | initialBonusQuantity | needShip | invalidStatus | unit | originalDistributionChannelName | giftDistributionChannelName | lot_no      | production_date | expiration_date | remark     |
      |                     | 10000265        |                                 | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | AT_LTY_0001 | 2024-05-01      | 2025-12-31      | Detail_001 |
      |                     | 10000265        |                                 | 22                          | 100                |                  |              |                      | 0        | NORMAL        | 盒    |                                 | 分销-非医疗                      | AT_LTY_0002 | 2024-05-01      | 2025-12-31      | Detail_002 |
    And [创建订单提交校验02]添加商品
      | itemSapCode | quantity |
      | 10000841    | 10       |
      | 10000456    | 10       |
      | 10000556    | 10       |
    Then [创建订单提交校验02]创建订单应校验失败且提示错误信息
      | orderWideDiscountSubject | itemSapCode | needShip | unitPrice | preDiscount | calculationValue | boxRatio | quantity | totalInline | preDiscountTotal | orderDiscountTotal | realInline | remark     |
      | PT00                     | 10000841    | 1        | 10        | 2           | 8                | 1        | 10       | 100         | 20               | 1.43               | 78.57      |            |
      | PT00                     | 10000265    | 1        | 6         | 1           | 5                | 1        | 10       | 60          | 10               | 2.86               | 47.14      |            |
      | PT00                     | 10000456    | 1        | 20        | 2           | 18               | 1        | 1        | 20          | 2                | 2.29               | 15.71      |            |
      | PT00                     | 10000265    | 1        | 6         | 1           | 5                | 1        | 8        | 48          | 8                | 2.29               | 37.71      |            |
      | PT00                     | 10000265    | 1        | 6         | 1           | 5                | 1        | 2        | 12          | 2                | 0.57               | 9.43       |            |
      | PT02                     | 10000265    | 1        | 6         | 1           | 5                | 1        | 2        | 12          | 2                | 0.56               | 9.44       | Detail_001 |

    Examples: 基础信息
      | saleOrgSapCode | regionCode | customerSapCode | regionName | customerName | shippingRecipient | shippingTelephone | shippingAddress |


  @orderCommitVerify03
  Scenario Outline: [场景03]创建订单时，收货信息为空，提交订单校验提示拦截，提示错误信息
  "[主要测试点]
  1. 有预折
  2. 有整单折扣
  3. 收货信息为空
  4. 创建订单失败"
    Given [创建订单提交校验03]当前存在如下客户信息
      | saleOrgSapCode   | regionCode   | customerSapCode   | regionName   | customerName   | shippingAddress   | shippingRecipient   | shippingTelephone   |
      | <saleOrgSapCode> | <regionCode> | <customerSapCode> | <regionName> | <customerName> | <shippingAddress> | <shippingRecipient> | <shippingTelephone> |
    When [创建订单提交校验03]当前存在如下的预折商品信息
      | itemSapCode | currentPrice | calculationType  | calculationValue | preType               | effectiveState | distributionChannelCode | distributionChannelName |
      | 10000841    | 10           | DISCOUNTED_PRICE | 8                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000265    | 6            | DISCOUNTED_PRICE | 5                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000289    | 5            | DISCOUNTED_PRICE | 4.5              | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000556    | 30           | DISCOUNTED_PRICE | 27               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000456    | 20           | DISCOUNTED_PRICE | 18               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000835    | 40           | DISCOUNTED_PRICE | 32               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000633    | 50           | DISCOUNTED_PRICE | 40               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000478    | 17           | DISCOUNTED_PRICE | 13               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
    And [创建订单提交校验03]当前存在如下的预折折扣信息
      | itemSapCode | calculationValue | effectiveState |
      | 10000841    | 8                | ENABLE         |
      | 10000456    | 18               | ENABLE         |
      | 10000556    | 27               | ENABLE         |
      | 10000265    | 5                | ENABLE         |
    And [创建订单提交校验03]当前存在如下的整单折扣申请单信息
      | projectType | module             |
      | PT02        | KM03  [创建订单提交校验03] |
    And [创建订单提交校验03]当前存在如下的整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 10000265    | 1000     | 盒     |
      | 10000289    | 1000     | 盒     |
    And [创建订单提交校验03]当前存在如下的整单折扣分配计划信息
    And [创建订单提交校验03]当前存在如下的整单折扣分配计划详细信息
      | originalItemSapCode | giftItemSapCode | originalDistributionChannelCode | giftDistributionChannelCode | allocationQuantity | originalQuantity | giftQuantity | initialBonusQuantity | needShip | invalidStatus | unit | originalDistributionChannelName | giftDistributionChannelName | lot_no      | production_date | expiration_date | remark     |
      |                     | 10000265        |                                 | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | AT_LTY_0001 | 2024-05-01      | 2025-12-31      | Detail_001 |
      |                     | 10000265        |                                 | 22                          | 100                |                  |              |                      | 0        | NORMAL        | 盒    |                                 | 分销-非医疗                      | AT_LTY_0002 | 2024-05-01      | 2025-12-31      | Detail_002 |
    And [创建订单提交校验03]添加商品
      | itemSapCode | quantity |
      | 10000841    | 10       |
      | 10000456    | 10       |
      | 10000556    | 10       |
    Then [创建订单提交校验03]创建订单应校验失败且提示错误信息
      | orderWideDiscountSubject | itemSapCode | needShip | unitPrice | preDiscount | calculationValue | boxRatio | quantity | totalInline | preDiscountTotal | orderDiscountTotal | realInline | remark     |
      | PT00                     | 10000841    | 1        | 10        | 2           | 8                | 1        | 10       | 100         | 20               | 1.43               | 78.57      |            |
      | PT00                     | 10000265    | 1        | 6         | 1           | 5                | 1        | 10       | 60          | 10               | 2.86               | 47.14      |            |
      | PT00                     | 10000456    | 1        | 20        | 2           | 18               | 1        | 1        | 20          | 2                | 2.29               | 15.71      |            |
      | PT00                     | 10000265    | 1        | 6         | 1           | 5                | 1        | 8        | 48          | 8                | 2.29               | 37.71      |            |
      | PT00                     | 10000265    | 1        | 6         | 1           | 5                | 1        | 2        | 12          | 2                | 0.57               | 9.43       |            |
      | PT02                     | 10000265    | 1        | 6         | 1           | 5                | 1        | 2        | 12          | 2                | 0.56               | 9.44       | Detail_001 |

    Examples: 基础信息
      | saleOrgSapCode | regionCode | customerSapCode | regionName | customerName      | shippingRecipient | shippingTelephone | shippingAddress |
      | 1017           | 340000     | 0010000262      | 天津大区       | 天津市海王星辰健康药房连锁有限公司 |                   |                   |                 |

  @orderCommitVerify04
  Scenario Outline: [场景04]创建订单时，收货地址为空，提交订单校验提示拦截，提示错误信息
  "[主要测试点]
  1. 有预折
  2. 有整单折扣
  3. 收货地址为空
  4. 创建订单失败"
    Given [创建订单提交校验04]当前存在如下客户信息
      | saleOrgSapCode   | regionCode   | customerSapCode   | regionName   | customerName   | shippingAddress   | shippingRecipient   | shippingTelephone   |
      | <saleOrgSapCode> | <regionCode> | <customerSapCode> | <regionName> | <customerName> | <shippingAddress> | <shippingRecipient> | <shippingTelephone> |
    When [创建订单提交校验04]当前存在如下的预折商品信息
      | itemSapCode | currentPrice | calculationType  | calculationValue | preType               | effectiveState | distributionChannelCode | distributionChannelName |
      | 10000841    | 10           | DISCOUNTED_PRICE | 8                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000265    | 6            | DISCOUNTED_PRICE | 5                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000289    | 5            | DISCOUNTED_PRICE | 4.5              | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000556    | 30           | DISCOUNTED_PRICE | 27               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000456    | 20           | DISCOUNTED_PRICE | 18               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000835    | 40           | DISCOUNTED_PRICE | 32               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000633    | 50           | DISCOUNTED_PRICE | 40               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000478    | 17           | DISCOUNTED_PRICE | 13               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
    And [创建订单提交校验04]当前存在如下的预折折扣信息
      | itemSapCode | calculationValue | effectiveState |
      | 10000841    | 8                | ENABLE         |
      | 10000456    | 18               | ENABLE         |
      | 10000556    | 27               | ENABLE         |
      | 10000265    | 5                | ENABLE         |
    And [创建订单提交校验04]当前存在如下的整单折扣申请单信息
      | projectType | module             |
      | PT02        | KM03  [创建订单提交校验04] |
    And [创建订单提交校验04]当前存在如下的整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 10000265    | 1000     | 盒     |
      | 10000289    | 1000     | 盒     |
    And [创建订单提交校验04]当前存在如下的整单折扣分配计划信息
    And [创建订单提交校验04]当前存在如下的整单折扣分配计划详细信息
      | originalItemSapCode | giftItemSapCode | originalDistributionChannelCode | giftDistributionChannelCode | allocationQuantity | originalQuantity | giftQuantity | initialBonusQuantity | needShip | invalidStatus | unit | originalDistributionChannelName | giftDistributionChannelName | lot_no      | production_date | expiration_date | remark     |
      |                     | 10000265        |                                 | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | AT_LTY_0001 | 2024-05-01      | 2025-12-31      | Detail_001 |
      |                     | 10000265        |                                 | 22                          | 100                |                  |              |                      | 0        | NORMAL        | 盒    |                                 | 分销-非医疗                      | AT_LTY_0002 | 2024-05-01      | 2025-12-31      | Detail_002 |
    And [创建订单提交校验04]添加商品
      | itemSapCode | quantity |
      | 10000841    | 10       |
      | 10000456    | 10       |
      | 10000556    | 10       |
    Then [创建订单提交校验04]创建订单应校验失败且提示错误信息
      | orderWideDiscountSubject | itemSapCode | needShip | unitPrice | preDiscount | calculationValue | boxRatio | quantity | totalInline | preDiscountTotal | orderDiscountTotal | realInline | remark     |
      | PT00                     | 10000841    | 1        | 10        | 2           | 8                | 1        | 10       | 100         | 20               | 1.43               | 78.57      |            |
      | PT00                     | 10000265    | 1        | 6         | 1           | 5                | 1        | 10       | 60          | 10               | 2.86               | 47.14      |            |
      | PT00                     | 10000456    | 1        | 20        | 2           | 18               | 1        | 1        | 20          | 2                | 2.29               | 15.71      |            |
      | PT00                     | 10000265    | 1        | 6         | 1           | 5                | 1        | 8        | 48          | 8                | 2.29               | 37.71      |            |
      | PT00                     | 10000265    | 1        | 6         | 1           | 5                | 1        | 2        | 12          | 2                | 0.57               | 9.43       |            |
      | PT02                     | 10000265    | 1        | 6         | 1           | 5                | 1        | 2        | 12          | 2                | 0.56               | 9.44       | Detail_001 |

    Examples: 基础信息
      | saleOrgSapCode | regionCode | customerSapCode | regionName | customerName      | shippingRecipient | shippingTelephone | shippingAddress |
      | 1017           | 340000     | 0010000262      | 天津大区       | 天津市海王星辰健康药房连锁有限公司 | 王伟升               | 18658822626       |                 |

  @orderCommitVerify05
  Scenario Outline: [场景05]创建订单时，收货人为空，提交订单校验提示拦截，提示错误信息
  "[主要测试点]
  1. 有预折
  2. 有整单折扣
  3. 收货人为空
  4. 创建订单失败"
    Given [创建订单提交校验05]当前存在如下客户信息
      | saleOrgSapCode   | regionCode   | customerSapCode   | regionName   | customerName   | shippingAddress   | shippingRecipient   | shippingTelephone   |
      | <saleOrgSapCode> | <regionCode> | <customerSapCode> | <regionName> | <customerName> | <shippingAddress> | <shippingRecipient> | <shippingTelephone> |
    When [创建订单提交校验05]当前存在如下的预折商品信息
      | itemSapCode | currentPrice | calculationType  | calculationValue | preType               | effectiveState | distributionChannelCode | distributionChannelName |
      | 10000841    | 10           | DISCOUNTED_PRICE | 8                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000265    | 6            | DISCOUNTED_PRICE | 5                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000289    | 5            | DISCOUNTED_PRICE | 4.5              | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000556    | 30           | DISCOUNTED_PRICE | 27               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000456    | 20           | DISCOUNTED_PRICE | 18               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000835    | 40           | DISCOUNTED_PRICE | 32               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000633    | 50           | DISCOUNTED_PRICE | 40               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000478    | 17           | DISCOUNTED_PRICE | 13               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
    And [创建订单提交校验05]当前存在如下的预折折扣信息
      | itemSapCode | calculationValue | effectiveState |
      | 10000841    | 8                | ENABLE         |
      | 10000456    | 18               | ENABLE         |
      | 10000556    | 27               | ENABLE         |
      | 10000265    | 5                | ENABLE         |
    And [创建订单提交校验05]当前存在如下的整单折扣申请单信息
      | projectType | module             |
      | PT02        | KM03  [创建订单提交校验05] |
    And [创建订单提交校验05]当前存在如下的整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 10000265    | 1000     | 盒     |
      | 10000289    | 1000     | 盒     |
    And [创建订单提交校验05]当前存在如下的整单折扣分配计划信息
    And [创建订单提交校验05]当前存在如下的整单折扣分配计划详细信息
      | originalItemSapCode | giftItemSapCode | originalDistributionChannelCode | giftDistributionChannelCode | allocationQuantity | originalQuantity | giftQuantity | initialBonusQuantity | needShip | invalidStatus | unit | originalDistributionChannelName | giftDistributionChannelName | lot_no      | production_date | expiration_date | remark     |
      |                     | 10000265        |                                 | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | AT_LTY_0001 | 2024-05-01      | 2025-12-31      | Detail_001 |
      |                     | 10000265        |                                 | 22                          | 100                |                  |              |                      | 0        | NORMAL        | 盒    |                                 | 分销-非医疗                      | AT_LTY_0002 | 2024-05-01      | 2025-12-31      | Detail_002 |
    And [创建订单提交校验05]添加商品
      | itemSapCode | quantity |
      | 10000841    | 10       |
      | 10000456    | 10       |
      | 10000556    | 10       |
    Then [创建订单提交校验05]创建订单应校验失败且提示错误信息
      | orderWideDiscountSubject | itemSapCode | needShip | unitPrice | preDiscount | calculationValue | boxRatio | quantity | totalInline | preDiscountTotal | orderDiscountTotal | realInline | remark     |
      | PT00                     | 10000841    | 1        | 10        | 2           | 8                | 1        | 10       | 100         | 20               | 1.43               | 78.57      |            |
      | PT00                     | 10000265    | 1        | 6         | 1           | 5                | 1        | 10       | 60          | 10               | 2.86               | 47.14      |            |
      | PT00                     | 10000456    | 1        | 20        | 2           | 18               | 1        | 1        | 20          | 2                | 2.29               | 15.71      |            |
      | PT00                     | 10000265    | 1        | 6         | 1           | 5                | 1        | 8        | 48          | 8                | 2.29               | 37.71      |            |
      | PT00                     | 10000265    | 1        | 6         | 1           | 5                | 1        | 2        | 12          | 2                | 0.57               | 9.43       |            |
      | PT02                     | 10000265    | 1        | 6         | 1           | 5                | 1        | 2        | 12          | 2                | 0.56               | 9.44       | Detail_001 |

    Examples: 基础信息
      | saleOrgSapCode | regionCode | customerSapCode | regionName | customerName      | shippingRecipient | shippingTelephone | shippingAddress                |
      | 1017           | 340000     | 0010000262      | 天津大区       | 天津市海王星辰健康药房连锁有限公司 |                   | 18658822626       | 天津市东丽区大毕庄镇跃进路西侧天津市物流货运中心金钟物流园内 |

  @orderCommitVerify06
  Scenario Outline: [场景06]创建订单时，联系方式为空，提交订单校验提示拦截，提示错误信息
  "[主要测试点]
  1. 有预折
  2. 有整单折扣
  3. 联系方式为空
  4. 创建订单失败"
    Given [创建订单提交校验06]当前存在如下客户信息
      | saleOrgSapCode   | regionCode   | customerSapCode   | regionName   | customerName   | shippingAddress   | shippingRecipient   | shippingTelephone   |
      | <saleOrgSapCode> | <regionCode> | <customerSapCode> | <regionName> | <customerName> | <shippingAddress> | <shippingRecipient> | <shippingTelephone> |
    When [创建订单提交校验06]当前存在如下的预折商品信息
      | itemSapCode | currentPrice | calculationType  | calculationValue | preType               | effectiveState | distributionChannelCode | distributionChannelName |
      | 10000841    | 10           | DISCOUNTED_PRICE | 8                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000265    | 6            | DISCOUNTED_PRICE | 5                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000289    | 5            | DISCOUNTED_PRICE | 4.5              | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000556    | 30           | DISCOUNTED_PRICE | 27               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000456    | 20           | DISCOUNTED_PRICE | 18               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000835    | 40           | DISCOUNTED_PRICE | 32               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000633    | 50           | DISCOUNTED_PRICE | 40               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000478    | 17           | DISCOUNTED_PRICE | 13               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
    And [创建订单提交校验06]当前存在如下的预折折扣信息
      | itemSapCode | calculationValue | effectiveState |
      | 10000841    | 8                | ENABLE         |
      | 10000456    | 18               | ENABLE         |
      | 10000556    | 27               | ENABLE         |
      | 10000265    | 5                | ENABLE         |
    And [创建订单提交校验06]当前存在如下的整单折扣申请单信息
      | projectType | module             |
      | PT02        | KM03  [创建订单提交校验06] |
    And [创建订单提交校验06]当前存在如下的整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 10000265    | 1000     | 盒     |
      | 10000289    | 1000     | 盒     |
    And [创建订单提交校验06]当前存在如下的整单折扣分配计划信息
    And [创建订单提交校验06]当前存在如下的整单折扣分配计划详细信息
      | originalItemSapCode | giftItemSapCode | originalDistributionChannelCode | giftDistributionChannelCode | allocationQuantity | originalQuantity | giftQuantity | initialBonusQuantity | needShip | invalidStatus | unit | originalDistributionChannelName | giftDistributionChannelName | lot_no      | production_date | expiration_date | remark     |
      |                     | 10000265        |                                 | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | AT_LTY_0001 | 2024-05-01      | 2025-12-31      | Detail_001 |
      |                     | 10000265        |                                 | 22                          | 100                |                  |              |                      | 0        | NORMAL        | 盒    |                                 | 分销-非医疗                      | AT_LTY_0002 | 2024-05-01      | 2025-12-31      | Detail_002 |
    And [创建订单提交校验06]添加商品
      | itemSapCode | quantity |
      | 10000841    | 10       |
      | 10000456    | 10       |
      | 10000556    | 10       |
    Then [创建订单提交校验06]创建订单应校验失败且提示错误信息
      | orderWideDiscountSubject | itemSapCode | needShip | unitPrice | preDiscount | calculationValue | boxRatio | quantity | totalInline | preDiscountTotal | orderDiscountTotal | realInline | remark     |
      | PT00                     | 10000841    | 1        | 10        | 2           | 8                | 1        | 10       | 100         | 20               | 1.43               | 78.57      |            |
      | PT00                     | 10000265    | 1        | 6         | 1           | 5                | 1        | 10       | 60          | 10               | 2.86               | 47.14      |            |
      | PT00                     | 10000456    | 1        | 20        | 2           | 18               | 1        | 1        | 20          | 2                | 2.29               | 15.71      |            |
      | PT00                     | 10000265    | 1        | 6         | 1           | 5                | 1        | 8        | 48          | 8                | 2.29               | 37.71      |            |
      | PT00                     | 10000265    | 1        | 6         | 1           | 5                | 1        | 2        | 12          | 2                | 0.57               | 9.43       |            |
      | PT02                     | 10000265    | 1        | 6         | 1           | 5                | 1        | 2        | 12          | 2                | 0.56               | 9.44       | Detail_001 |

    Examples: 基础信息
      | saleOrgSapCode | regionCode | customerSapCode | regionName | customerName      | shippingRecipient | shippingTelephone | shippingAddress                |
      | 1017           | 340000     | 0010000262      | 天津大区       | 天津市海王星辰健康药房连锁有限公司 | 王伟升               |                   | 天津市东丽区大毕庄镇跃进路西侧天津市物流货运中心金钟物流园内 |

  @orderCommitVerify07
  Scenario Outline: [场景07]创建订单时，商品数量为空，创建订单失败，提示错误信息
  "[主要测试点]
  1. 有预折
  2. 有整单折扣
  3. 商品数量为空
  4. 创建订单失败"
    Given [创建订单提交校验07]当前存在如下客户信息
      | saleOrgSapCode   | regionCode   | customerSapCode   | regionName   | customerName   | shippingAddress   | shippingRecipient   | shippingTelephone   |
      | <saleOrgSapCode> | <regionCode> | <customerSapCode> | <regionName> | <customerName> | <shippingAddress> | <shippingRecipient> | <shippingTelephone> |
    When [创建订单提交校验07]当前存在如下的预折商品信息
      | itemSapCode | currentPrice | calculationType  | calculationValue | preType               | effectiveState | distributionChannelCode | distributionChannelName |
      | 10000841    | 10           | DISCOUNTED_PRICE | 8                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000265    | 6            | DISCOUNTED_PRICE | 5                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000289    | 5            | DISCOUNTED_PRICE | 4.5              | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000556    | 30           | DISCOUNTED_PRICE | 27               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000456    | 20           | DISCOUNTED_PRICE | 18               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000835    | 40           | DISCOUNTED_PRICE | 32               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000633    | 50           | DISCOUNTED_PRICE | 40               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000478    | 17           | DISCOUNTED_PRICE | 13               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
    And [创建订单提交校验07]当前存在如下的预折折扣信息
      | itemSapCode | calculationValue | effectiveState |
      | 10000841    | 8                | ENABLE         |
      | 10000456    | 18               | ENABLE         |
      | 10000556    | 27               | ENABLE         |
      | 10000265    | 5                | ENABLE         |
    And [创建订单提交校验07]当前存在如下的整单折扣申请单信息
      | projectType | module             |
      | PT02        | KM03  [创建订单提交校验07] |
    And [创建订单提交校验07]当前存在如下的整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 10000265    | 1000     | 盒     |
      | 10000289    | 1000     | 盒     |
    And [创建订单提交校验07]当前存在如下的整单折扣分配计划信息
    And [创建订单提交校验07]当前存在如下的整单折扣分配计划详细信息
      | originalItemSapCode | giftItemSapCode | originalDistributionChannelCode | giftDistributionChannelCode | allocationQuantity | originalQuantity | giftQuantity | initialBonusQuantity | needShip | invalidStatus | unit | originalDistributionChannelName | giftDistributionChannelName | lot_no      | production_date | expiration_date | remark     |
      |                     | 10000265        |                                 | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | AT_LTY_0001 | 2024-05-01      | 2025-12-31      | Detail_001 |
      |                     | 10000265        |                                 | 22                          | 100                |                  |              |                      | 0        | NORMAL        | 盒    |                                 | 分销-非医疗                      | AT_LTY_0002 | 2024-05-01      | 2025-12-31      | Detail_002 |
    And [创建订单提交校验07]添加商品
      | itemSapCode | quantity |
      | 10000841    | 10       |
      | 10000456    | 10       |
      | 10000556    | 10       |
    Then [创建订单提交校验07]创建订单应校验失败且提示错误信息
      | orderWideDiscountSubject | itemSapCode | needShip | unitPrice | preDiscount | calculationValue | boxRatio | quantity | totalInline | preDiscountTotal | orderDiscountTotal | realInline | remark     |
      | PT00                     | 10000841    | 1        | 10        | 2           | 8                | 1        |          | 100         | 20               | 1.43               | 78.57      |            |
      | PT00                     | 10000265    | 1        | 6         | 1           | 5                | 1        | 10       | 60          | 10               | 2.86               | 47.14      |            |
      | PT00                     | 10000456    | 1        | 20        | 2           | 18               | 1        |          | 20          | 2                | 2.29               | 15.71      |            |
      | PT00                     | 10000265    | 1        | 6         | 1           | 5                | 1        | 8        | 48          | 8                | 2.29               | 37.71      |            |
      | PT00                     | 10000265    | 1        | 6         | 1           | 5                | 1        | 2        | 12          | 2                | 0.57               | 9.43       |            |
      | PT02                     | 10000265    | 1        | 6         | 1           | 5                | 1        | 2        | 12          | 2                | 0.56               | 9.44       | Detail_001 |

    Examples: 基础信息
      | saleOrgSapCode | regionCode | customerSapCode | regionName | customerName | shippingRecipient | shippingTelephone | shippingAddress |


  @orderCommitVerify08
  Scenario Outline: [场景08]创建订单时，校验商品数量非正整数（商品数量=0），创建订单失败，提示错误信息
  "[主要测试点]
  1. 有预折
  2. 有整单折扣
  3. 校验商品数量非正整数（商品数量=0）
  4. 创建订单失败"
    Given [创建订单提交校验08]当前存在如下客户信息
      | saleOrgSapCode   | regionCode   | customerSapCode   | regionName   | customerName   | shippingAddress   | shippingRecipient   | shippingTelephone   |
      | <saleOrgSapCode> | <regionCode> | <customerSapCode> | <regionName> | <customerName> | <shippingAddress> | <shippingRecipient> | <shippingTelephone> |
    When [创建订单提交校验08]当前存在如下的预折商品信息
      | itemSapCode | currentPrice | calculationType  | calculationValue | preType               | effectiveState | distributionChannelCode | distributionChannelName |
      | 10000841    | 10           | DISCOUNTED_PRICE | 8                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000265    | 6            | DISCOUNTED_PRICE | 5                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000289    | 5            | DISCOUNTED_PRICE | 4.5              | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000556    | 30           | DISCOUNTED_PRICE | 27               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000456    | 20           | DISCOUNTED_PRICE | 18               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000835    | 40           | DISCOUNTED_PRICE | 32               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000633    | 50           | DISCOUNTED_PRICE | 40               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000478    | 17           | DISCOUNTED_PRICE | 13               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
    And [创建订单提交校验08]当前存在如下的预折折扣信息
      | itemSapCode | calculationValue | effectiveState |
      | 10000841    | 8                | ENABLE         |
      | 10000456    | 18               | ENABLE         |
      | 10000556    | 27               | ENABLE         |
      | 10000265    | 5                | ENABLE         |
    And [创建订单提交校验08]当前存在如下的整单折扣申请单信息
      | projectType | module             |
      | PT02        | KM03  [创建订单提交校验08] |
    And [创建订单提交校验08]当前存在如下的整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 10000265    | 1000     | 盒     |
      | 10000289    | 1000     | 盒     |
    And [创建订单提交校验08]当前存在如下的整单折扣分配计划信息
    And [创建订单提交校验08]当前存在如下的整单折扣分配计划详细信息
      | originalItemSapCode | giftItemSapCode | originalDistributionChannelCode | giftDistributionChannelCode | allocationQuantity | originalQuantity | giftQuantity | initialBonusQuantity | needShip | invalidStatus | unit | originalDistributionChannelName | giftDistributionChannelName | lot_no      | production_date | expiration_date | remark     |
      |                     | 10000265        |                                 | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | AT_LTY_0001 | 2024-05-01      | 2025-12-31      | Detail_001 |
      |                     | 10000265        |                                 | 22                          | 100                |                  |              |                      | 0        | NORMAL        | 盒    |                                 | 分销-非医疗                      | AT_LTY_0002 | 2024-05-01      | 2025-12-31      | Detail_002 |
    And [创建订单提交校验08]添加商品
      | itemSapCode | quantity |
      | 10000841    | 10       |
      | 10000456    | 10       |
      | 10000556    | 10       |
    Then [创建订单提交校验08]创建订单应校验失败且提示错误信息
      | orderWideDiscountSubject | itemSapCode | needShip | unitPrice | preDiscount | calculationValue | boxRatio | quantity | totalInline | preDiscountTotal | orderDiscountTotal | realInline | remark |
      | PT00                     | 10000841    | 1        | 10        | 2           | 8                | 1        | 0        | 0           | 0                | 0                  | 0          |        |

    Examples: 基础信息
      | saleOrgSapCode | regionCode | customerSapCode | regionName | customerName | shippingRecipient | shippingTelephone | shippingAddress |


  @orderCommitVerify09
  Scenario Outline: [场景09]创建订单时，商品数量非正整数（含小数），创建订单失败，提示错误信息
  "[主要测试点]
  1. 有预折
  2. 有整单折扣
  3. 商品数量非正整数（含小数）
  4. 创建订单失败"
    Given [创建订单提交校验09]当前存在如下客户信息
      | saleOrgSapCode   | regionCode   | customerSapCode   | regionName   | customerName   | shippingAddress   | shippingRecipient   | shippingTelephone   |
      | <saleOrgSapCode> | <regionCode> | <customerSapCode> | <regionName> | <customerName> | <shippingAddress> | <shippingRecipient> | <shippingTelephone> |
    When [创建订单提交校验09]当前存在如下的预折商品信息
      | itemSapCode | currentPrice | calculationType  | calculationValue | preType               | effectiveState | distributionChannelCode | distributionChannelName |
      | 10000841    | 10           | DISCOUNTED_PRICE | 8                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000265    | 6            | DISCOUNTED_PRICE | 5                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000289    | 5            | DISCOUNTED_PRICE | 4.5              | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000556    | 30           | DISCOUNTED_PRICE | 27               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000456    | 20           | DISCOUNTED_PRICE | 18               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000835    | 40           | DISCOUNTED_PRICE | 32               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000633    | 50           | DISCOUNTED_PRICE | 40               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000478    | 17           | DISCOUNTED_PRICE | 13               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
    And [创建订单提交校验09]当前存在如下的预折折扣信息
      | itemSapCode | calculationValue | effectiveState |
      | 10000841    | 8                | ENABLE         |
      | 10000456    | 18               | ENABLE         |
      | 10000556    | 27               | ENABLE         |
      | 10000265    | 5                | ENABLE         |
    And [创建订单提交校验09]当前存在如下的整单折扣申请单信息
      | projectType | module             |
      | PT02        | KM03  [创建订单提交校验09] |
    And [创建订单提交校验09]当前存在如下的整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 10000265    | 1000     | 盒     |
      | 10000289    | 1000     | 盒     |
    And [创建订单提交校验09]当前存在如下的整单折扣分配计划信息
    And [创建订单提交校验09]当前存在如下的整单折扣分配计划详细信息
      | originalItemSapCode | giftItemSapCode | originalDistributionChannelCode | giftDistributionChannelCode | allocationQuantity | originalQuantity | giftQuantity | initialBonusQuantity | needShip | invalidStatus | unit | originalDistributionChannelName | giftDistributionChannelName | lot_no      | production_date | expiration_date | remark     |
      |                     | 10000265        |                                 | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | AT_LTY_0001 | 2024-05-01      | 2025-12-31      | Detail_001 |
      |                     | 10000265        |                                 | 22                          | 100                |                  |              |                      | 0        | NORMAL        | 盒    |                                 | 分销-非医疗                      | AT_LTY_0002 | 2024-05-01      | 2025-12-31      | Detail_002 |
    And [创建订单提交校验09]添加商品
      | itemSapCode | quantity |
      | 10000841    | 10       |
      | 10000456    | 10       |
      | 10000556    | 10       |
    Then [创建订单提交校验09]创建订单应校验失败且提示错误信息
      | orderWideDiscountSubject | itemSapCode | needShip | unitPrice | preDiscount | calculationValue | boxRatio | quantity | totalInline | preDiscountTotal | orderDiscountTotal | realInline | remark     |
      | PT00                     | 10000841    | 1        | 10        | 2           | 8                | 1        | 1.95     | 100         | 20               | 1.43               | 78.57      |            |
      | PT00                     | 10000265    | 1        | 6         | 1           | 5                | 1        | 10       | 60          | 10               | 2.86               | 47.14      |            |
      | PT00                     | 10000456    | 1        | 20        | 2           | 18               | 1        | 1.2      | 20          | 2                | 2.29               | 15.71      |            |
      | PT00                     | 10000265    | 1        | 6         | 1           | 5                | 1        | 8        | 48          | 8                | 2.29               | 37.71      |            |
      | PT00                     | 10000265    | 1        | 6         | 1           | 5                | 1        | 2        | 12          | 2                | 0.57               | 9.43       |            |
      | PT02                     | 10000265    | 1        | 6         | 1           | 5                | 1        | 2        | 12          | 2                | 0.56               | 9.44       | Detail_001 |

    Examples: 基础信息
      | saleOrgSapCode | regionCode | customerSapCode | regionName | customerName | shippingRecipient | shippingTelephone | shippingAddress |


  @orderCommitVerify10
  Scenario Outline: [场景10]创建订单时，商品数量非正整数（负数），创建订单失败，提示错误信息
  "[主要测试点]
  1. 有预折
  2. 有整单折扣
  3. 商品数量非正整数（负数）
  4. 创建订单失败"
    Given [创建订单提交校验10]当前存在如下客户信息
      | saleOrgSapCode   | regionCode   | customerSapCode   | regionName   | customerName   | shippingAddress   | shippingRecipient   | shippingTelephone   |
      | <saleOrgSapCode> | <regionCode> | <customerSapCode> | <regionName> | <customerName> | <shippingAddress> | <shippingRecipient> | <shippingTelephone> |
    When [创建订单提交校验10]当前存在如下的预折商品信息
      | itemSapCode | currentPrice | calculationType  | calculationValue | preType               | effectiveState | distributionChannelCode | distributionChannelName |
      | 10000841    | 10           | DISCOUNTED_PRICE | 8                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000265    | 6            | DISCOUNTED_PRICE | 5                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000289    | 5            | DISCOUNTED_PRICE | 4.5              | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000556    | 30           | DISCOUNTED_PRICE | 27               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000456    | 20           | DISCOUNTED_PRICE | 18               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000835    | 40           | DISCOUNTED_PRICE | 32               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000633    | 50           | DISCOUNTED_PRICE | 40               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000478    | 17           | DISCOUNTED_PRICE | 13               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
    And [创建订单提交校验10]当前存在如下的预折折扣信息
      | itemSapCode | calculationValue | effectiveState |
      | 10000841    | 8                | ENABLE         |
      | 10000456    | 18               | ENABLE         |
      | 10000556    | 27               | ENABLE         |
      | 10000265    | 5                | ENABLE         |
    And [创建订单提交校验10]当前存在如下的整单折扣申请单信息
      | projectType | module |
      | PT02        | KM03   |
    And [创建订单提交校验10]当前存在如下的整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 10000265    | 1000     | 盒     |
      | 10000289    | 1000     | 盒     |
    And [创建订单提交校验10]当前存在如下的整单折扣分配计划信息
    And [创建订单提交校验10]当前存在如下的整单折扣分配计划详细信息
      | originalItemSapCode | giftItemSapCode | originalDistributionChannelCode | giftDistributionChannelCode | allocationQuantity | originalQuantity | giftQuantity | initialBonusQuantity | needShip | invalidStatus | unit | originalDistributionChannelName | giftDistributionChannelName | lot_no      | production_date | expiration_date | remark     |
      |                     | 10000265        |                                 | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | AT_LTY_0001 | 2024-05-01      | 2025-12-31      | Detail_001 |
      |                     | 10000265        |                                 | 22                          | 100                |                  |              |                      | 0        | NORMAL        | 盒    |                                 | 分销-非医疗                      | AT_LTY_0002 | 2024-05-01      | 2025-12-31      | Detail_002 |
    And [创建订单提交校验10]添加商品
      | itemSapCode | quantity |
      | 10000841    | 10       |
      | 10000456    | 10       |
      | 10000556    | 10       |
    Then [创建订单提交校验10]创建订单应校验失败且提示错误信息
      | orderWideDiscountSubject | itemSapCode | needShip | unitPrice | preDiscount | calculationValue | boxRatio | quantity | totalInline | preDiscountTotal | orderDiscountTotal | realInline | remark     |
      | PT00                     | 10000841    | 1        | 10        | 2           | 8                | 1        | -10      | 100         | 20               | 1.43               | 78.57      |            |
      | PT00                     | 10000265    | 1        | 6         | 1           | 5                | 1        | 10       | 60          | 10               | 2.86               | 47.14      |            |
      | PT00                     | 10000456    | 1        | 20        | 2           | 18               | 1        | 1        | 20          | 2                | 2.29               | 15.71      |            |
      | PT00                     | 10000265    | 1        | 6         | 1           | 5                | 1        | 8        | 48          | 8                | 2.29               | 37.71      |            |
      | PT00                     | 10000265    | 1        | 6         | 1           | 5                | 1        | 2        | 12          | 2                | 0.57               | 9.43       |            |
      | PT02                     | 10000265    | 1        | 6         | 1           | 5                | 1        | -2       | 12          | 2                | 0.56               | 9.44       | Detail_001 |

    Examples: 基础信息
      | saleOrgSapCode | regionCode | customerSapCode | regionName | customerName | shippingRecipient | shippingTelephone | shippingAddress |


  @orderCommitVerify11
  Scenario Outline: [场景11]创建订单时，商品（原品）禁销，提交订单校验提示拦截，提示错误信息
  "[主要测试点]
  1. 有预折
  2. 有整单折扣
  3. 商品（原品）禁销
  4. 创建订单失败"
    Given [创建订单提交校验11]当前存在如下客户信息
      | saleOrgSapCode   | regionCode   | customerSapCode   | regionName   | customerName   | shippingAddress   | shippingRecipient   | shippingTelephone   |
      | <saleOrgSapCode> | <regionCode> | <customerSapCode> | <regionName> | <customerName> | <shippingAddress> | <shippingRecipient> | <shippingTelephone> |
    When [创建订单提交校验11]当前存在如下的预折商品信息
      | itemSapCode | currentPrice | calculationType  | calculationValue | preType               | effectiveState | distributionChannelCode | distributionChannelName |
      | 10000841    | 10           | DISCOUNTED_PRICE | 8                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000265    | 6            | DISCOUNTED_PRICE | 5                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000289    | 5            | DISCOUNTED_PRICE | 4.5              | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000556    | 30           | DISCOUNTED_PRICE | 27               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000456    | 20           | DISCOUNTED_PRICE | 18               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000835    | 40           | DISCOUNTED_PRICE | 32               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000633    | 50           | DISCOUNTED_PRICE | 40               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000478    | 17           | DISCOUNTED_PRICE | 13               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
    And [创建订单提交校验11]当前存在如下的预折折扣信息
      | itemSapCode | calculationValue | effectiveState |
      | 10000841    | 8                | ENABLE         |
      | 10000456    | 15               | ENABLE         |
      | 10000556    | 27               | ENABLE         |
      | 10000265    | 5                | DISABLE        |
    And [创建订单提交校验11]当前存在如下的整单折扣申请单信息
      | projectType | module |
      | PT04        | KM03   |
    And [创建订单提交校验11]当前存在如下的整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 10000265    | 100      | 盒     |
      | 10000289    | 100      | 盒     |
    And [创建订单提交校验11]当前存在如下的整单折扣分配计划信息
    And [创建订单提交校验11]当前存在如下的整单折扣分配计划详细信息
      | originalItemSapCode | originalDistributionChannelCode | giftItemSapCode | giftDistributionChannelCode | allocationQuantity | originalQuantity | giftQuantity | initialBonusQuantity | needShip | invalidStatus | unit | originalDistributionChannelName | giftDistributionChannelName | remark     |
      |                     |                                 | 10000265        | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | Detail_001 |
    And [创建订单提交校验11]添加商品
      | itemSapCode | quantity |
      | 10000841    | 10       |
      | 10000456    | 3        |
      | 10000556    | 10       |
    And [创建订单提交校验11]选择项目
      | itemSapCode | quantity |
      | 10000265    | 24       |
    And [创建订单提交校验11]当前存在如下的商品禁销信息
      | itemSapCode | saleOrgSapCode   | regionCode | saleOrgEffectiveState | permittedSaleQuantityRegion | regionEffectiveState | hasSaleQuantityUnits | customerEntZtCode | customerEntSapCode | customerEntCrmCode | type  | customerName | permittedSaleQuantityCustomer | hasSaleQuantityCustomer | customerEffectiveState | unitCode | unitName |
      | 10000841    | <saleOrgSapCode> | 100000     | ENABLE                | 200                         | ENABLE               | 0                    |                   |                    |                    | SHARE | 大区共享         | 200                           | 0                       | NORMAL                 | HE       | 盒        |
    Then [创建订单提交校验11]创建订单应校验失败且提示错误信息
      | orderWideDiscountSubject | itemSapCode | needShip | unitPrice | quantity | preDiscount | calculationValue | boxRatio | totalInline | preDiscountTotal | orderDiscountTotal | realInline | remark |
      | PT00                     | 10000841    | 1        | 10        | 2        | 2           | 8                | 1        | 20          | 4                | 0                  | 16         |        |

    Examples: 基础信息
      | saleOrgSapCode | regionCode | customerSapCode | regionName | customerName | shippingRecipient | shippingTelephone | shippingAddress |


  @orderCommitVerify12
  Scenario Outline: [场景12]创建订单时，大区限销，且限购商品的数量>限购数量，提交订单校验提示拦截，提示错误信息
  "[主要测试点]
  1. 有预折
  2. 无整单折扣
  3. 大区限销，且限购商品的数量>限购数量 （希望购买5盒，当前可购的最大数量：4盒）
  4. 创建订单失败"
    Given [创建订单提交校验12]当前存在如下客户信息
      | saleOrgSapCode   | regionCode   | customerSapCode   | regionName   | customerName   | shippingAddress   | shippingRecipient   | shippingTelephone   |
      | <saleOrgSapCode> | <regionCode> | <customerSapCode> | <regionName> | <customerName> | <shippingAddress> | <shippingRecipient> | <shippingTelephone> |
    When [创建订单提交校验12]当前存在如下的预折商品信息
      | itemSapCode | currentPrice | calculationType  | calculationValue | preType               | effectiveState | distributionChannelCode | distributionChannelName |
      | 10000841    | 10           | DISCOUNTED_PRICE | 8                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000265    | 6            | DISCOUNTED_PRICE | 5                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000289    | 5            | DISCOUNTED_PRICE | 4.5              | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000556    | 30           | DISCOUNTED_PRICE | 27               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000456    | 20           | DISCOUNTED_PRICE | 18               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000835    | 40           | DISCOUNTED_PRICE | 32               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000633    | 50           | DISCOUNTED_PRICE | 40               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000478    | 17           | DISCOUNTED_PRICE | 13               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
    And [创建订单提交校验12]当前存在如下的预折折扣信息
      | itemSapCode | calculationValue | effectiveState |
      | 10000841    | 8                | ENABLE         |
      | 10000456    | 15               | ENABLE         |
      | 10000556    | 27               | ENABLE         |
      | 10000265    | 5                | DISABLE        |
    And [创建订单提交校验12]当前存在如下的整单折扣申请单信息
      | projectType | module |
      | PT04        | KM03   |
    And [创建订单提交校验12]当前存在如下的整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 10000265    | 100      | 盒     |
      | 10000289    | 100      | 盒     |
    And [创建订单提交校验12]当前存在如下的整单折扣分配计划信息
    And [创建订单提交校验12]当前存在如下的整单折扣分配计划详细信息
      | originalItemSapCode | originalDistributionChannelCode | giftItemSapCode | giftDistributionChannelCode | allocationQuantity | originalQuantity | giftQuantity | initialBonusQuantity | needShip | invalidStatus | unit | originalDistributionChannelName | giftDistributionChannelName | remark     |
      |                     |                                 | 10000265        | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | Detail_001 |
    And [创建订单提交校验12]添加商品
      | itemSapCode | quantity |
      | 10000841    | 10       |
      | 10000456    | 3        |
      | 10000556    | 10       |
    And [创建订单提交校验12]选择项目
      | itemSapCode | quantity |
      | 10000265    | 24       |
    And [创建订单提交校验12]当前存在如下的商品禁销信息
      | itemSapCode | saleOrgSapCode   | regionCode | saleOrgEffectiveState | permittedSaleQuantityRegion | regionEffectiveState | hasSaleQuantityUnits | customerEntZtCode | customerEntSapCode | customerEntCrmCode | type  | customerName | permittedSaleQuantityCustomer | hasSaleQuantityCustomer | customerEffectiveState | unitCode | unitName |
      | 10000841    | <saleOrgSapCode> | 340000     | ENABLE                | 200                         | ENABLE               | 0                    |                   |                    |                    | SHARE | 大区共享         | 200                           | 196                     | NORMAL                 | HE       | 盒        |
    Then [创建订单提交校验12]创建订单应校验失败且提示错误信息
      | orderWideDiscountSubject | itemSapCode | needShip | unitPrice | quantity | preDiscount | calculationValue | boxRatio | totalInline | preDiscountTotal | orderDiscountTotal | realInline | remark |
      | PT00                     | 10000841    | 1        | 10        | 5        | 10          | 8                | 0        | 50          | 10               | 0                  | 40         |        |

    Examples: 基础信息
      | saleOrgSapCode | regionCode | customerSapCode | regionName | customerName | shippingRecipient | shippingTelephone | shippingAddress |


  @orderCommitVerify13
  Scenario Outline: [场景13]创建订单时，大区限销，客户允销，且限购商品的数量>限购数量，提交订单校验提示拦截，提示错误信息
  "[主要测试点]
  1. 有预折
  2. 无整单折扣
  3. 大区限销，客户允销，且限购商品的数量>限购数量 （希望购买5盒，当前可购的最大数量：4盒）
  4. 创建订单失败"
    Given [创建订单提交校验13]当前存在如下客户信息
      | saleOrgSapCode   | regionCode   | customerSapCode   | regionName   | customerName   | shippingAddress   | shippingRecipient   | shippingTelephone   |
      | <saleOrgSapCode> | <regionCode> | <customerSapCode> | <regionName> | <customerName> | <shippingAddress> | <shippingRecipient> | <shippingTelephone> |
    When [创建订单提交校验13]当前存在如下的预折商品信息
      | itemSapCode | currentPrice | calculationType  | calculationValue | preType               | effectiveState | distributionChannelCode | distributionChannelName |
      | 10000841    | 10           | DISCOUNTED_PRICE | 8                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000265    | 6            | DISCOUNTED_PRICE | 5                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000289    | 5            | DISCOUNTED_PRICE | 4.5              | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000556    | 30           | DISCOUNTED_PRICE | 27               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000456    | 20           | DISCOUNTED_PRICE | 18               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000835    | 40           | DISCOUNTED_PRICE | 32               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000633    | 50           | DISCOUNTED_PRICE | 40               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000478    | 17           | DISCOUNTED_PRICE | 13               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
    And [创建订单提交校验13]当前存在如下的预折折扣信息
      | itemSapCode | calculationValue | effectiveState |
      | 10000841    | 8                | ENABLE         |
      | 10000456    | 15               | ENABLE         |
      | 10000556    | 27               | ENABLE         |
      | 10000265    | 5                | DISABLE        |
    And [创建订单提交校验13]当前存在如下的整单折扣申请单信息
      | projectType | module |
      | PT04        | KM03   |
    And [创建订单提交校验13]当前存在如下的整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 10000265    | 100      | 盒     |
      | 10000289    | 100      | 盒     |
    And [创建订单提交校验13]当前存在如下的整单折扣分配计划信息
    And [创建订单提交校验13]当前存在如下的整单折扣分配计划详细信息
      | originalItemSapCode | originalDistributionChannelCode | giftItemSapCode | giftDistributionChannelCode | allocationQuantity | originalQuantity | giftQuantity | initialBonusQuantity | needShip | invalidStatus | unit | originalDistributionChannelName | giftDistributionChannelName | remark     |
      |                     |                                 | 10000265        | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | Detail_001 |
    And [创建订单提交校验13]添加商品
      | itemSapCode | quantity |
      | 10000841    | 10       |
      | 10000456    | 3        |
      | 10000556    | 10       |
    And [创建订单提交校验13]选择项目
      | itemSapCode | quantity |
      | 10000265    | 24       |
    And [创建订单提交校验13]当前存在如下的商品禁销信息
      | itemSapCode | saleOrgSapCode   | regionCode | saleOrgEffectiveState | permittedSaleQuantityRegion | regionEffectiveState | hasSaleQuantityUnits | customerEntZtCode | customerEntSapCode | customerEntCrmCode | type  | customerName | permittedSaleQuantityCustomer | hasSaleQuantityCustomer | customerEffectiveState | unitCode | unitName |
      | 10000841    | <saleOrgSapCode> | 340000     | ENABLE                | 200                         | ENABLE               | 0                    |                   |                    |                    | SHARE | 大区共享         | 150                           | 0                       | NORMAL                 | HE       | 盒        |
    And [创建订单提交校验13]当前存在如下的商品一级商允销信息
      | customerSapCode   | type                 | customerName      | permittedSaleQuantityCustomer | hasSaleQuantityCustomer | customerEffectiveState |
      | <customerSapCode> | FIRST_LEVEL_CUSTOMER | 天津市海王星辰健康药房连锁有限公司 | 50                            | 46                      | NORMAL                 |
    Then [创建订单提交校验13]创建订单应校验失败且提示错误信息
      | orderWideDiscountSubject | itemSapCode | needShip | unitPrice | quantity | preDiscount | calculationValue | boxRatio | totalInline | preDiscountTotal | orderDiscountTotal | realInline | remark |
      | PT00                     | 10000841    | 1        | 10        | 5        | 10          | 8                | 0        | 50          | 10               | 0                  | 40         |        |

    Examples: 基础信息
      | saleOrgSapCode | regionCode | customerSapCode | regionName | customerName | shippingRecipient | shippingTelephone | shippingAddress |


  @orderCommitVerify14
  Scenario Outline: [场景14]创建订单时，商品（赠品）禁销，提交订单校验提示拦截，提示错误信息
  "[主要测试点]
  1. 有预折
  2. 有整单折扣
  3. 商品（赠品）禁销
  4. 创建订单失败"
    Given [创建订单提交校验14]当前存在如下客户信息
      | saleOrgSapCode   | regionCode   | customerSapCode   | regionName   | customerName   | shippingAddress   | shippingRecipient   | shippingTelephone   |
      | <saleOrgSapCode> | <regionCode> | <customerSapCode> | <regionName> | <customerName> | <shippingAddress> | <shippingRecipient> | <shippingTelephone> |
    When [创建订单提交校验14]当前存在如下的预折商品信息
      | itemSapCode | currentPrice | calculationType  | calculationValue | preType               | effectiveState | distributionChannelCode | distributionChannelName |
      | 10000841    | 10           | DISCOUNTED_PRICE | 8                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000265    | 6            | DISCOUNTED_PRICE | 5                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000289    | 5            | DISCOUNTED_PRICE | 4.5              | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000556    | 30           | DISCOUNTED_PRICE | 27               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000456    | 20           | DISCOUNTED_PRICE | 18               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000835    | 40           | DISCOUNTED_PRICE | 32               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000633    | 50           | DISCOUNTED_PRICE | 40               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000478    | 17           | DISCOUNTED_PRICE | 13               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
    And [创建订单提交校验14]当前存在如下的预折折扣信息
      | itemSapCode | calculationValue | effectiveState |
      | 10000841    | 8                | ENABLE         |
      | 10000456    | 15               | ENABLE         |
      | 10000556    | 27               | ENABLE         |
      | 10000265    | 5                | DISABLE        |
    And [创建订单提交校验14]当前存在如下的整单折扣申请单信息
      | projectType | module |
      | PT04        | KM03   |
    And [创建订单提交校验14]当前存在如下的整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 10000265    | 100      | 盒     |
      | 10000289    | 100      | 盒     |
    And [创建订单提交校验14]当前存在如下的整单折扣分配计划信息
    And [创建订单提交校验14]当前存在如下的整单折扣分配计划详细信息
      | originalItemSapCode | originalDistributionChannelCode | giftItemSapCode | giftDistributionChannelCode | allocationQuantity | originalQuantity | giftQuantity | initialBonusQuantity | needShip | invalidStatus | unit | originalDistributionChannelName | giftDistributionChannelName | remark     |
      |                     |                                 | 10000265        | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | Detail_001 |
    And [创建订单提交校验14]添加商品
      | itemSapCode | quantity |
      | 10000841    | 30       |
    And [创建订单提交校验14]选择项目
      | itemSapCode | quantity |
      | 10000265    | 3        |
    And [创建订单提交校验14]当前存在如下的商品禁销信息
      | itemSapCode | saleOrgSapCode   | regionCode | saleOrgEffectiveState | permittedSaleQuantityRegion | regionEffectiveState | hasSaleQuantityUnits | customerEntZtCode | customerEntSapCode | customerEntCrmCode | type  | customerName | permittedSaleQuantityCustomer | hasSaleQuantityCustomer | customerEffectiveState | unitCode | unitName |
      | 10000265    | <saleOrgSapCode> | 100000     | ENABLE                | 200                         | ENABLE               | 0                    |                   |                    |                    | SHARE | 大区共享         | 200                           | 0                       | NORMAL                 | HE       | 盒        |
    Then [创建订单提交校验14]创建订单应校验失败且提示错误信息
      | orderWideDiscountSubject | itemSapCode | needShip | unitPrice | quantity | preDiscount | calculationValue | boxRatio | totalInline | preDiscountTotal | orderDiscountTotal | realInline | remark     |
      | PT00                     | 10000841    | 1        | 10        | 30       | 2           | 8                | 1        | 300         | 60               | 13.58              | 226.42     |            |
      | PT04                     | 10000265    | 1        | 6         | 3        | 0           | 0                | 1        | 18          | 0                | 4.42               | 13.58      | Detail_001 |

    Examples: 基础信息
      | saleOrgSapCode | regionCode | customerSapCode | regionName | customerName | shippingRecipient | shippingTelephone | shippingAddress |


  @orderCommitVerify15
  Scenario Outline: [场景15]创建订单时，大区限销，且限购商品的数量>限购数量，提交订单校验提示拦截，提示错误信息
  "[主要测试点]
  1. 有预折
  2. 有整单折扣
  3. 大区限销，且限购商品的数量>限购数量 （希望加购赠品3盒，当前可购的最大数量：2盒）
  4. 创建订单失败"
    Given [创建订单提交校验15]当前存在如下客户信息
      | saleOrgSapCode   | regionCode   | customerSapCode   | regionName   | customerName   | shippingAddress   | shippingRecipient   | shippingTelephone   |
      | <saleOrgSapCode> | <regionCode> | <customerSapCode> | <regionName> | <customerName> | <shippingAddress> | <shippingRecipient> | <shippingTelephone> |
    When [创建订单提交校验15]当前存在如下的预折商品信息
      | itemSapCode | currentPrice | calculationType  | calculationValue | preType               | effectiveState | distributionChannelCode | distributionChannelName |
      | 10000841    | 10           | DISCOUNTED_PRICE | 8                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000265    | 6            | DISCOUNTED_PRICE | 5                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000289    | 5            | DISCOUNTED_PRICE | 4.5              | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000556    | 30           | DISCOUNTED_PRICE | 27               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000456    | 20           | DISCOUNTED_PRICE | 18               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000835    | 40           | DISCOUNTED_PRICE | 32               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000633    | 50           | DISCOUNTED_PRICE | 40               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000478    | 17           | DISCOUNTED_PRICE | 13               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
    And [创建订单提交校验15]当前存在如下的预折折扣信息
      | itemSapCode | calculationValue | effectiveState |
      | 10000841    | 8                | ENABLE         |
      | 10000456    | 15               | ENABLE         |
      | 10000556    | 27               | ENABLE         |
      | 10000265    | 5                | DISABLE        |
    And [创建订单提交校验15]当前存在如下的整单折扣申请单信息
      | projectType | module |
      | PT04        | KM03   |
    And [创建订单提交校验15]当前存在如下的整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 10000265    | 100      | 盒     |
      | 10000289    | 100      | 盒     |
    And [创建订单提交校验15]当前存在如下的整单折扣分配计划信息
    And [创建订单提交校验15]当前存在如下的整单折扣分配计划详细信息
      | originalItemSapCode | originalDistributionChannelCode | giftItemSapCode | giftDistributionChannelCode | allocationQuantity | originalQuantity | giftQuantity | initialBonusQuantity | needShip | invalidStatus | unit | originalDistributionChannelName | giftDistributionChannelName | remark     |
      |                     |                                 | 10000265        | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | Detail_001 |
    And [创建订单提交校验15]添加商品
      | itemSapCode | quantity |
      | 10000841    | 30       |
    And [创建订单提交校验15]选择项目
      | itemSapCode | quantity |
      | 10000265    | 3        |
    And [创建订单提交校验15]当前存在如下的商品禁销信息
      | itemSapCode | saleOrgSapCode   | regionCode | saleOrgEffectiveState | permittedSaleQuantityRegion | regionEffectiveState | hasSaleQuantityUnits | customerEntZtCode | customerEntSapCode | customerEntCrmCode | type  | customerName | permittedSaleQuantityCustomer | hasSaleQuantityCustomer | customerEffectiveState | unitCode | unitName |
      | 10000265    | <saleOrgSapCode> | 340000     | ENABLE                | 200                         | ENABLE               | 0                    |                   |                    |                    | SHARE | 大区共享         | 200                           | 198                     | NORMAL                 | HE       | 盒        |
    Then [创建订单提交校验15]创建订单应校验失败且提示错误信息
      | orderWideDiscountSubject | itemSapCode | needShip | unitPrice | quantity | preDiscount | calculationValue | boxRatio | totalInline | preDiscountTotal | orderDiscountTotal | realInline | remark     |
      | PT00                     | 10000841    | 1        | 10        | 30       | 2           | 8                | 1        | 300         | 60               | 13.58              | 226.42     |            |
      | PT04                     | 10000265    | 1        | 6         | 3        | 0           | 0                | 1        | 18          | 0                | 4.42               | 13.58      | Detail_001 |

    Examples: 基础信息
      | saleOrgSapCode | regionCode | customerSapCode | regionName | customerName | shippingRecipient | shippingTelephone | shippingAddress |


  @orderCommitVerify16
  Scenario Outline: [场景16]创建订单时，大区限销，客户允销，且限购商品的数量>限购数量，提交订单校验提示拦截，提示错误信息
  "[主要测试点]
  1. 有预折
  2. 有整单折扣
  3. 大区限销，客户允销，且限购商品的数量>限购数量 （希望加购赠品3盒，当前可购的最大数量：2盒）
  4. 创建订单失败"
    Given [创建订单提交校验16]当前存在如下客户信息
      | saleOrgSapCode   | regionCode   | customerSapCode   | regionName   | customerName   | shippingAddress   | shippingRecipient   | shippingTelephone   |
      | <saleOrgSapCode> | <regionCode> | <customerSapCode> | <regionName> | <customerName> | <shippingAddress> | <shippingRecipient> | <shippingTelephone> |
    When [创建订单提交校验16]当前存在如下的预折商品信息
      | itemSapCode | currentPrice | calculationType  | calculationValue | preType               | effectiveState | distributionChannelCode | distributionChannelName |
      | 10000841    | 10           | DISCOUNTED_PRICE | 8                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000265    | 6            | DISCOUNTED_PRICE | 5                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000289    | 5            | DISCOUNTED_PRICE | 4.5              | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000556    | 30           | DISCOUNTED_PRICE | 27               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000456    | 20           | DISCOUNTED_PRICE | 18               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000835    | 40           | DISCOUNTED_PRICE | 32               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000633    | 50           | DISCOUNTED_PRICE | 40               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000478    | 17           | DISCOUNTED_PRICE | 13               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
    And [创建订单提交校验16]当前存在如下的预折折扣信息
      | itemSapCode | calculationValue | effectiveState |
      | 10000841    | 8                | ENABLE         |
      | 10000456    | 15               | ENABLE         |
      | 10000556    | 27               | ENABLE         |
      | 10000265    | 5                | DISABLE        |
    And [创建订单提交校验16]当前存在如下的整单折扣申请单信息
      | projectType | module |
      | PT04        | KM03   |
    And [创建订单提交校验16]当前存在如下的整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 10000265    | 100      | 盒     |
      | 10000289    | 100      | 盒     |
    And [创建订单提交校验16]当前存在如下的整单折扣分配计划信息
    And [创建订单提交校验16]当前存在如下的整单折扣分配计划详细信息
      | originalItemSapCode | originalDistributionChannelCode | giftItemSapCode | giftDistributionChannelCode | allocationQuantity | originalQuantity | giftQuantity | initialBonusQuantity | needShip | invalidStatus | unit | originalDistributionChannelName | giftDistributionChannelName | remark     |
      |                     |                                 | 10000265        | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | Detail_001 |
    And [创建订单提交校验16]添加商品
      | itemSapCode | quantity |
      | 10000841    | 30       |
    And [创建订单提交校验16]选择项目
      | itemSapCode | quantity |
      | 10000265    | 3        |
    And [创建订单提交校验16]当前存在如下的商品禁销信息
      | itemSapCode | saleOrgSapCode   | regionCode | saleOrgEffectiveState | permittedSaleQuantityRegion | regionEffectiveState | hasSaleQuantityUnits | customerEntZtCode | customerEntSapCode | customerEntCrmCode | type  | customerName | permittedSaleQuantityCustomer | hasSaleQuantityCustomer | customerEffectiveState | unitCode | unitName |
      | 10000265    | <saleOrgSapCode> | 340000     | ENABLE                | 200                         | ENABLE               | 0                    |                   |                    |                    | SHARE | 大区共享         | 150                           | 0                       | NORMAL                 | HE       | 盒        |
    And [创建订单提交校验16]当前存在如下的商品一级商允销信息
      | customerSapCode   | type                 | customerName      | permittedSaleQuantityCustomer | hasSaleQuantityCustomer | customerEffectiveState |
      | <customerSapCode> | FIRST_LEVEL_CUSTOMER | 天津市海王星辰健康药房连锁有限公司 | 50                            | 48                      | NORMAL                 |
    Then [创建订单提交校验16]创建订单应校验失败且提示错误信息
      | orderWideDiscountSubject | itemSapCode | needShip | unitPrice | quantity | preDiscount | calculationValue | boxRatio | totalInline | preDiscountTotal | orderDiscountTotal | realInline | remark     |
      | PT00                     | 10000841    | 1        | 10        | 30       | 2           | 8                | 1        | 300         | 60               | 13.58              | 226.42     |            |
      | PT04                     | 10000265    | 1        | 6         | 3        | 0           | 0                | 1        | 18          | 0                | 4.42               | 13.58      | Detail_001 |

    Examples: 基础信息
      | saleOrgSapCode | regionCode | customerSapCode | regionName | customerName | shippingRecipient | shippingTelephone | shippingAddress |


  @orderCommitVerify17
  Scenario Outline: [场景17]创建订单时，销售组织和客户关系未关联，提交订单校验提示拦截，提示错误信息
  "[主要测试点]
  1. 有预折
  2. 有整单折扣
  3. 销售组织和客户关系未关联
  4. 创建订单失败"
    Given [创建订单提交校验17]当前存在如下客户信息
      | saleOrgSapCode   | regionCode   | customerSapCode   | regionName   | customerName   | shippingAddress   | shippingRecipient   | shippingTelephone   |
      | <saleOrgSapCode> | <regionCode> | <customerSapCode> | <regionName> | <customerName> | <shippingAddress> | <shippingRecipient> | <shippingTelephone> |
    When [创建订单提交校验17]当前存在如下的预折商品信息
      | itemSapCode | currentPrice | calculationType  | calculationValue | preType               | effectiveState | distributionChannelCode | distributionChannelName |
      | 10000841    | 10           | DISCOUNTED_PRICE | 8                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000265    | 6            | DISCOUNTED_PRICE | 5                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000289    | 5            | DISCOUNTED_PRICE | 4.5              | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000556    | 30           | DISCOUNTED_PRICE | 27               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000456    | 20           | DISCOUNTED_PRICE | 18               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000835    | 40           | DISCOUNTED_PRICE | 32               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000633    | 50           | DISCOUNTED_PRICE | 40               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000478    | 17           | DISCOUNTED_PRICE | 13               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
    And [创建订单提交校验17]当前存在如下的预折折扣信息
      | itemSapCode | calculationValue | effectiveState |
      | 10000841    | 8                | ENABLE         |
      | 10000456    | 15               | ENABLE         |
      | 10000556    | 27               | ENABLE         |
      | 10000265    | 5                | DISABLE        |
    And [创建订单提交校验17]当前存在如下的整单折扣申请单信息
      | projectType | module |
      | PT04        | KM03   |
    And [创建订单提交校验17]当前存在如下的整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 10000265    | 100      | 盒     |
      | 10000289    | 100      | 盒     |
    And [创建订单提交校验17]当前存在如下的整单折扣分配计划信息
    And [创建订单提交校验17]当前存在如下的整单折扣分配计划详细信息
      | originalItemSapCode | originalDistributionChannelCode | giftItemSapCode | giftDistributionChannelCode | allocationQuantity | originalQuantity | giftQuantity | initialBonusQuantity | needShip | invalidStatus | unit | originalDistributionChannelName | giftDistributionChannelName | remark     |
      |                     |                                 | 10000265        | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | Detail_001 |
    And [创建订单提交校验17]添加商品
      | itemSapCode | quantity |
      | 10000841    | 30       |
    And [创建订单提交校验17]选择项目
      | itemSapCode | quantity |
      | 10000265    | 3        |
    And [创建订单提交校验17]当前存在如下的商品禁销信息
      | itemSapCode | saleOrgSapCode   | regionCode | saleOrgEffectiveState | permittedSaleQuantityRegion | regionEffectiveState | hasSaleQuantityUnits | customerEntZtCode | customerEntSapCode | customerEntCrmCode | type  | customerName | permittedSaleQuantityCustomer | hasSaleQuantityCustomer | customerEffectiveState | unitCode | unitName |
      | 10000265    | <saleOrgSapCode> | 340000     | ENABLE                | 200                         | ENABLE               | 0                    |                   |                    |                    | SHARE | 大区共享         | 150                           | 0                       | NORMAL                 | HE       | 盒        |
    And [创建订单提交校验17]当前存在如下的商品一级商允销信息
      | customerSapCode   | type                 | customerName      | permittedSaleQuantityCustomer | hasSaleQuantityCustomer | customerEffectiveState |
      | <customerSapCode> | FIRST_LEVEL_CUSTOMER | 天津市海王星辰健康药房连锁有限公司 | 50                            | 0                       | NORMAL                 |
    Then [创建订单提交校验17]创建订单应校验失败且提示错误信息
      | orderWideDiscountSubject | itemSapCode | needShip | unitPrice | quantity | preDiscount | calculationValue | boxRatio | totalInline | preDiscountTotal | orderDiscountTotal | realInline | remark     |
      | PT00                     | 10000841    | 1        | 10        | 30       | 2           | 8                | 1        | 300         | 60               | 13.58              | 226.42     |            |
      | PT04                     | 10000265    | 1        | 6         | 3        | 0           | 0                | 1        | 18          | 0                | 4.42               | 13.58      | Detail_001 |

    Examples: 基础信息
      | saleOrgSapCode | regionCode | customerSapCode | regionName | customerName | shippingRecipient | shippingTelephone | shippingAddress |


  @orderCommitVerify18
  Scenario Outline: [场景18]创建订单时，整件发货，基本计价单位=计价单位，件装比为非正整数（含小数），提交订单校验提示拦截，提示错误信息
  "[主要测试点]
  1. 有预折
  2. 无整单折扣
  3. 整件发货，基本计价单位=计价单位，件装比为非正整数（含小数）
  4. 创建订单失败"
    Given [创建订单提交校验18]当前存在如下客户信息
      | saleOrgSapCode   | regionCode   | customerSapCode   | regionName   | customerName   | shippingAddress   | shippingRecipient   | shippingTelephone   |
      | <saleOrgSapCode> | <regionCode> | <customerSapCode> | <regionName> | <customerName> | <shippingAddress> | <shippingRecipient> | <shippingTelephone> |
    When [创建订单提交校验18]当前存在如下的预折商品信息
      | itemSapCode | currentPrice | calculationType  | calculationValue | preType               | effectiveState | distributionChannelCode | distributionChannelName |
      | 10000841    | 10           | DISCOUNTED_PRICE | 8                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000265    | 6            | DISCOUNTED_PRICE | 5                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000289    | 5            | DISCOUNTED_PRICE | 4.5              | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000556    | 30           | DISCOUNTED_PRICE | 27               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000456    | 20           | DISCOUNTED_PRICE | 18               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000835    | 40           | DISCOUNTED_PRICE | 32               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000633    | 50           | DISCOUNTED_PRICE | 40               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000478    | 17           | DISCOUNTED_PRICE | 13               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
    And [创建订单提交校验18]当前存在如下的预折折扣信息
      | itemSapCode | calculationValue | effectiveState |
      | 10000841    | 8                | ENABLE         |
      | 10000456    | 15               | ENABLE         |
      | 10000556    | 27               | ENABLE         |
      | 10000265    | 5                | DISABLE        |
    And [创建订单提交校验18]当前存在如下的整单折扣申请单信息
      | projectType | module |
      | PT04        | KM03   |
    And [创建订单提交校验18]当前存在如下的整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 10000265    | 100      | 盒     |
      | 10000289    | 100      | 盒     |
    And [创建订单提交校验18]当前存在如下的整单折扣分配计划信息
    And [创建订单提交校验18]当前存在如下的整单折扣分配计划详细信息
      | originalItemSapCode | originalDistributionChannelCode | giftItemSapCode | giftDistributionChannelCode | allocationQuantity | originalQuantity | giftQuantity | initialBonusQuantity | needShip | invalidStatus | unit | originalDistributionChannelName | giftDistributionChannelName | remark     |
      |                     |                                 | 10000265        | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | Detail_001 |
    And [创建订单提交校验18]添加商品
      | itemSapCode | quantity |
      | 10000841    | 360      |
    And [创建订单提交校验18]选择项目
      | itemSapCode | quantity |
      | 10000265    | 3        |
    Then [创建订单提交校验18]创建订单应校验失败且提示错误信息
      | orderWideDiscountSubject | itemSapCode | needShip | unitPrice | quantity | preDiscount | calculationValue | boxRatio | totalInline | preDiscountTotal | orderDiscountTotal | realInline | remark |
      | PT00                     | 10000841    | 1        | 10        | 2        | 8           | 1                | 240      | 0           | 0                | 0                  | 0          |        |

    Examples: 基础信息
      | saleOrgSapCode | regionCode | customerSapCode | regionName | customerName | shippingRecipient | shippingTelephone | shippingAddress |


  @orderCommitVerify19
  Scenario Outline: [场景19]创建订单时，整件发货，基本计价单位!=计价单位，件装比为非正整数（含小数），提交订单校验提示拦截，提示错误信息
  "[主要测试点]
  1. 无预折
  2. 无整单折扣
  3. 整件发货，基本计价单位<>计价单位，件装比为非正整数（含小数）
  4. 创建订单失败"
    Given [创建订单提交校验19]当前存在如下客户信息
      | saleOrgSapCode   | regionCode   | customerSapCode   | regionName   | customerName   | shippingAddress   | shippingRecipient   | shippingTelephone   |
      | <saleOrgSapCode> | <regionCode> | <customerSapCode> | <regionName> | <customerName> | <shippingAddress> | <shippingRecipient> | <shippingTelephone> |
    When [创建订单提交校验19]当前存在如下的预折商品信息
      | itemSapCode | currentPrice | calculationType  | calculationValue | preType               | effectiveState | distributionChannelCode | distributionChannelName |
      | 10000841    | 10           | DISCOUNTED_PRICE | 8                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000265    | 6            | DISCOUNTED_PRICE | 5                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000289    | 5            | DISCOUNTED_PRICE | 4.5              | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000556    | 30           | DISCOUNTED_PRICE | 27               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000456    | 20           | DISCOUNTED_PRICE | 18               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000835    | 40           | DISCOUNTED_PRICE | 32               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000633    | 50           | DISCOUNTED_PRICE | 40               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000478    | 17           | DISCOUNTED_PRICE | 13               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
    And [创建订单提交校验19]当前存在如下的预折折扣信息
      | itemSapCode | calculationValue | effectiveState |
      | 10000841    | 8                | DISABLE        |
      | 10000456    | 15               | ENABLE         |
      | 10000556    | 27               | ENABLE         |
      | 10000265    | 5                | DISABLE        |
    And [创建订单提交校验19]当前存在如下的整单折扣申请单信息
      | projectType | module |
      | PT04        | KM03   |
    And [创建订单提交校验19]当前存在如下的整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 10000265    | 100      | 盒     |
      | 10000289    | 100      | 盒     |
    And [创建订单提交校验19]当前存在如下的整单折扣分配计划信息
    And [创建订单提交校验19]当前存在如下的整单折扣分配计划详细信息
      | originalItemSapCode | originalDistributionChannelCode | giftItemSapCode | giftDistributionChannelCode | allocationQuantity | originalQuantity | giftQuantity | initialBonusQuantity | needShip | invalidStatus | unit | originalDistributionChannelName | giftDistributionChannelName | remark     |
      |                     |                                 | 10000265        | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | Detail_001 |
    And [创建订单提交校验19]添加商品
      | itemSapCode | quantity |
      | 10000841    | 30       |
    And [创建订单提交校验19]选择项目
      | itemSapCode | quantity |
      | 10000265    | 3        |
    And [创建订单提交校验19]修改商品计价单位
      | itemSapCode | unit | unitDesc |
      | 10000841    | PC1  | 支        |
    Then [创建订单提交校验19]创建订单应校验失败且提示错误信息
      | orderWideDiscountSubject | itemSapCode | needShip | unitPrice | quantity | preDiscount | calculationValue | boxRatio | totalInline | preDiscountTotal | orderDiscountTotal | realInline | remark |
      | PT00                     | 10000841    | 1        | 10        | 240      | 0           | 0                | 240      | 2400        | 0                | 0                  | 2400       |        |

    Examples: 基础信息
      | saleOrgSapCode | regionCode | customerSapCode | regionName | customerName | shippingRecipient | shippingTelephone | shippingAddress |


  @orderCommitVerify20
  Scenario Outline: [场景20]创建订单时，非整件发货，且有中包装要求，不符合中包装的整数倍（含小数），提交订单校验提示拦截，提示错误信息
  "[主要测试点]
  1. 无预折
  2. 无整单折扣
  3. 原品-非整件发货，且有中包装要求，不符合中包装的整数倍（含小数）
  4. 创建订单失败"
    Given [创建订单提交校验20]当前存在如下客户信息
      | saleOrgSapCode   | regionCode   | customerSapCode   | regionName   | customerName   | shippingAddress   | shippingRecipient   | shippingTelephone   |
      | <saleOrgSapCode> | <regionCode> | <customerSapCode> | <regionName> | <customerName> | <shippingAddress> | <shippingRecipient> | <shippingTelephone> |
    When [创建订单提交校验20]当前存在如下的预折商品信息
      | itemSapCode | currentPrice | calculationType  | calculationValue | preType               | effectiveState | distributionChannelCode | distributionChannelName |
      | 10000841    | 10           | DISCOUNTED_PRICE | 8                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000265    | 6            | DISCOUNTED_PRICE | 5                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000289    | 5            | DISCOUNTED_PRICE | 4.5              | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000556    | 30           | DISCOUNTED_PRICE | 27               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000456    | 20           | DISCOUNTED_PRICE | 18               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000835    | 40           | DISCOUNTED_PRICE | 32               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000633    | 50           | DISCOUNTED_PRICE | 40               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000478    | 17           | DISCOUNTED_PRICE | 13               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
    And [创建订单提交校验20]当前存在如下的预折折扣信息
      | itemSapCode | calculationValue | effectiveState |
      | 10000841    | 8                | DISABLE        |
      | 10000456    | 15               | ENABLE         |
      | 10000556    | 27               | ENABLE         |
      | 10000265    | 5                | DISABLE        |
    And [创建订单提交校验20]当前存在如下的整单折扣申请单信息
      | projectType | module |
      | PT04        | KM03   |
    And [创建订单提交校验20]当前存在如下的整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 10000265    | 100      | 盒     |
      | 10000289    | 100      | 盒     |
    And [创建订单提交校验20]当前存在如下的整单折扣分配计划信息
    And [创建订单提交校验20]当前存在如下的整单折扣分配计划详细信息
      | originalItemSapCode | originalDistributionChannelCode | giftItemSapCode | giftDistributionChannelCode | allocationQuantity | originalQuantity | giftQuantity | initialBonusQuantity | needShip | invalidStatus | unit | originalDistributionChannelName | giftDistributionChannelName | remark     |
      |                     |                                 | 10000265        | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | Detail_001 |
    And [创建订单提交校验20]添加商品
      | itemSapCode | quantity |
      | 10000841    | 30       |
    And [创建订单提交校验20]选择项目
      | itemSapCode | quantity |
      | 10000265    | 3        |
    And [创建订单提交校验20]设置商品中包装
      | itemSapCode | mediumPackagingSpecification | name   | quantity |
      | 10000841    | 10盒/包                        | 醒脑静注射液 | 10       |
    Then [创建订单提交校验20]创建订单应校验失败且提示错误信息
      | orderWideDiscountSubject | itemSapCode | needShip | unitPrice | quantity | preDiscount | calculationValue | boxRatio | totalInline | preDiscountTotal | orderDiscountTotal | realInline | remark |
      | PT00                     | 10000841    | 1        | 10        | 2        | 8           | 1                | 240      | 0           | 0                | 0                  | 0          |        |

    Examples: 基础信息
      | saleOrgSapCode | regionCode | customerSapCode | regionName | customerName | shippingRecipient | shippingTelephone | shippingAddress |


  @orderCommitVerify21
  Scenario Outline: [场景21]创建订单时，超过对应项目的待分配数量（买赠比计算值 ＞ 待分配数量），提交订单校验提示拦截，提示错误信息
  "[主要测试点]
  1. 无预折
  2. 有整单折扣
  3. 超过对应项目的待分配数量（买赠比计算值 ＞ 待分配数量）
  4. 创建订单失败"
    Given [创建订单提交校验21]当前存在如下客户信息
      | saleOrgSapCode   | regionCode   | customerSapCode   | regionName   | customerName   | shippingAddress   | shippingRecipient   | shippingTelephone   |
      | <saleOrgSapCode> | <regionCode> | <customerSapCode> | <regionName> | <customerName> | <shippingAddress> | <shippingRecipient> | <shippingTelephone> |
    When [创建订单提交校验21]当前存在如下的预折商品信息
      | itemSapCode | currentPrice | calculationType  | calculationValue | preType               | effectiveState | distributionChannelCode | distributionChannelName |
      | 10000841    | 10           | DISCOUNTED_PRICE | 8                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000265    | 6            | DISCOUNTED_PRICE | 5                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000289    | 5            | DISCOUNTED_PRICE | 4.5              | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000556    | 30           | DISCOUNTED_PRICE | 27               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000456    | 20           | DISCOUNTED_PRICE | 18               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000835    | 40           | DISCOUNTED_PRICE | 32               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000633    | 50           | DISCOUNTED_PRICE | 40               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000478    | 17           | DISCOUNTED_PRICE | 13               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
    And [创建订单提交校验21]当前存在如下的预折折扣信息
      | itemSapCode | calculationValue | effectiveState |
      | 10000841    | 8                | DISABLE        |
      | 10000456    | 15               | ENABLE         |
      | 10000556    | 27               | ENABLE         |
      | 10000265    | 5                | DISABLE        |
    And [创建订单提交校验21]当前存在如下的整单折扣申请单信息
      | projectType | module |
      | PT01        | KM03   |
    And [创建订单提交校验21]当前存在如下的整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 10000841    | 1000     | 盒     |
      | 10000456    | 1000     | 盒     |
    And [创建订单提交校验21]当前存在如下的整单折扣分配计划信息
    And [创建订单提交校验21]当前存在如下的整单折扣分配计划详细信息
      | originalItemSapCode | originalDistributionChannelCode | giftItemSapCode | giftDistributionChannelCode | allocationQuantity | originalQuantity | giftQuantity | initialBonusQuantity | needShip | invalidStatus | unit | originalDistributionChannelName | giftDistributionChannelName | remark     |
      | 10000841            | 22                              | 10000841        | 22                          | 100                | 5                | 1            | 5                    | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | Detail_001 |
      | 10000456            | 22                              | 10000456        | 22                          | 100                | 2                | 1            | 5                    | 0        | NORMAL        | 盒    |                                 | 分销-非医疗                      | Detail_002 |
    And [创建订单提交校验21]添加商品
      | itemSapCode | quantity |
      | 10000841    | 30       |
    And [创建订单提交校验21]选择项目
      | itemSapCode | quantity |
      | 10000265    | 3        |
    Then [创建订单提交校验21]创建订单应校验失败且提示错误信息
      | orderWideDiscountSubject | itemSapCode | needShip | unitPrice | quantity | preDiscount | calculationValue | boxRatio | totalInline | preDiscountTotal | orderDiscountTotal | realInline | remark     |
      | PT00                     | 10000841    | 1        | 10        | 1000     | 0           | 8                | 1        | 10000       | 0                | 1666.67            | 8333.33    |            |
      | PT01                     | 10000841    | 1        | 10        | 200      | 0           | 0                | 1        | 2000        | 0                | 333.33             | 1666.67    | Detail_001 |

    Examples: 基础信息
      | saleOrgSapCode | regionCode | customerSapCode | regionName | customerName | shippingRecipient | shippingTelephone | shippingAddress |


  @orderCommitVerify22
  Scenario Outline: [场景22] 创建订单时，超过对应项目买赠比计算出来的赠品数量的最小值，提交订单校验提示拦截，提示错误信息（废弃：前端控制无法输入，后端未做校验）
  "[主要测试点]
  1. 无预折
  2. 有整单折扣
  3. 超过对应项目买赠比计算出来的赠品数量的最小值（买赠比计算值 ≤ 待分配数量）
  4. 创建订单失败"
    Given [创建订单提交校验22]当前存在如下客户信息
      | saleOrgSapCode   | regionCode   | customerSapCode   | regionName   | customerName   | shippingAddress   | shippingRecipient   | shippingTelephone   |
      | <saleOrgSapCode> | <regionCode> | <customerSapCode> | <regionName> | <customerName> | <shippingAddress> | <shippingRecipient> | <shippingTelephone> |
    When [创建订单提交校验22]当前存在如下的预折商品信息
      | itemSapCode | currentPrice | calculationType  | calculationValue | preType               | effectiveState | distributionChannelCode | distributionChannelName |
      | 10000841    | 10           | DISCOUNTED_PRICE | 8                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000265    | 6            | DISCOUNTED_PRICE | 5                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000289    | 5            | DISCOUNTED_PRICE | 4.5              | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000556    | 30           | DISCOUNTED_PRICE | 27               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000456    | 20           | DISCOUNTED_PRICE | 18               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000835    | 40           | DISCOUNTED_PRICE | 32               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000633    | 50           | DISCOUNTED_PRICE | 40               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000478    | 17           | DISCOUNTED_PRICE | 13               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
    And [创建订单提交校验22]当前存在如下的预折折扣信息
      | itemSapCode | calculationValue | effectiveState |
      | 10000841    | 8                | DISABLE        |
      | 10000456    | 15               | ENABLE         |
      | 10000556    | 27               | ENABLE         |
      | 10000265    | 5                | DISABLE        |
    And [创建订单提交校验22]当前存在如下的整单折扣申请单信息
      | projectType | module |
      | PT01        | KM03   |
    And [创建订单提交校验22]当前存在如下的整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 10000841    | 1000     | 盒     |
      | 10000456    | 1000     | 盒     |
    And [创建订单提交校验22]当前存在如下的整单折扣分配计划信息
    And [创建订单提交校验22]当前存在如下的整单折扣分配计划详细信息
      | originalItemSapCode | originalDistributionChannelCode | giftItemSapCode | giftDistributionChannelCode | allocationQuantity | originalQuantity | giftQuantity | initialBonusQuantity | needShip | invalidStatus | unit | originalDistributionChannelName | giftDistributionChannelName | remark     |
      | 10000841            | 22                              | 10000841        | 22                          | 100                | 5                | 1            | 5                    | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | Detail_001 |
      | 10000456            | 22                              | 10000456        | 22                          | 100                | 2                | 1            | 5                    | 0        | NORMAL        | 盒    |                                 | 分销-非医疗                      | Detail_002 |
    And [创建订单提交校验22]添加商品
      | itemSapCode | quantity |
      | 10000841    | 30       |
    And [创建订单提交校验22]选择项目
      | itemSapCode | quantity |
      | 10000265    | 3        |
    Then [创建订单提交校验22]创建订单应校验失败且提示错误信息
      | orderWideDiscountSubject | itemSapCode | needShip | unitPrice | quantity | preDiscount | calculationValue | boxRatio | totalInline | preDiscountTotal | orderDiscountTotal | realInline | remark     |
      | PT00                     | 10000841    | 1        | 10        | 100      | 0           | 8                | 1        | 1000        | 0                | 200                | 800        |            |
      | PT01                     | 10000841    | 1        | 10        | 25       | 0           | 0                | 1        | 250         | 0                | 50                 | 200        | Detail_001 |

    Examples: 基础信息
      | saleOrgSapCode | regionCode | customerSapCode | regionName | customerName | shippingRecipient | shippingTelephone | shippingAddress |


  @orderCommitVerify23
  Scenario Outline: [场景23]创建订单时，项目类商品数量超过待分配数量（提交前：待分配数量变化），提交订单校验提示拦截，提示错误信息
  "[主要测试点]
  1. 无预折
  2. 有整单折扣
  3. 项目类商品数量超过待分配数量（提交前：待分配数量变化）
  4. 创建订单失败"
    Given [创建订单提交校验23]当前存在如下客户信息
      | saleOrgSapCode   | regionCode   | customerSapCode   | regionName   | customerName   | shippingAddress   | shippingRecipient   | shippingTelephone   |
      | <saleOrgSapCode> | <regionCode> | <customerSapCode> | <regionName> | <customerName> | <shippingAddress> | <shippingRecipient> | <shippingTelephone> |
    When [创建订单提交校验23]当前存在如下的预折商品信息
      | itemSapCode | currentPrice | calculationType  | calculationValue | preType               | effectiveState | distributionChannelCode | distributionChannelName |
      | 10000841    | 10           | DISCOUNTED_PRICE | 8                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000265    | 6            | DISCOUNTED_PRICE | 5                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000289    | 5            | DISCOUNTED_PRICE | 4.5              | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000556    | 30           | DISCOUNTED_PRICE | 27               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000456    | 20           | DISCOUNTED_PRICE | 18               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000835    | 40           | DISCOUNTED_PRICE | 32               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000633    | 50           | DISCOUNTED_PRICE | 40               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000478    | 17           | DISCOUNTED_PRICE | 13               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
    And [创建订单提交校验23]当前存在如下的预折折扣信息
      | itemSapCode | calculationValue | effectiveState |
      | 10000841    | 8                | DISABLE        |
      | 10000456    | 15               | ENABLE         |
      | 10000556    | 27               | ENABLE         |
      | 10000265    | 5                | DISABLE        |
    And [创建订单提交校验23]当前存在如下的整单折扣申请单信息
      | projectType | module |
      | PT04        | KM03   |
    And [创建订单提交校验23]当前存在如下的整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 10000265    | 100      | 盒     |
      | 10000289    | 100      | 盒     |
    And [创建订单提交校验23]当前存在如下的整单折扣分配计划信息
    And [创建订单提交校验23]当前存在如下的整单折扣分配计划详细信息
      | originalItemSapCode | originalDistributionChannelCode | giftItemSapCode | giftDistributionChannelCode | allocationQuantity | originalQuantity | giftQuantity | initialBonusQuantity | needShip | invalidStatus | unit | originalDistributionChannelName | giftDistributionChannelName | remark     |
      |                     |                                 | 10000265        | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | Detail_001 |
    And [创建订单提交校验23]添加商品
      | itemSapCode | quantity |
      | 10000841    | 30       |
    And [创建订单提交校验23]选择项目
      | itemSapCode | quantity |
      | 10000265    | 3        |
    And [创建订单提交校验23]赠品待分配数量变化
      | itemSapCode | usedQuantity |
      | 10000265    | 98           |
    Then [创建订单提交校验23]创建订单应校验失败且提示错误信息
      | orderWideDiscountSubject | itemSapCode | needShip | unitPrice | quantity | preDiscount | calculationValue | boxRatio | totalInline | preDiscountTotal | orderDiscountTotal | realInline | remark     |
      | PT00                     | 10000841    | 1        | 10        | 30       | 0           | 8                | 1        | 300         | 0                | 22.22              | 277.78     |            |
      | PT04                     | 10000265    | 1        | 6         | 4        | 0           | 0                | 1        | 24          | 0                | 1.78               | 22.22      | Detail_001 |

    Examples: 基础信息
      | saleOrgSapCode | regionCode | customerSapCode | regionName | customerName | shippingRecipient | shippingTelephone | shippingAddress |


  @orderCommitVerify24
  Scenario Outline: [场景24]创建订单时，项目类商品数量超过待分配数量（提交前：计划状态非执行中状态），提交订单校验提示拦截，提示错误信息
  "[主要测试点]
  1. 无预折
  2. 有整单折扣
  3. 项目类商品数量超过待分配数量（提交前：商品对应的分配计划状态变更为作废状态）
  4. 创建订单失败"
    Given [创建订单提交校验24]当前存在如下客户信息
      | saleOrgSapCode   | regionCode   | customerSapCode   | regionName   | customerName   | shippingAddress   | shippingRecipient   | shippingTelephone   |
      | <saleOrgSapCode> | <regionCode> | <customerSapCode> | <regionName> | <customerName> | <shippingAddress> | <shippingRecipient> | <shippingTelephone> |
    When [创建订单提交校验24]当前存在如下的预折商品信息
      | itemSapCode | currentPrice | calculationType  | calculationValue | preType               | effectiveState | distributionChannelCode | distributionChannelName |
      | 10000841    | 10           | DISCOUNTED_PRICE | 8                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000265    | 6            | DISCOUNTED_PRICE | 5                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000289    | 5            | DISCOUNTED_PRICE | 4.5              | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000556    | 30           | DISCOUNTED_PRICE | 27               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000456    | 20           | DISCOUNTED_PRICE | 18               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000835    | 40           | DISCOUNTED_PRICE | 32               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000633    | 50           | DISCOUNTED_PRICE | 40               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000478    | 17           | DISCOUNTED_PRICE | 13               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
    And [创建订单提交校验24]当前存在如下的预折折扣信息
      | itemSapCode | calculationValue | effectiveState |
      | 10000841    | 8                | DISABLE        |
      | 10000456    | 15               | ENABLE         |
      | 10000556    | 27               | ENABLE         |
      | 10000265    | 5                | DISABLE        |
    And [创建订单提交校验24]当前存在如下的整单折扣申请单信息
      | projectType | module |
      | PT04        | KM03   |
    And [创建订单提交校验24]当前存在如下的整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 10000265    | 100      | 盒     |
      | 10000289    | 100      | 盒     |
    And [创建订单提交校验24]当前存在如下的整单折扣分配计划信息
    And [创建订单提交校验24]当前存在如下的整单折扣分配计划详细信息
      | originalItemSapCode | originalDistributionChannelCode | giftItemSapCode | giftDistributionChannelCode | allocationQuantity | originalQuantity | giftQuantity | initialBonusQuantity | needShip | invalidStatus | unit | originalDistributionChannelName | giftDistributionChannelName | remark     |
      |                     |                                 | 10000265        | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | Detail_001 |
    And [创建订单提交校验24]添加商品
      | itemSapCode | quantity |
      | 10000841    | 30       |
    And [创建订单提交校验24]选择项目
      | itemSapCode | quantity |
      | 10000265    | 3        |
    Then [创建订单提交校验24]创建订单应校验失败且提示错误信息
      | orderWideDiscountSubject | itemSapCode | needShip | unitPrice | quantity | preDiscount | calculationValue | boxRatio | totalInline | preDiscountTotal | orderDiscountTotal | realInline | remark     |
      | PT00                     | 10000841    | 1        | 10        | 30       | 2           | 8                | 1        | 300         | 60               | 13.58              | 226.42     |            |
      | PT04                     | 10000265    | 1        | 6         | 3        | 0           | 0                | 1        | 18          | 0                | 4.42               | 13.58      | Detail_001 |

    Examples: 基础信息
      | saleOrgSapCode | regionCode | customerSapCode | regionName | customerName | shippingRecipient | shippingTelephone | shippingAddress |


  @orderCommitVerify25
  Scenario Outline: [场景25]创建订单时，整单折扣总额超过大区月度折让可用余额，提交订单校验提示拦截，提示错误信息
  "[主要测试点]
  1. 有预折
  2. 有整单折扣
  3. 整单折扣总额超过大区月度折让可用余额
  4. 创建订单失败"
    Given [创建订单提交校验25]当前存在如下客户信息
      | saleOrgSapCode   | regionCode   | customerSapCode   | regionName   | customerName   | shippingAddress   | shippingRecipient   | shippingTelephone   |
      | <saleOrgSapCode> | <regionCode> | <customerSapCode> | <regionName> | <customerName> | <shippingAddress> | <shippingRecipient> | <shippingTelephone> |
    When [创建订单提交校验25]当前存在如下的预折商品信息
      | itemSapCode | currentPrice | calculationType  | calculationValue | preType               | effectiveState | distributionChannelCode | distributionChannelName |
      | 10000841    | 10           | DISCOUNTED_PRICE | 8                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000265    | 6            | DISCOUNTED_PRICE | 5                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000289    | 5            | DISCOUNTED_PRICE | 4.5              | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000556    | 30           | DISCOUNTED_PRICE | 27               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000456    | 20           | DISCOUNTED_PRICE | 18               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000835    | 40           | DISCOUNTED_PRICE | 32               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000633    | 50           | DISCOUNTED_PRICE | 40               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000478    | 17           | DISCOUNTED_PRICE | 13               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
    And [创建订单提交校验25]当前存在如下的预折折扣信息
      | itemSapCode | calculationValue | effectiveState |
      | 10000841    | 8                | ENABLE         |
      | 10000456    | 15               | ENABLE         |
      | 10000556    | 27               | ENABLE         |
      | 10000265    | 5                | DISABLE        |
    And [创建订单提交校验25]当前存在如下的整单折扣申请单信息
      | projectType | module |
      | PT04        | KM03   |
    And [创建订单提交校验25]当前存在如下的整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 10000265    | 100      | 盒     |
      | 10000289    | 100      | 盒     |
    And [创建订单提交校验25]当前存在如下的整单折扣分配计划信息
    And [创建订单提交校验25]当前存在如下的整单折扣分配计划详细信息
      | originalItemSapCode | originalDistributionChannelCode | giftItemSapCode | giftDistributionChannelCode | allocationQuantity | originalQuantity | giftQuantity | initialBonusQuantity | needShip | invalidStatus | unit | originalDistributionChannelName | giftDistributionChannelName | remark     |
      |                     |                                 | 10000265        | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | Detail_001 |
    And [创建订单提交校验25]添加商品
      | itemSapCode | quantity |
      | 10000841    | 30       |
    And [创建订单提交校验25]选择项目
      | itemSapCode | quantity |
      | 10000265    | 3        |
    And [创建订单提交校验25]修改大区月度折让可用余额
    Then [创建订单提交校验25]创建订单应校验失败且提示错误信息
      | orderWideDiscountSubject | itemSapCode | needShip | unitPrice | quantity | preDiscount | calculationValue | boxRatio | totalInline | preDiscountTotal | orderDiscountTotal | realInline | remark     |
      | PT00                     | 10000841    | 1        | 10        | 30       | 2           | 8                | 1        | 300         | 60               | 13.58              | 226.42     |            |
      | PT04                     | 10000265    | 1        | 6         | 3        | 0           | 0                | 1        | 18          | 0                | 4.42               | 13.58      | Detail_001 |

    Examples: 基础信息
      | saleOrgSapCode | regionCode | customerSapCode | regionName | customerName | shippingRecipient | shippingTelephone | shippingAddress |


  @orderCommitVerify26
  Scenario Outline: [场景26]创建订单时，（预折折扣金额+整单折扣金额）/订单行原总价 > 32%，创建订单失败，提示错误信息
  "[主要测试点]
  1. 有预折
  2. 有整单折扣
  3. （预折折扣金额+整单折扣金额）/订单行原总价 > 32%
  4. 创建订单失败"
    Given [创建订单提交校验26]当前存在如下客户信息
      | saleOrgSapCode   | regionCode   | customerSapCode   | regionName   | customerName   | shippingAddress   | shippingRecipient   | shippingTelephone   |
      | <saleOrgSapCode> | <regionCode> | <customerSapCode> | <regionName> | <customerName> | <shippingAddress> | <shippingRecipient> | <shippingTelephone> |
    When [创建订单提交校验26]当前存在如下的预折商品信息
      | itemSapCode | currentPrice | calculationType  | calculationValue | preType               | effectiveState | distributionChannelCode | distributionChannelName |
      | 10000841    | 10           | DISCOUNTED_PRICE | 8                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000265    | 6            | DISCOUNTED_PRICE | 5                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000289    | 5            | DISCOUNTED_PRICE | 4.5              | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000556    | 30           | DISCOUNTED_PRICE | 27               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000456    | 20           | DISCOUNTED_PRICE | 18               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000835    | 40           | DISCOUNTED_PRICE | 32               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000633    | 50           | DISCOUNTED_PRICE | 40               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000478    | 17           | DISCOUNTED_PRICE | 13               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
    And [创建订单提交校验26]当前存在如下的预折折扣信息
      | itemSapCode | calculationValue | effectiveState |
      | 10000841    | 8                | ENABLE         |
      | 10000456    | 15               | ENABLE         |
      | 10000556    | 27               | ENABLE         |
      | 10000265    | 5                | DISABLE        |
    And [创建订单提交校验26]当前存在如下的整单折扣申请单信息
      | projectType | module |
      | PT04        | KM03   |
    And [创建订单提交校验26]当前存在如下的整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 10000265    | 100      | 盒     |
      | 10000289    | 100      | 盒     |
    And [创建订单提交校验26]当前存在如下的整单折扣分配计划信息
    And [创建订单提交校验26]当前存在如下的整单折扣分配计划详细信息
      | originalItemSapCode | originalDistributionChannelCode | giftItemSapCode | giftDistributionChannelCode | allocationQuantity | originalQuantity | giftQuantity | initialBonusQuantity | needShip | invalidStatus | unit | originalDistributionChannelName | giftDistributionChannelName | remark     |
      |                     |                                 | 10000265        | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | Detail_001 |
    And [创建订单提交校验26]添加商品
      | itemSapCode | quantity |
      | 10000841    | 10       |
      | 10000456    | 3        |
      | 10000556    | 10       |
    And [创建订单提交校验26]选择项目
      | itemSapCode | quantity |
      | 10000265    | 24       |
    Then [创建订单提交校验26]创建订单应校验失败且提示错误信息
      | orderWideDiscountSubject | itemSapCode | needShip | unitPrice | quantity | preDiscount | calculationValue | boxRatio | totalInline | preDiscountTotal | orderDiscountTotal | realInline | remark     |
      | PT00                     | 10000841    | 1        | 10        | 10       | 2           | 8                | 1        | 100         | 20               | 14.6               | 65.4       |            |
      | PT00                     | 10000456    | 1        | 20        | 3        | 5           | 15               | 1        | 60          | 15               | 5.76               | 39.24      |            |
      | PT00                     | 10000556    | 1        | 30        | 10       | 3           | 27               | 1        | 300         | 30               | 73.81              | 196.19     |            |
      | PT04                     | 10000265    | 1        | 6         | 24       | 0           | 6                | 1        | 144         | 0                | 49.83              | 94.17      | Detail_001 |

    Examples: 基础信息
      | saleOrgSapCode | regionCode | customerSapCode | regionName | customerName | shippingRecipient | shippingTelephone | shippingAddress |


  @orderCommitVerify27
  Scenario Outline: [场景27]创建订单时，预折折扣小于0，提交订单校验提示拦截，提示错误信息
  "[主要测试点]
  1. 有预折
  2. 无整单折扣
  3. 预折折扣小于0 ：修改商品价格（商品价格变小，造成折后价格大于当前商品单价）
  4. 创建订单失败"
    Given [创建订单提交校验27]当前存在如下客户信息
      | saleOrgSapCode   | regionCode   | customerSapCode   | regionName   | customerName   | shippingAddress   | shippingRecipient   | shippingTelephone   |
      | <saleOrgSapCode> | <regionCode> | <customerSapCode> | <regionName> | <customerName> | <shippingAddress> | <shippingRecipient> | <shippingTelephone> |
    When [创建订单提交校验27]当前存在如下的预折商品信息
      | itemSapCode | currentPrice | calculationType  | calculationValue | preType               | effectiveState | distributionChannelCode | distributionChannelName |
      | 10000841    | 10           | DISCOUNTED_PRICE | 8                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000265    | 6            | DISCOUNTED_PRICE | 5                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000289    | 5            | DISCOUNTED_PRICE | 4.5              | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000556    | 30           | DISCOUNTED_PRICE | 27               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000456    | 20           | DISCOUNTED_PRICE | 18               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000835    | 40           | DISCOUNTED_PRICE | 32               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000633    | 50           | DISCOUNTED_PRICE | 40               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000478    | 17           | DISCOUNTED_PRICE | 13               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
    And [创建订单提交校验27]当前存在如下的预折折扣信息
      | itemSapCode | calculationValue | effectiveState |
      | 10000841    | 8                | ENABLE         |
      | 10000456    | 15               | ENABLE         |
      | 10000556    | 27               | ENABLE         |
      | 10000265    | 5                | DISABLE        |
    And [创建订单提交校验27]当前存在如下的整单折扣申请单信息
      | projectType | module |
      | PT04        | KM03   |
    And [创建订单提交校验27]当前存在如下的整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 10000265    | 100      | 盒     |
      | 10000289    | 100      | 盒     |
    And [创建订单提交校验27]当前存在如下的整单折扣分配计划信息
    And [创建订单提交校验27]当前存在如下的整单折扣分配计划详细信息
      | originalItemSapCode | originalDistributionChannelCode | giftItemSapCode | giftDistributionChannelCode | allocationQuantity | originalQuantity | giftQuantity | initialBonusQuantity | needShip | invalidStatus | unit | originalDistributionChannelName | giftDistributionChannelName | remark     |
      |                     |                                 | 10000265        | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | Detail_001 |
    And [创建订单提交校验27]添加商品
      | itemSapCode | quantity |
      | 10000841    | 30       |
    And [创建订单提交校验27]选择项目
      | itemSapCode | quantity |
      | 10000265    | 3        |
    Then [创建订单提交校验27]创建订单应校验失败且提示错误信息
      | orderWideDiscountSubject | itemSapCode | needShip | unitPrice | quantity | preDiscount | calculationValue | boxRatio | totalInline | preDiscountTotal | orderDiscountTotal | realInline | remark |
      | PT00                     | 10000841    | 1        | 10        | 2        | 8           | 1                | 240      | 0           | 0                | 0                  | 0          |        |

    Examples: 基础信息
      | saleOrgSapCode | regionCode | customerSapCode | regionName | customerName | shippingRecipient | shippingTelephone | shippingAddress |


  @orderCommitVerify28
  Scenario Outline: [场景28]创建订单时，订单行商品销售组织变更，提交订单校验提示拦截，提示错误信息
  "[主要测试点]
  1. 有预折
  2. 有整单折扣
  3. 订单行商品销售组织变更
  4. 创建订单失败"
    Given [创建订单提交校验28]当前存在如下客户信息
      | saleOrgSapCode   | regionCode   | customerSapCode   | regionName   | customerName   | shippingAddress   | shippingRecipient   | shippingTelephone   |
      | <saleOrgSapCode> | <regionCode> | <customerSapCode> | <regionName> | <customerName> | <shippingAddress> | <shippingRecipient> | <shippingTelephone> |
    When [创建订单提交校验28]当前存在如下的预折商品信息
      | itemSapCode | currentPrice | calculationType  | calculationValue | preType               | effectiveState | distributionChannelCode | distributionChannelName |
      | 10000841    | 10           | DISCOUNTED_PRICE | 8                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000265    | 6            | DISCOUNTED_PRICE | 5                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000289    | 5            | DISCOUNTED_PRICE | 4.5              | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000556    | 30           | DISCOUNTED_PRICE | 27               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000456    | 20           | DISCOUNTED_PRICE | 18               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000835    | 40           | DISCOUNTED_PRICE | 32               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000633    | 50           | DISCOUNTED_PRICE | 40               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000478    | 17           | DISCOUNTED_PRICE | 13               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
    And [创建订单提交校验28]当前存在如下的预折折扣信息
      | itemSapCode | calculationValue | effectiveState |
      | 10000841    | 8                | ENABLE         |
      | 10000456    | 15               | ENABLE         |
      | 10000556    | 27               | ENABLE         |
      | 10000265    | 5                | DISABLE        |
    And [创建订单提交校验28]当前存在如下的整单折扣申请单信息
      | projectType | module |
      | PT04        | KM03   |
    And [创建订单提交校验28]当前存在如下的整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 10000265    | 100      | 盒     |
      | 10000289    | 100      | 盒     |
    And [创建订单提交校验28]当前存在如下的整单折扣分配计划信息
    And [创建订单提交校验28]当前存在如下的整单折扣分配计划详细信息
      | originalItemSapCode | originalDistributionChannelCode | giftItemSapCode | giftDistributionChannelCode | allocationQuantity | originalQuantity | giftQuantity | initialBonusQuantity | needShip | invalidStatus | unit | originalDistributionChannelName | giftDistributionChannelName | remark     |
      |                     |                                 | 10000265        | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | Detail_001 |
    And [创建订单提交校验28]添加商品
      | itemSapCode | quantity |
      | 10000841    | 30       |
    And [创建订单提交校验28]选择项目
      | itemSapCode | quantity |
      | 10000265    | 3        |
    And [创建订单提交校验28]商品销售组织变更
      | itemSapCode | saleOrgSapCodeNew |
      | 10000841    | 2810              |
    Then [创建订单提交校验28]创建订单应校验失败且提示错误信息
      | orderWideDiscountSubject | itemSapCode | needShip | unitPrice | quantity | preDiscount | calculationValue | boxRatio | totalInline | preDiscountTotal | orderDiscountTotal | realInline | remark     |
      | PT00                     | 10000841    | 1        | 10        | 30       | 2           | 8                | 1        | 300         | 60               | 13.58              | 226.42     |            |
      | PT04                     | 10000265    | 1        | 6         | 3        | 0           | 0                | 1        | 18          | 0                | 4.42               | 13.58      | Detail_001 |

    Examples: 基础信息
      | saleOrgSapCode | regionCode | customerSapCode | regionName | customerName | shippingRecipient | shippingTelephone | shippingAddress |


  @orderCommitVerify29
  Scenario Outline: [场景29]创建订单时，订单行商品下架，提交订单校验提示拦截，提示错误信息
  "[主要测试点]
  1. 有预折
  2. 有整单折扣
  3. 订单行商品下架
  4. 创建订单失败"
    Given [创建订单提交校验29]当前存在如下客户信息
      | saleOrgSapCode   | regionCode   | customerSapCode   | regionName   | customerName   | shippingAddress   | shippingRecipient   | shippingTelephone   |
      | <saleOrgSapCode> | <regionCode> | <customerSapCode> | <regionName> | <customerName> | <shippingAddress> | <shippingRecipient> | <shippingTelephone> |
    When [创建订单提交校验29]当前存在如下的预折商品信息
      | itemSapCode | currentPrice | calculationType  | calculationValue | preType               | effectiveState | distributionChannelCode | distributionChannelName |
      | 10000841    | 10           | DISCOUNTED_PRICE | 8                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000265    | 6            | DISCOUNTED_PRICE | 5                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000289    | 5            | DISCOUNTED_PRICE | 4.5              | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000556    | 30           | DISCOUNTED_PRICE | 27               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000456    | 20           | DISCOUNTED_PRICE | 18               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000835    | 40           | DISCOUNTED_PRICE | 32               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000633    | 50           | DISCOUNTED_PRICE | 40               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000478    | 17           | DISCOUNTED_PRICE | 13               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
    And [创建订单提交校验29]当前存在如下的预折折扣信息
      | itemSapCode | calculationValue | effectiveState |
      | 10000841    | 8                | ENABLE         |
      | 10000456    | 15               | ENABLE         |
      | 10000556    | 27               | ENABLE         |
      | 10000265    | 5                | DISABLE        |
    And [创建订单提交校验29]当前存在如下的整单折扣申请单信息
      | projectType | module |
      | PT04        | KM03   |
    And [创建订单提交校验29]当前存在如下的整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 10000265    | 100      | 盒     |
      | 10000289    | 100      | 盒     |
    And [创建订单提交校验29]当前存在如下的整单折扣分配计划信息
    And [创建订单提交校验29]当前存在如下的整单折扣分配计划详细信息
      | originalItemSapCode | originalDistributionChannelCode | giftItemSapCode | giftDistributionChannelCode | allocationQuantity | originalQuantity | giftQuantity | initialBonusQuantity | needShip | invalidStatus | unit | originalDistributionChannelName | giftDistributionChannelName | remark     |
      |                     |                                 | 10000265        | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | Detail_001 |
    And [创建订单提交校验29]添加商品
      | itemSapCode | quantity |
      | 10000841    | 30       |
    And [创建订单提交校验29]选择项目
      | itemSapCode | quantity |
      | 10000265    | 3        |
    And [创建订单提交校验29]订单行商品下架
      | itemSapCode | isActive |
      | 10000841    | 0        |
    Then [创建订单提交校验29]创建订单应校验失败且提示错误信息
      | orderWideDiscountSubject | itemSapCode | needShip | unitPrice | quantity | preDiscount | calculationValue | boxRatio | totalInline | preDiscountTotal | orderDiscountTotal | realInline | remark     |
      | PT00                     | 10000841    | 1        | 10        | 30       | 2           | 8                | 1        | 300         | 60               | 13.58              | 226.42     |            |
      | PT04                     | 10000265    | 1        | 6         | 3        | 0           | 0                | 1        | 18          | 0                | 4.42               | 13.58      | Detail_001 |

    Examples: 基础信息
      | saleOrgSapCode | regionCode | customerSapCode | regionName | customerName | shippingRecipient | shippingTelephone | shippingAddress |


  @orderCommitVerify30
  Scenario Outline: [场景30]创建订单时，订单行商品分销渠道变更，提交订单校验提示拦截，提示错误信息
  "[主要测试点]
  1. 有预折
  2. 无整单折扣
  3. 订单行商品分销渠道变更
  4. 创建订单失败"
    Given [创建订单提交校验30]当前存在如下客户信息
      | saleOrgSapCode   | regionCode   | customerSapCode   | regionName   | customerName   | shippingAddress   | shippingRecipient   | shippingTelephone   |
      | <saleOrgSapCode> | <regionCode> | <customerSapCode> | <regionName> | <customerName> | <shippingAddress> | <shippingRecipient> | <shippingTelephone> |
    When [创建订单提交校验30]当前存在如下的预折商品信息
      | itemSapCode | currentPrice | calculationType  | calculationValue | preType               | effectiveState | distributionChannelCode | distributionChannelName |
      | 10000841    | 10           | DISCOUNTED_PRICE | 8                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000265    | 6            | DISCOUNTED_PRICE | 5                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000289    | 5            | DISCOUNTED_PRICE | 4.5              | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000556    | 30           | DISCOUNTED_PRICE | 27               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000456    | 20           | DISCOUNTED_PRICE | 18               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000835    | 40           | DISCOUNTED_PRICE | 32               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000633    | 50           | DISCOUNTED_PRICE | 40               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000478    | 17           | DISCOUNTED_PRICE | 13               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
    And [创建订单提交校验30]当前存在如下的预折折扣信息
      | itemSapCode | calculationValue | effectiveState |
      | 10000841    | 8                | ENABLE         |
      | 10000456    | 15               | ENABLE         |
      | 10000556    | 27               | ENABLE         |
      | 10000265    | 5                | DISABLE        |
    And [创建订单提交校验30]当前存在如下的整单折扣申请单信息
      | projectType | module |
      | PT04        | KM03   |
    And [创建订单提交校验30]当前存在如下的整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 10000265    | 100      | 盒     |
      | 10000289    | 100      | 盒     |
    And [创建订单提交校验30]当前存在如下的整单折扣分配计划信息
    And [创建订单提交校验30]当前存在如下的整单折扣分配计划详细信息
      | originalItemSapCode | originalDistributionChannelCode | giftItemSapCode | giftDistributionChannelCode | allocationQuantity | originalQuantity | giftQuantity | initialBonusQuantity | needShip | invalidStatus | unit | originalDistributionChannelName | giftDistributionChannelName | remark     |
      |                     |                                 | 10000265        | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | Detail_001 |
    And [创建订单提交校验30]添加商品
      | itemSapCode | quantity |
      | 10000841    | 30       |
    And [创建订单提交校验30]选择项目
      | itemSapCode | quantity |
      | 10000265    | 3        |
    Then [创建订单提交校验30]创建订单应校验失败且提示错误信息
      | orderWideDiscountSubject | itemSapCode | needShip | unitPrice | quantity | preDiscount | calculationValue | boxRatio | totalInline | preDiscountTotal | orderDiscountTotal | realInline | remark |
      | PT00                     | 10000841    | 1        | 10        | 2        | 8           | 1                | 240      | 0           | 0                | 0                  | 0          |        |

    Examples: 基础信息
      | saleOrgSapCode | regionCode | customerSapCode | regionName | customerName | shippingRecipient | shippingTelephone | shippingAddress |


  @orderCommitVerify31
  Scenario Outline: [场景31]创建订单时，订单行商品单价变更，提交订单校验提示拦截，提示错误信息
  "[主要测试点]
  1. 有预折
  2. 有整单折扣
  3. 订单行商品单价变更
  4. 创建订单失败"
    Given [创建订单提交校验31]当前存在如下客户信息
      | saleOrgSapCode   | regionCode   | customerSapCode   | regionName   | customerName   | shippingAddress   | shippingRecipient   | shippingTelephone   |
      | <saleOrgSapCode> | <regionCode> | <customerSapCode> | <regionName> | <customerName> | <shippingAddress> | <shippingRecipient> | <shippingTelephone> |
    When [创建订单提交校验31]当前存在如下的预折商品信息
      | itemSapCode | currentPrice | calculationType  | calculationValue | preType               | effectiveState | distributionChannelCode | distributionChannelName |
      | 10000841    | 10           | DISCOUNTED_PRICE | 8                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000265    | 6            | DISCOUNTED_PRICE | 5                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000289    | 5            | DISCOUNTED_PRICE | 4.5              | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000556    | 30           | DISCOUNTED_PRICE | 27               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000456    | 20           | DISCOUNTED_PRICE | 18               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000835    | 40           | DISCOUNTED_PRICE | 32               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000633    | 50           | DISCOUNTED_PRICE | 40               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000478    | 17           | DISCOUNTED_PRICE | 13               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
    And [创建订单提交校验31]当前存在如下的预折折扣信息
      | itemSapCode | calculationValue | effectiveState |
      | 10000841    | 8                | ENABLE         |
      | 10000456    | 15               | ENABLE         |
      | 10000556    | 27               | ENABLE         |
      | 10000265    | 5                | DISABLE        |
    And [创建订单提交校验31]当前存在如下的整单折扣申请单信息
      | projectType | module |
      | PT04        | KM03   |
    And [创建订单提交校验31]当前存在如下的整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 10000265    | 100      | 盒     |
      | 10000289    | 100      | 盒     |
    And [创建订单提交校验31]当前存在如下的整单折扣分配计划信息
    And [创建订单提交校验31]当前存在如下的整单折扣分配计划详细信息
      | originalItemSapCode | originalDistributionChannelCode | giftItemSapCode | giftDistributionChannelCode | allocationQuantity | originalQuantity | giftQuantity | initialBonusQuantity | needShip | invalidStatus | unit | originalDistributionChannelName | giftDistributionChannelName | remark     |
      |                     |                                 | 10000265        | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | Detail_001 |
    And [创建订单提交校验31]添加商品
      | itemSapCode | quantity |
      | 10000841    | 30       |
    And [创建订单提交校验31]选择项目
      | itemSapCode | quantity |
      | 10000265    | 3        |
    Then [创建订单提交校验31]创建订单应校验失败且提示错误信息
      | orderWideDiscountSubject | itemSapCode | needShip | unitPrice | quantity | preDiscount | calculationValue | boxRatio | totalInline | preDiscountTotal | orderDiscountTotal | realInline | remark |
      | PT00                     | 10000841    | 1        | 10        | 2        | 8           | 1                | 240      | 0           | 0                | 0                  | 0          |        |

    Examples: 基础信息
      | saleOrgSapCode | regionCode | customerSapCode | regionName | customerName | shippingRecipient | shippingTelephone | shippingAddress |


  @orderCommitVerify32
  Scenario Outline: [场景32]创建订单时，订单行商品预折折扣变化，提交订单校验提示拦截，提示错误信息
  "[主要测试点]
  1. 有预折
  2. 有整单折扣
  3. 订单行商品预折折扣变化
  4. 创建订单失败"
    Given [创建订单提交校验32]当前存在如下客户信息
      | saleOrgSapCode   | regionCode   | customerSapCode   | regionName   | customerName   | shippingAddress   | shippingRecipient   | shippingTelephone   |
      | <saleOrgSapCode> | <regionCode> | <customerSapCode> | <regionName> | <customerName> | <shippingAddress> | <shippingRecipient> | <shippingTelephone> |
    When [创建订单提交校验32]当前存在如下的预折商品信息
      | itemSapCode | currentPrice | calculationType  | calculationValue | preType               | effectiveState | distributionChannelCode | distributionChannelName |
      | 10000841    | 10           | DISCOUNTED_PRICE | 8                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000265    | 6            | DISCOUNTED_PRICE | 5                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000289    | 5            | DISCOUNTED_PRICE | 4.5              | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000556    | 30           | DISCOUNTED_PRICE | 27               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000456    | 20           | DISCOUNTED_PRICE | 18               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000835    | 40           | DISCOUNTED_PRICE | 32               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000633    | 50           | DISCOUNTED_PRICE | 40               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000478    | 17           | DISCOUNTED_PRICE | 13               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
    And [创建订单提交校验32]当前存在如下的预折折扣信息
      | itemSapCode | calculationValue | effectiveState |
      | 10000841    | 8                | ENABLE         |
      | 10000456    | 15               | ENABLE         |
      | 10000556    | 27               | ENABLE         |
      | 10000265    | 5                | DISABLE        |
    And [创建订单提交校验32]当前存在如下的整单折扣申请单信息
      | projectType | module |
      | PT04        | KM03   |
    And [创建订单提交校验32]当前存在如下的整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 10000265    | 100      | 盒     |
      | 10000289    | 100      | 盒     |
    And [创建订单提交校验32]当前存在如下的整单折扣分配计划信息
    And [创建订单提交校验32]当前存在如下的整单折扣分配计划详细信息
      | originalItemSapCode | originalDistributionChannelCode | giftItemSapCode | giftDistributionChannelCode | allocationQuantity | originalQuantity | giftQuantity | initialBonusQuantity | needShip | invalidStatus | unit | originalDistributionChannelName | giftDistributionChannelName | remark     |
      |                     |                                 | 10000265        | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | Detail_001 |
    And [创建订单提交校验32]添加商品
      | itemSapCode | quantity |
      | 10000841    | 30       |
    And [创建订单提交校验32]选择项目
      | itemSapCode | quantity |
      | 10000265    | 3        |
    Then [创建订单提交校验32]创建订单应校验失败且提示错误信息
      | orderWideDiscountSubject | itemSapCode | needShip | unitPrice | quantity | preDiscount | calculationValue | boxRatio | totalInline | preDiscountTotal | orderDiscountTotal | realInline | remark |
      | PT00                     | 10000841    | 1        | 10        | 2        | 8           | 1                | 240      | 0           | 0                | 0                  | 0          |        |

    Examples: 基础信息
      | saleOrgSapCode | regionCode | customerSapCode | regionName | customerName | shippingRecipient | shippingTelephone | shippingAddress |


  @orderCommitVerify33
  Scenario Outline: [场景33]创建订单时，大区限销，且限购商品的数量=限购数量，提交订单校验通过
  "[主要测试点]
  1. 有预折
  2. 无整单折扣
  3. 大区限销，且限购商品的数量=限购数量 （希望购买5盒，当前可购的最大数量：5盒）
  4. 提交订单校验通过"
    Given [创建订单提交校验33]当前存在如下客户信息
      | saleOrgSapCode   | regionCode   | customerSapCode   | regionName   | customerName   | shippingAddress   | shippingRecipient   | shippingTelephone   |
      | <saleOrgSapCode> | <regionCode> | <customerSapCode> | <regionName> | <customerName> | <shippingAddress> | <shippingRecipient> | <shippingTelephone> |
    When [创建订单提交校验33]当前存在如下的预折商品信息
      | itemSapCode | currentPrice | calculationType  | calculationValue | preType               | effectiveState | distributionChannelCode | distributionChannelName |
      | 10000841    | 10           | DISCOUNTED_PRICE | 8                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000265    | 6            | DISCOUNTED_PRICE | 5                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000289    | 5            | DISCOUNTED_PRICE | 4.5              | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000556    | 30           | DISCOUNTED_PRICE | 27               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000456    | 20           | DISCOUNTED_PRICE | 18               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000835    | 40           | DISCOUNTED_PRICE | 32               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000633    | 50           | DISCOUNTED_PRICE | 40               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000478    | 17           | DISCOUNTED_PRICE | 13               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
    And [创建订单提交校验33]当前存在如下的预折折扣信息
      | itemSapCode | calculationValue | effectiveState |
      | 10000841    | 8                | ENABLE         |
      | 10000456    | 15               | ENABLE         |
      | 10000556    | 27               | ENABLE         |
      | 10000265    | 5                | DISABLE        |
    And [创建订单提交校验33]当前存在如下的整单折扣申请单信息
      | projectType | module |
      | PT04        | KM03   |
    And [创建订单提交校验33]当前存在如下的整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 10000265    | 100      | 盒     |
      | 10000289    | 100      | 盒     |
    And [创建订单提交校验33]当前存在如下的整单折扣分配计划信息
    And [创建订单提交校验33]当前存在如下的整单折扣分配计划详细信息
      | originalItemSapCode | originalDistributionChannelCode | giftItemSapCode | giftDistributionChannelCode | allocationQuantity | originalQuantity | giftQuantity | initialBonusQuantity | needShip | invalidStatus | unit | originalDistributionChannelName | giftDistributionChannelName | remark     |
      |                     |                                 | 10000265        | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | Detail_001 |
    And [创建订单提交校验33]添加商品
      | itemSapCode | quantity |
      | 10000841    | 10       |
      | 10000456    | 3        |
      | 10000556    | 10       |
    And [创建订单提交校验33]选择项目
      | itemSapCode | quantity |
      | 10000265    | 24       |
    And [创建订单提交校验33]当前存在如下的商品禁销信息
      | itemSapCode | saleOrgSapCode   | regionCode | saleOrgEffectiveState | permittedSaleQuantityRegion | regionEffectiveState | hasSaleQuantityUnits | customerEntZtCode | customerEntSapCode | customerEntCrmCode | type  | customerName | permittedSaleQuantityCustomer | hasSaleQuantityCustomer | customerEffectiveState | unitCode | unitName |
      | 10000841    | <saleOrgSapCode> | 340000     | ENABLE                | 200                         | ENABLE               | 0                    |                   |                    |                    | SHARE | 大区共享         | 200                           | 195                     | NORMAL                 | HE       | 盒        |
    Then [创建订单提交校验33]创建订单提交校验通过
      | orderWideDiscountSubject | itemSapCode | needShip | unitPrice | quantity | preDiscount | calculationValue | boxRatio | totalInline | preDiscountTotal | orderDiscountTotal | realInline | remark |
      | PT00                     | 10000841    | 1        | 10        | 5        | 10          | 8                | 0        | 50          | 10               | 0                  | 40         |        |

    Examples: 基础信息
      | saleOrgSapCode | regionCode | customerSapCode | regionName | customerName | shippingRecipient | shippingTelephone | shippingAddress |


  @orderCommitVerify34
  Scenario Outline: [场景34]创建订单时，大区限销，客户允销，且限购商品的数量=限购数量，提交订单校验通过
  "[主要测试点]
  1. 有预折
  2. 无整单折扣
  3. 大区限销，客户允销，且限购商品的数量>限购数量 （希望购买5盒，当前可购的最大数量：5盒）
  4. 提交订单校验通过"
    Given [创建订单提交校验34]当前存在如下客户信息
      | saleOrgSapCode   | regionCode   | customerSapCode   | regionName   | customerName   | shippingAddress   | shippingRecipient   | shippingTelephone   |
      | <saleOrgSapCode> | <regionCode> | <customerSapCode> | <regionName> | <customerName> | <shippingAddress> | <shippingRecipient> | <shippingTelephone> |
    When [创建订单提交校验34]当前存在如下的预折商品信息
      | itemSapCode | currentPrice | calculationType  | calculationValue | preType               | effectiveState | distributionChannelCode | distributionChannelName |
      | 10000841    | 10           | DISCOUNTED_PRICE | 8                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000265    | 6            | DISCOUNTED_PRICE | 5                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000289    | 5            | DISCOUNTED_PRICE | 4.5              | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000556    | 30           | DISCOUNTED_PRICE | 27               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000456    | 20           | DISCOUNTED_PRICE | 18               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000835    | 40           | DISCOUNTED_PRICE | 32               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000633    | 50           | DISCOUNTED_PRICE | 40               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000478    | 17           | DISCOUNTED_PRICE | 13               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
    And [创建订单提交校验34]当前存在如下的预折折扣信息
      | itemSapCode | calculationValue | effectiveState |
      | 10000841    | 8                | ENABLE         |
      | 10000456    | 15               | ENABLE         |
      | 10000556    | 27               | ENABLE         |
      | 10000265    | 5                | DISABLE        |
    And [创建订单提交校验34]当前存在如下的整单折扣申请单信息
      | projectType | module |
      | PT04        | KM03   |
    And [创建订单提交校验34]当前存在如下的整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 10000265    | 100      | 盒     |
      | 10000289    | 100      | 盒     |
    And [创建订单提交校验34]当前存在如下的整单折扣分配计划信息
    And [创建订单提交校验34]当前存在如下的整单折扣分配计划详细信息
      | originalItemSapCode | originalDistributionChannelCode | giftItemSapCode | giftDistributionChannelCode | allocationQuantity | originalQuantity | giftQuantity | initialBonusQuantity | needShip | invalidStatus | unit | originalDistributionChannelName | giftDistributionChannelName | remark     |
      |                     |                                 | 10000265        | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | Detail_001 |
    And [创建订单提交校验34]添加商品
      | itemSapCode | quantity |
      | 10000841    | 10       |
      | 10000456    | 3        |
      | 10000556    | 10       |
    And [创建订单提交校验34]选择项目
      | itemSapCode | quantity |
      | 10000265    | 24       |
    And [创建订单提交校验34]当前存在如下的商品禁销信息
      | itemSapCode | saleOrgSapCode   | regionCode | saleOrgEffectiveState | permittedSaleQuantityRegion | regionEffectiveState | hasSaleQuantityUnits | customerEntZtCode | customerEntSapCode | customerEntCrmCode | type  | customerName | permittedSaleQuantityCustomer | hasSaleQuantityCustomer | customerEffectiveState | unitCode | unitName |
      | 10000841    | <saleOrgSapCode> | 340000     | ENABLE                | 200                         | ENABLE               | 0                    |                   |                    |                    | SHARE | 大区共享         | 150                           | 0                       | NORMAL                 | HE       | 盒        |
    And [创建订单提交校验34]当前存在如下的商品一级商允销信息
      | customerSapCode   | type                 | customerName      | permittedSaleQuantityCustomer | hasSaleQuantityCustomer | customerEffectiveState |
      | <customerSapCode> | FIRST_LEVEL_CUSTOMER | 天津市海王星辰健康药房连锁有限公司 | 50                            | 45                      | NORMAL                 |
    Then [创建订单提交校验34]创建订单提交校验通过
      | orderWideDiscountSubject | itemSapCode | needShip | unitPrice | quantity | preDiscount | calculationValue | boxRatio | totalInline | preDiscountTotal | orderDiscountTotal | realInline | remark |
      | PT00                     | 10000841    | 1        | 10        | 5        | 10          | 8                | 0        | 50          | 10               | 0                  | 40         |        |

    Examples: 基础信息
      | saleOrgSapCode | regionCode | customerSapCode | regionName | customerName | shippingRecipient | shippingTelephone | shippingAddress |


  @orderCommitVerify35
  Scenario Outline: [场景35]创建订单时，大区限销，且限购商品的数量>限购数量，提交订单校验提示拦截，提示错误信息
  "[主要测试点]
  1. 有预折
  2. 无整单折扣
  3. 大区限销，且限购商品的数量>限购数量 （希望购买5盒，当前可购的最大数量：4盒(多行合并)）
  4. 创建订单失败"
    Given [创建订单提交校验35]当前存在如下客户信息
      | saleOrgSapCode   | regionCode   | customerSapCode   | regionName   | customerName   | shippingAddress   | shippingRecipient   | shippingTelephone   |
      | <saleOrgSapCode> | <regionCode> | <customerSapCode> | <regionName> | <customerName> | <shippingAddress> | <shippingRecipient> | <shippingTelephone> |
    When [创建订单提交校验35]当前存在如下的预折商品信息
      | itemSapCode | currentPrice | calculationType  | calculationValue | preType               | effectiveState | distributionChannelCode | distributionChannelName |
      | 10000841    | 10           | DISCOUNTED_PRICE | 8                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000265    | 6            | DISCOUNTED_PRICE | 5                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000289    | 5            | DISCOUNTED_PRICE | 4.5              | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000556    | 30           | DISCOUNTED_PRICE | 27               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000456    | 20           | DISCOUNTED_PRICE | 18               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000835    | 40           | DISCOUNTED_PRICE | 32               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000633    | 50           | DISCOUNTED_PRICE | 40               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000478    | 17           | DISCOUNTED_PRICE | 13               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
    And [创建订单提交校验35]当前存在如下的预折折扣信息
      | itemSapCode | calculationValue | effectiveState |
      | 10000841    | 8                | ENABLE         |
      | 10000456    | 15               | ENABLE         |
      | 10000556    | 27               | ENABLE         |
      | 10000265    | 5                | DISABLE        |
    And [创建订单提交校验35]当前存在如下的整单折扣申请单信息
      | projectType | module |
      | PT04        | KM03   |
    And [创建订单提交校验35]当前存在如下的整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 10000265    | 100      | 盒     |
      | 10000289    | 100      | 盒     |
    And [创建订单提交校验35]当前存在如下的整单折扣分配计划信息
    And [创建订单提交校验35]当前存在如下的整单折扣分配计划详细信息
      | originalItemSapCode | originalDistributionChannelCode | giftItemSapCode | giftDistributionChannelCode | allocationQuantity | originalQuantity | giftQuantity | initialBonusQuantity | needShip | invalidStatus | unit | originalDistributionChannelName | giftDistributionChannelName | remark     |
      |                     |                                 | 10000265        | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | Detail_001 |
    And [创建订单提交校验35]添加商品
      | itemSapCode | quantity |
      | 10000841    | 10       |
      | 10000456    | 3        |
      | 10000556    | 10       |
    And [创建订单提交校验35]选择项目
      | itemSapCode | quantity |
      | 10000265    | 24       |
    And [创建订单提交校验35]当前存在如下的商品禁销信息
      | itemSapCode | saleOrgSapCode   | regionCode | saleOrgEffectiveState | permittedSaleQuantityRegion | regionEffectiveState | hasSaleQuantityUnits | customerEntZtCode | customerEntSapCode | customerEntCrmCode | type  | customerName | permittedSaleQuantityCustomer | hasSaleQuantityCustomer | customerEffectiveState | unitCode | unitName |
      | 10000841    | <saleOrgSapCode> | 340000     | ENABLE                | 200                         | ENABLE               | 0                    |                   |                    |                    | SHARE | 大区共享         | 200                           | 196                     | NORMAL                 | HE       | 盒        |
    Then [创建订单提交校验35]创建订单应校验失败且提示错误信息
      | orderWideDiscountSubject | itemSapCode | needShip | unitPrice | quantity | preDiscount | calculationValue | boxRatio | totalInline | preDiscountTotal | orderDiscountTotal | realInline | remark |
      | PT00                     | 10000841    | 1        | 10        | 2        | 4           | 8                | 0        | 20          | 4                | 0                  | 16         |        |
      | PT00                     | 10000841    | 1        | 10        | 3        | 6           | 8                | 0        | 30          | 6                | 0                  | 24         |        |

    Examples: 基础信息
      | saleOrgSapCode | regionCode | customerSapCode | regionName | customerName | shippingRecipient | shippingTelephone | shippingAddress |


  @orderCommitVerify36
  Scenario Outline: [场景36]创建订单时，大区限销，客户允销，且限购商品的数量>限购数量，提交订单校验提示拦截，提示错误信息
  "[主要测试点]
  1. 有预折
  2. 无整单折扣
  3. 大区限销，客户允销，且限购商品的数量>限购数量 （希望购买5盒，当前可购的最大数量：4盒(多行合并)）
  4. 创建订单失败"
    Given [创建订单提交校验36]当前存在如下客户信息
      | saleOrgSapCode   | regionCode   | customerSapCode   | regionName   | customerName   | shippingAddress   | shippingRecipient   | shippingTelephone   |
      | <saleOrgSapCode> | <regionCode> | <customerSapCode> | <regionName> | <customerName> | <shippingAddress> | <shippingRecipient> | <shippingTelephone> |
    When [创建订单提交校验36]当前存在如下的预折商品信息
      | itemSapCode | currentPrice | calculationType  | calculationValue | preType               | effectiveState | distributionChannelCode | distributionChannelName |
      | 10000841    | 10           | DISCOUNTED_PRICE | 8                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000265    | 6            | DISCOUNTED_PRICE | 5                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000289    | 5            | DISCOUNTED_PRICE | 4.5              | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000556    | 30           | DISCOUNTED_PRICE | 27               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000456    | 20           | DISCOUNTED_PRICE | 18               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000835    | 40           | DISCOUNTED_PRICE | 32               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000633    | 50           | DISCOUNTED_PRICE | 40               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000478    | 17           | DISCOUNTED_PRICE | 13               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
    And [创建订单提交校验36]当前存在如下的预折折扣信息
      | itemSapCode | calculationValue | effectiveState |
      | 10000841    | 8                | ENABLE         |
      | 10000456    | 15               | ENABLE         |
      | 10000556    | 27               | ENABLE         |
      | 10000265    | 5                | DISABLE        |
    And [创建订单提交校验36]当前存在如下的整单折扣申请单信息
      | projectType | module |
      | PT04        | KM03   |
    And [创建订单提交校验36]当前存在如下的整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 10000265    | 100      | 盒     |
      | 10000289    | 100      | 盒     |
    And [创建订单提交校验36]当前存在如下的整单折扣分配计划信息
    And [创建订单提交校验36]当前存在如下的整单折扣分配计划详细信息
      | originalItemSapCode | originalDistributionChannelCode | giftItemSapCode | giftDistributionChannelCode | allocationQuantity | originalQuantity | giftQuantity | initialBonusQuantity | needShip | invalidStatus | unit | originalDistributionChannelName | giftDistributionChannelName | remark     |
      |                     |                                 | 10000265        | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | Detail_001 |
    And [创建订单提交校验36]添加商品
      | itemSapCode | quantity |
      | 10000841    | 10       |
      | 10000456    | 3        |
      | 10000556    | 10       |
    And [创建订单提交校验36]选择项目
      | itemSapCode | quantity |
      | 10000265    | 24       |
    And [创建订单提交校验36]当前存在如下的商品禁销信息
      | itemSapCode | saleOrgSapCode   | regionCode | saleOrgEffectiveState | permittedSaleQuantityRegion | regionEffectiveState | hasSaleQuantityUnits | customerEntZtCode | customerEntSapCode | customerEntCrmCode | type  | customerName | permittedSaleQuantityCustomer | hasSaleQuantityCustomer | customerEffectiveState | unitCode | unitName |
      | 10000841    | <saleOrgSapCode> | 340000     | ENABLE                | 200                         | ENABLE               | 0                    |                   |                    |                    | SHARE | 大区共享         | 150                           | 0                       | NORMAL                 | HE       | 盒        |
    And [创建订单提交校验36]当前存在如下的商品一级商允销信息
      | customerSapCode   | type                 | customerName      | permittedSaleQuantityCustomer | hasSaleQuantityCustomer | customerEffectiveState |
      | <customerSapCode> | FIRST_LEVEL_CUSTOMER | 天津市海王星辰健康药房连锁有限公司 | 50                            | 46                      | NORMAL                 |
    Then [创建订单提交校验36]创建订单应校验失败且提示错误信息
      | orderWideDiscountSubject | itemSapCode | needShip | unitPrice | quantity | preDiscount | calculationValue | boxRatio | totalInline | preDiscountTotal | orderDiscountTotal | realInline | remark |
      | PT00                     | 10000841    | 1        | 10        | 2        | 4           | 8                | 0        | 20          | 4                | 0                  | 16         |        |
      | PT00                     | 10000841    | 1        | 10        | 3        | 6           | 8                | 0        | 30          | 6                | 0                  | 24         |        |

    Examples: 基础信息
      | saleOrgSapCode | regionCode | customerSapCode | regionName | customerName | shippingRecipient | shippingTelephone | shippingAddress |


  @orderCommitVerify37
  Scenario Outline: [场景37]创建订单时，大区限销，且限购商品的数量>限购数量，提交订单校验提示拦截，提示错误信息
  "[主要测试点]
  1. 有预折
  2. 有整单折扣
  3. 大区限销，且限购商品的数量>限购数量 （希望加购赠品3盒，当前可购的最大数量：2盒（多行合并））
  4. 创建订单失败"
    Given [创建订单提交校验37]当前存在如下客户信息
      | saleOrgSapCode   | regionCode   | customerSapCode   | regionName   | customerName   | shippingAddress   | shippingRecipient   | shippingTelephone   |
      | <saleOrgSapCode> | <regionCode> | <customerSapCode> | <regionName> | <customerName> | <shippingAddress> | <shippingRecipient> | <shippingTelephone> |
    When [创建订单提交校验37]当前存在如下的预折商品信息
      | itemSapCode | currentPrice | calculationType  | calculationValue | preType               | effectiveState | distributionChannelCode | distributionChannelName |
      | 10000841    | 10           | DISCOUNTED_PRICE | 8                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000265    | 6            | DISCOUNTED_PRICE | 5                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000289    | 5            | DISCOUNTED_PRICE | 4.5              | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000556    | 30           | DISCOUNTED_PRICE | 27               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000456    | 20           | DISCOUNTED_PRICE | 18               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000835    | 40           | DISCOUNTED_PRICE | 32               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000633    | 50           | DISCOUNTED_PRICE | 40               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000478    | 17           | DISCOUNTED_PRICE | 13               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
    And [创建订单提交校验37]当前存在如下的预折折扣信息
      | itemSapCode | calculationValue | effectiveState |
      | 10000841    | 8                | ENABLE         |
      | 10000456    | 15               | ENABLE         |
      | 10000556    | 27               | ENABLE         |
      | 10000265    | 5                | DISABLE        |
    And [创建订单提交校验37]当前存在如下的整单折扣申请单信息
      | projectType | module |
      | PT04        | KM03   |
    And [创建订单提交校验37]当前存在如下的整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 10000265    | 100      | 盒     |
      | 10000289    | 100      | 盒     |
    And [创建订单提交校验37]当前存在如下的整单折扣分配计划信息
    And [创建订单提交校验37]当前存在如下的整单折扣分配计划详细信息
      | originalItemSapCode | originalDistributionChannelCode | giftItemSapCode | giftDistributionChannelCode | allocationQuantity | originalQuantity | giftQuantity | initialBonusQuantity | needShip | invalidStatus | unit | originalDistributionChannelName | giftDistributionChannelName | remark     |
      |                     |                                 | 10000265        | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | Detail_001 |
    And [创建订单提交校验37]添加商品
      | itemSapCode | quantity |
      | 10000841    | 30       |
    And [创建订单提交校验37]选择项目
      | itemSapCode | quantity |
      | 10000265    | 3        |
    And [创建订单提交校验37]当前存在如下的商品禁销信息
      | itemSapCode | saleOrgSapCode   | regionCode | saleOrgEffectiveState | permittedSaleQuantityRegion | regionEffectiveState | hasSaleQuantityUnits | customerEntZtCode | customerEntSapCode | customerEntCrmCode | type  | customerName | permittedSaleQuantityCustomer | hasSaleQuantityCustomer | customerEffectiveState | unitCode | unitName |
      | 10000265    | <saleOrgSapCode> | 340000     | ENABLE                | 200                         | ENABLE               | 0                    |                   |                    |                    | SHARE | 大区共享         | 200                           | 198                     | NORMAL                 | HE       | 盒        |
    Then [创建订单提交校验37]创建订单应校验失败且提示错误信息
      | orderWideDiscountSubject | itemSapCode | needShip | unitPrice | quantity | preDiscount | calculationValue | boxRatio | totalInline | preDiscountTotal | orderDiscountTotal | realInline | remark     |
      | PT00                     | 10000841    | 1        | 10        | 30       | 2           | 8                | 1        | 300         | 60               | 13.58              | 226.42     |            |
      | PT04                     | 10000265    | 1        | 6         | 2        | 0           | 0                | 1        | 12          | 0                | 2.94               | 9.06       | Detail_001 |
      | PT04                     | 10000265    | 1        | 6         | 1        | 0           | 0                | 1        | 6           | 0                | 1.47               | 4.53       | Detail_001 |

    Examples: 基础信息
      | saleOrgSapCode | regionCode | customerSapCode | regionName | customerName | shippingRecipient | shippingTelephone | shippingAddress |


  @orderCommitVerify38
  Scenario Outline: [场景38]创建订单时，大区限销，且限购商品的数量=限购数量，提交订单校验通过
  "[主要测试点]
  1. 有预折
  2. 无整单折扣
  3. 大区限销，且限购商品的数量=限购数量 （希望购买5盒，当前可购的最大数量：5盒（多行合并））
  4. 提交订单校验通过"
    Given [创建订单提交校验38]当前存在如下客户信息
      | saleOrgSapCode   | regionCode   | customerSapCode   | regionName   | customerName   | shippingAddress   | shippingRecipient   | shippingTelephone   |
      | <saleOrgSapCode> | <regionCode> | <customerSapCode> | <regionName> | <customerName> | <shippingAddress> | <shippingRecipient> | <shippingTelephone> |
    When [创建订单提交校验38]当前存在如下的预折商品信息
      | itemSapCode | currentPrice | calculationType  | calculationValue | preType               | effectiveState | distributionChannelCode | distributionChannelName |
      | 10000841    | 10           | DISCOUNTED_PRICE | 8                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000265    | 6            | DISCOUNTED_PRICE | 5                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000289    | 5            | DISCOUNTED_PRICE | 4.5              | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000556    | 30           | DISCOUNTED_PRICE | 27               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000456    | 20           | DISCOUNTED_PRICE | 18               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000835    | 40           | DISCOUNTED_PRICE | 32               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000633    | 50           | DISCOUNTED_PRICE | 40               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000478    | 17           | DISCOUNTED_PRICE | 13               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
    And [创建订单提交校验38]当前存在如下的预折折扣信息
      | itemSapCode | calculationValue | effectiveState |
      | 10000841    | 8                | ENABLE         |
      | 10000456    | 15               | ENABLE         |
      | 10000556    | 27               | ENABLE         |
      | 10000265    | 5                | DISABLE        |
    And [创建订单提交校验38]当前存在如下的整单折扣申请单信息
      | projectType | module |
      | PT04        | KM03   |
    And [创建订单提交校验38]当前存在如下的整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 10000265    | 100      | 盒     |
      | 10000289    | 100      | 盒     |
    And [创建订单提交校验38]当前存在如下的整单折扣分配计划信息
    And [创建订单提交校验38]当前存在如下的整单折扣分配计划详细信息
      | originalItemSapCode | originalDistributionChannelCode | giftItemSapCode | giftDistributionChannelCode | allocationQuantity | originalQuantity | giftQuantity | initialBonusQuantity | needShip | invalidStatus | unit | originalDistributionChannelName | giftDistributionChannelName | remark     |
      |                     |                                 | 10000265        | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | Detail_001 |
    And [创建订单提交校验38]添加商品
      | itemSapCode | quantity |
      | 10000841    | 10       |
      | 10000456    | 3        |
      | 10000556    | 10       |
    And [创建订单提交校验38]选择项目
      | itemSapCode | quantity |
      | 10000265    | 24       |
    And [创建订单提交校验38]当前存在如下的商品禁销信息
      | itemSapCode | saleOrgSapCode   | regionCode | saleOrgEffectiveState | permittedSaleQuantityRegion | regionEffectiveState | hasSaleQuantityUnits | customerEntZtCode | customerEntSapCode | customerEntCrmCode | type  | customerName | permittedSaleQuantityCustomer | hasSaleQuantityCustomer | customerEffectiveState | unitCode | unitName |
      | 10000841    | <saleOrgSapCode> | 340000     | ENABLE                | 200                         | ENABLE               | 0                    |                   |                    |                    | SHARE | 大区共享         | 200                           | 195                     | NORMAL                 | HE       | 盒        |
    Then [创建订单提交校验38]创建订单提交校验通过
      | orderWideDiscountSubject | itemSapCode | needShip | unitPrice | quantity | preDiscount | calculationValue | boxRatio | totalInline | preDiscountTotal | orderDiscountTotal | realInline | remark |
      | PT00                     | 10000841    | 1        | 10        | 2        | 2           | 8                | 0        | 20          | 4                | 0                  | 16         |        |
      | PT00                     | 10000841    | 1        | 10        | 3        | 2           | 8                | 0        | 30          | 6                | 0                  | 24         |        |

    Examples: 基础信息
      | saleOrgSapCode | regionCode | customerSapCode | regionName | customerName | shippingRecipient | shippingTelephone | shippingAddress |


  @orderCommitVerify39
  Scenario Outline: [场景39]创建订单时，大区限销，客户允销，且限购商品的数量=限购数量，提交订单校验通过
  "[主要测试点]
  1. 有预折
  2. 无整单折扣
  3. 大区限销，客户允销，且限购商品的数量>限购数量 （希望购买5盒，当前可购的最大数量：5盒（多行合并））
  4. 提交订单校验通过"
    Given [创建订单提交校验39]当前存在如下客户信息
      | saleOrgSapCode   | regionCode   | customerSapCode   | regionName   | customerName   | shippingAddress   | shippingRecipient   | shippingTelephone   |
      | <saleOrgSapCode> | <regionCode> | <customerSapCode> | <regionName> | <customerName> | <shippingAddress> | <shippingRecipient> | <shippingTelephone> |
    When [创建订单提交校验39]当前存在如下的预折商品信息
      | itemSapCode | currentPrice | calculationType  | calculationValue | preType               | effectiveState | distributionChannelCode | distributionChannelName |
      | 10000841    | 10           | DISCOUNTED_PRICE | 8                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000265    | 6            | DISCOUNTED_PRICE | 5                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000289    | 5            | DISCOUNTED_PRICE | 4.5              | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000556    | 30           | DISCOUNTED_PRICE | 27               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000456    | 20           | DISCOUNTED_PRICE | 18               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000835    | 40           | DISCOUNTED_PRICE | 32               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000633    | 50           | DISCOUNTED_PRICE | 40               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000478    | 17           | DISCOUNTED_PRICE | 13               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
    And [创建订单提交校验39]当前存在如下的预折折扣信息
      | itemSapCode | calculationValue | effectiveState |
      | 10000841    | 8                | ENABLE         |
      | 10000456    | 15               | ENABLE         |
      | 10000556    | 27               | ENABLE         |
      | 10000265    | 5                | DISABLE        |
    And [创建订单提交校验39]当前存在如下的整单折扣申请单信息
      | projectType | module |
      | PT04        | KM03   |
    And [创建订单提交校验39]当前存在如下的整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 10000265    | 100      | 盒     |
      | 10000289    | 100      | 盒     |
    And [创建订单提交校验39]当前存在如下的整单折扣分配计划信息
    And [创建订单提交校验39]当前存在如下的整单折扣分配计划详细信息
      | originalItemSapCode | originalDistributionChannelCode | giftItemSapCode | giftDistributionChannelCode | allocationQuantity | originalQuantity | giftQuantity | initialBonusQuantity | needShip | invalidStatus | unit | originalDistributionChannelName | giftDistributionChannelName | remark     |
      |                     |                                 | 10000265        | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | Detail_001 |
    And [创建订单提交校验39]添加商品
      | itemSapCode | quantity |
      | 10000841    | 10       |
      | 10000456    | 3        |
      | 10000556    | 10       |
    And [创建订单提交校验39]选择项目
      | itemSapCode | quantity |
      | 10000265    | 24       |
    And [创建订单提交校验39]当前存在如下的商品禁销信息
      | itemSapCode | saleOrgSapCode   | regionCode | saleOrgEffectiveState | permittedSaleQuantityRegion | regionEffectiveState | hasSaleQuantityUnits | customerEntZtCode | customerEntSapCode | customerEntCrmCode | type  | customerName | permittedSaleQuantityCustomer | hasSaleQuantityCustomer | customerEffectiveState | unitCode | unitName |
      | 10000841    | <saleOrgSapCode> | 340000     | ENABLE                | 200                         | ENABLE               | 0                    |                   |                    |                    | SHARE | 大区共享         | 150                           | 0                       | NORMAL                 | HE       | 盒        |
    And [创建订单提交校验39]当前存在如下的商品一级商允销信息
      | customerSapCode   | type                 | customerName      | permittedSaleQuantityCustomer | hasSaleQuantityCustomer | customerEffectiveState |
      | <customerSapCode> | FIRST_LEVEL_CUSTOMER | 天津市海王星辰健康药房连锁有限公司 | 50                            | 45                      | NORMAL                 |
    Then [创建订单提交校验39]创建订单提交校验通过
      | orderWideDiscountSubject | itemSapCode | needShip | unitPrice | quantity | preDiscount | calculationValue | boxRatio | totalInline | preDiscountTotal | orderDiscountTotal | realInline | remark |
      | PT00                     | 10000841    | 1        | 10        | 2        | 2           | 8                | 0        | 20          | 4                | 0                  | 16         |        |
      | PT00                     | 10000841    | 1        | 10        | 3        | 2           | 8                | 0        | 30          | 6                | 0                  | 24         |        |

    Examples: 基础信息
      | saleOrgSapCode | regionCode | customerSapCode | regionName | customerName | shippingRecipient | shippingTelephone | shippingAddress |


  @orderCommitVerify40
  Scenario Outline: [场景40]创建订单时，大区限销，且限购商品的数量>限购数量，提交订单校验提示拦截，提示错误信息
  "[主要测试点]
  1. 有预折
  2. 无整单折扣
  3. 大区限销，且限购商品的数量>限购数量 （希望购买10盒，当前可购的最大数量：9盒(多行合并:原品9盒和赠品1盒)）
  4. 创建订单失败"
    Given [创建订单提交校验40]当前存在如下客户信息
      | saleOrgSapCode   | regionCode   | customerSapCode   | regionName   | customerName   | shippingAddress   | shippingRecipient   | shippingTelephone   |
      | <saleOrgSapCode> | <regionCode> | <customerSapCode> | <regionName> | <customerName> | <shippingAddress> | <shippingRecipient> | <shippingTelephone> |
    When [创建订单提交校验40]当前存在如下的预折商品信息
      | itemSapCode | currentPrice | calculationType  | calculationValue | preType               | effectiveState | distributionChannelCode | distributionChannelName |
      | 10000841    | 10           | DISCOUNTED_PRICE | 8                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000265    | 6            | DISCOUNTED_PRICE | 5                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000289    | 5            | DISCOUNTED_PRICE | 4.5              | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000556    | 30           | DISCOUNTED_PRICE | 27               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000456    | 20           | DISCOUNTED_PRICE | 18               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000835    | 40           | DISCOUNTED_PRICE | 32               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000633    | 50           | DISCOUNTED_PRICE | 40               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000478    | 17           | DISCOUNTED_PRICE | 13               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
    And [创建订单提交校验40]当前存在如下的预折折扣信息
      | itemSapCode | calculationValue | effectiveState |
      | 10000841    | 8                | ENABLE         |
      | 10000456    | 15               | ENABLE         |
      | 10000556    | 27               | ENABLE         |
      | 10000265    | 5                | DISABLE        |
    And [创建订单提交校验40]当前存在如下的整单折扣申请单信息
      | projectType | module |
      | PT04        | KM03   |
    And [创建订单提交校验40]当前存在如下的整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 10000841    | 100      | 盒     |
    And [创建订单提交校验40]当前存在如下的整单折扣分配计划信息
    And [创建订单提交校验40]当前存在如下的整单折扣分配计划详细信息
      | originalItemSapCode | originalDistributionChannelCode | giftItemSapCode | giftDistributionChannelCode | allocationQuantity | originalQuantity | giftQuantity | initialBonusQuantity | needShip | invalidStatus | unit | originalDistributionChannelName | giftDistributionChannelName | remark     |
      |                     |                                 | 10000841        | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | Detail_001 |
    And [创建订单提交校验40]添加商品
      | itemSapCode | quantity |
      | 10000841    | 10       |
      | 10000456    | 3        |
      | 10000556    | 10       |
    And [创建订单提交校验40]选择项目
      | itemSapCode | quantity |
      | 10000841    | 24       |
    And [创建订单提交校验40]当前存在如下的商品禁销信息
      | itemSapCode | saleOrgSapCode   | regionCode | saleOrgEffectiveState | permittedSaleQuantityRegion | regionEffectiveState | hasSaleQuantityUnits | customerEntZtCode | customerEntSapCode | customerEntCrmCode | type  | customerName | permittedSaleQuantityCustomer | hasSaleQuantityCustomer | customerEffectiveState | unitCode | unitName |
      | 10000841    | <saleOrgSapCode> | 340000     | ENABLE                | 200                         | ENABLE               | 0                    |                   |                    |                    | SHARE | 大区共享         | 200                           | 191                     | NORMAL                 | HE       | 盒        |
    Then [创建订单提交校验40]创建订单应校验失败且提示错误信息
      | orderWideDiscountSubject | itemSapCode | needShip | unitPrice | quantity | preDiscount | calculationValue | boxRatio | totalInline | preDiscountTotal | orderDiscountTotal | realInline | remark     |
      | PT00                     | 10000841    | 1        | 10        | 2        | 2           | 8                | 0        | 20          | 4                | 1.6                | 14.4       |            |
      | PT00                     | 10000841    | 1        | 10        | 7        | 2           | 8                | 0        | 70          | 14               | 5.6                | 50.4       |            |
      | PT04                     | 10000841    | 1        | 10        | 1        | 2           | 8                | 0        | 10          | 2                | 0.8                | 7.2        | Detail_001 |

    Examples: 基础信息
      | saleOrgSapCode | regionCode | customerSapCode | regionName | customerName | shippingRecipient | shippingTelephone | shippingAddress |


  @orderCommitVerify41
  Scenario Outline: [场景41]创建订单时，大区限销，客户允销，且限购商品的数量>限购数量，提交订单校验提示拦截，提示错误信息
  "[主要测试点]
  1. 有预折
  2. 无整单折扣
  3. 大区限销，客户允销，且限购商品的数量>限购数量 （希望购买10盒，当前可购的最大数量：9盒(多行合并:原品9盒和赠品1盒)）
  4. 创建订单失败"
    Given [创建订单提交校验41]当前存在如下客户信息
      | saleOrgSapCode   | regionCode   | customerSapCode   | regionName   | customerName   | shippingAddress   | shippingRecipient   | shippingTelephone   |
      | <saleOrgSapCode> | <regionCode> | <customerSapCode> | <regionName> | <customerName> | <shippingAddress> | <shippingRecipient> | <shippingTelephone> |
    When [创建订单提交校验41]当前存在如下的预折商品信息
      | itemSapCode | currentPrice | calculationType  | calculationValue | preType               | effectiveState | distributionChannelCode | distributionChannelName |
      | 10000841    | 10           | DISCOUNTED_PRICE | 8                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000265    | 6            | DISCOUNTED_PRICE | 5                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000289    | 5            | DISCOUNTED_PRICE | 4.5              | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000556    | 30           | DISCOUNTED_PRICE | 27               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000456    | 20           | DISCOUNTED_PRICE | 18               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000835    | 40           | DISCOUNTED_PRICE | 32               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000633    | 50           | DISCOUNTED_PRICE | 40               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000478    | 17           | DISCOUNTED_PRICE | 13               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
    And [创建订单提交校验41]当前存在如下的预折折扣信息
      | itemSapCode | calculationValue | effectiveState |
      | 10000841    | 8                | ENABLE         |
      | 10000456    | 15               | ENABLE         |
      | 10000556    | 27               | ENABLE         |
      | 10000265    | 5                | DISABLE        |
    And [创建订单提交校验41]当前存在如下的整单折扣申请单信息
      | projectType | module |
      | PT04        | KM03   |
    And [创建订单提交校验41]当前存在如下的整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 10000841    | 100      | 盒     |
    And [创建订单提交校验41]当前存在如下的整单折扣分配计划信息
    And [创建订单提交校验41]当前存在如下的整单折扣分配计划详细信息
      | originalItemSapCode | originalDistributionChannelCode | giftItemSapCode | giftDistributionChannelCode | allocationQuantity | originalQuantity | giftQuantity | initialBonusQuantity | needShip | invalidStatus | unit | originalDistributionChannelName | giftDistributionChannelName | remark     |
      |                     |                                 | 10000841        | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | Detail_001 |
    And [创建订单提交校验41]添加商品
      | itemSapCode | quantity |
      | 10000841    | 10       |
      | 10000456    | 3        |
      | 10000556    | 10       |
    And [创建订单提交校验41]选择项目
      | itemSapCode | quantity |
      | 10000265    | 24       |
    And [创建订单提交校验41]当前存在如下的商品禁销信息
      | itemSapCode | saleOrgSapCode   | regionCode | saleOrgEffectiveState | permittedSaleQuantityRegion | regionEffectiveState | hasSaleQuantityUnits | customerEntZtCode | customerEntSapCode | customerEntCrmCode | type  | customerName | permittedSaleQuantityCustomer | hasSaleQuantityCustomer | customerEffectiveState | unitCode | unitName |
      | 10000841    | <saleOrgSapCode> | 340000     | ENABLE                | 200                         | ENABLE               | 0                    |                   |                    |                    | SHARE | 大区共享         | 150                           | 0                       | NORMAL                 | HE       | 盒        |
    And [创建订单提交校验41]当前存在如下的商品一级商允销信息
      | customerSapCode   | type                 | customerName      | permittedSaleQuantityCustomer | hasSaleQuantityCustomer | customerEffectiveState |
      | <customerSapCode> | FIRST_LEVEL_CUSTOMER | 天津市海王星辰健康药房连锁有限公司 | 50                            | 41                      | NORMAL                 |
    Then [创建订单提交校验41]创建订单应校验失败且提示错误信息
      | orderWideDiscountSubject | itemSapCode | needShip | unitPrice | quantity | preDiscount | calculationValue | boxRatio | totalInline | preDiscountTotal | orderDiscountTotal | realInline | remark     |
      | PT00                     | 10000841    | 1        | 10        | 2        | 2           | 8                | 0        | 20          | 4                | 1.6                | 14.4       |            |
      | PT00                     | 10000841    | 1        | 10        | 7        | 2           | 8                | 0        | 70          | 14               | 5.6                | 50.4       |            |
      | PT04                     | 10000841    | 1        | 10        | 1        | 2           | 8                | 0        | 10          | 2                | 0.8                | 7.2        | Detail_001 |

    Examples: 基础信息
      | saleOrgSapCode | regionCode | customerSapCode | regionName | customerName | shippingRecipient | shippingTelephone | shippingAddress |


  @orderCommitVerify42
  Scenario Outline: [场景42]创建订单时，大区限销，且限购商品的数量=限购数量，提交订单校验通过
  "[主要测试点]
  1. 有预折
  2. 无整单折扣
  3. 大区限销，且限购商品的数量=限购数量 （希望购买10盒，当前可购的最大数量：10盒(多行合并:原品9盒和赠品1盒)）
  4. 创建订单提交校验通过"
    Given [创建订单提交校验42]当前存在如下客户信息
      | saleOrgSapCode   | regionCode   | customerSapCode   | regionName   | customerName   | shippingAddress   | shippingRecipient   | shippingTelephone   |
      | <saleOrgSapCode> | <regionCode> | <customerSapCode> | <regionName> | <customerName> | <shippingAddress> | <shippingRecipient> | <shippingTelephone> |
    When [创建订单提交校验42]当前存在如下的预折商品信息
      | itemSapCode | currentPrice | calculationType  | calculationValue | preType               | effectiveState | distributionChannelCode | distributionChannelName |
      | 10000841    | 10           | DISCOUNTED_PRICE | 8                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000265    | 6            | DISCOUNTED_PRICE | 5                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000289    | 5            | DISCOUNTED_PRICE | 4.5              | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000556    | 30           | DISCOUNTED_PRICE | 27               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000456    | 20           | DISCOUNTED_PRICE | 18               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000835    | 40           | DISCOUNTED_PRICE | 32               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000633    | 50           | DISCOUNTED_PRICE | 40               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000478    | 17           | DISCOUNTED_PRICE | 13               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
    And [创建订单提交校验42]当前存在如下的预折折扣信息
      | itemSapCode | calculationValue | effectiveState |
      | 10000841    | 8                | ENABLE         |
      | 10000456    | 15               | ENABLE         |
      | 10000556    | 27               | ENABLE         |
      | 10000265    | 5                | DISABLE        |
    And [创建订单提交校验42]当前存在如下的整单折扣申请单信息
      | projectType | module |
      | PT04        | KM03   |
    And [创建订单提交校验42]当前存在如下的整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 10000841    | 100      | 盒     |
    And [创建订单提交校验42]当前存在如下的整单折扣分配计划信息
    And [创建订单提交校验42]当前存在如下的整单折扣分配计划详细信息
      | originalItemSapCode | originalDistributionChannelCode | giftItemSapCode | giftDistributionChannelCode | allocationQuantity | originalQuantity | giftQuantity | initialBonusQuantity | needShip | invalidStatus | unit | originalDistributionChannelName | giftDistributionChannelName | remark     |
      |                     |                                 | 10000841        | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | Detail_001 |
    And [创建订单提交校验42]添加商品
      | itemSapCode | quantity |
      | 10000841    | 10       |
      | 10000456    | 3        |
      | 10000556    | 10       |
    And [创建订单提交校验42]选择项目
      | itemSapCode | quantity |
      | 10000841    | 24       |
    And [创建订单提交校验42]当前存在如下的商品禁销信息
      | itemSapCode | saleOrgSapCode   | regionCode | saleOrgEffectiveState | permittedSaleQuantityRegion | regionEffectiveState | hasSaleQuantityUnits | customerEntZtCode | customerEntSapCode | customerEntCrmCode | type  | customerName | permittedSaleQuantityCustomer | hasSaleQuantityCustomer | customerEffectiveState | unitCode | unitName |
      | 10000841    | <saleOrgSapCode> | 340000     | ENABLE                | 200                         | ENABLE               | 0                    |                   |                    |                    | SHARE | 大区共享         | 200                           | 190                     | NORMAL                 | HE       | 盒        |
    Then [创建订单提交校验42]创建订单提交校验通过
      | orderWideDiscountSubject | itemSapCode | needShip | unitPrice | quantity | preDiscount | calculationValue | boxRatio | totalInline | preDiscountTotal | orderDiscountTotal | realInline | remark     |
      | PT00                     | 10000841    | 1        | 10        | 2        | 2           | 8                | 0        | 20          | 4                | 1.6                | 14.4       |            |
      | PT00                     | 10000841    | 1        | 10        | 7        | 2           | 8                | 0        | 70          | 14               | 5.6                | 50.4       |            |
      | PT04                     | 10000841    | 1        | 10        | 1        | 2           | 8                | 0        | 10          | 2                | 0.8                | 7.2        | Detail_001 |

    Examples: 基础信息
      | saleOrgSapCode | regionCode | customerSapCode | regionName | customerName | shippingRecipient | shippingTelephone | shippingAddress |


  @orderCommitVerify43
  Scenario Outline: [场景43]创建订单时，大区限销，客户允销，且限购商品的数量=限购数量，提交订单校验通过
  "[主要测试点]
  1. 有预折
  2. 无整单折扣
  3. 大区限销，客户允销，且限购商品的数量=限购数量 （希望购买10盒，当前可购的最大数量：10盒(多行合并:原品9盒和赠品1盒)）
  4. 创建订单提交校验通过"
    Given [创建订单提交校验43]当前存在如下客户信息
      | saleOrgSapCode   | regionCode   | customerSapCode   | regionName   | customerName   | shippingAddress   | shippingRecipient   | shippingTelephone   |
      | <saleOrgSapCode> | <regionCode> | <customerSapCode> | <regionName> | <customerName> | <shippingAddress> | <shippingRecipient> | <shippingTelephone> |
    When [创建订单提交校验43]当前存在如下的预折商品信息
      | itemSapCode | currentPrice | calculationType  | calculationValue | preType               | effectiveState | distributionChannelCode | distributionChannelName |
      | 10000841    | 10           | DISCOUNTED_PRICE | 8                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000265    | 6            | DISCOUNTED_PRICE | 5                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000289    | 5            | DISCOUNTED_PRICE | 4.5              | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000556    | 30           | DISCOUNTED_PRICE | 27               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000456    | 20           | DISCOUNTED_PRICE | 18               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000835    | 40           | DISCOUNTED_PRICE | 32               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000633    | 50           | DISCOUNTED_PRICE | 40               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000478    | 17           | DISCOUNTED_PRICE | 13               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
    And [创建订单提交校验43]当前存在如下的预折折扣信息
      | itemSapCode | calculationValue | effectiveState |
      | 10000841    | 8                | ENABLE         |
      | 10000456    | 15               | ENABLE         |
      | 10000556    | 27               | ENABLE         |
      | 10000265    | 5                | DISABLE        |
    And [创建订单提交校验43]当前存在如下的整单折扣申请单信息
      | projectType | module |
      | PT04        | KM03   |
    And [创建订单提交校验43]当前存在如下的整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 10000841    | 100      | 盒     |
    And [创建订单提交校验43]当前存在如下的整单折扣分配计划信息
    And [创建订单提交校验43]当前存在如下的整单折扣分配计划详细信息
      | originalItemSapCode | originalDistributionChannelCode | giftItemSapCode | giftDistributionChannelCode | allocationQuantity | originalQuantity | giftQuantity | initialBonusQuantity | needShip | invalidStatus | unit | originalDistributionChannelName | giftDistributionChannelName | remark     |
      |                     |                                 | 10000841        | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | Detail_001 |
    And [创建订单提交校验43]添加商品
      | itemSapCode | quantity |
      | 10000841    | 10       |
      | 10000456    | 3        |
      | 10000556    | 10       |
    And [创建订单提交校验43]选择项目
      | itemSapCode | quantity |
      | 10000265    | 24       |
    And [创建订单提交校验43]当前存在如下的商品禁销信息
      | itemSapCode | saleOrgSapCode   | regionCode | saleOrgEffectiveState | permittedSaleQuantityRegion | regionEffectiveState | hasSaleQuantityUnits | customerEntZtCode | customerEntSapCode | customerEntCrmCode | type  | customerName | permittedSaleQuantityCustomer | hasSaleQuantityCustomer | customerEffectiveState | unitCode | unitName |
      | 10000841    | <saleOrgSapCode> | 340000     | ENABLE                | 200                         | ENABLE               | 0                    |                   |                    |                    | SHARE | 大区共享         | 150                           | 0                       | NORMAL                 | HE       | 盒        |
    And [创建订单提交校验43]当前存在如下的商品一级商允销信息
      | customerSapCode   | type                 | customerName      | permittedSaleQuantityCustomer | hasSaleQuantityCustomer | customerEffectiveState |
      | <customerSapCode> | FIRST_LEVEL_CUSTOMER | 天津市海王星辰健康药房连锁有限公司 | 50                            | 40                      | NORMAL                 |
    Then [创建订单提交校验43]创建订单提交校验通过
      | orderWideDiscountSubject | itemSapCode | needShip | unitPrice | quantity | preDiscount | calculationValue | boxRatio | totalInline | preDiscountTotal | orderDiscountTotal | realInline | remark     |
      | PT00                     | 10000841    | 1        | 10        | 2        | 2           | 8                | 0        | 20          | 4                | 1.6                | 14.4       |            |
      | PT00                     | 10000841    | 1        | 10        | 7        | 2           | 8                | 0        | 70          | 14               | 5.6                | 50.4       |            |
      | PT04                     | 10000841    | 1        | 10        | 1        | 2           | 8                | 0        | 10          | 2                | 0.8                | 7.2        | Detail_001 |

    Examples: 基础信息
      | saleOrgSapCode | regionCode | customerSapCode | regionName | customerName | shippingRecipient | shippingTelephone | shippingAddress |
      
