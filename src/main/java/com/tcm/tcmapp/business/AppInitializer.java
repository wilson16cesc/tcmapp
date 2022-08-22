package com.tcm.tcmapp.business;

import com.tcm.tcmapp.dao.UsuarioDAO;
import com.tcm.tcmapp.entity.Usuario;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.inject.Inject;
import java.time.LocalDateTime;
import javax.ejb.Startup;

@Singleton
@Startup
public class AppInitializer {

    @Inject
    UsuarioDAO usuarioDAO;

    @PostConstruct
    private void initializeApp(){
        usuarioDAO.deleteAll();

        Usuario usuario = new Usuario();
        usuario.setUsername("mfigueroa");
        usuario.setPassword("password");
        usuario.setFechaCrea(LocalDateTime.now());
        usuario.setUsuarioCrea("mfigueroa");
        usuario.setActivo(true);

        usuarioDAO.save(usuario);
    }

}
