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
        String sql = "INSERT INTO petitions (title, content, created_by, category_id) VALUES (?, ?, ?, ?)";
        PreparedStatement pst = con.prepareStatement(sql);
        
        pst.setString(1, petition.getTitle());
        pst.setString(2, petition.getContent());
        pst.setInt(3, petition.getCreatorId());
        pst.setInt(4, petition.getCategoryId());

        int rows = pst.executeUpdate();
        pst.close();
        con.close();
        return rows > 0;
    }

    public List<Petition> getAllApprovedPetitions() throws Exception {
        List<Petition> petitions = new ArrayList<>();
        Connection con = DBconfig.getDbConnection();
        
        String sql = "SELECT p.*, u.username as creator_username, c.name as category_name, "
            + "(SELECT COUNT(*) FROM petition_votes pv WHERE pv.petition_id = p.id) AS vote_count "
            + "FROM petitions p "
            + "JOIN users u ON p.created_by = u.id "
            + "JOIN categories c ON p.category_id = c.id "
            + "WHERE p.is_approved = TRUE "
            + "ORDER BY p.created_at DESC";
        
        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            Petition p = new Petition();
            p.setId(rs.getInt("id"));
            p.setTitle(rs.getString("title"));
            p.setContent(rs.getString("content"));
            p.setCreatorUserName(rs.getString("creator_username"));
            p.setCategoryName(rs.getString("category_name"));
            p.setCreatedAt(rs.getObject("created_at", LocalDateTime.class));
            p.setVoteCount(rs.getInt("vote_count"));
            petitions.add(p);
        }

        rs.close();
        pst.close();
        con.close();
        return petitions;
    }
    
    public List<Petition> getAllPetitions() throws Exception {
        List<Petition> petitions = new ArrayList<>();
        Connection con = DBconfig.getDbConnection();
        
        String sql = "SELECT p.*, u.username as creator_username, c.name as category_name, "
            + "(SELECT COUNT(*) FROM petition_votes pv WHERE pv.petition_id = p.id) AS vote_count "
            + "FROM petitions p "
            + "JOIN users u ON p.created_by = u.id "
            + "JOIN categories c ON p.category_id = c.id "
            + "ORDER BY p.created_at DESC";
        
        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            Petition p = new Petition();
            p.setId(rs.getInt("id"));
            p.setTitle(rs.getString("title"));
            p.setContent(rs.getString("content"));
            p.setCreatorUserName(rs.getString("creator_username"));
            p.setCategoryName(rs.getString("category_name"));
            p.setCreatedAt(rs.getObject("created_at", LocalDateTime.class));
            p.setVoteCount(rs.getInt("vote_count"));
            petitions.add(p);
        }

        rs.close();
        pst.close();
        con.close();
        return petitions;
    }
    
    public List<Petition> getPendingPetitions() throws Exception {
        List<Petition> petitions = new ArrayList<>();
        Connection con = DBconfig.getDbConnection();
        
        String sql = "SELECT p.*, u.username as creator_username, c.name as category_name, "
            + "(SELECT COUNT(*) FROM petition_votes pv WHERE pv.petition_id = p.id) AS vote_count "
            + "FROM petitions p "
            + "JOIN users u ON p.created_by = u.id "
            + "JOIN categories c ON p.category_id = c.id "
            + "WHERE p.is_approved = FALSE "
            + "ORDER BY p.created_at DESC";
        
        PreparedStatement pst = con.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            Petition p = new Petition();
            p.setId(rs.getInt("id"));
            p.setTitle(rs.getString("title"));
            p.setContent(rs.getString("content"));
            p.setCreatorUserName(rs.getString("creator_username"));
            p.setCategoryName(rs.getString("category_name"));
            p.setCreatedAt(rs.getObject("created_at", LocalDateTime.class));
            p.setVoteCount(rs.getInt("vote_count"));
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
                    "u.username AS creator_username, " +
                    "c.name AS category_name, " +
                    "(SELECT COUNT(*) FROM petition_votes pv WHERE pv.petition_id = p.id) AS vote_count " +
                    "FROM petitions p " +
                    "LEFT JOIN users u ON p.created_by = u.id " +
                    "LEFT JOIN categories c ON p.category_id = c.id " +
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
            p.setCreatorUserName(rs.getString("creator_username"));
            p.setCategoryName(rs.getString("category_name"));
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
    
    public boolean deletePetition(int petitionId, String userRole) throws Exception {
        if (!"Admin".equalsIgnoreCase(userRole)) {
            return false;
        }
        
        Connection con = DBconfig.getDbConnection();
        PreparedStatement pstVotes = null;
        PreparedStatement pstPetition = null;
        boolean success = false;
        
        try {
            con.setAutoCommit(false);
            
            String deleteVotesSql = "DELETE FROM petition_votes WHERE petition_id = ?";
            pstVotes = con.prepareStatement(deleteVotesSql);
            pstVotes.setInt(1, petitionId);
            pstVotes.executeUpdate();
            
            String deletePetitionSql = "DELETE FROM petitions WHERE id = ?";
            pstPetition = con.prepareStatement(deletePetitionSql);
            pstPetition.setInt(1, petitionId);
            int rows = pstPetition.executeUpdate();
            
            con.commit();
            success = rows > 0;
        } catch (Exception e) {
            if (con != null) {
                con.rollback();
            }
            throw e;
        } finally {
            if (pstVotes != null) pstVotes.close();
            if (pstPetition != null) pstPetition.close();
            if (con != null) con.close();
        }
        
        return success;
    }
}