package com.example.pokemon;

import com.example.Exceptions.PokemonNotFound;
import jakarta.inject.Singleton;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Singleton
public class PokemonService {

  private final PokemonRepository pokemonRepository;

  public PokemonService(PokemonRepository pokemonRepository) {
    this.pokemonRepository = pokemonRepository;
  }

  public List<Pokemon> get() {
    return StreamSupport.stream(pokemonRepository.findAll().spliterator(), false)
        .collect(Collectors.toList());
  }

  public Pokemon addPokemon(Pokemon pokemon) {
   if(!isPokemonExistByName(pokemon.getName())){
     return pokemonRepository.save(pokemon);
   }
   else {
     throw new PokemonNotFound("Pokemon is already exist with this name");
   }
  }

  public Pokemon getPokemonById(Integer id) {
    return pokemonRepository
        .findById(id)
        .orElseThrow(() -> new PokemonNotFound("Pokemon not found"));
  }

  public Pokemon updatePokemon(Pokemon pokemon) {
    if (pokemonRepository.findById(pokemon.getId()).isEmpty()) {
      throw new PokemonNotFound("Pokemon not found in update");
    }
    return pokemonRepository.update(pokemon);
  }
  //
  public Pokemon deletePokemon(Integer id) {
    Pokemon deletePokemon =
        pokemonRepository.findById(id).orElseThrow(() -> new PokemonNotFound("Pokemon not found delete"));
    pokemonRepository.deleteById(id);
    return deletePokemon;
  }


  public  boolean isPokemonExistByName(String name){
    List<String> pokemonList =  StreamSupport.stream(pokemonRepository.findAll().spliterator(), false)
            .filter(pokemon -> pokemon.getName().equals(name))
            .map(pokemon -> pokemon.getName())
            .collect(Collectors.toList());
    System.out.println("pokemonList = " + pokemonList);
    return  pokemonList.isEmpty();
  }
  
}
