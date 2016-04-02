package net.github.rtc.app.model.entity.user;

import net.github.rtc.app.model.entity.AbstractPersistenceObject;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * @author Vladislav Pikus
 */
@Entity
public class Role extends AbstractPersistenceObject implements GrantedAuthority  {

    @Enumerated(EnumType.STRING)
    @Column
    private RoleType name;

    public Role() {
    }

    public Role(final String strName) {
       for (RoleType role : RoleType.values()) {
           if (role.getRoleViewName().equals(strName) || role.name().equals(strName)) {
               this.name = role;
               break;
           }
       }

    }

    public Role(final RoleType name) {
        this.name = name;
    }

    public RoleType getName() {
        return name;
    }

    public void setName(final RoleType name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return name.name();
    }

    @Override
    public String toString() {
        return name.name();
    }
}
