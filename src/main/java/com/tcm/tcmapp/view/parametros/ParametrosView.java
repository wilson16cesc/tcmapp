package com.tcm.tcmapp.view.parametros;

import com.tcm.tcmapp.entity.parametros.Datos;
import com.tcm.tcmapp.entity.parametros.Parametros;
import com.tcm.tcmapp.service.parametros.DatosService;
import com.tcm.tcmapp.service.parametros.ParametrosService;
import org.omnifaces.util.Faces;
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
    DatosService datosService;

    @Inject
    Logger logger;

    private List<Parametros> parametros;

    private Parametros selectedParametro;

    private Datos selectedDato;

    public ParametrosView(){
        parametros = new ArrayList<>();
        selectedParametro = new Parametros();
        selectedDato = new Datos();
    }

    @PostConstruct
    protected void init(){
        String accion = String.valueOf(Faces.getRequestParameter("accion"));
        logger.info("accion:{}", accion);

        switch (accion) {
            case "crear":
                nuevoParametro();
                break;
            case "editar":
                String parametroId = Faces.getRequestParameter("parametroId");
                selectedParametro = parametrosService.findById(Long.valueOf(parametroId));
                break;
            case "datos":
                String parametroIdDatos = Faces.getRequestParameter("parametroId");
                selectedParametro = parametrosService.findById(Long.valueOf(parametroIdDatos));
                break;
            default:
                parametros = parametrosService.findAll();
        }

    }

    public void guardarParametros() {
        parametrosService.update(selectedParametro);
        Messages.addInfo(null, "Datos guardados correctamente");
    }

    public void guardarDato() {
        Messages.addInfo(null, selectedParametro.getCod_parametro());
        selectedDato.setId_parametro(selectedParametro);
        datosService.update(selectedDato);
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

    public Datos getSelectedDato() {
        return selectedDato;
    }

    public void setSelectedDato(Datos selectedDato) {
        this.selectedDato = selectedDato;
    }
}
