package br.com.gubee.interview.core.features.hero.controller;

import br.com.gubee.interview.core.application.util.exception.NotFoundHeroException;
import br.com.gubee.interview.core.application.in.HeroService;
import br.com.gubee.interview.core.infrastructure.features.hero.controller.HeroController;
import br.com.gubee.interview.domain.model.hero.enums.Race;
import br.com.gubee.interview.domain.model.request.CreateHeroRequest;
import br.com.gubee.interview.domain.model.request.RetrieveHeroRequest;
import br.com.gubee.interview.domain.model.request.UpdateHeroRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.core.StringContains.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HeroController.class)
class HeroControllerRestTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private HeroService heroService;

    @BeforeEach
    public void initTest() {
        when(heroService.create(any())).thenReturn(UUID.randomUUID());
    }

    @Test
    void createAHeroWithAllRequiredArguments() throws Exception {
        //given
        final String body = objectMapper.writeValueAsString(createHeroRequest());

        //when
        final ResultActions resultActions = mockMvc.perform(post("/api/v1/heroes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body));

        //then
        resultActions.andExpect(status().isCreated()).andExpect(header().exists("Location"));
        verify(heroService, times(1)).create(any());
    }

    @Test
    void shouldReturnBadRequestIfHasInvalidArgument() throws Exception {
        //when
        final var createHeroRequest = createHeroRequest();
        createHeroRequest.setName("");
        final String body = objectMapper.writeValueAsString(createHeroRequest);

        //when
        final ResultActions resultActions = mockMvc.perform(post("/api/v1/heroes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body));
        //then
        resultActions.andExpect(status().isBadRequest());
        verify(heroService, never()).create(any());
    }

    @Test
    void returnRetrieveHeroRequestInBody() throws Exception {
        //given
        RetrieveHeroRequest retrieveHeroRequest = getRetrieveHeroRequest();
        when(heroService.retriveById(any())).thenReturn(retrieveHeroRequest);
        //when
        final ResultActions resultActions = mockMvc.perform(get("/api/v1/heroes/{id}", retrieveHeroRequest.getId()));
        //then
        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(retrieveHeroRequest.getName()));
        verify(heroService, times(1)).retriveById(any());
    }

    @Test
    void whenHeroNotFoundRespondWithNotFound() throws Exception {
        //given
        final var id = UUID.randomUUID();
        when(heroService.retriveById(any())).thenThrow(new NotFoundHeroException(id));
        //when
        final ResultActions resultActions = mockMvc.perform(get("/api/v1/heroes/{id}",id));
        //then
        resultActions.andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(containsString(id.toString())));
    }

    @Test
    void returnAListHeroRequestInBody() throws Exception {
        //given
        RetrieveHeroRequest retrieveHeroRequest = getRetrieveHeroRequest();
        when(heroService.retriveByName(any())).thenReturn(List.of(retrieveHeroRequest));
        //when
        final ResultActions resultActions = mockMvc.perform(get("/api/v1/heroes/filter").param("name","batman"));
        //then
        resultActions.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void returnObjectToCompareHeroes() throws Exception {
        //given
        RetrieveHeroRequest firstHero = getRetrieveHeroRequest();
        RetrieveHeroRequest secondHero = getRetrieveHeroRequest();
        secondHero.setName("Flash");
        when(heroService.retriveHerosByIds(any(UUID.class), any(UUID.class)))
                .thenReturn(List.of(firstHero,
                        secondHero));
        //when
        final ResultActions resultActions = mockMvc.perform(get("/api/v1/heroes/battle?firstHero={firstHeroId}&secondHero={secondHeroId}", firstHero.getId(), secondHero.getId()));
        //then
        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.heroes.Flash").value(secondHero.getId().toString()))
                .andExpect(jsonPath("$.heroes.Batman").value(firstHero.getId().toString()));
    }

    @Test
    void updateHeroAndReturnsIt() throws Exception {
        //given
        final var updateHero = getUpdateHeroRequest();
        final var heroId = UUID.randomUUID();
        RetrieveHeroRequest retrieveHeroRequest = retriveHeroFromUpdateHero(updateHero, heroId);

        final String body = objectMapper.writeValueAsString(updateHero);
        when(heroService.update(heroId, updateHero))
                .thenReturn(retrieveHeroRequest);
        //when
        final ResultActions resultActions = mockMvc.perform(put("/api/v1/heroes/{id}", heroId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body));
        //then
        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.agility").value(4));
    }

    @Test
    void deleteHeroPassingItsId() throws Exception {
        //given
        doNothing().when(heroService).deleteById(any());
        //when
        final ResultActions resultActions = mockMvc.perform(delete("/api/v1/heroes/{id}", UUID.randomUUID()));
        //then
        resultActions.andExpect(status().isNoContent());
    }

    private static UpdateHeroRequest getUpdateHeroRequest() {
        return UpdateHeroRequest.builder()
                .name("Batman")
                .race(Race.ALIEN)
                .agility(4)
                .strength(5)
                .dexterity(3)
                .intelligence(8).build();
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

    private static RetrieveHeroRequest getRetrieveHeroRequest() {
        return RetrieveHeroRequest.builder()
                .id(UUID.randomUUID())
                .name("Batman")
                .strength(4)
                .intelligence(4)
                .dexterity(6)
                .agility(8).build();
    }

    private RetrieveHeroRequest retriveHeroFromUpdateHero(UpdateHeroRequest updateHero, UUID heroId) {
        return new RetrieveHeroRequest(heroId,
                updateHero.getName(),
                updateHero.getRace(),
                updateHero.getStrength(),
                updateHero.getAgility(),
                updateHero.getDexterity(),
                updateHero.getIntelligence());
    }
}