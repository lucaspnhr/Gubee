package br.com.gubee.interview.core.features.powerstats;

import br.com.gubee.interview.model.PowerStats;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

import static br.com.gubee.interview.core.features.util.constants.PowerStatsIdsByHero.*;
import static br.com.gubee.interview.core.features.util.constants.PowerStatsIdsByHero.POWER_STATS_BATMAN_ID;

public class PowerStatsRepositoryTestImpl implements PowerStatsRepositoryI {

    private List<PowerStats> POWER_STATS_REPOSITORY = new ArrayList<>();

    public PowerStatsRepositoryTestImpl() {
        this.POWER_STATS_REPOSITORY.add(powerStats(POWER_STATS_AQUAMEN_ID));
        this.POWER_STATS_REPOSITORY.add(powerStats(POWER_STATS_LANTERNA_VERMELHA_ID));
        this.POWER_STATS_REPOSITORY.add(powerStats(POWER_STATS_LANTERNA_VERDE_ID));
        this.POWER_STATS_REPOSITORY.add(powerStats(POWER_STATS_BATMAN_ID));
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
                .findFirst();
    }

    @Override
    public int update(PowerStats entityToUpdate) {
        POWER_STATS_REPOSITORY = POWER_STATS_REPOSITORY.stream()
                .peek((powerStats) -> {
                    if (powerStats.getId().equals(entityToUpdate.getId())){
                        powerStats.setAgility(entityToUpdate.getAgility());
                        powerStats.setStrength(entityToUpdate.getStrength());
                        powerStats.setIntelligence(entityToUpdate.getIntelligence());
                        powerStats.setDexterity(entityToUpdate.getDexterity());
                        powerStats.setUpdatedAt(Instant.now());
                    }
                }).collect(Collectors.toList());
        return 1;
    }

    @Override
    public int delete(UUID uuid) {
        POWER_STATS_REPOSITORY = POWER_STATS_REPOSITORY.stream()
                .filter(powerStats -> !powerStats.getId().equals(uuid))
                .collect(Collectors.toList());
        return 1;
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
