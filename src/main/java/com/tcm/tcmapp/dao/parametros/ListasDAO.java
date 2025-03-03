package com.tcm.tcmapp.dao.parametros;

import com.tcm.tcmapp.dao.base.BaseDAO;
import com.tcm.tcmapp.entity.parametros.Listas;

import javax.ejb.Stateless;

@Stateless
public class ListasDAO extends BaseDAO<Listas> {

    public ListasDAO (){
        super.setEntityClass(Listas.class);
    }


}
