package br.com.gubee.interview.core.features.hero.controller;

import br.com.gubee.interview.core.features.hero.service.HeroService;
import br.com.gubee.interview.model.enums.Race;
import br.com.gubee.interview.model.request.CreateHeroRequest;
import br.com.gubee.interview.model.request.UpdateHeroRequest;
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

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

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
        final ResultActions resultActions = mockMvc.perform(get("/api/v1/heroes/" + RED_LANTERN_ID));
        //then
        resultActions.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void whenHeroNotFoundRespondWithNotFound() throws Exception {
        //given
        //when
        final ResultActions resultActions = mockMvc.perform(get("/api/v1/heroes/" + UUID.randomUUID()));
        //then
        resultActions.andExpect(status().isNotFound()).andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void returnAListHeroRequestInBody() throws Exception {
        //given
        //when
        final ResultActions resultActions = mockMvc.perform(get("/api/v1/heroes/filter?name=lan"));
        //then
        resultActions.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void returnObjectToCompareHeroes() throws Exception {
        //given
        //when
        final ResultActions resultActions = mockMvc.perform(get(String.format("/api/v1/heroes/battle?firstHero=%s&secondHero=%s", RED_LANTERN_ID, GREEN_LANTERN_ID)));
        //then
        resultActions.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void updateHeroAndReturnsIt() throws Exception {
        //given
        final String body = objectMapper.writeValueAsString(getUpdateHeroRequest());
        //when
        final ResultActions resultActions = mockMvc.perform(put(String.format("/api/v1/heroes/%s", GREEN_LANTERN_ID))
                .contentType(MediaType.APPLICATION_JSON)
                .content(body));
        //then
        resultActions.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void deleteHeroPassingItsId() throws Exception {
        //when
        final ResultActions resultActions = mockMvc.perform(delete(String.format("/api/v1/heroes/%s", RED_LANTERN_ID)));
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
