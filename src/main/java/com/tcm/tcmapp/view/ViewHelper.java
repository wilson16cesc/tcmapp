package com.tcm.tcmapp.view;

import org.omnifaces.cdi.ViewScoped;
import org.omnifaces.util.Faces;
import org.slf4j.Logger;

import java.io.Serializable;
import javax.annotation.Resource;
import javax.inject.Inject;
import javax.inject.Named;

@ViewScoped
@Named
public class ViewHelper implements Serializable {

    @Inject
    Logger logger;

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
     * @return
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
    
    
}
