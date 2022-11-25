package com.github.lucaspnhr.service;

import com.github.lucaspnhr.model.hero.Hero;
import com.github.lucaspnhr.outport.LoadHeroPort;
import com.github.lucaspnhr.outport.RetrievedHero;
import com.github.lucaspnhr.usecase.request.CompareHeroRequest;
import com.github.lucaspnhr.usecase.CompareHeroUseCase;
import com.github.lucaspnhr.util.RetrieveHeroRequestMap;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.UUID;

@RequiredArgsConstructor
public class CompareHeroService implements CompareHeroUseCase {

    private final LoadHeroPort loadHeroPort;


    @Override
    public CompareHeroRequest compareTwoHeroes(UUID firstHeroId, UUID secondHeroId) {
        RetrievedHero firstRetrievedHero = loadHeroPort.loadHeroByid(firstHeroId);
        RetrievedHero secondRetrievedHero = loadHeroPort.loadHeroByid(secondHeroId);

        Hero firstHero = RetrieveHeroRequestMap.toHero(firstRetrievedHero);
        Hero secondHero = RetrieveHeroRequestMap.toHero(secondRetrievedHero);

        Map<String, Integer> comparedResult = firstHero.compareStats(secondHero.getPowerStats());

        return new CompareHeroRequest(firstHeroId,
                secondHeroId,
                firstHero.getName(),
                secondHero.getName(),
                comparedResult.get("strength"),
                comparedResult.get("agility"),
                comparedResult.get("intelligence"),
                comparedResult.get("dexterity"));
    }

}
