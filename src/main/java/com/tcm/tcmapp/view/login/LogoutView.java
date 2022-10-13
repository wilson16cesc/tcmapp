package com.tcm.tcmapp.view.login;

import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.servlet.ServletException;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
@RequestScoped
public class LogoutView {
    final Logger logger = LoggerFactory.getLogger(this.getClass().getSimpleName());
    
    public LogoutView() {
    }

    public void logout() {
        try {
            Faces.getRequest().logout();
            Faces.getRequest().getSession().invalidate();
            Faces.redirect(Faces.getRequestContextPath() + "/login.xhtml");
            Messages.addInfo(null, "Sesión cerrada correctamente");
            logger.info("Sesion cerrada correctamente");
        } catch (ServletException e) {
            Messages.addError(null, "Error al cerrar la sesión");
            logger.error("Error al cerrar la sesión");
        }
    }
}
