package hive.nknuckles;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@EnableZuulProxy
@EnableEurekaClient
public class NKnucklesApplication {

  public static void main(String[] args) {
    SpringApplication.run(NKnucklesApplication.class, args);
  }

}
