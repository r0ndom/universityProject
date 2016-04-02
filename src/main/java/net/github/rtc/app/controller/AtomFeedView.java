package net.github.rtc.app.controller;

import com.sun.syndication.feed.atom.*;
import net.github.rtc.app.controller.common.FeedNewsController;
import net.github.rtc.app.model.entity.news.News;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.servlet.view.feed.AbstractAtomFeedView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Component
public class AtomFeedView extends AbstractAtomFeedView {

    @Value("${feed.title}")
    private String title;
    @Value("${feed.id}")
    private String feedId;
    @Value("${feed.url}")
    private String newsAbsoluteUrl;

    @Override
    protected void buildFeedMetadata(Map<String, Object> model, Feed feed, HttpServletRequest request) {

        feed.setTitle(title);
        final Link link = new Link();
        link.setHref(newsAbsoluteUrl + "rss");
        feed.setAlternateLinks(Arrays.asList(link));
        setUpdatedIfNeeded(model, feed);
        final Person author = new Person();
        author.setName("Tatyana Bulanaya");
        author.setEmail("johndoe@example.com");
        feed.setAuthors(Arrays.asList(author));
        feed.setId(feedId);
    }

    /**
     * Set updated foe feed if needed
     *
     * @param model the model, contains latest update date
     * @param feed  feed
     */
    private void setUpdatedIfNeeded(Map<String, Object> model, Feed feed) {
        final Date lastUpdate = (Date) model.get(FeedNewsController.LAST_UPDATE_VIEW_KEY);
        try {
            if (lastUpdate == null) {
                throw new NullPointerException("Last Update can not be null");
            } else {
                if (feed.getUpdated() == null || lastUpdate.compareTo(feed.getUpdated()) > 0) {
                    feed.setUpdated(lastUpdate);
                }
            }
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected List<Entry> buildFeedEntries(
      final Map<String, Object> model,
      final HttpServletRequest request,
      final HttpServletResponse response) throws Exception {
        final List<News> newsList = (List<News>) model.get("news");
        final List<Entry> entries = new ArrayList<>();
        for (News news : newsList) {
            addEntry(entries, news);
        }
        return entries;
    }

    /**
     * Build Entry from News and add it to entry
     *
     * @param entries list of entries
     * @param news    the News we want to convert to entry and push to list
     */
    private void addEntry(List<Entry> entries, News news) {
        Entry entry = new Entry();
        entry.setId(newsAbsoluteUrl + news.getCode());
        entry.setTitle(news.getTitle());
        entry.setUpdated(news.getPublishDate());
        final Person author = new Person();
        author.setName(news.getAuthor().getName());
        entry.setAuthors(Arrays.asList(author));
        entry = setSummary(news, entry);
        entry = setLink(news, entry);
        entries.add(entry);
    }

    /**
     * get Summary from News and set it to Entry
     *
     * @param entry entry where we wanna set summary
     * @param news  news from what we want to read summary data
     */
    private Entry setSummary(News news, Entry entry) {
        final Content summary = new Content();
        summary.setValue(news.getDescription());
        entry.setSummary(summary);
        return entry;
    }

    private Entry setLink(News news, Entry entry) {
        final Link link = new Link();
        String uri;
        try {
            uri = ServletUriComponentsBuilder.fromCurrentContextPath().
              path("/admin/news/" + news.getCode()).build().toUriString();
        } catch (IllegalStateException e) {
            uri = newsAbsoluteUrl;
        }
        link.setHref(uri);
        entry.setAlternateLinks(Arrays.asList(link));
        return entry;
    }
}
