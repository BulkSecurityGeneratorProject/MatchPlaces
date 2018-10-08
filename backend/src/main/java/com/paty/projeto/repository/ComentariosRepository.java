package com.paty.projeto.repository;

import com.paty.projeto.domain.Comentarios;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Comentarios entity.
 */
@SuppressWarnings("unused")
public interface ComentariosRepository extends JpaRepository<Comentarios,Long> {

}
