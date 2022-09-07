package com.tcm.tcmapp.security;

import javax.enterprise.context.ApplicationScoped;
import javax.security.enterprise.authentication.mechanism.http.*;

@CustomFormAuthenticationMechanismDefinition(
        loginToContinue = @LoginToContinue(
                loginPage = "/login.xhtml",
                errorPage = "/login.xhtml",
                useForwardToLogin = false
        )
)

@ApplicationScoped
public class AppConfig {

}
