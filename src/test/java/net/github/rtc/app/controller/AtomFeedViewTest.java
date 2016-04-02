package net.github.rtc.app.controller;


import com.sun.syndication.feed.atom.Entry;
import com.sun.syndication.feed.atom.Feed;
import com.sun.syndication.feed.atom.Link;
import com.sun.syndication.feed.atom.Person;
import net.github.rtc.app.controller.AtomFeedView;
import net.github.rtc.app.controller.common.FeedNewsController;
import net.github.rtc.app.model.entity.news.News;
import net.github.rtc.app.model.entity.user.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.*;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:feed-test.xml")
public class AtomFeedViewTest {

    @Value("${feed.title}")
    private String title;
    @Value("${feed.id}")
    private String feedId;
    @Value("${feed.url}")
    private String newsAbsoluteUrl;


    @Autowired
    private AtomFeedView atomFeedView;

    @Test
    public void testBuildFeedMetadata() {
        Map<String, Object> map = new HashMap();
        Date currentDate = new Date();
        map.put(FeedNewsController.LAST_UPDATE_VIEW_KEY, currentDate);
        Feed feed = new Feed();
        atomFeedView.buildFeedMetadata(map, feed, null);
        assertEquals(title, feed.getTitle());
        assertEquals(feedId, feed.getId());
        final Link link = new Link();
        link.setHref(newsAbsoluteUrl + "rss");
        assertTrue(feed.getAlternateLinks().contains(link));
        assertEquals(currentDate, feed.getUpdated());
    }

    @Test
    public void  buildFeedEntries() throws Exception {
        Date currentDate = new Date();
        News news1 = new News();
        User author = new User();
        author.setName("author");
        news1.setAuthor(author);
        news1.setTitle("title");
        news1.setDescription("bla bla");
        news1.setPublishDate(currentDate);
        Map<String, Object> map = new HashMap<>();
        map.put("news", Arrays.asList(news1));
        List<Entry> entries =  atomFeedView.buildFeedEntries(map, null, null);
        Entry entry = entries.get(0);

        assertEquals("author", ((Person)entry.getAuthors().get(0)).getName());
        assertEquals("title", entry.getTitle());
        assertEquals("bla bla", entry.getSummary().getValue());
        assertEquals(currentDate, entry.getUpdated());
    }

}
