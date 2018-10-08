package com.paty.projeto.repository;

import com.paty.projeto.domain.Pessoa;

import org.springframework.data.jpa.repository.*;

import java.util.List;
import org.springframework.data.repository.query.Param;

/**
 * Spring Data JPA repository for the Pessoa entity.
 */
@SuppressWarnings("unused")
public interface PessoaRepository extends JpaRepository<Pessoa,Long> {

    @Query("select pessoa from Pessoa pessoa "
            + "where pessoa.idTwitter =:idTwitter")
    public Pessoa findOneByIdTwitter(@Param("idTwitter") String idTwitter);

}
