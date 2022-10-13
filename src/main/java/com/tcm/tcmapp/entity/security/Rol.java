package com.tcm.tcmapp.entity.security;

import com.tcm.tcmapp.entity.base.BaseEntityIdentity;

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
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.nombre);
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
        final Rol other = (Rol) obj;
        return Objects.equals(this.nombre, other.nombre);
    }

    @Override
    public String toString() {
        return "Rol{" + "nombre=" + nombre + '}';
    }




}