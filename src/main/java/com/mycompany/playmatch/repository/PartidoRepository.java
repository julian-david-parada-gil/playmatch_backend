package com.mycompany.playmatch.repository;

import com.mycompany.playmatch.domain.Partido;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Partido entity.
 */
@Repository
public interface PartidoRepository extends MongoRepository<Partido, String> {
    @Query("{}")
    Page<Partido> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<Partido> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<Partido> findOneWithEagerRelationships(String id);
}
