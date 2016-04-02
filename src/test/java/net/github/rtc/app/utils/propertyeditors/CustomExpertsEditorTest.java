package net.github.rtc.app.utils.propertyeditors;

import net.github.rtc.app.model.entity.user.User;
import net.github.rtc.app.service.user.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Set;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(BlockJUnit4ClassRunner.class)
public class CustomExpertsEditorTest {
    @Mock
    private UserService userService;
    private User user1;
    private User user2;
    private String testString;
    private CustomExpertsEditor customExpertsEditor;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        user1 = new User();
        user1.setCode("X");

        user2 = new User();
        user2.setCode("Y");
        testString = "X,Y";
        customExpertsEditor = new CustomExpertsEditor(userService);
    }

    @Test
    public void testSetAsText() {
        when(userService.findByCode("X")).thenReturn(user1);
        when(userService.findByCode("Y")).thenReturn(user2);
        customExpertsEditor.setAsText(testString);
        Set<User> users = (Set<User>) customExpertsEditor.getValue();
        assertEquals(2, users.size());
        assertTrue(users.contains(user1));
        assertTrue(users.contains(user2));
    }
}
