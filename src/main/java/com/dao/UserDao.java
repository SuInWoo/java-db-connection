package com.dao;

import com.domain.User;
import org.springframework.dao.EmptyResultDataAccessException;

import java.sql.*;
import java.util.Map;

public class UserDao {
    private ConnectionMaker connectionMaker;

    public UserDao() {
        connectionMaker = new AwsConnectionMaker();
    }

    public UserDao(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

    public void add(User user) {
        Connection c;
        try {
            c = connectionMaker.getConnection();

            PreparedStatement pstmt = c.prepareStatement("INSERT INTO users(id, name, password) VALUES(?,?,?);");
            pstmt.setString(1, user.getId());
            pstmt.setString(2, user.getName());
            pstmt.setString(3, user.getPassword());

            pstmt.executeUpdate();

            pstmt.close();
            c.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User findById(String id) throws SQLException {
        Connection c;
        try {
            c = connectionMaker.getConnection();

            PreparedStatement pstmt = c.prepareStatement("SELECT * FROM `users` WHERE id = ?");
            pstmt.setString(1, id);
            ResultSet rs = pstmt.executeQuery();
            User user = null;

            if (rs.next()) {
                user = new User();
                user.setId(rs.getString("id"));
                user.setName(rs.getString("name"));
                user.setPassword(rs.getString("password"));
            }

            rs.close();
            pstmt.close();
            c.close();

            if (user == null) throw new NullPointerException();

            return user;

        }catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void deleteAll() throws SQLException {
        Connection conn = connectionMaker.getConnection();

        PreparedStatement ps = conn.prepareStatement("delete from users");

        ps.executeUpdate();
        ps.close();
        conn.close();
    }

    public int getCount() throws SQLException {
        Connection conn = connectionMaker.getConnection();

        PreparedStatement ps = conn.prepareStatement("select count(*) from users");

        ResultSet rs = ps.executeQuery();
        rs.next();
        int count = rs.getInt(1);

        rs.close();
        ps.close();
        conn.close();

        return count;
    }
}