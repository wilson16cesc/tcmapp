package com.tcm.tcmapp.entity.parametros;

import com.tcm.tcmapp.entity.base.BaseEntityIdentity;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name="par_parametros")
public class Parametros extends BaseEntityIdentity {

    @Column(name = "tipo_parametro", nullable = false)
    private String tipo_parametro;

    @Column(name = "cod_parametro", nullable = false)
    private String cod_parametro;

    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @OneToMany(mappedBy = "id_parametro", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Datos> datos;

    public Parametros(){

    }

    public Parametros(String tipo_parametro, String cod_parametro, String descripcion) {
        this.tipo_parametro = tipo_parametro;
        this.cod_parametro = cod_parametro;
        this.descripcion = descripcion;
    }

    public String getTipo_parametro() {
        return tipo_parametro;
    }

    public void setTipo_parametro(String tipo_parametro) {
        this.tipo_parametro = tipo_parametro;
    }

    public String getCod_parametro() {
        return cod_parametro;
    }

    public void setCod_parametro(String cod_parametro) {
        this.cod_parametro = cod_parametro;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Datos> getDatos() {
        return datos;
    }

    public void setDatos(List<Datos> datos) {
        this.datos = datos;
    }

    @Override
    public String toString() {
        return "Parametros{" +
                "tipo_parametro='" + tipo_parametro + '\'' +
                ", cod_parametro='" + cod_parametro + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Parametros that = (Parametros) o;
        return Objects.equals(tipo_parametro, that.tipo_parametro) && Objects.equals(cod_parametro, that.cod_parametro) && Objects.equals(descripcion, that.descripcion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tipo_parametro, cod_parametro, descripcion);
    }
}
