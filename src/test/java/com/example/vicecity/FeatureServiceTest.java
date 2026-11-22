package com.example.vicecity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.Test;

class FeatureServiceTest {

  private final FeatureService service = new FeatureService();

  @Test
  void returnsSixFeatures() {
    assertThat(service.findAll()).hasSize(6);
  }

  @Test
  void everyFeatureIsFullyPopulated() {
    assertThat(service.findAll())
        .allSatisfy(
            f -> {
              assertThat(f.icon()).isNotBlank();
              assertThat(f.title()).isNotBlank();
              assertThat(f.description()).isNotBlank();
            });
  }

  @Test
  void listIsImmutable() {
    List<Feature> features = service.findAll();
    Feature extra = new Feature("x", "y", "z");
    assertThatThrownBy(() -> features.add(extra))
        .isInstanceOf(UnsupportedOperationException.class);
  }
}
