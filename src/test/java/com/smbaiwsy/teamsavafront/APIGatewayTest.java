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
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.containing;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.equalToJson;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 *
 * @author anamattuzzi-stojanovic
 */
@SpringBootTest
@AutoConfigureWebMvc
@ContextConfiguration(initializers = {WireMockInitializr.class}, classes = {TeamSavaFrontApplication.class})
public class APIGatewayTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Autowired
    private WireMockServer wireMockServer;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @AfterEach
    public void afterEach() {
        this.wireMockServer.resetAll();
    }

    @Test
    public void testGetUser() throws Exception {

        this.wireMockServer.stubFor(get(urlPathMatching("/api/users/1"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"id\":\"1\",\"firstName\":\"Ana\",\"surname\":\"Mattuzzi\",\"birthday\":\"1974-07-28\"}")));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/api/users/1").accept(
                        MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.firstName").value("Ana"));

    }

    @Test
    public void testGetUsers() throws Exception {

        this.wireMockServer.stubFor(get(urlPathMatching("/api/users"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("[{\"id\":\"1\",\"firstName\":\"Ana\",\"surname\":\"Mattuzzi\",\"birthday\":\"1974-07-28\"},"
                                + "{\"id\":\"2\",\"firstName\":\"Miloš\",\"surname\":\"Mirković\",\"birthday\":\"1977-10-23\"}]")));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/api/users").accept(
                        MediaType.APPLICATION_JSON);

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.[0].id").value("1"))
                .andExpect(jsonPath("$.[0].firstName").value("Ana"))
                .andExpect(jsonPath("$.[0].surname").value("Mattuzzi"))
                .andExpect(jsonPath("$.[0].birthday").value("1974-07-28"))
                .andExpect(jsonPath("$.[1].id").value("2"))
                .andExpect(jsonPath("$.[1].firstName").value("Miloš"))
                .andExpect(jsonPath("$.[1].surname").value("Mirković"))
                .andExpect(jsonPath("$.[1].birthday").value("1977-10-23"));

    }

    @Test
    public void testPostUser() throws Exception {

        this.wireMockServer.stubFor(post(urlPathMatching("/api/users"))
                .withHeader("Content-Type", equalTo("application/json"))
	        .withRequestBody(equalToJson("{\"firstName\":\"Ana\",\"surname\":\"Mattuzzi\",\"birthday\":\"1974-07-28\"}"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"id\":\"1\",\"firstName\":\"Ana\",\"surname\":\"Mattuzzi\",\"birthday\":\"1974-07-28\"}")));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(
                "/api/users")
                .accept(
                        MediaType.APPLICATION_JSON)
                .contentType(
                        MediaType.APPLICATION_JSON)
                .content("{\"firstName\":\"Ana\",\"surname\":\"Mattuzzi\",\"birthday\":\"1974-07-28\"}");

        mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.firstName").value("Ana"));

    }
}
