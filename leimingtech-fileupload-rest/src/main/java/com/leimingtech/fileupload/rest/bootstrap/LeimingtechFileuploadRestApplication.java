package com.leimingtech.fileupload.rest.bootstrap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@SpringBootApplication(scanBasePackages = {"com.leimingtech.fileupload.rest"})
public class LeimingtechFileuploadRestApplication {

	public static void main(String[] args) {
		SpringApplication.run(LeimingtechFileuploadRestApplication.class, args);
	}
}
