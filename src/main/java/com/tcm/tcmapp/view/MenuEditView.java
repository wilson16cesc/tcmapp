package com.tcm.tcmapp.view;

import com.tcm.tcmapp.bean.MenuCounter;
import com.tcm.tcmapp.entity.Pagina;
import com.tcm.tcmapp.service.PaginasService;
import org.omnifaces.util.Messages;
import org.primefaces.PrimeFaces;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Named
@SessionScoped
public class MenuEditView implements Serializable {

    final Logger logger = LoggerFactory.getLogger(MenuEditView.class.getSimpleName());

    @Inject
    PaginasService paginasService;
    @Inject
    MenuCounter menuCounter;

    private TreeNode<MenuInfo> menuRoot;

    private List<Pagina> paginas;
    private TreeNode<MenuInfo> selectedNode;
    private Pagina selectedPagina;
    private Pagina paginaEditar;

    public MenuEditView() {
        this.paginaEditar = new Pagina();
        this.selectedPagina = new Pagina();
    }

    @PostConstruct
    public void init() {
        logger.info("Ejecutando metodo init - {}", this.getClass().getSimpleName());
        this.paginas = paginasService.getPaginasParaMenu();
        inicializarMenu();
    }

    private void inicializarMenu() {
        //todo:garantizar que el orden del arbol de menu sea siempre el mismo.
        // en ocasiones sale el nodo 'Item 16' arriba del 'Item 13' y otras veces a la inversa.
        List<Pagina> paginasNivel1 = this.paginas.stream()
                .filter(pagina -> pagina.getIdPadre() == 0L)
                .sorted(Comparator.comparing(Pagina::getFechaCrea))
                .collect(Collectors.toList());

        menuRoot = new DefaultTreeNode<>();

        paginasNivel1.forEach(pagina -> {
            TreeNode<MenuInfo> menuItem
                    = new DefaultTreeNode<>(MenuInfo.fromPagina(pagina));
            menuItem.setExpanded(true);
            menuRoot.getChildren().add(menuItem);
            agregarHijos(pagina, menuItem);
        });
    }

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
            System.out.println("pagina Crear: " + paginaEditar);
            //si la nueva es hoja dejarla seleccionada en la página
            if (!paginaEditar.getHoja()) {
                selectedNode = menuNode;
                selectedPagina = paginaEditar;
            }
        } else { //si se está modificando
            paginaEditar.setEditado(Boolean.TRUE);
            System.out.println("pagina a modificar: " + paginaEditar);
            selectedNode.getData().setIcon(paginaEditar.getIcono());
            selectedNode.getData().setName(paginaEditar.getNombre());
        }
        System.out.println("Paginas: " + Arrays.toString(paginas.toArray()));
        PrimeFaces.current().executeScript("PF('dlgCrearPagina').hide();");
    }

    public void guardarMenu() {
        List<Pagina> paginasPorCrear = paginas.stream()
                .filter(Pagina::getCreado)
                .collect(Collectors.toList());
        //todo: colocar estos campos en el PrePersist de la entidad.
        // tratar de colocarlos en la clase BaseEntity a ver si funciona
        paginasPorCrear.forEach(pagina -> {
            pagina.setFechaCrea(LocalDateTime.now());
            pagina.setUsuarioCrea("mfigueroa");
            pagina.setActivo(true);
            pagina.setCreado(false);
        });
        List<Pagina> paginasPorActualizar = paginas.stream()
                .filter(Pagina::getEditado)
                .collect(Collectors.toList());
        //todo: colocar estos campos en el PrePersist de la entidad.
        // tratar de colocarlos en la clase BaseEntity a ver si funciona
        paginasPorActualizar.forEach(pagina -> {
            pagina.setFechaEdita(LocalDateTime.now());
            pagina.setUsuario_edita("mfigueroa");
            pagina.setEditado(Boolean.FALSE);
        });
        List<Pagina> paginasPorGuardar = new ArrayList<>();
        paginasPorGuardar.addAll(paginasPorActualizar);
        paginasPorGuardar.addAll(paginasPorCrear);
        System.out.println("paginasPorGuardar: " + Arrays.toString(paginasPorGuardar.toArray()));
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
}
