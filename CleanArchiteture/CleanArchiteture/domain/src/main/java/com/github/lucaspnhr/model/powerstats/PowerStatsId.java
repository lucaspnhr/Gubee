package com.github.lucaspnhr.model.powerstats;

import java.util.UUID;

public class PowerStatsId {

    private final UUID id;

    public PowerStatsId(UUID id) {
        this.id = id;
    }

    public PowerStatsId() {
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }
}
