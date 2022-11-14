package br.com.gubee.interview.domain.model.powerstats;

import br.com.gubee.interview.domain.shared.ValueObject;
import lombok.Getter;
import org.apache.commons.lang.Validate;

import java.util.Objects;

public class Intelligence implements ValueObject<Intelligence> {

    @Getter
    private final int intelligence;


    public Intelligence(final int intelligence) {
        Validate.isTrue((intelligence >= 0 && intelligence <= 10));
        this.intelligence = intelligence;
    }

    public Intelligence updateIntelligence(int intelligence){
        return new Intelligence(intelligence);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Intelligence intelligence1 = (Intelligence) o;
        return sameValueAs(intelligence1);
    }

    @Override
    public int hashCode() {
        return Objects.hash(intelligence);
    }

    @Override
    public boolean sameValueAs(Intelligence other) {
        return intelligence == other.intelligence;
    }
}
