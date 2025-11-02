package me.manishcodes.hotelapplication.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HealthController.class)
class HealthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testHealthEndpointReturnsCorrectStatusMessage() throws Exception {
        // Given: The /health endpoint is available
        // When: A GET request is made to /health
        // Then: The response should have HTTP 200 status and the correct message
        mockMvc.perform(get("/health"))
                .andExpect(status().isOk())
                .andExpect(content().string("The health of application 'Hotel project' is ok and working fine"));
    }

    @Test
    void testHealthEndpointReturnsString() throws Exception {
        // Given: The /health endpoint is available
        // When: A GET request is made to /health
        // Then: The response content type should be text/plain (default for String return)
        mockMvc.perform(get("/health"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("text/plain"));
    }
}
