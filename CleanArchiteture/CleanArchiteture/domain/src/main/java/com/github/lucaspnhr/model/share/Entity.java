package com.github.lucaspnhr.model.share;

import org.apache.commons.lang3.Validate;

import java.time.Instant;
import java.util.Objects;

import static com.github.lucaspnhr.model.share.util.Constants.INVALID_MESSAGE;

public abstract class Entity<ID> {

    private final ID id;

    private final Instant createdAt;
    private Instant updatedAt;

    public Entity(ID id, Instant createdAt, Instant updatedAt) {
        Validate.notNull(createdAt, INVALID_MESSAGE.formatted("created at"));
        Validate.notNull(updatedAt, INVALID_MESSAGE.formatted("updated at"));
        Validate.isTrue(!updatedAt.isBefore(createdAt), "updated at cannot be before created");
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        Validate.notNull(id);
        this.id = id;
    }

    public Entity(ID id) {
        this.id = id;
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    public ID getId() {
        return id;
    }

    public void setUpdatedAt(Instant updatedAt) {
        Validate.isTrue(!updatedAt.isBefore(createdAt), "updated at cannot be before created");
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entity<ID> entity = (Entity<ID>) o;
        return sameIdentity(entity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    boolean sameIdentity(ID otherId){
        return this.id.equals(otherId);
    }
}
