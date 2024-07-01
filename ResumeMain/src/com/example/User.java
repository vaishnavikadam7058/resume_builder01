package com.example;

import java.awt.image.BufferedImage;

public class User {
    private String username;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String address;
    private String profile;
    private String education;
    private String institution;
    private String graduationYear;
    private BufferedImage image;

    public User(String username, String firstName, String lastName, String phone, String email, String address, String profile, String education, String institution, String graduationYear, BufferedImage image) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.profile = profile;
        this.education = education;
        this.institution = institution;
        this.graduationYear = graduationYear;
        this.image = image;
    }

   

    public User(String username2, String firstName2, String lastName2, String phone2, String address2,
			String profile2) {
		
	}

	
	public String getUsername() {
        return username;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getProfile() {
        return profile;
    }

    public String getEducation() {
        return education;
    }

    public String getInstitution() {
        return institution;
    }

    public String getGraduationYear() {
        return graduationYear;
    }

    public BufferedImage getImage() {
        return image;
    }
}
