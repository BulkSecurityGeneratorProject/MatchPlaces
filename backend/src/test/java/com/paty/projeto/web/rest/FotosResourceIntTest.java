package com.paty.projeto.web.rest;

import com.paty.projeto.ProjetoServiceApp;

import com.paty.projeto.domain.Fotos;
import com.paty.projeto.repository.FotosRepository;

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
 * Test class for the FotosResource REST controller.
 *
 * @see FotosResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProjetoServiceApp.class)
public class FotosResourceIntTest {

    private static final String DEFAULT_FOTO = "";
    private static final String UPDATED_FOTO = "";

    @Inject
    private FotosRepository fotosRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restFotosMockMvc;

    private Fotos fotos;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        FotosResource fotosResource = new FotosResource();
        ReflectionTestUtils.setField(fotosResource, "fotosRepository", fotosRepository);
        this.restFotosMockMvc = MockMvcBuilders.standaloneSetup(fotosResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Fotos createEntity(EntityManager em) {
        Fotos fotos = new Fotos()
                .foto(DEFAULT_FOTO);
        return fotos;
    }

    @Before
    public void initTest() {
        fotos = createEntity(em);
    }

    @Test
    @Transactional
    public void createFotos() throws Exception {
        int databaseSizeBeforeCreate = fotosRepository.findAll().size();

        // Create the Fotos

        restFotosMockMvc.perform(post("/api/fotos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(fotos)))
                .andExpect(status().isCreated());

        // Validate the Fotos in the database
        List<Fotos> fotos = fotosRepository.findAll();
        assertThat(fotos).hasSize(databaseSizeBeforeCreate + 1);
        Fotos testFotos = fotos.get(fotos.size() - 1);
        assertThat(testFotos.getFoto()).isEqualTo(DEFAULT_FOTO);
    }

    @Test
    @Transactional
    public void getAllFotos() throws Exception {
        // Initialize the database
        fotosRepository.saveAndFlush(fotos);

        // Get all the fotos
        restFotosMockMvc.perform(get("/api/fotos?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(fotos.getId().intValue())))
                .andExpect(jsonPath("$.[*].foto").value(hasItem(DEFAULT_FOTO.toString())));
    }

    @Test
    @Transactional
    public void getFotos() throws Exception {
        // Initialize the database
        fotosRepository.saveAndFlush(fotos);

        // Get the fotos
        restFotosMockMvc.perform(get("/api/fotos/{id}", fotos.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(fotos.getId().intValue()))
            .andExpect(jsonPath("$.foto").value(DEFAULT_FOTO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFotos() throws Exception {
        // Get the fotos
        restFotosMockMvc.perform(get("/api/fotos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFotos() throws Exception {
        // Initialize the database
        fotosRepository.saveAndFlush(fotos);
        int databaseSizeBeforeUpdate = fotosRepository.findAll().size();

        // Update the fotos
        Fotos updatedFotos = fotosRepository.findOne(fotos.getId());
        updatedFotos
                .foto(UPDATED_FOTO);

        restFotosMockMvc.perform(put("/api/fotos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedFotos)))
                .andExpect(status().isOk());

        // Validate the Fotos in the database
        List<Fotos> fotos = fotosRepository.findAll();
        assertThat(fotos).hasSize(databaseSizeBeforeUpdate);
        Fotos testFotos = fotos.get(fotos.size() - 1);
        assertThat(testFotos.getFoto()).isEqualTo(UPDATED_FOTO);
    }

    @Test
    @Transactional
    public void deleteFotos() throws Exception {
        // Initialize the database
        fotosRepository.saveAndFlush(fotos);
        int databaseSizeBeforeDelete = fotosRepository.findAll().size();

        // Get the fotos
        restFotosMockMvc.perform(delete("/api/fotos/{id}", fotos.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Fotos> fotos = fotosRepository.findAll();
        assertThat(fotos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
