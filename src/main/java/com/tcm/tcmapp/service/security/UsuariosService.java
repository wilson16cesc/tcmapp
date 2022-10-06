package com.tcm.tcmapp.service.security;

import com.tcm.tcmapp.dao.security.UsuarioDAO;
import com.tcm.tcmapp.entity.security.Usuario;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.List;

@Stateless
public class UsuariosService {

    @Inject
    UsuarioDAO usuarioDAO;


    public List<Usuario> findAll() {
        return usuarioDAO.findAll();
    }

    public Usuario findById(Long id) {
        return usuarioDAO.findByIdWithRoles(id);
    }

    public void update(Usuario usuario) {
        usuarioDAO.update(usuario);
    }
}
