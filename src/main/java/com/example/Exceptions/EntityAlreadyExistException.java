package com.example.Exceptions;

public class EntityAlreadyExistException extends PokemonException {
    public EntityAlreadyExistException(String message) {
        super(message);
    }
}
