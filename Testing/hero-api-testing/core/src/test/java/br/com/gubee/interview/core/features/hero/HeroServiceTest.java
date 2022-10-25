package br.com.gubee.interview.core.features.hero;

import br.com.gubee.interview.core.exception.customException.HeroAlredyExistsException;
import br.com.gubee.interview.core.exception.customException.NotFoundHeroException;
import br.com.gubee.interview.core.features.powerstats.PowerStatsService;
import br.com.gubee.interview.model.Hero;
import br.com.gubee.interview.model.PowerStats;
import br.com.gubee.interview.model.enums.Race;
import br.com.gubee.interview.model.request.CreateHeroRequest;
import br.com.gubee.interview.model.request.RetriveHeroRequest;
import br.com.gubee.interview.model.request.UpdateHeroRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
public class HeroServiceTest {

    @Mock
    private HeroRepository heroRepository;

    @Mock
    private PowerStatsService powerStatsService;

    @InjectMocks
    private HeroService heroService;


    @Test
    void ItShouldSaveHeroToDatabaseAndResturnsItsId(){
        // Given

        final var createHeroRequest = createHeroRequest();
        final var powerStatsId = UUID.randomUUID(); //createPowerStats(createHeroRequest);
        final var heroId = UUID.randomUUID();//createHero(createHeroRequest, powerStatsId.getId());

        // When

        //When PowerStatsService's method create is called then it returns the created UUID
        when(powerStatsService.create(any(PowerStats.class))).thenReturn(powerStatsId);
        //When HeroRepository's method create is called then it returns the created UUID
        when(heroRepository.create(any(Hero.class))).thenReturn(heroId);

        UUID heroReturnedId = heroService.create(createHeroRequest);

        // Then

        assertThat(heroReturnedId).isNotNull();
        verify(heroRepository, times(1)).create(any(Hero.class));
        verify(heroRepository, times(1)).alreadyExists(anyString());
        verify(powerStatsService, times(1)).create(any(PowerStats.class));
        verifyNoMoreInteractions(heroRepository);
        verifyNoMoreInteractions(powerStatsService);
    }

    @Test
    void itShouldThrowHeroAlredyExistsExceptionWhenNameAlredyInDb(){
        // Given

        final var createHeroRequest = createHeroRequest();

        given(heroRepository.alreadyExists(anyString())).willReturn(true);
        // When
        // Then

        assertThatThrownBy(()-> heroService.create(createHeroRequest))
                .isInstanceOf(HeroAlredyExistsException.class)
                        .hasMessageContaining(createHeroRequest.getName());

        verify(heroRepository, times(1)).alreadyExists(anyString());
        verify(powerStatsService, never()).create(any(PowerStats.class));
        verifyNoMoreInteractions(heroRepository);
    }


    @Test
    void itShouldRetriveAHeroAndStatsUsingItsId(){
        // Given

        final var createHeroRequest = createHeroRequest();
        final var powerStats = createPowerStats(createHeroRequest);
        final var hero = createHero(createHeroRequest, powerStats.getId());
        final var heroId = hero.getId();
        final var powerStatsId = powerStats.getId();

        // When

        when(heroRepository.retriveById(heroId)).thenReturn(Optional.of(hero));
        when(powerStatsService.retriveById(powerStatsId)).thenReturn(powerStats);

        RetriveHeroRequest retrivedHero = heroService.retriveById(heroId);

        // Then

        assertThat(retrivedHero).isNotNull();
        assertThat(retrivedHero.getAgility()).isEqualTo(powerStats.getAgility());

        verify(heroRepository, times(1)).retriveById(any(UUID.class));
        verify(powerStatsService, times(1)).retriveById(any(UUID.class));
        verifyNoMoreInteractions(heroRepository);
        verifyNoMoreInteractions(powerStatsService);
    }



    @Test
    @DisplayName("When hero not found throw NotFoundHeroException")
    void itShouldThrowANotFoundHeroExceptionIfOptionalHeroIsEmpty(){
        // Given

        final var hero = createHero(createHeroRequest(),UUID.randomUUID());
        final var heroId = hero.getId();

        given(heroRepository.retriveById(heroId)).willReturn(Optional.empty());
        // When
        // Then

        assertThatExceptionOfType(NotFoundHeroException.class).isThrownBy(() -> {
            heroService.retriveById(heroId);
        }).withMessageContaining(hero.getId().toString());
    }

    @Test
    void itShouldReturnListOfHeroThatContainCertainName(){
        // Given

        final var powerStats = createPowerStats(createHeroRequest());
        // When

        when(heroRepository.retriveByName(anyString())).thenReturn(List.of(createHero(createHeroRequest(),powerStats.getId())));
        when(powerStatsService.retriveById(any(UUID.class))).thenReturn(powerStats);

        final var resultListOfHeroes = heroService.retriveByName("Batman");
        // Then

        System.out.println(resultListOfHeroes);
        assertThat(resultListOfHeroes).isNotEmpty();

        verify(heroRepository, atLeast(1)).retriveByName(anyString());
        verify(powerStatsService, atLeast(1)).retriveById(any(UUID.class));
        verifyNoMoreInteractions(heroRepository);
        verifyNoMoreInteractions(powerStatsService);
    }

    @Test
    void itShouldReturnEmptyListIfNotHeroWithNameIsFound(){
        // When

        when(heroRepository.retriveByName(anyString())).thenReturn(Collections.emptyList());

        final var resultListOfHeroes = heroService.retriveByName("Batman");
        // Then

        assertThat(resultListOfHeroes).isEmpty();

        verify(heroRepository, atLeast(1)).retriveByName(anyString());
        verify(powerStatsService, never()).retriveById(any(UUID.class));
        verifyNoMoreInteractions(heroRepository);
    }

    @Test
    void itShouldReturnEmptyListIfNameIsEmpty(){
        // When

        final var resultListOfHeroes = heroService.retriveByName("");
        // Then

        assertThat(resultListOfHeroes).isEmpty();

        verify(heroRepository, never()).retriveByName(anyString());
        verify(powerStatsService, never()).retriveById(any(UUID.class));
    }

    @Test
    void GivenAnUpdateHeroRequestObjectAndidShouldUpdateThatHero() {
        //Given
        final var updateHeroObj = updateHeroRequest();
        final var powerStats = createPowerStats(createHeroRequest());
        final var oldHero = createHero(createHeroRequest(), powerStats.getId());
        final var heroId = oldHero.getId();
        final var updatedHero = updateHeroRequestToHero(updateHeroObj,heroId);

        given(heroRepository.retriveById(any(UUID.class))).willReturn(Optional.of(oldHero));
        given(powerStatsService.retriveById(any(UUID.class))).willReturn(powerStats);
        given(powerStatsService.update(eq(updateHeroObj), any(UUID.class))).willReturn(1);
        given(heroRepository.updateHero(any())).willReturn(1);

        //When
        RetriveHeroRequest retriveHero = heroService.update(heroId, updateHeroObj);
        //Then
        assertThat(retriveHero).isNotNull();
        assertThat(retriveHero).isEqualTo(createRetriveHero(updatedHero, powerStats));
        verify(heroRepository).retriveById(any());
        verify(heroRepository).updateHero(any());
        verify(powerStatsService).retriveById(any());
        verify(powerStatsService).update(any(), any());
    }

    @Test
    void GivenAnUpdateHeroRequestObjectAndIdWhenNotUpdatedShouldReturnSameHero() {
        //Given
        final var updateHeroObj = updateHeroRequest();
        final var powerStats = createPowerStats(createHeroRequest());
        final var oldHero = createHero(createHeroRequest(), powerStats.getId());
        final var heroId = oldHero.getId();

        given(heroRepository.retriveById(any(UUID.class))).willReturn(Optional.of(oldHero));
        given(powerStatsService.retriveById(any(UUID.class))).willReturn(powerStats);
        given(powerStatsService.update(eq(updateHeroObj), any(UUID.class))).willReturn(1);
        given(heroRepository.updateHero(any())).willReturn(0);

        //When
        RetriveHeroRequest retriveHero = heroService.update(heroId, updateHeroObj);
        //Then
        assertThat(retriveHero).isNotNull();
        assertThat(retriveHero).isEqualTo(createRetriveHero(oldHero, powerStats));
        verify(heroRepository).retriveById(any(UUID.class));
        verify(heroRepository).updateHero(any(Hero.class));
        verify(powerStatsService).retriveById(any(UUID.class));
        verify(powerStatsService).update(any(UpdateHeroRequest.class), any(UUID.class));
    }

    private Hero updateHeroRequestToHero(UpdateHeroRequest updateHeroRequest, UUID id) {
        return Hero.builder()
                .id(id)
                .powerStatsId(UUID.randomUUID())
                .name(updateHeroRequest.getName())
                .race(updateHeroRequest.getRace())
                .build();
    }

    private UpdateHeroRequest updateHeroRequest() {
        return UpdateHeroRequest.builder()
                .name("Batman-Begins")
                .agility(4)
                .dexterity(5)
                .strength(7)
                .intelligence(10)
                .race(Race.HUMAN)
                .build();
    }

    @Test
    void deleteById() {
    }

    @Test
    void retriveHerosByIds() {
    }

    private Hero createHero(CreateHeroRequest createHeroRequest, UUID powerStatsId) {
        var hero = new Hero(createHeroRequest, powerStatsId);
        hero.setCreatedAt(Instant.now());
        hero.setUpdatedAt(Instant.now());
        hero.setId(UUID.randomUUID());
        return hero;
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
    private PowerStats createPowerStats(CreateHeroRequest createHeroRequest){
        var powerStats = new PowerStats(createHeroRequest);
        powerStats.setCreatedAt(Instant.now());
        powerStats.setUpdatedAt(Instant.now());
        powerStats.setId(UUID.randomUUID());
        return powerStats;
    }

    private RetriveHeroRequest createRetriveHero(Hero hero,PowerStats powerStats) {
        return RetriveHeroRequest.builder()
                .id(hero.getId())
                .name(hero.getName())
                .race(hero.getRace())
                .strength(powerStats.getStrength())
                .agility(powerStats.getAgility())
                .dexterity(powerStats.getDexterity())
                .intelligence(powerStats.getIntelligence()).build();
    }
}
