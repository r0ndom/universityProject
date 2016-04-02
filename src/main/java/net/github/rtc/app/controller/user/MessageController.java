package net.github.rtc.app.controller.user;



import net.github.rtc.app.controller.common.MenuItem;
import net.github.rtc.app.model.dto.message.MessageDto;
import net.github.rtc.app.model.dto.user.ProfileHeaderDto;
import net.github.rtc.app.model.entity.message.MessageStatus;
import net.github.rtc.app.model.entity.user.RoleType;
import net.github.rtc.app.service.message.MessageService;
import net.github.rtc.app.service.security.AuthorizedUserProvider;
import net.github.rtc.app.model.dto.SearchResults;
import net.github.rtc.app.model.dto.filter.MessageSearchFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = { "/user/message", "/expert/message" })
public class MessageController implements MenuItem {
    private static final String USER_ROOT = "portal/user/message";
    private static final String EXPERT_ROOT = "portal/expert/message";

    private static final String MESSAGE_FILTER = "messageFilter";
    private static final String MESSAGE_STATUS = "messageStatus";
    private static final String MESSAGES = "messages";

    @Autowired
    private MessageService messageService;

    @RequestMapping(method = RequestMethod.GET)
    public String messageList() {
        return getRoot() + "/message";
    }

    @RequestMapping(value = "/messageTable", method = RequestMethod.GET)
    public ModelAndView messageTable(@ModelAttribute(MESSAGE_FILTER) MessageSearchFilter searchFilter) {
        final ModelAndView mav = new ModelAndView(getRoot() + "/messageTable");
        final SearchResults<MessageDto> searchResults = messageService.searchUserMessages(searchFilter);

        mav.addObject(MESSAGES, searchResults.getResults());
        mav.addAllObjects(searchResults.getPageModel().getPageParams());
        return mav;
    }

    @RequestMapping(value = "/{code}", method = RequestMethod.GET)
    public ModelAndView getMessageDetail(@PathVariable String code,
                                         @ModelAttribute("profileHeader") ProfileHeaderDto headerDto) {
        final ModelAndView mav = new ModelAndView(getRoot() + "/messageDetails");
        mav.addObject("message", messageService.getMessage(code));
        headerDto.setUnreadMessageCount(messageService.getMessageCountByUserAndStatus(
                AuthorizedUserProvider.getAuthorizedUser(), MessageStatus.UNREAD));
        return mav;
    }

    @RequestMapping(value = "/remove/{code}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    public void removeMessage(@PathVariable String code) {
        messageService.deleteByCode(code);
    }

    @RequestMapping(value = "/unreadMessages", method = RequestMethod.GET)
    @ResponseBody
    public int getUnreadMessage() {
        return messageService.getMessageCountByUserAndStatus(AuthorizedUserProvider.getAuthorizedUser(), MessageStatus.UNREAD);
    }

    @ModelAttribute(MESSAGE_FILTER)
    public MessageSearchFilter getMessageSearchFilter() {
        final MessageSearchFilter messageSearchFilter = new MessageSearchFilter();
        messageSearchFilter.setUser(AuthorizedUserProvider.getAuthorizedUser());
        return messageSearchFilter;
    }

    private String getRoot() {
        if (AuthorizedUserProvider.getAuthorizedUser().hasRole(RoleType.ROLE_EXPERT.name())) {
            return EXPERT_ROOT;
        }
        return USER_ROOT;
    }

    @ModelAttribute(MESSAGE_STATUS)
    public MessageStatus[] getStats() {
        return MessageStatus.values();
    }

    @Override
    public String getMenuItem() {
        return MESSAGES;
    }
}
