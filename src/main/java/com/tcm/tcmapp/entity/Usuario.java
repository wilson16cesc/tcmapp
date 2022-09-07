package com.tcm.tcmapp.entity;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "seg_usuarios")
@NamedQueries({
        @NamedQuery(name = "Usuario.findByUsername", query = "SELECT u FROM Usuario u WHERE u.username=:username AND u.activo = TRUE"),
        @NamedQuery(name = "Usuario.findByUsernameAndPassword", query = "SELECT u FROM Usuario u WHERE u.username=:username AND u.password=:password AND u.activo = TRUE")
})
public class Usuario extends BaseEntityIdentity {

    @Column(name = "username", nullable = false, unique = true, length = 100)
    private String username;
    @Column(name = "password", nullable = false)
    private String password;
    @ManyToMany
    @JoinTable(name = "seg_usuarios_roles",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "rol_id"))

    private Set<Rol> roles = new LinkedHashSet<>();

    public Usuario() {
    }

    public Usuario(String username, String password, Set<Rol> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
    }

    public Set<Rol> getRoles() {
        return roles;
    }

    public void setRoles(Set<Rol> roles) {
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}