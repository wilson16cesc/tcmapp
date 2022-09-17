package com.tcm.tcmapp.bean;

import com.tcm.tcmapp.dao.*;
import com.tcm.tcmapp.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;
import java.time.LocalDateTime;
import java.util.*;

@Singleton
@Startup
public class AppInitializer {

    final Logger logger = LoggerFactory.getLogger(AppInitializer.class.getSimpleName());

    @Inject
    UsuarioDAO usuarioDAO;

    @Inject
    PaginaDAO paginaDAO;

    @Inject
    IconoDAO iconoDAO;

    @Inject
    RolDAO rolDAO;

    @Inject
    PermisoDAO permisoDAO;

    @Inject
    Pbkdf2PasswordHash passwordHash;

    @PostConstruct
    @TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
    public void initializeApp() {

        eliminarDatosAplicacion();

        logger.info("Inicializando datos de la aplicación");
        Pagina pagina = paginaDAO.findById(1L);
        if (Objects.isNull(pagina)) {
            crearPaginas();
        }

        Icono icono = iconoDAO.findFirst();
        if (Objects.isNull(icono)) {
            crearIconos();
        }

        Usuario usuario = usuarioDAO.findFirst();
        if (Objects.isNull(usuario)) {
            crearUsuariosRoles();
        }

        Permiso permiso = permisoDAO.findFirst();
        if (Objects.isNull(permiso)) {
            crearPermisos();
        }


        logger.info("Datos de la aplicación inicializados");

    }


    private void eliminarDatosAplicacion() {
        logger.info("Eliminando datos de aplicación");
        paginaDAO.deleteAll();
//        iconoDAO.deleteAll();
        usuarioDAO.deleteAll();
        rolDAO.deleteAll();
        permisoDAO.deleteAll();
    }

    private void crearUsuariosRoles() {
        logger.info("Creando roles y usuarios en la base de datos");
        Rol rol1Admin = new Rol("ADMIN");
        Rol rolUser = new Rol("USER");
        rolDAO.save(rol1Admin);
        rolDAO.save(rolUser);
        Usuario usuario1 = new Usuario("mfigueroa", passwordHash.generate("12345".toCharArray()), "Miguel Figueroa",
                Arrays.asList(rol1Admin, rolUser));
        Usuario usuario2 = new Usuario("usuario", passwordHash.generate("12345".toCharArray()), "Pedro Perez",
                Collections.singletonList(rolUser));
        usuarioDAO.save(usuario1);
        usuarioDAO.save(usuario2);
    }

    private void crearPermisos() {
        Permiso editarMenuRead = new Permiso("EditarMenuRead", "Permite leer la ventana 'Editar Menu'.");
        Permiso editarMenuWrite = new Permiso("EditarMenuWrite", "Permite escribir en la ventana 'Editar Menu");
        Permiso permisosRolesRead = new Permiso("PermisosRolesRead", "Tiene permiso de lectura en la ventana Permisos Roles");
        Permiso permisosRolesWrite = new Permiso("PermisosRolesWrite", "Tiene permiso de escritura en la ventana Permisos Roles");

        List<Permiso> permisos = new ArrayList<>(Arrays.asList(editarMenuRead, editarMenuWrite, permisosRolesWrite, permisosRolesRead));

        permisos.add(new Permiso("UsuarioRead", "Tiene permiso de lectura en la ventana Usuarios"));
        permisos.add(new Permiso("UsuarioEdit", "Tiene permiso de escritura en la ventana Usuarios"));
        permisos.add(new Permiso("RolesRead", "Tiene permiso de lectura en la ventana Roles"));
        permisos.add(new Permiso("RolesEdit", "Tiene permiso de escritura en la ventana Roles"));

        permisos.forEach(permiso ->
                permisoDAO.save(permiso)
        );

        Rol adminRol = rolDAO.findFirstByNombre("ADMIN");
        Rol userRol = rolDAO.findFirstByNombre("USER");

        if (Objects.nonNull(adminRol) && Objects.nonNull(userRol)) {
            adminRol.setPermisos(Arrays.asList(editarMenuWrite, permisosRolesWrite));
            userRol.setPermisos(Arrays.asList(editarMenuRead, permisosRolesRead));
        }

        rolDAO.update(adminRol);
        rolDAO.update(userRol);
    }

    private void crearPaginas() {
        logger.info("Creando páginas en la base de datos");
        Pagina primero = new Pagina(1L, "Panel de Administracion", "http://item1.com", false, "user-edit", 0L, LocalDateTime.now(), "mfigueroa", true);
        paginaDAO.save(primero);
        Pagina segundo = new Pagina(2L, "Item 2", "http://item2.com", false, "user-edit", primero.getId(), LocalDateTime.now(), "mfigueroa", true);
        paginaDAO.save(segundo);
        Pagina tercero = new Pagina(3L, "Item 3", "http://item3.com", false, "user-edit", segundo.getId(), LocalDateTime.now(), "mfigueroa", true);
        paginaDAO.save(tercero);
        Pagina cuarto = new Pagina(4L, "Editar Menu", "/pages/menuEdit.xhtml", true, "user-edit", tercero.getId(), LocalDateTime.now(), "mfigueroa", true);
        paginaDAO.save(cuarto);
        Pagina quinto = new Pagina(5L, "Pagina de Inicio", "/pages/home.xhtml", true, "user-edit", tercero.getId(), LocalDateTime.now(), "mfigueroa", true);
        paginaDAO.save(quinto);

        Pagina sexto = new Pagina(6L, "Item 6", "http://item6.com", false, "user-edit", segundo.getId(), LocalDateTime.now(), "mfigueroa", true);
        paginaDAO.save(sexto);

        paginaDAO.save(new Pagina(7L, "Permisos Roles", "/pages/permisosRoles.xhtml", true, "user-edit", sexto.getId(), LocalDateTime.now(), "mfigueroa", true));
        Pagina octavo = new Pagina(8L, "Reportes Financieros", "http://item8.com", false, "user-edit", 0L, LocalDateTime.now(), "mfigueroa", true);
        paginaDAO.save(octavo);

        Pagina noveno = new Pagina(9L, "Item 9", "http://item9.com", false, "user-edit", octavo.getId(), LocalDateTime.now(), "mfigueroa", true);
        paginaDAO.save(noveno);

        paginaDAO.save(new Pagina(10L, "Item 10", "http://item10.com", true, "user-edit", noveno.getId(), LocalDateTime.now(), "mfigueroa", true));
        paginaDAO.save(new Pagina(11L, "Item 11", "http://item11.com", true, "user-edit", noveno.getId(), LocalDateTime.now(), "mfigueroa", true));

        Pagina doce = new Pagina(12L, "Registros Generales", "http://item12.com", false, "user-edit", 0L, LocalDateTime.now(), "mfigueroa", true);
        paginaDAO.save(doce);

        Pagina trece = new Pagina(13L, "Item 13", "http://item13.com", false, "user-edit", doce.getId(), LocalDateTime.now(), "mfigueroa", true);
        paginaDAO.save(trece);

        paginaDAO.save(new Pagina(14L, "Item 14", "http://item14.com", true, "user-edit", trece.getId(), LocalDateTime.now(), "mfigueroa", true));
        paginaDAO.save(new Pagina(15L, "Item 15", "http://item15.com", true, "user-edit", trece.getId(), LocalDateTime.now(), "mfigueroa", true));

        Pagina dieciseis = new Pagina(16L, "Item 16", "http://item16.com", false, "user-edit", doce.getId(), LocalDateTime.now(), "mfigueroa", true);
        paginaDAO.save(dieciseis);

        paginaDAO.save(new Pagina(17L, "Item 17", "http://item17.com", true, "user-edit", dieciseis.getId(), LocalDateTime.now(), "mfigueroa", true));
        paginaDAO.save(new Pagina(18L, "Item 18", "http://item18.com", true, "user-edit", dieciseis.getId(), LocalDateTime.now(), "mfigueroa", true));
    }

    public void crearIconos() {
        logger.info("Creando íconos en la base de datos");
        List<String> nombresIconos = new ArrayList<>(Arrays.asList("sort-alt", "arrows-h", "arrows-v", "pound", "prime",
                "chart-pie", "reddit", "code", "sync", "shopping-bag", "server", "database", "hashtag",
                "bookmark-fill", "filter-fill", "heart-fill", "flag-fill", "circle", "circle-fill", "bolt",
                "history", "box", "at", "arrow-up", "arrow-down", "telegram", "stop-circle", "stop", "whatsapp",
                "building", "qrcode", "car", "instagram", "linkedin", "send", "slack", "moon", "sun", "youtube",
                "vimeo", "flag", "wallet", "map", "link", "credit-card", "discord", "percentage", "euro", "book",
                "shield", "paypal", "amazon", "phone", "filter-slash", "facebook", "github", "twitter", "step-backward",
                "step-forward", "forward", "backward", "fast-backward", "fast-forward", "pause", "play", "compass",
                "id-card", "ticket", "file-o", "reply", "directions-alt", "directions", "thumbs-up", "thumbs-down",
                "sort-numeric", "sort-alpha", "sort-amount", "palette", "undo", "desktop", "sliders-v", "sliders-h",
                "search-plus", "search-minus", "file-excel", "file-pdf", "check-square", "chart-line", "user-edit",
                "exclamation-circle", "android", "google", "apple", "microsoft", "heart", "mobile", "tablet", "key",
                "shopping-cart", "comments", "comment", "briefcase", "bell", "paperclip", "share-alt", "envelope",
                "volume-down", "volume-up", "volume-off", "eject", "money-bill", "images", "image", "sign-in", "sign-out",
                "wifi", "sitemap", "chart-bar", "camera", "dollar", "lock-open", "table", "map-marker", "list", "eye-slash",
                "eye", "folder-open", "folder", "video", "inbox", "lock", "unlock", "tags", "tag", "power-off", "save",
                "question-circle", "question", "copy", "file", "clone", "calendar-times", "calendar-minus", "calendar-plus",
                "ellipsis-v", "ellipsis-h", "bookmark", "globe", "replay", "filter", "print", "align-right", "align-left",
                "align-center", "align-justify", "cog", "cloud-download", "cloud-upload", "cloud", "pencil", "users", "clock",
                "user-minus", "user-plus", "trash", "window-minimize", "window-maximize", "external-link", "refresh", "user",
                "exclamation-triangle", "calendar", "chevron-circle", "angle-double", "angle-down", "angle-left", "angle-right",
                "angle-up", "upload", "download", "ban", "star-fill", "star", "chevron-left", "chevron-right", "chevron-down",
                "chevron-up", "caret-left", "caret-right", "caret-down", "caret-up", "search", "check", "check-circle", "times",
                "times-circle", "plus", "plus-circle", "minus", "minus-circle", "circle-on", "circle-off", "sort-down",
                "sort-up", "sort", "th-large", "arrow-left", "arrow-right", "bars", "arrow-circle", "info", "info-circle",
                "home", "spinner"));

        nombresIconos.forEach(nombre ->
                iconoDAO.save(new Icono(nombre))
        );
    }

}
