package com.tcm.tcmapp.entity.parametros;


import com.tcm.tcmapp.entity.base.BaseEntityIdentity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Entity
@Table(name = "par_listas")
public class Listas extends BaseEntityIdentity {


    @Column(name = "tipo_lista", nullable = false)
    private String tipo_lista;

    @Column(name = "cod_lista", nullable = false)
    private String cod_lista;

    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @Column(name = "desc_caracteristica", nullable = false)
    private String desc_caracteristica;

    @Column(name = "icono", nullable = false)
    private String icono;

    public Listas() {

    }

    public Listas(String tipo_lista, String cod_lista, String descripcion, String desc_caracteristica, String icono) {
        this.tipo_lista = tipo_lista;
        this.cod_lista = cod_lista;
        this.descripcion = descripcion;
        this.desc_caracteristica = desc_caracteristica;
        this.icono = icono;
    }

    public String getTipo_lista() {
        return tipo_lista;
    }

    public void setTipo_lista(String tipo_lista) {
        this.tipo_lista = tipo_lista;
    }

    public String getCod_lista() {
        return cod_lista;
    }

    public void setCod_lista(String cod_lista) {
        this.cod_lista = cod_lista;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getDesc_caracteristica() {
        return desc_caracteristica;
    }

    public void setDesc_caracteristica(String desc_caracteristica) {
        this.desc_caracteristica = desc_caracteristica;
    }

    public String getIcono() {
        return icono;
    }

    public void setIcono(String icono) {
        this.icono = icono;
    }


    @Override
    public String toString() {
        return "Listas{" +
                "tipo_lista='" + tipo_lista + '\'' +
                ", cod_lista='" + cod_lista + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", desc_caracteristica='" + desc_caracteristica + '\'' +
                ", icono='" + icono + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Listas listas = (Listas) o;
        return Objects.equals(tipo_lista, listas.tipo_lista) && Objects.equals(cod_lista, listas.cod_lista) && Objects.equals(descripcion, listas.descripcion) && Objects.equals(desc_caracteristica, listas.desc_caracteristica) && Objects.equals(icono, listas.icono);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tipo_lista, cod_lista, descripcion, desc_caracteristica, icono);
    }
}
