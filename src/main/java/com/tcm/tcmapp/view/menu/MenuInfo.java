package com.tcm.tcmapp.view.menu;

import com.tcm.tcmapp.entity.shared.Pagina;
import java.util.Objects;

public class MenuInfo {

    public MenuInfo() {
    }

    public MenuInfo(Long paginaId, String name, String icon, Boolean hoja, Long idPadre) {
        this.paginaId = paginaId;
        this.name = name;
        this.icon = icon;
        this.hoja = hoja;
        this.idPadre = idPadre;
    }

    private Long paginaId;
    private String name;
    private String icon;
    private Boolean hoja;
    private Long idPadre;

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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public Boolean getHoja() {
        return hoja;
    }

    public void setHoja(Boolean hoja) {
        this.hoja = hoja;
    }

    public Long getIdPadre() {
        return idPadre;
    }

    public void setIdPadre(Long idPadre) {
        this.idPadre = idPadre;
    }

    @Override
    public String toString() {
        return "MenuInfo{" + "paginaId=" + paginaId + ", name=" + name + ", icon=" + icon + ", hoja=" + hoja + ", idPadre=" + idPadre + '}';
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

    public static MenuInfo fromPagina(Pagina pagina) {
        return new MenuInfo(pagina.getId(), pagina.getNombre(), pagina.getIcono(), pagina.getHoja(), pagina.getIdPadre());
    }

}
