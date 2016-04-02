package net.github.rtc.app.utils;


import net.github.rtc.util.annotation.ForExport;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(BlockJUnit4ClassRunner.class)
public class ExportFieldExtractorTest {

    @Test
    public void getFieldsFromClassTest() throws NoSuchFieldException {
        List<Field> fields = ExportFieldExtractor.getFieldsFromClass(TestClass.class, Arrays.asList("TestString","TestInt","TestDate","TestDependency"));
        List<Field> neededFields = new ArrayList<>();
        neededFields.add(TestClass.class.getDeclaredField("testString"));
        neededFields.add(TestClass.class.getDeclaredField("testInt"));
        neededFields.add(TestClass.class.getDeclaredField("testDate"));
        neededFields.add(TestClass.class.getDeclaredField("testClassDependency"));
        neededFields.add(TestClassDependency.class.getDeclaredField("innerField"));
        assertEquals(5, fields.size());
        assertTrue(neededFields.containsAll(fields));
    }

    @Test
    public void getAvailableFieldListTest() {
        List<String> availableFields = Arrays.asList("TestString","TestInt","TestDate","TestDependency", "InnerField");
        List<String> results = ExportFieldExtractor.getAvailableFieldList(TestClass.class);
        assertEquals(5, results.size());
        assertTrue(availableFields.containsAll(results));
    }

    public class TestClass{
        @ForExport("TestString")
        private String testString;
        @ForExport("TestInt")
        private int testInt;
        @ForExport("TestDate")
        private Date testDate;
        @ForExport(value = "TestDependency", inculdeField = {"InnerField"})
        private TestClassDependency testClassDependency;
    }

    private class TestClassDependency{
        @ForExport("InnerField")
        private String innerField;
    }
}
