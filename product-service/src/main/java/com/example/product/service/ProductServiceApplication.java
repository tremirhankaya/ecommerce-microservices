package com.example.product.service;

import com.example.product.service.service.ProductService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ProductServiceApplication {
	private ProductService productService;

	public static void main(String[] args) {
		SpringApplication.run(ProductServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner seedData(ProductService productService) {
		return args -> {
			//Testler
			//Product product = new Product("Playstation","Gaming Console",BigDecimal.valueOf(499.99),100);
//			productService.updateProduct(3L,product);
//			productService.findById(3L);
//			productService.deleteProduct(4L);
//			List<Product> findAll = productService.findAll();
//			for (Product product : findAll) {
//				System.out.println(product.toString());
//			}




		};
	}}