package com.github.lucaspnhr.model.hero;

import com.github.lucaspnhr.model.share.ValueObject;

import java.util.Objects;
import java.util.UUID;

public class HeroId implements ValueObject<HeroId> {

    private final UUID id;

    public HeroId(UUID id) {
        this.id = id;
    }

    public HeroId() {
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
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
