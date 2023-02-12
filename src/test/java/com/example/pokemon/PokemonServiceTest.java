package com.example.pokemon;

import com.example.Exceptions.EntityAlreadyExistException;
import com.example.Exceptions.EntityNotFoundException;
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

        // when
        Mockito.when(pokemonRepository.findByName(pokemonCreationForm.getName()))
                .thenReturn(Optional.of(new Pokemon()));

        // then
        Assertions.assertThatThrownBy(() -> pokemonService.addPokemon(pokemonCreationForm))
                .isInstanceOf(EntityAlreadyExistException.class)
                .hasMessage("Pokemon is Already Exist");
    }

    @Test
    public void canGetPokemonById() {
      // given
      Mockito.when(pokemonRepository.findById(pikachu.getId()))
              .thenReturn(Optional.of(pikachu));

      // when
      Pokemon pokemonResponse = pokemonService.getPokemonById(pikachu.getId());

      // then
      Assertions.assertThat(pokemonResponse).isEqualTo(pikachu);
      Mockito.verify(pokemonRepository).findById(pikachu.getId());
    }

    @Test
    public void shouldThrowExceptionWhenGetPokemonWithUnknownId() {
        // given
        Integer idOfPokemonToBeFetched = 2;

        // when
        Mockito.when(pokemonRepository.findById(idOfPokemonToBeFetched))
                .thenReturn(Optional.empty());

        // then
        Assertions.assertThatThrownBy(() -> pokemonService
                        .getPokemonById(idOfPokemonToBeFetched))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Pokemon not found");
        Mockito.verify(pokemonRepository).findById(idOfPokemonToBeFetched);
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

  @Test
  public void shouldThrowExceptionWhenUpdatingPokemonWithUnknownName() {
    // given
    Pokemon unknownPokemon = new Pokemon(null, "ramu", new Power(2, "grass"), "ramu.png");

    // when
    Mockito.when(pokemonRepository.findByName(unknownPokemon.getName()))
        .thenReturn(Optional.empty());

    // then
    Assertions.assertThatThrownBy(() -> pokemonService.updatePokemon(unknownPokemon))
        .isInstanceOf(EntityNotFoundException.class)
        .hasMessage("Pokemon Name is not exist in database");

    Mockito.verify(pokemonRepository).findByName(unknownPokemon.getName());

    }

    @Test
    public void shouldDeletePokemon() {
        // given
        Mockito.when(pokemonRepository.findById(bulbasaur.getId()))
                .thenReturn(Optional.of(bulbasaur));

        // when
        Pokemon deletedPokemon = pokemonService.deletePokemon(bulbasaur.getId());

        // then
        Mockito.verify(pokemonRepository).findById(bulbasaur.getId());
        Mockito.verify(pokemonRepository).deleteById(bulbasaur.getId());
        Assertions.assertThat(deletedPokemon).isEqualTo(bulbasaur);
    }

    @Test void shouldThrowExceptionWhenDeletingPokemonWithUnknownId() {
        // given
        Integer idOfPokemonToBeDeleted = 5;

        // when
        Mockito.when(pokemonRepository.findById(idOfPokemonToBeDeleted))
                .thenReturn(Optional.empty());

        // then
        Assertions.assertThatThrownBy(() ->
                        pokemonService.deletePokemon(idOfPokemonToBeDeleted))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Pokemon not found delete");
        Mockito.verify(pokemonRepository).findById(idOfPokemonToBeDeleted);
    }
}
