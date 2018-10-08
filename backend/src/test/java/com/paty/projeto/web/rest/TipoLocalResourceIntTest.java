package com.paty.projeto.web.rest;

import com.paty.projeto.ProjetoServiceApp;

import com.paty.projeto.domain.TipoLocal;
import com.paty.projeto.repository.TipoLocalRepository;

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

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TipoLocalResource REST controller.
 *
 * @see TipoLocalResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProjetoServiceApp.class)
public class TipoLocalResourceIntTest {

    private static final String DEFAULT_TIPO = "AAAAA";
    private static final String UPDATED_TIPO = "BBBBB";

    @Inject
    private TipoLocalRepository tipoLocalRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restTipoLocalMockMvc;

    private TipoLocal tipoLocal;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TipoLocalResource tipoLocalResource = new TipoLocalResource();
        ReflectionTestUtils.setField(tipoLocalResource, "tipoLocalRepository", tipoLocalRepository);
        this.restTipoLocalMockMvc = MockMvcBuilders.standaloneSetup(tipoLocalResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoLocal createEntity(EntityManager em) {
        TipoLocal tipoLocal = new TipoLocal()
                .tipo(DEFAULT_TIPO);
        return tipoLocal;
    }

    @Before
    public void initTest() {
        tipoLocal = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipoLocal() throws Exception {
        int databaseSizeBeforeCreate = tipoLocalRepository.findAll().size();

        // Create the TipoLocal

        restTipoLocalMockMvc.perform(post("/api/tipo-locals")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(tipoLocal)))
                .andExpect(status().isCreated());

        // Validate the TipoLocal in the database
        List<TipoLocal> tipoLocals = tipoLocalRepository.findAll();
        assertThat(tipoLocals).hasSize(databaseSizeBeforeCreate + 1);
        TipoLocal testTipoLocal = tipoLocals.get(tipoLocals.size() - 1);
        assertThat(testTipoLocal.getTipo()).isEqualTo(DEFAULT_TIPO);
    }

    @Test
    @Transactional
    public void getAllTipoLocals() throws Exception {
        // Initialize the database
        tipoLocalRepository.saveAndFlush(tipoLocal);

        // Get all the tipoLocals
        restTipoLocalMockMvc.perform(get("/api/tipo-locals?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(tipoLocal.getId().intValue())))
                .andExpect(jsonPath("$.[*].tipo").value(hasItem(DEFAULT_TIPO.toString())));
    }

    @Test
    @Transactional
    public void getTipoLocal() throws Exception {
        // Initialize the database
        tipoLocalRepository.saveAndFlush(tipoLocal);

        // Get the tipoLocal
        restTipoLocalMockMvc.perform(get("/api/tipo-locals/{id}", tipoLocal.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tipoLocal.getId().intValue()))
            .andExpect(jsonPath("$.tipo").value(DEFAULT_TIPO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTipoLocal() throws Exception {
        // Get the tipoLocal
        restTipoLocalMockMvc.perform(get("/api/tipo-locals/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipoLocal() throws Exception {
        // Initialize the database
        tipoLocalRepository.saveAndFlush(tipoLocal);
        int databaseSizeBeforeUpdate = tipoLocalRepository.findAll().size();

        // Update the tipoLocal
        TipoLocal updatedTipoLocal = tipoLocalRepository.findOne(tipoLocal.getId());
        updatedTipoLocal
                .tipo(UPDATED_TIPO);

        restTipoLocalMockMvc.perform(put("/api/tipo-locals")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedTipoLocal)))
                .andExpect(status().isOk());

        // Validate the TipoLocal in the database
        List<TipoLocal> tipoLocals = tipoLocalRepository.findAll();
        assertThat(tipoLocals).hasSize(databaseSizeBeforeUpdate);
        TipoLocal testTipoLocal = tipoLocals.get(tipoLocals.size() - 1);
        assertThat(testTipoLocal.getTipo()).isEqualTo(UPDATED_TIPO);
    }

    @Test
    @Transactional
    public void deleteTipoLocal() throws Exception {
        // Initialize the database
        tipoLocalRepository.saveAndFlush(tipoLocal);
        int databaseSizeBeforeDelete = tipoLocalRepository.findAll().size();

        // Get the tipoLocal
        restTipoLocalMockMvc.perform(delete("/api/tipo-locals/{id}", tipoLocal.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<TipoLocal> tipoLocals = tipoLocalRepository.findAll();
        assertThat(tipoLocals).hasSize(databaseSizeBeforeDelete - 1);
    }
}
