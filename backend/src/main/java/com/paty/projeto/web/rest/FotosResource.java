package com.paty.projeto.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.paty.projeto.domain.Fotos;

import com.paty.projeto.repository.FotosRepository;
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
 * REST controller for managing Fotos.
 */
@RestController
@RequestMapping("/api")
public class FotosResource {

    private final Logger log = LoggerFactory.getLogger(FotosResource.class);
        
    @Inject
    private FotosRepository fotosRepository;

    /**
     * POST  /fotos : Create a new fotos.
     *
     * @param fotos the fotos to create
     * @return the ResponseEntity with status 201 (Created) and with body the new fotos, or with status 400 (Bad Request) if the fotos has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/fotos")
    @Timed
    public ResponseEntity<Fotos> createFotos(@RequestBody Fotos fotos) throws URISyntaxException {
        log.debug("REST request to save Fotos : {}", fotos);
        if (fotos.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("fotos", "idexists", "A new fotos cannot already have an ID")).body(null);
        }
        Fotos result = fotosRepository.save(fotos);
        return ResponseEntity.created(new URI("/api/fotos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("fotos", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /fotos : Updates an existing fotos.
     *
     * @param fotos the fotos to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated fotos,
     * or with status 400 (Bad Request) if the fotos is not valid,
     * or with status 500 (Internal Server Error) if the fotos couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/fotos")
    @Timed
    public ResponseEntity<Fotos> updateFotos(@RequestBody Fotos fotos) throws URISyntaxException {
        log.debug("REST request to update Fotos : {}", fotos);
        if (fotos.getId() == null) {
            return createFotos(fotos);
        }
        Fotos result = fotosRepository.save(fotos);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("fotos", fotos.getId().toString()))
            .body(result);
    }

    /**
     * GET  /fotos : get all the fotos.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of fotos in body
     * @throws URISyntaxException if there is an error to generate the pagination HTTP headers
     */
    @GetMapping("/fotos")
    @Timed
    public ResponseEntity<List<Fotos>> getAllFotos(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of Fotos");
        Page<Fotos> page = fotosRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/fotos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /fotos/:id : get the "id" fotos.
     *
     * @param id the id of the fotos to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the fotos, or with status 404 (Not Found)
     */
    @GetMapping("/fotos/{id}")
    @Timed
    public ResponseEntity<Fotos> getFotos(@PathVariable Long id) {
        log.debug("REST request to get Fotos : {}", id);
        Fotos fotos = fotosRepository.findOne(id);
        return Optional.ofNullable(fotos)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /fotos/:id : delete the "id" fotos.
     *
     * @param id the id of the fotos to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/fotos/{id}")
    @Timed
    public ResponseEntity<Void> deleteFotos(@PathVariable Long id) {
        log.debug("REST request to delete Fotos : {}", id);
        fotosRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("fotos", id.toString())).build();
    }

}
