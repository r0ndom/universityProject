package net.github.rtc.app.utils.propertyeditors;

import net.github.rtc.app.model.entity.user.Role;
import net.github.rtc.app.model.entity.user.RoleType;
import net.github.rtc.app.service.user.UserService;
import net.github.rtc.app.utils.enums.EnumHelper;

import java.beans.PropertyEditorSupport;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * Custom tags editor for tag collection to render it as string
 * and set it by passing as string
 */
public class CustomRoleEditor extends PropertyEditorSupport {

    private static final String COMMA = ",";

    private UserService userService;

    public CustomRoleEditor(UserService userService) {
       this.userService = userService;
    }

    @Override
    public void setAsText(final String text) {
        final List<String> rolesSplit = Arrays.asList(text.split(COMMA));
        final Collection<Role> roles = new ArrayList<>();
        if (!rolesSplit.get(0).isEmpty()) {
            for (final String roleName : rolesSplit) {
               roles.add(userService.findRoleByType(EnumHelper.getTypeByString(RoleType.class, roleName)));
            }
        }
        this.setValue(roles);
    }

}
