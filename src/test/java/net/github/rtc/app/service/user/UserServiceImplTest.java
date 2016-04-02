package net.github.rtc.app.service.user;

import net.github.rtc.app.dao.DaoTestContext;
import net.github.rtc.app.dao.generic.GenericDao;
import net.github.rtc.app.dao.user.UserDao;
import net.github.rtc.app.model.dto.filter.AbstractSearchFilter;
import net.github.rtc.app.model.dto.filter.UserSearchFilter;
import net.github.rtc.app.model.entity.AbstractPersistenceObject;
import net.github.rtc.app.model.entity.activity.ActivityEntity;
import net.github.rtc.app.model.entity.user.Role;
import net.github.rtc.app.model.entity.user.RoleType;
import net.github.rtc.app.model.entity.user.User;
import net.github.rtc.app.model.entity.user.UserStatus;
import net.github.rtc.app.service.builder.MailMessageBuilder;
import net.github.rtc.app.service.date.DateService;
import net.github.rtc.app.service.generic.AbstractCrudEventsServiceTest;
import net.github.rtc.app.service.generic.GenericService;
import net.github.rtc.app.service.mail.MailService;
import net.github.rtc.app.utils.web.files.upload.FileUpload;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceImplTest extends AbstractCrudEventsServiceTest {

    @Mock
    private UserDao userDao;
    @Mock
    private DateService dateService;
    @Mock
    private FileUpload upload;
    @Mock
    private MailService mailService;
    @Mock
    private MailMessageBuilder mailMessageBuilder;
    @InjectMocks
    private UserServiceImpl userService;

    private DaoTestContext daoTestContext = new DaoTestContext();

    @Test
    public void testCreate() {

        User user = (User) getTestEntity();
        MultipartFile image = new MockMultipartFile("file", new byte[10]);

        when(userDao.create(any(User.class))).thenReturn(user);

        User resultUser = userService.create(user, image, true);

        assertEquals(resultUser.getStatus(), UserStatus.ACTIVE);
        verify(userDao, times(1)).create(any(User.class));
        verify(upload, times(1)).saveImage(anyString(), any(MultipartFile.class));

        user = (User) getTestEntity();
        resultUser = userService.create(user, image, false);

        assertEquals(resultUser.getStatus(), UserStatus.INACTIVE);
        verify(userDao, times(2)).create(any(User.class));
        verify(upload, times(2)).saveImage(anyString(), any(MultipartFile.class));

        super.testCreate();
    }

    @Test
    public void testUpdate() {
        User user = (User) getTestEntity();
        MultipartFile image = new MockMultipartFile("file", new byte[10]);

        doAnswer(new Answer() {
            @Override
            public Object answer(final InvocationOnMock invocation) throws Throwable {
                return invocation.getArguments()[0]; // courseDao.create(user) -> user
            }
        }).when(userDao).update(any(User.class));

        User resultUser = userService.update(user, image, true);

        assertEquals(resultUser.getStatus(), UserStatus.ACTIVE);
        verify(userDao, times(1)).update(any(User.class));
        verify(upload, times(1)).saveImage(anyString(), any(MultipartFile.class));

        user = (User) getTestEntity();
        user.setStatus(UserStatus.INACTIVE);
        resultUser = userService.update(user, image, false);

        assertEquals(resultUser.getStatus(), UserStatus.INACTIVE);
        verify(userDao, times(2)).update(any(User.class));
        verify(upload, times(2)).saveImage(anyString(), any(MultipartFile.class));

        super.testUpdate();
    }

    @Test
    public void testGetType() {
        assertEquals(userService.getType(), User.class);
    }

    @Test
    public void testCreateRole() {
        when(userDao.createRole(any(Role.class))).thenReturn(anyLong());

        final long id = userService.createRole(RoleType.ROLE_USER);
        verify(userDao).createRole(any(Role.class));
    }

    @Test
    public void testGetRoleByType() {
        userService.findRoleByType(RoleType.ROLE_USER);
        verify(userDao).findRoleByType(RoleType.ROLE_USER);
    }

    @Test
    public void testFindAllByRole() {
        userService.findAllByRole(RoleType.ROLE_USER);
        verify(userDao).findAllByType(RoleType.ROLE_USER);
    }

    @Test
    public void testLoadUserByUsername() {
        userService.loadUserByUsername("email");
        verify(userDao).findByEmail("email");
    }

    @Test
    public void testMarkUserForRemoval() {
        User user = (User) getTestEntity();
        when(userDao.findByCode(CODE)).thenReturn(user);
        when(dateService.addDays(any(Date.class), anyInt())).thenReturn(new Date());
        doAnswer(new Answer() {
            @Override
            public Object answer(final InvocationOnMock invocation) throws Throwable {
                return invocation.getArguments()[0]; // courseDao.create(user) -> user
            }
        }).when(userDao).update(any(User.class));

        userService.markUserForRemoval(CODE);

        assertEquals(user.getStatus(), UserStatus.FOR_REMOVAL);
        assertNotNull(user.getRemovalDate());
        verify(userDao).update(any(User.class));
    }

    @Test
    public void testActivateUser() {
        User user = (User) getTestEntity();

        when(userDao.findByCode(CODE)).thenReturn(user);
        doAnswer(new Answer() {
            @Override
            public Object answer(final InvocationOnMock invocation) throws Throwable {
                return invocation.getArguments()[0]; // courseDao.create(user) -> user
            }
        }).when(userDao).update(any(User.class));

        userService.activateUser(CODE);

        verify(userDao).update(user);
        assertEquals(user.getStatus(), UserStatus.ACTIVE);
    }

    @Test
    public void testInactivateUser() {
        User user = (User) getTestEntity();

        when(userDao.findByCode(CODE)).thenReturn(user);
        doAnswer(new Answer() {
            @Override
            public Object answer(final InvocationOnMock invocation) throws Throwable {
                return invocation.getArguments()[0]; // courseDao.create(user) -> user
            }
        }).when(userDao).update(any(User.class));

        userService.inactivateUser(CODE);

        verify(userDao).update(user);
        assertEquals(user.getStatus(), UserStatus.INACTIVE);
    }

    @Test
    public void testRestoreUser() {
        User user = (User) getTestEntity();

        when(userDao.findByCode(CODE)).thenReturn(user);
        doAnswer(new Answer() {
            @Override
            public Object answer(final InvocationOnMock invocation) throws Throwable {
                return invocation.getArguments()[0]; // courseDao.create(user) -> user
            }
        }).when(userDao).update(any(User.class));

        userService.restoreUser(CODE);

        verify(userDao).update(user);
        assertEquals(user.getStatus(), UserStatus.INACTIVE);
    }

    @Test
    public void testFindAllUserNameAndCode() {
        User user = (User) getTestEntity();
        when(userDao.findAllByType(RoleType.ROLE_USER)).thenReturn(Arrays.asList(user));

        Map<String, String> result = userService.findAllUserNameAndCode(RoleType.ROLE_USER);

        assertEquals(result.size(), 1);
        assertEquals(result.get(user.shortString()), user.getCode());

    }

    @Test
    public void testRegisterUser() {
        User user = (User) getTestEntity();
        MultipartFile image = new MockMultipartFile("file", new byte[10]);

        when(userDao.create(any(User.class))).thenReturn(user);
        Role role = new Role(RoleType.ROLE_USER);
        when(userDao.findRoleByType(RoleType.ROLE_USER)).thenReturn(role);

        userService.registerUser(user, image);

        assertEquals(user.getStatus(), UserStatus.ACTIVE);
        assertEquals(user.getAuthorities().size(), 1);
        assertTrue(user.getAuthorities().contains(role));
        assertNotNull(user.getRegisterDate());
        verify(userDao).create(any(User.class));
        verify(mailService).sendMail(any(SimpleMailMessage.class));
    }

    @Test
    public void testIsEmailExist() {
        when(userDao.isEmailExist(CODE)).thenReturn(true);
        assertEquals(userService.isEmailExist(CODE), true);
        verify(userDao).isEmailExist(CODE);
    }

    @Override
    protected GenericService<AbstractPersistenceObject> getGenericService() {
        return (GenericService) userService;
    }

    @Override
    protected AbstractPersistenceObject getTestEntity() {
        return daoTestContext.getModelBuilder(User.class).build();
    }

    @Override
    protected AbstractSearchFilter getSearchFilter() {
        return new UserSearchFilter();
    }

    @Override
    protected GenericDao<AbstractPersistenceObject> getGenericDao() {
        return (GenericDao) userDao;
    }

    @Override
    protected ActivityEntity trueActivityEntity() {
        return ActivityEntity.USER;
    }
}