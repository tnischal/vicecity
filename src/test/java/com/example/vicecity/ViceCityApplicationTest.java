package com.example.vicecity;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

/** Full-context smoke test: the app boots, serves the page and reports healthy. */
@SpringBootTest
@AutoConfigureMockMvc
class ViceCityApplicationTest {

  @Autowired private MockMvc mvc;

  @Test
  void contextLoadsAndServesHomePage() throws Exception {
    mvc.perform(get("/"))
        .andExpect(status().isOk())
        .andExpect(content().string(org.hamcrest.Matchers.containsString("build")));
  }

  @Test
  void healthEndpointIsUp() throws Exception {
    mvc.perform(get("/actuator/health")).andExpect(status().isOk()).andExpect(jsonPath("$.status").value("UP"));
  }

  @Test
  void staticAssetsAreServed() throws Exception {
    mvc.perform(get("/css/style.css")).andExpect(status().isOk());
    mvc.perform(get("/js/app.js")).andExpect(status().isOk());
  }
}
