package com.tcm.tcmapp.view.parametros;

import com.tcm.tcmapp.entity.parametros.Datos;
import com.tcm.tcmapp.entity.parametros.Parametros;
import com.tcm.tcmapp.service.parametros.DatosService;
import com.tcm.tcmapp.service.parametros.ParametrosService;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;
import org.slf4j.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class DatosView implements Serializable {

    @Inject
    DatosService datosService;

    @Inject
    ParametrosService parametrosService;

    @Inject
    Logger logger;

    private Parametros selectedParametro;

    private Datos selectedDato;

    private  String parametroId;


    public DatosView(){
        selectedDato = new Datos();
        selectedParametro = new Parametros();
    }
    @PostConstruct
    protected void init(){
        String accion = String.valueOf(Faces.getRequestParameter("accion"));
        logger.info("accion:{}", accion);

        //parametroId = Faces.getRequestParameter("parametroId");
        //selectedParametro = parametrosService.findById(Long.valueOf(parametroId));

        switch (accion) {
            case "crear":
                parametroId = Faces.getRequestParameter("parametroId");
                selectedParametro = parametrosService.findById(Long.valueOf(parametroId));
                break;
            case "editar":
                String datosId = Faces.getRequestParameter("datosId");
                selectedDato = datosService.findById(Long.valueOf(datosId));
                parametroId = Faces.getRequestParameter("parametroId");
                selectedParametro = parametrosService.findById(Long.valueOf(parametroId));
            break;
            default:
                //parametroId = Faces.getRequestParameter("parametroId");
                //selectedParametro = parametrosService.findById(Long.valueOf(parametroId));
        }
    }

    public void guardarDato() {
        Messages.addInfo(null, selectedParametro.getCod_parametro());
        selectedDato.setId_parametro(selectedParametro);
        datosService.update(selectedDato);
        Messages.addInfo(null, "Datos guardados correctamente");
    }

    public Datos getSelectedDato() {
        return selectedDato;
    }

    public void setSelectedDato(Datos selectedDato) {
        this.selectedDato = selectedDato;
    }

    public Parametros getSelectedParametro() {
        return selectedParametro;
    }

    public void setSelectedParametro(Parametros selectedParametro) {
        this.selectedParametro = selectedParametro;
    }


}
