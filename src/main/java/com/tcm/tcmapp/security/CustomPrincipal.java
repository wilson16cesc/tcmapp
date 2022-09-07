package com.tcm.tcmapp.security;

import com.tcm.tcmapp.entity.Usuario;

import java.security.Principal;

public class CustomPrincipal implements Principal {

    private final UsuarioDetalle usuarioDetalle;

    public CustomPrincipal(UsuarioDetalle usuarioDetalle) {
        this.usuarioDetalle = usuarioDetalle;
    }

    @Override
    public String getName() {
        return usuarioDetalle.getUsername();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + ":" + getName();
    }
}
