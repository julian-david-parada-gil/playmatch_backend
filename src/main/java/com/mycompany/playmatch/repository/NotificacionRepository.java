package com.mycompany.playmatch.repository;

import com.mycompany.playmatch.domain.Notificacion;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Notificacion entity.
 */
@Repository
public interface NotificacionRepository extends MongoRepository<Notificacion, String> {}
