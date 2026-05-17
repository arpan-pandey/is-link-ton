package com.islinkton.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.islinkton.model.User;
import com.islinkton.utils.DBconfig;

public class UserDAO {

    public void insertUser(String fullName, String username, String  email, String password, String fileName) throws Exception {

        Connection con = DBconfig.getDbConnection();

        String sql = "INSERT INTO users (full_name, username, email, password, role, profile_image, is_approved)"
                   + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement pst = con.prepareStatement(sql);

        pst.setString(1, fullName);
        pst.setString(2, username);
        pst.setString(3, email);
        pst.setString(4, password);
        pst.setString(5, "Student"); // default value
        pst.setString(6, fileName);
        pst.setBoolean(7, false);

        pst.executeUpdate();
        pst.close();
        con.close();
    }

    public User getUserByEmail(String email) throws Exception {

        User user = null;

        Connection con = DBconfig.getDbConnection();

        String sql = "SELECT * FROM users WHERE email = ?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1, email);

        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            user = new User();
            user.setId(rs.getInt("id"));
            user.setFullName(rs.getString("full_name"));
            user.setUsername(rs.getString("username"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            user.setRole(rs.getString("role"));
            user.setProfileImage(rs.getString("profile_image"));
            user.setApproved(rs.getBoolean("is_approved"));
        }

        rs.close();
        pst.close();
        con.close();

        return user;
    }
//
//    public List<User> getAllUsers() throws Exception {
//
//        List<User> users = new ArrayList<>();
//
//        Connection con = DBconfig.getDbConnection();
//
//        String sql = "SELECT * FROM users ORDER BY role, full_name";
//        PreparedStatement pst = con.prepareStatement(sql);
//
//        ResultSet rs = pst.executeQuery();
//
//        while (rs.next()) {
//            User user = new User();
//            user.setId(rs.getInt("id"));
//            user.setFullName(rs.getString("full_name"));
//            user.setUsername(rs.getString("username"));
//            user.setEmail(rs.getString("email"));
//            user.setRole(rs.getString("role"));
//            user.setProfileImage(rs.getString("profile_image"));
//            user.setApproved(rs.getBoolean("is_approved"));
//
//            users.add(user);
//        }
//
//        rs.close();
//        pst.close();
//        con.close();
//
//        return users;
//    }
//
//    public List<User> getAllApprovedUsers() throws Exception {
//
//        List<User> users = new ArrayList<>();
//
//        Connection con = DBconfig.getDbConnection();
//
//        String sql = "SELECT * FROM users WHERE is_approved = true";
//        PreparedStatement pst = con.prepareStatement(sql);
//
//        ResultSet rs = pst.executeQuery();
//
//        while (rs.next()) {
//            User user = new User();
//            user.setId(rs.getInt("id"));
//            user.setFullName(rs.getString("full_name"));
//            user.setUsername(rs.getString("username"));
//            user.setEmail(rs.getString("email"));
//            user.setRole(rs.getString("role"));
//
//            users.add(user);
//        }
//
//        rs.close();
//        pst.close();
//        con.close();
//
//        return users;
//    }
//
//    public void updateUser(User user) throws Exception {
//
//        Connection con = DBconfig.getDbConnection();
//
//        String sql = "UPDATE users SET full_name=?, username=?, email=?, role=?, profile_image=? WHERE id=?";
//        PreparedStatement pst = con.prepareStatement(sql);
//
//        pst.setString(1, user.getFullName());
//        pst.setString(2, user.getUsername());
//        pst.setString(3, user.getEmail());
//        pst.setString(4, user.getRole());
//        pst.setString(5, user.getProfileImage());
//        pst.setInt(6, user.getId());
//
//        pst.executeUpdate();
//
//        pst.close();
//        con.close();
//    }
//
//    public void deleteUser(int id) throws Exception {
//
//        Connection con = DBconfig.getDbConnection();
//
//        String sql = "DELETE FROM users WHERE id=?";
//        PreparedStatement pst = con.prepareStatement(sql);
//
//        pst.setInt(1, id);
//
//        pst.executeUpdate();
//
//        pst.close();
//        con.close();
//    }
}