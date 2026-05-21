package com.islinkton.dao;

import com.islinkton.model.Vote;
import com.islinkton.utils.DBconfig;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VoteDAO {

    /**
     * toggles a user's vote. 
     * if already voted -> deletes vote (unvote).
     * If not voted -> inserts vote.
     */
    public boolean toggleVote(Vote vote) throws Exception {
        Connection con = DBconfig.getDbConnection();
        
        // Determine table and column configurations using simple String matching
        String table = vote.getTargetType().equalsIgnoreCase("THREAD") ? "thread_votes" : "petition_votes";
        String column = vote.getTargetType().equalsIgnoreCase("THREAD") ? "thread_id" : "petition_id";

        // Check if row already exists
        String checkSql = "SELECT id FROM " + table + " WHERE " + column + " = ? AND user_id = ?";
        PreparedStatement checkPst = con.prepareStatement(checkSql);
        checkPst.setInt(1, vote.getTargetId());
        checkPst.setInt(2, vote.getUserId());
        ResultSet rs = checkPst.executeQuery();

        boolean alreadyVoted = rs.next();
        rs.close();
        checkPst.close();

        String actionSql;
        if (alreadyVoted) {
            actionSql = "DELETE FROM " + table + " WHERE " + column + " = ? AND user_id = ?";
        } else {
            actionSql = "INSERT INTO " + table + " (" + column + ", user_id) VALUES (?, ?)";
        }

        PreparedStatement actionPst = con.prepareStatement(actionSql);
        actionPst.setInt(1, vote.getTargetId());
        actionPst.setInt(2, vote.getUserId());
        
        int rows = actionPst.executeUpdate();
        actionPst.close();
        con.close();

        return rows > 0;
    }
    
    public List<Integer> getUserVotedThreadIds(int userId) throws Exception {
        List<Integer> ids = new ArrayList<>();
        Connection con = DBconfig.getDbConnection();
        String sql = "SELECT thread_id FROM thread_votes WHERE user_id = ?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setInt(1, userId);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            ids.add(rs.getInt("thread_id"));
        }
        rs.close(); pst.close(); con.close();
        return ids;
    }

    public List<Integer> getUserVotedPetitionIds(int userId) throws Exception {
        List<Integer> ids = new ArrayList<>();
        Connection con = DBconfig.getDbConnection();
        String sql = "SELECT petition_id FROM petition_votes WHERE user_id = ?";
        PreparedStatement pst = con.prepareStatement(sql);
        pst.setInt(1, userId);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            ids.add(rs.getInt("petition_id"));
        }
        rs.close(); pst.close(); con.close();
        return ids;
    }
}