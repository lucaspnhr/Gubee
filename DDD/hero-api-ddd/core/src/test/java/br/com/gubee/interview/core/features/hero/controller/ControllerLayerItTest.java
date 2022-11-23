package br.com.gubee.interview.core.features.hero.controller;

import br.com.gubee.interview.core.application.in.HeroService;
import br.com.gubee.interview.domain.model.hero.enums.Race;
import br.com.gubee.interview.domain.model.request.CreateHeroRequest;
import br.com.gubee.interview.domain.model.request.UpdateHeroRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.MultiValueMap;
import org.springframework.util.MultiValueMapAdapter;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("it")
public class ControllerLayerItTest {

    private UUID GREEN_LANTERN_ID;
    private UUID RED_LANTERN_ID;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private HeroService heroService;

    @BeforeEach
    void setUp() {
       RED_LANTERN_ID = heroService.create(CreateHeroRequest.builder()
                        .name("Lanterna Vermelha")
                        .race(Race.ALIEN)
                        .agility(6)
                        .strength(4)
                        .dexterity(3)
                        .intelligence(8)
                .build());
       GREEN_LANTERN_ID = heroService.create(CreateHeroRequest.builder()
                        .name("Lanterna Verde")
                        .race(Race.ALIEN)
                        .agility(8)
                        .strength(3)
                        .dexterity(7)
                        .intelligence(3)
                .build());
    }

    @AfterEach
    void tearDown() {
        heroService.deleteAll();
    }

    @Test
    void shouldReturnCreateWithLocation() throws Exception {
        //given
        final String body = objectMapper.writeValueAsString(createHeroRequest());
        //when
        ResultActions resultActions = mockMvc.perform(post("/api/v1/heroes")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(body));
        //then
        resultActions.andExpect(status().isCreated()).andExpect(header().exists("Location"));
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
    }

    @Test
    void returnRetrieveHeroRequestInBody() throws Exception {
        //given
        //when
        final ResultActions resultActions = mockMvc.perform(get("/api/v1/heroes/{id}", RED_LANTERN_ID));
        //then
        resultActions.andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Lanterna Vermelha"))
                .andExpect(jsonPath("$.id").value(RED_LANTERN_ID.toString()));
    }

    @Test
    void whenHeroNotFoundRespondWithNotFound() throws Exception {
        //given
        final var heroId = UUID.randomUUID();
        //when

        final ResultActions resultActions = mockMvc.perform(get("/api/v1/heroes/{id}", heroId));
        //then
        resultActions.andExpect(status().isNotFound())
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value(containsString(heroId.toString())));
    }

    @Test
    void returnAListHeroRequestInBody() throws Exception {
        //given
        //when
        final ResultActions resultActions = mockMvc.perform(get("/api/v1/heroes/filter").param("name", "lan"));
        //then
        resultActions
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].name").isNotEmpty())
                .andExpect(jsonPath("$.[1].name").value("Lanterna Verde"));
    }

    @Test
    void returnObjectToCompareHeroes() throws Exception {
        //given
        MultiValueMap<String, String> params = new MultiValueMapAdapter<>(
                Map.of("firstHero", List.of(RED_LANTERN_ID.toString()),
                        "secondHero", List.of(GREEN_LANTERN_ID.toString()))
        );
        //when
        final ResultActions resultActions = mockMvc.perform(get("/api/v1/heroes/battle").params(params));
        //then
        resultActions
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.heroes.['Lanterna Vermelha']").value(RED_LANTERN_ID.toString()))
                .andExpect(jsonPath("$.heroes.['Lanterna Verde']").value(GREEN_LANTERN_ID.toString()));
    }

    @Test
    void updateHeroAndReturnsIt() throws Exception {
        //given
        final String body = objectMapper.writeValueAsString(getUpdateHeroRequest());
        //when
        final ResultActions resultActions = mockMvc.perform(put("/api/v1/heroes/{id}", GREEN_LANTERN_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body));
        //then
        resultActions
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Lanterna-Verde"));
    }

    @Test
    void deleteHeroPassingItsId() throws Exception {
        //when
        final ResultActions resultActions = mockMvc.perform(delete("/api/v1/heroes/{id}", RED_LANTERN_ID));
        //then
        resultActions.andExpect(status().isNoContent());
    }


    private static UpdateHeroRequest getUpdateHeroRequest() {
        return UpdateHeroRequest.builder()
                .name("Lanterna-Verde")
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
}
