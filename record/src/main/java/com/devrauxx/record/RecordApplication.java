package com.devrauxx.record;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication(scanBasePackages="com.devrauxx.record")
@PropertySource(value="classpath:configuration.properties")
public class RecordApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecordApplication.class, args);
	}

}
