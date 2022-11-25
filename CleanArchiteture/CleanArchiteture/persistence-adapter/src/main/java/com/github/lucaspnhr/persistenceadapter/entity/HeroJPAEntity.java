package com.github.lucaspnhr.persistenceadapter.entity;

import com.github.lucaspnhr.outport.SaveHeroCommand;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name ="hero")
@Data
public class HeroJPAEntity {
    @Id
    @Column(name = "id", nullable = false)
    private UUID heroId;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String race;
    @Column(nullable = false)
    private UUID powerStatsId;
    @Column(nullable = false)
    private Instant createdAt;
    @Column(nullable = false)
    private Instant updatedAt;

    public HeroJPAEntity(UUID heroId, String name, String race, UUID powerStatsId, Instant createdAt, Instant updatedAt) {
        this.heroId = heroId;
        this.name = name;
        this.race = race;
        this.powerStatsId = powerStatsId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public HeroJPAEntity() {

    }

    public HeroJPAEntity(SaveHeroCommand saveHeroCommand) {
        this.heroId = saveHeroCommand.getHeroId();
        this.name = saveHeroCommand.getName();
        this.race = saveHeroCommand.getRace();
        this.powerStatsId = saveHeroCommand.getPowerStatsid();
        this.createdAt = saveHeroCommand.getHeroCreatedAt();
        this.updatedAt = saveHeroCommand.getHeroUpdatedAt();
    }

    public UUID getHeroId() {
        return heroId;
    }

    public void setHeroId(UUID heroId) {
        this.heroId = heroId;
    }
}
