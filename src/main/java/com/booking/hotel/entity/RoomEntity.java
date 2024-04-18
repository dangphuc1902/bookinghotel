package com.booking.hotel.entity;

import com.booking.hotel.payload.request.BookingRequest;
import jakarta.persistence.*;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "room")
public class RoomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "room_type")
    private String roomType;

    @Column(name = "room_price")
    private double roomPrice;

    @Column(name = "image")
    private String image;

    @Column(name = "is_booked")
    private boolean isBooked = false;

    @OneToMany(mappedBy="room", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<BookingEntity> bookings;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public double getRoomPrice() {
        return roomPrice;
    }

    public void setRoomPrice(double roomPrice) {
        this.roomPrice = roomPrice;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isBooked() {
        return isBooked;
    }

    public void setBooked(boolean booked) {
        isBooked = booked;
    }

    public List<BookingEntity> getBookings() {
        return bookings;
    }

    public void setBookings(List<BookingEntity> bookings) {
        this.bookings = bookings;
    }
//    public void setUser(UsersEntity user) {
//        this.user = user;
//    }
    public String addBooking(BookingEntity bookingEntity){
        if (bookings == null){
            bookings = new ArrayList<>();
        }
        bookings.add(bookingEntity);
        bookingEntity.setRoom(this);
        isBooked = true;
        String bookingCode = RandomStringUtils.randomNumeric(10);
        bookingEntity.setConfirmationCode(bookingCode);
        return bookingCode;
    }
}
