package br.com.gubee.interview.model.request;

import br.com.gubee.interview.model.Hero;
import br.com.gubee.interview.model.PowerStats;
import br.com.gubee.interview.model.enums.Race;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RetrieveHeroRequest {
    private UUID id;
    private String name;
    private Race race;
    private int strength;
    private int agility;
    private int dexterity;
    private int intelligence;

    public RetrieveHeroRequest(Hero hero, PowerStats powerStats){
        this.setId(hero.getId());
        this.setName(hero.getName());
        this.setRace(hero.getRace());
        this.setStrength(powerStats.getStrength());
        this.setAgility(powerStats.getAgility());
        this.setDexterity(powerStats.getDexterity());
        this.setIntelligence(powerStats.getIntelligence());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RetrieveHeroRequest that = (RetrieveHeroRequest) o;
        return strength == that.strength && agility == that.agility && dexterity == that.dexterity && intelligence == that.intelligence && Objects.equals(id, that.id) && Objects.equals(name, that.name) && race == that.race;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, race, strength, agility, dexterity, intelligence);
    }
}
