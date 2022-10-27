package br.com.gubee.interview.core.features.hero;

import br.com.gubee.interview.model.Hero;
import br.com.gubee.interview.model.enums.Race;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static br.com.gubee.interview.core.features.util.constants.HeroIds.*;
import static br.com.gubee.interview.core.features.util.constants.PowerStatsIdsByHero.*;

@Slf4j
public class HeroRepositoryTestImpl implements HeroRepositoryI {

    private List<Hero> HERO_STORAGE = new ArrayList<>();

    public HeroRepositoryTestImpl() {

        this.HERO_STORAGE.add(hero(AQUAMEN_ID, "Aquamen", POWER_STATS_AQUAMEN_ID));
        this.HERO_STORAGE.add(hero(LANTERNA_VERDE_ID, "Lanterna Vermelha", POWER_STATS_LANTERNA_VERMELHA_ID));
        this.HERO_STORAGE.add(hero(LANTERNA_VERMELHA_ID, "Lanterna Verde", POWER_STATS_LANTERNA_VERDE_ID));
        this.HERO_STORAGE.add(hero(BATMAN_ID, "Batman", POWER_STATS_BATMAN_ID));

    }

    private static Hero hero(UUID id, String name, UUID powerStatsId) {

        return Hero.builder()
                .id(id)
                .name(name)
                .race(Race.HUMAN)
                .powerStatsId(powerStatsId)
                .updatedAt(Instant.now())
                .createdAt(Instant.now())
                .build();
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
                .findFirst();
    }

    @Override
    public List<Hero> retriveByName(String name) {
        return HERO_STORAGE.stream()
                .filter((hero) -> hero.getName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());
    }

    @Override
    public int update(Hero heroToUpdate) {
        HERO_STORAGE = HERO_STORAGE.stream()
                .peek((hero) ->{
                   if (hero.getId().equals(heroToUpdate.getId())){
                       hero.setName(heroToUpdate.getName());
                       hero.setRace(heroToUpdate.getRace());
                       hero.setUpdatedAt(Instant.now());
                   }
                }).collect(Collectors.toList());
        return 1;
    }

    @Override
    public int delete(UUID id) {
        HERO_STORAGE = HERO_STORAGE.stream()
                .filter(hero -> !hero.getId().equals(id))
                .collect(Collectors.toList());
        return 1;
    }
}
