package net.github.rtc.app.utils.propertyeditors;

import java.beans.PropertyEditorSupport;
import java.util.*;

/**
 * Custom editor for collection to render it as string
 * or set it by passing as string
 */
public class CustomTypeEditor extends PropertyEditorSupport {

    private static final String COMMA = ",";

    @Override
    public String getAsText() {
        final Object obj = this.getValue();
        if (obj == null) {
            return "";
        }
        final Collection<String> strs = ((Collection<String>) obj);
        final StringBuffer sb = new StringBuffer();
        for (final String str : strs) {
            sb.append(str).append(COMMA);
        }
        return sb.toString();
    }

    @Override
    public void setAsText(final String text) {
        final List<String> strsSplit = Arrays.asList(text.split(COMMA));
        final Set<String> strs = new HashSet<>();
        for (final String str : strsSplit) {
            strs.add(str);
        }
        this.setValue(strs);
    }
}
