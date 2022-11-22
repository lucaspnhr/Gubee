package br.com.gubee.interview.core.infrastructure.persistence.jdbc;

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
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Repository
@RequiredArgsConstructor
@Slf4j
public class HeroRepositoryJdbcImpl implements HeroRepository {

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
    public Optional<Hero> find(HeroId heroId) {
        return Optional.empty();
    }

    @Override
    public List<Hero> findAll() {
        return null;
    }

    @Override
    public List<Hero> findByName(String query) {
        return null;
    }

    @Override
    public HeroId save(Hero hero) {
        return null;
    }

    @Override
    public int delete(HeroId heroId) {
        return 0;
    }

    @Override
    public boolean alreadyExists(String name) {
        return false;
    }
}
