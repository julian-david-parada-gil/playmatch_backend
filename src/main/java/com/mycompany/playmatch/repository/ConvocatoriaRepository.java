package com.mycompany.playmatch.repository;

import com.mycompany.playmatch.domain.Convocatoria;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Convocatoria entity.
 */
@Repository
public interface ConvocatoriaRepository extends MongoRepository<Convocatoria, String> {
    @Query("{}")
    Page<Convocatoria> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<Convocatoria> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<Convocatoria> findOneWithEagerRelationships(String id);
}
