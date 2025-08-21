package com.anturo.qa.center.order.main.model.pojo;

import lombok.*;
import lombok.experimental.FieldDefaults;

/**
 * @Author Tesla
 * @Date 2024/03/08
 * @Description
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Domain {
	String url;
	String protocal;
	String host;
	String port;
	String session;
	String token;
}
