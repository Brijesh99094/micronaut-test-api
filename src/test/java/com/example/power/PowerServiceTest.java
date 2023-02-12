package com.example.power;

import com.example.Exceptions.EntityNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

class PowerServiceTest {

  PowerRepository powerRepository;
  Power expectedPower;
  PowerService powerService;

  @BeforeEach
  public void setUp() {
    powerRepository = Mockito.mock(PowerRepository.class);
    powerService = new PowerService(powerRepository);
    expectedPower = new Power(2, "grass");
  }

  @Test
  public void shouldReturnPower() {
    powerRepository = Mockito.mock(PowerRepository.class);
    powerService = new PowerService(powerRepository);
    expectedPower = new Power(2, "grass");
    // given
    Mockito.when(powerRepository.findById(2)).thenReturn(Optional.of(expectedPower));
    // when
    Power receivedPower = powerService.getById(2);
    // then
    Mockito.verify(powerRepository).findById(2);

    Assertions.assertThat(receivedPower).isEqualTo(expectedPower);
  }

  @Test
  public void shouldThrowExceptionIfPowerIsNotExist() {
    int powerId = 15;
    Mockito.when(powerRepository.findById(2)).thenReturn(Optional.ofNullable(expectedPower));
    Assertions.assertThatThrownBy(() -> powerService.getById(powerId))
        .isInstanceOf(EntityNotFoundException.class);
  }
}
