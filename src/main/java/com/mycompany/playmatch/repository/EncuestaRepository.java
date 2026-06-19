package com.mycompany.playmatch.repository;

import com.mycompany.playmatch.domain.Encuesta;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Encuesta entity.
 */
@Repository
public interface EncuestaRepository extends MongoRepository<Encuesta, String> {
    @Query("{}")
    Page<Encuesta> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<Encuesta> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<Encuesta> findOneWithEagerRelationships(String id);
}
