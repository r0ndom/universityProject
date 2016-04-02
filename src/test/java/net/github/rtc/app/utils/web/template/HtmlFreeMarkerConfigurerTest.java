package net.github.rtc.app.utils.web.template;

import freemarker.cache.FileTemplateLoader;
import freemarker.cache.TemplateLoader;
import junit.framework.Assert;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class HtmlFreeMarkerConfigurerTest {

    private static final String TEMPLATE_BASE_DIR = "templates";
    private static final String TEMPLATE_PATH_GOOD = "goodTemplate.ftl";
    ClassLoader classLoader = getClass().getClassLoader();

    @Test
    public void getAggregateTemplateLoaderTest() throws IOException {
        TemplateLoader templateLoader = new FileTemplateLoader(new File(classLoader.getResource(TEMPLATE_BASE_DIR).getFile()));
        TemplateLoader[] loaders = new TemplateLoader[]{templateLoader};
        HtmlFreeMarkerConfigurer configurer = new HtmlFreeMarkerConfigurer();
        TemplateLoader loader = configurer.getAggregateTemplateLoader(Arrays.asList(loaders));
        Assert.assertNotNull(loader.findTemplateSource(TEMPLATE_PATH_GOOD));
    }
}
