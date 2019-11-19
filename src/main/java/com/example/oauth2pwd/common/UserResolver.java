package com.example.oauth2pwd.common;

import com.example.oauth2pwd.common.auth.MyUserDetails;
import com.example.oauth2pwd.model.User;
import java.security.Principal;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class UserResolver implements HandlerMethodArgumentResolver {

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    return User.class.isAssignableFrom(parameter.getParameterType());
  }

  @Override
  public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
      NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
    Principal principal = webRequest.getUserPrincipal();
    if (principal instanceof Authentication) {
      return ((MyUserDetails) ((Authentication) principal).getPrincipal()).getUser();
    } else {
      throw new UnsupportedOperationException(
          "can't not resolving method parameters into argument values");
    }
  }
}
