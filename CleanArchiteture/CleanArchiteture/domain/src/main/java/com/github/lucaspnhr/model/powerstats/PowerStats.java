package com.github.lucaspnhr.model.powerstats;

import com.github.lucaspnhr.model.share.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.apache.commons.lang3.Validate;

import java.time.Instant;

import static com.github.lucaspnhr.model.share.util.Constants.INVALID_MESSAGE;

@Getter
@Setter(AccessLevel.PRIVATE)
public class PowerStats extends Entity<PowerStatsId> {

    private Strength strength;
    private Intelligence intelligence;
    private Agility agility;
    private Dexterity dexterity;

    public PowerStats(PowerStatsId powerStatsId, Instant createdAt, Instant updatedAt, Strength strength, Intelligence intelligence, Agility agility, Dexterity dexterity) {
        super(powerStatsId, createdAt, updatedAt);
        Validate.notNull(strength, INVALID_MESSAGE.formatted("strength"));
        Validate.notNull(intelligence, INVALID_MESSAGE.formatted("intelligence"));
        Validate.notNull(agility, INVALID_MESSAGE.formatted("agility"));
        Validate.notNull(dexterity, INVALID_MESSAGE.formatted("dexterity"));
        this.strength = strength;
        this.intelligence = intelligence;
        this.agility = agility;
        this.dexterity = dexterity;
    }

    public void updateStats(@NonNull PowerStats powerStats) {
        powerStats.setAgility(powerStats.agility);
        powerStats.setIntelligence(powerStats.intelligence);
        powerStats.setStrength(powerStats.strength);
        powerStats.setDexterity(powerStats.dexterity);
    }


}
