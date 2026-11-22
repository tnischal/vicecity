package com.example.vicecity;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest({HomeController.class, FeatureApiController.class})
@Import(FeatureService.class)
class HomeControllerTest {

  @Autowired private MockMvc mvc;

  @Test
  void homePageRendersThemedView() throws Exception {
    mvc.perform(get("/"))
        .andExpect(status().isOk())
        .andExpect(view().name("index"))
        .andExpect(model().attributeExists("features", "version"))
        .andExpect(content().string(org.hamcrest.Matchers.containsString("VICE CITY NIGHTS")))
        .andExpect(content().string(org.hamcrest.Matchers.containsString("Neon Coastline")));
  }

  @Test
  void apiReturnsFeatureList() throws Exception {
    mvc.perform(get("/api/features"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(6))
        .andExpect(jsonPath("$[0].title").value("Neon Coastline"));
  }
}
