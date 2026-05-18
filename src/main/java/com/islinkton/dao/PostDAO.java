package com.islinkton.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.islinkton.model.Post;
import com.islinkton.utils.DBconfig;

public class PostDAO {

    public void insertPost(Post post) throws Exception {

        Connection con = DBconfig.getDbConnection();

        String sql = "INSERT INTO posts (thread_id, user_id, content) VALUES (?, ?, ?)";
        PreparedStatement pst = con.prepareStatement(sql);

        pst.setInt(1, post.getThreadId());
        pst.setInt(2, post.getUserId());
        pst.setString(3, post.getContent());

        pst.executeUpdate();

        pst.close();
        con.close();
    }

    public List<Post> getPostsByThread(int threadId) throws Exception {

        List<Post> list = new ArrayList<>();

        Connection con = DBconfig.getDbConnection();

        String sql = "SELECT * FROM posts WHERE thread_id=?";
        PreparedStatement pst = con.prepareStatement(sql);

        pst.setInt(1, threadId);

        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            Post p = new Post();
            p.setId(rs.getInt("id"));
            p.setThreadId(rs.getInt("thread_id"));
            p.setUserId(rs.getInt("user_id"));
            p.setContent(rs.getString("content"));

            list.add(p);
        }

        rs.close();
        pst.close();
        con.close();

        return list;
    }

    public void deletePost(int id) throws Exception {

        Connection con = DBconfig.getDbConnection();

        String sql = "DELETE FROM posts WHERE id=?";
        PreparedStatement pst = con.prepareStatement(sql);

        pst.setInt(1, id);

        pst.executeUpdate();

        pst.close();
        con.close();
    }
}