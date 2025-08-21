Feature: 交货单校验逻辑


  @deliveryVerifySameDataFilter01
  Scenario Outline: [场景1]正向订单数据库中订单10行无交货单数据，SAP发送订单10行交货单数据，数据正常接收入库。
  "[主要测试点]
  1. 数据库中订单10行无交货单数据
  2. SAP发送订单10行交货单数据
  3. 数据正常接收入库"
    Given [交货单校验相同过滤01]当前存在如下客户信息
      | saleOrgSapCode | saleOrgSapName   | regionCode | regionName   | customerSapCode | customerSapName   |
      | 1017           | <saleOrgSapName> | 340000     | <regionName> | 0010020753      | <customerSapName> |
    When [交货单校验相同过滤01]创建订单且下发SAP至待发货
    And [交货单校验相同过滤01]SAP发送交货单数据如下
      | orderItem | batchNum |
      | 10        | 1        |
    Then [交货单校验相同过滤01]交货单过滤重复数据正常入库
      | orderItem | batchNum | type | status |
      | 10        | 1        | 601  | VALID  |

    Examples: 基础信息
      | saleOrgSapName | regionName | customerSapName |


  @deliveryVerifySameDataFilter02
  Scenario Outline: [场景2]正向订单数据库中订单10行无交货单数据，SAP发送订单10行2条相同交货单数据，过滤重复数据后入库。
  "[主要测试点]
  1. 数据库中订单10行无交货单数据
  2. SAP发送订单10行2条相同交货单数据
  3. 过滤重复数据后入库"
    Given [交货单校验相同过滤02]当前存在如下客户信息
      | saleOrgSapCode | saleOrgSapName   | regionCode | regionName   | customerSapCode | customerSapName   |
      | 1017           | <saleOrgSapName> | 340000     | <regionName> | 0010020753      | <customerSapName> |
    When [交货单校验相同过滤02]创建订单且下发SAP至待发货
    And [交货单校验相同过滤02]SAP发送交货单数据如下
      | orderItem | batchNum |
      | 10        | 1        |
      | 10        | 1        |
    Then [交货单校验相同过滤02]交货单过滤重复数据正常入库
      | orderItem | batchNum | type | status |
      | 10        | 1        | 601  | VALID  |

    Examples: 基础信息
      | saleOrgSapName | regionName | customerSapName |


  @deliveryVerifySameDataFilter03
  Scenario Outline: [场景3]正向订单数据库中订单10行有交货单数据，SAP发送订单10行1条相同交货单数据和20行数据，过滤重复数据后入库。
  "[主要测试点]
  1. 数据库中订单10行有交货单数据
  2. 数据库中订单20行无交货单数据
  3. SAP发送订单10行1条相同交货单数据
  4. SAP发送订单20行1条不同交货单数据
  5. 过滤重复数据后入库"
    Given [交货单校验相同过滤03]当前存在如下客户信息
      | saleOrgSapCode | saleOrgSapName   | regionCode | regionName   | customerSapCode | customerSapName   |
      | 1017           | <saleOrgSapName> | 340000     | <regionName> | 0010020753      | <customerSapName> |
    When [交货单校验相同过滤03]创建订单且下发SAP至待发货
    And [交货单校验相同过滤03]SAP发送交货单数据如下
      | orderItem | batchNum |
      | 10        | 1        |
    And [交货单校验相同过滤03]SAP发送交货单重复数据如下
      | orderItem | batchNum |
      | 10        | 1        |
    Then [交货单校验相同过滤03]交货单过滤重复数据正常入库
      | orderItem | batchNum | type | status |
      | 10        | 1        | 601  | VALID  |

    Examples: 基础信息
      | saleOrgSapName | regionName | customerSapName |


  @deliveryVerifySameDataFilter04
  Scenario Outline: [场景4]正向订单数据库中订单10行无交货单冲销数据，SAP发送订单10行1条交货单冲销数据，数据正常接收入库。
  "[主要测试点]
  1. 数据库中订单10行无交货单冲销数据
  2. SAP发送订单10行1条交货单冲销数据
  3. 数据正常接收入库"
    Given [交货单校验相同过滤04]当前存在如下客户信息
      | saleOrgSapCode | saleOrgSapName   | regionCode | regionName   | customerSapCode | customerSapName   |
      | 1017           | <saleOrgSapName> | 340000     | <regionName> | 0010020753      | <customerSapName> |
    When [交货单校验相同过滤04]创建订单且下发SAP至待发货
    And [交货单校验相同过滤04]SAP发送交货单数据如下
      | orderItem | batchNum |
      | 10        | 1        |
    And [交货单校验相同过滤04]SAP发送交货单10行冲销数据
      | orderItem | batchNum |
      | 10        | 1        |
    Then [交货单校验相同过滤04]交货单过滤重复数据正常入库
      | orderItem | batchNum | type | status   |
      | 10        | 1        | 601  | IN_VALID |
      | 10        | 1        | 602  | VALID    |

    Examples: 基础信息
      | saleOrgSapName | regionName | customerSapName |


  @deliveryVerifySameDataFilter05
  Scenario Outline: [场景5]正向订单数据库中订单10行无交货单冲销数据，SAP发送订单10行2条相同交货单冲销数据，过滤重复数据后入库。
  "[主要测试点]
  1. 数据库中订单10行无交货单冲销数据
  2. SAP发送订单10行2条相同交货单冲销数据
  3. 过滤重复数据后入库"
    Given [交货单校验相同过滤05]当前存在如下客户信息
      | saleOrgSapCode | saleOrgSapName   | regionCode | regionName   | customerSapCode | customerSapName   |
      | 1017           | <saleOrgSapName> | 340000     | <regionName> | 0010020753      | <customerSapName> |
    When [交货单校验相同过滤05]创建订单且下发SAP至待发货
    And [交货单校验相同过滤05]SAP发送交货单数据如下
      | orderItem | batchNum |
      | 10        | 1        |
    And [交货单校验相同过滤05]SAP发送交货单10行冲销数据
      | orderItem | batchNum |
      | 10        | 1        |
      | 10        | 1        |
    Then [交货单校验相同过滤05]交货单过滤重复数据正常入库
      | orderItem | batchNum | type | status   |
      | 10        | 1        | 601  | IN_VALID |
      | 10        | 1        | 602  | VALID    |

    Examples: 基础信息
      | saleOrgSapName | regionName | customerSapName |


  @deliveryVerifySameDataFilter06
  Scenario Outline: [场景6]正向订单数据库中订单10行有交货单冲销数据，SAP发送订单10行相同交货单冲销数据，过滤重复数据后入库。
  "[主要测试点]
  1. 数据库中订单10行无交货单冲销数据
  2. SAP发送订单10行1条交货单冲销数据
  3. SAP再次发送订单10行1条相同交货单冲销数据
  4. 过滤重复数据后入库"
    Given [交货单校验相同过滤06]当前存在如下客户信息
      | saleOrgSapCode | saleOrgSapName   | regionCode | regionName   | customerSapCode | customerSapName   |
      | 1017           | <saleOrgSapName> | 340000     | <regionName> | 0010020753      | <customerSapName> |
    When [交货单校验相同过滤06]创建订单且下发SAP至待发货
    And [交货单校验相同过滤06]SAP发送交货单数据如下
      | orderItem | batchNum |
      | 10        | 1        |
    And [交货单校验相同过滤06]SAP发送交货单10行冲销数据
      | orderItem | batchNum |
      | 10        | 1        |
    And [交货单校验相同过滤06]SAP发送交货单10行重复冲销数据
      | orderItem | batchNum |
      | 10        | 1        |
    Then [交货单校验相同过滤06]交货单过滤重复数据正常入库
      | orderItem | batchNum | type | status   |
      | 10        | 1        | 601  | IN_VALID |
      | 10        | 1        | 602  | VALID    |

    Examples: 基础信息
      | saleOrgSapName | regionName | customerSapName |


  @deliveryVerifySameDataFilter07
  Scenario Outline: [场景7]退货订单数据库中订单10行无交货单数据，SAP发送订单10行交货单数据，数据正常接收入库。
  "[主要测试点]
  1. 数据库中订单10行无交货单数据
  2. SAP发送订单10行交货单数据
  3. 数据正常接收入库"
    Given [交货单校验相同过滤07]当前存在如下客户信息
      | saleOrgSapCode | saleOrgSapName   | regionCode | regionName   | customerSapCode | customerSapName   |
      | 1017           | <saleOrgSapName> | 340000     | <regionName> | 0010020753      | <customerSapName> |
    When [交货单校验相同过滤07]创建订单且下发SAP至待发货
    And [交货单校验相同过滤07]SAP发送交货单数据如下
      | orderItem | batchNum |
      | 10        | 3        |
    Then [交货单校验相同过滤07]交货单过滤重复数据正常入库
      | orderItem | batchNum | type | status |
      | 10        | 3        | 601  | VALID  |
    When [交货单校验相同过滤07]创建退货订单且下发SAP
      | returnOrderItem | sku      | returnBatchNum | unitPrice |
      | 10              | 10000353 | 3              | 16.37     |
    And [交货单校验相同过滤07]SAP发送退货交货单数据如下
      | returnOrderItem | deliveryBatchNum |
      | 10              | 1                |
    Then [交货单校验相同过滤07]退货交货单过滤重复数据正常入库
      | orderItem | batchNum | type | status |
      | 10        | 3        | 601  | VALID  |
      | 10        | 1        | 657  | VALID  |

    Examples: 基础信息
      | saleOrgSapName | regionName | customerSapName |


  @deliveryVerifySameDataFilter08
  Scenario Outline: [场景8]退货订单数据库中订单10行无交货单数据，SAP发送订单10行2条相同交货单数据，过滤重复数据后入库。
  "[主要测试点]
  1. 数据库中订单10行无交货单数据
  2. SAP发送订单10行2条相同交货单数据
  3. 过滤重复数据后入库"
    Given [交货单校验相同过滤08]当前存在如下客户信息
      | saleOrgSapCode | saleOrgSapName   | regionCode | regionName   | customerSapCode | customerSapName   |
      | 1017           | <saleOrgSapName> | 340000     | <regionName> | 0010020753      | <customerSapName> |
    When [交货单校验相同过滤08]创建订单且下发SAP至待发货
    And [交货单校验相同过滤08]SAP发送交货单数据如下
      | orderItem | batchNum |
      | 10        | 3        |
    Then [交货单校验相同过滤08]交货单过滤重复数据正常入库
      | orderItem | batchNum | type | status |
      | 10        | 3        | 601  | VALID  |
    When [交货单校验相同过滤08]创建退货订单且下发SAP
      | returnOrderItem | sku      | returnBatchNum | unitPrice |
      | 10              | 10000353 | 3              | 16.37     |
    And [交货单校验相同过滤08]SAP发送退货交货单数据如下
      | returnOrderItem | deliveryBatchNum |
      | 10              | 1                |
      | 10              | 1                |
    Then [交货单校验相同过滤08]退货交货单过滤重复数据正常入库
      | orderItem | batchNum | type | status |
      | 10        | 3        | 601  | VALID  |
      | 10        | 1        | 657  | VALID  |

    Examples: 基础信息
      | saleOrgSapName | regionName | customerSapName |


  @deliveryVerifySameDataFilter09
  Scenario Outline: [场景9]退货订单数据库中订单10行有交货单数据，SAP发送订单10行1条相同交货单数据，过滤重复数据后入库。
  "[主要测试点]
  1. 数据库中订单10行有交货单数据
  2. SAP发送订单10行1条相同交货单数据
  3. 过滤重复数据后入库"
    Given [交货单校验相同过滤09]当前存在如下客户信息
      | saleOrgSapCode | saleOrgSapName   | regionCode | regionName   | customerSapCode | customerSapName   |
      | 1017           | <saleOrgSapName> | 340000     | <regionName> | 0010020753      | <customerSapName> |
    When [交货单校验相同过滤09]创建订单且下发SAP至待发货
    And [交货单校验相同过滤09]SAP发送交货单数据如下
      | orderItem | batchNum |
      | 10        | 3        |
    Then [交货单校验相同过滤09]交货单过滤重复数据正常入库
      | orderItem | batchNum | type | status |
      | 10        | 3        | 601  | VALID  |
    When [交货单校验相同过滤09]创建退货订单且下发SAP
      | returnOrderItem | sku      | returnBatchNum | unitPrice |
      | 10              | 10000353 | 3              | 16.37     |
    And [交货单校验相同过滤09]SAP发送退货交货单数据如下
      | returnOrderItem | deliveryBatchNum |
      | 10              | 1                |
    And [交货单校验相同过滤09]SAP发送退货交货单重复数据如下
      | returnOrderItem | deliveryBatchNum |
      | 10              | 1                |
    Then [交货单校验相同过滤09]退货交货单过滤重复数据正常入库
      | orderItem | batchNum | type | status |
      | 10        | 3        | 601  | VALID  |
      | 10        | 1        | 657  | VALID  |

    Examples: 基础信息
      | saleOrgSapName | regionName | customerSapName |


  @deliveryVerifySameDataFilter10
  Scenario Outline: [场景10]退货订单数据库中订单10行无交货单数据，SAP发送订单10行1条冲销交货单数据，数据正常接收入库。
  "[主要测试点]
  1. 数据库中订单10行无交货单数据
  2. SAP发送订单10行1条冲销交货单数据
  3. 数据正常接收入库"
    Given [交货单校验相同过滤10]当前存在如下客户信息
      | saleOrgSapCode | saleOrgSapName   | regionCode | regionName   | customerSapCode | customerSapName   |
      | 1017           | <saleOrgSapName> | 340000     | <regionName> | 0010020753      | <customerSapName> |
    When [交货单校验相同过滤10]创建订单且下发SAP至待发货
    And [交货单校验相同过滤10]SAP发送交货单数据如下
      | orderItem | batchNum |
      | 10        | 3        |
    Then [交货单校验相同过滤10]交货单过滤重复数据正常入库
      | orderItem | batchNum | type | status |
      | 10        | 3        | 601  | VALID  |
    When [交货单校验相同过滤10]创建退货订单且下发SAP
      | returnOrderItem | sku      | returnBatchNum | unitPrice |
      | 10              | 10000353 | 3              | 16.37     |
    And [交货单校验相同过滤10]SAP发送退货交货单数据如下
      | returnOrderItem | deliveryBatchNum |
      | 10              | 1                |
    And [交货单校验相同过滤10]SAP发送退货冲销交货单数据如下
      | returnOrderItem | deliveryBatchNum |
      | 10              | 1                |
    Then [交货单校验相同过滤10]退货交货单过滤重复数据正常入库
      | orderItem | batchNum | type | status   |
      | 10        | 3        | 601  | VALID    |
      | 10        | 1        | 657  | IN_VALID |
      | 10        | 1        | 658  | VALID    |

    Examples: 基础信息
      | saleOrgSapName | regionName | customerSapName |


  @deliveryVerifySameDataFilter11
  Scenario Outline: [场景11]退货订单数据库中订单10行无交货单数据，SAP发送订单10行1条重复冲销交货单数据，过滤重复数据后入库。
  "[主要测试点]
  1. 数据库中订单10行无交货单数据
  2. SAP发送订单10行1条重复冲销交货单数据
  3. 过滤重复数据后入库"
    Given [交货单校验相同过滤11]当前存在如下客户信息
      | saleOrgSapCode | saleOrgSapName   | regionCode | regionName   | customerSapCode | customerSapName   |
      | 1017           | <saleOrgSapName> | 340000     | <regionName> | 0010020753      | <customerSapName> |
    When [交货单校验相同过滤11]创建订单且下发SAP至待发货
    And [交货单校验相同过滤11]SAP发送交货单数据如下
      | orderItem | batchNum |
      | 10        | 3        |
    Then [交货单校验相同过滤11]交货单过滤重复数据正常入库
      | orderItem | batchNum | type | status |
      | 10        | 3        | 601  | VALID  |
    When [交货单校验相同过滤11]创建退货订单且下发SAP
      | returnOrderItem | sku      | returnBatchNum | unitPrice |
      | 10              | 10000353 | 3              | 16.37     |
    And [交货单校验相同过滤11]SAP发送退货交货单数据如下
      | returnOrderItem | deliveryBatchNum |
      | 10              | 1                |
    And [交货单校验相同过滤11]SAP发送退货冲销交货单数据如下
      | returnOrderItem | deliveryBatchNum |
      | 10              | 1                |
      | 10              | 1                |
    Then [交货单校验相同过滤11]退货交货单过滤重复数据正常入库
      | orderItem | batchNum | type | status   |
      | 10        | 3        | 601  | VALID    |
      | 10        | 1        | 657  | IN_VALID |
      | 10        | 1        | 658  | VALID    |

    Examples: 基础信息
      | saleOrgSapName | regionName | customerSapName |


  @deliveryVerifySameDataFilter12
  Scenario Outline: [场景12]退货订单数据库中订单10行有交货单数据，SAP发送订单10行1条重复冲销交货单数据，过滤重复数据后入库。
  "[主要测试点]
  1. 数据库中订单10行有交货单数据
  2. SAP发送订单10行1条重复冲销交货单数据
  3. 过滤重复数据后入库"
    Given [交货单校验相同过滤12]当前存在如下客户信息
      | saleOrgSapCode | saleOrgSapName   | regionCode | regionName   | customerSapCode | customerSapName   |
      | 1017           | <saleOrgSapName> | 340000     | <regionName> | 0010020753      | <customerSapName> |
    When [交货单校验相同过滤12]创建订单且下发SAP至待发货
    And [交货单校验相同过滤12]SAP发送交货单数据如下
      | orderItem | batchNum |
      | 10        | 3        |
    Then [交货单校验相同过滤12]交货单过滤重复数据正常入库
      | orderItem | batchNum | type | status |
      | 10        | 3        | 601  | VALID  |
    When [交货单校验相同过滤12]创建退货订单且下发SAP
      | returnOrderItem | sku      | returnBatchNum | unitPrice |
      | 10              | 10000353 | 3              | 16.37     |
    And [交货单校验相同过滤12]SAP发送退货交货单数据如下
      | returnOrderItem | deliveryBatchNum |
      | 10              | 1                |
    And [交货单校验相同过滤12]SAP发送退货冲销交货单数据如下
      | returnOrderItem | deliveryBatchNum |
      | 10              | 1                |
    And [交货单校验相同过滤12]SAP发送退货重复冲销交货单数据如下
      | returnOrderItem | deliveryBatchNum |
      | 10              | 1                |
    Then [交货单校验相同过滤12]退货交货单过滤重复数据正常入库
      | orderItem | batchNum | type | status   |
      | 10        | 3        | 601  | VALID    |
      | 10        | 1        | 657  | IN_VALID |
      | 10        | 1        | 658  | VALID    |

    Examples: 基础信息
      | saleOrgSapName | regionName | customerSapName |
     