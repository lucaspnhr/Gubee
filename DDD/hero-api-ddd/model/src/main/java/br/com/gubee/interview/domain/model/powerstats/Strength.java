package br.com.gubee.interview.domain.model.powerstats;

import br.com.gubee.interview.domain.shared.ValueObject;
import lombok.Getter;
import org.apache.commons.lang.Validate;

import java.util.Objects;

public class Strength implements ValueObject<Strength> {

    @Getter
    private final int strength;


    public Strength(final int strength) {
        Validate.isTrue((strength >= 0 && strength <= 10));
        this.strength = strength;
    }

    public Strength updateStrength(int strength){
        return new Strength(strength);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Strength strength1 = (Strength) o;
        return sameValueAs(strength1);
    }

    @Override
    public int hashCode() {
        return Objects.hash(strength);
    }

    @Override
    public boolean sameValueAs(Strength other) {
        return strength == other.strength;
    }
}
