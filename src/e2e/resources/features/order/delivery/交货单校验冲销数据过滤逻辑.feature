Feature: 交货单校验逻辑


  @deliveryVerifyOffsetDataFilter01
  Scenario Outline: [场景1]正向订单数据库中订单10行有正向交货单数据，SAP发送订单10行交货单冲销数据，数据正常接收入库。
  "[主要测试点]
  1. 数据库中订单10行有正向交货单数据
  2. SAP发送订单10行交货单冲销数据
  3. 数据正常接收入库"
    Given [交货单校验冲销过滤01]当前存在如下客户信息
      | saleOrgSapCode | saleOrgSapName   | regionCode | regionName   | customerSapCode | customerSapName   |
      | 1017           | <saleOrgSapName> | 340000     | <regionName> | 0010020753      | <customerSapName> |
    When [交货单校验冲销过滤01]创建订单且下发SAP至待发货
    And [交货单校验冲销过滤01]SAP发送交货单数据如下
      | orderItem | batchNum |
      | 10        | 1        |
    And [交货单校验冲销过滤01]SAP发送交货单10行冲销数据
      | orderItem | batchNum |
      | 10        | 1        |
    Then [交货单校验冲销过滤01]交货单过滤重复数据正常入库
      | orderItem | batchNum | type | status   |
      | 10        | 1        | 601  | IN_VALID |
      | 10        | 1        | 602  | VALID    |

    Examples: 基础信息
      | saleOrgSapName | regionName | customerSapName |


  @deliveryVerifyOffsetDataFilter02
  Scenario Outline: [场景2]正向订单数据库中订单10行无正向交货单数据，SAP发送订单10行交货单冲销数据，数据被过滤。
  "[主要测试点]
  1. 数据库中订单10行无正向交货单数据
  2. SAP发送订单10行交货单冲销数据
  3. 数据被过滤"
    Given [交货单校验冲销过滤02]当前存在如下客户信息
      | saleOrgSapCode | saleOrgSapName   | regionCode | regionName   | customerSapCode | customerSapName   |
      | 1017           | <saleOrgSapName> | 340000     | <regionName> | 0010020753      | <customerSapName> |
    When [交货单校验冲销过滤02]创建订单且下发SAP至待发货
    And [交货单校验冲销过滤02]SAP发送交货单数据如下不发送
      | orderItem | batchNum |
      | 10        | 1        |
    And [交货单校验冲销过滤02]SAP发送交货单10行冲销数据
      | orderItem | batchNum |
      | 10        | 1        |
    Then [交货单校验冲销过滤02]交货单过滤数据无法入库

    Examples: 基础信息
      | saleOrgSapName | regionName | customerSapName |


  @deliveryVerifyOffsetDataFilter03
  Scenario Outline: [场景3]正向订单数据库中订单10行无正向交货单数据，SAP发送订单10行交货单正向和冲销数据，数据正常接收入库。
  "[主要测试点]
  1. 数据库中订单10行无正向交货单数据
  2. SAP发送订单10行交货单正向和冲销数据
  3. 数据正常接收入库"
    Given [交货单校验冲销过滤03]当前存在如下客户信息
      | saleOrgSapCode | saleOrgSapName   | regionCode | regionName   | customerSapCode | customerSapName   |
      | 1017           | <saleOrgSapName> | 340000     | <regionName> | 0010020753      | <customerSapName> |
    When [交货单校验冲销过滤03]创建订单且下发SAP至待发货
    And [交货单校验冲销过滤03]SAP发送交货单10行正向和冲销数据同时接收
      | orderItem | batchNum |
      | 10        | 1        |
    Then [交货单校验冲销过滤03]数据正常接收入库
      | orderItem | batchNum | type | status   |
      | 10        | 1        | 601  | IN_VALID |
      | 10        | 1        | 602  | VALID    |

    Examples: 基础信息
      | saleOrgSapName | regionName | customerSapName |


  @deliveryVerifyOffsetDataFilter04
  Scenario Outline: [场景04]退货订单数据库中订单10行有正向交货单数据，SAP发送订单10行1条交货单冲销数据，数据正常接收入库。
  "[主要测试点]
  1. 数据库中订单10行有正向交货单数据
  2. SAP发送订单10行1条交货单冲销数据
  3. 数据正常接收入库"
    Given [交货单校验冲销过滤04]当前存在如下客户信息
      | saleOrgSapCode | saleOrgSapName   | regionCode | regionName   | customerSapCode | customerSapName   |
      | 1017           | <saleOrgSapName> | 340000     | <regionName> | 0010020753      | <customerSapName> |
    When [交货单校验冲销过滤04]创建订单且下发SAP至待发货
    And [交货单校验冲销过滤04]SAP发送交货单数据如下
      | orderItem | batchNum |
      | 10        | 3        |
    Then [交货单校验冲销过滤04]交货单过滤重复数据正常入库
      | orderItem | batchNum | type | status |
      | 10        | 3        | 601  | VALID  |
    When [交货单校验冲销过滤04]创建退货订单且下发SAP
      | returnOrderItem | sku      | returnBatchNum | unitPrice |
      | 10              | 10000353 | 3              | 16.37     |
    And [交货单校验冲销过滤04]SAP发送退货交货单数据如下
      | returnOrderItem | deliveryBatchNum |
      | 10              | 1                |
    And [交货单校验冲销过滤04]SAP发送退货冲销交货单数据如下
      | returnOrderItem | deliveryBatchNum |
      | 10              | 1                |
    Then [交货单校验冲销过滤04]退货交货单过滤重复数据正常入库
      | orderItem | batchNum | type | status   |
      | 10        | 3        | 601  | VALID    |
      | 10        | 1        | 657  | IN_VALID |
      | 10        | 1        | 658  | VALID    |

    Examples: 基础信息
      | saleOrgSapName | regionName | customerSapName |


  @deliveryVerifyOffsetDataFilter05
  Scenario Outline: [场景05]退货订单数据库中订单10行无正向交货单数据，SAP发送订单10行1条交货单冲销数据，数据被过滤。
  "[主要测试点]
  1. 数据库中订单10行无正向交货单数据
  2. SAP发送订单10行1条交货单冲销数据
  3. 数据被过滤"
    Given [交货单校验冲销过滤05]当前存在如下客户信息
      | saleOrgSapCode | saleOrgSapName   | regionCode | regionName   | customerSapCode | customerSapName   |
      | 1017           | <saleOrgSapName> | 340000     | <regionName> | 0010020753      | <customerSapName> |
    When [交货单校验冲销过滤05]创建订单且下发SAP至待发货
    And [交货单校验冲销过滤05]SAP发送交货单数据如下
      | orderItem | batchNum |
      | 10        | 3        |
    Then [交货单校验冲销过滤05]交货单过滤重复数据正常入库
      | orderItem | batchNum | type | status |
      | 10        | 3        | 601  | VALID  |
    When [交货单校验冲销过滤05]创建退货订单且下发SAP
      | returnOrderItem | sku      | returnBatchNum | unitPrice |
      | 10              | 10000353 | 3              | 16.37     |
    And [交货单校验冲销过滤05]SAP发送退货交货单数据如下不发送
      | returnOrderItem | deliveryBatchNum |
      | 10              | 1                |
    And [交货单校验冲销过滤05]SAP发送退货冲销交货单数据如下
      | returnOrderItem | deliveryBatchNum |
      | 10              | 1                |
    Then [交货单校验冲销过滤05]退货交货单过滤重复数据正常入库
      | orderItem | batchNum | type | status |
      | 10        | 3        | 601  | VALID  |

    Examples: 基础信息
      | saleOrgSapName | regionName | customerSapName |


  @deliveryVerifyOffsetDataFilter06
  Scenario Outline: [场景06]退货订单数据库中订单10行无正向交货单数据，SAP发送订单10行交货单正向和冲销数据，数据正常接收入库。
  "[主要测试点]
  1. 数据库中订单10行无正向交货单数据
  2. SAP发送订单10行交货单正向和冲销数据
  3. 数据正常接收入库"
    Given [交货单校验冲销过滤06]当前存在如下客户信息
      | saleOrgSapCode | saleOrgSapName   | regionCode | regionName   | customerSapCode | customerSapName   |
      | 1017           | <saleOrgSapName> | 340000     | <regionName> | 0010020753      | <customerSapName> |
    When [交货单校验冲销过滤06]创建订单且下发SAP至待发货
    And [交货单校验冲销过滤06]SAP发送交货单数据如下
      | orderItem | batchNum |
      | 10        | 3        |
    Then [交货单校验冲销过滤06]交货单过滤重复数据正常入库
      | orderItem | batchNum | type | status |
      | 10        | 3        | 601  | VALID  |
    When [交货单校验冲销过滤06]创建退货订单且下发SAP
      | returnOrderItem | sku      | returnBatchNum | unitPrice |
      | 10              | 10000353 | 3              | 16.37     |
    And [交货单校验冲销过滤06]SAP发送交货单10行正向和冲销数据同时接收
      | returnOrderItem | deliveryBatchNum |
      | 10              | 1                |
    Then [交货单校验冲销过滤06]退货交货单过滤重复数据正常入库
      | orderItem | batchNum | type | status   |
      | 10        | 3        | 601  | VALID    |
      | 10        | 1        | 657  | IN_VALID |
      | 10        | 1        | 658  | VALID    |

    Examples: 基础信息
      | saleOrgSapName | regionName | customerSapName |
      