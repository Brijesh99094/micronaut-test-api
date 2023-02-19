package com.example.power;

import com.example.Exceptions.EntityNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

class PowerServiceShould {

    PowerRepository powerRepository;
    Power grass;
    PowerService powerService;

    @BeforeEach
    public void setUp() {
        powerRepository = Mockito.mock(PowerRepository.class);
        powerService = new PowerService(powerRepository);
        grass = new Power(2, "grass");
    }

    @Test
    public void shouldReturnPower() {

        // given
        Mockito.when(powerRepository.findById(grass.getId())).thenReturn(Optional.of(grass));

        // when
        Power receivedPower = powerService.getById(grass.getId());

        // then
        Mockito.verify(powerRepository).findById(grass.getId());
        Assertions.assertThat(receivedPower).isEqualTo(grass);
    }

    @Test
    public void shouldThrowExceptionWhenFetchingPowerWithUnknownId() {

        //given
        int idOfPowerToBeFetched = 15;

        //when
        Mockito.when(powerRepository.findById(idOfPowerToBeFetched)).thenReturn(Optional.empty());

        //then
        Assertions.assertThatThrownBy(() -> powerService.getById(idOfPowerToBeFetched))
                .isInstanceOf(EntityNotFoundException.class).hasMessage("This power doesn't exist");

        Mockito.verify(powerRepository).findById(idOfPowerToBeFetched);
    }
}
