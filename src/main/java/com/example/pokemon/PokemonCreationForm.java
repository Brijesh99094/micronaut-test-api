package com.example.pokemon;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = false)
public class PokemonCreationForm {

  private final String name;
  private final Integer power;

  private final String imgUrl;

  @JsonCreator
  public PokemonCreationForm(
      @JsonProperty("name") String name,
      @JsonProperty("power") Integer power,
      @JsonProperty("imgUrl") String imgUrl) {
    this.name = name;
    this.power = power;
    this.imgUrl = imgUrl;
  }

  public String getName() {
    return name;
  }

  public Integer getPower() {
    return power;
  }

    public String getImgUrl() {
        return imgUrl;
    }
}
