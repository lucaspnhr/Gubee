package br.com.gubee.interview.model.request;

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
public class RetriveHeroRequest {
    private UUID id;
    private String name;
    private Race race;
    private int strength;
    private int agility;
    private int dexterity;
    private int intelligence;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RetriveHeroRequest that = (RetriveHeroRequest) o;
        return strength == that.strength && agility == that.agility && dexterity == that.dexterity && intelligence == that.intelligence && Objects.equals(id, that.id) && Objects.equals(name, that.name) && race == that.race;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, race, strength, agility, dexterity, intelligence);
    }
}
