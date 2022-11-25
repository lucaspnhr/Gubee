package com.github.lucaspnhr.outport;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class RetrievedHero {
    private UUID heroId;
    private UUID powerStatsid;
    private String name;
    private String race;
    private int strength;
    private int agility;
    private int intelligence;
    private int dexterity;
}
