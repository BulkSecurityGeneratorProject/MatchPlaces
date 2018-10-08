package com.paty.projeto.repository;

import com.paty.projeto.domain.Fotos;
import com.paty.projeto.domain.Local;

import org.springframework.data.jpa.repository.*;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

/**
 * Spring Data JPA repository for the Local entity.
 */
@SuppressWarnings("unused")
public interface LocalRepository extends JpaRepository<Local, Long> {

    public Local findByPlaceId(String placeId);

    @Query("from Local local")
    public Page<Local> findAllMatch(Pageable pageable);

//    public Local findOneWithEagerRelationships(Long id);
    @Query("select local from Local local "
            + " left join fetch local.comentarios "
            + "where local.id =:id")
    Local findOneWithEagerRelationships(@Param("id") Long id);

    @Query("select local from Local local "
            + " left join fetch local.fotos "
            + "where local.id =:id")
    Local findOnePlacePhoto(@Param("id") Long id);

    @Query(value = "SELECT "
            + "DISTINCT "
            + "local.*, "
            + "(ABS((op.percentile - :opennes))/((op.percentile+:opennes)/2)*100) +"
            + "(ABS((con.percentile - :conscientiousness))/((con.percentile+:conscientiousness)/2)*100)+"
            + "(ABS((ex.percentile - :extraversion))/((ex.percentile+:extraversion)/2)*100)+"
            + "(ABS((agr.percentile - :agreeableness))/((agr.percentile+:agreeableness)/2)*100)+"
            + "(ABS((neu.percentile - :neuroticism))/((neu.percentile+ :neuroticism)/2)*100) "
            + "as 'totalDiff' "
            + " FROM projetoService.local "
            + "INNER JOIN profile_personality pp ON pp.profile_id=local.profile_id "
            + "INNER JOIN trait op ON op.id IN (SELECT personality_id FROM profile_personality WHERE profile_id=local.profile_id ) AND op.trait_id=\"big5_openness\" "
            + "INNER JOIN trait con ON con.id IN (SELECT personality_id FROM profile_personality WHERE profile_id=local.profile_id ) AND con.trait_id=\"big5_conscientiousness\" "
            + "INNER JOIN trait ex ON ex.id IN (SELECT personality_id FROM profile_personality WHERE profile_id=local.profile_id ) AND ex.trait_id=\"big5_extraversion\" "
            + "INNER JOIN trait agr ON agr.id IN (SELECT personality_id FROM profile_personality WHERE profile_id=local.profile_id ) AND agr.trait_id=\"big5_agreeableness\" "
            + "INNER JOIN trait neu ON neu.id IN (SELECT personality_id FROM profile_personality WHERE profile_id=local.profile_id ) AND neu.trait_id=\"big5_neuroticism\" " 
            + " ORDER BY totalDiff "
            + " LIMIT 20; ", nativeQuery = true)
    public List<Local> findMatches(@Param("opennes") Double opennes,@Param("conscientiousness") 
            Double conscientiousness, @Param("extraversion") Double extraversion,@Param("agreeableness")  
                    Double agreeableness,@Param("neuroticism")  Double neuroticism);

     @Query(value = "select * from local WHERE imagem IS NULL", nativeQuery = true)
    public List<Local> findAllPlaces();


}
