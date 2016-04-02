package net.github.rtc.app.utils.web.template;


import freemarker.cache.FileTemplateLoader;
import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.TemplateLoader;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

import java.io.*;

import static org.junit.Assert.assertEquals;

public class HtmlTemplateLoaderTest {

    private static final String TEMPLATE_BASE_DIR = "templates";
    private static final String TEMPLATE_PATH_GOOD = "goodTemplate.ftl";
    private static final String TEMPLATE_PATH_BAD = "badTemplate.ftl";
    private static final String TEMPLATE_TEST_RESULT = "<#ftl strip_whitespace=true><#escape x as x?html>test</#escape>";
    private static final String TEMPLATE_TEST_RESULT_FTL = "<#ftl>test";
    ClassLoader classLoader = getClass().getClassLoader();

    @Test
    public void getReaderFtlPresent() throws IOException {
        getReader(TEMPLATE_PATH_GOOD, TEMPLATE_TEST_RESULT);
    }

    @Test
    public void getReaderFtlAbsent() throws IOException {
        getReader(TEMPLATE_PATH_BAD, TEMPLATE_TEST_RESULT_FTL);
    }

    public void getReader(String templatePath, String testResult) throws IOException {
        TemplateLoader templateLoader = new FileTemplateLoader(new File(classLoader.getResource(TEMPLATE_BASE_DIR).getFile()));
        MultiTemplateLoader multiTemplateLoader = new MultiTemplateLoader(new TemplateLoader[]{templateLoader});
        HtmlTemplateLoader htmlTemplateLoader = new HtmlTemplateLoader(multiTemplateLoader);
        Reader reader = htmlTemplateLoader.getReader(multiTemplateLoader.findTemplateSource(templatePath), "UTF-8");
        StringWriter writer = new StringWriter();
        IOUtils.copy(reader, writer);
        String result = writer.toString();
        assertEquals(testResult, result);
    }
}
