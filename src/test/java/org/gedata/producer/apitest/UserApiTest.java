package org.gedata.producer.apitest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.gedata.producer.model.user.dto.UserDataDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.Base64Utils;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc()
@SpringBootTest
public class UserApiTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objecMapper;

    @Test
    public void userDataGetAllTest() throws Exception {
       var result =  this.mockMvc.perform(get("/v1/userdata")).andDo(print())
                .andExpect(status().isOk()).andReturn();
        JsonNode actual = objecMapper.readTree(result.getResponse().getContentAsString());
        assertThat(actual.size(),is(greaterThan(0)));
    }

    @Test
    public void userDataGetOne() throws Exception {
        var result =  this.mockMvc.perform(get("/v1/userdata/1")).andDo(print())
                .andExpect(status().isOk()).andReturn();
        JsonNode actual = objecMapper.readTree(result.getResponse().getContentAsString());
        assertThat(actual,is(notNullValue()));
    }

    @Test
    public void addUserDataIsConflict() throws Exception {
        var result =  this.mockMvc.perform(post("/v1/userdata").contentType(MediaType.APPLICATION_JSON).content("{\n" +
                "     \"firstName\": \"Karol\",\n" +
                "     \"lastName\": \"Testowy\",\n" +
                "    \"emailAddress\": \"martest@gmail.com\",\n" +
                "    \"nickname\": \"MarekXX24\"\n" +
                "}"))
                .andDo(print())
                .andExpect(status().isConflict()).andReturn();
    }

    @Test
    public void addUserData() throws Exception {
        var result =  this.mockMvc.perform(post("/v1/userdata").contentType(MediaType.APPLICATION_JSON).content("{\n" +
                "     \"firstName\": \"Karol\",\n" +
                "     \"lastName\": \"Testowy\",\n" +
                "    \"emailAddress\": \"exet@gmail.com\",\n" +
                "    \"nickname\": \"unique123\"\n" +
                "}"))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();
        JsonNode actual = objecMapper.readTree(result.getResponse().getContentAsString());
        assertThat(actual,is(notNullValue()));
    }
}
