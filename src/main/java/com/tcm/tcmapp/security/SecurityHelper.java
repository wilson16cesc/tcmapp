package com.tcm.tcmapp.security;

import com.tcm.tcmapp.entity.Permiso;

import javax.enterprise.context.Dependent;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.SecurityContext;
import java.io.Serializable;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import org.slf4j.Logger;

@Stateless
public class SecurityHelper implements Serializable {

    @Resource
    private SessionContext sessionContext;
    
    @Inject
    Logger logger;

    /**
     * Permite comprobar varios permisos pasados como una lista de Objetos
     * @param permisos lista de objetos de tipo Permiso
     * @return
     */
    public boolean hasPermissions(List<Permiso> permisos) {
        logger.info("Usuario autenticado ->-> " + sessionContext.getCallerPrincipal().getName());
        
        for (Permiso permiso : permisos) {
            String permisoStr = "perm:" + permiso.getNombre();
            logger.info("Rol a validar::: " + permisoStr);
            boolean response = sessionContext.isCallerInRole(permisoStr);
            if (response) {
                return true;
            }
        }
        return false;
    }
}
