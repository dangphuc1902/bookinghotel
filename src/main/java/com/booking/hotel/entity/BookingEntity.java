package com.booking.hotel.entity;

import com.booking.hotel.dto.BookingDto;
import jakarta.persistence.*;
import org.apache.commons.lang3.RandomStringUtils;

import java.time.LocalDate;
import java.util.ArrayList;

@Entity(name = "booking")
public class BookingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "check_in")
    private LocalDate checkIn;

    @Column(name = "check_out")
    private LocalDate checkOut;
    @Column(name = "fullname")
    private String fullName;

    @Column(name = "email")
    private String userEmail;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public void setTotalGuest(int totalGuest) {
        this.totalGuest = totalGuest;
    }

    @Column(name = "adults")
    private int adults;

    @Column(name = "children")
    private int children;

    @Column(name = "total_guest")
    private int totalGuest;

    @Column(name = "confirmation_code")
    private String confirmationCode;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private RoomEntity room;

    public void calculateTotalNumberOfGuest() {
        this.totalGuest = this.adults + children;
    }

    public void setAdults(int adults) {
        this.adults = adults;
        calculateTotalNumberOfGuest();
    }

    public void setChildren(int children) {
        this.children = children;
        calculateTotalNumberOfGuest();
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getChildren() {
        return children;
    }

    public int getTotalGuest() {
        return totalGuest;
    }
    public void setTotalGuest() {
        this.totalGuest = getAdults() + getChildren();
    }
    public String getConfirmationCode() {
        return confirmationCode;
    }

    public void setConfirmationCode(String confirmationCode) {
        this.confirmationCode = confirmationCode;
    }

    public RoomEntity getRoom() {
        return room;
    }

    public void setRoom(RoomEntity room) {
        this.room = room;
    }

}
