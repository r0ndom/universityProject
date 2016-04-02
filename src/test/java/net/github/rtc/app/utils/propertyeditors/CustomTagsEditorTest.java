package net.github.rtc.app.utils.propertyeditors;

import net.github.rtc.app.model.entity.course.Tag;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static junit.framework.Assert.*;

@RunWith(BlockJUnit4ClassRunner.class)
public class CustomTagsEditorTest {

    private static final String TEST_STRING = "X,Y,Z,";
    private CustomTagsEditor customTagsEditor;

    @Before
    public void setUp() {
        customTagsEditor = new CustomTagsEditor();
    }

    @Test
    public void getAsTextTest() {
        customTagsEditor.setValue(Arrays.asList(new Tag("X"), new Tag("Y"), new Tag("Z")));
        assertEquals(TEST_STRING, customTagsEditor.getAsText());
    }

    @Test
    public void setAsTextTest() {
        customTagsEditor.setAsText(TEST_STRING);
        List<Tag> tags =  Arrays.asList(new Tag("X"), new Tag("Y"), new Tag("Z"));
        assertTrue(tags.containsAll((Collection) customTagsEditor.getValue()));
    }

    @Test
    public void setAsTextIfIsEmptyTest(){
        customTagsEditor.setAsText("");
        assertNull(customTagsEditor.getValue());
    }

    @Test
    public void getAsTextNullObj(){
        customTagsEditor.setValue(null);
        assertEquals("", customTagsEditor.getAsText());
    }
}
