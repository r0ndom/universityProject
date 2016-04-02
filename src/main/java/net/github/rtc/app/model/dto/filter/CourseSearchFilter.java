package net.github.rtc.app.model.dto.filter;

import net.github.rtc.app.model.entity.course.*;
import net.github.rtc.app.service.date.DateService;
import org.hibernate.criterion.*;
import org.hibernate.type.StringType;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Component
public class CourseSearchFilter extends AbstractSearchFilter {


    private static final String PERCENT = "%";
    private static final String TAGS = "tags";
    private static final String START_DATE = "startDate";
    private static final String END_DATE = "endDate";
    private static final String EXPERTS = "experts";
    private static final String STATUS = "status";
    private static final String TAGS_VALUE = "tags.value";
    private static final String NAME = "name";
    private String name;
    private char dateMoreLessEq;
    private Set<CourseType> types;
    private Date startDate;
    private Set<CourseStatus> status;
    private List<Tag> tags;
    private String expertCode;
    private boolean withArchived = false;
    private TimePeriod timePeriod;
    private String keyword;

    @Autowired
    private DateService dateService;

    public char getDateMoreLessEq() {
        return dateMoreLessEq;
    }

    public void setDateMoreLessEq(char dateMoreLessEq) {
        this.dateMoreLessEq = dateMoreLessEq;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Set<CourseType> getTypes() {
        return types;
    }

    public void setTypes(final Set<CourseType> types) {
        this.types = types;
    }

    public Date getStartDate() {
        return startDate == null ? null : new Date(startDate.getTime());
    }

    public void setStartDate(final Date startDate) {
        if (startDate != null) {
            this.startDate = new Date(startDate.getTime());
        }
    }

    public Set<CourseStatus> getStatus() {
        return status;
    }

    public void setStatus(Set<CourseStatus> status) {
        this.status = status;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(final List<Tag> tags) {
        this.tags = tags;
    }

    public String getExpertCode() {
        return expertCode;
    }

    public void setExpertCode(final String expertCode) {
        this.expertCode = expertCode;
    }

    public boolean isWithArchived() {
        return withArchived;
    }

    public void setWithArchived(boolean withArchived) {
        this.withArchived = withArchived;
        if (withArchived) {
            getStatus().add(CourseStatus.ARCHIVED);
        }
    }

    public TimePeriod getTimePeriod() {
        return timePeriod;
    }

    public void setTimePeriod(TimePeriod timePeriod) {
        this.timePeriod = timePeriod;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public Order order() {
        return Order.desc("id");
    }

    public DetachedCriteria getCriteria() {
        final DetachedCriteria criteria = DetachedCriteria.forClass(Course.class);

        if (name != null && !("").equals(name)) {
            criteria.add(Restrictions.like(NAME, PERCENT + name + PERCENT));
        }
        if (status != null && status.size() > 0) {
            criteria.add(Restrictions.in(STATUS, status));
        }
        if (startDate != null) {
            final DateCriteriaCreator dateCriteriaCreator = new DateCriteriaCreator(START_DATE, startDate);
            criteria.add(dateCriteriaCreator.getDateCriteria(dateMoreLessEq));
        }
        if (timePeriod != null) {
            setTimePeriodCriteria(criteria, timePeriod);
        }
        if (tags != null && tags.size() > 0) {
            criteria.createAlias(TAGS, TAGS);
            final Disjunction tagDis = Restrictions.disjunction();
            for (final Tag tag : tags) {
                tagDis.add(Restrictions.eq(TAGS_VALUE, tag.getValue()));
            }
            criteria.add(tagDis);
        }
        if (types != null && types.size() > 0) {
            final Conjunction typesCon = Restrictions.conjunction();
            for (final CourseType type : types) {
                typesCon.add(Restrictions.sqlRestriction(
                  "exists (select t.course_id from CourseTypes t where t.course_id = {alias}.id and t.types = ?)",
                  type.name(), new StringType()));
            }
            criteria.add(typesCon);
        }
        if (expertCode != null && !("").equals(expertCode)) {
            criteria.createAlias(EXPERTS, EXPERTS);
            final Conjunction experts = Restrictions.conjunction();
            experts.add(Restrictions.eq("experts.code", expertCode));
            criteria.add(experts);
        }
        if (keyword != null && !("").equals(keyword)) {
            criteria.createAlias(EXPERTS, EXPERTS);
            criteria.createAlias(TAGS, TAGS);
            final Disjunction keywordDisjunction = Restrictions.disjunction();
            keywordDisjunction.add(Restrictions.like(NAME, PERCENT + keyword + PERCENT));
            keywordDisjunction.add(Restrictions.like("description", PERCENT + keyword + PERCENT));
            keywordDisjunction.add(Restrictions.like("experts.name", PERCENT + keyword + PERCENT));
            keywordDisjunction.add(Restrictions.like("experts.surname", PERCENT + keyword + PERCENT));
            keywordDisjunction.add(Restrictions.like(TAGS_VALUE, PERCENT + keyword + PERCENT));
            criteria.add(keywordDisjunction);
        }
        if (withArchived) {
            criteria.addOrder(Order.desc(STATUS));
        }
        criteria.addOrder(Order.desc("createDate"));
        return criteria;
    }


    private void setTimePeriodCriteria(DetachedCriteria criteria, TimePeriod timePeriod) {
        final DateCriteriaCreator dateCriteriaCreator;

        switch (timePeriod) {
            case UPCOMING:
                dateCriteriaCreator = new DateCriteriaCreator(START_DATE, dateService.getCurrentDate());
                criteria.add(dateCriteriaCreator.getDateCriteria('>'));
                break;

            case FINISHED:
                dateCriteriaCreator = new DateCriteriaCreator(END_DATE, dateService.getCurrentDate());
                criteria.add(dateCriteriaCreator.getDateCriteria('<'));
                break;

            case CURRENT:
                final DateTime today = new DateTime(dateService.getCurrentDate()).withTimeAtStartOfDay();
                criteria.add(Restrictions.le(START_DATE, today.toDate()));
                criteria.add(Restrictions.ge(END_DATE, today.toDate()));
                break;

            default: //nothing
        }
    }

}
