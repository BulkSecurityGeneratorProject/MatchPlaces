package com.paty.projeto.repository;

import com.paty.projeto.domain.Fotos;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Fotos entity.
 */
@SuppressWarnings("unused")
public interface FotosRepository extends JpaRepository<Fotos,Long> {

}
