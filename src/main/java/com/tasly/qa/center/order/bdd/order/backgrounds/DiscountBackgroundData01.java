package com.anturo.qa.center.order.bdd.order.backgrounds;


import com.anturo.qa.center.order.bdd.order.batchs.OrderBatchClearData01;
import com.anturo.qa.center.order.bdd.order.batchs.OrderBatchInsertData01;
import com.anturo.qa.center.order.bdd.order.batchs.PriceBatchClearData01;
import com.anturo.qa.center.order.main.support.PersistanceOperations;
import com.panzer.tool.sql.MySqlTools;
import io.cucumber.java8.En;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import net.thucydides.core.annotations.Steps;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author Tesla
 * @Date 2024/05/20
 * @Description 预折初始化数据
 */

@SuppressWarnings("all")
@Data
@FieldDefaults(level = AccessLevel.PROTECTED)
@Slf4j
public class DiscountBackgroundData01 implements En {

	@Steps(actor = "QA", shared = true)
	PriceBatchClearData01 priceBatchClearData01;

	@Steps(actor = "QA", shared = true)
	OrderBatchClearData01 orderBatchClearData01;

	@Steps(actor = "QA", shared = true)
	OrderBatchInsertData01 orderBatchInsertData01;

	private MySqlTools priceSystemPersist = PersistanceOperations.mysql("price-center");

	public DiscountBackgroundData01() {
		this.background01();
	}

	private void background01() {
		Given("[整单折扣基础数据01]清除价格\\预折\\整单折扣的测试数据", () -> {
			priceBatchClearData01.clearPirceData01("at");
			orderBatchClearData01.clearOrderPreDiscountData01("at");
			orderBatchClearData01.clearDiscountData01("at");
		});
		When("[整单折扣基础数据01]商品价格测试数据初始化", () -> {
			initTestDataForBackground01();
		});
	}

	private void initTestDataForBackground01() throws Exception {
		String SQL = URLDecoder.decode(ClassLoader.getSystemResource("scripts/order/discount/价格中心数据初始化.sql").getPath(), "UTF-8");
		List<String> PRICE_SQLS = Arrays.stream(FileUtils.readFileToString(new File(SQL), Charset.defaultCharset()).trim().split(";"))
				.map(sql -> sql.trim()).collect(Collectors.toList());
		PRICE_SQLS.forEach(sql -> {
			priceSystemPersist.sqlAlter(sql);
		});
		log.info("[价格中心]初始化自动化测试数据成功");
	}
}
