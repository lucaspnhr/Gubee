package br.com.gubee.interview.domain.model.powerstats;

import br.com.gubee.interview.domain.shared.ValueObject;
import lombok.Getter;
import org.apache.commons.lang.Validate;

import java.util.Objects;

public class Dexterity implements ValueObject<Dexterity> {

    @Getter
    private final int dexterity;


    public Dexterity(final int dexterity) {
        Validate.isTrue((dexterity >= 0 && dexterity <= 10));
        this.dexterity = dexterity;
    }

    public Dexterity updateDexterity(int dexterity) {
        return new Dexterity(dexterity);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dexterity dexterity1 = (Dexterity) o;
        return sameValueAs(dexterity1);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dexterity);
    }

    @Override
    public boolean sameValueAs(Dexterity other) {
        return dexterity == other.dexterity;
    }
}
