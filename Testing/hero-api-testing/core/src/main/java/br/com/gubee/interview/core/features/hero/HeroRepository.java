package br.com.gubee.interview.core.features.hero;

import br.com.gubee.interview.model.Hero;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
@Slf4j
public class HeroRepository implements HeroRepositoryI {

    private static final String RETRIVE_ALL_HEROS_BY_NAME_QUERY = "SELECT * FROM hero WHERE LOWER(name) LIKE LOWER('%pattern%')" ;
    private static final String RETRIVE_ALL_HERO_QUERY = "SELECT * FROM hero";
    private static final String CREATE_HERO_QUERY = "INSERT INTO hero" +
        " (name, race, power_stats_id)" +
        " VALUES (:name, :race, :powerStatsId) RETURNING id";
    private static final String RETRIVE_HERO_BY_ID_QUERY = "SELECT * FROM hero WHERE id = :id";
    private static final String UPDATE_HERO_QUERY = "UPDATE hero" +
            " SET name = :name, race = :race, updated_at = now()" +
            " WHERE id = :id";
    public static final String DELETE_HERO_BY_ID = "DELETE FROM hero WHERE id = :id";

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public boolean alreadyExists(String heroName){
        return !retriveByName(heroName).isEmpty();
    }

    @Override
    public UUID create(Hero hero) {
        final Map<String, Object> params = Map.of("name", hero.getName(),
            "race", hero.getRace().name(),
            "powerStatsId", hero.getPowerStatsId());
        log.info("Saving the desired hero to the database");
        return namedParameterJdbcTemplate.queryForObject(
            CREATE_HERO_QUERY,
            params,
            UUID.class);
    }

    @Override
    public Optional<Hero> retriveById(UUID heroId){
        final Map<String, Object> params = Map.of("id", heroId);
        try{
            return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(
                    RETRIVE_HERO_BY_ID_QUERY,
                    params,
                    new BeanPropertyRowMapper<>(Hero.class)
            ));
        }catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }

    @Override
    public List<Hero> retriveByName(String name){
            return namedParameterJdbcTemplate.query(
                    RETRIVE_ALL_HEROS_BY_NAME_QUERY.replace("pattern",name),
                    new BeanPropertyRowMapper<>(Hero.class)
            );
    }
    @Override
    public int update(Hero heroToUpdate){
        final Map<String, Object> params = Map.of("id", heroToUpdate.getId(),
                "name", heroToUpdate.getName(),
                "race", heroToUpdate.getRace().name());

        return namedParameterJdbcTemplate.update(
                UPDATE_HERO_QUERY,
                params
        );
    }

    @Override
    public int delete(UUID id){
        Map<String, UUID> param = Map.of("id", id);
        return namedParameterJdbcTemplate.update(
                DELETE_HERO_BY_ID,
                param
        );
    }


}
