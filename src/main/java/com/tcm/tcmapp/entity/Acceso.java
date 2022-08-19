package com.tcm.tcmapp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "seg_accesos")
public class Acceso extends BaseEntity {
    @Id
    @Column(name = "id", nullable = false, length = 1)
    private String id;

    @Column(name = "nombre", nullable = false, unique = true, length = 100)
    private String nombre;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}