package com.islinkton.dao;

import com.islinkton.model.Thread;
import com.islinkton.utils.DBconfig;
import java.sql.*;
import java.time.LocalDateTime;
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
        
        String sql = " SELECT t.*, u.username as author_username, c.name as category_name " 
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
            t.setAuthorUserName(rs.getString("author_username"));
            t.setCategoryName(rs.getString("category_name"));
            t.setApproved(rs.getBoolean("is_approved"));
            threads.add(t);
        }

        rs.close();
        pst.close();
        con.close();
        return threads;
    }
    
    public List<Thread> getRecentThreads() throws Exception {
        List<Thread> threads = new ArrayList<>();
        Connection con = DBconfig.getDbConnection();
        
        String sql = "SELECT t.id, t.title, t.content, t.created_at, " +
	                "c.name AS category_name, " +
	                "COALESCE(u.username, 'Deleted User') AS author_username, " +
	                "(SELECT COUNT(*) FROM thread_votes tv WHERE tv.thread_id = t.id) AS vote_count, " +
	                "(SELECT COUNT(*) FROM posts p WHERE p.thread_id = t.id) AS comment_count " +
	                "FROM threads t " +
	                "LEFT JOIN categories c ON t.category_id = c.id " +
	                "LEFT JOIN users u ON t.author_id = u.id " +
	                "WHERE t.is_approved = TRUE " +
	                "ORDER BY t.created_at DESC LIMIT 2";
        
        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            Thread t = new Thread();
            t.setId(rs.getInt("id"));
            t.setTitle(rs.getString("title"));
            t.setContent(rs.getString("content"));
            t.setCreatedAt(rs.getObject("created_at", LocalDateTime.class)); // reading the SQL timestamp value directly into a LocalDateTime object
            t.setCategoryName(rs.getString("category_name"));
            t.setAuthorUserName(rs.getString("author_username"));
            t.setVoteCount(rs.getInt("vote_count"));
            t.setCommentCount(rs.getInt("comment_count"));
            threads.add(t);
        }

        rs.close();
        pst.close();
        con.close();
        return threads;
    }

    public Thread getThreadById(int id) throws Exception {
        Connection con = DBconfig.getDbConnection();
        String sql = " SELECT t.*, u.username as author_username, c.name as category_name "
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
            thread.setAuthorUserName(rs.getString("author_username"));
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