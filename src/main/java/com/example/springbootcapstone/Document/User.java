package com.example.springbootcapstone.Document;

import com.example.springbootcapstone.CustomAnnotations.ValidPassword;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.List;



@Document

public class User implements UserDetails {


    @Id
    private String userId;
    @Email(message = "enter the proper mail id")
    @NotBlank(message = "username couldn't be empty")
   private String userName;

    @NotBlank(message = "password couldn't be empty")
    @ValidPassword
   private String password;
    @Size(min = 3,message = "enter mininum 3 character")
    @NotBlank(message = "firstName couldn't be empty")
   private String firstName;

    @NotBlank(message = "lastName couldn't be empty")
   private String lastName;

    @NotBlank(message = "role couldn't be empty")
   private String role;
    @NotBlank(message = "DOB couldn't be empty")
   private String DOB;

    @NotBlank(message = "Gender couldn't be empty")
   private String gender;

    boolean accountNotExpired=true;

    boolean accountNonLocked=true;

    boolean credentialsNonExpired=true;

    boolean enabled=false;

    List<GrantedAuthority> authorities;

    boolean passwordChangeable=false;


    public User(String userName, String password, String firstName, String lastName, String role,String DOB, String gender) {
        this.userName = userName;
        System.out.println(userName);
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.DOB = DOB;
        this.gender=gender;
        this.role=role;
        this.authorities=List.of(new SimpleGrantedAuthority(role));
    }




    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }



    @Override
    public boolean isAccountNonExpired() {
        return accountNotExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setPassword( String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setAccountNotExpired(boolean accountNotExpired) {
        this.accountNotExpired = accountNotExpired;
    }

    public void setAccountNonLocked(boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }

    public void setCredentialsNonExpired(boolean credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getDOB() {
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public boolean isPasswordChangeable() {
        return passwordChangeable;
    }

    public void setPasswordChangeable(boolean passwordChangeable) {
        this.passwordChangeable = passwordChangeable;
    }
}
