package com.anturo.qa.center.order.bdd.order.batchs;

import com.anturo.qa.center.order.main.support.PersistanceOperations;
import com.google.common.collect.ImmutableSet;
import com.panzer.tool.sql.MySqlTools;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import net.thucydides.core.annotations.Step;

import java.util.Set;

/**
 * @Author Tesla
 * @Date 2024/05/20
 * @Description 订单中心清除数据
 */

@SuppressWarnings("all")
@Getter
@Setter
@Log4j2(topic = "DefaultLogger")
public class OrderBatchClearData01 {

	private MySqlTools orderSystemPersist = PersistanceOperations.mysql("order-center");

	@Step(value = "【CLEAR】清除预折测试数据")
	public void clearOrderPreDiscountData01(String createPerson) {
		Set<String> SQL = ImmutableSet.of(
				String.format("delete from `anturo-center-order`.order_pre_discount where CREATE_PERSON = '%s';", createPerson),
				String.format("delete from `anturo-center-order`.order_pre_discount_detail where CREATE_PERSON = '%s';", createPerson)
		);
		SQL.stream().forEach(
				sql -> orderSystemPersist.delete(sql)
		);
		log.info("[订单中心] 清除预折测试数据成功");
	}

	@Step(value = "【CLEAR】清除整单折扣测试数据")
	public void clearDiscountData01(String createPerson) {
		Set<String> SQL = ImmutableSet.of(
				String.format("delete from `anturo-center-order`.order_discount_application_form where CREATE_PERSON = '%s';", createPerson),
				String.format("delete from `anturo-center-order`.order_discount_application_form_detail where CREATE_PERSON = '%s';", createPerson),
				String.format("delete from `anturo-center-order`.order_discount_allocation_plan where CREATE_PERSON = '%s';", createPerson),
				String.format("delete from `anturo-center-order`.order_discount_allocation_plan_detail where CREATE_PERSON = '%s';", createPerson)
		);
		SQL.stream().forEach(
				sql -> orderSystemPersist.delete(sql)
		);
		log.info("[订单中心] 清除整单折扣测试数据成功");
	}

	@Step(value = "【CLEAR】清除订单测试数据")
	public void clearOrderData01(String orderNo) {
		Set<String> SQL = ImmutableSet.of(
				String.format("delete from `anturo-center-order`.order_purchase_order where ORDER_NO = '%s';", orderNo),
				String.format("delete from `anturo-center-order`.order_purchase_order_item where ORDER_NO = '%s';", orderNo)
		);
		SQL.stream().forEach(
				sql -> orderSystemPersist.delete(sql)
		);
		log.info("[订单中心] 清除订单测试数据成功");
	}

	@Step(value = "【CLEAR】清除限销测试数据")
	public void clearPermittedSalesData01(String createPerson) {
		Set<String> SQL = ImmutableSet.of(
				String.format("delete from `anturo-center-order`.order_sale_org_controlled_product where CREATE_PERSON = '%s';", createPerson),
				String.format("delete from `anturo-center-order`.order_permitted_sales_region where CREATE_PERSON = '%s';", createPerson),
				String.format("delete from `anturo-center-order`.order_permitted_sales_customer where CREATE_PERSON = '%s';", createPerson)
		);
		SQL.stream().forEach(
				sql -> orderSystemPersist.delete(sql)
		);
		log.info("[订单中心] 清除限销测试数据成功");
	}

	@Step(value = "【CLEAR】清除交货单测试数据")
	public void clearDeliveryData01(String orderNo) {
		Set<String> SQL = ImmutableSet.of(
				String.format("delete from `anturo-center-order`.order_purchase_order where ORDER_NO = '%s';", orderNo),
				String.format("delete from `anturo-center-order`.order_purchase_order_item where ORDER_NO = '%s';", orderNo),
				String.format("delete from `anturo-center-order`.order_fulfillment_shipment where ORDER_NO = '%s';", orderNo),
				String.format("delete from `anturo-center-order`.order_fulfillment_shipment_detail where ORDER_NO = '%s';", orderNo),
				String.format("delete from `anturo-center-order`.order_fulfillment_shipment_whse_post_receipt where order_id = '%s';", orderNo)
		);
		SQL.stream().forEach(
				sql -> orderSystemPersist.delete(sql)
		);
		log.info("[订单中心] 清除交货单测试数据成功");
	}

}
