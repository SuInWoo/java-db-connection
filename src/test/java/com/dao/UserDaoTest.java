package com.dao;

import com.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = UserDaoFactory.class)
class UserDaoTest {

    @Autowired
    ApplicationContext context;

    private UserDao userDao;
    private User user1, user2, user3;

    @BeforeEach
    void setUp(){
        this.userDao = context.getBean("awsUserDao", UserDao.class);
        this.user1 = new User("1", "SuIn", "1123");
        this.user2 = new User("2", "Min", "1234");
        this.user3 = new User("3", "Woo", "4312");
    }

    @Test
    @DisplayName("insert and select Test")
    void addAndGet() throws SQLException {
        userDao.deleteAll();

        userDao.add(user1);
        assertEquals(1, userDao.getCount());
        User user = userDao.findById(user1.getId());

        assertEquals(user1.getName(), user.getName());
        assertEquals(user1.getPassword(), user.getPassword());

        userDao.deleteAll();
    }

    @Test
    @DisplayName("count Test")
    void count() throws SQLException {
        userDao.deleteAll();

        userDao.add(user1);
        assertEquals(1, userDao.getCount());
        userDao.add(user2);
        assertEquals(2, userDao.getCount());
        userDao.add(user3);
        assertEquals(3, userDao.getCount());

        userDao.deleteAll();
    }

    @Test
    @DisplayName("user null Test")
    void userNull(){
        assertThrows(NullPointerException.class, () -> {
            userDao.deleteAll();
            userDao.findById("30");
        });
    }
}