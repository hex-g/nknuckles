package hive.nknuckles.security;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import hive.pandora.constant.HiveInternalHeaders;
import org.springframework.core.Ordered;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticatedUserHeaderZuulFilter extends ZuulFilter {
  @Override
  public String filterType() {
    return "pre";
  }

  @Override
  public int filterOrder() {
    return Ordered.LOWEST_PRECEDENCE - 100;
  }

  @Override
  public boolean shouldFilter() {
    return true;
  }

  @Override
  public Object run() {
    RequestContext.getCurrentContext().addZuulRequestHeader(
        HiveInternalHeaders.AUTHENTICATED_USER_ID,
        SecurityContextHolder.getContext().getAuthentication().getName()
    );
    return null;
  }
}
