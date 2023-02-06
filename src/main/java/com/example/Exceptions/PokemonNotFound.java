package com.example.Exceptions;

import com.example.pokemon.Pokemon;

import java.util.function.Supplier;

public class PokemonNotFound extends  RuntimeException {
    public PokemonNotFound(String message) {
        super(message);
    }
}
