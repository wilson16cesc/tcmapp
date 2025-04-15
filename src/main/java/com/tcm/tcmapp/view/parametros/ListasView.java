package com.tcm.tcmapp.view.parametros;

import com.tcm.tcmapp.entity.parametros.Listas;
import com.tcm.tcmapp.service.parametros.ListasService;
import org.apache.poi.ss.usermodel.*;
import org.omnifaces.util.Faces;
import org.omnifaces.util.Messages;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;
import org.slf4j.Logger;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;


@Named
@ViewScoped
public class ListasView implements Serializable {

    @Inject
    ListasService listasService;
    @Inject
    Logger logger;

    private List<Listas> listas;

    private Listas selectedLista;

    private UploadedFile file;

    private UploadedFile imagenLista;

    private String rutaRelativa;

    public ListasView(){
        listas = new ArrayList<>();
        selectedLista = new Listas();
        rutaRelativa = "";
    }

    @PostConstruct
    protected void init(){
        String accion = String.valueOf(Faces.getRequestParameter("accion"));
        logger.info("accion:{}", accion);

        switch (accion) {
            case "crear":
                nuevoLista();
                break;
            case "editar":
                String userId = Faces.getRequestParameter("userId");
                selectedLista = listasService.findById(Long.valueOf(userId));
                break;
            default:
                listas = listasService.findAll();
        }
    }

    public void guardarLista() {

        rutaRelativa = " ";

        if (imagenLista != null && imagenLista.getSize() > 0) {

            try {
                String directorioDestino = "/opt/tcmapp/imagenes/";
                String nombreUnico = UUID.randomUUID() + "_" + imagenLista.getFileName();
                Path rutaFinal = Paths.get(directorioDestino, nombreUnico);

                // Guardar físicamente la imagen
                Files.copy(imagenLista.getInputStream(), rutaFinal);

                // Guardar la ruta relativa (ej: /imagenes/archivo.png)
                rutaRelativa = "/imagenes/" + nombreUnico;

                // Aquí llamarías a tu servicio o DAO para guardar en la BD
                // datoDAO.guardarRutaImagen(rutaRelativa);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        selectedLista.setIcono(rutaRelativa);
        listasService.update(selectedLista);
        Messages.addInfo(null, "Datos guardados correctamente");
    }
    public void nuevoLista() {
        logger.info("ejecutando metodo: nuevoParametro");
        selectedLista = new Listas();
    }
    public void upload() {

        if (file != null) {
            try (InputStream input = file.getInputStream()) {
                Workbook workbook = WorkbookFactory.create(input);
                Sheet sheet = workbook.getSheetAt(0); // Primera hoja del Excel
                Iterator<Row> rowIterator = sheet.iterator();

                // Saltar la primera fila si tiene encabezados
                 if (rowIterator.hasNext()) rowIterator.next();

                while (rowIterator.hasNext()) {
                    Row row = rowIterator.next();
                    Listas lista = new Listas();

                    // Suponiendo que la columna 0 es "nombre" y la 1 es "valor"
                    lista.setTipo_lista(row.getCell(0).getStringCellValue());
                    lista.setCod_lista(row.getCell(1).getStringCellValue());
                    lista.setDescripcion(row.getCell(2).getStringCellValue());
                    lista.setIcono(row.getCell(3).getStringCellValue());
                    // Cell Caracteristica
                    Cell cellCaracteristica = row.getCell(4);
                    if (cellCaracteristica != null && cellCaracteristica.getCellType() == CellType.STRING) {
                        lista.setCaracteristica(cellCaracteristica.getStringCellValue());
                    } else {
                        lista.setCaracteristica("");
                    }
                    Cell cellDescCaracteristica = row.getCell(5);
                    if (cellDescCaracteristica != null && cellDescCaracteristica.getCellType() == CellType.STRING) {
                        lista.setDesc_caracteristica(cellDescCaracteristica.getStringCellValue());
                    } else {
                        lista.setDesc_caracteristica("");
                    }
                    listasService.save(lista);
                }

                workbook.close();
                Messages.addInfo(null, "Datos guardados correctamente");
                System.out.println("Archivo procesado correctamente.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public List<Listas> getListas() {
        return listas;
    }

    public void setListas(List<Listas> listas) {
        this.listas = listas;
    }

    public Listas getSelectedLista() {
        return selectedLista;
    }

    public void setSelectedLista(Listas selectedLista) {
        this.selectedLista = selectedLista;
    }

    public UploadedFile getFile() {
        return file;
    }

    public void setFile(UploadedFile file) {
        this.file = file;
    }

    public UploadedFile getImagenLista() {
        return imagenLista;
    }

    public void setImagenLista(UploadedFile imagenLista) {
        this.imagenLista = imagenLista;
    }

    public String getRutaRelativa() {
        return rutaRelativa;
    }

    public void setRutaRelativa(String rutaRelativa) {
        this.rutaRelativa = rutaRelativa;
    }
}
