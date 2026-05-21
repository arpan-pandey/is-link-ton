package com.islinkton.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.islinkton.model.User;
import com.islinkton.utils.DBconfig;

public class UserDAO {

    public boolean insertUser(String fullName, String username, String email, String password, String fileName) throws Exception {
        Connection con = DBconfig.getDbConnection();
        String sql = "INSERT INTO users (full_name, username, email, password, role, profile_image, is_approved) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1, fullName);
        pst.setString(2, username);
        pst.setString(3, email);
        pst.setString(4, password);
        pst.setString(5, "Student"); 
        pst.setString(6, fileName);
        pst.setBoolean(7, false);
        int rows = pst.executeUpdate();
        pst.close();
        con.close();
        return rows > 0;
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
            user.setIsActive(rs.getInt("is_active"));
        }
        rs.close();
        pst.close();
        con.close();
        return user;
    }

    public List<User> getPendingUsers() throws Exception {
        List<User> users = new ArrayList<>();
        Connection con = DBconfig.getDbConnection();
        String sql = "SELECT * FROM users WHERE is_approved = FALSE ORDER BY created_at DESC";
        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setFullName(rs.getString("full_name"));
            user.setUsername(rs.getString("username"));
            user.setEmail(rs.getString("email"));
            user.setRole(rs.getString("role"));
            user.setProfileImage(rs.getString("profile_image"));
            user.setApproved(rs.getBoolean("is_approved"));
            users.add(user);
        }
        rs.close();
        pst.close();
        con.close();
        return users;
    }
   
    public List<User> getAllApprovedUsers() throws Exception {
        List<User> users = new ArrayList<>();
        Connection con = DBconfig.getDbConnection();
        String sql = "SELECT * FROM users WHERE is_approved = TRUE ORDER BY created_at DESC";
        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setFullName(rs.getString("full_name"));
            user.setUsername(rs.getString("username"));
            user.setEmail(rs.getString("email"));
            user.setRole(rs.getString("role"));
            user.setProfileImage(rs.getString("profile_image"));
            user.setApproved(rs.getBoolean("is_approved"));
            users.add(user);
        }
        rs.close();
        pst.close();
        con.close();
        return users;
    }
   
    public boolean approveUser(int userId) throws Exception {
        Connection con = DBconfig.getDbConnection();
        String sql = "UPDATE users SET is_approved = TRUE WHERE id = ?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setInt(1, userId);
        int rows = pst.executeUpdate();
        pst.close();
        con.close();
        return rows > 0;
    }

    public boolean deleteUser(int userId) throws Exception {
        Connection con = DBconfig.getDbConnection();
        String sql = "DELETE FROM users WHERE id = ?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setInt(1, userId);
        int rows = pst.executeUpdate();
        pst.close();
        con.close();
        return rows > 0;
    }
    
    public int deactivateUser(int userId) throws Exception {
        Connection con = DBconfig.getDbConnection();
        String sql = "UPDATE users SET is_active = 0 WHERE id = ?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setInt(1, userId);
        int rows = pst.executeUpdate();
        pst.close();
        con.close();
        return rows;
    }

    public int updateUserProfile(int userId, String fullName, String email) throws Exception {
        Connection con = null;
        PreparedStatement pst = null;
        try {
            con = DBconfig.getDbConnection();
            String sql = "UPDATE users SET full_name = ?, email = ? WHERE id = ?";
            pst = con.prepareStatement(sql);
            pst.setString(1, fullName);
            pst.setString(2, email);
            pst.setInt(3, userId);
            return pst.executeUpdate();
        } finally {
            if (pst != null) pst.close();
            if (con != null) con.close();
        }
    }

    public int updateProfileImage(int userId, String filePath) throws Exception {
        Connection con = null;
        PreparedStatement pst = null;
        try {
            con = DBconfig.getDbConnection();
            String sql = "UPDATE users SET profile_image = ? WHERE id = ?";
            pst = con.prepareStatement(sql);
            pst.setString(1, filePath);        
            pst.setInt(2, userId);
            return pst.executeUpdate();
        } finally {
            if (pst != null) pst.close();
            if (con != null) con.close();
        }
    }

    public int updatePassword(int userId, String hashedPassword) throws Exception {
        Connection con = null;
        PreparedStatement pst = null;
        try {
            con = DBconfig.getDbConnection();
            String sql = "UPDATE users SET password = ? WHERE id = ?";
            pst = con.prepareStatement(sql);
            pst.setString(1, hashedPassword);
            pst.setInt(2, userId);
            return pst.executeUpdate();
        } finally {
            if (pst != null) pst.close();
            if (con != null) con.close();
        }
    }
}