package com.corner.eshop.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * 商品信息改变要写到mq中，因为对实时性要求不高
 */
@SpringCloudApplication
@MapperScan("com.corner.eshop.product.mapper")
public class EshopProductApplication {

	/**
	 * The entry point of application.
	 *
	 * @param args the input arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(EshopProductApplication.class, args);
	}
}