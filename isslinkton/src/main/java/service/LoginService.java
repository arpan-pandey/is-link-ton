package com.islinkton.service;

import com.islinkton.dao.UserDAO;
import com.islinkton.model.UserModel;
import com.islinkton.utils.PasswordUtil;

public class LoginService {

    public UserModel login(String email, String password) {

        try {
            UserDAO dao = new UserDAO();
            UserModel user = dao.login(email);

            if (user != null && PasswordUtil.checkPassword(password, user.getPassword())) {
                return user;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}