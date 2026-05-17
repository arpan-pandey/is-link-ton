package com.islinkton.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.islinkton.model.UserModel;
import com.islinkton.utils.DBconfig;

public class UserDAO {

    public void insertUser(UserModel user) throws Exception {

        Connection conn = DBconfig.getDbConnection();

        String sql = "INSERT INTO users(name,email,password,role) VALUES(?,?,?,?)";

        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, user.getName());
        pst.setString(2, user.getEmail());
        pst.setString(3, user.getPassword());
        pst.setString(4, user.getRole());

        pst.executeUpdate();

        pst.close();
        conn.close();
    }
    
    public void deleteUser(UserModel user) throws Exception {

        Connection conn = DBconfig.getDbConnection();

        String sql = "DELETE FROM users WHERE email=?";

        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, user.getEmail());

        pst.executeUpdate();

        pst.close();
        conn.close();
    }
    
    public void updateUser(UserModel user) throws Exception {

        Connection conn = DBconfig.getDbConnection();

        String sql = "UPDATE users SET name=?, email=?, role=? WHERE userID=?";

        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, user.getName());
        pst.setString(2, user.getEmail());
        pst.setString(3, user.getRole());
        pst.setInt(4, user.getId());

        pst.executeUpdate();

        pst.close();
        conn.close();
    }

    public UserModel getUserByEmail(String email) throws Exception {

        Connection conn = DBconfig.getDbConnection();

        String sql = "SELECT * FROM users WHERE email=?";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, email);

        ResultSet rs = pst.executeQuery();

        UserModel user = null;

        if (rs.next()) {
            user = new UserModel();
            user.setId(rs.getInt("userID"));
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

    public List<UserModel> getUsers() throws Exception {
        List<UserModel> list = new ArrayList<>();
        Connection conn = DBconfig.getDbConnection();
        String sql = "SELECT * FROM users ORDER BY role ASC, name";
        PreparedStatement pst = conn.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();
        
        while (rs.next()) {
            UserModel u = new UserModel();
            u.setId(rs.getInt("userID"));
            u.setName(rs.getString("name"));
            u.setEmail(rs.getString("email"));
            u.setRole(rs.getString("role"));
            list.add(u);
        }
        
        rs.close();
        pst.close();
        conn.close();
        return list;
    }
}