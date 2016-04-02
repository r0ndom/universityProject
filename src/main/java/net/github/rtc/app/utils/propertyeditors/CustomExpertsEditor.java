package net.github.rtc.app.utils.propertyeditors;

import net.github.rtc.app.model.entity.user.User;
import net.github.rtc.app.service.user.UserService;

import java.beans.PropertyEditorSupport;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Custom expert editor to set for example course.expert field by passing value as string
 * @see net.github.rtc.app.model.entity.course.Course
 * @see net.github.rtc.app.model.entity.user.User
 */
public class CustomExpertsEditor extends PropertyEditorSupport {

    private static final String COMMA = ",";

    private UserService userService;

    public CustomExpertsEditor(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void setAsText(final String text) {
        final List<String> expertsSplit = Arrays.asList(text.split(COMMA));
        final Set<User> experts = new HashSet<>();
        for (String s : expertsSplit) {
            experts.add(userService.findByCode(s));
        }
        this.setValue(experts);
    }
}
