package com.tcm.tcmapp.view;

import java.util.LinkedList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.http.HttpServletRequest;


public class FacesUtils {

    //private static final String LOGIN_PRINCIPAL = "PRINCIPAL";
    private static FacesContext getContext() {
        return FacesContext.getCurrentInstance();
    }

    public static void setInfoMessage(String msg) {
        FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, msg, msg);
        getContext().addMessage(null, m);
    }
    
    public static void setWarningMessage(String msg) {
        FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_WARN, msg, msg);
        getContext().addMessage(null, m);
    }

    public static void setErrorMessage(String msg) {

        setErrorMessage(null, msg);
    }
    
    public static void setErrorMessage(String clientId, String msg) {

        FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg);
        getContext().addMessage(clientId, m);
    }

    public static void setFatalMessage(String msg) {

        FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_FATAL, msg, msg);
        getContext().addMessage(null, m);
    }

    public static HttpServletRequest getHttpRequest() {
        return (HttpServletRequest) getContext().getExternalContext().getRequest();
    }

    public static String getAutenticatedUserName() {
        return getHttpRequest().getUserPrincipal().getName();
    }

    /**
     * Convierte una lista de objetos a lista de SelectItem, para un
     * funcionamiento adecuando la clase origen pasada como parámetro debe
     * contar con su respectivo javax.faces.Converter
     *
     * @param objects Lista de objetos origen
     * @return Lista de SelectItem
     */
    public static List<SelectItem> toSelectItemList(List objects) {
        List<SelectItem> results = new LinkedList<SelectItem>();
        for (Object o : objects) {
            results.add(new SelectItem(o, o.toString()));
        }
        return results;
    }

    /**
     * Agrega un mensaje de información sobre un componente
     *
     * @param componentId Id del componente
     * @param message Mensaje a mostrar
     */
    public static void addInfo(String componentId, String message) {
        FacesMessage fm = new FacesMessage(message);
        fm.setSeverity(FacesMessage.SEVERITY_INFO);
        FacesContext.getCurrentInstance().addMessage(componentId, fm);
    }

    /**
     * Agrega un mensaje de error sobre un componente
     *
     * @param componentId Id del componente
     * @param message Mensaje a mostrar
     */
    public static void addError(String componentId, String message) {
        FacesMessage fm = new FacesMessage(message);
        fm.setSeverity(FacesMessage.SEVERITY_ERROR);
        FacesContext.getCurrentInstance().addMessage(componentId, fm);
    }
    
    public static void addWarning(String componentId, String message) {
        FacesMessage fm = new FacesMessage(message);
        fm.setSeverity(FacesMessage.SEVERITY_WARN);
        FacesContext.getCurrentInstance().addMessage(componentId, fm);
    }
    
    public static void addFatal(String componentId, String message) {
        FacesMessage fm = new FacesMessage(message);
        fm.setSeverity(FacesMessage.SEVERITY_FATAL);
        FacesContext.getCurrentInstance().addMessage(componentId, fm);
    }    
    
    /**
     * Sets a session attribute
     * @param key Key for attribute
     * @param value Value to keep in session
     */
    public static void setSessionAttribute(String key, Object value) {
        getHttpRequest().getSession().setAttribute(key, value);
    }
    
    /**
     * Gets a session attribute
     * @param key Key to find
     * @return Object kept in session
     */
    public static Object getSessionAttribute(String key) {
        return getHttpRequest().getSession().getAttribute(key);
    }
    /*
    public static User getAuthenticatedUser()
    {
        return (User)getSessionAttribute("authenticatedUser");
    }
    */

}