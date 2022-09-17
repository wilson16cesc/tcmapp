package com.tcm.tcmapp.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
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
    @Column(name = "nombre", length = 100)
    private String nombre;

    @ManyToMany
    @JoinTable(name = "seg_usuarios_roles",
            joinColumns = @JoinColumn(name = "usuario_id"),
            inverseJoinColumns = @JoinColumn(name = "rol_id"))

    private List<Rol> roles = new ArrayList<>();

    public Usuario() {
    }

    public Usuario(String username, String password, String nombre, List<Rol> roles) {
        this.username = username;
        this.password = password;
        this.nombre = nombre;
        this.roles = roles;
    }

    public List<Rol> getRoles() {
        return roles;
    }

    public void setRoles(List<Rol> roles) {
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "username='" + username + '\'' +
                '}';
    }
}
