package hive.nknuckles.security;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import hive.nknuckles.repository.UserRepository;
import hive.pandora.constant.HiveInternalHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.Ordered;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class AuthenticatedUserHeaderZuulFilter extends ZuulFilter {
  private final UserRepository userRepository;

  @Autowired
  public AuthenticatedUserHeaderZuulFilter(UserRepository userRepository) {
    this.userRepository = userRepository;

    // hard coded users from caronte
    userRepository.save(new hive.ishigami.entity.user.User("admin", "", 0, 0));
    userRepository.save(new hive.ishigami.entity.user.User("ped", "", 0, 0));
    userRepository.save(new hive.ishigami.entity.user.User("stu", "", 0, 0));
  }

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
    var user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());

    RequestContext.getCurrentContext().addZuulRequestHeader(
        HiveInternalHeaders.AUTHENTICATED_USER_ID,
        user == null ? "-1" : user.getId().toString()
    );
    return null;
  }
}
