package net.github.rtc.app.model.entity.user;


public enum RoleType {
    ROLE_USER("User"), ROLE_ADMIN("Administrator"), ROLE_EXPERT("Expert");
    private final String roleViewName;

    RoleType(final String s) {
        roleViewName = s;
    }

    public String getRoleViewName() {
        return roleViewName;
    }
}
