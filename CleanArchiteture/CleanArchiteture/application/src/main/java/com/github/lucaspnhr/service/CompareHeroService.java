package com.github.lucaspnhr.service;

import com.github.lucaspnhr.outport.LoadHeroPort;
import com.github.lucaspnhr.outport.RetrieveHeroRequest;
import com.github.lucaspnhr.usecase.CompareHeroRequest;
import com.github.lucaspnhr.usecase.CompareHeroUseCase;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class CompareHeroService implements CompareHeroUseCase {

    private final LoadHeroPort loadHeroPort;

    @Override
    public CompareHeroRequest compareTwoHeroes(UUID firstHeroId, UUID secondHeroId) {
        RetrieveHeroRequest firstHero = loadHeroPort.loadHeroByid(firstHeroId);
        RetrieveHeroRequest secondHero = loadHeroPort.loadHeroByid(secondHeroId);
        int differenceStrength = firstHero.getStrength() - secondHero.getStrength();
        int agilityRate = firstHero.getAgility() - secondHero.getAgility();
        int intelligenceRate = firstHero.getIntelligence() - secondHero.getIntelligence();
        int dexterityRate = firstHero.getDexterity() - secondHero.getDexterity();
        return new CompareHeroRequest(firstHeroId,
                secondHeroId,
                firstHero.getName(),
                secondHero.getName(),
                differenceStrength,
                agilityRate,
                intelligenceRate,
                dexterityRate);
    }



}
