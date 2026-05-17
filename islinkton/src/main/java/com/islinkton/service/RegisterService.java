package com.islinkton.service;

import com.islinkton.dao.UserDAO;
import com.islinkton.model.UserModel;
import com.islinkton.utils.PasswordUtil;

public class RegisterService {

    public boolean registerUser(UserModel user) {

        try {
            user.setPassword(PasswordUtil.hashPassword(user.getPassword()));

            UserDAO dao = new UserDAO();
            dao.insertUser(user);

            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
}