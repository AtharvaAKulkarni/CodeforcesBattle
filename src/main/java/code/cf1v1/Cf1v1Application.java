package code.cf1v1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class Cf1v1Application {

	public static void main(String[] args) {
		SpringApplication.run(Cf1v1Application.class, args);
	}
    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
