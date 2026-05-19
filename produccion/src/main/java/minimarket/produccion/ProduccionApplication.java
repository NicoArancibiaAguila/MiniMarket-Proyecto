package minimarket.produccion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
@EnableFeignClients
@SpringBootApplication
public class ProduccionApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProduccionApplication.class, args);
	}

}
