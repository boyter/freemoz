package com.freemoz.app.util;

import org.mindrot.jbcrypt.BCrypt;

public class Password {

    public String hashPassword(String password, int times) {
        String hashed = this.hashPassword(password);

        for (int i = 0; i < times; i++) {
            hashed = this.hashPassword(hashed);
        }

        return hashed;
    }

    private String hashPassword(String password) {
        String hashed = BCrypt.hashpw(password, BCrypt.gensalt());
        return hashed;
    }
}
