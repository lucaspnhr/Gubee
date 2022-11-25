package com.github.lucaspnhr.model.hero;

import com.github.lucaspnhr.model.hero.enums.Race;
import com.github.lucaspnhr.model.powerstats.*;
import com.github.lucaspnhr.model.share.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.Validate;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

import static com.github.lucaspnhr.model.share.util.Constants.INVALID_MESSAGE;

@Getter
@Setter(AccessLevel.PRIVATE)
public class Hero extends Entity<HeroId>{

    private String name;
    private Race race;
    private PowerStats powerStats;



    public Hero(final HeroId heroId, String name, Race race, PowerStats powerStats, Instant createdAt, Instant updatedAt) {
        super(heroId, createdAt, updatedAt);
        Validate.notBlank(name, INVALID_MESSAGE.formatted("name"));
        Validate.notNull(race, INVALID_MESSAGE.formatted("power stats"));
        Validate.notNull(powerStats, INVALID_MESSAGE.formatted("race"));

        this.name = name;
        this.race = race;
        this.powerStats = powerStats;
    }

    public Hero(HeroId heroId, String name, Race race, PowerStats powerStats) {
        super(heroId);
        validateAllFields(name, race, powerStats);
        this.name = name;
        this.race = race;
        this.powerStats = powerStats;
    }

    public void setName(String name) {
       validateName(name);
        this.name = name;
    }

    public boolean updateHero(String name, Race race, PowerStats powerStats){
        validateAllFields(name, race, powerStats);
        if(name.equals(this.name) &&
                race.equals(this.race) &&
                powerStats.equals(this.powerStats)){
            return false;
        }
        if(!name.equals(this.name)){
            setName(name);
        }
        if(!race.equals(this.race)){
            setRace(race);
        }
        if(!powerStats.equals(this.powerStats)){
            this.powerStats.updateStats(powerStats);
        }
        return true;
    }

    public Map<String, Integer> compareStats(PowerStats otherHeroStats){
        Map<String, Integer> resultDifferenceMap = new HashMap<>();
        Strength strength = this.powerStats.compareStrength(otherHeroStats.getStrength());
        Agility agility = this.powerStats.compareAgility(otherHeroStats.getAgility());
        Intelligence intelligence = this.powerStats.compareIntelligence(otherHeroStats.getIntelligence());
        Dexterity dexterity = this.powerStats.compareDexterity(otherHeroStats.getDexterity());
        resultDifferenceMap.put("strength", strength.getValue());
        resultDifferenceMap.put("agility", agility.getValue());
        resultDifferenceMap.put("intelligence", intelligence.getValue());
        resultDifferenceMap.put("dexterity", dexterity.getValue());
        return resultDifferenceMap;
    }


    private void validateAllFields(String name, Race race, PowerStats powerStats){
        validateName(name);
        validateRace(race);
        validatePowerStats(powerStats);
    }

    private void validateName(String name){
        Validate.notBlank(name, INVALID_MESSAGE.formatted("name"));
    }

    private void validateRace(Race race){
        Validate.notNull(race, INVALID_MESSAGE.formatted("race"));
    }

    private void validatePowerStats(PowerStats powerStats){
        Validate.notNull(powerStats, INVALID_MESSAGE.formatted("power stats"));
    }

}
