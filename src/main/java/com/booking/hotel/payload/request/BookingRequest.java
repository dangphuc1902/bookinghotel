package com.booking.hotel.payload.request;

import com.booking.hotel.entity.RoomEntity;
import com.booking.hotel.entity.UsersEntity;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.time.LocalDate;

public class BookingRequest {
    private int id;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private String fullNameGuest;
    private String userEmail;

    public String getFullNameGuest() {
        return fullNameGuest;
    }

    public void setFullNameGuest(String fullNameGuest) {
        this.fullNameGuest = fullNameGuest;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
    private int adults;
    private int children;
    private String confirmationCode;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UsersEntity users(){
        return users();
    }
    public LocalDate getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDate checkIn) {
        this.checkIn = checkIn;
    }

        public LocalDate getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDate checkOut) {
        this.checkOut = checkOut;
    }

    public int getAdults() {
        return adults;
    }

    public void setAdults(int adults) {
        this.adults = adults;
    }

    public int getChildren() {
        return children;
    }

    public void setChildren(int children) {
        this.children = children;
    }

    public String getConfirmationCode() {
        return confirmationCode;
    }

    public void setConfirmationCode(String confirmationCode) {
        this.confirmationCode = confirmationCode;
    }
}
