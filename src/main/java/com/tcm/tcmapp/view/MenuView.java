package com.tcm.tcmapp.view;

import com.tcm.tcmapp.service.PaginasService;
import com.tcm.tcmapp.entity.Pagina;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.inject.Named;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
@SessionScoped
public class MenuView implements Serializable {
    
    final Logger logger = LoggerFactory.getLogger(MenuView.class.getSimpleName());
    private String name = "Miguel Figueroa";

    private MenuModel menuModel;

    private List<Pagina> paginas;
    @Inject
    PaginasService paginasService;


    private void agregarHijos(Pagina pagina, DefaultSubMenu submenu) {
        List<Pagina> hijos = this.paginas.stream()
                .filter(p -> p.getIdPadre() == pagina.getId().longValue())
                .collect(Collectors.toList());
        for (Pagina hijo : hijos) {
            if (hijo.getHoja()) {
                DefaultMenuItem menuItem = DefaultMenuItem.builder()
                        .id(hijo.getId()+"")
                        .value(hijo.getNombre())
                        .icon(hijo.getIcono())
                        //.command("#{menuView.redirect(\"/pages/menuEdit.xhtml\")}")
                        //.command("#{menuView.redirect(\"hola mundo\")}")
                        .outcome(hijo.getUrl().startsWith("/") ? hijo.getUrl() : null)
                        .build();
                submenu.getElements().add(menuItem);
            } else {
                DefaultSubMenu newSubmenu = DefaultSubMenu.builder()
                        .id(hijo.getId()+"")
                        .label(hijo.getNombre())
                        .icon(hijo.getIcono())
                        .build();
                submenu.getElements().add(newSubmenu);
                agregarHijos(hijo, newSubmenu);
            }
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
            DefaultSubMenu submenu = DefaultSubMenu.builder()
                    .id(pagina.getId()+"")
                    .label(pagina.getNombre())
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

    public void redirect(String page){
        logger.info("Pagina a redireccionar: "+ page);
    }
}
