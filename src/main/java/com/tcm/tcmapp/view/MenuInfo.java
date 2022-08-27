package com.tcm.tcmapp.view;

import java.util.Objects;


public class MenuInfo {

    public MenuInfo() {
    }

    public MenuInfo(Long paginaId, String name, String type, String icon) {
        this.paginaId = paginaId;
        this.name = name;
        this.type = type;
        this.icon = icon;
    }

    private Long paginaId;
    private String name;
    private String type;
    private String icon;

    public Long getPaginaId() {
        return paginaId;
    }

    public void setPaginaId(Long paginaId) {
        this.paginaId = paginaId;
    }

    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + Objects.hashCode(this.paginaId);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final MenuInfo other = (MenuInfo) obj;
        return Objects.equals(this.paginaId, other.paginaId);
    }

    
    
    
}
