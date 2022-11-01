package br.com.gubee.interview.core.features.hero.controller;

import br.com.gubee.interview.core.exception.customException.NotFoundHeroException;
import br.com.gubee.interview.core.features.hero.service.HeroService;
import br.com.gubee.interview.model.enums.Race;
import br.com.gubee.interview.model.request.CreateHeroRequest;
import br.com.gubee.interview.model.request.RetrieveHeroRequest;
import br.com.gubee.interview.model.request.UpdateHeroRequest;
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
    }

    @Test
    void returnRetrieveHeroRequestInBody() throws Exception {
        //given
        when(heroService.retriveById(any())).thenReturn(RetrieveHeroRequest.builder().id(UUID.randomUUID()).build());
        //when
        final ResultActions resultActions = mockMvc.perform(get("/api/v1/heroes/" + UUID.randomUUID()));
        //then
        resultActions.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void whenHeroNotFoundRespondWithNotFound() throws Exception {
        //given
        when(heroService.retriveById(any())).thenThrow(new NotFoundHeroException(UUID.randomUUID()));
        //when
        final ResultActions resultActions = mockMvc.perform(get("/api/v1/heroes/" + UUID.randomUUID()));
        //then
        resultActions.andExpect(status().isNotFound()).andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void returnAListHeroRequestInBody() throws Exception {
        //given
        when(heroService.retriveByName(any())).thenReturn(List.of(RetrieveHeroRequest.builder().id(UUID.randomUUID()).build()));
        //when
        final ResultActions resultActions = mockMvc.perform(get("/api/v1/heroes/filter?name=batman"));
        //then
        resultActions.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void returnObjectToCompareHeroes() throws Exception {
        //given
        when(heroService.retriveHerosByIds(any(UUID.class), any(UUID.class)))
                .thenReturn(List.of(RetrieveHeroRequest.builder().id(UUID.randomUUID()).name("fakeHero").build(),
                        RetrieveHeroRequest.builder().id(UUID.randomUUID()).name("fakeHero").build()));
        //when
        final ResultActions resultActions = mockMvc.perform(get(String.format("/api/v1/heroes/battle?firstHero=%s&secondHero=%s", UUID.randomUUID(), UUID.randomUUID())));
        //then
        resultActions.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void updateHeroAndReturnsIt() throws Exception {
        //given
        final String body = objectMapper.writeValueAsString(getUpdateHeroRequest());
        when(heroService.update(any(UUID.class), any(UpdateHeroRequest.class)))
                .thenReturn(RetrieveHeroRequest.builder().id(UUID.randomUUID()).build());
        //when
        final ResultActions resultActions = mockMvc.perform(put(String.format("/api/v1/heroes/%s", UUID.randomUUID()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(body));
        //then
        resultActions.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void deleteHeroPassingItsId() throws Exception {
        //given
        doNothing().when(heroService).deleteById(any());
        //when
        final ResultActions resultActions = mockMvc.perform(delete(String.format("/api/v1/heroes/%s", UUID.randomUUID())));
        //then
        resultActions.andExpect(status().isNoContent());
    }

    private static UpdateHeroRequest getUpdateHeroRequest() {
        return UpdateHeroRequest.builder()
                .name("Aquaman")
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