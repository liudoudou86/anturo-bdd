Feature: 商品获取单价逻辑

  Background: [前置条件]测试数据初始化
    Given [创建订单整单折扣计算商品基础数据01]清除测试数据
    When [创建订单整单折扣计算商品基础数据01]商品中心导入测试数据


  @productPriceOrderDiscountCalc01
  Scenario Outline: [场景01]【医药商业】【创建订单】整单折扣计算取价格维度组 → 60且优先级为1的分销渠道
  "[主要测试点]
  1. 销售组织: 医药商业
  2. 创建订单
  3. 输入商品数量后
  4. 价格维度组: 销售商/分销渠道/客户/商品
  5. 根据价格中心配置表取优先级为1的分销渠道"
    Given [创建订单整单折扣计算获取单价01]当前存在如下客户信息
      | saleOrgSapCode | regionCode | regionName   | customerSapCode | shippingAddress   | shippingRecipient   | shippingTelephone   |
      | 1017           | 340000     | <regionName> | 0010000262      | <shippingAddress> | <shippingRecipient> | <shippingTelephone> |
    When [创建订单整单折扣计算获取单价01]当前存在如下商品价格信息
      | dimensionCode        | dimensionPriority | distributionChannelCode | priceGroupCode | customerCode | itemCode | price  |
      | SELL_DIST_CUST_ITEM  | 60                | 21                      | N              | 0010000262   | 19999999 | 666.21 |
      | SELL_DIST_CUST_ITEM  | 60                | 22                      | N              | 0010000262   | 19999999 | 666.22 |
      | SELL_DIST_CUST_ITEM  | 60                | 12                      | N              | 0010000262   | 19999999 | 666.12 |
      | SELL_DIST_CUST_ITEM  | 60                | 99                      | N              | 0010000262   | 19999999 | 666.99 |
      | SELL_DIST_PRICE_ITEM | 50                | 21                      | 03             | N            | 19999999 | 555.21 |
      | SELL_DIST_PRICE_ITEM | 50                | 22                      | 03             | N            | 19999999 | 555.22 |
      | SELL_DIST_PRICE_ITEM | 50                | 12                      | 03             | N            | 19999999 | 555.12 |
      | SELL_DIST_PRICE_ITEM | 50                | 99                      | 03             | N            | 19999999 | 555.99 |
      | SELL_CUST_ITEM       | 40                | N                       | N              | 0010000262   | 19999999 | 444.44 |
      | SELL_DIST_ITEM       | 30                | 21                      | N              | N            | 19999999 | 333.21 |
      | SELL_DIST_ITEM       | 30                | 22                      | N              | N            | 19999999 | 333.22 |
      | SELL_DIST_ITEM       | 30                | 12                      | N              | N            | 19999999 | 333.12 |
      | SELL_DIST_ITEM       | 30                | 99                      | N              | N            | 19999999 | 333.99 |
      | SELL_PRICE_ITEM      | 20                | N                       | 03             | N            | 19999999 | 222.22 |
      | SELL_ITEM            | 10                | N                       | N              | N            | 19999999 | 111.11 |
    And [创建订单整单折扣计算获取单价01]创建订单添加商品时获取价格应该如下
      | itemCode | price  | distributionChannelCode |
      | 19999999 | 666.21 | 21                      |
      | 19999999 | 666.22 | 22                      |
    Then [创建订单整单折扣计算获取单价01]进行整单折扣计算后,订单行分摊如下
      | orderWideDiscountSubject | itemSapCode | needShip | unitPrice | preDiscount | calculationValue | boxRatio | quantity | totalInline | preDiscountTotal | orderDiscountTotal | distributionChannelCode |
      | PT00                     | 19999999    | 1        | 666.21    | 0           | 666.21           | 1        | 1        | 666.21      | 0                | 0                  | 21                      |

    Examples: 基础信息
      | saleOrgSapName | regionName | customerSapName | shippingRecipient | shippingTelephone | shippingAddress |


  @productPriceOrderDiscountCalc02
  Scenario Outline: [场景02]【医药商业】【创建订单】整单折扣计算取价格维度组 → 60且优先级为2的分销渠道
  "[主要测试点]
  1. 销售组织: 医药商业
  2. 创建订单
  3. 输入商品数量后
  4. 价格维度组: 销售商/分销渠道/客户/商品
  5. 根据价格中心配置表取优先级为2的分销渠道"
    Given [创建订单整单折扣计算获取单价02]当前存在如下客户信息
      | saleOrgSapCode | regionCode | regionName   | customerSapCode | shippingAddress   | shippingRecipient   | shippingTelephone   |
      | 1017           | 340000     | <regionName> | 0010000262      | <shippingAddress> | <shippingRecipient> | <shippingTelephone> |
    When [创建订单整单折扣计算获取单价02]当前存在如下商品价格信息
      | dimensionCode        | dimensionPriority | distributionChannelCode | priceGroupCode | customerCode | itemCode | price  |
      | SELL_DIST_CUST_ITEM  | 60                | 12                      | N              | 0010000262   | 19999999 | 666.12 |
      | SELL_DIST_CUST_ITEM  | 60                | 99                      | N              | 0010000262   | 19999999 | 666.99 |
      | SELL_DIST_PRICE_ITEM | 50                | 21                      | 03             | N            | 19999999 | 555.21 |
      | SELL_DIST_PRICE_ITEM | 50                | 22                      | 03             | N            | 19999999 | 555.22 |
      | SELL_DIST_PRICE_ITEM | 50                | 12                      | 03             | N            | 19999999 | 555.12 |
      | SELL_DIST_PRICE_ITEM | 50                | 99                      | 03             | N            | 19999999 | 555.99 |
      | SELL_CUST_ITEM       | 40                | N                       | N              | 0010000262   | 19999999 | 444.44 |
      | SELL_DIST_ITEM       | 30                | 21                      | N              | N            | 19999999 | 333.21 |
      | SELL_DIST_ITEM       | 30                | 22                      | N              | N            | 19999999 | 333.22 |
      | SELL_DIST_ITEM       | 30                | 12                      | N              | N            | 19999999 | 333.12 |
      | SELL_DIST_ITEM       | 30                | 99                      | N              | N            | 19999999 | 333.99 |
      | SELL_PRICE_ITEM      | 20                | N                       | 03             | N            | 19999999 | 222.22 |
      | SELL_ITEM            | 10                | N                       | N              | N            | 19999999 | 111.11 |
    And [创建订单整单折扣计算获取单价02]创建订单添加商品时获取价格应该如下
      | itemCode | price  | distributionChannelCode |
      | 19999999 | 666.12 | 12                      |
    Then [创建订单整单折扣计算获取单价02]进行整单折扣计算后,订单行分摊如下
      | orderWideDiscountSubject | itemSapCode | needShip | unitPrice | preDiscount | calculationValue | boxRatio | quantity | totalInline | preDiscountTotal | orderDiscountTotal | distributionChannelCode |
      | PT00                     | 19999999    | 1        | 666.12    | 0           | 666.12           | 1        | 1        | 666.12      | 0                | 0                  | 12                      |
    Examples: 基础信息
      | saleOrgSapName | regionName | customerSapName | shippingRecipient | shippingTelephone | shippingAddress |


  @productPriceOrderDiscountCalc03
  Scenario Outline: [场景03]【医药商业】【创建订单】整单折扣计算取价格维度组 → 50且优先级为1的分销渠道
  "[主要测试点]
  1. 销售组织: 医药商业
  2. 创建订单
  3. 输入商品数量后
  4. 价格维度组: 销售商/分销渠道/价格组/商品
  5. 根据价格中心配置表取优先级为1的分销渠道"
    Given [创建订单整单折扣计算获取单价03]当前存在如下客户信息
      | saleOrgSapCode | regionCode | regionName   | customerSapCode | shippingAddress   | shippingRecipient   | shippingTelephone   |
      | 1017           | 340000     | <regionName> | 0010000262      | <shippingAddress> | <shippingRecipient> | <shippingTelephone> |
    When [创建订单整单折扣计算获取单价03]当前存在如下商品价格信息
      | dimensionCode        | dimensionPriority | distributionChannelCode | priceGroupCode | customerCode | itemCode | price  |
      | SELL_DIST_CUST_ITEM  | 60                | 99                      | N              | 0010000262   | 19999999 | 666.99 |
      | SELL_DIST_PRICE_ITEM | 50                | 21                      | 03             | N            | 19999999 | 555.21 |
      | SELL_DIST_PRICE_ITEM | 50                | 22                      | 03             | N            | 19999999 | 555.22 |
      | SELL_DIST_PRICE_ITEM | 50                | 12                      | 03             | N            | 19999999 | 555.12 |
      | SELL_DIST_PRICE_ITEM | 50                | 99                      | 03             | N            | 19999999 | 555.99 |
      | SELL_CUST_ITEM       | 40                | N                       | N              | 0010000262   | 19999999 | 444.44 |
      | SELL_DIST_ITEM       | 30                | 21                      | N              | N            | 19999999 | 333.21 |
      | SELL_DIST_ITEM       | 30                | 22                      | N              | N            | 19999999 | 333.22 |
      | SELL_DIST_ITEM       | 30                | 12                      | N              | N            | 19999999 | 333.12 |
      | SELL_DIST_ITEM       | 30                | 99                      | N              | N            | 19999999 | 333.99 |
      | SELL_PRICE_ITEM      | 20                | N                       | 03             | N            | 19999999 | 222.22 |
      | SELL_ITEM            | 10                | N                       | N              | N            | 19999999 | 111.11 |
    And [创建订单整单折扣计算获取单价03]创建订单添加商品时获取价格应该如下
      | itemCode | price  | distributionChannelCode |
      | 19999999 | 555.21 | 21                      |
      | 19999999 | 555.22 | 22                      |
    Then [创建订单整单折扣计算获取单价03]进行整单折扣计算后,订单行分摊如下
      | orderWideDiscountSubject | itemSapCode | needShip | unitPrice | preDiscount | calculationValue | boxRatio | quantity | totalInline | preDiscountTotal | orderDiscountTotal | distributionChannelCode |
      | PT00                     | 19999999    | 1        | 555.21    | 0           | 555.21           | 1        | 1        | 555.21      | 0                | 0                  | 21                      |

    Examples: 基础信息
      | saleOrgSapName | regionName | customerSapName | shippingRecipient | shippingTelephone | shippingAddress |


  @productPriceOrderDiscountCalc04
  Scenario Outline: [场景04]【医药商业】【创建订单】整单折扣计算取价格维度组 → 50且优先级为2的分销渠道
  "[主要测试点]
  1. 销售组织: 医药商业
  2. 创建订单
  3. 输入商品数量后
  4. 价格维度组: 销售商/分销渠道/价格组/商品
  5. 根据价格中心配置表取优先级为2的分销渠道"
    Given [创建订单整单折扣计算获取单价04]当前存在如下客户信息
      | saleOrgSapCode | regionCode | regionName   | customerSapCode | shippingAddress   | shippingRecipient   | shippingTelephone   |
      | 1017           | 340000     | <regionName> | 0010000262      | <shippingAddress> | <shippingRecipient> | <shippingTelephone> |
    When [创建订单整单折扣计算获取单价04]当前存在如下商品价格信息
      | dimensionCode        | dimensionPriority | distributionChannelCode | priceGroupCode | customerCode | itemCode | price  |
      | SELL_DIST_PRICE_ITEM | 50                | 12                      | 03             | N            | 19999999 | 555.12 |
      | SELL_DIST_PRICE_ITEM | 50                | 99                      | 03             | N            | 19999999 | 555.99 |
      | SELL_CUST_ITEM       | 40                | N                       | N              | 0010000262   | 19999999 | 444.44 |
      | SELL_DIST_ITEM       | 30                | 21                      | N              | N            | 19999999 | 333.21 |
      | SELL_DIST_ITEM       | 30                | 22                      | N              | N            | 19999999 | 333.22 |
      | SELL_DIST_ITEM       | 30                | 12                      | N              | N            | 19999999 | 333.12 |
      | SELL_DIST_ITEM       | 30                | 99                      | N              | N            | 19999999 | 333.99 |
      | SELL_PRICE_ITEM      | 20                | N                       | 03             | N            | 19999999 | 222.22 |
      | SELL_ITEM            | 10                | N                       | N              | N            | 19999999 | 111.11 |
    And [创建订单整单折扣计算获取单价04]创建订单添加商品时获取价格应该如下
      | itemCode | price  | distributionChannelCode |
      | 19999999 | 555.12 | 12                      |
    Then [创建订单整单折扣计算获取单价04]进行整单折扣计算后,订单行分摊如下
      | orderWideDiscountSubject | itemSapCode | needShip | unitPrice | preDiscount | calculationValue | boxRatio | quantity | totalInline | preDiscountTotal | orderDiscountTotal | distributionChannelCode |
      | PT00                     | 19999999    | 1        | 555.12    | 0           | 555.12           | 1        | 1        | 555.12      | 0                | 0                  | 12                      |

    Examples: 基础信息
      | saleOrgSapName | regionName | customerSapName | shippingRecipient | shippingTelephone | shippingAddress |


  @productPriceOrderDiscountCalc05
  Scenario Outline: [场景05]【医药商业】【创建订单】整单折扣计算取价格维度组 → 40且无优先级的分销渠道
  "[主要测试点]
  1. 销售组织: 医药商业
  2. 创建订单
  3. 输入商品数量后
  4. 价格维度组: 销售商/客户/商品
  5. 价格中心分销渠道为N"
    Given [创建订单整单折扣计算获取单价05]当前存在如下客户信息
      | saleOrgSapCode | regionCode | regionName   | customerSapCode | shippingAddress   | shippingRecipient   | shippingTelephone   |
      | 1017           | 340000     | <regionName> | 0010000262      | <shippingAddress> | <shippingRecipient> | <shippingTelephone> |
    When [创建订单整单折扣计算获取单价05]当前存在如下商品价格信息
      | dimensionCode        | dimensionPriority | distributionChannelCode | priceGroupCode | customerCode | itemCode | price  |
      | SELL_DIST_PRICE_ITEM | 50                | 99                      | 03             | N            | 19999999 | 555.99 |
      | SELL_CUST_ITEM       | 40                | N                       | N              | 0010000262   | 19999999 | 444.44 |
      | SELL_DIST_ITEM       | 30                | 21                      | N              | N            | 19999999 | 333.21 |
      | SELL_DIST_ITEM       | 30                | 22                      | N              | N            | 19999999 | 333.22 |
      | SELL_DIST_ITEM       | 30                | 12                      | N              | N            | 19999999 | 333.12 |
      | SELL_DIST_ITEM       | 30                | 99                      | N              | N            | 19999999 | 333.99 |
      | SELL_PRICE_ITEM      | 20                | N                       | 03             | N            | 19999999 | 222.22 |
      | SELL_ITEM            | 10                | N                       | N              | N            | 19999999 | 111.11 |
    And [创建订单整单折扣计算获取单价05]创建订单添加商品时获取价格应该如下
      | itemCode | price  | distributionChannelCode |
      | 19999999 | 444.44 | N                       |
    Then [创建订单整单折扣计算获取单价05]进行整单折扣计算后,订单行分摊如下
      | orderWideDiscountSubject | itemSapCode | needShip | unitPrice | preDiscount | calculationValue | boxRatio | quantity | totalInline | preDiscountTotal | orderDiscountTotal | distributionChannelCode |
      | PT00                     | 19999999    | 1        | 444.44    | 0           | 444.44           | 1        | 1        | 444.44      | 0                | 0                  | N                       |

    Examples: 基础信息
      | saleOrgSapName | regionName | customerSapName | shippingRecipient | shippingTelephone | shippingAddress |


  @productPriceOrderDiscountCalc06
  Scenario Outline: [场景06]【医药商业】【创建订单】整单折扣计算取价格维度组 → 30且优先级为1的分销渠道
  "[主要测试点]
  1. 销售组织: 医药商业
  2. 创建订单
  3. 输入商品数量后
  4. 价格维度组: 销售商/分销渠道/商品
  5. 根据价格中心配置表取优先级为1的分销渠道"
    Given [创建订单整单折扣计算获取单价06]当前存在如下客户信息
      | saleOrgSapCode | regionCode | regionName   | customerSapCode | shippingAddress   | shippingRecipient   | shippingTelephone   |
      | 1017           | 340000     | <regionName> | 0010000262      | <shippingAddress> | <shippingRecipient> | <shippingTelephone> |
    When [创建订单整单折扣计算获取单价06]当前存在如下商品价格信息
      | dimensionCode   | dimensionPriority | distributionChannelCode | priceGroupCode | customerCode | itemCode | price  |
      | SELL_DIST_ITEM  | 30                | 21                      | N              | N            | 19999999 | 333.21 |
      | SELL_DIST_ITEM  | 30                | 22                      | N              | N            | 19999999 | 333.22 |
      | SELL_DIST_ITEM  | 30                | 12                      | N              | N            | 19999999 | 333.12 |
      | SELL_DIST_ITEM  | 30                | 99                      | N              | N            | 19999999 | 333.99 |
      | SELL_PRICE_ITEM | 20                | N                       | 03             | N            | 19999999 | 222.22 |
      | SELL_ITEM       | 10                | N                       | N              | N            | 19999999 | 111.11 |
    And [创建订单整单折扣计算获取单价06]创建订单添加商品时获取价格应该如下
      | itemCode | price  | distributionChannelCode |
      | 19999999 | 333.21 | 21                      |
      | 19999999 | 333.22 | 22                      |
    Then [创建订单整单折扣计算获取单价06]进行整单折扣计算后,订单行分摊如下
      | orderWideDiscountSubject | itemSapCode | needShip | unitPrice | preDiscount | calculationValue | boxRatio | quantity | totalInline | preDiscountTotal | orderDiscountTotal | distributionChannelCode |
      | PT00                     | 19999999    | 1        | 333.21    | 0           | 333.21           | 1        | 1        | 333.21      | 0                | 0                  | 21                      |

    Examples: 基础信息
      | saleOrgSapName | regionName | customerSapName | shippingRecipient | shippingTelephone | shippingAddress |


  @productPriceOrderDiscountCalc07
  Scenario Outline: [场景07]【医药商业】【创建订单】整单折扣计算取价格维度组 → 30且优先级为2的分销渠道
  "[主要测试点]
  1. 销售组织: 医药商业
  2. 创建订单
  3. 输入商品数量后
  4. 价格维度组: 销售商/分销渠道/商品
  5. 根据价格中心配置表取优先级为2的分销渠道"
    Given [创建订单整单折扣计算获取单价07]当前存在如下客户信息
      | saleOrgSapCode | regionCode | regionName   | customerSapCode | shippingAddress   | shippingRecipient   | shippingTelephone   |
      | 1017           | 340000     | <regionName> | 0010000262      | <shippingAddress> | <shippingRecipient> | <shippingTelephone> |
    When [创建订单整单折扣计算获取单价07]当前存在如下商品价格信息
      | dimensionCode   | dimensionPriority | distributionChannelCode | priceGroupCode | customerCode | itemCode | price  |
      | SELL_DIST_ITEM  | 30                | 12                      | N              | N            | 19999999 | 333.12 |
      | SELL_DIST_ITEM  | 30                | 99                      | N              | N            | 19999999 | 333.99 |
      | SELL_PRICE_ITEM | 20                | N                       | 03             | N            | 19999999 | 222.22 |
      | SELL_ITEM       | 10                | N                       | N              | N            | 19999999 | 111.11 |
    And [创建订单整单折扣计算获取单价07]创建订单添加商品时获取价格应该如下
      | itemCode | price  | distributionChannelCode |
      | 19999999 | 333.12 | 12                      |
    Then [创建订单整单折扣计算获取单价07]进行整单折扣计算后,订单行分摊如下
      | orderWideDiscountSubject | itemSapCode | needShip | unitPrice | preDiscount | calculationValue | boxRatio | quantity | totalInline | preDiscountTotal | orderDiscountTotal | distributionChannelCode |
      | PT00                     | 19999999    | 1        | 333.12    | 0           | 333.12           | 1        | 1        | 333.12      | 0                | 0                  | 12                      |

    Examples: 基础信息
      | saleOrgSapName | regionName | customerSapName | shippingRecipient | shippingTelephone | shippingAddress |


  @productPriceOrderDiscountCalc08
  Scenario Outline: [场景08]【医药商业】【创建订单】整单折扣计算取价格维度组 → 20且无优先级的分销渠道
  "[主要测试点]
  1. 销售组织: 医药商业
  2. 创建订单
  3. 输入商品数量后
  4. 价格维度组: 销售商/价格组/商品
  5. 价格中心配置表无此分销渠道"
    Given [创建订单整单折扣计算获取单价08]当前存在如下客户信息
      | saleOrgSapCode | regionCode | regionName   | customerSapCode | shippingAddress   | shippingRecipient   | shippingTelephone   |
      | 1017           | 340000     | <regionName> | 0010000262      | <shippingAddress> | <shippingRecipient> | <shippingTelephone> |
    When [创建订单整单折扣计算获取单价08]当前存在如下商品价格信息
      | dimensionCode   | dimensionPriority | distributionChannelCode | priceGroupCode | customerCode | itemCode | price  |
      | SELL_DIST_ITEM  | 30                | 99                      | N              | N            | 19999999 | 333.99 |
      | SELL_PRICE_ITEM | 20                | N                       | 03             | N            | 19999999 | 222.22 |
      | SELL_ITEM       | 10                | N                       | N              | N            | 19999999 | 111.11 |
    And [创建订单整单折扣计算获取单价08]创建订单添加商品时获取价格应该如下
      | itemCode | price  | distributionChannelCode |
      | 19999999 | 222.22 | N                       |
    Then [创建订单整单折扣计算获取单价08]进行整单折扣计算后,订单行分摊如下
      | orderWideDiscountSubject | itemSapCode | needShip | unitPrice | preDiscount | calculationValue | boxRatio | quantity | totalInline | preDiscountTotal | orderDiscountTotal | distributionChannelCode |
      | PT00                     | 19999999    | 1        | 222.22    | 0           | 222.22           | 1        | 1        | 222.22      | 0                | 0                  | 22                      |

    Examples: 基础信息
      | saleOrgSapName | regionName | customerSapName | shippingRecipient | shippingTelephone | shippingAddress |


  @productPriceOrderDiscountCalc09
  Scenario Outline: [场景09]【医药商业】【创建订单】整单折扣计算取价格维度组 → 10且无优先级的分销渠道
  "[主要测试点]
  1. 销售组织: 医药商业
  2. 创建订单
  3. 输入商品数量后
  4. 价格维度组: 销售商/商品
  5. 价格中心分销渠道为N"
    Given [创建订单整单折扣计算获取单价09]当前存在如下客户信息
      | saleOrgSapCode | regionCode | regionName   | customerSapCode | shippingAddress   | shippingRecipient   | shippingTelephone   |
      | 1017           | 340000     | <regionName> | 0010000262      | <shippingAddress> | <shippingRecipient> | <shippingTelephone> |
    When [创建订单整单折扣计算获取单价09]当前存在如下商品价格信息
      | dimensionCode | dimensionPriority | distributionChannelCode | priceGroupCode | customerCode | itemCode | price  |
      | SELL_ITEM     | 10                | N                       | N              | N            | 19999999 | 111.11 |
    And [创建订单整单折扣计算获取单价09]创建订单添加商品时获取价格应该如下
      | itemCode | price  | distributionChannelCode |
      | 19999999 | 111.11 | N                       |
    Then [创建订单整单折扣计算获取单价09]进行整单折扣计算后,订单行分摊如下
      | orderWideDiscountSubject | itemSapCode | needShip | unitPrice | preDiscount | calculationValue | boxRatio | quantity | totalInline | preDiscountTotal | orderDiscountTotal | distributionChannelCode |
      | PT00                     | 19999999    | 1        | 111.11    | 0           | 111.11           | 1        | 1        | 111.11      | 0                | 0                  | N                       |

    Examples: 基础信息
      | saleOrgSapName | regionName | customerSapName | shippingRecipient | shippingTelephone | shippingAddress |


  @productPriceOrderDiscountCalc10
  Scenario Outline: [场景10]【江苏帝益】创建订单整单折扣计算取价格维度组 → 60且优先级为1的分销渠道
  "[主要测试点]
  1. 销售组织: 江苏帝益
  2. 创建订单
  3. 输入商品数量后
  4. 价格维度组: 销售商/分销渠道/客户/商品
  5. 根据价格中心配置表取优先级为1的分销渠道"
    Given [创建订单整单折扣计算获取单价10]当前存在如下客户信息
      | saleOrgSapCode | regionCode | regionName   | customerSapCode | shippingAddress   | shippingRecipient   | shippingTelephone   |
      | 2810           | 140000     | <regionName> | 0010644902      | <shippingAddress> | <shippingRecipient> | <shippingTelephone> |
    When [创建订单整单折扣计算获取单价10]当前存在如下商品价格信息
      | dimensionCode        | dimensionPriority | distributionChannelCode | priceGroupCode | customerCode | itemCode | price  |
      | SELL_DIST_CUST_ITEM  | 60                | 21                      | N              | 0010644902   | 19999999 | 666.21 |
      | SELL_DIST_CUST_ITEM  | 60                | 22                      | N              | 0010644902   | 19999999 | 666.22 |
      | SELL_DIST_CUST_ITEM  | 60                | 25                      | N              | 0010644902   | 19999999 | 666.25 |
      | SELL_DIST_CUST_ITEM  | 60                | 99                      | N              | 0010644902   | 19999999 | 666.99 |
      | SELL_DIST_PRICE_ITEM | 50                | 21                      | 19             | N            | 19999999 | 555.21 |
      | SELL_DIST_PRICE_ITEM | 50                | 22                      | 19             | N            | 19999999 | 555.22 |
      | SELL_DIST_PRICE_ITEM | 50                | 25                      | 19             | N            | 19999999 | 555.25 |
      | SELL_DIST_PRICE_ITEM | 50                | 99                      | 19             | N            | 19999999 | 555.99 |
      | SELL_CUST_ITEM       | 40                | N                       | N              | 0010644902   | 19999999 | 444.44 |
      | SELL_DIST_ITEM       | 30                | 21                      | N              | N            | 19999999 | 333.21 |
      | SELL_DIST_ITEM       | 30                | 22                      | N              | N            | 19999999 | 333.22 |
      | SELL_DIST_ITEM       | 30                | 25                      | N              | N            | 19999999 | 333.25 |
      | SELL_DIST_ITEM       | 30                | 99                      | N              | N            | 19999999 | 333.99 |
      | SELL_PRICE_ITEM      | 20                | N                       | 19             | N            | 19999999 | 222.22 |
      | SELL_ITEM            | 10                | N                       | N              | N            | 19999999 | 111.11 |
    And [创建订单整单折扣计算获取单价10]创建订单添加商品时获取价格应该如下
      | itemCode | price  | distributionChannelCode |
      | 19999999 | 666.25 | 25                      |
    Then [创建订单整单折扣计算获取单价10]进行整单折扣计算后,订单行分摊如下
      | orderWideDiscountSubject | itemSapCode | needShip | unitPrice | preDiscount | calculationValue | boxRatio | quantity | totalInline | preDiscountTotal | orderDiscountTotal | distributionChannelCode |
      | PT00                     | 19999999    | 1        | 666.25    | 0           | 666.25           | 1        | 1        | 666.25      | 0                | 0                  | 25                      |

    Examples: 基础信息
      | saleOrgSapName | regionName | customerSapName | shippingRecipient | shippingTelephone | shippingAddress |


  @productPriceOrderDiscountCalc11
  Scenario Outline: [场景11]【江苏帝益】创建订单整单折扣计算取价格维度组 → 50且优先级为1的分销渠道
  "[主要测试点]
  1. 销售组织: 江苏帝益
  2. 创建订单
  3. 输入商品数量后
  4. 价格维度组: 销售商/分销渠道/客户/商品
  5. 根据价格中心配置表取优先级为1的分销渠道"
    Given [创建订单整单折扣计算获取单价11]当前存在如下客户信息
      | saleOrgSapCode | regionCode | regionName   | customerSapCode | shippingAddress   | shippingRecipient   | shippingTelephone   |
      | 2810           | 140000     | <regionName> | 0010644902      | <shippingAddress> | <shippingRecipient> | <shippingTelephone> |
    When [创建订单整单折扣计算获取单价11]当前存在如下商品价格信息
      | dimensionCode        | dimensionPriority | distributionChannelCode | priceGroupCode | customerCode | itemCode | price  |
      | SELL_DIST_CUST_ITEM  | 60                | 21                      | N              | 0010644902   | 19999999 | 666.21 |
      | SELL_DIST_CUST_ITEM  | 60                | 22                      | N              | 0010644902   | 19999999 | 666.22 |
      | SELL_DIST_CUST_ITEM  | 60                | 99                      | N              | 0010644902   | 19999999 | 666.99 |
      | SELL_DIST_PRICE_ITEM | 50                | 21                      | 19             | N            | 19999999 | 555.21 |
      | SELL_DIST_PRICE_ITEM | 50                | 22                      | 19             | N            | 19999999 | 555.22 |
      | SELL_DIST_PRICE_ITEM | 50                | 25                      | 19             | N            | 19999999 | 555.25 |
      | SELL_DIST_PRICE_ITEM | 50                | 99                      | 19             | N            | 19999999 | 555.99 |
      | SELL_CUST_ITEM       | 40                | N                       | N              | 0010644902   | 19999999 | 444.44 |
      | SELL_DIST_ITEM       | 30                | 21                      | N              | N            | 19999999 | 333.21 |
      | SELL_DIST_ITEM       | 30                | 22                      | N              | N            | 19999999 | 333.22 |
      | SELL_DIST_ITEM       | 30                | 25                      | N              | N            | 19999999 | 333.25 |
      | SELL_DIST_ITEM       | 30                | 99                      | N              | N            | 19999999 | 333.99 |
      | SELL_PRICE_ITEM      | 20                | N                       | 19             | N            | 19999999 | 222.22 |
      | SELL_ITEM            | 10                | N                       | N              | N            | 19999999 | 111.11 |
    Then [创建订单整单折扣计算获取单价11]创建订单添加商品时获取价格应该如下
      | itemCode | price  | distributionChannelCode |
      | 19999999 | 555.25 | 25                      |
    Then [创建订单整单折扣计算获取单价11]进行整单折扣计算后,订单行分摊如下
      | orderWideDiscountSubject | itemSapCode | needShip | unitPrice | preDiscount | calculationValue | boxRatio | quantity | totalInline | preDiscountTotal | orderDiscountTotal | distributionChannelCode |
      | PT00                     | 19999999    | 1        | 555.25    | 0           | 555.25           | 1        | 1        | 555.25      | 0                | 0                  | 25                      |

    Examples: 基础信息
      | saleOrgSapName | regionName | customerSapName | shippingRecipient | shippingTelephone | shippingAddress |


  @productPriceOrderDiscountCalc12
  Scenario Outline: [场景12]【医药商业】【创建订单】整单折扣计算取价格维度组 → 50且使用SAP回传企业中心的价格维度组
  "[主要测试点]
  1. 销售组织: 医药商业
  2. 创建订单
  3. 输入商品数量后
  4. 价格维度组: 销售商/分销渠道/价格组/商品
  5. 根据价格中心配置表取优先级为1的分销渠道
  6. 根据企业中心ch_sap_sale表的价格组进行查询"
    Given [创建订单整单折扣计算获取单价12]当前存在如下客户信息
      | saleOrgSapCode | regionCode | regionName   | customerSapCode | shippingAddress   | shippingRecipient   | shippingTelephone   |
      | 1017           | 230000     | <regionName> | 0010001196      | <shippingAddress> | <shippingRecipient> | <shippingTelephone> |
    When [创建订单整单折扣计算获取单价12]当前存在如下商品价格信息
      | dimensionCode        | dimensionPriority | distributionChannelCode | priceGroupCode | customerCode | itemCode | price  |
      | SELL_DIST_CUST_ITEM  | 60                | 99                      | N              | 0010001196   | 19999999 | 666.99 |
      | SELL_DIST_PRICE_ITEM | 50                | 21                      | 04             | N            | 19999999 | 504.21 |
      | SELL_DIST_PRICE_ITEM | 50                | 22                      | 04             | N            | 19999999 | 504.22 |
      | SELL_DIST_PRICE_ITEM | 50                | 12                      | 04             | N            | 19999999 | 504.12 |
      | SELL_DIST_PRICE_ITEM | 50                | 99                      | 04             | N            | 19999999 | 504.99 |
      | SELL_DIST_PRICE_ITEM | 50                | 21                      | 08             | N            | 19999999 | 508.21 |
      | SELL_DIST_PRICE_ITEM | 50                | 22                      | 08             | N            | 19999999 | 508.22 |
      | SELL_DIST_PRICE_ITEM | 50                | 12                      | 08             | N            | 19999999 | 508.12 |
      | SELL_DIST_PRICE_ITEM | 50                | 99                      | 08             | N            | 19999999 | 508.99 |
      | SELL_CUST_ITEM       | 40                | N                       | N              | 0010001196   | 19999999 | 444.44 |
      | SELL_DIST_ITEM       | 30                | 21                      | N              | N            | 19999999 | 333.21 |
      | SELL_DIST_ITEM       | 30                | 22                      | N              | N            | 19999999 | 333.22 |
      | SELL_DIST_ITEM       | 30                | 12                      | N              | N            | 19999999 | 333.12 |
      | SELL_DIST_ITEM       | 30                | 99                      | N              | N            | 19999999 | 333.99 |
      | SELL_PRICE_ITEM      | 20                | N                       | 03             | N            | 19999999 | 222.22 |
      | SELL_ITEM            | 10                | N                       | N              | N            | 19999999 | 111.11 |
    And [创建订单整单折扣计算获取单价12]创建订单添加商品时获取价格应该如下
      | itemCode | price  | distributionChannelCode |
      | 19999999 | 504.21 | 21                      |
      | 19999999 | 504.22 | 22                      |
    Then [创建订单整单折扣计算获取单价12]进行整单折扣计算后,订单行分摊如下
      | orderWideDiscountSubject | itemSapCode | needShip | unitPrice | preDiscount | calculationValue | boxRatio | quantity | totalInline | preDiscountTotal | orderDiscountTotal | distributionChannelCode |
      | PT00                     | 19999999    | 1        | 504.21    | 0           | 504.21           | 1        | 1        | 504.21      | 0                | 0                  | 21                      |

    Examples: 基础信息
      | saleOrgSapName | regionName | customerSapName | shippingRecipient | shippingTelephone | shippingAddress |


  @productPriceOrderDiscountCalc13
  Scenario Outline: [场景13]【医药商业】【创建订单】整单折扣计算取价格维度组 → 20且使用SAP回传企业中心的价格维度组
  "[主要测试点]
  1. 销售组织: 医药商业
  2. 创建订单
  3. 输入商品数量后
  4. 价格维度组: 销售商/价格组/商品
  5. 价格中心配置表无此分销渠道
  6. 根据企业中心ch_sap_sale表的价格组进行查询"
    Given [创建订单整单折扣计算获取单价12]当前存在如下客户信息
      | saleOrgSapCode | regionCode | regionName   | customerSapCode | shippingAddress   | shippingRecipient   | shippingTelephone   |
      | 1017           | 230000     | <regionName> | 0010001196      | <shippingAddress> | <shippingRecipient> | <shippingTelephone> |
    When [创建订单整单折扣计算获取单价12]当前存在如下商品价格信息
      | dimensionCode        | dimensionPriority | distributionChannelCode | priceGroupCode | customerCode | itemCode | price  |
      | SELL_DIST_CUST_ITEM  | 60                | 99                      | N              | 0010001196   | 19999999 | 666.99 |
      | SELL_DIST_PRICE_ITEM | 50                | 21                      | 04             | N            | 19999999 | 504.21 |
      | SELL_DIST_PRICE_ITEM | 50                | 22                      | 04             | N            | 19999999 | 504.22 |
      | SELL_DIST_PRICE_ITEM | 50                | 12                      | 04             | N            | 19999999 | 504.12 |
      | SELL_DIST_PRICE_ITEM | 50                | 99                      | 04             | N            | 19999999 | 504.99 |
      | SELL_DIST_PRICE_ITEM | 50                | 21                      | 08             | N            | 19999999 | 508.21 |
      | SELL_DIST_PRICE_ITEM | 50                | 22                      | 08             | N            | 19999999 | 508.22 |
      | SELL_DIST_PRICE_ITEM | 50                | 12                      | 08             | N            | 19999999 | 508.12 |
      | SELL_DIST_PRICE_ITEM | 50                | 99                      | 08             | N            | 19999999 | 508.99 |
      | SELL_CUST_ITEM       | 40                | N                       | N              | 0010001196   | 19999999 | 444.44 |
      | SELL_DIST_ITEM       | 30                | 21                      | N              | N            | 19999999 | 333.21 |
      | SELL_DIST_ITEM       | 30                | 22                      | N              | N            | 19999999 | 333.22 |
      | SELL_DIST_ITEM       | 30                | 12                      | N              | N            | 19999999 | 333.12 |
      | SELL_DIST_ITEM       | 30                | 99                      | N              | N            | 19999999 | 333.99 |
      | SELL_PRICE_ITEM      | 20                | N                       | 03             | N            | 19999999 | 222.22 |
      | SELL_ITEM            | 10                | N                       | N              | N            | 19999999 | 111.11 |
    And [创建订单整单折扣计算获取单价12]创建订单添加商品时获取价格应该如下
      | itemCode | price  | distributionChannelCode |
      | 19999999 | 504.21 | 21                      |
      | 19999999 | 504.22 | 22                      |
    Then [创建订单整单折扣计算获取单价12]进行整单折扣计算后,订单行分摊如下
      | orderWideDiscountSubject | itemSapCode | needShip | unitPrice | preDiscount | calculationValue | boxRatio | quantity | totalInline | preDiscountTotal | orderDiscountTotal | distributionChannelCode |
      | PT00                     | 19999999    | 1        | 504.21    | 0           | 504.21           | 1        | 1        | 504.21      | 0                | 0                  | 21                      |

    Examples: 基础信息
      | saleOrgSapName | regionName | customerSapName | shippingRecipient | shippingTelephone | shippingAddress |
     