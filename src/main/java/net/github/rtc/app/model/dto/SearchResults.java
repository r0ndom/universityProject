package net.github.rtc.app.model.dto;

import java.util.ArrayList;
import java.util.List;

public class SearchResults<T> {

    private List<T> results;
    private PageModel pageModel;

    public PageModel getPageModel() {
        if (pageModel == null) {
            pageModel = new PageModel();
        }
        return pageModel;
    }

    public void setPageModel(PageModel pageModel) {
        this.pageModel = pageModel;
    }

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> newResults) {
        if (newResults == null) {
            this.results = new ArrayList<>();
        }
        this.results = newResults;
    }
}
