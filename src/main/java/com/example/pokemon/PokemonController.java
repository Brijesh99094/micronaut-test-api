package com.example.pokemon;

import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.*;

import java.util.List;


@Controller("/pokemon")
public class PokemonController {

    private final PokemonService pokemonService;

    public PokemonController(PokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }


    @Get
    public HttpResponse<List<Pokemon>> getAll() {
        return HttpResponse.ok(pokemonService.get());
    }

    @Post
    public HttpResponse<Pokemon> create(@Body PokemonCreationForm pokemonForm) {
        return HttpResponse.created(pokemonService.addPokemon(pokemonForm));
    }

    @Get(value = "/{id}")
    public Pokemon getPokemonById(@QueryValue Integer id) {
        return pokemonService.getPokemonById(id);
    }

    @Put
    public Pokemon updatePokemon(@Body Pokemon pokemon) {
        return pokemonService.updatePokemon(pokemon);
    }

    @Delete(value = "/{id}")
    public Pokemon deletePokemon(@QueryValue Integer id) {
        return pokemonService.deletePokemon(id);
    }
}
