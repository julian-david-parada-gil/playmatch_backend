package com.mycompany.playmatch.repository;

import com.mycompany.playmatch.domain.Inscripcion;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Inscripcion entity.
 */
@Repository
public interface InscripcionRepository extends MongoRepository<Inscripcion, String> {
    @Query("{}")
    Page<Inscripcion> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<Inscripcion> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<Inscripcion> findOneWithEagerRelationships(String id);
}
