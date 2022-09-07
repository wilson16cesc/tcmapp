package com.tcm.tcmapp.security;

import com.tcm.tcmapp.dao.UsuarioDAO;
import com.tcm.tcmapp.entity.Usuario;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;
import java.util.*;

import static javax.security.enterprise.identitystore.CredentialValidationResult.INVALID_RESULT;

@ApplicationScoped
public class IdentityStore4Authentication implements IdentityStore{

    @Inject
    UsuarioDAO usuarioDAO;

    Map<String, String> users = new HashMap<>();


    @PostConstruct
    private void init() {
        List<Usuario> userList = usuarioDAO.findAllActive();
        userList.forEach(u-> users.put(u.getUsername(), u.getPassword()));

    }

    @Override
    public int priority() {
        return 70;
    }

    @Override
    public Set<ValidationType> validationTypes() {
        return EnumSet.of(ValidationType.VALIDATE);
    }

    public CredentialValidationResult validate(UsernamePasswordCredential credential) {
        String password = users.get(credential.getCaller());
        if (password != null && password.equals(credential.getPasswordAsString())) {
            return new CredentialValidationResult(credential.getCaller());
        }
        return INVALID_RESULT;
    }

}