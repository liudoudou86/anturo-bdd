package com.anturo.qa.center.order.bdd.order.backgrounds;

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
 * @Date 2024/07/31
 * @Description
 */

@SuppressWarnings("all")
@Data
@FieldDefaults(level = AccessLevel.PROTECTED)
@Slf4j
public class ProductBackgroundData02 implements En {

	@Steps(actor = "QA", shared = true)
	PriceBatchClearData01 priceBatchClearData01;

	private MySqlTools itemSystemPersist = PersistanceOperations.mysql("item-center");

	public ProductBackgroundData02() {
		this.background01();
	}

	private void background01() {
		Given("[大区预折商品基础数据01]清除测试数据", () -> {
			priceBatchClearData01.clearPirceData01("at");
			priceBatchClearData01.clearProductData01("at");
		});
		When("[大区预折商品基础数据01]商品中心导入测试数据", () -> {
			String SQL_PATH = URLDecoder.decode(ClassLoader.getSystemResource("scripts/order/price/商品中心数据初始化.sql").getPath(), "UTF-8");
			List<String> SQLS = Arrays.stream(FileUtils.readFileToString(new File(SQL_PATH), Charset.defaultCharset()).trim().split(";"))
					.map(sql -> sql.trim()).collect(Collectors.toList());
			SQLS.forEach(sql -> {
				itemSystemPersist.sqlAlter(sql);
			});
			log.info("初始化商品中心自动化测试数据成功");
		});
	}
}
