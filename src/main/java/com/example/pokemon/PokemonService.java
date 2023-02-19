package com.example.pokemon;

import com.example.Exceptions.EntityAlreadyExistException;
import com.example.Exceptions.EntityNotFoundException;
import com.example.power.PowerService;
import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Singleton
public class PokemonService {

    private static final Logger log = LoggerFactory.getLogger(PokemonService.class);
    private final PokemonRepository pokemonRepository;
    private final PowerService powerService;

    public PokemonService(PokemonRepository pokemonRepository, PowerService powerService) {
        this.pokemonRepository = pokemonRepository;
        this.powerService = powerService;
    }

    public List<Pokemon> get() {
        return pokemonRepository.findAll();
    }


    public Pokemon addPokemon(PokemonCreationForm pokemonForm) {
        Optional<Pokemon> fetchedPokemon = pokemonRepository.findByName(pokemonForm.getName());
        if (fetchedPokemon.isPresent()) {
            throw new EntityAlreadyExistException(
                    "Pokemon with name: " + pokemonForm.getName() + " Already Exists");
        }
        Pokemon pokemon = new Pokemon();
        pokemon.setName(pokemonForm.getName());
        pokemon.setPower(powerService.getById(pokemonForm.getPower()));
        pokemon.setImgUrl(pokemonForm.getImgUrl());
        return pokemonRepository.save(pokemon);
    }

    public Pokemon getPokemonById(Integer id) {
        return pokemonRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pokemon not found"));
    }

    public Pokemon updatePokemon(Pokemon pokemon) {
        Optional<Pokemon> fetchedPokemon = pokemonRepository.findByName(pokemon.getName());
        if (fetchedPokemon.isPresent()) {
            return pokemonRepository.updateByName(pokemon.getName(), pokemon);
        } else throw new EntityNotFoundException("Pokemon Name is not exist in database");
    }

    //
    public Pokemon deletePokemon(Integer id) {
        Pokemon deletePokemon =
                pokemonRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Pokemon not found delete"));
        pokemonRepository.deleteById(id);
        return deletePokemon;
    }


    public boolean isPokemonExistByName(String name) {
        Optional<Pokemon> fetchedPokemon = pokemonRepository.findByName(name);
        if (fetchedPokemon.isPresent()) return true;
        else throw new EntityNotFoundException("Pokemon Not found");
    }

}
