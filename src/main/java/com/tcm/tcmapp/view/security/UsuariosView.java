package com.tcm.tcmapp.view.security;

import com.tcm.tcmapp.entity.security.Rol;
import com.tcm.tcmapp.entity.security.Usuario;
import com.tcm.tcmapp.service.security.RolesService;
import com.tcm.tcmapp.service.security.UsuariosService;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;
import org.slf4j.Logger;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Named
@ViewScoped
public class UsuariosView implements Serializable {

    @Inject
    UsuariosService usuariosService;
    @Inject
    Logger logger;

    @Inject
    RolesService rolesService;

    @Inject
    private transient Pbkdf2PasswordHash passwordHash;

    private List<Usuario> usuarios;
    private List<Rol> roles;
    private Usuario selectedUsuario;

    public UsuariosView() {
        usuarios = new ArrayList<>();
        selectedUsuario = new Usuario();
        roles = new ArrayList<>();
    }

    @PostConstruct
    protected void init() {
        String accion = String.valueOf(Faces.getRequestParameter("accion"));
        logger.info("accion:{}", accion);
        switch (accion) {
            case "crear":
                selectedUsuario = new Usuario();
                roles = rolesService.findAllActiveWithPermisos();
                break;
            case "editar":
                String userId = Faces.getRequestParameter("userId");
                selectedUsuario = usuariosService.findById(Long.valueOf(userId));
                roles = rolesService.findAllActiveWithPermisos();
                break;
            default:
                usuarios = usuariosService.findAll();
        }
        logger.info("Usuario: {}", selectedUsuario);
    }

    public List<Rol> completeRol(String query) {
        return roles.stream()
                .filter(rol -> rol.getNombre().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }
    public void guardarUsuario(){
        String encodedPassword = passwordHash.generate(selectedUsuario.getPassword().toCharArray());
        selectedUsuario.setPassword(encodedPassword);
        usuariosService.update(selectedUsuario);
        Messages.addInfo(null, "Datos guardados correctamente");
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public Usuario getSelectedUsuario() {
        return selectedUsuario;
    }

    public void setSelectedUsuario(Usuario selectedUsuario) {
        this.selectedUsuario = selectedUsuario;
    }

    public List<Rol> getRoles() {
        return roles;
    }

    public void setRoles(List<Rol> roles) {
        this.roles = roles;
    }

    public void nuevoUsuario(){
        logger.info("ejecutando metodo: nuevoUsuario");
        selectedUsuario = new Usuario();
    }
}
