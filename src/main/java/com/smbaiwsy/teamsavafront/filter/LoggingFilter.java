/*
 * Copyright 2020 the original author.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
