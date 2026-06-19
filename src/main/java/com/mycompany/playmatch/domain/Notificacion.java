package com.mycompany.playmatch.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mycompany.playmatch.domain.enumeration.EstadoGeneral;
import jakarta.validation.constraints.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A Notificacion.
 */
@Document(collection = "notificacion")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Notificacion implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @NotNull
    @Size(max = 120)
    @Field("titulo")
    private String titulo;

    @Field("mensaje")
    private String mensaje;

    @NotNull
    @Field("leida")
    private Boolean leida;

    @NotNull
    @Field("fecha_envio")
    private Instant fechaEnvio;

    @NotNull
    @Field("estado")
    private EstadoGeneral estado;

    @DBRef
    @Field("destinatario")
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
    private Cuenta destinatario;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public String getId() {
        return this.id;
    }

    public Notificacion id(String id) {
        this.setId(id);
        return this;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public Notificacion titulo(String titulo) {
        this.setTitulo(titulo);
        return this;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getMensaje() {
        return this.mensaje;
    }

    public Notificacion mensaje(String mensaje) {
        this.setMensaje(mensaje);
        return this;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Boolean getLeida() {
        return this.leida;
    }

    public Notificacion leida(Boolean leida) {
        this.setLeida(leida);
        return this;
    }

    public void setLeida(Boolean leida) {
        this.leida = leida;
    }

    public Instant getFechaEnvio() {
        return this.fechaEnvio;
    }

    public Notificacion fechaEnvio(Instant fechaEnvio) {
        this.setFechaEnvio(fechaEnvio);
        return this;
    }

    public void setFechaEnvio(Instant fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public EstadoGeneral getEstado() {
        return this.estado;
    }

    public Notificacion estado(EstadoGeneral estado) {
        this.setEstado(estado);
        return this;
    }

    public void setEstado(EstadoGeneral estado) {
        this.estado = estado;
    }

    public Cuenta getDestinatario() {
        return this.destinatario;
    }

    public void setDestinatario(Cuenta cuenta) {
        this.destinatario = cuenta;
    }

    public Notificacion destinatario(Cuenta cuenta) {
        this.setDestinatario(cuenta);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Notificacion)) {
            return false;
        }
        return getId() != null && getId().equals(((Notificacion) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Notificacion{" +
            "id=" + getId() +
            ", titulo='" + getTitulo() + "'" +
            ", mensaje='" + getMensaje() + "'" +
            ", leida='" + getLeida() + "'" +
            ", fechaEnvio='" + getFechaEnvio() + "'" +
            ", estado='" + getEstado() + "'" +
            "}";
    }
}
