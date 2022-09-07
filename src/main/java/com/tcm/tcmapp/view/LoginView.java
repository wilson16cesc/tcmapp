package com.tcm.tcmapp.view;

import com.tcm.tcmapp.dao.UsuarioDAO;
import com.tcm.tcmapp.entity.Rol;
import com.tcm.tcmapp.entity.Usuario;
import com.tcm.tcmapp.security.CustomPrincipal;
import com.tcm.tcmapp.security.UsuarioDetalle;
import org.omnifaces.util.Faces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.RequestScoped;
import javax.faces.annotation.FacesConfig;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.SecurityContext;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;
import javax.servlet.ServletException;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import javax.security.enterprise.AuthenticationStatus;
import static javax.security.enterprise.authentication.mechanism.http.AuthenticationParameters.withParams;
import javax.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.Password;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import org.omnifaces.util.Messages;

@FacesConfig
@Named
@RequestScoped
public class LoginView {

    final Logger logger = LoggerFactory.getLogger(LoginView.class.getSimpleName());
    @Inject
    Pbkdf2PasswordHash passwordHash;
    @Inject
    UsuarioDAO usuarioDAO;
    @Inject
    private SecurityContext securityContext;

    @NotNull
    private String username;
    @NotNull
    private String password;

    public void autenticar() throws IOException {

//        String username = Faces.getRequest().getParameter("username");
//        String password = Faces.getRequest().getParameter("password");
        
//        Usuario usuario = usuarioDAO.findWithRoles(username, password);
//        if (usuario != null) {
//            UsuarioDetalle usuarioDetalle = obtenerDatosUsuario(usuario);
//            AuthenticationStatus notifyContainerAboutLogin = httpMessageContext.notifyContainerAboutLogin(
//                    new CustomPrincipal(usuarioDetalle),
//                    new HashSet<>(usuarioDetalle.getRoles()));
//            System.out.println("notifyContainerAboutLogin: "+ notifyContainerAboutLogin);
//        }
//        return "/pages/home.xhtml"+"?faces-redirect=true";
        
        Credential credential = new UsernamePasswordCredential(username, new Password(password));
//        try {
//            Faces.getRequest().login(username, password);
//            return "/pages/home.xhtml"+"?faces-redirect=true";
//        } catch (ServletException ex) {
//            String message = "Usuario o contraseña incorrectos";
//            Messages.addError(null, message);
//            logger.error(message);
//        }

        AuthenticationStatus status = securityContext.authenticate(
                Faces.getRequest(),
                Faces.getResponse(),
                withParams().credential(credential));
        switch (status) {
            case SUCCESS:
                logger.info("SUCCESS");
                Messages.addInfo(null, "Autenticado correctamente");
                Faces.redirect(Faces.getRequestContextPath()+"/pages/home.xhtml");
                //return "/pages/home.xhtml?faces-redirect=true";
                break;
            case SEND_CONTINUE:
                logger.info("SEND_CONTINUE");
                Faces.getContext().responseComplete();
                break;
            case SEND_FAILURE:
                logger.info("SEND_FAILURE");
                Messages.addError(null, "Error de autenticación");
                break;
            default:
                break;
        }

    }

//    private UsuarioDetalle obtenerDatosUsuario(Usuario usuario) {
//        List<String> roles = usuario.getRoles().stream()
//                .map(Rol::getNombre)
//                .collect(Collectors.toList());
//        return new UsuarioDetalle(usuario.getUsername(), usuario.getPassword(), roles);
//    }

    

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
