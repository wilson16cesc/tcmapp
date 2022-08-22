package com.tcm.tcmapp.dao;

import com.tcm.tcmapp.entity.Usuario;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class UsuarioDAO extends BaseDAO<Usuario> {

    public UsuarioDAO() {
        super.setEntityClass(Usuario.class);
    }


}
