package com.tcm.tcmapp.view;

import com.tcm.tcmapp.business.PaginasService;
import com.tcm.tcmapp.entity.Pagina;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.primefaces.model.menu.MenuModel;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class MenuViewTest {

    @Mock
    private PaginasService paginasService;

    private List<Pagina> paginas;

    @InjectMocks
    private final MenuView menuView = new MenuView();

    @Before
    public void setUp() throws Exception {
        paginas = cargarPaginas();
    }


    @Test
    public void givenPaginasData_whenGetMenuModel_thenReturnMenuContent() {
        given(paginasService.getPaginasParaMenu()).willReturn(paginas);

        menuView.init();
        MenuModel menuModel = menuView.getMenuModel();

        verify(paginasService, times(1)).getPaginasParaMenu();

        assertEquals(3, menuModel.getElements().size());
        assertEquals("12", menuModel.getElements().get(2).getId());

    }
    /** con este método se crea la siguiente estructura de menú para el test
     0--1--2
     |      --3
     |          --4
     |          --5
     |       --6
     |          --7
     0--8--9
     |       --10
     |       --11
     0--12
     |    --13
     |        --14
     |        --15
     |    --16
     |        --17
     |        --18
     */
    private List<Pagina> cargarPaginas() {
        ArrayList<Pagina> paginasList = new ArrayList<>();
        paginasList.add(new Pagina(1L, "Item 1", "http://item1.com", false, "pi pi-save", 0L, LocalDateTime.now(), "mfigueroa", true, 0));
        paginasList.add(new Pagina(2L, "Item 2", "http://item2.com", false, "pi pi-save", 1L, LocalDateTime.now(), "mfigueroa", true, 0));
        paginasList.add(new Pagina(3L, "Item 3", "http://item3.com", false, "pi pi-save", 2L, LocalDateTime.now(), "mfigueroa", true, 0));
        paginasList.add(new Pagina(4L, "Item 4", "http://item4.com", true, "pi pi-save", 3L, LocalDateTime.now(), "mfigueroa", true, 0));
        paginasList.add(new Pagina(5L, "Item 5", "http://item5.com", true, "pi pi-save", 3L, LocalDateTime.now(), "mfigueroa", true, 0));
        paginasList.add(new Pagina(6L, "Item 6", "http://item6.com", false, "pi pi-save", 2L, LocalDateTime.now(), "mfigueroa", true, 0));
        paginasList.add(new Pagina(7L, "Item 7", "http://item7.com", true, "pi pi-save", 6L, LocalDateTime.now(), "mfigueroa", true, 0));

        paginasList.add(new Pagina(8L, "Item 8", "http://item8.com", false, "pi pi-save", 0L, LocalDateTime.now(), "mfigueroa", true, 0));
        paginasList.add(new Pagina(9L, "Item 9", "http://item9.com", false, "pi pi-save", 8L, LocalDateTime.now(), "mfigueroa", true, 0));
        paginasList.add(new Pagina(10L, "Item 10", "http://item10.com", true, "pi pi-save", 9L, LocalDateTime.now(), "mfigueroa", true, 0));
        paginasList.add(new Pagina(11L, "Item 11", "http://item11.com", true, "pi pi-save", 9L, LocalDateTime.now(), "mfigueroa", true, 0));

        paginasList.add(new Pagina(12L, "Item 12", "http://item12.com", false, "pi pi-save", 0L, LocalDateTime.now(), "mfigueroa", true, 0));
        paginasList.add(new Pagina(13L, "Item 13", "http://item13.com", false, "pi pi-save", 12L, LocalDateTime.now(), "mfigueroa", true, 0));
        paginasList.add(new Pagina(14L, "Item 14", "http://item14.com", true, "pi pi-save", 13L, LocalDateTime.now(), "mfigueroa", true, 0));
        paginasList.add(new Pagina(15L, "Item 15", "http://item15.com", true, "pi pi-save", 13L, LocalDateTime.now(), "mfigueroa", true, 0));
        paginasList.add(new Pagina(16L, "Item 16", "http://item16.com", false, "pi pi-save", 12L, LocalDateTime.now(), "mfigueroa", true, 0));
        paginasList.add(new Pagina(17L, "Item 17", "http://item17.com", true, "pi pi-save", 16L, LocalDateTime.now(), "mfigueroa", true, 0));
        paginasList.add(new Pagina(18L, "Item 18", "http://item18.com", true, "pi pi-save", 16L, LocalDateTime.now(), "mfigueroa", true, 0));
        return paginasList;
    }

}