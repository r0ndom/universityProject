package net.github.rtc.app.service.export.table;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.mockito.InjectMocks;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

import static org.junit.Assert.*;

@RunWith(BlockJUnit4ClassRunner.class)
public class XLSNXTableTest {

    @InjectMocks
    private XLSNXTable xlsxTable = new XLSNXTable(new XSSFWorkbook(), "XLSXTest");
    @InjectMocks
    private XLSNXTable xlsTable = new XLSNXTable(new HSSFWorkbook(), "XLSTest");

    private static final String TEST_FOLDER_PATH = "src/test/resources/files/";

    private static final String XLSX_TEST_FILE_PATH = TEST_FOLDER_PATH + "XLSXTest";
    private static final String XLS_TEST_FILE_PATH =  TEST_FOLDER_PATH + "XLSTest";

    @BeforeClass
    public static void setUpClass() throws Exception {
        File theDir = new File(TEST_FOLDER_PATH);
        if (!theDir.exists()) {
            theDir.mkdir();
        }
    }

    @Test
    public void testCreateAndWriteXLSXTableToFile() throws IOException {
        for (int i = 0; i < 3; i++) {
            xlsxTable.createRow(i);
            for (int j = 0; j < 3; j++) {
                xlsxTable.createCell(i, j, i + "" + j);
            }
        }
        xlsxTable.writeToFile(XLSX_TEST_FILE_PATH);
        String expectedValueOfCreatedFile = "[[00, 01, 02], [10, 11, 12], [20, 21, 22]]";
        assertEquals(expectedValueOfCreatedFile, readCreatedXLSXFile(XLSX_TEST_FILE_PATH));

        File file = new File(XLSX_TEST_FILE_PATH);
        assertTrue(file.delete());
    }

    @Test
    public void testCreateAndWriteXLSTableToFile() throws IOException {
        for (int i = 0; i < 3; i++) {
            xlsTable.createRow(i);
            for (int j = 0; j < 3; j++) {
                xlsTable.createCell(i, j, i + "" + j);
            }
        }
        xlsTable.writeToFile(XLS_TEST_FILE_PATH);
        String expectedValueOfCreatedFile = "[[00, 01, 02], [10, 11, 12], [20, 21, 22]]";
        assertEquals(expectedValueOfCreatedFile, readCreatedXLSFile(XLS_TEST_FILE_PATH));

        File file = new File(XLS_TEST_FILE_PATH);
        assertTrue(file.delete());
    }

    private String readCreatedXLSFile(String fileName) {
        Vector cellVectorHolder = new Vector();
        try {
            Workbook workBook = WorkbookFactory.create(new FileInputStream(fileName));
            Sheet sheet = workBook.getSheetAt(0);
            Iterator rowIter = sheet.rowIterator();

            while(rowIter.hasNext()) {
                HSSFRow row = (HSSFRow) rowIter.next();
                Iterator cellIter = row.cellIterator();
                Vector cellStoreVector = new Vector();

                while(cellIter.hasNext()) {
                    HSSFCell cell = (HSSFCell) cellIter.next();
                    cellStoreVector.addElement(cell);
                }
                cellVectorHolder.addElement(cellStoreVector);
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return cellVectorHolder.toString();
    }

    private String readCreatedXLSXFile(String fileName) {
        Vector cellVectorHolder = new Vector();
        try {
            Workbook workBook = WorkbookFactory.create(new FileInputStream(fileName));
            Sheet sheet = workBook.getSheetAt(0);
            Iterator rowIter = sheet.rowIterator();

            while(rowIter.hasNext()) {
                XSSFRow row = (XSSFRow) rowIter.next();
                Iterator cellIter = row.cellIterator();
                Vector cellStoreVector = new Vector();

                while(cellIter.hasNext()) {
                    XSSFCell cell = (XSSFCell) cellIter.next();
                    cellStoreVector.addElement(cell);
                }
                cellVectorHolder.addElement(cellStoreVector);
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return cellVectorHolder.toString();
    }
}
