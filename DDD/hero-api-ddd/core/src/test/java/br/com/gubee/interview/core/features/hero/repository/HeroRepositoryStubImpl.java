package br.com.gubee.interview.core.features.hero.repository;

import br.com.gubee.interview.core.application.PowerStatsService;
import br.com.gubee.interview.core.application.impl.PowerStatsServiceStubImpl;
import br.com.gubee.interview.core.features.util.constants.HeroIds;
import br.com.gubee.interview.core.features.util.constants.PowerStatsIdsByHero;
import br.com.gubee.interview.domain.model.hero.Hero;
import br.com.gubee.interview.domain.model.powerstats.PowerStats;
import br.com.gubee.interview.domain.model.hero.enums.Race;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class HeroRepositoryStubImpl implements HeroRepository {

    private List<Hero> HERO_STORAGE = new ArrayList<>();
    private PowerStatsService powerStatsService = new PowerStatsServiceStubImpl();

    public HeroRepositoryStubImpl() {

        this.HERO_STORAGE.add(hero(HeroIds.AQUAMEN_ID, "Aquamen", PowerStatsIdsByHero.POWER_STATS_AQUAMEN_ID));
        this.HERO_STORAGE.add(hero(HeroIds.LANTERNA_VERDE_ID, "Lanterna Vermelha", PowerStatsIdsByHero.POWER_STATS_LANTERNA_VERMELHA_ID));
        this.HERO_STORAGE.add(hero(HeroIds.LANTERNA_VERMELHA_ID, "Lanterna Verde", PowerStatsIdsByHero.POWER_STATS_LANTERNA_VERDE_ID));
        this.HERO_STORAGE.add(hero(HeroIds.BATMAN_ID, "Batman", PowerStatsIdsByHero.POWER_STATS_BATMAN_ID));

    }

    private Hero hero(UUID id, String name, UUID powerStatsId) {
        PowerStats powerStats = powerStatsService.retriveById(powerStatsId);
        var hero =  Hero.builder()
                .name(name)
                .race(Race.HUMAN)
                .powerStats(powerStats)
                .updatedAt(Instant.now())
                .createdAt(Instant.now())
                .build();
        hero.setId(id);
        return hero;
    }


    @Override
    public boolean alreadyExists(String heroName) {
        List<Hero> heroes = retriveByName(heroName);
        return heroes.size() > 0;
    }

    @Override
    public UUID create(Hero hero) {
        hero.setId(UUID.randomUUID());
        HERO_STORAGE.add(hero);
        log.info("Adding hero to list {}", hero);
        log.info("List uptodated {}",HERO_STORAGE);
        return hero.getId();
    }

    @Override
    public Optional<Hero> retriveById(UUID heroId) {
        return HERO_STORAGE.stream()
                .filter(hero -> hero.getId().equals(heroId))
                .map(hero -> {
                    var heroReturn = Hero.builder()
                            .name(hero.getName())
                            .race(hero.getRace())
                            .powerStats(hero.getPowerStats())
                            .createdAt(hero.getCreatedAt())
                            .updatedAt(hero.getUpdatedAt())
                            .enabled(hero.isEnabled()).build();

                    heroReturn.setId(hero.getId());
                    return heroReturn;
                })
                .findFirst();
    }

    @Override
    public List<Hero> retriveByName(String name) {
        long quantityFound = HERO_STORAGE.stream()
                .filter(hero -> hero.getName().toLowerCase().contains(name.toLowerCase()))
                .count();
        if(quantityFound <= 0){
            return Collections.emptyList();
        }

        return HERO_STORAGE.stream()
                .filter(hero -> hero.getName().toLowerCase().contains(name.toLowerCase()))
                .map(hero ->{
                    var heroReturn = Hero.builder()
                            .name(hero.getName())
                            .race(hero.getRace())
                            .powerStats(hero.getPowerStats())
                            .createdAt(hero.getCreatedAt())
                            .updatedAt(hero.getUpdatedAt())
                            .enabled(hero.isEnabled()).build();

                    heroReturn.setId(hero.getId());
                    return heroReturn;
                })
                .collect(Collectors.toList());
    }

    @Override
    public int update(Hero heroToUpdate) {
        Hero heroToBeUpdated = HERO_STORAGE.stream()
                .filter(hero -> hero.getId().equals(heroToUpdate.getId()))
                .map(hero -> {
                    var returnHero = Hero.builder().
                    name(hero.getName()).
                    race(hero.getRace()).
                    powerStats(hero.getPowerStats()).
                    createdAt(hero.getCreatedAt()).
                    updatedAt(hero.getUpdatedAt()).
                    enabled(hero.isEnabled()).build();
                returnHero.setId(hero.getId());
                return returnHero;
                })
                .findFirst().get();

        HERO_STORAGE.remove(heroToBeUpdated);

        heroToBeUpdated.setName(heroToUpdate.getName());
        heroToBeUpdated.setRace(heroToUpdate.getRace());
        heroToBeUpdated.setUpdatedAt(Instant.now());

        HERO_STORAGE.add(heroToBeUpdated);
        return 1;
    }

    @Override
    public int delete(UUID id) {
        Hero heroToBeDeleted = HERO_STORAGE.stream()
                .filter(hero -> hero.getId().equals(id))
                .findFirst().get();
        HERO_STORAGE.remove(heroToBeDeleted);
        return 1;
    }

    @Override
    public void deleteAll() {

    }
}
