package com.anturo.qa.center.order.bdd.order.batchs;

import com.anturo.qa.center.order.main.model.dto.ItMediumPackagingSpecificationDTO;
import com.anturo.qa.center.order.main.support.PersistanceOperations;
import com.panzer.tool.sql.MySqlTools;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import net.thucydides.core.annotations.Step;

/**
 * @Author guoqing
 * @Date 2024/10/09
 * @Description
 */

@SuppressWarnings("all")
@Getter
@Setter
@Log4j2(topic = "DefaultLogger")
public class ItemBatchInsertData01 {

	private MySqlTools itemSystemPersist = PersistanceOperations.mysql("order-center");

	@Step(value = "【INSERT】插入中包装测试数据")
	public void insertItMediumPackagingSpecificationData01(ItMediumPackagingSpecificationDTO sqlDTO) {
		final String SQL = String.format("INSERT INTO `anturo-center-item`.it_medium_packaging_specification \n" +
						" (product_code, medium_packaging_specification, name, quantity,  \n" +
						"extension, tenant_id, instance_id, create_time, update_time,  \n" +
						"create_person_name, create_person, update_person_name, update_person, dr) \n" +
						"VALUES ('%s', '%s', '%s', '%s', \n" +
						"null, null, null, null, null, \n" +
						"'%s', '%s', null, null, 0);", sqlDTO.getProductCode(), sqlDTO.getMediumPackagingSpecification(), sqlDTO.getName(),
				sqlDTO.getQuantity(), sqlDTO.getCreatePerson(), sqlDTO.getCreateTime()
		);
		int sqlResult = itemSystemPersist.insert(SQL);
		log.info("[商品中心] 插入中包装测试数据成功");
	}

}
