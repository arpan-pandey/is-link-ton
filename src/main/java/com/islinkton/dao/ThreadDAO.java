package com.islinkton.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.islinkton.model.Thread;
import com.islinkton.utils.DBconfig;

public class ThreadDAO {

    public void insertThread(Thread thread) throws Exception {

        Connection con = DBconfig.getDbConnection();

        String sql = "INSERT INTO threads (title, content, category_id, author_id, is_approved) "
                   + "VALUES (?, ?, ?, ?, ?)";

        PreparedStatement pst = con.prepareStatement(sql);

        pst.setString(1, thread.getTitle());
        pst.setString(2, thread.getContent());
        pst.setInt(3, thread.getCategoryId());
        pst.setInt(4, thread.getAuthorId());
        pst.setBoolean(5, thread.isApproved());

        pst.executeUpdate();

        pst.close();
        con.close();
    }

    public List<Thread> getAllThreads() throws Exception {

        List<Thread> list = new ArrayList<>();

        Connection con = DBconfig.getDbConnection();

        String sql = "SELECT * FROM threads ORDER BY id DESC";
        PreparedStatement pst = con.prepareStatement(sql);

        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            Thread t = new Thread();
            t.setId(rs.getInt("id"));
            t.setTitle(rs.getString("title"));
            t.setContent(rs.getString("content"));
            t.setCategoryId(rs.getInt("category_id"));
            t.setAuthorId(rs.getInt("author_id"));
            t.setApproved(rs.getBoolean("is_approved"));

            list.add(t);
        }

        rs.close();
        pst.close();
        con.close();

        return list;
    }

    public Thread getThreadById(int id) throws Exception {

        Thread t = null;

        Connection con = DBconfig.getDbConnection();

        String sql = "SELECT * FROM threads WHERE id=?";
        PreparedStatement pst = con.prepareStatement(sql);

        pst.setInt(1, id);

        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            t = new Thread();
            t.setId(rs.getInt("id"));
            t.setTitle(rs.getString("title"));
            t.setContent(rs.getString("content"));
            t.setCategoryId(rs.getInt("category_id"));
            t.setAuthorId(rs.getInt("author_id"));
            t.setApproved(rs.getBoolean("is_approved"));
        }

        rs.close();
        pst.close();
        con.close();

        return t;
    }

    public List<Thread> getThreadsByCategory(int categoryId) throws Exception {

        List<Thread> list = new ArrayList<>();

        Connection con = DBconfig.getDbConnection();

        String sql = "SELECT * FROM threads WHERE category_id=?";
        PreparedStatement pst = con.prepareStatement(sql);

        pst.setInt(1, categoryId);

        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            Thread t = new Thread();
            t.setId(rs.getInt("id"));
            t.setTitle(rs.getString("title"));
            t.setContent(rs.getString("content"));
            t.setAuthorId(rs.getInt("author_id"));

            list.add(t);
        }

        rs.close();
        pst.close();
        con.close();

        return list;
    }

    public void updateThread(Thread thread) throws Exception {

        Connection con = DBconfig.getDbConnection();

        String sql = "UPDATE threads SET title=?, content=?, category_id=? WHERE id=?";
        PreparedStatement pst = con.prepareStatement(sql);

        pst.setString(1, thread.getTitle());
        pst.setString(2, thread.getContent());
        pst.setInt(3, thread.getCategoryId());
        pst.setInt(4, thread.getId());

        pst.executeUpdate();

        pst.close();
        con.close();
    }

    public void deleteThread(int id) throws Exception {

        Connection con = DBconfig.getDbConnection();

        String sql = "DELETE FROM threads WHERE id=?";
        PreparedStatement pst = con.prepareStatement(sql);

        pst.setInt(1, id);

        pst.executeUpdate();

        pst.close();
        con.close();
    }

    public void approveThread(int id) throws Exception {

        Connection con = DBconfig.getDbConnection();

        String sql = "UPDATE threads SET is_approved=true WHERE id=?";
        PreparedStatement pst = con.prepareStatement(sql);

        pst.setInt(1, id);

        pst.executeUpdate();

        pst.close();
        con.close();
    }
}