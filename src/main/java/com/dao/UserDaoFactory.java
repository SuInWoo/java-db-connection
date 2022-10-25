package com.dao;

import org.springframework.context.annotation.Bean;

public class UserDaoFactory {
    @Bean
    public UserDao awsUserDao() {
        UserDao userDao = new UserDao(new AwsConnectionMaker());
        return userDao;
    }

    @Bean
    public UserDao localUserDao() {
        UserDao userDao = new UserDao(new LocalConnectionMaker());
        return userDao;
    }
}
