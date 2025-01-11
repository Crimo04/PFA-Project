package ma.karim.techleadms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class TechLeadMsApplication {

    public static void main(String[] args) {
        SpringApplication.run(TechLeadMsApplication.class, args);
    }

}
