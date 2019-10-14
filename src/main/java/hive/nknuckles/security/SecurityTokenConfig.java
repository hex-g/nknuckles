package hive.nknuckles.security;

import hive.pandora.security.JwtConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

@EnableWebSecurity
public class SecurityTokenConfig extends WebSecurityConfigurerAdapter {
  private final JwtConfig jwtConfig;

  @Autowired
  public SecurityTokenConfig(final JwtConfig jwtConfig) {
    this.jwtConfig = jwtConfig;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .cors()
        .and()
        .csrf()
        .disable()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .exceptionHandling()
        .authenticationEntryPoint((req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED))
        .and()
        .addFilterAfter(
            new JwtTokenAuthenticationFilter(jwtConfig),
            UsernamePasswordAuthenticationFilter.class
        )
        .authorizeRequests()
        // Swagger
        .antMatchers("/swagger-ui.html")
        .permitAll()
        .antMatchers("/*/v2/api-docs")
        .permitAll()
        // Token API
        .antMatchers(HttpMethod.POST, "/caronte" + jwtConfig.getUri())
        .permitAll()
        .antMatchers("/caronte/log")
        .hasRole("ADMIN")
        // API routes
        .antMatchers("/pokedex/user/**")
        .hasRole("ADMIN")
        .antMatchers("/pokedex/student/**")
        .hasRole("ADMIN")
        .antMatchers("/pokedex/pedagogue/**")
        .hasRole("ADMIN")
        .antMatchers("/pokedex/person/**")
        .authenticated()
        .antMatchers("/kirby/**")
        .authenticated()
        .antMatchers("/mugshot/**")
        .authenticated()
        .antMatchers("/tamagotchi/**")
        .authenticated()
        .antMatchers("/player/**")
        .authenticated();
  }

  // TODO change this to use properties
  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    final var config = new CorsConfiguration();
    config.setAllowedOrigins(Arrays.asList("*"));
    config.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE"));
    config.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization"));
    config.setExposedHeaders(Arrays.asList("Content-Type", "Authorization"));
    final var source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", config);
    return source;
  }
}
