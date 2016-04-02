package net.github.rtc.app.dao.news;

import net.github.rtc.app.dao.generic.AbstractGenericDaoImpl;
import net.github.rtc.app.model.entity.news.News;
import net.github.rtc.app.model.entity.news.NewsStatus;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class NewsDaoImpl extends AbstractGenericDaoImpl<News> implements NewsDao {

    public static final String STATUS = "status";
    public static final String PUBLISH_DATE = "publishDate";

    @Override
    public List<News> findAllByStatus(NewsStatus status) {
        return getCurrentSession().createCriteria(News.class).add(Restrictions.eq(STATUS, status)).addOrder(Order.asc(PUBLISH_DATE)).list();
    }
}
