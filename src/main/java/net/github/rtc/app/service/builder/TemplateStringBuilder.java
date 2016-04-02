package net.github.rtc.app.service.builder;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Object that helps to get string from freemarker template
 */
@Component
public class TemplateStringBuilder {

    public static final String UNKNOWN_VALUE = "UNKNOWN VALUE";

    /**
     * Get string from freemarker template that contains no params.
     * @param templatePath the path to template file
     * @return resulting string, cannot be null
     */
    @Nonnull
    public String build(String templatePath) {
        return build(templatePath, new HashMap<String, Object>());
    }

    /**
     * Get string from freemarker template that contains some params.
     * @param templatePath the template path
     * @param templateParams Map that contains params in for freemarker template
     * @return resulting string, cannot be null
     */
    @Nonnull
    public String build(String templatePath, @Nullable Map<String, Object> templateParams) {
        try {
            final Configuration config = new Configuration();
            config.setClassForTemplateLoading(this.getClass(), "/");
            final Template template = config.getTemplate(templatePath);

            final StringWriter writer = new StringWriter();
            if (templateParams != null) {
                template.process(templateParams, writer);
            }

            return writer.toString();
        } catch (IOException | TemplateException e) {
            return UNKNOWN_VALUE;
        }
    }


}
