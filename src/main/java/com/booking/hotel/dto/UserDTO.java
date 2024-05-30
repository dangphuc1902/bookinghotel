package com.booking.hotel.dto;

public class UserDTO {

    private int id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private RoleDTO role;
    private String avatar;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public RoleDTO getRole() {
        return role;
    }

    public void setRole(RoleDTO role) {
        this.role = role;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public UserDTO(){

    }

    public UserDTO(int id, String email, String firstName, String lastName, RoleDTO role, String avatar) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        setImageDynamic(avatar);
    }

    // Phương thức để thiết lập đường dẫn ảnh động
    public void setImageDynamic(String imageName) {
        System.out.println(imageName);
        // Đường dẫn cơ bản của ảnh
        String basePath = "http://localhost:8088/file/";
        // Xây dựng đường dẫn đầy đủ
        if(imageName == null || imageName.isEmpty()){
            this.avatar = null;
        }else {
            this.avatar = basePath + imageName;
        }

    }
}
