package com.paty.projeto.web.rest;

import com.paty.projeto.ProjetoServiceApp;

import com.paty.projeto.domain.Comentarios;
import com.paty.projeto.repository.ComentariosRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the ComentariosResource REST controller.
 *
 * @see ComentariosResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProjetoServiceApp.class)
public class ComentariosResourceIntTest {

    private static final String DEFAULT_AUTOR = "AAAAA";
    private static final String UPDATED_AUTOR = "BBBBB";

    private static final String DEFAULT_URL = "AAAAA";
    private static final String UPDATED_URL = "BBBBB";

    private static final String DEFAULT_TEXTO = "AAAAA";
    private static final String UPDATED_TEXTO = "BBBBB";

    private static final Double DEFAULT_RATE = 1D;
    private static final Double UPDATED_RATE = 2D;

    @Inject
    private ComentariosRepository comentariosRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restComentariosMockMvc;

    private Comentarios comentarios;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ComentariosResource comentariosResource = new ComentariosResource();
        ReflectionTestUtils.setField(comentariosResource, "comentariosRepository", comentariosRepository);
        this.restComentariosMockMvc = MockMvcBuilders.standaloneSetup(comentariosResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Comentarios createEntity(EntityManager em) {
        Comentarios comentarios = new Comentarios()
                .autor(DEFAULT_AUTOR)
                .url(DEFAULT_URL)
                .texto(DEFAULT_TEXTO)
                .rate(DEFAULT_RATE);
        return comentarios;
    }

    @Before
    public void initTest() {
        comentarios = createEntity(em);
    }

    @Test
    @Transactional
    public void createComentarios() throws Exception {
        int databaseSizeBeforeCreate = comentariosRepository.findAll().size();

        // Create the Comentarios

        restComentariosMockMvc.perform(post("/api/comentarios")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(comentarios)))
                .andExpect(status().isCreated());

        // Validate the Comentarios in the database
        List<Comentarios> comentarios = comentariosRepository.findAll();
        assertThat(comentarios).hasSize(databaseSizeBeforeCreate + 1);
        Comentarios testComentarios = comentarios.get(comentarios.size() - 1);
        assertThat(testComentarios.getAutor()).isEqualTo(DEFAULT_AUTOR);
        assertThat(testComentarios.getUrl()).isEqualTo(DEFAULT_URL);
        assertThat(testComentarios.getTexto()).isEqualTo(DEFAULT_TEXTO);
        assertThat(testComentarios.getRate()).isEqualTo(DEFAULT_RATE);
    }

    @Test
    @Transactional
    public void getAllComentarios() throws Exception {
        // Initialize the database
        comentariosRepository.saveAndFlush(comentarios);

        // Get all the comentarios
        restComentariosMockMvc.perform(get("/api/comentarios?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(comentarios.getId().intValue())))
                .andExpect(jsonPath("$.[*].autor").value(hasItem(DEFAULT_AUTOR.toString())))
                .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL.toString())))
                .andExpect(jsonPath("$.[*].texto").value(hasItem(DEFAULT_TEXTO.toString())))
                .andExpect(jsonPath("$.[*].rate").value(hasItem(DEFAULT_RATE.doubleValue())));
    }

    @Test
    @Transactional
    public void getComentarios() throws Exception {
        // Initialize the database
        comentariosRepository.saveAndFlush(comentarios);

        // Get the comentarios
        restComentariosMockMvc.perform(get("/api/comentarios/{id}", comentarios.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(comentarios.getId().intValue()))
            .andExpect(jsonPath("$.autor").value(DEFAULT_AUTOR.toString()))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL.toString()))
            .andExpect(jsonPath("$.texto").value(DEFAULT_TEXTO.toString()))
            .andExpect(jsonPath("$.rate").value(DEFAULT_RATE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingComentarios() throws Exception {
        // Get the comentarios
        restComentariosMockMvc.perform(get("/api/comentarios/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateComentarios() throws Exception {
        // Initialize the database
        comentariosRepository.saveAndFlush(comentarios);
        int databaseSizeBeforeUpdate = comentariosRepository.findAll().size();

        // Update the comentarios
        Comentarios updatedComentarios = comentariosRepository.findOne(comentarios.getId());
        updatedComentarios
                .autor(UPDATED_AUTOR)
                .url(UPDATED_URL)
                .texto(UPDATED_TEXTO)
                .rate(UPDATED_RATE);

        restComentariosMockMvc.perform(put("/api/comentarios")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedComentarios)))
                .andExpect(status().isOk());

        // Validate the Comentarios in the database
        List<Comentarios> comentarios = comentariosRepository.findAll();
        assertThat(comentarios).hasSize(databaseSizeBeforeUpdate);
        Comentarios testComentarios = comentarios.get(comentarios.size() - 1);
        assertThat(testComentarios.getAutor()).isEqualTo(UPDATED_AUTOR);
        assertThat(testComentarios.getUrl()).isEqualTo(UPDATED_URL);
        assertThat(testComentarios.getTexto()).isEqualTo(UPDATED_TEXTO);
        assertThat(testComentarios.getRate()).isEqualTo(UPDATED_RATE);
    }

    @Test
    @Transactional
    public void deleteComentarios() throws Exception {
        // Initialize the database
        comentariosRepository.saveAndFlush(comentarios);
        int databaseSizeBeforeDelete = comentariosRepository.findAll().size();

        // Get the comentarios
        restComentariosMockMvc.perform(delete("/api/comentarios/{id}", comentarios.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Comentarios> comentarios = comentariosRepository.findAll();
        assertThat(comentarios).hasSize(databaseSizeBeforeDelete - 1);
    }
}
