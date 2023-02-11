package com.example.pokemon;

import com.example.power.Power;
import com.example.power.PowerService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.*;
class PokemonServiceTest {

  PokemonService pokemonService;
  PokemonCreationForm pokemonCreationForm;
  PokemonService mockedService;
  PokemonRepository pokemonRepository;

  PowerService powerService;

  @BeforeEach
  void setUp() {
    pokemonRepository = Mockito.mock(PokemonRepository.class);
    powerService = Mockito.mock(PowerService.class);
    pokemonService = new PokemonService(pokemonRepository, powerService);
  }

  @Test
  public void testAddPokemon() {
    // given
    pokemonCreationForm = new PokemonCreationForm("sagar", 2, "sagarurl");

   //when
    pokemonService.addPokemon(pokemonCreationForm);
  //then
    Mockito.verify(pokemonRepository).findByName(pokemonCreationForm.getName());
    Mockito.verify(powerService).getById(pokemonCreationForm.getPower());
    Mockito.verify(pokemonRepository).save(any());
  }

  @Test
  public void shouldReturnListOfPokemons() {}
}
