package com.booking.hotel.dto;

import org.springframework.context.annotation.Primary;

import java.time.LocalDate;

public class BookingDto {
    private int id;
    private LocalDate checkInRoom;
    private LocalDate checkOutRoom;
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

    private int guestAdults;
    private int guestChildren;
    private int bookingTotalGuest;
    private String bookingConfirmationCode;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getCheckInRoom() {
        return checkInRoom;
    }

    public void setCheckInRoom(LocalDate checkInRoom) {
        this.checkInRoom = checkInRoom;
    }

    public LocalDate getCheckOutRoom() {
        return checkOutRoom;
    }

    public void setCheckOutRoom(LocalDate checkOutRoom) {
        this.checkOutRoom = checkOutRoom;
    }

    public int getGuestAdults() {
        return guestAdults;
    }

    public void setGuestAdults(int guestAdults) {
        this.guestAdults = guestAdults;
    }

    public int getGuestChildren() {
        return guestChildren;
    }

    public void setGuestChildren(int guestChildren) {
        this.guestChildren = guestChildren;
    }

    public int getBookingTotalGuest() {
        return bookingTotalGuest;
    }

    public void setBookingTotalGuest(int bookingTotalGuest) {
        this.bookingTotalGuest = bookingTotalGuest;
    }

    public String getBookingConfirmationCode() {
        return bookingConfirmationCode;
    }

    public void setBookingConfirmationCode(String bookingConfirmationCode) {
        this.bookingConfirmationCode = bookingConfirmationCode;
    }

}
