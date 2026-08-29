package com.Anchal.firstjobApp.Person.DTO;

public class UserCredsInput {
    private Long id;
    private String emailId;
    private String password;

    public UserCredsInput() {
    }

    public UserCredsInput(Long id, String emailId, String password) {
        this.id = id;
        this.emailId = emailId;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
