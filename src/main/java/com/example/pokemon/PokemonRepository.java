package com.example.pokemon;

import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@Repository
public interface PokemonRepository extends CrudRepository<Pokemon,Integer> {
    List<Pokemon> findAll();
    Optional<Pokemon> findByName(String name);
    Pokemon updateByName(String name,Pokemon pokemon);
}
