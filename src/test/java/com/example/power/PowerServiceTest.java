package com.example.power;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.testng.annotations.BeforeMethod;

class PowerServiceTest {

    PowerRepository powerRepository;

  @BeforeMethod
  public void setUp() {
    powerRepository = Mockito.mock(PowerRepository.class);


  }

  @Test
  public  void shouldReturnPower(){
      PowerService powerService = new PowerService(powerRepository);


      Power recievedPower =  powerService.getById(2);
  }



}