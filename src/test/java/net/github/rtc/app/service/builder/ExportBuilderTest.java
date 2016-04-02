package net.github.rtc.app.service.builder;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;

import org.junit.runners.BlockJUnit4ClassRunner;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


import net.github.rtc.app.model.entity.export.ExportFormat;
import net.github.rtc.app.model.entity.user.User;
import net.github.rtc.app.utils.ExportFieldExtractor;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.util.*;


@RunWith(BlockJUnit4ClassRunner.class)
public class ExportBuilderTest {


    @InjectMocks
    private ExportBuilder exportBuilder;

    private static final String FILE_PATH = "src/test/resources/files/XLSXTest1";


    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testBuild() {
        final List<String> fieldName = Arrays.asList("Surname", "Phone");
        final List<Field> exportField = ExportFieldExtractor.getFieldsFromClass(User.class, fieldName);

        final List<User> objectsList = createUserList();

        final String sheetName = "sheetName";

        ExportFormat exportFormat = ExportFormat.XLSX;
        exportBuilder.build(exportField, objectsList, sheetName, FILE_PATH, exportFormat);


        Vector headerRow = new Vector();
        for (String name: fieldName) {
            headerRow.add(name);
        }
        Vector expectedVector = createVectorFields(headerRow, objectsList);

        assertXLSXFile(FILE_PATH, sheetName, expectedVector);

        File file = new File(FILE_PATH);
        assertTrue(file.delete());
    }

    private void assertXLSXFile(String fileName, String sheetName, Vector expectedVector) {
        Vector cellVectorHolder = new Vector();
        try {
            Workbook workBook = WorkbookFactory.create(new FileInputStream(fileName));
            Sheet sheet = workBook.getSheetAt(0);

            assertEquals(sheetName, sheet.getSheetName());

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

            assertEquals(expectedVector.toString(), cellVectorHolder.toString());
        }
        catch (Exception e) {
            assertTrue("File does not open.", false);
        }
    }


    private List<User> createUserList() {
        final List<User> objectsList = new ArrayList();

        final User user1 = new User();
        user1.setSurname("name1");
        user1.setPhone("1111");

        final User user2 = new User();
        user2.setSurname("name2");
        user2.setPhone("2222");

        final User user3 = new User();
        user3.setSurname("name3");
        user3.setPhone("2222");


        objectsList.add(user1);
        objectsList.add(user2);
        objectsList.add(user3);
        return objectsList;
    }


    private Vector createVectorFields(Vector headerRow, List<User> objectsList) {
        Vector expectedVector = new Vector();
        expectedVector.add(headerRow);
        for (User user: objectsList) {
            final Vector row = new Vector();
            row.add(user.getSurname());
            row.add(user.getPhone());

            expectedVector.add(row);
        }
        return expectedVector;
    }
}
