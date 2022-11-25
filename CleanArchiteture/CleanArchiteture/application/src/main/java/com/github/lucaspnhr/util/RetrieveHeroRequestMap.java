package com.github.lucaspnhr.util;

import com.github.lucaspnhr.model.hero.Hero;
import com.github.lucaspnhr.model.hero.HeroId;
import com.github.lucaspnhr.model.hero.enums.Race;
import com.github.lucaspnhr.model.powerstats.*;
import com.github.lucaspnhr.outport.RetrievedHero;

public class RetrieveHeroRequestMap {

    public static Hero toHero(RetrievedHero retrievedHero){
        HeroId heroId = new HeroId(retrievedHero.getHeroId());
        PowerStatsId powerStatsId = new PowerStatsId(retrievedHero.getPowerStatsid());
        Strength strength = new Strength(retrievedHero.getStrength());
        Agility agility = new Agility(retrievedHero.getAgility());
        Intelligence intelligence = new Intelligence(retrievedHero.getIntelligence());
        Dexterity dexterity = new Dexterity(retrievedHero.getDexterity());
        PowerStats powerStats = new PowerStats(powerStatsId, strength,intelligence,agility,dexterity);
        return new Hero(heroId,
                retrievedHero.getName(),
                Race.valueOf(retrievedHero.getRace().toUpperCase()),
                powerStats);
    }
}
