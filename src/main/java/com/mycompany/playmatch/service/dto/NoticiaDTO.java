package com.mycompany.playmatch.service.dto;

import com.mycompany.playmatch.domain.enumeration.EstadoGeneral;
import com.mycompany.playmatch.domain.enumeration.TipoNoticia;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.playmatch.domain.Noticia} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NoticiaDTO implements Serializable {

    private String id;

    @NotNull
    @Size(max = 150)
    private String titulo;

    private String contenido;

    @NotNull
    private TipoNoticia tipo;

    @NotNull
    private Instant fechaPublicacion;

    @NotNull
    private EstadoGeneral estado;

    @NotNull
    private CuentaDTO autor;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public TipoNoticia getTipo() {
        return tipo;
    }

    public void setTipo(TipoNoticia tipo) {
        this.tipo = tipo;
    }

    public Instant getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(Instant fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public EstadoGeneral getEstado() {
        return estado;
    }

    public void setEstado(EstadoGeneral estado) {
        this.estado = estado;
    }

    public CuentaDTO getAutor() {
        return autor;
    }

    public void setAutor(CuentaDTO autor) {
        this.autor = autor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NoticiaDTO)) {
            return false;
        }

        NoticiaDTO noticiaDTO = (NoticiaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, noticiaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NoticiaDTO{" +
            "id='" + getId() + "'" +
            ", titulo='" + getTitulo() + "'" +
            ", contenido='" + getContenido() + "'" +
            ", tipo='" + getTipo() + "'" +
            ", fechaPublicacion='" + getFechaPublicacion() + "'" +
            ", estado='" + getEstado() + "'" +
            ", autor=" + getAutor() +
            "}";
    }
}
