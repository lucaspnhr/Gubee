package br.com.gubee.interview.core.features.powerstats.repository;

import br.com.gubee.interview.core.features.powerstats.repository.PowerStatsRepository;
import br.com.gubee.interview.model.PowerStats;

import java.time.Instant;
import java.util.*;

import static br.com.gubee.interview.core.features.util.constants.PowerStatsIdsByHero.*;
import static br.com.gubee.interview.core.features.util.constants.PowerStatsIdsByHero.POWER_STATS_BATMAN_ID;

public class PowerStatsRepositoryStubImpl implements PowerStatsRepository {

    private List<PowerStats> POWER_STATS_REPOSITORY = new ArrayList<>();

    public PowerStatsRepositoryStubImpl() {
        this.POWER_STATS_REPOSITORY.add(powerStats(PowerStatsIdsByHero.POWER_STATS_AQUAMEN_ID));
        this.POWER_STATS_REPOSITORY.add(powerStats(PowerStatsIdsByHero.POWER_STATS_LANTERNA_VERMELHA_ID));
        this.POWER_STATS_REPOSITORY.add(powerStats(PowerStatsIdsByHero.POWER_STATS_LANTERNA_VERDE_ID));
        this.POWER_STATS_REPOSITORY.add(powerStats(PowerStatsIdsByHero.POWER_STATS_BATMAN_ID));
    }

    @Override
    public UUID create(PowerStats entity) {
        entity.setId(UUID.randomUUID());
        POWER_STATS_REPOSITORY.add(entity);
        return entity.getId();
    }

    @Override
    public Optional<PowerStats> retriveById(UUID uuid) {
        return POWER_STATS_REPOSITORY.stream()
                .filter(powerStats -> powerStats.getId().equals(uuid))
                .map(powerStats -> new PowerStats(powerStats.getId(),
                        powerStats.getStrength(),
                        powerStats.getAgility(),
                        powerStats.getDexterity(),
                        powerStats.getIntelligence(),
                        powerStats.getCreatedAt(),
                        powerStats.getUpdatedAt()))
                .findFirst();
    }

    @Override
    public int update(PowerStats powerStatsToUpdate) {
        PowerStats powerStatsToBeUpdated = POWER_STATS_REPOSITORY.stream()
                .filter(powerStats -> powerStats.getId().equals(powerStatsToUpdate.getId()))
                .map(powerStats -> new PowerStats(powerStats.getId(),
                        powerStats.getStrength(),
                        powerStats.getAgility(),
                        powerStats.getDexterity(),
                        powerStats.getIntelligence(),
                        powerStats.getCreatedAt(),
                        powerStats.getUpdatedAt()))
                .findFirst().get();

        POWER_STATS_REPOSITORY.remove(powerStatsToBeUpdated);

        powerStatsToBeUpdated.setAgility(powerStatsToUpdate.getAgility());
        powerStatsToBeUpdated.setStrength(powerStatsToUpdate.getStrength());
        powerStatsToBeUpdated.setDexterity(powerStatsToUpdate.getDexterity());
        powerStatsToBeUpdated.setIntelligence(powerStatsToUpdate.getIntelligence());
        powerStatsToBeUpdated.setUpdatedAt(Instant.now());

        POWER_STATS_REPOSITORY.add(powerStatsToBeUpdated);
        return 1;
    }

    @Override
    public int delete(UUID id) {
        PowerStats powerStatsToBeDeleted = POWER_STATS_REPOSITORY.stream()
                .filter(powerStats-> powerStats.getId().equals(id))
                .findFirst().get();
        POWER_STATS_REPOSITORY.remove(powerStatsToBeDeleted);
        return 1;
    }

    @Override
    public void deleteAll() {
        POWER_STATS_REPOSITORY.clear();
    }

    private static PowerStats powerStats(UUID id){
        Random r = new Random();

        return PowerStats.builder()
                .id(id)
                .agility(r.nextInt(10))
                .dexterity(r.nextInt(10))
                .strength(r.nextInt(10))
                .intelligence(r.nextInt(10))
                .updatedAt(Instant.now())
                .createdAt(Instant.now())
                .build();
    }
}
