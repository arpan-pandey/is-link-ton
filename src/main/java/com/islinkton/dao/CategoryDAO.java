package com.islinkton.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.islinkton.model.Category;
import com.islinkton.utils.DBconfig;

public class CategoryDAO {
	
	// categories are hardcoded into the database so CRUD isn't needed
    
    public List<Category> getCategoriesByType(String type) throws Exception {
        List<Category> categories = new ArrayList<>();
        Connection con = DBconfig.getDbConnection();
        
        // filtering categories strictly by type
        String sql = "SELECT id, name, type FROM categories WHERE type = ? ORDER BY name ASC";
        
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setString(1, type);
        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            Category c = new Category();
            c.setId(rs.getInt("id"));
            c.setName(rs.getString("name"));
            c.setType(rs.getString("type"));
            categories.add(c);
        }

        rs.close();
        pst.close();
        con.close();
        return categories;
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
            c.setType(rs.getString("type"));
        }

        rs.close();
        pst.close();
        con.close();

        return c;
    }
}