package com.example.vicecity;

import java.util.List;
import org.springframework.stereotype.Service;

/** Provides the highlight cards displayed on the site. */
@Service
public class FeatureService {

  private static final List<Feature> FEATURES =
      List.of(
          new Feature("🌴", "Neon Coastline", "Sun-soaked beaches that glow pink after dark."),
          new Feature("🏎️", "Street Racing", "Tear down the boulevard in fast, flashy rides."),
          new Feature("🌃", "Night Life", "Clubs, rooftops and synth-heavy after-hours."),
          new Feature("💼", "Big Heists", "Plan the score, grab the cash, vanish into the traffic."),
          new Feature("📻", "Radio Waves", "Retro stations with a soundtrack for every mood."),
          new Feature("🌊", "Open World", "Swamps, islands and highways waiting to be explored."));

  /**
   * Returns every highlight card.
   *
   * @return an immutable list of features
   */
  public List<Feature> findAll() {
    return FEATURES;
  }
}
