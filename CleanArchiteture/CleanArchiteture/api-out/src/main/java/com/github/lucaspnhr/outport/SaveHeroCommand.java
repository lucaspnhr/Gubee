package com.github.lucaspnhr.outport;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
public class SaveHeroCommand {
    private UUID heroId;
    private UUID powerStatsid;
    private String name;
    private String race;
    private int strength;
    private int agility;
    private int intelligence;
    private int dexterity;
    private Instant powerStatsCreatedAt;
    private Instant powerStatsUpdatedAt;
    private Instant heroCreatedAt;
    private Instant heroUpdatedAt;
}
