package br.com.gubee.interview.core.features.hero;

import br.com.gubee.interview.core.features.powerstats.PowerStatsRepository;
import br.com.gubee.interview.core.features.powerstats.PowerStatsRepositoryImpl;
import br.com.gubee.interview.model.Hero;
import br.com.gubee.interview.model.PowerStats;
import br.com.gubee.interview.model.enums.Race;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static br.com.gubee.interview.core.features.util.constants.PowerStatsIdsByHero.POWER_STATS_BATMAN_ID;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest

@ActiveProfiles("it")
class HeroRepositoryItTest {



    private HeroRepository heroRepository;
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private PowerStatsRepository powerStatsRepository;
    private UUID powerStatsId;


    @BeforeEach
    void setUp() {
        heroRepository = new HeroRepositoryImpl(namedParameterJdbcTemplate);
        powerStatsRepository = new PowerStatsRepositoryImpl(namedParameterJdbcTemplate);
        powerStatsId =  powerStatsRepository.create(getPowerStats());
    }


    @Test
    void shouldSaveHeroToDatabase() {
        Hero heroToSave = getHero();
        UUID uuid = heroRepository.create(heroToSave);
        Optional<Hero> hero = heroRepository.retriveById(uuid);

        assertThat(uuid).isNotNull();
        assertThat(hero)
                .isPresent()
                .get()
                .hasNoNullFieldsOrProperties();
    }


    @Test
    void shouldReturnOptionalWithRetrievedHero() {
        Hero heroToSave = getHero();
        UUID uuid = heroRepository.create(heroToSave);
        Optional<Hero> hero = heroRepository.retriveById(uuid);

        assertThat(hero)
                .isPresent()
                .get()
                .hasNoNullFieldsOrProperties();
    }

    @Test
    void shouldReturnEmptyOptionalWhenNoMatchedId() {
        Optional<Hero> hero = heroRepository.retriveById(UUID.randomUUID());
        assertThat(hero).isEmpty();
    }

    @Test
    void shouldReturnListOfHerosThatContainsNameValue() {
        Hero heroToSave = getHero();
        heroRepository.create(heroToSave);
        List<Hero> herosFromDb = heroRepository.retriveByName("man");

        assertThat(herosFromDb.isEmpty()).isFalse();
    }



    @Test
    void shouldReturnEmptyListWhenNoHeroThatNameContainsNameValue() {
        List<Hero> herosFromDb = heroRepository.retriveByName("man");

        assertThat(herosFromDb.isEmpty()).isTrue();
    }

    @Test
    void shouldReturnTrueWhenHeroAlredyExistsInDb(){
        Hero heroToSave = getHero();
        heroRepository.create(heroToSave);

        boolean alreadyExists = heroRepository.alreadyExists(heroToSave.getName());

        assertThat(alreadyExists).isTrue();
    }

    @Test
    void shouldUpdateHeroInDb() {
        final var heroToSave = getHero();
        UUID heroId = heroRepository.create(heroToSave);

        final var heroToUpdated = Hero.builder()
                .id(heroId)
                .name("Batman-Who_Laughts")
                .race(Race.HUMAN).build();

        int updateReturn = heroRepository.update(heroToUpdated);

        final var hero = heroRepository.retriveById(heroId).get();

        assertThat(updateReturn).isGreaterThan(0);
        assertThat(hero.getName()).isEqualTo(heroToUpdated.getName());
    }

    @Test
    void shouldDeleteHeroFromDb() {
        final var heroToSave = getHero();
        UUID heroId = heroRepository.create(heroToSave);

        int deleteReturn = heroRepository.delete(heroId);
        Optional<Hero> heroFromDb = heroRepository.retriveById(heroId);

        assertThat(deleteReturn).isGreaterThan(0);
        assertThat(heroFromDb).isEmpty();
    }

    @AfterEach
    void tearDown() {
        heroRepository.deleteAll();
        powerStatsRepository.deleteAll();
    }
    private PowerStats getPowerStats() {
        return PowerStats.builder()
                .agility(4)
                .dexterity(5)
                .intelligence(8)
                .strength(10).build();
    }

    private Hero getHero() {
        return Hero.builder()
                .name("Batman")
                .race(Race.ALIEN)
                .powerStatsId(powerStatsId)
                .build();
    }
}