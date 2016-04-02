package net.github.rtc.app.service.message;

import net.github.rtc.app.dao.message.MessageDao;
import net.github.rtc.app.model.dto.PageModel;
import net.github.rtc.app.model.dto.SearchResults;
import net.github.rtc.app.model.dto.filter.MessageSearchFilter;
import net.github.rtc.app.model.entity.activity.Activity;
import net.github.rtc.app.model.entity.message.Message;
import net.github.rtc.app.model.entity.message.MessageStatus;
import net.github.rtc.app.model.entity.order.UserCourseOrder;
import net.github.rtc.app.model.entity.user.User;
import net.github.rtc.app.service.builder.message.OrderResponseMessageBuilder;
import net.github.rtc.app.service.builder.message.OrderSendMessageBuilder;
import net.github.rtc.app.service.user.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class MessageServiceTest {

    public static final String CODE = "any";

    @InjectMocks
    private MessageServiceImpl messageService;

    @Mock
    private MessageDao messageDao;

    @Mock
    private UserService userService;

    @Mock
    private OrderResponseMessageBuilder orderResponseMessageBuilder;

    @Mock
    private OrderSendMessageBuilder orderSendMessageBuilder;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSearchMessagesForUser() {
        SearchResults searchResults = new SearchResults();
        searchResults.setResults(new ArrayList());
        searchResults.setPageModel(new PageModel());
        when(messageService.search(any(MessageSearchFilter.class))).thenReturn(searchResults);
        SearchResults test = messageService.searchUserMessages(new MessageSearchFilter());
        assertTrue(isEqualSearchResults(test, searchResults));
    }

    @Test
    public void testReadMessage() {
        Message message = new Message();
        Message expected = new Message();
        expected.setRead(true);
        when(messageService.findByCode(anyString())).thenReturn(message);
        when(messageDao.update(any(Message.class))).thenReturn(message);
        assertTrue((messageService.getMessage(CODE)).equals(expected));
    }

    @Test
    public void testGetUserUnreadMessageCount() {
        User user = new User();
        when(messageDao.getMessageCountByUserAndRead(user, true)).thenReturn(new Integer(1));
        when(messageDao.getMessageCountByUserAndRead(user, false)).thenReturn(new Integer(0));
        assertEquals(0, messageService.getMessageCountByUserAndStatus(user, MessageStatus.UNREAD));
    }

    @Test
    public void testCreateOrderResponseMessage() {
        UserCourseOrder order = new UserCourseOrder();
        when(orderResponseMessageBuilder.build(order)).thenReturn(new ArrayList<Message>());
        messageService.createOrderResponseMessage(order);
        assertEquals(new ArrayList<Message>(), orderResponseMessageBuilder.build(order));
    }

    @Test
    public void testCreateOrderSendMessage() {
        UserCourseOrder order = new UserCourseOrder();
        when(orderSendMessageBuilder.build(order)).thenReturn(new ArrayList<Message>());
        messageService.createOrderSendMessage(order);
        assertEquals(new ArrayList<Message>(), orderSendMessageBuilder.build(order));
    }

    private boolean isEqualSearchResults(SearchResults res1, SearchResults res2) {
        return (res1.getResults().equals(res2.getResults())&&res1.getPageModel().equals(res2.getPageModel()));
    }
}
