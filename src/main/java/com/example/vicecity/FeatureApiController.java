package com.example.vicecity;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/** JSON API, handy for smoke-testing a deployment from the pipeline. */
@RestController
public class FeatureApiController {

  private final FeatureService featureService;

  public FeatureApiController(FeatureService featureService) {
    this.featureService = featureService;
  }

  @GetMapping("/api/features")
  public List<Feature> features() {
    return featureService.findAll();
  }
}
