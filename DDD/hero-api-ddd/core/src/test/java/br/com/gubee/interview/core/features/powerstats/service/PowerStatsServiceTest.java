package br.com.gubee.interview.core.features.powerstats.service;

import br.com.gubee.interview.core.application.impl.PowerStatsServiceImpl;
import br.com.gubee.interview.core.application.PowerStatsService;
import br.com.gubee.interview.core.infrastructure.persistence.jdbc.PowerStatsRepositoryStubImpl;
import br.com.gubee.interview.domain.model.powerstats.PowerStats;
import br.com.gubee.interview.domain.model.request.UpdateHeroRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static br.com.gubee.interview.core.features.util.constants.PowerStatsIdsByHero.POWER_STATS_BATMAN_ID;
import static br.com.gubee.interview.core.features.util.constants.PowerStatsIdsByHero.POWER_STATS_LANTERNA_VERDE_ID;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class PowerStatsServiceTest {

    private PowerStatsService underTest;


    @BeforeEach
    void setUp() {
        PowerStatsRepositoryStubImpl heroRepository = new PowerStatsRepositoryStubImpl();
        this.underTest = new PowerStatsServiceImpl(heroRepository);
    }


    @Test
    void shouldCreatePowerStatsInRepository(){
        PowerStats powerStatsToSave = getPowerStats();

        UUID powerStatsId = underTest.create(powerStatsToSave);

        assertThat(powerStatsId).isNotNull();
    }

    @Test
    void shouldRetrievePowerStatsFromRepositoryWhenValidIdIsPassed(){
        PowerStats powerStats = underTest.retriveById(POWER_STATS_BATMAN_ID);
        assertThat(powerStats).isNotNull();
        assertThat(powerStats).hasNoNullFieldsOrProperties();
    }

    @Test
    void shouldUpdatePowerStats(){
        final var powerStatsId = POWER_STATS_LANTERNA_VERDE_ID;
        final var oldpowerStats = underTest.retriveById(powerStatsId);
        final var updatePowerStats = UpdateHeroRequest.builder()
                .strength(2)
                .build();

        int updateReturn = underTest.update(updatePowerStats, powerStatsId);
        PowerStats powerStats = underTest.retriveById(powerStatsId);


        assertThat(updateReturn).isGreaterThan(0);
        assertThat(powerStats.getStrength()).isNotEqualTo(oldpowerStats.getStrength());
    }

    private PowerStats getPowerStats() {
        return PowerStats.builder()
                .agility(4)
                .dexterity(5)
                .intelligence(8)
                .strength(10).build();
    }
}
