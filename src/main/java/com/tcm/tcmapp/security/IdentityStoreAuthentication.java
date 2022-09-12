package com.tcm.tcmapp.security;

import com.tcm.tcmapp.dao.UsuarioDAO;
import com.tcm.tcmapp.entity.Usuario;
import com.tcm.tcmapp.view.LoginView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.security.enterprise.identitystore.CredentialValidationResult;
import javax.security.enterprise.identitystore.IdentityStore;
import java.util.*;

import static javax.security.enterprise.identitystore.CredentialValidationResult.INVALID_RESULT;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;

@ApplicationScoped
public class IdentityStoreAuthentication implements IdentityStore {

    @Inject
    Logger logger;

    @Inject
    UsuarioDAO usuarioDAO;

    @Inject
    Pbkdf2PasswordHash passwordHash;

    Map<String, String> users = new HashMap<>();

    @PostConstruct
    private void init() {
        logger.info("Cargando datos para autenticación");
        List<Usuario> userList = usuarioDAO.findAllActive();
        userList.forEach(u -> 
                users.put(u.getUsername(), u.getPassword())
        );

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
        String storedPassword = users.get(credential.getCaller());
        char[] charArrayPassword = credential.getPassword().getValue();
        if (passwordHash.verify(charArrayPassword, storedPassword)) {
            return new CredentialValidationResult(credential.getCaller());
        }
        return INVALID_RESULT;
    }

}
