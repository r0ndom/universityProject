package net.github.rtc.app.utils.propertyeditors;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import java.util.Arrays;
import java.util.Set;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

@RunWith(BlockJUnit4ClassRunner.class)
public class CustomTypeEditorTest {
    private static final String TEST_STRING = "X,Y,Z,";
    private CustomTypeEditor customTypeEditor;

    @Before
    public void setUp() {
        customTypeEditor = new CustomTypeEditor();
    }

    @Test
    public void getAsTextTest() {
        customTypeEditor.setValue(Arrays.asList("X", "Y", "Z"));
        assertEquals(TEST_STRING, customTypeEditor.getAsText());
    }

    @Test
    public void setAsTextTest() {
        customTypeEditor.setAsText(TEST_STRING);
        assertTrue(((Set<String>) customTypeEditor.getValue()).containsAll(Arrays.asList("X", "Y", "Z")));
    }

    @Test
    public void getAsTextNullObj(){
        customTypeEditor.setValue(null);
        assertEquals("", customTypeEditor.getAsText());
    }
}
