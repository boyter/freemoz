package com.freemoz.app.dto;

public class UserDTO {
    private String username;
    private String password;
    private UserDTOData userDTOData;

    public UserDTO(String username, String password, UserDTOData userDTOData) {
        this.username = username;
        this.password = password;
        this.userDTOData = userDTOData;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserDTOData getUserDTOData() {
        return userDTOData;
    }

    public void setUserDTOData(UserDTOData userDTOData) {
        this.userDTOData = userDTOData;
    }
}
