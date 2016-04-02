package net.github.rtc.app.service.export.table;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ivan Yatcuba on 8/22/14.
 */
public class CSVTable implements ExportTable {

    private List<List<String>> table;

    public CSVTable() {
        table = new ArrayList<>();
    }

    @Override
    public void createRow(final int rowIndex) {
        table.add(rowIndex, new ArrayList<String>());
    }

    @Override
    public void createCell(
      final int rowIndex, final int cellIndex, final Object value) {
        table.get(rowIndex).add(cellIndex, value.toString());
    }

    @Override
    public void writeToFile(final String fileName) throws IOException {
        final File file = new File(fileName);
        final Writer writer = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
        for (final List<String> row : table) {
            for (final String cell : row) {
                writer.append(cell).append(',');
            }
            writer.append('\n');
        }
        writer.flush();
        writer.close();
    }

    public List<List<String>> getTable() {
        return table;
    }

    public void setTable(final List<List<String>> table) {
        this.table = table;
    }
}
