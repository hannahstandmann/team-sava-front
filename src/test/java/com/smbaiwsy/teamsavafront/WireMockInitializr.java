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
package com.smbaiwsy.teamsavafront;

import com.github.tomakehurst.wiremock.WireMockServer;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextClosedEvent;

/**
 *
 * @author anamattuzzi-stojanovic
 */
public class WireMockInitializr implements ApplicationContextInitializer<ConfigurableApplicationContext> {
 
  @Override
  public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
    WireMockServer wireMockServer = new WireMockServer(options().port(10112));
    wireMockServer.start();
 
    configurableApplicationContext.getBeanFactory().registerSingleton("wireMockServer", wireMockServer);
 
    configurableApplicationContext.addApplicationListener(applicationEvent -> {
      if (applicationEvent instanceof ContextClosedEvent) {
        wireMockServer.stop();
      }
    });
 
    TestPropertyValues
      .of("api_url:http://localhost:10768/api")
      .applyTo(configurableApplicationContext);
  }
}