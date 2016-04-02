package net.github.rtc.app.service.mail;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import static org.mockito.Mockito.*;

@RunWith(BlockJUnit4ClassRunner.class)
public class MailServiceImplTest {

    @InjectMocks
    private MailService mailService = new MailServiceImpl();

    @Mock
    MailSender mailSender;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSendMailSimpleMailMessage() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("");
        message.setTo("");
        message.setSubject("");
        message.setText("");
        doNothing().when(mailSender).send(message);
        mailService.sendMail(message);
        verify(mailSender).send(message);
    }

    @Test
    public void testSendMailStrings() {
        String EXAMPLE_STRING = "111";

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(EXAMPLE_STRING);
        message.setTo(EXAMPLE_STRING);
        message.setSubject(EXAMPLE_STRING);
        message.setText(EXAMPLE_STRING);

        doNothing().when(mailSender).send(message);
        mailService.sendMail(EXAMPLE_STRING,EXAMPLE_STRING,EXAMPLE_STRING,EXAMPLE_STRING);
        verify(mailSender).send(message);
    }
}