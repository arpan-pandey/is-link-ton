package com.islinkton.service;

import java.util.List;

import com.islinkton.dao.UserDAO;
import com.islinkton.model.UserModel;

public class AdminService {

    public List<UserModel> getAllStudents() {

        try {
            UserDAO dao = new UserDAO();
            return dao.getStudents();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}