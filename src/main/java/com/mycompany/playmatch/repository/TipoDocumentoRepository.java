package com.mycompany.playmatch.repository;

import com.mycompany.playmatch.domain.TipoDocumento;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the TipoDocumento entity.
 */
@Repository
public interface TipoDocumentoRepository extends MongoRepository<TipoDocumento, String> {}
