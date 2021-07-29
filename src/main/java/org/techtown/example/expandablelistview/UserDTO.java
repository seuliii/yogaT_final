package org.techtown.example.expandablelistview;

public class UserDTO {
    private String email;
    private String password;
    private String name;
    private String birth;


    public UserDTO(String email, String password, String name, String birth) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.birth = birth;
    }

    public UserDTO(String email, String password) {
        this.email = email;
        this.password = password;

    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }
}
