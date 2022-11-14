package br.com.gubee.interview.domain.model.powerstats;

import br.com.gubee.interview.domain.shared.ValueObject;
import lombok.Getter;

import java.util.Objects;
import java.util.UUID;

public class PowerStatsId implements ValueObject<PowerStatsId> {
    @Getter
    private UUID id;

    public PowerStatsId() {
        this.id = UUID.randomUUID();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PowerStatsId that = (PowerStatsId) o;
        return sameValueAs(that);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean sameValueAs(PowerStatsId other) {
        return id.equals(other.id);
    }
}
