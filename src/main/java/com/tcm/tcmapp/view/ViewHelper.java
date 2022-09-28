package com.tcm.tcmapp.view;

import com.tcm.tcmapp.dao.GlobalConfigDAO;
import com.tcm.tcmapp.entity.GlobalConfig;
import com.tcm.tcmapp.entity.Permiso;
import org.omnifaces.cdi.ViewScoped;
import org.omnifaces.util.Faces;
import org.slf4j.Logger;

import java.io.Serializable;
import java.util.List;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.inject.Named;

@ViewScoped
@Named
public class ViewHelper implements Serializable {

    public static final String FILAS_TABLAS = "FILAS_TABLAS";

    @Inject
    Logger logger;

    @Inject
    GlobalConfigDAO globalConfigDAO;

    @Resource(name = "com.tcm.dates.timezone")
    private String timeZone;

    @Resource(name = "com.tcm.dates.datePattern")
    private String datePattern;

    @Resource(name = "com.tcm.dates.dateTimePattern")
    private String dateTimePattern;


    /**
     * Permite comprobar varios permisos separados por coma
     * @param commaSeparatedPermissions lista de permisos separados por coma, no
     * utilizar prefijo "permission:"
     * @return true o false
     */
    public boolean hasPermissions(String commaSeparatedPermissions) {
        String[] permissions = commaSeparatedPermissions.split(",");
        for (String permission : permissions) {
            boolean response = Faces.isUserInRole("perm:" + permission);
            if (response) {
                return true;
            }
        }
        return false;
    }

    public boolean hasPermission(String permission) {
        return Faces.isUserInRole("perm:" + permission);
    }
    /**
     * Permite comprobar varios permisos pasados como una lista de Objetos
     * @param permisos lista de objetos de tipo Permiso
     * @return true o false dependiendo de si el usuario autenticado tiene alguno de los permisos
     */
    public boolean hasPermissions(List<Permiso> permisos) {
        for (Permiso permiso : permisos) {
            String permisoStr = "perm:" + permiso.getNombre();
            boolean response = Faces.isUserInRole(permisoStr);
            if (response) {
                return true;
            }
        }
        return false;
    }
    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getDatePattern() {
        return datePattern;
    }

    public void setDatePattern(String datePattern) {
        this.datePattern = datePattern;
    }

    public String getDateTimePattern() {
        return dateTimePattern;
    }

    public void setDateTimePattern(String dateTimePattern) {
        this.dateTimePattern = dateTimePattern;
    }

    public Long getFilasTablas(){
        GlobalConfig filasTablas = globalConfigDAO.findById(FILAS_TABLAS);
        return filasTablas.getIntegerValue();
    }

}
