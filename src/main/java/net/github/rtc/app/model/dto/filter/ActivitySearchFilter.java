package net.github.rtc.app.model.dto.filter;

import net.github.rtc.app.model.entity.activity.Activity;
import net.github.rtc.app.model.entity.activity.ActivityAction;
import net.github.rtc.app.model.entity.activity.ActivityEntity;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Set;

/**
 * Created by Илья on 29.12.2014.
 */

@Component
public class ActivitySearchFilter extends AbstractSearchFilter {

    private static final String PERCENT = "%";
    private static final String DATE = "actionDate";
    private String user;
    private Set<ActivityEntity> entity;
    private Set<ActivityAction> action;
    private Date date;
    private char dateMoreLessEq;

    public char getDateMoreLessEq() {
        return dateMoreLessEq;
    }

    public Date getDate() {
        return date == null ? null : new Date(date.getTime());
    }

    public String getUser() {
        return user;
    }

    public void setDateMoreLessEq(char dateMoreLessEq) {
        this.dateMoreLessEq = dateMoreLessEq;
    }

    public void setDate(final Date date) {
        if (date != null) {
            this.date = new Date(date.getTime());
        }
    }

    public void setUser(final String user) {
        this.user = user;
    }

    public Set<ActivityEntity> getEntity() {
        return entity;
    }

    public void setEntity(Set<ActivityEntity> entity) {
        this.entity = entity;
    }

    public Set<ActivityAction> getAction() {
        return action;
    }

    public void setAction(Set<ActivityAction> action) {
        this.action = action;
    }

    @Override
    public Order order() {
        return Order.desc(DATE);
    }

    @Override
    public DetachedCriteria getCriteria() {
        final DetachedCriteria criteria = DetachedCriteria.forClass(Activity.class);

        if (user != null && !("").equals(user)) {
            criteria.add(Restrictions.like("username", PERCENT + user + PERCENT));
        }
        if (date != null) {
            final DateCriteriaCreator dateCriteriaCreator = new DateCriteriaCreator(DATE, date);
            criteria.add(dateCriteriaCreator.getDateCriteria(dateMoreLessEq));
        }
        if (entity != null && entity.size() > 0) {
            final Disjunction entityDis = Restrictions.disjunction();
            for (final ActivityEntity en : entity) {
                entityDis.add(Restrictions.eq("entity", en));
            }
            criteria.add(entityDis);
        }

        if (action != null && action.size() > 0) {
            final Disjunction actionDis = Restrictions.disjunction();
            for (final ActivityAction ac : action) {
                actionDis.add(Restrictions.eq("action", ac));
            }
            criteria.add(actionDis);
        }
        return criteria;
    }
}
