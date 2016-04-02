package net.github.rtc.app.utils.propertyeditors;

import net.github.rtc.app.model.entity.course.Tag;

import java.beans.PropertyEditorSupport;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Custom tags editor for tag collection to render it as string
 * or set it by passing as string
 */
public class CustomTagsEditor extends PropertyEditorSupport {

    private static final String COMMA = ",";

    @Override
    public String getAsText() {
        final Object obj = this.getValue();
        if (obj == null) {
            return "";
        }
        final Collection<Tag> tags = ((Collection<Tag>) obj);
        final StringBuffer sb = new StringBuffer();
        for (final Tag tag : tags) {
            sb.append(tag.getValue()).append(COMMA);
        }
        return sb.toString();
    }

    @Override
    public void setAsText(final String text) {
        final List<String> tagsSplit = Arrays.asList(text.split(COMMA));
        Collection<Tag> tags = null;
        if (!tagsSplit.get(0).isEmpty()) {
            tags = new ArrayList<>();
            for (final String tagName : tagsSplit) {
                tags.add(new Tag(tagName));
            }
        }
        this.setValue(tags);
    }
}
