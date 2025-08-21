Feature: 整单折扣计算逻辑

  Background: [前置条件] 预折和整单折扣的数据初始化
    Given [整单折扣基础数据01]清除价格\预折\整单折扣的测试数据
    When [整单折扣基础数据01]商品价格测试数据初始化

  @discountCalculation01
  Scenario Outline: [场景1]当前订单的商品无预折折扣，不使用整单折扣，预折折扣和整单折扣计算值正确。
  "[主要测试点]
  1. 原品行无预折折扣
  2. 订单不使用整单折扣
  3. 预折折扣和整单折扣计算值正确"
    Given [整单折扣计算01]当前存在如下客户信息
      | saleOrgSapCode   | regionCode   | customerSapCode   | regionName   | customerName   | shippingAddress   | shippingRecipient   | shippingTelephone   |
      | <saleOrgSapCode> | <regionCode> | <customerSapCode> | <regionName> | <customerName> | <shippingAddress> | <shippingRecipient> | <shippingTelephone> |
    When [整单折扣计算01]当前存在如下的预折商品信息
      | itemSapCode | currentPrice | calculationType  | calculationValue | preType               | effectiveState | distributionChannelCode | distributionChannelName |
      | 10000841    | 10           | DISCOUNTED_PRICE | 8                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000265    | 6            | DISCOUNTED_PRICE | 5                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000289    | 5            | DISCOUNTED_PRICE | 4.5              | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000556    | 30           | DISCOUNTED_PRICE | 27               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000456    | 20           | DISCOUNTED_PRICE | 18               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000835    | 40           | DISCOUNTED_PRICE | 32               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000633    | 50           | DISCOUNTED_PRICE | 40               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000478    | 17           | DISCOUNTED_PRICE | 13               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
    And [整单折扣计算01]当前存在如下的有效预折折扣信息
      | itemSapCode | effectiveState |
      | 10000841    | DISABLE        |
      | 10000456    | DISABLE        |
      | 10000556    | DISABLE        |
    And [整单折扣计算01]当前存在如下的整单折扣申请单信息
      | projectType | module |
      | PT04        | KM03   |
    And [整单折扣计算01]当前存在如下的整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 10000265    | 100      | 盒     |
      | 10000289    | 100      | 盒     |
    And [整单折扣计算01]当前存在如下的整单折扣分配计划信息
    And [整单折扣计算01]当前存在如下的整单折扣分配计划详细信息
      | originalItemSapCode | originalDistributionChannelCode | giftItemSapCode | giftDistributionChannelCode | allocationQuantity | originalQuantity | giftQuantity | initialBonusQuantity | needShip | invalidStatus | unit | originalDistributionChannelName | giftDistributionChannelName |
      |                     |                                 | 10000289        | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      |
    And [整单折扣计算01]添加商品
      | itemSapCode | quantity |
      | 10000841    | 1        |
      | 10000456    | 2        |
      | 10000556    | 3        |
    Then [整单折扣计算01]进行整单折扣计算后,订单行分摊如下
      | orderWideDiscountSubject | itemSapCode | needShip | unitPrice | preDiscount | calculationValue | boxRatio | quantity | totalInline | preDiscountTotal | orderDiscountTotal |
      | PT00                     | 10000841    | 1        | 10        | 0           | 10               | 1        | 1        | 10          | 0                | 0                  |
      | PT00                     | 10000456    | 1        | 20        | 0           | 20               | 1        | 2        | 40          | 0                | 0                  |
      | PT00                     | 10000556    | 1        | 30        | 0           | 30               | 1        | 3        | 90          | 0                | 0                  |
    Examples: 基础信息
      | saleOrgSapCode | regionCode | customerSapCode | regionName | customerName | shippingRecipient | shippingTelephone | shippingAddress |


  @discountCalculation02
  Scenario Outline: [场景2]当前订单的商品有预折折扣，不使用整单折扣，预折折扣和整单折扣计算值正确。
  "[主要测试点]
  1. 原品行有预折折扣
  2. 订单不使用整单折扣
  3. 预折折扣和整单折扣计算值正确
    Given [整单折扣计算02]当前存在如下客户信息
      | saleOrgSapCode   | regionCode   | customerSapCode   | regionName   | customerName   | shippingAddress   | shippingRecipient   | shippingTelephone   |
      | <saleOrgSapCode> | <regionCode> | <customerSapCode> | <regionName> | <customerName> | <shippingAddress> | <shippingRecipient> | <shippingTelephone> |
    When [整单折扣计算02]当前存在如下的预折商品信息
      | itemSapCode | currentPrice | calculationType  | calculationValue | preType               | effectiveState | distributionChannelCode | distributionChannelName |
      | 10000841    | 10           | DISCOUNTED_PRICE | 8                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000265    | 6            | DISCOUNTED_PRICE | 5                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000289    | 5            | DISCOUNTED_PRICE | 4.5              | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000556    | 30           | DISCOUNTED_PRICE | 27               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000456    | 20           | DISCOUNTED_PRICE | 18               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000835    | 40           | DISCOUNTED_PRICE | 32               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000633    | 50           | DISCOUNTED_PRICE | 40               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000478    | 17           | DISCOUNTED_PRICE | 13               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
    And [整单折扣计算02]当前存在如下的整单折扣申请单信息
      | projectType | module |
      | PT04        | KM03   |
    And [整单折扣计算02]当前存在如下的整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 10000265    | 100      | 盒     |
      | 10000289    | 100      | 盒     |
    And [整单折扣计算02]当前存在如下的整单折扣分配计划信息
    And [整单折扣计算02]当前存在如下的整单折扣分配计划详细信息
      | originalItemSapCode | originalDistributionChannelCode | giftItemSapCode | giftDistributionChannelCode | allocationQuantity | originalQuantity | giftQuantity | initialBonusQuantity | needShip | invalidStatus | unit | originalDistributionChannelName | giftDistributionChannelName |
      |                     |                                 | 10000289        | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      |
    And [整单折扣计算02]添加商品
      | itemSapCode | quantity |
      | 10000841    | 5        |
      | 10000556    | 10       |
      | 10000456    | 1        |
    Then [整单折扣计算02]进行整单折扣计算后,订单行分摊如下
      | orderWideDiscountSubject | itemSapCode | needShip | unitPrice | preDiscount | calculationValue | boxRatio | quantity | totalInline | preDiscountTotal | orderDiscountTotal |
      | PT00                     | 10000841    | 1        | 10        | 2           | 8                | 1        | 5        | 50          | 10               | 0                  |
      | PT00                     | 10000456    | 1        | 20        | 2           | 18               | 1        | 10       | 200         | 20               | 0                  |
      | PT00                     | 10000556    | 1        | 30        | 3           | 27               | 1        | 1        | 30          | 3                | 0                  |
    Examples: 基础信息
      | saleOrgSapCode | regionCode | customerSapCode | regionName | customerName | shippingRecipient | shippingTelephone | shippingAddress |


  @discountCalculation03
  Scenario Outline: [场景5A]当前订单的商品无预折折扣，且使用整单折扣，无相同原品，预折折扣和整单折扣计算值正确（发货赠品行参与计算）。
  "[主要测试点]
  1. 原品行无预折折扣，赠品行无预折折扣
  2. 订单使用整单折扣（1个活动项目，1个分配计划）
  3. 全部行均参与计算（预折折扣 < 总折扣）
  4. 无相同原品"
    Given [整单折扣计算05A]当前存在如下客户信息
      | saleOrgSapCode   | regionCode   | customerSapCode   | regionName   | customerName   | shippingAddress   | shippingRecipient   | shippingTelephone   |
      | <saleOrgSapCode> | <regionCode> | <customerSapCode> | <regionName> | <customerName> | <shippingAddress> | <shippingRecipient> | <shippingTelephone> |
    When [整单折扣计算05A]当前存在如下的预折商品信息
      | itemSapCode | currentPrice | calculationType  | calculationValue | preType               | effectiveState | distributionChannelCode | distributionChannelName |
      | 10000841    | 10           | DISCOUNTED_PRICE | 8                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000265    | 6            | DISCOUNTED_PRICE | 5                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000289    | 5            | DISCOUNTED_PRICE | 4.5              | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000556    | 30           | DISCOUNTED_PRICE | 27               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000456    | 20           | DISCOUNTED_PRICE | 18               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000835    | 40           | DISCOUNTED_PRICE | 32               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000633    | 50           | DISCOUNTED_PRICE | 40               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000478    | 17           | DISCOUNTED_PRICE | 13               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
    And [整单折扣计算05A]当前存在如下的有效预折折扣信息
      | itemSapCode | effectiveState |
      | 10000841    | DISABLE        |
      | 10000456    | DISABLE        |
      | 10000556    | DISABLE        |
      | 10000289    | DISABLE        |
    And [整单折扣计算05A]当前存在如下的整单折扣申请单信息
      | projectType | module |
      | PT01        | KM03   |
    And [整单折扣计算05A]当前存在如下的整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 10000265    | 100      | 盒     |
      | 10000289    | 100      | 盒     |
    And [整单折扣计算05A]当前存在如下的整单折扣分配计划信息
    And [整单折扣计算05A]当前存在如下的整单折扣分配计划详细信息
      | originalItemSapCode | originalDistributionChannelCode | giftItemSapCode | giftDistributionChannelCode | allocationQuantity | originalQuantity | giftQuantity | initialBonusQuantity | needShip | invalidStatus | unit | originalDistributionChannelName | giftDistributionChannelName |
      |                     |                                 | 10000289        | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      |
    And [整单折扣计算05A]添加商品
      | itemSapCode | quantity |
      | 10000841    | 10       |
      | 10000556    | 10       |
      | 10000456    | 10       |
    And [整单折扣计算05A]选择项目
      | itemSapCode | quantity |
      | 10000289    | 2        |
    Then [整单折扣计算05A]进行整单折扣计算后,订单行分摊如下
      | orderWideDiscountSubject | itemSapCode | needShip | unitPrice | preDiscount | calculationValue | boxRatio | quantity | totalInline | preDiscountTotal | orderDiscountTotal |
      | PT00                     | 10000841    | 1        | 10        | 0           | 10               | 1        | 10       | 100         | 0                | 1.64               |
      | PT04                     | 10000289    | 1        | 5         | 0           | 5                | 1        | 2        | 10          | 0                | 0.16               |
      | PT00                     | 10000456    | 1        | 20        | 0           | 20               | 1        | 10       | 200         | 0                | 3.28               |
      | PT00                     | 10000556    | 1        | 30        | 0           | 30               | 1        | 10       | 300         | 0                | 4.92               |
    Examples: 基础信息
      | saleOrgSapCode | regionCode | customerSapCode | regionName | customerName | shippingRecipient | shippingTelephone | shippingAddress |


  @discountCalculation04
  Scenario Outline: [场景5B]当前订单的商品无预折折扣，且使用整单折扣，有相同原品，预折折扣和整单折扣计算值正确（发货赠品行参与计算）。
  "[主要测试点]
  1. 原品行无预折折扣，赠品行无预折折扣
  2. 订单使用整单折扣（1个活动项目，1个分配计划）
  3. 全部行均参与计算（预折折扣 < 总折扣）
  4. 有相同原品"
    Given [整单折扣计算05B]当前存在如下客户信息
      | saleOrgSapCode   | regionCode   | customerSapCode   | regionName   | customerName   | shippingAddress   | shippingRecipient   | shippingTelephone   |
      | <saleOrgSapCode> | <regionCode> | <customerSapCode> | <regionName> | <customerName> | <shippingAddress> | <shippingRecipient> | <shippingTelephone> |
    When [整单折扣计算05B]当前存在如下的预折商品信息
      | itemSapCode | currentPrice | calculationType  | calculationValue | preType               | effectiveState | distributionChannelCode | distributionChannelName |
      | 10000841    | 10           | DISCOUNTED_PRICE | 8                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000265    | 6            | DISCOUNTED_PRICE | 5                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000289    | 5            | DISCOUNTED_PRICE | 4.5              | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000556    | 30           | DISCOUNTED_PRICE | 27               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000456    | 20           | DISCOUNTED_PRICE | 18               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000835    | 40           | DISCOUNTED_PRICE | 32               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000633    | 50           | DISCOUNTED_PRICE | 40               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000478    | 17           | DISCOUNTED_PRICE | 13               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
    And [整单折扣计算05B]当前存在如下的有效预折折扣信息
      | itemSapCode | effectiveState |
      | 10000841    | DISABLE        |
      | 10000456    | DISABLE        |
      | 10000556    | DISABLE        |
      | 10000289    | DISABLE        |
    And [整单折扣计算05B]当前存在如下的整单折扣申请单信息
      | projectType | module |
      | PT04        | KM03   |
    And [整单折扣计算05B]当前存在如下的整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 10000265    | 100      | 盒     |
      | 10000289    | 100      | 盒     |
    And [整单折扣计算05B]当前存在如下的整单折扣分配计划信息
    And [整单折扣计算05B]当前存在如下的整单折扣分配计划详细信息
      | originalItemSapCode | originalDistributionChannelCode | giftItemSapCode | giftDistributionChannelCode | allocationQuantity | originalQuantity | giftQuantity | initialBonusQuantity | needShip | invalidStatus | unit | originalDistributionChannelName | giftDistributionChannelName |
      |                     |                                 | 10000289        | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      |
    And [整单折扣计算05B]添加商品
      | itemSapCode | quantity |
      | 10000841    | 10       |
      | 10000556    | 10       |
      | 10000456    | 10       |
    And [整单折扣计算05B]选择项目
      | itemSapCode | quantity |
      | 10000289    | 2        |
    Then [整单折扣计算05B]进行整单折扣计算后,订单行分摊如下
      | orderWideDiscountSubject | itemSapCode | needShip | unitPrice | quantity | preDiscount | calculationValue | boxRatio | totalInline | preDiscountTotal | orderDiscountTotal |
      | PT00                     | 10000841    | 1        | 10        | 4        | 0           | 10               | 1        | 40          | 0                | 0.66               |
      | PT00                     | 10000841    | 1        | 10        | 6        | 0           | 10               | 1        | 60          | 0                | 0.98               |
      | PT04                     | 10000289    | 1        | 5         | 2        | 0           | 5                | 1        | 10          | 0                | 0.16               |
      | PT00                     | 10000456    | 1        | 20        | 10       | 0           | 20               | 1        | 200         | 0                | 3.28               |
      | PT00                     | 10000556    | 1        | 30        | 2        | 0           | 30               | 1        | 60          | 0                | 0.98               |
      | PT00                     | 10000556    | 1        | 30        | 3        | 0           | 30               | 1        | 90          | 0                | 1.48               |
      | PT00                     | 10000556    | 1        | 30        | 5        | 0           | 30               | 1        | 150         | 0                | 2.46               |
    Examples: 基础信息
      | saleOrgSapCode | regionCode | customerSapCode | regionName | customerName | shippingRecipient | shippingTelephone | shippingAddress |


  @discountCalculation05
  Scenario Outline: [场景6]当前订单的商品有预折折扣，且使用整单折扣，预折折扣和整单折扣计算值正确（部分行参与计算: 首行 预折折扣 > 总折扣，不参与计算; 发货赠品行参与计算）。
  "[主要测试点]
  1. 原品行有预折折扣，赠品行有预折折扣
  2. 订单使用整单折扣（1个活动项目，1个分配计划）
  3. 部分行参与计算（首行 预折折扣 > 总折扣，不参与计算）"
    Given [整单折扣计算06]当前存在如下客户信息
      | saleOrgSapCode   | regionCode   | customerSapCode   | regionName   | customerName   | shippingAddress   | shippingRecipient   | shippingTelephone   |
      | <saleOrgSapCode> | <regionCode> | <customerSapCode> | <regionName> | <customerName> | <shippingAddress> | <shippingRecipient> | <shippingTelephone> |
    When [整单折扣计算06]当前存在如下的预折商品信息
      | itemSapCode | currentPrice | calculationType  | calculationValue | preType               | effectiveState | distributionChannelCode | distributionChannelName |
      | 10000841    | 10           | DISCOUNTED_PRICE | 7                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000265    | 6            | DISCOUNTED_PRICE | 5                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000289    | 5            | DISCOUNTED_PRICE | 4.5              | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000556    | 30           | DISCOUNTED_PRICE | 27               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000456    | 20           | DISCOUNTED_PRICE | 18               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000835    | 40           | DISCOUNTED_PRICE | 32               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000633    | 50           | DISCOUNTED_PRICE | 40               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000478    | 17           | DISCOUNTED_PRICE | 13               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
    And [整单折扣计算06]当前存在如下的整单折扣申请单信息
      | projectType | module |
      | PT04        | KM03   |
    And [整单折扣计算06]当前存在如下的整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 10000265    | 100      | 盒     |
      | 10000289    | 100      | 盒     |
    And [整单折扣计算06]当前存在如下的整单折扣分配计划信息
    And [整单折扣计算06]当前存在如下的整单折扣分配计划详细信息
      | originalItemSapCode | originalDistributionChannelCode | giftItemSapCode | giftDistributionChannelCode | allocationQuantity | originalQuantity | giftQuantity | initialBonusQuantity | needShip | invalidStatus | unit | originalDistributionChannelName | giftDistributionChannelName |
      |                     |                                 | 10000289        | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      |
    And [整单折扣计算06]添加商品
      | itemSapCode | quantity |
      | 10000841    | 10       |
      | 10000556    | 10       |
      | 10000456    | 5        |
    And [整单折扣计算06]选择项目
      | itemSapCode | quantity |
      | 10000289    | 10       |
    Then [整单折扣计算06]进行整单折扣计算后,订单行分摊如下
      | orderWideDiscountSubject | itemSapCode | needShip | unitPrice | quantity | preDiscount | calculationValue | boxRatio | totalInline | preDiscountTotal | orderDiscountTotal |
      | PT00                     | 10000841    | 1        | 10        | 10       | 3           | 7                | 1        | 100         | 30               | 0                  |
      | PT04                     | 10000289    | 1        | 5         | 10       | 0.5         | 4.5              | 1        | 50          | 5                | 5                  |
      | PT00                     | 10000556    | 1        | 30        | 10       | 3           | 27               | 1        | 300         | 30               | 30                 |
      | PT00                     | 10000456    | 1        | 20        | 5        | 2           | 18               | 1        | 100         | 10               | 10                 |
    Examples: 基础信息
      | saleOrgSapCode | regionCode | customerSapCode | regionName | customerName | shippingRecipient | shippingTelephone | shippingAddress |


  @discountCalculation06
  Scenario Outline: [场景7]当前订单的商品有预折折扣，且使用整单折扣，预折折扣和整单折扣计算值正确（部分行不参与计算（中间行 预折折扣=总折扣 不参与计算））。
  "[主要测试点]
  1. 原品行有预折折扣，赠品行无预折折扣
  2. 订单使用整单折扣（1个活动项目，1个分配计划）
  3. 原品行不参与计算（中间行 预折折扣=总折扣 不参与计算）"
    Given [整单折扣计算07]当前存在如下客户信息
      | saleOrgSapCode   | regionCode   | customerSapCode   | regionName   | customerName   | shippingAddress   | shippingRecipient   | shippingTelephone   |
      | <saleOrgSapCode> | <regionCode> | <customerSapCode> | <regionName> | <customerName> | <shippingAddress> | <shippingRecipient> | <shippingTelephone> |
    When [整单折扣计算07]当前存在如下的预折商品信息
      | itemSapCode | currentPrice | calculationType  | calculationValue | preType               | effectiveState | distributionChannelCode | distributionChannelName |
      | 10000841    | 10           | DISCOUNTED_PRICE | 8                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000265    | 6            | DISCOUNTED_PRICE | 5                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000289    | 5            | DISCOUNTED_PRICE | 4.5              | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000556    | 30           | DISCOUNTED_PRICE | 27               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000456    | 20           | DISCOUNTED_PRICE | 18               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000835    | 40           | DISCOUNTED_PRICE | 32               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000633    | 50           | DISCOUNTED_PRICE | 40               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000478    | 17           | DISCOUNTED_PRICE | 13               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
    And [整单折扣计算07]当前存在如下的预折折扣信息
      | itemSapCode | calculationValue | effectiveState |
      | 10000841    | 8                | ENABLE         |
      | 10000289    | 4.5              | DISABLE        |
      | 10000456    | 15               | ENABLE         |
      | 10000835    | 32               | ENABLE         |
    And [整单折扣计算07]当前存在如下的整单折扣申请单信息
      | projectType | module |
      | PT04        | KM03   |
    And [整单折扣计算07]当前存在如下的整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 10000265    | 100      | 盒     |
      | 10000289    | 100      | 盒     |
    And [整单折扣计算07]当前存在如下的整单折扣分配计划信息
    And [整单折扣计算07]当前存在如下的整单折扣分配计划详细信息
      | originalItemSapCode | originalDistributionChannelCode | giftItemSapCode | giftDistributionChannelCode | allocationQuantity | originalQuantity | giftQuantity | initialBonusQuantity | needShip | invalidStatus | unit | originalDistributionChannelName | giftDistributionChannelName |
      |                     |                                 | 10000289        | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      |
    And [整单折扣计算07]添加商品
      | itemSapCode | quantity |
      | 10000841    | 10       |
      | 10000456    | 3        |
      | 10000835    | 5        |
    And [整单折扣计算07]选择项目
      | itemSapCode | quantity |
      | 10000289    | 4        |
    Then [整单折扣计算07]进行整单折扣计算后,订单行分摊如下
      | orderWideDiscountSubject | itemSapCode | needShip | unitPrice | quantity | preDiscount | calculationValue | boxRatio | totalInline | preDiscountTotal | orderDiscountTotal |
      | PT00                     | 10000841    | 1        | 10        | 10       | 2           | 8                | 1        | 100         | 20               | 5                  |
      | PT04                     | 10000289    | 1        | 5         | 4        | 0           | 5                | 1        | 20          | 0                | 5                  |
      | PT00                     | 10000456    | 1        | 20        | 3        | 5           | 15               | 1        | 60          | 15               | 0                  |
      | PT00                     | 10000835    | 1        | 40        | 5        | 8           | 32               | 1        | 200         | 40               | 10                 |
    Examples: 基础信息
      | saleOrgSapCode | regionCode | customerSapCode | regionName | customerName | shippingRecipient | shippingTelephone | shippingAddress |


  @discountCalculation07
  Scenario Outline: [场景8-1]当前订单的商品有预折折扣，且使用整单折扣（ 整单折扣计算保留2位小数-四舍 ），预折折扣和整单折扣计算值正确（全部行均参与计算（ 30% < 总折扣 ＜32% ））。
  "[主要测试点]
  1. 原品行有预折折扣，赠品行有预折折扣
  2. 订单使用整单折扣（1个活动项目，1个分配计划）
  3. 全部行均参与计算（ 30% < 总折扣 ＜32% ）
  4. 整单折扣金额比例计算方式 > 整单折扣-（n-1)行整单折扣金额之和方式（ 整单折扣计算保留2位小数-四舍 ）"
    Given [整单折扣计算08]当前存在如下客户信息
      | saleOrgSapCode   | regionCode   | customerSapCode   | regionName   | customerName   | shippingAddress   | shippingRecipient   | shippingTelephone   |
      | <saleOrgSapCode> | <regionCode> | <customerSapCode> | <regionName> | <customerName> | <shippingAddress> | <shippingRecipient> | <shippingTelephone> |
    When [整单折扣计算08]当前存在如下的预折商品信息
      | itemSapCode | currentPrice | calculationType  | calculationValue | preType               | effectiveState | distributionChannelCode | distributionChannelName |
      | 10000841    | 10           | DISCOUNTED_PRICE | 8                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000265    | 6            | DISCOUNTED_PRICE | 5                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000289    | 5            | DISCOUNTED_PRICE | 4.5              | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000556    | 30           | DISCOUNTED_PRICE | 27               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000456    | 20           | DISCOUNTED_PRICE | 18               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000835    | 40           | DISCOUNTED_PRICE | 32               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000633    | 50           | DISCOUNTED_PRICE | 40               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000478    | 17           | DISCOUNTED_PRICE | 13               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
    And [整单折扣计算08]当前存在如下的预折折扣信息
      | itemSapCode | calculationValue | effectiveState |
      | 10000841    | 9.7              | ENABLE         |
      | 10000289    | 4.5              | ENABLE         |
      | 10000456    | 19               | ENABLE         |
      | 10000835    | 27               | ENABLE         |
    And [整单折扣计算08]当前存在如下的整单折扣申请单信息
      | projectType | module |
      | PT06        | KM03   |
    And [整单折扣计算08]当前存在如下的整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 10000265    | 100      | 盒     |
      | 10000289    | 100      | 盒     |
    And [整单折扣计算08]当前存在如下的整单折扣分配计划信息
    And [整单折扣计算08]当前存在如下的整单折扣分配计划详细信息
      | originalItemSapCode | originalDistributionChannelCode | giftItemSapCode | giftDistributionChannelCode | allocationQuantity | originalQuantity | giftQuantity | initialBonusQuantity | needShip | invalidStatus | unit | originalDistributionChannelName | giftDistributionChannelName |
      |                     |                                 | 10000289        | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      |
    And [整单折扣计算08]添加商品
      | itemSapCode | quantity |
      | 10000841    | 10       |
      | 10000456    | 10       |
      | 10000556    | 10       |
    And [整单折扣计算08]选择项目
      | itemSapCode | quantity |
      | 10000289    | 4        |
    Then [整单折扣计算08]进行整单折扣计算后,订单行分摊如下
      | orderWideDiscountSubject | itemSapCode | needShip | unitPrice | quantity | preDiscount | calculationValue | boxRatio | totalInline | preDiscountTotal | orderDiscountTotal |
      | PT00                     | 10000841    | 1        | 10        | 10       | 0.3         | 9.7              | 1        | 100         | 3                | 7.16               |
      | PT06                     | 10000289    | 1        | 5         | 4        | 0.5         | 4.5              | 1        | 20          | 2                | 0.03               |
      | PT00                     | 10000456    | 1        | 20        | 10       | 1           | 19               | 1        | 200         | 10               | 10.32              |
      | PT00                     | 10000556    | 1        | 30        | 10       | 3           | 27               | 1        | 300         | 30               | 0.49               |
    Examples: 基础信息
      | saleOrgSapCode | regionCode | customerSapCode | regionName | customerName | shippingRecipient | shippingTelephone | shippingAddress |


  @discountCalculation08
  Scenario Outline: [场景8-2]当前订单的商品有预折折扣，且使用整单折扣（ 整单折扣计算保留2位小数-五入 ），预折折扣和整单折扣计算值正确（全部行均参与计算（ 30% < 总折扣 ＜32% ））。
  "[主要测试点]
  1. 原品行有预折折扣，赠品行有预折折扣
  2. 订单使用整单折扣（1个活动项目，1个分配计划）
  3. 全部行均参与计算（ 30% < 总折扣 ＜32% ）
  4. 整单折扣金额比例计算方式 > 整单折扣-（n-1)行整单折扣金额之和方式（ 整单折扣计算保留2位小数-五入 ）"
    Given [整单折扣计算09]当前存在如下客户信息
      | saleOrgSapCode   | regionCode   | customerSapCode   | regionName   | customerName   | shippingAddress   | shippingRecipient   | shippingTelephone   |
      | <saleOrgSapCode> | <regionCode> | <customerSapCode> | <regionName> | <customerName> | <shippingAddress> | <shippingRecipient> | <shippingTelephone> |
    When [整单折扣计算09]当前存在如下的预折商品信息
      | itemSapCode | currentPrice | calculationType  | calculationValue | preType               | effectiveState | distributionChannelCode | distributionChannelName |
      | 10000841    | 10           | DISCOUNTED_PRICE | 8                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000265    | 6            | DISCOUNTED_PRICE | 5                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000289    | 5            | DISCOUNTED_PRICE | 4.5              | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000556    | 30           | DISCOUNTED_PRICE | 27               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000456    | 20           | DISCOUNTED_PRICE | 18               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000835    | 40           | DISCOUNTED_PRICE | 32               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000633    | 50           | DISCOUNTED_PRICE | 40               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000478    | 17           | DISCOUNTED_PRICE | 13               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
    And [整单折扣计算09]当前存在如下的预折折扣信息
      | itemSapCode | calculationValue | effectiveState |
      | 10000841    | 8                | ENABLE         |
      | 10000456    | 15               | ENABLE         |
      | 10000556    | 27               | ENABLE         |
      | 10000265    | 5                | ENABLE         |
    And [整单折扣计算09]当前存在如下的整单折扣申请单信息
      | projectType | module |
      | PT02        | KM03   |
    And [整单折扣计算09]当前存在如下的整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 10000265    | 100      | 盒     |
    And [整单折扣计算09]当前存在如下的整单折扣分配计划信息
    And [整单折扣计算09]当前存在如下的整单折扣分配计划详细信息
      | originalItemSapCode | originalDistributionChannelCode | giftItemSapCode | giftDistributionChannelCode | allocationQuantity | originalQuantity | giftQuantity | initialBonusQuantity | needShip | invalidStatus | unit | originalDistributionChannelName | giftDistributionChannelName | lot_no      | production_date | expiration_date |
      |                     |                                 | 10000265        | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | AT_LTY_0001 | 2024-05-01      | 2025-12-31      |
    And [整单折扣计算09]添加商品
      | itemSapCode | quantity |
      | 10000841    | 10       |
      | 10000456    | 3        |
      | 10000556    | 10       |
    And [整单折扣计算09]选择项目
      | itemSapCode | quantity |
      | 10000265    | 20       |
    Then [整单折扣计算09]进行整单折扣计算后,订单行分摊如下
      | orderWideDiscountSubject | itemSapCode | needShip | unitPrice | quantity | preDiscount | calculationValue | boxRatio | totalInline | preDiscountTotal | orderDiscountTotal |
      | PT00                     | 10000841    | 1        | 10        | 10       | 2           | 8                | 1        | 100         | 20               | 11.9               |
      | PT00                     | 10000456    | 1        | 20        | 3        | 5           | 15               | 1        | 60          | 15               | 4.14               |
      | PT00                     | 10000556    | 1        | 30        | 10       | 3           | 27               | 1        | 300         | 30               | 65.69              |
      | PT02                     | 10000265    | 1        | 6         | 20       | 1           | 5                | 1        | 120         | 20               | 18.27              |
    Examples: 基础信息
      | saleOrgSapCode | regionCode | customerSapCode | regionName | customerName | shippingRecipient | shippingTelephone | shippingAddress |


  @discountCalculation09
  Scenario Outline: [场景8-3]当前订单的商品有预折折扣，且使用整单折扣（1个活动项目，1个分配计划），预折折扣和整单折扣计算值正确（ 总折扣 > 32% ）。
  "[主要测试点]
  1. 原品行有预折折扣，赠品行有预折折扣
  2. 订单使用整单折扣（1个活动项目，1个分配计划）
  3. 总折扣 > 32%
  4. 全部订单行整单折扣保留2位（四舍）"
    Given [整单折扣计算10]当前存在如下客户信息
      | saleOrgSapCode   | regionCode   | customerSapCode   | regionName   | customerName   | shippingAddress   | shippingRecipient   | shippingTelephone   |
      | <saleOrgSapCode> | <regionCode> | <customerSapCode> | <regionName> | <customerName> | <shippingAddress> | <shippingRecipient> | <shippingTelephone> |
    When [整单折扣计算10]当前存在如下的预折商品信息
      | itemSapCode | currentPrice | calculationType  | calculationValue | preType               | effectiveState | distributionChannelCode | distributionChannelName |
      | 10000841    | 10           | DISCOUNTED_PRICE | 8                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000265    | 6            | DISCOUNTED_PRICE | 5                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000289    | 5            | DISCOUNTED_PRICE | 4.5              | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000556    | 30           | DISCOUNTED_PRICE | 27               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000456    | 20           | DISCOUNTED_PRICE | 18               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000835    | 40           | DISCOUNTED_PRICE | 32               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000633    | 50           | DISCOUNTED_PRICE | 40               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000478    | 17           | DISCOUNTED_PRICE | 13               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
    And [整单折扣计算10]当前存在如下的预折折扣信息
      | itemSapCode | calculationValue | effectiveState |
      | 10000841    | 8                | ENABLE         |
      | 10000456    | 15               | ENABLE         |
      | 10000556    | 27               | ENABLE         |
      | 10000265    | 5                | DISABLE        |
    And [整单折扣计算10]当前存在如下的整单折扣申请单信息
      | projectType | module |
      | PT04        | KM03   |
    And [整单折扣计算10]当前存在如下的整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 10000265    | 100      | 盒     |
      | 10000289    | 100      | 盒     |
    And [整单折扣计算10]当前存在如下的整单折扣分配计划信息
    And [整单折扣计算10]当前存在如下的整单折扣分配计划详细信息
      | originalItemSapCode | originalDistributionChannelCode | giftItemSapCode | giftDistributionChannelCode | allocationQuantity | originalQuantity | giftQuantity | initialBonusQuantity | needShip | invalidStatus | unit | originalDistributionChannelName | giftDistributionChannelName |
      |                     |                                 | 10000289        | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      |
    And [整单折扣计算10]添加商品
      | itemSapCode | quantity |
      | 10000841    | 10       |
      | 10000456    | 3        |
      | 10000556    | 10       |
    And [整单折扣计算10]选择项目
      | itemSapCode | quantity |
      | 10000265    | 24       |
    Then [整单折扣计算10]进行整单折扣计算后,订单行分摊如下
      | orderWideDiscountSubject | itemSapCode | needShip | unitPrice | quantity | preDiscount | calculationValue | boxRatio | totalInline | preDiscountTotal | orderDiscountTotal |
      | PT00                     | 10000841    | 1        | 10        | 10       | 2           | 8                | 1        | 100         | 20               | 14.6               |
      | PT00                     | 10000456    | 1        | 20        | 3        | 5           | 15               | 1        | 60          | 15               | 5.76               |
      | PT00                     | 10000556    | 1        | 30        | 10       | 3           | 27               | 1        | 300         | 30               | 73.81              |
      | PT04                     | 10000265    | 1        | 6         | 24       | 0           | 6                | 1        | 144         | 0                | 49.83              |
    Examples: 基础信息
      | saleOrgSapCode | regionCode | customerSapCode | regionName | customerName | shippingRecipient | shippingTelephone | shippingAddress |


  @discountCalculation10
  Scenario Outline: [场景8-4]当前订单的商品有预折折扣，且使用整单折扣（1个活动项目，1个分配计划），预折折扣和整单折扣计算值正确（ 总折扣 = 30% ）。
  "[主要测试点]
  1. 原品行有预折折扣，赠品行有预折折扣
  2. 订单使用整单折扣（1个活动项目，1个分配计划）
  3. 赠品行不进行计算（ 赠品行 预折折扣 > 总折扣）
  4. 总折扣 = 30%"
    Given [整单折扣计算11]当前存在如下客户信息
      | saleOrgSapCode   | regionCode   | customerSapCode   | regionName   | customerName   | shippingAddress   | shippingRecipient   | shippingTelephone   |
      | <saleOrgSapCode> | <regionCode> | <customerSapCode> | <regionName> | <customerName> | <shippingAddress> | <shippingRecipient> | <shippingTelephone> |
    When [整单折扣计算11]当前存在如下的预折商品信息
      | itemSapCode | currentPrice | calculationType  | calculationValue | preType               | effectiveState | distributionChannelCode | distributionChannelName |
      | 10000841    | 10           | DISCOUNTED_PRICE | 8                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000265    | 6            | DISCOUNTED_PRICE | 5                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000289    | 5            | DISCOUNTED_PRICE | 4.5              | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000556    | 30           | DISCOUNTED_PRICE | 27               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000456    | 20           | DISCOUNTED_PRICE | 18               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000835    | 40           | DISCOUNTED_PRICE | 32               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000633    | 50           | DISCOUNTED_PRICE | 40               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000478    | 17           | DISCOUNTED_PRICE | 13               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
    And [整单折扣计算11]当前存在如下的预折折扣信息
      | itemSapCode | calculationValue | effectiveState |
      | 10000841    | 8                | DISABLE        |
      | 10000265    | 5                | ENABLE         |
    And [整单折扣计算11]当前存在如下的整单折扣申请单信息
      | projectType | module |
      | PT02        | KM03   |
    And [整单折扣计算11]当前存在如下的整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 10000265    | 100      | 盒     |
    And [整单折扣计算11]当前存在如下的整单折扣分配计划信息
    And [整单折扣计算11]当前存在如下的整单折扣分配计划详细信息
      | originalItemSapCode | originalDistributionChannelCode | giftItemSapCode | giftDistributionChannelCode | allocationQuantity | originalQuantity | giftQuantity | initialBonusQuantity | needShip | invalidStatus | unit | originalDistributionChannelName | giftDistributionChannelName | lot_no      | production_date | expiration_date |
      |                     |                                 | 10000265        | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | AT_LTY_0001 | 2024-05-01      | 2025-12-31      |
    And [整单折扣计算11]添加商品
      | itemSapCode | quantity |
      | 10000841    | 7        |
    And [整单折扣计算11]选择项目
      | itemSapCode | quantity |
      | 10000265    | 5        |
    Then [整单折扣计算11]进行整单折扣计算后,订单行分摊如下
      | orderWideDiscountSubject | itemSapCode | needShip | unitPrice | quantity | preDiscount | calculationValue | boxRatio | totalInline | preDiscountTotal | orderDiscountTotal |
      | PT02                     | 10000265    | 1        | 6         | 5        | 1           | 5                | 1        | 30          | 5                | 4                  |
      | PT00                     | 10000841    | 1        | 10        | 7        | 0           | 10               | 1        | 70          | 0                | 21                 |
    Examples: 基础信息
      | saleOrgSapCode | regionCode | customerSapCode | regionName | customerName | shippingRecipient | shippingTelephone | shippingAddress |


  @discountCalculation11
  Scenario Outline: [场景8-5]当前订单的商品有预折折扣，且使用整单折扣（多种类型项目活动，不同分配计划），预折折扣和整单折扣计算值正确（ 总折扣 = 32% ）。
  "[主要测试点]
  1. 原品行无预折折扣，赠品行无预折折扣
  2. 订单使用整单折扣（多种项目活动：纯赠，零头药）
  3. 全部行均参与计算（ 总折扣 = 32% ）"
    Given [整单折扣计算12]当前存在如下客户信息
      | saleOrgSapCode   | regionCode   | customerSapCode   | regionName   | customerName   | shippingAddress   | shippingRecipient   | shippingTelephone   |
      | <saleOrgSapCode> | <regionCode> | <customerSapCode> | <regionName> | <customerName> | <shippingAddress> | <shippingRecipient> | <shippingTelephone> |
    When [整单折扣计算12]当前存在如下的预折商品信息
      | itemSapCode | currentPrice | calculationType  | calculationValue | preType               | effectiveState | distributionChannelCode | distributionChannelName |
      | 10000841    | 10           | DISCOUNTED_PRICE | 8                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000265    | 6            | DISCOUNTED_PRICE | 5                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000289    | 5            | DISCOUNTED_PRICE | 4.5              | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000556    | 30           | DISCOUNTED_PRICE | 27               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000456    | 20           | DISCOUNTED_PRICE | 18               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000835    | 40           | DISCOUNTED_PRICE | 32               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000633    | 50           | DISCOUNTED_PRICE | 40               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000478    | 17           | DISCOUNTED_PRICE | 13               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
    And [整单折扣计算12]当前存在如下的有效预折折扣信息
      | itemSapCode | effectiveState |
      | 10000289    | DISABLE        |
      | 10000265    | DISABLE        |
      | 10000478    | DISABLE        |
    And [整单折扣计算12]当前存在如下的[纯赠]整单折扣申请单信息
      | projectType | module |
      | PT04        | KM03   |
    And [整单折扣计算12]当前存在如下的[纯赠]整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 10000289    | 100      | 盒     |
    And [整单折扣计算12]当前存在如下的[纯赠]整单折扣分配计划信息
    And [整单折扣计算12]当前存在如下的[纯赠]整单折扣分配计划详细信息
      | originalItemSapCode | originalDistributionChannelCode | giftItemSapCode | giftDistributionChannelCode | allocationQuantity | originalQuantity | giftQuantity | initialBonusQuantity | needShip | invalidStatus | unit | originalDistributionChannelName | giftDistributionChannelName |
      |                     |                                 | 10000289        | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      |
    And [整单折扣计算12]当前存在如下的[零头药]整单折扣申请单信息
      | projectType | module |
      | PT02        | KM03   |
    And [整单折扣计算12]当前存在如下的[零头药]整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 10000265    | 100      | 盒     |
    And [整单折扣计算12]当前存在如下的[零头药]整单折扣分配计划信息
    And [整单折扣计算12]当前存在如下的[零头药]整单折扣分配计划详细信息
      | originalItemSapCode | originalDistributionChannelCode | giftItemSapCode | giftDistributionChannelCode | allocationQuantity | originalQuantity | giftQuantity | initialBonusQuantity | needShip | invalidStatus | unit | originalDistributionChannelName | giftDistributionChannelName | lot_no      | production_date | expiration_date |
      |                     |                                 | 10000265        | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | AT_LTY_0001 | 2024-05-01      | 2025-12-31      |
    And [整单折扣计算12]添加商品
      | itemSapCode | quantity |
      | 10000478    | 2        |
    And [整单折扣计算12]选择项目
      | itemSapCode | quantity | projectType |
      | 10000289    | 2        | PT04        |
      | 10000265    | 1        | PT02        |
    Then [整单折扣计算12]进行整单折扣计算后,订单行分摊如下
      | orderWideDiscountSubject | itemSapCode | needShip | unitPrice | quantity | preDiscount | calculationValue | boxRatio | totalInline | preDiscountTotal | orderDiscountTotal |
      | PT04                     | 10000289    | 1        | 5         | 2        | 0           | 5                | 1        | 10          | 0                | 3.2                |
      | PT02                     | 10000265    | 1        | 6         | 1        | 0           | 6                | 1        | 6           | 0                | 1.92               |
      | PT00                     | 10000478    | 1        | 17        | 2        | 0           | 17               | 1        | 34          | 0                | 10.88              |
    Examples: 基础信息
      | saleOrgSapCode | regionCode | customerSapCode | regionName | customerName | shippingRecipient | shippingTelephone | shippingAddress |


  @discountCalculation12
  Scenario Outline: [场景8-6]当前订单的商品有预折折扣，且使用整单折扣（多种类型项目活动，不同分配计划），首行、尾行和部分中间行不参与计算（原品行部分参与计算，赠品行部分参与计算），预折折扣和整单折扣计算值正确（按照第二轮占比计算结果）。
  "[主要测试点]
  1. 原品行有预折折扣行和无折扣行均有，赠品行有预折折扣行和无折扣行均有
  2. 订单使用整单折扣（多种项目活动：纯赠，零头药）
  3. 首行、尾行和部分中间行不参与计算（原品行部分参与计算，赠品行部分参与计算）"
    Given [整单折扣计算13]当前存在如下客户信息
      | saleOrgSapCode   | regionCode   | customerSapCode   | regionName   | customerName   | shippingAddress   | shippingRecipient   | shippingTelephone   |
      | <saleOrgSapCode> | <regionCode> | <customerSapCode> | <regionName> | <customerName> | <shippingAddress> | <shippingRecipient> | <shippingTelephone> |
    When [整单折扣计算13]当前存在如下的预折商品信息
      | itemSapCode | currentPrice | calculationType  | calculationValue | preType               | effectiveState | distributionChannelCode | distributionChannelName |
      | 10000841    | 10           | DISCOUNTED_PRICE | 8                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000265    | 6            | DISCOUNTED_PRICE | 5                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000289    | 5            | DISCOUNTED_PRICE | 4.5              | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000556    | 30           | DISCOUNTED_PRICE | 27               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000456    | 20           | DISCOUNTED_PRICE | 18               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000835    | 40           | DISCOUNTED_PRICE | 32               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000633    | 50           | DISCOUNTED_PRICE | 40               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000478    | 17           | DISCOUNTED_PRICE | 13               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
    And [整单折扣计算13]当前存在如下的预折折扣信息
      | itemSapCode | calculationValue | effectiveState |
      | 10000841    | 8                | ENABLE         |
      | 10000835    | 32               | DISABLE        |
      | 10000265    | 5                | ENABLE         |
      | 10000456    | 18               | DISABLE        |
      | 10000289    | 4.5              | DISABLE        |
      | 10000633    | 40               | ENABLE         |
    And [整单折扣计算13]当前存在如下的[纯赠]整单折扣申请单信息
      | projectType | module |
      | PT04        | KM03   |
    And [整单折扣计算13]当前存在如下的[纯赠]整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 10000289    | 100      | 盒     |
    And [整单折扣计算13]当前存在如下的[纯赠]整单折扣分配计划信息
    And [整单折扣计算13]当前存在如下的[纯赠]整单折扣分配计划详细信息
      | originalItemSapCode | originalDistributionChannelCode | giftItemSapCode | giftDistributionChannelCode | allocationQuantity | originalQuantity | giftQuantity | initialBonusQuantity | needShip | invalidStatus | unit | originalDistributionChannelName | giftDistributionChannelName |
      |                     |                                 | 10000289        | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      |
    And [整单折扣计算13]当前存在如下的[零头药]整单折扣申请单信息
      | projectType | module |
      | PT02        | KM03   |
    And [整单折扣计算13]当前存在如下的[零头药]整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 10000265    | 100      | 盒     |
    And [整单折扣计算13]当前存在如下的[零头药]整单折扣分配计划信息
    And [整单折扣计算13]当前存在如下的[零头药]整单折扣分配计划详细信息
      | originalItemSapCode | originalDistributionChannelCode | giftItemSapCode | giftDistributionChannelCode | allocationQuantity | originalQuantity | giftQuantity | initialBonusQuantity | needShip | invalidStatus | unit | originalDistributionChannelName | giftDistributionChannelName | lot_no      | production_date | expiration_date |
      |                     |                                 | 10000289        | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | AT_LTY_0001 | 2024-05-01      | 2025-12-31      |
    And [整单折扣计算13]添加商品
      | itemSapCode | quantity |
      | 10000841    | 10       |
      | 10000835    | 5        |
      | 10000456    | 15       |
      | 10000633    | 8        |
    And [整单折扣计算13]选择项目
      | itemSapCode | quantity | projectType |
      | 10000289    | 10       | PT04        |
      | 10000265    | 2        | PT02        |
    Then [整单折扣计算13]进行整单折扣计算后,订单行分摊如下
      | orderWideDiscountSubject | itemSapCode | needShip | unitPrice | quantity | preDiscount | calculationValue | boxRatio | totalInline | preDiscountTotal | orderDiscountTotal |
      | PT00                     | 10000841    | 1        | 10        | 10       | 2           | 8                | 1        | 100         | 20               | 0                  |
      | PT00                     | 10000835    | 1        | 40        | 5        | 0           | 40               | 1        | 200         | 0                | 21.82              |
      | PT02                     | 10000265    | 1        | 6         | 2        | 1           | 5                | 1        | 12          | 2                | 0                  |
      | PT00                     | 10000456    | 1        | 20        | 15       | 0           | 20               | 1        | 300         | 0                | 32.73              |
      | PT04                     | 10000289    | 1        | 5         | 10       | 0           | 5                | 1        | 50          | 0                | 5.45               |
      | PT00                     | 10000633    | 1        | 50        | 8        | 10          | 40               | 1        | 400         | 80               | 0                  |
    Examples: 基础信息
      | saleOrgSapCode | regionCode | customerSapCode | regionName | customerName | shippingRecipient | shippingTelephone | shippingAddress |


  @discountCalculation13
  Scenario Outline: [场景8-7]当前订单的商品有预折折扣，且使用整单折扣（多种类型项目活动，不同分配计划），部分中间行不参与计算（原品行部分参与计算，赠品行部分参与计算），预折折扣和整单折扣计算值正确（按照第三轮占比计算结果）。
  "[主要测试点]
  1. 原品行有预折折扣行和无折扣行均有，赠品行有预折折扣行和无折扣行均有
  2. 订单使用整单折扣（多种项目活动：纯赠，零头药）
  3. 部分中间行不参与计算（原品行部分参与计算，赠品行部分参与计算）"
    Given [整单折扣计算14]当前存在如下客户信息
      | saleOrgSapCode   | regionCode   | customerSapCode   | regionName   | customerName   | shippingAddress   | shippingRecipient   | shippingTelephone   |
      | <saleOrgSapCode> | <regionCode> | <customerSapCode> | <regionName> | <customerName> | <shippingAddress> | <shippingRecipient> | <shippingTelephone> |
    When [整单折扣计算14]当前存在如下的预折商品信息
      | itemSapCode | currentPrice | calculationType  | calculationValue | preType               | effectiveState | distributionChannelCode | distributionChannelName |
      | 10000841    | 10           | DISCOUNTED_PRICE | 8                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000265    | 6            | DISCOUNTED_PRICE | 5                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000289    | 5            | DISCOUNTED_PRICE | 4.5              | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000556    | 30           | DISCOUNTED_PRICE | 27               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000456    | 20           | DISCOUNTED_PRICE | 18               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000835    | 40           | DISCOUNTED_PRICE | 32               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000633    | 50           | DISCOUNTED_PRICE | 40               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000478    | 17           | DISCOUNTED_PRICE | 13               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
    And [整单折扣计算14]当前存在如下的预折折扣信息
      | itemSapCode | calculationValue | effectiveState |
      | 10000633    | 40               | ENABLE         |
      | 10000456    | 14               | ENABLE         |
      | 10000289    | 5                | DISABLE        |
      | 10000556    | 21.2             | ENABLE         |
      | 10000835    | 39               | ENABLE         |
      | 10000265    | 5                | ENABLE         |
      | 10000478    | 13               | ENABLE         |
      | 10000841    | 10               | DISABLE        |
    And [整单折扣计算14]当前存在如下的[纯赠]整单折扣申请单信息
      | projectType | module |
      | PT04        | KM03   |
    And [整单折扣计算14]当前存在如下的[纯赠]整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 10000478    | 100      | 盒     |
    And [整单折扣计算14]当前存在如下的[纯赠]整单折扣分配计划信息
    And [整单折扣计算14]当前存在如下的[纯赠]整单折扣分配计划详细信息
      | originalItemSapCode | originalDistributionChannelCode | giftItemSapCode | giftDistributionChannelCode | allocationQuantity | originalQuantity | giftQuantity | initialBonusQuantity | needShip | invalidStatus | unit | originalDistributionChannelName | giftDistributionChannelName |
      |                     |                                 | 10000478        | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      |
    And [整单折扣计算14]当前存在如下的[零头药]整单折扣申请单信息
      | projectType | module |
      | PT02        | KM03   |
    And [整单折扣计算14]当前存在如下的[零头药]整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 10000265    | 100      | 盒     |
    And [整单折扣计算14]当前存在如下的[零头药]整单折扣分配计划信息
    And [整单折扣计算14]当前存在如下的[零头药]整单折扣分配计划详细信息
      | originalItemSapCode | originalDistributionChannelCode | giftItemSapCode | giftDistributionChannelCode | allocationQuantity | originalQuantity | giftQuantity | initialBonusQuantity | needShip | invalidStatus | unit | originalDistributionChannelName | giftDistributionChannelName | lot_no      | production_date | expiration_date |
      |                     |                                 | 10000265        | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | AT_LTY_0001 | 2024-05-01      | 2025-12-31      |
    And [整单折扣计算14]添加商品
      | itemSapCode | quantity |
      | 10000633    | 2        |
      | 10000456    | 5        |
      | 10000289    | 1        |
      | 10000556    | 1        |
      | 10000835    | 1        |
      | 10000841    | 4        |
    And [整单折扣计算14]选择项目
      | itemSapCode | quantity | projectType |
      | 10000265    | 1        | PT04        |
      | 10000478    | 1        | PT02        |
    Then [整单折扣计算14]进行整单折扣计算后,订单行分摊如下
      | orderWideDiscountSubject | itemSapCode | needShip | unitPrice | quantity | preDiscount | calculationValue | boxRatio | totalInline | preDiscountTotal | orderDiscountTotal |
      | PT00                     | 10000633    | 1        | 50        | 2        | 10          | 40               | 1        | 100         | 20               | 0.94               |
      | PT00                     | 10000456    | 1        | 20        | 5        | 6           | 14               | 1        | 100         | 30               | 0                  |
      | PT00                     | 10000289    | 1        | 5         | 1        | 0           | 5                | 1        | 5           | 0                | 1.05               |
      | PT00                     | 10000556    | 1        | 30        | 1        | 8.8         | 21.2             | 1        | 30          | 8.8              | 0                  |
      | PT00                     | 10000835    | 1        | 40        | 1        | 1           | 39               | 1        | 40          | 1                | 7.38               |
      | PT02                     | 10000265    | 1        | 6         | 1        | 1           | 5                | 1        | 6           | 1                | 0.26               |
      | PT04                     | 10000478    | 1        | 17        | 1        | 4           | 13               | 1        | 17          | 4                | 0                  |
      | PT00                     | 10000841    | 1        | 10        | 4        | 0           | 10               | 1        | 40          | 0                | 8.37               |
    Examples: 基础信息
      | saleOrgSapCode | regionCode | customerSapCode | regionName | customerName | shippingRecipient | shippingTelephone | shippingAddress |


  @discountCalculation14
  Scenario Outline: [场景8-8]当前订单的商品有预折折扣，且使用整单折扣（多种类型项目活动，不同分配计划），尾行和中间行不参与计算（原品行部分参与计算，赠品行部分参与计算），预折折扣和整单折扣计算值正确（按照第三轮占比计算结果）。
  "[主要测试点]
  1. 原品行有预折折扣行和无折扣行均有，赠品行有预折折扣行和无折扣行均有
  2. 订单使用整单折扣（多种项目活动：纯赠，零头药）
  3. 尾行和中间行不参与计算（原品行部分参与计算，赠品行部分参与计算）"
    Given [整单折扣计算15]当前存在如下客户信息
      | saleOrgSapCode   | regionCode   | customerSapCode   | regionName   | customerName   | shippingAddress   | shippingRecipient   | shippingTelephone   |
      | <saleOrgSapCode> | <regionCode> | <customerSapCode> | <regionName> | <customerName> | <shippingAddress> | <shippingRecipient> | <shippingTelephone> |
    When [整单折扣计算15]当前存在如下的预折商品信息
      | itemSapCode | currentPrice | calculationType  | calculationValue | preType               | effectiveState | distributionChannelCode | distributionChannelName |
      | 10000841    | 10           | DISCOUNTED_PRICE | 8                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000265    | 6            | DISCOUNTED_PRICE | 5                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000289    | 5            | DISCOUNTED_PRICE | 4.5              | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000556    | 30           | DISCOUNTED_PRICE | 27               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000456    | 20           | DISCOUNTED_PRICE | 18               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000835    | 40           | DISCOUNTED_PRICE | 32               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000633    | 50           | DISCOUNTED_PRICE | 40               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000478    | 17           | DISCOUNTED_PRICE | 13               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
    And [整单折扣计算15]当前存在如下的预折折扣信息
      | itemSapCode | calculationValue | effectiveState |
      | 10000633    | 40               | ENABLE         |
      | 10000456    | 14               | ENABLE         |
      | 10000289    | 5                | DISABLE        |
      | 10000556    | 21.2             | ENABLE         |
      | 10000835    | 39               | ENABLE         |
      | 10000265    | 5                | ENABLE         |
      | 10000478    | 13               | ENABLE         |
      | 10000841    | 8                | DISABLE        |
    And [整单折扣计算15]当前存在如下的[纯赠]整单折扣申请单信息
      | projectType | module |
      | PT04        | KM03   |
    And [整单折扣计算15]当前存在如下的[纯赠]整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 10000478    | 100      | 盒     |
    And [整单折扣计算15]当前存在如下的[纯赠]整单折扣分配计划信息
    And [整单折扣计算15]当前存在如下的[纯赠]整单折扣分配计划详细信息
      | originalItemSapCode | originalDistributionChannelCode | giftItemSapCode | giftDistributionChannelCode | allocationQuantity | originalQuantity | giftQuantity | initialBonusQuantity | needShip | invalidStatus | unit | originalDistributionChannelName | giftDistributionChannelName |
      |                     |                                 | 10000478        | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      |
    And [整单折扣计算15]当前存在如下的[零头药]整单折扣申请单信息
      | projectType | module |
      | PT02        | KM03   |
    And [整单折扣计算15]当前存在如下的[零头药]整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 10000265    | 100      | 盒     |
    And [整单折扣计算15]当前存在如下的[零头药]整单折扣分配计划信息
    And [整单折扣计算15]当前存在如下的[零头药]整单折扣分配计划详细信息
      | originalItemSapCode | originalDistributionChannelCode | giftItemSapCode | giftDistributionChannelCode | allocationQuantity | originalQuantity | giftQuantity | initialBonusQuantity | needShip | invalidStatus | unit | originalDistributionChannelName | giftDistributionChannelName | lot_no      | production_date | expiration_date |
      |                     |                                 | 10000265        | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | AT_LTY_0001 | 2024-05-01      | 2025-12-31      |
    And [整单折扣计算15]添加商品
      | itemSapCode | quantity |
      | 10000633    | 2        |
      | 10000456    | 5        |
      | 10000289    | 1        |
      | 10000556    | 1        |
      | 10000835    | 1        |
      | 10000841    | 4        |
    And [整单折扣计算15]选择项目
      | itemSapCode | quantity | projectType |
      | 10000265    | 1        | PT04        |
      | 10000478    | 1        | PT02        |
    Then [整单折扣计算15]进行整单折扣计算后,订单行分摊如下
      | orderWideDiscountSubject | itemSapCode | needShip | unitPrice | quantity | preDiscount | calculationValue | boxRatio | totalInline | preDiscountTotal | orderDiscountTotal |
      | PT00                     | 10000633    | 1        | 50        | 2        | 10          | 40               | 1        | 100         | 20               | 0.94               |
      | PT00                     | 10000289    | 1        | 5         | 1        | 0           | 5                | 1        | 5           | 0                | 1.05               |
      | PT00                     | 10000835    | 1        | 40        | 1        | 1           | 39               | 1        | 40          | 1                | 7.38               |
      | PT02                     | 10000265    | 1        | 6         | 1        | 1           | 5                | 1        | 6           | 1                | 0.26               |
      | PT00                     | 10000841    | 1        | 10        | 4        | 0           | 10               | 1        | 40          | 0                | 8.37               |
      | PT04                     | 10000478    | 1        | 17        | 1        | 4           | 13               | 1        | 17          | 4                | 0                  |
      | PT00                     | 10000456    | 1        | 20        | 5        | 6           | 14               | 1        | 100         | 30               | 0                  |
      | PT00                     | 10000556    | 1        | 30        | 1        | 8.8         | 21.2             | 1        | 30          | 8.8              | 0                  |
    Examples: 基础信息
      | saleOrgSapCode | regionCode | customerSapCode | regionName | customerName | shippingRecipient | shippingTelephone | shippingAddress |


  @discountCalculation15
  Scenario Outline: [场景8-9]当前订单的商品有预折折扣，且使用整单折扣（多种类型项目活动，不同分配计划），首行和中间行不参与计算（原品行部分参与计算，赠品行部分参与计算），预折折扣和整单折扣计算值正确（按照第三轮占比计算结果）。
  "[主要测试点]
  1. 原品行有预折折扣行和无折扣行均有，赠品行有预折折扣行和无折扣行均有
  2. 订单使用整单折扣（多种项目活动：纯赠，零头药）
  3. 首行和中间行不参与计算（原品行部分参与计算，赠品行部分参与计算）"
    Given [整单折扣计算16]当前存在如下客户信息
      | saleOrgSapCode   | regionCode   | customerSapCode   | regionName   | customerName   | shippingAddress   | shippingRecipient   | shippingTelephone   |
      | <saleOrgSapCode> | <regionCode> | <customerSapCode> | <regionName> | <customerName> | <shippingAddress> | <shippingRecipient> | <shippingTelephone> |
    When [整单折扣计算16]当前存在如下的预折商品信息
      | itemSapCode | currentPrice | calculationType  | calculationValue | preType               | effectiveState | distributionChannelCode | distributionChannelName |
      | 10000841    | 10           | DISCOUNTED_PRICE | 8                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000265    | 6            | DISCOUNTED_PRICE | 5                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000289    | 5            | DISCOUNTED_PRICE | 4.5              | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000556    | 30           | DISCOUNTED_PRICE | 27               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000456    | 20           | DISCOUNTED_PRICE | 18               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000835    | 40           | DISCOUNTED_PRICE | 32               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000633    | 50           | DISCOUNTED_PRICE | 40               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000478    | 17           | DISCOUNTED_PRICE | 13               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
    And [整单折扣计算16]当前存在如下的预折折扣信息
      | itemSapCode | calculationValue | effectiveState |
      | 10000633    | 40               | ENABLE         |
      | 10000456    | 14               | ENABLE         |
      | 10000289    | 5                | DISABLE        |
      | 10000556    | 21.2             | ENABLE         |
      | 10000835    | 39               | ENABLE         |
      | 10000265    | 5                | ENABLE         |
      | 10000478    | 13               | ENABLE         |
      | 10000841    | 10               | DISABLE        |
    And [整单折扣计算16]当前存在如下的[纯赠]整单折扣申请单信息
      | projectType | module |
      | PT04        | KM03   |
    And [整单折扣计算16]当前存在如下的[纯赠]整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 10000478    | 100      | 盒     |
    And [整单折扣计算16]当前存在如下的[纯赠]整单折扣分配计划信息
    And [整单折扣计算16]当前存在如下的[纯赠]整单折扣分配计划详细信息
      | originalItemSapCode | originalDistributionChannelCode | giftItemSapCode | giftDistributionChannelCode | allocationQuantity | originalQuantity | giftQuantity | initialBonusQuantity | needShip | invalidStatus | unit | originalDistributionChannelName | giftDistributionChannelName |
      |                     |                                 | 10000478        | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      |
    And [整单折扣计算16]当前存在如下的[零头药]整单折扣申请单信息
      | projectType | module |
      | PT02        | KM03   |
    And [整单折扣计算16]当前存在如下的[零头药]整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 10000265    | 100      | 盒     |
    And [整单折扣计算16]当前存在如下的[零头药]整单折扣分配计划信息
    And [整单折扣计算16]当前存在如下的[零头药]整单折扣分配计划详细信息
      | originalItemSapCode | originalDistributionChannelCode | giftItemSapCode | giftDistributionChannelCode | allocationQuantity | originalQuantity | giftQuantity | initialBonusQuantity | needShip | invalidStatus | unit | originalDistributionChannelName | giftDistributionChannelName | lot_no      | production_date | expiration_date |
      |                     |                                 | 10000265        | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | AT_LTY_0001 | 2024-05-01      | 2025-12-31      |
    And [整单折扣计算16]添加商品
      | itemSapCode | quantity |
      | 10000633    | 2        |
      | 10000456    | 5        |
      | 10000289    | 1        |
      | 10000556    | 1        |
      | 10000835    | 1        |
      | 10000841    | 4        |
    And [整单折扣计算16]选择项目
      | itemSapCode | quantity | projectType |
      | 10000265    | 1        | PT04        |
      | 10000478    | 1        | PT02        |
    Then [整单折扣计算16]进行整单折扣计算后,订单行分摊如下
      | orderWideDiscountSubject | itemSapCode | needShip | unitPrice | quantity | preDiscount | calculationValue | boxRatio | totalInline | preDiscountTotal | orderDiscountTotal |
      | PT00                     | 10000556    | 1        | 30        | 1        | 8.8         | 21.2             | 1        | 30          | 8.8              | 0                  |
      | PT00                     | 10000456    | 1        | 20        | 5        | 6           | 14               | 1        | 100         | 30               | 0                  |
      | PT04                     | 10000478    | 1        | 17        | 1        | 4           | 13               | 1        | 17          | 4                | 0                  |
      | PT00                     | 10000633    | 1        | 50        | 2        | 10          | 40               | 1        | 100         | 20               | 0.94               |
      | PT00                     | 10000289    | 1        | 5         | 1        | 0           | 5                | 1        | 5           | 0                | 1.05               |
      | PT00                     | 10000835    | 1        | 40        | 1        | 1           | 39               | 1        | 40          | 1                | 7.38               |
      | PT00                     | 10000841    | 1        | 10        | 4        | 0           | 10               | 1        | 40          | 0                | 8.38               |
      | PT02                     | 10000265    | 1        | 6         | 1        | 1           | 5                | 1        | 6           | 1                | 0.25               |
    Examples: 基础信息
      | saleOrgSapCode | regionCode | customerSapCode | regionName | customerName | shippingRecipient | shippingTelephone | shippingAddress |


  @discountCalculation16
  Scenario Outline: [场景9A]当前订单的商品无预折折扣，且使用整单折扣，无相同原品，预折折扣和整单折扣计算值正确（不发货赠品行不参与计算）。
  "[主要测试点]
  1. 原品行无预折折扣，赠品行无预折折扣
  2. 订单使用整单折扣（1个活动项目，1个分配计划）
  3. 不发货赠品行不参与计算
  4. 整单折扣总金额==各类项目赠品（零头药）商品行的实付款之和
  5. 订单行原总价==商品行原总价之和 - 不发货商品行原总价不发货商品行预折折扣"
    Given [整单折扣计算17]当前存在如下客户信息
      | saleOrgSapCode   | regionCode   | customerSapCode   | regionName   | customerName   | shippingAddress   | shippingRecipient   | shippingTelephone   |
      | <saleOrgSapCode> | <regionCode> | <customerSapCode> | <regionName> | <customerName> | <shippingAddress> | <shippingRecipient> | <shippingTelephone> |
    When [整单折扣计算17]当前存在如下的预折商品信息
      | itemSapCode | currentPrice | calculationType  | calculationValue | preType               | effectiveState | distributionChannelCode | distributionChannelName |
      | 10000841    | 10           | DISCOUNTED_PRICE | 8                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000265    | 6            | DISCOUNTED_PRICE | 5                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000289    | 5            | DISCOUNTED_PRICE | 4.5              | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000556    | 30           | DISCOUNTED_PRICE | 27               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000456    | 20           | DISCOUNTED_PRICE | 18               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000835    | 40           | DISCOUNTED_PRICE | 32               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000633    | 50           | DISCOUNTED_PRICE | 40               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000478    | 17           | DISCOUNTED_PRICE | 13               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
    And [整单折扣计算17]当前存在如下的有效预折折扣信息
      | itemSapCode | effectiveState |
      | 10000841    | DISABLE        |
      | 10000456    | DISABLE        |
      | 10000556    | DISABLE        |
      | 10000289    | DISABLE        |
    And [整单折扣计算17]当前存在如下的整单折扣申请单信息
      | projectType | module |
      | PT04        | KM03   |
    And [整单折扣计算17]当前存在如下的整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 10000265    | 100      | 盒     |
      | 10000289    | 100      | 盒     |
    And [整单折扣计算17]当前存在如下的整单折扣分配计划信息
    And [整单折扣计算17]当前存在如下的整单折扣分配计划详细信息
      | originalItemSapCode | originalDistributionChannelCode | giftItemSapCode | giftDistributionChannelCode | allocationQuantity | originalQuantity | giftQuantity | initialBonusQuantity | needShip | invalidStatus | unit | originalDistributionChannelName | giftDistributionChannelName |
      |                     |                                 | 10000289        | 22                          | 100                |                  |              |                      | 0        | NORMAL        | 盒    |                                 | 分销-非医疗                      |
    And [整单折扣计算17]添加商品
      | itemSapCode | quantity |
      | 10000841    | 10       |
      | 10000556    | 10       |
      | 10000456    | 10       |
    And [整单折扣计算17]选择项目
      | itemSapCode | quantity |
      | 10000289    | 2        |
    Then [整单折扣计算17]进行整单折扣计算后,订单行分摊如下
      | orderWideDiscountSubject | itemSapCode | needShip | unitPrice | quantity | preDiscount | calculationValue | boxRatio | totalInline | preDiscountTotal | orderDiscountTotal |
      | PT00                     | 10000841    | 1        | 10        | 10       | 0           | 10               | 1        | 100         | 0                | 1.67               |
      | PT04                     | 10000289    | 0        | 5         | 2        | 0           | 5                | 1        | 10          | 0                | 0                  |
      | PT00                     | 10000456    | 1        | 20        | 10       | 0           | 20               | 1        | 200         | 0                | 3.33               |
      | PT00                     | 10000556    | 1        | 30        | 10       | 0           | 30               | 1        | 300         | 0                | 5                  |
    Examples: 基础信息
      | saleOrgSapCode | regionCode | customerSapCode | regionName | customerName | shippingRecipient | shippingTelephone | shippingAddress |


  @discountCalculation17
  Scenario Outline: [场景9B]当前订单的商品无预折折扣，且使用整单折扣，有相同原品，预折折扣和整单折扣计算值正确（不发货赠品行不参与计算）。
  "[主要测试点]
  1. 原品行无预折折扣，赠品行无预折折扣
  2. 订单使用整单折扣（多个活动项目，多个分配计划）
  3. 不发货赠品行不参与计算
  4. 整单折扣总金额==各类项目赠品（零头药）商品行的实付款之和
  5. 订单行原总价==商品行原总价之和 - 不发货商品行原总价不发货商品行预折折扣
  6. 有相同原品"
    Given [整单折扣计算18]当前存在如下客户信息
      | saleOrgSapCode   | regionCode   | customerSapCode   | regionName   | customerName   | shippingAddress   | shippingRecipient   | shippingTelephone   |
      | <saleOrgSapCode> | <regionCode> | <customerSapCode> | <regionName> | <customerName> | <shippingAddress> | <shippingRecipient> | <shippingTelephone> |
    When [整单折扣计算18]当前存在如下的预折商品信息
      | itemSapCode | currentPrice | calculationType  | calculationValue | preType               | effectiveState | distributionChannelCode | distributionChannelName |
      | 10000841    | 10           | DISCOUNTED_PRICE | 8                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000265    | 6            | DISCOUNTED_PRICE | 5                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000289    | 5            | DISCOUNTED_PRICE | 4.5              | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000556    | 30           | DISCOUNTED_PRICE | 27               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000456    | 20           | DISCOUNTED_PRICE | 18               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000835    | 40           | DISCOUNTED_PRICE | 32               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000633    | 50           | DISCOUNTED_PRICE | 40               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000478    | 17           | DISCOUNTED_PRICE | 13               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
    And [整单折扣计算18]当前存在如下的有效预折折扣信息
      | itemSapCode | effectiveState |
      | 10000841    | DISABLE        |
      | 10000456    | DISABLE        |
      | 10000556    | DISABLE        |
      | 10000289    | DISABLE        |
    And [整单折扣计算18]当前存在如下的[买赠]整单折扣申请单信息
      | projectType | module |
      | PT01        | KM03   |
    And [整单折扣计算18]当前存在如下的[买赠]整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 10000289    | 100      | 盒     |
    And [整单折扣计算18]当前存在如下的[买赠]整单折扣分配计划信息
    And [整单折扣计算18]当前存在如下的[买赠]整单折扣分配计划详细信息
      | originalItemSapCode | originalDistributionChannelCode | giftItemSapCode | giftDistributionChannelCode | allocationQuantity | originalQuantity | giftQuantity | initialBonusQuantity | needShip | invalidStatus | unit | originalDistributionChannelName | giftDistributionChannelName |
      | 10000841            | 22                              | 10000289        | 22                          | 100                | 5                | 1            | 2                    | 0        | NORMAL        | 盒    |                                 | 分销-非医疗                      |
    And [整单折扣计算18]当前存在如下的[纯赠]整单折扣申请单信息
      | projectType | module |
      | PT04        | KM03   |
    And [整单折扣计算18]当前存在如下的[纯赠]整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 10000478    | 100      | 盒     |
    And [整单折扣计算18]当前存在如下的[纯赠]整单折扣分配计划信息
    And [整单折扣计算18]当前存在如下的[纯赠]整单折扣分配计划详细信息
      | originalItemSapCode | originalDistributionChannelCode | giftItemSapCode | giftDistributionChannelCode | allocationQuantity | originalQuantity | giftQuantity | initialBonusQuantity | needShip | invalidStatus | unit | originalDistributionChannelName | giftDistributionChannelName |
      |                     |                                 | 10000478        | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      |
    And [整单折扣计算18]当前存在如下的[零头药]整单折扣申请单信息
      | projectType | module |
      | PT02        | KM03   |
    And [整单折扣计算18]当前存在如下的[零头药]整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 10000289    | 100      | 盒     |
    And [整单折扣计算18]当前存在如下的[零头药]整单折扣分配计划信息
    And [整单折扣计算18]当前存在如下的[零头药]整单折扣分配计划详细信息
      | originalItemSapCode | originalDistributionChannelCode | giftItemSapCode | giftDistributionChannelCode | allocationQuantity | originalQuantity | giftQuantity | initialBonusQuantity | needShip | invalidStatus | unit | originalDistributionChannelName | giftDistributionChannelName | lot_no      | production_date | expiration_date |
      |                     |                                 | 10000289        | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | AT_LTY_0001 | 2024-05-01      | 2025-12-31      |
    And [整单折扣计算18]添加商品
      | itemSapCode | quantity |
      | 10000841    | 10       |
      | 10000556    | 10       |
      | 10000456    | 10       |
    And [整单折扣计算18]选择项目
      | itemSapCode | quantity |
      | 10000289    | 4        |
    Then [整单折扣计算18]进行整单折扣计算后,订单行分摊如下
      | orderWideDiscountSubject | itemSapCode | needShip | unitPrice | preDiscount | calculationValue | boxRatio | quantity | totalInline | preDiscountTotal | orderDiscountTotal |
      | PT00                     | 10000841    | 1        | 10        | 0           | 10               | 1        | 3        | 30          | 0                | 0.99               |
      | PT00                     | 10000841    | 1        | 10        | 0           | 10               | 1        | 7        | 70          | 0                | 2.31               |
      | PT01                     | 10000289    | 0        | 5         | 0           | 5                | 1        | 2        | 10          | 0                | 0                  |
      | PT02                     | 10000289    | 1        | 5         | 0           | 5                | 1        | 1        | 5           | 0                | 0.17               |
      | PT04                     | 10000289    | 0        | 5         | 0           | 5                | 1        | 1        | 5           | 0                | 0                  |
      | PT00                     | 10000456    | 1        | 20        | 0           | 20               | 1        | 2        | 40          | 0                | 1.32               |
      | PT00                     | 10000456    | 1        | 20        | 0           | 20               | 1        | 3        | 60          | 0                | 1.98               |
      | PT00                     | 10000456    | 1        | 20        | 0           | 20               | 1        | 5        | 100         | 0                | 3.31               |
      | PT00                     | 10000556    | 1        | 30        | 0           | 30               | 1        | 4        | 120         | 0                | 3.97               |
      | PT00                     | 10000556    | 1        | 30        | 0           | 30               | 1        | 6        | 180         | 0                | 5.95               |
    Examples: 基础信息
      | saleOrgSapCode | regionCode | customerSapCode | regionName | customerName | shippingRecipient | shippingTelephone | shippingAddress |


  @discountCalculation18
  Scenario Outline: [场景10A]当前订单的商品有预折折扣，且使用整单折扣，无相同原品，预折折扣和整单折扣计算值正确（不发货赠品行不参与计算 ）。
  "[主要测试点]
  1. 原品行有预折折扣，赠品行有预折折扣
  2. 订单使用整单折扣（1个活动项目，1个分配计划）
  3. 不发货赠品行不参与计算
  4. 整单折扣总金额==各类项目赠品（买赠）商品行的实付款之和
  5. 订单行原总价==商品行原总价之和 - 不发货商品行原总价不发货商品行预折折扣
  6. 总预折折扣==商品行预折折扣之和 - 不发货商品行预折折扣
  7. 无相同原品"
    Given [整单折扣计算19]当前存在如下客户信息
      | saleOrgSapCode   | regionCode   | customerSapCode   | regionName   | customerName   | shippingAddress   | shippingRecipient   | shippingTelephone   |
      | <saleOrgSapCode> | <regionCode> | <customerSapCode> | <regionName> | <customerName> | <shippingAddress> | <shippingRecipient> | <shippingTelephone> |
    When [整单折扣计算19]当前存在如下的预折商品信息
      | itemSapCode | currentPrice | calculationType  | calculationValue | preType               | effectiveState | distributionChannelCode | distributionChannelName |
      | 10000841    | 10           | DISCOUNTED_PRICE | 8                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000265    | 6            | DISCOUNTED_PRICE | 5                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000289    | 5            | DISCOUNTED_PRICE | 4.5              | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000556    | 30           | DISCOUNTED_PRICE | 27               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000456    | 20           | DISCOUNTED_PRICE | 18               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000835    | 40           | DISCOUNTED_PRICE | 32               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000633    | 50           | DISCOUNTED_PRICE | 40               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000478    | 17           | DISCOUNTED_PRICE | 13               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
    And [整单折扣计算19]当前存在如下的预折折扣信息
      | itemSapCode | calculationValue | effectiveState |
      | 10000841    | 8                | ENABLE         |
      | 10000289    | 4.5              | ENABLE         |
      | 10000556    | 27               | ENABLE         |
      | 10000456    | 18               | ENABLE         |
    And [整单折扣计算19]当前存在如下的整单折扣申请单信息
      | projectType | module |
      | PT05        | KM03   |
    And [整单折扣计算19]当前存在如下的整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 10000289    | 100      | 盒     |
    And [整单折扣计算19]当前存在如下的整单折扣分配计划信息
    And [整单折扣计算19]当前存在如下的整单折扣分配计划详细信息
      | originalItemSapCode | originalDistributionChannelCode | giftItemSapCode | giftDistributionChannelCode | allocationQuantity | originalQuantity | giftQuantity | initialBonusQuantity | needShip | invalidStatus | unit | originalDistributionChannelName | giftDistributionChannelName |
      | 10000841            | 22                              | 10000289        | 22                          | 100                | 1                | 1            | 5                    | 0        | NORMAL        | 盒    |                                 | 分销-非医疗                      |
    And [整单折扣计算19]添加商品
      | itemSapCode | quantity |
      | 10000841    | 10       |
      | 10000556    | 10       |
      | 10000456    | 5        |
    And [整单折扣计算19]选择项目
      | itemSapCode | quantity |
      | 10000289    | 10       |
    Then [整单折扣计算19]进行整单折扣计算后,订单行分摊如下
      | orderWideDiscountSubject | itemSapCode | needShip | unitPrice | quantity | preDiscount | calculationValue | boxRatio | totalInline | preDiscountTotal | orderDiscountTotal |
      | PT00                     | 10000841    | 1        | 10        | 10       | 2           | 8                | 1        | 100         | 20               | 1                  |
      | PT05                     | 10000289    | 0        | 5         | 10       | 0.5         | 4.5              | 1        | 50          | 5                | 0                  |
      | PT00                     | 10000556    | 1        | 30        | 10       | 3           | 27               | 1        | 300         | 30               | 33                 |
      | PT00                     | 10000456    | 1        | 20        | 5        | 2           | 18               | 1        | 100         | 10               | 11                 |
    Examples: 基础信息
      | saleOrgSapCode | regionCode | customerSapCode | regionName | customerName | shippingRecipient | shippingTelephone | shippingAddress |


  @discountCalculation19
  Scenario Outline: [场景10B]当前订单的商品有预折折扣，且使用整单折扣，有相同原品，预折折扣和整单折扣计算值正确（不发货赠品行不参与计算 ）。
  "[主要测试点]
  1. 原品行有预折折扣，赠品行有预折折扣
  2. 订单使用整单折扣（多个活动项目，多个分配计划）
  3. 不发货赠品行不参与计算
  4. 整单折扣总金额==各类项目赠品（买赠）商品行的实付款之和
  5. 订单行原总价==商品行原总价之和 - 不发货商品行原总价不发货商品行预折折扣
  6. 总预折折扣==商品行预折折扣之和 - 不发货商品行预折折扣
  7. 有相同原品"
    Given [整单折扣计算20]当前存在如下客户信息
      | saleOrgSapCode   | regionCode   | customerSapCode   | regionName   | customerName   | shippingAddress   | shippingRecipient   | shippingTelephone   |
      | <saleOrgSapCode> | <regionCode> | <customerSapCode> | <regionName> | <customerName> | <shippingAddress> | <shippingRecipient> | <shippingTelephone> |
    When [整单折扣计算20]当前存在如下的预折商品信息
      | itemSapCode | currentPrice | calculationType  | calculationValue | preType               | effectiveState | distributionChannelCode | distributionChannelName |
      | 10000841    | 10           | DISCOUNTED_PRICE | 8                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000265    | 6            | DISCOUNTED_PRICE | 5                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000289    | 5            | DISCOUNTED_PRICE | 4.5              | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000556    | 30           | DISCOUNTED_PRICE | 27               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000456    | 20           | DISCOUNTED_PRICE | 18               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000835    | 40           | DISCOUNTED_PRICE | 32               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000633    | 50           | DISCOUNTED_PRICE | 40               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000478    | 17           | DISCOUNTED_PRICE | 13               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
    And [整单折扣计算20]当前存在如下的预折折扣信息
      | itemSapCode | calculationValue | effectiveState |
      | 10000841    | 8                | ENABLE         |
      | 10000289    | 4.5              | ENABLE         |
      | 10000556    | 27               | ENABLE         |
      | 10000456    | 18               | ENABLE         |
    And [整单折扣计算20]当前存在如下的[买赠]整单折扣申请单信息
      | projectType | module |
      | PT01        | KM03   |
    And [整单折扣计算20]当前存在如下的[买赠]整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 10000289    | 100      | 盒     |
    And [整单折扣计算20]当前存在如下的[买赠]整单折扣分配计划信息
    And [整单折扣计算20]当前存在如下的[买赠]整单折扣分配计划详细信息
      | originalItemSapCode | originalDistributionChannelCode | giftItemSapCode | giftDistributionChannelCode | allocationQuantity | originalQuantity | giftQuantity | initialBonusQuantity | needShip | invalidStatus | unit | originalDistributionChannelName | giftDistributionChannelName |
      | 10000841            | 22                              | 10000289        | 22                          | 100                | 5                | 1            | 2                    | 0        | NORMAL        | 盒    |                                 | 分销-非医疗                      |
    And [整单折扣计算20]当前存在如下的[纯赠]整单折扣申请单信息
      | projectType | module |
      | PT04        | KM03   |
    And [整单折扣计算20]当前存在如下的[纯赠]整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 10000289    | 100      | 盒     |
    And [整单折扣计算20]当前存在如下的[纯赠]整单折扣分配计划信息
    And [整单折扣计算20]当前存在如下的[纯赠]整单折扣分配计划详细信息
      | originalItemSapCode | originalDistributionChannelCode | giftItemSapCode | giftDistributionChannelCode | allocationQuantity | originalQuantity | giftQuantity | initialBonusQuantity | needShip | invalidStatus | unit | originalDistributionChannelName | giftDistributionChannelName |
      |                     |                                 | 10000289        | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      |
    And [整单折扣计算20]当前存在如下的[零头药]整单折扣申请单信息
      | projectType | module |
      | PT02        | KM03   |
    And [整单折扣计算20]当前存在如下的[零头药]整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 10000289    | 100      | 盒     |
    And [整单折扣计算20]当前存在如下的[零头药]整单折扣分配计划信息
    And [整单折扣计算20]当前存在如下的[零头药]整单折扣分配计划详细信息
      | originalItemSapCode | originalDistributionChannelCode | giftItemSapCode | giftDistributionChannelCode | allocationQuantity | originalQuantity | giftQuantity | initialBonusQuantity | needShip | invalidStatus | unit | originalDistributionChannelName | giftDistributionChannelName | lot_no      | production_date | expiration_date |
      |                     |                                 | 10000289        | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | AT_LTY_0001 | 2024-05-01      | 2025-12-31      |
    And [整单折扣计算20]添加商品
      | itemSapCode | quantity |
      | 10000841    | 10       |
      | 10000556    | 10       |
      | 10000456    | 10       |
    And [整单折扣计算20]选择项目
      | itemSapCode | quantity |
      | 10000289    | 4        |
    Then [整单折扣计算20]进行整单折扣计算后,订单行分摊如下
      | orderWideDiscountSubject | itemSapCode | needShip | unitPrice | quantity | preDiscount | calculationValue | boxRatio | totalInline | preDiscountTotal | orderDiscountTotal |
      | PT00                     | 10000841    | 1        | 10        | 3        | 2           | 8                | 1        | 30          | 6                | 0                  |
      | PT00                     | 10000841    | 1        | 10        | 7        | 2           | 8                | 1        | 70          | 14               | 0                  |
      | PT01                     | 10000289    | 0        | 5         | 2        | 0.5         | 4.5              | 1        | 10          | 1                | 0                  |
      | PT02                     | 10000289    | 1        | 5         | 1        | 0.5         | 4.5              | 1        | 5           | 0.5              | 0.18               |
      | PT04                     | 10000289    | 0        | 5         | 1        | 0.5         | 4.5              | 1        | 5           | 0.5              | 0                  |
      | PT00                     | 10000456    | 1        | 20        | 2        | 2           | 18               | 1        | 40          | 4                | 1.43               |
      | PT00                     | 10000456    | 1        | 20        | 3        | 2           | 18               | 1        | 60          | 6                | 2.14               |
      | PT00                     | 10000456    | 1        | 20        | 5        | 2           | 18               | 1        | 100         | 10               | 3.56               |
      | PT00                     | 10000556    | 1        | 30        | 4        | 3           | 27               | 1        | 120         | 12               | 4.28               |
      | PT00                     | 10000556    | 1        | 30        | 6        | 3           | 27               | 1        | 180         | 18               | 6.41               |
    Examples: 基础信息
      | saleOrgSapCode | regionCode | customerSapCode | regionName | customerName | shippingRecipient | shippingTelephone | shippingAddress |


  @discountCalculation20
  Scenario Outline: [场景11]当前订单的商品有预折折扣，且使用整单折扣，预折折扣和整单折扣计算值正确（预折折扣 > 32% 不参与计算）。
  "[主要测试点]
  1. 原品行有预折折扣，赠品行有预折折扣
  2. 订单使用整单折扣（1个活动项目，1个分配计划）
  3. 部分行参与计算（预折折扣 > 32% 不参与计算）"
    Given [整单折扣计算21]当前存在如下客户信息
      | saleOrgSapCode   | regionCode   | customerSapCode   | regionName   | customerName   | shippingAddress   | shippingRecipient   | shippingTelephone   |
      | <saleOrgSapCode> | <regionCode> | <customerSapCode> | <regionName> | <customerName> | <shippingAddress> | <shippingRecipient> | <shippingTelephone> |
    When [整单折扣计算21]当前存在如下的预折商品信息
      | itemSapCode | currentPrice | calculationType  | calculationValue | preType               | effectiveState | distributionChannelCode | distributionChannelName |
      | 10000841    | 10           | DISCOUNTED_PRICE | 8                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000265    | 6            | DISCOUNTED_PRICE | 5                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000289    | 5            | DISCOUNTED_PRICE | 4.5              | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000556    | 30           | DISCOUNTED_PRICE | 27               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000456    | 20           | DISCOUNTED_PRICE | 18               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000835    | 40           | DISCOUNTED_PRICE | 32               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000633    | 50           | DISCOUNTED_PRICE | 40               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000478    | 17           | DISCOUNTED_PRICE | 13               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
    And [整单折扣计算21]当前存在如下的预折折扣信息
      | itemSapCode | calculationValue | effectiveState |
      | 10000841    | 6.7              | ENABLE         |
      | 10000289    | 4.5              | ENABLE         |
      | 10000556    | 27               | ENABLE         |
      | 10000456    | 18               | ENABLE         |
    And [整单折扣计算21]当前存在如下的整单折扣申请单信息
      | projectType | module |
      | PT04        | KM03   |
    And [整单折扣计算21]当前存在如下的整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 10000265    | 100      | 盒     |
      | 10000289    | 100      | 盒     |
    And [整单折扣计算21]当前存在如下的整单折扣分配计划信息
    And [整单折扣计算21]当前存在如下的整单折扣分配计划详细信息
      | originalItemSapCode | originalDistributionChannelCode | giftItemSapCode | giftDistributionChannelCode | allocationQuantity | originalQuantity | giftQuantity | initialBonusQuantity | needShip | invalidStatus | unit | originalDistributionChannelName | giftDistributionChannelName |
      |                     |                                 | 10000289        | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      |
    And [整单折扣计算21]添加商品
      | itemSapCode | quantity |
      | 10000841    | 10       |
      | 10000556    | 10       |
      | 10000456    | 5        |
    And [整单折扣计算21]选择项目
      | itemSapCode | quantity |
      | 10000289    | 10       |
    Then [整单折扣计算21]进行整单折扣计算后,订单行分摊如下
      | orderWideDiscountSubject | itemSapCode | needShip | unitPrice | quantity | preDiscount | calculationValue | boxRatio | totalInline | preDiscountTotal | orderDiscountTotal |
      | PT00                     | 10000841    | 1        | 10        | 10       | 3.3         | 6.7              | 1        | 100         | 33               | 0                  |
      | PT04                     | 10000289    | 1        | 5         | 10       | 0.5         | 4.5              | 1        | 50          | 5                | 5                  |
      | PT00                     | 10000556    | 1        | 30        | 10       | 3           | 27               | 1        | 300         | 30               | 30                 |
      | PT00                     | 10000456    | 1        | 20        | 5        | 2           | 18               | 1        | 100         | 10               | 10                 |
    Examples: 基础信息
      | saleOrgSapCode | regionCode | customerSapCode | regionName | customerName | shippingRecipient | shippingTelephone | shippingAddress |


  @discountCalculation21
  Scenario Outline: [场景12]当前订单的商品有预折折扣，且使用整单折扣，预折折扣和整单折扣计算值正确（预折折扣 = 32% 不参与计算）。
  "[主要测试点]
  1. 原品行有预折折扣，赠品行有预折折扣
  2. 订单使用整单折扣（1个活动项目，1个分配计划）
  3. 部分行参与计算（预折折扣 = 32% 不参与计算）"
    Given [整单折扣计算22]当前存在如下客户信息
      | saleOrgSapCode   | regionCode   | customerSapCode   | regionName   | customerName   | shippingAddress   | shippingRecipient   | shippingTelephone   |
      | <saleOrgSapCode> | <regionCode> | <customerSapCode> | <regionName> | <customerName> | <shippingAddress> | <shippingRecipient> | <shippingTelephone> |
    When [整单折扣计算22]当前存在如下的预折商品信息
      | itemSapCode | currentPrice | calculationType  | calculationValue | preType               | effectiveState | distributionChannelCode | distributionChannelName |
      | 10000841    | 10           | DISCOUNTED_PRICE | 8                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000265    | 6            | DISCOUNTED_PRICE | 5                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000289    | 5            | DISCOUNTED_PRICE | 4.5              | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000556    | 30           | DISCOUNTED_PRICE | 27               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000456    | 20           | DISCOUNTED_PRICE | 18               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000835    | 40           | DISCOUNTED_PRICE | 32               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000633    | 50           | DISCOUNTED_PRICE | 40               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000478    | 17           | DISCOUNTED_PRICE | 13               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
    And [整单折扣计算22]当前存在如下的预折折扣信息
      | itemSapCode | calculationValue | effectiveState |
      | 10000841    | 6.8              | ENABLE         |
      | 10000289    | 4.5              | ENABLE         |
      | 10000556    | 27               | ENABLE         |
      | 10000456    | 18               | ENABLE         |
    And [整单折扣计算22]当前存在如下的整单折扣申请单信息
      | projectType | module |
      | PT04        | KM03   |
    And [整单折扣计算22]当前存在如下的整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 10000265    | 100      | 盒     |
      | 10000289    | 100      | 盒     |
    And [整单折扣计算22]当前存在如下的整单折扣分配计划信息
    And [整单折扣计算22]当前存在如下的整单折扣分配计划详细信息
      | originalItemSapCode | originalDistributionChannelCode | giftItemSapCode | giftDistributionChannelCode | allocationQuantity | originalQuantity | giftQuantity | initialBonusQuantity | needShip | invalidStatus | unit | originalDistributionChannelName | giftDistributionChannelName |
      |                     |                                 | 10000289        | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      |
    And [整单折扣计算22]添加商品
      | itemSapCode | quantity |
      | 10000841    | 10       |
      | 10000556    | 10       |
      | 10000456    | 5        |
    And [整单折扣计算22]选择项目
      | itemSapCode | quantity |
      | 10000289    | 10       |
    Then [整单折扣计算22]进行整单折扣计算后,订单行分摊如下
      | orderWideDiscountSubject | itemSapCode | needShip | unitPrice | quantity | preDiscount | calculationValue | boxRatio | totalInline | preDiscountTotal | orderDiscountTotal |
      | PT00                     | 10000841    | 1        | 10        | 10       | 3.2         | 6.8              | 1        | 100         | 32               | 0                  |
      | PT04                     | 10000289    | 1        | 5         | 10       | 0.5         | 4.5              | 1        | 50          | 5                | 5                  |
      | PT00                     | 10000556    | 1        | 30        | 10       | 3           | 27               | 1        | 300         | 30               | 30                 |
      | PT00                     | 10000456    | 1        | 20        | 5        | 2           | 18               | 1        | 100         | 10               | 10                 |
    Examples: 基础信息
      | saleOrgSapCode | regionCode | customerSapCode | regionName | customerName | shippingRecipient | shippingTelephone | shippingAddress |
      
