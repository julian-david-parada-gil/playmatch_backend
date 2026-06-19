package com.mycompany.playmatch.repository;

import com.mycompany.playmatch.domain.MensajeGrupo;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the MensajeGrupo entity.
 */
@Repository
public interface MensajeGrupoRepository extends MongoRepository<MensajeGrupo, String> {
    @Query("{}")
    Page<MensajeGrupo> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<MensajeGrupo> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<MensajeGrupo> findOneWithEagerRelationships(String id);
}
