package net.github.rtc.app.utils.propertyeditors;

import net.github.rtc.app.model.entity.user.Role;
import net.github.rtc.app.model.entity.user.RoleType;
import net.github.rtc.app.service.user.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(BlockJUnit4ClassRunner.class)
public class CustomRoleEditorTest {
    @Mock
    private UserService userService;
    private Role role1;
    private Role role2;
    private String testString;
    private CustomRoleEditor customRoleEditor;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        role1 = new Role(RoleType.ROLE_ADMIN);
        role2 = new Role(RoleType.ROLE_EXPERT);

        testString = "ROLE_ADMIN,ROLE_EXPERT";

        customRoleEditor = new CustomRoleEditor(userService);
    }

    @Test
    public void testSetAsText() {
        when(userService.findRoleByType(RoleType.ROLE_ADMIN)).thenReturn(role1);
        when(userService.findRoleByType(RoleType.ROLE_EXPERT)).thenReturn(role2);
        customRoleEditor.setAsText(testString);
        List<Role> roles = (List<Role>) customRoleEditor.getValue();
        assertEquals(2, roles.size());
        assertTrue(roles.contains(role1));
        assertTrue(roles.contains(role2));
    }
}
