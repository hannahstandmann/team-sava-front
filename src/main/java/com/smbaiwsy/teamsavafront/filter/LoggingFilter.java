/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smbaiwsy.teamsavafront.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author anamattuzzi-stojanovic
 */
@Slf4j
public class LoggingFilter extends ZuulFilter {


   @Override
   public String filterType() {
       return "pre";
   }

   @Override
   public int filterOrder() {
       return 1;
   }

   @Override
   public boolean shouldFilter() {
       return true;
   }

   @Override
   public Object run() {
       RequestContext ctx = RequestContext.getCurrentContext();
       HttpServletRequest request = ctx.getRequest();

       log.info(String.format("%s request to %s", request.getMethod(), request.getRequestURL().toString()));

       return null;
   }   
}
