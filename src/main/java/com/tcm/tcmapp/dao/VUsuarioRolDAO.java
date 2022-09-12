package com.tcm.tcmapp.dao;

import com.tcm.tcmapp.entity.VUsuarioRol;

import javax.ejb.Stateless;
import java.util.List;

@Stateless
public class VUsuarioRolDAO extends BaseDAO<VUsuarioRol> {

    public VUsuarioRolDAO() {
        super.setEntityClass(VUsuarioRol.class);
    }

    public List<VUsuarioRol> findByUsername(String username) {
        return findActiveByField("username", username);
    }
}
