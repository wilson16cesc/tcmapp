package com.tcm.tcmapp.view.login;

import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.RequestScoped;
import javax.faces.annotation.FacesConfig;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.SecurityContext;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.Password;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.validation.constraints.NotNull;
import java.io.IOException;

import static javax.security.enterprise.authentication.mechanism.http.AuthenticationParameters.withParams;

@FacesConfig
@Named
@RequestScoped
public class LoginView {

    final Logger logger = LoggerFactory.getLogger(LoginView.class.getSimpleName());

    @Inject
    private SecurityContext securityContext;

    @NotNull
    private String username;
    @NotNull
    private String password;

    public void autenticar() throws IOException {
        
        Credential credential = new UsernamePasswordCredential(username, new Password(password));

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
