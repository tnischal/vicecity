package com.example.vicecity;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.info.BuildProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/** Serves the themed landing page. */
@Controller
public class HomeController {

  private final FeatureService featureService;
  private final String version;

  public HomeController(FeatureService featureService, ObjectProvider<BuildProperties> build) {
    this.featureService = featureService;
    this.version = build.stream().map(BuildProperties::getVersion).findFirst().orElse("dev");
  }

  @GetMapping("/")
  public String home(Model model) {
    model.addAttribute("features", featureService.findAll());
    model.addAttribute("version", version);
    return "index";
  }
}
