package com.tcm.tcmapp.dao;

import org.jboss.arquillian.junit.Arquillian;
import org.junit.runner.RunWith;

//@RunWith(Arquillian.class)
//@Disabled ////////////////////////////////DESHABILITADO POR QUE SIEMPRE EVALUA LOS ASSERT COMO TRUE ////////////////////////////
class VUsuarioRolDAOTest {
/*
    public static final String ADMIN1 = "admin1";
    public static final String USUARIO1 = "usuario1";


    @Inject
    RolDAO rolDAO;

    @Inject
    UsuarioDAO usuarioDAO;

    @Inject
    PermisoDAO permisoDAO;

    @Inject
    VUsuarioRolDAO vUsuarioRolDAO;


    @Deployment
    public static WebArchive createDeployment() {
        PomEquippedResolveStage pomFile = Maven.resolver().loadPomFromFile("pom.xml");
        WebArchive war = ShrinkWrap.create(WebArchive.class)
                .addAsLibraries(pomFile.resolve("org.assertj:assertj-core").withTransitivity().asFile())
                .addAsLibraries(pomFile.resolve("org.slf4j:slf4j-api").withTransitivity().asFile())
                .addClass(Usuario.class)
                .addClass(Rol.class)
                .addClass(Permiso.class)
                .addClass(VUsuarioRol.class)
                .addClass(BaseEntityIdentity.class)
                .addClass(BaseEntity.class)
                .addClass(BaseDAO.class)
                .addClass(UsuarioDAO.class)
                .addClass(RolDAO.class)
                .addClass(VUsuarioRolDAO.class)
                .addClass(LoggerProducer.class)
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
        //System.out.println(war.toString(true));
        return war;
    }

    @Before
    void setUp() {
        System.out.println("Preparando test...");
        usuarioDAO.deleteAll();
        rolDAO.deleteAll();
        permisoDAO.deleteAll();

    }


    @After
    void tearDown() {
        usuarioDAO.deleteAll();
        rolDAO.deleteAll();
        permisoDAO.deleteAll();
    }

    @Test
    void findByUsername() {
        crearVistaRolesUsuarios();
        crearUsuariosRolesPermisos();
        List<VUsuarioRol> usuarioRoles = vUsuarioRolDAO.findByUsername(ADMIN1);
//        logger.info("Usuarios roles: {}", Arrays.toString(usuarioRoles.toArray()));
        assertThat(usuarioRoles).size().isEqualTo(10);
    }

    private void crearUsuariosRolesPermisos() {
//        logger.info("Creando roles usuarios y permisos en la base de datos");
        Rol rol1Admin = new Rol("ADMIN");
        Rol rolUser = new Rol("USER");
        rolDAO.save(rol1Admin);
        rolDAO.save(rolUser);
        Usuario usuario1 = new Usuario(ADMIN1, "12345",
                new HashSet<>(Arrays.asList(rol1Admin, rolUser)));
        Usuario usuario2 = new Usuario(USUARIO1, "12345",
                new HashSet<>(Collections.singletonList(rolUser)));
        usuarioDAO.save(usuario1);
        usuarioDAO.save(usuario2);

        Permiso editarMenuRead = new Permiso("EditarMenuRead", "Tiene permiso de lectura en la ventana 'Editar Menu'.");
        Permiso editarMenuWrite = new Permiso("EditarMenuWrite", "Tiene permiso de escritura en la ventana 'Editar Menu");

        permisoDAO.save(editarMenuRead);
        permisoDAO.save(editarMenuWrite);

        rol1Admin.setPermisos(Arrays.asList(editarMenuRead, editarMenuWrite));
        rolUser.setPermisos(Collections.singletonList(editarMenuRead));

        rolDAO.update(rol1Admin);
        rolDAO.update(rolUser);
    }

    private void crearVistaRolesUsuarios() {
        String sql = "CREATE VIEW IF NOT EXISTS vseg_usuarios_roles AS select random_uuid() as id, username, rolename from\n" +
                "            (select u.username, r.nombre as rolename from seg_usuarios u\n" +
                "            inner join seg_usuarios_roles ur on u.id = ur.usuario_id\n" +
                "            inner join seg_roles r on r.id = ur.rol_id\n" +
                "            inner join seg_roles_permisos rp on rp.rol_id = r.id\n" +
                "            inner join seg_permisos p on p.id = rp.permiso_id\n" +
                "            union\n" +
                "            select u.username, 'perm:'+p.nombre as rolename from seg_usuarios u\n" +
                "            inner join seg_usuarios_roles ur on u.id = ur.usuario_id\n" +
                "            inner join seg_roles r on r.id = ur.rol_id\n" +
                "            inner join seg_roles_permisos rp on rp.rol_id = r.id\n" +
                "            inner join seg_permisos p on p.id = rp.permiso_id) resultado";
        usuarioDAO.executeNativeQuery(sql);
    }

 */
}
