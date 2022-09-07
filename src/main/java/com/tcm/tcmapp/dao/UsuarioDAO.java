package com.tcm.tcmapp.dao;

import com.tcm.tcmapp.entity.Usuario;

import javax.ejb.Stateless;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import java.util.List;

@Stateless
public class UsuarioDAO extends BaseDAO<Usuario> {

    public UsuarioDAO() {
        super.setEntityClass(Usuario.class);
    }

    public Usuario findByUsername(String username) {
        try {
            return getEntityManager().createNamedQuery("Usuario.findByUsername", Usuario.class)
                    .setParameter("username", username)
                    .getSingleResult();

        } catch (NoResultException | NonUniqueResultException ex) {
            return null;
        }

    }


    public List<Usuario> findAllWithRoles() {
        List<Usuario> usuariosRoles = findAllActive();
        usuariosRoles.forEach(Usuario::getRoles);
        return usuariosRoles;
    }

    public Usuario findWithRoles(String username, String password) {
        try {
            Usuario usuario = getEntityManager().createNamedQuery("Usuario.findByUsernameAndPassword", Usuario.class)
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .getSingleResult();
            usuario.getRoles();
            return usuario;
        } catch (NoResultException | NonUniqueResultException ex) {
            return null;
        }
    }
}
