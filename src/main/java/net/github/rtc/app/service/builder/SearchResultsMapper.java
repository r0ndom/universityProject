package net.github.rtc.app.service.builder;

import java.util.List;

/**
 * Helps to convert one list from search results to another
 * @param <T>  the type from what list needs to be transformed for example Course
 * @param <E>  the type to what list needs to be transformed for example UserCourseDto
 */
public interface SearchResultsMapper<T, E> {

    /**
     * Map list.
     * @param searchResults the list that needs to be mapped
     * @return mapped list
     */
    List<E> map(List<T> searchResults);
}
