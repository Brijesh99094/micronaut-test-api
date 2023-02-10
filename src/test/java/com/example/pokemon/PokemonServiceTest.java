package com.example.pokemon;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

class PokemonServiceTest {

    @Test
    public  void testAddPokemon(){
        Pokemon mockedPokemon = Mockito.mock(Pokemon.class);
        PokemonCreationForm pokemonCreationForm = Mockito.mock(PokemonCreationForm.class);

        PokemonService mockedService = Mockito.mock(PokemonService.class);

        Mockito.when(mockedService.addPokemon(pokemonCreationForm)).thenReturn(mockedPokemon);

        //when
        Pokemon pokemon = mockedService.addPokemon(pokemonCreationForm);

        //then
        Mockito.verify(mockedService).addPokemon(pokemonCreationForm);

        Assertions.assertThat(pokemon).isEqualTo(mockedPokemon);

    }
}