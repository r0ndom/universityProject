package net.github.rtc.app.controller.admin;


import net.github.rtc.app.controller.common.MenuItem;
import net.github.rtc.app.model.entity.news.News;
import net.github.rtc.app.model.entity.news.NewsStatus;
import net.github.rtc.app.service.news.NewsService;
import net.github.rtc.app.utils.enums.EnumHelper;
import net.github.rtc.util.converter.ValidationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;

@Controller
@RequestMapping(value = "/admin/news")
public class NewsController implements MenuItem {

    public static final String NEWS = "news";
    private static final String STATUSES = "statuses";
    private static final String VALIDATION_RULES = "validationRules";

    private static final String ROOT = "portal/admin";
    private static final String REDIRECT = "redirect:";
    private static final String ADMIN_SEARCH = "/admin/search";
    private static final String REDIRECT_VIEW = "redirect:/admin/news/";
    private static final String UPDATE_VIEW = "/news/newsUpdate";
    private static final String CREATE_VIEW = "/news/newsCreate";
    private static final String DETAILS_VIEW = "/news/newsDetails";

    @Autowired
    private NewsService newsService;
    @Autowired
    private ValidationContext validationContext;
    @Autowired
    private LastSearchCommand lastSearchCommand;

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView create() {
        final ModelAndView mav = new ModelAndView(ROOT + CREATE_VIEW);
        mav.addObject(VALIDATION_RULES, validationContext.get(News.class));
        return mav;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@ModelAttribute(NEWS) final News news,
                       @RequestParam(required = false) final boolean publish) {
        newsService.create(news, publish);
        lastSearchCommand.dropFilter();
        return REDIRECT_VIEW + news.getCode();
    }

    @RequestMapping(value = "/{newsCode}", method = RequestMethod.GET)
    public ModelAndView single(@PathVariable final String newsCode) {
        final ModelAndView mav = new ModelAndView(ROOT + DETAILS_VIEW);
        mav.addObject(NEWS, newsService.findByCode(newsCode));
        return mav;
    }

    @RequestMapping(value = "update/{newsCode}", method = RequestMethod.GET)
    public ModelAndView update(@PathVariable final String newsCode) {
        final ModelAndView mav = new ModelAndView(ROOT + UPDATE_VIEW);
        mav.getModelMap().addAttribute(NEWS, newsService.findByCode(newsCode));
        mav.addObject(VALIDATION_RULES, validationContext.get(News.class));
        return mav;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public String update(@ModelAttribute(NEWS) final News news,
                         @RequestParam(required = false) final boolean publish) {
        newsService.update(news, publish);
        lastSearchCommand.dropFilter();
        return REDIRECT_VIEW + news.getCode();
    }

    @RequestMapping(value = "/delete/{newsCode}", method = RequestMethod.GET)
    public String deleteByCode(@PathVariable final String newsCode) {
        newsService.deleteByCode(newsCode);
        return REDIRECT + ADMIN_SEARCH;
    }

    @RequestMapping(value = "/publish/{newsCode}", method = RequestMethod.GET)
    public String publishByCode(@PathVariable final String newsCode) {
        newsService.publish(newsCode);
        return REDIRECT + ADMIN_SEARCH;
    }

    @ModelAttribute(STATUSES)
    public Collection<String> getStatuses() {
        return EnumHelper.getNames(NewsStatus.class);
    }

    @ModelAttribute(NEWS)
    public News getNews() {
        return new News();
    }

    @Override
    public String getMenuItem() {
        return NEWS;
    }
}
