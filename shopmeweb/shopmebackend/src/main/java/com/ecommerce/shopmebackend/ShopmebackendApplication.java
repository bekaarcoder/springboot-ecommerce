package com.ecommerce.shopmebackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan({"com.ecommerce.shopmecommon.entity", "com.ecommerce.shopmebackend.admin.user"})
public class ShopmebackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopmebackendApplication.class, args);
    }

}
