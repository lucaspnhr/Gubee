package br.com.gubee.interview.core.interfaces.hero.request;

import br.com.gubee.interview.domain.model.hero.enums.Race;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import static lombok.AccessLevel.PRIVATE;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PRIVATE)
public class UpdateHeroRequest {

    @NotBlank(message = "message.name.mandatory")
    @Length(min = 1, max = 255, message = "message.name.length")
    private String name;

    private Race race;

    @Min(value = 0, message = "message.powerstats.strength.min")
    @Max(value = 10, message = "message.powerstats.strength.max")
    private int strength = -1;

    @Min(value = 0, message = "message.powerstats.agility.min")
    @Max(value = 10, message = "message.powerstats.agility.max")
    private int agility = -1;

    @Min(value = 0, message = "message.powerstats.dexterity.min")
    @Max(value = 10, message = "message.powerstats.dexterity.max")
    private int dexterity = -1;

    @Min(value = 0, message = "message.powerstats.intelligence.min")
    @Max(value = 10, message = "message.powerstats.intelligence.max")
    private int intelligence = -1;

    public boolean hasAllStatsDefault(){
        return this.getDexterity() < 0 &&
                this.getStrength() < 0 &&
                this.getIntelligence() < 0 &&
                this.getAgility() < 0;
    }
}
