package com.tcm.tcmapp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "seg_roles")
public class Rol extends BaseEntityIdentity {
    @Column(name = "nombre", nullable = false, unique = true, length = 100)
    private String nombre;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rol rol = (Rol) o;
        return getId() != null && Objects.equals(getId(), rol.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}