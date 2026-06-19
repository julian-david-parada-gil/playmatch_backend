package com.mycompany.playmatch.repository;

import com.mycompany.playmatch.domain.Noticia;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Noticia entity.
 */
@Repository
public interface NoticiaRepository extends MongoRepository<Noticia, String> {}
