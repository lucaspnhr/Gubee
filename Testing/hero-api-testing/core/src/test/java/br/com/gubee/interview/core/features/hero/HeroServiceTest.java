package br.com.gubee.interview.core.features.hero;

import br.com.gubee.interview.core.exception.customException.HeroAlredyExistsException;
import br.com.gubee.interview.core.exception.customException.NotFoundHeroException;
import br.com.gubee.interview.core.features.powerstats.PowerStatsService;
import br.com.gubee.interview.core.features.powerstats.PowerStatsServiceFakeImpl;
import br.com.gubee.interview.model.enums.Race;
import br.com.gubee.interview.model.request.CreateHeroRequest;
import br.com.gubee.interview.model.request.RetrieveHeroRequest;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
public class HeroServiceTest {


    private HeroRepositoryI heroRepository = new HeroRepositoryFakeImpl();


    private final PowerStatsService powerStatsService = new PowerStatsServiceFakeImpl();

    private HeroService underTest;


    @BeforeEach
    void setUp() {

        this.underTest = new HeroService(heroRepository, powerStatsService);
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
        final var heroId = UUID.fromString("bda51c4a-583c-4b96-8923-860e28854058");

        RetrieveHeroRequest retrieveHeroRequest = underTest.retriveById(heroId);

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


