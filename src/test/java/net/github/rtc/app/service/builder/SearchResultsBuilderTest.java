package net.github.rtc.app.service.builder;

import net.github.rtc.app.model.dto.PageModel;
import net.github.rtc.app.model.dto.SearchResults;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(BlockJUnit4ClassRunner.class)
public class SearchResultsBuilderTest {

    @InjectMocks
    private SearchResultsBuilder searchResultsBuilder;
    @Mock
    private SearchResults searchResults;
    @Mock
    private SearchResultsMapper searchResultsMapper;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testBuild() {
        SearchResults results = new SearchResults();
        when(searchResults.getPageModel()).thenReturn(new PageModel());
        results.setResults(searchResultsMapper.map(searchResults.getResults()));
        results.setPageModel(new PageModel());
        assertTrue(equalsObject(searchResults, searchResultsBuilder.build()));
    }

    private boolean equalsObject(SearchResults results1, SearchResults results2) {
        final boolean firstCondition = results1.getResults().equals(results2.getResults());
        final boolean secondCondition = results1.getPageModel().equals(results2.getPageModel());
        return firstCondition&&secondCondition;
    }

}
