package com.github.lucaspnhr.service;

import com.github.lucaspnhr.model.hero.Hero;
import com.github.lucaspnhr.model.hero.HeroId;
import com.github.lucaspnhr.model.hero.enums.Race;
import com.github.lucaspnhr.model.powerstats.*;
import com.github.lucaspnhr.outport.SaveHeroCommand;
import com.github.lucaspnhr.outport.SaveHeroPort;
import com.github.lucaspnhr.usecase.RegisterHeroUseCase;
import com.github.lucaspnhr.usecase.request.RegisterHeroRequest;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class RegisterHeroService implements RegisterHeroUseCase {

    private final SaveHeroPort saveHeroPort;

    @Override
    public UUID registerHero(RegisterHeroRequest heroToRegister) {
        Hero hero = getHero(heroToRegister);
        SaveHeroCommand saveHeroCommand = getSaveHeroCommand(hero);
        boolean successfulySaved = saveHeroPort.saveHero(saveHeroCommand);
        if (!successfulySaved) {
            //TODO change this exception to a custom one
            throw new IllegalArgumentException();
        }
        return hero.getId().getId();
    }

    private static SaveHeroCommand getSaveHeroCommand(Hero hero) {
        return SaveHeroCommand.builder()
                .heroId(hero.getId().getId())
                .name(hero.getName())
                .race(hero.getRace().name())
                .powerStatsid(hero.getPowerStats().getId().getId())
                .agility(hero.getPowerStats().getAgility().getValue())
                .dexterity(hero.getPowerStats().getDexterity().getValue())
                .intelligence(hero.getPowerStats().getIntelligence().getValue())
                .strength(hero.getPowerStats().getStrength().getValue())
                .heroCreatedAt(hero.getCreatedAt())
                .heroUpdatedAt(hero.getUpdatedAt())
                .powerStatsCreatedAt(hero.getPowerStats().getCreatedAt())
                .powerStatsUpdatedAt(hero.getPowerStats().getUpdatedAt()).build();
    }

    private static Hero getHero(RegisterHeroRequest heroToRegister) {
        return new Hero(new HeroId(),
                heroToRegister.getName(),
                Race.valueOf(heroToRegister.getRace().toUpperCase()),
                new PowerStats(new PowerStatsId(),
                        new Strength(heroToRegister.getStrength()),
                        new Intelligence(heroToRegister.getIntelligence()),
                        new Agility(heroToRegister.getAgility()),
                        new Dexterity(heroToRegister.getDexterity())));
    }
}
