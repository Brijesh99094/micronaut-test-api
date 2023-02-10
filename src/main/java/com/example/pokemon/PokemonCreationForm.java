package com.example.pokemon;


import com.example.power.PowerRepository;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = false)
public class PokemonCreationForm  {

    private final String  name;
    private final Integer  power;

    @JsonCreator
    public PokemonCreationForm(@JsonProperty("name") String name,@JsonProperty("power") Integer power) {
        this.name = name;
        this.power = power;
    }
    public String getName() {
        return name;
    }
    public Integer getPower() {
        return power;
    }
}
