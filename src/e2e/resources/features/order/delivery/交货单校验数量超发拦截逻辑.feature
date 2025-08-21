Feature: 交货单校验逻辑


  @deliveryVerifyQuantityExceedLimit01
  Scenario Outline: [场景1]正向订单数据库中订单10行无交货单数据，SAP发送订单10行交货单数量大于订单购买数量，数据被拦截无法入库。
  "[主要测试点]
  1. 数据库中订单10行无交货单数据
  2. SAP发送订单10行交货单数量大于订单购买数量
  3. 数据被拦截无法入库"
    Given [交货单校验超发01]当前存在如下客户信息
      | saleOrgSapCode | saleOrgSapName   | regionCode | regionName   | customerSapCode | customerSapName   |
      | 1017           | <saleOrgSapName> | 340000     | <regionName> | 0010020753      | <customerSapName> |
    When [交货单校验超发01]创建订单且下发SAP至待发货
    And [交货单校验超发01]SAP发送交货单数据如下
      | orderItem | batchNum |
      | 10        | 4        |
    Then [交货单校验超发01]数据被拦截无法入库

    Examples: 基础信息
      | saleOrgSapName | regionName | customerSapName |


  @deliveryVerifyQuantityExceedLimit02
  Scenario Outline: [场景2]正向订单数据库中订单10行无交货单数据，SAP发送订单10行交货单数量等于订单购买数量，数据正常入库。
  "[主要测试点]
  1. 数据库中订单10行无交货单数据
  2. SAP发送订单10行交货单数量等于订单购买数量
  3. 数据正常入库"
    Given [交货单校验超发02]当前存在如下客户信息
      | saleOrgSapCode | saleOrgSapName   | regionCode | regionName   | customerSapCode | customerSapName   |
      | 1017           | <saleOrgSapName> | 340000     | <regionName> | 0010020753      | <customerSapName> |
    When [交货单校验超发02]创建订单且下发SAP至待发货
    And [交货单校验超发02]SAP发送交货单数据如下
      | orderItem | batchNum |
      | 10        | 3        |
    Then [交货单校验超发02]交货单数据正常入库
      | orderItem | batchNum | type | status |
      | 10        | 3        | 601  | VALID  |

    Examples: 基础信息
      | saleOrgSapName | regionName | customerSapName |


  @deliveryVerifyQuantityExceedLimit03
  Scenario Outline: [场景3]正向订单数据库中订单10行无交货单数据，SAP发送订单10行交货单数量小于订单购买数量，数据正常入库。
  "[主要测试点]
  1. 数据库中订单10行无交货单数据
  2. SAP发送订单10行交货单数量小于订单购买数量
  3. 数据正常入库"
    Given [交货单校验超发03]当前存在如下客户信息
      | saleOrgSapCode | saleOrgSapName   | regionCode | regionName   | customerSapCode | customerSapName   |
      | 1017           | <saleOrgSapName> | 340000     | <regionName> | 0010020753      | <customerSapName> |
    When [交货单校验超发03]创建订单且下发SAP至待发货
    And [交货单校验超发03]SAP发送交货单数据如下
      | orderItem | batchNum |
      | 10        | 3        |
    Then [交货单校验超发03]交货单数据正常入库
      | orderItem | batchNum | type | status |
      | 10        | 3        | 601  | VALID  |

    Examples: 基础信息
      | saleOrgSapName | regionName | customerSapName |


  @deliveryVerifyQuantityExceedLimit04
  Scenario Outline: [场景4]正向订单数据库中订单10行有交货单数据，数据库交货单数量+SAP发送订单10行交货单数量大于订单购买数量，数据被拦截无法入库。
  "[主要测试点]
  1. 数据库中订单10行有交货单数据
  2. 数据库交货单数量+SAP发送订单10行交货单数量大于订单购买数量
  3. 数据被拦截无法入库"
    Given [交货单校验超发04]当前存在如下客户信息
      | saleOrgSapCode | saleOrgSapName   | regionCode | regionName   | customerSapCode | customerSapName   |
      | 1017           | <saleOrgSapName> | 340000     | <regionName> | 0010020753      | <customerSapName> |
    When [交货单校验超发04]创建订单且下发SAP至待发货
    And [交货单校验超发04]SAP发送交货单数据如下
      | orderItem | batchNum |
      | 10        | 2        |
    And [交货单校验超发04]SAP再次发送交货单数据如下
      | orderItem | batchNum |
      | 10        | 2        |
    Then [交货单校验超发04]交货单数据入库数据如下
      | orderItem | batchNum | type | status |
      | 10        | 2        | 601  | VALID  |

    Examples: 基础信息
      | saleOrgSapName | regionName | customerSapName |


  @deliveryVerifyQuantityExceedLimit05
  Scenario Outline: [场景5]正向订单数据库中订单10行有交货单数据，数据库交货单数量+SAP发送订单10行交货单数量等于订单购买数量，数据正常入库。
  "[主要测试点]
  1. 数据库中订单10行有交货单数据
  2. 数据库交货单数量+SAP发送订单10行交货单数量等于订单购买数量
  3. 数据正常入库"
    Given [交货单校验超发05]当前存在如下客户信息
      | saleOrgSapCode | saleOrgSapName   | regionCode | regionName   | customerSapCode | customerSapName   |
      | 1017           | <saleOrgSapName> | 340000     | <regionName> | 0010020753      | <customerSapName> |
    When [交货单校验超发05]创建订单且下发SAP至待发货
    And [交货单校验超发05]SAP发送交货单数据如下
      | orderItem | batchNum |
      | 10        | 2        |
    And [交货单校验超发05]SAP再次发送交货单数据如下
      | orderItem | batchNum |
      | 10        | 1        |
    Then [交货单校验超发05]交货单数据入库数据如下
      | orderItem | batchNum | type | status |
      | 10        | 2        | 601  | VALID  |
      | 10        | 1        | 601  | VALID  |

    Examples: 基础信息
      | saleOrgSapName | regionName | customerSapName |


  @deliveryVerifyQuantityExceedLimit06
  Scenario Outline: [场景6]正向订单数据库中订单10行有交货单数据，数据库交货单数量+SAP发送订单10行交货单数量小于订单购买数量，数据正常入库。
  "[主要测试点]
  1. 数据库中订单10行有交货单数据
  2. 数据库交货单数量+SAP发送订单10行交货单数量小于订单购买数量
  3. 数据正常入库"
    Given [交货单校验超发06]当前存在如下客户信息
      | saleOrgSapCode | saleOrgSapName   | regionCode | regionName   | customerSapCode | customerSapName   |
      | 1017           | <saleOrgSapName> | 340000     | <regionName> | 0010020753      | <customerSapName> |
    When [交货单校验超发06]创建订单且下发SAP至待发货
    And [交货单校验超发06]SAP发送交货单数据如下
      | orderItem | batchNum |
      | 10        | 1        |
    And [交货单校验超发06]SAP再次发送交货单数据如下
      | orderItem | batchNum |
      | 10        | 1        |
    Then [交货单校验超发06]交货单数据入库数据如下
      | orderItem | batchNum | type | status |
      | 10        | 1        | 601  | VALID  |
      | 10        | 1        | 601  | VALID  |

    Examples: 基础信息
      | saleOrgSapName | regionName | customerSapName |


  @deliveryVerifyQuantityExceedLimit07
  Scenario Outline: [场景7]退货订单数据库中订单10行无交货单数据，SAP发送订单10行交货单数量大于订单购买数量，数据被拦截无法入库。
  "[主要测试点]
  1. 数据库中订单10行无交货单数据
  2. SAP发送订单10行交货单数量大于订单购买数量
  3. 数据被拦截无法入库"
    Given [交货单校验超发07]当前存在如下客户信息
      | saleOrgSapCode | saleOrgSapName   | regionCode | regionName   | customerSapCode | customerSapName   |
      | 1017           | <saleOrgSapName> | 340000     | <regionName> | 0010020753      | <customerSapName> |
    When [交货单校验超发07]创建订单且下发SAP至待发货
    And [交货单校验超发07]SAP发送交货单数据如下
      | orderItem | batchNum |
      | 10        | 3        |
    Then [交货单校验超发07]交货单过滤重复数据正常入库
      | orderItem | batchNum | type | status |
      | 10        | 3        | 601  | VALID  |
    When [交货单校验超发07]创建退货订单且下发SAP
      | returnOrderItem | sku      | returnBatchNum | unitPrice |
      | 10              | 10000353 | 3              | 16.37     |
    And [交货单校验超发07]SAP发送退货交货单数据如下
      | returnOrderItem | deliveryBatchNum |
      | 10              | 4                |
    Then [交货单校验超发07]退货交货单数据入库数据如下
      | orderItem | batchNum | type | status |
      | 10        | 3        | 601  | VALID  |

    Examples: 基础信息
      | saleOrgSapName | regionName | customerSapName |


  @deliveryVerifyQuantityExceedLimit08
  Scenario Outline: [场景8]退货订单数据库中订单10行无交货单数据，SAP发送订单10行交货单数量等于订单购买数量，数据正常入库。
  "[主要测试点]
  1. 数据库中订单10行无交货单数据
  2. SAP发送订单10行交货单数量等于订单购买数量
  3. 数据正常入库"
    Given [交货单校验超发08]当前存在如下客户信息
      | saleOrgSapCode | saleOrgSapName   | regionCode | regionName   | customerSapCode | customerSapName   |
      | 1017           | <saleOrgSapName> | 340000     | <regionName> | 0010020753      | <customerSapName> |
    When [交货单校验超发08]创建订单且下发SAP至待发货
    And [交货单校验超发08]SAP发送交货单数据如下
      | orderItem | batchNum |
      | 10        | 3        |
    Then [交货单校验超发08]交货单过滤重复数据正常入库
      | orderItem | batchNum | type | status |
      | 10        | 3        | 601  | VALID  |
    When [交货单校验超发08]创建退货订单且下发SAP
      | returnOrderItem | sku      | returnBatchNum | unitPrice |
      | 10              | 10000353 | 3              | 16.37     |
    And [交货单校验超发08]SAP发送退货交货单数据如下
      | returnOrderItem | deliveryBatchNum |
      | 10              | 3                |
    Then [交货单校验超发08]退货交货单数据入库数据如下
      | orderItem | batchNum | type | status |
      | 10        | 3        | 601  | VALID  |
      | 10        | 3        | 657  | VALID  |

    Examples: 基础信息
      | saleOrgSapName | regionName | customerSapName |


  @deliveryVerifyQuantityExceedLimit09
  Scenario Outline: [场景9]退货订单数据库中订单10行无交货单数据，SAP发送订单10行交货单数量小于订单购买数量，数据正常入库。
  "[主要测试点]
  1. 数据库中订单10行无交货单数据
  2. SAP发送订单10行交货单数量小于订单购买数量
  3. 数据正常入库"
    Given [交货单校验超发09]当前存在如下客户信息
      | saleOrgSapCode | saleOrgSapName   | regionCode | regionName   | customerSapCode | customerSapName   |
      | 1017           | <saleOrgSapName> | 340000     | <regionName> | 0010020753      | <customerSapName> |
    When [交货单校验超发09]创建订单且下发SAP至待发货
    And [交货单校验超发09]SAP发送交货单数据如下
      | orderItem | batchNum |
      | 10        | 3        |
    Then [交货单校验超发09]交货单过滤重复数据正常入库
      | orderItem | batchNum | type | status |
      | 10        | 3        | 601  | VALID  |
    When [交货单校验超发09]创建退货订单且下发SAP
      | returnOrderItem | sku      | returnBatchNum | unitPrice |
      | 10              | 10000353 | 3              | 16.37     |
    And [交货单校验超发09]SAP发送退货交货单数据如下
      | returnOrderItem | deliveryBatchNum |
      | 10              | 1                |
    Then [交货单校验超发09]退货交货单数据入库数据如下
      | orderItem | batchNum | type | status |
      | 10        | 3        | 601  | VALID  |
      | 10        | 1        | 657  | VALID  |

    Examples: 基础信息
      | saleOrgSapName | regionName | customerSapName |


  @deliveryVerifyQuantityExceedLimit10
  Scenario Outline: [场景10]退货订单数据库中订单10行有交货单数据，数据库交货单数量+SAP发送订单10行交货单数量大于订单购买数量，数据正常入库。
  "[主要测试点]
  1. 数据库中订单10行有交货单数据
  2. 数据库交货单数量+SAP发送订单10行交货单数量大于订单购买数量
  3. 数据正常入库"
    Given [交货单校验超发10]当前存在如下客户信息
      | saleOrgSapCode | saleOrgSapName   | regionCode | regionName   | customerSapCode | customerSapName   |
      | 1017           | <saleOrgSapName> | 340000     | <regionName> | 0010020753      | <customerSapName> |
    When [交货单校验超发10]创建订单且下发SAP至待发货
    And [交货单校验超发10]SAP发送交货单数据如下
      | orderItem | batchNum |
      | 10        | 3        |
    Then [交货单校验超发10]交货单过滤重复数据正常入库
      | orderItem | batchNum | type | status |
      | 10        | 3        | 601  | VALID  |
    When [交货单校验超发10]创建退货订单且下发SAP
      | returnOrderItem | sku      | returnBatchNum | unitPrice |
      | 10              | 10000353 | 3              | 16.37     |
    And [交货单校验超发10]SAP发送退货交货单数据如下
      | returnOrderItem | deliveryBatchNum |
      | 10              | 2                |
    And [交货单校验超发10]SAP再次发送退货交货单数据如下
      | returnOrderItem | deliveryBatchNum |
      | 10              | 2                |
    Then [交货单校验超发10]退货交货单数据入库数据如下
      | orderItem | batchNum | type | status |
      | 10        | 3        | 601  | VALID  |
      | 10        | 2        | 657  | VALID  |

    Examples: 基础信息
      | saleOrgSapName | regionName | customerSapName |


  @deliveryVerifyQuantityExceedLimit11
  Scenario Outline: [场景11]退货订单数据库中订单10行有交货单数据，数据库交货单数量+SAP发送订单10行交货单数量等于订单购买数量，数据正常入库。
  "[主要测试点]
  1. 数据库中订单10行有交货单数据
  2. 数据库交货单数量+SAP发送订单10行交货单数量等于订单购买数量
  3. 数据正常入库"
    Given [交货单校验超发11]当前存在如下客户信息
      | saleOrgSapCode | saleOrgSapName   | regionCode | regionName   | customerSapCode | customerSapName   |
      | 1017           | <saleOrgSapName> | 340000     | <regionName> | 0010020753      | <customerSapName> |
    When [交货单校验超发11]创建订单且下发SAP至待发货
    And [交货单校验超发11]SAP发送交货单数据如下
      | orderItem | batchNum |
      | 10        | 3        |
    Then [交货单校验超发11]交货单过滤重复数据正常入库
      | orderItem | batchNum | type | status |
      | 10        | 3        | 601  | VALID  |
    When [交货单校验超发11]创建退货订单且下发SAP
      | returnOrderItem | sku      | returnBatchNum | unitPrice |
      | 10              | 10000353 | 3              | 16.37     |
    And [交货单校验超发11]SAP发送退货交货单数据如下
      | returnOrderItem | deliveryBatchNum |
      | 10              | 2                |
    And [交货单校验超发11]SAP再次发送退货交货单数据如下
      | returnOrderItem | deliveryBatchNum |
      | 10              | 1                |
    Then [交货单校验超发11]退货交货单数据入库数据如下
      | orderItem | batchNum | type | status |
      | 10        | 3        | 601  | VALID  |
      | 10        | 2        | 657  | VALID  |
      | 10        | 1        | 657  | VALID  |

    Examples: 基础信息
      | saleOrgSapName | regionName | customerSapName |


  @deliveryVerifyQuantityExceedLimit12
  Scenario Outline: [场景12]退货订单数据库中订单10行有交货单数据，数据库交货单数量+SAP发送订单10行交货单数量小于订单购买数量，数据正常入库。
  "[主要测试点]
  1. 数据库中订单10行有交货单数据
  2. 数据库交货单数量+SAP发送订单10行交货单数量小于订单购买数量
  3. 数据正常入库"
    Given [交货单校验超发12]当前存在如下客户信息
      | saleOrgSapCode | saleOrgSapName   | regionCode | regionName   | customerSapCode | customerSapName   |
      | 1017           | <saleOrgSapName> | 340000     | <regionName> | 0010020753      | <customerSapName> |
    When [交货单校验超发12]创建订单且下发SAP至待发货
    And [交货单校验超发12]SAP发送交货单数据如下
      | orderItem | batchNum |
      | 10        | 3        |
    Then [交货单校验超发12]交货单过滤重复数据正常入库
      | orderItem | batchNum | type | status |
      | 10        | 3        | 601  | VALID  |
    When [交货单校验超发12]创建退货订单且下发SAP
      | returnOrderItem | sku      | returnBatchNum | unitPrice |
      | 10              | 10000353 | 3              | 16.37     |
    And [交货单校验超发12]SAP发送退货交货单数据如下
      | returnOrderItem | deliveryBatchNum |
      | 10              | 1                |
    And [交货单校验超发12]SAP再次发送退货交货单数据如下
      | returnOrderItem | deliveryBatchNum |
      | 10              | 1                |
    Then [交货单校验超发12]退货交货单数据入库数据如下
      | orderItem | batchNum | type | status |
      | 10        | 3        | 601  | VALID  |
      | 10        | 1        | 657  | VALID  |
      | 10        | 1        | 657  | VALID  |

    Examples: 基础信息
      | saleOrgSapName | regionName | customerSapName |
    