package br.com.gubee.interview.core.features.hero.repository;

import br.com.gubee.interview.core.features.powerstats.repository.PowerStatsRepository;
import br.com.gubee.interview.core.infrastructure.persistence.jdbc.PowerStatsRepositoryJdbcImpl;
import br.com.gubee.interview.core.infrastructure.persistence.jdbc.HeroRepositoryJdbcImpl;
import br.com.gubee.interview.domain.model.hero.Hero;
import br.com.gubee.interview.domain.model.powerstats.PowerStats;
import br.com.gubee.interview.domain.model.hero.enums.Race;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
        heroRepository = new HeroRepositoryJdbcImpl(namedParameterJdbcTemplate);
        powerStatsRepository = new PowerStatsRepositoryJdbcImpl(namedParameterJdbcTemplate);
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
        List<Hero> heroesFromDb = heroRepository.retriveByName("man");

        assertThat(heroesFromDb.isEmpty()).isFalse();
    }



    @Test
    void shouldReturnEmptyListWhenNoHeroThatNameContainsNameValue() {
        List<Hero> heroesFromDb = heroRepository.retriveByName("man");

        assertThat(heroesFromDb.isEmpty()).isTrue();
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