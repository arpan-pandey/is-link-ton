package com.islinkton.dao;

import com.islinkton.model.Petition;
import com.islinkton.utils.DBconfig;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class PetitionDAO {

    public boolean createPetition(Petition petition) throws Exception {
        Connection con = DBconfig.getDbConnection();
        String sql = "INSERT INTO petitions (title, content, created_by) VALUES (?, ?, ?)";
        PreparedStatement pst = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        
        pst.setString(1, petition.getTitle());
        pst.setString(2, petition.getContent());
        pst.setString(3, petition.getCreatorUsername());

        int rows = pst.executeUpdate();
        pst.close();
        con.close();
        return rows > 0;
    }

    public List<Petition> getAllApprovedPetitions() throws Exception {
        List<Petition> petitions = new ArrayList<>();
        Connection con = DBconfig.getDbConnection();
        
        String sql = " SELECT p.*, u.username as creator_username " 
            + "FROM petitions p "
            + "JOIN users u ON p.created_by = u.id " 
            + "WHERE p.is_approved = TRUE "
            + "ORDER BY p.created_at DESC ";
        
        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            Petition p = new Petition();
            p.setId(rs.getInt("id"));
            p.setTitle(rs.getString("title"));
            p.setContent(rs.getString("content"));
            p.setCreatorUsername(rs.getString("creator_username"));
            p.setCreatedAt(rs.getObject("created_at", LocalDateTime.class));
            petitions.add(p);
        }

        rs.close();
        pst.close();
        con.close();
        return petitions;
    }
    
    public List<Petition> getRecentPetitions() throws Exception {
        List<Petition> petitions = new ArrayList<>();
        Connection con = DBconfig.getDbConnection();
        
        String sql = "SELECT p.id, p.title, p.content, p.created_at, " +
	                "COALESCE(u.username, 'Deleted User') AS creator_username, " +
	                "(SELECT COUNT(*) FROM petition_votes pv WHERE pv.petition_id = p.id) AS vote_count " +
	                "FROM petitions p " +
	                "LEFT JOIN users u ON p.created_by = u.id " +
	                "WHERE p.is_approved = TRUE " +
	                "ORDER BY p.created_at DESC LIMIT 3";
        
        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            Petition p = new Petition();
            p.setId(rs.getInt("id"));
            p.setTitle(rs.getString("title"));
            p.setContent(rs.getString("content"));
            p.setCreatedAt(rs.getObject("created_at", LocalDateTime.class)); 
            p.setCreatorUsername(rs.getString("creator_username"));
            p.setVoteCount(rs.getInt("vote_count"));
            petitions.add(p);
        }

        rs.close();
        pst.close();
        con.close();
        return petitions;
    }
    
    public boolean approvePetition(int petitionId) throws Exception {
        Connection con = DBconfig.getDbConnection();
        String sql = "UPDATE petitions SET is_approved = TRUE WHERE id = ?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setInt(1, petitionId);
        int rows = pst.executeUpdate();
        pst.close();
        con.close();
        return rows > 0;
    }
    
    public boolean deletePetition(int petitionId) throws Exception {
        Connection con = DBconfig.getDbConnection();
        String sql = "DELETE FROM petitions WHERE id = ?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setInt(1, petitionId);
        int rows = pst.executeUpdate();
        pst.close();
        con.close();
        return rows > 0;
    }
}