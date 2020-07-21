/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.smbaiwsy.teamsavafront;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.servlet.mvc.ServletWrappingController;

/**
 *
 * @author anamattuzzi-stojanovic
 */
@WebMvcTest(value = ServletWrappingController.class)
public class APIGatewayTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;
    

   // @Test
    public void testGetUser() throws Exception {
        WireMockServer wireMockServer = new WireMockServer(wireMockConfig().port(10112)); //No-args constructor will start on port 8080, no HTTPS
        wireMockServer.start();
        stubFor(get(urlPathMatching("/api/users/1"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"id\":\"1\",\"firstName\":\"Ana\",\"surname\":\"Mattuzzi\",\"birthday\":\"1974-07-28\"}")));

        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(
                "/api/users/1").accept(
                        MediaType.APPLICATION_JSON);

       mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.code").value("NOT_FOUND"))
                .andExpect(jsonPath("$.message").value("Record not found"));
    }

}
