package com.tcm.tcmapp.dao.parametros;

import com.tcm.tcmapp.dao.base.BaseDAO;
import com.tcm.tcmapp.entity.parametros.Listas;
import com.tcm.tcmapp.entity.parametros.Parametros;

import javax.ejb.Stateless;

@Stateless
public class ParametrosDAO extends BaseDAO<Parametros> {

    public ParametrosDAO(){super.setEntityClass(Parametros.class); }

    public Parametros findById(Long id){
        Parametros parametro = getEntityManager().find(Parametros.class, id);
        return parametro;
    }
}
