package com.mycompany.playmatch.repository;

import com.mycompany.playmatch.domain.MiembroGrupo;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the MiembroGrupo entity.
 */
@Repository
public interface MiembroGrupoRepository extends MongoRepository<MiembroGrupo, String> {
    @Query("{}")
    Page<MiembroGrupo> findAllWithEagerRelationships(Pageable pageable);

    @Query("{}")
    List<MiembroGrupo> findAllWithEagerRelationships();

    @Query("{'id': ?0}")
    Optional<MiembroGrupo> findOneWithEagerRelationships(String id);
}
