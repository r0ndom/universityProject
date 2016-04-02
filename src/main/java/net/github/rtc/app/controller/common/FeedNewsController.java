package net.github.rtc.app.controller.common;

import net.github.rtc.app.model.entity.news.News;
import net.github.rtc.app.model.entity.news.NewsStatus;
import net.github.rtc.app.service.news.NewsService;
import net.github.rtc.app.controller.AtomFeedView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;
import java.util.List;

/**
 * Created by dell on 21.01.15.
 */
@Controller
public class FeedNewsController {
    public static final String NEWS = "news";
    public static final String LAST_UPDATE_VIEW_KEY = "lastUpdate";
    @Autowired
    private NewsService newsService;
    @Autowired
    private AtomFeedView atomFeedView;
    /**
     * Redirect to atom feed view to show latest published news
     *
     * @return redirect to "/news/feed"
     */
    @RequestMapping(value = "/news/feed", method = RequestMethod.GET)
    public ModelAndView feed() {
        final ModelAndView modelAndView = new ModelAndView();
        modelAndView.setView(atomFeedView);
        final List<News> news = newsService.findAllByStatus(NewsStatus.PUBLISHED);
        modelAndView.addObject(NEWS, news);
        modelAndView.addObject(LAST_UPDATE_VIEW_KEY, getCreationDateOfTheLast(news));
        return modelAndView;
    }

    private Date getCreationDateOfTheLast(List<News> news) {
        if (news.size() > 0) {
            return news.get(0).getPublishDate();
        }
        return new Date(0);
    }
}

