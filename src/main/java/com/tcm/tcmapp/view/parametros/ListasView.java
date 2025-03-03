package com.tcm.tcmapp.view.parametros;

import com.tcm.tcmapp.entity.parametros.Listas;
import com.tcm.tcmapp.service.parametros.ListasService;
import org.slf4j.Logger;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Named
@ViewScoped
public class ListasView implements Serializable {

    @Inject
    ListasService listasService;

    @Inject
    Logger logger;

    private List<Listas> listas;

    private String prueba;

    public ListasView(){
        listas = new ArrayList<>();
        prueba = "hola Perros";
    }

    @PostConstruct
    protected void init(){
        logger.info("mostrando las listas ");
        listas = new ArrayList<>();
        Listas lista = new Listas();
        lista.setId(10L);
        lista.setTipo_lista("Tipo_DB");
        lista.setCod_lista("Oracle");
        listas.add(lista);
        System.out.println(listasService.findAll());
        listas = listasService.findAll();
       // prueba = listas.get(0).toString();
    }

    public List<Listas> getListas() {
        return listas;
    }

    public void setListas(List<Listas> listas) {
        this.listas = listas;
    }

    public String getPrueba() {
        return prueba;
    }

    public void setPrueba(String prueba) {
        this.prueba = prueba;
    }
}
