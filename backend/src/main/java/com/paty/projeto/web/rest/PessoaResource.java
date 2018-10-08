package com.paty.projeto.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.paty.projeto.domain.Pessoa;

import com.paty.projeto.repository.PessoaRepository;
import com.paty.projeto.service.Trait;
import com.paty.projeto.service.TwitterBuilder;
import com.paty.projeto.service.WatsonService;
import com.paty.projeto.web.rest.dto.UserpessoaDTO;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.transaction.annotation.Transactional;
import twitter4j.Paging;
import twitter4j.Twitter;
import twitter4j.TwitterException;

/**
 * REST controller for managing Pessoa.
 */
@RestController
@RequestMapping("/api")
public class PessoaResource {

    private final Logger log = LoggerFactory.getLogger(PessoaResource.class);

    @Inject
    private PessoaRepository pessoaRepository;
    @Inject
    private WatsonService watsonService;

    /**
     * POST /pessoas : Create a new pessoa.
     *
     * @param pessoa the pessoa to create
     * @return the ResponseEntity with status 201 (Created) and with body the
     * new pessoa, or with status 400 (Bad Request) if the pessoa has already an
     * ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pessoas")
    @Timed
    public ResponseEntity<Pessoa> createPessoa(@RequestBody Pessoa pessoa) throws URISyntaxException {
        log.debug("REST request to save Pessoa : {}", pessoa);
        if (pessoa.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("pessoa", "idexists", "A new pessoa cannot already have an ID")).body(null);
        }
        Pessoa result = pessoaRepository.save(pessoa);
        return ResponseEntity.created(new URI("/api/pessoas/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("pessoa", result.getId().toString()))
                .body(result);
    }

    /**
     * PUT /pessoas : Updates an existing pessoa.
     *
     * @param pessoa the pessoa to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated
     * pessoa, or with status 400 (Bad Request) if the pessoa is not valid, or
     * with status 500 (Internal Server Error) if the pessoa couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pessoas")
    @Timed
    public ResponseEntity<Pessoa> updatePessoa(@RequestBody Pessoa pessoa) throws URISyntaxException {
        log.debug("REST request to update Pessoa : {}", pessoa);
        if (pessoa.getId() == null) {
            return createPessoa(pessoa);
        }
        Pessoa result = pessoaRepository.save(pessoa);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("pessoa", pessoa.getId().toString()))
                .body(result);
    }

    /**
     * GET /pessoas : get all the pessoas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of pessoas
     * in body
     * @throws URISyntaxException if there is an error to generate the
     * pagination HTTP headers
     */
    @GetMapping("/pessoas")
    @Timed
    public ResponseEntity<List<Pessoa>> getAllPessoas(Pageable pageable)
            throws URISyntaxException {
        log.debug("REST request to get a page of Pessoas");
        Page<Pessoa> page = pessoaRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/pessoas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET /pessoas/:id : get the "id" pessoa.
     *
     * @param id the id of the pessoa to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pessoa,
     * or with status 404 (Not Found)
     */
    @GetMapping("/pessoas/{id}")
    @Timed
    public ResponseEntity<Pessoa> getPessoa(@PathVariable Long id) {
        log.debug("REST request to get Pessoa : {}", id);
        Pessoa pessoa = pessoaRepository.findOne(id);
        return Optional.ofNullable(pessoa)
                .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE /pessoas/:id : delete the "id" pessoa.
     *
     * @param id the id of the pessoa to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pessoas/{id}")
    @Timed
    public ResponseEntity<Void> deletePessoa(@PathVariable Long id) {
        log.debug("REST request to delete Pessoa : {}", id);
        pessoaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("pessoa", id.toString())).build();
    }

    @GetMapping("/profile/{token}/{secret}/{idTwitter}")
    @Timed
    public ResponseEntity<UserpessoaDTO> getprofile(@PathVariable String token,
            @PathVariable String secret,
            @PathVariable String idTwitter)
            throws URISyntaxException {
        log.debug("REST request the profile");

        System.out.println("******************************");
        System.out.println("chegou no service");
        System.out.println(token);
        System.out.println(secret);
        System.out.println(idTwitter);
        System.out.println("******************************");

        Pessoa result = null;

       // Long idTwitterSearch=Long.parseLong(idTwitter);
        result = pessoaRepository.findOneByIdTwitter(idTwitter);
        
        System.out.println("**result****************************");
        System.out.println(result);
        System.out.println("******************************");

        if (result == null) {

            String consumerKey = "consumerKey";
            String consumerSecret = "consumerSecret";

            String accessToken = token;
            String accessTokenSecret = secret;

            Twitter twitter = new TwitterBuilder().build(consumerKey, consumerSecret, accessToken, accessTokenSecret);

            
//            List<String> timeline = twitter.getUserTimeline().stream()
//                    .map(item -> item.getText())
//                    .collect(Collectors.toList());
//
//            for (String tweet : timeline) {
//                System.out.println("*******************");
//                System.out.println(tweet);
//                System.out.println("*******************");
//            }
            int pageno = 1;
            Long user = Long.parseLong(idTwitter);
            List<String> tweets = new ArrayList();

            while (pageno<4) {

                try {

                    int size = tweets.size();
                    Paging page = new Paging(pageno++, 100); //pega 100 tweets por vez
                    tweets.addAll(twitter.getUserTimeline(user, page).stream()
                            .map(item -> item.getText())
                            .collect(Collectors.toList()));
                    if (tweets.size() == size) {
                        break;
                    }
                } catch (TwitterException e) {

                    e.printStackTrace();
                }
            }

            System.out.println("Total: " + tweets.size());

            String textoTweet = "";

            for (Object tweet : tweets) {
                textoTweet += " " + tweet;
            }

            String texto = watsonService.translator(textoTweet);

            int palavras = texto.trim().split("\\s+").length;
            String retorna = "";
            if (palavras > 110) {
                com.ibm.watson.developer_cloud.personality_insights.v3.model.Profile personal = watsonService.personal(texto);
                Pessoa pessoa = new Pessoa();
                pessoa.setIdTwitter(idTwitter); //mudar pra string
                pessoa.setSecretTwitter(secret);
                pessoa.setTokenTwitter(token);
                pessoa.setProfile(watsonService.converte(personal));
                result = pessoaRepository.save(pessoa);

            }

        }

        UserpessoaDTO usuario = null;

        if (result != null) {
            usuario = new UserpessoaDTO();
            usuario.setIdpessoa(result.getId());
        }

        System.out.println("*******************");
        System.out.println(usuario);
        System.out.println(usuario.getIdpessoa());
        System.out.println("*******************");

        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

}
