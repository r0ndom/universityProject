package net.github.rtc.app.service.handler;

import net.github.rtc.app.model.entity.message.Message;
import net.github.rtc.app.model.event.OrderMessageEvent;
import net.github.rtc.app.service.message.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Class that listen to messages events and handles them
 * @see net.github.rtc.app.model.event.OrderMessageEvent
 */
@Component
public class SendMessageEventHandler implements ApplicationListener<OrderMessageEvent> {

    @Autowired
    private MessageService messageService;

    /**
     * Catch OrderMessageEvent and persist messages in it to db
     * @param sendMessageEvent message that was published
     */
    @Override
    public void onApplicationEvent(OrderMessageEvent sendMessageEvent) {
        final List<Message> msgList = sendMessageEvent.getMessages();
        for (Message message: msgList) {
            messageService.create(message);
        }
    }
}
