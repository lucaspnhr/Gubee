package br.com.gubee.interview.core.features.powerstats;

import br.com.gubee.interview.core.features.powerstats.repository.PowerStatsRepository;
import br.com.gubee.interview.core.features.powerstats.repository.PowerStatsRepositoryImpl;
import br.com.gubee.interview.model.PowerStats;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@ActiveProfiles("it")
public class PowerStatsRepositoryItTest {

    private PowerStatsRepository powerStatsRepository;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @BeforeEach
    void setUp() {
        powerStatsRepository = new PowerStatsRepositoryImpl(namedParameterJdbcTemplate);
    }

    @AfterEach
    void tearDown() {
        powerStatsRepository.deleteAll();
    }

    @Test
    void shouldSavePowerStatsAndReturnItsId(){
        PowerStats powerStatsToSave = getPowerStats();
        UUID powerStatsId = powerStatsRepository.create(powerStatsToSave);

        assertThat(powerStatsId).isNotNull();
    }



    @Test
    void shouldReturnPowerStatsFromDb(){
        PowerStats powerStatsToSave = getPowerStats();
        UUID powerStatsId = powerStatsRepository.create(powerStatsToSave);
        Optional<PowerStats> powerStatsatsFromDb = powerStatsRepository.retriveById(powerStatsId);

        assertThat(powerStatsatsFromDb)
                .isPresent()
                .get()
                .hasNoNullFieldsOrProperties();
    }

    @Test
    void shouldReturnEmptyOptionalWhenNotFound(){
        UUID powerStatsId = UUID.randomUUID();
        Optional<PowerStats> powerStatsatsFromDb = powerStatsRepository.retriveById(powerStatsId);

        assertThat(powerStatsatsFromDb)
                .isEmpty();
    }

    @Test
    void shouldUpdatePowerStatsInDb(){
        final var powerStatsToSave = getPowerStats();
        UUID powerStatsId = powerStatsRepository.create(powerStatsToSave);

        final var powerStatsToUpdate = PowerStats.builder()
                .id(powerStatsId)
                .agility(9)
                .dexterity(2)
                .intelligence(3)
                .strength(4).build();

        int updateReturn = powerStatsRepository.update(powerStatsToUpdate);

        PowerStats powerStats = powerStatsRepository.retriveById(powerStatsId).get();

        assertThat(updateReturn).isGreaterThan(0);
        assertThat(powerStats.getAgility()).isEqualTo(powerStatsToUpdate.getAgility());
    }

    @Test
    void shouldDeletePowerStatsFromDb(){
        final var powerStatsToSave = getPowerStats();
        UUID powerStatsId = powerStatsRepository.create(powerStatsToSave);

        int deleteReturn = powerStatsRepository.delete(powerStatsId);
        Optional<PowerStats> powerStatsFromDb = powerStatsRepository.retriveById(powerStatsId);

        assertThat(deleteReturn).isGreaterThan(0);
        assertThat(powerStatsFromDb).isEmpty();
    }

    private static PowerStats getPowerStats() {
        return PowerStats.builder()
                .agility(4)
                .dexterity(5)
                .intelligence(8)
                .strength(10).build();
    }


}
