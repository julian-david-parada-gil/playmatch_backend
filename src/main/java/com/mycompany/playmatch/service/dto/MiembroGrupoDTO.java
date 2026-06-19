package com.mycompany.playmatch.service.dto;

import com.mycompany.playmatch.domain.enumeration.EstadoGeneral;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.playmatch.domain.MiembroGrupo} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class MiembroGrupoDTO implements Serializable {

    private String id;

    @NotNull
    private Instant fechaIngreso;

    @NotNull
    private EstadoGeneral estado;

    @NotNull
    private Boolean esAdministrador;

    @NotNull
    private GrupoDTO grupo;

    @NotNull
    private CuentaDTO usuario;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Instant getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Instant fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public EstadoGeneral getEstado() {
        return estado;
    }

    public void setEstado(EstadoGeneral estado) {
        this.estado = estado;
    }

    public Boolean getEsAdministrador() {
        return esAdministrador;
    }

    public void setEsAdministrador(Boolean esAdministrador) {
        this.esAdministrador = esAdministrador;
    }

    public GrupoDTO getGrupo() {
        return grupo;
    }

    public void setGrupo(GrupoDTO grupo) {
        this.grupo = grupo;
    }

    public CuentaDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(CuentaDTO usuario) {
        this.usuario = usuario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MiembroGrupoDTO)) {
            return false;
        }

        MiembroGrupoDTO miembroGrupoDTO = (MiembroGrupoDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, miembroGrupoDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MiembroGrupoDTO{" +
            "id='" + getId() + "'" +
            ", fechaIngreso='" + getFechaIngreso() + "'" +
            ", estado='" + getEstado() + "'" +
            ", esAdministrador='" + getEsAdministrador() + "'" +
            ", grupo=" + getGrupo() +
            ", usuario=" + getUsuario() +
            "}";
    }
}
