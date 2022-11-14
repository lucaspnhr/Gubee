package br.com.gubee.interview.core.features.hero.repository;

import br.com.gubee.interview.domain.model.hero.Hero;
import br.com.gubee.interview.domain.model.hero.HeroId;
import br.com.gubee.interview.domain.model.hero.HeroRepository;
import br.com.gubee.interview.domain.model.hero.enums.Race;
import br.com.gubee.interview.domain.model.powerstats.*;
import br.com.gubee.interview.model.PowerStats;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.*;

@Repository
@RequiredArgsConstructor
@Slf4j
public class HeroRepositoryImpl implements HeroRepository {

    private static final String RETRIVE_ALL_HEROS_BY_NAME_QUERY = "SELECT * FROM hero WHERE LOWER(name) LIKE LOWER('%pattern%')" ;
    private static final String RETRIVE_ALL_HERO_QUERY = "SELECT * FROM hero";
    private static final String CREATE_HERO_QUERY = "INSERT INTO hero" +
        " (name, race, power_stats_id)" +
        " VALUES (:name, :race, :powerStatsId) RETURNING id";
    private static final String RETRIVE_HERO_BY_ID_QUERY = "SELECT h.id AS hero_id, h.name, h.race,h.created_at AS hero_created, h.updated_at AS hero_updated," +
            "p.id AS power_id, p.strength, p.agility, p.dexterity, p.intelligence,p.created_at AS power_stats_created, p.updated_at AS power_stats_updated" +
            " FROM hero AS h " +
            "INNER JOIN power_stats AS p " +
            "ON h.power_stats_id = p.id " +
            "WHERE id = :id";
    private static final String UPDATE_HERO_QUERY = "UPDATE hero" +
            " SET name = :name, race = :race, updated_at = now()" +
            " WHERE id = :id";
    public static final String DELETE_HERO_BY_ID = "DELETE FROM hero WHERE id = :id";
    public static final String DELETE_ALL = "DELETE FROM hero";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public boolean alreadyExists(String heroName){
        return !findByName(heroName).isEmpty();
    }

    @Override
    public HeroId save(Hero hero) {
        final Map<String, Object> params = Map.of("name", hero.getName(),
            "race", hero.getRace().name(),
            "powerStatsId", hero.getPowerStats().getId());
        log.info("Saving the desired hero to the database");
        return namedParameterJdbcTemplate.queryForObject(
                CREATE_HERO_QUERY,
                params,
                (rs, rowNum) -> rowNum > 0 ? new HeroId(UUID.fromString(rs.getString("id"))) : null);

    }

    @Override
    public Optional<Hero> find(HeroId heroId){
        final Map<String, Object> params = Map.of("id", heroId);
        try{
            return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(
                    RETRIVE_HERO_BY_ID_QUERY,
                    params,
                    (rs, rows) -> {
                        var powerStats = getPowerStatsFromResultset(rs);
                        return new Hero(
                                new HeroId(UUID.fromString(rs.getString("hero_id"))),
                                rs.getString("name"),
                                Race.valueOf(rs.getString("race")),
                                powerStats,
                                rs.getBoolean("enabled"),
                                rs.getDate("hero_created").toInstant(),
                                rs.getDate("hero_updated").toInstant());
                    }
            ));
        }catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    @Override
    public List<Hero> findByName(String name){
            return namedParameterJdbcTemplate.query(
                    RETRIVE_ALL_HEROS_BY_NAME_QUERY.replace("pattern",name),
                    new BeanPropertyRowMapper<>(Hero.class)
            );
    }

    @Override
    public int delete(HeroId id){
        Map<String, UUID> param = Map.of("id", id.getId());
        return namedParameterJdbcTemplate.update(
                DELETE_HERO_BY_ID,
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

    private PowerStats getPowerStatsFromResultset(ResultSet rs) throws SQLException {
        var strenth = new Strength(rs.getInt("strength"));
        var powerStatsId = new PowerStatsId(UUID.fromString(rs.getString("power_id")));
        var intelligence = new Intelligence(rs.getInt("intelligence"));
        var agility = new Agility(rs.getInt("agility"));
        var dexterity = new Dexterity(rs.getInt("dexterity"));
        var createdAt = rs.getDate("power_stats_created").toInstant();
        var updatedAt = rs.getDate("power_stats_updated").toInstant();

        return new PowerStats(powerStatsId, strenth, agility, dexterity, intelligence, createdAt, updatedAt);

    }
}
