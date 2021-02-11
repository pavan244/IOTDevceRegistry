package com.iotdevices.registry;

import java.io.File;
import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.util.ResourceUtils;





@SpringBootApplication
@ComponentScan("com.iotdevices.registry")
public class RegistryApplication {

	public static void main(String[] args) throws IOException {
		File file = ResourceUtils.getFile("classpath:Simulator.exe");
		System.out.println(file.getAbsolutePath());
		Process process = new ProcessBuilder(file.getAbsolutePath()).start();
		SpringApplication.run(RegistryApplication.class, args);
		process.destroy();
		
		
	}

}
