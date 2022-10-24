package br.com.gubee.interview.core.features.hero;

import br.com.gubee.interview.core.features.powerstats.PowerStatsService;
import br.com.gubee.interview.model.Hero;
import br.com.gubee.interview.model.PowerStats;
import br.com.gubee.interview.model.enums.Race;
import br.com.gubee.interview.model.request.CreateHeroRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class HeroServiceTest {

    @Mock
    private HeroRepository heroRepository;

    @Mock
    private PowerStatsService powerStatsService;

    @InjectMocks
    private HeroService heroService;


    @Test
    void saveHeroToDatabaseAndResturnsItsId(){
        final var createHeroRequest = createHeroRequest();
        final var powerStats = createPowerStats(createHeroRequest);
        final var hero = createHero(createHeroRequest, powerStats.getId());

        //When PowerStatsService's method create is called then it returns the created UUID
        when(powerStatsService.create(any(PowerStats.class))).thenReturn(powerStats.getId());
        //When HeroRepository's method create is called then it returns the created UUID
        when(heroRepository.create(any(Hero.class))).thenReturn(hero.getId());

        UUID heroReturnedId = heroService.create(createHeroRequest);

        assertThat(heroReturnedId).isEqualTo(hero.getId());
        verify(heroRepository, times(1)).create(any(Hero.class));
        verify(powerStatsService, times(1)).create(any(PowerStats.class));
        verifyNoMoreInteractions(heroRepository);
    }


    private Hero createHero(CreateHeroRequest createHeroRequest, UUID powerStatsId) {
        var hero = new Hero(createHeroRequest, powerStatsId);
        hero.setCreatedAt(Instant.now());
        hero.setUpdatedAt(Instant.now());
        hero.setId(UUID.randomUUID());
        return hero;
    }
    private CreateHeroRequest createHeroRequest() {
        return CreateHeroRequest.builder()
                .name("Batman")
                .agility(5)
                .dexterity(8)
                .strength(6)
                .intelligence(10)
                .race(Race.HUMAN)
                .build();
    }
    private PowerStats createPowerStats(CreateHeroRequest createHeroRequest){
        var powerStats = new PowerStats(createHeroRequest);
        powerStats.setCreatedAt(Instant.now());
        powerStats.setUpdatedAt(Instant.now());
        powerStats.setId(UUID.randomUUID());
        return powerStats;
    }
}
