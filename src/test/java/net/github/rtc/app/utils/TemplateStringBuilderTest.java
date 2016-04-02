package net.github.rtc.app.utils;
import net.github.rtc.app.service.builder.TemplateStringBuilder;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.Assert.assertEquals;

public class TemplateStringBuilderTest {

    private static final String TEMPLATE_PATH = "/templates/testTemplate.ftl";
    private static final String STRING_TEMPLATE = "This is test string with params: %s, %s";

    @Test
    public void testStringFromTemplateBuilder() {
        final Map<String, Object> templateParams = new HashMap<>();
        templateParams.put("param1", "Some String");
        templateParams.put("param2", 5);
        TemplateStringBuilder builder = new TemplateStringBuilder();
        assertEquals(String.format(STRING_TEMPLATE, "Some String", 5), builder.build(TEMPLATE_PATH, templateParams));
    }

    @Test
    public void testWrongArgumentMap(){
        final Map<String, Object> templateParams = new HashMap<>();
        templateParams.put("paramNotExists", "Some String");
        assertEquals(TemplateStringBuilder.UNKNOWN_VALUE,
                new TemplateStringBuilder().build(TEMPLATE_PATH, templateParams));
        }

}
