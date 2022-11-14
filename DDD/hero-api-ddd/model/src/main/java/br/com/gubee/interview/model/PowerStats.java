package br.com.gubee.interview.model;

import br.com.gubee.interview.model.request.CreateHeroRequest;
import br.com.gubee.interview.model.request.UpdateHeroRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;

@Data
@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor
@Builder
public class PowerStats {

    private UUID id;
    private int strength;
    private int agility;
    private int dexterity;
    private int intelligence;
    private Instant createdAt;
    private Instant updatedAt;

    public PowerStats(CreateHeroRequest createHeroRequest) {
        this.strength = createHeroRequest.getStrength();
        this.agility = createHeroRequest.getAgility();
        this.dexterity = createHeroRequest.getDexterity();
        this.intelligence = createHeroRequest.getIntelligence();
    }

    public void update(UpdateHeroRequest updateHeroRequest) {
        if (updateHeroRequest.getDexterity() >= 0){
            this.setDexterity(updateHeroRequest.getDexterity());
        }
        if(updateHeroRequest.getStrength() >= 0){
            this.setStrength(updateHeroRequest.getStrength());
        }
        if (updateHeroRequest.getIntelligence() >= 0) {
            this.setIntelligence(updateHeroRequest.getIntelligence());
        }
        if (updateHeroRequest.getAgility() >= 0) {
            this.setAgility(updateHeroRequest.getAgility());
        }
    }
}
