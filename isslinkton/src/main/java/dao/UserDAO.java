package com.islinkton.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.islinkton.model.UserModel;
import com.islinkton.utils.DBconfig;

public class UserDAO {

    public void register(UserModel user) throws Exception {

        Connection conn = DBconfig.getDbConnection();

        String sql = "INSERT INTO users(name,email,password,role) VALUES(?,?,?,?)";

        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, user.getName());
        pst.setString(2, user.getEmail());
        pst.setString(3, user.getPassword());
        pst.setString(4, "student");

        pst.executeUpdate();

        pst.close();
        conn.close();
    }

    public UserModel login(String email) throws Exception {

        Connection conn = DBconfig.getDbConnection();

        String sql = "SELECT * FROM users WHERE email=?";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, email);

        ResultSet rs = pst.executeQuery();

        UserModel user = null;

        if (rs.next()) {
            user = new UserModel();
            user.setId(rs.getInt("id"));
            user.setName(rs.getString("name"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            user.setRole(rs.getString("role"));
        }

        rs.close();
        pst.close();
        conn.close();

        return user;
    }

    public List<UserModel> getStudents() throws Exception {

        List<UserModel> list = new ArrayList<>();

        Connection conn = DBconfig.getDbConnection();

        String sql = "SELECT * FROM users WHERE role='student'";
        PreparedStatement pst = conn.prepareStatement(sql);

        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            UserModel u = new UserModel();
            u.setId(rs.getInt("id"));
            u.setName(rs.getString("name"));
            u.setEmail(rs.getString("email"));

            list.add(u);
        }

        return list;
    }
}