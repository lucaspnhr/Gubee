package com.github.lucaspnhr.persistenceadapter.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "power_stats")
@Data
public class PowerStatsJPAEntity {
    @Id
    @Column(nullable = false)
    private UUID id;
    @Column(nullable = false)
    private int strength;
    @Column(nullable = false)
    private int agility;
    @Column(nullable = false)
    private int intelligence;
    @Column(nullable = false)
    private int dexterity;
    @Column(nullable = false)
    private Instant createdAt;
    @Column(nullable = false)
    private Instant updatedAt;

    public PowerStatsJPAEntity(UUID id, int strength, int agility, int intelligence, int dexterity, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.strength = strength;
        this.agility = agility;
        this.intelligence = intelligence;
        this.dexterity = dexterity;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public PowerStatsJPAEntity() {
    }
}
