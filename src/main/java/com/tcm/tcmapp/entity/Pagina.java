package com.tcm.tcmapp.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "seg_paginas")
@NamedQueries({@NamedQuery(name = "Pagina.findAllOrderByIdPadre", query = "SELECT p FROM Pagina p WHERE p.activo = TRUE ORDER BY p.idPadre")})
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

    @Column(name = "nivel", nullable = false)
    private Integer nivel;

    public Integer getNivel() {
        return nivel;
    }

    public void setNivel(Integer nivel) {
        this.nivel = nivel;
    }

    public Pagina() {
    }

    public Pagina(Long id, String nombre, String url, Boolean hoja, String icono, Long idPadre, LocalDateTime fechaCrea, String usuarioCrea, Boolean activo, Integer nivel) {
        super.setId(id);
        this.nombre = nombre;
        this.url = url;
        this.hoja = hoja;
        this.icono = icono;
        this.idPadre = idPadre;
        this.nivel = nivel;
        super.setFechaCrea(fechaCrea);
        super.setUsuarioCrea(usuarioCrea);
        super.setActivo(activo);
    }

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