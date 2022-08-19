package com.tcm.tcmapp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "seg_paginas")
public class Pagina extends BaseEntityIdentity {
    @Column(name = "nombre", nullable = false, unique = true, length = 100)
    private String nombre;

    @Column(name = "url", length = 500)
    private String url;

    @Column(name = "hoja", nullable = false)
    private Boolean hoja = false;

    @Column(name = "icono", length = 50)
    private String icono;

    @Column(name = "id_padre", nullable = false)
    private Long idPadre;

    public Long getIdPadre() {
        return idPadre;
    }

    public void setIdPadre(Long idPadre) {
        this.idPadre = idPadre;
    }

    public String getIcono() {
        return icono;
    }

    public void setIcono(String icono) {
        this.icono = icono;
    }

    public Boolean getHoja() {
        return hoja;
    }

    public void setHoja(Boolean hoja) {
        this.hoja = hoja;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}