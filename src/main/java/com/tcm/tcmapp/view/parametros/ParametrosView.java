package com.tcm.tcmapp.view.parametros;

import com.tcm.tcmapp.entity.parametros.Parametros;
import com.tcm.tcmapp.service.parametros.ParametrosService;
import org.omnifaces.util.Messages;
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
public class ParametrosView implements Serializable {

    @Inject
    ParametrosService parametrosService;

    @Inject
    Logger logger;

    private List<Parametros> parametros;

    private Parametros selectedParametro;

    public ParametrosView(){
        parametros = new ArrayList<>();
        selectedParametro = new Parametros();
    }

    @PostConstruct
    protected void init(){

        parametros = parametrosService.findAll();
    }

    public void guardarParametros() {
        parametrosService.update(selectedParametro);
        Messages.addInfo(null, "Datos guardados correctamente");
    }

    public void nuevoParametro() {
        logger.info("ejecutando metodo: nuevoParametro");
        selectedParametro = new Parametros();
    }

    public List<Parametros> getParametros() {
        return parametros;
    }

    public void setParametros(List<Parametros> parametros) {
        this.parametros = parametros;
    }

    public Parametros getSelectedParametro() {
        return selectedParametro;
    }

    public void setSelectedParametro(Parametros selectedParametro) {
        this.selectedParametro = selectedParametro;
    }
}
