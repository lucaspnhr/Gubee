package br.com.gubee.interview.core.features.hero;

import br.com.gubee.interview.model.Hero;
import br.com.gubee.interview.model.enums.Race;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class HeroRepositoryFakeImpl implements HeroRepositoryI {


    /*bda51c4a-583c-4b96-8923-860e28854058
    2c9f845f-095f-4434-b6d6-d07c50163340
    4330d660-8f7c-47a2-96dd-ab564fb42929
    49f32bc5-428c-4a4e-9161-98590d0f31c0*/
    private List<Hero> HERO_STORAGE = new ArrayList<>();

    public HeroRepositoryFakeImpl() {

        this.HERO_STORAGE.add(hero("bda51c4a-583c-4b96-8923-860e28854058", "Aquamen"));
        this.HERO_STORAGE.add(hero("2c9f845f-095f-4434-b6d6-d07c50163340", "Lanterna Vermelha"));
        this.HERO_STORAGE.add(hero("4330d660-8f7c-47a2-96dd-ab564fb42929", "Lanterna Verde"));
        this.HERO_STORAGE.add(hero("49f32bc5-428c-4a4e-9161-98590d0f31c0", "Batman"));

    }

    private static Hero hero(String id, String name) {
        return Hero.builder()
                .id(UUID.fromString(id))
                .name(name)
                .race(Race.HUMAN)
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
