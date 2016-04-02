package net.github.rtc.app.model.dto.filter;

import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * Created by Илья on 20.02.2015.
 */

@Component
public class LogsSearchFilter implements SearchFilter {
    private static final int PAGE_OFFSET = 1;
    private static final int MAX_OFFSET = 3;
    private static final int NUMBER_OF_ENTITIES_PER_PAGE = 5;

    private Date createdDate;
    private char dateMoreLessEq;
    private int page;
    private int perPage = NUMBER_OF_ENTITIES_PER_PAGE;


    public char getDateMoreLessEq() {
        return dateMoreLessEq;
    }

    public void setDateMoreLessEq(char dateMoreLessEq) {
        this.dateMoreLessEq = dateMoreLessEq;
    }

    public Date getCreatedDate() {
        return createdDate == null ? null : new Date(createdDate.getTime());
    }

    public void setCreatedDate(final Date createdDate) {
        if (createdDate != null) {
            this.createdDate = new Date(createdDate.getTime());
        }
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

    public int getPageOffset() {
        return PAGE_OFFSET;
    }

    public int getMaxOffset() {
        return MAX_OFFSET;
    }
}
