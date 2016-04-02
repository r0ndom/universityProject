package net.github.rtc.app.utils.web.template;

import freemarker.cache.TemplateLoader;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

/**
 * Custom template loader to prevent XSS attacks
 */
public class HtmlTemplateLoader implements TemplateLoader {

    public static final String ESCAPE_PREFIX = "<#ftl strip_whitespace=true><#escape x as x?html>";
    public static final String ESCAPE_SUFFIX = "</#escape>";

    private final TemplateLoader delegate;

    public HtmlTemplateLoader(final TemplateLoader delegate) {
        this.delegate = delegate;
    }

    @Override
    public Object findTemplateSource(final String name) throws IOException {
        return delegate.findTemplateSource(name);
    }

    @Override
    public long getLastModified(final Object templateSource) {
        return delegate.getLastModified(templateSource);
    }

    /**
     * Wrap freemarker template content in ESCAPE_PREFIX and ESCAPE_SUFFIX  to prevent XSS attack
     * @param templateSource template that needs to be wrapped
     * @param encoding what encoding is used in source file
     * @return character stream which contains template data that is not sensitive to XSS
     * @throws IOException if templateSource reading error
     */
    @Override
    public Reader getReader(final Object templateSource, final String encoding) throws IOException {
        try (final Reader reader = delegate.getReader(templateSource, encoding)) {
            final String templateText = IOUtils.toString(reader);
            if (!templateText.contains("<#ftl")) {
                return new StringReader(ESCAPE_PREFIX + templateText + ESCAPE_SUFFIX);
            } else {
                return new StringReader(templateText);
            }
        }
    }

    @Override
    public void closeTemplateSource(final Object templateSource) throws IOException {
        delegate.closeTemplateSource(templateSource);
    }
}
