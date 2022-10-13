package com.tcm.tcmapp.audit;

import com.tcm.tcmapp.entity.base.BaseEntity;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.security.enterprise.SecurityContext;
import java.io.Serializable;
import java.security.Principal;
import java.time.LocalDateTime;

@AuditFieldsInterceptor
@Interceptor
public class AuditFieldsInterceptorImpl implements Serializable {

    @Inject
    SecurityContext securityContext;

    /**
     * Método que intercepta los métodos de guardado de entidades para setear los valores de auditoría.
     * (Ejemplo en la clase BaseDAO metodos save y update)
     * @param context Contexto de Invocación del método
     * @return
     * @throws Exception
     */
    @AroundInvoke
    public Object populateAutitFields(InvocationContext context) throws Exception {
        Principal callerPrincipal = securityContext.getCallerPrincipal();
        String authUserName = callerPrincipal != null ? callerPrincipal.getName() : null;
        System.out.println("authUserName: " + authUserName);
        BaseEntity entity = (BaseEntity) context.getParameters()[0];
        if (entity.getUsuarioCrea() == null) {
            entity.setUsuarioCrea(authUserName == null ? "test-user" : authUserName);
            entity.setFechaCrea(LocalDateTime.now());
        } else {
            entity.setUsuarioEdita(authUserName == null ? "test-user" : authUserName);
            entity.setFechaEdita(LocalDateTime.now());
        }
        System.out.println("Entidad con campos de auditoria: " + entity);
        return context.proceed();
    }
}
