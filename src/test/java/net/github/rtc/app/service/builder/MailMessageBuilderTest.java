package net.github.rtc.app.service.builder;

import net.github.rtc.app.model.entity.user.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.SimpleMailMessage;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Mockito.when;

@RunWith(BlockJUnit4ClassRunner.class)
public class MailMessageBuilderTest {
    private final static String NAME = "name";
    private final static String EMAIL = "email@email.com";
    private final static String FROM = "rtcsender@gmail.com";
    @Mock
    private TemplateStringBuilder templateStringBuilder = new TemplateStringBuilder();

    @InjectMocks
    private MailMessageBuilder mailMessageBuilder = new MailMessageBuilder();

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void buildMsgTest() {
        when(templateStringBuilder.build(any(String.class), anyMap())).thenReturn("");

        User user = new User();
        user.setName(NAME);
        user.setEmail(EMAIL);
        final SimpleMailMessage mailMessage = mailMessageBuilder.build(user);
        assertNotNull(mailMessage);
        String mail = mailMessage.getTo()[0];
        assertEquals(EMAIL, mail);
        assertEquals(FROM,mailMessage.getFrom());
    }
}
