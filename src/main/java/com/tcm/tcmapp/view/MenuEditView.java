package com.tcm.tcmapp.view;

import com.tcm.tcmapp.business.PaginasService;
import com.tcm.tcmapp.entity.Pagina;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
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
@SessionScoped
public class MenuEditView implements Serializable {

    final Logger logger = LoggerFactory.getLogger(MenuEditView.class.getSimpleName());

    @Inject
    PaginasService paginasService;

    private TreeNode<MenuInfo> menuRoot;

    //todo:quitar estos campos al finalizar el desarrollo de esta clase
    private TreeNode<MenuInfo> root2;
    private String name = "Miguel Figueroa";

    private List<Pagina> paginas;
    private TreeNode<MenuInfo> selectedNode;
    private Pagina selectedPagina;
    

    @PostConstruct
    public void init() {
        logger.info("Ejecutando metodo init - {}", this.getClass().getSimpleName());
        this.paginas = paginasService.getPaginasParaMenu();

        List<Pagina> paginasNivel1 = this.paginas.stream()
                .filter(pagina -> pagina.getIdPadre() == 0L)
                .sorted(Comparator.comparing(Pagina::getFechaCrea))
                .collect(Collectors.toList());

        menuRoot = new DefaultTreeNode<>();

        paginasNivel1.forEach(pagina -> {
            TreeNode<MenuInfo> menuItem =
                    new DefaultTreeNode<>(new MenuInfo(pagina.getId(), pagina.getNombre(), "", pagina.getIcono()));
            menuRoot.getChildren().add(menuItem);
            agregarHijos(pagina, menuItem);
        });

    }

    private void agregarHijos(Pagina pagina, TreeNode<MenuInfo> node) {
        List<Pagina> hijos = this.paginas.stream()
                .filter(p -> p.getIdPadre() == pagina.getId().longValue())
                .collect(Collectors.toList());
        for (Pagina hijo : hijos) {
            if (hijo.getHoja()) {
                TreeNode<MenuInfo> menuItem =
                        new DefaultTreeNode<>(new MenuInfo(hijo.getId(),hijo.getNombre(), "", hijo.getIcono()));
                node.getChildren().add(menuItem);
            } else {
                TreeNode<MenuInfo> menuItem =
                        new DefaultTreeNode<>(new MenuInfo(hijo.getId(),hijo.getNombre(), "", hijo.getIcono()));
                node.getChildren().add(menuItem);
                agregarHijos(hijo, menuItem);
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
    
    public void updateSelectedPagina(){
        selectedPagina = paginas.stream()
                .filter(p->p.getId() == selectedNode.getData().getPaginaId().longValue())
                .findFirst().orElse(new Pagina());
        System.out.println("selectedPagina: "+ selectedPagina);
    }

//    private TreeNode<MenuInfo> populateRoot() {
//        TreeNode<MenuInfo> root = new DefaultTreeNode<>(new MenuInfo("Item 0", "-", "Folder"), null);
//
//        TreeNode<MenuInfo> applications = new DefaultTreeNode<>(new MenuInfo("Item 1", "100kb", "pi pi-save"), root);
//
//        //Applications
//        TreeNode<MenuInfo> primeface = new DefaultTreeNode<>(new MenuInfo("Primefaces", "25kb", "Folder"), applications);
//        TreeNode<MenuInfo> primefacesapp = new DefaultTreeNode<>(new MenuInfo("primefaces.app", "10kb", "pi pi-save"), primeface);
//        TreeNode<MenuInfo> nativeapp = new DefaultTreeNode<>(new MenuInfo("native.app", "10kb", "pi pi-save"), primeface);
//        TreeNode<MenuInfo> mobileapp = new DefaultTreeNode<>(new MenuInfo("mobile.app", "5kb", "pi pi-save"), primeface);
//
//        return root;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TreeNode getRoot2() {
        return root2;
    }

    public void setRoot2(TreeNode root2) {
        this.root2 = root2;
    }

//    private TreeNode populateRoot2() {
//        root2 = new DefaultTreeNode("Files", null);
//        TreeNode node0 = new DefaultTreeNode("Documents", root2);
//        TreeNode node1 = new DefaultTreeNode("Events", root2);
//        TreeNode node2 = new DefaultTreeNode("Movies", root2);
//
//        TreeNode node00 = new DefaultTreeNode("Work", node0);
//        TreeNode node01 = new DefaultTreeNode("Home", node0);
//
//        node00.getChildren().add(new DefaultTreeNode("Expenses.doc"));
//        node00.getChildren().add(new DefaultTreeNode("Resume.doc"));
//        node01.getChildren().add(new DefaultTreeNode("Invoices.txt"));
//
//        TreeNode node10 = new DefaultTreeNode("Meeting", node1);
//        TreeNode node11 = new DefaultTreeNode("Product Launch", node1);
//        TreeNode node12 = new DefaultTreeNode("Report Review", node1);
//
//        TreeNode node20 = new DefaultTreeNode("Al Pacino", node2);
//        TreeNode node21 = new DefaultTreeNode("Robert De Niro", node2);
//
//        node20.getChildren().add(new DefaultTreeNode("Scarface"));
//        node20.getChildren().add(new DefaultTreeNode("Serpico"));
//
//        node21.getChildren().add(new DefaultTreeNode("Goodfellas"));
//        node21.getChildren().add(new DefaultTreeNode("Untouchables"));
//        return root2;
//    }

}
