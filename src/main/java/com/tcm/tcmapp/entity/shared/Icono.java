package com.tcm.tcmapp.entity.shared;

import com.tcm.tcmapp.entity.base.BaseEntityIdentity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "app_iconos")
public class Icono extends BaseEntityIdentity {

    @Column(name = "nombre", nullable = false, unique = true)
    private String nombre;

    public Icono() {
    }

    public Icono(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Icono{" + "nombre=" + nombre + ", usuarioCrea=" + this.getUsuarioCrea() + ", fechaCrea=" + this.getFechaCrea() + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 31 * hash + Objects.hashCode(this.nombre);
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
        final Icono other = (Icono) obj;
        return Objects.equals(this.nombre, other.nombre);
    }


}