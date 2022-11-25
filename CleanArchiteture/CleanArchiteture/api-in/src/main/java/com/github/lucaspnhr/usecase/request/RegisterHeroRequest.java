package com.github.lucaspnhr.usecase.request;

import com.github.lucaspnhr.commom.SelfValidating;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class RegisterHeroRequest extends SelfValidating<RegisterHeroRequest> {
    @NotBlank
    private String name;
    @NotNull
    private String race;
    @Min(0)
    @Max(10)
    private int strength;
    @Min(0)
    @Max(10)
    private int agility;
    @Min(0)
    @Max(10)
    private int intelligence;
    @Min(0)
    @Max(10)
    private int dexterity;

    public RegisterHeroRequest(String name, String race, int strength, int agility, int intelligence, int dexterity) {
        this.name = name;
        this.race = race;
        this.strength = strength;
        this.agility = agility;
        this.intelligence = intelligence;
        this.dexterity = dexterity;
        validateSelf();
    }
}
