package br.com.gubee.interview.core.features.hero;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

@JdbcTest
@Sql({"/sql/schema.sql"})
@ActiveProfiles("test")
class HeroRepositoryImplTest {

    @Autowired
    private HeroRepository heroRepository;


    @AfterEach
    void tearDown() {
        heroRepository.deleteAll();
    }

    @Test
    void create() {
    }
//
//    @Test
//    void retriveById() {
//    }
//
//    @Test
//    void retriveByName() {
//    }
//
//    @Test
//    void updateHero() {
//    }
//
//    @Test
//    void delete() {
//    }
}