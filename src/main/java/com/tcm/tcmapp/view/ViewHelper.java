package com.tcm.tcmapp.view;

import org.omnifaces.cdi.ViewScoped;
import org.omnifaces.util.Faces;

import java.io.Serializable;
import javax.inject.Named;

@ViewScoped
@Named
public class ViewHelper implements Serializable {

    /**
     * Permite comprobar varios permisos separados por coma
     * @param commaSeparatedPermissions lista de permisos separados por coma, no utilizar prefijo "permission:"
     * @return
     */
    public boolean hasPermissions(String commaSeparatedPermissions) {
        String[] permissions = commaSeparatedPermissions.split(",");
        for(String permission:permissions) {
            boolean response = Faces.isUserInRole("perm:" + permission);
            if (response)
                return true;
        }
        return false;
    }

    public boolean hasPermission(String permission) {
        return Faces.isUserInRole("perm:" + permission);
    }
}
