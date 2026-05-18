package com.islinkton.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.islinkton.model.Resource;
import com.islinkton.utils.DBconfig;

public class ResourceDAO {

    public void insertResource(Resource resource) throws Exception {

        Connection con = DBconfig.getDbConnection();

        String sql = "INSERT INTO resources (title, description, file_path, uploaded_by, module_name) "
                   + "VALUES (?, ?, ?, ?, ?)";

        PreparedStatement pst = con.prepareStatement(sql);

        pst.setString(1, resource.getTitle());
        pst.setString(2, resource.getDescription());
        pst.setString(3, resource.getFilePath());
        pst.setInt(4, resource.getUploadedBy());
        pst.setString(5, resource.getModuleName());

        pst.executeUpdate();

        pst.close();
        con.close();
    }

    public List<Resource> getAllResources() throws Exception {

        List<Resource> list = new ArrayList<>();

        Connection con = DBconfig.getDbConnection();

        String sql = "SELECT * FROM resources ORDER BY id DESC";
        PreparedStatement pst = con.prepareStatement(sql);

        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            Resource r = new Resource();
            r.setId(rs.getInt("id"));
            r.setTitle(rs.getString("title"));
            r.setDescription(rs.getString("description"));
            r.setFilePath(rs.getString("file_path"));
            r.setUploadedBy(rs.getInt("uploaded_by"));
            r.setModuleName(rs.getString("module_name"));

            list.add(r);
        }

        rs.close();
        pst.close();
        con.close();

        return list;
    }

    public void updateResource(Resource resource) throws Exception {

        Connection con = DBconfig.getDbConnection();

        String sql = "UPDATE resources SET title=?, description=?, file_path=?, module_name=? WHERE id=?";
        PreparedStatement pst = con.prepareStatement(sql);

        pst.setString(1, resource.getTitle());
        pst.setString(2, resource.getDescription());
        pst.setString(3, resource.getFilePath());
        pst.setString(4, resource.getModuleName());
        pst.setInt(5, resource.getId());

        pst.executeUpdate();

        pst.close();
        con.close();
    }

    public void deleteResource(int id) throws Exception {

        Connection con = DBconfig.getDbConnection();

        String sql = "DELETE FROM resources WHERE id=?";
        PreparedStatement pst = con.prepareStatement(sql);

        pst.setInt(1, id);

        pst.executeUpdate();

        pst.close();
        con.close();
    }
}