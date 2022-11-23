package br.com.gubee.interview.core.application.DTO;

import br.com.gubee.interview.domain.model.hero.enums.Race;
import br.com.gubee.interview.domain.shared.SelfValidating;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class SaveHeroDTO extends SelfValidating<SaveHeroDTO> {
    @NotNull
    private String name;
    @NotNull
    private Race race;
    @Min(0)
    @Max(10)
    private int strength;
    @Min(0)
    @Max(10)
    private int agility;
    @Min(0)
    @Max(10)
    private int dexterity;
    @Min(0)
    @Max(10)
    private int intelligence;

    public SaveHero(String name, Race race, int strength, int agility, int dexterity, int intelligence) {
        this.name = name;
        this.race = race;
        this.strength = strength;
        this.agility = agility;
        this.dexterity = dexterity;
        this.intelligence = intelligence;
        validateSelf();
    }
}