package com.islinkton.dao;

import com.islinkton.model.Resource;
import com.islinkton.utils.DBconfig;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ResourceDAO {

    public boolean uploadResource(Resource resource) throws Exception {
        Connection con = DBconfig.getDbConnection();
        String sql = "INSERT INTO resources (title, description, file_path, file_type, uploaded_by, category_id) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";
        
        PreparedStatement pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        
        pst.setString(1, resource.getTitle());
        pst.setString(2, resource.getDescription());
        pst.setString(3, resource.getFilePath());
        pst.setString(4, resource.getFileType());
        pst.setInt(5, resource.getUploadedBy());
        pst.setInt(6, resource.getCategoryId());

        int rows = pst.executeUpdate();
        
        if (rows > 0) {
            ResultSet rs = pst.getGeneratedKeys();
            if (rs.next()) {
                resource.setId(rs.getInt(1));
            }
            rs.close();
        }
        
        pst.close();
        con.close();
        return rows > 0;
    }

    public List<Resource> getAllResources() throws Exception {
        List<Resource> resources = new ArrayList<>();
        Connection con = DBconfig.getDbConnection();
        
        String sql = "SELECT r.*, u.full_name as author_username, c.name as category_name "
            + "FROM resources r "
            + "JOIN users u ON r.uploaded_by = u.id "
            + "LEFT JOIN categories c ON r.category_id = c.id " 
            + "ORDER BY r.created_at DESC";
        
        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            Resource r = new Resource();
            r.setId(rs.getInt("id"));
            r.setTitle(rs.getString("title"));
            r.setDescription(rs.getString("description"));
            r.setFilePath(rs.getString("file_path"));
            r.setFileType(rs.getString("file_type"));
            r.setUploadedBy(rs.getInt("uploaded_by"));
            r.setCategoryId(rs.getInt("category_id"));
            r.setCategoryName(rs.getString("category_name"));
            r.setAuthorUserName(rs.getString("author_username"));
            r.setCreatedAt(rs.getObject("created_at", LocalDateTime.class));
            resources.add(r);
        }

        rs.close();
        pst.close();
        con.close();
        return resources;
    }

    public List<Resource> getRecentResources() throws Exception {
        List<Resource> resources = new ArrayList<>();
        Connection con = DBconfig.getDbConnection();
        
        String sql = "SELECT r.id, r.title, r.description, r.file_path, r.file_type, "
                   + "r.created_at, r.uploaded_by, r.category_id, u.full_name as author_username, "
                   + "c.name as category_name "
                   + "FROM resources r "
                   + "JOIN users u ON r.uploaded_by = u.id "
                   + "LEFT JOIN categories c ON r.category_id = c.id "
                   + "ORDER BY r.created_at DESC "
                   + "LIMIT 4";
        
        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            Resource r = new Resource();
            r.setId(rs.getInt("id"));
            r.setTitle(rs.getString("title"));
            r.setDescription(rs.getString("description"));
            r.setFilePath(rs.getString("file_path"));
            r.setFileType(rs.getString("file_type"));
            r.setUploadedBy(rs.getInt("uploaded_by"));
            r.setCategoryId(rs.getInt("category_id"));
            r.setAuthorUserName(rs.getString("author_username"));
            r.setCategoryName(rs.getString("category_name"));
            r.setCreatedAt(rs.getObject("created_at", LocalDateTime.class));
            resources.add(r);
        }

        rs.close();
        pst.close();
        con.close();
        return resources;
    }

    public Resource getResourceById(int id) throws Exception {
        Connection con = DBconfig.getDbConnection();
        String sql = "SELECT r.*, u.full_name as author_username, c.name as category_name " 
            + "FROM resources r "
            + "JOIN users u ON r.uploaded_by = u.id "
            + "LEFT JOIN categories c ON r.category_id = c.id "
            + "WHERE r.id = ? ";
        
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();

        Resource resource = null;
        if (rs.next()) {
            resource = new Resource();
            resource.setId(rs.getInt("id"));
            resource.setTitle(rs.getString("title"));
            resource.setDescription(rs.getString("description"));
            resource.setFilePath(rs.getString("file_path"));
            resource.setFileType(rs.getString("file_type"));
            resource.setUploadedBy(rs.getInt("uploaded_by"));
            resource.setCategoryId(rs.getInt("category_id"));
            resource.setCategoryName(rs.getString("category_name"));
            resource.setAuthorUserName(rs.getString("author_username"));
            resource.setCreatedAt(rs.getObject("created_at", LocalDateTime.class));
        }

        rs.close();
        pst.close();
        con.close();
        return resource;
    }
    
    public boolean deleteResource(int resourceId, int userId, String userRole) throws Exception {
        Connection con = DBconfig.getDbConnection();
        
        String sql;
        if ("Admin".equalsIgnoreCase(userRole)) {
            sql = "DELETE FROM resources WHERE id = ?";
        } else if ("Faculty".equalsIgnoreCase(userRole)) {
            sql = "DELETE FROM resources WHERE id = ? AND uploaded_by = ?";
        } else {
        	return false; // users cannot delete resource
        }
        
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setInt(1, resourceId);
        if (!"Admin".equalsIgnoreCase(userRole)) {
            pst.setInt(2, userId);
        }
        
        int rows = pst.executeUpdate();
        pst.close();
        con.close();
        return rows > 0;
    }
}