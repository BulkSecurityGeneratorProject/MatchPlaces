package com.paty.projeto.web.rest;

import com.paty.projeto.ProjetoServiceApp;

import com.paty.projeto.domain.Local;
import com.paty.projeto.repository.LocalRepository;

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
 * Test class for the LocalResource REST controller.
 *
 * @see LocalResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProjetoServiceApp.class)
public class LocalResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAA";
    private static final String UPDATED_NAME = "BBBBB";

    private static final Double DEFAULT_LATITUDE = 1D;
    private static final Double UPDATED_LATITUDE = 2D;

    private static final Double DEFAULT_LONGITUDE = 1D;
    private static final Double UPDATED_LONGITUDE = 2D;

    private static final String DEFAULT_PHOTO_REFERENCE = "AAAAA";
    private static final String UPDATED_PHOTO_REFERENCE = "BBBBB";

    private static final String DEFAULT_PLACE_ID = "AAAAA";
    private static final String UPDATED_PLACE_ID = "BBBBB";

    private static final String DEFAULT_COMENTARIO = "AAAAA";
    private static final String UPDATED_COMENTARIO = "BBBBB";

    private static final String DEFAULT_TELEFONE = "AAAAA";
    private static final String UPDATED_TELEFONE = "BBBBB";

    private static final String DEFAULT_ENDERECO = "AAAAA";
    private static final String UPDATED_ENDERECO = "BBBBB";

    private static final Double DEFAULT_RATE = 1D;
    private static final Double UPDATED_RATE = 2D;

    private static final String DEFAULT_WEBSITE = "AAAAA";
    private static final String UPDATED_WEBSITE = "BBBBB";

    private static final String DEFAULT_BAIRRO = "AAAAA";
    private static final String UPDATED_BAIRRO = "BBBBB";

    private static final String DEFAULT_IMAGEM = "AAAAA";
    private static final String UPDATED_IMAGEM = "BBBBB";

    @Inject
    private LocalRepository localRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restLocalMockMvc;

    private Local local;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        LocalResource localResource = new LocalResource();
        ReflectionTestUtils.setField(localResource, "localRepository", localRepository);
        this.restLocalMockMvc = MockMvcBuilders.standaloneSetup(localResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Local createEntity(EntityManager em) {
        Local local = new Local()
                .name(DEFAULT_NAME)
                .latitude(DEFAULT_LATITUDE)
                .longitude(DEFAULT_LONGITUDE)
                .photo_reference(DEFAULT_PHOTO_REFERENCE)
                .placeId(DEFAULT_PLACE_ID)
                .comentario(DEFAULT_COMENTARIO)
                .telefone(DEFAULT_TELEFONE)
                .endereco(DEFAULT_ENDERECO)
                .rate(DEFAULT_RATE)
                .website(DEFAULT_WEBSITE)
                .bairro(DEFAULT_BAIRRO)
                .imagem(DEFAULT_IMAGEM);
        return local;
    }

    @Before
    public void initTest() {
        local = createEntity(em);
    }

    @Test
    @Transactional
    public void createLocal() throws Exception {
        int databaseSizeBeforeCreate = localRepository.findAll().size();

        // Create the Local

        restLocalMockMvc.perform(post("/api/locals")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(local)))
                .andExpect(status().isCreated());

        // Validate the Local in the database
        List<Local> locals = localRepository.findAll();
        assertThat(locals).hasSize(databaseSizeBeforeCreate + 1);
        Local testLocal = locals.get(locals.size() - 1);
        assertThat(testLocal.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testLocal.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testLocal.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
        assertThat(testLocal.getPhoto_reference()).isEqualTo(DEFAULT_PHOTO_REFERENCE);
        assertThat(testLocal.getPlaceId()).isEqualTo(DEFAULT_PLACE_ID);
        assertThat(testLocal.getComentario()).isEqualTo(DEFAULT_COMENTARIO);
        assertThat(testLocal.getTelefone()).isEqualTo(DEFAULT_TELEFONE);
        assertThat(testLocal.getEndereco()).isEqualTo(DEFAULT_ENDERECO);
        assertThat(testLocal.getRate()).isEqualTo(DEFAULT_RATE);
        assertThat(testLocal.getWebsite()).isEqualTo(DEFAULT_WEBSITE);
        assertThat(testLocal.getBairro()).isEqualTo(DEFAULT_BAIRRO);
        assertThat(testLocal.getImagem()).isEqualTo(DEFAULT_IMAGEM);
    }

    @Test
    @Transactional
    public void getAllLocals() throws Exception {
        // Initialize the database
        localRepository.saveAndFlush(local);

        // Get all the locals
        restLocalMockMvc.perform(get("/api/locals?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(local.getId().intValue())))
                .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
                .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.doubleValue())))
                .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.doubleValue())))
                .andExpect(jsonPath("$.[*].photo_reference").value(hasItem(DEFAULT_PHOTO_REFERENCE.toString())))
                .andExpect(jsonPath("$.[*].placeId").value(hasItem(DEFAULT_PLACE_ID.toString())))
                .andExpect(jsonPath("$.[*].comentario").value(hasItem(DEFAULT_COMENTARIO.toString())))
                .andExpect(jsonPath("$.[*].telefone").value(hasItem(DEFAULT_TELEFONE.toString())))
                .andExpect(jsonPath("$.[*].endereco").value(hasItem(DEFAULT_ENDERECO.toString())))
                .andExpect(jsonPath("$.[*].rate").value(hasItem(DEFAULT_RATE.doubleValue())))
                .andExpect(jsonPath("$.[*].website").value(hasItem(DEFAULT_WEBSITE.toString())))
                .andExpect(jsonPath("$.[*].bairro").value(hasItem(DEFAULT_BAIRRO.toString())))
                .andExpect(jsonPath("$.[*].imagem").value(hasItem(DEFAULT_IMAGEM.toString())));
    }

    @Test
    @Transactional
    public void getLocal() throws Exception {
        // Initialize the database
        localRepository.saveAndFlush(local);

        // Get the local
        restLocalMockMvc.perform(get("/api/locals/{id}", local.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(local.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE.doubleValue()))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE.doubleValue()))
            .andExpect(jsonPath("$.photo_reference").value(DEFAULT_PHOTO_REFERENCE.toString()))
            .andExpect(jsonPath("$.placeId").value(DEFAULT_PLACE_ID.toString()))
            .andExpect(jsonPath("$.comentario").value(DEFAULT_COMENTARIO.toString()))
            .andExpect(jsonPath("$.telefone").value(DEFAULT_TELEFONE.toString()))
            .andExpect(jsonPath("$.endereco").value(DEFAULT_ENDERECO.toString()))
            .andExpect(jsonPath("$.rate").value(DEFAULT_RATE.doubleValue()))
            .andExpect(jsonPath("$.website").value(DEFAULT_WEBSITE.toString()))
            .andExpect(jsonPath("$.bairro").value(DEFAULT_BAIRRO.toString()))
            .andExpect(jsonPath("$.imagem").value(DEFAULT_IMAGEM.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingLocal() throws Exception {
        // Get the local
        restLocalMockMvc.perform(get("/api/locals/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLocal() throws Exception {
        // Initialize the database
        localRepository.saveAndFlush(local);
        int databaseSizeBeforeUpdate = localRepository.findAll().size();

        // Update the local
        Local updatedLocal = localRepository.findOne(local.getId());
        updatedLocal
                .name(UPDATED_NAME)
                .latitude(UPDATED_LATITUDE)
                .longitude(UPDATED_LONGITUDE)
                .photo_reference(UPDATED_PHOTO_REFERENCE)
                .placeId(UPDATED_PLACE_ID)
                .comentario(UPDATED_COMENTARIO)
                .telefone(UPDATED_TELEFONE)
                .endereco(UPDATED_ENDERECO)
                .rate(UPDATED_RATE)
                .website(UPDATED_WEBSITE)
                .bairro(UPDATED_BAIRRO)
                .imagem(UPDATED_IMAGEM);

        restLocalMockMvc.perform(put("/api/locals")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedLocal)))
                .andExpect(status().isOk());

        // Validate the Local in the database
        List<Local> locals = localRepository.findAll();
        assertThat(locals).hasSize(databaseSizeBeforeUpdate);
        Local testLocal = locals.get(locals.size() - 1);
        assertThat(testLocal.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLocal.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testLocal.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
        assertThat(testLocal.getPhoto_reference()).isEqualTo(UPDATED_PHOTO_REFERENCE);
        assertThat(testLocal.getPlaceId()).isEqualTo(UPDATED_PLACE_ID);
        assertThat(testLocal.getComentario()).isEqualTo(UPDATED_COMENTARIO);
        assertThat(testLocal.getTelefone()).isEqualTo(UPDATED_TELEFONE);
        assertThat(testLocal.getEndereco()).isEqualTo(UPDATED_ENDERECO);
        assertThat(testLocal.getRate()).isEqualTo(UPDATED_RATE);
        assertThat(testLocal.getWebsite()).isEqualTo(UPDATED_WEBSITE);
        assertThat(testLocal.getBairro()).isEqualTo(UPDATED_BAIRRO);
        assertThat(testLocal.getImagem()).isEqualTo(UPDATED_IMAGEM);
    }

    @Test
    @Transactional
    public void deleteLocal() throws Exception {
        // Initialize the database
        localRepository.saveAndFlush(local);
        int databaseSizeBeforeDelete = localRepository.findAll().size();

        // Get the local
        restLocalMockMvc.perform(delete("/api/locals/{id}", local.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Local> locals = localRepository.findAll();
        assertThat(locals).hasSize(databaseSizeBeforeDelete - 1);
    }
}
