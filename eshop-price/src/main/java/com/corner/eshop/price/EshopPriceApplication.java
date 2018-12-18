package com.corner.eshop.price;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * 价格信息改变要mysql redis双写一致，因为对实时性有要求
 */
@SpringCloudApplication
@MapperScan("com.corner.eshop.price.mapper")
public class EshopPriceApplication {

	/**
	 * The entry point of application.
	 *
	 * @param args the input arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(EshopPriceApplication.class, args);
	}
}