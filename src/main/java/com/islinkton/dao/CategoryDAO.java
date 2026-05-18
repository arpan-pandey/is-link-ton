package com.islinkton.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.islinkton.model.Category;
import com.islinkton.utils.DBconfig;

public class CategoryDAO {

    public boolean insertCategory(Category category) throws Exception {

        Connection con = DBconfig.getDbConnection();

        String sql = "INSERT INTO categories (name, description) VALUES (?, ?)";
        PreparedStatement pst = con.prepareStatement(sql);

        pst.setString(1, category.getName());
        pst.setString(2, category.getDescription());

        int rows = pst.executeUpdate();

        pst.close();
        con.close();
        
        return rows > 0;
    }

    public List<Category> getAllCategories() throws Exception {

        List<Category> list = new ArrayList<>();

        Connection con = DBconfig.getDbConnection();

        String sql = "SELECT * FROM categories";
        PreparedStatement pst = con.prepareStatement(sql);

        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            Category c = new Category();
            c.setId(rs.getInt("id"));
            c.setName(rs.getString("name"));
            c.setDescription(rs.getString("description"));

            list.add(c);
        }

        rs.close();
        pst.close();
        con.close();

        return list;
    }

    public Category getCategoryById(int id) throws Exception {

        Category c = null;

        Connection con = DBconfig.getDbConnection();

        String sql = "SELECT * FROM categories WHERE id=?";
        PreparedStatement pst = con.prepareStatement(sql);

        pst.setInt(1, id);

        ResultSet rs = pst.executeQuery();

        if (rs.next()) {
            c = new Category();
            c.setId(rs.getInt("id"));
            c.setName(rs.getString("name"));
            c.setDescription(rs.getString("description"));
        }

        rs.close();
        pst.close();
        con.close();

        return c;
    }

    public boolean updateCategory(Category category) throws Exception {

        Connection con = DBconfig.getDbConnection();

        String sql = "UPDATE categories SET name=?, description=? WHERE id=?";
        PreparedStatement pst = con.prepareStatement(sql);

        pst.setString(1, category.getName());
        pst.setString(2, category.getDescription());
        pst.setInt(3, category.getId());

        int rows = pst.executeUpdate();

        pst.close();
        con.close();
        
        return rows > 0;
    }

    public boolean deleteCategory(int id) throws Exception {

        Connection con = DBconfig.getDbConnection();

        String sql = "DELETE FROM categories WHERE id=?";
        PreparedStatement pst = con.prepareStatement(sql);

        pst.setInt(1, id);

        int rows = pst.executeUpdate();

        pst.close();
        con.close();
        
        return rows > 0;
    }
}