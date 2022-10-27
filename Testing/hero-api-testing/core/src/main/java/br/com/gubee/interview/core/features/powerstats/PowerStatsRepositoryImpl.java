package br.com.gubee.interview.core.features.powerstats;

import br.com.gubee.interview.core.features.base.repository.BaseRepository;
import br.com.gubee.interview.model.PowerStats;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class PowerStatsRepositoryImpl implements BaseRepository<PowerStats, UUID>, PowerStatsRepository {

    private static final String CREATE_POWER_STATS_QUERY = "INSERT INTO power_stats" +
        " (strength, agility, dexterity, intelligence)" +
        " VALUES (:strength, :agility, :dexterity, :intelligence) RETURNING id";
    private static final String UPDATE_HERO_QUERY = "UPDATE power_stats" +
            " SET strength = :strength, agility = :agility, dexterity = :dexterity, intelligence = :intelligence, updated_at = now()" +
            " WHERE id = :id";
    private static final String RETRIVE_POWER_STATS_BY_ID_QUERY = "SELECT * FROM power_stats WHERE id = :id";
    public static final String DELETE_POWER_STATS_BY_ID = "DELETE FROM power_stats WHERE id = :id RETURNING id";
    public static final String DELETE_ALL = "DELETE FROM power_stats";
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public UUID create(PowerStats powerStats) {
        return namedParameterJdbcTemplate.queryForObject(
            CREATE_POWER_STATS_QUERY,
            new BeanPropertySqlParameterSource(powerStats),
            UUID.class);
    }

    @Override
    public Optional<PowerStats> retriveById(UUID id){
        final Map<String, Object> params = Map.of("id", id);
        try{
            return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(
                RETRIVE_POWER_STATS_BY_ID_QUERY,
                params,
                new BeanPropertyRowMapper<>(PowerStats.class)
        ));}
        catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }


    @Override
    public int update(PowerStats powerStats){
        final Map<String, Object> params = Map.of("id", powerStats.getId(),
                "strength", powerStats.getStrength(),
                "agility", powerStats.getAgility(),
                "dexterity", powerStats.getDexterity(),
                "intelligence", powerStats.getIntelligence());

        return namedParameterJdbcTemplate.update(
                UPDATE_HERO_QUERY,
                params
        );
    }

    @Override
    public int delete(UUID id){
        Map<String, UUID> param = Map.of("id", id);
        return namedParameterJdbcTemplate.update(
                DELETE_POWER_STATS_BY_ID,
                param
        );
    }

    @Override
    public void deleteAll() {
        namedParameterJdbcTemplate.update(
                DELETE_ALL,
                Collections.emptyMap()
        );
    }
}
