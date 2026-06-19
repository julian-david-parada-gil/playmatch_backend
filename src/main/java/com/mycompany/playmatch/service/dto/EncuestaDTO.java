package com.mycompany.playmatch.service.dto;

import com.mycompany.playmatch.domain.enumeration.EstadoEncuesta;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.playmatch.domain.Encuesta} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EncuestaDTO implements Serializable {

    private String id;

    private String titulo;

    @NotNull
    @Size(max = 255)
    private String opcion1;

    @NotNull
    @Size(max = 255)
    private String opcion2;

    @Size(max = 255)
    private String opcion3;

    @Size(max = 255)
    private String opcion4;

    private Integer votosOpcion1;

    private Integer votosOpcion2;

    private Integer votosOpcion3;

    private Integer votosOpcion4;

    private Instant fechaInicio;

    private Instant fechaFin;

    @NotNull
    private EstadoEncuesta estado;

    private GrupoDTO grupo;

    private TorneoDTO torneo;

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

    public String getOpcion1() {
        return opcion1;
    }

    public void setOpcion1(String opcion1) {
        this.opcion1 = opcion1;
    }

    public String getOpcion2() {
        return opcion2;
    }

    public void setOpcion2(String opcion2) {
        this.opcion2 = opcion2;
    }

    public String getOpcion3() {
        return opcion3;
    }

    public void setOpcion3(String opcion3) {
        this.opcion3 = opcion3;
    }

    public String getOpcion4() {
        return opcion4;
    }

    public void setOpcion4(String opcion4) {
        this.opcion4 = opcion4;
    }

    public Integer getVotosOpcion1() {
        return votosOpcion1;
    }

    public void setVotosOpcion1(Integer votosOpcion1) {
        this.votosOpcion1 = votosOpcion1;
    }

    public Integer getVotosOpcion2() {
        return votosOpcion2;
    }

    public void setVotosOpcion2(Integer votosOpcion2) {
        this.votosOpcion2 = votosOpcion2;
    }

    public Integer getVotosOpcion3() {
        return votosOpcion3;
    }

    public void setVotosOpcion3(Integer votosOpcion3) {
        this.votosOpcion3 = votosOpcion3;
    }

    public Integer getVotosOpcion4() {
        return votosOpcion4;
    }

    public void setVotosOpcion4(Integer votosOpcion4) {
        this.votosOpcion4 = votosOpcion4;
    }

    public Instant getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Instant fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Instant getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Instant fechaFin) {
        this.fechaFin = fechaFin;
    }

    public EstadoEncuesta getEstado() {
        return estado;
    }

    public void setEstado(EstadoEncuesta estado) {
        this.estado = estado;
    }

    public GrupoDTO getGrupo() {
        return grupo;
    }

    public void setGrupo(GrupoDTO grupo) {
        this.grupo = grupo;
    }

    public TorneoDTO getTorneo() {
        return torneo;
    }

    public void setTorneo(TorneoDTO torneo) {
        this.torneo = torneo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EncuestaDTO)) {
            return false;
        }

        EncuestaDTO encuestaDTO = (EncuestaDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, encuestaDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EncuestaDTO{" +
            "id='" + getId() + "'" +
            ", titulo='" + getTitulo() + "'" +
            ", opcion1='" + getOpcion1() + "'" +
            ", opcion2='" + getOpcion2() + "'" +
            ", opcion3='" + getOpcion3() + "'" +
            ", opcion4='" + getOpcion4() + "'" +
            ", votosOpcion1=" + getVotosOpcion1() +
            ", votosOpcion2=" + getVotosOpcion2() +
            ", votosOpcion3=" + getVotosOpcion3() +
            ", votosOpcion4=" + getVotosOpcion4() +
            ", fechaInicio='" + getFechaInicio() + "'" +
            ", fechaFin='" + getFechaFin() + "'" +
            ", estado='" + getEstado() + "'" +
            ", grupo=" + getGrupo() +
            ", torneo=" + getTorneo() +
            "}";
    }
}
