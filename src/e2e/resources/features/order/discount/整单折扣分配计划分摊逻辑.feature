Feature: 整单折扣分配计划计算逻辑

  Background: [前置条件] 预折和整单折扣的数据初始化
    Given [整单折扣分配计划基础数据01]清除价格\预折\整单折扣的测试数据
    When [整单折扣分配计划基础数据01]商品价格测试数据初始化


  @discountApportionment01
  Scenario Outline: [场景2-1] 验证折扣是否首先分配给同商品行
  "[主要测试点]
  1. 不存在相同原品行
  2. 按照订单行顺序分配"
    Given [整单折扣分配计划分摊01]当前存在如下客户信息
      | saleOrgSapCode   | regionCode   | customerSapCode   | regionName   | customerName   | shippingRecipient   | shippingTelephone   | shippingAddress   |
      | <saleOrgSapCode> | <regionCode> | <customerSapCode> | <regionName> | <customerName> | <shippingRecipient> | <shippingTelephone> | <shippingAddress> |
    When [整单折扣分配计划分摊01]当前存在如下的预折商品信息
      | itemSapCode | currentPrice | calculationType  | calculationValue | preType               | effectiveState | distributionChannelCode | distributionChannelName |
      | 10000841    | 10           | DISCOUNTED_PRICE | 8                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000265    | 6            | DISCOUNTED_PRICE | 5                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000289    | 5            | DISCOUNTED_PRICE | 4.5              | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000556    | 30           | DISCOUNTED_PRICE | 27               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000456    | 20           | DISCOUNTED_PRICE | 18               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000835    | 40           | DISCOUNTED_PRICE | 32               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000633    | 50           | DISCOUNTED_PRICE | 40               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000478    | 17           | DISCOUNTED_PRICE | 13               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
    And [整单折扣分配计划分摊01]当前存在如下的有效预折折扣信息
      | itemSapCode | effectiveState |
      | 10000841    | DISABLE        |
      | 10000289    | DISABLE        |
      | 10000456    | DISABLE        |
      | 10000556    | DISABLE        |
    And [整单折扣分配计划分摊01]当前存在如下的整单折扣申请单信息
      | projectType | module |
      | PT04        | KM03   |
    And [整单折扣分配计划分摊01]当前存在如下的整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 10000265    | 100      | 盒     |
      | 10000289    | 100      | 盒     |
    And [整单折扣分配计划分摊01]当前存在如下的整单折扣分配计划信息
    And [整单折扣分配计划分摊01]当前存在如下的整单折扣分配计划详细信息
      | originalItemSapCode | originalDistributionChannelCode | giftItemSapCode | giftDistributionChannelCode | allocationQuantity | originalQuantity | giftQuantity | initialBonusQuantity | needShip | invalidStatus | unit | originalDistributionChannelName | giftDistributionChannelName | lot_no      | production_date | expiration_date | remark     |
      |                     |                                 | 10000289        | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | AT_LTY_0001 | 2024-05-01      | 2025-12-31      | Detail_001 |
      |                     |                                 | 10000289        | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | AT_LTY_0002 | 2024-05-01      | 2025-12-31      | Detail_002 |
    And [整单折扣分配计划分摊01]添加商品
      | itemSapCode | quantity |
      | 10000841    | 10       |
      | 10000456    | 10       |
      | 10000556    | 10       |
    Then [整单折扣分配计划分摊01]创建订单应校验通过且提交成功
      | orderWideDiscountSubject | itemSapCode | needShip | unitPrice | quantity | preDiscount | calculationValue | boxRatio | totalInline | preDiscountTotal | orderDiscountTotal | realInline | remark     |
      | PT00                     | 10000841    | 1        | 10        | 10       | 0           | 10               | 1        | 100         | 0                | 1.64               | 98.36      |            |
      | PT04                     | 10000289    | 1        | 5         | 2        | 0           | 5                | 1        | 10          | 0                | 0.16               | 9.84       | Detail_001 |
      | PT00                     | 10000456    | 1        | 20        | 10       | 0           | 20               | 1        | 200         | 0                | 3.28               | 196.72     |            |
      | PT00                     | 10000556    | 1        | 30        | 10       | 0           | 30               | 1        | 300         | 0                | 4.92               | 295.08     |            |
    Then [整单折扣分配计划分摊01]订单整单折扣使用记录成功
      | parentsOrderNo | parentOrderItemNo | orderNo | orderItemNo | orderDiscountApplicationPlanId | applicationPlanDetailId | operateType  | operateAmount | remark     |
      |                | 20                |         | 20          |                                |                         | CREATE_ORDER | 0.16          | Detail_001 |
      |                | 10                |         | 10          |                                |                         | CREATE_ORDER | 1.64          | Detail_001 |
      |                | 30                |         | 30          |                                |                         | CREATE_ORDER | 3.28          | Detail_001 |
      |                | 40                |         | 40          |                                |                         | CREATE_ORDER | 4.92          | Detail_001 |
    Examples: 基础信息
      | saleOrgSapCode | regionCode | customerSapCode | regionName | customerName | shippingRecipient | shippingTelephone | shippingAddress |


  @discountApportionment02
  Scenario Outline: [场景2-2] 验证折扣是否首先分配给同商品行（存在相同原品行，优先分配给相同商品行，多行原品行按照原品订单行顺序分配）
  "[主要测试点]
  1. 存在相同原品行
  2. 优先分配给相同商品行，多行原品行按照原品订单行顺序分配"
    Given [整单折扣分配计划分摊02]当前存在如下客户信息
      | saleOrgSapCode   | regionCode   | customerSapCode   | regionName   | customerName   | shippingAddress   | shippingRecipient   | shippingTelephone   |
      | <saleOrgSapCode> | <regionCode> | <customerSapCode> | <regionName> | <customerName> | <shippingAddress> | <shippingRecipient> | <shippingTelephone> |
    When [整单折扣分配计划分摊02]当前存在如下的预折商品信息
      | itemSapCode | currentPrice | calculationType  | calculationValue | preType               | effectiveState | distributionChannelCode | distributionChannelName |
      | 10000841    | 10           | DISCOUNTED_PRICE | 8                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000265    | 6            | DISCOUNTED_PRICE | 5                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000289    | 5            | DISCOUNTED_PRICE | 4.5              | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000556    | 30           | DISCOUNTED_PRICE | 27               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000456    | 20           | DISCOUNTED_PRICE | 18               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000835    | 40           | DISCOUNTED_PRICE | 32               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000633    | 50           | DISCOUNTED_PRICE | 40               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000478    | 17           | DISCOUNTED_PRICE | 13               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
    And [整单折扣分配计划分摊02]当前存在如下的预折折扣信息
      | itemSapCode | calculationValue | effectiveState |
      | 10000841    | 8                | ENABLE         |
      | 10000456    | 18               | ENABLE         |
      | 10000556    | 27               | ENABLE         |
      | 10000265    | 5                | ENABLE         |
    And [整单折扣分配计划分摊02]当前存在如下的整单折扣申请单信息
      | projectType | module |
      | PT02        | KM03   |
    And [整单折扣分配计划分摊02]当前存在如下的整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 10000265    | 1000     | 盒     |
      | 10000289    | 1000     | 盒     |
    And [整单折扣分配计划分摊02]当前存在如下的整单折扣分配计划信息
    And [整单折扣分配计划分摊02]当前存在如下的整单折扣分配计划详细信息
      | originalItemSapCode | giftItemSapCode | originalDistributionChannelCode | giftDistributionChannelCode | allocationQuantity | originalQuantity | giftQuantity | initialBonusQuantity | needShip | invalidStatus | unit | originalDistributionChannelName | giftDistributionChannelName | lot_no      | production_date | expiration_date | remark     |
      |                     | 10000265        |                                 | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | AT_LTY_0001 | 2024-05-01      | 2025-12-31      | Detail_001 |
      |                     | 10000265        |                                 | 22                          | 100                |                  |              |                      | 0        | NORMAL        | 盒    |                                 | 分销-非医疗                      | AT_LTY_0002 | 2024-05-01      | 2025-12-31      | Detail_002 |
    And [整单折扣分配计划分摊02]添加商品
      | itemSapCode | quantity |
      | 10000841    | 10       |
      | 10000456    | 10       |
      | 10000556    | 10       |
    Then [整单折扣分配计划分摊02]创建订单应校验通过且提交成功
      | orderWideDiscountSubject | itemSapCode | needShip | unitPrice | preDiscount | calculationValue | boxRatio | quantity | totalInline | preDiscountTotal | orderDiscountTotal | realInline | remark     |
      | PT00                     | 10000841    | 1        | 10        | 2           | 8                | 1        | 10       | 100         | 20               | 1.43               | 78.57      |            |
      | PT00                     | 10000265    | 1        | 6         | 1           | 5                | 1        | 10       | 60          | 10               | 2.86               | 47.14      |            |
      | PT00                     | 10000456    | 1        | 20        | 2           | 18               | 1        | 1        | 20          | 2                | 2.29               | 15.71      |            |
      | PT00                     | 10000265    | 1        | 6         | 1           | 5                | 1        | 8        | 48          | 8                | 2.29               | 37.71      |            |
      | PT00                     | 10000265    | 1        | 6         | 1           | 5                | 1        | 2        | 12          | 2                | 0.57               | 9.43       |            |
      | PT02                     | 10000265    | 1        | 6         | 1           | 5                | 1        | 2        | 12          | 2                | 0.56               | 9.44       | Detail_001 |
    Then [整单折扣分配计划分摊02]订单整单折扣使用记录成功
      | parentsOrderNo | parentOrderItemNo | orderNo | orderItemNo | orderDiscountApplicationPlanId | applicationPlanDetailId | operateType  | operateAmount | remark     |
      |                | 20                |         | 20          |                                |                         | CREATE_ORDER | 2.86          | Detail_001 |
      |                | 40                |         | 40          |                                |                         | CREATE_ORDER | 2.29          | Detail_001 |
      |                | 50                |         | 50          |                                |                         | CREATE_ORDER | 0.57          | Detail_001 |
      |                | 10                |         | 10          |                                |                         | CREATE_ORDER | 1.43          | Detail_001 |
      |                | 30                |         | 30          |                                |                         | CREATE_ORDER | 2.29          | Detail_001 |
      |                | 60                |         | 60          |                                |                         | CREATE_ORDER | 0.56          | Detail_001 |

    Examples: 基础信息
      | saleOrgSapCode | regionCode | customerSapCode | regionName | customerName | shippingRecipient | shippingTelephone | shippingAddress |


  @discountApportionment03
  Scenario Outline: [场景3-1] 验证折扣行在同商品行中扣减顺序（存在相同原品行，相同原品行完全分配，不相同的原品行中完全分配）
  "[主要测试点]
  1. 存在相同原品行
  2. 相同原品行完全分配
  3. 不相同的原品行中完全分配
  4. 赠品不发货"
    Given [整单折扣分配计划分摊03]当前存在如下客户信息
      | saleOrgSapCode   | regionCode   | customerSapCode   | regionName   | customerName   | shippingAddress   | shippingRecipient   | shippingTelephone   |
      | <saleOrgSapCode> | <regionCode> | <customerSapCode> | <regionName> | <customerName> | <shippingAddress> | <shippingRecipient> | <shippingTelephone> |
    When [整单折扣分配计划分摊03]当前存在如下的预折商品信息
      | itemSapCode | currentPrice | calculationType  | calculationValue | preType               | effectiveState | distributionChannelCode | distributionChannelName |
      | 10000841    | 10           | DISCOUNTED_PRICE | 8                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000265    | 6            | DISCOUNTED_PRICE | 5                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000289    | 5            | DISCOUNTED_PRICE | 4.5              | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000556    | 30           | DISCOUNTED_PRICE | 27               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000456    | 20           | DISCOUNTED_PRICE | 18               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000835    | 40           | DISCOUNTED_PRICE | 32               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000633    | 50           | DISCOUNTED_PRICE | 40               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000478    | 17           | DISCOUNTED_PRICE | 13               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
    And [整单折扣分配计划分摊03]当前存在如下的预折折扣信息
      | itemSapCode | calculationValue | effectiveState |
      | 10000841    | 8                | ENABLE         |
      | 10000456    | 18               | ENABLE         |
      | 10000556    | 27               | ENABLE         |
      | 10000265    | 5                | ENABLE         |
    And [整单折扣分配计划分摊03]当前存在如下的整单折扣申请单信息
      | projectType | module |
      | PT02        | KM03   |
    And [整单折扣分配计划分摊03]当前存在如下的整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 10000265    | 1000     | 盒     |
      | 10000289    | 1000     | 盒     |
    And [整单折扣分配计划分摊03]当前存在如下的整单折扣分配计划信息
    And [整单折扣分配计划分摊03]当前存在如下的整单折扣分配计划详细信息
      | originalItemSapCode | giftItemSapCode | originalDistributionChannelCode | giftDistributionChannelCode | allocationQuantity | originalQuantity | giftQuantity | initialBonusQuantity | needShip | invalidStatus | unit | originalDistributionChannelName | giftDistributionChannelName | lot_no      | production_date | expiration_date | remark     |
      |                     | 10000265        |                                 | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | AT_LTY_0001 | 2024-05-01      | 2025-12-31      | Detail_001 |
      |                     | 10000265        |                                 | 22                          | 100                |                  |              |                      | 0        | NORMAL        | 盒    |                                 | 分销-非医疗                      | AT_LTY_0002 | 2024-05-01      | 2025-12-31      | Detail_002 |
    And [整单折扣分配计划分摊03]添加商品
      | itemSapCode | quantity |
      | 10000265    | 10       |
      | 10000265    | 8        |
      | 10000265    | 2        |
    Then [整单折扣分配计划分摊03]创建订单应校验通过且提交成功
      | orderWideDiscountSubject | itemSapCode | needShip | unitPrice | quantity | preDiscount | calculationValue | boxRatio | totalInline | preDiscountTotal | orderDiscountTotal | realInline | remark     |
      | PT00                     | 10000265    | 1        | 6         | 10       | 1           | 5                | 1        | 60          | 10               | 2.5                | 47.5       |            |
      | PT00                     | 10000265    | 1        | 6         | 8        | 1           | 5                | 1        | 48          | 8                | 2                  | 38         |            |
      | PT00                     | 10000265    | 1        | 6         | 2        | 1           | 5                | 1        | 12          | 2                | 0.5                | 9.5        |            |
      | PT02                     | 10000265    | 0        | 6         | 1        | 1           | 5                | 1        | 6           | 1                | 0                  | 5          | Detail_002 |
    Then [整单折扣分配计划分摊03]订单整单折扣使用记录成功
      | parentsOrderNo | parentOrderItemNo | orderNo | orderItemNo | orderDiscountApplicationPlanId | applicationPlanDetailId | operateType  | operateAmount | remark     |
      |                | 10                |         | 10          |                                |                         | CREATE_ORDER | 2.50          | Detail_002 |
      |                | 20                |         | 20          |                                |                         | CREATE_ORDER | 2.00          | Detail_002 |
      |                | 30                |         | 30          |                                |                         | CREATE_ORDER | 0.50          | Detail_002 |
    Examples: 基础信息
      | saleOrgSapCode | regionCode | customerSapCode | regionName | customerName | shippingRecipient | shippingTelephone | shippingAddress |


  @discountApportionment04
  Scenario Outline: [场景3-2] 验证折扣行在同商品行中扣减顺序（存在相同原品行，相同原品行完全分配，不相同的原品行中部分分配）
  "[主要测试点]
  1. 存在相同原品行
  2. 相同原品行完全分配
  3. 不相同的原品行中部分分配
  4. 赠品不发货"
    Given [整单折扣分配计划分摊04]当前存在如下客户信息
      | saleOrgSapCode   | regionCode   | customerSapCode   | regionName   | customerName   | shippingAddress   | shippingRecipient   | shippingTelephone   |
      | <saleOrgSapCode> | <regionCode> | <customerSapCode> | <regionName> | <customerName> | <shippingAddress> | <shippingRecipient> | <shippingTelephone> |
    When [整单折扣分配计划分摊04]当前存在如下的预折商品信息
      | itemSapCode | currentPrice | calculationType  | calculationValue | preType               | effectiveState | distributionChannelCode | distributionChannelName |
      | 10000841    | 10           | DISCOUNTED_PRICE | 8                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000265    | 6            | DISCOUNTED_PRICE | 5                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000289    | 5            | DISCOUNTED_PRICE | 4.5              | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000556    | 30           | DISCOUNTED_PRICE | 27               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000456    | 20           | DISCOUNTED_PRICE | 18               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000835    | 40           | DISCOUNTED_PRICE | 32               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000633    | 50           | DISCOUNTED_PRICE | 40               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000478    | 17           | DISCOUNTED_PRICE | 13               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
    And [整单折扣分配计划分摊04]当前存在如下的预折折扣信息
      | itemSapCode | calculationValue | effectiveState |
      | 10000841    | 8                | DISABLE        |
      | 10000456    | 18               | DISABLE        |
      | 10000556    | 27               | DISABLE        |
      | 10000265    | 5                | DISABLE        |
      | 10000289    | 4.5              | DISABLE        |
    And [整单折扣分配计划分摊04]当前存在如下的整单折扣申请单信息
      | projectType | module |
      | PT02        | KM03   |
    And [整单折扣分配计划分摊04]当前存在如下的整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 10000265    | 1000     | 盒     |
      | 10000289    | 1000     | 盒     |
    And [整单折扣分配计划分摊04]当前存在如下的[零头药-AT_FP001]整单折扣分配计划信息
    And [整单折扣分配计划分摊04]当前存在如下的[零头药-AT_FP001]整单折扣分配计划详细信息
      | originalItemSapCode | giftItemSapCode | originalDistributionChannelCode | giftDistributionChannelCode | allocationQuantity | originalQuantity | giftQuantity | initialBonusQuantity | needShip | invalidStatus | unit | originalDistributionChannelName | giftDistributionChannelName | lot_no      | production_date | expiration_date | remark     |
      |                     | 10000289        |                                 | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | AT_LTY_0001 | 2024-05-01      | 2025-12-31      | Detail_001 |
      |                     | 10000289        |                                 | 22                          | 100                |                  |              |                      | 0        | NORMAL        | 盒    |                                 | 分销-非医疗                      | AT_LTY_0002 | 2024-05-01      | 2025-12-31      | Detail_002 |
      |                     | 10000289        |                                 | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | AT_LTY_0003 | 2024-05-01      | 2025-12-31      | Detail_003 |
    And [整单折扣分配计划分摊04]当前存在如下的[零头药-AT_FP002]整单折扣分配计划信息
    And [整单折扣分配计划分摊04]当前存在如下的[零头药-AT_FP002]整单折扣分配计划详细信息
      | originalItemSapCode | giftItemSapCode | originalDistributionChannelCode | giftDistributionChannelCode | allocationQuantity | originalQuantity | giftQuantity | initialBonusQuantity | needShip | invalidStatus | unit | originalDistributionChannelName | giftDistributionChannelName | lot_no      | production_date | expiration_date | remark     |
      |                     | 10000289        |                                 | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | AT_LTY_1001 | 2024-05-01      | 2025-12-31      | Detail_201 |
      |                     | 10000289        |                                 | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | AT_LTY_1002 | 2024-05-01      | 2025-12-31      | Detail_202 |
    And [整单折扣分配计划分摊04]添加商品
      | itemSapCode | quantity |
      | 10000289    | 8        |
      | 10000289    | 10       |
      | 10000289    | 1        |
    Then [整单折扣分配计划分摊04]创建订单应校验通过且提交成功
      | orderWideDiscountSubject | itemSapCode | needShip | unitPrice | quantity | preDiscount | calculationValue | boxRatio | totalInline | preDiscountTotal | orderDiscountTotal | realInline | remark     |
      | PT00                     | 10000289    | 1        | 5         | 8        | 0           | 5                | 1        | 40          | 0                | 7.27               | 32.73      |            |
      | PT00                     | 10000289    | 1        | 5         | 10       | 0           | 5                | 1        | 50          | 0                | 9.09               | 40.91      |            |
      | PT00                     | 10000289    | 1        | 5         | 1        | 0           | 5                | 1        | 5           | 0                | 0.91               | 4.09       |            |
      | PT02                     | 10000289    | 1        | 5         | 1        | 0           | 5                | 1        | 5           | 0                | 0.91               | 4.09       | Detail_001 |
      | PT02                     | 10000289    | 1        | 5         | 2        | 0           | 5                | 1        | 10          | 0                | 1.82               | 8.18       | Detail_201 |
      | PT02                     | 10000289    | 0        | 5         | 1        | 0           | 5                | 1        | 5           | 0                | 0                  | 5          | Detail_002 |
    Then [整单折扣分配计划分摊04]订单整单折扣使用记录成功
      | parentsOrderNo | parentOrderItemNo | orderNo | orderItemNo | orderDiscountApplicationPlanId | applicationPlanDetailId | operateType  | operateAmount | remark     |
      |                | 10                |         | 10          |                                |                         | CREATE_ORDER | 5.00          | Detail_001 |
      |                | 10                |         | 10          |                                |                         | CREATE_ORDER | 2.27          | Detail_201 |
      |                | 20                |         | 20          |                                |                         | CREATE_ORDER | 7.73          | Detail_201 |
      |                | 20                |         | 20          |                                |                         | CREATE_ORDER | 1.36          | Detail_002 |
      |                | 30                |         | 30          |                                |                         | CREATE_ORDER | 0.91          | Detail_002 |
      |                | 40                |         | 40          |                                |                         | CREATE_ORDER | 0.91          | Detail_002 |
      |                | 50                |         | 50          |                                |                         | CREATE_ORDER | 1.82          | Detail_002 |
    Examples: 基础信息
      | saleOrgSapCode | regionCode | customerSapCode | regionName | customerName | shippingRecipient | shippingTelephone | shippingAddress |


  @discountApportionment05
  Scenario Outline: [场景5-1] 验证订单中，零头药类型，同一个分配计划，相同商品，多行赠品行在母单中不合并，在子单中合并
  "[主要测试点]
  1. 项目类型：零头药类型
  2. 同一个分配计划，相同商品
  3. 多行赠品行在母单中不合并，在子单中合并"
    Given [整单折扣分配计划分摊05]当前存在如下客户信息
      | saleOrgSapCode   | regionCode   | customerSapCode   | regionName   | customerName   | shippingAddress   | shippingRecipient   | shippingTelephone   |
      | <saleOrgSapCode> | <regionCode> | <customerSapCode> | <regionName> | <customerName> | <shippingAddress> | <shippingRecipient> | <shippingTelephone> |
    When [整单折扣分配计划分摊05]当前存在如下的预折商品信息
      | itemSapCode | currentPrice | calculationType  | calculationValue | preType               | effectiveState | distributionChannelCode | distributionChannelName |
      | 10000841    | 10           | DISCOUNTED_PRICE | 8                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000265    | 6            | DISCOUNTED_PRICE | 5                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000289    | 5            | DISCOUNTED_PRICE | 4.5              | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000556    | 30           | DISCOUNTED_PRICE | 27               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000456    | 20           | DISCOUNTED_PRICE | 18               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000835    | 40           | DISCOUNTED_PRICE | 32               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000633    | 50           | DISCOUNTED_PRICE | 40               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000478    | 17           | DISCOUNTED_PRICE | 13               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
    And [整单折扣分配计划分摊05]当前存在如下的预折折扣信息
      | itemSapCode | calculationValue | effectiveState |
      | 10000841    | 8                | DISABLE        |
      | 10000456    | 18               | DISABLE        |
      | 10000556    | 27               | DISABLE        |
      | 10000289    | 4.5              | DISABLE        |
      | 10000265    | 5                | ENABLE         |
    And [整单折扣分配计划分摊05]当前存在如下的整单折扣申请单信息
      | projectType | module |
      | PT02        | KM03   |
    And [整单折扣分配计划分摊05]当前存在如下的整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 10000265    | 1000     | 盒     |
      | 10000289    | 1000     | 盒     |
    And [整单折扣分配计划分摊05]当前存在如下的[零头药-AT_FP001]整单折扣分配计划信息
    And [整单折扣分配计划分摊05]当前存在如下的[零头药-AT_FP001]整单折扣分配计划详细信息
      | originalItemSapCode | giftItemSapCode | originalDistributionChannelCode | giftDistributionChannelCode | allocationQuantity | originalQuantity | giftQuantity | initialBonusQuantity | needShip | invalidStatus | unit | originalDistributionChannelName | giftDistributionChannelName | lot_no      | production_date | expiration_date | remark     |
      |                     | 10000289        |                                 | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | AT_LTY_0001 | 2024-05-01      | 2025-12-31      | Detail_001 |
      |                     | 10000289        |                                 | 22                          | 100                |                  |              |                      | 0        | NORMAL        | 盒    |                                 | 分销-非医疗                      | AT_LTY_0002 | 2024-05-01      | 2025-12-31      | Detail_002 |
      |                     | 10000289        |                                 | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | AT_LTY_0003 | 2024-05-01      | 2025-12-31      | Detail_003 |
      |                     | 10000265        |                                 | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | AT_LTY_0004 | 2024-05-01      | 2025-12-31      | Detail_004 |
    And [整单折扣分配计划分摊05]当前存在如下的[零头药-AT_FP002]整单折扣分配计划信息
    And [整单折扣分配计划分摊05]当前存在如下的[零头药-AT_FP002]整单折扣分配计划详细信息
      | originalItemSapCode | giftItemSapCode | originalDistributionChannelCode | giftDistributionChannelCode | allocationQuantity | originalQuantity | giftQuantity | initialBonusQuantity | needShip | invalidStatus | unit | originalDistributionChannelName | giftDistributionChannelName | lot_no      | production_date | expiration_date | remark     |
      |                     | 10000289        |                                 | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | AT_LTY_1001 | 2024-05-01      | 2025-12-31      | Detail_201 |
      |                     | 10000289        |                                 | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | AT_LTY_1002 | 2024-05-01      | 2025-12-31      | Detail_202 |
      |                     | 10000265        |                                 | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | AT_LTY_1003 | 2024-05-01      | 2025-12-31      | Detail_203 |
    And [整单折扣分配计划分摊05]添加商品
      | itemSapCode | quantity |
      | 10000841    | 10       |
      | 10000289    | 10       |
      | 10000289    | 1        |
    Then [整单折扣分配计划分摊05]创建订单应校验通过且提交成功
      | orderWideDiscountSubject | itemSapCode | needShip | unitPrice | quantity | preDiscount | calculationValue | boxRatio | totalInline | preDiscountTotal | orderDiscountTotal | realInline | remark     |
      | PT00                     | 10000841    | 1        | 10        | 10       | 0           | 10               | 1        | 100         | 0                | 8.82               | 91.18      |            |
      | PT00                     | 10000289    | 1        | 5         | 10       | 0           | 5                | 1        | 50          | 0                | 4.41               | 45.59      |            |
      | PT00                     | 10000289    | 1        | 5         | 1        | 0           | 5                | 1        | 5           | 0                | 0.44               | 4.56       |            |
      | PT02                     | 10000289    | 1        | 5         | 2        | 0           | 5                | 1        | 10          | 0                | 0.88               | 9.12       | Detail_001 |
      | PT02                     | 10000289    | 1        | 5         | 1        | 0           | 5                | 1        | 5           | 0                | 0.45               | 4.55       | Detail_003 |
    Then [整单折扣分配计划分摊05]订单整单折扣使用记录成功
      | parentsOrderNo | parentOrderItemNo | orderNo | orderItemNo | orderDiscountApplicationPlanId | applicationPlanDetailId | operateType  | operateAmount | remark     |
      |                | 20                |         | 20          |                                |                         | CREATE_ORDER | 4.41          | Detail_001 |
      |                | 30                |         | 30          |                                |                         | CREATE_ORDER | 0.44          | Detail_001 |
      |                | 40                |         | 40          |                                |                         | CREATE_ORDER | 0.88          | Detail_001 |
      |                | 50                |         | 40          |                                |                         | CREATE_ORDER | 0.45          | Detail_001 |
      |                | 10                |         | 10          |                                |                         | CREATE_ORDER | 3.82          | Detail_001 |
      |                | 10                |         | 10          |                                |                         | CREATE_ORDER | 5             | Detail_003 |
    Examples: 基础信息
      | saleOrgSapCode | regionCode | customerSapCode | regionName | customerName | shippingRecipient | shippingTelephone | shippingAddress |


  @discountApportionment06
  Scenario Outline: [场景5-2] 验证订单中，零头药类型，不同分配计划，相同商品，多行赠品行在母单中不合并，在子单中不合并
  "[主要测试点]
  1. 项目类型：零头药类型
  2. 不同分配计划，相同商品
  3. 多行赠品行在母单中不合并，在子单中合并"
    Given [整单折扣分配计划分摊06]当前存在如下客户信息
      | saleOrgSapCode   | regionCode   | customerSapCode   | regionName   | customerName   | shippingAddress   | shippingRecipient   | shippingTelephone   |
      | <saleOrgSapCode> | <regionCode> | <customerSapCode> | <regionName> | <customerName> | <shippingAddress> | <shippingRecipient> | <shippingTelephone> |
    When [整单折扣分配计划分摊06]当前存在如下的预折商品信息
      | itemSapCode | currentPrice | calculationType  | calculationValue | preType               | effectiveState | distributionChannelCode | distributionChannelName |
      | 10000841    | 10           | DISCOUNTED_PRICE | 8                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000265    | 6            | DISCOUNTED_PRICE | 5                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000289    | 5            | DISCOUNTED_PRICE | 4.5              | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000556    | 30           | DISCOUNTED_PRICE | 27               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000456    | 20           | DISCOUNTED_PRICE | 18               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000835    | 40           | DISCOUNTED_PRICE | 32               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000633    | 50           | DISCOUNTED_PRICE | 40               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000478    | 17           | DISCOUNTED_PRICE | 13               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
    And [整单折扣分配计划分摊06]当前存在如下的预折折扣信息
      | itemSapCode | calculationValue | effectiveState |
      | 10000841    | 8                | DISABLE        |
      | 10000456    | 18               | DISABLE        |
      | 10000556    | 27               | DISABLE        |
      | 10000289    | 4.5              | DISABLE        |
      | 10000265    | 5                | DISABLE        |
    And [整单折扣分配计划分摊06]当前存在如下的整单折扣申请单信息
      | projectType | module |
      | PT02        | KM03   |
    And [整单折扣分配计划分摊06]当前存在如下的整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 10000265    | 1000     | 盒     |
      | 10000289    | 1000     | 盒     |
    And [整单折扣分配计划分摊06]当前存在如下的[零头药-AT_FP001]整单折扣分配计划信息
    And [整单折扣分配计划分摊06]当前存在如下的[零头药-AT_FP001]整单折扣分配计划详细信息
      | originalItemSapCode | giftItemSapCode | originalDistributionChannelCode | giftDistributionChannelCode | allocationQuantity | originalQuantity | giftQuantity | initialBonusQuantity | needShip | invalidStatus | unit | originalDistributionChannelName | giftDistributionChannelName | lot_no      | production_date | expiration_date | remark     |
      |                     | 10000289        |                                 | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | AT_LTY_0001 | 2024-05-01      | 2025-12-31      | Detail_001 |
      |                     | 10000289        |                                 | 22                          | 100                |                  |              |                      | 0        | NORMAL        | 盒    |                                 | 分销-非医疗                      | AT_LTY_0002 | 2024-05-01      | 2025-12-31      | Detail_002 |
      |                     | 10000289        |                                 | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | AT_LTY_0003 | 2024-05-01      | 2025-12-31      | Detail_003 |
      |                     | 10000265        |                                 | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | AT_LTY_0004 | 2024-05-01      | 2025-12-31      | Detail_004 |
    And [整单折扣分配计划分摊06]当前存在如下的[零头药-AT_FP002]整单折扣分配计划信息
    And [整单折扣分配计划分摊06]当前存在如下的[零头药-AT_FP002]整单折扣分配计划详细信息
      | originalItemSapCode | giftItemSapCode | originalDistributionChannelCode | giftDistributionChannelCode | allocationQuantity | originalQuantity | giftQuantity | initialBonusQuantity | needShip | invalidStatus | unit | originalDistributionChannelName | giftDistributionChannelName | lot_no      | production_date | expiration_date | remark     |
      |                     | 10000289        |                                 | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | AT_LTY_1001 | 2024-05-01      | 2025-12-31      | Detail_201 |
      |                     | 10000289        |                                 | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | AT_LTY_1002 | 2024-05-01      | 2025-12-31      | Detail_202 |
      |                     | 10000265        |                                 | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | AT_LTY_1003 | 2024-05-01      | 2025-12-31      | Detail_203 |
    And [整单折扣分配计划分摊06]添加商品
      | itemSapCode | quantity |
      | 10000841    | 10       |
      | 10000289    | 10       |
      | 10000289    | 1        |
    Then [整单折扣分配计划分摊06]创建订单应校验通过且提交成功
      | orderWideDiscountSubject | itemSapCode | needShip | unitPrice | quantity | preDiscount | calculationValue | boxRatio | totalInline | preDiscountTotal | orderDiscountTotal | realInline | remark     |
      | PT00                     | 10000841    | 1        | 10        | 10       | 0           | 10               | 1        | 100         | 0                | 8.82               | 91.18      |            |
      | PT00                     | 10000289    | 1        | 5         | 10       | 0           | 5                | 1        | 50          | 0                | 4.41               | 45.59      |            |
      | PT00                     | 10000289    | 1        | 5         | 1        | 0           | 5                | 1        | 5           | 0                | 0.44               | 4.56       |            |
      | PT02                     | 10000289    | 1        | 5         | 2        | 0           | 5                | 1        | 10          | 0                | 0.88               | 9.12       | Detail_001 |
      | PT02                     | 10000289    | 1        | 5         | 1        | 0           | 5                | 1        | 5           | 0                | 0.45               | 4.55       | Detail_201 |
    Then [整单折扣分配计划分摊06]订单整单折扣使用记录成功
      | parentsOrderNo | parentOrderItemNo | orderNo | orderItemNo | orderDiscountApplicationPlanId | applicationPlanDetailId | operateType  | operateAmount | remark     |
      |                | 20                |         | 20          |                                |                         | CREATE_ORDER | 4.41          | Detail_001 |
      |                | 30                |         | 30          |                                |                         | CREATE_ORDER | 0.44          | Detail_001 |
      |                | 40                |         | 40          |                                |                         | CREATE_ORDER | 0.88          | Detail_001 |
      |                | 50                |         | 50          |                                |                         | CREATE_ORDER | 0.45          | Detail_001 |
      |                | 10                |         | 10          |                                |                         | CREATE_ORDER | 3.82          | Detail_001 |
      |                | 10                |         | 10          |                                |                         | CREATE_ORDER | 5             | Detail_201 |
    Examples: 基础信息
      | saleOrgSapCode | regionCode | customerSapCode | regionName | customerName | shippingRecipient | shippingTelephone | shippingAddress |


  @discountApportionment07
  Scenario Outline: [场景5-3] 验证订单中，零头药类型，相同分配计划，不同商品，多行赠品行在母单中不合并，在子单中不合并
  "[主要测试点]
  1. 项目类型：零头药类型
  2. 相同分配计划，不同商品
  3. 多行赠品行在母单中不合并，在子单中合并"
    Given [整单折扣分配计划分摊07]当前存在如下客户信息
      | saleOrgSapCode   | regionCode   | customerSapCode   | regionName   | customerName   | shippingAddress   | shippingRecipient   | shippingTelephone   |
      | <saleOrgSapCode> | <regionCode> | <customerSapCode> | <regionName> | <customerName> | <shippingAddress> | <shippingRecipient> | <shippingTelephone> |
    When [整单折扣分配计划分摊07]当前存在如下的预折商品信息
      | itemSapCode | currentPrice | calculationType  | calculationValue | preType               | effectiveState | distributionChannelCode | distributionChannelName |
      | 10000841    | 10           | DISCOUNTED_PRICE | 8                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000265    | 6            | DISCOUNTED_PRICE | 5                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000289    | 5            | DISCOUNTED_PRICE | 4.5              | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000556    | 30           | DISCOUNTED_PRICE | 27               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000456    | 20           | DISCOUNTED_PRICE | 18               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000835    | 40           | DISCOUNTED_PRICE | 32               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000633    | 50           | DISCOUNTED_PRICE | 40               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000478    | 17           | DISCOUNTED_PRICE | 13               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
    And [整单折扣分配计划分摊07]当前存在如下的预折折扣信息
      | itemSapCode | calculationValue | effectiveState |
      | 10000841    | 8                | DISABLE        |
      | 10000456    | 18               | DISABLE        |
      | 10000556    | 27               | DISABLE        |
      | 10000289    | 4.5              | DISABLE        |
      | 10000265    | 5                | DISABLE        |
    And [整单折扣分配计划分摊07]当前存在如下的整单折扣申请单信息
      | projectType | module |
      | PT02        | KM03   |
    And [整单折扣分配计划分摊07]当前存在如下的整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 10000265    | 1000     | 盒     |
      | 10000289    | 1000     | 盒     |
    And [整单折扣分配计划分摊07]当前存在如下的[零头药-AT_FP001]整单折扣分配计划信息
    And [整单折扣分配计划分摊07]当前存在如下的[零头药-AT_FP001]整单折扣分配计划详细信息
      | originalItemSapCode | giftItemSapCode | originalDistributionChannelCode | giftDistributionChannelCode | allocationQuantity | originalQuantity | giftQuantity | initialBonusQuantity | needShip | invalidStatus | unit | originalDistributionChannelName | giftDistributionChannelName | lot_no      | production_date | expiration_date | remark     |
      |                     | 10000289        |                                 | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | AT_LTY_0001 | 2024-05-01      | 2025-12-31      | Detail_001 |
      |                     | 10000289        |                                 | 22                          | 100                |                  |              |                      | 0        | NORMAL        | 盒    |                                 | 分销-非医疗                      | AT_LTY_0002 | 2024-05-01      | 2025-12-31      | Detail_002 |
      |                     | 10000289        |                                 | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | AT_LTY_0003 | 2024-05-01      | 2025-12-31      | Detail_003 |
      |                     | 10000265        |                                 | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | AT_LTY_0004 | 2024-05-01      | 2025-12-31      | Detail_004 |
    And [整单折扣分配计划分摊07]当前存在如下的[零头药-AT_FP002]整单折扣分配计划信息
    And [整单折扣分配计划分摊07]当前存在如下的[零头药-AT_FP002]整单折扣分配计划详细信息
      | originalItemSapCode | giftItemSapCode | originalDistributionChannelCode | giftDistributionChannelCode | allocationQuantity | originalQuantity | giftQuantity | initialBonusQuantity | needShip | invalidStatus | unit | originalDistributionChannelName | giftDistributionChannelName | lot_no      | production_date | expiration_date | remark     |
      |                     | 10000289        |                                 | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | AT_LTY_1001 | 2024-05-01      | 2025-12-31      | Detail_201 |
      |                     | 10000289        |                                 | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | AT_LTY_1002 | 2024-05-01      | 2025-12-31      | Detail_202 |
      |                     | 10000265        |                                 | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | AT_LTY_1003 | 2024-05-01      | 2025-12-31      | Detail_203 |
    And [整单折扣分配计划分摊07]添加商品
      | itemSapCode | quantity |
      | 10000841    | 10       |
      | 10000289    | 10       |
      | 10000289    | 1        |
    Then [整单折扣分配计划分摊07]创建订单应校验通过且提交成功
      | orderWideDiscountSubject | itemSapCode | needShip | unitPrice | quantity | preDiscount | calculationValue | boxRatio | totalInline | preDiscountTotal | orderDiscountTotal | realInline | remark     |
      | PT00                     | 10000841    | 1        | 10        | 10       | 0           | 10               | 1        | 100         | 0                | 9.36               | 90.64      |            |
      | PT00                     | 10000289    | 1        | 5         | 10       | 0           | 5                | 1        | 50          | 0                | 4.68               | 45.32      |            |
      | PT00                     | 10000289    | 1        | 5         | 1        | 0           | 5                | 1        | 5           | 0                | 0.47               | 4.53       |            |
      | PT02                     | 10000289    | 1        | 5         | 2        | 0           | 5                | 1        | 10          | 0                | 0.94               | 9.06       | Detail_001 |
      | PT02                     | 10000265    | 1        | 6         | 1        | 0           | 6                | 1        | 6           | 0                | 0.55               | 5.45       | Detail_004 |
    Then [整单折扣分配计划分摊07]订单整单折扣使用记录成功
      | parentsOrderNo | parentOrderItemNo | orderNo | orderItemNo | orderDiscountApplicationPlanId | applicationPlanDetailId | operateType  | operateAmount | remark     |
      |                | 40                |         | 40          |                                |                         | CREATE_ORDER | 0.94          | Detail_001 |
      |                | 20                |         | 20          |                                |                         | CREATE_ORDER | 4.68          | Detail_001 |
      |                | 30                |         | 30          |                                |                         | CREATE_ORDER | 0.47          | Detail_001 |
      |                | 10                |         | 10          |                                |                         | CREATE_ORDER | 3.91          | Detail_001 |
      |                | 50                |         | 50          |                                |                         | CREATE_ORDER | 0.55          | Detail_004 |
      |                | 10                |         | 10          |                                |                         | CREATE_ORDER | 5.45          | Detail_004 |
    Examples: 基础信息
      | saleOrgSapCode | regionCode | customerSapCode | regionName | customerName | shippingRecipient | shippingTelephone | shippingAddress |


  @discountApportionment08
  Scenario Outline: [场景5-4] 验证订单中，零头药类型，不同分配计划，不同商品，多行赠品行在母单中不合并，在子单中不合并
  "[主要测试点]
  1. 项目类型：零头药类型
  2. 不同分配计划，不同商品
  3. 多行赠品行在母单中不合并，在子单中合并"
    Given [整单折扣分配计划分摊08]当前存在如下客户信息
      | saleOrgSapCode   | regionCode   | customerSapCode   | regionName   | customerName   | shippingAddress   | shippingRecipient   | shippingTelephone   |
      | <saleOrgSapCode> | <regionCode> | <customerSapCode> | <regionName> | <customerName> | <shippingAddress> | <shippingRecipient> | <shippingTelephone> |
    When [整单折扣分配计划分摊08]当前存在如下的预折商品信息
      | itemSapCode | currentPrice | calculationType  | calculationValue | preType               | effectiveState | distributionChannelCode | distributionChannelName |
      | 10000841    | 10           | DISCOUNTED_PRICE | 8                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000265    | 6            | DISCOUNTED_PRICE | 5                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000289    | 5            | DISCOUNTED_PRICE | 4.5              | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000556    | 30           | DISCOUNTED_PRICE | 27               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000456    | 20           | DISCOUNTED_PRICE | 18               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000835    | 40           | DISCOUNTED_PRICE | 32               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000633    | 50           | DISCOUNTED_PRICE | 40               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000478    | 17           | DISCOUNTED_PRICE | 13               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
    And [整单折扣分配计划分摊08]当前存在如下的预折折扣信息
      | itemSapCode | calculationValue | effectiveState |
      | 10000841    | 8                | DISABLE        |
      | 10000456    | 18               | DISABLE        |
      | 10000556    | 27               | DISABLE        |
      | 10000289    | 4.5              | DISABLE        |
      | 10000265    | 5                | DISABLE        |
    And [整单折扣分配计划分摊08]当前存在如下的整单折扣申请单信息
      | projectType | module |
      | PT02        | KM03   |
    And [整单折扣分配计划分摊08]当前存在如下的整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 10000265    | 1000     | 盒     |
      | 10000289    | 1000     | 盒     |
    And [整单折扣分配计划分摊08]当前存在如下的[零头药-AT_FP001]整单折扣分配计划信息
    And [整单折扣分配计划分摊08]当前存在如下的[零头药-AT_FP001]整单折扣分配计划详细信息
      | originalItemSapCode | giftItemSapCode | originalDistributionChannelCode | giftDistributionChannelCode | allocationQuantity | originalQuantity | giftQuantity | initialBonusQuantity | needShip | invalidStatus | unit | originalDistributionChannelName | giftDistributionChannelName | lot_no      | production_date | expiration_date | remark     |
      |                     | 10000289        |                                 | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | AT_LTY_0001 | 2024-05-01      | 2025-12-31      | Detail_001 |
      |                     | 10000289        |                                 | 22                          | 100                |                  |              |                      | 0        | NORMAL        | 盒    |                                 | 分销-非医疗                      | AT_LTY_0002 | 2024-05-01      | 2025-12-31      | Detail_002 |
      |                     | 10000289        |                                 | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | AT_LTY_0003 | 2024-05-01      | 2025-12-31      | Detail_003 |
      |                     | 10000265        |                                 | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | AT_LTY_0004 | 2024-05-01      | 2025-12-31      | Detail_004 |
    And [整单折扣分配计划分摊08]当前存在如下的[零头药-AT_FP002]整单折扣分配计划信息
    And [整单折扣分配计划分摊08]当前存在如下的[零头药-AT_FP002]整单折扣分配计划详细信息
      | originalItemSapCode | giftItemSapCode | originalDistributionChannelCode | giftDistributionChannelCode | allocationQuantity | originalQuantity | giftQuantity | initialBonusQuantity | needShip | invalidStatus | unit | originalDistributionChannelName | giftDistributionChannelName | lot_no      | production_date | expiration_date | remark     |
      |                     | 10000289        |                                 | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | AT_LTY_1001 | 2024-05-01      | 2025-12-31      | Detail_201 |
      |                     | 10000289        |                                 | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | AT_LTY_1002 | 2024-05-01      | 2025-12-31      | Detail_202 |
      |                     | 10000265        |                                 | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | AT_LTY_1003 | 2024-05-01      | 2025-12-31      | Detail_203 |
    And [整单折扣分配计划分摊08]添加商品
      | itemSapCode | quantity |
      | 10000841    | 10       |
      | 10000289    | 10       |
      | 10000289    | 1        |
    Then [整单折扣分配计划分摊08]创建订单应校验通过且提交成功
      | orderWideDiscountSubject | itemSapCode | needShip | unitPrice | quantity | preDiscount | calculationValue | boxRatio | totalInline | preDiscountTotal | orderDiscountTotal | realInline | remark     |
      | PT00                     | 10000841    | 1        | 10        | 10       | 0           | 10               | 1        | 100         | 0                | 9.36               | 90.64      |            |
      | PT00                     | 10000289    | 1        | 5         | 10       | 0           | 5                | 1        | 50          | 0                | 4.68               | 45.32      |            |
      | PT00                     | 10000289    | 1        | 5         | 1        | 0           | 5                | 1        | 5           | 0                | 0.47               | 4.53       |            |
      | PT02                     | 10000289    | 1        | 5         | 2        | 0           | 5                | 1        | 10          | 0                | 0.94               | 9.06       | Detail_001 |
      | PT02                     | 10000265    | 1        | 6         | 1        | 0           | 6                | 1        | 6           | 0                | 0.55               | 5.45       | Detail_203 |
    Then [整单折扣分配计划分摊08]订单整单折扣使用记录成功
      | parentsOrderNo | parentOrderItemNo | orderNo | orderItemNo | orderDiscountApplicationPlanId | applicationPlanDetailId | operateType  | operateAmount | remark     |
      |                | 20                |         | 20          |                                |                         | CREATE_ORDER | 4.68          | Detail_001 |
      |                | 30                |         | 30          |                                |                         | CREATE_ORDER | 0.47          | Detail_001 |
      |                | 40                |         | 40          |                                |                         | CREATE_ORDER | 0.94          | Detail_001 |
      |                | 10                |         | 10          |                                |                         | CREATE_ORDER | 3.91          | Detail_001 |
      |                | 10                |         | 10          |                                |                         | CREATE_ORDER | 5.45          | Detail_203 |
      |                | 50                |         | 50          |                                |                         | CREATE_ORDER | 0.55          | Detail_203 |
    Examples: 基础信息
      | saleOrgSapCode | regionCode | customerSapCode | regionName | customerName | shippingRecipient | shippingTelephone | shippingAddress |


  @discountApportionment09
  Scenario Outline: [场景5-5] 验证订单中，非零头药类型，同一个分配计划，相同商品，多行赠品行在母单中不合并，在子单中不合并
  "[主要测试点]
  1. 项目类型：非零头药类型
  2. 同一个分配计划，相同商品
  3. 多行赠品行在母单中不合并，在子单中合并"
    Given [整单折扣分配计划分摊09]当前存在如下客户信息
      | saleOrgSapCode   | regionCode   | customerSapCode   | regionName   | customerName   | shippingAddress   | shippingRecipient   | shippingTelephone   |
      | <saleOrgSapCode> | <regionCode> | <customerSapCode> | <regionName> | <customerName> | <shippingAddress> | <shippingRecipient> | <shippingTelephone> |
    When [整单折扣分配计划分摊09]当前存在如下的预折商品信息
      | itemSapCode | currentPrice | calculationType  | calculationValue | preType               | effectiveState | distributionChannelCode | distributionChannelName |
      | 10000841    | 10           | DISCOUNTED_PRICE | 8                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000265    | 6            | DISCOUNTED_PRICE | 5                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000289    | 5            | DISCOUNTED_PRICE | 4.5              | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000556    | 30           | DISCOUNTED_PRICE | 27               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000456    | 20           | DISCOUNTED_PRICE | 18               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000835    | 40           | DISCOUNTED_PRICE | 32               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000633    | 50           | DISCOUNTED_PRICE | 40               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000478    | 17           | DISCOUNTED_PRICE | 13               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
    And [整单折扣分配计划分摊09]当前存在如下的预折折扣信息
      | itemSapCode | calculationValue | effectiveState |
      | 10000841    | 8                | DISABLE        |
      | 10000456    | 18               | DISABLE        |
      | 10000556    | 27               | DISABLE        |
      | 10000289    | 4.5              | DISABLE        |
      | 10000265    | 5                | DISABLE        |
    And [整单折扣分配计划分摊09]当前存在如下的[纯赠]整单折扣申请单信息
      | projectType | module |
      | PT04        | KM03   |
    And [整单折扣分配计划分摊09]当前存在如下的[纯赠]整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 10000265    | 1000     | 盒     |
      | 10000289    | 1000     | 盒     |
    And [整单折扣分配计划分摊09]当前存在如下的[纯赠-AT_FP001]整单折扣分配计划信息
    And [整单折扣分配计划分摊09]当前存在如下的[纯赠-AT_FP001]整单折扣分配计划详细信息
      | originalItemSapCode | giftItemSapCode | originalDistributionChannelCode | giftDistributionChannelCode | allocationQuantity | originalQuantity | giftQuantity | initialBonusQuantity | needShip | invalidStatus | unit | originalDistributionChannelName | giftDistributionChannelName | remark     |
      |                     | 10000289        |                                 | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | Detail_001 |
      |                     | 10000289        |                                 | 22                          | 100                |                  |              |                      | 0        | NORMAL        | 盒    |                                 | 分销-非医疗                      | Detail_002 |
      |                     | 10000289        |                                 | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | Detail_003 |
      |                     | 10000265        |                                 | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | Detail_004 |
    And [整单折扣分配计划分摊09]当前存在如下的[捐赠]整单折扣申请单信息
      | projectType | module |
      | PT06        | KM03   |
    And [整单折扣分配计划分摊09]当前存在如下的[捐赠]整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 10000265    | 1000     | 盒     |
      | 10000289    | 1000     | 盒     |
    And [整单折扣分配计划分摊09]当前存在如下的[捐赠-AT_FP002]整单折扣分配计划信息
    And [整单折扣分配计划分摊09]当前存在如下的[捐赠-AT_FP002]整单折扣分配计划详细信息
      | originalItemSapCode | giftItemSapCode | originalDistributionChannelCode | giftDistributionChannelCode | allocationQuantity | originalQuantity | giftQuantity | initialBonusQuantity | needShip | invalidStatus | unit | originalDistributionChannelName | giftDistributionChannelName | remark     |
      |                     | 10000289        |                                 | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | Detail_201 |
      |                     | 10000289        |                                 | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | Detail_202 |
      |                     | 10000265        |                                 | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | Detail_203 |
    And [整单折扣分配计划分摊09]添加商品
      | itemSapCode | quantity |
      | 10000841    | 10       |
      | 10000289    | 10       |
      | 10000289    | 1        |
    Then [整单折扣分配计划分摊09]创建订单应校验通过且提交成功
      | orderWideDiscountSubject | itemSapCode | needShip | unitPrice | quantity | preDiscount | calculationValue | boxRatio | totalInline | preDiscountTotal | orderDiscountTotal | realInline | remark     |
      | PT00                     | 10000841    | 1        | 10        | 10       | 0           | 10               | 1        | 100         | 0                | 8.82               | 91.18      |            |
      | PT00                     | 10000289    | 1        | 5         | 10       | 0           | 5                | 1        | 50          | 0                | 4.41               | 45.59      |            |
      | PT00                     | 10000289    | 1        | 5         | 1        | 0           | 5                | 1        | 5           | 0                | 0.44               | 4.56       |            |
      | PT04                     | 10000289    | 1        | 5         | 2        | 0           | 5                | 1        | 10          | 0                | 0.88               | 9.12       | Detail_001 |
      | PT04                     | 10000289    | 1        | 5         | 1        | 0           | 5                | 1        | 5           | 0                | 0.45               | 4.55       | Detail_003 |
    Then [整单折扣分配计划分摊09]订单整单折扣使用记录成功
      | parentsOrderNo | parentOrderItemNo | orderNo | orderItemNo | orderDiscountApplicationPlanId | applicationPlanDetailId | operateType  | operateAmount | remark     |
      |                | 20                |         | 20          |                                |                         | CREATE_ORDER | 4.41          | Detail_001 |
      |                | 30                |         | 30          |                                |                         | CREATE_ORDER | 0.44          | Detail_001 |
      |                | 40                |         | 40          |                                |                         | CREATE_ORDER | 0.88          | Detail_001 |
      |                | 50                |         | 50          |                                |                         | CREATE_ORDER | 0.45          | Detail_001 |
      |                | 10                |         | 10          |                                |                         | CREATE_ORDER | 3.82          | Detail_001 |
      |                | 10                |         | 10          |                                |                         | CREATE_ORDER | 5             | Detail_003 |
    Examples: 基础信息
      | saleOrgSapCode | regionCode | customerSapCode | regionName | customerName | shippingRecipient | shippingTelephone | shippingAddress |


  @discountApportionment10
  Scenario Outline: [场景5-6] 验证订单中，非零头药类型，不同分配计划，相同商品，多行赠品行在母单中不合并，在子单中不合并
  "[主要测试点]
  1. 项目类型：非零头药类型
  2. 不同分配计划，相同商品
  3. 多行赠品行在母单中不合并，在子单中不合并"
    Given [整单折扣分配计划分摊10]当前存在如下客户信息
      | saleOrgSapCode   | regionCode   | customerSapCode   | regionName   | customerName   | shippingAddress   | shippingRecipient   | shippingTelephone   |
      | <saleOrgSapCode> | <regionCode> | <customerSapCode> | <regionName> | <customerName> | <shippingAddress> | <shippingRecipient> | <shippingTelephone> |
    When [整单折扣分配计划分摊10]当前存在如下的预折商品信息
      | itemSapCode | currentPrice | calculationType  | calculationValue | preType               | effectiveState | distributionChannelCode | distributionChannelName |
      | 10000841    | 10           | DISCOUNTED_PRICE | 8                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000265    | 6            | DISCOUNTED_PRICE | 5                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000289    | 5            | DISCOUNTED_PRICE | 4.5              | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000556    | 30           | DISCOUNTED_PRICE | 27               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000456    | 20           | DISCOUNTED_PRICE | 18               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000835    | 40           | DISCOUNTED_PRICE | 32               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000633    | 50           | DISCOUNTED_PRICE | 40               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000478    | 17           | DISCOUNTED_PRICE | 13               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
    And [整单折扣分配计划分摊10]当前存在如下的预折折扣信息
      | itemSapCode | calculationValue | effectiveState |
      | 10000841    | 8                | DISABLE        |
      | 10000456    | 18               | DISABLE        |
      | 10000556    | 27               | DISABLE        |
      | 10000289    | 4.5              | DISABLE        |
      | 10000265    | 5                | DISABLE        |
    And [整单折扣分配计划分摊10]当前存在如下的[纯赠]整单折扣申请单信息
      | projectType | module |
      | PT04        | KM03   |
    And [整单折扣分配计划分摊10]当前存在如下的[纯赠]整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 10000265    | 1000     | 盒     |
      | 10000289    | 1000     | 盒     |
    And [整单折扣分配计划分摊10]当前存在如下的[纯赠-AT_FP001]整单折扣分配计划信息
    And [整单折扣分配计划分摊10]当前存在如下的[纯赠-AT_FP001]整单折扣分配计划详细信息
      | originalItemSapCode | giftItemSapCode | originalDistributionChannelCode | giftDistributionChannelCode | allocationQuantity | originalQuantity | giftQuantity | initialBonusQuantity | needShip | invalidStatus | unit | originalDistributionChannelName | giftDistributionChannelName | remark     |
      |                     | 10000289        |                                 | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | Detail_001 |
      |                     | 10000289        |                                 | 22                          | 100                |                  |              |                      | 0        | NORMAL        | 盒    |                                 | 分销-非医疗                      | Detail_002 |
      |                     | 10000289        |                                 | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | Detail_003 |
      |                     | 10000265        |                                 | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | Detail_004 |
    And [整单折扣分配计划分摊10]当前存在如下的[捐赠]整单折扣申请单信息
      | projectType | module |
      | PT06        | KM03   |
    And [整单折扣分配计划分摊10]当前存在如下的[捐赠]整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 10000265    | 1000     | 盒     |
      | 10000289    | 1000     | 盒     |
    And [整单折扣分配计划分摊10]当前存在如下的[捐赠-AT_FP002]整单折扣分配计划信息
    And [整单折扣分配计划分摊10]当前存在如下的[捐赠-AT_FP002]整单折扣分配计划详细信息
      | originalItemSapCode | giftItemSapCode | originalDistributionChannelCode | giftDistributionChannelCode | allocationQuantity | originalQuantity | giftQuantity | initialBonusQuantity | needShip | invalidStatus | unit | originalDistributionChannelName | giftDistributionChannelName | remark     |
      |                     | 10000289        |                                 | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | Detail_201 |
      |                     | 10000289        |                                 | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | Detail_202 |
      |                     | 10000265        |                                 | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | Detail_203 |
    And [整单折扣分配计划分摊10]添加商品
      | itemSapCode | quantity |
      | 10000841    | 10       |
      | 10000289    | 10       |
      | 10000289    | 1        |
    Then [整单折扣分配计划分摊10]创建订单应校验通过且提交成功
      | orderWideDiscountSubject | itemSapCode | needShip | unitPrice | quantity | preDiscount | calculationValue | boxRatio | totalInline | preDiscountTotal | orderDiscountTotal | realInline | remark     |
      | PT00                     | 10000841    | 1        | 10        | 10       | 0           | 10               | 1        | 100         | 0                | 8.82               | 91.18      |            |
      | PT00                     | 10000289    | 1        | 5         | 10       | 0           | 5                | 1        | 50          | 0                | 4.41               | 45.59      |            |
      | PT00                     | 10000289    | 1        | 5         | 1        | 0           | 5                | 1        | 5           | 0                | 0.44               | 4.56       |            |
      | PT04                     | 10000289    | 1        | 5         | 2        | 0           | 5                | 1        | 10          | 0                | 0.88               | 9.12       | Detail_001 |
      | PT06                     | 10000289    | 1        | 5         | 1        | 0           | 5                | 1        | 5           | 0                | 0.45               | 4.55       | Detail_201 |
    Then [整单折扣分配计划分摊10]订单整单折扣使用记录成功
      | parentsOrderNo | parentOrderItemNo | orderNo | orderItemNo | orderDiscountApplicationPlanId | applicationPlanDetailId | operateType  | operateAmount | remark     |
      |                | 20                |         | 20          |                                |                         | CREATE_ORDER | 4.41          | Detail_001 |
      |                | 30                |         | 30          |                                |                         | CREATE_ORDER | 0.44          | Detail_001 |
      |                | 40                |         | 40          |                                |                         | CREATE_ORDER | 0.88          | Detail_001 |
      |                | 50                |         | 50          |                                |                         | CREATE_ORDER | 0.45          | Detail_001 |
      |                | 10                |         | 10          |                                |                         | CREATE_ORDER | 3.82          | Detail_001 |
      |                | 10                |         | 10          |                                |                         | CREATE_ORDER | 5             | Detail_201 |
    Examples: 基础信息
      | saleOrgSapCode | regionCode | customerSapCode | regionName | customerName | shippingRecipient | shippingTelephone | shippingAddress |


  @discountApportionment11
  Scenario Outline: [场景5-7] 验证订单中，非零头药类型，相同分配计划，不同商品，多行赠品行在母单中不合并，在子单中不合并
  "[主要测试点]
  1. 项目类型：非零头药类型
  2. 相同分配计划，不同商品
  3. 多行赠品行在母单中不合并，在子单中不合并"
    Given [整单折扣分配计划分摊11]当前存在如下客户信息
      | saleOrgSapCode   | regionCode   | customerSapCode   | regionName   | customerName   | shippingAddress   | shippingRecipient   | shippingTelephone   |
      | <saleOrgSapCode> | <regionCode> | <customerSapCode> | <regionName> | <customerName> | <shippingAddress> | <shippingRecipient> | <shippingTelephone> |
    When [整单折扣分配计划分摊11]当前存在如下的预折商品信息
      | itemSapCode | currentPrice | calculationType  | calculationValue | preType               | effectiveState | distributionChannelCode | distributionChannelName |
      | 10000841    | 10           | DISCOUNTED_PRICE | 8                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000265    | 6            | DISCOUNTED_PRICE | 5                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000289    | 5            | DISCOUNTED_PRICE | 4.5              | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000556    | 30           | DISCOUNTED_PRICE | 27               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000456    | 20           | DISCOUNTED_PRICE | 18               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000835    | 40           | DISCOUNTED_PRICE | 32               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000633    | 50           | DISCOUNTED_PRICE | 40               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000478    | 17           | DISCOUNTED_PRICE | 13               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
    And [整单折扣分配计划分摊11]当前存在如下的预折折扣信息
      | itemSapCode | calculationValue | effectiveState |
      | 10000841    | 8                | DISABLE        |
      | 10000456    | 18               | DISABLE        |
      | 10000556    | 27               | DISABLE        |
      | 10000289    | 4.5              | DISABLE        |
      | 10000265    | 5                | DISABLE        |
    And [整单折扣分配计划分摊11]当前存在如下的[纯赠]整单折扣申请单信息
      | projectType | module |
      | PT04        | KM03   |
    And [整单折扣分配计划分摊11]当前存在如下的[纯赠]整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 10000265    | 1000     | 盒     |
      | 10000289    | 1000     | 盒     |
    And [整单折扣分配计划分摊11]当前存在如下的[纯赠-AT_FP001]整单折扣分配计划信息
    And [整单折扣分配计划分摊11]当前存在如下的[纯赠-AT_FP001]整单折扣分配计划详细信息
      | originalItemSapCode | giftItemSapCode | originalDistributionChannelCode | giftDistributionChannelCode | allocationQuantity | originalQuantity | giftQuantity | initialBonusQuantity | needShip | invalidStatus | unit | originalDistributionChannelName | giftDistributionChannelName | remark     |
      |                     | 10000289        |                                 | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | Detail_001 |
      |                     | 10000289        |                                 | 22                          | 100                |                  |              |                      | 0        | NORMAL        | 盒    |                                 | 分销-非医疗                      | Detail_002 |
      |                     | 10000289        |                                 | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | Detail_003 |
      |                     | 10000265        |                                 | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | Detail_004 |
    And [整单折扣分配计划分摊11]当前存在如下的[捐赠]整单折扣申请单信息
      | projectType | module |
      | PT06        | KM03   |
    And [整单折扣分配计划分摊11]当前存在如下的[捐赠]整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 10000265    | 1000     | 盒     |
      | 10000289    | 1000     | 盒     |
    And [整单折扣分配计划分摊11]当前存在如下的[捐赠-AT_FP002]整单折扣分配计划信息
    And [整单折扣分配计划分摊11]当前存在如下的[捐赠-AT_FP002]整单折扣分配计划详细信息
      | originalItemSapCode | giftItemSapCode | originalDistributionChannelCode | giftDistributionChannelCode | allocationQuantity | originalQuantity | giftQuantity | initialBonusQuantity | needShip | invalidStatus | unit | originalDistributionChannelName | giftDistributionChannelName | remark     |
      |                     | 10000289        |                                 | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | Detail_201 |
      |                     | 10000289        |                                 | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | Detail_202 |
      |                     | 10000265        |                                 | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | Detail_203 |
    And [整单折扣分配计划分摊11]添加商品
      | itemSapCode | quantity |
      | 10000841    | 10       |
      | 10000289    | 10       |
      | 10000289    | 1        |
    Then [整单折扣分配计划分摊11]创建订单应校验通过且提交成功
      | orderWideDiscountSubject | itemSapCode | needShip | unitPrice | quantity | preDiscount | calculationValue | boxRatio | totalInline | preDiscountTotal | orderDiscountTotal | realInline | remark     |
      | PT00                     | 10000841    | 1        | 10        | 10       | 0           | 10               | 1        | 100         | 0                | 9.36               | 90.64      |            |
      | PT00                     | 10000289    | 1        | 5         | 10       | 0           | 5                | 1        | 50          | 0                | 4.68               | 45.32      |            |
      | PT00                     | 10000289    | 1        | 5         | 1        | 0           | 5                | 1        | 5           | 0                | 0.47               | 4.53       |            |
      | PT04                     | 10000289    | 1        | 5         | 2        | 0           | 5                | 1        | 10          | 0                | 0.94               | 9.06       | Detail_001 |
      | PT04                     | 10000265    | 1        | 6         | 1        | 0           | 6                | 1        | 6           | 0                | 0.55               | 5.45       | Detail_004 |
    Then [整单折扣分配计划分摊11]订单整单折扣使用记录成功
      | parentsOrderNo | parentOrderItemNo | orderNo | orderItemNo | orderDiscountApplicationPlanId | applicationPlanDetailId | operateType  | operateAmount | remark     |
      |                | 20                |         | 20          |                                |                         | CREATE_ORDER | 4.68          | Detail_001 |
      |                | 30                |         | 30          |                                |                         | CREATE_ORDER | 0.47          | Detail_001 |
      |                | 40                |         | 40          |                                |                         | CREATE_ORDER | 0.94          | Detail_001 |
      |                | 50                |         | 50          |                                |                         | CREATE_ORDER | 0.55          | Detail_004 |
      |                | 10                |         | 10          |                                |                         | CREATE_ORDER | 3.91          | Detail_001 |
      |                | 10                |         | 10          |                                |                         | CREATE_ORDER | 5.45          | Detail_004 |
    Examples: 基础信息
      | saleOrgSapCode | regionCode | customerSapCode | regionName | customerName | shippingRecipient | shippingTelephone | shippingAddress |


  @discountApportionment12
  Scenario Outline: [场景5-8] 验证订单中，非零头药类型，不同分配计划，不同商品，多行赠品行在母单中不合并，在子单中不合并
  "[主要测试点]
  1. 项目类型：非零头药类型
  2. 不同分配计划，不同商品
  3. 多行赠品行在母单中不合并，在子单中不合并"
    Given [整单折扣分配计划分摊12]当前存在如下客户信息
      | saleOrgSapCode   | regionCode   | customerSapCode   | regionName   | customerName   | shippingAddress   | shippingRecipient   | shippingTelephone   |
      | <saleOrgSapCode> | <regionCode> | <customerSapCode> | <regionName> | <customerName> | <shippingAddress> | <shippingRecipient> | <shippingTelephone> |
    When [整单折扣分配计划分摊12]当前存在如下的预折商品信息
      | itemSapCode | currentPrice | calculationType  | calculationValue | preType               | effectiveState | distributionChannelCode | distributionChannelName |
      | 10000841    | 10           | DISCOUNTED_PRICE | 8                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000265    | 6            | DISCOUNTED_PRICE | 5                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000289    | 5            | DISCOUNTED_PRICE | 4.5              | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000556    | 30           | DISCOUNTED_PRICE | 27               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000456    | 20           | DISCOUNTED_PRICE | 18               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000835    | 40           | DISCOUNTED_PRICE | 32               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000633    | 50           | DISCOUNTED_PRICE | 40               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000478    | 17           | DISCOUNTED_PRICE | 13               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
    And [整单折扣分配计划分摊12]当前存在如下的预折折扣信息
      | itemSapCode | calculationValue | effectiveState |
      | 10000841    | 8                | DISABLE        |
      | 10000456    | 18               | DISABLE        |
      | 10000556    | 27               | DISABLE        |
      | 10000289    | 4.5              | DISABLE        |
      | 10000265    | 5                | DISABLE        |
    And [整单折扣分配计划分摊12]当前存在如下的[纯赠]整单折扣申请单信息
      | projectType | module |
      | PT04        | KM03   |
    And [整单折扣分配计划分摊12]当前存在如下的[纯赠]整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 10000265    | 1000     | 盒     |
      | 10000289    | 1000     | 盒     |
    And [整单折扣分配计划分摊12]当前存在如下的[纯赠-AT_FP001]整单折扣分配计划信息
    And [整单折扣分配计划分摊12]当前存在如下的[纯赠-AT_FP001]整单折扣分配计划详细信息
      | originalItemSapCode | giftItemSapCode | originalDistributionChannelCode | giftDistributionChannelCode | allocationQuantity | originalQuantity | giftQuantity | initialBonusQuantity | needShip | invalidStatus | unit | originalDistributionChannelName | giftDistributionChannelName | remark     |
      |                     | 10000289        |                                 | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | Detail_001 |
      |                     | 10000289        |                                 | 22                          | 100                |                  |              |                      | 0        | NORMAL        | 盒    |                                 | 分销-非医疗                      | Detail_002 |
      |                     | 10000289        |                                 | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | Detail_003 |
      |                     | 10000265        |                                 | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | Detail_004 |
    And [整单折扣分配计划分摊12]当前存在如下的[捐赠]整单折扣申请单信息
      | projectType | module |
      | PT06        | KM03   |
    And [整单折扣分配计划分摊12]当前存在如下的[捐赠]整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 10000265    | 1000     | 盒     |
      | 10000289    | 1000     | 盒     |
    And [整单折扣分配计划分摊12]当前存在如下的[捐赠-AT_FP002]整单折扣分配计划信息
    And [整单折扣分配计划分摊12]当前存在如下的[捐赠-AT_FP002]整单折扣分配计划详细信息
      | originalItemSapCode | giftItemSapCode | originalDistributionChannelCode | giftDistributionChannelCode | allocationQuantity | originalQuantity | giftQuantity | initialBonusQuantity | needShip | invalidStatus | unit | originalDistributionChannelName | giftDistributionChannelName | remark     |
      |                     | 10000289        |                                 | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | Detail_201 |
      |                     | 10000289        |                                 | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | Detail_202 |
      |                     | 10000265        |                                 | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | Detail_203 |
    And [整单折扣分配计划分摊12]添加商品
      | itemSapCode | quantity |
      | 10000841    | 10       |
      | 10000289    | 10       |
      | 10000289    | 1        |
    Then [整单折扣分配计划分摊12]创建订单应校验通过且提交成功
      | orderWideDiscountSubject | itemSapCode | needShip | unitPrice | quantity | preDiscount | calculationValue | boxRatio | totalInline | preDiscountTotal | orderDiscountTotal | realInline | remark     |
      | PT00                     | 10000841    | 1        | 10        | 10       | 0           | 10               | 1        | 100         | 0                | 9.36               | 90.64      |            |
      | PT00                     | 10000289    | 1        | 5         | 10       | 0           | 5                | 1        | 50          | 0                | 4.68               | 45.32      |            |
      | PT00                     | 10000289    | 1        | 5         | 1        | 0           | 5                | 1        | 5           | 0                | 0.47               | 4.53       |            |
      | PT04                     | 10000289    | 1        | 5         | 2        | 0           | 5                | 1        | 10          | 0                | 0.94               | 9.06       | Detail_001 |
      | PT06                     | 10000265    | 1        | 6         | 1        | 0           | 6                | 1        | 6           | 0                | 0.55               | 5.45       | Detail_203 |
    Then [整单折扣分配计划分摊12]订单整单折扣使用记录成功
      | parentsOrderNo | parentOrderItemNo | orderNo | orderItemNo | orderDiscountApplicationPlanId | applicationPlanDetailId | operateType  | operateAmount | remark     |
      |                | 20                |         | 20          |                                |                         | CREATE_ORDER | 4.68          | Detail_001 |
      |                | 30                |         | 30          |                                |                         | CREATE_ORDER | 0.47          | Detail_001 |
      |                | 40                |         | 40          |                                |                         | CREATE_ORDER | 0.94          | Detail_001 |
      |                | 50                |         | 50          |                                |                         | CREATE_ORDER | 0.55          | Detail_203 |
      |                | 10                |         | 10          |                                |                         | CREATE_ORDER | 3.91          | Detail_001 |
      |                | 10                |         | 10          |                                |                         | CREATE_ORDER | 5.45          | Detail_203 |
    Examples: 基础信息
      | saleOrgSapCode | regionCode | customerSapCode | regionName | customerName | shippingRecipient | shippingTelephone | shippingAddress |


  @discountApportionment13
  Scenario Outline: [场景6] 验证订单中，优先将折扣行的折扣分配至同商品行，对未分配完毕的折扣行按照商品行顺序分配
  "[主要测试点]
  1. 项目类型：买赠、纯赠、零头药类型
  2. 分配计划+商品组合（含不发货赠品）
  3. 优先将折扣行的折扣分配至同商品行，相同商品行按照排序顺序分配；
  4. 折扣行的剩余待分配金额进行分配
  （1）对未分配完毕的折扣行进行排序，按照商品行顺序升序排列
  （2）对整单折扣金额未分配完毕的商品行进行排序，按照商品行顺序升序排列；
  （3）按照以上排序，依次将1)中剩余的折扣金额分配至2）"
    Given [整单折扣分配计划分摊13]当前存在如下客户信息
      | saleOrgSapCode   | regionCode   | customerSapCode   | regionName   | customerName   | shippingAddress   | shippingRecipient   | shippingTelephone   |
      | <saleOrgSapCode> | <regionCode> | <customerSapCode> | <regionName> | <customerName> | <shippingAddress> | <shippingRecipient> | <shippingTelephone> |
    When [整单折扣分配计划分摊13]当前存在如下的预折商品信息
      | itemSapCode | currentPrice | calculationType  | calculationValue | preType               | effectiveState | distributionChannelCode | distributionChannelName |
      | 10000841    | 10           | DISCOUNTED_PRICE | 8                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000265    | 6            | DISCOUNTED_PRICE | 5                | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000289    | 5            | DISCOUNTED_PRICE | 4.5              | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000556    | 30           | DISCOUNTED_PRICE | 27               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000456    | 20           | DISCOUNTED_PRICE | 18               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000835    | 40           | DISCOUNTED_PRICE | 32               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000633    | 50           | DISCOUNTED_PRICE | 40               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
      | 10000478    | 17           | DISCOUNTED_PRICE | 13               | CUSTOMER_PRE_DISCOUNT | ENABLE         | 22                      | 分销-非医疗                  |
    And [整单折扣分配计划分摊13]当前存在如下的预折折扣信息
      | itemSapCode | calculationValue | effectiveState |
      | 10000841    | 6.8              | ENABLE         |
      | 10000456    | 19               | ENABLE         |
      | 10000633    | 49               | ENABLE         |
      | 10000289    | 4.5              | DISABLE        |
      | 10000265    | 5                | DISABLE        |
    And [整单折扣分配计划分摊13]当前存在如下的[买赠-AT_FP001]整单折扣申请单信息
      | projectType | module |
      | PT01        | KM03   |
    And [整单折扣分配计划分摊13]当前存在如下的[买赠-AT_FP001]整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 10000841    | 1000     | 盒     |
      | 10000456    | 1000     | 盒     |
    And [整单折扣分配计划分摊13]当前存在如下的[买赠-AT_FP001]整单折扣分配计划信息
    And [整单折扣分配计划分摊13]当前存在如下的[买赠-AT_FP001]整单折扣分配计划详细信息
      | originalItemSapCode | originalDistributionChannelCode | giftItemSapCode | giftDistributionChannelCode | allocationQuantity | originalQuantity | giftQuantity | initialBonusQuantity | needShip | invalidStatus | unit | originalDistributionChannelName | giftDistributionChannelName | remark     |
      | 10000841            | 22                              | 10000841        | 22                          | 100                | 2                | 1            | 5                    | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | Detail_001 |
      | 10000456            | 22                              | 10000456        | 22                          | 100                | 2                | 1            | 5                    | 0        | NORMAL        | 盒    |                                 | 分销-非医疗                      | Detail_002 |
    And [整单折扣分配计划分摊13]当前存在如下的[纯赠-AT_FP002]整单折扣申请单信息
      | projectType | module |
      | PT04        | KM03   |
    And [整单折扣分配计划分摊13]当前存在如下的[纯赠-AT_FP002]整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 10000633    | 1000     | 盒     |
    And [整单折扣分配计划分摊13]当前存在如下的[纯赠-AT_FP002]整单折扣分配计划信息
    And [整单折扣分配计划分摊13]当前存在如下的[纯赠-AT_FP002]整单折扣分配计划详细信息
      | originalItemSapCode | giftItemSapCode | originalDistributionChannelCode | giftDistributionChannelCode | allocationQuantity | originalQuantity | giftQuantity | initialBonusQuantity | needShip | invalidStatus | unit | originalDistributionChannelName | giftDistributionChannelName | remark     |
      |                     | 10000633        |                                 | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | Detail_201 |
    And [整单折扣分配计划分摊13]当前存在如下的[零头药-AT_FP003]整单折扣申请单信息
      | projectType | module |
      | PT02        | KM03   |
    And [整单折扣分配计划分摊13]当前存在如下的[零头药-AT_FP003]整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 10000289    | 1000     | 盒     |
    And [整单折扣分配计划分摊13]当前存在如下的[零头药-AT_FP003]整单折扣分配计划信息
    And [整单折扣分配计划分摊13]当前存在如下的[零头药-AT_FP003]整单折扣分配计划详细信息
      | originalItemSapCode | giftItemSapCode | originalDistributionChannelCode | giftDistributionChannelCode | allocationQuantity | originalQuantity | giftQuantity | initialBonusQuantity | needShip | invalidStatus | unit | originalDistributionChannelName | giftDistributionChannelName | lot_no      | production_date | expiration_date | remark     |
      |                     | 10000289        |                                 | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | AT_LTY_0001 | 2024-05-01      | 2025-12-31      | Detail_301 |
      |                     | 10000289        |                                 | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 盒    |                                 | 分销-非医疗                      | AT_LTY_0002 | 2024-05-01      | 2025-12-31      | Detail_302 |
    And [整单折扣分配计划分摊13]当前存在如下的[捐赠-AT_FP004]整单折扣申请单信息
      | projectType | module |
      | PT06        | KM03   |
    And [整单折扣分配计划分摊13]当前存在如下的[捐赠-AT_FP004]整单折扣申请单商品信息
      | itemSapCode | quantity | units |
      | 10000727    | 1000     | 套     |
    And [整单折扣分配计划分摊13]当前存在如下的[捐赠-AT_FP004]整单折扣分配计划信息
    And [整单折扣分配计划分摊13]当前存在如下的[捐赠-AT_FP004]整单折扣分配计划详细信息
      | originalItemSapCode | giftItemSapCode | originalDistributionChannelCode | giftDistributionChannelCode | allocationQuantity | originalQuantity | giftQuantity | initialBonusQuantity | needShip | invalidStatus | unit | originalDistributionChannelName | giftDistributionChannelName | remark     |
      |                     | 10000727        |                                 | 22                          | 100                |                  |              |                      | 1        | NORMAL        | 套    |                                 | 分销-非医疗                      | Detail_401 |
    And [整单折扣分配计划分摊13]添加商品
      | itemSapCode | quantity |
      | 10000841    | 10       |
      | 10000841    | 10       |
      | 10000456    | 5        |
      | 10000456    | 5        |
      | 10000633    | 2        |
      | 10000289    | 20       |
      | 10000353    | 200      |
    Then [整单折扣分配计划分摊13]创建订单应校验通过且提交成功
      | orderWideDiscountSubject | itemSapCode | needShip | unitPrice | preDiscount | calculationValue | boxRatio | quantity | totalInline | preDiscountTotal | orderDiscountTotal | realInline | remark     |
      | PT00                     | 10000841    | 1        | 10        | 3.2         | 6.8              | 1        | 10       | 100         | 32               | 0                  | 68         |            |
      | PT00                     | 10000841    | 1        | 10        | 3.2         | 6.8              | 1        | 10       | 100         | 32               | 0                  | 68         |            |
      | PT01                     | 10000841    | 1        | 10        | 3.2         | 6.8              | 1        | 10       | 100         | 32               | 0                  | 68         | Detail_001 |
      | PT00                     | 10000456    | 1        | 20        | 1           | 19               | 1        | 5        | 100         | 5                | 26.94              | 68.06      |            |
      | PT00                     | 10000456    | 1        | 20        | 1           | 19               | 1        | 5        | 100         | 5                | 26.94              | 68.06      |            |
      | PT01                     | 10000456    | 0        | 20        | 1           | 19               | 1        | 5        | 100         | 5                | 0                  | 95         | Detail_002 |
      | PT00                     | 10000633    | 1        | 50        | 1           | 49               | 1        | 2        | 100         | 2                | 29.94              | 68.06      |            |
      | PT04                     | 10000633    | 1        | 50        | 1           | 49               | 1        | 2        | 100         | 2                | 29.94              | 68.06      | Detail_201 |
      | PT00                     | 10000289    | 1        | 5         | 0           | 4.5              | 1        | 20       | 100         | 0                | 31.94              | 68.06      |            |
      | PT02                     | 10000289    | 1        | 5         | 0           | 4.5              | 1        | 20       | 100         | 0                | 31.94              | 68.06      | Detail_301 |
      | PT02                     | 10000289    | 1        | 5         | 0           | 4.5              | 1        | 20       | 100         | 0                | 31.94              | 68.06      | Detail_302 |
      | PT00                     | 10000353    | 1        | 5         | 0           | 5                | 1        | 200      | 1000        | 0                | 319.44             | 680.56     |            |
      | PT06                     | 10000727    | 1        | 100       | 0           | 100              | 1        | 1        | 100         | 0                | 31.98              | 68.02      | Detail_401 |
    Then [整单折扣分配计划分摊13]订单整单折扣使用记录成功
      | parentsOrderNo | parentOrderItemNo | orderNo | orderItemNo | orderDiscountApplicationPlanId | applicationPlanDetailId | operateType  | operateAmount | remark     |
      |                | 40                |         | 40          |                                |                         | CREATE_ORDER | 26.94         | Detail_002 |
      |                | 50                |         | 50          |                                |                         | CREATE_ORDER | 26.94         | Detail_002 |
      |                | 70                |         | 70          |                                |                         | CREATE_ORDER | 29.94         | Detail_201 |
      |                | 80                |         | 80          |                                |                         | CREATE_ORDER | 29.94         | Detail_201 |
      |                | 90                |         | 90          |                                |                         | CREATE_ORDER | 31.94         | Detail_301 |
      |                | 100               |         | 100         |                                |                         | CREATE_ORDER | 31.94         | Detail_301 |
      |                | 110               |         | 100         |                                |                         | CREATE_ORDER | 31.94         | Detail_301 |
      |                | 130               |         | 120         |                                |                         | CREATE_ORDER | 31.98         | Detail_401 |
      |                | 120               |         | 110         |                                |                         | CREATE_ORDER | 68            | Detail_001 |
      |                | 120               |         | 110         |                                |                         | CREATE_ORDER | 41.12         | Detail_002 |
      |                | 120               |         | 110         |                                |                         | CREATE_ORDER | 38.12         | Detail_201 |
      |                | 120               |         | 110         |                                |                         | CREATE_ORDER | 4.18          | Detail_301 |
      |                | 120               |         | 110         |                                |                         | CREATE_ORDER | 100           | Detail_302 |
      |                | 120               |         | 110         |                                |                         | CREATE_ORDER | 68.02         | Detail_401 |

    Examples: 基础信息
      | saleOrgSapCode | regionCode | customerSapCode | regionName | customerName | shippingRecipient | shippingTelephone | shippingAddress |
      




