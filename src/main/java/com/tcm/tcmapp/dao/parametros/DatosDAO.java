package com.tcm.tcmapp.dao.parametros;

import com.tcm.tcmapp.dao.base.BaseDAO;
import com.tcm.tcmapp.entity.parametros.Datos;
import com.tcm.tcmapp.entity.parametros.Listas;

import javax.ejb.Stateless;

@Stateless
public class DatosDAO extends BaseDAO<Datos> {

    public DatosDAO(){
        super.setEntityClass(Datos.class);
    }

    public Datos findById(Long id){
        Datos dato = getEntityManager().find(Datos.class, id);
        return dato;
    }

}
