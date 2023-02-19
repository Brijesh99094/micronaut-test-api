package com.example.power;

import com.example.Exceptions.EntityNotFoundException;
import jakarta.inject.Singleton;

@Singleton
public class PowerService {

    PowerRepository powerRepository;

    public PowerService(PowerRepository powerRepository) {
        this.powerRepository = powerRepository;
    }

    public Power getById(Integer id) {
        return powerRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("This power doesn't exist"));
    }
}
