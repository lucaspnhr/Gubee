package br.com.gubee.interview.domain.model.hero;

import br.com.gubee.interview.domain.model.powerstats.PowerStats;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang.Validate;
import br.com.gubee.interview.domain.model.hero.enums.Race;
import br.com.gubee.interview.domain.shared.Entity;

import java.time.Instant;
import java.util.Objects;

@Builder
@AllArgsConstructor
@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor()
public class Hero extends Entity<HeroId> {
    private String name;
    private Race race;
    private PowerStats powerStats;
    private Instant createdAt;
    private Instant updatedAt;
    private boolean enabled;

    public Hero(HeroId heroId, String name, Race race, PowerStats powerStats) {
        super(heroId);
        Validate.notEmpty(name);
        Validate.notNull(race);
        this.name = name;
        this.race = race;
        this.enabled = true;
        this.powerStats = powerStats;
        this.createdAt = this.updatedAt = Instant.now();
    }

    public Hero(HeroId heroId,
                String name,
                Race race,
                PowerStats powerStats,
                boolean enabled,
                Instant createdAt,
                Instant updatedAt) {
        super(heroId);
        Validate.notEmpty(name);
        Validate.notNull(race);
        Validate.notNull(powerStats);
        Validate.notNull(createdAt);
        Validate.notNull(updatedAt);
        Validate.isTrue(createdAt.isBefore(updatedAt) || createdAt.equals(updatedAt));
        this.name = name;
        this.race = race;
        this.enabled = enabled;
        this.powerStats = powerStats;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public void update(String name, Race race) {
        Validate.notNull(name);
        Validate.notEmpty(name);
        Validate.notNull(race);
        this.setName(name);
        this.setRace(race);
        this.setUpdatedAt(Instant.now());
    }
    public void update(String name) {
        Validate.notNull(name);
        Validate.notEmpty(name);
        this.setName(name);
        this.setUpdatedAt(Instant.now());
    }
    public void update(Race race) {
        Validate.notNull(race);
        this.setRace(race);
        this.setUpdatedAt(Instant.now());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hero hero = (Hero) o;
        return sameIdentityAs(hero.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
