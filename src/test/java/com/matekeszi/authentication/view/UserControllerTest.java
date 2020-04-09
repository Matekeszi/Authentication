package com.matekeszi.authentication.view;

import com.matekeszi.authentication.domain.Roles;
import com.matekeszi.authentication.domain.UserEntity;
import com.matekeszi.authentication.domain.UserRoles;
import com.matekeszi.authentication.repository.UserRepository;
import net.minidev.json.JSONUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest()
@AutoConfigureMockMvc
@ComponentScan({"com.matekeszi.authentication"})
public class UserControllerTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MockMvc mockMvc;

    private String token;

    @Before
    public void setUp() throws Exception {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("testUser");
        userEntity.setPassword("123");
        userEntity.setRoles(new ArrayList<>());
        UserRoles adminRole = new UserRoles();
        adminRole.setRoles(Roles.ROLE_ADMIN);
        adminRole.setUser(userEntity);
        userEntity.getRoles().add(adminRole);
        UserRoles publicRole = new UserRoles();
        publicRole.setRoles(Roles.ROLE_PUBLIC);
        publicRole.setUser(userEntity);
        userEntity.getRoles().add(publicRole);
        userRepository.save(userEntity);
        final MvcResult result = mockMvc.perform(
                MockMvcRequestBuilders.post("/user/authenticate")
                        .content(parseJSONFile("login"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        token = result.getResponse().getContentAsString();
    }

    @Test
    public void findByIdHappyPath() throws Exception {
        final MvcResult result = mockMvc.perform(get("/user/1")
                .header("x-auth-token", token))
                .andExpect(status().isOk()).andReturn();
        JSONAssert.assertEquals(parseJSONFile("findById"), result.getResponse().getContentAsString(), JSONCompareMode.NON_EXTENSIBLE);
    }

    @Test
    public void findByIdUnhappyPath() throws Exception {
        mockMvc.perform(get("/user/2")
                .header("x-auth-token", token))
                .andExpect(status().isNotFound()).andReturn();
    }

    @Test
    public void register() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.post("/user/authenticate")
                        .content(parseJSONFile("login"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    public void findAll() throws Exception {
        final MvcResult result = mockMvc.perform(get("/user/1")
                .header("x-auth-token", token))
                .andExpect(status().isOk()).andReturn();
        JSONAssert.assertEquals(parseJSONFile("findById"), result.getResponse().getContentAsString(), JSONCompareMode.NON_EXTENSIBLE);
    }

    @Test
    public void deleteById() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/user/1"))
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    public void authenticate() {

    }

    private static String parseJSONFile(final String filename) {
        try (final BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(
                        Objects.requireNonNull(
                                JSONUtil.class.getClassLoader().getResourceAsStream("jsons/" + filename + ".json")),
                        StandardCharsets.UTF_8))) {
            return bufferedReader.lines().collect(Collectors.joining("\n"));
        } catch (final IOException e) {
            throw new RuntimeException("Failed to read test json file.", e);
        }
    }
}