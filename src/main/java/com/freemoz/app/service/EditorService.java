package com.freemoz.app.service;

import com.freemoz.app.dao.UserDAO;
import com.freemoz.app.dto.UserDTO;
import com.freemoz.app.util.Password;

public class EditorService {

    private UserDAO userDAO;
    private Password password;

    public EditorService() {
        this.userDAO = new UserDAO();
        this.password = new Password();
    }

    public UserDTO loginUser(String username, String password) {
        UserDTO user = this.userDAO.getUserByUsername(username);

        if (user == null) {
            return null;
        }

        String hashed = this.password.hashPassword(password, user.getUserDTOData().getHashStretch());

        if (hashed.equals(user.getPassword())) {
            return user;
        }

        return user;
    }
}
