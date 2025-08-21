Feature: 商品获取单价逻辑

  Background: [前置条件]测试数据初始化
    Given [创建订单选择项目商品基础数据01]清除测试数据
    When [创建订单选择项目商品基础数据01]商品中心导入测试数据


  @productPriceOrderSelectProject01
  Scenario Outline: [场景01]【医药商业】【创建订单】选择项目取价格维度组 → 60且优先级为1的分销渠道
  "[主要测试点]
  1. 销售组织: 医药商业
  2. 整单折扣: 纯赠\捐赠
  3. 板块为: OTC
  4. 点击【选择项目】
  5. 价格维度组: 销售商/分销渠道/客户/商品
  6. 根据价格中心配置表取优先级为1的分销渠道"
    Given [创建订单选择项目获取单价01]当前存在如下客户信息
      | saleOrgSapCode | regionCode | customerSapCode |
      | 1017           | 340000     | 0010000262      |
    When [创建订单选择项目获取单价01]当前存在如下商品价格信息
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
    And [创建订单选择项目获取单价01]当前存在如下的整单折扣申请单信息
      | projectType | module |
      | PT04        | KM03   |
    And [创建订单选择项目获取单价01]当前存在如下的整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 19999999    | 100      | 盒     |
    And [创建订单选择项目获取单价01]当前存在如下的整单折扣分配计划信息
    And [创建订单选择项目获取单价01]当前存在如下的整单折扣分配计划详细信息
      | giftItemSapCode | allocationQuantity | needShip | invalidStatus |
      | 19999999        | 100                | 1        | NORMAL        |
    Then [创建订单选择项目获取单价01]创建订单选择项目时获取价格应该如下
      | itemCode | price  | distributionChannelCode |
      | 19999999 | 666.22 | 22                      |
    Examples: 基础信息
      | saleOrgSapName | regionName | customerSapName |


  @productPriceOrderSelectProject02
  Scenario Outline: [场景02]【医药商业】【创建订单】选择项目取价格维度组 → 60且优先级为2的分销渠道
  "[主要测试点]
  1. 销售组织: 医药商业
  2. 整单折扣: 纯赠\捐赠
  3. 板块为: OTC
  4. 点击【选择项目】
  5. 价格维度组: 销售商/分销渠道/客户/商品
  6. 根据价格中心配置表取优先级为2的分销渠道"
    Given [创建订单选择项目获取单价02]当前存在如下客户信息
      | saleOrgSapCode | regionCode | customerSapCode |
      | 1017           | 340000     | 0010000262      |
    When [创建订单选择项目获取单价02]当前存在如下商品价格信息
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
    And [创建订单选择项目获取单价02]当前存在如下的整单折扣申请单信息
      | projectType | module |
      | PT04        | KM03   |
    And [创建订单选择项目获取单价02]当前存在如下的整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 19999999    | 100      | 盒     |
    And [创建订单选择项目获取单价02]当前存在如下的整单折扣分配计划信息
    And [创建订单选择项目获取单价02]当前存在如下的整单折扣分配计划详细信息
      | giftItemSapCode | allocationQuantity | needShip | invalidStatus |
      | 19999999        | 100                | 1        | NORMAL        |
    Then [创建订单选择项目获取单价02]创建订单选择项目时获取价格应该如下
      | itemCode | price  | distributionChannelCode |
      | 19999999 | 666.12 | 12                      |
    Examples: 基础信息
      | saleOrgSapName | regionName | customerSapName |


  @productPriceOrderSelectProject03
  Scenario Outline: [场景03]【医药商业】【创建订单】选择项目取价格维度组 → 50且优先级为1的分销渠道
  "[主要测试点]
  1. 销售组织: 医药商业
  2. 整单折扣: 纯赠\捐赠
  3. 板块为: OTC
  4. 点击【选择项目】
  5. 价格维度组: 销售商/分销渠道/价格组/商品
  6. 根据价格中心配置表取优先级为1的分销渠道"
    Given [创建订单选择项目获取单价03]当前存在如下客户信息
      | saleOrgSapCode | regionCode | customerSapCode |
      | 1017           | 340000     | 0010000262      |
    When [创建订单选择项目获取单价03]当前存在如下商品价格信息
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
    And [创建订单选择项目获取单价03]当前存在如下的整单折扣申请单信息
      | projectType | module |
      | PT04        | KM03   |
    And [创建订单选择项目获取单价03]当前存在如下的整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 19999999    | 100      | 盒     |
    And [创建订单选择项目获取单价03]当前存在如下的整单折扣分配计划信息
    And [创建订单选择项目获取单价03]当前存在如下的整单折扣分配计划详细信息
      | giftItemSapCode | allocationQuantity | needShip | invalidStatus |
      | 19999999        | 100                | 1        | NORMAL        |
    Then [创建订单选择项目获取单价03]创建订单选择项目时获取价格应该如下
      | itemCode | price  | distributionChannelCode |
      | 19999999 | 555.22 | 22                      |
    Examples: 基础信息
      | saleOrgSapName | regionName | customerSapName |


  @productPriceOrderSelectProject04
  Scenario Outline: [场景04]【医药商业】【创建订单】选择项目取价格维度组 → 50且优先级为2的分销渠道
  "[主要测试点]
  1. 销售组织: 医药商业
  2. 整单折扣: 纯赠\捐赠
  3. 板块为: OTC
  4. 点击【选择项目】
  5. 价格维度组: 销售商/分销渠道/价格组/商品
  6. 根据价格中心配置表取优先级为2的分销渠道"
    Given [创建订单选择项目获取单价04]当前存在如下客户信息
      | saleOrgSapCode | regionCode | customerSapCode |
      | 1017           | 340000     | 0010000262      |
    When [创建订单选择项目获取单价04]当前存在如下商品价格信息
      | dimensionCode        | dimensionPriority | distributionChannelCode | priceGroupCode | customerCode | itemCode | price  |
      | SELL_DIST_PRICE_ITEM | 50                | 21                      | 03             | N            | 19999999 | 555.21 |
      | SELL_DIST_PRICE_ITEM | 50                | 12                      | 03             | N            | 19999999 | 555.12 |
      | SELL_DIST_PRICE_ITEM | 50                | 99                      | 03             | N            | 19999999 | 555.99 |
      | SELL_CUST_ITEM       | 40                | N                       | N              | 0010000262   | 19999999 | 444.44 |
      | SELL_DIST_ITEM       | 30                | 21                      | N              | N            | 19999999 | 333.21 |
      | SELL_DIST_ITEM       | 30                | 22                      | N              | N            | 19999999 | 333.22 |
      | SELL_DIST_ITEM       | 30                | 12                      | N              | N            | 19999999 | 333.12 |
      | SELL_DIST_ITEM       | 30                | 99                      | N              | N            | 19999999 | 333.99 |
      | SELL_PRICE_ITEM      | 20                | N                       | 03             | N            | 19999999 | 222.22 |
      | SELL_ITEM            | 10                | N                       | N              | N            | 19999999 | 111.11 |
    And [创建订单选择项目获取单价04]当前存在如下的整单折扣申请单信息
      | projectType | module |
      | PT04        | KM03   |
    And [创建订单选择项目获取单价04]当前存在如下的整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 19999999    | 100      | 盒     |
    And [创建订单选择项目获取单价04]当前存在如下的整单折扣分配计划信息
    And [创建订单选择项目获取单价04]当前存在如下的整单折扣分配计划详细信息
      | giftItemSapCode | allocationQuantity | needShip | invalidStatus |
      | 19999999        | 100                | 1        | NORMAL        |
    Then [创建订单选择项目获取单价04]创建订单选择项目时获取价格应该如下
      | itemCode | price  | distributionChannelCode |
      | 19999999 | 555.12 | 12                      |
    Examples: 基础信息
      | saleOrgSapName | regionName | customerSapName |


  @productPriceOrderSelectProject05
  Scenario Outline: [场景05]【医药商业】【创建订单】选择项目取价格维度组 → 40且无优先级的分销渠道
  "[主要测试点]
  1. 销售组织: 医药商业
  2. 整单折扣: 纯赠\捐赠
  3. 板块为: OTC
  4. 点击【选择项目】
  5. 价格维度组: 销售商/客户/商品
  6. 价格中心配置表无此分销渠道"
    Given [创建订单选择项目获取单价05]当前存在如下客户信息
      | saleOrgSapCode | regionCode | customerSapCode |
      | 1017           | 340000     | 0010000262      |
    When [创建订单选择项目获取单价05]当前存在如下商品价格信息
      | dimensionCode        | dimensionPriority | distributionChannelCode | priceGroupCode | customerCode | itemCode | price  |
      | SELL_DIST_PRICE_ITEM | 50                | 99                      | 03             | N            | 19999999 | 555.99 |
      | SELL_CUST_ITEM       | 40                | N                       | N              | 0010000262   | 19999999 | 444.44 |
      | SELL_DIST_ITEM       | 30                | 21                      | N              | N            | 19999999 | 333.21 |
      | SELL_DIST_ITEM       | 30                | 22                      | N              | N            | 19999999 | 333.22 |
      | SELL_DIST_ITEM       | 30                | 12                      | N              | N            | 19999999 | 333.12 |
      | SELL_DIST_ITEM       | 30                | 99                      | N              | N            | 19999999 | 333.99 |
      | SELL_PRICE_ITEM      | 20                | N                       | 03             | N            | 19999999 | 222.22 |
      | SELL_ITEM            | 10                | N                       | N              | N            | 19999999 | 111.11 |
    And [创建订单选择项目获取单价05]当前存在如下的整单折扣申请单信息
      | projectType | module |
      | PT04        | KM03   |
    And [创建订单选择项目获取单价05]当前存在如下的整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 19999999    | 100      | 盒     |
    And [创建订单选择项目获取单价05]当前存在如下的整单折扣分配计划信息
    And [创建订单选择项目获取单价05]当前存在如下的整单折扣分配计划详细信息
      | giftItemSapCode | allocationQuantity | needShip | invalidStatus |
      | 19999999        | 100                | 1        | NORMAL        |
    Then [创建订单选择项目获取单价05]创建订单选择项目时获取价格应该如下
      | itemCode | price  | distributionChannelCode |
      | 19999999 | 444.44 | N                       |
    Examples: 基础信息
      | saleOrgSapName | regionName | customerSapName |


  @productPriceOrderSelectProject06
  Scenario Outline: [场景06]【医药商业】【创建订单】选择项目取价格维度组 → 30且优先级为1的分销渠道
  "[主要测试点]
  1. 销售组织: 医药商业
  2. 整单折扣: 纯赠\捐赠
  3. 板块为: OTC
  4. 点击【选择项目】
  5. 价格维度组: 销售商/分销渠道/商品
  6. 根据价格中心配置表取优先级为1的分销渠道"
    Given [创建订单选择项目获取单价06]当前存在如下客户信息
      | saleOrgSapCode | regionCode | customerSapCode |
      | 1017           | 340000     | 0010000262      |
    When [创建订单选择项目获取单价06]当前存在如下商品价格信息
      | dimensionCode   | dimensionPriority | distributionChannelCode | priceGroupCode | customerCode | itemCode | price  |
      | SELL_DIST_ITEM  | 30                | 21                      | N              | N            | 19999999 | 333.21 |
      | SELL_DIST_ITEM  | 30                | 22                      | N              | N            | 19999999 | 333.22 |
      | SELL_DIST_ITEM  | 30                | 12                      | N              | N            | 19999999 | 333.12 |
      | SELL_DIST_ITEM  | 30                | 99                      | N              | N            | 19999999 | 333.99 |
      | SELL_PRICE_ITEM | 20                | N                       | 03             | N            | 19999999 | 222.22 |
      | SELL_ITEM       | 10                | N                       | N              | N            | 19999999 | 111.11 |
    And [创建订单选择项目获取单价06]当前存在如下的整单折扣申请单信息
      | projectType | module |
      | PT04        | KM03   |
    And [创建订单选择项目获取单价06]当前存在如下的整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 19999999    | 100      | 盒     |
    And [创建订单选择项目获取单价06]当前存在如下的整单折扣分配计划信息
    And [创建订单选择项目获取单价06]当前存在如下的整单折扣分配计划详细信息
      | giftItemSapCode | allocationQuantity | needShip | invalidStatus |
      | 19999999        | 100                | 1        | NORMAL        |
    Then [创建订单选择项目获取单价06]创建订单选择项目时获取价格应该如下
      | itemCode | price  | distributionChannelCode |
      | 19999999 | 333.22 | 22                      |
    Examples: 基础信息
      | saleOrgSapName | regionName | customerSapName |


  @productPriceOrderSelectProject07
  Scenario Outline: [场景07]【医药商业】【创建订单】选择项目取价格维度组 → 30且优先级为2的分销渠道
  "[主要测试点]
  1. 销售组织: 医药商业
  2. 整单折扣: 纯赠\捐赠
  3. 板块为: OTC
  4. 点击【选择项目】
  5. 价格维度组: 销售商/分销渠道/商品
  6. 根据价格中心配置表取优先级为2的分销渠道"
    Given [创建订单选择项目获取单价07]当前存在如下客户信息
      | saleOrgSapCode | regionCode | customerSapCode |
      | 1017           | 340000     | 0010000262      |
    When [创建订单选择项目获取单价07]当前存在如下商品价格信息
      | dimensionCode   | dimensionPriority | distributionChannelCode | priceGroupCode | customerCode | itemCode | price  |
      | SELL_DIST_ITEM  | 30                | 21                      | N              | N            | 19999999 | 333.21 |
      | SELL_DIST_ITEM  | 30                | 12                      | N              | N            | 19999999 | 333.12 |
      | SELL_DIST_ITEM  | 30                | 99                      | N              | N            | 19999999 | 333.99 |
      | SELL_PRICE_ITEM | 20                | N                       | 03             | N            | 19999999 | 222.22 |
      | SELL_ITEM       | 10                | N                       | N              | N            | 19999999 | 111.11 |
    And [创建订单选择项目获取单价07]当前存在如下的整单折扣申请单信息
      | projectType | module |
      | PT04        | KM03   |
    And [创建订单选择项目获取单价07]当前存在如下的整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 19999999    | 100      | 盒     |
    And [创建订单选择项目获取单价07]当前存在如下的整单折扣分配计划信息
    And [创建订单选择项目获取单价07]当前存在如下的整单折扣分配计划详细信息
      | giftItemSapCode | allocationQuantity | needShip | invalidStatus |
      | 19999999        | 100                | 1        | NORMAL        |
    Then [创建订单选择项目获取单价07]创建订单选择项目时获取价格应该如下
      | itemCode | price  | distributionChannelCode |
      | 19999999 | 333.12 | 12                      |
    Examples: 基础信息
      | saleOrgSapName | regionName | customerSapName |


  @productPriceOrderSelectProject08
  Scenario Outline: [场景08]【医药商业】【创建订单】选择项目取价格维度组 → 20且无优先级的分销渠道
  "[主要测试点]
  1. 销售组织: 医药商业
  2. 整单折扣: 纯赠\捐赠
  3. 板块为: OTC
  4. 点击【选择项目】
  5. 价格维度组: 销售商/价格组/商品
  6. 价格中心配置表无此分销渠道"
    Given [创建订单选择项目获取单价08]当前存在如下客户信息
      | saleOrgSapCode | regionCode | customerSapCode |
      | 1017           | 340000     | 0010000262      |
    When [创建订单选择项目获取单价08]当前存在如下商品价格信息
      | dimensionCode   | dimensionPriority | distributionChannelCode | priceGroupCode | customerCode | itemCode | price  |
      | SELL_DIST_ITEM  | 30                | 99                      | N              | N            | 19999999 | 333.99 |
      | SELL_PRICE_ITEM | 20                | N                       | 03             | N            | 19999999 | 222.22 |
      | SELL_ITEM       | 10                | N                       | N              | N            | 19999999 | 111.11 |
    And [创建订单选择项目获取单价08]当前存在如下的整单折扣申请单信息
      | projectType | module |
      | PT04        | KM03   |
    And [创建订单选择项目获取单价08]当前存在如下的整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 19999999    | 100      | 盒     |
    And [创建订单选择项目获取单价08]当前存在如下的整单折扣分配计划信息
    And [创建订单选择项目获取单价08]当前存在如下的整单折扣分配计划详细信息
      | giftItemSapCode | allocationQuantity | needShip | invalidStatus |
      | 19999999        | 100                | 1        | NORMAL        |
    Then [创建订单选择项目获取单价08]创建订单选择项目时获取价格应该如下
      | itemCode | price  | distributionChannelCode |
      | 19999999 | 222.22 | N                       |
    Examples: 基础信息
      | saleOrgSapName | regionName | customerSapName |


  @productPriceOrderSelectProject09
  Scenario Outline: [场景09]【医药商业】【创建订单】选择项目取价格维度组 → 10且无优先级的分销渠道
  "[主要测试点]
  1. 销售组织: 医药商业
  2. 整单折扣: 纯赠\捐赠
  3. 板块为: OTC
  4. 点击【选择项目】
  5. 价格维度组: 销售商/商品
  6. 价格中心配置表无此分销渠道"
    Given [创建订单选择项目获取单价09]当前存在如下客户信息
      | saleOrgSapCode | regionCode | customerSapCode |
      | 1017           | 340000     | 0010000262      |
    When [创建订单选择项目获取单价09]当前存在如下商品价格信息
      | dimensionCode | dimensionPriority | distributionChannelCode | priceGroupCode | customerCode | itemCode | price  |
      | SELL_ITEM     | 10                | N                       | N              | N            | 19999999 | 111.11 |
    And [创建订单选择项目获取单价09]当前存在如下的整单折扣申请单信息
      | projectType | module |
      | PT04        | KM03   |
    And [创建订单选择项目获取单价09]当前存在如下的整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 19999999    | 100      | 盒     |
    And [创建订单选择项目获取单价09]当前存在如下的整单折扣分配计划信息
    And [创建订单选择项目获取单价09]当前存在如下的整单折扣分配计划详细信息
      | giftItemSapCode | allocationQuantity | needShip | invalidStatus |
      | 19999999        | 100                | 1        | NORMAL        |
    Then [创建订单选择项目获取单价09]创建订单选择项目时获取价格应该如下
      | itemCode | price  | distributionChannelCode |
      | 19999999 | 111.11 | N                       |
    Examples: 基础信息
      | saleOrgSapName | regionName | customerSapName |


  @productPriceOrderSelectProject10
  Scenario Outline: [场景10]【医药商业】【创建订单】选择项目取价格维度组 → 60且无优先级的分销渠道
  "[主要测试点]
  1. 销售组织: 医药商业
  2. 整单折扣: 纯赠\捐赠
  3. 板块为: 商务
  4. 点击【选择项目】
  5. 价格维度组: 销售商/分销渠道/客户/商品
  6. 价格中心配置表无此分销渠道"
    Given [创建订单选择项目获取单价10]当前存在如下客户信息
      | saleOrgSapCode | regionCode | customerSapCode |
      | 1017           | 340000     | 0010000262      |
    When [创建订单选择项目获取单价10]当前存在如下商品价格信息
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
    And [创建订单选择项目获取单价10]当前存在如下的整单折扣申请单信息
      | projectType | module |
      | PT04        | KM01   |
    And [创建订单选择项目获取单价10]当前存在如下的整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 19999999    | 100      | 盒     |
    And [创建订单选择项目获取单价10]当前存在如下的整单折扣分配计划信息
    And [创建订单选择项目获取单价10]当前存在如下的整单折扣分配计划详细信息
      | giftItemSapCode | allocationQuantity | needShip | invalidStatus |
      | 19999999        | 100                | 1        | NORMAL        |
    Then [创建订单选择项目获取单价10]创建订单选择项目时应提示异常:"查询商品价格失败"
      | itemCode | price  | distributionChannelCode |
      | 19999999 | 666.21 | 21                      |
    Examples: 基础信息
      | saleOrgSapName | regionName | customerSapName |


  @productPriceOrderSelectProject11
  Scenario Outline: [场景11]【江苏帝益】创建订单选择项目取价格维度组 → 60且优先级为1的分销渠道
  "[主要测试点]
  1. 销售组织: 江苏帝益
  2. 整单折扣: 纯赠\捐赠
  3. 板块为: OTC
  4. 点击【选择项目】
  5. 价格维度组: 销售商/分销渠道/客户/商品
  6. 根据价格中心配置表取优先级为1的分销渠道"
    Given [创建订单选择项目获取单价11]当前存在如下客户信息
      | saleOrgSapCode | regionCode | customerSapCode |
      | 2810           | 140000     | 0010644902      |
    When [创建订单选择项目获取单价11]当前存在如下商品价格信息
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
    And [创建订单选择项目获取单价11]当前存在如下的整单折扣申请单信息
      | projectType | module |
      | PT04        | KM03   |
    And [创建订单选择项目获取单价11]当前存在如下的整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 19999999    | 100      | 盒     |
    And [创建订单选择项目获取单价11]当前存在如下的整单折扣分配计划信息
    And [创建订单选择项目获取单价11]当前存在如下的整单折扣分配计划详细信息
      | giftItemSapCode | allocationQuantity | needShip | invalidStatus |
      | 19999999        | 100                | 1        | NORMAL        |
    Then [创建订单选择项目获取单价11]创建订单选择项目时获取价格应该如下
      | itemCode | price  | distributionChannelCode |
      | 19999999 | 666.25 | 25                      |
    Examples: 基础信息
      | saleOrgSapName | regionName | customerSapName |


  @productPriceOrderSelectProject12
  Scenario Outline: [场景12]【江苏帝益】创建订单选择项目取价格维度组 → 50且优先级为1的分销渠道
  "[主要测试点]
  1. 销售组织: 江苏帝益
  2. 整单折扣: 纯赠\捐赠
  3. 板块为: OTC
  4. 点击【选择项目】
  5. 价格维度组: 销售商/客户/商品
  6. 根据价格中心配置表取优先级为1的分销渠道"
    Given [创建订单选择项目获取单价12]当前存在如下客户信息
      | saleOrgSapCode | regionCode | customerSapCode |
      | 2810           | 140000     | 0010644902      |
    When [创建订单选择项目获取单价12]当前存在如下商品价格信息
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
    And [创建订单选择项目获取单价12]当前存在如下的整单折扣申请单信息
      | projectType | module |
      | PT04        | KM03   |
    And [创建订单选择项目获取单价12]当前存在如下的整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 19999999    | 100      | 盒     |
    And [创建订单选择项目获取单价12]当前存在如下的整单折扣分配计划信息
    And [创建订单选择项目获取单价12]当前存在如下的整单折扣分配计划详细信息
      | giftItemSapCode | allocationQuantity | needShip | invalidStatus |
      | 19999999        | 100                | 1        | NORMAL        |
    Then [创建订单选择项目获取单价12]创建订单选择项目时获取价格应该如下
      | itemCode | price  | distributionChannelCode |
      | 19999999 | 555.25 | 25                      |
    Examples: 基础信息
      | saleOrgSapName | regionName | customerSapName |


  @productPriceOrderSelectProject13
  Scenario Outline: [场景13]【医药商业】【创建订单】选择项目取价格维度组 → 50且使用SAP回传企业中心的价格维度组
  "[主要测试点]
  1. 销售组织: 医药商业
  2. 整单折扣: 纯赠\捐赠
  3. 板块为: OTC
  4. 点击【选择项目】
  5. 价格维度组: 销售商/分销渠道/价格组/商品
  6. 根据价格中心配置表取优先级为1的分销渠道
  7. 根据企业中心ch_sap_sale表的价格组进行查询"
    Given [创建订单选择项目获取单价13]当前存在如下客户信息
      | saleOrgSapCode | regionCode | customerSapCode |
      | 1017           | 230000     | 0010001196      |
    When [创建订单选择项目获取单价13]当前存在如下商品价格信息
      | dimensionCode        | dimensionPriority | distributionChannelCode | priceGroupCode | customerCode | itemCode | price  |
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
      | SELL_PRICE_ITEM      | 20                | N                       | 04             | N            | 19999999 | 204.22 |
      | SELL_PRICE_ITEM      | 20                | N                       | 08             | N            | 19999999 | 208.22 |
      | SELL_ITEM            | 10                | N                       | N              | N            | 19999999 | 111.11 |
    And [创建订单选择项目获取单价13]当前存在如下的整单折扣申请单信息
      | projectType | module |
      | PT04        | KM03   |
    And [创建订单选择项目获取单价13]当前存在如下的整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 19999999    | 100      | 盒     |
    And [创建订单选择项目获取单价13]当前存在如下的整单折扣分配计划信息
    And [创建订单选择项目获取单价13]当前存在如下的整单折扣分配计划详细信息
      | giftItemSapCode | allocationQuantity | needShip | invalidStatus |
      | 19999999        | 100                | 1        | NORMAL        |
    Then [创建订单选择项目获取单价13]创建订单选择项目时获取价格应该如下
      | itemCode | price  | distributionChannelCode |
      | 19999999 | 504.22 | 22                      |
    Examples: 基础信息
      | saleOrgSapName | regionName | customerSapName |


  @productPriceOrderSelectProject14
  Scenario Outline: [场景14]【医药商业】【创建订单】选择项目取价格维度组 → 20且使用SAP回传企业中心的价格维度组
  "[主要测试点]
  1. 销售组织: 医药商业
  2. 整单折扣: 纯赠\捐赠
  3. 板块为: OTC
  4. 点击【选择项目】
  5. 价格维度组: 销售商/价格组/商品
  6. 价格中心配置表无此分销渠道
  7. 根据企业中心ch_sap_sale表的价格组进行查询"
    Given [创建订单选择项目获取单价14]当前存在如下客户信息
      | saleOrgSapCode | regionCode | customerSapCode |
      | 1017           | 230000     | 0010001196      |
    When [创建订单选择项目获取单价14]当前存在如下商品价格信息
      | dimensionCode   | dimensionPriority | distributionChannelCode | priceGroupCode | customerCode | itemCode | price  |
      | SELL_DIST_ITEM  | 30                | 99                      | N              | N            | 19999999 | 333.99 |
      | SELL_PRICE_ITEM | 20                | N                       | 04             | N            | 19999999 | 204.22 |
      | SELL_PRICE_ITEM | 20                | N                       | 08             | N            | 19999999 | 208.22 |
      | SELL_ITEM       | 10                | N                       | N              | N            | 19999999 | 111.11 |
    And [创建订单选择项目获取单价14]当前存在如下的整单折扣申请单信息
      | projectType | module |
      | PT04        | KM03   |
    And [创建订单选择项目获取单价14]当前存在如下的整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 19999999    | 100      | 盒     |
    And [创建订单选择项目获取单价14]当前存在如下的整单折扣分配计划信息
    And [创建订单选择项目获取单价14]当前存在如下的整单折扣分配计划详细信息
      | giftItemSapCode | allocationQuantity | needShip | invalidStatus |
      | 19999999        | 100                | 1        | NORMAL        |
    Then [创建订单选择项目获取单价14]创建订单选择项目时获取价格应该如下
      | itemCode | price  | distributionChannelCode |
      | 19999999 | 204.22 | N                       |
    Examples: 基础信息
      | saleOrgSapName | regionName | customerSapName |
     