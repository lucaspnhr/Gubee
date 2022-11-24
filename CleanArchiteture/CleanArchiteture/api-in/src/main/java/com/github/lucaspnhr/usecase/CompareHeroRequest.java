package com.github.lucaspnhr.usecase;

import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Data
public class CompareHeroRequest {
    private Map<UUID, String> heroes = new HashMap<>();
    private int strength;
    private int agility;
    private int intelligence;
    private int dexterity;

    public CompareHeroRequest(UUID firstHeroId, UUID secondHeroId, String firstHeroName, String secondHeroName,int strength, int agility, int intelligence, int dexterity) {
        heroes.put(firstHeroId, firstHeroName);
        heroes.put(secondHeroId, secondHeroName);
        this.strength = strength;
        this.agility = agility;
        this.intelligence = intelligence;
        this.dexterity = dexterity;
    }
}
