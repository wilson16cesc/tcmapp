package com.tcm.tcmapp.security;

import com.tcm.tcmapp.dao.UsuarioDAO;
import com.tcm.tcmapp.entity.Rol;
import com.tcm.tcmapp.entity.Usuario;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.AuthenticationException;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import javax.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ApplicationScoped
public class CustomAuthentication /*implements HttpAuthenticationMechanism*/ {

    @Inject
    UsuarioDAO usuarioDAO;

    //@Override
    public AuthenticationStatus validateRequest(
            HttpServletRequest request,
            HttpServletResponse response,
            HttpMessageContext httpMessageContext)
            throws AuthenticationException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        Usuario usuario = usuarioDAO.findWithRoles(username, password);
        if (usuario != null) {
            UsuarioDetalle usuarioDetalle = obtenerDatosUsuario(usuario);
            return httpMessageContext.notifyContainerAboutLogin(
                    new CustomPrincipal(usuarioDetalle),
                    new HashSet<>(usuarioDetalle.getRoles()));
        }
        return httpMessageContext.responseUnauthorized();
    }

    private UsuarioDetalle obtenerDatosUsuario(Usuario usuario) {
        List<String> roles = usuario.getRoles().stream()
                .map(Rol::getNombre)
                .collect(Collectors.toList());
        return new UsuarioDetalle(usuario.getUsername(), usuario.getPassword(), roles);
    }
}
