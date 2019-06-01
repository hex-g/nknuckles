package hive.nknuckles.security;

import hive.pandora.security.JwtConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityBeans {
  @Bean
  public JwtConfig jwtConfig() {
    return new JwtConfig();
  }
}
