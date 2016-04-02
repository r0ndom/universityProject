package net.github.rtc.app.controller.admin;

import net.github.rtc.app.model.dto.filter.SearchFilter;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

/**
 * Class that contains last performed search command from search page.
 * @see net.github.rtc.app.controller.admin.SearchController
 */
@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class LastSearchCommand {

    private SearchFilter searchFilter;
    private String menuItem;

    /**
     * Sets last performed search filtering operation
     * @param searchFilter the search filter
     */
    public void setLastFilter(SearchFilter searchFilter) {
        this.searchFilter = searchFilter;
    }

    public void setMenuItem(String menuItem) {
        this.menuItem = menuItem;
    }

    public String getMenuItem() {
        return menuItem;
    }

    /**
     * Return last searchFilter if it is instance of class param else
     * return new instance of required class if error return null
     * @param aClass instance of what class required
     * @return the search filter
     */
    public SearchFilter getSearchFilter(Class<? extends SearchFilter> aClass) {
        if (aClass.isInstance(searchFilter)) {
            return searchFilter;
        }
        try {
            return aClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            return null;
        }
    }

    /**
     * Gets last page.
     * @return the last page
     */
    public int getLastPage() {
        if (searchFilter != null) {
            return searchFilter.getPage();
        } else {
            return 1;
        }
    }

    /**
     * Drop filter.
     */
    public void dropFilter() {
        searchFilter = null;
    }
}
