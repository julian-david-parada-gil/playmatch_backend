package com.mycompany.playmatch.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.playmatch.domain.enumeration.EstadoGeneral;
import com.mycompany.playmatch.domain.enumeration.TipoNoticia;
import jakarta.validation.constraints.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Noticia.
 */
@Document(collection = "noticia")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Noticia implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Size(max = 150)
    @Field("titulo")
    private String titulo;

    @Field("contenido")
    private String contenido;

    @NotNull
    @Field("tipo")
    private TipoNoticia tipo;

    @NotNull
    @Field("fecha_publicacion")
    private Instant fechaPublicacion;

    @NotNull
    @Field("estado")
    private EstadoGeneral estado;

    @DBRef
    @Field("autor")
    @JsonIgnoreProperties(
        value = {
            "user",
            "gruposes",
            "inscripcioneses",
            "mensajeses",
            "calificacioneses",
            "noticiases",
            "notificacioneses",
            "tipoDocumento",
        },
        allowSetters = true
    )
    private Cuenta autor;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Noticia id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public Noticia titulo(String titulo) {
        this.setTitulo(titulo);
        return this;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getContenido() {
        return this.contenido;
    }

    public Noticia contenido(String contenido) {
        this.setContenido(contenido);
        return this;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public TipoNoticia getTipo() {
        return this.tipo;
    }

    public Noticia tipo(TipoNoticia tipo) {
        this.setTipo(tipo);
        return this;
    }

    public void setTipo(TipoNoticia tipo) {
        this.tipo = tipo;
    }

    public Instant getFechaPublicacion() {
        return this.fechaPublicacion;
    }

    public Noticia fechaPublicacion(Instant fechaPublicacion) {
        this.setFechaPublicacion(fechaPublicacion);
        return this;
    }

    public void setFechaPublicacion(Instant fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public EstadoGeneral getEstado() {
        return this.estado;
    }

    public Noticia estado(EstadoGeneral estado) {
        this.setEstado(estado);
        return this;
    }

    public void setEstado(EstadoGeneral estado) {
        this.estado = estado;
    }

    public Cuenta getAutor() {
        return this.autor;
    }

    public void setAutor(Cuenta cuenta) {
        this.autor = cuenta;
    }

    public Noticia autor(Cuenta cuenta) {
        this.setAutor(cuenta);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Noticia)) {
            return false;
        }
        return getId() != null && getId().equals(((Noticia) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Noticia{" +
            "id=" + getId() +
            ", titulo='" + getTitulo() + "'" +
            ", contenido='" + getContenido() + "'" +
            ", tipo='" + getTipo() + "'" +
            ", fechaPublicacion='" + getFechaPublicacion() + "'" +
            ", estado='" + getEstado() + "'" +
            "}";
    }
}
