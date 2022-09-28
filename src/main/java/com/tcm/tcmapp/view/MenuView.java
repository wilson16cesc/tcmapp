package com.tcm.tcmapp.view;

import com.tcm.tcmapp.entity.Pagina;
import com.tcm.tcmapp.entity.Permiso;
import com.tcm.tcmapp.security.SecurityHelper;
import com.tcm.tcmapp.service.PaginasService;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Named
//@ViewScoped
@SessionScoped
public class MenuView implements Serializable {

    public static final String ICON_PREFIX = "pi pi-";
    final Logger logger = LoggerFactory.getLogger(MenuView.class.getSimpleName());
    @Inject
    PaginasService paginasService;
    @Inject
    SecurityHelper securityHelper;
    private String name = "Miguel Figueroa";
    private MenuModel menuModel;
    private List<Pagina> paginas;

    private void agregarHijos(Pagina pagina, DefaultSubMenu submenu) {
        List<Pagina> hijos = this.paginas.stream()
                .filter(p -> p.getIdPadre() == pagina.getId().longValue())
                .collect(Collectors.toList());
        for (Pagina hijo : hijos) {
            //List<Permiso> permisosPagina = hijo.getPermisos();
            //if (securityHelper.hasPermissions(permisosPagina)) {
                String icono = ICON_PREFIX + hijo.getIcono();
                if (hijo.getHoja()) {
                    DefaultMenuItem menuItem = DefaultMenuItem.builder()
                            .id(hijo.getId() + "")
                            .value(hijo.getNombre())
                            .icon(ICON_PREFIX + hijo.getIcono())
                            .outcome(hijo.getUrl().startsWith("/") ? hijo.getUrl() : null)
                            .build();
                    submenu.getElements().add(menuItem);
                } else {
                    DefaultSubMenu newSubmenu = DefaultSubMenu.builder()
                            .id(hijo.getId() + "")
                            .label(hijo.getNombre())
                            .icon(icono)
                            .build();
                    submenu.getElements().add(newSubmenu);
                    agregarHijos(hijo, newSubmenu);
                }
            //}
        }
    }

    @PostConstruct
    public void init() {
        logger.info("Cargando datos del menu - {}", this.getClass().getSimpleName());
        this.paginas = paginasService.getPaginasParaMenu();

        List<Pagina> paginasNivel1 = this.paginas.stream()
                .filter(pagina -> pagina.getIdPadre() == 0L)
                .sorted(Comparator.comparing(Pagina::getFechaCrea))
                .collect(Collectors.toList());

        menuModel = new DefaultMenuModel();

        paginasNivel1.forEach(pagina -> {
            String icono = ICON_PREFIX + pagina.getIcono();
            DefaultSubMenu submenu = DefaultSubMenu.builder()
                    .id(pagina.getId() + "")
                    .label(pagina.getNombre())
                    .icon(icono)
                    .build();
            menuModel.getElements().add(submenu);
            agregarHijos(pagina, submenu);
        });
    }


    public MenuModel getMenuModel() {
        return menuModel;
    }

    public void setMenuModel(MenuModel menuModel) {
        this.menuModel = menuModel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void redirect(String page) {
        logger.info("Pagina a redireccionar: " + page);
    }
}
