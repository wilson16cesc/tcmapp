package com.tcm.tcmapp.security;

import com.tcm.tcmapp.dao.UsuarioDAO;
import com.tcm.tcmapp.entity.Rol;
import com.tcm.tcmapp.entity.Usuario;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;
import java.util.*;
import java.util.stream.Collectors;


@ApplicationScoped
class IdentityStore4Authorization implements IdentityStore {

    private final Map<String, List<String>> userRoles = new HashMap<>();

    public IdentityStore4Authorization() {

    }
    @Inject
    UsuarioDAO usuarioDAO;

    @PostConstruct
    private void init() {
        List<Usuario> usuariosConRoles = usuarioDAO.findAllWithRoles();
        usuariosConRoles.forEach(usuario-> {
            List<String> roles = usuario.getRoles().stream()
                    .map(Rol::getNombre)
                    .collect(Collectors.toList());
            userRoles.put(usuario.getUsername(), roles);
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