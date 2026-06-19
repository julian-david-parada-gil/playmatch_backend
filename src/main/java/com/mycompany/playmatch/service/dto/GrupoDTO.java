package com.mycompany.playmatch.service.dto;

import com.mycompany.playmatch.domain.enumeration.EstadoGeneral;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.playmatch.domain.Grupo} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class GrupoDTO implements Serializable {

    private String id;

    @NotNull
    @Size(max = 100)
    private String nombre;

    private String descripcion;

    private byte[] escudo;

    private String escudoContentType;

    @NotNull
    private Integer limiteParticipantes;

    @NotNull
    private EstadoGeneral estado;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public byte[] getEscudo() {
        return escudo;
    }

    public void setEscudo(byte[] escudo) {
        this.escudo = escudo;
    }

    public String getEscudoContentType() {
        return escudoContentType;
    }

    public void setEscudoContentType(String escudoContentType) {
        this.escudoContentType = escudoContentType;
    }

    public Integer getLimiteParticipantes() {
        return limiteParticipantes;
    }

    public void setLimiteParticipantes(Integer limiteParticipantes) {
        this.limiteParticipantes = limiteParticipantes;
    }

    public EstadoGeneral getEstado() {
        return estado;
    }

    public void setEstado(EstadoGeneral estado) {
        this.estado = estado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof GrupoDTO)) {
            return false;
        }

        GrupoDTO grupoDTO = (GrupoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, grupoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "GrupoDTO{" +
            "id='" + getId() + "'" +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", escudo='" + getEscudo() + "'" +
            ", limiteParticipantes=" + getLimiteParticipantes() +
            ", estado='" + getEstado() + "'" +
            "}";
    }
}
