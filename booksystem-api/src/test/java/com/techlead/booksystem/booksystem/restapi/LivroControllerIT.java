package com.techlead.booksystem.booksystem.restapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techlead.booksystem.booksystem.dto.LivroDTO;
import com.techlead.booksystem.booksystem.services.LivroService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
public class LivroControllerIT {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private LivroService service;
    @Autowired
    private ObjectMapper mapper;

    @Value("${security.oauth2.client.client-id}")
    private String clientId;
    @Value("${security.oauth2.client.client-secret}")
    private String clientSecret;

    private String token;

    @BeforeEach
    void setUp() throws Exception {
        token = obtainAccessToken("admin", "123456");
    }

    @Test
    void getAll_shouldReturnListOfLivros() throws Exception {
        mockMvc.perform(get("/livros")
                .accept(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").exists());

    }

    @Test
    void getAll_shouldReturnUnauthorized_whenTokenNotExist() throws Exception {

        mockMvc.perform(get("/livros")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());

    }

    @Test
    void save_shouldReturnLivroDTO_whenValidLivroInputDto() throws Exception {
        LivroDTO dataInput = new LivroDTO("Livro Teste", "Autor desconhecido");
        String jsonInput = mapper.writeValueAsString(dataInput);

        mockMvc.perform(post("/livros")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonInput)
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    void save_shouldReturnUnprocessableEntity_whenInvalidLivroInputDto() throws Exception {
        LivroDTO dataInput = new LivroDTO(null, null);
        String jsonInput = mapper.writeValueAsString(dataInput);

        mockMvc.perform(post("/livros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInput)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(jsonPath("$.errors").exists())
                .andExpect(jsonPath("$.errors").isArray());
    }

    @Test
    void save_shouldReturnUnauthorized_whenTokenNotExist() throws Exception {
        LivroDTO dataInput = new LivroDTO("Livro Teste", "Autor desconhecido");
        String jsonInput = mapper.writeValueAsString(dataInput);

        mockMvc.perform(post("/livros")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInput))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getById_shouldReturnLivroDTO_whenIdExistAndValidToken() throws Exception {
        mockMvc.perform(get("/livros/{id}",1)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").isNotEmpty());
    }

    @Test
    void getById_shouldReturnUnathorized_whenIdExistButTokenNotExist() throws Exception {
        mockMvc.perform(get("/livros/{id}",1)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getById_shouldReturnNotFound_whenIdNotExistWithValidToken() throws Exception {
        mockMvc.perform(get("/livros/{id}",99)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer "+token))
                .andExpect(status().isNotFound());
    }

    @Test
    void update_shouldReturnLivroDtoUpdated_whenValidLivroDtoAndIdExist() throws Exception {
        LivroDTO livro = service.findById(1L);
        String novoTitulo = "Título alterado";
        livro.setNome(novoTitulo);
        String jsonInput = mapper.writeValueAsString(livro);

        mockMvc.perform(put("/livros/{id}",1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInput)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").exists())
                .andExpect(jsonPath("$.nome").value(novoTitulo));
    }

    @Test
    void update_shouldReturnNotFound_whenIdNotExist() throws Exception {
        LivroDTO livro = service.findById(1L);
        String novoTitulo = "Título alterado";
        livro.setNome(novoTitulo);
        String jsonInput = mapper.writeValueAsString(livro);

        mockMvc.perform(put("/livros/{id}",99)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInput)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNotFound());
    }

    @Test
    void update_shouldReturnUnauthorized_whenTokenNotExist() throws Exception {
        LivroDTO livro = service.findById(1L);
        String novoTitulo = "Título alterado";
        livro.setNome(novoTitulo);
        String jsonInput = mapper.writeValueAsString(livro);

        mockMvc.perform(put("/livros/{id}",1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInput))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void update_shouldReturnUnauthorized_whenUserAuthNotAllowed() throws Exception {
        LivroDTO livro = service.findById(1L);
        String novoTitulo = "Título alterado";
        livro.setNome(novoTitulo);
        String jsonInput = mapper.writeValueAsString(livro);
        String anotherToken = obtainAccessToken("user", "123456");

        mockMvc.perform(put("/livros/{id}",1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonInput)
                        .header("Authorization", "Bearer " + anotherToken))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void delete_shouldReturnUnathorized_whenUserAuthNotAllowed() throws Exception {
        String anotherToken = obtainAccessToken("user","123456");

        mockMvc.perform(delete("/livros/{id}",1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + anotherToken))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void delete_shouldReturnNoContent_whenIdExistAndUserAuthAllowed() throws Exception {

        mockMvc.perform(delete("/livros/{id}",1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNoContent());
    }

    @Test
    void delete_shouldReturnUnauthorized_whenIdExistAndUserAuthNotAllowed() throws Exception {

        mockMvc.perform(delete("/livros/{id}",1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNoContent());
    }



    private String obtainAccessToken(String username, String password) throws Exception {

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "password");
        params.add("client_id", clientId);
        params.add("username", username);
        params.add("password", password);

        ResultActions result
                = mockMvc.perform(post("/oauth/token")
                        .params(params)
                        .with(httpBasic(clientId, clientSecret))
                        .accept("application/json;charset=UTF-8"))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"));

        String resultString = result.andReturn().getResponse().getContentAsString();

        JacksonJsonParser jsonParser = new JacksonJsonParser();
        return jsonParser.parseMap(resultString).get("access_token").toString();
    }
}
