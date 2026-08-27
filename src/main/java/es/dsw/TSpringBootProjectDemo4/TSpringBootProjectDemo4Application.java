package es.dsw.TSpringBootProjectDemo4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "es.dsw")
public class TSpringBootProjectDemo4Application {

	public static void main(String[] args) {
		SpringApplication.run(TSpringBootProjectDemo4Application.class, args);
	}

}
