package br.com.gubee.interview.core.features.hero;

import br.com.gubee.interview.core.exception.customException.HeroAlredyExistsException;
import br.com.gubee.interview.core.features.powerstats.PowerStatsService;
import br.com.gubee.interview.model.Hero;
import br.com.gubee.interview.model.PowerStats;
import br.com.gubee.interview.model.enums.Race;
import br.com.gubee.interview.model.request.CreateHeroRequest;
import br.com.gubee.interview.model.request.RetrieveHeroRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class HeroServiceTest {

    @Mock
    private HeroRepository heroRepository;

    @Mock
    private PowerStatsService powerStatsService;

    private HeroService underTest;


    @BeforeEach
    void setUp() {
        this.underTest = new HeroService(heroRepository, powerStatsService);
    }

    @Test
    void shouldAddHeroToDatabaseAndReturnItsId(){
        final var heroToAdd = createHeroRequest();
        given(heroRepository.alreadyExists(anyString())).willReturn(false);
        given(heroRepository.create(any())).willReturn(UUID.randomUUID());

        UUID id = underTest.create(heroToAdd);

        assertThat(id).isInstanceOf(UUID.class);
    }

    @Test
    void shouldThrowHeroAlredyExistsException(){
        final var heroToAdd = createHeroRequest();
        given(heroRepository.alreadyExists(anyString())).willReturn(true);

        assertThatThrownBy(() -> underTest.create(heroToAdd))
                .isInstanceOf(HeroAlredyExistsException.class)
                .hasMessageContaining(heroToAdd.getName());
    }

    @Test
    void shouldReturnRetrieveHeroRequest(){
        final var lanterna = retrieveHeroRequest();
        final var heroToReturn = Hero.builder().id(lanterna.getId()).name(lanterna.getName()).race(lanterna.getRace()).build();
        given(heroRepository.retriveById(lanterna.getId())).willReturn(Optional.of(heroToReturn));
        given(powerStatsService.retriveById(any())).willReturn(powerStats(lanterna));

        RetrieveHeroRequest result = underTest.retriveById(lanterna.getId());

        assertThat(result).isEqualTo(lanterna);
    }

    private PowerStats powerStats(RetrieveHeroRequest retrieveHeroRequest){
        return PowerStats.builder()
                .id(UUID.randomUUID())
                .agility(retrieveHeroRequest.getAgility())
                .dexterity(retrieveHeroRequest.getDexterity())
                .strength(retrieveHeroRequest.getStrength())
                .intelligence(retrieveHeroRequest.getIntelligence())
                .build();
    }

    private CreateHeroRequest createHeroRequest() {
        return CreateHeroRequest.builder()
                .name("Batman")
                .agility(5)
                .dexterity(8)
                .strength(6)
                .intelligence(10)
                .race(Race.HUMAN)
                .build();
    }
    private RetrieveHeroRequest retrieveHeroRequest() {
        return RetrieveHeroRequest.builder()
                .id(UUID.randomUUID())
                .name("Lanterna")
                .race(Race.HUMAN)
                .agility(6)
                .dexterity(7)
                .intelligence(4)
                .strength(8).build();
    }
}


