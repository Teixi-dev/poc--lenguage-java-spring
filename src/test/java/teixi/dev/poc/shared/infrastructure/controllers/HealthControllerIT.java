package teixi.dev.poc.shared.infrastructure.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@WebMvcTest(HealthController.class)
public class HealthControllerIT {
    private static final String URL_TEMPLATE = "/health";
    private static final String EXPECTED_CONTENT = "OK";

    @Autowired
    private MockMvc mockMvc;

    @Test
    void whenCallHealthReturn200AndOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(URL_TEMPLATE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(EXPECTED_CONTENT));
    }
}
