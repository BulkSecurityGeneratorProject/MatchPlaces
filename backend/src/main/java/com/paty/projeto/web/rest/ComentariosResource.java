package com.paty.projeto.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.paty.projeto.domain.Comentarios;

import com.paty.projeto.repository.ComentariosRepository;
import com.paty.projeto.web.rest.util.HeaderUtil;
import com.paty.projeto.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Comentarios.
 */
@RestController
@RequestMapping("/api")
public class ComentariosResource {

    private final Logger log = LoggerFactory.getLogger(ComentariosResource.class);
        
    @Inject
    private ComentariosRepository comentariosRepository;

    /**
     * POST  /comentarios : Create a new comentarios.
     *
     * @param comentarios the comentarios to create
     * @return the ResponseEntity with status 201 (Created) and with body the new comentarios, or with status 400 (Bad Request) if the comentarios has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/comentarios")
    @Timed
    public ResponseEntity<Comentarios> createComentarios(@RequestBody Comentarios comentarios) throws URISyntaxException {
        log.debug("REST request to save Comentarios : {}", comentarios);
        if (comentarios.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("comentarios", "idexists", "A new comentarios cannot already have an ID")).body(null);
        }
        Comentarios result = comentariosRepository.save(comentarios);
        return ResponseEntity.created(new URI("/api/comentarios/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("comentarios", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /comentarios : Updates an existing comentarios.
     *
     * @param comentarios the comentarios to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated comentarios,
     * or with status 400 (Bad Request) if the comentarios is not valid,
     * or with status 500 (Internal Server Error) if the comentarios couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/comentarios")
    @Timed
    public ResponseEntity<Comentarios> updateComentarios(@RequestBody Comentarios comentarios) throws URISyntaxException {
        log.debug("REST request to update Comentarios : {}", comentarios);
        if (comentarios.getId() == null) {
            return createComentarios(comentarios);
        }
        Comentarios result = comentariosRepository.save(comentarios);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("comentarios", comentarios.getId().toString()))
            .body(result);
    }

    /**
     * GET  /comentarios : get all the comentarios.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of comentarios in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/comentarios")
    @Timed
    public ResponseEntity<List<Comentarios>> getAllComentarios(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Comentarios");
        Page<Comentarios> page = comentariosRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/comentarios");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /comentarios/:id : get the "id" comentarios.
     *
     * @param id the id of the comentarios to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the comentarios, or with status 404 (Not Found)
     */
    @GetMapping("/comentarios/{id}")
    @Timed
    public ResponseEntity<Comentarios> getComentarios(@PathVariable Long id) {
        log.debug("REST request to get Comentarios : {}", id);
        Comentarios comentarios = comentariosRepository.findOne(id);
        return Optional.ofNullable(comentarios)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /comentarios/:id : delete the "id" comentarios.
     *
     * @param id the id of the comentarios to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/comentarios/{id}")
    @Timed
    public ResponseEntity<Void> deleteComentarios(@PathVariable Long id) {
        log.debug("REST request to delete Comentarios : {}", id);
        comentariosRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("comentarios", id.toString())).build();
    }

}
