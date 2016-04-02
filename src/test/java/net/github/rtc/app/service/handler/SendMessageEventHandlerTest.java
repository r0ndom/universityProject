package net.github.rtc.app.service.handler;

import net.github.rtc.app.model.entity.message.Message;
import net.github.rtc.app.model.event.OrderMessageEvent;
import net.github.rtc.app.service.message.MessageService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(BlockJUnit4ClassRunner.class)
public class SendMessageEventHandlerTest {

    @InjectMocks
    private SendMessageEventHandler sendMessageEventHandler;

    @Mock
    private MessageService messageService;

    @Mock
    private OrderMessageEvent messageEvent;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testApplicationEvent() {
        List<Message> messages = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            messages.add(new Message());
        }
        when(messageEvent.getMessages()).thenReturn(messages);
        sendMessageEventHandler.onApplicationEvent(messageEvent);
        verify(messageService, times(5)).create(any(Message.class));

    }
}
