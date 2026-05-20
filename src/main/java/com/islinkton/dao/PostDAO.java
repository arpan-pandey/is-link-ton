package com.islinkton.dao;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.islinkton.model.Post;
import com.islinkton.utils.DBconfig;

public class PostDAO {

    public boolean insertPost(Post post) throws Exception {
        Connection con = DBconfig.getDbConnection();
        String sql = "INSERT INTO posts (thread_id, user_id, content) VALUES (?, ?, ?)";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setInt(1, post.getThreadId());
        pst.setInt(2, post.getUserId());
        pst.setString(3, post.getContent());

        int rows = pst.executeUpdate();
        pst.close();
        con.close();
        return rows > 0;
    }

    public List<Post> getPostsByThread(int threadId) throws Exception {
        List<Post> list = new ArrayList<>();
        Connection con = DBconfig.getDbConnection();

        String sql = "SELECT p.*, COALESCE(u.username, 'Deleted User') AS username, u.role " +
                     "FROM posts p " +
                     "LEFT JOIN users u ON p.user_id = u.id " +
                     "WHERE p.thread_id = ? " +
                     "ORDER BY p.created_at ASC";
                     
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setInt(1, threadId);
        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            Post p = new Post();
            p.setId(rs.getInt("id"));
            p.setThreadId(rs.getInt("thread_id"));
            p.setUserId(rs.getInt("user_id"));
            p.setContent(rs.getString("content"));
            p.setCreatedAt(rs.getObject("created_at", LocalDateTime.class));
            p.setAuthorUserName(rs.getString("username"));
            p.setUserRole(rs.getString("role"));
            list.add(p);
        }

        rs.close();
        pst.close();
        con.close();
        return list;
    }

    public boolean deletePost(int id) throws Exception {
        Connection con = DBconfig.getDbConnection();
        String sql = "DELETE FROM posts WHERE id=?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setInt(1, id);
        int rows = pst.executeUpdate();
        pst.close();
        con.close();
        return rows > 0;
    }
}