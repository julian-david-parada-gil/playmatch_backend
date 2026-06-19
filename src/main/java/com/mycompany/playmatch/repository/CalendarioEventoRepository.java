package com.mycompany.playmatch.repository;

import com.mycompany.playmatch.domain.CalendarioEvento;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the CalendarioEvento entity.
 */
@Repository
public interface CalendarioEventoRepository extends MongoRepository<CalendarioEvento, String> {
    @Query("{}")
    Page<CalendarioEvento> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<CalendarioEvento> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<CalendarioEvento> findOneWithEagerRelationships(String id);
}
