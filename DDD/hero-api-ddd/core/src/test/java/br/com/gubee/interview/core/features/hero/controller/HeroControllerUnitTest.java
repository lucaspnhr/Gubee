package br.com.gubee.interview.core.features.hero.controller;

import br.com.gubee.interview.core.features.hero.service.HeroService;
import br.com.gubee.interview.core.features.hero.service.HeroServiceStubImpl;
import br.com.gubee.interview.domain.model.hero.enums.Race;
import br.com.gubee.interview.domain.model.request.BattleHeroRequest;
import br.com.gubee.interview.domain.model.request.CreateHeroRequest;
import br.com.gubee.interview.domain.model.request.RetrieveHeroRequest;
import br.com.gubee.interview.domain.model.request.UpdateHeroRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static br.com.gubee.interview.core.features.util.constants.HeroIds.AQUAMEN_ID;
import static br.com.gubee.interview.core.features.util.constants.HeroIds.BATMAN_ID;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.http.HttpStatus.NO_CONTENT;

public class HeroControllerUnitTest {
    private HeroController heroController;

    @BeforeEach
    void setUp() {
        HeroService heroService = new HeroServiceStubImpl();
        heroController = new HeroController(heroService);
    }
    @Test
    void createHeroAndReturnResponseEntityWithItsId() {
        //given
        CreateHeroRequest createHeroRequest = CreateHeroRequest.builder()
                .name("Super-Mano")
                .race(Race.ALIEN)
                .agility(6)
                .strength(8)
                .build();
        //when
        ResponseEntity<String> returnResponseEntity = heroController.create(createHeroRequest);
        //then
        assertThat(returnResponseEntity.getHeaders().containsKey("Location")).isTrue();
        assertThat(returnResponseEntity.getHeaders().get("Location").isEmpty()).isFalse();
        assertThat(returnResponseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    void findHeroAndReturnRetirveHeroRequestObject() {
        //given
        //when
        ResponseEntity<RetrieveHeroRequest> responseEntity = heroController.findHeroById(BATMAN_ID);
        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isInstanceOf(RetrieveHeroRequest.class);
        assertThat(responseEntity.getBody()).hasNoNullFieldsOrProperties();
    }

    @Test
    void findAnyHeroWithNameContainingNameQuery() {
        //given
        final var queryName = "lan";
        //when
        ResponseEntity<List<RetrieveHeroRequest>> responseEntity = heroController.findHeroByName(queryName);
        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isInstanceOf(List.class);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().isEmpty()).isFalse();
    }
    @Test
    void returnEmptyListWhenNoHeroWithNameQuery() {
        //given
        final var queryName = "elastico";
        //when
        ResponseEntity<List<RetrieveHeroRequest>> responseEntity = heroController.findHeroByName(queryName);
        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isInstanceOf(List.class);
        assertThat(responseEntity.getBody()).isNotNull();
        assertThat(responseEntity.getBody().isEmpty()).isTrue();
    }
    @Test
    void findTwoHerosAndReturnsABattleHeroRequestObject() {
        //given
        //when
        ResponseEntity<BattleHeroRequest> responseEntity = heroController.compareToHeroes(BATMAN_ID, AQUAMEN_ID);
        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isInstanceOf(BattleHeroRequest.class);
        assertThat(responseEntity.getBody()).hasNoNullFieldsOrProperties();
    }

    @Test
    void takeIdAndUpdateHero() {
        //given
        UpdateHeroRequest updateHeroRequest = getUpdateHeroRequest();
        //when
        ResponseEntity<RetrieveHeroRequest> responseEntity = heroController.updateHero(BATMAN_ID, updateHeroRequest);
        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody()).isInstanceOf(RetrieveHeroRequest.class);
        assertThat(responseEntity.getBody()).hasNoNullFieldsOrProperties();
    }

    @Test
    void deleteHeroById() {
        //given
        //when
        ResponseEntity<String> responseEntity = heroController.deleteHeroById(BATMAN_ID);
        //then
        assertThat(responseEntity.getStatusCode()).isEqualTo(NO_CONTENT);
    }

    private static UpdateHeroRequest getUpdateHeroRequest() {
        return UpdateHeroRequest.builder()
                .name("Batman")
                .agility(3)
                .intelligence(6)
                .race(Race.ALIEN)
                .strength(1)
                .dexterity(8).build();
    }
}
