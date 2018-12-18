package com.corner.eshop.inventory;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * 库存信息改变要mysql redis双写一致，因为对实时性有要求
 */
@SpringCloudApplication
@MapperScan("com.corner.eshop.inventory.mapper")
public class EshopInventoryApplication {

	/**
	 * The entry point of application.
	 *
	 * @param args the input arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(EshopInventoryApplication.class, args);
	}
}