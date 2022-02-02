package com.train2middle.springboot;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.train2middle.springboot.model.AuthResponse;
import com.train2middle.springboot.model.Film;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.EnabledIf;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ContextConfiguration
@RequiredArgsConstructor
@EnabledIf(expression = "#{environment['spring.profiles.active'] == 'dev'}", loadContext = true)
public class FilmTest {
    private static final Logger log = LoggerFactory.getLogger(FilmTest.class);

    private MockMvc mockMvc;
    private final WebApplicationContext wac;
    private ObjectMapper objectMapper;

    private AuthResponse authResponse;

    @BeforeEach
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .apply(springSecurity())
                .build();
        objectMapper = new ObjectMapper();
        authResponse = getAuthResponse("user1", "password1");
    }

    @Test
    public void testGetFilms() {
        try {
            MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/film/")
                            .header("Authorization", "Bearer " + authResponse.getToken())
                            .accept(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn();

            List<Film> response = Arrays.asList(objectMapper.readValue(result.getResponse().getContentAsString(), Film[].class));
            log.debug("number of films {}", response.size());

            for (Film film : response) {
                log.debug("film id {}", film.getId());

                mockMvc.perform(MockMvcRequestBuilders.get("/film/" + film.getId())
                                .header("Authorization", "Bearer " + authResponse.getToken())
                                .accept(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$.id").exists())
                        .andExpect(jsonPath("$.name").exists())
                        .andExpect(jsonPath("$.description").exists())
                        .andExpect(jsonPath("$.genre").exists())
                        .andExpect(jsonPath("$.budget").exists())
                        .andDo(print());
            }
        } catch (Exception ex) {
            log.error(ex.getMessage());
            Assertions.fail();
        }
    }

    public AuthResponse getAuthResponse(String username, String password) throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/oauth/login")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("{\n" +
                                "\t\"username\": \"" + username + "\",\n" +
                                "\t\"password\": \"" + password + "\"\n" +
                                "}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andReturn();

        try {
            return objectMapper.readValue(result.getResponse().getContentAsString(),AuthResponse.class);
        } catch (Exception e) {
            log.debug("error: " + e.getMessage());
        }

        return null;
    }

    @Test
    public void testCRUDFilm() throws Exception {

        Film filmTest = new Film();
        filmTest.setName("TestName");
        filmTest.setDescription("TestDescription");
        filmTest.setGenre("TestGenre");
        filmTest.setBudget(11.1f);


        MvcResult resultPost = mockMvc.perform(MockMvcRequestBuilders.post("/film")
                        .header("Authorization", "Bearer " + authResponse.getToken())
                        .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                        .content(objectMapper.writeValueAsString(filmTest))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();

        Film responsePost = objectMapper.readValue(resultPost.getResponse().getContentAsString(),
                objectMapper.getTypeFactory().constructType(Film.class));
        log.debug("inserted id: {}", responsePost.getId());

        MvcResult resultGet = mockMvc.perform(MockMvcRequestBuilders.get("/film/" + responsePost.getId())
                        .header("Authorization", "Bearer " + authResponse.getToken())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.description").exists())
                .andExpect(jsonPath("$.genre").exists())
                .andExpect(jsonPath("$.budget").exists())
                .andDo(print())
                .andReturn();

        Film responseGet = objectMapper.readValue(resultGet.getResponse().getContentAsString(),
                objectMapper.getTypeFactory().constructType(Film.class));
        log.debug("inserted id: {}", responseGet.getId());

        responseGet.setName("New name");

        mockMvc.perform(MockMvcRequestBuilders.put("/film/" + responseGet.getId())
                        .header("Authorization", "Bearer " + authResponse.getToken())
                        .contentType(MediaType.APPLICATION_PROBLEM_JSON_UTF8_VALUE)
                        .content(objectMapper.writeValueAsString(responseGet))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());

        mockMvc.perform(MockMvcRequestBuilders.delete("/film/" + responseGet.getId())
                        .header("Authorization", "Bearer " + authResponse.getToken())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(print());
    }
}
