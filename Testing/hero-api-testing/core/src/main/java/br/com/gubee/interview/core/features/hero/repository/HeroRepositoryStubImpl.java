package br.com.gubee.interview.core.features.hero.repository;

import br.com.gubee.interview.core.features.hero.repository.HeroRepository;
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
public class HeroRepositoryStubImpl implements HeroRepository {

    private List<Hero> HERO_STORAGE = new ArrayList<>();

    public HeroRepositoryStubImpl() {

        this.HERO_STORAGE.add(hero(HeroIds.AQUAMEN_ID, "Aquamen", PowerStatsIdsByHero.POWER_STATS_AQUAMEN_ID));
        this.HERO_STORAGE.add(hero(HeroIds.LANTERNA_VERDE_ID, "Lanterna Vermelha", PowerStatsIdsByHero.POWER_STATS_LANTERNA_VERMELHA_ID));
        this.HERO_STORAGE.add(hero(HeroIds.LANTERNA_VERMELHA_ID, "Lanterna Verde", PowerStatsIdsByHero.POWER_STATS_LANTERNA_VERDE_ID));
        this.HERO_STORAGE.add(hero(HeroIds.BATMAN_ID, "Batman", PowerStatsIdsByHero.POWER_STATS_BATMAN_ID));

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
                .map(hero ->
                        new Hero(hero.getId(),
                                hero.getName(),
                                hero.getRace(),
                                hero.getPowerStatsId(),
                                hero.getCreatedAt(),
                                hero.getUpdatedAt(),
                                hero.isEnabled()))
                .findFirst();
    }

    @Override
    public List<Hero> retriveByName(String name) {
        return HERO_STORAGE.stream()
                .filter(hero -> hero.getName().toLowerCase().contains(name.toLowerCase()))
                .map(hero ->
                        new Hero(hero.getId(),
                                hero.getName(),
                                hero.getRace(),
                                hero.getPowerStatsId(),
                                hero.getCreatedAt(),
                                hero.getUpdatedAt(),
                                hero.isEnabled()))
                .collect(Collectors.toList());
    }

    @Override
    public int update(Hero heroToUpdate) {
        Hero heroToBeUpdated = HERO_STORAGE.stream()
                .filter(hero -> hero.getId().equals(heroToUpdate.getId()))
                .map(hero -> new Hero(hero.getId(),
                        hero.getName(),
                        hero.getRace(),
                        hero.getPowerStatsId(),
                        hero.getCreatedAt(),
                        hero.getUpdatedAt(),
                        hero.isEnabled()))
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
