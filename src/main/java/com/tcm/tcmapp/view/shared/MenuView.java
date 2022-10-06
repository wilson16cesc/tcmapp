package com.tcm.tcmapp.view.shared;

import com.tcm.tcmapp.entity.shared.Pagina;
import com.tcm.tcmapp.service.shared.PaginasService;
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
import java.util.Arrays;
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
    ViewHelper viewHelper;


    private MenuModel menuModel;
    private List<Pagina> paginas;

    @PostConstruct
    public void init() {
        logger.info("Cargando datos del menu - {}", this.getClass().getSimpleName());
        this.paginas = paginasService.getPaginasParaMenu();
        logger.info("Paginas para menu: {}", Arrays.toString(paginas.toArray()));

        List<Pagina> paginasNivel1 = this.paginas.stream()
                .filter(pagina -> pagina.getIdPadre() == 0L)
                .sorted(Comparator.comparing(Pagina::getFechaCrea))
                .collect(Collectors.toList());
        logger.info("paginasNivel1: {}", Arrays.toString(paginasNivel1.toArray()));

        menuModel = new DefaultMenuModel();

        paginasNivel1.forEach(pagina -> {
            logger.info("viewHelper.hasPermissions(hijo.getPermisos()): {}", viewHelper.hasPermissions(pagina.getPermisos()));
            if (viewHelper.hasPermissions(pagina.getPermisos())) {
                String icono = ICON_PREFIX + pagina.getIcono();
                DefaultSubMenu submenu = DefaultSubMenu.builder()
                        .id(pagina.getId() + "")
                        .label(pagina.getNombre())
                        .icon(icono)
                        .build();
                menuModel.getElements().add(submenu);
                agregarHijos(pagina, submenu);
            }
        });
    }

    private void agregarHijos(Pagina pagina, DefaultSubMenu submenu) {
        List<Pagina> hijos = this.paginas.stream()
                .filter(p -> p.getIdPadre() == pagina.getId().longValue())
                .collect(Collectors.toList());
        logger.info("Hijos: {}", Arrays.toString(hijos.toArray()));

        for (Pagina hijo : hijos) {
            if (viewHelper.hasPermissions(hijo.getPermisos())) {
                String icono = ICON_PREFIX + hijo.getIcono();
                if (hijo.getHoja()) {
                    DefaultMenuItem menuItem = DefaultMenuItem.builder()
                            .id(hijo.getId() + "")
                            .value(hijo.getNombre())
                            .icon(ICON_PREFIX + hijo.getIcono())
                            .outcome(hijo.getUrl().startsWith("/") ? hijo.getUrl() : null)
                            .url(hijo.getUrl().startsWith("http") ? hijo.getUrl() : null)
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
            }
        }
    }


    public MenuModel getMenuModel() {
        return menuModel;
    }

    public void setMenuModel(MenuModel menuModel) {
        this.menuModel = menuModel;
    }


    public void redirect(String page) {
        logger.info("Pagina a redireccionar: " + page);
    }

    public void setViewHelper(ViewHelper viewHelper) {
        this.viewHelper = viewHelper;
    }
}
