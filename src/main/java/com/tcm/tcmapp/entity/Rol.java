package com.tcm.tcmapp.entity;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "seg_roles")
public class Rol extends BaseEntityIdentity {

    public Rol() {
        this.permisos = new ArrayList<>();
    }

    public Rol(String nombre) {
        this.nombre = nombre;
    }

    public Rol(String nombre, List<Permiso> permisos) {
        this.nombre = nombre;
        this.permisos = permisos;
    }

    @Column(name = "nombre", nullable = false, unique = true, length = 100)
    private String nombre;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE })
    @JoinTable(name = "seg_roles_permisos",
            joinColumns = @JoinColumn(name = "rol_id"),
            inverseJoinColumns = @JoinColumn(name = "permiso_id"))
    private List<Permiso> permisos = new ArrayList<>();

    public List<Permiso> getPermisos() {
        return permisos;
    }

    public void setPermisos(List<Permiso> permisos) {
        this.permisos = permisos;
    }

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