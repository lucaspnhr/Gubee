package br.com.gubee.interview.core.features.hero;

import br.com.gubee.interview.core.exception.customException.NotFoundHeroException;
import br.com.gubee.interview.model.enums.Race;
import br.com.gubee.interview.model.request.CreateHeroRequest;
import br.com.gubee.interview.model.request.RetrieveHeroRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HeroController.class)
class HeroControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private HeroServiceImpl heroServiceImpl;

    @BeforeEach
    public void initTest() {
        when(heroServiceImpl.create(any())).thenReturn(UUID.randomUUID());
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
        verify(heroServiceImpl, times(1)).create(any());
    }

    @Test
    void throwsExceptionIfHasInvalidArgument() throws Exception {
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
        when(heroServiceImpl.retriveById(any())).thenReturn(RetrieveHeroRequest.builder().id(UUID.randomUUID()).build());
        //when
        final ResultActions resultActions = mockMvc.perform(get("/api/v1/heroes/"+UUID.randomUUID()));
        //then
        resultActions.andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }
    @Test
    void whenHeroNotFoundRespondWithNotFound() throws Exception {
        //given
        when(heroServiceImpl.retriveById(any())).thenThrow(new NotFoundHeroException(UUID.randomUUID()));
        //when
        final ResultActions resultActions = mockMvc.perform(get("/api/v1/heroes/"+UUID.randomUUID()));
        //then
        resultActions.andExpect(status().isNotFound()).andExpect(content().contentType(MediaType.APPLICATION_JSON));
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