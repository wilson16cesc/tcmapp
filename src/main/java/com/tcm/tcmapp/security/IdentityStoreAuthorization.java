package com.tcm.tcmapp.security;

import com.tcm.tcmapp.dao.UsuarioDAO;
import com.tcm.tcmapp.dao.VUsuarioRolDAO;
import com.tcm.tcmapp.entity.Rol;
import com.tcm.tcmapp.entity.VUsuarioRol;
import org.slf4j.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;
import java.util.*;
import java.util.stream.Collectors;


@ApplicationScoped
class IdentityStoreAuthorization implements IdentityStore {

    private final Map<String, List<String>> userRoles = new HashMap<>();


    @Inject
    UsuarioDAO usuarioDAO;

    @Inject
    VUsuarioRolDAO vUsuarioRolDAO;

    @Inject
    Logger logger;

    public IdentityStoreAuthorization() {

    }

    @PostConstruct
    private void init() {
        logger.info("Cargando datos para autorización");
        List<VUsuarioRol> usuariosRoles = vUsuarioRolDAO.findAll();
        usuariosRoles.forEach(ur -> {
            userRoles.putIfAbsent(ur.getUsername(), new ArrayList<>());
            userRoles.get(ur.getUsername()).add(ur.getRolename());
            userRoles.put(ur.getUsername(), null);
        });
    }

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
        List<String> roles = userRoles.get(validationResult.getCallerPrincipal().getName());
        return new HashSet<>(roles);
    }

}