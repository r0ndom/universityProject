package net.github.rtc.app.dao.news;

import net.github.rtc.app.dao.generic.GenericDao;
import net.github.rtc.app.model.entity.news.News;
import net.github.rtc.app.model.entity.news.NewsStatus;

import java.util.List;

/**
 * Data access object for News domain class
 * @see net.github.rtc.app.model.entity.news.News
 */
public interface NewsDao extends GenericDao<News> {

    /**
     * Find all news in db by status
     * @param status of which news need to find
     * @return list of news with specified status
     */
    List<News> findAllByStatus(NewsStatus status);
}
