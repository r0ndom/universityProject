/*package net.github.rtc.app.dao.impl;

//todo
import net.github.rtc.app.dao.*;
import net.github.rtc.app.model.AbstractPersistenceObject;
import net.github.rtc.app.model.user.UserCourseOrder;
import net.github.rtc.app.model.user.UserRequestStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

import static junit.framework.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:mvc-dao-test.xml")
public class UserCourseOrderDaoImplTest extends AbstractGenericDaoTest<UserCourseOrder> {
    @Autowired
    private UserCourseOrderDao userCourseOrderDao;

    @Override
    protected GenericDao<UserCourseOrder> getGenericDao() {
        return userCourseOrderDao;
    }

    @Override
    protected ModelBuilder getModelBuilder() {
        return daoTestContext.getModelBuilder(UserCourseOrder.class);
    }

    @Override
    protected EqualityChecker getEqualityChecker() {
        return daoTestContext.getEqualityChecker(UserCourseOrder.class);
    }

    @Test
    @Transactional
    public void testGetUserOrder(){
        String userCode = "X";
        UserCourseOrder order = new UserCourseOrder();
        order.setUserCode(userCode);

        UserCourseOrder order1 = new UserCourseOrder();
        order1.setUserCode("Y");

        UserCourseOrder order2 = new UserCourseOrder();
        order2.setUserCode("Z");

        getGenericDao().create(order);
        getGenericDao().create(order1);
        getGenericDao().create(order2);

        assertEquals(userCode, userCourseOrderDao.getUserOrder(userCode).getUserCode());
    }

    @Test
    @Transactional
    public void getOrderByStatus(){
        UserRequestStatus status = UserRequestStatus.PENDING;
        UserCourseOrder order = new UserCourseOrder();
        order.setStatus(status);

        UserCourseOrder order1 = new UserCourseOrder();
        order1.setStatus(UserRequestStatus.ACCEPTED);

        UserCourseOrder order2 = new UserCourseOrder();
        order2.setStatus(UserRequestStatus.ACCEPTED);

        getGenericDao().create(order);
        getGenericDao().create(order1);
        getGenericDao().create(order2);

        List<UserCourseOrder> orders = userCourseOrderDao.getOrderByStatus(UserRequestStatus.ACCEPTED);
        assertEquals(2, orders.size());
        for(UserCourseOrder courseOrder : orders){
            assertEquals(UserRequestStatus.ACCEPTED, courseOrder.getStatus());
        }
    }
}*/
