package com.tcm.tcmapp.servlet;

import com.tcm.tcmapp.entity.Permiso;
import com.tcm.tcmapp.security.SecurityHelper;
import javax.inject.Inject;
import javax.security.enterprise.SecurityContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@WebServlet("/welcome")
@ServletSecurity(@HttpConstraint(rolesAllowed = "ADMIN"))
public class WelcomeServlet extends HttpServlet {

    @Inject
    private SecurityContext securityContext;
    
    @Inject
    SecurityHelper securityHelper;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        securityContext.hasAccessToWebResource("/protectedServlet", "GET");
        resp.getWriter().write("" +
                "Authentication type :" + req.getAuthType() + "\n" +
                "Caller Principal :" + securityContext.getCallerPrincipal().getName() + "\n" +
                "User in Role USER_ROLE :" + securityContext.isCallerInRole("USER") + "\n" +
                "User in Role ADMIN_ROLE :" + securityContext.isCallerInRole("ADMIN") + "\n" +
                "User in Role perm:UsuariosWrite :" + securityContext.isCallerInRole("perm:UsuariosWrite") + "\n" +
                "User in Role perm:UsuariosWrite :" + securityHelper.hasPermissions(Arrays.asList(new Permiso("UsuariosWrite", "UsuariosWrite"))) + "\n" +
                "");
    }
}