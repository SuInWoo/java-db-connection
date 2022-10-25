package com.dao;

import com.domain.User;

import java.sql.*;
import java.util.Map;

public class UserDao {
    private ConnectionMaker connectionMaker;

    public UserDao() {
        connectionMaker = new ConnectionMaker();
    }

    public UserDao(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

    public void add(User user) {
        Connection c;
        try {
            // DB접속
            c = connectionMaker.getConnection();

            // Query문 작성
            PreparedStatement pstmt = c.prepareStatement("INSERT INTO users(id, name, password) VALUES(?,?,?);");
            pstmt.setString(1, user.getId());
            pstmt.setString(2, user.getName());
            pstmt.setString(3, user.getPassword());

            // Query문 실행
            pstmt.executeUpdate();

            pstmt.close();
            c.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User findById(String id) {
        Map<String, String> env = System.getenv();
        Connection c;
        try {
            // DB접속
            c = connectionMaker.getConnection();

            // Query문 작성
            PreparedStatement pstmt = c.prepareStatement("SELECT * FROM users WHERE id = ?");
            pstmt.setString(1, id);

            // Query문 실행
            ResultSet rs = pstmt.executeQuery();
            rs.next();
            User user = new User(rs.getString("id"), rs.getString("name"),
                    rs.getString("password"));

            rs.close();
            pstmt.close();
            c.close();

            return user;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //테스트를 위한 main
    public static void main(String[] args) {
        UserDao userDao = new UserDao();

        User user = new User();
        user.setId("9");
        user.setName("suin");
        user.setPassword("1234");

        userDao.add(user);

        User user1 = userDao.findById(user.getId());
        System.out.println(user1.getName());
    }
}