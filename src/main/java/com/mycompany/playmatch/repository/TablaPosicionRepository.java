package com.mycompany.playmatch.repository;

import com.mycompany.playmatch.domain.TablaPosicion;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the TablaPosicion entity.
 */
@Repository
public interface TablaPosicionRepository extends MongoRepository<TablaPosicion, String> {
    @Query("{}")
    Page<TablaPosicion> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<TablaPosicion> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<TablaPosicion> findOneWithEagerRelationships(String id);
}
