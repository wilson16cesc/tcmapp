package com.tcm.tcmapp.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "seg_paginas")
@NamedQueries({@NamedQuery(name = "Pagina.findAllOrderByIdPadre", query = "SELECT p FROM Pagina p WHERE p.activo = TRUE ORDER BY p.idPadre"),
    @NamedQuery(name="Pagina.findMaxId", query="SELECT MAX(p.id) FROM Pagina p")
})
public class Pagina extends BaseEntity {

    @Id
    @Column(name = "id", nullable = false)
    private Long id;
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

    @Transient
    private Boolean editado = Boolean.FALSE;
    @Transient
    private Boolean creado = Boolean.FALSE;

    public Pagina() {
    }

    public Pagina(Long id, String nombre, String url, Boolean hoja, String icono, Long idPadre, LocalDateTime fechaCrea, String usuarioCrea, Boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.url = url;
        this.hoja = hoja;
        this.icono = icono;
        this.idPadre = idPadre;
        this.setFechaCrea(fechaCrea);
        this.setUsuarioCrea(usuarioCrea);
        this.setActivo(activo);
    }
    public Pagina(String nombre, String url, Boolean hoja, String icono, Long idPadre, LocalDateTime fechaCrea, String usuarioCrea, Boolean activo) {
        this.nombre = nombre;
        this.url = url;
        this.hoja = hoja;
        this.icono = icono;
        this.idPadre = idPadre;
        this.setFechaCrea(fechaCrea);
        this.setUsuarioCrea(usuarioCrea);
        this.setActivo(activo);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Boolean getEditado() {
        return editado;
    }

    public void setEditado(Boolean editado) {
        this.editado = editado;
    }

    public Boolean getCreado() {
        return creado;
    }

    public void setCreado(Boolean creado) {
        this.creado = creado;
    }

    @Override
    public String toString() {
        return "Pagina{" + "id=" + this.getId() + ", " + "nombre=" + nombre + ", url=" + url + ", hoja=" + hoja + ", icono=" + icono + ", idPadre=" + idPadre + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 61 * hash + Objects.hashCode(this.getId());
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Pagina other = (Pagina) obj;
        return Objects.equals(this.getId(), other.getId());
    }

}