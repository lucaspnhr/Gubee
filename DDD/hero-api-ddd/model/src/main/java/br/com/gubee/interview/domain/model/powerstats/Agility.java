package br.com.gubee.interview.domain.model.powerstats;

import br.com.gubee.interview.domain.shared.ValueObject;
import lombok.Getter;
import org.apache.commons.lang.Validate;

import java.util.Objects;

public class Agility implements ValueObject<Agility> {

    @Getter
    private final int agility;


    public Agility(final int agility) {
        Validate.isTrue((agility >= 0 && agility <= 10));
        this.agility = agility;
    }

    public Agility updateAgility(int agility){
        return new Agility(agility);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Agility agility1 = (Agility) o;
        return sameValueAs(agility1);
    }

    @Override
    public int hashCode() {
        return Objects.hash(agility);
    }

    @Override
    public boolean sameValueAs(Agility other) {
        return agility == other.agility;
    }
}
