package net.github.rtc.app.model.dto;

import java.util.HashMap;
import java.util.Map;

public class PageModel {

    private static final int PAGE_OFFSET = 1;
    private static final int MAX_OFFSET = 3;

    private int totalResults;
    private int page;
    private int perPage;

    public Map<String, Object> getPageParams() {
        final int countPages = totalResults / perPage + ((totalResults % perPage == 0) ? 0 : 1);
        final Map<String, Object> map = new HashMap<>();
        int begin;
        int end;
        if (page == countPages) {
            begin = Math.max(1, page - PAGE_OFFSET - 1);
            end = page;
        } else {
            begin = Math.max(1, page - PAGE_OFFSET);
            if (countPages == MAX_OFFSET) {
                end = MAX_OFFSET;
            } else {
                end = Math.min(begin + PAGE_OFFSET, countPages);
            }
        }
        map.put("currentPage", page);
        map.put("lastPage", countPages);
        map.put("beginIndex", begin);
        map.put("endIndex", end);
        return map;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPerPage() {
        return perPage;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }
}
