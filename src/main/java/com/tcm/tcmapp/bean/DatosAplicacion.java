package com.tcm.tcmapp.bean;


import com.tcm.tcmapp.dao.IconoDAO;
import com.tcm.tcmapp.entity.Icono;
import org.omnifaces.cdi.Eager;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;


@ApplicationScoped
@Eager
@Named
public class DatosAplicacion {

    @Inject
    IconoDAO iconoDAO;

    private List<Icono> iconos = new ArrayList<>();

    @PostConstruct
    public void initApplicationData() {
        iconos = iconoDAO.findAllActive();

    }

    public List<Icono> getIconos() {
        return iconos;
    }

    public void setIconos(List<Icono> iconos) {
        this.iconos = iconos;
    }
}
