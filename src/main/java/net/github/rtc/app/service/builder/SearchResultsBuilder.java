package net.github.rtc.app.service.builder;

import net.github.rtc.app.model.dto.SearchResults;

import javax.annotation.Nonnull;


/**
 * The class that helps to build Search results of one type to another
 * @see net.github.rtc.app.model.dto.SearchResults
 * @param <T>  the type from what search results needs to be transformed for example Course
 * @param <E>  the type to what search results needs to be transformed for example UserCourseDto
 */
public class SearchResultsBuilder<T, E> {

    private SearchResults<T> searchResultsToTransform;
    private SearchResultsMapper<T, E> searchResultsMapper;

    /**
     * Sets search results to transform.
     * @param searchResultsToTransform the search results to transform
     * @return this object, cannot be null
     */
    @Nonnull
    public SearchResultsBuilder<T, E> setSearchResultsToTransform(SearchResults<T> searchResultsToTransform) {
        this.searchResultsToTransform = searchResultsToTransform;
        return this;
    }

    /**
     * Sets search results mapper
     * @param searchResultsMapper object that map from one type of the list to another
     * @return this object, cannot be null
     */
    @Nonnull
    public SearchResultsBuilder<T, E> setSearchResultsMapper(SearchResultsMapper<T, E> searchResultsMapper) {
        this.searchResultsMapper = searchResultsMapper;
        return this;
    }

    /**
     * Return current prebuilt SearchResults object
     * @return the resulting object, cannot be null
     */
    @Nonnull
    public SearchResults<E> build() {
        final SearchResults<E> transformedSearchResults = new SearchResults<>();
        transformedSearchResults.setResults(searchResultsMapper.map(searchResultsToTransform.getResults()));
        transformedSearchResults.setPageModel(searchResultsToTransform.getPageModel());
        return transformedSearchResults;
    }
}
