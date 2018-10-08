package com.paty.projeto.repository;

import com.paty.projeto.domain.TipoLocal;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TipoLocal entity.
 */
@SuppressWarnings("unused")
public interface TipoLocalRepository extends JpaRepository<TipoLocal,Long> {

}
