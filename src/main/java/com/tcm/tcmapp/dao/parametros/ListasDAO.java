package com.tcm.tcmapp.dao.parametros;

import com.tcm.tcmapp.dao.base.BaseDAO;
import com.tcm.tcmapp.entity.parametros.Listas;
import com.tcm.tcmapp.entity.security.Usuario;

import javax.ejb.Stateless;

@Stateless
public class ListasDAO extends BaseDAO<Listas> {

    public ListasDAO (){
        super.setEntityClass(Listas.class);
    }


    public Listas findById(Long id){
        Listas lista = getEntityManager().find(Listas.class, id);
        return lista;
    }
}
