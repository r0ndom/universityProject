package net.github.rtc.app.service.order;

import net.github.rtc.app.dao.generic.GenericDao;
import net.github.rtc.app.dao.order.UserCourseOrderDao;
import net.github.rtc.app.model.dto.SearchResults;
import net.github.rtc.app.model.dto.filter.OrderSearchFilter;
import net.github.rtc.app.model.dto.user.ExpertOrderDto;
import net.github.rtc.app.model.entity.course.CourseType;
import net.github.rtc.app.model.entity.order.UserCourseOrder;
import net.github.rtc.app.model.entity.order.UserRequestStatus;
import net.github.rtc.app.service.builder.ExpertOrderDtoBuilder;
import net.github.rtc.app.service.builder.SearchResultsBuilder;
import net.github.rtc.app.service.builder.SearchResultsMapper;
import net.github.rtc.app.service.course.CourseService;
import net.github.rtc.app.service.date.DateService;
import net.github.rtc.app.service.generic.AbstractGenericServiceImpl;
import net.github.rtc.app.service.message.MessageService;
import net.github.rtc.app.service.security.AuthorizedUserProvider;
import net.github.rtc.app.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserCourseOrderServiceImpl extends AbstractGenericServiceImpl<UserCourseOrder> implements
        UserCourseOrderService {

    private static final Logger LOG = LoggerFactory.getLogger(UserCourseOrderServiceImpl.class.getName());
    @Autowired
    private UserCourseOrderDao userCourseOrderDao;
    @Autowired
    private DateService dateService;
    @Autowired
    private UserService userService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private MessageService messageService;

    @Override
    public SearchResults<ExpertOrderDto> searchExpertOrders(OrderSearchFilter searchFilter) {
        final SearchResultsBuilder<UserCourseOrder, ExpertOrderDto> resultsBuilder = new SearchResultsBuilder<>();
        return resultsBuilder.setSearchResultsToTransform(search(searchFilter)).
                setSearchResultsMapper(getOrderToExpertOrderMapper()).build();
    }

    @Override
    public int getAcceptedOrdersCount(String courseCode) {
        return userCourseOrderDao.getAcceptedOrdersCourseCount(courseCode);
    }

    @Override
    @Transactional
    @Nonnull
    public UserCourseOrder acceptOrder(String orderCode) {
        final UserCourseOrder order = findByCode(orderCode);
        changeOrderStatus(order, UserRequestStatus.ACCEPTED);
        messageService.createOrderResponseMessage(order);
        return userCourseOrderDao.update(order);
    }

    @Override
    @Transactional
    @Nonnull
    public UserCourseOrder rejectOrder(String orderCode, String reason) {
        final UserCourseOrder order = findByCode(orderCode);
        order.setRejectReason(reason);
        changeOrderStatus(order, UserRequestStatus.REJECTED);
        messageService.createOrderResponseMessage(order);
        return userCourseOrderDao.update(order);
    }

    /**
     * Set new status for order and response date
     *
     * @param order  order that needs to be updated
     * @param status new status of the order
     * @return updated order
     */
    @Nonnull
    private UserCourseOrder changeOrderStatus(final UserCourseOrder order, UserRequestStatus status) {
        order.setResponseDate(dateService.getCurrentDate());
        order.setStatus(status);
        return order;
    }


    @Override
    @Transactional
    @Nonnull
    public UserCourseOrder create(String courseCode, CourseType position) {
        final String userCode = AuthorizedUserProvider.getAuthorizedUser().getCode();

        final UserCourseOrder userCourseOrder = new UserCourseOrder();
        userCourseOrder.setCourseCode(courseCode);
        userCourseOrder.setPosition(position);
        userCourseOrder.setUserCode(userCode);
        userCourseOrder.setRequestDate(dateService.getCurrentDate());

        messageService.createOrderSendMessage(userCourseOrder);
        return super.create(userCourseOrder);
    }

    @Override
    @Transactional
    public UserCourseOrder findByUserCodeAndCourseCode(String userCode, String courseCode) {
        return userCourseOrderDao.getUserCourseOrder(userCode, courseCode);
    }

    @Override
    @Transactional
    public List findLastByUserCode(String userCode) {
        return userCourseOrderDao.getLastUserCourseOrders(userCode);
    }

    @Override
    protected GenericDao<UserCourseOrder> getDao() {
        return userCourseOrderDao;
    }

    /**
     * Returns an object that map list of orders to orderDTOs list
     *
     * @return anonymous class mapper
     */
    private SearchResultsMapper<UserCourseOrder, ExpertOrderDto> getOrderToExpertOrderMapper() {
        return new SearchResultsMapper<UserCourseOrder, ExpertOrderDto>() {
            @Override
            public List<ExpertOrderDto> map(List<UserCourseOrder> searchResults) {
                final List<ExpertOrderDto> outputResults = new ArrayList<>();
                for (UserCourseOrder order : searchResults) {
                    final ExpertOrderDtoBuilder dtoBuilder = new ExpertOrderDtoBuilder();
                    outputResults.add(dtoBuilder.buildOrderFields(order).
                            buildCourseFields(courseService.findByCode(order.getCourseCode())).
                            buildAcceptedOrders(getAcceptedOrdersCount(order.getCourseCode())).
                            buildUserFields(userService.findByCode(order.getUserCode())).get());
                }
                return outputResults;
            }
        };
    }
}
