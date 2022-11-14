package br.com.gubee.interview.core.features.powerstats.repository;

import br.com.gubee.interview.core.features.shared.repository.BaseRepository;
import br.com.gubee.interview.domain.model.powerstats.PowerStats;

import java.util.UUID;

public interface PowerStatsRepository extends BaseRepository<PowerStats, UUID> {
}
