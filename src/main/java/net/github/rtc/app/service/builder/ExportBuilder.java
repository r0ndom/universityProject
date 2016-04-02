package net.github.rtc.app.service.builder;

import net.github.rtc.app.model.entity.export.ExportDetails;
import net.github.rtc.app.model.entity.export.ExportFormat;
import net.github.rtc.app.service.export.table.CSVTable;
import net.github.rtc.app.service.export.table.ExportTable;
import net.github.rtc.app.service.export.table.XLSNXTable;
import net.github.rtc.util.annotation.ForExport;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by Ivan Yatcuba on 7/29/14.
 * <p/>
 * This class helps to create xlsx report file of some model class
 * collection on disk
 */
@Component
public class ExportBuilder {
    public ExportBuilder() {
    }

    public <T> void build(ExportDetails export, List<T> objectsList, String exportPath) throws NoSuchFieldException {
        build(export.getFieldsFromClass(), objectsList, export.getName(), exportPath, export.getExportFormat());
    }

    /**
     * Create xlsx export file
     *
     * @param exportFields export about this model class,
     *                     which fields that needs to be in export are
     *                     annotated by @ForExport.
     * @param objectsList  list of object that will be stored in export table.
     * @param sheetName    name of sheet in xlsx file.
     * @param filePath     path where export file will be stored
     */
    public <T> void build(
            final List<Field> exportFields,
            final List<T> objectsList,
            final String sheetName,
            final String filePath,
            final ExportFormat exportFormat) {

        ExportTable exportTable = null;
        try {
            if (exportFormat.equals(ExportFormat.XLSX)) {
                exportTable = new XLSNXTable(new XSSFWorkbook(), sheetName);
            } else if (exportFormat.equals(ExportFormat.XLS)) {
                exportTable = new XLSNXTable(new HSSFWorkbook(), sheetName);
            } else if (exportFormat.equals(ExportFormat.CSV)) {
                exportTable = new CSVTable();
            }

            //Table header creation
            int currentCol = 0;
            int currentRow = 0;
            exportTable.createRow(currentRow);
            for (final Field field : exportFields) {
                exportTable.createCell(currentRow, currentCol,
                        field.getAnnotation(ForExport.class).value());
                currentCol++;
            }
            //Getting info from list
            currentRow++;
            for (final T object : objectsList) {
                exportTable.createRow(currentRow);
                for (int j = 0; j < exportFields.size(); j++) {
                    exportFields.get(j).setAccessible(true);
                    try {
                        if (exportFields.get(
                                j).getDeclaringClass() != object.getClass()) {
                            for (final Field f : object.getClass()
                                    .getDeclaredFields()) {
                                if (f.getType() == exportFields.get(
                                        j).getDeclaringClass()) {
                                    f.setAccessible(true);
                                    exportTable.createCell(currentRow, j,
                                            exportFields.get(j).get(f.get(object)));
                                    f.setAccessible(false);
                                    break;
                                }
                            }
                        } else {
                            exportTable.createCell(currentRow, j,
                                    exportFields.get(j).get(object));
                        }
                    } catch (IllegalAccessException | NullPointerException e) {
                        exportTable.createCell(currentRow, j, "");
                    }
                    exportFields.get(j).setAccessible(false);
                }
                currentRow++;
            }
            try {
                exportTable.writeToFile(filePath);
            } catch (final IOException e) {
                e.printStackTrace();
            }
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
    }
}
