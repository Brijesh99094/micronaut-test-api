package com.example.power;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.testng.annotations.BeforeMethod;

import java.util.Optional;

class PowerServiceTest {

    PowerRepository powerRepository;

  @BeforeMethod
  public void setUp() {

  }

  @Test
  public  void shouldReturnPower(){
      powerRepository = Mockito.mock(PowerRepository.class);
      PowerService powerService = new PowerService(powerRepository);
      Power expectedPower = new Power(2,"grass");
      //given
      Mockito.when(powerRepository.findById(2)).thenReturn(Optional.of(expectedPower));

      //when
      Power receivedPower =  powerService.getById(2);

      //then
      Mockito.verify(powerRepository).findById(2);


      Assertions.assertThat(receivedPower).isEqualTo(expectedPower);
  }



}