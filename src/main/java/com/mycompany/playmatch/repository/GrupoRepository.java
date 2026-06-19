package com.mycompany.playmatch.repository;

import com.mycompany.playmatch.domain.Grupo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Grupo entity.
 */
@Repository
public interface GrupoRepository extends MongoRepository<Grupo, String> {}
