package net.github.rtc.app.controller.user;

import net.github.rtc.app.controller.common.RegisterController;
import net.github.rtc.app.model.entity.user.User;
import net.github.rtc.app.service.mail.MailService;
import net.github.rtc.app.service.user.UserService;
import net.github.rtc.app.service.date.DateService;
import net.github.rtc.util.converter.ValidationContext;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = "classpath:mvc-test.xml")
public class RegisterControllerTest {

    @InjectMocks
    private RegisterController controller;

    @Mock
    private ValidationContext validationContext;
    @Mock
    private UserService userService;
    @Mock
    private DateService dateService;
    @Mock
    private MailService mailService;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    public void testRegisterPage() throws Exception {
        when(validationContext.get(User.class)).thenReturn("validationContext");
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("validationRules"));
    }

    @Test
    public void testSave() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.fileUpload("/register/save").requestAttr("user", new User()))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/login/"));
    }
}
