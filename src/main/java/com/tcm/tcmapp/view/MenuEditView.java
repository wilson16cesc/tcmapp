package com.tcm.tcmapp.view;

import com.tcm.tcmapp.bean.MenuCounter;
import com.tcm.tcmapp.entity.Icono;
import com.tcm.tcmapp.entity.Pagina;
import com.tcm.tcmapp.entity.Permiso;
import com.tcm.tcmapp.service.IconosService;
import com.tcm.tcmapp.service.PaginasService;
import com.tcm.tcmapp.service.PermisosService;
import org.omnifaces.util.Messages;
import org.primefaces.PrimeFaces;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.slf4j.Logger;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Named
@ViewScoped
//@SessionScoped
public class MenuEditView implements Serializable {

    @Inject
    Logger logger;

    @Inject
    PaginasService paginasService;

    @Inject
    IconosService iconosService;

    @Inject
    MenuCounter menuCounter;

    @Inject
    PermisosService permisosService;

    private TreeNode<MenuInfo> menuRoot;

    private List<Pagina> paginas;
    private TreeNode<MenuInfo> selectedNode;
    private Pagina selectedPagina;
    private Pagina paginaEditar;
    private List<String> iconos;
    private List<Permiso> permisos;

    public MenuEditView() {
        this.paginaEditar = new Pagina();
        this.selectedPagina = new Pagina();
    }

    @PostConstruct
    public void init() {
        logger.info("Ejecutando metodo init - {}", this.getClass().getSimpleName());
        this.paginas = paginasService.getPaginasParaMenu();
        this.permisos = permisosService.findAllActive();
        List<Icono> iconosAll = iconosService.findAllActive();
        this.iconos = iconosAll.stream()
                .map(Icono::getNombre)
                .collect(Collectors.toList());
        inicializarMenu();
    }

    private void inicializarMenu() {
        Pagina paginaCero = new Pagina(0L, "Raiz", null, false, null, -1L, null, null, true);
        paginas.add(paginaCero);
        //todo:garantizar que el orden del arbol de menu sea siempre el mismo.
        // en ocasiones sale el nodo 'Item 16' arriba del 'Item 13' y otras veces a la inversa.

        menuRoot = new DefaultTreeNode<>();
        DefaultTreeNode<MenuInfo> nodoCero =
                new DefaultTreeNode<>(MenuInfo.fromPagina(paginaCero));
        nodoCero.setExpanded(true);
        menuRoot.getChildren().add(nodoCero);
        agregarHijos(paginaCero, nodoCero);
    }

    /**
     * Agrega los elementos al arbol del menu basado en la información de las páginas. Metodo recursivo
     * @param pagina información de la página
     * @param node   nodo a ser procesado
     */
    private void agregarHijos(Pagina pagina, TreeNode<MenuInfo> node) {
        List<Pagina> hijos = this.paginas.stream()
                .filter(p -> p.getIdPadre() == pagina.getId().longValue())
                .collect(Collectors.toList());
        for (Pagina hijo : hijos) {
            TreeNode<MenuInfo> menuItem
                    = new DefaultTreeNode<>(MenuInfo.fromPagina(hijo));
            node.setExpanded(true);
            node.getChildren().add(menuItem);
            if (!hijo.getHoja()) {
                agregarHijos(hijo, menuItem);
            }
        }
    }

    /**
     * Agrega un nuevo elemento al menu. es un método de tipo Recursivo
     *
     * @param menuTree  menú base
     * @param idPadre   id del nodo padre
     * @param nuevoHijo nodo a agregar
     */
    private void agregarHijo(TreeNode<MenuInfo> menuTree, long idPadre, TreeNode<MenuInfo> nuevoHijo) {

        for (TreeNode<MenuInfo> treeNode : menuTree.getChildren()) {
            DefaultTreeNode<MenuInfo> nodo = (DefaultTreeNode<MenuInfo>) treeNode;
            System.out.println("Nodo recorrido: " + nodo.getData());
            if (!nodo.getData().getHoja()) {
                if (nodo.getData().getPaginaId() == idPadre) {
                    nodo.getChildren().add(nuevoHijo);
                    System.out.println("nodo al que se agrega el hijo: " + nodo.getData());
                    System.out.println("hijo agregado al menu: " + nuevoHijo.getData());
                    return;
                } else {
                    agregarHijo(nodo, idPadre, nuevoHijo);
                }
            }
        }
    }

    public TreeNode<MenuInfo> getMenuRoot() {
        return menuRoot;
    }

    public void setMenuRoot(TreeNode<MenuInfo> menuRoot) {
        this.menuRoot = menuRoot;
    }

    public TreeNode<MenuInfo> getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(TreeNode<MenuInfo> selectedNode) {
        this.selectedNode = selectedNode;
    }

    public Pagina getSelectedPagina() {
        return selectedPagina;
    }

    public void setSelectedPagina(Pagina selectedPagina) {
        this.selectedPagina = selectedPagina;
    }

    public Pagina getPaginaEditar() {
        return paginaEditar;
    }

    public void setPaginaEditar(Pagina paginaEditar) {
        this.paginaEditar = paginaEditar;
    }

    public void updateSelectedPagina() {
        selectedPagina = paginas.stream()
                .filter(p -> p.getId() == selectedNode.getData().getPaginaId().longValue())
                .findFirst().orElse(new Pagina());
        System.out.println("selectedPagina: " + selectedPagina);
    }

    public void nuevoNodoMenu() {
        this.paginaEditar = new Pagina();
        this.paginaEditar.setHoja(Boolean.FALSE);
        Long idPadre = selectedPagina.getId();
        this.paginaEditar.setIdPadre(idPadre);

        PrimeFaces.current().executeScript("PF('dlgCrearPagina').show();");
    }


    public void agregarActualizarNodoMenu() {

        //si se está creando
        if (paginaEditar.getId() == null) {
            paginaEditar.setId(menuCounter.getNextId());
            paginaEditar.setCreado(Boolean.TRUE);
            this.paginas.add(paginaEditar);
            TreeNode<MenuInfo> menuNode = new DefaultTreeNode<>(MenuInfo.fromPagina(paginaEditar));
            agregarHijo(this.menuRoot, paginaEditar.getIdPadre(), menuNode);
            selectedNode = menuNode;
            selectedNode.setSelected(true);
            selectedPagina = paginaEditar;

        } else { //si se está modificando
            paginaEditar.setEditado(Boolean.TRUE);
            selectedNode.getData().setIcon(paginaEditar.getIcono());
            selectedNode.getData().setName(paginaEditar.getNombre());
        }
        PrimeFaces.current().executeScript("PF('dlgCrearPagina').hide();");
    }

    public void guardarMenu() {
        List<Pagina> paginasEditadas = paginas.stream()
                .filter(pagina -> pagina.getCreado() || pagina.getEditado())
                .collect(Collectors.toList());

        paginasEditadas.forEach(pagina -> {
            if (!pagina.getPermisos().isEmpty()) {
                agregarPermisosPaginaPadre(pagina);
            }
        });

        List<Pagina> paginasPorGuardar = paginas.stream()
                .filter(pagina -> pagina.getCreado() || pagina.getEditado())
                .collect(Collectors.toList());

        paginasPorGuardar.forEach(pagina -> {
            //System.out.println(Arrays.toString(pagina.getPermisos().toArray()));
            pagina.setActivo(true);
            pagina.setCreado(false);
            pagina.setEditado(false);
        });

        logger.info("paginasPorGuardar: " + Arrays.toString(paginasPorGuardar.toArray()));
        try {
            paginasService.saveOrUpdateAll(paginasPorGuardar);
            this.paginas = paginasService.getPaginasParaMenu();
            System.out.println("paginas: " + Arrays.toString(paginas.toArray()));
            inicializarMenu();
            Messages.addInfo(null, "Datos guardados correctamente");
        } catch (Exception e) {
            Messages.addError(null, "Error al guardar los datos", e);
            logger.error("Error al guardar datos del menú", e);
        }
    }

    private void agregarPermisosPaginaPadre(Pagina pagina) {
        Pagina padre = paginas.stream()
                .filter(p -> p.getId().longValue() == pagina.getIdPadre())
                .findFirst().orElse(new Pagina());
        if (padre.getId() != 0) {
            padre.getPermisos().removeAll(pagina.getPermisos());
            padre.getPermisos().addAll(pagina.getPermisos());
            padre.setEditado(true);
            agregarPermisosPaginaPadre(padre);
        }
    }

    public void borrarNodoMenu() {
        TreeNode<MenuInfo> parentNode = selectedNode.getParent();
        paginas.remove(selectedPagina);
        selectedNode.getParent().getChildren().remove(selectedNode);
        selectedNode.setParent(null);
        selectedNode = parentNode;
        updateSelectedPagina();
    }

    public void editarNodoMenu() {
        paginaEditar = selectedPagina;
        PrimeFaces.current().executeScript("PF('dlgCrearPagina').show();");
    }

    public List<Pagina> getPaginas() {
        return paginas;
    }

    public void setPaginas(List<Pagina> paginas) {
        this.paginas = paginas;
    }

    public List<String> completeIcono(String query) {
        return this.iconos.stream()
                .filter(icono -> icono.contains(query))
                .collect(Collectors.toList());
    }

    public List<Permiso> completePermiso(String query) {
        return this.permisos.stream()
                .filter(permiso -> permiso.getNombre().toLowerCase()
                        .contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<String> getIconos() {
        return iconos;
    }

    public void setIconos(List<String> iconos) {
        this.iconos = iconos;
    }

    public List<Permiso> getPermisos() {
        return permisos;
    }

    public void setPermisos(List<Permiso> permisos) {
        this.permisos = permisos;
    }
}
