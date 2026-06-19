package com.mycompany.playmatch.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.playmatch.domain.enumeration.EstadoGeneral;
import jakarta.validation.constraints.*;
import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Grupo.
 */
@Document(collection = "grupo")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Grupo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Size(max = 100)
    @Field("nombre")
    private String nombre;

    @Field("descripcion")
    private String descripcion;

    @Field("escudo")
    private byte[] escudo;

    @Field("escudo_content_type")
    private String escudoContentType;

    @NotNull
    @Field("limite_participantes")
    private Integer limiteParticipantes;

    @NotNull
    @Field("estado")
    private EstadoGeneral estado;

    @DBRef
    @Field("miembros")
    @JsonIgnoreProperties(value = { "grupo", "usuario" }, allowSetters = true)
    private Set<MiembroGrupo> miembroses = new HashSet<>();

    @DBRef
    @Field("mensajes")
    @JsonIgnoreProperties(value = { "grupo", "autor" }, allowSetters = true)
    private Set<MensajeGrupo> mensajeses = new HashSet<>();

    @DBRef
    @Field("encuestas")
    @JsonIgnoreProperties(value = { "grupo", "torneo" }, allowSetters = true)
    private Set<Encuesta> encuestases = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Grupo id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public Grupo nombre(String nombre) {
        this.setNombre(nombre);
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public Grupo descripcion(String descripcion) {
        this.setDescripcion(descripcion);
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public byte[] getEscudo() {
        return this.escudo;
    }

    public Grupo escudo(byte[] escudo) {
        this.setEscudo(escudo);
        return this;
    }

    public void setEscudo(byte[] escudo) {
        this.escudo = escudo;
    }

    public String getEscudoContentType() {
        return this.escudoContentType;
    }

    public Grupo escudoContentType(String escudoContentType) {
        this.escudoContentType = escudoContentType;
        return this;
    }

    public void setEscudoContentType(String escudoContentType) {
        this.escudoContentType = escudoContentType;
    }

    public Integer getLimiteParticipantes() {
        return this.limiteParticipantes;
    }

    public Grupo limiteParticipantes(Integer limiteParticipantes) {
        this.setLimiteParticipantes(limiteParticipantes);
        return this;
    }

    public void setLimiteParticipantes(Integer limiteParticipantes) {
        this.limiteParticipantes = limiteParticipantes;
    }

    public EstadoGeneral getEstado() {
        return this.estado;
    }

    public Grupo estado(EstadoGeneral estado) {
        this.setEstado(estado);
        return this;
    }

    public void setEstado(EstadoGeneral estado) {
        this.estado = estado;
    }

    public Set<MiembroGrupo> getMiembroses() {
        return this.miembroses;
    }

    public void setMiembroses(Set<MiembroGrupo> miembroGrupos) {
        if (this.miembroses != null) {
            this.miembroses.forEach(i -> i.setGrupo(null));
        }
        if (miembroGrupos != null) {
            miembroGrupos.forEach(i -> i.setGrupo(this));
        }
        this.miembroses = miembroGrupos;
    }

    public Grupo miembroses(Set<MiembroGrupo> miembroGrupos) {
        this.setMiembroses(miembroGrupos);
        return this;
    }

    public Grupo addMiembros(MiembroGrupo miembroGrupo) {
        this.miembroses.add(miembroGrupo);
        miembroGrupo.setGrupo(this);
        return this;
    }

    public Grupo removeMiembros(MiembroGrupo miembroGrupo) {
        this.miembroses.remove(miembroGrupo);
        miembroGrupo.setGrupo(null);
        return this;
    }

    public Set<MensajeGrupo> getMensajeses() {
        return this.mensajeses;
    }

    public void setMensajeses(Set<MensajeGrupo> mensajeGrupos) {
        if (this.mensajeses != null) {
            this.mensajeses.forEach(i -> i.setGrupo(null));
        }
        if (mensajeGrupos != null) {
            mensajeGrupos.forEach(i -> i.setGrupo(this));
        }
        this.mensajeses = mensajeGrupos;
    }

    public Grupo mensajeses(Set<MensajeGrupo> mensajeGrupos) {
        this.setMensajeses(mensajeGrupos);
        return this;
    }

    public Grupo addMensajes(MensajeGrupo mensajeGrupo) {
        this.mensajeses.add(mensajeGrupo);
        mensajeGrupo.setGrupo(this);
        return this;
    }

    public Grupo removeMensajes(MensajeGrupo mensajeGrupo) {
        this.mensajeses.remove(mensajeGrupo);
        mensajeGrupo.setGrupo(null);
        return this;
    }

    public Set<Encuesta> getEncuestases() {
        return this.encuestases;
    }

    public void setEncuestases(Set<Encuesta> encuestas) {
        if (this.encuestases != null) {
            this.encuestases.forEach(i -> i.setGrupo(null));
        }
        if (encuestas != null) {
            encuestas.forEach(i -> i.setGrupo(this));
        }
        this.encuestases = encuestas;
    }

    public Grupo encuestases(Set<Encuesta> encuestas) {
        this.setEncuestases(encuestas);
        return this;
    }

    public Grupo addEncuestas(Encuesta encuesta) {
        this.encuestases.add(encuesta);
        encuesta.setGrupo(this);
        return this;
    }

    public Grupo removeEncuestas(Encuesta encuesta) {
        this.encuestases.remove(encuesta);
        encuesta.setGrupo(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Grupo)) {
            return false;
        }
        return getId() != null && getId().equals(((Grupo) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Grupo{" +
            "id=" + getId() +
            ", nombre='" + getNombre() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", escudo='" + getEscudo() + "'" +
            ", escudoContentType='" + getEscudoContentType() + "'" +
            ", limiteParticipantes=" + getLimiteParticipantes() +
            ", estado='" + getEstado() + "'" +
            "}";
    }
}
