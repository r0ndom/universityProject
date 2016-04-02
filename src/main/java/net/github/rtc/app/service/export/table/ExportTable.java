package net.github.rtc.app.service.export.table;

import java.io.IOException;

/**
 *
 * Interface that provide basic operations with table for export
 */
public interface ExportTable {
    /**
     * Add row to table
     * @param rowIndex index of created row
     */
    void createRow(int rowIndex);
    /**
     * Add cell to table
     * @param rowIndex index of a row
     * @param cellIndex index of a created cell
     * @param value stored value
     */
    void createCell(int rowIndex, int cellIndex, Object value);
    /**
     * Write table to file
     * @param fileName name of file for writing
     */
    void writeToFile(String fileName) throws IOException;
}
