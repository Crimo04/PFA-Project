package ma.karim.rhservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class RhServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RhServiceApplication.class, args);
    }

}




