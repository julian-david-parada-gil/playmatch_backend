package com.mycompany.playmatch.repository;

import com.mycompany.playmatch.domain.Torneo;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Torneo entity.
 */
@Repository
public interface TorneoRepository extends MongoRepository<Torneo, String> {
    @Query("{}")
    Page<Torneo> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<Torneo> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<Torneo> findOneWithEagerRelationships(String id);
}
