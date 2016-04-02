package net.github.rtc.app.utils.web;

import net.github.rtc.app.model.dto.filter.ActivitySearchFilter;
import net.github.rtc.app.model.entity.activity.ActivityEntity;
import net.github.rtc.app.model.entity.user.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.servlet.support.BindStatus;
import org.springframework.web.servlet.support.RequestContext;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:mvc-test.xml" )
public class AdditionalTagsRenderTest {

    @InjectMocks
    private AdditionalTagsRenderer tagsRenderer;
    @Mock
    private BindStatus bindStatus;
    @Mock
    private Map<String, Object> model;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRender() {
        final String beanName = "beanName.";
        User user = new User();
        user.setName("name");
        user.setSurname("surname");
        when(bindStatus.getPath()).thenReturn(beanName);
        when(bindStatus.getExpression()).thenReturn("name");
        when(model.get(anyString())).thenReturn(user);
        assertEquals("maxlength=\"50\"", tagsRenderer.renderAdditionalTags(bindStatus, model));
    }
}
