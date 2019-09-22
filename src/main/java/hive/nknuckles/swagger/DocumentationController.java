package hive.nknuckles.swagger;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;

@Component
@Primary
@EnableAutoConfiguration
public class DocumentationController implements SwaggerResourcesProvider {

  @Override
  public List get() {
    List resources = new ArrayList<>();
    resources.add(swaggerResource("token", "/caronte/v2/api-docs", "2.0"));
    resources.add(swaggerResource("mugshot-service", "/mugshot/v2/api-docs", "2.0"));
    resources.add(swaggerResource("kirby-service", "/kirby/v2/api-docs", "2.0"));
    resources.add(swaggerResource("player-service", "/player/v2/api-docs", "2.0"));
    resources.add(swaggerResource("pokedex-service", "/pokedex/v2/api-docs", "2.0"));
    resources.add(swaggerResource("tamagochi-service", "/tamagochi/v2/api-docs", "2.0"));
    return resources;
  }

  private SwaggerResource swaggerResource(String name, String location, String version) {
    SwaggerResource swaggerResource = new SwaggerResource();
    swaggerResource.setName(name);
    swaggerResource.setLocation(location);
    swaggerResource.setSwaggerVersion(version);
    return swaggerResource;
  }

}