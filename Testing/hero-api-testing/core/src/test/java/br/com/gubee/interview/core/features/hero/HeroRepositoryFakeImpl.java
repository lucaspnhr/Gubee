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

@Slf4j
public class HeroRepositoryFakeImpl implements HeroRepositoryI {


    /*bda51c4a-583c-4b96-8923-860e28854058
    2c9f845f-095f-4434-b6d6-d07c50163340
    4330d660-8f7c-47a2-96dd-ab564fb42929
    49f32bc5-428c-4a4e-9161-98590d0f31c0*/
    private List<Hero> HERO_STORAGE = new ArrayList<>();

    public HeroRepositoryFakeImpl() {

        this.HERO_STORAGE.add(hero("bda51c4a-583c-4b96-8923-860e28854058", "Aquamen", "f62be55c-22b3-4f91-a817-56c5da24668c"));
        this.HERO_STORAGE.add(hero("2c9f845f-095f-4434-b6d6-d07c50163340", "Lanterna Vermelha", "76543d70-383a-4770-ab75-3540412bdf34"));
        this.HERO_STORAGE.add(hero("4330d660-8f7c-47a2-96dd-ab564fb42929", "Lanterna Verde", "dca80f01-4894-4e82-8b46-762e9a236ea4"));
        this.HERO_STORAGE.add(hero("49f32bc5-428c-4a4e-9161-98590d0f31c0", "Batman", "c235875d-8966-4428-89cb-8320cd1950c5"));

    }

    private static Hero hero(String id, String name, String powerStatsId) {

        return Hero.builder()
                .id(UUID.fromString(id))
                .name(name)
                .race(Race.HUMAN)
                .powerStatsId(UUID.fromString(powerStatsId))
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
                .filter((hero) -> hero.getName().contains(name))
                .collect(Collectors.toList());
    }

    @Override
    public int update(Hero heroToUpdate) {
        Optional<Hero> optionalHero = retriveById(heroToUpdate.getId());
        if (optionalHero.isPresent()) {
            Hero hero = optionalHero.get();
            HERO_STORAGE.remove(hero);
            hero.setName(heroToUpdate.getName());
            hero.setRace(heroToUpdate.getRace());
            HERO_STORAGE.add(hero);
            return 1;
        }
        return 0;
    }

    @Override
    public int delete(UUID id) {
        Optional<Hero> optionalHero = retriveById(id);
        if (optionalHero.isPresent()) {
            Hero hero = optionalHero.get();
            HERO_STORAGE.remove(hero);
            return 1;
        }
        return 0;
    }
}
