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
        String sql = "INSERT INTO posts (thread_id, user_id, content, parent_post_id) VALUES (?, ?, ?, ?)";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setInt(1, post.getThreadId());
        pst.setInt(2, post.getUserId());
        pst.setString(3, post.getContent());
        
        // if parentPostId is 0, inserting NULL (main thread comment) 
        if(post.getParentPostId()==0) {
        	pst.setNull(4, java.sql.Types.INTEGER);
        }
        else {
        	pst.setInt(4, post.getParentPostId());
        }

        int rows = pst.executeUpdate();
        pst.close();
        con.close();
        return rows > 0;
    }

    public List<Post> getPostsByThread(int threadId) throws Exception {
        List<Post> allPosts = new ArrayList<>();
        Connection con = DBconfig.getDbConnection();

        String sql = "SELECT p.*, COALESCE(u.username, 'Deleted User') AS username, u.role, u.profile_image " +
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
            p.setParentPostId(rs.getInt("parent_post_id"));
            p.setContent(rs.getString("content"));
            p.setCreatedAt(rs.getObject("created_at", LocalDateTime.class));
            p.setAuthorUserName(rs.getString("username"));
            p.setUserRole(rs.getString("role"));
            p.setProfileImage(rs.getString("profile_image"));
            allPosts.add(p);
        }

        rs.close();
        pst.close();
        con.close();
        
		List<Post> rootComments = new ArrayList<>();
		        
        for (Post p : allPosts) {
            if (p.getParentPostId() == 0) {
                // Top-level main topic thread comments
                rootComments.add(p);
            } else {
                // look for its matching parent object container
                for (Post parent : allPosts) {
                    if (parent.getId() == p.getParentPostId()) {
                        parent.getReplies().add(p);
                        break;
                    }
                }
            }
        }
        
        return rootComments; // returns only main nodes, containing their time-sorted array replies
    }

    public boolean deletePost(int postId, int userId, String userRole) throws Exception {
        Connection con = DBconfig.getDbConnection();
        
        String sql;
        if ("Admin".equalsIgnoreCase(userRole)) {
            sql = "DELETE FROM posts WHERE id = ?";
        } else {
            sql = "DELETE FROM posts WHERE id = ? AND user_id = ?";
        }
        
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setInt(1, postId);
        if (!"Admin".equalsIgnoreCase(userRole)) {
            pst.setInt(2, userId);
        }
        
        int rows = pst.executeUpdate();
        pst.close();
        con.close();
        return rows > 0;
    }
}