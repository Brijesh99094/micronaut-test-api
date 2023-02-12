package com.example.pokemon;

import com.example.Exceptions.EntityAlreadyExistException;
import com.example.power.Power;
import com.example.power.PowerService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

class PokemonServiceTest {

  PokemonService pokemonService;
  PokemonCreationForm pokemonCreationForm;
  PokemonService mockedService;
  PokemonRepository pokemonRepository;
  PowerService powerService;

  Pokemon bulbasaur, pikachu;

  @BeforeEach
  void setUp() {
    bulbasaur = new Pokemon(1, "bulbasaur", new Power(1, "grass"), "bulbasaur.png");
    pikachu = new Pokemon(2, "pikachu", new Power(2, "thunder"), "pikachu.png");
    pokemonRepository = Mockito.mock(PokemonRepository.class);
    powerService = Mockito.mock(PowerService.class);
    pokemonService = new PokemonService(pokemonRepository, powerService);
  }

  @Test
  public void shouldAddPokemon() {
    // given
    pokemonCreationForm = new PokemonCreationForm("sagar", 2, "sagarurl");
    Power expectedPower = new Power(2, "grass");
    Pokemon expectedPokemon = new Pokemon(null, "sagar", expectedPower, "sagarUrl");
    Mockito.when(powerService.getById(pokemonCreationForm.getPower())).thenReturn(expectedPower);
    Mockito.when(pokemonRepository.save(any(Pokemon.class))).thenReturn(expectedPokemon);

    // when
    Pokemon recievedPokemon = pokemonService.addPokemon(pokemonCreationForm);

    // then
    Mockito.verify(pokemonRepository).findByName(pokemonCreationForm.getName());
    Mockito.verify(powerService).getById(pokemonCreationForm.getPower());
    Mockito.verify(pokemonRepository).save(any(Pokemon.class));

    Assertions.assertThat(recievedPokemon).isEqualTo(expectedPokemon);
  }

  @Test
  public void shouldThrowExceptionWhenAddPokemon() {
    // given
    pokemonCreationForm = new PokemonCreationForm("sagar", 2, "sagarurl");

    //    when
    Mockito.when(pokemonRepository.findByName(pokemonCreationForm.getName()))
        .thenReturn(Optional.of(new Pokemon()));

    //    then
    Assertions.assertThatThrownBy(() -> pokemonService.addPokemon(pokemonCreationForm))
        .isInstanceOf(EntityAlreadyExistException.class)
        .hasMessage("Pokemon is Already Exist");
  }

  @Test
  public void shouldReturnListOfPokemon() {
    // given
    Mockito.when(pokemonRepository.findAll()).thenReturn(List.of(bulbasaur, pikachu));
    // when
    List<Pokemon> pokemonList = pokemonService.get();
    // then
    Mockito.verify(pokemonRepository).findAll();
    Assertions.assertThat(pokemonList)
        .containsExactlyInAnyOrderElementsOf(List.of(pikachu, bulbasaur));
  }

  @Test
  public void shouldUpdatePokemon() {

    // given
    Mockito.when(pokemonRepository.findByName(bulbasaur.getName()))
        .thenReturn(Optional.ofNullable(bulbasaur));
    Mockito.when(pokemonRepository.updateByName(bulbasaur.getName(), bulbasaur))
        .thenReturn(bulbasaur);

    // when
    Pokemon updatedPokemon = pokemonService.updatePokemon(bulbasaur);

    /// then
    Mockito.verify(pokemonRepository).findByName(bulbasaur.getName());
    Mockito.verify(pokemonRepository).updateByName(bulbasaur.getName(), bulbasaur);
    Assertions.assertThat(updatedPokemon).isEqualTo(bulbasaur);
  }
}
