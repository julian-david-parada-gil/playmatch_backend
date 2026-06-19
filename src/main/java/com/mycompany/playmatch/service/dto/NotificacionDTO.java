package com.mycompany.playmatch.service.dto;

import com.mycompany.playmatch.domain.enumeration.EstadoGeneral;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DTO for the {@link com.mycompany.playmatch.domain.Notificacion} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class NotificacionDTO implements Serializable {

    private String id;

    @NotNull
    @Size(max = 120)
    private String titulo;

    private String mensaje;

    @NotNull
    private Boolean leida;

    @NotNull
    private Instant fechaEnvio;

    @NotNull
    private EstadoGeneral estado;

    @NotNull
    private CuentaDTO destinatario;

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

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Boolean getLeida() {
        return leida;
    }

    public void setLeida(Boolean leida) {
        this.leida = leida;
    }

    public Instant getFechaEnvio() {
        return fechaEnvio;
    }

    public void setFechaEnvio(Instant fechaEnvio) {
        this.fechaEnvio = fechaEnvio;
    }

    public EstadoGeneral getEstado() {
        return estado;
    }

    public void setEstado(EstadoGeneral estado) {
        this.estado = estado;
    }

    public CuentaDTO getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(CuentaDTO destinatario) {
        this.destinatario = destinatario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NotificacionDTO)) {
            return false;
        }

        NotificacionDTO notificacionDTO = (NotificacionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, notificacionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "NotificacionDTO{" +
            "id='" + getId() + "'" +
            ", titulo='" + getTitulo() + "'" +
            ", mensaje='" + getMensaje() + "'" +
            ", leida='" + getLeida() + "'" +
            ", fechaEnvio='" + getFechaEnvio() + "'" +
            ", estado='" + getEstado() + "'" +
            ", destinatario=" + getDestinatario() +
            "}";
    }
}
