package com.example.Exceptions;

public class EntityNotFoundException extends PokemonException {
    public EntityNotFoundException(String message) {
        super(message);
    }
}
