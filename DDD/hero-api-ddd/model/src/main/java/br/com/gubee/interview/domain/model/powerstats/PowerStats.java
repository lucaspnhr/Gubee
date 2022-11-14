package br.com.gubee.interview.domain.model.powerstats;

import br.com.gubee.interview.domain.model.request.CreateHeroRequest;
import br.com.gubee.interview.domain.model.request.UpdateHeroRequest;
import br.com.gubee.interview.domain.shared.Entity;
import lombok.*;
import org.apache.commons.lang.Validate;

import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor
@Getter
@Setter(PRIVATE)
@Builder
public class PowerStats extends Entity<PowerStatsId> {
    private Strength strength;
    private Agility agility;
    private Dexterity dexterity;
    private Intelligence intelligence;
    private Instant createdAt;
    private Instant updatedAt;

    public PowerStats(PowerStatsId powerStatsId,
            Strength strength,
                      Agility agility,
                      Dexterity dexterity,
                      Intelligence intelligence) {
        super(powerStatsId);
        Validate.notNull(strength);
        Validate.notNull(agility);
        Validate.notNull(dexterity);
        Validate.notNull(intelligence);
        this.strength = strength;
        this.agility = agility;
        this.dexterity = dexterity;
        this.intelligence = intelligence;
        this.createdAt = this.updatedAt = Instant.now();
    }

    public PowerStats(PowerStatsId powerStatsId,
                      Strength strength,
                      Agility agility,
                      Dexterity dexterity,
                      Intelligence intelligence,
                      Instant createdAt,
                      Instant updatedAt) {
        super(powerStatsId);
        Validate.notNull(strength);
        Validate.notNull(agility);
        Validate.notNull(dexterity);
        Validate.notNull(intelligence);
        Validate.notNull(createdAt);
        Validate.notNull(updatedAt);
        Validate.isTrue(createdAt.isBefore(updatedAt) || createdAt.equals(updatedAt));
        this.strength = strength;
        this.agility = agility;
        this.dexterity = dexterity;
        this.intelligence = intelligence;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void update(Strength strength,
                       Agility agility,
                       Dexterity dexterity,
                       Intelligence intelligence) {
        Validate.notNull(strength);
        Validate.notNull(agility);
        Validate.notNull(dexterity);
        Validate.notNull(intelligence);
        this.setDexterity(dexterity);
        this.setStrength(strength);
        this.setIntelligence(intelligence);
        this.setAgility(agility);
        this.updatedAt = Instant.now();
    }

    public void update(Dexterity dexterity) {
        Validate.notNull(dexterity);
       this.setDexterity(dexterity);
       this.updatedAt = Instant.now();
    }
    public void update(Strength strength) {
        Validate.notNull(strength);
      this.setStrength(strength);
      this.updatedAt = Instant.now();
    }
    public void update(Agility agility){
        Validate.notNull(agility);
        this.setAgility(agility);
        this.updatedAt = Instant.now();
    }
    public void update(Intelligence intelligence){
        Validate.notNull(intelligence);
        this.setIntelligence(intelligence);
        this.updatedAt = Instant.now();
    }

}
