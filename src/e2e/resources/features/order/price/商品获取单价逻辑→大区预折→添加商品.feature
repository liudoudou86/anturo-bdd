Feature: 商品获取单价逻辑

  Background: [前置条件]测试数据初始化
    Given [大区预折商品基础数据01]清除测试数据
    When [大区预折商品基础数据01]商品中心导入测试数据


  @productPriceRegionPreDiscount01
  Scenario Outline: [场景01]【医药商业】【大区预折】添加商品取价格维度组 → 50且优先级为1的分销渠道
  "[主要测试点]
  1. 大区预折
  2. 点击【添加商品】
  3. 价格维度组: 销售商/分销渠道/价格组/商品
  4. 根据价格中心配置表取优先级为1的分销渠道"
    Given [大区预折商品获取单价01]当前存在如下客户信息
      | saleOrgSapCode | regionCode | regionName   | customerSapCode |
      | 1017           | 340000     | <regionName> | 0010000262      |
    When [大区预折商品获取单价01]当前存在如下商品价格信息
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
    Then [大区预折商品获取单价01]预折添加商品时获取价格应该如下
      | preType             | itemCode | price  | distributionChannelCode |
      | REGION_PRE_DISCOUNT | 19999999 | 555.21 | 21                      |
      | REGION_PRE_DISCOUNT | 19999999 | 555.22 | 22                      |

    Examples: 基础信息
      | saleOrgSapName | regionName | customerSapName |


  @productPriceRegionPreDiscount02
  Scenario Outline: [场景02]【医药商业】【大区预折】添加商品取价格维度组 → 50且优先级为2的分销渠道
  "[主要测试点]
  1. 大区预折
  2. 点击【添加商品】
  3. 价格维度组: 销售商/分销渠道/价格组/商品
  4. 根据价格中心配置表取优先级为2的分销渠道"
    Given [大区预折商品获取单价02]当前存在如下客户信息
      | saleOrgSapCode | regionCode | regionName   | customerSapCode |
      | 1017           | 340000     | <regionName> | 0010000262      |
    When [大区预折商品获取单价02]当前存在如下商品价格信息
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
    Then [大区预折商品获取单价02]预折添加商品时获取价格应该如下
      | preType             | itemCode | price  | distributionChannelCode |
      | REGION_PRE_DISCOUNT | 19999999 | 555.12 | 12                      |

    Examples: 基础信息
      | saleOrgSapName | regionName | customerSapName |


  @productPriceRegionPreDiscount03
  Scenario Outline: [场景03]【医药商业】【大区预折】添加商品取价格维度组 → 30且优先级为1的分销渠道
  "[主要测试点]
  1. 大区预折
  2. 点击【添加商品】
  3. 价格维度组: 销售商/分销渠道/商品
  4. 根据价格中心配置表取优先级为1的分销渠道"
    Given [大区预折商品获取单价03]当前存在如下客户信息
      | saleOrgSapCode | regionCode | regionName   | customerSapCode |
      | 1017           | 340000     | <regionName> | 0010000262      |
    When [大区预折商品获取单价03]当前存在如下商品价格信息
      | dimensionCode        | dimensionPriority | distributionChannelCode | priceGroupCode | customerCode | itemCode | price  |
      | SELL_DIST_PRICE_ITEM | 50                | 99                      | 03             | N            | 19999999 | 555.99 |
      | SELL_CUST_ITEM       | 40                | N                       | N              | 0010000262   | 19999999 | 444.44 |
      | SELL_DIST_ITEM       | 30                | 21                      | N              | N            | 19999999 | 333.21 |
      | SELL_DIST_ITEM       | 30                | 22                      | N              | N            | 19999999 | 333.22 |
      | SELL_DIST_ITEM       | 30                | 12                      | N              | N            | 19999999 | 333.12 |
      | SELL_DIST_ITEM       | 30                | 99                      | N              | N            | 19999999 | 333.99 |
      | SELL_PRICE_ITEM      | 20                | N                       | 03             | N            | 19999999 | 222.22 |
      | SELL_ITEM            | 10                | N                       | N              | N            | 19999999 | 111.11 |
    Then [大区预折商品获取单价03]预折添加商品时获取价格应该如下
      | preType             | itemCode | price  | distributionChannelCode |
      | REGION_PRE_DISCOUNT | 19999999 | 333.21 | 21                      |
      | REGION_PRE_DISCOUNT | 19999999 | 333.22 | 22                      |

    Examples: 基础信息
      | saleOrgSapName | regionName | customerSapName |


  @productPriceRegionPreDiscount04
  Scenario Outline: [场景04]【医药商业】【大区预折】添加商品取价格维度组 → 30且优先级为2的分销渠道
  "[主要测试点]
  1. 大区预折
  2. 点击【添加商品】
  3. 价格维度组: 销售商/分销渠道/商品
  4. 根据价格中心配置表取优先级为2的分销渠道"
    Given [大区预折商品获取单价04]当前存在如下客户信息
      | saleOrgSapCode | regionCode | regionName   | customerSapCode |
      | 1017           | 340000     | <regionName> | 0010000262      |
    When [大区预折商品获取单价04]当前存在如下商品价格信息
      | dimensionCode   | dimensionPriority | distributionChannelCode | priceGroupCode | customerCode | itemCode | price  |
      | SELL_DIST_ITEM  | 30                | 12                      | N              | N            | 19999999 | 333.12 |
      | SELL_DIST_ITEM  | 30                | 99                      | N              | N            | 19999999 | 333.99 |
      | SELL_PRICE_ITEM | 20                | N                       | 03             | N            | 19999999 | 222.22 |
      | SELL_ITEM       | 10                | N                       | N              | N            | 19999999 | 111.11 |
    Then [大区预折商品获取单价04]预折添加商品时获取价格应该如下
      | preType             | itemCode | price  | distributionChannelCode |
      | REGION_PRE_DISCOUNT | 19999999 | 333.12 | 12                      |

    Examples: 基础信息
      | saleOrgSapName | regionName | customerSapName |


  @productPriceRegionPreDiscount05
  Scenario Outline: [场景05]【医药商业】【大区预折】添加商品取价格维度组 → 20且无优先级的分销渠道
  "[主要测试点]
  1. 大区预折
  2. 点击【添加商品】
  3. 价格维度组: 销售商/价格组/商品
  4. 价格中心配置表无此分销渠道"
    Given [大区预折商品获取单价05]当前存在如下客户信息
      | saleOrgSapCode | regionCode | regionName   | customerSapCode |
      | 1017           | 340000     | <regionName> | 0010000262      |
    When [大区预折商品获取单价05]当前存在如下商品价格信息
      | dimensionCode   | dimensionPriority | distributionChannelCode | priceGroupCode | customerCode | itemCode | price  |
      | SELL_DIST_ITEM  | 30                | 99                      | N              | N            | 19999999 | 333.99 |
      | SELL_PRICE_ITEM | 20                | N                       | 03             | N            | 19999999 | 222.22 |
      | SELL_ITEM       | 10                | N                       | N              | N            | 19999999 | 111.11 |
    Then [大区预折商品获取单价05]预折添加商品时获取价格应该如下
      | preType             | itemCode | price  | distributionChannelCode |
      | REGION_PRE_DISCOUNT | 19999999 | 222.22 | N                       |

    Examples: 基础信息
      | saleOrgSapName | regionName | customerSapName |


  @productPriceRegionPreDiscount06
  Scenario Outline: [场景06]【医药商业】【大区预折】添加商品取价格维度组 → 10且无优先级的分销渠道
  "[主要测试点]
  1. 大区预折
  2. 点击【添加商品】
  3. 价格维度组: 销售商/商品
  4. 价格中心配置表无此分销渠道"
    Given [大区预折商品获取单价06]当前存在如下客户信息
      | saleOrgSapCode | regionCode | regionName   | customerSapCode |
      | 1017           | 340000     | <regionName> | 0010000262      |
    When [大区预折商品获取单价06]当前存在如下商品价格信息
      | dimensionCode | dimensionPriority | distributionChannelCode | priceGroupCode | customerCode | itemCode | price  |
      | SELL_ITEM     | 10                | N                       | N              | N            | 19999999 | 111.11 |
    Then [大区预折商品获取单价06]预折添加商品时获取价格应该如下
      | preType             | itemCode | price  | distributionChannelCode |
      | REGION_PRE_DISCOUNT | 19999999 | 111.11 | N                       |

    Examples: 基础信息
      | saleOrgSapName | regionName | customerSapName |
      