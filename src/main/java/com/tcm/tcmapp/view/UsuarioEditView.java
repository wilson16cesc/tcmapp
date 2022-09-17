package com.tcm.tcmapp.view;

import com.tcm.tcmapp.entity.Usuario;
import com.tcm.tcmapp.service.RolesService;
import com.tcm.tcmapp.service.UsuariosService;
import org.omnifaces.util.Faces;
import org.slf4j.Logger;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Objects;


@Named
@ViewScoped
public class UsuarioEditView implements Serializable {

    //todo: pendiente validar si se borra esta clase al terminar el CRUD de usuarios
    @Inject
    UsuariosService usuariosService;

    private Usuario usuario;

    public UsuarioEditView() {
        usuario = new Usuario();
    }

    @PostConstruct
    private void init() {

        String userId = Faces.getRequestParameter("userId");
        if (Objects.nonNull(userId)) {
            usuario = usuariosService.findById(Long.valueOf(userId));
        }

    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
