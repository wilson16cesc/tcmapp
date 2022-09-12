package com.tcm.tcmapp.entity;

import javax.persistence.*;

@Entity
@Table(name = "vseg_usuarios_roles")
//@NamedQueries({
//        @NamedQuery(name = "VUsuarioRol.findByUsername", query = "SELECT ur FROM VUsuarioRol ur WHERE ur.username=:username AND ur.activo = TRUE"),
//})
public class VUsuarioRol {
    @Id
    @Column(name = "id", nullable = false, length = 100)
    private String id;

    @Column(name = "username", nullable = false, length = 100)
    private String username;

    @Column(name = "rolename", nullable = false, length = 100)
    private String rolename;

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
