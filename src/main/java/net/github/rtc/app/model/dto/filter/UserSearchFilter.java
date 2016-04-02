package net.github.rtc.app.model.dto.filter;

import net.github.rtc.app.model.entity.user.Role;
import net.github.rtc.app.model.entity.user.User;
import net.github.rtc.app.model.entity.user.UserStatus;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import java.util.Date;
import java.util.List;


public class UserSearchFilter extends AbstractSearchFilter {
    private static final String PERCENT = "%";
    private static final String AUTHORITIES = "authorities";
    private static final String REGISTER_DATE = "registerDate";

    private String surname;

    private Date registerDate;

    private List<Role> authorities;

    private String status;

    private char dateMoreLessEq;

    public char getDateMoreLessEq() {

        return dateMoreLessEq;
    }

    public void setDateMoreLessEq(char dateMoreLessEq) {
        this.dateMoreLessEq = dateMoreLessEq;
    }

    public String getStatus() {

        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Role> getAuthorities() {

        return authorities;
    }

    public void setAuthorities(List<Role> authorities) {
        this.authorities = authorities;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(final String surname) {
        this.surname = surname;
    }

    public Date getRegisterDate() {

        return registerDate == null ? null : new Date(registerDate.getTime());
    }

    public void setRegisterDate(Date registerDate) {
        if (registerDate != null) {
            this.registerDate = new Date(registerDate.getTime());
        }
    }

    @Override
    public Order order() {
        return Order.desc(REGISTER_DATE);
    }

    public DetachedCriteria getCriteria() {
        final DetachedCriteria criteria = DetachedCriteria.forClass(User.class);

        if (surname != null && !("").equals(surname)) {
            criteria.add(Restrictions.ilike("surname", PERCENT + surname + PERCENT));
        }

        if (registerDate != null) {
            final DateCriteriaCreator dateCriteriaCreator = new DateCriteriaCreator(REGISTER_DATE, registerDate);
            criteria.add(dateCriteriaCreator.getDateCriteria(dateMoreLessEq));

        }

        if (status != null && !"".equals(status) && !"All".equals(status)) {
            for (UserStatus userStatus : UserStatus.values()) {
                if (userStatus.name().equals(status)) {
                    criteria.add(Restrictions.eq("status", userStatus));
                    break;
                }
            }

        }

        if (authorities != null && authorities.size() > 0) {
                criteria.createAlias(AUTHORITIES, AUTHORITIES);
                final Disjunction authoritiesDis = Restrictions.disjunction();
                for (final Role role : authorities) {
                        authoritiesDis.add(Restrictions.eq("authorities.name", role.getName()));
                }
                criteria.add(authoritiesDis);
        }

        return criteria;
    }
}


