package com.tweetapp.model;

import com.tweetapp.validation.PasswordValidationAnnotation;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;



@Document(collection = "userProfileTable")

@NoArgsConstructor
@AllArgsConstructor


@PasswordValidationAnnotation
public class UserProfile {
    @Id
    private String loginid;

    @NotBlank(message ="Firstname cannot be empty")
    private String firstname;
    @NotBlank(message ="Lastname cannot be empty")
    private String lastname;

    @NotBlank(message ="Email cannot be empty")
    @Email(message ="enter valid email")
    @Indexed(unique=true)
    private String email;
    @NotBlank(message ="Password cannot be empty")
    private String password;
    @NotBlank(message ="ConfirmPassword cannot be empty")

    private String confirmpassword;

    private Long contactnumber;

    public String getLoginid() {
        return loginid;
    }

    public void setLoginid(String loginid) {
        this.loginid = loginid;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
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

    public String getConfirmpassword() {
        return confirmpassword;
    }

    public void setConfirmpassword(String confirmpassword) {
        this.confirmpassword = confirmpassword;
    }

    public Long getContactnumber() {
        return contactnumber;
    }

    public void setContactnumber(Long contactnumber) {
        this.contactnumber = contactnumber;
    }
}
