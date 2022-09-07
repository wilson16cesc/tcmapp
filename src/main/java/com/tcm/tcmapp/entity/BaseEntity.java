package com.tcm.tcmapp.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
public class BaseEntity implements Serializable{
    @Column(name = "usuario_crea", nullable = false, length = 100)
    private String usuarioCrea = "mfigueroa";

    @Column(name = "fecha_crea", nullable = false)
    private LocalDateTime fechaCrea = LocalDateTime.now();

    @Column(name = "usuario_edita", length = 100)
    private String usuario_edita;

    @Column(name = "fecha_edita")
    private LocalDateTime fechaEdita;

    @Column(name = "activo", nullable = false)
    private Boolean activo = true;

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public String getUsuarioCrea() {
        return usuarioCrea;
    }

    public void setUsuarioCrea(String usuarioCrea) {
        this.usuarioCrea = usuarioCrea;
    }

    public LocalDateTime getFechaCrea() {
        return fechaCrea;
    }

    public void setFechaCrea(LocalDateTime fechaCrea) {
        this.fechaCrea = fechaCrea;
    }

    public String getUsuario_edita() {
        return usuario_edita;
    }

    public void setUsuario_edita(String usuario_edita) {
        this.usuario_edita = usuario_edita;
    }

    public LocalDateTime getFechaEdita() {
        return fechaEdita;
    }

    public void setFechaEdita(LocalDateTime fechaEdita) {
        this.fechaEdita = fechaEdita;
    }
}