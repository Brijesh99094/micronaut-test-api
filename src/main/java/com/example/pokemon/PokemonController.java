package com.example.pokemon;

import io.micronaut.http.annotation.*;

import java.util.List;

@Controller("/pokemon")
public class PokemonController {

  private final PokemonService pokemonService;

  public PokemonController(PokemonService pokemonService) {
    this.pokemonService = pokemonService;
  }

  @Get
  public List<Pokemon> test() {
    return pokemonService.get();
  }

  @Post
  public Pokemon create(@Body Pokemon pokemon) {
    return pokemonService.addPokemon(pokemon);
  }

  @Get(value = "/{id}")
  public Pokemon getPokemonById(@QueryValue Integer id) {
    return pokemonService.getPokemonById(id);
  }

  @Put(value = "/{id}")
  public Pokemon updatePokemon(@Body Pokemon pokemon) {
    return pokemonService.updatePokemon(pokemon);
  }

  @Delete(value = "/{id}")
  public Pokemon deletePokemon(@QueryValue Integer id) {
    return pokemonService.deletePokemon(id);
  }
}
