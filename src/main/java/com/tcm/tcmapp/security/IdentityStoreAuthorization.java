package com.tcm.tcmapp.security;

import com.tcm.tcmapp.dao.VUsuarioRolDAO;
import com.tcm.tcmapp.entity.VUsuarioRol;
import org.slf4j.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;
import java.util.*;

@ApplicationScoped
class IdentityStoreAuthorization implements IdentityStore {

    @Inject
    VUsuarioRolDAO vUsuarioRolDAO;

    @Inject
    Logger logger;

    @Override
    public int priority() {
        return 80;
    }

    @Override
    public Set<ValidationType> validationTypes() {
        return EnumSet.of(ValidationType.PROVIDE_GROUPS);
    }

    @Override
    public Set<String> getCallerGroups(CredentialValidationResult validationResult) {
        List<VUsuarioRol> userRoles = vUsuarioRolDAO.findByUsername(validationResult.getCallerPrincipal().getName());
        List<String> roles = new ArrayList<>();
        userRoles.forEach(ur -> roles.add(ur.getRolename()));

        logger.info("Usuario autenticado: {}, roles asignados: {}",
                validationResult.getCallerPrincipal().getName(), Arrays.toString(roles.toArray()));
        return new HashSet<>(roles);
    }

}
