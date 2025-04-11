package com.tcm.tcmapp.entity.parametros;

import com.tcm.tcmapp.entity.base.BaseEntityIdentity;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name="par_dato")
public class Datos extends BaseEntityIdentity {

    @Column(name = "codigo_dato", nullable = false)
    private String codigo_dato;
    @Column(name = "descripcion_dato", nullable = false)
    private String descripcion_dato;
    @Column(name = "valor_numerico", nullable = true)
    private long valor_numerico;
    @Column(name = "valor_texto", nullable = true)
    private String valor_texto;
    @Column(name = "valor_fecha", nullable = true)
    private Date valor_fecha;

    @ManyToOne
    @JoinColumn(name="id_parametro", nullable = false)
    private Parametros id_parametro;

    public Datos() {
    }

    public Datos(String codigo_dato, String descripcion_dato, long valor_numerico, String valor_texto, Date valor_fecha, Parametros id_parametro) {
        this.codigo_dato = codigo_dato;
        this.descripcion_dato = descripcion_dato;
        this.valor_numerico = valor_numerico;
        this.valor_texto = valor_texto;
        this.valor_fecha = valor_fecha;
        this.id_parametro = id_parametro;
    }

    public String getCodigo_dato() {
        return codigo_dato;
    }

    public void setCodigo_dato(String codigo_dato) {
        this.codigo_dato = codigo_dato;
    }

    public String getDescripcion_dato() {
        return descripcion_dato;
    }

    public void setDescripcion_dato(String descripcion_dato) {
        this.descripcion_dato = descripcion_dato;
    }

    public long getValor_numerico() {
        return valor_numerico;
    }

    public void setValor_numerico(long valor_numerico) {
        this.valor_numerico = valor_numerico;
    }

    public String getValor_texto() {
        return valor_texto;
    }

    public void setValor_texto(String valor_texto) {
        this.valor_texto = valor_texto;
    }

    public Date getValor_fecha() {
        return valor_fecha;
    }

    public void setValor_fecha(Date valor_fecha) {
        this.valor_fecha = valor_fecha;
    }

    public Parametros getId_parametro() {
        return id_parametro;
    }

    public void setId_parametro(Parametros id_parametro) {
        this.id_parametro = id_parametro;
    }

    @Override
    public String toString() {
        return "Datos{" +
                "codigo_dato='" + codigo_dato + '\'' +
                ", descripcion_dato='" + descripcion_dato + '\'' +
                ", valor_numerico=" + valor_numerico +
                ", valor_texto='" + valor_texto + '\'' +
                ", valor_fecha=" + valor_fecha +
                ", id_parametro=" + id_parametro +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Datos datos = (Datos) o;
        return valor_numerico == datos.valor_numerico && Objects.equals(codigo_dato, datos.codigo_dato) && Objects.equals(descripcion_dato, datos.descripcion_dato) && Objects.equals(valor_texto, datos.valor_texto) && Objects.equals(valor_fecha, datos.valor_fecha) && Objects.equals(id_parametro, datos.id_parametro);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codigo_dato, descripcion_dato, valor_numerico, valor_texto, valor_fecha, id_parametro);
    }
}
