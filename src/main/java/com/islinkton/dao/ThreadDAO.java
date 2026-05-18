package com.islinkton.dao;

import com.islinkton.model.Thread;
import com.islinkton.utils.DBconfig;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ThreadDAO {

    public boolean createThread(Thread thread) throws Exception {
        Connection con = DBconfig.getDbConnection();
        String sql = "INSERT INTO threads (title, content, category_id, author_id) VALUES (?, ?, ?, ?)";
        PreparedStatement pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        
        pst.setString(1, thread.getTitle());
        pst.setString(2, thread.getContent());
        pst.setInt(3, thread.getCategoryId());
        pst.setInt(4, thread.getAuthorId());

        int rows = pst.executeUpdate();
        pst.close();
        con.close();
        return rows > 0;
    }

    public List<Thread> getAllApprovedThreads() throws Exception {
        List<Thread> threads = new ArrayList<>();
        Connection con = DBconfig.getDbConnection();
        
        String sql = " SELECT t.*, u.full_name as author_name, c.name as category_name " 
            + "FROM threads t "
            + "JOIN users u ON t.author_id = u.id " 
            + "LEFT JOIN categories c ON t.category_id = c.id " 
            + "WHERE t.is_approved = TRUE "
            + "ORDER BY t.created_at DESC ";
        
        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            Thread t = new Thread();
            t.setId(rs.getInt("id"));
            t.setTitle(rs.getString("title"));
            t.setContent(rs.getString("content"));
            t.setCategoryId(rs.getInt("category_id"));
            t.setAuthorId(rs.getInt("author_id"));
            t.setAuthorName(rs.getString("author_name"));
            t.setCategoryName(rs.getString("category_name"));
            t.setApproved(rs.getBoolean("is_approved"));
            threads.add(t);
        }

        rs.close();
        pst.close();
        con.close();
        return threads;
    }

    public Thread getThreadById(int id) throws Exception {
        Connection con = DBconfig.getDbConnection();
        String sql = " SELECT t.*, u.full_name as author_name, c.name as category_name "
            + "FROM threads t "
            + "JOIN users u ON t.author_id = u.id "
            + "LEFT JOIN categories c ON t.category_id = c.id " 
            + "WHERE t.id = ?";
        
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();

        Thread thread = null;
        if (rs.next()) {
            thread = new Thread();
            thread.setId(rs.getInt("id"));
            thread.setTitle(rs.getString("title"));
            thread.setContent(rs.getString("content"));
            thread.setCategoryId(rs.getInt("category_id"));
            thread.setAuthorId(rs.getInt("author_id"));
            thread.setAuthorName(rs.getString("author_name"));
            thread.setCategoryName(rs.getString("category_name"));
            thread.setApproved(rs.getBoolean("is_approved"));
        }

        rs.close();
        pst.close();
        con.close();
        return thread;
    }

    public boolean approveThread(int threadId) throws Exception {
        Connection con = DBconfig.getDbConnection();
        String sql = "UPDATE threads SET is_approved = TRUE WHERE id = ?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setInt(1, threadId);
        int rows = pst.executeUpdate();
        pst.close();
        con.close();
        return rows > 0;
    }
    
    public boolean deleteThread(int threadId) throws Exception {
        Connection con = DBconfig.getDbConnection();
        String sql = "DELETE FROM threads WHERE id = ?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setInt(1, threadId);
        int rows = pst.executeUpdate();
        pst.close();
        con.close();
        return rows > 0;
    }
}