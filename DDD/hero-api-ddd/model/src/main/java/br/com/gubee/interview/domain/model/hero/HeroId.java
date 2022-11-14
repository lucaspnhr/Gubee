package br.com.gubee.interview.domain.model.hero;

import br.com.gubee.interview.domain.shared.ValueObject;
import lombok.Getter;
import org.apache.commons.lang.Validate;

import java.util.Objects;
import java.util.UUID;

public class HeroId implements ValueObject<HeroId> {

    @Getter
    private final UUID id;

    public HeroId() {
        id = UUID.randomUUID();
    }

    public HeroId(final UUID id) {
        this.id = id;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HeroId heroId = (HeroId) o;
        return sameValueAs(heroId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean sameValueAs(HeroId other) {
        return id.equals(other.id);
    }

}
