package com.paty.projeto.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.paty.projeto.domain.TipoLocal;

import com.paty.projeto.repository.TipoLocalRepository;
import com.paty.projeto.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * REST controller for managing TipoLocal.
 */
@RestController
@RequestMapping("/api")
public class TipoLocalResource {

    private final Logger log = LoggerFactory.getLogger(TipoLocalResource.class);
        
    @Inject
    private TipoLocalRepository tipoLocalRepository;

    /**
     * POST  /tipo-locals : Create a new tipoLocal.
     *
     * @param tipoLocal the tipoLocal to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tipoLocal, or with status 400 (Bad Request) if the tipoLocal has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tipo-locals")
    @Timed
    public ResponseEntity<TipoLocal> createTipoLocal(@RequestBody TipoLocal tipoLocal) throws URISyntaxException {
        log.debug("REST request to save TipoLocal : {}", tipoLocal);
        if (tipoLocal.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("tipoLocal", "idexists", "A new tipoLocal cannot already have an ID")).body(null);
        }
        TipoLocal result = tipoLocalRepository.save(tipoLocal);
        return ResponseEntity.created(new URI("/api/tipo-locals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("tipoLocal", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tipo-locals : Updates an existing tipoLocal.
     *
     * @param tipoLocal the tipoLocal to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tipoLocal,
     * or with status 400 (Bad Request) if the tipoLocal is not valid,
     * or with status 500 (Internal Server Error) if the tipoLocal couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tipo-locals")
    @Timed
    public ResponseEntity<TipoLocal> updateTipoLocal(@RequestBody TipoLocal tipoLocal) throws URISyntaxException {
        log.debug("REST request to update TipoLocal : {}", tipoLocal);
        if (tipoLocal.getId() == null) {
            return createTipoLocal(tipoLocal);
        }
        TipoLocal result = tipoLocalRepository.save(tipoLocal);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("tipoLocal", tipoLocal.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tipo-locals : get all the tipoLocals.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of tipoLocals in body
     */
    @GetMapping("/tipo-locals")
    @Timed
    public List<TipoLocal> getAllTipoLocals() {
        log.debug("REST request to get all TipoLocals");
        List<TipoLocal> tipoLocals = tipoLocalRepository.findAll();
        return tipoLocals;
    }

    /**
     * GET  /tipo-locals/:id : get the "id" tipoLocal.
     *
     * @param id the id of the tipoLocal to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tipoLocal, or with status 404 (Not Found)
     */
    @GetMapping("/tipo-locals/{id}")
    @Timed
    public ResponseEntity<TipoLocal> getTipoLocal(@PathVariable Long id) {
        log.debug("REST request to get TipoLocal : {}", id);
        TipoLocal tipoLocal = tipoLocalRepository.findOne(id);
        return Optional.ofNullable(tipoLocal)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /tipo-locals/:id : delete the "id" tipoLocal.
     *
     * @param id the id of the tipoLocal to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tipo-locals/{id}")
    @Timed
    public ResponseEntity<Void> deleteTipoLocal(@PathVariable Long id) {
        log.debug("REST request to delete TipoLocal : {}", id);
        tipoLocalRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("tipoLocal", id.toString())).build();
    }

}
