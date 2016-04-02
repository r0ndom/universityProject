package net.github.rtc.app.model.dto.filter;

import net.github.rtc.app.model.entity.news.News;
import net.github.rtc.app.model.entity.news.NewsStatus;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import java.util.Date;

public class NewsSearchFilter extends AbstractSearchFilter {

    private static final String PERCENT = "%";
    private static final String CREATE_DATE = "createDate";
    private static final String AUTHOR = "author";

    private String title;

    private String authorCode;

    private NewsStatus status;

    private Date createDate;

    private char dateMoreLessEq;

    @Override
    public Order order() {
        return Order.desc(CREATE_DATE);
    }

    @Override
    public DetachedCriteria getCriteria() {
        final DetachedCriteria criteria = DetachedCriteria.forClass(News.class);
        if (title != null && !("").equals(title)) {
            criteria.add(Restrictions.like("title", PERCENT + title + PERCENT));
        }
        if (createDate != null) {
            final DateCriteriaCreator dateCriteriaCreator = new DateCriteriaCreator(CREATE_DATE, createDate);
            criteria.add(dateCriteriaCreator.getDateCriteria(dateMoreLessEq));
        }

        if (status != null) {
            criteria.add(Restrictions.eq("status", status));
        }

        if (authorCode != null && !("").equals(authorCode)) {
            criteria.createAlias(AUTHOR, AUTHOR);
            criteria.add(Restrictions.eq("author.code", authorCode));
        }
        return criteria;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthorCode() {
        return authorCode;
    }

    public void setAuthorCode(final String authorCode) {
        this.authorCode = authorCode;
    }

    public NewsStatus getStatus() {
        return status;
    }

    public void setStatus(NewsStatus status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return createDate == null ? null : new Date(createDate.getTime());
    }

    public void setCreateDate(Date createDate) {
        if (createDate != null) {
            this.createDate = new Date(createDate.getTime());
        }
    }

    public char getDateMoreLessEq() {
        return dateMoreLessEq;
    }

    public void setDateMoreLessEq(char dateMoreLessEq) {
        this.dateMoreLessEq = dateMoreLessEq;
    }
}
