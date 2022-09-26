package com.tcm.tcmapp.view;

import com.tcm.tcmapp.entity.Pagina;
import com.tcm.tcmapp.entity.Permiso;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MenuBaseTest {

    public static final String PAGINAS = "paginas";
    public static final String MENU = "menuRoot";

    /**
     * con este método se crea la siguiente estructura de menú para el test
     * 0--1--2
     * |      --3
     * |      --4
     */
    protected Map<String, Object> cargarPaginasAndMenu() {
        Map<String,Object> paginasAndMenuRoot = new HashMap<>();

        ArrayList<Pagina> paginasList = new ArrayList<>();
        Pagina paginaCero = new Pagina(0L, "Raiz", null, false, null, -1L, null, null, true);
        paginasList.add(paginaCero);
        Pagina pagina1 = new Pagina(1L, "Item 1", "http://item1.com", false, "save", 0L, LocalDateTime.now(), "mfigueroa", true);
        paginasList.add(pagina1);
            Pagina pagina2 = new Pagina(2L, "Item 2", "http://item2.com", false, "save", 1L, LocalDateTime.now(), "mfigueroa", true);
            paginasList.add(pagina2);
                Pagina pagina3 = new Pagina(3L, "Item 3", "http://item3.com", true, "save", 2L, LocalDateTime.now(), "mfigueroa", true);
                paginasList.add(pagina3);
                Pagina pagina4 = new Pagina(4L, "Item 4", "http://item4.com", true, "save", 2L, LocalDateTime.now(), "mfigueroa", true);
                paginasList.add(pagina4);

        paginasAndMenuRoot.put(PAGINAS, paginasList);

        TreeNode<MenuInfo> menuRoot = new DefaultTreeNode<>();
        TreeNode<MenuInfo> menuItem1
                = new DefaultTreeNode<>(MenuInfo.fromPagina(pagina1));
        menuRoot.getChildren().add(menuItem1);
        TreeNode<MenuInfo> menuItem2
                = new DefaultTreeNode<>(MenuInfo.fromPagina(pagina2));
        menuItem1.getChildren().add(menuItem2);
        TreeNode<MenuInfo> menuItem3
                = new DefaultTreeNode<>(MenuInfo.fromPagina(pagina3));
        menuItem2.getChildren().add(menuItem3);
        TreeNode<MenuInfo> menuItem4
                = new DefaultTreeNode<>(MenuInfo.fromPagina(pagina4));
        menuItem2.getChildren().add(menuItem4);

        paginasAndMenuRoot.put(MENU, menuRoot);
        return paginasAndMenuRoot;
    }
}
