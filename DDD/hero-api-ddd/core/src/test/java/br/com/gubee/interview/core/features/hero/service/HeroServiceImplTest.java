package br.com.gubee.interview.core.features.hero.service;

import br.com.gubee.interview.core.application.impl.HeroServiceImpl;
import br.com.gubee.interview.core.application.HeroService;
import br.com.gubee.interview.core.application.util.exception.HeroAlredyExistsException;
import br.com.gubee.interview.core.application.util.exception.NotFoundHeroException;
import br.com.gubee.interview.core.features.hero.repository.HeroRepositoryStubImpl;
import br.com.gubee.interview.core.infrastructure.persistence.jdbc.PowerStatsRepositoryStubImpl;
import br.com.gubee.interview.core.application.PowerStatsService;
import br.com.gubee.interview.core.application.impl.PowerStatsServiceStubImpl;
import br.com.gubee.interview.domain.model.hero.enums.Race;
import br.com.gubee.interview.domain.model.request.CreateHeroRequest;
import br.com.gubee.interview.domain.model.request.RetrieveHeroRequest;
import br.com.gubee.interview.domain.model.request.UpdateHeroRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static br.com.gubee.interview.core.features.util.constants.HeroIds.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;


public class HeroServiceImplTest {


    private HeroService underTest;


    @BeforeEach
    void setUp() {
        HeroRepository heroRepository = new HeroRepositoryStubImpl();
        PowerStatsService powerStatsService = new PowerStatsServiceStubImpl(new PowerStatsRepositoryStubImpl());
        this.underTest = new HeroServiceImpl(heroRepository, powerStatsService);
    }

    @Test
    void shouldCreateHeroAndReturnItsId(){
        UUID heroId = underTest.create(createHeroRequest());

        assertThat(heroId).isNotNull();
    }
    @Test
    void shouldThrowHeroAlredyExistsExceptionWhenHeroNameAlreadyInDB(){
        CreateHeroRequest heroRequest = createHeroRequest();
        heroRequest.setName("Batman");
        assertThatThrownBy(() ->underTest.create(heroRequest))
                .isInstanceOf(HeroAlredyExistsException.class)
                .hasMessageContaining(heroRequest.getName());
    }

    @Test
    void shouldRetriveHeroFromDBWhenExists(){

        RetrieveHeroRequest retrieveHeroRequest = underTest.retriveById(AQUAMEN_ID);

        assertThat(retrieveHeroRequest).isNotNull();
        assertThat(retrieveHeroRequest).hasNoNullFieldsOrProperties();
    }

    @Test
    void shouldThrowHeroNotFounExceptionWhenHeroNotExistsInDB(){
        final var heroId = UUID.fromString("bda51c4a-583c-4b96-8923-860e28854628");

         assertThatThrownBy(() -> underTest.retriveById(heroId))
                 .isInstanceOf(NotFoundHeroException.class)
                 .hasMessageContaining(heroId.toString());
    }

    @Test
    void shouldReturnListOfHeroWhenNameMatched(){
        final var heroName = "Lan";

        List<RetrieveHeroRequest> retrieveHeroRequests = underTest.retriveByName(heroName);

        assertThat(retrieveHeroRequests.isEmpty()).isFalse();
    }

    @Test
    void shouldreturnemptyListWhenNameIsEmpty(){
        final var heroName = "";

        List<RetrieveHeroRequest> retrieveHeroRequests = underTest.retriveByName(heroName);

        assertThat(retrieveHeroRequests.isEmpty()).isTrue();
    }

    @Test
    void shouldreturnemptyListWhenNameHasNoMatch(){
        final var heroName = "Flash";

        List<RetrieveHeroRequest> retrieveHeroRequests = underTest.retriveByName(heroName);

        assertThat(retrieveHeroRequests.isEmpty()).isTrue();
    }

    @Test
    void shouldUpdateHeroAndReturnIt(){
        final var heroId = LANTERNA_VERDE_ID;
        final var oldHero = underTest.retriveById(heroId);
        final var updateHeroRequest = UpdateHeroRequest.builder()
                .name("Lanterna-Verde")
                .build();

        RetrieveHeroRequest retrieveHeroRequest = underTest.update(heroId, updateHeroRequest);

        assertThat(retrieveHeroRequest).isNotEqualTo(oldHero);
        assertThat(retrieveHeroRequest.getName()).isEqualTo(updateHeroRequest.getName());
    }

    @Test
    void shouldDeleteHeroFromDB(){
        final var heroId = BATMAN_ID;

        underTest.deleteById(heroId);

        assertThatThrownBy(() -> underTest.retriveById(heroId))
                .isInstanceOf(NotFoundHeroException.class);
    }



    private CreateHeroRequest createHeroRequest() {
        return CreateHeroRequest.builder()
                .name("Mulher Maravilha")
                .agility(5)
                .dexterity(8)
                .strength(6)
                .intelligence(10)
                .race(Race.HUMAN)
                .build();
    }
}


