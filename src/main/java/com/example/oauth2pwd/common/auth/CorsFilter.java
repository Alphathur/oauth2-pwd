package com.example.oauth2pwd.common.auth;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter implements Filter {

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
      FilterChain filterChain) throws IOException, ServletException {
    HttpServletResponse response = ((HttpServletResponse) servletResponse);
    HttpServletRequest request = (HttpServletRequest) servletRequest;

    //支持跨域
    response.setHeader("Access-Control-Allow-Origin", "*");

    //跨域请求允许设置cookie
    response.setHeader("Access-Control-Allow-Credentials", "true");

    //设置跨域请求的类型
    response.setHeader("Access-Control-Allow-Methods",
        "ACL, CANCELUPLOAD, CHECKIN, CHECKOUT, COPY, DELETE, GET, HEAD, LOCK, MKCALENDAR, MKCOL, MOVE, OPTIONS, POST, PROPFIND, PROPPATCH, PUT, REPORT, SEARCH, UNCHECKOUT, UNLOCK, UPDATE, VERSION-CONTROL");
    response.setHeader("Access-Control-Max-Age", "3600");
    response.setHeader("Access-Control-Allow-Headers",
        "Origin, X-Requested-With, Content-Type, Accept, Key, Authorization");
    if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
      response.setStatus(HttpServletResponse.SC_OK);
    } else {
      filterChain.doFilter(servletRequest, servletResponse);
    }
  }

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    // not needed
  }

  @Override
  public void destroy() {
    // not needed
  }
}
